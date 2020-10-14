package commands.general;

import commands.CommandEnum;
import commands.CommandReceivedEvent;
import commands.ICommand;
import database.guild.DatabaseGuild;
import music.youtube.SearchYouTube;

public class YouTube implements ICommand {
    CommandReceivedEvent e;

    String url;

    CommandEnum commandEnum = new CommandEnum();
    DatabaseGuild databaseGuild = new DatabaseGuild();
    SearchYouTube searchYouTube = new SearchYouTube();

    String command = "youtube";
    String commandAlias = "youtube";
    String category = "general";
    String exampleCommand = "`!youtube <gifurl>`";
    String shortCommandDescription = "Search for a YouTube video.";
    String fullCommandDescription = "Search for a YouTube video.";

    @Override
    public void command(CommandReceivedEvent event) {
        e = event;

        String input = e.getMessage().getContentRaw().replace(databaseGuild.getPrefixGuildInDB(e.getGuild().getId()) + command + " ", "");

        String videoUrl = searchYouTube.getVideoUrl(input);

        if (videoUrl.startsWith("Error:")) {
            e.getMessageChannel().sendMessage(commandEnum.getFullHelpItem("youtube").setDescription(videoUrl).build()).queue();
        }


        url = videoUrl;

        if (url.isEmpty()) {
            e.getMessageChannel().sendMessage("Nothing has been found by " + input).queue();
        } else {
            e.getMessageChannel().sendMessage(url).queue();
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
