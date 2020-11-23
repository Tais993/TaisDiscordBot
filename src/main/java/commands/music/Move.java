package commands.music;

import commands.CommandReceivedEvent;
import commands.ICommand;
import music.PlayerManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static util.AllowedToPlayMusic.allowedToPlayMusic;

public class Move implements ICommand {
    CommandReceivedEvent e;

    ArrayList<String> commandAliases = new ArrayList<>(Collections.singletonList("move"));
    String category = "music";
    String exampleCommand = "move (index song to move) (index to move to)";
    String shortCommandDescription = "Loop the current queue / current song.";
    String fullCommandDescription = """
            Loop the current queue / current song.
            `!loop all` to loop the current queue.\s
            `!loop 1` to loop the current playing song.""";

    @Override
    public void command(CommandReceivedEvent event) {
        e = event;

        String[] args = e.getArgs();

        if (!e.hasArgs() || args.length <= 1) {
            e.getMessageChannel().sendMessage(getFullHelp("Requires at least 2 arguments", e.getPrefix())).queue();
        }

        if (!allowedToPlayMusic(e, commandAliases.get(0))) {
            return;
        }

        PlayerManager manager = PlayerManager.getInstance();
        manager.moveTrackInQueue(event.getGuild(), Integer.parseInt(e.getArgs()[0]), Integer.parseInt(e.getArgs()[1]));
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
