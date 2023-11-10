package me.nuclearteam.uranium.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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
        if (bannedPlayersFile.exists()) {
            try (Reader reader = new FileReader(bannedPlayersFile)) {
                this.uranium235.bannedPlayers.clear();
                this.uranium235.bannedPlayers.addAll(Arrays.asList(gson.fromJson(reader, BannedPlayer[].class)));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (bannedIpsFile.exists()) {
            try (Reader reader = new FileReader(bannedIpsFile)) {
                this.uranium235.bannedIps.clear();
                this.uranium235.bannedIps.addAll(Arrays.asList(gson.fromJson(reader, BannedIp[].class)));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (playersFile.exists()) {
            try (Reader reader = new FileReader(playersFile)) {
                this.uranium235.cachedPlayers.clear();
                this.uranium235.cachedPlayers.addAll(Arrays.asList(gson.fromJson(reader, CachePlayer[].class)));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public File getPlayersFile() {
        return playersFile;
    }

    public void SaveCache() {
//        this.uranium235.getProxy().getScheduler().runAsync(this.uranium235,
//                () -> {
                        System.out.println("no... That would be YOUR MOTHER!");
                    try (Writer writer = new FileWriter(playersFile)) {
                        this.uranium235.getLogger().info("bruh: " + gson.toJson(this.uranium235.cachedPlayers.toArray()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
//                });
        this.uranium235.getProxy().getScheduler().runAsync(this.uranium235,
                () -> {
                    try (Writer writer = new FileWriter(this.bannedPlayersFile)) {
                        prettyGson.toJson(this.uranium235.bannedPlayers.toArray(), writer);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                });
        this.uranium235.getProxy().getScheduler().runAsync(this.uranium235,
                () -> {
                    try (Writer writer = new FileWriter(this.bannedIpsFile)) {
                        prettyGson.toJson(this.uranium235.bannedIps.toArray(), writer);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                });
    }

}
