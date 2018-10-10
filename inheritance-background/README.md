# InheritanceBackground
Project where you design and implement the basic setup for a Link / Zelda Game

[What is Inheritance?](http://java.sun.com/docs/books/tutorial/java/concepts/inheritance.html) - very basic primer on inheritance in OO languages

[Managing Inheritance](https://docs.oracle.com/javase/tutorial/java/IandI/subclasses.html) - how class hierarchies work
Specifications

[Project Setup](https://docs.google.com/document/d/1iHi5su31MD-YcwSnSosE0mPXrXGqBPHlJt3FdYo-OJ8/edit?usp=sharing)

## Getting updates on the project 

Be sure to add the base repository as a remote repository as I will be updating this README to further help you with the project. Clone your project and in Terminal enter this command,

```
git remote add teacherSpecs https://github.com/jd12/InheritanceBackground.git
```

Then when working on your project 

```
git checkout master
git pull teacherSpecs master
```

# Assignment 

1. Create a parent class called Tile that you can use to easily build your level. Your code should be set up in a way such that if I wanted to add an additional Tile to your level, you could give me the API for your Tile class (e.g. Robocode's RobotPart) and I could create my own custom tile to integrate into your level.
2. You should have 3 custom made tiles in your level. 

## Bonus

1. Implement a character that is controlled by the arrow keys. Have your character "react" to the different tiles (e.g. on a water tile, your character "swims" and on land tiles, your character "walks"
