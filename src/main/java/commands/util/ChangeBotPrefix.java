package commands.util;

import commands.CommandEnum;
import commands.ICommand;
import database.guild.DatabaseGuild;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class ChangeBotPrefix implements ICommand {
    GuildMessageReceivedEvent e;
    CommandEnum commandEnum = new CommandEnum();
    DatabaseGuild databaseGuild = new DatabaseGuild();

    String command = "setbotprefix";
    String commandAlias = "botprefix";
    String category = "util";
    String exampleCommand = "`!setbotprefix <botprefix>`";
    String shortCommandDescription = "Set the prefix of the bot in this guild.";
    String fullCommandDescription = "Set the prefix of the bot in this guild.";

    @Override
    public void command(GuildMessageReceivedEvent e, String[] args) {
        this.e = e;
        String prefix;
        String guildID = e.getGuild().getId();

        String currentPrefix = databaseGuild.getPrefixGuildInDB(e.getGuild().getId());

        if (args.length > 1) {
            String toReplace = currentPrefix + command + " ";
            String messageRaw = e.getMessage().getContentRaw();
            prefix = messageRaw.replace(toReplace, "");
            databaseGuild.setPrefixGuildInDB(guildID, prefix);
            e.getChannel().sendMessage("Prefix changed to: " + prefix).queue();
        } else {
            e.getChannel().sendMessage(commandEnum.getFullHelpItem("setbotprefix").build()).queue();
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
