package commands.music;

import commands.CommandReceivedEvent;
import commands.ICommand;
import music.PlayerManager;
import net.dv8tion.jda.api.managers.AudioManager;

import java.util.ArrayList;
import java.util.Arrays;

public class RickRoll implements ICommand {
    CommandReceivedEvent e;

    ArrayList<String> commandAliases = new ArrayList<>(Arrays.asList("rickroll", "dQw"));
    String category = "music";
    String exampleCommand = "rickroll";
    String shortCommandDescription = "Good one";
    String fullCommandDescription = "IMAGINE GETTING RICKROLLED LOLLL";

    @Override
    public void command(CommandReceivedEvent event) {
        e = event;

        if (!e.isFromGuild()) {
            e.getChannel().sendMessage(getFullHelp("You can only run this comand in a Discord server/guild!", e.getPrefix())).queue();
            return;
        }

        if (e.getCommand().equalsIgnoreCase("dqw")) {
            if (!e.getArgs()[0].equals("dQw")) {
                return;
            }
        }

        AudioManager audioManager = e.getGuild().getAudioManager();

        if (!audioManager.isConnected()) {
            Join join = new Join();
            join.joinChannel(e);
        }

        String url = "https://www.youtube.com/watch?v=dQw4w9WgXcQ";

        PlayerManager manager = PlayerManager.getInstance();

        manager.loadAndPlay(e.getTextChannel(), url, false, e.getAuthor().getId(), e.getAuthor().getAsTag(), true);

        manager.getGuildMusicManager(e.getGuild()).player.setVolume(100);
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
