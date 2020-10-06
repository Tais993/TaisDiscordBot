package functions;

import commands.CommandEnum;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.managers.AudioManager;

public class AllowedToPlayMusic {
    CommandEnum commandEnum = new CommandEnum();

    public boolean allowedToPlayMusic (GuildMessageReceivedEvent e, String[] args) {
        AudioManager audioManager = e.getGuild().getAudioManager();

        if (e.getAuthor().getId().equals("257500867568205824")) {
            if (!audioManager.isConnected()) audioManager.openAudioConnection(e.getMember().getVoiceState().getChannel());
            return true;
        }

        GuildVoiceState memberVoiceState = e.getMember().getVoiceState();

        if (!memberVoiceState.inVoiceChannel()){
            e.getChannel().sendMessage(commandEnum.getFullHelpItem(args[0]).setDescription("Error: Join a voice channel before running this command.").build()).queue();
            return false;
        }

        VoiceChannel voiceChannel = memberVoiceState.getChannel();
        Member selfMember = e.getGuild().getSelfMember();

        if (audioManager.isConnected()) {
            if (!audioManager.getConnectedChannel().equals(voiceChannel)) {
                e.getChannel().sendMessage(commandEnum.getFullHelpItem(args[0]).setDescription("Error: Join the same channel as the bot.").build()).queue();
                return false;
            }
        } else {
            audioManager.openAudioConnection(memberVoiceState.getChannel());
        }

        if (!selfMember.hasPermission(voiceChannel, Permission.VOICE_CONNECT)) {
            e.getChannel().sendMessage(commandEnum.getFullHelpItem(args[0]).setDescription("Error: Missing VOICE_CONNECT permission").build()).queue();
            return false;
        }

        audioManager.openAudioConnection(voiceChannel);
        return true;
    }
}
