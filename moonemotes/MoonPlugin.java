package net.runelite.client.plugins.moonemotes;

import joptsimple.internal.Strings;
import net.runelite.api.*;
import net.runelite.api.events.ChatMessage;
import net.runelite.api.events.GameStateChanged;
import net.runelite.api.events.OverheadTextChanged;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;

import net.runelite.client.util.ImageUtil;
import lombok.extern.slf4j.Slf4j;
import net.runelite.client.util.Text;
import javax.annotation.Nullable;
import javax.inject.Inject;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.regex.Pattern;


@PluginDescriptor(
        name="Moon2Emotes",
        description = "Adds World Famous variety Streamer moonmoon no w's twitch emotes",
        tags={"moon","twitch","why"}
)
@Slf4j
public class MoonPlugin extends Plugin {
    private static final Pattern WHITESPACE_REGEXP = Pattern.compile("[\\s\\u00A0]");

    @Inject
    private Client client;

    @Inject
    private ClientThread clientThread;

    private int modIconsStart = -1;

    @Override
    protected void startUp(){clientThread.invoke(this::loadMoonEmotes);}

    @Subscribe
    public void onGameStateChanged(GameStateChanged gameStateChanged ){
        if(gameStateChanged.getGameState() == GameState.LOGGED_IN){
            loadMoonEmotes();
        }
    }

    private void loadMoonEmotes(){
        final IndexedSprite[] modIcons = client.getModIcons();
        if (modIconsStart != -1 || modIcons == null)
        {
            return;
        }
        final MoonEmotes[] moonemotes = MoonEmotes.values();
        final IndexedSprite[] newModIcons = Arrays.copyOf(modIcons, modIcons.length + moonemotes.length);
        modIconsStart = modIcons.length;

        for (int i = 0; i < moonemotes.length; i++)
        {
            final MoonEmotes moonemote = moonemotes[i];

            try
            {
                final BufferedImage image = moonemote.loadImage();
                final IndexedSprite sprite = ImageUtil.getImageIndexedSprite(image, client);
                newModIcons[modIconsStart + i] = sprite;
            }
            catch (Exception ex)
            {

                log.warn("Failed to load the sprite for emoji " + moonemote, ex);
            }
        }

        log.debug("Adding emoji icons");
        client.setModIcons(newModIcons);

    }
    @Subscribe
    public void onChatMessage(ChatMessage chatMessage){
        if (client.getGameState() != GameState.LOGGED_IN || modIconsStart == -1)
        {
            return;
        }

        switch (chatMessage.getType())
        {
            case PUBLICCHAT:
            case MODCHAT:
            case FRIENDSCHAT:
            case PRIVATECHAT:
            case PRIVATECHATOUT:
            case MODPRIVATECHAT:
                break;
            default:
                return;
        }

        final MessageNode messageNode = chatMessage.getMessageNode();
        final String message = messageNode.getValue();
        final String updatedMessage = updateMessage(message);

        if (updatedMessage == null)
        {
            return;
        }

        messageNode.setValue(updatedMessage);
    }


    @Subscribe
    public void onOverheadTextChanged(final OverheadTextChanged event)
    {
        if (!(event.getActor() instanceof Player))
        {
            return;
        }

        final String message = event.getOverheadText();
        final String updatedMessage = updateMessage(message);

        if (updatedMessage == null)
        {
            return;
        }

        event.getActor().setOverheadText(updatedMessage);
    }

    @Nullable
    String updateMessage(final String message)
    {
        final String[] messageWords = WHITESPACE_REGEXP.split(message);

        boolean editedMessage = false;
        for (int i = 0; i < messageWords.length; i++)
        {
            // Remove tags except for <lt> and <gt>
            final String trigger = Text.removeFormattingTags(messageWords[i]);
            final MoonEmotes emote = MoonEmotes.getEmote(trigger);

            if (emote == null)
            {
                continue;
            }

            final int emoteId = modIconsStart + emote.ordinal();

            messageWords[i] = messageWords[i].replace(trigger, "<img=" + emoteId + ">");
            editedMessage = true;
        }

        // If we haven't edited the message any, don't update it.
        if (!editedMessage)
        {
            return null;
        }

        return Strings.join(messageWords, " ");
    }
    
}
