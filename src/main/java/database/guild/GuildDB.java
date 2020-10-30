package database.guild;

public class GuildDB {
    String guildID;
    String prefix = "!";

    public GuildDB(String guildID) {
        this.guildID = guildID;
    }

    public GuildDB(String guildID, String prefix) {
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
