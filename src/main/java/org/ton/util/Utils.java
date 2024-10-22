package org.ton.util;

import org.ton.exception.TONAPIBadRequestError;
import org.ton.exception.TONAPIError;
import org.ton.exception.TONAPIInternalServerError;
import org.ton.exception.TONAPINotFoundError;
import org.ton.exception.TONAPINotImplementedError;
import org.ton.exception.TONAPITooManyRequestsError;
import org.ton.exception.TONAPIUnauthorizedError;

import java.nio.ByteBuffer;
import java.util.Base64;
import java.util.HexFormat;

public class Utils {
    private static int calculateCrcXmodem(byte[] payload) {
        int crc = 0;
        int poly = 0x1021;  // CRC-16 Xmodem polynomial

        for (byte b : payload) {
            crc ^= (b & 0xFF) << 8;

            for (int i = 0; i < 8; i++) {
                if ((crc & 0x8000) != 0) {
                    crc = ((crc << 1) ^ poly);
                } else {
                    crc <<= 1;
                }
            }
            crc &= 0xFFFF;  // Ensure crc is 16-bit
        }

        return crc & 0xFFFF;
    }

    public static String rawToUserFriendly(String address, boolean isBounceable) {
        int tag = isBounceable ? 0x11 : 0x51;
        String[] parts = address.split(":");
        long workchainId = Long.parseLong(parts[0]);
        byte[] key = HexFormat.of().parseHex(parts[1]);

        ByteBuffer payload = ByteBuffer.allocate(34); // tag (1 byte) + workchainId (1 byte) + key (32 bytes)
        payload.put((byte) tag);
        payload.put((byte) workchainId);
        payload.put(key);

        byte[] payloadArray = payload.array();
        int crc = calculateCrcXmodem(payloadArray);

        ByteBuffer encodedBuffer = ByteBuffer.allocate(36); // payload + crc (2 bytes)
        encodedBuffer.put(payloadArray);
        encodedBuffer.putShort((short) crc);

        byte[] encodedKey = encodedBuffer.array();
        String base64Address = Base64.getUrlEncoder().withoutPadding().encodeToString(encodedKey);

        return base64Address;
    }

    public static String userFriendlyToRaw(String address) {
        byte[] decoded = Base64.getUrlDecoder().decode(address);

        ByteBuffer buffer = ByteBuffer.wrap(decoded, 1, 33); // skip tag, get workchainId and key
        byte workchainId = buffer.get();
        byte[] keyBytes = new byte[32];
        buffer.get(keyBytes);

        String keyHex = HexFormat.of().withLowerCase().formatHex(keyBytes);
        return workchainId + ":" + keyHex;
    }

    public static Number toAmount(long value, int decimals, int precision) {
        if (value < 0) {
            throw new IllegalArgumentException("Value must be a positive integer.");
        }
        if (precision < 0) {
            throw new IllegalArgumentException("Precision must be a non-negative integer.");
        }

        double tonValue = value / Math.pow(10, decimals);
        double scale = Math.pow(10, precision);
        double roundedValue = Math.round(tonValue * scale) / scale;

        if (roundedValue % 1 == 0) {
            return (long) roundedValue;
        } else {
            return roundedValue;
        }
    }

    public static long toNano(Number value, int decimals) {
        if (!(value instanceof Integer || value instanceof Long || value instanceof Float || value instanceof Double)) {
            throw new IllegalArgumentException("Value must be a positive integer or float.");
        }

        double doubleValue = value.doubleValue();
        return (long) (doubleValue * Math.pow(10, decimals));
    }

    public static TONAPIError mapStatusCodeToException(int statusCode, String message) {
        return switch (statusCode) {
            case 400 -> new TONAPIBadRequestError(message);
            case 401 -> new TONAPIUnauthorizedError(message);
            case 403, 500 -> new TONAPIInternalServerError(message);
            case 404 -> new TONAPINotFoundError(message);
            case 429 -> new TONAPITooManyRequestsError(message);
            case 501 -> new TONAPINotImplementedError(message);
            default -> new TONAPIError(message);
        };
    }
}
