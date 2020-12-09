package commands.fun;

import commands.CommandReceivedEvent;
import commands.ICommand;
import database.hugs.DatabaseHugs;
import net.dv8tion.jda.api.entities.User;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Hug implements ICommand {
    DatabaseHugs databaseHugs = new DatabaseHugs();
    Random r = new Random();

    String authorMentioned;
    String userToHugMentioned;

    static String filePath = "C:\\Users\\Tijs\\Documents\\Discord bot\\gif\\animehug.gif";
    static File file = new File(filePath);

    CommandReceivedEvent e;
    ArrayList<String> commandAliases = new ArrayList<>(Collections.singletonList("hug"));
    String category = "fun";
    String exampleCommand = "hug <@user>/<userID>";
    String shortCommandDescription = "Love you too";
    String fullCommandDescription = "*No homo* lets hug each other!";

    @Override
    public void command(CommandReceivedEvent event) {
        e = event;

        if (!e.isFromGuild()) {
            e.getChannel().sendMessage(getShortHelp("Run this command in a Discord guild/server", e.getPrefix())).queue();
            return;
        }

        if (!e.hasArgs()) {
            e.getChannel().sendMessage(getShortHelp("Requires a argument", e.getPrefix())).queue();
            return;
        }

        if (e.mentionsEveryone()) {
            e.getChannel().sendMessage(getShortHelp("Don't mention everyone! Not nice >.<", e.getPrefix())).queue();
            return;
        }

        User toHug = e.getFirstArgAsUser();
        if (toHug == null) {
            e.getChannel().sendMessage("Either mention a user or give a valid user ID!").queue();
            return;
        }

        userToHugMentioned = toHug.getAsMention();

        int maxInt = databaseHugs.numberItemsInDB();
        int randomGifIndex = r.nextInt(maxInt);
        String gifUrl = databaseHugs.getGifFromDB(randomGifIndex + 1);

        createFile(gifUrl);

        authorMentioned = e.getAuthor().getAsMention();

        e.getChannel().sendMessage(authorMentioned + " hugs " + userToHugMentioned + " :heart:").addFile(file).queue();

    }

    public void createFile(String gifUrl) {
        URL url;
        byte[] bytes;

        try {
            url = new URL(gifUrl);
            URLConnection urlConnection = url.openConnection();
            urlConnection.addRequestProperty("Accept", "image/gif");
            DataInputStream di = new DataInputStream(urlConnection.getInputStream());
            bytes = di.readAllBytes();

            OutputStream outputStream = new FileOutputStream(file);
            outputStream.write(bytes);
            outputStream.close();
        } catch (IOException malformedURLException) {
            malformedURLException.printStackTrace();
        }
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
