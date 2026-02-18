package me.yusufali.onehiteverything;

import me.yusufali.onehiteverything.commands.OneHitCommand;
import me.yusufali.onehiteverything.listeners.HitListener;
import me.yusufali.onehiteverything.listeners.PlayerListener;
import me.yusufali.onehiteverything.managers.ChallengeManager;
import me.yusufali.onehiteverything.managers.ConfigManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    private ConfigManager configManager;
    private ChallengeManager challengeManager;

    @Override
    public void onEnable() {
        // Plugin startup logic
        saveDefaultConfig();

        this.configManager = new ConfigManager(this);
        this.challengeManager = new ChallengeManager(this);

        OneHitCommand cmd = new OneHitCommand(this, challengeManager, configManager);

        getServer().getPluginManager().registerEvents(new PlayerListener(this, configManager), this);
        getServer().getPluginManager().registerEvents(new HitListener(this, configManager), this);

        getCommand("onehit").setExecutor(cmd);
        getCommand("onehit").setTabCompleter(cmd);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        challengeManager.disableChallenge();

    }
}
