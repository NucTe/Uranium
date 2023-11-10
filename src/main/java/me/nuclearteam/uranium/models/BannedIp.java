package me.nuclearteam.uranium.models;

import java.util.UUID;

public class BannedIp {

    private final UUID source;
    private final String ip;
    private final String createdDate;
    private final String expirationDate;
    private final String reason;

    public BannedIp(UUID source, String ip, String createdDate, String expirationDate, String reason) {
        this.source = source;
        this.ip = ip;
        this.createdDate = createdDate;
        this.expirationDate = expirationDate;
        this.reason = reason;
    }

    public UUID getSource() {
        return source;
    }

    public String getIp() {
        return ip;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public String getReason() {
        return reason;
    }
}
