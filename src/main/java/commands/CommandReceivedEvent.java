package commands;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.Arrays;

public class CommandReceivedEvent {
    MessageChannel messageChannel;
    TextChannel textChannel;
    User user;
    Member member = null;
    Guild guild = null;
    JDA JDA;

    Message message;

    boolean isFromGuild;
    boolean hasArgs;
    boolean isBotModerator;
    boolean mentionsEveryone;

    String[] args;
    String messageAsString;
    String messageWithoutCommand = "";

    String prefix;

    String command;

    public CommandReceivedEvent(MessageReceivedEvent e, String prefix) {

        isFromGuild = e.isFromGuild();
        messageChannel = e.getChannel();
        user = e.getAuthor();

        this.prefix = prefix;
        message = e.getMessage();

        messageAsString = e.getMessage().getContentRaw();

        if (isFromGuild) {
            member = e.getMember();
            guild = e.getGuild();
            textChannel = e.getTextChannel();
        }

        args = messageAsString.split(" ");

        if (args.length >= 2) {
            hasArgs = true;
            args = Arrays.stream(args).skip(1).toArray(String[]::new);

            Arrays.stream(args).forEach((item -> {
                if (!messageWithoutCommand.isEmpty()) {
                    messageWithoutCommand += " ";
                }
                messageWithoutCommand += item;
            }));
        } else {
            hasArgs = false;
            args[0] = "";
            messageWithoutCommand = messageAsString;
        }

        mentionsEveryone = message.mentionsEveryone();

        command = messageAsString.replace(prefix, "").split(" ")[0];

        isBotModerator = e.getAuthor().getId().equalsIgnoreCase("257500867568205824");

        JDA = e.getJDA();
    }

    public TextChannel getTextChannel() {
        return textChannel;
    }

    public MessageChannel getMessageChannel() {
        return messageChannel;
    }

    public User getAuthor() {
        return user;
    }

    public Member getMember() {
        return member;
    }

    public Guild getGuild() {
        return guild;
    }

    public JDA getJDA() {
        return JDA;
    }

    public String getMessageAsString() {
        return messageAsString;
    }

    public boolean isFromGuild() {
        return isFromGuild;
    }

    public boolean hasArgs() {
        return hasArgs;
    }

    public String[] getArgs() {
        return args;
    }

    public Message getMessage() {
        return message;
    }

    public String getMessageWithoutCommand() {
        return messageWithoutCommand;
    }

    public boolean isBotModerator() {
        return isBotModerator;
    }

    public String getPrefix() {
        return prefix;
    }

    public String getCommand() {
        return command;
    }

    public boolean mentionsEveryone() {
        return mentionsEveryone;
    }
}
