package database.jokes;

public class JokeDB {
    String jokeId = "0";
    String setup;
    String punchline;


    public JokeDB(String setup, String punchline) {
        this.setup = setup;
        this.punchline = punchline;
    }

    public JokeDB(String jokeId, String setup, String punchline) {
        this.jokeId = jokeId;
        this.setup = setup;
        this.punchline = punchline;
    }

    public void setJokeId(String jokeId) {
        this.jokeId = jokeId;
    }

    public void setPunchline(String punchline) {
        this.punchline = punchline;
    }

    public void setSetup(String setup) {
        this.setup = setup;
    }

    public String getJokeId() {
        return jokeId;
    }

    public String getPunchline() {
        return punchline;
    }

    public String getSetup() {
        return setup;
    }
}
