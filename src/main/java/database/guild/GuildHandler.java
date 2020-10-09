package database.guild;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class GuildHandler {
    DatabaseGuild databaseGuild = new DatabaseGuild();
    GuildMessageReceivedEvent e;
    String guildID;

    public void checkGuild(GuildMessageReceivedEvent event) {
        e = event;
        guildID = e.getGuild().getId();

        if (!databaseGuild.guildExistsInDB(guildID)){
            GuildDB guildDB = new GuildDB(guildID);
            databaseGuild.addGuildToDB(guildDB);
        }
    }
}
