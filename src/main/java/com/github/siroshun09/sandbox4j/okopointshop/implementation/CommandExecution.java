package com.github.siroshun09.sandbox4j.okopointshop.implementation;

import com.github.siroshun09.sandbox4j.okopointshop.context.PurchaseContext;
import com.github.siroshun09.sandbox4j.okopointshop.context.SubscriptionContext;
import com.github.siroshun09.sandbox4j.okopointshop.model.content.SubscribeContent;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

public record CommandExecution(@NotNull String command, @NotNull Trigger trigger) implements SubscribeContent {

    public static Function<String, CompletableFuture<Void>> COMMAND_EXECUTOR = command -> CompletableFuture.completedFuture(null);

    @Override
    public @NotNull CompletableFuture<Void> onPurchase(@NotNull PurchaseContext context) {
        return trigger == Trigger.ON_PURCHASE ?
                COMMAND_EXECUTOR.apply(replacePlaceholder(context)) :
                CompletableFuture.completedFuture(null);
    }

    @Override
    public @NotNull CompletableFuture<Void> onExtend(@NotNull SubscriptionContext context) {
        return trigger == Trigger.ON_EXTEND ?
                COMMAND_EXECUTOR.apply(replacePlaceholder(context)) :
                CompletableFuture.completedFuture(null);
    }

    @Override
    public @NotNull CompletableFuture<Void> onExpire(@NotNull SubscriptionContext context) {
        return trigger == Trigger.ON_EXPIRE ?
                COMMAND_EXECUTOR.apply(replacePlaceholder(context)) :
                CompletableFuture.completedFuture(null);
    }

    private @NotNull String replacePlaceholder(@NotNull PurchaseContext context) {
        return command.replace("%player_uuid%", context.playerUuid().toString())
                .replace("%player_name%", context.playerName())
                .replace("%price%", Double.toString(context.price()));
    }

    private @NotNull String replacePlaceholder(@NotNull SubscriptionContext context) {
        return command.replace("%player_uuid%", context.playerUuid().toString())
                .replace("%player_name%", context.playerName())
                .replace("%price%", Double.toString(context.price()));
    }

    public enum Trigger {
        ON_PURCHASE,
        ON_EXTEND,
        ON_EXPIRE;

        private static final Map<String, Trigger> CONFIG_VALUES;

        static {
            var map = new HashMap<String, Trigger>();

            for (var trigger : values()) {
                map.put(trigger.configValue(), trigger);
            }

            CONFIG_VALUES = Collections.unmodifiableMap(map);
        }

        public static @NotNull Optional<Trigger> fromConfigValue(@NotNull String value) {
            return Optional.ofNullable(CONFIG_VALUES.get(value));
        }

        public @NotNull String configValue() {
            return name().substring(3).toLowerCase(Locale.ENGLISH);
        }
    }
}
