package com.lyae.model;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data @Getter @Setter @JsonIgnoreProperties(ignoreUnknown = true)
public class LeagueTable {

	private int position;
	private String teamName;
	@JsonProperty("crestURI") private String logo;
	private int playedGames;
	private int points;
	private int goals;
	private int goalsAgainst;
	private int goalDifference;
	private int wins;
	private int draws;
	private int losses;
	private Map<String,Object> home;
	private Map<String,Object> away;
	
}
