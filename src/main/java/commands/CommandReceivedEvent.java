package commands;

import database.guild.GuildDB;
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
    boolean hasChannelMentions;
    boolean hasRoleMentions;

    String[] args;
    String messageAsString;
    String messageWithoutCommand = "";

    String prefix;
    String command;

    UserDB userDB;
    GuildDB guildDB = null;

    User firstUserMentioned;
    TextChannel firstChannelMentioned;
    Role firstRoleMentioned;

    public CommandReceivedEvent(MessageReceivedEvent e, GuildDB guildDB) {
        isFromGuild = e.isFromGuild();
        messageChannel = e.getChannel();
        user = e.getAuthor();

        message = e.getMessage();

        messageAsString = e.getMessage().getContentRaw();

        if (isFromGuild) {
            member = e.getMember();
            guild = e.getGuild();
            textChannel = e.getTextChannel();
            hasUserMentions = e.getMessage().getMentionedMembers().size() >= 1;
            hasRoleMentions = e.getMessage().getMentionedRoles().size() >= 1;

            if (hasUserMentions) {
                firstUserMentioned = e.getMessage().getMentionedMembers().get(0).getUser();
            }

            if (hasRoleMentions) {
                firstRoleMentioned = e.getMessage().getMentionedRoles().get(0);
            }

            this.guildDB = guildDB;
            prefix = guildDB.getPrefix();
        } else {
            prefix = "!";
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

        JDA = e.getJDA();

        DatabaseUser databaseUser = new DatabaseUser();
        userDB = databaseUser.getUserFromDBToUserDB(e.getAuthor().getId());
        isBotModerator = userDB.isBotModerator();

        if (!userDB.getPrefix().equals("")) {
            prefix = userDB.getPrefix();
        }

        command = messageAsString.replace(prefix, "").split(" ")[0];
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

    public String getAsTag() {
        return user.getAsTag();
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

    public GuildDB getGuildDB() {
        return guildDB;
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

    public User getFirstArgAsUser() {
        if (hasUserMentions()) {
            return getFirstUserMentioned();
        } else {
            if (args[0].matches("[0-9]+")) {
                try {
                    User userInArg = getJDA().retrieveUserById(args[0]).complete();
                    if (!(userInArg == null)) {
                        return userInArg;
                    }
                } catch (NumberFormatException e) {
                    return null;
                }
            }
        }
        return null;
    }

    public Role getFirstArgAsRole() {
        if (hasRoleMentions()) {
            return getFirstRoleMentioned();
        } else {
            if (args[0].matches("[0-9]+")) {
                Role roleInArg = getGuild().getRoleById(args[0]);
                if (!(roleInArg == null)) {
                    return roleInArg;
                }
            }
        }
        return null;
    }

    public boolean hasChannelMentions() {
        return hasChannelMentions;
    }

    public TextChannel getFirstChannelMentioned() {
        return firstChannelMentioned;
    }

    public boolean hasRoleMentions() {
        return hasRoleMentions;
    }

    public Role getFirstRoleMentioned() {
        return firstRoleMentioned;
    }
}
