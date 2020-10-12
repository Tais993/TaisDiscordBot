package commands.general;

import commands.CommandEnum;
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
    CommandEnum commandEnum = new CommandEnum();

    TextChannel textChannel;
    String[] allArgs;

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
        allArgs = e.getArgs();

        if (!e.isFromGuild()) {
            quoteCommandPrivate();
        } else {
            quoteCommandGuild();
        }
    }

    public void quoteCommandPrivate() {
        String[] args =e.getArgs();
        if (args.length >= 3) {
            switch (args[1]) {
                case "message":
                    e.getMessageChannel().sendMessage(commandEnum.getFullHelpItem("quote").setDescription("Quoting a message only works in a guild.").build()).queue();
                    break;
                case "text":
                    createPersonalEmbed();
                    break;
                default:
                    e.getMessageChannel().sendMessage(commandEnum.getFullHelpItem("quote").setDescription("Error: second input should  either be `text` or `message`.").build()).queue();
            }
        } else {
            e.getMessageChannel().sendMessage(commandEnum.getFullHelpItem("quote").setDescription("Error: requires at least 3 arguments.").build()).queue();
        }
    }

    public void quoteCommandGuild() {
        String[] args = e.getArgs();
        if (args.length >= 3) {
            switch (args[1]) {
                case "message":
                    if (mentionsTextChannel()){
                        textChannel = e.getMessage().getMentionedChannels().get(0);
                    } else {
                        textChannel = e.getTextChannel();
                    }
                    getMessage(args[2]);
                    break;
                case "text":
                    createPersonalEmbed();
                    break;
                default:
                    e.getMessageChannel().sendMessage(commandEnum.getFullHelpItem("quote").setDescription("Error: second input should  either be `text` or `message`.").build()).queue();
            }
        } else {
            e.getMessageChannel().sendMessage(commandEnum.getFullHelpItem("quote").setDescription("Error: requires at least 3 arguments.").build()).queue();
        }
    }

    public void createEmbed(Message message) {
        EmbedBuilder eb = getEmbed();

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
        return allArgs[allArgs.length - 1].equalsIgnoreCase("true");
    }

    public boolean mentionsTextChannel() {
        return e.getMessage().getMentionedChannels().size() > 0;
    }

    public String getMessageToQuote() {
        StringBuilder messageToQuote = new StringBuilder();

        if (isSunTzu()) {
            for (int i = 2; i < allArgs.length - 1; i++) {
                if (messageToQuote.toString().equals("")) {
                    messageToQuote.append(allArgs[i]);
                } else {
                    messageToQuote.append(" ").append(allArgs[i]);
                }
            }
        } else {
            for (int i = 2; i < allArgs.length; i++) {
                if (messageToQuote.toString().equals("")) {
                    messageToQuote.append(allArgs[i]);
                } else {
                    messageToQuote.append(" ").append(allArgs[i]);
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
