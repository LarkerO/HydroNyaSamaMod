package cn.hydcraft.hydronyasama.railway.compat;

/**
 * Interface for external railway mod compatibility.
 * Migrated from club.nsdn.nyasamarailway.extmod.IExtMod.
 */
public interface IExternalModCompat {

    /** @return the mod ID this compat handles */
    String getModId();

    /** @return true if the target mod is loaded */
    boolean isLoaded();

    /** Initialize compatibility layer if target mod is present */
    void init();

    /** Check if an entity class belongs to the target mod */
    boolean isExternalEntity(String entityClassName);

    /** Get max velocity for an external mod entity, or -1 if not applicable */
    double getMaxVelocity(String entityClassName);
}
