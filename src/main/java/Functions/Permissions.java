package Functions;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;

public class Permissions {

    public boolean botHasPermission(Guild guild, Permission permission) {
        return guild.getSelfMember().hasPermission(permission);
    }

    public boolean userHasPermission(User user, Guild guild, Permission permission) {
        return guild.getMember(user).hasPermission(permission);
    }

    public boolean botCanInteract(Member userToInteractWith, Guild guild) {
        return guild.getSelfMember().canInteract(userToInteractWith);
    }

    public boolean userCanInteract(User user, Member userToInteractWith, Guild guild) {
        return guild.getMember(user).canInteract(userToInteractWith);
    }
}
