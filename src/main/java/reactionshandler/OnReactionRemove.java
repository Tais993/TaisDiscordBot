package reactionshandler;

import database.reactionroles.DatabaseReactionRoles;
import database.reactions.DatabaseReactions;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import reactionshandler.amongushandler.ReactionRemovedAmongUs;
import reactionshandler.reactionrolehandler.ReactionRemovedRole;

public class OnReactionRemove extends ListenerAdapter {
    GuildMessageReactionRemoveEvent e;

    DatabaseReactions databaseReactions = new DatabaseReactions();
    DatabaseReactionRoles databaseReactionRoles = new DatabaseReactionRoles();

    @Override
    public void onGuildMessageReactionRemove(@NotNull GuildMessageReactionRemoveEvent event) {
        e = event;

        String messageId = event.getMessageId();

        if (e.getUser().isBot()) return;
        if (databaseReactions.reactionDBExistsInDB(messageId)) new ReactionRemovedAmongUs(e);
        if (databaseReactionRoles.reactionRoleExistsInDb(messageId)) new ReactionRemovedRole(e);
    }
}
