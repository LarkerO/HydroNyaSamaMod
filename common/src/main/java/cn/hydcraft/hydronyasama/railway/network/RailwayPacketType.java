package cn.hydcraft.hydronyasama.railway.network;

/**
 * Types of railway network packets.
 */
public enum RailwayPacketType {
    TRAIN_CONTROL,
    TRAIN_SYNC,
    SIGNAL_UPDATE,
    RFID_QUERY,
    RFID_RESPONSE
}
