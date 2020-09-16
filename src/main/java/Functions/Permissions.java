package Functions;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;

public class Permissions {
    Guild guild;

    public Permissions(Guild guildGiven) {
        guild = guildGiven;
    }

    public boolean botHasPermission(Permission permission) {
        return guild.getSelfMember().hasPermission(permission);
    }

    public boolean userHasPermission(User user, Permission permission) {
        return guild.getMember(user).hasPermission(permission);
    }

    public boolean botCanInteract(Member userToInteractWith) {
        return guild.getSelfMember().canInteract(userToInteractWith);
    }

    public boolean userCanInteract(User user, Member userToInteractWith) {
        return guild.getMember(user).canInteract(userToInteractWith);
    }
}
