package commands.general;

import commands.CommandEnum;
import commands.ICommand;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class RemindMe implements ICommand {
    GuildMessageReceivedEvent e;
    CommandEnum commandEnum = new CommandEnum();

    String command = "remindme";
    String commandAlias = "remindme";
    String category = "general";
    String exampleCommand = "`!remindme <time>s/m/h/d <content to remind you with>`";
    String shortCommandDescription = "Remind you with something you gave the bot.";
    String fullCommandDescription = "Remind you with something you gave the bot after a specified amount of time.";
    @Override
    public void command(GuildMessageReceivedEvent event, String[] args) {
        e = event;

        System.currentTimeMillis();

        String time = args[2];

        if (isSecond(time)) {

        } else if (isMinute(time)) {

        } else if (isHour(time)) {

        } else if (isDay(time)) {

        } else if (isNumber(time)) {

        } else {
            e.getChannel().sendMessage(commandEnum.getFullHelpItem("invite").build()).queue();
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

    public boolean isNumber(String args) {
        return args.matches("\\d+");
    }

    public boolean isSecond(String args) {
        return args.contains("s");
    }

    public boolean isMinute(String args) {
        return args.contains("m");
    }

    public boolean isHour(String args) {
        return args.contains("h");
    }

    public boolean isDay(String args) {
        return args.contains("d");
    }
}
