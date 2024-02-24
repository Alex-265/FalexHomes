package at.alex.falexhomes.utils;

import org.apache.commons.lang3.NotImplementedException;
import org.bukkit.Location;
import org.bukkit.event.player.PlayerMoveEvent;

public class MoveUtils {
    public static boolean hasPlayerMoved(PlayerMoveEvent e) {
        Location from = e.getFrom();
        Location to = e.getTo();
        return from.getX() != to.getX() || from.getZ() != to.getZ();
    }
}
