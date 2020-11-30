package commands.music;

import commands.CommandReceivedEvent;
import commands.ICommand;
import music.PlayerManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static util.AllowedToPlayMusic.allowedToPlayMusic;

public class Shuffle implements ICommand {
    CommandReceivedEvent e;

    ArrayList<String> commandAliases = new ArrayList<>(Collections.singletonList("shuffle"));
    String category = "music";
    String exampleCommand = "shuffle";
    String shortCommandDescription = "Shuffle the queue.";
    String fullCommandDescription = "Shuffle the queue.";

    @Override
    public void command(CommandReceivedEvent event) {
        e = event;

        if (!allowedToPlayMusic(e, commandAliases.get(0))) {
            return;
        }

        PlayerManager manager = PlayerManager.getInstance();
        manager.shuffleQueue(e.getGuild());

        e.getChannel().sendMessage("Queue has been shuffeled").queue();
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
