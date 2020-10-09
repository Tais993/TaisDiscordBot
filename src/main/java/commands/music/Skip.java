package commands.music;

import commands.CommandEnum;
import commands.ICommand;
import functions.AllowedToPlayMusic;
import music.PlayerManager;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.managers.AudioManager;

public class Skip implements ICommand {
    GuildMessageReceivedEvent e;
    CommandEnum commandEnum = new CommandEnum();

    String command = "skip";
    String commandAlias = "s";
    String category = "music";
    String exampleCommand = "`!skip`";
    String shortCommandDescription = "Skips the currently running song.";
    String fullCommandDescription = "Skips the currently running song.";

    @Override
    public void command(GuildMessageReceivedEvent event, String[] args) {
        e = event;

        AllowedToPlayMusic allowedToPlayMusic = new AllowedToPlayMusic();
        if (!allowedToPlayMusic.allowedToPlayMusic(e, "skip")) {
            return;
        }

        PlayerManager manager = PlayerManager.getInstance();

        manager.skip(e.getGuild());
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
