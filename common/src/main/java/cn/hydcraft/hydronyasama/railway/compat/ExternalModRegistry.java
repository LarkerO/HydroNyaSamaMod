package cn.hydcraft.hydronyasama.railway.compat;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Registry for external mod compatibility adapters.
 * Migrated from club.nsdn.nyasamarailway.extmod pattern.
 */
public final class ExternalModRegistry {

    private static final List<IExternalModCompat> ADAPTERS = new CopyOnWriteArrayList<>();

    private ExternalModRegistry() {}

    public static void register(IExternalModCompat adapter) {
        ADAPTERS.add(adapter);
    }

    public static void initAll() {
        for (IExternalModCompat adapter : ADAPTERS) {
            if (adapter.isLoaded()) {
                adapter.init();
            }
        }
    }

    public static List<IExternalModCompat> getLoadedAdapters() {
        List<IExternalModCompat> loaded = new ArrayList<>();
        for (IExternalModCompat adapter : ADAPTERS) {
            if (adapter.isLoaded()) loaded.add(adapter);
        }
        return loaded;
    }

    public static boolean isExternalEntity(String entityClassName) {
        for (IExternalModCompat adapter : ADAPTERS) {
            if (adapter.isLoaded() && adapter.isExternalEntity(entityClassName)) return true;
        }
        return false;
    }

    public static double getMaxVelocity(String entityClassName) {
        for (IExternalModCompat adapter : ADAPTERS) {
            if (adapter.isLoaded() && adapter.isExternalEntity(entityClassName)) {
                return adapter.getMaxVelocity(entityClassName);
            }
        }
        return -1;
    }
}
