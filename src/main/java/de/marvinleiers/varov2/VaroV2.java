package de.marvinleiers.varov2;

import de.marvinleiers.marvinplugin.MarvinPlugin;
import de.marvinleiers.varov2.listeners.DiscordListener;
import de.marvinleiers.varov2.listeners.FreezeListener;
import de.marvinleiers.varov2.listeners.PlayerDeathListener;
import de.marvinleiers.varov2.listeners.PlayerJoinListener;
import de.marvinleiers.varov2.time.KickTimer;
import de.marvinleiers.varov2.utils.DiscordUtils;
import de.marvinleiers.varov2.utils.VaroPlayer;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;
import org.bukkit.OfflinePlayer;

import javax.security.auth.login.LoginException;
import java.util.Collection;
import java.util.HashMap;
import java.util.UUID;

public final class VaroV2 extends MarvinPlugin
{
    private static final HashMap<UUID, VaroPlayer> varoPlayers = new HashMap<>();
    private static JDA discordBot;
    private static Guild discordServer;

    @Override
    public void onEnable()
    {
        super.onEnable();

        saveDefaultConfig();

        add("discord-server-name", "Server von Marvin");
        add("join-message", "&l&e<v> &7hat den Server betreten!");
        add("time-out", "&6Deine Zeit ist um!");
        add("come-back-tomorrow", "&cDu kannst erst in &e<v> Stunden &cund &e<v> Minuten &cwieder joinen.");
        add("lost-ban", "&cDu bist aus Varo ausgeschieden!");

        this.initDiscord();

        this.getServer().getPluginManager().registerEvents(new PlayerJoinListener(), this);
        this.getServer().getPluginManager().registerEvents(new PlayerDeathListener(), this);
        this.getServer().getPluginManager().registerEvents(new FreezeListener(), this);
    }

    @Override
    public void onDisable()
    {
        super.onDisable();

        discordBot.shutdown();
    }

    private void initDiscord()
    {
        try
        {
            discordBot = JDABuilder.createDefault(getConfig().getString("DISCORD_TOKEN"))
                    .build();

            discordBot.addEventListener(new DiscordListener());
            discordBot.getPresence().setActivity(Activity.of(Activity.ActivityType.DEFAULT, "VaroBot von Marvin Leiers"));
        }
        catch (LoginException e)
        {
            e.printStackTrace();
            this.getServer().getPluginManager().disablePlugin(this);
        }
    }

    public static void registerVaroPlayer(VaroPlayer varoPlayer)
    {
        varoPlayers.put(varoPlayer.getPlayer().getUniqueId(), varoPlayer);

        DiscordUtils.sendMessage("⚔️ **" + varoPlayer.getPlayer().getName() + "** hat den Server betreten! ⚔️");
        new KickTimer(getInstance(), varoPlayer, true, true);
    }

    public static void unregister(VaroPlayer varoPlayer)
    {
        varoPlayers.remove(varoPlayer.getPlayer().getUniqueId());
    }

    public static boolean isRegistered(OfflinePlayer player)
    {
        for (VaroPlayer varoPlayer : varoPlayers.values())
        {
            if (varoPlayer.getPlayer().getUniqueId().toString().equals(player.getUniqueId().toString()))
                return true;
        }

        return false;
    }

    public static VaroPlayer getVaroPlayer(OfflinePlayer player)
    {
        return varoPlayers.get(player.getUniqueId());
    }

    public static Collection<VaroPlayer> getVaroPlayers()
    {
        return varoPlayers.values();
    }

    public static void setDiscordServer(Guild discordServer)
    {
        VaroV2.discordServer = discordServer;
    }

    public static Guild getDiscordServer()
    {
        return discordServer;
    }

    public static JDA getDiscordBot()
    {
        return discordBot;
    }
}
