package commands.general;

import commands.CommandReceivedEvent;
import commands.ICommand;
import database.user.DatabaseUser;
import database.user.UserDB;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;

import java.util.ArrayList;
import java.util.Arrays;

public class Reps implements ICommand {
    CommandReceivedEvent e;

    DatabaseUser databaseUser = new DatabaseUser();

    String userId;
    User user;

    UserDB userDB;

    ArrayList<String> commandAliases = new ArrayList<>(Arrays.asList("reps"));
    String category = "general";
    String exampleCommand = "reps (user id)/(user mention)";
    String shortCommandDescription = "Get amount of reps user has.";
    String fullCommandDescription = "See how many pointless internet points a user has";

    @Override
    public void command(CommandReceivedEvent event) {
        e = event;

        if (!e.hasArgs()) {
            user = e.getAuthor();
            userDB = e.getUserDB();
        } else {
            user = e.getFirstArgAsUser();

            if (user == null) {
                e.getChannel().sendMessage(getFullHelp("Give a valid user ID!", e.getPrefix())).queue();
                return;
            }

            userId = user.getId();
            userDB = databaseUser.getUserFromDBToUserDB(userId);
        }

        EmbedBuilder eb = getEmbed();

        if (userDB.getReps() == 0) {
            eb.setDescription(user.getName() + " has 0 reps.. Please rep him!");
        } else {
            eb.setDescription(user.getName() + " has a total of " + userDB.getReps() + ((userDB.getReps() == 1) ? " rep!" : " reps!"));
        }

        eb.setAuthor(user.getAsTag(), user.getEffectiveAvatarUrl(), user.getEffectiveAvatarUrl());

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
