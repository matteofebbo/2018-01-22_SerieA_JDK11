package it.polito.tdp.seriea;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import it.polito.tdp.seriea.model.Model;
import it.polito.tdp.seriea.model.Stagione;
import it.polito.tdp.seriea.model.Team;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;

public class FXMLController {
	
	private Model model;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ChoiceBox<Team> boxSquadra;

    @FXML
    private Button btnSelezionaSquadra;

    @FXML
    private Button btnTrovaAnnataOro;

    @FXML
    private Button btnTrovaCamminoVirtuoso;

    @FXML
    private TextArea txtResult;

    @FXML
    void doSelezionaSquadra(ActionEvent event) {
    	
    	txtResult.clear();
    	Team team= this.boxSquadra.getValue();
    	if(team==null) {
    		txtResult.appendText("Errore,selezionare una squadra\n");
    		return;
    	}
    	Map<Integer,Integer> classifiche= new HashMap<>();
    	classifiche= model.getClassifiche(team);
    	for(Integer season : classifiche.keySet()) {
    		txtResult.appendText("season: "+season+" -> "+classifiche.get(season)+"\n");
    	}
    }

    @FXML
    void doTrovaAnnataOro(ActionEvent event) {
    	
    	txtResult.clear();
    	Team team= this.boxSquadra.getValue();
    	if(team==null) {
    		txtResult.appendText("Errore,selezionare una squadra\n");
    		return;
    	}
    	model.creaGrafo(team);
    }

    @FXML
    void doTrovaCamminoVirtuoso(ActionEvent event) {
    	
    	txtResult.clear();
    	Team team= this.boxSquadra.getValue();
    	if(team==null) {
    		txtResult.appendText("Errore,selezionare una squadra\n");
    		return;
    	}
    	List<Stagione> cammino= new ArrayList<>();
    	cammino= model.getCamminoVirtuoso();
    	for(Stagione season : cammino) {
    		txtResult.appendText("season: "+season.getStagione()+" -> "+season.getPunti()+"\n");
    	}
    	
 
    }

    @FXML
    void initialize() {
        assert boxSquadra != null : "fx:id=\"boxSquadra\" was not injected: check your FXML file 'SerieA.fxml'.";
        assert btnSelezionaSquadra != null : "fx:id=\"btnSelezionaSquadra\" was not injected: check your FXML file 'SerieA.fxml'.";
        assert btnTrovaAnnataOro != null : "fx:id=\"btnTrovaAnnataOro\" was not injected: check your FXML file 'SerieA.fxml'.";
        assert btnTrovaCamminoVirtuoso != null : "fx:id=\"btnTrovaCamminoVirtuoso\" was not injected: check your FXML file 'SerieA.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'SerieA.fxml'.";

    }

	public void setModel(Model model) {
		this.model = model;
		this.boxSquadra.getItems().addAll(model.getSquadre());
	}
}
