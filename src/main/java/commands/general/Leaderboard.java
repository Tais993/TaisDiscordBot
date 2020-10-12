package commands.general;

import commands.CommandReceivedEvent;
import commands.ICommand;
import database.user.DatabaseUser;
import functions.Colors;
import net.dv8tion.jda.api.EmbedBuilder;

public class Leaderboard implements ICommand {
    CommandReceivedEvent e;
    DatabaseUser databaseUser = new DatabaseUser();
    Colors colors = new Colors();

    String command = "leaderboard";
    String commandAlias = "leaderboard";
    String category = "general";
    String exampleCommand = "`!leaderboard`";
    String shortCommandDescription = "Get the ranking of players";
    String fullCommandDescription = "Get the ranking of players";

    @Override
    public void command(CommandReceivedEvent event, String[] args) {
        e = event;
        EmbedBuilder eb = new EmbedBuilder(databaseUser.topTenLeaderboard(e));

        eb.setColor(colors.getCurrentColor());
        eb.setTitle("Leaderboard");

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
