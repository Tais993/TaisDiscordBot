package commands.music;

import commands.CommandReceivedEvent;
import commands.ICommand;
import music.PlayerManager;

import static util.AllowedToPlayMusic.allowedToPlayMusic;

public class Replay implements ICommand {
    CommandReceivedEvent e;

    String command = "replay";
    String commandAlias = "replay";
    String category = "music";
    String exampleCommand = "!replay";
    String shortCommandDescription = "Replay the current playing track.";

    @Override
    public void command(CommandReceivedEvent event) {
        e = event;

        if (!allowedToPlayMusic(e, "replay")) {
            return;
        }

        PlayerManager manager = PlayerManager.getInstance();
        manager.replayTrack(e.getGuild());
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
