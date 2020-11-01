package commands.music;

import commands.CommandReceivedEvent;
import commands.ICommand;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.managers.AudioManager;

import java.util.ArrayList;
import java.util.Arrays;

public class Join implements ICommand {
    CommandReceivedEvent e;
    AudioManager audioManager;

    ArrayList<String> commandAliases = new ArrayList<>(Arrays.asList("join"));
    String category = "music";
    String exampleCommand = "join";
    String shortCommandDescription = "Joins the bot in a voice channel.";
    String fullCommandDescription = "Joins the bot in a voice channel.";

    @Override
    public void command(CommandReceivedEvent event) {
        e = event;

        audioManager = e.getGuild().getAudioManager();

        if (audioManager.isConnected()) {
            e.getMessageChannel().sendMessage(getFullHelp("Error: already connected to a channel", e.getPrefix())).queue();
            return;
        }

        if (joinChannel(e)) {
            e.getMessageChannel().sendMessage("Joining your voice channel").queue();
        }
    }


    public boolean joinChannel(CommandReceivedEvent e) {

        audioManager = e.getGuild().getAudioManager();

        GuildVoiceState memberVoiceState = e.getMember().getVoiceState();

        if (!memberVoiceState.inVoiceChannel()){
            e.getMessageChannel().sendMessage(getFullHelp("Error: Join a voice channel before running this command.", e.getPrefix())).queue();
            return false;
        }

        VoiceChannel voiceChannel = memberVoiceState.getChannel();
        Member selfMember = e.getGuild().getSelfMember();

        if (!selfMember.hasPermission(voiceChannel, Permission.VOICE_CONNECT)) {
            e.getMessageChannel().sendMessage(getFullHelp("Error: Missing VOICE_CONNECT permission", e.getPrefix())).queue();
            return false;
        }

        audioManager.openAudioConnection(voiceChannel);
        return true;
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
