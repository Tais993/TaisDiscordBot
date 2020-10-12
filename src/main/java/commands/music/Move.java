package commands.music;

import commands.CommandReceivedEvent;
import commands.ICommand;
import functions.AllowedToPlayMusic;
import music.PlayerManager;

public class Move implements ICommand {

    String command = "move";
    String commandAlias = "move";
    String category = "music";
    String exampleCommand = "`!move (index song to move) (index to move to)`";
    String shortCommandDescription = "Loop the current queue / current song.";
    String fullCommandDescription = "Loop the current queue / current song.\n" +
            "`!loop all` to loop the current queue. \n" +
            "`!loop 1` to loop the current playing song.";

    @Override
    public void command(CommandReceivedEvent event, String[] args) {
        AllowedToPlayMusic allowedToPlayMusic = new AllowedToPlayMusic();
        if (!allowedToPlayMusic.allowedToPlayMusic(event, "move")) {
            return;
        }

        PlayerManager manager = PlayerManager.getInstance();
        manager.moveTrackInQueue(event.getGuild(), Integer.parseInt(args[1]), Integer.parseInt(args[2]));
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
