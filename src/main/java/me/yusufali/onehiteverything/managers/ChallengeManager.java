package me.yusufali.onehiteverything.managers;

import me.yusufali.onehiteverything.Main;
import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Player;

public class ChallengeManager {

    private Main plugin;

    public ChallengeManager(Main plugin) {
        this.plugin = plugin;
    }

    public void enableChallenge() {

        for (Player player : Bukkit.getOnlinePlayers()) {

            AttributeInstance healthAttr = player.getAttribute(Attribute.MAX_HEALTH);

            if (healthAttr != null) {
                healthAttr.setBaseValue(2.0);
                player.setHealth(2.0);
                player.setFoodLevel(20);
            }
        }
    }

    public void disableChallenge() {

        for (Player player : Bukkit.getOnlinePlayers()) {
            AttributeInstance healthAttribute = player.getAttribute(Attribute.MAX_HEALTH);

            if (healthAttribute != null) {
                healthAttribute.setBaseValue(20.0);
                player.setHealth(20);
                player.setFoodLevel(20);
            }
        }
    }
}
