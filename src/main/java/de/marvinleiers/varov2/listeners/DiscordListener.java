package de.marvinleiers.varov2.listeners;

import de.marvinleiers.marvinplugin.utils.Messages;
import de.marvinleiers.varov2.VaroV2;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class DiscordListener extends ListenerAdapter
{
    @Override
    public void onReady(@NotNull ReadyEvent event)
    {
        List<Guild> guilds = VaroV2.getDiscordBot().getGuildsByName(Messages.get("discord-server-name"), true);

        if (guilds.size() == 0)
        {
            VaroV2.getInstance().getLogger().severe("Bot ist nicht auf dem Discord-Server!");
        }
        else
        {
            VaroV2.setDiscordServer(guilds.get(0));
        }
    }

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event)
    {
        if (event.isFromType(ChannelType.PRIVATE) && !event.getAuthor().isBot())
        {
            User user = event.getAuthor();

            user.openPrivateChannel().queue((channel) ->
                    channel.sendMessage("Auf private Nachrichten kann ich dir keine Antwort geben! :smile:").queue());
        }
    }
}
