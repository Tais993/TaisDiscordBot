package commands.bot;

import commands.CommandEnum;
import commands.CommandReceivedEvent;
import commands.ICommand;
import functions.entities.BotInfo;
import net.dv8tion.jda.api.EmbedBuilder;

public class BotStats implements ICommand {
    CommandReceivedEvent e;

    CommandEnum commandEnum = new CommandEnum();
    BotInfo botInfo = new BotInfo();

    String command = "botstats";
    String commandAlias = "stats";
    String category = "general";
    String exampleCommand = "!serverinfo / !serverstats";
    String shortCommandDescription = "Get info about the server.";
    String fullCommandDescription = "Get information about the server.";

    @Override
    public void command(CommandReceivedEvent event) {
        e = event;
        EmbedBuilder eb = getEmbed();

        eb.setTitle("Botstats");
        eb.addField("Total joined guilds:",  botInfo.getJoinedGuilds() + "", false);
        eb.addField("Total commands", commandEnum.getTotalCommands() + "", true);
        eb.addField("Created by:", e.getJDA().retrieveUserById("257500867568205824").complete().getAsTag(), true);
        eb.addField("Github:", "[Click here to get it!](https://github.com/Tais993/taisdiscordbot \"Tais Discord Bot on Githubz\")", true);
        eb.setThumbnail(botInfo.getAvatarUrl());

        e.getMessageChannel().sendMessage(eb.build()).queue();
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
