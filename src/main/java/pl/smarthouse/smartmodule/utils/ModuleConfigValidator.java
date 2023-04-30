package pl.smarthouse.smartmodule.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.function.Consumer;
import org.apache.commons.lang3.StringUtils;
import pl.smarthouse.smartmodule.model.configuration.Configuration;

public interface ModuleConfigValidator extends Consumer<Configuration> {
  String WRONG_FORMAT = "Wrong format. Accepted is yyyyMMdd.xx Now is %s";

  Consumer<String> isVersionOrFirmwareFormatCorrect =
      version -> {
        // Check version format
        // Check length
        if (version.length() != 11 || version.charAt(8) != '.') {
          throw new IllegalArgumentException(String.format(WRONG_FORMAT, version));
        }

        // Check sub version
        String subVersion = version.substring(9);
        if (!StringUtils.isNumeric(subVersion)) {
          throw new IllegalArgumentException(String.format(WRONG_FORMAT, version));
        }

        // Check date format
        try {
          final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
          String date = version.substring(0, 8);
          LocalDate.parse(date, formatter);
        } catch (Exception e) {
          throw new IllegalArgumentException(String.format(WRONG_FORMAT, version));
        }
      };
}
