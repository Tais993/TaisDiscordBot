package commands;

import commands.amongus.SetAmongUsRole;
import commands.amongus.StartGame;
import commands.bot.*;
import commands.fun.*;
import commands.general.*;
import commands.music.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class CommandMap {
    static HashMap<String, ICommand> commands = new HashMap<>(Map.ofEntries(
            Map.entry("setamongusrole", new SetAmongUsRole()),
            Map.entry("startgame", new StartGame()),
            Map.entry("botstats", new BotStats()),
            Map.entry("dm", new Dm()),
            Map.entry("removejoke", new RemoveJoke()),
            Map.entry("say", new Say()),
            Map.entry("setblacklisted", new SetBlacklisted()),
            Map.entry("setbotmoderator", new SetBotModerator()),
            Map.entry("shutdown", new Shutdown()),
            Map.entry("addjoke", new AddJoke()),
            Map.entry("calculator", new Calculator()),
            Map.entry("hug", new Hug()),
            Map.entry("joke", new Joke()),
            Map.entry("mock", new Mock()),
            Map.entry("ping", new Ping()),
            Map.entry("addhug", new AddHug()),
            Map.entry("avatar", new Avatar()),
            Map.entry("leaderboard", new Leaderboard()),
            Map.entry("level", new Level()),
            Map.entry("quote", new Quote()),
            Map.entry("remindme", new RemindMe()),
            Map.entry("rep", new Rep()),
            Map.entry("reps", new Reps()),
            Map.entry("test", new Test()),
            Map.entry("youtube", new YouTube()),
            Map.entry("backward", new Backward()),
            Map.entry("clearqueue", new ClearQueue()),
            Map.entry("clearuser", new ClearUser()),
            Map.entry("forward", new Forward()),
            Map.entry("join", new Join()),
            Map.entry("leave", new Leave()),
            Map.entry("loop", new Loop()),
            Map.entry("lyrics", new Lyrics()),
            Map.entry("move", new Move()),
            Map.entry("nowplaying", new NowPlaying()),
            Map.entry("pause", new Pause()),
            Map.entry("play", new Play()),
            Map.entry("playlist", new Playlist()),
            Map.entry("playnow", new PlayNow()),
            Map.entry("playplaylist", new PlayPlaylist()),
            Map.entry("playtop", new PlayTop()),
            Map.entry("previous", new Previous()),
            Map.entry("queue", new Queue()),
            Map.entry("radio538", new Radio538()),
            Map.entry("remove", new Remove()),
            Map.entry("replay", new Replay()),
            Map.entry("resume", new Resume()),
            Map.entry("rickroll", new RickRoll()),
            Map.entry("seek", new Seek()),
            Map.entry("setvolume", new SetVolume()),
            Map.entry("shuffle", new Shuffle()),
            Map.entry("skip", new Skip()),
            Map.entry("skipto", new SkipTo()),
            Map.entry("skunk", new Skunk())
    ));

//    private HashMap<String, ICommand> test = new HashMap<String, ICommand>(Map.of(
//            "setamongusrole", new SetAmongUsRole(),
//            "startgame", new StartGame(),
//            "botstats", new BotStats(),
//            "dm", new Dm(),
//            "removejoke", new RemoveJoke(),
//            "say", new Say(),
//            "setblacklisted", new SetBlacklisted(),
//            "setbotmoderator", new SetBotModerator(),
//            "shutdown", new Shutdown(),
//            "addjoke", new AddJoke(),
//            "calculator", new Calculator(),
//            "hug", new Hug(),
//            "joke", new Joke(),
//            "mock", new Mock(),
//            "ping", new Ping(),
//            "addhug", new AddHug(),
//            "avatar", new Avatar(),
//            "leaderboard", new Leaderboard(),
//            "level", new Level(),
//            "quote", new Quote(),
//            "remindme", new RemindMe(),
//            "rep", new Rep(),
//            "reps", new Reps(),
//            "test", new Test(),
//            "youtube", new YouTube(),
//            "backward", new Backward(),
//            "clearqueue", new ClearQueue(),
//            "clearuser", new ClearUser(),
//            "forward", new Forward(),
//            "join", new Join(),
//            "leave", new Leave(),
//            "loop", new Loop(),
//            "lyrics", new Lyrics(),
//            "move", new Move(),
//            "nowplaying", new NowPlaying(),
//            "pause", new Pause(),
//            "play", new Play(),
//            "playlist", new Playlist(),
//            "playnow", new PlayNow(),
//            "playplaylist", new PlayPlaylist(),
//            "playtop", new PlayTop(),
//            "previous", new Previous(),
//            "queue", new Queue(),
//            "radio538", new Radio538(),
//            "remove", new Remove(),
//            "replay", new Replay(),
//            "resume", new Resume(),
//            "rickroll", new RickRoll(),
//            "seek", new Seek(),
//            "setvolume", new SetVolume(),
//            "shuffle", new Shuffle(),
//            "skip", new Skip(),
//            "skipto", new SkipTo(),
//            "skunk", new Skunk()
//            ));

    static ArrayList<String> categories = new ArrayList<>(List.of("General", "Fun", "Bot", "Music", "Util"));

    static HashMap<String, ICommand> commandAliases = new HashMap<>();

    private final static Stream<ICommand> generalCommands = commands.values().stream().filter(iCommand -> iCommand.getCategory().equalsIgnoreCase("general"));

    public boolean isCategory(String categoryGiven) {
        return categories.stream().anyMatch(category -> category.equalsIgnoreCase(categoryGiven));
    }

    public boolean isCommand(String commandGiven) {
        return commands.entrySet().stream().anyMatch(entry -> entry.getValue().getCommandAliases().stream().anyMatch(command -> command.equalsIgnoreCase(commandGiven)));
    }

    public void prepareAliases() {
        commands.values().forEach(iCommand -> {
            iCommand.getCommandAliases().stream().skip(0).forEach(s -> {
                commandAliases.put(s, iCommand);
            });
        });
    }

    public boolean runCommand(CommandReceivedEvent e) {
        ICommand command = (commands.get(e.getCommand()) == null) ? commandAliases.get(e.getCommand()) : commands.get(e.getCommand());

        if (command != null) {
            Thread thread = new Thread(() -> command.command(e));
            thread.start();
            return true;
        }
        return false;
    }
}
