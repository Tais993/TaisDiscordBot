package commands.util;

import commands.CommandReceivedEvent;
import commands.ICommand;
import functions.Colors;
import functions.entities.GuildInfo;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;

import java.util.List;

public class ServerStats implements ICommand {
    CommandReceivedEvent e;
    Colors colors = new Colors();

    String command = "serverinfo";
    String commandAlias = "serverstats";
    String category = "util";
    String exampleCommand = "!serverinfo / !serverstats";
    String shortCommandDescription = "Get info about the server.";
    String fullCommandDescription = "Get information about the server.";

    @Override
    public void command(CommandReceivedEvent event) {
        e = event;

        if (!e.isFromGuild()) {
            e.getMessageChannel().sendMessage("This command only works in Discord servers/guild").queue();
            return;
        }

        GuildInfo guildInfo = new GuildInfo(e.getGuild());

        EmbedBuilder eb = new EmbedBuilder();

        eb.setTitle("Server stats of: " + e.getGuild().getName());
        eb.addField("Total members:", e.getGuild().getMemberCount() + "", true);
//        eb.addField("Total online/DND members:", guildInfo.getOnlineMemberCount() + "", false);
        eb.addField("Total non animated emojis:", guildInfo.getTotalNonAnimatedEmojis() + "", true);
        eb.addBlankField(true);
        eb.addField("Total animated emojis:", guildInfo.getTotalAnimatedEmojis() + "", true);
        eb.addField("Server owner:", guildInfo.getOwnerName(), true);
        eb.setThumbnail(e.getGuild().getIconUrl());
        eb.setFooter("Made by Tijs");

        eb.setColor(colors.getCurrentColor());

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
