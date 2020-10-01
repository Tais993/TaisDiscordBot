package commands.util;

import commands.CommandEnum;
import commands.ICommand;
import functions.Permissions;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class Clear implements ICommand {
    GuildMessageReceivedEvent e;
    CommandEnum commandEnum = new CommandEnum();

    String command = "clear";
    String commandAlias = "clear";
    String category = "util";
    String exampleCommand = "`!clear (number messages)`";
    String shortCommandDescription = "Clear the chat for a specified amount of messages.";
    String fullCommandDescription = "Clear the chat for a specified amount of messages.";

    @Override
    public void command(GuildMessageReceivedEvent event, String[] args) {
        e = event;

        Permissions permissions = new Permissions(e.getGuild());

        if (permissions.userHasPermission(e.getAuthor(), Permission.MANAGE_CHANNEL)) {
            int messagesToRemove = Integer.parseInt(args[1]);

            List<Message> messages = e.getChannel().getHistory().retrievePast(messagesToRemove).complete();

            e.getChannel().deleteMessages(messages).queue();

            e.getChannel().sendMessage("Message removed successfully!").queue((message -> message.delete().queueAfter(5, TimeUnit.SECONDS)));
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
