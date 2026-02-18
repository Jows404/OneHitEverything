package me.yusufali.onehiteverything.listeners;

import me.yusufali.onehiteverything.Main;
import me.yusufali.onehiteverything.managers.ConfigManager;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

public class HitListener implements Listener {

    private Main plugin;
    private ConfigManager configManager;

    public HitListener(Main plugin, ConfigManager configManager) {
        this.plugin = plugin;
        this.configManager = configManager;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEntityDamage(EntityDamageByEntityEvent e) {

        if (!configManager.isEnabled()) return;

        // Make sure projectiles such as: snowballs, eggs, splash potions, and bottles don't damage
        if (e.getDamager() instanceof Projectile proj) {
            switch (proj.getType()) {
                case SNOWBALL:
                case EGG:
                case SPLASH_POTION:
                case LINGERING_POTION:
                case EXPERIENCE_BOTTLE:
                    return;
                default:
                    break;
            }
        }

        double damage = 100000.0;

        // Damager, entity dealing the damage
        // Damagee, entity receiving the damage

       if (e.getEntity() instanceof LivingEntity) {
           e.setDamage(damage);
       }

    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerDeath(PlayerDeathEvent e) {
       if (!configManager.isEnabled()) return;

       e.setDeathMessage(e.getEntity().getName() + configManager.getMessage("death"));
    }
}
