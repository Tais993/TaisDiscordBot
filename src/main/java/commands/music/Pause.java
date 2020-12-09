package commands.music;

import commands.CommandReceivedEvent;
import commands.ICommand;
import music.PlayerManager;

import java.util.ArrayList;
import java.util.Arrays;

import static utilities.AllowedToPlayMusic.allowedToPlayMusic;

public class Pause implements ICommand {
    ArrayList<String> commandAliases = new ArrayList<>(Arrays.asList("pause"));
    String category = "music";
    String exampleCommand = "pause";
    String shortCommandDescription = "Pause the music.";
    String fullCommandDescription = "Pause the currently playing music.";

    @Override
    public void command(CommandReceivedEvent event) {

        if (!allowedToPlayMusic(event, "pause")) return;

        PlayerManager manager = PlayerManager.getInstance();

        manager.pause(event.getGuild());
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
