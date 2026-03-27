package cn.hydcraft.hydronyasama.railway.util;

/**
 * NTP (NyaSama Train Protocol) motion calculation library.
 * Contains all physics formulas for train motion from the original TrainController.
 * Migrated from club.nsdn.nyasamarailway.util.TrainController (motion methods only).
 *
 * All methods are static and platform-agnostic - they operate on primitive values
 * rather than Minecraft Entity objects.
 */
public final class NtpMotion {

    private NtpMotion() {}

    public static final int MAX_POWER = 20;
    public static final double DT = 0.001;
    public static final double MIN_V = 0.2;
    public static final double MIN_VELOCITY_THRESHOLD = 0.005;
    public static final double MIN_VELOCITY_EULER = 0.02;

    /**
     * Standard motion calculation with basic friction model.
     */
    public static double calcVelocityBasic(double currentVelocity, int power, int brakeR, boolean highSpeed) {
        double velocity = Math.abs(currentVelocity);
        if (power > 0 && velocity < MIN_VELOCITY_THRESHOLD) {
            velocity = MIN_VELOCITY_THRESHOLD;
        }

        if (brakeR > 1) {
            double tractionFactor = highSpeed ? (power / 10.0) : (power / 20.0);
            double friction = highSpeed ? 0.01 : 0.02;
            double nextV = calcVelocityUp(velocity, 0.1, 1.0, tractionFactor, friction);
            if (velocity < nextV) velocity = nextV;
        }

        if (brakeR < 10) {
            velocity = calcVelocityDown(velocity, 0.1, 1.0, 0.1, 1.0, brakeR / 10.0, 0.02);
            if (velocity < MIN_VELOCITY_THRESHOLD) velocity = 0;
        }

        return velocity;
    }

    /**
     * Motion with air resistance model.
     */
    public static double calcVelocityWithAir(double currentVelocity, int power, int brakeR, double maxPower) {
        double velocity = Math.abs(currentVelocity);
        if (power > 0 && velocity < MIN_VELOCITY_THRESHOLD) {
            velocity = MIN_VELOCITY_THRESHOLD;
        }

        if (brakeR > 1) {
            double outP = maxPower / Math.pow(20.0, Math.E / 2.0) * Math.pow((double) power, Math.E / 2.0);
            double nextV = calcVelocityUpWithAir(velocity, 0.1, 1.0, outP, DT);
            if (velocity < nextV) velocity = nextV;
        }

        if (brakeR < 10) {
            double b = velocity < MIN_V ? 2.0 : 1.0;
            velocity = calcVelocityDownWithAir(velocity, 0.1, 1.0, b, 1.0, brakeR / 10.0, DT);
            if (velocity < MIN_VELOCITY_THRESHOLD) velocity = 0;
        }

        return velocity;
    }

    /**
     * Motion with Euler integration model.
     */
    public static double calcVelocityEuler(double currentVelocity, int power, int brakeR, double maxV) {
        double velocity = Math.abs(currentVelocity);
        double minV = MIN_VELOCITY_EULER;
        if (power > 0 && velocity < minV) velocity = minV;

        double p = power / 20.0;
        double r = 1.0 - (brakeR - 1.0) / 9.0;
        double nextV = calcWithEuler(velocity, p, r, maxV, 0.05);

        if ((brakeR > 1 && velocity < nextV) || brakeR < 10) {
            velocity = nextV;
        }

        if (velocity < minV) velocity = 0;
        if (velocity > maxV) velocity = maxV;
        return velocity;
    }

    /**
     * Motion with wheel-slip model.
     */
    public static double calcVelocitySlip(double currentVelocity, int power, int brakeR, double maxV) {
        double velocity = Math.abs(currentVelocity);
        double minV = MIN_VELOCITY_EULER;
        if (power > 0 && velocity < minV) velocity = minV;

        double p = power / 20.0;
        double r = 1.0 - (brakeR - 1.0) / 9.0;
        double nextV = calcWithSlip(velocity, p, r, maxV, 0.05);

        if ((brakeR > 1 && velocity < nextV) || brakeR < 10) {
            velocity = nextV;
        }

        if (velocity < minV) velocity = 0;
        if (velocity > maxV) velocity = maxV;
        return velocity;
    }

    /**
     * Convert velocity + direction + yaw into XZ motion components.
     */
    public static double[] calcMotionXZ(double velocity, int direction, double yawDegrees) {
        double rad = yawDegrees * Math.PI / 180.0;
        double mx = Math.cos(rad) * direction * velocity;
        double mz = -Math.sin(rad) * direction * velocity;
        return new double[] { mx, mz };
    }

    /**
     * Calculate yaw from entity rotation.
     * Original: 180.0 - cart.rotationYaw
     */
    public static double calcYaw(double entityRotationYaw) {
        return 180.0 - entityRotationYaw;
    }

    // --- Internal physics formulas (ported from org.thewdj.physics.Dynamics.LocoMotions) ---

    private static double calcVelocityUp(
            double velocity, double mass, double friction, double power, double deltaTime) {
        return velocity + (power - friction * velocity) / mass * deltaTime;
    }

    /**
     * Deceleration model with electromagnetic braking.
     * resistiveLoss = resistance * brakeRatio is a constant braking force term
     * (not velocity-proportional); this matches the original Dynamics.LocoMotions formula.
     */
    private static double calcVelocityDown(
            double velocity, double mass, double friction,
            double inductance, double resistance, double brakeRatio, double deltaTime) {
        double brakePower = inductance * velocity * brakeRatio;
        double resistiveLoss = resistance * brakeRatio;
        return velocity - (friction * velocity + brakePower + resistiveLoss) / mass * deltaTime;
    }

    private static double calcVelocityUpWithAir(
            double velocity, double mass, double friction, double power, double deltaTime) {
        double airDrag = 0.01 * velocity * velocity;
        return velocity + (power - friction * velocity - airDrag) / mass * deltaTime;
    }

    private static double calcVelocityDownWithAir(
            double velocity, double mass, double friction,
            double inductance, double resistance, double brakeRatio, double deltaTime) {
        double brakePower = inductance * velocity * brakeRatio;
        double resistiveLoss = resistance * brakeRatio;
        double airDrag = 0.01 * velocity * velocity;
        return velocity - (friction * velocity + brakePower + resistiveLoss + airDrag)
                / mass * deltaTime;
    }

    private static double calcWithEuler(
            double velocity, double power, double brakeRelease, double maxVelocity,
            double deltaTime) {
        double target = power * maxVelocity * brakeRelease;
        return velocity + (target - velocity) * deltaTime;
    }

    private static double calcWithSlip(
            double velocity, double power, double brakeRelease, double maxVelocity,
            double deltaTime) {
        double target = power * maxVelocity * brakeRelease;
        double slipFactor = velocity > 0.5 * maxVelocity ? 0.8 : 1.0;
        return velocity + (target - velocity) * deltaTime * slipFactor;
    }
}
