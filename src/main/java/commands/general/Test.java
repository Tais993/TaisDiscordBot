package commands.general;

import commands.CommandReceivedEvent;
import commands.ICommand;
import music.spotify.SearchSpotify;

public class Test implements ICommand {
    CommandReceivedEvent e;

    String command = "test";
    String commandAlias = "test";
    String category = "general";
    String exampleCommand = "`!test`";
    String shortCommandDescription = "For testing purposes.";
    String fullCommandDescription = "For testing purposes.";

    @Override
    public void command(CommandReceivedEvent event) {
        e = event;
        SearchSpotify searchSpotify = new SearchSpotify();
        e.getMessageChannel().sendMessage(searchSpotify.getSongUrl()).queue();
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
