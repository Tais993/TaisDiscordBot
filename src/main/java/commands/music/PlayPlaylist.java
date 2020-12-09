package commands.music;

import commands.CommandReceivedEvent;
import commands.ICommand;
import music.PlayerManager;

import java.util.ArrayList;
import java.util.Arrays;

import static utilities.AllowedToPlayMusic.allowedToPlayMusic;

public class PlayPlaylist implements ICommand {
    CommandReceivedEvent e;

    ArrayList<String> commandAliases = new ArrayList<>(Arrays.asList("playplaylist", "ppl"));
    String category = "music";
    String exampleCommand = "playplaylist <playlist name>";
    String shortCommandDescription = "Play a playlist you created.";
    String fullCommandDescription = "Play a playlist you created.";

    @Override
    public void command(CommandReceivedEvent event) {
        e = event;

        if (!allowedToPlayMusic(e, commandAliases.get(0))) {
            return;
        }

        if (!e.hasArgs() || e.getUserDB().getPlaylist(e.getArgs()[0]) == null) {
            e.getChannel().sendMessage(getShortHelp("Requires a valid playlist name!", e.getPrefix())).queue();
        }

        PlayerManager manager = PlayerManager.getInstance();
        e.getUserDB().getPlaylist(e.getArgs()[0]).forEach((song -> manager.loadAndPlay(e.getTextChannel(), song.getSongUrl(), false, e.getAuthor().getId(), e.getAuthor().getAsTag(), false)));
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
