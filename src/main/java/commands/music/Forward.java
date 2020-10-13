package commands.music;

import commands.CommandEnum;
import commands.CommandReceivedEvent;
import commands.ICommand;
import functions.AllowedToPlayMusic;
import music.PlayerManager;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class Forward implements ICommand {
    CommandReceivedEvent e;

    CommandEnum commandEnum = new CommandEnum();

    String command = "forward";
    String commandAlias = "forwards";
    String category = "music";
    String exampleCommand = "`!forward (seconds)`";
    String shortCommandDescription = "Forward the current playing song a amount of seconds.";
    String fullCommandDescription = "Forward the current playing song a amount of seconds.";

    @Override
    public void command(CommandReceivedEvent event) {
        e = event;

        AllowedToPlayMusic allowedToPlayMusic = new AllowedToPlayMusic();
        if (!allowedToPlayMusic.allowedToPlayMusic(e, "forward")) {
            return;
        }

        if (!e.hasArgs()) {
            e.getMessageChannel().sendMessage(getFullHelp("Requires at least 1 argument")).queue();
        }

        PlayerManager manager = PlayerManager.getInstance();
        manager.forwardsTrack(e.getGuild(), Long.parseLong(e.getArgs()[0]));
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
