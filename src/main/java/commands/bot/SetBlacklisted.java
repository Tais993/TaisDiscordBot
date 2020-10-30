package commands.bot;

import commands.CommandReceivedEvent;
import commands.ICommand;
import database.user.DatabaseUser;

public class SetBlacklisted implements ICommand {
    DatabaseUser databaseUser = new DatabaseUser();
    CommandReceivedEvent e;

    String userId;

    String command = "setblacklisted";
    String commandAlias = "setblacklisted";
    String category = "bot";
    String exampleCommand = "`!setblacklisted <user id>/<user mention> <true/false>`";
    String shortCommandDescription = "Set someone blacklisted from the bot.";
    String fullCommandDescription = "Set someone blacklisted from the bot.\n" +
            "That user won't be able to run any commands then.";

    @Override
    public void command(CommandReceivedEvent event) {
        e = event;

        if (!e.isBotModerator()) {
            e.getMessageChannel().sendMessage("Requires to be a bot moderator!").queue();
            return;
        }

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
                    e.getMessageChannel().sendMessage(getFullHelp("That is not a valid user ID!")).queue();
                    return;
                }
            } else {
                e.getMessageChannel().sendMessage(getFullHelp("A user ID should only contain numbers!")).queue();
                return;
            }
        }

        databaseUser.setBlacklisted(userId, Boolean.parseBoolean(args[1]));
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
