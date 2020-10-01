package commands;

import commands.general.BotPrefix;
import database.guild.DatabaseGuild;
import database.guild.GuildHandler;
import database.user.UserHandler;
import net.dv8tion.jda.api.entities.Member;
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
    BotPrefix botPrefix = new BotPrefix();

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        e = event;

        guildPrefix = databaseGuild.getPrefixGuildInDB(event.getGuild().getId());
        messageSent = event.getMessage().getContentRaw();

        if (messageSent.startsWith(guildPrefix)) command();

        String botUserId = e.getJDA().getSelfUser().getId();
        Member botMember = e.getGuild().getMemberById(botUserId);

        if (e.getMessage().getMentionedMembers().contains(botMember)) botPrefix.command(e);

        userHandler.checkUser(event);
        guildHandler.checkGuild(event);
    }

    public void command() {
        if (e.getAuthor().isBot()) return;

        String[] messageSentSplit = messageSent.replace(guildPrefix, "").split(" ");

        commandEnum.checkCommand(e, messageSentSplit);
    }
}

