package com.oddschecker.userservice.odds.service;

import java.util.List;
import com.oddschecker.userservice.odds.web.dto.OddsDto;

public interface OddsService {

   List<OddsDto> getOddsByBetId(Integer betId);

   OddsDto postOdd(OddsDto oddsDto);
}
