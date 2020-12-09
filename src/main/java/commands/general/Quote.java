package commands.general;

import commands.CommandReceivedEvent;
import commands.ICommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.exceptions.ErrorResponseException;
import net.dv8tion.jda.api.requests.ErrorResponse;

import java.util.ArrayList;
import java.util.Collections;

import static utilities.Colors.getCurrentColor;

public class Quote implements ICommand {
    CommandReceivedEvent e;

    TextChannel textChannel;
    String[] args;

    ArrayList<String> commandAliases = new ArrayList<>(Collections.singletonList("quote"));
    String category = "general";
    String exampleCommand = "quote message <messageID> (channel) (Sun Tzu)`\n`!quote message <messageID> (Sun Tzu)`\n`!quote text <input> (Sun Tzu)";
    String shortCommandDescription = "Create a message quote.";
    String fullCommandDescription = """
            Create a message quote, channel and Sun Tzu are optimal.
            Channel should be like `#general`
            Sun Tzu should be `true` to be on
            If you use `!quote text <input>`\s
            change input to the text you would like to quote.""";

    @Override
    public void command(CommandReceivedEvent event) {
        e = event;

        if (e.mentionsEveryone()) {
            e.getChannel().sendMessage(getFullHelp("Don't mention everyone! Not nice >.<", e.getPrefix())).queue();
            return;
        }

        if (!e.hasArgs()) {
            e.getChannel().sendMessage(getFullHelp("Requires 2 or more arguments", e.getPrefix())).queue();
            return;
        }

        args = e.getArgs();

        if (!e.isFromGuild()) {
            quoteCommandPrivate();
        } else {
            quoteCommandGuild();
        }
    }

    public void quoteCommandPrivate() {
        switch (args[0]) {
            case "message" -> e.getChannel().sendMessage(getFullHelp("Quoting a message only works in a guild.", e.getPrefix())).queue();
            case "text" -> createPersonalEmbed();
            default -> e.getChannel().sendMessage(getFullHelp("Error: second input should  either be `text` or `message`.", e.getPrefix())).queue();
        }
    }

    public void quoteCommandGuild() {
        switch (args[0]) {
            case "message" -> {
                if (mentionsTextChannel()) {
                    textChannel = e.getMessage().getMentionedChannels().get(0);
                } else {
                    textChannel = e.getTextChannel();
                }
                getMessage(args[1]);
            }
            case "text" -> createPersonalEmbed();
            default -> e.getChannel().sendMessage(getFullHelp("Error: second input should  either be `text` or `message`.", e.getPrefix())).queue();
        }
    }

    public void createEmbed(Message message) {
        EmbedBuilder eb = new EmbedBuilder();
        eb.setColor(getCurrentColor());

        if (isSunTzu()) {
            eb.setTitle("Quote from: Sun Tzu");
            eb.setThumbnail("https://pbs.twimg.com/profile_images/1102567546/sun_tzu_general_400x400.jpg");
            eb.setDescription(message.getContentDisplay());
        } else {
            eb.setTitle("Quote from: " + message.getAuthor().getName());
            eb.setThumbnail(message.getAuthor().getAvatarUrl());
            eb.setDescription(message.getContentDisplay());
            eb.appendDescription("\n[Link](" + message.getJumpUrl() + ")");
        }
        
        e.getChannel().sendMessage(eb.build()).queue();
    }

    public void createPersonalEmbed() {
        EmbedBuilder eb = new EmbedBuilder();
        eb.setColor(getCurrentColor());

        if (isSunTzu()) {
            eb.setTitle("Quote from: Sun Tzu");
            eb.setThumbnail("https://pbs.twimg.com/profile_images/1102567546/sun_tzu_general_400x400.jpg");
        } else {
            eb.setTitle("Quote from: " + e.getAuthor().getName());
            eb.setThumbnail(e.getAuthor().getAvatarUrl());
        }

        eb.setDescription(getMessageToQuote());
        e.getChannel().sendMessage(eb.build()).queue();
    }

    public void getMessage(String id) {
        textChannel.retrieveMessageById(id).queue(this::createEmbed, (failure) -> {
            if (failure instanceof ErrorResponseException) {
                ErrorResponseException ex = (ErrorResponseException) failure;
                if (ex.getErrorResponse() == ErrorResponse.UNKNOWN_MESSAGE) {
                    e.getChannel().sendMessage("Message doesn't exist!").queue();
                }
            }
            failure.printStackTrace();
        });
    }

    public boolean isSunTzu() {
        return args[args.length - 1].equalsIgnoreCase("true");
    }

    public boolean mentionsTextChannel() {
        return e.getMessage().getMentionedChannels().size() > 0;
    }

    public String getMessageToQuote() {
        StringBuilder messageToQuote = new StringBuilder();

        if (isSunTzu()) {
            for (int i = 1; i < args.length - 1; i++) {
                if (messageToQuote.toString().equals("")) {
                    messageToQuote.append(args[i]);
                } else {
                    messageToQuote.append(" ").append(args[i]);
                }
            }
        } else {
            for (int i = 1; i < args.length; i++) {
                if (messageToQuote.toString().equals("")) {
                    messageToQuote.append(args[i]);
                } else {
                    messageToQuote.append(" ").append(args[i]);
                }
            }
        }

        return messageToQuote.toString();
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
