package pl.smarthouse.smartmodule.utils.errorpredictions;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.function.Predicate;
import lombok.experimental.UtilityClass;

@UtilityClass
public class TimeoutUtils {
  private static final int UPDATE_TIMEOUT_IN_SECONDS = 120;
  public final Predicate<LocalDateTime> updateTimeout =
      updateTimestamp ->
          Objects.isNull(updateTimestamp)
              || updateTimestamp
                  .plusSeconds(UPDATE_TIMEOUT_IN_SECONDS)
                  .isBefore(LocalDateTime.now());
}
