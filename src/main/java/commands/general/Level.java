package commands.general;

import commands.CommandReceivedEvent;
import commands.ICommand;
import database.user.DatabaseUser;
import database.user.UserDB;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;

import java.util.ArrayList;
import java.util.Arrays;

public class Level implements ICommand {
    DatabaseUser databaseUser = new DatabaseUser();
    CommandReceivedEvent e;

    User userGiven;
    UserDB userDB;

    ArrayList<String> commandAliases = new ArrayList<>(Arrays.asList("level", "rank"));
    String category = "general";
    String exampleCommand = "level <@user>/<userID>";
    String shortCommandDescription = "Get the level of someone in this server.";
    String fullCommandDescription = "Get the level of someone in this server.";

    @Override
    public void command(CommandReceivedEvent event) {
        e = event;

        if (!e.hasArgs()) {
            userGiven = e.getAuthor();
            userDB = e.getUserDB();
        } else {
            userGiven = e.getFirstArgAsUser();

            if (userGiven == null) {
                e.getChannel().sendMessage(getFullHelp("Give a valid user ID!", e.getPrefix())).queue();
                return;
            }

            userDB = databaseUser.getUserFromDBToUserDB(userGiven.getId());
        }

        createEmbed();
    }

    public void createEmbed() {
        userDB.calculateXpForLevelUp();

        EmbedBuilder eb = getEmbed();

        eb.setThumbnail(userGiven.getAvatarUrl());
        eb.setAuthor(userGiven.getAsTag());
        eb.appendDescription("Level: " + userDB.getLevel() + "\n");
        eb.appendDescription("XP: " + userDB.getXp() + " out of " + userDB.getXpForLevelUp());
        eb.setFooter(userDB.getReps() + " total reps");

        e.getChannel().sendMessage(eb.build()).queue();
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

    @Override
    public ArrayList<String> getCommandAliases() {
        return commandAliases;
    }
}
