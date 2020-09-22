package commands;

import database.guild.DatabaseGuild;
import database.guild.GuildHandler;
import database.user.UserHandler;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class CommandHandler extends ListenerAdapter {
    GuildMessageReceivedEvent e;
    String guildPrefix;
    String messageSent;

    CommandEnum commandEnum = new CommandEnum();
    UserHandler userHandler = new UserHandler();
    GuildHandler guildHandler = new GuildHandler();
    DatabaseGuild databaseGuild = new DatabaseGuild();

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        e = event;

        guildPrefix = databaseGuild.getPrefixGuildInDB(event.getGuild().getId());
        messageSent = event.getMessage().getContentRaw();

        if (messageSent.startsWith(guildPrefix)) command();

        userHandler.checkUser(event);
        guildHandler.checkGuild(event);
    }

    public void command() {
        if (e.getAuthor().isBot()) return;

        String[] messageSentSplit = messageSent.replace(guildPrefix, "").split(" ");

        commandEnum.checkCommand(e, messageSentSplit);
    }
}

