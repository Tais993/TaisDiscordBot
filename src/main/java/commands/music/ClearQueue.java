package commands.music;

import commands.CommandReceivedEvent;
import commands.ICommand;
import music.PlayerManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static util.AllowedToPlayMusic.allowedToPlayMusic;

public class ClearQueue implements ICommand {
    CommandReceivedEvent e;

    ArrayList<String> commandAliases = new ArrayList<>(Collections.singletonList("clearqueue"));
    String category = "music";
    String exampleCommand = "clearqueue";
    String shortCommandDescription = "Queue gets cleared.";
    String fullCommandDescription = "Queue gets cleared";

    @Override
    public void command(CommandReceivedEvent event) {
        e = event;

        if (!allowedToPlayMusic(e, commandAliases.get(0))) {
            return;
        }

        PlayerManager manager = PlayerManager.getInstance();
        manager.clearQueue(e.getGuild());

        e.getMessageChannel().sendMessage("Queue has been cleared.").queue();
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
