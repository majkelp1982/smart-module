package pl.smarthouse.smartmodule;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pl.smarthouse.smartmodule.model.actors.Actor;
import pl.smarthouse.smartmodule.model.actors.BME280.BME280;
import pl.smarthouse.smartmodule.model.configuration.Configuration;

import java.util.HashMap;

@SpringBootApplication
public class SmartModuleApplication {

  public static void main(final String[] args) {
    SpringApplication.run(SmartModuleApplication.class, args);

    final Actor bme280 = new BME280("pogoda");
    final Configuration configuration =
        new Configuration("", "2022-02-02.00", "", new HashMap<>(), "");
  }
}
