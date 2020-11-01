package commands.bot;

import commands.CommandReceivedEvent;
import commands.ICommand;
import net.dv8tion.jda.api.entities.TextChannel;

public class Say implements ICommand {
    CommandReceivedEvent e;

    TextChannel channel;

    String command = "say";
    String commandAlias = "say";
    String category = "botmoderation";
    String exampleCommand = "`!say <channel id>/<channel as mention> <text>`";
    String shortCommandDescription = "Say some text.";
    String fullCommandDescription = "Say some text in a channel you want.\n" +
            "Abuse = bad.";

    @Override
    public void command(CommandReceivedEvent event) {
        e = event;

        if (e.hasChannelMentions()) {
            channel = e.getFirstChannelMentioned();
        } else {
            if (e.getArgs()[0].matches("[0-9]+")) {
                TextChannel channelInArgs = e.getGuild().getTextChannelById(e.getArgs()[0]);
                if (channelInArgs != null) {
                    channel = channelInArgs;
                }
            }
        }

        String messageToSay = e.getMessageWithoutCommand();

        messageToSay = messageToSay.replaceFirst(e.getArgs()[0], "");

        channel.sendMessage(messageToSay).queue();
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
