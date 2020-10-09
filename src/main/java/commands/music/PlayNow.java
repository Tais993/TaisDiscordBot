package commands.music;

import commands.CommandEnum;
import commands.ICommand;
import database.guild.DatabaseGuild;
import functions.AllowedToPlayMusic;
import music.PlayerManager;
import music.youtube.Search;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.net.MalformedURLException;
import java.net.URL;

public class PlayNow implements ICommand {
    GuildMessageReceivedEvent e;
    CommandEnum commandEnum = new CommandEnum();
    DatabaseGuild databaseGuild = new DatabaseGuild();

    String url;

    String command = "playnow";
    String commandAlias = "playskip";
    String category = "music";
    String exampleCommand = "`!playnow (url/song title)`";
    String shortCommandDescription = "Instantly play a song.";
    String fullCommandDescription = "Instantly play a song, the song gets added to the top of the queue.\n" +
            "And the current playing song gets skipped.";


    @Override
    public void command(GuildMessageReceivedEvent event, String[] args) {
        e = event;

        if (!(args.length > 1)) {
            e.getChannel().sendMessage(commandEnum.getFullHelpItem("play").setDescription("Error: requires at least 1 argument").build()).queue();
            return;
        }

        AllowedToPlayMusic allowedToPlayMusic = new AllowedToPlayMusic();
        if (!allowedToPlayMusic.allowedToPlayMusic(e, "playnow")) {
            return;
        }

        String input = e.getMessage().getContentRaw().replace(databaseGuild.getPrefixGuildInDB(e.getGuild().getId()) + command + " ", "");

        if (!isUrl(input)) {
            if (!searchByName(input)) return;
        } else {
            url = input;
        }

        PlayerManager manager = PlayerManager.getInstance();

        manager.loadAndPlay(e.getChannel(), url, true, e.getAuthor().getId(), e.getAuthor().getAsTag());
        manager.skip(e.getGuild());
    }

    private boolean isUrl(String input) {
        try {
            new URL(input);
            return true;
        } catch (MalformedURLException malformedURLException) {
            return false;
        }
    }

    private boolean searchByName(String input) {
        Search search = new Search();

        String videoUrl = search.getVideoUrl(input);

        if (videoUrl.startsWith("Error:")) {
            e.getChannel().sendMessage(commandEnum.getFullHelpItem("play").setDescription(videoUrl).build()).queue();
            return false;
        }

        url = videoUrl;
        return true;
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
