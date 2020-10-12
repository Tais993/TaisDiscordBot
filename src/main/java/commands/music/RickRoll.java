package commands.music;

import commands.CommandEnum;
import commands.CommandReceivedEvent;
import commands.ICommand;
import music.PlayerManager;
import net.dv8tion.jda.api.managers.AudioManager;

public class RickRoll implements ICommand {
    CommandReceivedEvent e;
    CommandEnum commandEnum = new CommandEnum();

    String command = "rickroll";
    String commandAlias = "dQw";
    String category = "music";
    String exampleCommand = "`!rickroll`";
    String shortCommandDescription = "Good one";
    String fullCommandDescription = "IMAGINE GETTING RICKROLLED LOLLL";

    @Override
    public void command(CommandReceivedEvent event) {
        e = event;

        if (!e.isFromGuild()) {
            e.getMessageChannel().sendMessage(commandEnum.getFullHelpItem("rickroll").setDescription("You can only run this comand in a Discord server/guild!").build()).queue();
            return;
        }

        if (e.getArgs()[0].equalsIgnoreCase("dqw")) {
            if (!e.getArgs()[0].equals("dQw")) {
                return;
            }
        }

        AudioManager audioManager = e.getGuild().getAudioManager();

        if (!audioManager.isConnected()) {
            Join join = new Join();
            join.joinChannel(e);
        }

        String url = "https://www.youtube.com/watch?v=dQw4w9WgXcQ";

        PlayerManager manager = PlayerManager.getInstance();

        manager.loadAndPlay(e.getTextChannel(), url, false, e.getAuthor().getId(), e.getAuthor().getAsTag());

        manager.getGuildMusicManager(e.getGuild()).player.setVolume(100);
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
