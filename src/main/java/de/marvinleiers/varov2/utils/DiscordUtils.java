package de.marvinleiers.varov2.utils;

import de.marvinleiers.varov2.VaroV2;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Category;
import net.dv8tion.jda.api.entities.TextChannel;

import java.util.Collections;
import java.util.List;

public class DiscordUtils
{
    public static void sendMessage(String message)
    {
        TextChannel chatChannel = getOrCreateChannel("varo-bot");

        if (chatChannel == null) return;

        chatChannel.sendMessage(message).complete();
    }

    private static TextChannel getOrCreateChannel(String name)
    {
        List<TextChannel> matches = VaroV2.getDiscordBot().getTextChannelsByName(name, true);
        TextChannel channel;

        if (matches.size() == 0)
        {
            List<Category> categories = VaroV2.getDiscordBot().getCategoriesByName("Varo", true);
            Category category;

            if (categories.size() == 0)
            {
                if (VaroV2.getDiscordServer() == null)
                {
                    return null;
                }

                category = VaroV2.getDiscordServer().createCategory("Varo").complete();
            }
            else
            {
                category = categories.get(0);
            }

            channel = category.createTextChannel(name).addPermissionOverride(VaroV2.getDiscordServer().getPublicRole(),
                    Collections.singleton(Permission.MESSAGE_ADD_REACTION), Collections.singleton(Permission.MESSAGE_WRITE))
                    .addMemberPermissionOverride(VaroV2.getDiscordBot().getSelfUser().getIdLong(), Collections.singleton(Permission.MESSAGE_WRITE),
                            null).complete();
        }
        else
        {
            channel = matches.get(0);
        }

        return channel;
    }
}
