package commands.general;

import commands.CommandReceivedEvent;
import database.guild.DatabaseGuild;
import functions.Colors;
import net.dv8tion.jda.api.EmbedBuilder;

public class BotPrefix {

    DatabaseGuild databaseGuild = new DatabaseGuild();
    Colors colors = new Colors();

    public void command (CommandReceivedEvent e) {
        String guildPrefix = databaseGuild.getPrefixGuildInDB(e.getGuild().getId());

        EmbedBuilder eb = new EmbedBuilder();

        eb.setAuthor(e.getGuild().getName(), e.getGuild().getIconUrl(), e.getGuild().getIconUrl());
        eb.setTitle("Bot prefix in this guild is:");
        eb.setDescription("`" + guildPrefix + "`");
        eb.setColor(colors.getCurrentColor());

        e.getMessageChannel().sendMessage(eb.build()).queue();
    }
}
