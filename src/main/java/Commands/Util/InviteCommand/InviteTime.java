package Commands.Util.InviteCommand;

import Commands.CommandEnum;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class InviteTime {
    GuildMessageReceivedEvent e;

    public InviteTime(GuildMessageReceivedEvent event, String args) {
        e = event;
        CommandEnum commandEnum = new CommandEnum();

        args = args.toLowerCase();
        if (isSecond(args)) {
            checkSeconds(args);
        } else if (isMinute(args)) {
            checkMinute(args);
        } else if (isHour(args)) {
            checkHour(args);
        } else if (isDay(args)) {
            checkDay(args);
        } else if (isNumber(args)) {
            checkSeconds(args);
        } else {
            e.getChannel().sendMessage(commandEnum.getFullHelpItem("invite").build()).queue();
        }
    }

    public int minutesToSeconds(int timeGiven) {
        int output = timeGiven;
        output = output*60;
        return output;
    }

    public int hoursToSeconds(int timeGiven) {
        int output = timeGiven;
        output = output*60;
        output = output*60;
        return output;
    }

    public int daysToSeconds(int timeGiven) {
        int output = timeGiven;
        output = output*24;
        output = output*60;
        output = output*60;
        return output;
    }

    public String removeTimeNote(String timeGiven) {
        timeGiven = timeGiven.replaceAll("s", "").replaceAll("m", "").replaceAll("h", "").replaceAll("d", "");
        return timeGiven;
    }

    public void checkSeconds(String args) {
        String invite = "";

        if (isNumber(removeTimeNote(args))){
            int timeGiven = Integer.parseInt(removeTimeNote(args));
            if (timeGiven > 86400) {
                invite = e.getChannel().createInvite().complete().getUrl();
                e.getChannel().sendMessage("A permanent invite has been created! \n" + invite).queue();
            } else {
                invite = e.getChannel().createInvite().setTemporary(true).setMaxAge(timeGiven).complete().getUrl();
                e.getChannel().sendMessage("A temporary invite has been created for " + timeGiven + " seconds ! \n" + invite).queue();
            }
        }
    }

    public void checkMinute(String args) {
        String invite = "";

        if (isNumber(removeTimeNote(args))) {
            int timeGiven = Integer.parseInt(removeTimeNote(args));
            if (timeGiven > 1440) {
                invite = e.getChannel().createInvite().complete().getUrl();
                e.getChannel().sendMessage("A permanent invite has been created! \n" + invite).queue();
            } else {
                invite = e.getChannel().createInvite().setTemporary(true).setMaxAge(minutesToSeconds(timeGiven)).complete().getUrl();
                e.getChannel().sendMessage("A temporary invite has been created for " + timeGiven + " minutes ! \n" + invite).queue();
            }
        }
    }

    public void checkHour(String args) {
        String invite = "";

        if (isNumber(removeTimeNote(args))) {
            int timeGiven = Integer.parseInt(removeTimeNote(args));
            if (timeGiven > 24) {
                invite = e.getChannel().createInvite().complete().getUrl();
                e.getChannel().sendMessage("A permanent invite has been created! \n" + invite).queue();
            } else {
                invite = e.getChannel().createInvite().setTemporary(true).setMaxAge(hoursToSeconds(timeGiven)).complete().getUrl();
                e.getChannel().sendMessage("A temporary invite has been created for " + timeGiven + " hours ! \n" + invite).queue();
            }
        }
    }

    public void checkDay(String args) {
        String invite = "";

        if (isNumber(removeTimeNote(args))) {
            int timeGiven = Integer.parseInt(removeTimeNote(args));
            if (timeGiven > 1) {
                invite = e.getChannel().createInvite().complete().getUrl();
                e.getChannel().sendMessage("A permanent invite has been created! \n" + invite).queue();
            } else {
                invite = e.getChannel().createInvite().setTemporary(true).setMaxAge(daysToSeconds(timeGiven)).complete().getUrl();
                e.getChannel().sendMessage("A temporary invite has been created for " + timeGiven + " days ! \n" + invite).queue();
            }
        }
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
