package main;

import java.io.IOException;
import controllers.Controller;
import databaseConnection.DatabaseHandler;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class App extends Application{
	
	private DatabaseHandler dbh;
	private Stage primaryStage;
	private Controller currentController;
	private Stage popupStage;
	
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		this.primaryStage = primaryStage;
		this.setLogin();
//		this.setDash();
			
	}
	
	public Stage getPrimaryStage() {
		return this.primaryStage;
	}
	
	public Stage getPopupStage() {
		return this.popupStage;
	}
	
	@Override
	public void init() {
		this.dbh = DatabaseHandler.getInstance();
	}
	
	@Override
	public void stop() {
		try {
			this.dbh.closeConnection();
		} catch (Exception e) {
			if (e instanceof NullPointerException) {
				System.out.println("No connection to close");
			}
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}

	
	public void setLogin() throws IOException {
		this.setScene(this.loadScene("/resources/Login.fxml"), "Login");
	}
	
	public void setDash()  {
		try {
			this.setScene(this.loadScene("/resources/Dash.fxml"), "Dash");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		DashController controller = (DashController) this.currentController;
//		controller.showØkter();
	}
	
	public void setAddØvelse() {
		try {
			this.setScene(this.loadScene("/resources/AddØvelse.fxml"), "Legg til øvelse");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
    public Parent loadScene(String fxmlFile) throws IOException {
    	Parent parent = null;
    	
    	FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlFile));
   
		parent = fxmlLoader.load();
	
    	currentController = fxmlLoader.getController();
    	System.out.println("Current Controller: " + currentController);
    	currentController.setApp(this);
    		 	
    	return parent;
    }
  
    
    public void setScene(Parent parent, String sceneTitle) {
        
    	Scene newScene = new Scene(parent);
//    	newScene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
    	this.primaryStage.setScene(newScene);
    	this.primaryStage.setTitle(sceneTitle);
    	this.primaryStage.show();
    }
    

	public Controller getCurrentController() {
		return this.currentController;
	}

	public void setPopupStage(Stage popupStage) {
		this.popupStage = popupStage;
		
	}
	
	public void showPopupStage(String fxmlfileName) throws IOException {
		popupStage = new Stage();
		popupStage.initModality(Modality.APPLICATION_MODAL);
		popupStage.initOwner(getPrimaryStage());
		Parent parent = loadScene(fxmlfileName);
		Scene scene = new Scene(parent);
		popupStage.setScene(scene);
		popupStage.show();
		setPopupStage(popupStage);
	}
	


	
	

}
