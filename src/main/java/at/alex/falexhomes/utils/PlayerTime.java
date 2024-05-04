package at.alex.falexhomes.utils;


public class PlayerTime {
    private String playerUUID;
    private long gameTime;

    public PlayerTime(String playerName, long gameTime) {
        this.playerUUID = playerName;
        this.gameTime = gameTime;
    }

    public String getPlayerUUID() {
        return playerUUID;
    }

    public long getTime() {
        return gameTime;
    }

    public void setPlayerUUID(String playerName) {
        this.playerUUID = playerName;
    }

    public void setTime(long gameTime) {
        this.gameTime = gameTime;
    }
}
