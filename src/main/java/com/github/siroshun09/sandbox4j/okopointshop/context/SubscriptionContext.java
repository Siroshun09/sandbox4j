package com.github.siroshun09.sandbox4j.okopointshop.context;

import org.jetbrains.annotations.NotNull;

import java.time.Instant;
import java.util.UUID;

public record SubscriptionContext(@NotNull UUID playerUuid, @NotNull String playerName, double price, @NotNull Instant start) {
}
