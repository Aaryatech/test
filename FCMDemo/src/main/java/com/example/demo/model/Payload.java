package com.example.demo.model;

public class Payload {

	String team;
	String score;
	
	public String getTeam() {
		return team;
	}
	public void setTeam(String team) {
		this.team = team;
	}
	public String getScore() {
		return score;
	}
	public void setScore(String score) {
		this.score = score;
	}
	
	@Override
	public String toString() {
		return "Payload [team=" + team + ", score=" + score + "]";
	}
	
	
}
