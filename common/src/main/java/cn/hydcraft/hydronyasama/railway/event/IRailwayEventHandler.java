package cn.hydcraft.hydronyasama.railway.event;

/** Combined interface for railway event handling. Platform-specific modules implement this. */
public interface IRailwayEventHandler {

  /** Called when a player interacts with a railway entity. */
  void onEntityInteract(Object player, Object entity);

  /** Called each server tick for train control processing. */
  void onServerTick();
}
