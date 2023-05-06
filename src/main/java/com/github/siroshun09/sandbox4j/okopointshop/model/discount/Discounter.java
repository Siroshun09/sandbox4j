package com.github.siroshun09.sandbox4j.okopointshop.model.discount;

import com.github.siroshun09.sandbox4j.okopointshop.context.PurchaseContext;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;
import java.util.function.Predicate;

public interface Discounter {

    Discounter FREE = price -> 0;

    static @NotNull Discounter percentOff(double percent) {
        return context -> context.price() - (context.price() * percent / 100);
    }

    static @NotNull Discounter priceCut(double value) {
        return context -> context.price() - value;
    }

    static @NotNull Discounter freeTrial(@NotNull Predicate<UUID> isFirstPurchase) {
        return context -> isFirstPurchase.test(context.playerUuid()) ? 0.0 : context.price();
    }

    double discount(@NotNull PurchaseContext context);

}
