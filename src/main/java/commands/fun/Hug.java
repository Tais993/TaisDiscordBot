package commands.fun;

import commands.CommandReceivedEvent;
import commands.ICommand;
import database.hugs.DatabaseHugs;
import net.dv8tion.jda.api.entities.Member;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.Random;

public class Hug implements ICommand {
    DatabaseHugs databaseHugs = new DatabaseHugs();
    Random r = new Random();

    String authorMentioned;
    String userToHugMentioned;

    static String filePath = "C:\\Users\\Tijs\\Documents\\Discord bot\\gif\\animehug.gif";
    static File file = new File(filePath);

    CommandReceivedEvent e;
    String command = "hug";
    String commandAlias = "hug";
    String category = "fun";
    String exampleCommand = "`!hug <@user>/<userID>`";
    String shortCommandDescription = "Love you too";
    String fullCommandDescription = "*No homo* lets hug each other!";

    @Override
    public void command(CommandReceivedEvent event) {
        e = event;

        if (!e.isFromGuild()) {
            e.getMessageChannel().sendMessage(commandEnum.getFullHelpItem("hug").setDescription("Run this command in a Discord guild/server").build()).queue();
            return;
        }

        if (!e.hasArgs()) {
            e.getMessageChannel().sendMessage(commandEnum.getFullHelpItem("hug").setDescription("Requires a argument").build()).queue();
            return;
        }

        if (e.getMessage().getMentionedMembers().size() >= 1) {
            userToHugMentioned = e.getMessage().getMentionedMembers().get(0).getAsMention();
        } else if (e.hasArgs()) {
            Member member = e.getGuild().getMemberById(e.getArgs()[0]);
            if (member != null) {
                userToHugMentioned = member.getAsMention();
            } else {
                e.getMessageChannel().sendMessage(commandEnum.getFullHelpItem("hug").setDescription("Error: give a valid userId").build()).queue();
                return;
            }
        } else {
            e.getMessageChannel().sendMessage(commandEnum.getFullHelpItem("hug").setDescription("Error: either mention a user or give a user ID").build()).queue();
            return;
        }

        int easterEgg = r.nextInt(101);

        if (easterEgg >= 99) {
            createFile("https://media1.tenor.com/images/4d853211454cb61fa38e308f60c62e28/tenor.gif?itemid=15723089");
            e.getMessageChannel().sendMessage("KNOLPOWER").addFile(file).queue();
            return;
        }

        int maxInt = databaseHugs.numberItemsInDB();
        int randomGifIndex = r.nextInt(maxInt);
        String gifUrl = databaseHugs.getGifFromDB(randomGifIndex + 1);

        createFile(gifUrl);

        authorMentioned = e.getAuthor().getAsMention();

        e.getMessageChannel().sendMessage(authorMentioned + " hugs " + userToHugMentioned + " :heart:").addFile(file).queue();

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
