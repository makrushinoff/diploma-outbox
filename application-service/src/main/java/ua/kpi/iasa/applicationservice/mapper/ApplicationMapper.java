package ua.kpi.iasa.applicationservice.mapper;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import ua.kpi.iasa.applicationservice.entity.Application;
import ua.kpi.iasa.commons.dto.ApplicationMessageDto;
import ua.kpi.iasa.commons.dto.RepairmentApplicationDto;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface ApplicationMapper {

  Application repairmentApplicationDtoToApplication(RepairmentApplicationDto repairmentApplicationDto);

  RepairmentApplicationDto applicationToRepairmentApplicationDto(Application application);

  ApplicationMessageDto applicationToApplicationMessageDto(Application application);

}
