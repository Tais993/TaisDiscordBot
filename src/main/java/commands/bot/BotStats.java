package commands.bot;

import commands.ICommand;
import functions.Colors;
import functions.entities.BotInfo;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class BotStats implements ICommand {
    GuildMessageReceivedEvent e;
    BotInfo botInfo = new BotInfo();
    Colors colors = new Colors();

    String command = "botstats";
    String commandAlias = "stats";
    String category = "general";
    String exampleCommand = "!serverinfo / !serverstats";
    String shortCommandDescription = "Get info about the server.";
    String fullCommandDescription = "Get information about the server.";

    @Override
    public void command(GuildMessageReceivedEvent event, String[] args) {
        e = event;
        EmbedBuilder eb = new EmbedBuilder();

        eb.setTitle("Server stats of: " + e.getGuild().getName());
        eb.addField("Total joined guilds:",  botInfo.getJoinedGuilds() + "", false);
        eb.setThumbnail(botInfo.getAvatarUrl());
        eb.setFooter("Made by Tijs");

        eb.setColor(colors.getCurrentColor());

        e.getChannel().sendMessage(eb.build()).queue();
    }

    @Override
    public String getCommand() {
        return command;
    }

    @Override
    public String getCommandAlias() {
        return commandAlias;
    }

    @Override
    public String getCategory() {
        return category;
    }

    @Override
    public String getExampleCommand() {
        return exampleCommand;
    }

    @Override
    public String getShortCommandDescription() {
        return shortCommandDescription;
    }

    @Override
    public String getFullCommandDescription() {
        return fullCommandDescription;
    }
}
