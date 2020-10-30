package commands.general;

import commands.CommandReceivedEvent;
import commands.ICommand;
import database.user.DatabaseUser;
import net.dv8tion.jda.api.entities.User;

public class Rep implements ICommand {
    CommandReceivedEvent e;

    DatabaseUser databaseUser = new DatabaseUser();

    String userId;
    User user;

    String command = "rep";
    String commandAlias = "rep";
    String category = "general";
    String exampleCommand = "`!rep <user id>/<user mention>`";
    String shortCommandDescription = "Rep someone";
    String fullCommandDescription = "Give someone a internet point with no value at all.";

    @Override
    public void command(CommandReceivedEvent event) {
        e = event;

        if (!e.hasArgs()) {
            e.getMessageChannel().sendMessage(getFullHelp("Requires at least 1 argument")).queue();
            return;
        }

        String[] args = e.getArgs();

        if (e.hasUserMentions()) {
            userId = e.getFirstUserMentioned().getId();
        } else {
            if (args[0].matches("[0-9]+")) {
                user = e.getJDA().retrieveUserById(args[0]).complete();
                if (user == null) {
                    e.getMessageChannel().sendMessage(getFullHelp("That is not a valid user ID!")).queue();
                    return;
                }
                userId = args[0];

            } else {
                e.getMessageChannel().sendMessage(getFullHelp("A user ID should only contain numbers!")).queue();
                return;
            }
        }

        databaseUser.addRep(userId);

        e.getMessageChannel().sendMessage("A rep has been given to " + user.getName()).queue();
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
