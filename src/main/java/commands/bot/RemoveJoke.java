package commands.bot;

import commands.CommandReceivedEvent;
import commands.ICommand;
import database.jokes.DatabaseJokes;
import database.jokes.JokeDB;
import net.dv8tion.jda.api.EmbedBuilder;

import java.util.ArrayList;
import java.util.Arrays;

public class RemoveJoke implements ICommand {
    DatabaseJokes databaseJokes = new DatabaseJokes();

    CommandReceivedEvent e;
    ArrayList<String> commandAliases = new ArrayList<>(Arrays.asList("removejoke", "deletejoke"));
    String category = "botmoderation";
    String exampleCommand = "deletejoke";
    String shortCommandDescription = "Remove a funny joke";
    String fullCommandDescription = "Remove a funny joke that actually, isn't funny at all.";

    @Override
    public void command(CommandReceivedEvent event) {
        e = event;

        if (!e.hasArgs()) {
            e.getMessageChannel().sendMessage(getFullHelp("Requires at least 1 argument (the joke ID)", e.getPrefix())).queue();
            return;
        }

        String[] args = e.getArgs();

        if (!args[0].matches("[0-9]+")) {
            e.getMessageChannel().sendMessage("Give a valid ID!").queue();
            return;
        }

        JokeDB jokeDB = databaseJokes.getJokeAsJokeDB(args[0]);
        databaseJokes.removeJokeById(args[0]);

        if (args.length >= 2 && Boolean.parseBoolean(args[1])) {
            EmbedBuilder eb = getEmbed();

            eb.setDescription("We will miss you..");
            eb.appendDescription(jokeDB.getSetup() + "\n");
            eb.appendDescription("*" + jokeDB.getPunchline() + "*");
            eb.setFooter("Joke Id: " + jokeDB.getJokeId());

            e.getMessageChannel().sendMessage(eb.build()).queue();

        } else {
            e.getMessageChannel().sendMessage(jokeDB.getJokeId() +  " has been removed.").queue();
        }
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
