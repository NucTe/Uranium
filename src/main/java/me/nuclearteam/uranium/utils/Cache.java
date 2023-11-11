package me.nuclearteam.uranium.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import me.nuclearteam.nuclearlib.Bungee.FileWriter;
import me.nuclearteam.uranium.Uranium;
import me.nuclearteam.uranium.models.BannedIp;
import me.nuclearteam.uranium.models.BannedPlayer;
import me.nuclearteam.uranium.models.CachePlayer;

import java.io.*;
import java.util.Arrays;

public class Cache {

    private File playersFile;
    private File bannedPlayersFile;
    private File bannedIpsFile;
    private Uranium uranium235;

    private final Gson gson = new Gson();
    private final Gson prettyGson = new GsonBuilder().setPrettyPrinting().create();

    public Cache(Uranium myUsualDailyPortionOfRadiation) {
        this.playersFile = new File(myUsualDailyPortionOfRadiation.getDataFolder(), "players.json");
        this.bannedPlayersFile = new File(myUsualDailyPortionOfRadiation.getDataFolder(), "banned-players.json");
        this.bannedIpsFile = new File(myUsualDailyPortionOfRadiation.getDataFolder(), "banned-ips.json");
        this.uranium235 = myUsualDailyPortionOfRadiation;
    }

    public void LoadCache() {
        if (bannedPlayersFile.exists() && bannedPlayersFile.length() > 0) {
            try (Reader reader = new FileReader(bannedPlayersFile)) {
                this.uranium235.bannedPlayers.clear();
                this.uranium235.bannedPlayers.addAll(Arrays.asList(gson.fromJson(reader, BannedPlayer[].class)));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (bannedIpsFile.exists() && bannedIpsFile.length() > 0) {
            try (Reader reader = new FileReader(bannedIpsFile)) {
                this.uranium235.bannedIps.clear();
                this.uranium235.bannedIps.addAll(Arrays.asList(gson.fromJson(reader, BannedIp[].class)));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (playersFile.exists() && playersFile.length() > 0) {
            try (Reader reader = new FileReader(playersFile)) {
                this.uranium235.cachedPlayers.clear();
                this.uranium235.cachedPlayers.addAll(Arrays.asList(gson.fromJson(reader, CachePlayer[].class)));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public File getPlayersFile() {
        return playersFile;
    }

    // Used generally to clear cache before saving it
    public void ClearCache() {
        try {
            PrintWriter printWriter = new PrintWriter(this.uranium235.getDataFolder() + "/banned-players.json");
            printWriter.print("");
            printWriter.close();
        } catch (FileNotFoundException ignored) {

        }
        try {
            PrintWriter printWriter = new PrintWriter(this.uranium235.getDataFolder() + "/banned-ips.json");
            printWriter.print("");
            printWriter.close();
        } catch (FileNotFoundException ignored) {

        }
        try {
            PrintWriter printWriter = new PrintWriter(this.uranium235.getDataFolder() + "/players.json");
            printWriter.print("");
            printWriter.close();
        } catch (FileNotFoundException ignored) {

        }
    }

    public void SaveCache() {
        ClearCache();
        FileWriter writer = new FileWriter(this.uranium235);
        writer.writeToFileAsync("players.json", gson.toJson(this.uranium235.cachedPlayers.toArray()));
        writer.writeToFileAsync("banned-players.json", gson.toJson(this.uranium235.bannedPlayers.toArray()));
        writer.writeToFileAsync("banned-ips.json", gson.toJson(this.uranium235.bannedIps.toArray()));
    }

}
