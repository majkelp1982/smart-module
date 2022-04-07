package pl.smarthouse.smartmodule.model.configuration;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import pl.smarthouse.smartmodule.model.actors.Actor;
import pl.smarthouse.smartmodule.utils.ModuleConfigValidator;

import java.util.HashMap;

@Builder
@Getter
public class Configuration {
  @NonNull private String type;
  @NonNull private String version;
  @NonNull private String macAddress;
  @NonNull private HashMap<String, Actor> actorMap;
  private String baseUrl;

  public Configuration(
      @NonNull final String type,
      @NonNull final String version,
      @NonNull final String macAddress,
      @NonNull final HashMap<String, Actor> actorMap,
      final String baseUrl) {
    ModuleConfigValidator.isVersionValid.accept(version);
    this.type = type;
    this.version = version;
    this.macAddress = macAddress;
    this.actorMap = actorMap;
    this.baseUrl = baseUrl;
  }

  public void setBaseUrl(final String baseUrl) {
    if (!baseUrl.startsWith("http")) {
      this.baseUrl = "http://" + baseUrl;
    } else {
      this.baseUrl = baseUrl;
    }
  }
}
