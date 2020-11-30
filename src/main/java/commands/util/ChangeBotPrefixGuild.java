package commands.util;

import commands.CommandReceivedEvent;
import commands.ICommand;
import database.guild.DatabaseGuild;
import database.guild.GuildDB;

import java.util.ArrayList;
import java.util.Arrays;

public class ChangeBotPrefixGuild implements ICommand {
    CommandReceivedEvent e;
    DatabaseGuild databaseGuild = new DatabaseGuild();

    GuildDB guildDB;

    ArrayList<String> commandAliases = new ArrayList<>(Arrays.asList("changebotprefixguild", "setbotprefixserver", "changebotprefixserver", "botprefixserver"));
    String category = "util";
    String exampleCommand = "changebotprefixguild <botprefix>";
    String shortCommandDescription = "Set the prefix of the bot in this guild.";
    String fullCommandDescription = "Set the prefix of the bot in this guild.";

    @Override
    public void command(CommandReceivedEvent event) {
        e = event;

        String prefix;

        if (!e.isFromGuild()) {
            e.getChannel().sendMessage("This command only works in Discord servers/guild").queue();
            return;
        }

        if (!e.hasArgs()) {
            e.getChannel().sendMessage(getFullHelp("Requires at least 1 argument", e.getPrefix())).queue();
            return;
        }

        guildDB = e.getGuildDB();

        prefix = e.getMessageWithoutCommand();

        guildDB.setPrefix(prefix);

        databaseGuild.updateGuildInDB(guildDB);

        e.getChannel().sendMessage("Prefix changed to: " + prefix).queue();
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
