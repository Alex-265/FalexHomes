package at.alex.falexhomes.utils;

import org.bukkit.Location;
import org.bukkit.event.player.PlayerMoveEvent;

public class MoveUtils {
    public static boolean hasPlayerMoved(PlayerMoveEvent e) {
        Location from = e.getFrom();
        Location to = e.getTo();
        return from.getX() != to.getX() || from.getY() != to.getY() || from.getZ() != to.getZ();
    }
}
