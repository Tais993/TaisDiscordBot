package commands.general;

import commands.CommandEnum;
import commands.ICommand;
import database.guild.DatabaseGuild;
import music.youtube.Search;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class YouTube implements ICommand {
    GuildMessageReceivedEvent e;

    String url;

    CommandEnum commandEnum = new CommandEnum();
    DatabaseGuild databaseGuild = new DatabaseGuild();
    Search search = new Search();

    String command = "youtube";
    String commandAlias = "youtube";
    String category = "general";
    String exampleCommand = "`!youtube <gifurl>`";
    String shortCommandDescription = "Search for a YouTube video.";
    String fullCommandDescription = "Search for a YouTube video.";

    @Override
    public void command(GuildMessageReceivedEvent event, String[] args) {
        e = event;

        String input = e.getMessage().getContentRaw().replace(databaseGuild.getPrefixGuildInDB(e.getGuild().getId()) + command + " ", "");

        String videoUrl = search.getVideoUrl(input);

        if (videoUrl.startsWith("Error:")) {
            e.getChannel().sendMessage(commandEnum.getFullHelpItem("youtube").setDescription(videoUrl).build()).queue();
        }


        url = videoUrl;

        if (url.isEmpty()) {
            e.getChannel().sendMessage("Nothing has been found by " + input).queue();
        } else {
            e.getChannel().sendMessage(url).queue();
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