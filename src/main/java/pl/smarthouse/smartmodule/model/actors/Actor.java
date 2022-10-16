package pl.smarthouse.smartmodule.model.actors;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@RequiredArgsConstructor
@Getter
public abstract class Actor {
  @NonNull protected String name;

  public abstract Command getCommand();

  public abstract void setResponse(final Map response) throws JsonProcessingException;
}
