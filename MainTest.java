import java.util.ArrayList;
import java.util.List;
// import rankLeague.*;

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
    static void prepareForDisplay() {
        List<Team> teams = new ArrayList<Team>();
        RankLeague test = new RankLeague();

        Team team[] = new Team[6];
        team[0] = new Team("Sharks");
        team[1] = new Team("Bulls");
        team[2] = new Team("Stormers");
        team[3] = new Team("Lions");
        team[4] = new Team("Cheetahs");
        team[5] = new Team("Pumas");

        team[0].setWins(2);
        team[1].setWins(1);
        team[2].setWins(2);
        team[3].setWins(1);
        team[4].setWins(1);
        team[5].setWins(0);

        team[0].setDraws(1);
        team[1].setDraws(2);
        team[2].setDraws(3);
        team[3].setDraws(2);
        team[4].setDraws(1);
        team[5].setDraws(4);

        team[0].setLosses(0);
        team[1].setLosses(1);
        team[2].setLosses(1);
        team[3].setLosses(1);
        team[4].setLosses(1);
        team[5].setLosses(1);

        teams.add(team[0]);
        teams.add(team[1]);
        teams.add(team[2]);
        teams.add(team[3]);
        teams.add(team[4]);
        teams.add(team[5]);

        String expected = "1. Stormers, 9 pts" + System.lineSeparator() +
                          "2. Sharks, 7 pts" + System.lineSeparator() +
                          "3. Bulls, 5 pts" + System.lineSeparator() +
                          "3. Lions, 5 pts" + System.lineSeparator() +
                          "5. Cheetahs, 4 pts" + System.lineSeparator() +
                          "5. Pumas, 4 pts" + System.lineSeparator();

        assertEquals(expected, test.prepareForDisplay(teams));
    }
}