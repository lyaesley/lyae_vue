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
public class TeamPlayers {

	private String name;
	private String position;
	@JsonProperty("jerseyNumber") private int number;
	@JsonProperty("dateOfBirth") private String birth;
	private String nationality;
	private String contractUntil;
	private String marketValue;
	
}
