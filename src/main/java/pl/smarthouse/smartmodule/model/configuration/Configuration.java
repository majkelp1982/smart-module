package pl.smarthouse.smartmodule.model.configuration;

import lombok.*;
import pl.smarthouse.smartmodule.model.actors.actor.ActorMap;
import pl.smarthouse.smartmodule.utils.ModuleConfigValidator;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Configuration {
  @NonNull private String type;
  @NonNull private String version;
  @NonNull private String macAddress;
  @NonNull private ActorMap actorMap;
  private String baseUrl;

  public Configuration(
      @NonNull final String type,
      @NonNull final String version,
      @NonNull final String macAddress,
      @NonNull final ActorMap actorMap) {
    ModuleConfigValidator.isVersionValid.accept(version);
    this.type = type;
    this.version = version;
    this.macAddress = macAddress;
    this.actorMap = actorMap;
  }

  public void setBaseUrl(final String baseUrl) {
    if (!baseUrl.startsWith("http")) {
      this.baseUrl = "http://" + baseUrl;
    } else {
      this.baseUrl = baseUrl;
    }
  }
}
