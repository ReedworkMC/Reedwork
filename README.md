# Reedwork

A modern framework for building PaperMC plugins with less boilerplate.

---

## Features

### 🚀 Minimal Boilerplate

Focus on your plugin's functionality instead of repetitive setup code. Reedwork automatically discovers, instantiates,
registers, and wires your plugin components wherever possible.

### 💉 Dependency Injection

Built-in dependency injection for plugin components such as commands, listeners, and services. Dependencies are
automatically discovered and resolved where possible.

### 🎧 Automatic Event Registration

Automatically discovers and registers Bukkit/Paper event listeners, eliminating the need for manual `PluginManager`
registration.

### ✨ Automatic Custom Enchantment Registration

Provides a dedicated API for defining, registering, and managing custom enchantments using Paper's experimental
enchantment features.

### 🧩 Annotation-Based Commands

Define commands and subcommands using annotations instead of repetitive registration code. Supports hierarchical command
structures and descriptive command metadata.

### ⚡ Brigadier Integration

Integrates with Paper's Brigadier-based command system to provide structured command parsing, validation, execution, and
native suggestions.

### 🔄 Automatic Parameter Resolution

Automatically resolves command parameters based on their declared Java types, reducing manual parsing and conversion
code.

### 🔐 Permission Support

Integrates with Bukkit/Paper's permission system, allowing commands to be protected by permissions.

### 📋 Automatic Usage Generation

Generates command usage information from the declared command structure, keeping usage output consistent with the actual
command definition.

### 🔀 Command Aliases & Metadata

Supports command descriptions, aliases, permissions, cooldowns, and additional metadata directly in command definitions.

### 🧰 Developer Utilities

Provides reusable utilities for common Bukkit/Paper development tasks, including readable object representations and
simplified API operations.

---

## Installation

Installation instructions will be available soon.

---

## Quick Start

### Automatic Listener and Command Registration

`MyFirstReedworkPlugin.java`

```java
package dev.reedworkmc.example;

import dev.reedworkmc.reedwork.Reedwork;
import org.bukkit.plugin.java.JavaPlugin;

public final class MyFirstReedworkPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        Reedwork.create(this).scan("dev.reedworkmc.example");
    }
}
```

`HelloCommand.java`

```java
package dev.reedworkmc.example;

import dev.reedworkmc.reedwork.annotation.Command;
import dev.reedworkmc.reedwork.annotation.CommandHandler;
import dev.reedworkmc.reedwork.annotation.SubCommand;
import dev.reedworkmc.reedwork.command.CommandContext;
import org.bukkit.entity.Player;

@Command(
        value = "helloreedwork",
        description = "Send a welcome message to a player",
        permission = "minecraft.commands.op",
        cooldown = 10,
        aliases = {"hellorw", "hrw"}
)
public final class HelloCommand {

    private final GreeterService greeterService;

    public HelloCommand(GreeterService greeterService) {
        this.greeterService = greeterService;
    }

    @CommandHandler
    public boolean greetSelf(CommandContext context) {
        greeterService.sendGreeting(context.player());
        return true;
    }

    @SubCommand("<target>")
    public boolean greetTarget(CommandContext context, Player target) {
        greeterService.sendGreeting(target);
        return true;
    }
}
```

`GreeterService.java`

```java
package dev.reedworkmc.example;

import dev.reedworkmc.reedwork.annotation.Transient;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

@Transient
public final class GreeterService {

    public void sendGreeting(Player target) {
        target.sendMessage(Component.text("Welcome to Reedwork!"));
    }
}
```

### Automatic Custom Enchantment Registration

`MyFirstReedworkPluginBootstrap.java`

```java
package dev.reedworkmc.example;

import dev.reedworkmc.reedwork.Reedwork;
import io.papermc.paper.plugin.bootstrap.PluginBootstrap;

public final class MyFirstReedworkPluginBootstrap implements PluginBootstrap {

    @Override
    public void bootstrap(final BootstrapContext context) {
        // Plugin bootstrap logic
        Reedwork.bootstrap(context).scan("dev.reedworkmc.example");
    }
}
```

`ReedworkEnchantment.java` - the best enchantment in the world, probably...

```java
package dev.reedworkmc.example;

import dev.reedworkmc.reedwork.annotation.Enchantment;
import dev.reedworkmc.reedwork.enchantment.CustomEnchantment;
import io.papermc.paper.registry.data.EnchantmentRegistryEntry;
import io.papermc.paper.registry.keys.ItemTypeKeys;
import io.papermc.paper.registry.tag.TagKey;
import net.kyori.adventure.text.Component;
import org.bukkit.inventory.EquipmentSlotGroup;
import org.bukkit.inventory.ItemType;

@Enchantment("Reedwork Power")
public final class ReedworkEnchantment implements CustomEnchantment {
    public Component description() {
        return Component.text("The power of Reedwork");
    }

    @Override
    public TagKey<ItemType> supportedItems() {
        return ItemTypeKeys.DIRT;
    }

    @Override
    public int anvilCost() {
        return 1;
    }

    @Override
    public int maxLevel() {
        return 1;
    }

    @Override
    public int weight() {
        return 5;
    }

    @Override
    public EnchantmentRegistryEntry.EnchantmentCost minimumCost() {
        return EnchantmentRegistryEntry.EnchantmentCost.of(1, 1);
    }

    @Override
    public EnchantmentRegistryEntry.EnchantmentCost maximumCost() {
        return EnchantmentRegistryEntry.EnchantmentCost.of(1, 5);
    }

    @Override
    public EquipmentSlotGroup activeSlots() {
        return EquipmentSlotGroup.ANY;
    }
}
```

---

## Documentation

Documentation is coming soon. For now, see the [Quick Start](#quick-start).

---

## Contributing

See [CONTRIBUTING.md](CONTRIBUTING.md) for information about contributing to Reedwork.

---

## License

Copyright (c) 2026-present 0kAj

Reedwork is licensed under the [GNU Lesser General Public License v3.0](LICENSE).

Reedwork may be used in open-source, commercial, and proprietary plugins, subject to the terms of the LGPL-3.0.

Modifications to Reedwork itself are subject to the terms of the LGPL-3.0.