package pl.smarthouse.module.sensors.model;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface SensorResponseMap {
  SensorResponseMap map(final String json) throws JsonProcessingException;
}
