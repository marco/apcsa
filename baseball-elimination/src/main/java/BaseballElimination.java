import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.princeton.cs.algs4.FlowEdge;
import edu.princeton.cs.algs4.FlowNetwork;
import edu.princeton.cs.algs4.FordFulkerson;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

/**
 * A baseball eliminator, which determines which teams in a baseball season
 * are ineligible to reach first place.
 *
 * @author skunkmb
 */
public class BaseballElimination {
    /**
     * The current array of teams, ordered by their team IDs. Team IDs
     * do not necessarily correspond to the current team rankings.
     */
    private Team[] teams;

    /**
     * A map containing team names as keys and corresponding numerical
     * team IDs as values.
     */
    private Map<String, Integer> teamIDs = new HashMap<String, Integer>();

    /**
     * A map containing caches for eliminator lists for team names.
     * An eliminator list is the list of other team names that
     * contribute to the elimination of a given team. This list is empty
     * if the team is still eligible to finish first in the season.
     */
    private Map<String, List<String>> cachedEliminators = new HashMap<String, List<String>>();

    /**
     * Constructs a `BaseballElimination` session.
     *
     * @param filename The filename to read input data from.
     */
    public BaseballElimination(String filename) {
        if (filename == null) {
            throw new NullPointerException("The filename cannot be null.");
        }

        In in = new In(filename);
        int teamCount = in.readInt();
        Team[] teams = new Team[teamCount];

        for (int i = 0; i < teamCount; i++) {
            String name = in.readString();
            int winCount = in.readInt();
            int lossCount = in.readInt();
            int remainingTotal = in.readInt();
            int[] remainingGamesCount = new int[teamCount];

            for (int j = 0; j < teamCount; j++) {
                remainingGamesCount[j] = in.readInt();
            }

            teams[i] = new Team(name, winCount, lossCount, remainingTotal, remainingGamesCount, i);
            teamIDs.put(name, i);
        }

        this.teams = teams;
    }

    /**
     * Returns the number of teams in this division.
     *
     * @return The number of teams in this division.
     */
    public int numberOfTeams() {
        return teams.length;
    }

    /**
     * Returns an `Iterable` of team names in this division.
     *
     * @return The team names participating in this division.
     */
    public Iterable<String> teams() {
        return teamIDs.keySet();
    }

    /**
     * Returns the number of wins for a team with a given name.
     *
     * @param team The name of the team to return the win count for.
     * @return The number of wins for the team.
     */
    public int wins(String team) {
        if (!teamIDs.containsKey(team)) {
            throw new IllegalArgumentException("There is no team with the name " + team + ".");
        }

        if (team == null) {
            throw new NullPointerException("The team name cannot be null.");
        }

        return teams[teamIDs.get(team)].winCount;
    }

    /**
     * Returns the number of losses for a team with a given name.
     *
     * @param team The name of the team to return the loss count for.
     * @return The number of losses for the team.
     */
    public int losses(String team)  {
        if (!teamIDs.containsKey(team)) {
            throw new IllegalArgumentException("There is no team with the name " + team + ".");
        }

        if (team == null) {
            throw new NullPointerException("The team name cannot be null.");
        }

        return teams[teamIDs.get(team)].lossCount;
    }

    /**
     * Returns the number of games remaining for a team with a given name.
     * This number may be bigger than the sum of remaining games against
     * other teams within the division because a team can also play
     * teams outside of its division.
     *
     * @param team The name of the team to return the remaining game count for.
     * @return The number of remaining games for the team.
     */
    public int remaining(String team) {
        if (!teamIDs.containsKey(team)) {
            throw new IllegalArgumentException("There is no team with the name " + team + ".");
        }

        if (team == null) {
            throw new NullPointerException("The team name cannot be null.");
        }

        return teams[teamIDs.get(team)].remainingTotal;
    }

    /**
     * Returns the number of games remaining between two teams in
     * the division.
     *
     * @param team1 The name of the first team.
     * @param team2 The name of the second team.
     * @return The number of games remaining between the two teams.
     */
    public int against(String team1, String team2) {
        if (team1 == null || team2 == null) {
            throw new NullPointerException("The team name cannot be null.");
        }

        if (!teamIDs.containsKey(team1)) {
            throw new IllegalArgumentException("There is no team with the name " + team1 + ".");
        }

        if (!teamIDs.containsKey(team2)) {
            throw new IllegalArgumentException("There is no team with the name " + team2 + ".");
        }

        return teams[teamIDs.get(team1)].remainingGamesCounts[teams[teamIDs.get(team2)].teamID];
    }

    /**
     * Returns whether or not a team has been eliminated from placing
     * first in its division. This is equivalent to there being no
     * eliminators for the team.
     *
     * @param name The name of the team to check.
     * @return Whether or not the team has been eliminated.
     */
    public boolean isEliminated(String name) {
        if (name == null) {
            throw new NullPointerException("The team name cannot be null.");
        }

        if (!teamIDs.containsKey(name)) {
            throw new IllegalArgumentException("There is no team with the name " + name + ".");
        }

        return certificateOfElimination(name) != null;
    }

    /**
     * Returns an `Iterable` of teams that cause the elimination of a
     * certain team. In other words, these "eliminators" are placed above
     * the team in the best-case scenario, and they are the teams that
     * are placed on the source side of the mincut of the team graph.
     *
     * @param name The name of the team to find eliminators for.
     * @return The `Iterable` of eliminators, or `null` if the team
     * can be placed first.
     */
    public Iterable<String> certificateOfElimination(String name) {
        if (name == null) {
            throw new NullPointerException("The team name cannot be null.");
        }

        if (!teamIDs.containsKey(name)) {
            throw new IllegalArgumentException("There is no team with the name " + name + ".");
        }

        List<String> eliminators = getEliminators(name);

        if (eliminators.size() > 0) {
            return eliminators;
        }

        return null;
    }

    /**
     * Finds the eliminators for a certain team based on the min-cut
     * of the team flow network. Eliminators are teams that prevent
     * the given team from reaching first place in the best-case scenario,
     * and these are the teams that are placed on the source side of
     * the graph min-cut.
     *
     * @param name The name of the team to find eliminators for.
     * @return A `List` of eliminators for the team. If the list is empty,
     * then the team can reach first place and there are no eliminators.
     */
    private List<String> getEliminators(String name) {
        if (cachedEliminators.containsKey(name)) {
            return cachedEliminators.get(name);
        }

        List<String> simpleEliminators = getSimpleEliminators(name);

        if (simpleEliminators.size() > 0) {
            cachedEliminators.put(name, simpleEliminators);
            return simpleEliminators;
        }

        FlowNetwork flow = getFlowForTeam(name);
        FordFulkerson fordFulkerson = new FordFulkerson(flow, 0, 1);
        List<Matchup> otherMatchups = getOtherMatchups(name);
        List<String> currentEliminators = new ArrayList<String>();

        for (int i = 0; i < teams.length; i++) {
            if (teams[i].name.equals(name)) {
                continue;
            }

            if (fordFulkerson.inCut(i + 2 + otherMatchups.size())) {
                currentEliminators.add(teams[i].name);
            }
        }

        cachedEliminators.put(name, currentEliminators);
        return currentEliminators;
    }

    /**
     * Returns a list of "simple" team eliminators, which exist when a
     * team has more wins than another team's wins plus their remaining
     * amount of games. In other words, even if the specified team wins
     * all of their remaining games, there may still be teams that
     * have enough wins already to guarantee elimination.
     *
     * @param name The name of the team to find "simple" eliminators for.
     * @return A `List` of the simple eliminators. This list is empty
     * if there are no simple eliminators.
     */
    private List<String> getSimpleEliminators(String name) {
        Team team = teams[teamIDs.get(name)];
        List<String> currentSimpleEliminators = new ArrayList<String>();

        for (int i = 0; i < teams.length; i++) {
            if (teams[i].name.equals(name)) {
                continue;
            }

            if (team.winCount + team.remainingTotal < teams[i].winCount) {
                currentSimpleEliminators.add(teams[i].name);
            }
        }

        return currentSimpleEliminators;
    }

    /**
     * Returns a list of remaining `Matchup`s that do not include the
     * given team. This is used to build the division's flow network.
     *
     * @param name The team to exclude.
     * @return A list of the remaining `Matchup`s.
     */
    private List<Matchup> getOtherMatchups(String name) {
        List<Matchup> currentOtherMatchups = new ArrayList<Matchup>();

        for (int i = 0; i < teams.length; i++) {
            for (int j = i + 1; j < teams.length; j++) {
                if (teams[i].name.equals(name) || teams[j].name.equals(name)) {
                    continue;
                }

                currentOtherMatchups.add(new Matchup(teams[i], teams[j], teams[i].remainingGamesCounts[j]));
            }
        }

        return currentOtherMatchups;
    }

    /**
     * Returns the `FlowNetwork` for a given team's elimination. This team's
     * matchups are not included in the graph.All other teams' division
     * matchups are included, so that the source node directs to all
     * remaining matchups with a capacity equal to the number of remaining
     * games of that matchup. Then, each matchup points to a team with
     * an infinite capacity, which each then point to the target with a
     * capcity based on the remaining games for the desired team. If
     * a team is on the source side of the min-cut for the graph, it is
     * an eliminator for the exluded team.
     *
     * @param name The name of the team to construct a `FlowNetwork` for.
     * @return The `FlowNetwork` for the team.
     */
    private FlowNetwork getFlowForTeam(String name) {
        List<Matchup> otherMatchups = getOtherMatchups(name);
        Team testingTeam = teams[teamIDs.get(name)];
        FlowNetwork flow = new FlowNetwork(2 + otherMatchups.size() + teams.length);

        for (int i = 0; i < otherMatchups.size(); i++) {
            Matchup matchup = otherMatchups.get(i);

            flow.addEdge(new FlowEdge(0, i + 2, matchup.gameCount));
            flow.addEdge(new FlowEdge(
                i + 2,
                matchup.teamA.teamID + 2 + otherMatchups.size(),
                Double.POSITIVE_INFINITY
            ));
            flow.addEdge(new FlowEdge(
                i + 2,
                matchup.teamB.teamID + 2 + otherMatchups.size(),
                Double.POSITIVE_INFINITY
            ));
        }

        for (int i = 0; i < teams.length; i++) {
            if (teams[i].name.equals(testingTeam.name)) {
                continue;
            }

            int potentialWinCapacity = testingTeam.winCount + testingTeam.remainingTotal - teams[i].winCount;

            if (potentialWinCapacity >= 0) {
                flow.addEdge(new FlowEdge(i + 2 + otherMatchups.size(), 1, potentialWinCapacity));
            } else {
                flow.addEdge(new FlowEdge(i + 2 + otherMatchups.size(), 1, 0));
            }
        }

        return flow;
    }

    /**
     * Reads in a sports division from an input file and
     * prints whether each team is mathematically eliminated
     * and a certificate of elimination for each team that is eliminated.
     *
     * @param args Command-line arguments, which are ignored.
     */
    public static void main(String[] args) {
        BaseballElimination division = new BaseballElimination("baseball-testing-files/teams5c.txt");
        for (String team : division.teams()) {
            if (division.isEliminated(team)) {
                StdOut.print(team + " is eliminated by the subset R = { ");
                for (String t : division.certificateOfElimination(team)) {
                    StdOut.print(t + " ");
                }
                StdOut.println("}");
            }
            else {
                StdOut.println(team + " is not eliminated");
            }
        }
    }

    /**
     * An object representing a team in the division.
     *
     * @author skunkmb
     */
    private class Team {
        /**
         * The name of this team.
         */
        private String name;

        /**
         * The team's current win count.
         */
        private int winCount;

        /**
         * The team's current loss count.
         */
        private int lossCount;

        /**
         * The total number of remaining games for the team. This may
         * be larger than the sum of the values of `remainingGamesCounts`,
         * because the team may play games outside of its division.
         */
        private int remainingTotal;

        /**
         * The numerical team ID for this team.
         */
        private int teamID;

        /**
         * The number of remaining games against each other team, indexed
         * by other teams' IDs. The value at the index `teamID` will always
         * be zero, since a team cannot play against itself.
         */
        private int[] remainingGamesCounts;

        /**
         * Constructs a `Team`.
         *
         * @param name The name of the team.
         * @param winCount The team's current win count.
         * @param lossCount The team's current loss count.
         * @param remainingTotal The total number of remaining games for
         * the team, including games outside of this division.
         * @param remainingGamesCounts The number of remaining games
         * against each other team, indexed by the other teams' IDs.
         * @param teamID The numerical team ID for this team.
         */
        private Team(String name, int winCount, int lossCount, int remainingTotal, int[] remainingGamesCounts, int teamID) {
            this.name = name;
            this.winCount = winCount;
            this.lossCount = lossCount;
            this.remainingTotal = remainingTotal;
            this.teamID = teamID;
            this.remainingGamesCounts = remainingGamesCounts;
        }
    }

    /**
     * An object representing the matchup between two teams.
     *
     * @author skunkmb
     */
    private class Matchup {
        /**
         * The first team within the matchup.
         */
        private Team teamA;

        /**
         * The second team within the matchup.
         */
        private Team teamB;

        /**
         * The number of remaining games between the two teams.
         */
        private int gameCount;

        /**
         * Constructs a `Matchup`.
         *
         * @param teamA The first team within the matchup.
         * @param teamB The second team within the matchup.
         * @param gameCount The number of remaining games between
         * the two teams.
         */
        private Matchup(Team teamA, Team teamB, int gameCount) {
            this.teamA = teamA;
            this.teamB = teamB;
            this.gameCount = gameCount;
        }
    }
}
