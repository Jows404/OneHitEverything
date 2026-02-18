package me.yusufali.onehiteverything.commands;

import me.yusufali.onehiteverything.Main;
import me.yusufali.onehiteverything.managers.ChallengeManager;
import me.yusufali.onehiteverything.managers.ConfigManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class OneHitCommand implements CommandExecutor, TabCompleter {

    private Main plugin;
    private ChallengeManager challengeManager;
    private ConfigManager configManager;


    public OneHitCommand(Main plugin, ChallengeManager challengeManager, ConfigManager configManager) {
        this.plugin = plugin;
        this.challengeManager = challengeManager;
        this.configManager = configManager;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {

        if (!commandSender.hasPermission("onehit.admin")) {
            commandSender.sendMessage(Component.text("no permission."));
            return true;
        }

        if (args.length == 0) {
            sendHelp(commandSender);
            return true;
        }

        switch (args[0].toLowerCase()) {
            case "enable":
                enable(commandSender);
                return true;
            case "disable":
                disable(commandSender);
                return true;
            case "reload":
                break;
            case "help":
                sendHelp(commandSender);
        }

        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        // We only have 1 argument to complete (enable / disable)
        if (args.length == 1) {
            List<String> completions = new ArrayList<>();
            completions.add("enable");
            completions.add("disable");
            return completions;
        }
        // If there are no more orgs to complete
        return Collections.emptyList();
    }

    private void enable(CommandSender sender) {
        if (!configManager.isEnabled()) {
            configManager.setEnabled(true);
            configManager.save();

            String rawMessage = configManager.getMessage("enabled");
            Component broadcastNode = LegacyComponentSerializer.legacySection().deserialize(rawMessage);
            Bukkit.broadcast(broadcastNode);

            challengeManager.enableChallenge();
        }
        else {
            sender.sendMessage(Component.text("Plugin is already enabled!", NamedTextColor.RED));
        }
    }

    private void disable(CommandSender sender) {
        if (configManager.isEnabled()) {
            configManager.setEnabled(false);
            configManager.save();

            String rawMessage = configManager.getMessage("disabled");
            Component broadcastNode = LegacyComponentSerializer.legacySection().deserialize(rawMessage);
            Bukkit.broadcast(broadcastNode);

            challengeManager.disableChallenge();
        }
        else {
            sender.sendMessage(Component.text("Plugin is already disabled!", NamedTextColor.RED));
        }
    }

    private void sendHelp(CommandSender sender) {
        sender.sendMessage("§8§m----------------------------------------");
        sender.sendMessage("&6&1OneHitEverything Plugin Commands:");
        sender.sendMessage("&8 - &e/onehit enable &7 - Enable challenge");
        sender.sendMessage("&8 - &e/onehit disable &7 - Disable challenge");
        sender.sendMessage("&8 - &e/onehit help &7 - Show this help");
        sender.sendMessage("&8 - &e/onehit reload &7 - Reload config");
        sender.sendMessage("§8§m----------------------------------------");
    }
}
