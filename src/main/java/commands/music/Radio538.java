package commands.music;

import commands.CommandReceivedEvent;
import commands.ICommand;
import functions.AllowedToPlayMusic;
import music.PlayerManager;

public class Radio538 implements ICommand {
    CommandReceivedEvent e;

    String command = "radio538";
    String commandAlias = "radio538";
    String category = "music";
    String exampleCommand = "!radio538";
    String shortCommandDescription = "Listen to Radio 538 (Dutch)";
    String fullCommandDescription = "Listen to Radio 538 (Dutch)";

    @Override
    public void command(CommandReceivedEvent event) {
        e = event;

        AllowedToPlayMusic allowedToPlayMusic = new AllowedToPlayMusic();
        if (!allowedToPlayMusic.allowedToPlayMusic(e, "radio538")) {
            return;
        }

        PlayerManager manager = PlayerManager.getInstance();
        manager.loadAndPlay(e.getTextChannel(), "http://playerservices.streamtheworld.com/api/livestream-redirect/RADIO538.mp3", false ,e.getAuthor().getId(), e.getAuthor().getAsTag());
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
