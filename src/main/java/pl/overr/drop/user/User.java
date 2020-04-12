package pl.overr.drop.user;

import java.util.Map;
import java.util.UUID;

public class User {
    private final UUID playerUUID;

    public UUID getPlayerUUID() {
        return playerUUID;
    }

    public Map<String, Boolean> getActiveDrop() {
        return activeDrop;
    }

    public int getMinedStone() {
        return minedStone;
    }

    public boolean isActiveMessage() {
        return activeMessage;
    }

    public void setActiveMessage(boolean activeMessage) {
        this.activeMessage = activeMessage;
    }

    private boolean activeMessage;
    private final Map<String,Boolean> activeDrop;

    public void setMinedStone(int minedStone) {
        this.minedStone = minedStone;
    }

    private int minedStone;

    public User(UUID playerUUID, boolean activeMessage, Map<String, Boolean> activeDrop, int minedStone) {
        this.playerUUID = playerUUID;
        this.activeMessage = activeMessage;
        this.activeDrop = activeDrop;
        this.minedStone = minedStone;
    }
}
