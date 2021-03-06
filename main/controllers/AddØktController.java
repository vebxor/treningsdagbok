package controllers;

import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import databaseConnection.DatabaseHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import main.Økt;
import queryHandler.QueryHandler;
import utility.Utility;

public class AddØktController extends Controller {
	
	@FXML
	DatePicker øktDato;
	@FXML
	TextField klokkeslettText;
	@FXML
	TextField varighetText;
	@FXML
	TextField formText;
	@FXML
	TextField prestasjonText;
	@FXML
	TextArea notatText;
	
	@FXML
	Label queryStatus;
	
	
	private QueryHandler qh = new QueryHandler();
	
	private DashController controller;
	
	@FXML
	public void initialize() {
		
	}
	
	public void startUp(DashController controller) {
		
		this.controller = controller;
	}
	
	public void addPressed(ActionEvent e) {
		LocalDate dato = øktDato.getValue();
		System.out.println(dato);
		String timeString = klokkeslettText.getText();
		String varighetString = varighetText.getText();
		String formString = formText.getText();
		String prestasjonString = formText.getText();
		String notat = notatText.getText();
		
		Time starttid = null;
		Time varighet = null;
		int form = 0;
		int prestasjon = 0;
		
		if (!(Utility.isTime(timeString) && Utility.isTime(varighetString))) {
			queryStatus.setText("Velg gyldig tid");
			e.consume();
		} else {
			starttid = Time.valueOf(timeString);
			varighet = Time.valueOf(varighetString);
		}
			
		
		if (!(Utility.isInteger(formString) && Utility.isInteger(prestasjonString))) {
			queryStatus.setText("Velg heltall for form og prestasjon");
			e.consume();
		} else {
			form = Integer.parseInt(formString);
			prestasjon = Integer.parseInt(prestasjonString);
		}
		
		if (!(isValidInteger(form) && isValidInteger(prestasjon))) {
			queryStatus.setText("Velg heltall mellom 0 og 10 for form og prestasjon");
			e.consume();
		}
			
		Økt økt = new Økt(dato, starttid, varighet, form, prestasjon, notat);
		
		try {
			int resultInt = qh.addØkt(DatabaseHandler.getInstance(), økt);
			if (resultInt != 0) {
				queryStatus.setTextFill(Color.GREEN);
				queryStatus.setText("Økt lagt til i DB");
				controller.alleØkter().add(økt);
			} else {
				queryStatus.setTextFill(Color.RED);
				queryStatus.setText("Kunne ikke legge til økt i DB");
			}
		} catch (SQLException e1) {
			queryStatus.setTextFill(Color.RED);
			queryStatus.setText("Kunne ikke legge til økt i DB");
			e1.printStackTrace();
		}
		
	}
	
	
	private boolean isValidInteger(int i) {
		if (i<=10 && i >= 0)
			return true;
		return false;
	}

}
