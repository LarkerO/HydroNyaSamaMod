package cn.hydcraft.hydronyasama.railway.util;

/**
 * Core reception rail logic for train control at stations.
 * Migrated from club.nsdn.nyasamarailway.util.RailReceptionCore.
 *
 * Reception rails handle train stopping/starting at stations with timing control.
 */
public final class RailReceptionCore {

    private RailReceptionCore() {}

    /** Number of ticks to keep door open at a station (default 5 seconds). */
    public static final int DEFAULT_STOP_TICKS = 100;

    /**
     * Determine whether a train should stop at this reception point.
     * @param hasCart true if a cart is on the rail
     * @param powered true if the rail is receiving a redstone signal
     * @param antiMode true if operating in anti-direction mode
     * @return true if the train should apply brakes
     */
    public static boolean shouldStop(boolean hasCart, boolean powered, boolean antiMode) {
        if (!hasCart) return false;
        return antiMode != powered;
    }

    /**
     * Calculate the remaining stop time.
     * @param currentTick current timer value
     * @param maxTicks maximum stop duration
     * @return remaining ticks, or 0 if expired
     */
    public static int remainingStopTime(int currentTick, int maxTicks) {
        return Math.max(0, maxTicks - currentTick);
    }
}
