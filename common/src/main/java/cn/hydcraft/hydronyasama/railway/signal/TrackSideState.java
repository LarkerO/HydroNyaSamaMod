package cn.hydcraft.hydronyasama.railway.signal;

/**
 * State of a track-side signal device (blocking, RFID, reception, sniffer).
 */
public final class TrackSideState {
    private boolean powered;
    private int timer;
    private String storedData;
    private int signalStrength;

    public TrackSideState() {
        this.powered = false;
        this.timer = 0;
        this.storedData = "";
        this.signalStrength = 0;
    }

    public boolean isPowered() { return powered; }
    public void setPowered(boolean powered) { this.powered = powered; }
    public int getTimer() { return timer; }
    public void setTimer(int timer) { this.timer = timer; }
    public void decrementTimer() { if (timer > 0) timer--; }
    public String getStoredData() { return storedData; }
    public void setStoredData(String data) { this.storedData = data != null ? data : ""; }
    public int getSignalStrength() { return signalStrength; }
    public void setSignalStrength(int strength) { this.signalStrength = Math.min(15, Math.max(0, strength)); }
}
