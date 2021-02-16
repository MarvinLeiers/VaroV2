package de.marvinleiers.varov2.utils;

import de.marvinleiers.customconfig.CustomConfig;
import de.marvinleiers.varov2.VaroV2;
import org.bukkit.OfflinePlayer;

public class VaroPlayer
{
    private final CustomConfig customConfig;
    private final OfflinePlayer player;
    private boolean freeze;

    public VaroPlayer(OfflinePlayer player)
    {
        this.customConfig = new CustomConfig(VaroV2.getInstance().getDataFolder().getPath() + "/players/" +
                player.getUniqueId().toString() + ".yml");

        this.player = player;
        this.freeze = false;

        if (!VaroV2.isRegistered(player))
            VaroV2.registerVaroPlayer(this);
    }

    public void setFreeze(boolean freeze)
    {
        this.freeze = freeze;
    }

    public boolean isFrozen()
    {
        return freeze;
    }

    public CustomConfig getCustomConfig()
    {
        return customConfig;
    }

    public OfflinePlayer getPlayer()
    {
        return player;
    }
}
