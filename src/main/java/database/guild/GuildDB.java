package database.guild;

public class GuildDB {
    String guildID;
    String prefix = "!";

    String amongUsRoleId = "0";

    public GuildDB(String guildID) {
        this.guildID = guildID;
    }

    public GuildDB(String guildID, String prefix) {
        this.guildID = guildID;
        this.prefix = prefix;
    }

    public GuildDB(String guildID, String prefix, String amongUsRoleId) {
        this.guildID = guildID;
        this.prefix = prefix;
        this.amongUsRoleId = amongUsRoleId;
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

    public String getAmongUsRoleId() {
        return amongUsRoleId;
    }

    public void setAmongUsRoleId(String amongUsRoleId) {
        this.amongUsRoleId = amongUsRoleId;
    }
}
