package commands.music;

import commands.CommandEnum;
import commands.ICommand;
import functions.AllowedToPlayMusic;
import music.PlayerManager;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class Move implements ICommand {
    GuildMessageReceivedEvent e;
    CommandEnum commandEnum = new CommandEnum();

    String command = "move";
    String commandAlias = "move";
    String category = "music";
    String exampleCommand = "`!move (index song to move) (index to move to)`";
    String shortCommandDescription = "Loop the current queue / current song.";
    String fullCommandDescription = "Loop the current queue / current song.\n" +
            "`!loop all` to loop the current queue. \n" +
            "`!loop 1` to loop the current playing song.";

    @Override
    public void command(GuildMessageReceivedEvent event, String[] args) {
        e = event;

        AllowedToPlayMusic allowedToPlayMusic = new AllowedToPlayMusic();
        if (!allowedToPlayMusic.allowedToPlayMusic(e, "move")) {
            return;
        }

        PlayerManager manager = PlayerManager.getInstance();
        manager.moveTrackInQueue(e.getGuild(), Integer.parseInt(args[1]), Integer.parseInt(args[2]));
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
