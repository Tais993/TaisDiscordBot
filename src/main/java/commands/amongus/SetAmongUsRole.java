package commands.amongus;

import commands.CommandReceivedEvent;
import commands.ICommand;
import database.guild.DatabaseGuild;
import database.guild.GuildDB;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Role;

import java.util.ArrayList;
import java.util.Arrays;

public class SetAmongUsRole implements ICommand {
    DatabaseGuild databaseGuild = new DatabaseGuild();
    CommandReceivedEvent e;

    Role role;

    ArrayList<String> commandAliases = new ArrayList<>(Arrays.asList("setamongusrole"));
    String category = "util";
    String exampleCommand = "setamongusrole <role as mention>/<role ID>";
    String shortCommandDescription = "Sets the among us role ID to the correct role.";
    String fullCommandDescription = "Sets the among us role ID to the correct role.\n" +
            "When using the startgame command it will ping the correct role.";

    @Override
    public void command(CommandReceivedEvent event) {
        e = event;

        if (!e.getMember().getPermissions().contains(Permission.MANAGE_ROLES)) {
            e.getMessageChannel().sendMessage(getFullHelp("Requires manage roles permission!", e.getPrefix())).queue();
            return;
        }

        if (!e.hasArgs()) {
            e.getMessageChannel().sendMessage(getFullHelp("Requires at least 1 argument!", e.getPrefix())).queue();
            return;
        }

        if (e.hasRoleMentions()) {
            role = e.getFirstRoleMentioned();
        } else {
            if (e.getArgs()[0].matches("[0-9]+")) {
                role = e.getGuild().getRoleById(e.getArgs()[0]);
                if (role == null)  {
                    e.getMessageChannel().sendMessage(getFullHelp("Give a valid role ID!", e.getPrefix())).queue();
                    return;
                }
            }
        }

        GuildDB guildDB = e.getGuildDB();

        guildDB.setAmongUsRoleId(role.getId());

        databaseGuild.updateGuildInDB(guildDB);

        e.getMessageChannel().sendMessage("Role has succesfully been changed to " + role.getName() + " (" + role.getId() + ")").queue();
    }

    @Override
    public ArrayList<String> getCommandAliases() {
        return commandAliases;
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
}
