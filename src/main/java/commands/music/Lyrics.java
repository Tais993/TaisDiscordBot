package commands.music;

import commands.CommandReceivedEvent;
import commands.ICommand;

import java.util.ArrayList;
import java.util.Arrays;

public class Lyrics implements ICommand {

    ArrayList<String> commandAliases = new ArrayList<>(Arrays.asList("lyrics"));
    String category = "music";
    String exampleCommand = "lyrics (song)";
    String shortCommandDescription = "Get the lyrics of a song.";
    String fullCommandDescription = "Get the lyrics of a song.";

    @Override
    public void command(CommandReceivedEvent event) {
        event.getMessageChannel().sendMessage("Soon..").queue();
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
