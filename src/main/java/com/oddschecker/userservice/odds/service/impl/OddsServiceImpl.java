package com.oddschecker.userservice.odds.service.impl;

import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.oddschecker.userservice.odd.model.Odds;
import com.oddschecker.userservice.odd.persistence.dao.OddRepository;
import com.oddschecker.userservice.odds.web.dto.OddsDto;
import com.oddschecker.userservice.odds.mapper.OddsMapper;
import com.oddschecker.userservice.odds.service.OddsService;

@Service
public class OddsServiceImpl implements OddsService {

	private OddRepository oddRepository;
	private OddsMapper oddsMapper;

	@Autowired
	public OddsServiceImpl(final OddRepository oddRepository, final OddsMapper oddsMapper) {
		this.oddRepository = oddRepository;
		this.oddsMapper = oddsMapper;
	}

	@Override
	public List<OddsDto> getOddsByBetId(final Integer betId) {
		return oddRepository.findByBetId(betId)
				.stream()
				.map(oddsMapper::toDto)
				.collect(Collectors.toList());
	}

	@Override
	public OddsDto postOdd(final OddsDto oddsDto) {
		final Odds odds = oddsMapper.toDomain(oddsDto);
		return oddsMapper.toDto(oddRepository.save(odds));
	}
}
