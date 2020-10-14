package commands.util;

import commands.CommandReceivedEvent;
import commands.ICommand;
import functions.Permissions;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Message;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class Clear implements ICommand {
    CommandReceivedEvent e;

    String command = "clear";
    String commandAlias = "clear";
    String category = "util";
    String exampleCommand = "`!clear (number messages)`";
    String shortCommandDescription = "Clear the chat for a specified amount of messages.";
    String fullCommandDescription = "Clear the chat for a specified amount of messages.";

    @Override
    public void command(CommandReceivedEvent event) {
        e = event;
        String[] args = e.getArgs();

        if (!e.isFromGuild()) {
            e.getMessageChannel().sendMessage("This command only works in Discord servers/guild").queue();
            return;
        }

        Permissions permissions = new Permissions(e.getGuild());

        if (permissions.userHasPermission(e.getAuthor(), Permission.MANAGE_CHANNEL)) {
            int messagesToRemove = Integer.parseInt(args[0]);

            List<Message> messages = e.getMessageChannel().getHistory().retrievePast(messagesToRemove).complete();

            e.getTextChannel().deleteMessages(messages).queue();

            e.getMessageChannel().sendMessage("Message removed successfully!").queue((message -> message.delete().queueAfter(5, TimeUnit.SECONDS)));
        } else {
            e.getMessageChannel().sendMessage(getFullHelp("Requires permission MANAGE_CHANNEL")).queue();
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
