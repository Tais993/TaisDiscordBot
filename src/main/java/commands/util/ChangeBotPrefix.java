package commands.util;

import commands.CommandReceivedEvent;
import commands.ICommand;
import database.guild.DatabaseGuild;

public class ChangeBotPrefix implements ICommand {
    CommandReceivedEvent e;
    DatabaseGuild databaseGuild = new DatabaseGuild();

    String command = "setbotprefix";
    String commandAlias = "botprefix";
    String category = "util";
    String exampleCommand = "`!setbotprefix <botprefix>`";
    String shortCommandDescription = "Set the prefix of the bot in this guild.";
    String fullCommandDescription = "Set the prefix of the bot in this guild.";

    @Override
    public void command(CommandReceivedEvent event) {
        e = event;

        String prefix;

        if (!e.isFromGuild()) {
            e.getMessageChannel().sendMessage("This command only works in Discord servers/guild").queue();
            return;
        }

        String guildID = e.getGuild().getId();

        String currentPrefix = databaseGuild.getPrefixGuildInDB(guildID);

        if (!e.hasArgs()) {
            e.getMessageChannel().sendMessage(getFullHelp("Requires at least 1 argument")).queue();
            return;
        }

        String toReplace = currentPrefix + command + " ";
        String messageRaw = e.getMessage().getContentRaw();
        prefix = messageRaw.replace(toReplace, "");
        databaseGuild.setPrefixGuildInDB(guildID, prefix);
        e.getMessageChannel().sendMessage("Prefix changed to: " + prefix).queue();
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
