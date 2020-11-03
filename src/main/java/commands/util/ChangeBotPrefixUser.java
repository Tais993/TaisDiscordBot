package commands.util;

import commands.CommandReceivedEvent;
import commands.ICommand;
import database.user.DatabaseUser;
import database.user.UserDB;

import java.util.ArrayList;
import java.util.Arrays;

public class ChangeBotPrefixUser implements ICommand {
    CommandReceivedEvent e;
    DatabaseUser databaseUser = new DatabaseUser();
    UserDB userDB;

    ArrayList<String> commandAliases = new ArrayList<>(Arrays.asList("changebotprefixuser", "setbotprefixuser", "changebotprefixuser", "botprefixuser"));
    String category = "util";
    String exampleCommand = "changebotprefixuser <botprefix>";
    String shortCommandDescription = "Set the prefix of the bot for you.";
    String fullCommandDescription = "Set the prefix of the bot for you.";

    @Override
    public void command(CommandReceivedEvent event) {
        e = event;

        if (!e.hasArgs()) {
            e.getMessageChannel().sendMessage(getFullHelp("Requires at least 1 argument", e.getPrefix())).queue();
            return;
        }

        userDB = e.getUserDB();

        String prefix = e.getMessageWithoutCommand();

        userDB.setPrefix(prefix);

        databaseUser.updateUserInDB(userDB);

        e.getMessageChannel().sendMessage("Personal prefix changed to: " + prefix).queue();
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
