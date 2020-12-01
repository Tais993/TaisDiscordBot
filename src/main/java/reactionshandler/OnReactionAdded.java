package reactionshandler;

import database.reactionroles.DatabaseReactionRoles;
import database.reactions.DatabaseReactions;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import reactionshandler.amongushandler.ReactionAddedAmongUs;
import reactionshandler.reactionrolehandler.ReactionAddedRole;

public class OnReactionAdded extends ListenerAdapter {
    GuildMessageReactionAddEvent e;

    DatabaseReactions databaseReactions = new DatabaseReactions();
    DatabaseReactionRoles databaseReactionRoles = new DatabaseReactionRoles();

    @Override
    public void onGuildMessageReactionAdd(@NotNull GuildMessageReactionAddEvent event) {
        e = event;
        
        String messageID = event.getMessageId();

        if (e.getUser().isBot()) return;
        if (databaseReactions.reactionDBExistsInDB(messageID)) new ReactionAddedAmongUs(e);
        if (databaseReactionRoles.reactionRoleExistsInDb(e.getMessageId())) new ReactionAddedRole(e);
    }
}
