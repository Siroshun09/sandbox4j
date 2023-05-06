package com.github.siroshun09.sandbox4j.okopointshop.model.content;

import com.github.siroshun09.sandbox4j.okopointshop.context.SubscriptionContext;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public interface SubscribeContent extends Content {

    default @NotNull CompletableFuture<Void> onExtend(@NotNull SubscriptionContext context) {
        return CompletableFuture.completedFuture(null);
    }

    default @NotNull CompletableFuture<Void> onExpire(@NotNull SubscriptionContext context) {
        return CompletableFuture.completedFuture(null);
    }
}
