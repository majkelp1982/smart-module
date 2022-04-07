package pl.smarthouse.smartmodule.utils;

import org.apache.commons.lang3.StringUtils;
import pl.smarthouse.smartmodule.model.configuration.Configuration;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.function.Consumer;

public interface ModuleConfigValidator extends Consumer<Configuration> {
  String WRONG_VERSION_FORMAT = "Wrong version format. Accepted is yyyy-MM-dd.xx. Now is %s";

  Consumer<String> isVersionValid =
      version -> {
        // Version validation
        // Check length
        if (version.length() != 13 || version.charAt(10) != '.') {
          throw new IllegalArgumentException(String.format(WRONG_VERSION_FORMAT, version));
        }

        // Check sub version
        String subVersion = version.substring(11);
        if (!StringUtils.isNumeric(subVersion)) {
          throw new IllegalArgumentException(String.format(WRONG_VERSION_FORMAT, version));
        }

        // Check date format
        try {
          final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
          String date = version.substring(0, 10);
          LocalDate.parse(date, formatter);
        } catch (Exception e) {
          throw new IllegalArgumentException(String.format(WRONG_VERSION_FORMAT, version));
        }
      };
}
