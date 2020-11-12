package commands.bot;

import commands.CommandReceivedEvent;
import commands.ICommand;
import database.user.DatabaseUser;
import net.dv8tion.jda.api.entities.User;

import java.util.ArrayList;
import java.util.Arrays;

public class SetBotModerator implements ICommand {
    DatabaseUser databaseUser = new DatabaseUser();
    CommandReceivedEvent e;

    User user;
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

        user = e.getFirstArgAsUser();

        if (user == null) {
            e.getMessageChannel().sendMessage(getShortHelp("Requires a valid user ID!", e.getPrefix())).queue();
            return;
        }

        userId = user.getId();

        databaseUser.setBotModerator(userId, Boolean.parseBoolean(e.getArgs()[1]));
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
