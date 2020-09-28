package reactionshandler.reactionrolehandler;

import database.reactionroles.DatabaseReactionRoles;
import database.reactionroles.ReactionRoleDB;
import database.reactionroles.RoleEmojiObject;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;

public class ReactionAddedRole {
    GuildMessageReactionAddEvent e;

    Role role;

    DatabaseReactionRoles databaseReactionRoles = new DatabaseReactionRoles();

    public ReactionAddedRole(GuildMessageReactionAddEvent event) {
        e = event;

        String messageID = event.getMessageId();

        ReactionRoleDB reactionRoleDB = databaseReactionRoles.getReactionRoleToReactionRoleDB(messageID);

        for (RoleEmojiObject roleEmojiObject : reactionRoleDB.getRoleEmojiObject()) {
            if (roleEmojiObject.getEmojiId().equals(e.getReactionEmote().getId())) {
                role = e.getGuild().getRoleById(roleEmojiObject.getRoleId());
                e.getGuild().addRoleToMember(e.getMember(), role).queue();
                return;
            }
        }
    }
}
