package commands.music;

import commands.CommandReceivedEvent;
import commands.ICommand;
import functions.AllowedToPlayMusic;
import music.PlayerManager;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class ClearQueue implements ICommand {
    CommandReceivedEvent e;

    String command = "clearqueue";
    String commandAlias = "removeall";
    String category = "music";
    String exampleCommand = "`!clearqueue`";
    String shortCommandDescription = "Queue gets cleared.";
    String fullCommandDescription = "Queue gets cleared";

    @Override
    public void command(CommandReceivedEvent event) {
        e = event;
        String[] args = e.getArgs();

        AllowedToPlayMusic allowedToPlayMusic = new AllowedToPlayMusic();
        if (!allowedToPlayMusic.allowedToPlayMusic(e, "clearqueue")) {
            return;
        }

        PlayerManager manager = PlayerManager.getInstance();
        manager.clearQueueFromUser(e.getGuild(), args[1]);

        e.getMessageChannel().sendMessage("Queue has been cleared.").queue();
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
