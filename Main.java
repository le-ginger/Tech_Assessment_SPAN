import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Main {
    private static List<Team> teams;
    private static boolean debug_mode;

    public static void main(String[] args) {
        final String home = System.getProperty("user.home");
        final String path = home + File.separator + "Downloads" + File.separator + "input.txt";

//        debug_mode = true;
        debug_mode =false;

        List<String> games = Collections.emptyList();

//        System.out.println(path);

        try {
            games = Files.readAllLines(Paths.get(path), StandardCharsets.UTF_8);
        } catch (IOException ioException) {
            System.out.println(ioException.getMessage());
        }

        teams = new ArrayList<Team>(); //new Team[0];
        Team o_team1, o_team2;

        Iterator<String> iterator = games.iterator();
        String outcome = "";
        String line[];
        String t1 = "", t2 = "";

        while (iterator.hasNext()) {
            line = iterator.next().split(", ", 0);
            o_team1 = null;
            o_team2 = null;

            o_team1 = new Team(line[0]);
            o_team2 = new Team(line[1]);

            if (!containsTeam(o_team1))
                teams.add(o_team1);

            if (!containsTeam(o_team2))
                teams.add(o_team2);

            outcome = determineOutcome(line);
            updateScore(o_team1, o_team2, outcome);
        }

        log(prepareForDisplay(teams));
    }

    public static String prepareForDisplay(List<Team> _teams) {
        Iterator<Team> iterator = _teams.iterator();
        Team team_node = null;
        while (iterator.hasNext()){
            team_node = iterator.next();
            team_node.score = team_node.draws + (team_node.wins * 3);
        }
        _teams.sort(Comparator.comparing(Team::getScore).reversed().thenComparing(Team::getName));
        int count = 1;
        iterator = _teams.iterator();

        String dispStr = "";

        while (iterator.hasNext()) {
            team_node = iterator.next();
            dispStr += (Integer.toString(count) + ". " + team_node.toString() + System.lineSeparator());
            count++;
        }

        return dispStr;

//        log(teams.toString());
    }

    public static void updateScore(Team _team1, Team _team2, String outcome) {
        Iterator<Team> iterator = teams.iterator();
        Team team_node = null;
//        log(outcome);

        while (iterator.hasNext()) {
            team_node = iterator.next();
//            log("loop node of teams arr -> " + team_node.name);
            if (team_node.name.equals(_team1.name)) {
//                log("team1 = "+_team1.name + "\t" + outcome + " == " + _team1.name + " => " + Boolean.toString(outcome.equals(_team1.name)));
//                log("team1 = "+_team1.name + "\t" + outcome + " == " + _team1.name + " => " + Boolean.toString(outcome.trim()== _team1.name.trim()));
                if (outcome.equals(_team1.name)) {
//                    log("team1 won -> " + _team1.name + " wins + 1");
                    team_node.wins++;
                } else if (outcome.equals(_team2.name)) {
//                    log("team2 lost -> " + _team2.name + " losses + 1");
                    team_node.losses++;
                } else {
//                    log("update draw for "+team_node.name);
                    team_node.draws++;
                }
            } else if (team_node.name.equals(_team2.name)) {
//                log("team2 = " + _team2.name);
                if (outcome.equals(_team2.name)) {
//                    log("team2 ("+_team2.name+") wins");
                    team_node.wins++;
                } else if (outcome.equals(_team1.name)) {
//                    log("team1 ("+_team1.name+") losses");
                    team_node.losses++;
                } else {
//                    log("update draw for "+team_node.name);
                    team_node.draws++;
                }
            }
        }

//        System.out.println("-----------");

    }

    public static boolean containsTeam(Team _search_team) {
//        log(_search_team.name);
//        log(Boolean.toString(teams.stream().filter(obj -> obj.name.contains(_search_team.name)).findFirst().isPresent()));
        return teams.stream().anyMatch(obj -> obj.name.contains(_search_team.name));
    }

    public static String determineOutcome(String[] game) {
        // rather return object with wins and losses

        String[] team1 = game[0].split(" ", 0);
        String[] team2 = game[1].split(" ", 0);

        if (Integer.parseInt(team1[team1.length-1]) == Integer.parseInt(team2[team2.length-1])) {
            // draw
            return "draw";
        } else if (Integer.parseInt(team1[team1.length-1]) > Integer.parseInt(team2[team2.length-1])) {
            // team 1 wins
            return team1[0];
        } else {
            // team 2 wins
            return team2[0];
        }
    }

    public static void log(String msg) {
        System.out.println(msg);// + "\n");
//        System.out.println(msg + "\n");
    }

    public static class Team {
        String name;
        int wins;
        int losses;
        int draws;
        int score;

        Team(String _name) {
            name = "";

            String temp[] = _name.split(" ",0);
            for (int i = 0; i < temp.length; i++) {
                if (temp[i].matches("[a-zA-Z]+"))
                    name += temp[i] + " ";
            }
            name = name.trim();
            wins = 0;
            losses = 0;
            draws = 0;
            score = 0;
        }

        public String toString() {
            if (!debug_mode)
                return this.name + ", " + score + " pts";// + System.lineSeparator();
            else
                return System.lineSeparator() +
                       "Team name: " + this.name + System.lineSeparator() +
                       "Wins:      " + this.wins + System.lineSeparator() +
                       "Losses:    " + this.losses + System.lineSeparator() +
                       "Draws:     " + this.draws + System.lineSeparator() +
                        "===========================";
        }

        public int getScore() {
            return this.score;
        }

        public String getName() {
            return this.name;
        }
    }

}