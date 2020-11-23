package commands.music;

import commands.CommandReceivedEvent;
import commands.ICommand;
import music.PlayerManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static util.AllowedToPlayMusic.allowedToPlayMusic;

public class Resume implements ICommand {
    CommandReceivedEvent e;

    ArrayList<String> commandAliases = new ArrayList<>(Collections.singletonList("resume"));
    String category = "music";
    String exampleCommand = "resume";
    String shortCommandDescription = "Start the music again";
    String fullCommandDescription = "Get the queue of the music.";

    @Override
    public void command(CommandReceivedEvent event) {
        e = event;

        if (!allowedToPlayMusic(e, commandAliases.get(0))) {
            return;
        }

        PlayerManager manager = PlayerManager.getInstance();

        manager.resume(e.getGuild());
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
