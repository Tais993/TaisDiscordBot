package commands.general;

import commands.CommandReceivedEvent;
import commands.ICommand;
import music.spotify.SearchSpotify;

import java.util.ArrayList;
import java.util.Arrays;

public class Test implements ICommand {
    CommandReceivedEvent e;

    ArrayList<String> commandAliases = new ArrayList<>(Arrays.asList("test"));
    String category = "general";
    String exampleCommand = "test";
    String shortCommandDescription = "For testing purposes.";
    String fullCommandDescription = "For testing purposes.";

    @Override
    public void command(CommandReceivedEvent event) {
        e = event;
        SearchSpotify searchSpotify = new SearchSpotify();
        e.getMessageChannel().sendMessage(searchSpotify.getSongUrl()).queue();
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
