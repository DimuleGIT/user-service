package com.oddschecker.userservice.odds.service.impl;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import com.oddschecker.userservice.odds.service.OddsCheckService;

@SpringBootTest
@RunWith(SpringRunner.class)
public class OddsCheckServiceImplTest {

	@Autowired
	private OddsCheckService oddsCheckService;

	@Test
	public void givenNotValidOddsWhenAllAssertFalse() {
		Assert.assertFalse(oddsCheckService.isOddsValid("1 /2"));
		Assert.assertFalse(oddsCheckService.isOddsValid("1/ 2"));
		Assert.assertFalse(oddsCheckService.isOddsValid("0/2"));
		Assert.assertFalse(oddsCheckService.isOddsValid("1/0"));
		Assert.assertFalse(oddsCheckService.isOddsValid("TP"));
	}

	@Test
	public void givenValidOddsWhenAllTAssertsTrue() {
		Assert.assertTrue(oddsCheckService.isOddsValid("1/2"));
		Assert.assertTrue(oddsCheckService.isOddsValid("100/200"));
		Assert.assertTrue(oddsCheckService.isOddsValid("323523/3453"));
		Assert.assertTrue(oddsCheckService.isOddsValid("SP"));
	}
}