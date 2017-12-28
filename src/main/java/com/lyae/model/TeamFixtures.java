package com.lyae.model;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data @Getter @Setter @JsonIgnoreProperties(ignoreUnknown = true)
public class TeamFixtures {

	@JsonProperty("date") private String matchDate;
	private String status;
	private String matchday;
	private String homeTeamName;
	private String awayTeamName;
	private Map<String,Object> result;
	private Map<String,Object> odds;
	
	
}
