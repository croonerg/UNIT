package no.unit.transformer.utils;

import java.util.Locale;

public class FileUtils {

  public enum FILE_FORMAT {
    XML,
    JSON,
    UNKNOWN,
  }

  public static FILE_FORMAT getFileFormat(String format) {
    try {
      return FILE_FORMAT.valueOf(format.toUpperCase(Locale.getDefault()));
    } catch (IllegalArgumentException e) {
      return FILE_FORMAT.UNKNOWN;
    }
  }
}
