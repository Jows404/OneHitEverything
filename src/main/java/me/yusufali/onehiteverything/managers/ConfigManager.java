package me.yusufali.onehiteverything.managers;

import me.yusufali.onehiteverything.Main;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;
import java.util.List;

public class ConfigManager {

    private Main plugin;
    private FileConfiguration config;
    private File configFile;

    public ConfigManager(Main plugin) {
        this.plugin = plugin;
        this.configFile = new File(plugin.getDataFolder(), "config.yml");

        if (!configFile.exists()) {
            plugin.saveDefaultConfig();
        }

        this.config = plugin.getConfig();

        config.options().setHeader(List.of(
                "       [OneHitEverything] Configuration    ",
                "============================================",
                "Plugin by: Jows404",
                "Version: 1.0",
                "Social Media: https://www.youtube.com/@Jows404",
                "Discord: https://discord.gg/tymMfd2KC9"
        ));

        config.setComments("enabled", List.of(
                "============================================",
                "                  SETTINGS                  ",
                "============================================"
        ));

        config.setComments("messages", List.of(
                "============================================",
                "          MESSAGE SETTINGS                 ",
                "============================================",
                "Messages"
        ));

        config.setComments("advanced", List.of(
                "============================================",
                "          ADVANCED SETTINGS                ",
                "============================================"
        ));

        config.setComments("advanced.metrics", List.of(
                "Send anonymous usage statistics",
                "Helps improve the plugin",
                "Default: true"
        ));

        save();

    }

    public boolean isEnabled() {
        return config.getBoolean("enabled");
    }

    public void setEnabled(Boolean state) {
        config.set("enabled", state);
    }

    public String getMessage(String path) {
        // Looks in the config.yml for the value at messages.prefix
        String prefix = config.getString("messages.prefix", "&8[&6OneHitEverything&8]&r");

        String message = config.getString("messages." + path, "");

        if (message.contains("{prefix}")) {
            message = message.replace("{prefix}", prefix);
        }

        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public void save() {
        plugin.saveConfig();
    }

    public void reloadConfig() {
        plugin.reloadConfig();
        plugin.getLogger().info("Configuration reloaded!");
    }


}
