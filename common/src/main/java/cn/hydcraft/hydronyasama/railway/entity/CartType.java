package cn.hydcraft.hydronyasama.railway.entity;

/** Enumeration of all railway entity types from NyaSamaRailway. */
public enum CartType {
  // Standard carts
  NSBT1("nsbt1", "NSBT-1", false, false),
  NSPCT1("nspct1", "NSPCT-1", false, false),
  NSPCT2("nspct2", "NSPCT-2", false, false),
  NSPCT3("nspct3", "NSPCT-3", false, false),
  NSPCT4("nspct4", "NSPCT-4", true, false),
  NSPCT5("nspct5", "NSPCT-5", true, false),
  NSPCT5L("nspct5l", "NSPCT-5L", true, false),
  NSPCT6("nspct6", "NSPCT-6", true, false),
  NSPCT6W("nspct6w", "NSPCT-6W", true, false),
  NSPCT8("nspct8", "NSPCT-8", true, false),
  NSPCT8W("nspct8w", "NSPCT-8W", true, false),
  NSPCT9("nspct9", "NSPCT-9", true, false),
  NSPCT10("nspct10", "NSPCT-10", true, false),
  NSTCT1("nstct1", "NSTCT-1", false, false),

  // Locomotives
  NSET1("nset1", "NSET-1", true, true),
  NSET2("nset2", "NSET-2", true, true),
  NSPCT4M("nspct4m", "NSPCT-4M", true, true),
  NSPCT6C("nspct6c", "NSPCT-6C", true, true),
  NSPCT6L("nspct6l", "NSPCT-6L", true, true),
  NSPCT7("nspct7", "NSPCT-7", true, true),
  NSPCT8C("nspct8c", "NSPCT-8C", true, true),
  NSPCT8J("nspct8j", "NSPCT-8J", true, true),
  NSPCT8M("nspct8m", "NSPCT-8M", true, true),
  NSPCT9M("nspct9m", "NSPCT-9M", true, true),
  NSPCT10J("nspct10j", "NSPCT-10J", true, true),
  NSPCT10M("nspct10m", "NSPCT-10M", true, true),

  // NSC carts (NyaSamaCore design carts)
  NSC1A("nsc1a", "NSC-1A", true, false),
  NSC1AM("nsc1am", "NSC-1AM", true, true),
  NSC1B("nsc1b", "NSC-1B", true, false),
  NSC1BM("nsc1bm", "NSC-1BM", true, true),
  NSC2A("nsc2a", "NSC-2A", true, false),
  NSC2AM("nsc2am", "NSC-2AM", true, true),
  NSC2B("nsc2b", "NSC-2B", true, false),
  NSC2BM("nsc2bm", "NSC-2BM", true, true),
  NSC3A("nsc3a", "NSC-3A", true, false),
  NSC3AM("nsc3am", "NSC-3AM", true, true),
  NSC3B("nsc3b", "NSC-3B", true, false),
  NSC3BM("nsc3bm", "NSC-3BM", true, true);

  private final String id;
  private final String displayName;
  private final boolean motorized;
  private final boolean locomotive;

  CartType(String id, String displayName, boolean motorized, boolean locomotive) {
    this.id = id;
    this.displayName = displayName;
    this.motorized = motorized;
    this.locomotive = locomotive;
  }

  public String getId() {
    return id;
  }

  public String getDisplayName() {
    return displayName;
  }

  public boolean isMotorized() {
    return motorized;
  }

  public boolean isLocomotive() {
    return locomotive;
  }
}
