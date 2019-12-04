package com.oddschecker.userservice.odds.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import com.oddschecker.userservice.odd.model.Odds;
import com.oddschecker.userservice.odds.web.dto.OddsDto;

@Mapper(componentModel = "spring")
public interface OddsMapper {

    Odds toDomain(OddsDto dto);

    @InheritInverseConfiguration(name = "toDomain")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    OddsDto toDto(Odds odds);


}
