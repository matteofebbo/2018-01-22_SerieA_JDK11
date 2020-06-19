package it.polito.tdp.seriea.model;

public class Stagione {
	
	private int stagione;
	private Team team;
	private int punti;
	
	
	public int getStagione() {
		return stagione;
	}
	public void setStagione(int stagione) {
		this.stagione = stagione;
	}
	public Team getTeam() {
		return team;
	}
	public void setTeam(Team team) {
		this.team = team;
	}
	public int getPunti() {
		return punti;
	}
	public void setPunti(int punti) {
		this.punti = punti;
	}
	public Stagione(int stagione, Team team, int punti) {
		super();
		this.stagione = stagione;
		this.team = team;
		this.punti = punti;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + stagione;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Stagione other = (Stagione) obj;
		if (stagione != other.stagione)
			return false;
		return true;
	}
	
	
}
