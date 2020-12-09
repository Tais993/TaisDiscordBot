package commands;

import database.guild.GuildDB;
import database.user.UserDB;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.Arrays;

public class CommandReceivedEvent {
    private final JDA JDA;

    private final MessageChannel messageChannel;
    private TextChannel textChannel;
    private final User user;
    private Member member;
    private Guild guild;

    private final Message message;

    boolean isFromGuild;
    boolean hasArgs;
    boolean isBotModerator;
    boolean mentionsEveryone;

    boolean hasUserMentions;
    boolean hasChannelMentions;
    boolean hasRoleMentions;

    private String[] args;
    private final String messageAsString;
    private String messageWithoutCommand = "";

    private String prefix;
    private String command;

    private UserDB userDB;
    private GuildDB guildDB = null;

    private User firstUserMentioned;
    private TextChannel firstChannelMentioned;
    private Role firstRoleMentioned;

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
            hasUserMentions = e.getMessage().getMentionedMembers().size() >= 1;
            hasRoleMentions = e.getMessage().getMentionedRoles().size() >= 1;

            if (hasUserMentions) {
                firstUserMentioned = e.getMessage().getMentionedMembers().get(0).getUser();
            }

            if (hasRoleMentions) {
                firstRoleMentioned = e.getMessage().getMentionedRoles().get(0);
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

        JDA = e.getJDA();
    }

    public TextChannel getTextChannel() {
        return textChannel;
    }

    public MessageChannel getChannel() {
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

    public void setDBItems(UserDB userDB, GuildDB guildDB) {
        if (guildDB != null) {
            this.guildDB = guildDB;
            prefix = guildDB.getPrefix();
        }
        this.userDB = userDB;
        isBotModerator = userDB.isBotModerator();

        if (!userDB.getPrefix().equals("")) {
            prefix = userDB.getPrefix();
        }

        command = messageAsString.replace(prefix, "").split(" ")[0];
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

    public Member getFirstArgAsMember() {
        if (hasUserMentions()) {
            return message.getMentionedMembers().get(0);
        } else {
            if (args[0].matches("[0-9]+")) {
                try {
                    Member memberInArg = getGuild().retrieveMemberById(args[0]).complete();
                    if (memberInArg != null) {
                        return memberInArg;
                    }
                } catch (NumberFormatException e) {
                    return null;
                }
            }
        }
        return null;
    }

    public User getFirstArgAsUser() {
        if (hasUserMentions()) {
            return message.getMentionedUsers().get(0);
        } else {
            if (args[0].matches("[0-9]+")) {
                try {
                    User userInArg = getJDA().retrieveUserById(args[0]).complete();
                    if (userInArg != null) {
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
