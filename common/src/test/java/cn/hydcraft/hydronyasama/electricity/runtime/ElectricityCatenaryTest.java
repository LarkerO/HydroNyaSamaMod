package cn.hydcraft.hydronyasama.electricity.runtime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class ElectricityCatenaryTest {

  private static final float EPSILON = 1.0E-2F;

  @Test
  void solvesCableLengthBackToRequestedDistance() {
    float spanY = 3.0F;
    float drop = 2.0F;
    float distance = 12.0F;

    float cableLength = ElectricityCatenary.calcCableLength(spanY, drop, distance);

    assertTrue(cableLength > distance);
    assertEquals(distance, ElectricityCatenary.calcSpanDistance(spanY, cableLength, drop), EPSILON);
  }

  @Test
  void preservesEndpointsForAscendingSpan() {
    ElectricityCatenary catenary = new ElectricityCatenary(6.0F, 9.0F, 12.0F, 2.0F);

    assertEquals(6.0F, catenary.apply(0.0F), EPSILON);
    assertEquals(9.0F, catenary.apply(12.0F), EPSILON);
    assertTrue(catenary.derivative(0.0F) < 0.0F);
    assertTrue(catenary.derivative(12.0F) > 0.0F);
  }

  @Test
  void staysSymmetricForFlatSpan() {
    ElectricityCatenary catenary = new ElectricityCatenary(5.0F, 5.0F, 10.0F, 2.0F);

    assertEquals(5.0F, catenary.apply(0.0F), EPSILON);
    assertEquals(5.0F, catenary.apply(10.0F), EPSILON);
    assertEquals(catenary.apply(1.0F), catenary.apply(9.0F), EPSILON);
    assertEquals(catenary.derivative(4.0F), -catenary.derivative(6.0F), EPSILON);
  }
}
