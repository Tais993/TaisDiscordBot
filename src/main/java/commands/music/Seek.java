package commands.music;

import commands.CommandReceivedEvent;
import commands.ICommand;
import music.PlayerManager;

import java.util.ArrayList;
import java.util.Arrays;

import static util.AllowedToPlayMusic.allowedToPlayMusic;

public class Seek implements ICommand {
    CommandReceivedEvent e;

    ArrayList<String> commandAliases = new ArrayList<>(Arrays.asList("seek", "goto"));
    String category = "music";
    String exampleCommand = "seek (time in seconds)";
    String shortCommandDescription = "Set the current playing track to a specific time.";
    String fullCommandDescription = "Set the current playing track to a specific time.";

    @Override
    public void command(CommandReceivedEvent event) {
        e = event;

        if (!e.hasArgs()) {
            e.getMessageChannel().sendMessage(getFullHelp("Requires at least 1 argument", e.getPrefix())).queue();
            return;
        }

        if (!allowedToPlayMusic(e, commandAliases.get(0))) {
            return;
        }

        if (e.getArgs()[0].matches("\\d+")) {
            PlayerManager manager = PlayerManager.getInstance();
            manager.setSongPosition(e.getGuild(), Long.parseLong(e.getArgs()[0]));
        } else {
            e.getMessageChannel().sendMessage(getFullHelp("Give a valid number", e.getPrefix())).queue();
        }
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
