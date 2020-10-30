package commands;

import commands.general.BotPrefix;
import database.guild.DatabaseGuild;
import database.guild.GuildHandler;
import database.user.UserHandler;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class CommandHandler extends ListenerAdapter {
    CommandReceivedEvent e;

    String prefix;

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

        e = new CommandReceivedEvent(event, prefix);

        if (e.getUserDB().isBlackListed()) {
            e.getMessageChannel().sendMessage("You suck.").queue();
            return;
        }

        if (e.getMessage().getContentRaw().startsWith(prefix)) {
            if (commandEnum.checkCommand(e)) return;
        }

        if (e.getMessage().getMentionedUsers().contains(e.getJDA().getSelfUser())) botPrefix.command(e);

        if (e.isFromGuild()) {
            userHandler.checkUser(event);
            guildHandler.checkGuild(event);
        }

        if (e.isFromGuild()) {
            System.out.println(event.getGuild().getName() + " " + event.getAuthor().getAsTag() + " > " + event.getMessage().getContentRaw());
        } else {
            System.out.println("DM's " + event.getAuthor().getAsTag() + " > " + event.getMessage().getContentRaw());
        }
    }
}

