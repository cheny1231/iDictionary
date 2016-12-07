package com.java.dictionary;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.*;
import javafx.scene.control.*;

public class DialogueBox {
	
	public void displayShareSuccess(){
		GridPane paneShareSuccess = new GridPane(); 		
		Stage stgShareSuccess=new Stage();  
		stgShareSuccess.initModality(Modality.APPLICATION_MODAL);
//		paneShareSuccess.setPadding(new Insets(5, 5, 5, 5));  
		paneShareSuccess.setPrefSize(200, 150);
		
		
		/**Set the Labels*/
		Label success = new Label("Your friends have received the Word Card!");
		paneShareSuccess.add(success, 0, 0);
		
		/**Set the Button*/
		Button btnOK = new Button("OK");
		btnOK.setOnAction(event->{
			stgShareSuccess.close();						
	});	
		paneShareSuccess.add(btnOK, 0, 3);
		
		/**Set parent window unanswered*/
		paneShareSuccess.setAlignment(Pos.CENTER);
		Scene sceneShareSuccess = new Scene(paneShareSuccess); 
		stgShareSuccess.setScene(sceneShareSuccess); 
		stgShareSuccess.showAndWait();
		
	}
}
