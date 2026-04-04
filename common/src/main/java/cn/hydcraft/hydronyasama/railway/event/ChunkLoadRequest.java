package cn.hydcraft.hydronyasama.railway.event;

/**
 * Platform-agnostic chunk load request. Migrated from
 * club.nsdn.nyasamarailway.event.ChunkLoaderHandler. Used to keep chunks loaded around active
 * railway entities.
 */
public final class ChunkLoadRequest {

  private final int chunkX;
  private final int chunkZ;
  private final String dimensionId;
  private final boolean forceLoad;

  public ChunkLoadRequest(int chunkX, int chunkZ, String dimensionId, boolean forceLoad) {
    this.chunkX = chunkX;
    this.chunkZ = chunkZ;
    this.dimensionId = dimensionId;
    this.forceLoad = forceLoad;
  }

  public int getChunkX() {
    return chunkX;
  }

  public int getChunkZ() {
    return chunkZ;
  }

  public String getDimensionId() {
    return dimensionId;
  }

  public boolean isForceLoad() {
    return forceLoad;
  }
}
