package uk.co.notnull.BlockRateLimit;

import co.aikar.commands.PaperCommandManager;
import org.bukkit.plugin.java.JavaPlugin;
import uk.co.notnull.BlockRateLimit.Commands.BlockRateLimitCommand;
import uk.co.notnull.BlockRateLimit.Listeners.Blocks;

public class BlockRateLimit extends JavaPlugin {
    private RateLimitManager rateLimitManager;

    @Override
    public void onEnable() {
        rateLimitManager = new RateLimitManager();
        getServer().getPluginManager().registerEvents(new Blocks(this), this);

        registerCommands();
    }

    @Override
    public void onDisable() {
    }

    public RateLimitManager getRateLimitManager() {
        return rateLimitManager;
    }

    private void registerCommands() {
        PaperCommandManager commandManager = new PaperCommandManager(this);
        commandManager.registerCommand(new BlockRateLimitCommand());
    }
}