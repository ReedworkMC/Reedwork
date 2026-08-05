package dev.okaj.paper.common.inject.registry;

import java.util.Objects;

public record ServiceKey(Class<?> type, String name) {

    public ServiceKey(Class<?> type, String name) {
        this.type = type;
        this.name = name == null ? "" : name;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other)
            return true;

        if (!(other instanceof ServiceKey(Class<?> otherType, String otherName))) {
            return false;
        }

        return type.equals(otherType) && Objects.equals(name, otherName);
    }

}
