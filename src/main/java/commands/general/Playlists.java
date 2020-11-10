package commands.general;

import commands.CommandReceivedEvent;
import commands.ICommand;
import database.user.DatabaseUser;
import database.user.UserDB;

import java.util.ArrayList;
import java.util.Arrays;

public class Playlists implements ICommand {
    DatabaseUser databaseUser = new DatabaseUser();
    CommandReceivedEvent e;

    ArrayList<String> commandAliases = new ArrayList<>(Arrays.asList("playlist", "playlist"));
    String category = "general";
    String exampleCommand = "level <@user>/<userID>";
    String shortCommandDescription = "Get the level of someone in this server.";
    String fullCommandDescription = "Get the level of someone in this server.";

    @Override
    public void command(CommandReceivedEvent event) {
        e = event;

        UserDB userDB = e.getUserDB();

        userDB.addPlayList("meme");
        userDB.addSong("meme", "lttstore.com");

        databaseUser.updateUserInDB(userDB);

        StringBuilder stringBuilder = new StringBuilder();

        userDB.getPlaylists().forEach((key, playlist) -> {
            stringBuilder.append(key).append(" :");
            playlist.forEach(stringBuilder::append);
        });

        e.getMessageChannel().sendMessage(stringBuilder).queue();
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
