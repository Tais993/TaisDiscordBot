package commands.music;

import commands.CommandReceivedEvent;
import commands.ICommand;
import music.PlayerManager;
import net.dv8tion.jda.api.EmbedBuilder;

public class NowPlaying implements ICommand {
    String command = "nowplaying";
    String commandAlias = "np";
    String category = "music";
    String exampleCommand = "!nowplaying";
    String shortCommandDescription = "Get the current playing song";
    String fullCommandDescription = "Get the current playing song";

    @Override
    public void command(CommandReceivedEvent event, String[] args) {
        PlayerManager manager = PlayerManager.getInstance();
        EmbedBuilder eb = manager.getNowPlaying(event.getGuild());
        event.getMessageChannel().sendMessage(eb.build()).queue();
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
