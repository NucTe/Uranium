package me.nuclearteam.uranium.listeners;

import me.nuclearteam.uranium.Uranium;
import me.nuclearteam.uranium.models.CachePlayer;
import me.nuclearteam.uranium.utils.DateUtils;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.event.LoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.net.InetSocketAddress;
import java.util.UUID;

public class UserListener implements Listener {

    private final Uranium uranium;

    public UserListener(Uranium uranium) {
        this.uranium = uranium;
    }

    @EventHandler
    public void OnJoin(LoginEvent event) {
        String name = event.getConnection().getName();
        UUID uuid = event.getConnection().getUniqueId();
        String ip = event.getConnection().getAddress().getAddress().getHostAddress();

        this.uranium.bannedPlayers.stream().filter(p -> p.getUuid().equals(uuid)).findAny().ifPresent(p -> {
            event.setCancelled(true);
            event.setCancelReason(new TextComponent("You are banned from this server.\nReason: " + p.getReason()));
        });

        this.uranium.bannedIps.stream().filter(p -> p.getIp().equals(ip)).findAny().ifPresent(p -> {
            event.setCancelled(true);
            event.setCancelReason(new TextComponent("Your IP address is banned from this server.\nReason: " + p.getReason()));
        });

        this.uranium.cachedPlayers.removeIf(p -> DateUtils.hasExpired(p.getExpiresOn()));

        if (!event.isCancelled()) {
            this.uranium.cachedPlayers.removeIf(p -> p.getUuid().equals(uuid));
            this.uranium.cachedPlayers.add(new CachePlayer(name, uuid, DateUtils.monthLater()));
        }

        this.uranium.getCache().SaveCache();
    }

}