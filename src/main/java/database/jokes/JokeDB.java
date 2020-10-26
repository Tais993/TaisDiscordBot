package database.jokes;

public class JokeDB {
    String guildID;
    String prefix = "!";


    public JokeDB(String guildID) {
        this.guildID = guildID;
    }

    public JokeDB(String guildID, String prefix) {
        this.guildID = guildID;
        this.prefix = prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getPrefix() {
        return prefix;
    }

    public String getGuildID() {
        return guildID;
    }
}
