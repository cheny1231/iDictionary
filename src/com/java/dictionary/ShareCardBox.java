package com.java.dictionary;

import javafx.stage.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.*;
import javafx.geometry.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import javafx.beans.value.*;
import javafx.collections.*; 

public class ShareCardBox {
	
	//static DialogueBox dialogueBox;
	ObservableList<String> usersShared;
	
	public void display(String word, String translation, String type){
		/**Set Sign up Window*/
		BorderPane paneShareCard = new BorderPane(); 		
		Stage stgShareCard=new Stage();  
		stgShareCard.initModality(Modality.APPLICATION_MODAL);
//		paneShareCard.setPadding(new Insets(5, 5, 5, 5));  
//		paneShareCard.setVgap(5);  
//		paneShareCard.setHgap(5);
		
		/**Set the Labels*/
		Label tips = new Label("Choose the user(s) to share the word card"); 
		paneShareCard.setTop(tips);
		
		/**Set the Text Area*/
		TextArea userList = new TextArea();
		paneShareCard.setRight(userList);
		userList.setPrefSize(140, 170);
		BorderPane.setAlignment(userList, Pos.TOP_CENTER);
		
		/**Set the List*/
		ListView<String> list = new ListView<>();  
		//Todo receive the on-line user list from the server
		ObservableList<String> items =FXCollections.observableArrayList (  
		    "Single", "Double", "Suite", "Family App");  
		list.setItems(items); 
		list.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		list.setPrefSize(140, 170);
		paneShareCard.setLeft(list);
		BorderPane.setAlignment(list, Pos.TOP_CENTER);
		list.getSelectionModel().selectedItemProperty().addListener(  
		        (ObservableValue<? extends String> observable, String oldValue, String newValue) ->{  
		        	userList.setText(newValue);  
		});
		
		/**Set the Button*/
		Button btnShare = new Button("Share!");
		paneShareCard.setBottom(btnShare);
		BorderPane.setAlignment(btnShare, Pos.CENTER);
		btnShare.setPrefSize(100, 40);
		btnShare.setFont(Font.font("TimesRoman", 18));
		String[] translations = translation.split("！！！！");
		btnShare.setOnAction(event->{		
			usersShared = list.getSelectionModel().getSelectedItems();
			try {
				new ShareWordCard(usersShared).alphaWords2Image(word, translations[0], type);
				//To do send message to the server
				//if succeed
				new DialogueBox().displayShareSuccess();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			stgShareCard.close();						
	});		
		
		/**Set parent window unanswered*/
		Scene sceneShareCard = new Scene(paneShareCard,300,360); 
		InputStream is = null;
		String path = System.getProperty("user.dir").replace("\\", "/");
		File fileCss = new File(path.concat("/dark.css"));	
		try {
			is = new FileInputStream(fileCss);
			sceneShareCard.getStylesheets().add(fileCss.toURL().toExternalForm());
			is.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		paneShareCard.prefHeightProperty().bind(sceneShareCard.heightProperty()) ;
		paneShareCard.prefWidthProperty().bind(sceneShareCard.widthProperty()) ;
		stgShareCard.setScene(sceneShareCard); 
		stgShareCard.showAndWait();
	
	}
	
}
