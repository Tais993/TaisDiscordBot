package commands.music;

import commands.CommandEnum;
import commands.ICommand;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.managers.AudioManager;

public class Join implements ICommand {
    GuildMessageReceivedEvent e;
    CommandEnum commandEnum = new CommandEnum();

    String command = "join";
    String commandAlias = "join";
    String category = "general";
    String exampleCommand = "`!join`";
    String shortCommandDescription = "Joins the bot in a voice channel.";
    String fullCommandDescription = "Joins the bot in a voice channel.";

    @Override
    public void command(GuildMessageReceivedEvent event, String[] args) {
        e = event;

        AudioManager audioManager = e.getGuild().getAudioManager();

        if (audioManager.isConnected()) {
            e.getChannel().sendMessage(commandEnum.getFullHelpItem("join").setDescription("Error: Already connected to a channel.").build()).queue();
            return;
        }

        GuildVoiceState memberVoiceState = event.getMember().getVoiceState();

        if (!memberVoiceState.inVoiceChannel()){
            e.getChannel().sendMessage(commandEnum.getFullHelpItem("userinfo").setDescription("Error: Join a voice channel before running this command.").build()).queue();
            return;
        }

        VoiceChannel voiceChannel = memberVoiceState.getChannel();
        Member selfMember = event.getGuild().getSelfMember();

        if (!selfMember.hasPermission(voiceChannel, Permission.VOICE_CONNECT)) {
            e.getChannel().sendMessage(commandEnum.getFullHelpItem("userinfo").setDescription("Error: Missing VOICE_CONNECT permission").build()).queue();
            return;
        }

        audioManager.openAudioConnection(voiceChannel);
        e.getChannel().sendMessage("Joining your voice channel").queue();
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
