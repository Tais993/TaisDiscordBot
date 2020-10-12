package commands.general;

import commands.CommandEnum;
import commands.CommandReceivedEvent;
import commands.ICommand;
import database.remindme.DatabaseRemindMe;
import database.remindme.RemindMeDB;
import database.remindme.UserRemindMeDB;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class RemindMe implements ICommand {
    CommandReceivedEvent e;
    CommandEnum commandEnum = new CommandEnum();
    DatabaseRemindMe databaseRemindMe = new DatabaseRemindMe();

    int totalSeconds;

    String command = "remindme";
    String commandAlias = "remindme";
    String category = "general";
    String exampleCommand = "`!remindme <time>s/m/h/d <content to remind you with>`";
    String shortCommandDescription = "Remind you with something you gave the bot.";
    String fullCommandDescription = "Remind you with something you gave the bot after a specified amount of time.";

    @Override
    public void command(CommandReceivedEvent event) {
        e = event;
        String[] args = e.getArgs();

        String time = args[2];

        String userId = e.getAuthor().getId();


        if (isMinute(time)) {
            minutesToSeconds(Integer.parseInt(time));
        } else if (isHour(time)) {
            minutesToSeconds(Integer.parseInt(time));
        } else if (isDay(time)) {
            minutesToSeconds(Integer.parseInt(time));
        } else if (isSecond(time) || isNumber(time)) {
            totalSeconds = Integer.parseInt(time.replace("s", ""));
        } else {
            e.getMessageChannel().sendMessage(commandEnum.getFullHelpItem("remindme").build()).queue();
            return;
        }

        if (!databaseRemindMe.userRemindMeExistsInDB(userId)) {
            UserRemindMeDB userRemindMeDB = new UserRemindMeDB(userId);
            RemindMeDB remindMeDB = new RemindMeDB("1", args[1], totalSeconds);
            userRemindMeDB.addToArrayList(remindMeDB);
            databaseRemindMe.addUserRemindMeToDB(userRemindMeDB);
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

    public void minutesToSeconds(int timeGiven) {
        int output = timeGiven;
        output *= 60;
        totalSeconds = output;
    }

    public void hoursToSeconds(int timeGiven) {
        int output = timeGiven;
        output *= 60;
        output *= 60;
        totalSeconds = output;
    }

    public void daysToSeconds(int timeGiven) {
        int output = timeGiven;
        output *= 24;
        output *= 60;
        output *= 60;
        totalSeconds = output;
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
