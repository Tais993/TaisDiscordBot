package commands.music;

import commands.CommandEnum;
import commands.ICommand;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class Lyrics implements ICommand {
    GuildMessageReceivedEvent e;

    String command = "lyrics";
    String commandAlias = "lyrics";
    String category = "music";
    String exampleCommand = "`!lyrics (song)`";
    String shortCommandDescription = "Get the lyrics of a song.";
    String fullCommandDescription = "Get the lyrics of a song.";

    @Override
    public void command(GuildMessageReceivedEvent event, String[] args) {
        e = event;

        e.getChannel().sendMessage("Soon..").queue();
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
