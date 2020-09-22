package commands.music;

import commands.CommandEnum;
import commands.ICommand;
import database.guild.DatabaseGuild;
import music.PlayerManager;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.net.MalformedURLException;
import java.net.URL;

public class Play implements ICommand {
    GuildMessageReceivedEvent e;
    CommandEnum commandEnum = new CommandEnum();
    DatabaseGuild databaseGuild = new DatabaseGuild();

    String command = "play";
    String commandAlias = "p";
    String category = "general";
    String exampleCommand = "`!play <URL>`";
    String shortCommandDescription = "Plays music.";
    String fullCommandDescription = "Plays music.";

    @Override
    public void command(GuildMessageReceivedEvent event, String[] args) {
        e = event;

        if (!(args.length > 1)) {
            e.getChannel().sendMessage(commandEnum.getFullHelpItem("play").setDescription("Error: requires at least 1 argument").build()).queue();
            return;
        }

        String input = e.getMessage().getContentRaw().replace(databaseGuild.getPrefixGuildInDB(e.getGuild().getId()) + command + " ", "");

        if (!isUrl(input)) {
            e.getChannel().sendMessage(commandEnum.getFullHelpItem("play").setDescription("Error: currently requires a valid URL").build()).queue();
            return;
        }

        PlayerManager manager = PlayerManager.getInstance();

        manager.loadAndPlay(e.getChannel(), input);

        manager.getGuildMusicManager(e.getGuild()).player.setVolume(15);
    }

    private boolean isUrl(String input) {
        try {
            new URL(input);
            return true;
        } catch (MalformedURLException malformedURLException) {
            return false;
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
