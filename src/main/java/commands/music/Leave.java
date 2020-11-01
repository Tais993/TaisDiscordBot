package commands.music;

import commands.CommandReceivedEvent;
import commands.ICommand;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.managers.AudioManager;

import java.util.ArrayList;
import java.util.Arrays;

public class Leave implements ICommand {
    CommandReceivedEvent e;

    ArrayList<String> commandAliases = new ArrayList<>(Arrays.asList("leave", "disconnect"));
    String category = "music";
    String exampleCommand = "leave";
    String shortCommandDescription = "Leave the bot from a voice channel.";
    String fullCommandDescription = "Leave the bot from a voice channel.";

    @Override
    public void command(CommandReceivedEvent event) {
        e = event;

        AudioManager audioManager = e.getGuild().getAudioManager();

        if (e.isBotModerator()) {
            audioManager.closeAudioConnection();
        }

        if (!audioManager.isConnected()) {
            e.getMessageChannel().sendMessage(getFullHelp("Error: I'm not connected to a voice channel.", e.getPrefix())).queue();
            return;
        }

        VoiceChannel voiceChannel = audioManager.getConnectedChannel();

        if (!voiceChannel.getMembers().contains(event.getMember())) {
            e.getMessageChannel().sendMessage(getFullHelp("Error: You've to be in the same voice channel as the bot to use this command.", e.getPrefix())).queue();
            return;
        }

        audioManager.closeAudioConnection();
        e.getMessageChannel().sendMessage("Disconnected").queue();
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
