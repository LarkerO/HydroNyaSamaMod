package cn.hydcraft.hydronyasama.railway.signal;

/** Represents the state of a railway signal light. */
public enum SignalState {
  RED(0),
  YELLOW(1),
  GREEN(2),
  OFF(-1);

  private final int level;

  SignalState(int level) {
    this.level = level;
  }

  public int getLevel() {
    return level;
  }

  public static SignalState fromLevel(int level) {
    for (SignalState s : values()) {
      if (s.level == level) return s;
    }
    return OFF;
  }

  public static SignalState fromRedstone(int power) {
    if (power == 0) return RED;
    if (power <= 7) return YELLOW;
    return GREEN;
  }
}
