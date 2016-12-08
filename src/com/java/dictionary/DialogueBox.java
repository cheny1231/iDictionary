package com.java.dictionary;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.*;
import javafx.scene.control.*;

public class DialogueBox {
	
	public void displayShareSuccess(){
		VBox paneShareSuccess = new VBox(10); 		
		Stage stgShareSuccess=new Stage();  
		stgShareSuccess.initModality(Modality.APPLICATION_MODAL);
//		paneShareSuccess.setPadding(new Insets(5, 5, 5, 5));  
		paneShareSuccess.setPrefSize(300, 80);
		
		
		/**Set the Labels*/
		Label success = new Label("Your friends have received the Word Card!");
		paneShareSuccess.getChildren().add(success);
		
		/**Set the Button*/
		Button btnOK = new Button("OK");
		btnOK.setOnAction(event->{
			stgShareSuccess.close();						
	});	
		paneShareSuccess.getChildren().add(btnOK);
		
		/**Set parent window unanswered*/
		paneShareSuccess.setAlignment(Pos.CENTER);
		Scene sceneShareSuccess = new Scene(paneShareSuccess); 
		stgShareSuccess.setScene(sceneShareSuccess); 
		stgShareSuccess.showAndWait();
		
	}
	
	public void displayNetUnconnected(){
		VBox paneNetUnconnected = new VBox(10); 	 		
		Stage stgNetUnconnected=new Stage();  
		stgNetUnconnected.setTitle("Internet Unavailable");
		stgNetUnconnected.initModality(Modality.APPLICATION_MODAL); 
		paneNetUnconnected.setPrefSize(350, 80);
		
		
		/**Set the Labels*/
		Label NetUnconnected = new Label("I've tried really hard to connect the Internet T^T");
		NetUnconnected.setWrapText(true);
		NetUnconnected.setAlignment(Pos.CENTER);
		paneNetUnconnected.getChildren().add(NetUnconnected);
		
		/**Set the Button*/
		Button btnNetUnconnected = new Button("Have a rest");
		btnNetUnconnected.setOnAction(event->{
			stgNetUnconnected.close();						
	});	
		paneNetUnconnected.getChildren().add(btnNetUnconnected);
		
		/**Set parent window unanswered*/
		paneNetUnconnected.setAlignment(Pos.CENTER);
		Scene sceneNetUnconnected = new Scene(paneNetUnconnected); 
		stgNetUnconnected.setScene(sceneNetUnconnected); 
		stgNetUnconnected.showAndWait();
		
	}
}
