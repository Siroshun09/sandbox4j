package com.github.siroshun09.sandbox4j.okopointshop.implementation;

import com.github.siroshun09.configapi.api.Configuration;
import com.github.siroshun09.sandbox4j.okopointshop.context.PurchaseContext;
import com.github.siroshun09.sandbox4j.okopointshop.context.SubscriptionContext;
import com.github.siroshun09.sandbox4j.okopointshop.model.content.SubscribeContent;
import com.github.siroshun09.sandbox4j.okopointshop.serialization.Deserializer;
import com.github.siroshun09.sandbox4j.okopointshop.util.EmptyStringChecker;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.node.Node;
import net.luckperms.api.node.NodeBuilder;
import net.luckperms.api.node.types.PermissionNode;
import net.luckperms.api.node.types.PrefixNode;
import net.luckperms.api.node.types.SuffixNode;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public record LuckpermsNode(@NotNull Node node) implements SubscribeContent {

    /*
      type: luckperms_permission
      node: example.node
      contexts:
        - key: server
          value: example
     */
    @Contract("_ -> new")
    public static @NotNull LuckpermsNode permission(@NotNull Configuration source) {
        var permissionNode = EmptyStringChecker.isDefined(source.getString("node"), "node");

        var node = PermissionNode.builder().permission(permissionNode).value(true);

        applyContexts(source, node);

        return new LuckpermsNode(node.build());
    }

    /*
      type: luckperms_prefix
      prefix: "&8[&eAdmin&8] &r"
      contexts:
        - key: server
          value: example
     */
    @Contract("_ -> new")
    public static @NotNull LuckpermsNode prefix(@NotNull Configuration source) {
        var prefix = EmptyStringChecker.isDefined(source.getString("prefix"), "prefix");

        var node = PrefixNode.builder().prefix(prefix).value(true);

        applyContexts(source, node);

        return new LuckpermsNode(node.build());
    }

    /*
      type: luckperms_suffix
      suffix: "&e*&r"
      contexts:
        - key: server
          value: example
     */
    @Contract("_ -> new")
    public static @NotNull LuckpermsNode suffix(@NotNull Configuration source) {
        var suffix = EmptyStringChecker.isDefined(source.getString("suffix"), "suffix");

        var node = SuffixNode.builder().suffix(suffix).value(true);
        applyContexts(source, node);

        return new LuckpermsNode(node.build());
    }

    private static void applyContexts(@NotNull Configuration source, @NotNull NodeBuilder<?, ?> nodeBuilder) {
        for (var section : source.getList("contexts", Deserializer.CONFIGURATION.toConfigAPI())) {
            nodeBuilder.withContext(
                    EmptyStringChecker.isDefined(section.getString("key"), "key"),
                    EmptyStringChecker.isDefined(section.getString("value"), "value")
            );
        }
    }

    @Override
    public @NotNull CompletableFuture<Void> onPurchase(@NotNull PurchaseContext context) {
        return LuckPermsProvider.get().getUserManager().modifyUser(context.playerUuid(), user -> user.data().add(node));
    }

    @Override
    public @NotNull CompletableFuture<Void> onExpire(@NotNull SubscriptionContext context) {
        return LuckPermsProvider.get().getUserManager().modifyUser(context.playerUuid(), user -> user.data().remove(node));
    }
}
