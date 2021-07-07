package uk.co.notnull.BlockRateLimit;

import org.bukkit.entity.Player;

import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class RateLimitManager {
    private final Map<UUID, Instant> lastPlace = new HashMap<>();
    private final Map<UUID, Instant> lastBreak = new HashMap<>();
    private int time = 60000;
    private boolean enabled = false;

    public boolean canBreak(Player player) {
        UUID uuid = player.getUniqueId();

        if(!enabled) {
            return true;
        }

        if(player.hasPermission("blockratelimit.bypass") || player.hasPermission("blockratelimit.admin")) {
            return true;
        }

        if(!lastBreak.containsKey(uuid)) {
            return true;
        }

        return Duration.between(lastBreak.get(uuid), Instant.now()).toMillis() >= time;
    }

    public boolean canPlace(Player player) {
        UUID uuid = player.getUniqueId();

        if(!enabled) {
            return true;
        }

        if(player.hasPermission("blockratelimit.bypass") || player.hasPermission("blockratelimit.admin")) {
            return true;
        }

        if(!lastPlace.containsKey(uuid)) {
            return true;
        }

        return Duration.between(lastPlace.get(uuid), Instant.now()).toMillis() >= time;
    }

    public long getBreakTime(Player player) {
        UUID uuid = player.getUniqueId();

        if(!lastBreak.containsKey(uuid)) {
            return 0;
        }

        return (time - Duration.between(lastBreak.get(uuid), Instant.now()).toMillis()) / 1000;
    }

    public long getPlaceTime(Player player) {
        UUID uuid = player.getUniqueId();

        if(!lastPlace.containsKey(uuid)) {
            return 0;
        }

        return (time - Duration.between(lastPlace.get(uuid), Instant.now()).toMillis()) / 1000;
    }

    public void updateLastBroken(Player player) {
        lastBreak.put(player.getUniqueId(), Instant.now());
    }

    public void updateLastPlaced(Player player) {
        lastPlace.put(player.getUniqueId(), Instant.now());
    }

    public boolean setTime(int newTime) {
        if(newTime <= 0) {
            return false;
        }

        time = newTime * 1000;

        return true;
    }

    public void disable() {
        enabled = false;
    }

    public void enable() {
        enabled = true;
    }

    public boolean isEnabled() {
        return enabled;
    }
}
