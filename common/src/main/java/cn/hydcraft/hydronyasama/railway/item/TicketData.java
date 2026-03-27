package cn.hydcraft.hydronyasama.railway.item;

/**
 * Platform-agnostic ticket data model. Migrated from club.nsdn.nyasamarailway.item.ItemTicketBase.
 */
public final class TicketData {

  public enum TicketType {
    SINGLE_USE,
    STORED_VALUE
  }

  private final TicketType type;
  private String source;
  private String destination;
  private int remainingUses;
  private int value;

  public TicketData(TicketType type) {
    this.type = type;
    this.source = "";
    this.destination = "";
    this.remainingUses = type == TicketType.SINGLE_USE ? 1 : 0;
    this.value = type == TicketType.STORED_VALUE ? 100 : 0;
  }

  public TicketType getType() {
    return type;
  }

  public String getSource() {
    return source;
  }

  public void setSource(String source) {
    this.source = source != null ? source : "";
  }

  public String getDestination() {
    return destination;
  }

  public void setDestination(String destination) {
    this.destination = destination != null ? destination : "";
  }

  public int getRemainingUses() {
    return remainingUses;
  }

  public void setRemainingUses(int uses) {
    this.remainingUses = Math.max(0, uses);
  }

  public int getValue() {
    return value;
  }

  public void setValue(int value) {
    this.value = Math.max(0, value);
  }

  /** Try to consume one use. Returns true on success. */
  public boolean consume() {
    if (type == TicketType.SINGLE_USE && remainingUses > 0) {
      remainingUses--;
      return true;
    }
    if (type == TicketType.STORED_VALUE && value > 0) {
      value--;
      return true;
    }
    return false;
  }

  /** Check if this ticket is valid for travel. */
  public boolean isValid() {
    if (type == TicketType.SINGLE_USE) return remainingUses > 0;
    if (type == TicketType.STORED_VALUE) return value > 0;
    return false;
  }
}
