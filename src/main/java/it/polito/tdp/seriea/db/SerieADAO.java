package it.polito.tdp.seriea.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.polito.tdp.seriea.model.Season;
import it.polito.tdp.seriea.model.Team;

public class SerieADAO {

	public List<Season> listAllSeasons() {
		String sql = "SELECT season, description FROM seasons";
		List<Season> result = new ArrayList<>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				result.add(new Season(res.getInt("season"), res.getString("description")));
			}

			conn.close();
			return result;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public List<Team> listTeams() {
		String sql = "SELECT team FROM teams";
		List<Team> result = new ArrayList<>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				result.add(new Team(res.getString("team")));
			}

			conn.close();
			return result;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public Map<Integer, Integer> getClassifiche(Team team) {
		String sql = "SELECT * " + 
				"FROM matches AS m,seasons AS s,teams AS t " + 
				"WHERE m.Season=s.season " + 
				"AND (m.HomeTeam=t.team OR m.AwayTeam=t.team) " + 
				"AND t.team= ?";
		Map<Integer,Integer> result = new HashMap<>();
		Connection conn = DBConnect.getConnection();
		int stagione=2003;
		int punti=0;

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, team.getTeam());
			ResultSet res = st.executeQuery();
			int punti2017=0;
			
			while (res.next()) {
				
				if(stagione!=res.getInt("Season")) {
					result.put(stagione, punti);
					punti=0;
					stagione=res.getInt("Season");
				}
				String home=res.getString("HomeTeam");
				String away=res.getString("AwayTeam");
				String risultato=res.getString("FTR");
				switch(risultato) {
				case "H":
					if(home.compareTo(team.getTeam())==0) {
						if(res.getInt("Season")==2017) {
							punti2017=punti2017+3;
						} else
							punti=punti+3;
					}
					break;
				case "A":
					if(res.getInt("Season")==2017) {
						punti2017=punti2017+3;
					} else
						punti=punti+3;
					break;
				case "D":
					if(res.getInt("Season")==2017) {
						punti2017=punti2017+1;
					} else
						punti=punti+1;
					break;
				}
			}
			result.put(2017, punti2017);
			conn.close();
			return result;
		} catch (SQLException e) {
			
			e.printStackTrace();
			return null;
		}
	}

}

