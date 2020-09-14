package Commands.Util;

import Commands.ICommand;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class Ban implements ICommand {
    String command = "ban";
    String commandAlias = "ban";
    String category = "util";
    String exampleCommand = "!ban";
    String shortCommandDescription = "Has no functions yet cause I'm lazy";
    String fullCommandDescription = "Has no functions yet cause I'm lazy";

    @Override
    public void command(GuildMessageReceivedEvent event, String[] args) {
        event.getChannel().sendMessage("user has been banned MOEHAHAHAH").queue();
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
