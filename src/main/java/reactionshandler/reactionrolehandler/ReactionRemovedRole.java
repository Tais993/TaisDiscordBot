package reactionshandler.reactionrolehandler;

import database.reactionroles.DatabaseReactionRoles;
import database.reactionroles.ReactionRoleDB;
import database.reactionroles.RoleEmojiObject;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionRemoveEvent;

public class ReactionRemovedRole {
    GuildMessageReactionRemoveEvent e;

    Role role;

    DatabaseReactionRoles databaseReactionRoles = new DatabaseReactionRoles();

    public ReactionRemovedRole(GuildMessageReactionRemoveEvent event) {
        e = event;

        String messageID = event.getMessageId();

        ReactionRoleDB reactionRoleDB = databaseReactionRoles.getReactionRoleToReactionRoleDB(messageID);

        for (RoleEmojiObject roleEmojiObject : reactionRoleDB.getRoleEmojiObject()) {
            if (roleEmojiObject.getEmojiId().equals(e.getReactionEmote().getId())) {
                role = e.getGuild().getRoleById(roleEmojiObject.getRoleId());
                e.getGuild().removeRoleFromMember(e.getMember(), role).queue();
                return;
            }
        }
    }
}
