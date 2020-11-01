package commands.bot;

import commands.CommandReceivedEvent;
import commands.ICommand;
import database.user.DatabaseUser;
import database.user.UserDB;

import java.util.ArrayList;
import java.util.Arrays;

public class SetBlacklisted implements ICommand {
    DatabaseUser databaseUser = new DatabaseUser();
    CommandReceivedEvent e;

    String userId;

    ArrayList<String> commandAliases = new ArrayList<>(Arrays.asList("setblacklisted", "setblacklist"));
    String category = "botmoderation";
    String exampleCommand = "setblacklisted <user id>/<user mention> <true/false>";
    String shortCommandDescription = "Set someone blacklisted from the bot.";
    String fullCommandDescription = "Set someone blacklisted from the bot.\n" +
            "That user won't be able to run any commands then.";

    @Override
    public void command(CommandReceivedEvent event) {
        e = event;

        if (!e.hasArgs()) {
            e.getMessageChannel().sendMessage("Requires at least 2 arguments!").queue();
            return;
        }

        String[] args = e.getArgs();

        if (e.hasUserMentions()) {
            userId = e.getFirstUserMentioned().getId();
        } else {
            if (args[0].matches("[0-9]+")) {
                e.getJDA().retrieveUserById(args[0]).queue(user -> userId = args[0]);
                if (userId.isEmpty()) {
                    e.getMessageChannel().sendMessage(getFullHelp("That is not a valid user ID!", e.getPrefix())).queue();
                    return;
                }
            } else {
                e.getMessageChannel().sendMessage(getFullHelp("A user ID should only contain numbers!", e.getPrefix())).queue();
                return;
            }
        }

        UserDB userDB = databaseUser.getUserFromDBToUserDB(userId);

        if (userDB.isBotModerator()) {
            e.getMessageChannel().sendMessage(getFullHelp("This command doesn't work on bot moderators!", e.getPrefix())).queue();
            return;
        }

        databaseUser.setBlacklisted(userId, Boolean.parseBoolean(args[1]));
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
