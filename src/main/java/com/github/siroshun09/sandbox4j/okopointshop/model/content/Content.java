package com.github.siroshun09.sandbox4j.okopointshop.model.content;

import com.github.siroshun09.sandbox4j.okopointshop.context.PurchaseContext;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public interface Content {

    @NotNull CompletableFuture<Void> onPurchase(@NotNull PurchaseContext context);

}
