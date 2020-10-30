package commands.general;

import commands.CommandReceivedEvent;
import commands.ICommand;
import database.user.DatabaseUser;
import database.user.UserDB;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;

public class Reps implements ICommand {
    CommandReceivedEvent e;

    DatabaseUser databaseUser = new DatabaseUser();

    String userId;
    User user;

    UserDB userDB;

    String command = "reps";
    String commandAlias = "reps";
    String category = "general";
    String exampleCommand = "`!reps (user id)/(user mention)`";
    String shortCommandDescription = "Get amount of reps user has.";
    String fullCommandDescription = "See how many pointless internet points a user has";

    @Override
    public void command(CommandReceivedEvent event) {
        e = event;

        if (!e.hasArgs()) {
            user = e.getAuthor();
            userDB = e.getUserDB();
        } else {
            String[] args = e.getArgs();

            if (e.hasUserMentions()) {
                user = e.getFirstUserMentioned();
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

            userDB = databaseUser.getUserFromDBToUserDB(userId);
        }

        EmbedBuilder eb = getEmbed();

        if (userDB.getReps() == 0) {
            eb.setDescription(user.getName() + " has 0 reps.. Please rep him!");
        } else {
            eb.setDescription(user.getName() + " has a total of " + userDB.getReps() + ((userDB.getReps() == 1) ? " rep!" : " reps!"));

        }

        eb.setAuthor(user.getAsTag(), user.getEffectiveAvatarUrl(), user.getEffectiveAvatarUrl());

        e.getMessageChannel().sendMessage(eb.build()).queue();
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
