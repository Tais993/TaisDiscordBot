package commands.util;

import commands.CommandEnum;
import commands.CommandReceivedEvent;
import commands.ICommand;
import database.guild.DatabaseGuild;

public class ChangeBotPrefix implements ICommand {
    CommandReceivedEvent e;
    CommandEnum commandEnum = new CommandEnum();
    DatabaseGuild databaseGuild = new DatabaseGuild();

    String command = "setbotprefix";
    String commandAlias = "botprefix";
    String category = "util";
    String exampleCommand = "`!setbotprefix <botprefix>`";
    String shortCommandDescription = "Set the prefix of the bot in this guild.";
    String fullCommandDescription = "Set the prefix of the bot in this guild.";

    @Override
    public void command(CommandReceivedEvent event, String[] args) {
        e = event;
        String prefix;

        if (!e.isFromGuild()) {
            e.getMessageChannel().sendMessage("This command only works in Discord servers/guild").queue();
            return;
        }

        String guildID = e.getGuild().getId();

        String currentPrefix = databaseGuild.getPrefixGuildInDB(e.getGuild().getId());

        if (args.length > 1) {
            String toReplace = currentPrefix + command + " ";
            String messageRaw = e.getMessage().getContentRaw();
            prefix = messageRaw.replace(toReplace, "");
            databaseGuild.setPrefixGuildInDB(guildID, prefix);
            e.getMessageChannel().sendMessage("Prefix changed to: " + prefix).queue();
        } else {
            e.getMessageChannel().sendMessage(commandEnum.getFullHelpItem("setbotprefix").build()).queue();
        }
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
