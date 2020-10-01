package commands.general;

import database.guild.DatabaseGuild;
import functions.Colors;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class BotPrefix {

    DatabaseGuild databaseGuild = new DatabaseGuild();
    Colors colors = new Colors();

    public void command (GuildMessageReceivedEvent e) {
        String guildPrefix = databaseGuild.getPrefixGuildInDB(e.getGuild().getId());

        EmbedBuilder eb = new EmbedBuilder();

        eb.setAuthor(e.getGuild().getName(), e.getGuild().getIconUrl(), e.getGuild().getIconUrl());
        eb.setTitle("Bot prefix in this guild is:");
        eb.setDescription("`" + guildPrefix + "`");
        eb.setColor(colors.getCurrentColor());

        e.getChannel().sendMessage(eb.build()).queue();
    }
}
