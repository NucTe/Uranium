package me.nuclearteam.uranium.models;

import java.util.UUID;

public class CachePlayer {

    private final String name;
    private final UUID uuid;
    private final String expiresOn;

    public CachePlayer(String name, UUID uuid, String expiresOn) {
        this.uuid = uuid;
        this.expiresOn = expiresOn;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getExpiresOn() {
        return expiresOn;
    }
}
