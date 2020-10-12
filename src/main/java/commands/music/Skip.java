package commands.music;

import commands.CommandReceivedEvent;
import commands.ICommand;
import functions.AllowedToPlayMusic;
import music.PlayerManager;

public class Skip implements ICommand {
    CommandReceivedEvent e;

    String command = "skip";
    String commandAlias = "s";
    String category = "music";
    String exampleCommand = "`!skip`";
    String shortCommandDescription = "Skips the currently running song.";
    String fullCommandDescription = "Skips the currently running song.";

    @Override
    public void command(CommandReceivedEvent event) {
        e = event;

        AllowedToPlayMusic allowedToPlayMusic = new AllowedToPlayMusic();
        if (!allowedToPlayMusic.allowedToPlayMusic(e, "skip")) {
            return;
        }

        PlayerManager manager = PlayerManager.getInstance();

        manager.skip(e.getGuild());
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
