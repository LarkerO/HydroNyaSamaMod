package cn.hydcraft.hydronyasama.railway.util;

/**
 * Core RFID rail processing logic. Migrated from club.nsdn.nyasamarailway.util.RailRFIDCore.
 *
 * <p>RFID rails check for matching tags on passing carts and emit redstone signals.
 */
public final class RailRFIDCore {

  private RailRFIDCore() {}

  /** Default max scan range in blocks for RFID antenna. */
  public static final int DEFAULT_SCAN_RANGE = 8;

  /**
   * Check if the given tag matches the stored RFID pattern. Supports '*' wildcard for any-match.
   */
  public static boolean matchTag(String storedPattern, String cartTag) {
    if (storedPattern == null || storedPattern.isEmpty()) return false;
    if ("*".equals(storedPattern)) return true;
    if (cartTag == null || cartTag.isEmpty()) return false;
    return storedPattern.equals(cartTag);
  }

  /**
   * Calculate signal strength based on cart velocity and distance. Returns 0-15 redstone signal
   * strength.
   */
  public static int calcSignalStrength(double velocity, double distance, double maxRange) {
    if (distance > maxRange || distance < 0) return 0;
    double factor = 1.0 - (distance / maxRange);
    return Math.min(15, Math.max(0, (int) (factor * 15)));
  }
}
