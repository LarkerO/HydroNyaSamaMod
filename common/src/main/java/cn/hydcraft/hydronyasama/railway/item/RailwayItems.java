package cn.hydcraft.hydronyasama.railway.item;

import cn.hydcraft.hydronyasama.core.registry.ContentId;
import cn.hydcraft.hydronyasama.core.registry.ContentRegistrar;
import cn.hydcraft.hydronyasama.railway.entity.CartType;

/**
 * Registers all railway-specific items (tickets, coins, train spawners, tools).
 * Called from RailwayContent.register().
 */
public final class RailwayItems {

    private RailwayItems() {}

    public static void register(ContentRegistrar registrar) {
        // --- Tickets ---
        registerItem(registrar, "ticket_once", "ticket");
        registerItem(registrar, "ticket_store", "ticket");
        registerItem(registrar, "nya_coin", "coin");

        // --- Train spawner items (one per cart type) ---
        for (CartType type : CartType.values()) {
            registerItem(registrar, "item_" + type.getId(), "train_spawner");
        }

        // --- Tools ---
        registerItem(registrar, "wrench", "tool");
        registerItem(registrar, "crowbar", "tool");
    }

    private static void registerItem(ContentRegistrar registrar, String idPath, String kind) {
        ContentId id = ContentId.of("hydronyasama", idPath);
        registrar.registerItem(new ContentRegistrar.ItemDefinition(id, "railway", kind, null));
    }
}
