package cn.hydcraft.hydronyasama.railway.entity;

import java.util.Map;

/**
 * Extended data model for locomotive entities.
 * Migrated from club.nsdn.nyasamarailway.entity.LocoBase.
 */
public class LocoData extends MinecartData {

    private double motorPower;
    private double motorBrake;
    private boolean motorState;
    private double maxVelocity;
    private MotionModel motionModel;

    public LocoData(CartType cartType) {
        super(cartType);
        this.motorPower = 0;
        this.motorBrake = 0;
        this.motorState = false;
        this.maxVelocity = 2.0;
        this.motionModel = MotionModel.AIR_STANDARD;
    }

    public double getMotorPower() { return motorPower; }
    public void setMotorPower(double motorPower) { this.motorPower = motorPower; }

    public double getMotorBrake() { return motorBrake; }
    public void setMotorBrake(double motorBrake) { this.motorBrake = motorBrake; }

    public boolean isMotorState() { return motorState; }
    public void setMotorState(boolean motorState) { this.motorState = motorState; }

    public double getMaxVelocity() { return maxVelocity; }
    public void setMaxVelocity(double maxVelocity) { this.maxVelocity = maxVelocity; }

    public MotionModel getMotionModel() { return motionModel; }
    public void setMotionModel(MotionModel motionModel) { this.motionModel = motionModel; }

    @Override
    public Map<String, Object> toNbtMap() {
        Map<String, Object> map = super.toNbtMap();
        map.put("motorP", motorPower);
        map.put("motorB", motorBrake);
        map.put("motorS", motorState);
        map.put("maxV", maxVelocity);
        map.put("motion", motionModel.name());
        return map;
    }

    @Override
    public void fromNbtMap(Map<String, Object> map) {
        super.fromNbtMap(map);
        if (map == null) return;
        if (map.containsKey("motorP")) motorPower = ((Number) map.get("motorP")).doubleValue();
        if (map.containsKey("motorB")) motorBrake = ((Number) map.get("motorB")).doubleValue();
        if (map.containsKey("motorS")) motorState = (Boolean) map.get("motorS");
        if (map.containsKey("maxV")) maxVelocity = ((Number) map.get("maxV")).doubleValue();
        if (map.containsKey("motion")) {
            try {
                motionModel = MotionModel.valueOf((String) map.get("motion"));
            } catch (IllegalArgumentException e) {
                motionModel = MotionModel.AIR_STANDARD;
            }
        }
    }
}
