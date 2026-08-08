package dev.okaj.paper.common.command.cooldown;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class CommandCooldownService {

    private final Map<UUID, Map<String, Long>> cooldowns = new HashMap<>();

    public boolean isOnCooldown(UUID uniqueId, String command, long cooldown) {
        if (cooldown <= 0) {
            return false;
        }

        Map<String, Long> playerCooldowns = cooldowns.get(uniqueId);

        if (playerCooldowns == null) {
            return false;
        }

        Long lastExecution = playerCooldowns.get(command);

        if (lastExecution == null) {
            return false;
        }

        long elapsed = System.currentTimeMillis() - lastExecution;

        return elapsed < cooldown * 1000L;
    }

    public void start(UUID uniqueId, String command) {
        cooldowns.computeIfAbsent(uniqueId, ignored -> new HashMap<>())
                .put(command, System.currentTimeMillis());
    }

    public long remaining(UUID uniqueId, String command, long cooldown) {
        Map<String, Long> playerCooldowns = cooldowns.get(uniqueId);

        if (playerCooldowns == null) {
            return 0;
        }

        Long lastExecution = playerCooldowns.get(command);

        if (lastExecution == null) {
            return 0;
        }

        long elapsed = System.currentTimeMillis() - lastExecution;
        long remaining = cooldown * 1000L - elapsed;

        return Math.max(0, remaining);
    }

    public void clear(UUID uniqueId) {
        cooldowns.remove(uniqueId);
    }

    public void clear() {
        cooldowns.clear();
    }
}