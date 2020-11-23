package commands.bot;

import commands.CommandReceivedEvent;
import commands.ICommand;
import database.user.DatabaseUser;
import database.user.UserDB;
import net.dv8tion.jda.api.entities.User;

import java.util.ArrayList;
import java.util.Arrays;

public class SetBlacklisted implements ICommand {
    DatabaseUser databaseUser = new DatabaseUser();
    CommandReceivedEvent e;

    User user;
    String userId;

    ArrayList<String> commandAliases = new ArrayList<>(Arrays.asList("setblacklisted", "setblacklist", "addblacklisted"));
    String category = "botmoderation";
    String exampleCommand = "setblacklisted <user id>/<user mention> <true/false>";
    String shortCommandDescription = "Set someone blacklisted from the bot.";
    String fullCommandDescription = "Set someone blacklisted from the bot.\n" +
            "That user won't be able to run any commands then.";

    @Override
    public void command(CommandReceivedEvent event) {
        e = event;

        if (!e.hasArgs() || e.getArgs().length < 2) {
            e.getMessageChannel().sendMessage("Requires at least 2 arguments!").queue();
            return;
        }

        user = e.getFirstArgAsUser();

        if (user == null) {
            e.getMessageChannel().sendMessage(getShortHelp("Requires a valid user ID!", e.getPrefix())).queue();
            return;
        }

        userId = user.getId();

        UserDB userDB = databaseUser.getUserFromDBToUserDB(userId);

        if (userDB.isBotModerator()) {
            e.getMessageChannel().sendMessage(getFullHelp("This command doesn't work on bot moderators!", e.getPrefix())).queue();
            return;
        }

        databaseUser.setBlacklisted(userId, Boolean.parseBoolean(e.getArgs()[1]));
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
