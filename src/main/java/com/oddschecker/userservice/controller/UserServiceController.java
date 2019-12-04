package com.oddschecker.userservice.controller;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.oddschecker.userservice.odds.service.OddsService;
import com.oddschecker.userservice.odds.web.dto.OddsDto;

@RestController
@RequestMapping("/odds")
public class UserServiceController {

	private OddsService oddsService;

	@Autowired
	public UserServiceController(final OddsService oddsService) {
		this.oddsService = oddsService;
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	public OddsDto postOdd(@Validated @RequestBody OddsDto oddsDto) {
		return oddsService.postOdd(oddsDto);
	}

	@GetMapping(value = "/{betId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<OddsDto>> getOddsByBetId(@PathVariable Integer betId) {
		return Optional.ofNullable(oddsService.getOddsByBetId(betId))
				.filter(odds -> !odds.isEmpty())
				.map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.notFound().build());
	}
}
