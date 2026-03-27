package cn.hydcraft.hydronyasama.railway.event;

import cn.hydcraft.hydronyasama.railway.entity.MinecartData;
import cn.hydcraft.hydronyasama.railway.util.NtpMotion;

/**
 * NTP (NyaSama Train Protocol) control handler. Processes keyboard input and translates to train
 * control commands. Migrated from club.nsdn.nyasamarailway.event.NTPCtrlHandler /
 * TrainController.doControl.
 *
 * <p>This is platform-agnostic - keyboard binding is done in platform modules.
 */
public final class NtpControlHandler {

  private NtpControlHandler() {}

  /**
   * Process direction up command.
   *
   * @return true if direction was changed
   */
  public static boolean directionUp(MinecartData data) {
    if (data.getPower() > 0) return false;
    int dir = data.getDir();
    if (dir == -1) {
      data.setDir(0);
      return true;
    }
    if (dir == 0) {
      data.setDir(1);
      return true;
    }
    return false;
  }

  /**
   * Process direction down command.
   *
   * @return true if direction was changed
   */
  public static boolean directionDown(MinecartData data) {
    if (data.getPower() > 0) return false;
    int dir = data.getDir();
    if (dir == 1) {
      data.setDir(0);
      return true;
    }
    if (dir == 0) {
      data.setDir(-1);
      return true;
    }
    return false;
  }

  /** Increase power by 1. Returns new power value. */
  public static int powerUp(MinecartData data) {
    int p = data.getPower();
    if (p < NtpMotion.MAX_POWER) p++;
    data.setPower(p);
    return p;
  }

  /** Decrease power by 1. Returns new power value. */
  public static int powerDown(MinecartData data) {
    int p = data.getPower();
    if (p > 0) p--;
    data.setPower(p);
    return p;
  }

  /** Increase brake (lower brake release). Returns new brake value. */
  public static int brakeUp(MinecartData data) {
    int r = data.getBrakeResistance();
    if (r < 10) r++;
    data.setBrakeResistance(r);
    return r;
  }

  /** Decrease brake (higher brake release). Returns new brake value. */
  public static int brakeDown(MinecartData data) {
    int r = data.getBrakeResistance();
    if (r > 1) r--;
    data.setBrakeResistance(r);
    return r;
  }

  /** Toggle high-speed mode. Returns new mode state. */
  public static boolean toggleMode(MinecartData data) {
    boolean m = !data.isMode();
    data.setMode(m);
    return m;
  }
}
