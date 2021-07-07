package uk.co.notnull.BlockRateLimit.Listeners;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import uk.co.notnull.BlockRateLimit.BlockRateLimit;
import uk.co.notnull.BlockRateLimit.RateLimitManager;

public class Blocks implements Listener {
    private final RateLimitManager rateLimitManager;

    public Blocks(BlockRateLimit plugin) {
        rateLimitManager = plugin.getRateLimitManager();
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();

        if(!rateLimitManager.canBreak(player)) {
            player.sendMessage(ChatColor.RED + " Sorry, you must wait " + rateLimitManager.getBreakTime(player) + " seconds to break another block");
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onBlockBroken(BlockBreakEvent event) {
        Player player = event.getPlayer();

        rateLimitManager.updateLastBroken(player);
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onBlockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();

        if(!rateLimitManager.canPlace(player)) {
            player.sendMessage(ChatColor.RED + " Sorry, you must wait " + rateLimitManager.getPlaceTime(player) + " seconds to place another block");
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onBlockPlaced(BlockPlaceEvent event) {
        Player player = event.getPlayer();

        rateLimitManager.updateLastPlaced(player);
    }
}
