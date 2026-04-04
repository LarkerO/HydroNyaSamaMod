package cn.hydcraft.hydronyasama.railway.entity;

/**
 * Marker interface for high-speed capable carts. Migrated from
 * club.nsdn.nyasamarailway.entity.IHighSpeedCart
 */
public interface IHighSpeedCart {
  double getHighSpeedVelocityLimit();

  boolean isHighSpeedMode();

  void setHighSpeedMode(boolean mode);
}
