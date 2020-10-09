package commands.music;

import commands.CommandEnum;
import commands.ICommand;
import functions.AllowedToPlayMusic;
import music.PlayerManager;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.managers.AudioManager;

public class Resume implements ICommand {
    GuildMessageReceivedEvent e;

    String command = "resume";
    String commandAlias = "resume";
    String category = "music";
    String exampleCommand = "`!resume`";
    String shortCommandDescription = "Start the music again";
    String fullCommandDescription = "Get the queue of the music.";

    @Override
    public void command(GuildMessageReceivedEvent event, String[] args) {
        e = event;

        AllowedToPlayMusic allowedToPlayMusic = new AllowedToPlayMusic();
        if (!allowedToPlayMusic.allowedToPlayMusic(e, "resume")) {
            return;
        }

        PlayerManager manager = PlayerManager.getInstance();

        manager.resume(e.getGuild());
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
