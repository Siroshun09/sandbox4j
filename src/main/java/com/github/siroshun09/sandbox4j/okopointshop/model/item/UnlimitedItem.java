package com.github.siroshun09.sandbox4j.okopointshop.model.item;

import com.github.siroshun09.sandbox4j.okopointshop.model.content.Content;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public record UnlimitedItem(@NotNull String id, double price,
                            @NotNull String paymentMethod, @NotNull List<? extends Content> contents) implements Item {
}
