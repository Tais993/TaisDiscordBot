package commands.general;

import commands.CommandReceivedEvent;
import database.guild.DatabaseGuild;
import net.dv8tion.jda.api.EmbedBuilder;

import static functions.Colors.getCurrentColor;

public class BotPrefix {

    DatabaseGuild databaseGuild = new DatabaseGuild();

    public void command (CommandReceivedEvent e) {
        String guildPrefix = databaseGuild.getPrefixGuildInDB(e.getGuild().getId());

        EmbedBuilder eb = new EmbedBuilder();

        eb.setAuthor(e.getGuild().getName(), e.getGuild().getIconUrl(), e.getGuild().getIconUrl());
        eb.setTitle("Bot prefix in this guild is:");
        eb.setDescription("`" + guildPrefix + "`");
        eb.setColor(getCurrentColor());

        e.getMessageChannel().sendMessage(eb.build()).queue();
    }
}
