package database.user;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class UserDB {
    String userID;
    int level = 1;
    int xp = 0;
    int xpForLevelUp = 100;

    long lastTimeRepGiven;

    boolean isBotModerator = false;
    boolean isBlackListed = false;

    int reps = 0;

    String prefix = "";

    HashMap<String, ArrayList<Song>> playlists = new HashMap<>();

    public UserDB(String userID) {
        this.userID = userID;
    }

    public UserDB(String userID, int level, int xp, int reps, boolean isBotModerator, boolean isBlackListed, String prefix, HashMap<String, ArrayList<Song>> playlists) {
        this.userID = userID;
        this.level = level;
        this.xp = xp;
        this.reps = reps;
        this.isBotModerator = isBotModerator;
        this.isBlackListed = isBlackListed;
        this.prefix = prefix;
        this.playlists = playlists;
    }

    public void addRandomXp() {
        Random random = new Random();
        xp = xp + random.nextInt(20);
    }

    public String getUserID() {
        return userID;
    }

    public int getLevel() {
        return level;
    }

    public int getXp() {
        return xp;
    }

    public int getXpForLevelUp() {
        return xpForLevelUp;
    }

    public void calculateXpForLevelUp() {
        xpForLevelUp = level * 100;
    }

    public boolean calculateLevel() {
        if (xp >= xpForLevelUp) {
            xp = xp - xpForLevelUp;
            level++;
            return true;
        }
        return false;
    }

    public boolean isBotModerator() {
        return isBotModerator;
    }

    public void setBotModerator(boolean botModerator) {
        isBotModerator = botModerator;
    }

    public boolean isBlackListed() {
        return isBlackListed;
    }

    public void setBlackListed(boolean blackListed) {
        isBlackListed = blackListed;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }

    public int getReps() {
        return reps;
    }

    public void addRep() {
        reps++;
    }

    public long getLastTimeRepGiven() {
        return lastTimeRepGiven;
    }

    public void setLastTimeRepGiven() {
        this.lastTimeRepGiven = System.currentTimeMillis();
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public HashMap<String, ArrayList<Song>> getPlaylists() {
        return playlists;
    }

    public ArrayList<Song> getPlaylist(String playlistName) {
        return playlists.get(playlistName);
    }

    public void addPlayList(String playlistName) {
        playlists.put(playlistName, new ArrayList<>());
    }

    public void renamePlaylist(String oldPlaylistName, String newPlaylistName) {
        ArrayList<Song> playlist = playlists.get(oldPlaylistName);
        playlists.remove(oldPlaylistName);
        playlists.put(newPlaylistName, playlist);
    }

    public void removePlaylist(String playlistName) {
        playlists.remove(playlistName);
    }

    public void addSong(String playlistName, AudioTrack track) {
        Song song = new Song(track.getInfo().uri, track.getInfo().author, track.getInfo().title);
        playlists.get(playlistName).add(song);
    }

    public void removeSong(String playlistName, int index) {
        playlists.get(playlistName).remove(index);
    }
}
