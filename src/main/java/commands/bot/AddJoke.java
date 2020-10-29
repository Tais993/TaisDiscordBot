package commands.bot;

import commands.CommandReceivedEvent;
import commands.ICommand;
import database.jokes.DatabaseJokes;
import database.jokes.JokeDB;

public class AddJoke implements ICommand {
    DatabaseJokes databaseJokes = new DatabaseJokes();

    CommandReceivedEvent e;
    String command = "addjoke";
    String commandAlias = "addjoke";
    String category = "fun";
    String exampleCommand = "`!addjoke`";
    String shortCommandDescription = "Add a funny joke";
    String fullCommandDescription = "Add a funny joke that actually, isn't funny at all.";

    @Override
    public void command(CommandReceivedEvent event) {
        e = event;

        if (e.mentionsEveryone()) {
            e.getMessageChannel().sendMessage("Don't mention everyone! Not nice >.<").queue();
            return;
        }

        if (!e.hasArgs()) {
            e.getMessageChannel().sendMessage(getFullHelp("Requires 2 arguments")).queue();
            return;
        }

        String fullMessage = event.getMessageWithoutCommand();

        if (!fullMessage.contains(";")) {
            e.getMessageChannel().sendMessage(getFullHelp("Split the setup and punchline with a `;`")).queue();
            return;
        }

        String[] args = fullMessage.split(";");

        if (args[1].charAt(0) == ' ') args[1] = args[1].replaceFirst(" ", "");

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
