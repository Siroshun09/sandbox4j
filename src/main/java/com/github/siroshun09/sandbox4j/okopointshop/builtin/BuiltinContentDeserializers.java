package com.github.siroshun09.sandbox4j.okopointshop.builtin;

import com.github.siroshun09.configapi.api.Configuration;
import com.github.siroshun09.sandbox4j.okopointshop.implementation.CommandExecution;
import com.github.siroshun09.sandbox4j.okopointshop.implementation.LuckpermsNode;
import com.github.siroshun09.sandbox4j.okopointshop.model.content.Content;
import com.github.siroshun09.sandbox4j.okopointshop.model.registry.ConfigurationDeserializerRegistry;
import com.github.siroshun09.sandbox4j.okopointshop.serialization.Deserializer;
import com.github.siroshun09.sandbox4j.okopointshop.util.EmptyStringChecker;
import org.jetbrains.annotations.NotNull;

public class BuiltinContentDeserializers {

    public static void addToRegistry(@NotNull ConfigurationDeserializerRegistry<Content> registry) {
        registry.register("command", commandExecution());
        registry.register("luckperms_permission", LuckpermsNode::permission);
        registry.register("luckperms_prefix", LuckpermsNode::prefix);
        registry.register("luckperms_suffix", LuckpermsNode::suffix);
    }

    public static @NotNull Deserializer<Configuration, CommandExecution> commandExecution() {
        return source -> {
            var command = EmptyStringChecker.isDefined(source.getString("command"), "command");

            var triggerValue = EmptyStringChecker.isDefined(source.getString("on", "purchase"), "on");
            var trigger = CommandExecution.Trigger.fromConfigValue(triggerValue);

            if (trigger.isEmpty()) {
                throw new IllegalArgumentException("unknown trigger:" + triggerValue);
            }

            return new CommandExecution(command, trigger.get());
        };
    }
}
