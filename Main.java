import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;

public class Main {
    private static List<Team> teams;
    private static boolean debug_mode;
    private static int returnCode;

    public static void main(String[] args) {
        returnCode = 0;

        // final String home = System.getProperty("user.home");
        // final String path = home + File.separator + "Downloads" + File.separator + "input2.txt";

    //    debug_mode = true;
        debug_mode =false;

        List<String> games;

        games = readFile();

        // -1 = file not found
        // -2 = file empty
        // -3 = input chances depleted
        if (returnCode == -1) {
            log("File not found");
            return;
        } else if (returnCode == -2) {
            log("File empty");
            return;
        } else if (returnCode == -3) {
            log("Input chances depleted, please restart the program");
            return;
        }

        teams = new ArrayList<Team>();
        Team o_team1, o_team2;

        Iterator<String> iterator = games.iterator();
        String outcome = "";
        String line[];

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

    public static List<String> readFile() {

        String fileName = getFileName();
        File directory = new File("." + File.separator);
        List<String> games = Collections.emptyList();
        String path = directory.getAbsolutePath().substring(0, directory.getAbsolutePath().length()-1)+ fileName;

        try {
            games = Files.readAllLines(Paths.get(path), StandardCharsets.UTF_8);
            if (games.size() == 0)
                returnCode = -2;
            return games;
        } catch (IOException ioException) {
            log(ioException.getMessage());
            returnCode = -1;
            return null;
        }
    }

    public static String getFileName() {
        Scanner sc = new Scanner(System.in);

        log("What is the name of the input file?" + System.lineSeparator());
        int askChances = 0;

        while (askChances < 10) {
            String fileName = sc.next();

            if (fileName != "" && fileName != null) {
                sc.close();
                return fileName;
            } else {
                log("Please enter a valid file name");
            }
            askChances++;
        }

        if (askChances == 10) {
            returnCode = -3;
        }

        sc.close();
        return null;
    }

    public static String prepareForDisplay(List<Team> _teams) {
        Iterator<Team> iterator = _teams.iterator();
        Team team_node = null;
        while (iterator.hasNext()){
            team_node = iterator.next();
            team_node.score = team_node.draws + (team_node.wins * 3);
        }

        // sort teams by score and then by name
        _teams.sort(Comparator.comparing(Team::getScore).reversed().thenComparing(Team::getName));

        int count = 1;
        int tie_counter = 1;
        iterator = _teams.iterator();

        String dispStr = "";
        Team prev = null;


        while (iterator.hasNext()) {
            team_node = iterator.next();

            if (prev == null || team_node.score != prev.score)
                tie_counter = count;

            dispStr += (Integer.toString(tie_counter) + ". " + team_node.toString() + System.lineSeparator());

            prev = team_node;
            count++;
        }

        debug_log("====================" + 
                    System.lineSeparator() + 
                    "prepared string for display"  + 
                    System.lineSeparator() +
                    "====================" +
                    dispStr +
                    System.lineSeparator()
                    );

        return dispStr;
    }

    public static void updateScore(Team _team1, Team _team2, String outcome) {
        Iterator<Team> iterator = teams.iterator();
        Team team_node = null;
        debug_log("updateScore with " + _team1.name + " vs " + _team2.name);
        debug_log("outcome of the game: " + outcome);

        while (iterator.hasNext()) {
            team_node = iterator.next();
            debug_log("loop node of teams arr -> " + team_node.name);
            if (team_node.name.equals(_team1.name)) {
                debug_log("team1 = "+_team1.name + "\t" + outcome + " == " + _team1.name + " => " + Boolean.toString(outcome.equals(_team1.name)));
                if (outcome.equals(_team1.name)) {
                   debug_log("team1 won -> " + _team1.name + " wins + 1");
                    team_node.wins++;
                } else if (outcome.equals(_team2.name)) {
                    debug_log("team2 lost -> " + _team2.name + " losses + 1");
                    team_node.losses++;
                } else {
                    debug_log("update draw for "+team_node.name);
                    team_node.draws++;
                }
            } else if (team_node.name.equals(_team2.name)) {
                debug_log("team2 = " + _team2.name);
                if (outcome.equals(_team2.name)) {
                    debug_log("team2 ("+_team2.name+") wins");
                    team_node.wins++;
                } else if (outcome.equals(_team1.name)) {
                    debug_log("team1 ("+_team1.name+") losses");
                    team_node.losses++;
                } else {
                    debug_log("update draw for "+team_node.name);
                    team_node.draws++;
                }
            }
        }
    }

    public static boolean containsTeam(Team _search_team) {
        debug_log("containsTeam -> looking for " + _search_team.name);
        boolean res = teams.stream().anyMatch(obj -> obj.name.contains(_search_team.name));
        debug_log(Boolean.toString(res));
        return res;
    }

    public static String determineOutcome(String[] game) {
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
        System.out.println(msg);
    }

    public static void debug_log(String msg) {
        if (debug_mode)
            System.out.println(new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new java.util.Date()) + "\t" + msg);
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