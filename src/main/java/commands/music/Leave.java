package commands.music;

import commands.CommandEnum;
import commands.CommandReceivedEvent;
import commands.ICommand;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.managers.AudioManager;

public class Leave implements ICommand {
    CommandReceivedEvent e;
    CommandEnum commandEnum = new CommandEnum();

    String command = "leave";
    String commandAlias = "disconnect";
    String category = "music";
    String exampleCommand = "`!leave`";
    String shortCommandDescription = "Leave the bot from a voice channel.";
    String fullCommandDescription = "Leave the bot from a voice channel.";

    @Override
    public void command(CommandReceivedEvent event, String[] args) {
        e = event;

        AudioManager audioManager = e.getGuild().getAudioManager();

        if (e.getAuthor().getId().equals("257500867568205824")) {
            audioManager.closeAudioConnection();
        }

        if (!audioManager.isConnected()) {
            e.getMessageChannel().sendMessage(commandEnum.getFullHelpItem("leave").setDescription("Error: I'm not connected to a voice channel.").build()).queue();
            return;
        }

        VoiceChannel voiceChannel = audioManager.getConnectedChannel();

        if (!voiceChannel.getMembers().contains(event.getMember())) {
            e.getMessageChannel().sendMessage(commandEnum.getFullHelpItem("leavel").setDescription("Error: You've to be in the same voice channel as the bot to use this command.").build()).queue();
            return;
        }

        audioManager.closeAudioConnection();
        e.getMessageChannel().sendMessage("Disconnected").queue();
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
