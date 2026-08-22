# Reedwork

A modern framework for building PaperMC plugins with less boilerplate.

## Features

### 🚀 Minimal Boilerplate

Focus on your plugin's functionality instead of repetitive setup code.
Reedwork automatically discovers, instantiates, registers, and wires your plugin components wherever possible.

### 💉 Dependency Injection

Built-in dependency injection for plugin components such as commands, listeners, services.
Dependencies are automatically discovered and resolved where possible.

### 🎧 Automatic Event Registration

Automatically discovers and registers Bukkit/Paper event listeners, eliminating the need for manual `PluginManager` registration.

### ✨ Automatic Custom Enchantment Registration

Provides a dedicated API for defining, registering, and managing custom enchantments using Paper's experimental enchantment features.

### 🧩 Annotation-Based Commands Registration

Define commands and subcommands using annotations instead of repetitive registration code.
Supports hierarchical command structures and descriptive command metadata.

### ⚡ Brigadier Integration

Integrates with Paper's Brigadier-based command system to provide
structured command parsing, validation, execution, and native suggestions.

### 🔄 Automatic Parameter Resolution

Automatically resolves command parameters based on their declared Java
types, reducing manual parsing and conversion code.

### 🔐 Permission Support

Integrates with Bukkit/Paper's permission system, allowing commands to be protected by permissions.

### 📋 Automatic Usage Generation

Generates command usage information from the declared command structure,
keeping usage output consistent with the actual command definition.

### 🔀 Command Aliases & Metadata

Supports command descriptions, aliases, permissions, cooldowns, and additional metadata directly in command definitions.

### 🧰 Developer Utilities

Provides reusable utilities for common Bukkit/Paper development tasks, including readable object representations and simplified API operations.


## Contributing

See [CONTRIBUTING.md](CONTRIBUTING.md) for information about contributing to Reedwork.

## License

Copyright (c) 2026-present 0kAj

Reedwork is licensed under the [GNU Lesser General Public License v3.0](LICENSE).

Reedwork may be used in open-source, commercial, and proprietary plugins, subject to the terms of the LGPL-3.0.

Modifications to Reedwork itself are subject to the terms of the LGPL-3.0.