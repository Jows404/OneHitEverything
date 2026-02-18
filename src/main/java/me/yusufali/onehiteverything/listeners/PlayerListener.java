package me.yusufali.onehiteverything.listeners;

import me.yusufali.onehiteverything.Main;
import me.yusufali.onehiteverything.managers.ConfigManager;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerListener implements Listener {

    private Main plugin;

    private ConfigManager configManager;

    public PlayerListener(Main plugin, ConfigManager configManager) {
        this.plugin = plugin;
        this.configManager = configManager;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();

        if (!configManager.isEnabled()) {
            if (player.getAttribute(Attribute.MAX_HEALTH).getBaseValue() != 20.0) {
                player.getAttribute(Attribute.MAX_HEALTH).setBaseValue(20.0);
                player.setHealth(20.0);
            }
            return;
        }

        player.getAttribute(Attribute.MAX_HEALTH).setBaseValue(2.0);
        player.setHealth(2.0);
    }
}
