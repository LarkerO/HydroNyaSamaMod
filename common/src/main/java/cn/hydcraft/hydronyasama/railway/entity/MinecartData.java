package cn.hydcraft.hydronyasama.railway.entity;

import java.util.HashMap;
import java.util.Map;

/**
 * Platform-agnostic data model representing a railway minecart's state.
 * Migrated from club.nsdn.nyasamarailway.entity.MinecartBase / TrainBase.
 */
public class MinecartData {

    private CartType cartType;
    private int dir;
    private int power;
    private int brakeResistance;
    private double velocity;
    private double nextVelocity;
    private double yaw;
    private double prevYaw;
    private boolean highSpeed;
    private boolean mode;
    private String extendedInfo;
    private String customName;
    private int maxPassengers;
    private String rfidTag;

    public MinecartData(CartType cartType) {
        this.cartType = cartType;
        reset();
    }

    public void reset() {
        this.dir = 0;
        this.power = 0;
        this.brakeResistance = 1;
        this.velocity = 0;
        this.nextVelocity = 0;
        this.yaw = 0;
        this.prevYaw = 0;
        this.highSpeed = false;
        this.mode = false;
        this.extendedInfo = "";
        this.customName = "";
        this.maxPassengers = 1;
        this.rfidTag = "";
    }

    // --- Getters/Setters ---

    public CartType getCartType() { return cartType; }
    public void setCartType(CartType cartType) { this.cartType = cartType; }

    public int getDir() { return dir; }
    public void setDir(int dir) { this.dir = Math.max(-1, Math.min(1, dir)); }

    public int getPower() { return power; }
    public void setPower(int power) { this.power = Math.max(0, Math.min(20, power)); }

    public int getBrakeResistance() { return brakeResistance; }
    public void setBrakeResistance(int brakeResistance) {
        this.brakeResistance = Math.max(1, Math.min(10, brakeResistance));
    }

    public double getVelocity() { return velocity; }
    public void setVelocity(double velocity) { this.velocity = velocity; }

    public double getNextVelocity() { return nextVelocity; }
    public void setNextVelocity(double nextVelocity) { this.nextVelocity = nextVelocity; }

    public double getYaw() { return yaw; }
    public void setYaw(double yaw) { this.yaw = yaw; }

    public double getPrevYaw() { return prevYaw; }
    public void setPrevYaw(double prevYaw) { this.prevYaw = prevYaw; }

    public boolean isHighSpeed() { return highSpeed; }
    public void setHighSpeed(boolean highSpeed) { this.highSpeed = highSpeed; }

    public boolean isMode() { return mode; }
    public void setMode(boolean mode) { this.mode = mode; }

    public String getExtendedInfo() { return extendedInfo; }
    public void setExtendedInfo(String extendedInfo) {
        this.extendedInfo = extendedInfo != null ? extendedInfo : "";
    }

    public String getCustomName() { return customName; }
    public void setCustomName(String customName) {
        this.customName = customName != null ? customName : "";
    }

    public int getMaxPassengers() { return maxPassengers; }
    public void setMaxPassengers(int maxPassengers) {
        this.maxPassengers = Math.max(0, maxPassengers);
    }

    public String getRfidTag() { return rfidTag; }
    public void setRfidTag(String rfidTag) {
        this.rfidTag = rfidTag != null ? rfidTag : "";
    }

    // --- NBT Serialization (platform-agnostic using Map) ---

    public Map<String, Object> toNbtMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("cartType", cartType.getId());
        map.put("dir", dir);
        map.put("power", power);
        map.put("brakeR", brakeResistance);
        map.put("velocity", velocity);
        map.put("highSpeed", highSpeed);
        map.put("mode", mode);
        map.put("extInfo", extendedInfo);
        map.put("name", customName);
        map.put("maxP", maxPassengers);
        map.put("rfid", rfidTag);
        return map;
    }

    public void fromNbtMap(Map<String, Object> map) {
        if (map == null) return;
        Object ct = map.get("cartType");
        if (ct instanceof String) {
            for (CartType t : CartType.values()) {
                if (t.getId().equals(ct)) { cartType = t; break; }
            }
        }
        if (map.containsKey("dir")) dir = ((Number) map.get("dir")).intValue();
        if (map.containsKey("power")) power = ((Number) map.get("power")).intValue();
        if (map.containsKey("brakeR")) brakeResistance = ((Number) map.get("brakeR")).intValue();
        if (map.containsKey("velocity")) velocity = ((Number) map.get("velocity")).doubleValue();
        if (map.containsKey("highSpeed")) highSpeed = (Boolean) map.get("highSpeed");
        if (map.containsKey("mode")) mode = (Boolean) map.get("mode");
        if (map.containsKey("extInfo")) extendedInfo = (String) map.get("extInfo");
        if (map.containsKey("name")) customName = (String) map.get("name");
        if (map.containsKey("maxP")) maxPassengers = ((Number) map.get("maxP")).intValue();
        if (map.containsKey("rfid")) rfidTag = (String) map.get("rfid");
    }
}
