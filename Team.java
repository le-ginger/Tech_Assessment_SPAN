package rankLeague;

public class Team {
    String name;
    int wins;
    int losses;
    int draws;
    int score;

    public Team(String _name) {
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
        return this.name + ", " + score + " pts";
    }

    public String getName() {
        return this.name;
    }

    public int getScore() {
        return this.score;
    }

    public int getLosses() {
        return this.losses;
    }

    public int getWins() {
        return this.wins;
    }

    public int getDraws() {
        return this.draws;
    }

    public void setName(String _name) {
        this.name = _name;
    }

    public void setScore(int _score) {
        this.score = _score;
    }

    public void setLosses(int _losses) {
        this.losses = _losses;
    }

    public void setWins(int _wins) {
        this.wins = _wins;
    }

    public void setDraws(int _draws) {
        this.draws = _draws;
    }
}
