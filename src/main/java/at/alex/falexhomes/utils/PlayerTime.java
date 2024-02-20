package at.alex.falexhomes.utils;


public class PlayerTime {
    private String playerUUID;
    private int gameTime;

    public PlayerTime(String playerName, int gameTime) {
        this.playerUUID = playerName;
        this.gameTime = gameTime;
    }

    public String getPlayerUUID() {
        return playerUUID;
    }

    public int getTime() {
        return gameTime;
    }

    public void setPlayerUUID(String playerName) {
        this.playerUUID = playerName;
    }

    public void setTime(int gameTime) {
        this.gameTime = gameTime;
    }
}
