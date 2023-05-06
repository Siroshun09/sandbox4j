package com.github.siroshun09.sandbox4j.okopointshop.context;

import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public record PurchaseContext(@NotNull UUID playerUuid, @NotNull String playerName, double price) {

    public @NotNull PurchaseContext price(double price) {
        return new PurchaseContext(playerUuid, playerName, price);
    }

}
