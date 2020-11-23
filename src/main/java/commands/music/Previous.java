package commands.music;

import commands.CommandReceivedEvent;
import commands.ICommand;
import music.PlayerManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static util.AllowedToPlayMusic.allowedToPlayMusic;

public class Previous implements ICommand {
    ArrayList<String> commandAliases = new ArrayList<>(Collections.singletonList("previous"));
    String category = "music";
    String exampleCommand = "previous ";
    String shortCommandDescription = "Return to the previous song.";
    String fullCommandDescription = "Return to the previous song.";

    @Override
    public void command(CommandReceivedEvent event) {
        if (!allowedToPlayMusic(event, commandAliases.get(0))) {
            return;
        }

        PlayerManager manager = PlayerManager.getInstance();
        manager.playPreviousTrack(event.getGuild());
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
