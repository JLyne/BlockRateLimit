package uk.co.notnull.BlockRateLimit.Commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import uk.co.notnull.BlockRateLimit.BlockRateLimit;

@CommandAlias("blockratelimit|brl|blockrl")
@CommandPermission("blockratelimit.admin")
public class BlockRateLimitCommand extends BaseCommand {
    @Dependency
    private BlockRateLimit plugin;

    @Subcommand("enable")
    @Description("Enables rate limiting")
    public void onEnable(CommandSender sender) {
        plugin.getRateLimitManager().enable();
        sender.sendMessage(ChatColor.GREEN + "Rate limiting enabled");
    }

    @Subcommand("disable")
    @Description("Disables rate limiting")
    public void onDisable(CommandSender sender) {
        plugin.getRateLimitManager().disable();
        sender.sendMessage(ChatColor.GREEN + "Rate limiting disabled");
    }

    @Subcommand("settime")
    @Description("Set the time period per block action, in seconds")
    public void onSetTime(CommandSender sender, int time) {
        if(!plugin.getRateLimitManager().setTime(time)) {
            sender.sendMessage(ChatColor.RED + "Invalid time. Please enter a positive number of seconds");
        } else {
            sender.sendMessage(ChatColor.GREEN + "Time period set to " + time + " seconds");
        }
    }

}

