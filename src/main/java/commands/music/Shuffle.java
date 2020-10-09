package commands.music;

import commands.ICommand;
import functions.AllowedToPlayMusic;
import music.PlayerManager;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class Shuffle implements ICommand {
    GuildMessageReceivedEvent e;

    String command = "shuffle";
    String commandAlias = "shuffle";
    String category = "music";
    String exampleCommand = "`!shuffle`";
    String shortCommandDescription = "Shuffle the queue.";
    String fullCommandDescription = "Shuffle the queue.";

    @Override
    public void command(GuildMessageReceivedEvent event, String[] args) {
        e = event;

        AllowedToPlayMusic allowedToPlayMusic = new AllowedToPlayMusic();
        if (!allowedToPlayMusic.allowedToPlayMusic(e, "shuffle")) {
            return;
        }

        PlayerManager manager = PlayerManager.getInstance();
        manager.shuffleQueue(e.getGuild());

        e.getChannel().sendMessage("Queue has been shuffeled").queue();
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
