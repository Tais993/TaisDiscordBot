package commands.music;

import commands.CommandReceivedEvent;
import commands.ICommand;

public class Lyrics implements ICommand {

    String command = "lyrics";
    String commandAlias = "lyrics";
    String category = "music";
    String exampleCommand = "`!lyrics (song)`";
    String shortCommandDescription = "Get the lyrics of a song.";
    String fullCommandDescription = "Get the lyrics of a song.";

    @Override
    public void command(CommandReceivedEvent event, String[] args) {
        event.getMessageChannel().sendMessage("Soon..").queue();
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
