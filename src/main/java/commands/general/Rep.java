package commands.general;

import commands.CommandReceivedEvent;
import commands.ICommand;
import database.user.DatabaseUser;
import database.user.UserDB;
import net.dv8tion.jda.api.entities.User;

import java.util.ArrayList;
import java.util.Arrays;

public class Rep implements ICommand {
    CommandReceivedEvent e;

    DatabaseUser databaseUser = new DatabaseUser();

    String userId;
    User user;

    ArrayList<String> commandAliases = new ArrayList<>(Arrays.asList("rep"));
    String category = "general";
    String exampleCommand = "rep <user id>/<user mention>";
    String shortCommandDescription = "Rep someone";
    String fullCommandDescription = "Give someone a internet point with no value at all.";

    @Override
    public void command(CommandReceivedEvent event) {
        e = event;

        UserDB userdB = e.getUserDB();

        if ((userdB.getLastTimeRepGiven() - System.currentTimeMillis()) <= 3600000) {
            if (userdB.getLastTimeRepGiven() != 0) {
                e.getChannel().sendMessage(getFullHelp("Already given a rep in the last hour!", e.getPrefix())).queue();
                return;
            }
        }

        if (!e.hasArgs()) {
            e.getChannel().sendMessage(getFullHelp("Requires at least 1 argument", e.getPrefix())).queue();
            return;
        }

        user = e.getFirstArgAsUser();

        if (user == null) {
            e.getChannel().sendMessage(getFullHelp("Give a valid user ID!", e.getPrefix())).queue();
            return;
        }

        userId = user.getId();

        if (userId.equals(e.getAuthor().getId())) {
            e.getChannel().sendMessage(getFullHelp("You can't give a rep to yourself!", e.getPrefix())).queue();
            return;
        }

        UserDB userDB = e.getUserDB();
        userDB.setLastTimeRepGiven();

        databaseUser.addRep(userId);
        databaseUser.updateUserInDB(userDB);

        e.getChannel().sendMessage("A rep has been given to " + user.getName()).queue();
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
