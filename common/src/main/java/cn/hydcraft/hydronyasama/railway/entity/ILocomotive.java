package cn.hydcraft.hydronyasama.railway.entity;

/**
 * Interface for locomotive entities with enhanced control.
 * Migrated from club.nsdn.nyasamarailway.entity.ILocomotive
 */
public interface ILocomotive extends IMotorCart {
    int getMaxPassengers();
    double getMaxVelocity();
    void setDir(int dir);
    int getDir();
}
