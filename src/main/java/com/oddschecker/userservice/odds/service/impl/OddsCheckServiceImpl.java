package com.oddschecker.userservice.odds.service.impl;

import org.springframework.stereotype.Service;
import com.oddschecker.userservice.odds.service.OddsCheckService;

@Service
public class OddsCheckServiceImpl implements OddsCheckService {

	private static final String REGEX = "^([1-9][0-9]*[\\/][1-9][0-9]*)|(SP)$";

	@Override
	public boolean isOddsValid(final String odds) {
		return odds.matches(REGEX);
	}
}
