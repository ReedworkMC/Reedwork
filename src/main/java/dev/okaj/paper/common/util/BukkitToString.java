package dev.okaj.paper.common.util;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public final class BukkitToString {

    public static String formatLocation(Location location) {
        return location.getWorld().getName()
                + " "
                + location.getBlockX()
                + " "
                + location.getBlockY()
                + " "
                + location.getBlockZ();
    }

    public static String formatLocationDetailed(Location location) {
        return location.getWorld().getName()
                + " "
                + location.getX()
                + " "
                + location.getY()
                + " "
                + location.getZ()
                + " yaw:"
                + location.getYaw()
                + " pitch:"
                + location.getPitch();
    }

    public static String formatWorld(World world) {
        return world.getName();
    }

    public static String formatVector(Vector vector) {
        return vector.getX()
                + " "
                + vector.getY()
                + " "
                + vector.getZ();
    }

    public static String formatBlock(Location location) {
        return location.getBlock().getType().name()
                + " at "
                + formatLocation(location);
    }

    public static String formatMaterial(Material material) {
        return material.name().toLowerCase();
    }

    public static String formatItem(ItemStack item) {
        if (item == null || item.getType().isAir()) {
            return "air";
        }

        return item.getAmount()
                + "x "
                + formatMaterial(item.getType());
    }

    public static String formatEntity(Entity entity) {
        return entity.getType().name().toLowerCase()
                + " at "
                + formatLocation(entity.getLocation());
    }

    public static String formatPlayer(Player player) {
        return player.getName()
                + " at "
                + formatLocation(player.getLocation());
    }

    public static String formatRotation(Location location) {
        return "yaw:"
                + location.getYaw()
                + " pitch:"
                + location.getPitch();
    }
}
