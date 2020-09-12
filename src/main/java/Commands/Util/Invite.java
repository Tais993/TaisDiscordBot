package Commands.Util;

import Commands.CommandEnum;
import Commands.ICommand;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class Invite implements ICommand {
    GuildMessageReceivedEvent e;
    String category = "util";
    String command = "invite";
    String exampleCommand = "`!invite <time>`";
    String shortCommandDescription = "Create server invites";
    String fullCommandDescription = "Create server invites for a specified time: 1m/h/d or perm for a permanent.";

    @Override
    public void command(GuildMessageReceivedEvent event, String[] args) {
        e = event;
        CommandEnum commandEnum = new CommandEnum();

        if (args.length > 1) {
            e.getChannel().sendMessage(e.getChannel().createInvite().setTemporary(true).setMaxAge(Integer.valueOf(args[1].replaceAll(" ", ""))).complete().getUrl() + "").queue();
        } else {
            e.getChannel().sendMessage(commandEnum.getFullHelpItem("invite").build()).queue();
        }
    }

    @Override
    public String getCommand() {
        return command;
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
