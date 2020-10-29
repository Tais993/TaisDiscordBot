package commands.music;

import commands.CommandReceivedEvent;
import commands.ICommand;
import music.PlayerManager;

import static functions.AllowedToPlayMusic.allowedToPlayMusic;

public class Previous implements ICommand {
    String command = "previous";
    String commandAlias = "previous";
    String category = "music";
    String exampleCommand = "`!previous`";
    String shortCommandDescription = "Return to the previous song.";
    String fullCommandDescription = "Return to the previous song.";

    @Override
    public void command(CommandReceivedEvent event) {

        if (!allowedToPlayMusic(event, "previous")) return;

        PlayerManager manager = PlayerManager.getInstance();
        manager.playPreviousTrack(event.getGuild());
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
