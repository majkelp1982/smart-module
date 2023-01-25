package pl.smarthouse.smartmodule.model.actors.type.ds18b20;

import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;
import pl.smarthouse.smartmodule.exceptions.InvalidDs18b20AddressException;

@UtilityClass
public class Ds18b20Utils {

  private static final String INVALID_DS18B20_ADDRESS_EXCEPTION =
      "Invalid DS18B20 address. Pattern: 40-12-1-7-51-138-1-132, current: %s";

  public String getDs18b20Command(final @NonNull String... addresses) {
    final StringBuilder command = new StringBuilder();
    for (final String address : addresses) {
      checkAddress(address);
      command.append(address);
      command.append(";");
    }
    return command.toString();
  }

  private void checkAddress(final String address) {
    if (StringUtils.countMatches(address, "-") != 7) {
      throw new InvalidDs18b20AddressException(
          String.format(INVALID_DS18B20_ADDRESS_EXCEPTION, address));
    }
  }
}
