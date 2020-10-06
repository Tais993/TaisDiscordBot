package commands.music;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import commands.CommandEnum;
import commands.ICommand;
import functions.AllowedToPlayMusic;
import functions.Colors;
import music.PlayerManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import static music.TrackScheduler.videoDurationToYoutube;

public class SkipTo implements ICommand {
    GuildMessageReceivedEvent e;
    CommandEnum commandEnum = new CommandEnum();
    Colors colors = new Colors();

    String command = "skipto";
    String commandAlias = "skipto";
    String category = "music";
    String exampleCommand = "`!skipto (index song)`";
    String shortCommandDescription = "Skip to a certain position in the queue";
    String fullCommandDescription = "Skip to a certain position in the queue";

    @Override
    public void command(GuildMessageReceivedEvent event, String[] args) {
        e = event;

        AllowedToPlayMusic allowedToPlayMusic = new AllowedToPlayMusic();
        if (!allowedToPlayMusic.allowedToPlayMusic(e, args)) {
            return;
        }

        PlayerManager manager = PlayerManager.getInstance();
        manager.skipToTrack(e, Integer.parseInt(args[1]));

        AudioTrack track = manager.getGuildMusicManager(e.getGuild()).player.getPlayingTrack();

        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle("Queue has been skipped to:");
        eb.setColor(colors.getCurrentColor());
        eb.appendDescription("*Currently playing*\n");
        eb.appendDescription("**" + track.getInfo().author + "**\n");
        eb.appendDescription("[" + track.getInfo().title + "](" + track.getInfo().uri + ")\n");
        eb.appendDescription(videoDurationToYoutube(track.getPosition()) + " / " + videoDurationToYoutube(track.getDuration()) + "\n");

        e.getChannel().sendMessage(eb.build()).queue();

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
