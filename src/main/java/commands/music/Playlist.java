package commands.music;

import commands.CommandReceivedEvent;
import commands.ICommand;
import database.user.DatabaseUser;
import database.user.UserDB;
import net.dv8tion.jda.api.EmbedBuilder;

import java.util.ArrayList;
import java.util.Arrays;

public class Playlist implements ICommand {
    DatabaseUser databaseUser = new DatabaseUser();
    CommandReceivedEvent e;

    String[] args;
    UserDB userDB;

    ArrayList<String> commandAliases = new ArrayList<>(Arrays.asList("playlist", "playlist"));
    String category = "general";
    String exampleCommand = "playlist <something>";
    String shortCommandDescription = "Testing.";
    String fullCommandDescription = "Testing";

    @Override
    public void command(CommandReceivedEvent event) {
        e = event;

        if (!e.hasArgs() || e.getArgs().length < 2) {
            e.getMessageChannel().sendMessage(getShortHelp("Requires at least 2 arguments!", e.getPrefix())).queue();
            return;
        }

        args = e.getArgs();

        userDB = e.getUserDB();

        EmbedBuilder eb = getEmbed();

        switch (args[0]) {
            case "edit" -> {
                if (userDB.getPlaylist(args[1]) != null) editPlaylist();
                return;
            }
            case "view" -> {
                eb.setTitle(e.getAuthor().getAsTag() + "'s playlist " + args[1]);
                if (userDB.getPlaylist(args[1]) != null) for (int i = 0; i < userDB.getPlaylist(args[1]).size(); i++) {
                    ArrayList<String> songs = userDB.getPlaylist(args[1]);
                    eb.appendDescription("\nSong URL: " + songs.get(i) + "\n *Number " + i + "*\n");
                }
            }
        }

        e.getMessageChannel().sendMessage(eb.build()).queue();
    }

    public void editPlaylist() {
        switch (args[2]) {
            case "remove" -> {
                userDB.removeSong(args[1], Integer.parseInt(args[3]));
                e.getMessageChannel().sendMessage("Removed 1 song from " + args[1]).queue();
            }
            case "add" -> {
                userDB.addSong(args[1], args[3]);
                e.getMessageChannel().sendMessage("Added 1 song in " + args[1]).queue();
            }
        }

        databaseUser.updateUserInDB(userDB);
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

    @Override
    public ArrayList<String> getCommandAliases() {
        return commandAliases;
    }
}
