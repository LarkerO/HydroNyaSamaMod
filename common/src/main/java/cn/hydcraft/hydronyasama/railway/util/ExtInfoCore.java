package cn.hydcraft.hydronyasama.railway.util;

/**
 * Utility for encoding/decoding extended cart information.
 * Migrated from club.nsdn.nyasamarailway.util.ExtInfoCore.
 */
public final class ExtInfoCore {

    private ExtInfoCore() {}

    /** Separator used in encoded info strings. */
    public static final String SEPARATOR = "|";

    /**
     * Encode multiple values into a single info string.
     */
    public static String encode(String... values) {
        return String.join(SEPARATOR, values);
    }

    /**
     * Decode an info string into parts.
     */
    public static String[] decode(String encoded) {
        if (encoded == null || encoded.isEmpty()) return new String[0];
        return encoded.split("\\|");
    }

    /**
     * Get a specific field from an encoded info string, or default if missing.
     */
    public static String getField(String encoded, int index, String defaultValue) {
        String[] parts = decode(encoded);
        if (index < 0 || index >= parts.length) return defaultValue;
        return parts[index];
    }

    /**
     * Get a specific field as integer, or default if missing/invalid.
     */
    public static int getIntField(String encoded, int index, int defaultValue) {
        try {
            return Integer.parseInt(getField(encoded, index, ""));
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }
}
