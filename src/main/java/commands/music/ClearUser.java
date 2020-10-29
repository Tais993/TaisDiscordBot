package commands.music;

import commands.CommandReceivedEvent;
import commands.ICommand;
import music.PlayerManager;
import net.dv8tion.jda.api.managers.AudioManager;

import static functions.AllowedToPlayMusic.allowedToPlayMusic;

public class ClearUser implements ICommand {
    CommandReceivedEvent e;

    boolean isAllowed;

    String command = "clearuser";
    String commandAlias = "clearuser";
    String category = "music";
    String exampleCommand = "`!clearuser (userId/userTag)`";
    String shortCommandDescription = "Queue gets cleared from a user's songs.";
    String fullCommandDescription = "Queue gets cleared from a user's songs.";

    @Override
    public void command(CommandReceivedEvent event) {
        e = event;

        if (!allowedToPlayMusic(e, "clearuser")) return;

        if (!e.hasArgs()) {
            e.getMessageChannel().sendMessage(getFullHelp("Requires at least 1 argument")).queue();
            return;
        }

        if (!e.getAuthor().getId().equalsIgnoreCase(e.getArgs()[0])) {
            AudioManager audioManager = e.getGuild().getAudioManager();

            audioManager.getConnectedChannel().getMembers().forEach((member -> {
                if (member.getId().equals(e.getArgs()[0])) isAllowed = true;
            }));
        }

        if (isAllowed) {
            e.getMessageChannel().sendMessage(getFullHelp("Member is still in channel!")).queue();
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
