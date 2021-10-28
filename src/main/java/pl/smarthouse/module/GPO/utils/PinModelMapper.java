package pl.smarthouse.module.GPO.utils;

import pl.smarthouse.module.GPO.model.PinCommand;
import pl.smarthouse.module.GPO.model.PinConfigDto;
import pl.smarthouse.module.GPO.model.PinDao;

public class PinModelMapper {
  public static PinCommand toPinCommand(final PinDao pinDao) {
    return PinCommand.builder().pinNumber(pinDao.getPinNumber()).action(pinDao.getAction()).build();
  }

  public static PinConfigDto toPinConfigDto(final PinDao pinDao) {
    return PinConfigDto.builder()
        .pinNumber(pinDao.getPinNumber())
        .mode(pinDao.getMode())
        .standby(pinDao.getStandby())
        .defaultLatchTime(pinDao.getDefaultLatchTime())
        .build();
  }
}
