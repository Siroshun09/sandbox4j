package com.github.siroshun09.sandbox4j.okopointshop.model.discount;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Set;

public record Discount(@NotNull String id, @NotNull Period period, @NotNull Set<String> items, @NotNull List<? extends Discounter> details) {
}
