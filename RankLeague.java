package rankLeague;

import java.io.*;
import java.util.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;

// import rankLeague.Team.*;

public class RankLeague {
    private List<Team> teams;
    private int returnCode;

    public RankLeague() {
        this.teams = null;
        this.returnCode = 0;
    }

    public void main(String[] args) {
        List<String> games;
        returnCode = 0;
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

    public List<String> readFile() {

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

    public String getFileName() {
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

    public String prepareForDisplay(List<Team> _teams) {
        Iterator<Team> iterator = _teams.iterator();
        Team team_node = null;
        while (iterator.hasNext()){
            team_node = iterator.next();
            team_node.setScore(team_node.getDraws() + (team_node.getWins() * 3));
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

            if (prev == null || team_node.getScore() != prev.getScore())
                tie_counter = count;

            dispStr += (Integer.toString(tie_counter) + ". " + team_node.toString() + System.lineSeparator());

            prev = team_node;
            count++;
        }

        return dispStr;
    }

    public void updateScore(Team _team1, Team _team2, String outcome) {
        Iterator<Team> iterator = teams.iterator();
        Team team_node = null;

        while (iterator.hasNext()) {
            team_node = iterator.next();
            if (team_node.getName().equals(_team1.getName())) {
                if (outcome.equals(_team1.getName())) {
                    team_node.setWins(team_node.getWins()+1);
                } else if (outcome.equals(_team2.getName())) {
                    team_node.setLosses(team_node.getLosses()+1);
                } else {
                    team_node.setDraws(team_node.getDraws()+1);
                }
            } else if (team_node.getName().equals(_team2.getName())) {
                if (outcome.equals(_team2.getName())) {
                    team_node.setWins(team_node.getWins()+1);
                } else if (outcome.equals(_team1.getName())) {
                    team_node.setLosses(team_node.getLosses()+1);
                } else {
                    team_node.setDraws(team_node.getDraws()+1);
                }
            }
        }
    }

    public boolean containsTeam(Team _search_team) {
        return teams.stream().anyMatch(obj -> obj.getName().contains(_search_team.getName()));
    }

    public String determineOutcome(String[] game) {
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

    public void log(String msg) {
        System.out.println(msg);
    }

    // public class Team {
    //     String name;
    //     int wins;
    //     int losses;
    //     int draws;
    //     int score;

    //     Team(String _name) {
    //         name = "";

    //         String temp[] = _name.split(" ",0);
    //         for (int i = 0; i < temp.length; i++) {
    //             if (temp[i].matches("[a-zA-Z]+"))
    //                 name += temp[i] + " ";
    //         }
    //         name = name.trim();
    //         wins = 0;
    //         losses = 0;
    //         draws = 0;
    //         score = 0;
    //     }

    //     public String toString() {
    //         return this.name + ", " + score + " pts";
    //     }

    //     public int getScore() {
    //         return this.score;
    //     }

    //     public String getName() {
    //         return this.name;
    //     }
    // }

}