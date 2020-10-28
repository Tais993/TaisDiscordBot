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

    String prefix;
    String messageSent;

    CommandEnum commandEnum = new CommandEnum();
    UserHandler userHandler = new UserHandler();
    GuildHandler guildHandler = new GuildHandler();
    DatabaseGuild databaseGuild = new DatabaseGuild();
    BotPrefix botPrefix = new BotPrefix();

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getAuthor().isBot()) return;

        if (event.isFromGuild()) {
            prefix = databaseGuild.getPrefixGuildInDB(event.getGuild().getId());
        } else {
            prefix = "!";
        }

        if (event.getMessage().getContentRaw().startsWith(prefix)) {
            e = new CommandReceivedEvent(event, prefix);

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
        return commandEnum.checkCommand(e);
    }
}

