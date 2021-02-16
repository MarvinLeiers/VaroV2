package de.marvinleiers.varov2.listeners;

import de.marvinleiers.customconfig.CustomConfig;
import de.marvinleiers.marvinplugin.utils.Messages;
import de.marvinleiers.varov2.VaroV2;
import de.marvinleiers.varov2.utils.VaroPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener
{
    @EventHandler
    public void onLogin(AsyncPlayerPreLoginEvent event)
    {
        CustomConfig customConfig = new CustomConfig(VaroV2.getInstance().getDataFolder().getPath() + "/players/"
                + event.getUniqueId().toString() + ".yml");

        if (!customConfig.isSet("join-on"))
            return;

        boolean canJoin = System.currentTimeMillis() >= customConfig.getLong("join-on");

        if (!canJoin)
        {
            long joinOn = customConfig.getLong("join-on");
            long joinIn = joinOn - System.currentTimeMillis();
            long h = (joinIn / 1000 / 60 / 60) % 24;
            long m = (joinIn / 1000 / 60) % 60;

            if (joinIn > 0)
                event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, Messages.get("come-back-tomorrow", String.valueOf(h),
                        String.valueOf(m)));
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event)
    {
        event.setJoinMessage(null);

        Player player = event.getPlayer();

        if (player.isOp()) return;

        event.setJoinMessage(Messages.get("join-message", player.getName()));

        new VaroPlayer(player);
    }
}
