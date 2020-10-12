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

    String[] args;
    String messageAsString;
    String messageWithoutCommand;

    public CommandReceivedEvent(MessageReceivedEvent e) {
        isFromGuild = e.isFromGuild();
        messageChannel = e.getChannel();
        user = e.getAuthor();

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
                messageWithoutCommand += item;
                messageWithoutCommand += " ";
            }));
        } else {
            hasArgs = false;
            args[0] = "null";
            messageWithoutCommand = messageAsString;
        }

        JDA = e.getJDA();
        e.getChannel();
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
}
