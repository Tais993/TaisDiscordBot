package commands.fun;

import commands.CommandReceivedEvent;
import commands.ICommand;
import database.jokes.DatabaseJokes;
import database.jokes.JokeDB;

public class AddJoke implements ICommand {
    DatabaseJokes databaseJokes = new DatabaseJokes();

    CommandReceivedEvent e;
    String command = "joke";
    String commandAlias = "joke";
    String category = "fun";
    String exampleCommand = "`!joke`";
    String shortCommandDescription = "Get a funny joke";
    String fullCommandDescription = "Get a funny joke that actually, isn't funny at all.";

    @Override
    public void command(CommandReceivedEvent event) {
        e = event;

        String fullMessage = event.getMessageWithoutCommand();
        String[] args = fullMessage.split(";");

        String setup = args[0];
        String punchline = args[1];

        JokeDB jokeDB = new JokeDB(setup, punchline);

        databaseJokes.addJoke(jokeDB);
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
