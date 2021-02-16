package de.marvinleiers.varov2.time;

import de.marvinleiers.marvinplugin.utils.CountdownTimer;
import de.marvinleiers.marvinplugin.utils.Messages;
import de.marvinleiers.varov2.VaroV2;
import de.marvinleiers.varov2.utils.VaroPlayer;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class KickTimer extends CountdownTimer
{
    public KickTimer(JavaPlugin plugin, VaroPlayer varoPlayer, boolean freeze, boolean join)
    {
        super(plugin, 30,
                () ->
                {
                    if (freeze)
                        varoPlayer.setFreeze(true);
                },
                () ->
                {
                    varoPlayer.getCustomConfig().set("join-on", TimeHelper.calculate0Uhr());
                    VaroV2.unregister(varoPlayer);

                    if (varoPlayer.getPlayer().isOnline())
                    {
                        Player player = Bukkit.getPlayer(varoPlayer.getPlayer().getUniqueId());

                        player.kickPlayer(Messages.get("time-out"));
                    }
                },
                (t) -> {
                    if (t.getTotalSeconds() - t.getSecondsLeft() == 10)
                        varoPlayer.setFreeze(false);

                    if (varoPlayer.getPlayer().isOnline())
                    {
                        Player player = Bukkit.getPlayer(varoPlayer.getPlayer().getUniqueId());

                        String m = t.getSecondsLeft() / 60 + "";
                        String s = t.getSecondsLeft() % 60 < 10 ? "0" + t.getSecondsLeft() % 60 : t.getSecondsLeft() % 60 + "";

                        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, null, TextComponent.fromLegacyText("§e§l"
                                + m + ":" + s + " §r§ebis zum Kick!"));
                    }
                });

        scheduleTimer();
    }
}
