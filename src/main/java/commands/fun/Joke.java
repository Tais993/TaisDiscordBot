package commands.fun;

import commands.CommandReceivedEvent;
import commands.ICommand;
import database.jokes.DatabaseJokes;
import database.jokes.JokeDB;
import net.dv8tion.jda.api.EmbedBuilder;

import java.util.ArrayList;
import java.util.Arrays;

public class Joke implements ICommand {
    DatabaseJokes databaseJokes = new DatabaseJokes();
    CommandReceivedEvent e;

    ArrayList<String> commandAliases = new ArrayList<>(Arrays.asList("joke"));
    String category = "fun";
    String exampleCommand = "joke";
    String shortCommandDescription = "Get a funny joke";
    String fullCommandDescription = "Get a funny joke that actually, isn't funny at all.";

    @Override
    public void command(CommandReceivedEvent event) {
        e = event;

        if (!databaseJokes.containsIdList()) {
            databaseJokes.createIdList();
        } else {
            databaseJokes.getIdList();
        }

        JokeDB jokeDB = databaseJokes.getRandomJoke();

        EmbedBuilder eb = getEmbed();

        eb.setDescription(jokeDB.getSetup() + "\n");
        eb.appendDescription("*" + jokeDB.getPunchline() + "*");
        eb.setFooter("Joke Id: " + jokeDB.getJokeId());

        e.getMessageChannel().sendMessage(eb.build()).queue();
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
