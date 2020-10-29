package commands.music;

import commands.CommandReceivedEvent;
import commands.ICommand;
import music.PlayerManager;

import static functions.AllowedToPlayMusic.allowedToPlayMusic;

public class Resume implements ICommand {
    CommandReceivedEvent e;

    String command = "resume";
    String commandAlias = "resume";
    String category = "music";
    String exampleCommand = "`!resume`";
    String shortCommandDescription = "Start the music again";
    String fullCommandDescription = "Get the queue of the music.";

    @Override
    public void command(CommandReceivedEvent event) {
        e = event;

        if (!allowedToPlayMusic(e, "resume")) {
            return;
        }

        PlayerManager manager = PlayerManager.getInstance();

        manager.resume(e.getGuild());
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
