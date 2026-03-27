package cn.hydcraft.hydronyasama.railway.util;

/**
 * Core sniffer rail logic for detecting cart information. Migrated from
 * club.nsdn.nyasamarailway.util.RailSnifferCore.
 *
 * <p>Sniffer rails extract extended info from passing carts and convert it to redstone signals.
 */
public final class RailSnifferCore {

  private RailSnifferCore() {}

  /**
   * Parse extended info string from a cart into a numeric signal.
   *
   * @param extInfo the extended info string
   * @return redstone signal strength 0-15
   */
  public static int parseSignal(String extInfo) {
    if (extInfo == null || extInfo.isEmpty()) return 0;
    try {
      int val = Integer.parseInt(extInfo.trim());
      return Math.min(15, Math.max(0, val));
    } catch (NumberFormatException e) {
      return 1;
    }
  }

  /**
   * Build a comparator output from cart velocity.
   *
   * @param velocity cart velocity in m/s
   * @param maxVelocity maximum expected velocity
   * @return redstone signal strength 0-15
   */
  public static int velocityToSignal(double velocity, double maxVelocity) {
    if (maxVelocity <= 0) return 0;
    double ratio = Math.abs(velocity) / maxVelocity;
    return Math.min(15, Math.max(0, (int) (ratio * 15)));
  }
}
