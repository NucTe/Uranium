package me.nuclearteam.uranium;

import me.nuclearteam.uranium.commands.BanCmd;
import me.nuclearteam.uranium.listeners.UserListener;
import me.nuclearteam.uranium.models.BannedIp;
import me.nuclearteam.uranium.models.BannedPlayer;
import me.nuclearteam.uranium.models.CachePlayer;
import me.nuclearteam.uranium.utils.Cache;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.PluginManager;

import java.util.ArrayList;
import java.util.List;

public final class Uranium extends Plugin {

    public final List<BannedPlayer> bannedPlayers = new ArrayList<>();
    public final List<BannedIp> bannedIps = new ArrayList<>();
    public final List<CachePlayer> cachedPlayers = new ArrayList<>();

    public Cache cache;

    public Cache getCache() {
        return cache;
    }

    @Override
    public void onEnable() {
        this.cache = new Cache(this);
        this.cache.LoadCache();

        PluginManager pm = getProxy().getPluginManager();
        pm.registerListener(this, new UserListener(this));
        pm.registerCommand(this, new BanCmd(this));

        getLogger().info("Uranium is radioactive! Beware the banned ones");
    }

    @Override
    public void onDisable() {
        this.cache.SaveCache();
    }
}
