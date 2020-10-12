package commands;

import database.guild.DatabaseGuild;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class CommandReceivedEvent {
    MessageChannel messageChannel;
    TextChannel textChannel;
    User user;
    Member member = null;
    Guild guild = null;
    JDA JDA;

    Message message;

    boolean isFromGuild;

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

            String guildPrefix = new DatabaseGuild().getPrefixGuildInDB(guild.getId());
            args = messageAsString.replace(guildPrefix, "").split(" ");
        } else {
            args = messageAsString.replace("!", "").split(" ");
        }

        messageWithoutCommand = messageAsString.replace(args[0], "");

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
