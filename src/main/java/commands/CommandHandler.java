package commands;

import commands.general.BotPrefix;
import commands.general.YouTube;
import database.guild.DatabaseGuild;
import database.guild.GuildHandler;
import database.user.UserHandler;
import net.dv8tion.jda.api.entities.Member;
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

        String botUserId = e.getJDA().getSelfUser().getId();
        Member botMember = e.getGuild().getMemberById(botUserId);

        if (e.getMessage().getMentionedMembers().contains(botMember)) botPrefix.command(e);

        userHandler.checkUser(event);
        guildHandler.checkGuild(event);

        if (!e.isFromGuild) {
            YouTube youTube = new YouTube();
            youTube.command(e);
        }
    }

    public boolean command() {
        if (e.getAuthor().isBot()) return false;

        String[] messageSentSplit = messageSent.replace(guildPrefix, "").split(" ");

        return commandEnum.checkCommand(e, messageSentSplit);
    }
}

