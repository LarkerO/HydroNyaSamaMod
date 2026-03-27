package cn.hydcraft.hydronyasama.railway;

import cn.hydcraft.hydronyasama.BeaconProviderMod;

/** Bootstrap for migrated NyaSamaRailway features. */
public final class RailwayModule {
  private static volatile boolean initialized;

  private RailwayModule() {}

  public static void init() {
    if (initialized) {
      return;
    }
    initialized = true;
    BeaconProviderMod.LOGGER.info("Railway module baseline initialized");
  }
}
