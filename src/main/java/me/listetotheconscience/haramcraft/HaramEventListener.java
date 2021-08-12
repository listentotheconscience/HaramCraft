package me.listetotheconscience.haramcraft;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.jetbrains.annotations.NotNull;

public class HaramEventListener implements Listener {
    private Boolean isHaram = false;
    /**
     * Обработчик события onConsumeItem
     * @param event - объект PlayerItemConsumeEvent
     */
    @EventHandler
    public void onConsumeItem(PlayerItemConsumeEvent event) {
        Material id = event.getItem().getType();
        Player player = event.getPlayer();
        if (haramFood(id) && isPlayerSeesSky(player)) {
            haramMoment(player);
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Material carpet = Material.WHITE_CARPET;
        Material target = event.getItem().getType();
        if (isHaram) {
            if (target == carpet) {
                //TODO: Teleport player to spawn
                //TODO: Stop timer
                isHaram = false;
            }
        }

    }

    /**
     * Метод проверки блоков над игроком
     * @param player - объект Player
     * @return true если над игроком нет блоков, иначе false
     */
    private @NotNull Boolean isPlayerSeesSky(Player player) {
        final Material block = Material.BRICK;
        Location location = player.getLocation().clone();
        double y = location.getY();
        final double HIGH_BORDER = y + 10;

        for (; y <= HIGH_BORDER; y++) {
            location.add(0, 1, 0);
            if (location.getBlock().getType() != block) {
                player.sendMessage("Allah Didn't See");
                return false;
            }
        }
        return true;
    }

    /**
     * Метод проверки предмета на харам
     * @param food - объект Material
     * @return true если это свинина либо жаренная свинина
     */
    private @NotNull Boolean haramFood(Material food) {
        return (food == Material.PORKCHOP) || (food == Material.COOKED_PORKCHOP);
    }

    /**
     * Метод телепорта игрока в nether
     * @param player - объект Player
     */
    private void haramMoment(Player player){
        World nether = Bukkit.getWorld("world_nether");
        assert nether != null;
        player.teleport(nether.getSpawnLocation());
        player.sendMessage("Haram Moment");

        //TODO: Set timer
        isHaram = true;
    }
}
