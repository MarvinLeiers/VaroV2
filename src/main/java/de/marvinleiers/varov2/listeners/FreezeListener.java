package de.marvinleiers.varov2.listeners;

import de.marvinleiers.varov2.VaroV2;
import de.marvinleiers.varov2.utils.VaroPlayer;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class FreezeListener implements Listener
{
    @EventHandler
    public void onMove(PlayerMoveEvent event)
    {
        Player player = event.getPlayer();

        if (VaroV2.isRegistered(player))
        {
            VaroPlayer varoPlayer = VaroV2.getVaroPlayer(player);

            if (varoPlayer.isFrozen())
            {
                Location from = event.getFrom();
                Location to = event.getTo();

                if (from.getX() != to.getX() || from.getZ() != to.getZ())
                {
                    Location newLocation = new Location(from.getWorld(), from.getX(), to.getY(), from.getZ(), to.getYaw(),
                            to.getPitch());

                    event.setTo(newLocation);
                }
            }
        }
    }
}
