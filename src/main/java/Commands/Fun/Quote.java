package Commands.Fun;

import Commands.Colors;
import Commands.ICommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.exceptions.ErrorResponseException;
import net.dv8tion.jda.api.requests.ErrorResponse;

import java.awt.*;
import java.net.MalformedURLException;
import java.net.URL;

public class Quote implements ICommand {
    GuildMessageReceivedEvent e;
    Colors colors = new Colors();

    String command = "quote";
    String commandAlias = "quote";
    String category = "fun";
    String exampleCommand = "!quote <messageID>";
    String shortCommandDescription = "Get the server stats";
    String fullCommandDescription = "Get total member count, and total online/DND members.";

    @Override
    public void command(GuildMessageReceivedEvent event, String[] args) {
        e = event;

        e.getMessage().getMentionedChannels().size()

        if (args.length == 2){
            getMessage(args[1]);
        } else if (args.length == 3){
            getMessageSunTzu(args[1]);
        }
    }

    public void getMessage(String id) {
        e.getChannel().retrieveMessageById(id).queue(this::createEmbed, (failure) -> {
            if (failure instanceof ErrorResponseException) {
                ErrorResponseException ex = (ErrorResponseException) failure;
                if (ex.getErrorResponse() == ErrorResponse.UNKNOWN_MESSAGE) {
                    e.getChannel().sendMessage("Message doesn't exist!").queue();
                }
            }
            failure.printStackTrace();
        });
    }

    public void createEmbed(Message message) {
        EmbedBuilder eb = new EmbedBuilder();

        eb.setColor(colors.getCurrentColor());
        eb.setTitle("Quote from: " + message.getAuthor().getName());
        eb.setThumbnail(message.getAuthor().getAvatarUrl());
        eb.setDescription(message.getContentDisplay());

        e.getChannel().sendMessage(eb.build()).queue();
    }

    public void getMessageSunTzu(String id) {
        e.getChannel().retrieveMessageById(id).queue(this::createEmbedSunTzu, (failure) -> {
            if (failure instanceof ErrorResponseException) {
                ErrorResponseException ex = (ErrorResponseException) failure;
                if (ex.getErrorResponse() == ErrorResponse.UNKNOWN_MESSAGE) {
                    e.getChannel().sendMessage("Message doesn't exist!").queue();
                }
            }
            failure.printStackTrace();
        });
    }

    public void createEmbedSunTzu(Message message) {
        EmbedBuilder eb = new EmbedBuilder();

        eb.setColor(colors.getCurrentColor());
        eb.setTitle("Quote from: Sun Tzu");
        eb.setThumbnail("https://pbs.twimg.com/profile_images/1102567546/sun_tzu_general_400x400.jpg");
        eb.setDescription(message.getContentDisplay());

        e.getChannel().sendMessage(eb.build()).queue();
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
