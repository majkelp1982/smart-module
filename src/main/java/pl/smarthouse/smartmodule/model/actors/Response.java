package pl.smarthouse.smartmodule.model.actors;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public abstract class Response {

  public Response setResponse(final String response) throws IOException {
    final ObjectMapper mapper = new ObjectMapper();
    return mapper.readValue(response, Response.class);
  }
}
