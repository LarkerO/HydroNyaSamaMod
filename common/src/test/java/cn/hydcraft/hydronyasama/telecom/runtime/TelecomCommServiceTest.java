package cn.hydcraft.hydronyasama.telecom.runtime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class TelecomCommServiceTest {

  private final TelecomCommService service = TelecomCommService.getInstance();

  @AfterEach
  void tearDown() {
    service.reset();
  }

  @Test
  void connectsInputToOutputAndDisconnectsOnSecondLink() {
    assertEquals(
        TelecomCommService.ConnectResult.CONNECTED,
        service.connect("input-a", "signal_box_input", "output-a", "signal_box_output"));

    service.setInputFromRedstone("input-a", "signal_box_input", true);
    service.tick();
    service.tick();

    assertTrue(service.redstoneOutputPowered("output-a", "signal_box_output"));

    assertEquals(
        TelecomCommService.ConnectResult.DISCONNECTED,
        service.connect("input-a", "signal_box_input", "output-a", "signal_box_output"));

    service.setInputFromRedstone("input-a", "signal_box_input", false);
    service.tick();
    service.tick();

    assertFalse(service.redstoneOutputPowered("output-a", "signal_box_output"));
  }

  @Test
  void scriptEngineAppliesAndSanitizesNspgaProfileCommands() {
    List<String> logs =
        TelecomNgScriptEngine.run(
            "nspga_io 0\n"
                + "nspga_inputs in_a, in_b , , in_c\n"
                + "nspga_outputs out_a, out_b\n"
                + "nspga_code mov a b\n"
                + "nspga_show\n"
                + "snapshot",
            "nspga-endpoint",
            "nspga_flex",
            service);

    TelecomCommService.NspgaProfile profile = service.nspgaProfile("nspga-endpoint", "nspga_flex");
    assertEquals(1, profile.ioCount);
    assertIterableEquals(Arrays.asList("in_a", "in_b", "in_c"), profile.inputs);
    assertIterableEquals(Arrays.asList("out_a", "out_b"), profile.outputs);
    assertEquals("mov a b", profile.code);
    assertEquals("nspga:io=1;inputs=in_a,in_b,in_c;outputs=out_a,out_b;codeLen=7", logs.get(4));
    assertEquals(
        "kind=SIGNAL_BOX;enabled=false;input=false;output=false;sender=;target=;transceiver=",
        logs.get(5));
  }

  @Test
  void manualUseSupportsTriStateLatchControl() {
    assertEquals(
        TelecomCommService.ConnectResult.CONNECTED,
        service.connect("tri-a", "tri_state_signal_box", "latch-a", "rs_latch"));
    assertEquals(
        TelecomCommService.ConnectResult.CONNECTED,
        service.connect("latch-a", "rs_latch", "output-a", "signal_box_output"));

    service.handleManualUse("tri-a", "tri_state_signal_box", false);
    service.tick();
    service.tick();

    assertTrue(service.redstoneOutputPowered("output-a", "signal_box_output"));

    service.applyEditorState("tri-a", "tri_state_signal_box", 1, false);
    service.handleManualUse("tri-a", "tri_state_signal_box", false);
    service.handleManualUse("tri-a", "tri_state_signal_box", false);
    service.tick();
    service.tick();

    assertFalse(service.redstoneOutputPowered("output-a", "signal_box_output"));
  }
}
