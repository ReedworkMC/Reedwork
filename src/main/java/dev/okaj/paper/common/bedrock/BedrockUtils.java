package dev.okaj.paper.common.bedrock;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.geysermc.floodgate.api.FloodgateApi;

/**
 * Utility class for detecting Bedrock players through Floodgate.
 *
 * <p>This class provides a simple way to check whether a player joined
 * through Geyser/Floodgate without requiring the rest of the plugin
 * to directly interact with the Floodgate API.</p>
 *
 * <p>Floodgate is an optional dependency. If Floodgate is not installed,
 * all Bedrock checks will safely return {@code false}.</p>
 *
 * <p>Required {@code paper-plugin.yml} configuration:</p>
 *
 * <pre>{@code
 * dependencies:
 *   bootstrap:
 *     floodgate:
 *       load: BEFORE
 *       required: false
 *       join-classpath: true
 *   server:
 *     floodgate:
 *       load: BEFORE
 *       required: false
 *       join-classpath: true
 * }</pre>
 *
 * @author okaj
 */
public final class BedrockUtils {

    private static final boolean floodgateEnabled;

    static {
        floodgateEnabled = Bukkit.getPluginManager().isPluginEnabled("floodgate");
    }

    public static boolean isBedrockPlayer(Player player) {
        if (!floodgateEnabled) {
            return false;
        }

        return FloodgateApi.getInstance()
                .isFloodgatePlayer(player.getUniqueId());
    }
}
