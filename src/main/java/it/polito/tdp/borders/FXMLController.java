
package it.polito.tdp.borders;


import java.net.URL;
import java.util.ResourceBundle;

import org.jgrapht.alg.connectivity.ConnectivityInspector;

import it.polito.tdp.borders.model.Country;
import it.polito.tdp.borders.model.Model;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class FXMLController {

	private Model model;
	
	@FXML
	private TableColumn<Country, Integer> colConfini;

	@FXML
	private TableColumn<Country, String> colStato;

	@FXML
	private TableView<Country> tableResult;
	
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="txtAnno"
    private TextField txtAnno; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader
    
    @FXML
    private Button btnStatiRaggiungibili;

    @FXML
    private ComboBox<Country> cmbStati;

    @FXML
    private TableColumn<Country, String> colRaggiungibili;
    
    @FXML
    private TableView<Country> tblRaggiungibili;

    @FXML
    void doCalcolaConfini(ActionEvent event) {
    	txtResult.clear();
       if(!txtAnno.getText().matches("^[0-9]{4}$") || (Integer.parseInt(txtAnno.getText())>2016 || Integer.parseInt(txtAnno.getText())<1816))
    	   txtResult.appendText("Errore: inserire un anno dal 1816 al 2016");
       else {
    	  model.creaGrafo(Integer.parseInt(txtAnno.getText()));
    	  //String s=new String();
    	  for(Country c:model.getNazioni()) {
    		  if(model.getGrafo().containsVertex(c))
    		  c.setConfinanti(model.getGrafo().degreeOf(c));
    	  }
    	 tableResult.setItems(FXCollections.observableArrayList(model.getGrafo().vertexSet()));
    	 ConnectivityInspector ci = new ConnectivityInspector(model.getGrafo());
    	 txtResult.appendText("Numero di componenti connesse: "+ci.connectedSets().size());
    	 //for(Country c:model.getNazioni()) {
    	 // s=s+"Nome: "+c.getCountryName()+" Territori confinanti: "+ model.getGrafo().degreeOf(c)+"\n";
    	 //}
    	 //txtResult.appendText(s);  
       }
    }
    
    @FXML
    void handleRaggiungi(ActionEvent event) {
    	txtResult.clear();
    	if(!txtAnno.getText().matches("^[0-9]{4}$") || (Integer.parseInt(txtAnno.getText())>2016 || Integer.parseInt(txtAnno.getText())<1816)) {
     	   txtResult.appendText("Errore: inserire un anno dal 1816 al 2016");
     	   return ;
     	   }
    	if(model.getGrafo()==null) {
    	   txtResult.appendText("Errore: inserire un anno dal 1816 al 2016 e premere il bottone \"Calcola confini\"");
    	   return ;
    	   }
    	if(cmbStati.getValue()==null) {
    	   txtResult.appendText("Errore: seleziona uno stato");
     	   return ;
    	}
    	if(model.visitaGrafo(cmbStati.getValue())==null) {
    	   txtResult.appendText("Non ci sono stati raggiungibili");
      	   return ;
    	} else {
    	   //tblRaggiungibili.setItems(FXCollections.observableArrayList(model.visitaGrafo(cmbStati.getValue())));
    	   //tblRaggiungibili.setItems(FXCollections.observableArrayList(model.visitaGrafoRicorsiva(cmbStati.getValue())));
    		tblRaggiungibili.setItems(FXCollections.observableArrayList(model.visitaGrafoIterativa(cmbStati.getValue())));
    		
    	}
  
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
    	assert colConfini != null : "fx:id=\"colConfini\" was not injected: check your FXML file 'Scene.fxml'.";
        assert colStato != null : "fx:id=\"colStato\" was not injected: check your FXML file 'Scene.fxml'.";
        assert tableResult != null : "fx:id=\"tableResult\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtAnno != null : "fx:id=\"txtAnno\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnStatiRaggiungibili != null : "fx:id=\"btnStatiRaggiungibili\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbStati != null : "fx:id=\"cmbStati\" was not injected: check your FXML file 'Scene.fxml'.";
        assert colRaggiungibili != null : "fx:id=\"colRaggiungibili\" was not injected: check your FXML file 'Scene.fxml'.";
        assert tblRaggiungibili != null : "fx:id=\"tblRaggiungibili\" was not injected: check your FXML file 'Scene.fxml'.";
        colStato.setCellValueFactory(new PropertyValueFactory<Country,String>("countryName"));
        colConfini.setCellValueFactory(new PropertyValueFactory<Country,Integer>("confinanti"));
        colRaggiungibili.setCellValueFactory(new PropertyValueFactory<Country,String>("countryName"));
    }
    
    public void setModel(Model model) {
    	this.model = model;
    	cmbStati.getItems().setAll(model.getNazioni());
    }
}
