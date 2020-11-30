package commands.util;

import commands.CommandReceivedEvent;
import commands.ICommand;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Message;
import util.Permissions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Clear implements ICommand {
    CommandReceivedEvent e;

    ArrayList<String> commandAliases = new ArrayList<>(Arrays.asList("clear"));
    String category = "util";
    String exampleCommand = "clear (number messages)";
    String shortCommandDescription = "Clear the chat for a specified amount of messages.";
    String fullCommandDescription = "Clear the chat for a specified amount of messages.";

    @Override
    public void command(CommandReceivedEvent event) {
        e = event;

        if (!e.isFromGuild()) {
            e.getChannel().sendMessage("This command only works in Discord servers/guild").queue();
            return;
        }

        if (!e.hasArgs()) {
            e.getChannel().sendMessage(getFullHelp("Requires at least 1 argument", e.getPrefix())).queue();
            return;
        }

        Permissions permissions = new Permissions(e.getGuild());

        if (permissions.userHasPermission(e.getAuthor(), Permission.MANAGE_CHANNEL)) {
            int messagesToRemove = Integer.parseInt(e.getArgs()[0]);

            List<Message> messages = e.getChannel().getHistory().retrievePast(messagesToRemove).complete();

            e.getTextChannel().deleteMessages(messages).queue();

            e.getChannel().sendMessage("Message removed successfully!").queue((message -> message.delete().queueAfter(5, TimeUnit.SECONDS)));
        } else {
            e.getChannel().sendMessage(getFullHelp("Requires permission MANAGE_CHANNEL", e.getPrefix())).queue();
        }
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

    @Override
    public ArrayList<String> getCommandAliases() {
        return commandAliases;
    }
}
