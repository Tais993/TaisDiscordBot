package commands.music;

import commands.CommandReceivedEvent;
import commands.ICommand;
import music.PlayerManager;

import java.util.ArrayList;
import java.util.Arrays;

import static util.AllowedToPlayMusic.allowedToPlayMusic;

public class Forward implements ICommand {
    CommandReceivedEvent e;

    ArrayList<String> commandAliases = new ArrayList<>(Arrays.asList("forward", "forwards"));
    String category = "music";
    String exampleCommand = "forward (seconds)";
    String shortCommandDescription = "Forward the current playing song a amount of seconds.";
    String fullCommandDescription = "Forward the current playing song a amount of seconds.";

    @Override
    public void command(CommandReceivedEvent event) {
        e = event;

        if (!allowedToPlayMusic(e, commandAliases.get(0))) {
            return;
        }

        if (!e.hasArgs()) {
            e.getMessageChannel().sendMessage(getFullHelp("Requires at least 1 argument", e.getPrefix())).queue();
            return;
        }

        PlayerManager manager = PlayerManager.getInstance();
        manager.forwardsTrack(e.getGuild(), Long.parseLong(e.getArgs()[0]));
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
