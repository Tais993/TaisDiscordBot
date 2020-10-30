package commands;

import database.user.DatabaseUser;
import database.user.UserDB;
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
    boolean hasUserMentions;

    String[] args;
    String messageAsString;
    String messageWithoutCommand = "";

    String prefix;
    String command;

    UserDB userDB;

    User firstUserMentioned;

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
            hasUserMentions = e.getMessage().getMentionedMembers().size() >= 1;

            if (hasUserMentions) {
                firstUserMentioned = e.getMessage().getMentionedMembers().get(0).getUser();
            }
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

        JDA = e.getJDA();

        DatabaseUser databaseUser = new DatabaseUser();
        userDB = databaseUser.getUserFromDBToUserDB(e.getAuthor().getId());
        isBotModerator = userDB.isBotModerator();
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

    public UserDB getUserDB() {
        return userDB;
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

    public boolean hasUserMentions() {
        return hasUserMentions;
    }

    public User getFirstUserMentioned() {
        return firstUserMentioned;
    }
}
