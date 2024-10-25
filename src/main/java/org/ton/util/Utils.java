package org.ton.util;

import java.nio.ByteBuffer;
import java.util.Base64;
import org.ton.exception.TONAPIBadRequestError;
import org.ton.exception.TONAPIError;
import org.ton.exception.TONAPIInternalServerError;
import org.ton.exception.TONAPINotFoundError;
import org.ton.exception.TONAPINotImplementedError;
import org.ton.exception.TONAPITooManyRequestsError;
import org.ton.exception.TONAPIUnauthorizedError;

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
    byte[] key = hexStringToByteArray(parts[1]);

    ByteBuffer payload = ByteBuffer.allocate(
        34); // tag (1 byte) + workchainId (1 byte) + key (32 bytes)
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

  /**
   * Converts a hexadecimal string to a byte array.
   *
   * @param s the hexadecimal string to be converted
   * @return a byte array corresponding to the given hexadecimal string
   * @throws IllegalArgumentException if the string has an odd length or contains invalid
   *                                  characters
   */
  private static byte[] hexStringToByteArray(String s) {
    int len = s.length();
    if (len % 2 != 0) {
      throw new IllegalArgumentException("Invalid length of the hexadecimal string");
    }
    byte[] data = new byte[len / 2];
    for (int i = 0; i < len; i += 2) {
      int firstDigit = Character.digit(s.charAt(i), 16);
      int secondDigit = Character.digit(s.charAt(i + 1), 16);
      if (firstDigit == -1 || secondDigit == -1) {
        throw new IllegalArgumentException("Invalid character in the hexadecimal string");
      }
      data[i / 2] = (byte) ((firstDigit << 4) + secondDigit);
    }
    return data;
  }

  public static String userFriendlyToRaw(String address) {
    byte[] decoded = Base64.getUrlDecoder().decode(address);

    ByteBuffer buffer = ByteBuffer.wrap(decoded, 1, 33); // skip tag, get workchainId and key
    byte workchainId = buffer.get();
    byte[] keyBytes = new byte[32];
    buffer.get(keyBytes);

    String keyHex = bytesToHex(keyBytes).toLowerCase();
    return workchainId + ":" + keyHex;
  }

  /**
   * Converts a byte array to a hexadecimal string.
   *
   * @param bytes The byte array to convert.
   * @return The resulting hexadecimal string.
   */
  private static String bytesToHex(byte[] bytes) {
    char[] hexArray = "0123456789abcdef".toCharArray();
    char[] hexChars = new char[bytes.length * 2];
    for (int j = 0; j < bytes.length; j++) {
      int v = bytes[j] & 0xFF;
      hexChars[j * 2] = hexArray[v >>> 4];
      hexChars[j * 2 + 1] = hexArray[v & 0x0F];
    }
    return new String(hexChars);
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
    if (!(value instanceof Integer || value instanceof Long || value instanceof Float
        || value instanceof Double)) {
      throw new IllegalArgumentException("Value must be a positive integer or float.");
    }

    double doubleValue = value.doubleValue();
    return (long) (doubleValue * Math.pow(10, decimals));
  }

  public static TONAPIError mapStatusCodeToException(int statusCode, String message) {
    switch (statusCode) {
      case 400:
        return new TONAPIBadRequestError(message);
      case 401:
        return new TONAPIUnauthorizedError(message);
      case 403:
        return new TONAPIInternalServerError(message);
      case 404:
        return new TONAPINotFoundError(message);
      case 429:
        return new TONAPITooManyRequestsError(message);
      case 500:
        return new TONAPIInternalServerError(message);
      case 501:
        return new TONAPINotImplementedError(message);
    }
    return new TONAPIError(message);
  }
}
