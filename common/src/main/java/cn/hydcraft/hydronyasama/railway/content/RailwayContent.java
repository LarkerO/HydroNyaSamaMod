package cn.hydcraft.hydronyasama.railway.content;

import cn.hydcraft.hydronyasama.core.registry.ContentId;
import cn.hydcraft.hydronyasama.core.registry.ContentRegistrar;
import java.util.Arrays;
import java.util.List;

public final class RailwayContent {

  private static final String GROUP = "railway";

  // -- Decoration blocks (obj_collision) ------------------------------------------------

  private static final List<String> DECORATION_BLOCK_IDS =
      Arrays.asList(
          "nsr_sign",
          "track_plate",
          "track_shelf",
          "track_shelf_low",
          "pier_tag",
          "rail_iron_bars",
          "rail_iron_web",
          "t_bridge_head",
          "t_bridge_head_no_rib",
          "t_bridge_body",
          "t_bridge_body_no_rib",
          "t_bridge_shoulder",
          "nsr_half_block",
          "nsr_half_half_block",
          "platform",
          "nsr_edge",
          "pillar",
          "signal_pillar",
          "station_sign");

  // -- Rail blocks (rail) ---------------------------------------------------------------

  private static final List<String> RAIL_BLOCK_IDS =
      Arrays.asList(
          "rail_stone_sleeper",
          "rail_no_sleeper",
          "rail_stone_sleeper_powered",
          "rail_no_sleeper_powered",
          "rail_stone_sleeper_detector",
          "rail_no_sleeper_detector",
          "rail_rfid",
          "rail_no_sleeper_rfid",
          "rail_stone_sleeper_detector_5s",
          "rail_no_sleeper_detector_5s",
          "rail_stone_sleeper_detector_15s",
          "rail_no_sleeper_detector_15s",
          "rail_stone_sleeper_detector_30s",
          "rail_no_sleeper_detector_30s",
          "rail_protect_head",
          "rail_protect_head_anti",
          "rail_protect_body",
          "rail_reception",
          "rail_reception_anti",
          "rail_signal_transfer",
          "rail_speed_limit",
          "rail_directional",
          "rail_directional_anti",
          "rail_blocking",
          "rail_no_sleeper_blocking",
          "rail_sniffer",
          "rail_redstone",
          "bumper_stone_sleeper",
          "bumper_no_sleeper",
          "conv_wire_mono",
          "rail_3rd",
          "rail_3rd_switch",
          "rail_magnet_switch",
          "rail_tri_switch");

  // -- Mono rail blocks (rail) ----------------------------------------------------------

  private static final List<String> MONO_RAIL_BLOCK_IDS =
      Arrays.asList(
          "rail_mono",
          "rail_mono_bumper",
          "rail_mono_magnet",
          "rail_mono_switch",
          "rail_mono_magnet_detector",
          "rail_mono_magnet_detector_5s",
          "rail_mono_magnet_detector_15s",
          "rail_mono_magnet_detector_30s",
          "rail_mono_magnet_powered",
          "rail_mono_magnet_reception",
          "rail_mono_magnet_reception_anti",
          "rail_mono_magnet_directional",
          "rail_mono_magnet_directional_anti",
          "rail_mono_magnet_speed_limit",
          "rail_mono_magnet_signal_transfer",
          "rail_mono_magnet_blocking",
          "rail_mono_magnet_rfid",
          "rail_mono_magnet_sniffer",
          "rail_mono_magnet_redstone");

  // -- Signal blocks (obj_collision) ----------------------------------------------------

  private static final List<String> SIGNAL_BLOCK_IDS =
      Arrays.asList(
          "signal_light",
          "signal_lamp",
          "signal_stick",
          "bi_signal_light",
          "tri_signal_light",
          "pillar_signal_one",
          "pillar_signal_bi",
          "pillar_signal_tri",
          "signal_box",
          "signal_box_sender",
          "tri_state_signal_box");

  // -- Sign blocks (obj_collision) ------------------------------------------------------

  private static final List<String> SIGN_BLOCK_IDS =
      Arrays.asList(
          "rail_sign_body",
          "rail_sign_head_beep",
          "rail_sign_head_cut",
          "rail_sign_head_joe",
          "rail_sign_head_link",
          "rail_sign_head_cutlink",
          "rail_sign_head_t",
          "rail_sign_vertical_1",
          "rail_sign_vertical_2",
          "rail_sign_vertical_3",
          "rail_sign_vertical_4",
          "rail_sign_vertical_5",
          "rail_sign_vertical_6",
          "rail_sign_vertical_7",
          "rail_sign_vertical_8",
          "rail_sign_vertical_9",
          "rail_sign_vertical_10",
          "rail_sign_vertical_11");

  // -- Glass shield blocks (obj_collision) ----------------------------------------------

  private static final List<String> GLASS_SHIELD_BLOCK_IDS =
      Arrays.asList(
          "glass_shield",
          "glass_shield_half",
          "glass_shield_1x1",
          "glass_shield_3x1",
          "glass_shield_3x1d5",
          "glass_shield_al",
          "glass_shield_al_half",
          "glass_shield_al_base",
          "glass_shield_corner",
          "glass_shield_corner_half");

  // -- Gate & functional blocks (obj_collision) -----------------------------------------

  private static final List<String> GATE_BLOCK_IDS =
      Arrays.asList(
          "gate_base",
          "gate_door",
          "gate_front",
          "gate_front_n",
          "ticket_block_once",
          "ticket_block_card",
          "coin_block");

  // -- Trackside signal blocks (obj_collision) ------------------------------------------

  private static final List<String> TRACKSIDE_BLOCK_IDS =
      Arrays.asList(
          "trackside_blocking",
          "trackside_reception",
          "trackside_rfid",
          "trackside_sniffer",
          "trackside_blocking_hs",
          "trackside_rfid_hs",
          "trackside_sniffer_hs");

  // -- Test blocks (cube) ---------------------------------------------------------------

  private static final List<String> TEST_BLOCK_IDS =
      Arrays.asList("ns_test");

  private RailwayContent() {}

  public static void register(ContentRegistrar registrar) {
    for (String idPath : DECORATION_BLOCK_IDS) {
      registerObjCollision(registrar, idPath, "iron");
    }
    for (String idPath : RAIL_BLOCK_IDS) {
      registerRail(registrar, idPath);
    }
    for (String idPath : MONO_RAIL_BLOCK_IDS) {
      registerRail(registrar, idPath);
    }
    for (String idPath : SIGNAL_BLOCK_IDS) {
      registerObjCollision(registrar, idPath, "iron");
    }
    for (String idPath : SIGN_BLOCK_IDS) {
      registerObjCollision(registrar, idPath, "iron");
    }
    for (String idPath : GLASS_SHIELD_BLOCK_IDS) {
      registerObjCollision(registrar, idPath, "glass");
    }
    for (String idPath : GATE_BLOCK_IDS) {
      registerObjCollision(registrar, idPath, "iron");
    }
    for (String idPath : TRACKSIDE_BLOCK_IDS) {
      registerObjCollision(registrar, idPath, "iron");
    }
    for (String idPath : TEST_BLOCK_IDS) {
      registerCube(registrar, idPath);
    }
  }

  private static void registerObjCollision(
      ContentRegistrar registrar, String idPath, String material) {
    ContentId id = ContentId.of("hydronyasama", idPath);
    registrar.registerBlock(
        new ContentRegistrar.BlockDefinition(
            id, GROUP, "obj_collision", material, idPath, 0, null));
    registrar.registerItem(new ContentRegistrar.ItemDefinition(id, GROUP, "block_item", id));
  }

  private static void registerRail(ContentRegistrar registrar, String idPath) {
    ContentId id = ContentId.of("hydronyasama", idPath);
    registrar.registerBlock(
        new ContentRegistrar.BlockDefinition(id, GROUP, "rail", "iron", idPath, 0, null));
    registrar.registerItem(new ContentRegistrar.ItemDefinition(id, GROUP, "block_item", id));
  }

  private static void registerCube(ContentRegistrar registrar, String idPath) {
    ContentId id = ContentId.of("hydronyasama", idPath);
    registrar.registerBlock(
        new ContentRegistrar.BlockDefinition(id, GROUP, "cube", "iron", idPath, 0, null));
    registrar.registerItem(new ContentRegistrar.ItemDefinition(id, GROUP, "block_item", id));
  }
}
