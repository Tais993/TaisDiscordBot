package commands.bot;

import commands.CommandReceivedEvent;
import commands.ICommand;
import database.jokes.DatabaseJokes;

public class RemoveJoke implements ICommand {
    DatabaseJokes databaseJokes = new DatabaseJokes();

    CommandReceivedEvent e;
    String command = "removejoke";
    String commandAlias = "deletejoke";
    String category = "fun";
    String exampleCommand = "`!deletejoke `";
    String shortCommandDescription = "Add a funny joke";
    String fullCommandDescription = "Add a funny joke that actually, isn't funny at all.";

    @Override
    public void command(CommandReceivedEvent event) {
        e = event;

        if (!e.isBotModerator()) {
            e.getMessageChannel().sendMessage(getFullHelp("You've to be a bot moderator to run this command")).queue();
            return;
        }

        if (!e.hasArgs()) {
            e.getMessageChannel().sendMessage(getFullHelp("Requires at least 1 argument (the joke ID)")).queue();
            return;
        }

        String[] args = e.getArgs();
        databaseJokes.removeJokeById(Integer.parseInt(args[0]));
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
