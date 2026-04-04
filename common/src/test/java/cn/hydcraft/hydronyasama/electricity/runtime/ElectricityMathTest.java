package cn.hydcraft.hydronyasama.electricity.runtime;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cn.hydcraft.hydronyasama.core.physics.Vec3;
import org.junit.jupiter.api.Test;

class ElectricityMathTest {

  private static final double EPSILON = 1.0E-5D;

  @Test
  void convertsDegreesToTrigonometricValues() {
    assertEquals(1.0D, ElectricityMath.cosAngle(0.0F), EPSILON);
    assertEquals(0.0D, ElectricityMath.cosAngle(90.0F), EPSILON);
    assertEquals(1.0D, ElectricityMath.sinAngle(90.0F), EPSILON);
    assertEquals(-1.0D, ElectricityMath.sinAngle(270.0F), EPSILON);
  }

  @Test
  void matchesHyperbolicInverseFunctions() {
    assertEquals(Math.log(1.5D + Math.sqrt(1.5D * 1.5D + 1.0D)), ElectricityMath.asinh(1.5F), EPSILON);
    assertEquals(Math.log(2.0D + Math.sqrt(3.0D)), ElectricityMath.acosh(2.0F), EPSILON);
    assertEquals(0.5D * Math.log(3.0D), ElectricityMath.atanh(0.5F), EPSILON);
  }

  @Test
  void calculatesDistanceForCoordinatesAndVectors() {
    assertEquals(13.0D, ElectricityMath.distanceOf(0.0D, 0.0D, 0.0D, 3.0D, 4.0D, 12.0D), EPSILON);
    assertEquals(
        13.0D,
        ElectricityMath.distanceOf(new Vec3(0.0D, 0.0D, 0.0D), new Vec3(3.0D, 4.0D, 12.0D)),
        EPSILON);
  }
}
