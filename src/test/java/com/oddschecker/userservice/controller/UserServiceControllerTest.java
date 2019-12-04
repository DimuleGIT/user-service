package com.oddschecker.userservice.controller;

import java.util.Arrays;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oddschecker.userservice.odds.service.OddsService;
import com.oddschecker.userservice.odds.web.dto.OddsDto;
import lombok.SneakyThrows;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserServiceControllerTest {

	private static final String USER = "USER";
	private static final String CORRECT_ODDS = "1/2";
	private static final String INCORRECT_ODDS = "1/ 2";
	private static final int BET_ID = 123456;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private OddsService oddsService;

	@Test
	public void givenOddsDtoWhenPostOddsThenCreated() throws Exception {
		OddsDto oddsDto = OddsDto.builder()
				                .betId(BET_ID)
				                .userId(USER)
				                .odds(CORRECT_ODDS)
								.build();

		when(oddsService.postOdd(oddsDto)).thenReturn(oddsDto);
		assertPostOdds(postOdds(oddsDto), oddsDto);
	}

	@Test
	public void givenBetIdWhenGetOddsByBetIdThenOK() throws Exception {

		List<OddsDto> odds = Arrays.asList(
				new OddsDto(BET_ID, USER, CORRECT_ODDS),
				new OddsDto(BET_ID, USER, CORRECT_ODDS));

		when(oddsService.getOddsByBetId(BET_ID)).thenReturn(odds);

		assertGetOdds(getOdds(BET_ID));
	}

	@Test
	public void givenOddsDtoWhenPostOddsThenBadRequest() throws Exception {
		OddsDto oddsDto = OddsDto.builder()
				.betId(BET_ID)
				.userId(USER)
				.odds(INCORRECT_ODDS)
				.build();
		assertPostOddsBadRequest(postOdds(oddsDto));
	}


	private ResultActions getOdds(Integer betId) throws Exception {
		return mockMvc.perform(MockMvcRequestBuilders.get("/odds/{betId}", betId)
				                       .contentType(MediaType.APPLICATION_JSON));
	}

	private ResultActions postOdds(OddsDto oddsDto) throws Exception {
		return mockMvc.perform(json(MockMvcRequestBuilders.post("/odds"), oddsDto)
				                       .contentType(MediaType.APPLICATION_JSON));
	}

    private void assertGetOdds(ResultActions resultActions) throws Exception {
		resultActions.andExpect(status().isOk())
				.andExpect(jsonPath("$..betId", everyItem(is(BET_ID))))
				.andExpect(jsonPath("$..userId", everyItem(is(USER))))
				.andExpect(jsonPath("$..odds", everyItem(is(CORRECT_ODDS))));
	}



	private void assertPostOdds(ResultActions resultActions, OddsDto oddsDto) throws Exception {
		resultActions.andExpect(status().isCreated())
				.andExpect(jsonPath("$.betId", is(oddsDto.getBetId())))
				.andExpect(jsonPath("$.userId", is(oddsDto.getUserId())))
				.andExpect(jsonPath("$.odds", is(oddsDto.getOdds())))
		;
	}

	private void assertPostOddsBadRequest(ResultActions resultActions) throws Exception {
		resultActions.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.errors[0].error", is(notNullValue())))
				.andExpect(jsonPath("$.errors[0].message", is(notNullValue())))
				.andExpect(jsonPath("$.errors[0].field", is(notNullValue())));
	}

	@SneakyThrows
	private MockHttpServletRequestBuilder json(MockHttpServletRequestBuilder builder, Object value) {
		return builder.content(objectMapper.writeValueAsString(value))
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
	}

}