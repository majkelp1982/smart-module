package pl.smarthouse.module.GPO.utils;

import pl.smarthouse.module.GPO.model.PinCommand;
import pl.smarthouse.module.GPO.model.PinConfigDto;
import pl.smarthouse.module.GPO.model.PinDao;

public class PinModelMapper {
  public static PinCommand toPinCommand(final PinDao pinDao) {
    return PinCommand.builder().pinNumber(pinDao.getPinNumber()).action(pinDao.getAction()).build();
  }

  public static PinDao toPinDao(final PinConfigDto pinConfigDto) {
    return PinDao.builder()
        .pinNumber(pinConfigDto.getPinNumber())
        .mode(pinConfigDto.getMode())
        .standby(pinConfigDto.getStandby())
        .defaultLatchTime(pinConfigDto.getDefaultLatchTime())
        .build();
  }
}
