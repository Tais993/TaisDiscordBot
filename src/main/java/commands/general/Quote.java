package commands.general;

import commands.CommandReceivedEvent;
import commands.ICommand;
import functions.Colors;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.exceptions.ErrorResponseException;
import net.dv8tion.jda.api.requests.ErrorResponse;

public class Quote implements ICommand {
    CommandReceivedEvent e;
    Colors colors = new Colors();

    TextChannel textChannel;
    String[] args;

    String command = "quote";
    String commandAlias = "quote";
    String category = "general";
    String exampleCommand = "`!quote message <messageID> (channel) (Sun Tzu)`\n`!quote message <messageID> (Sun Tzu)`\n`!quote text <input> (Sun Tzu)`";
    String shortCommandDescription = "Create a message quote.";
    String fullCommandDescription = "Create a message quote, channel and Sun Tzu are optimal.\n" +
            "Channel should be like `#general`\n" +
            "Sun Tzu should be `true` to be on\n" +
            "If you use `!quote text <input>` \n" +
            "change input to the text you would like to quote.";

    @Override
    public void command(CommandReceivedEvent event) {
        e = event;

        if (e.mentionsEveryone()) {
            e.getMessageChannel().sendMessage(getFullHelp("Don't mention everyone! Not nice >.<")).queue();
            return;
        }

        if (!e.hasArgs()) {
            e.getMessageChannel().sendMessage(getFullHelp("Requires 2 or more arguments")).queue();
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
        if (args.length >= 2) {
            switch (args[0]) {
                case "message":
                    e.getMessageChannel().sendMessage(getFullHelp("Quoting a message only works in a guild.")).queue();
                    break;
                case "text":
                    createPersonalEmbed();
                    break;
                default:
                    e.getMessageChannel().sendMessage(getFullHelp("Error: second input should  either be `text` or `message`.")).queue();
            }
        } else {
            e.getMessageChannel().sendMessage(getFullHelp("Error: requires at least 3 arguments.")).queue();
        }
    }

    public void quoteCommandGuild() {
        if (args.length >= 2) {
            switch (args[0]) {
                case "message":
                    if (mentionsTextChannel()){
                        textChannel = e.getMessage().getMentionedChannels().get(0);
                    } else {
                        textChannel = e.getTextChannel();
                    }
                    getMessage(args[1]);
                    break;
                case "text":
                    createPersonalEmbed();
                    break;
                default:
                    e.getMessageChannel().sendMessage(getFullHelp("Error: second input should  either be `text` or `message`.")).queue();
            }
        } else {
            e.getMessageChannel().sendMessage(getFullHelp("Error: requires at least 3 arguments.")).queue();
        }
    }

    public void createEmbed(Message message) {
        EmbedBuilder eb = new EmbedBuilder();
        eb.setColor(colors.getCurrentColor());

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
        
        e.getMessageChannel().sendMessage(eb.build()).queue();
    }

    public void createPersonalEmbed() {
        EmbedBuilder eb = new EmbedBuilder();
        eb.setColor(colors.getCurrentColor());

        if (isSunTzu()) {
            eb.setTitle("Quote from: Sun Tzu");
            eb.setThumbnail("https://pbs.twimg.com/profile_images/1102567546/sun_tzu_general_400x400.jpg");
        } else {
            eb.setTitle("Quote from: " + e.getAuthor().getName());
            eb.setThumbnail(e.getAuthor().getAvatarUrl());
        }

        eb.setDescription(getMessageToQuote());
        e.getMessageChannel().sendMessage(eb.build()).queue();
    }

    public void getMessage(String id) {
        textChannel.retrieveMessageById(id).queue(this::createEmbed, (failure) -> {
            if (failure instanceof ErrorResponseException) {
                ErrorResponseException ex = (ErrorResponseException) failure;
                if (ex.getErrorResponse() == ErrorResponse.UNKNOWN_MESSAGE) {
                    e.getMessageChannel().sendMessage("Message doesn't exist!").queue();
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
