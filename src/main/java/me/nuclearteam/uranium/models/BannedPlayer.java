package me.nuclearteam.uranium.models;

import java.util.UUID;

public class BannedPlayer {

    private final UUID uuid;
    private final UUID source;
    private final String reason;
    private final String createdDate;
    private final String expirationDate;

    public BannedPlayer(UUID uuid, UUID source, String reason, String createdDate, String expirationDate) {
        this.uuid = uuid;
        this.source = source;
        this.reason = reason;
        this.createdDate = createdDate;
        this.expirationDate = expirationDate;
    }

    public UUID getUuid() {
        return uuid;
    }

    public UUID getSource() {
        return source;
    }

    public String getReason() {
        return reason;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public String getExpirationDate() {
        return expirationDate;
    }
}
