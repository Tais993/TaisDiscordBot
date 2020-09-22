package commands.util.ban;

import commands.ICommand;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class PermBan implements ICommand {
    GuildMessageReceivedEvent e;
    String command = "permban";
    String commandAlias = "permban";
    String category = "util";
    String exampleCommand = "`!8ball <question>?`";
    String shortCommandDescription = "Ask 8ball a question";
    String fullCommandDescription = "Ask 8ball a question, and get a totally real answer.\n" +
            "Question mark is required.";

    @Override
    public void command(GuildMessageReceivedEvent event, String[] args) {

    }

    @Override
    public String getCommand() {
        return null;
    }

    @Override
    public String getCommandAlias() {
        return null;
    }

    @Override
    public String getCategory() {
        return null;
    }

    @Override
    public String getExampleCommand() {
        return null;
    }

    @Override
    public String getShortCommandDescription() {
        return null;
    }

    @Override
    public String getFullCommandDescription() {
        return null;
    }
}
