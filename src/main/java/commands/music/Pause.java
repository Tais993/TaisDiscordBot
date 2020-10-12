package commands.music;

import commands.CommandReceivedEvent;
import commands.ICommand;
import functions.AllowedToPlayMusic;
import music.PlayerManager;

public class Pause implements ICommand {
    String command = "pause";
    String commandAlias = "pause";
    String category = "music";
    String exampleCommand = "`!pause`";
    String shortCommandDescription = "Pause the music.";
    String fullCommandDescription = "Pause the currently playing music.";

    @Override
    public void command(CommandReceivedEvent event, String[] args) {
        AllowedToPlayMusic allowedToPlayMusic = new AllowedToPlayMusic();
        if (!allowedToPlayMusic.allowedToPlayMusic(event, "pause")) {
            return;
        }

        PlayerManager manager = PlayerManager.getInstance();

        manager.pause(event.getGuild());
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
