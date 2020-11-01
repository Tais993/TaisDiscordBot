package commands.util;

import commands.CommandReceivedEvent;
import commands.ICommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Role;

import java.time.Instant;

import static util.Time.getDateFromOffset;

public class RoleInfo implements ICommand {
    CommandReceivedEvent e;

    Role role;

    String command = "roleinfo";
    String commandAlias = "roleinfo";
    String category = "util";
    String exampleCommand = "!roleinfo <@role>/<role id>";
    String shortCommandDescription = "Get information about a role.";
    String fullCommandDescription = "Get information about a role.";

    @Override
    public void command(CommandReceivedEvent event) {
        e = event;

        if (!e.isFromGuild()) {
            e.getMessageChannel().sendMessage("This command only works in Discord servers/guild").queue();
            return;
        }

        if (e.hasArgs()) {
            role = e.getFirstArgAsRole();
        }

        sendEmbed();
    }

    public void sendEmbed() {
        EmbedBuilder eb = new EmbedBuilder();

        eb.setTitle(role.getName());
        eb.setDescription(role.getAsMention() + "\n");
        eb.addField("Time created:", getDateFromOffset(role.getTimeCreated()), true);
        eb.addField("Position in list:", String.valueOf(role.getPosition()), true);
        eb.addField("Is mentionable", String.valueOf(role.isMentionable()), true);
        eb.setFooter("Role id: " + role.getId());
        eb.setColor(role.getColor());
        eb.setTimestamp(Instant.now());

        role.getPermissions().forEach((permission -> {
            if (!(permission.isText() || permission.isVoice())) {
                eb.appendDescription(permission.getName() + "\n");
            }
        }));

        e.getMessageChannel().sendMessage(eb.build()).queue();
    }

    @Override
    public String getCommand() {
        return command;
    }

    @Override
    public String getCommandAlias() {
        return commandAlias;
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
