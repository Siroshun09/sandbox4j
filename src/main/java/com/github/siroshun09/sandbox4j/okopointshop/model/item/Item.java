package com.github.siroshun09.sandbox4j.okopointshop.model.item;

import com.github.siroshun09.sandbox4j.okopointshop.model.content.Content;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.List;

public interface Item {

    @NotNull String id();

    double price();

    @NotNull String paymentMethod();

    @NotNull @Unmodifiable List<? extends Content> contents();

}
