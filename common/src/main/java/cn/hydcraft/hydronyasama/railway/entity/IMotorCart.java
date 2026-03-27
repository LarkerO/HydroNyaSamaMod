package cn.hydcraft.hydronyasama.railway.entity;

/**
 * Marker interface for motor-powered carts that provide their own traction.
 * Migrated from club.nsdn.nyasamarailway.entity.IMotorCart
 */
public interface IMotorCart {
    void setMotorPower(double power);
    double getMotorPower();
    void setMotorBrake(double brake);
    double getMotorBrake();
    void setMotorState(boolean enabled);
    boolean getMotorState();
}
