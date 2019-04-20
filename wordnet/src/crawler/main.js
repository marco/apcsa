let cheerio = require('cheerio')
let request = require('request-promise-native')
let urlRegExp = require('url-regex')
let _ = require('lodash')
let fs = require('fs');
let url = require('url');

const FILENAME = 'result-new.json';
const MAX_TIME = 1000 * 60 * 40;
const TIMEOUT = 1000 * 25;
const MAX_SITES = 100000;
const ROOT_SITES = [
    'http://google.com',
    'http://apple.com',
    'http://wikipedia.org',
    'http://github.com',
    'http://youtube.com',
    'http://youtube.com',
    'http://twitter.com',
    'http://facebook.com',
]

class Crawler {
    constructor(rootSites, maxSites) {
        this.siteQueue = rootSites;
        this.remainingSites = maxSites;
        this.runningRequests = 0;
        this.visitedSites = [];
        this.references = {};
        this.siteBodies = {};
        this.completedRequests = 0;
        this.hasCalledBack = false;
        this.endDate = (new Date().valueOf()) / 1000 + MAX_TIME / 1000;
        this.startEpoch = this.getCurrentEpoch();
    }

    getCurrentEpoch() {
        return (new Date().valueOf()) / 1000;
    }

    getElements($, tag, body) {
        $(tag).each((i, element) => {
            if ($(element)) {
                body.elements[tag].push($(element).text());
            }
        });
    }

    getResultsNow() {
        return { bodies: this.siteBodies, references: this.references }
    }

    endCrawl() {
        this.hasCalledBack = true;
    }

    crawl(callback) {
        let rateOfRequest = (this.completedRequests / (this.getCurrentEpoch() - this.startEpoch) || 0);
        let prediction = ((this.remainingSites + this.runningRequests) / rateOfRequest) / 60
        let remaining = (this.endDate - this.getCurrentEpoch()) / 60;
        console.log(this.remainingSites + ' remaining. ' + this.siteQueue.length + ' in queue. ' + this.runningRequests + ' running. ' + rateOfRequest.toFixed(2) + ' per second. ' + prediction.toFixed(2) + ' more minutes predicted. ' + remaining.toFixed(2) + ' allowed.');

        if (this.remainingSites <= 0 && this.runningRequests <= 0 && !this.hasCalledBack) {
            callback(this.getResultsNow());
            this.hasCalledBack = true;
            return;
        }

        while (this.siteQueue.length > 0) {
            // If we reach 0 remaining sites, just break until the `crawl`
            // is recursed over again and exited.
            if (this.remainingSites <= 0) {
                break;
            }

            let site = this.siteQueue.shift();
            if (this.visitedSites.includes(site)) {
                continue;
            }

            this.runningRequests++;
            this.remainingSites--;
            request({ method: 'GET', uri: site, timeout: TIMEOUT}).then((body) => {
                this.visitedSites.push(site);

                let $ = cheerio.load(body);
                let siteBody = {
                    // body: body,
                    keywords: ($('meta[name=keywords]').attr('content') || '').split(/[ ,]+/),
                    description: $('meta[name=description]').attr('content'),
                    author: $('meta[name=author]').attr('content'),
                    title: $('title').innerText,
                    elements: {
                        h1: [],
                        h2: [],
                        h3: [],
                        // p: [],
                    },
                };
                this.getElements($, 'h1', siteBody);
                this.getElements($, 'h2', siteBody);
                this.getElements($, 'h3', siteBody);
                // this.getElements($, 'p', siteBody);

                $('a').each((i, element) => {
                    let linkURL = $(element).attr('href');

                    if (!urlRegExp({ strict: false }).test(linkURL)) {
                        return;
                    }

                    if (!this.references[linkURL]) {
                        this.references[linkURL] = [site];
                    } else {
                        this.references[linkURL].push(site);
                    }

                    if (this.remainingSites > 0) {
                        this.siteQueue.push(url.resolve(site, linkURL));
                    }
                });

                this.siteBodies[site] = siteBody;
                this.runningRequests--;
                this.completedRequests++;
                this.crawl(callback);
            }).catch((error) => {
                this.runningRequests--;
                this.completedRequests++;
                this.crawl(callback);
            });
        }
    }
}

let crawler = new Crawler(ROOT_SITES, MAX_SITES);

crawler.crawl((result) => {
    fs.writeFile(FILENAME, JSON.stringify(result), 'utf8', () => {
        console.log('Output written to ' + FILENAME);
        process.exit(0);
    });
});

setTimeout(() => {
    let result = crawler.getResultsNow();
    crawler.endCrawl();

    fs.writeFile(FILENAME, JSON.stringify(result, null, 2), 'utf8', () => {
        console.log('Cut short. Output written to ' + FILENAME);
        process.exit(0);
    });
}, MAX_TIME);
