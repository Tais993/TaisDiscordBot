package commands.music;

import commands.CommandReceivedEvent;
import commands.ICommand;
import music.PlayerManager;

import java.util.ArrayList;
import java.util.Arrays;

import static utilities.AllowedToPlayMusic.allowedToPlayMusic;

public class Radio538 implements ICommand {
    CommandReceivedEvent e;

    ArrayList<String> commandAliases = new ArrayList<>(Arrays.asList("radio538", "radiovijfdrieacht"));
    String category = "music";
    String exampleCommand = "radio538";
    String shortCommandDescription = "Listen to Radio 538 (Dutch)";
    String fullCommandDescription = "Listen to Radio 538 (Dutch)";

    @Override
    public void command(CommandReceivedEvent event) {
        e = event;

        if (!allowedToPlayMusic(e, commandAliases.get(0))) {
            return;
        }

        PlayerManager manager = PlayerManager.getInstance();
        manager.loadAndPlayRadio(e.getTextChannel(), "http://playerservices.streamtheworld.com/api/livestream-redirect/RADIO538.mp3", e.getAuthor().getId(), e.getAuthor().getAsTag(), "Radio538");
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
