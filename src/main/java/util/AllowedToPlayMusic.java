package util;

import commands.CommandEnum;
import commands.CommandReceivedEvent;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.managers.AudioManager;

public class AllowedToPlayMusic {
    public static boolean allowedToPlayMusic (CommandReceivedEvent e, String commandName) {

        CommandEnum commandEnum = new CommandEnum();
        AudioManager audioManager;

        if (!e.isFromGuild()) {
            e.getMessageChannel().sendMessage("You can only run this command in a Discord server/guild!").queue();
            return false;
        }

        audioManager = e.getGuild().getAudioManager();

        if (e.isBotModerator()) {
            if (e.getMember().getVoiceState().inVoiceChannel()) {
                audioManager.openAudioConnection(e.getMember().getVoiceState().getChannel());
            }
            return true;
        }

        GuildVoiceState memberVoiceState = e.getMember().getVoiceState();

        if (!memberVoiceState.inVoiceChannel()){
            e.getMessageChannel().sendMessage(commandEnum.getFullHelpItem(commandName, e.getPrefix()).setDescription("Error: Join a voice channel before running this command.").build()).queue();
            return false;
        }

        VoiceChannel voiceChannel = memberVoiceState.getChannel();

        if (audioManager.isConnected() && audioManager.getConnectedChannel().getId().equals(voiceChannel.getId())) {
            e.getMessageChannel().sendMessage(commandEnum.getFullHelpItem(commandName, e.getPrefix()).setDescription("Error: Join the same channel as the bot.").build()).queue();
            return false;
        }

        Member selfMember = e.getGuild().getSelfMember();

        if (!selfMember.hasPermission(voiceChannel, Permission.VOICE_CONNECT)) {
            e.getMessageChannel().sendMessage(commandEnum.getFullHelpItem(commandName, e.getPrefix()).setDescription("Error: Missing VOICE_CONNECT permission").build()).queue();
            return false;
        }

        audioManager.openAudioConnection(voiceChannel);
        return true;
    }
}
