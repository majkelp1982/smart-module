package pl.smarthouse.smartmodule.model.module;

import java.util.Map;
import lombok.*;

@Getter
@EqualsAndHashCode
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ModuleResponses {
  public static final String TYPE = "type";
  public static final String VERSION = "version";
  public static final String RESPONSE_MAP = "responseMap";

  @NonNull private String type;
  @NonNull private String version;
  @NonNull private Map<String, String> responseMap;
}
