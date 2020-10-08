package commands.music;

import commands.CommandEnum;
import commands.ICommand;
import functions.AllowedToPlayMusic;
import music.PlayerManager;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class SetVolume implements ICommand {
    GuildMessageReceivedEvent e;
    CommandEnum commandEnum = new CommandEnum();

    String command = "setvolume";
    String commandAlias = "volume";
    String category = "music";
    String exampleCommand = "`!setvolume (volume)`";
    String shortCommandDescription = "Set the volume of the bot. Ranged between 1 and 200.";
    String fullCommandDescription = "Set the volume of the bot. Ranged between 1 and 200.";

    @Override
    public void command(GuildMessageReceivedEvent event, String[] args) {
        e = event;

        PlayerManager manager = PlayerManager.getInstance();

        if (!(args.length > 1)) {
            e.getChannel().sendMessage("Volume has been set to " + manager.getVolume(e.getGuild())).queue();
            return;
        }

        int volume;

        try {
            volume = Integer.parseInt(args[1]);
        } catch (NumberFormatException exception) {
            e.getChannel().sendMessage(commandEnum.getFullHelpItem("setvolume").setDescription("Error: give a valid number.").build()).queue();
            return;
        }

        if (!(volume >= 1 && volume <= 200)) {
            e.getChannel().sendMessage(commandEnum.getFullHelpItem("setvolume").setDescription("Error: give a number between 1 and 200.").build()).queue();
            return;
        }

        AllowedToPlayMusic allowedToPlayMusic = new AllowedToPlayMusic();
        if (!allowedToPlayMusic.allowedToPlayMusic(e, args)) {
            return;
        }

        if (manager.setVolume(e, volume)) {
            e.getChannel().sendMessage("Volume has been set correctly to " + volume).queue();
        } else {
            e.getChannel().sendMessage("Error unknown.").queue();
        }
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
