package commands.music;

import commands.CommandReceivedEvent;
import commands.ICommand;
import music.PlayerManager;
import net.dv8tion.jda.api.EmbedBuilder;

import java.util.ArrayList;
import java.util.Arrays;

import static util.AllowedToPlayMusic.allowedToPlayMusic;

public class PlayPlaylist implements ICommand {
    CommandReceivedEvent e;

    ArrayList<String> commandAliases = new ArrayList<>(Arrays.asList("playplaylist", "ppl"));
    String category = "music";
    String exampleCommand = "playplaylist <@user>/<userID>";
    String shortCommandDescription = "Play a playlist you created.";
    String fullCommandDescription = "Play a playlist you created.";

    @Override
    public void command(CommandReceivedEvent event) {
        e = event;

        if (!allowedToPlayMusic(e, "playplaylist")) return;

        if (!e.hasArgs()) {
            EmbedBuilder eb = getEmbed();
            eb.setTitle(e.getAuthor().getAsTag() + "'s playlists");

            e.getUserDB().getPlaylists().forEach((key, value) -> eb.appendDescription(key + " - *total: " + value.size() + " songs*\n"));

            e.getMessageChannel().sendMessage(eb.build()).queue();
        }

        PlayerManager manager = PlayerManager.getInstance();
        manager.loadAndPlayUserPlaylist (e.getTextChannel(), e.getAuthor().getAsTag(), e.getAuthor().getId(), e.getUserDB().getPlaylist(e.getArgs()[0]));
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
