package commands.music;

import commands.CommandReceivedEvent;
import commands.ICommand;
import database.user.DatabaseUser;
import database.user.Song;
import database.user.UserDB;
import music.PlayerManager;
import net.dv8tion.jda.api.EmbedBuilder;

import java.util.ArrayList;
import java.util.Arrays;

import static util.Colors.getCurrentColor;

public class Playlist implements ICommand {
    DatabaseUser databaseUser = new DatabaseUser();
    CommandReceivedEvent e;

    String[] args;
    UserDB userDB;

    ArrayList<String> commandAliases = new ArrayList<>(Arrays.asList("playlist", "playlist"));
    String category = "general";
    String exampleCommand = "playlist <edit/view/create/delete/help>";
    String shortCommandDescription = "Edit, view, create or delete playlists.";
    String fullCommandDescription = "Edit, view, create or delete playlists. Run help to see the full help of this command.";

    @Override
    public void command(CommandReceivedEvent event) {
        e = event;

        if (!e.hasArgs()) {
            e.getChannel().sendMessage(getShortHelp("Requires at least 1 arguments!", e.getPrefix())).queue();
            return;
        }

        args = e.getArgs();

        userDB = e.getUserDB();

        switch (args[0]) {
            case "edit" -> editPlaylist();
            case "view" -> viewPlaylists();
            case "create" -> createPlaylist();
            case "delete" -> deletePlaylist();
            case "help" -> getFullHelpCommand();
        }
    }

    public void editPlaylist() {
        if (args.length < 2) {
            customError("edit", "playlist <edit> <playlist name> <add/remove>", "requires 3 arguments!");
            return;
        } else if (userDB.getPlaylist(args[1]) == null) {
            customError("edit", "playlist <edit> <playlist name> <add/remove>", "requires a valid playlist name!");
            return;
        }

        switch (args[2]) {
            case "remove" -> {
                if (userDB.getPlaylist(args[1]).size() >= 4 || args.length < 4) {
                    customError("edit", "playlist <edit> <playlist name> <remove> <song index>", "Requires a valid index!");
                }
                userDB.removeSong(args[1], Integer.parseInt(args[3]));
                e.getChannel().sendMessage("Removed 1 song from " + args[1]).queue();
            }
            case "add" -> {
                if (args.length < 4) {
                    customError("edit", "playlist <edit> <playlist name> <add> <song url>", "Requires a URL!");
                }
                PlayerManager playerManager = PlayerManager.getInstance();
                playerManager.loadAndAddToPlaylist(args[1], args[3], e.getUserDB(), e.getGuild());
                e.getChannel().sendMessage("Added 1 song in " + args[1]).queue();
            }
            case "rename" -> {
                if (args.length < 4) {
                    customError("edit", "playlist <edit> <playlist name> <rename> <new playlist name>", "Requires a new name!");
                }
                userDB.renamePlaylist(args[1], args[3]);
                e.getChannel().sendMessage("Renamed " + args[1] + " to " + args[3]).queue();
            }
        }

        databaseUser.updateUserInDB(userDB);
    }

    public void viewPlaylists() {
        EmbedBuilder eb = getEmbed();

        if (e.getArgs().length >= 2) {
            if (userDB.getPlaylist(args[1]) != null) {

                eb.setTitle(e.getAuthor().getAsTag() + "'s playlist " + args[1]);

                for (int i = 0; i < userDB.getPlaylist(args[1]).size(); i++) {
                    ArrayList<Song> songs = userDB.getPlaylist(args[1]);
                    eb.appendDescription("\n*Number in playlist " + i + "*\n");
                    eb.appendDescription("**" + songs.get(i).getAuthor() + "**\n");
                    eb.appendDescription("[" + songs.get(i).getTitle() + "](" + songs.get(i).getSongUrl() + ")\n");
                }

                e.getChannel().sendMessage(eb.build()).queue();

            } else {
                e.getChannel().sendMessage("Requires a valid playlist name!").queue();
            }
        } else {
            eb.setTitle(e.getAsTag() + "'s playlists");
            userDB.getPlaylists().forEach((key, songs) -> eb.appendDescription("\n" + key + " - " + songs.size() + " songs."));
            e.getChannel().sendMessage(eb.build()).queue();
        }
    }

    public void createPlaylist() {
        if (args.length >= 2) {
            UserDB userDB = e.getUserDB();
            userDB.addPlayList(args[1]);
            databaseUser.updateUserInDB(userDB);
        } else {
            customError("create", "playlist <create> <playlist name>", "requires 2 arguments!");
        }
    }

    public void deletePlaylist() {
        if (args.length >= 2 && userDB.getPlaylist(args[1]) != null) {
            userDB.removePlaylist(args[1]);
            databaseUser.updateUserInDB(userDB);
        } else {
            customError("delete", "playlist <delete> <playlist name>", "Requires a valid playlist name!");
        }
    }

    public void customError(String commandName, String fullCommandExample, String error) {
        EmbedBuilder eb = getEmbed();
        eb.setTitle("Error playlist " + commandName);
        eb.addField("`" + e.getPrefix() + fullCommandExample + "`",  getShortCommandDescription(), true);

        if (error != null) {
            eb.setDescription("**" + error + "**");
        }
        e.getChannel().sendMessage(eb.build()).queue();
    }

    public void getFullHelpCommand() {
        EmbedBuilder eb = getEmbed();
        eb.setTitle("Full help playlist!");

        eb.addField("`" + e.getPrefix() + "playlist edit <playlist name>`",  "Edit a playlist", false);
        eb.addField("`" + e.getPrefix() + "playlist edit <playlist name> <remove> <index of song>`",  "Remove a song from a existing, use the index of the song (visible when doing " + e.getPrefix() + "playlist view <playlist name>)", false);
        eb.addField("`" + e.getPrefix() + "playlist edit <playlist name> <add> <song URL>`",  "Add a song to a existing playlist using a song URL", false);
        eb.addField("`" + e.getPrefix() + "playlist view (playlist name)`",  "View a specific playlist, wanna see all playlists? Give no playlist name.", false);
        eb.addField("`" + e.getPrefix() + "playlist create <playlist name>`",  "Creates a playlist by name.", false);
        eb.addField("`" + e.getPrefix() + "playlist delete <playlist name>`",  "Deletes a playlist by name.", false);
        eb.addField("`" + e.getPrefix() + "playlist help`",  "Get this message.", false);
        eb.setColor(getCurrentColor());

        e.getChannel().sendMessage(eb.build()).queue();
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
