package functions;

import commands.CommandEnum;
import commands.CommandReceivedEvent;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.managers.AudioManager;

public class AllowedToPlayMusic {
    CommandEnum commandEnum = new CommandEnum();
    AudioManager audioManager;

    public boolean allowedToPlayMusic (CommandReceivedEvent e, String commandName) {

        if (!e.isFromGuild()) {
            e.getMessageChannel().sendMessage("You can only run this comand in a Discord server/guild!").queue();
            return false;
        }

        if (e.getAuthor().getId().equals("257500867568205824")) {
            if (e.getMember().getVoiceState().inVoiceChannel()) {
                if (!audioManager.isConnected()) audioManager.openAudioConnection(e.getMember().getVoiceState().getChannel());
            }
            return true;
        }

        audioManager = e.getGuild().getAudioManager();

        GuildVoiceState memberVoiceState = e.getMember().getVoiceState();

        if (!memberVoiceState.inVoiceChannel()){
            e.getMessageChannel().sendMessage(commandEnum.getFullHelpItem(commandName).setDescription("Error: Join a voice channel before running this command.").build()).queue();
            return false;
        }

        VoiceChannel voiceChannel = memberVoiceState.getChannel();
        Member selfMember = e.getGuild().getSelfMember();

        if (audioManager.isConnected()) {
            if (!audioManager.getConnectedChannel().equals(voiceChannel)) {
                e.getMessageChannel().sendMessage(commandEnum.getFullHelpItem(commandName).setDescription("Error: Join the same channel as the bot.").build()).queue();
                return false;
            }
        } else {
            audioManager.openAudioConnection(memberVoiceState.getChannel());
        }

        if (!selfMember.hasPermission(voiceChannel, Permission.VOICE_CONNECT)) {
            e.getMessageChannel().sendMessage(commandEnum.getFullHelpItem(commandName).setDescription("Error: Missing VOICE_CONNECT permission").build()).queue();
            return false;
        }

        audioManager.openAudioConnection(voiceChannel);
        return true;
    }
}
