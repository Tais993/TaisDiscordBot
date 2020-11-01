package commands.music;

import commands.CommandReceivedEvent;
import commands.ICommand;
import music.PlayerManager;
import net.dv8tion.jda.api.EmbedBuilder;

import java.util.ArrayList;
import java.util.Arrays;

public class NowPlaying implements ICommand {
    ArrayList<String> commandAliases = new ArrayList<>(Arrays.asList("nowplaying", "np"));
    String category = "music";
    String exampleCommand = "nowplaying";
    String shortCommandDescription = "Get the current playing song";
    String fullCommandDescription = "Get the current playing song";

    @Override
    public void command(CommandReceivedEvent event) {
        PlayerManager manager = PlayerManager.getInstance();
        EmbedBuilder eb = manager.getNowPlaying(event.getGuild(), getEmbed());
        event.getMessageChannel().sendMessage(eb.build()).queue();
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
