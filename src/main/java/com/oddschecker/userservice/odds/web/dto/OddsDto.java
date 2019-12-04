package com.oddschecker.userservice.odds.web.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.oddschecker.userservice.common.validator.ValidOdds;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OddsDto {

    @NotNull
    private Integer betId;

    @NotNull
    @Size(max = 20)
    private String userId;

    @NotNull
    @ValidOdds
    private String odds;

}
