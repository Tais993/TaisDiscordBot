package commands.music;

import commands.CommandReceivedEvent;
import commands.ICommand;
import music.PlayerManager;
import net.dv8tion.jda.api.managers.AudioManager;

import java.util.ArrayList;
import java.util.Arrays;

import static utilities.AllowedToPlayMusic.allowedToPlayMusic;

public class ClearUser implements ICommand {
    CommandReceivedEvent e;

    boolean isAllowed;

    ArrayList<String> commandAliases = new ArrayList<>(Arrays.asList("clearuser"));
    String category = "music";
    String exampleCommand = "clearuser (userId/userTag)";
    String shortCommandDescription = "Queue gets cleared from a user's songs.";
    String fullCommandDescription = "Queue gets cleared from a user's songs.";

    @Override
    public void command(CommandReceivedEvent event) {
        e = event;

        if (!allowedToPlayMusic(e, "clearuser")) return;

        if (!e.hasArgs()) {
            e.getChannel().sendMessage(getFullHelp("Requires at least 1 argument", e.getPrefix())).queue();
            return;
        }

        if (!e.getAuthor().getId().equalsIgnoreCase(e.getArgs()[0])) {
            AudioManager audioManager = e.getGuild().getAudioManager();

            audioManager.getConnectedChannel().getMembers().forEach((member -> {
                if (member.getId().equals(e.getArgs()[0])) isAllowed = true;
            }));
        }

        if (isAllowed) {
            e.getChannel().sendMessage(getFullHelp("Member is still in channel!", e.getPrefix())).queue();
            return;
        }

        PlayerManager manager = PlayerManager.getInstance();
        manager.clearQueueFromUser(e.getGuild(), e.getArgs()[0]);

        e.getChannel().sendMessage("Queue has been cleared from user").queue();
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

    @Override
    public ArrayList<String> getCommandAliases() {
        return commandAliases;
    }
}
