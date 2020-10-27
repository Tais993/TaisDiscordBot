package commands;

import commands.general.BotPrefix;
import database.guild.DatabaseGuild;
import database.guild.GuildHandler;
import database.user.UserHandler;
import net.dv8tion.jda.api.entities.SelfUser;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class CommandHandler extends ListenerAdapter {
    CommandReceivedEvent e;

    String guildPrefix;
    String messageSent;

    CommandEnum commandEnum = new CommandEnum();
    UserHandler userHandler = new UserHandler();
    GuildHandler guildHandler = new GuildHandler();
    DatabaseGuild databaseGuild = new DatabaseGuild();
    BotPrefix botPrefix = new BotPrefix();

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        e = new CommandReceivedEvent(event);

        if (e.getAuthor().isBot()) return;

        if (e.isFromGuild) {
            guildPrefix = databaseGuild.getPrefixGuildInDB(event.getGuild().getId());
        } else {
            guildPrefix = "!";
        }

        messageSent = event.getMessage().getContentRaw();

        if (messageSent.startsWith(guildPrefix)) {
            if (command()) return;
        }

        SelfUser botUser = e.getJDA().getSelfUser();

        if (e.getMessage().getMentionedUsers().contains(botUser)) botPrefix.command(e);


        if (e.isFromGuild) {
            userHandler.checkUser(event);
            guildHandler.checkGuild(event);
        }
    }

    public boolean command() {
        if (e.getAuthor().isBot()) return false;

        String[] messageSentSplit = messageSent.replace(guildPrefix, "").split(" ");

        return commandEnum.checkCommand(e, messageSentSplit);
    }
}

