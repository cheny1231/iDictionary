package com.java.dictionary;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.*;
import javafx.scene.control.*;

/**
 * To generate the dialogue box with certain alert words
 * 
 * @author: cheny1231
 *
 */

public class DialogueBox {
	
	InputStream is = null;
	
	public void displayAlertBox(String alertWords){
		VBox paneAlertBox = new VBox(10); 		
		Stage stgAlertBox=new Stage();  
		stgAlertBox.initModality(Modality.APPLICATION_MODAL);
		paneAlertBox.setPrefSize(300, 80);
		
		
		/**Set the Labels*/
		Label success = new Label(alertWords);
		paneAlertBox.getChildren().add(success);
		
		/**Set the Button*/
		Button btnOK = new Button("OK");
		btnOK.setOnAction(event->{
			stgAlertBox.close();						
	});	
		paneAlertBox.getChildren().add(btnOK);
		
		/**Set parent window unanswered*/
		paneAlertBox.setAlignment(Pos.CENTER);
		Scene sceneAlertBox = new Scene(paneAlertBox); 
		String path = System.getProperty("user.dir").replace("\\", "/");
		File fileCss = new File(path.concat("/dark.css"));		
		try {
			is = new FileInputStream(fileCss);
			sceneAlertBox.getStylesheets().add(fileCss.toURL().toExternalForm());
			is.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		stgAlertBox.setScene(sceneAlertBox); 
		stgAlertBox.showAndWait();
		
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
		String path = System.getProperty("user.dir").replace("\\", "/");
		File fileCss = new File(path.concat("/dark.css"));		
		try {
			is = new FileInputStream(fileCss);
			sceneNetUnconnected.getStylesheets().add(fileCss.toURL().toExternalForm());
			is.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		stgNetUnconnected.setScene(sceneNetUnconnected); 
		stgNetUnconnected.showAndWait();
		
	}
}
