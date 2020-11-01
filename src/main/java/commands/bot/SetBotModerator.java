package commands.bot;

import commands.CommandReceivedEvent;
import commands.ICommand;
import database.user.DatabaseUser;

import java.util.ArrayList;
import java.util.Arrays;

public class SetBotModerator implements ICommand {
    DatabaseUser databaseUser = new DatabaseUser();
    CommandReceivedEvent e;

    String userId;

    ArrayList<String> commandAliases = new ArrayList<>(Arrays.asList("setbotmoderator", "setbotmoderator", "addbotmoderator"));
    String category = "botmoderation";
    String exampleCommand = "setbotmoderator <user id>/<user mention> <true/false>";
    String shortCommandDescription = "Make someone bot moderator or remove it from someone.";
    String fullCommandDescription = "Make someone bot moderator or remove it from someone.\n" +
            "Gives someone permission of a lot of more commands.";

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

        databaseUser.setBotModerator(userId, Boolean.parseBoolean(args[1]));
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
