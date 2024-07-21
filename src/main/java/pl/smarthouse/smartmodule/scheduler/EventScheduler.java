package pl.smarthouse.smartmodule.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pl.smarthouse.smartmodule.services.ModuleService;
import reactor.core.scheduler.Schedulers;

@EnableScheduling
@RequiredArgsConstructor
@Service
@Slf4j
public class EventScheduler {
  private final ModuleService moduleService;

  @Scheduled(fixedDelay = 1000)
  public void eventScheduler() {
    // Block. Do not run another exchange until last is not finished
    moduleService.exchange().subscribeOn(Schedulers.boundedElastic()).block();
  }
}
