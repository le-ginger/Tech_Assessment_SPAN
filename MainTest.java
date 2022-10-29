import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    @org.junit.jupiter.api.Test
    void main() {
    }

    @org.junit.jupiter.api.Test
    void updateScore() {
    }

    @org.junit.jupiter.api.Test
    void containsTeam() {
    }

    @org.junit.jupiter.api.Test
    void determineOutcome() {
    }

    @org.junit.jupiter.api.Test
    void prepareForDisplay() {
        List<Main.Team> teams = new ArrayList<Main.Team>();

        Main.Team team[] = new Main.Team[3];
        team[0] = new Main.Team("Sharks");
        team[1] = new Main.Team("Bulls");
        team[2] = new Main.Team("Stormers");

        team[0].wins = 2;
        team[1].wins = 1;
        team[2].wins = 0;

        team[0].draws = 1;
        team[1].draws = 2;
        team[2].draws = 3;

        team[0].losses = 0;
        team[1].losses = 1;
        team[2].losses = 1;

        teams.add(team[0]);
        teams.add(team[1]);
        teams.add(team[2]);

        String expected = "1. Sharks, 7 pts" + System.lineSeparator() +
                          "2. Bulls, 5 pts" + System.lineSeparator() +
                          "3. Stormers, 3 pts" + System.lineSeparator();

        assertEquals(expected, Main.prepareForDisplay(teams));
    }
}