package commands.util;

import commands.CommandReceivedEvent;
import commands.ICommand;
import net.dv8tion.jda.api.EmbedBuilder;
import util.entities.GuildInfo;

import java.util.ArrayList;
import java.util.Arrays;

import static util.Colors.getCurrentColor;

public class ServerStats implements ICommand {
    CommandReceivedEvent e;

    ArrayList<String> commandAliases = new ArrayList<>(Arrays.asList("serverstats", "serverinfo"));
    String category = "util";
    String exampleCommand = "serverinfo";
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
        eb.addField("Total non animated emojis:", guildInfo.getTotalNonAnimatedEmojis() + "", true);
        eb.addBlankField(true);
        eb.addField("Total animated emojis:", guildInfo.getTotalAnimatedEmojis() + "", true);
        eb.addField("Server owner:", guildInfo.getOwnerName(), true);
        eb.setThumbnail(e.getGuild().getIconUrl());

        eb.setColor(getCurrentColor());

        e.getMessageChannel().sendMessage(eb.build()).queue();
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

    @Override
    public ArrayList<String> getCommandAliases() {
        return commandAliases;
    }
}
