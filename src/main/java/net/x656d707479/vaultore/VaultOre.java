package net.x656d707479.vaultore;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public final class VaultOre extends JavaPlugin implements Listener {
    private Economy economy;
    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);

        if(!setupEconomy()) {
            getLogger().warning("Vault economy setup failed");
        }

    }

    @Override
    public void onDisable() {

    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();

        if (isOre(block.getType())) {
            double amount = getCurrencyValue(block.getType());

            if (amount > 0) {
                economy.depositPlayer(player, amount);
                player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f, 1.0f);
            }
        }
    }

    private boolean isOre(Material material) {
        return material == Material.NETHER_QUARTZ_ORE || material == Material.NETHER_GOLD_ORE || material == Material.COAL_ORE || material == Material.IRON_ORE || material == Material.LAPIS_ORE || material == Material.REDSTONE_ORE || material == Material.GOLD_ORE || material == Material.DIAMOND_ORE || material == Material.EMERALD_ORE || material == Material.ANCIENT_DEBRIS;
    }

    private double getCurrencyValue(Material material) {
        switch (material) {
            case NETHER_QUARTZ_ORE:
            case NETHER_GOLD_ORE:
            case COAL_ORE:
                return 1.0;
            case IRON_ORE:
                return 2.0;
            case LAPIS_ORE:
            case REDSTONE_ORE:
                return 3.0;
            case GOLD_ORE:
                return 4.0;
            case DIAMOND_ORE:
                return 5.0;
            case EMERALD_ORE:
                return 8.0;
            case ANCIENT_DEBRIS:
                return 10.0;
            default:
                return 0.0;
        }
    }

    private boolean setupEconomy() {
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp != null) {
            economy = rsp.getProvider();
        }
        return (economy != null);
    }
}
