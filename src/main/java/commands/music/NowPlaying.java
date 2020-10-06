package commands.music;

import commands.ICommand;
import music.PlayerManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class NowPlaying implements ICommand {
    GuildMessageReceivedEvent e;

    String command = "nowplaying";
    String commandAlias = "np";
    String category = "music";
    String exampleCommand = "!nowplaying";
    String shortCommandDescription = "Get the current playing song";
    String fullCommandDescription = "Get the current playing song";

    @Override
    public void command(GuildMessageReceivedEvent event, String[] args) {
        e = event;

        PlayerManager manager = PlayerManager.getInstance();
        EmbedBuilder eb = manager.getNowPlaying(e);
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
