package commands.bot;

import commands.CommandReceivedEvent;
import commands.ICommand;
import net.dv8tion.jda.api.entities.User;

public class Dm implements ICommand {
    CommandReceivedEvent e;

    User user;

    String command = "dm";
    String commandAlias = "directmessage";
    String category = "botmoderation";
    String exampleCommand = "`!dm <user id>/<user as mention> <text>`";
    String shortCommandDescription = "Send someone a DM.";
    String fullCommandDescription = "Send a user you want some text.\n" +
            "Abuse = bad.";

    @Override
    public void command(CommandReceivedEvent event) {
        e = event;

        user = e.getFirstArgAsUser();

        if (user == null) {
            e.getMessageChannel().sendMessage("Invalid user ID!").queue();
            return;
        }

        String messageToSay = e.getMessageWithoutCommand();

        messageToSay = messageToSay.replaceFirst(e.getArgs()[0], "");

        user.openPrivateChannel().complete().sendMessage(messageToSay).queue();

        e.getMessageChannel().sendMessage("Send: \"" + messageToSay + "\" to " + user.getAsTag()).queue();
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
