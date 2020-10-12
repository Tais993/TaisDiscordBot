package commands.music;

import commands.CommandReceivedEvent;
import commands.ICommand;
import functions.AllowedToPlayMusic;
import music.PlayerManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class Remove implements ICommand {
    CommandReceivedEvent e;

    String command = "remove";
    String commandAlias = "remove";
    String category = "music";
    String exampleCommand = "`!remove <index>`";
    String shortCommandDescription = "Removes song from queue.";
    String fullCommandDescription = "Removes song from queue.";

    @Override
    public void command(CommandReceivedEvent event) {
        e = event;
        String[] args = e.getArgs();

        AllowedToPlayMusic allowedToPlayMusic = new AllowedToPlayMusic();
        if (!allowedToPlayMusic.allowedToPlayMusic(e, "remove")) {
            return;
        }

        PlayerManager manager = PlayerManager.getInstance();

        EmbedBuilder eb = manager.removeFromQueue(e.getGuild(), Integer.parseInt(args[1]));

        e.getMessageChannel().sendMessage(eb.build()).queue();
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
