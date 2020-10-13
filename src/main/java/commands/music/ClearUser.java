package commands.music;

import commands.CommandReceivedEvent;
import commands.ICommand;
import functions.AllowedToPlayMusic;
import music.PlayerManager;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class ClearUser implements ICommand {
    CommandReceivedEvent e;

    String command = "clearuser";
    String commandAlias = "clearuser";
    String category = "music";
    String exampleCommand = "`!clearuser (userId/userTag)`";
    String shortCommandDescription = "Queue gets cleared from a user's songs.";
    String fullCommandDescription = "Queue gets cleared from a user's songs.";

    @Override
    public void command(CommandReceivedEvent event) {
        e = event;

        AllowedToPlayMusic allowedToPlayMusic = new AllowedToPlayMusic();
        if (!allowedToPlayMusic.allowedToPlayMusic(e, "clearuser")) {
            return;
        }

        if (!e.hasArgs()) {
            e.getMessageChannel().sendMessage(getFullHelp("Requires at least 1 argument")).queue();
            return;
        }

        PlayerManager manager = PlayerManager.getInstance();
        manager.clearQueueFromUser(e.getGuild(), e.getArgs()[0]);

        e.getMessageChannel().sendMessage("Queue has been cleared from user").queue();
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
