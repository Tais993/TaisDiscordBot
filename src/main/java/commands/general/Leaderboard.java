package commands.general;

import commands.ICommand;
import database.user.DatabaseUser;
import database.user.UserDB;
import functions.Colors;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.ArrayList;

public class Leaderboard implements ICommand {
    GuildMessageReceivedEvent e;
    DatabaseUser databaseUser = new DatabaseUser();
    Colors colors = new Colors();

    String command = "leaderboard";
    String commandAlias = "leaderboard";
    String category = "general";
    String exampleCommand = "`!leaderboard`";
    String shortCommandDescription = "Get the ranking of players";
    String fullCommandDescription = "Get the ranking of players";

    @Override
    public void command(GuildMessageReceivedEvent event, String[] args) {
        e = event;
        EmbedBuilder eb = new EmbedBuilder(databaseUser.topTenLeaderboard(e));

        eb.setColor(colors.getCurrentColor());
        eb.setTitle("Leaderboard");

        e.getChannel().sendMessage(eb.build()).queue();
    }

    public String getNameUser(String userId) {
        return e.getJDA().getUserById(userId).getName();
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
