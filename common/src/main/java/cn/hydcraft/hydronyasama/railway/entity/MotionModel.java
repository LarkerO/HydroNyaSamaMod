package cn.hydcraft.hydronyasama.railway.entity;

/**
 * Defines the physics model used by a locomotive. Migrated from
 * club.nsdn.nyasamarailway.util.TrainController motion variants.
 */
public enum MotionModel {
  BASIC(0, Double.MAX_VALUE),
  AIR_STANDARD(4.0, Double.MAX_VALUE),
  AIR_EXTENDED(10.0, Double.MAX_VALUE),
  AIR_HIGH(40.0, 6.0),
  AIR_HIGH_EX(80.0, 8.0),
  EULER(0, Double.MAX_VALUE),
  SLIP(0, Double.MAX_VALUE);

  private final double maxPower;
  private final double maxVelocity;

  MotionModel(double maxPower, double maxVelocity) {
    this.maxPower = maxPower;
    this.maxVelocity = maxVelocity;
  }

  public double getMaxPower() {
    return maxPower;
  }

  public double getMaxVelocity() {
    return maxVelocity;
  }
}
