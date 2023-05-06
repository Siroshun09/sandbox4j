package com.github.siroshun09.sandbox4j.okopointshop.builtin;

import com.github.siroshun09.configapi.api.Configuration;
import com.github.siroshun09.sandbox4j.okopointshop.model.content.Content;
import com.github.siroshun09.sandbox4j.okopointshop.model.item.UnlimitedItem;
import com.github.siroshun09.sandbox4j.okopointshop.model.item.Item;
import com.github.siroshun09.sandbox4j.okopointshop.model.item.OneTimeItem;
import com.github.siroshun09.sandbox4j.okopointshop.model.item.SubscriptionItem;
import com.github.siroshun09.sandbox4j.okopointshop.model.registry.ConfigurationDeserializerRegistry;
import com.github.siroshun09.sandbox4j.okopointshop.serialization.Deserializer;
import com.github.siroshun09.sandbox4j.okopointshop.serialization.FindingTypeDeserializer;
import com.github.siroshun09.sandbox4j.okopointshop.util.DurationParser;
import com.github.siroshun09.sandbox4j.okopointshop.util.EmptyStringChecker;
import org.jetbrains.annotations.NotNull;

public class BuiltinItemDeserializers {

    public static void addToRegistry(@NotNull ConfigurationDeserializerRegistry<Item> registry, @NotNull FindingTypeDeserializer<Content> contentDeserializer) {
        registry.register("unlimited", unlimitedItem(contentDeserializer));
        registry.register("one_time", oneTimeItem(contentDeserializer));
        registry.register("subscription", subscriptionItem(contentDeserializer));
    }

    public static Deserializer<Configuration, UnlimitedItem> unlimitedItem(@NotNull FindingTypeDeserializer<Content> contentDeserializer) {
        return source -> readAsUnlimitedItem(source, contentDeserializer);
    }

    public static Deserializer<Configuration, OneTimeItem> oneTimeItem(@NotNull FindingTypeDeserializer<Content> contentDeserializer) {
        return source -> {
            var item = readAsUnlimitedItem(source, contentDeserializer);
            return new OneTimeItem(item.id(), item.price(), item.paymentMethod(), item.contents());
        };
    }

    public static Deserializer<Configuration, SubscriptionItem> subscriptionItem(@NotNull FindingTypeDeserializer<Content> contentDeserializer) {
        return source -> {
            var item = readAsUnlimitedItem(source, contentDeserializer);
            var duration = DurationParser.parseDuration(source.getString("duration"));

            if (duration.isZero() || duration.isNegative()) {
                throw new IllegalArgumentException("duration should not be zero or negative");
            }

            var timer = source.getString("timer"); // todo: optional setting?

            return new SubscriptionItem(item.id(), item.price(), item.paymentMethod(), item.contents(), duration, timer);
        };
    }

    private static @NotNull UnlimitedItem readAsUnlimitedItem(@NotNull Configuration source, @NotNull FindingTypeDeserializer<Content> contentDeserializer) {
        return new UnlimitedItem(
                EmptyStringChecker.isDefined(source.getString("id"), "id"),
                source.getDouble("price", source.getInteger("price")),
                EmptyStringChecker.isDefined(source.getString("payment_method"), "payment_method"),
                source.getList("contents", Deserializer.CONFIGURATION.andThen(contentDeserializer).toConfigAPI())
        );
    }
}
