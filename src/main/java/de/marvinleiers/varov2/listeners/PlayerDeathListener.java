package de.marvinleiers.varov2.listeners;

import de.marvinleiers.marvinplugin.utils.Messages;
import de.marvinleiers.varov2.VaroV2;
import de.marvinleiers.varov2.utils.DiscordUtils;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeathListener implements Listener
{
    @EventHandler
    public void onDeath(PlayerDeathEvent event)
    {
        Player player = event.getEntity().getPlayer();

        Bukkit.getBanList(BanList.Type.NAME).addBan(player.getName(), Messages.get("lost-ban"), null, null);
        player.kickPlayer(Messages.get("lost-ban"));

        VaroV2.unregister(VaroV2.getVaroPlayer(player));

        DiscordUtils.sendMessage("☠️ **" + player.getName() + "** ist gestorben! ☠️");
    }
}
