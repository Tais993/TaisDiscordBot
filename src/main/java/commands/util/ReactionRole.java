package commands.util;

import commands.CommandReceivedEvent;
import commands.ICommand;
import database.reactionroles.DatabaseReactionRoles;
import database.reactionroles.ReactionRoleDB;
import database.reactionroles.RoleEmojiObject;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Emote;
import net.dv8tion.jda.api.entities.Role;
import utilities.Permissions;

import java.util.ArrayList;
import java.util.Arrays;

import static utilities.Colors.getCurrentColor;

public class ReactionRole implements ICommand {
    CommandReceivedEvent e;

    DatabaseReactionRoles databaseReactionRoles = new DatabaseReactionRoles();

    RoleEmojiObject[] roleEmojiObject;

    Role role;

    String messageId;
    String textChannelId;
    String roleId;
    String emojiId;

    int rolesToBeAdded;

    ArrayList<String> commandAliases = new ArrayList<>(Arrays.asList("reactionrole"));
    String category = "utilities";
    String exampleCommand = "reactionrole message <messageId> <emoji> <roleId>";
    String shortCommandDescription = "Set reaction roles on a message, or let the bot create a basic message.";
    String fullCommandDescription = "Set reaction roles on a message, or let the bot create a basic message.\n" +
            "`!reactionrole bot` to let the bot create a message, if you want multiple roles do it like:\n" +
            "`!reactionrole bot <emoji> <roleId> <emoji> <roleId>` the maximum is 5 roles\n" +
            "`!reactionrole message <messageId>` to assign the reaction roles to a already sent message.";

    @Override
    public void command(CommandReceivedEvent event) {
        e = event;
        String[] args = e.getArgs();

        if (!e.isFromGuild()) {
            e.getChannel().sendMessage("This command only works in Discord servers/guild").queue();
            return;
        }

        Permissions permissions = new Permissions(e.getGuild());

        if (!permissions.userHasPermission(e.getAuthor(), Permission.MANAGE_ROLES)) {
            e.getChannel().sendMessage(getFullHelp("Error: requires manage roles permission.", e.getPrefix())).queue();
        }

        switch (args[1]) {
            case "bot" -> {
                rolesToBeAdded = (args.length - 2) / 2;
                if (!(rolesToBeAdded == e.getMessage().getEmotes().size())) return;
                botCreateMessage(args);
            }
            case "message" -> {
                rolesToBeAdded = (args.length - 3) / 2;
                if (!(rolesToBeAdded == e.getMessage().getEmotes().size())) return;
                useUserMessage(args);
            }
        }
    }

    public void botCreateMessage(String[] args) {
        roleEmojiObject = new RoleEmojiObject[e.getMessage().getEmotes().size()];

        EmbedBuilder eb = new EmbedBuilder();
        textChannelId = e.getChannel().getId();

        for (int i = 0; i < rolesToBeAdded; i++) {
            roleId = args[((i + 1) * 2) + 1];
            emojiId = e.getMessage().getEmotes().get(i).getId();

            if (e.getGuild().getEmoteById(emojiId) == null) {
                e.getChannel().sendMessage(getFullHelp("Error: emoji not available in this guild", e.getPrefix())).queue();
                return;
            }
            Emote emote = e.getGuild().getEmoteById(emojiId);
            role = e.getGuild().getRoleById(roleId);

            eb.addField(role.getName(), emote.getAsMention(), false);
            roleEmojiObject[i] = new RoleEmojiObject(roleId, emojiId);
        }

        eb.setColor(getCurrentColor());
        eb.setTitle("Roles: ");

        e.getChannel().sendMessage(eb.build()).queue(m -> {
            messageId = m.getId();
            ReactionRoleDB reactionRoleDB = new ReactionRoleDB(messageId, textChannelId, roleEmojiObject);
            databaseReactionRoles.addReactionRoleToDB(reactionRoleDB);
            for (RoleEmojiObject emojiObject : roleEmojiObject) {
                Emote emote = e.getGuild().getEmoteById(emojiObject.getEmojiId());
                m.addReaction(emote).queue();
            }
        });
    }

    public void useUserMessage(String[] args) {
        roleEmojiObject = new RoleEmojiObject[e.getMessage().getEmotes().size()];

        textChannelId = e.getChannel().getId();

        for (int i = 0; i < rolesToBeAdded; i++) {
            roleId = args[((i + 1) * 2) + 2];
            emojiId = e.getMessage().getEmotes().get(i).getId();

            if (e.getGuild().getEmoteById(emojiId) == null) {
                e.getChannel().sendMessage(getFullHelp("Error: emoji not available in this guild", e.getPrefix())).queue();
                return;
            }

            role = e.getGuild().getRoleById(roleId);
            roleEmojiObject[i] = new RoleEmojiObject(roleId, emojiId);
        }

        messageId = args[2];
        ReactionRoleDB reactionRoleDB = new ReactionRoleDB(messageId, textChannelId, roleEmojiObject);
        databaseReactionRoles.addReactionRoleToDB(reactionRoleDB);

        e.getChannel().retrieveMessageById(messageId).queue((message -> {
            for (RoleEmojiObject emojiObject : roleEmojiObject) {
                Emote emote = e.getGuild().getEmoteById(emojiObject.getEmojiId());
                message.addReaction(emote).queue();
            }
        }));
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
