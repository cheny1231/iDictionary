package com.java.dictionary;

import javafx.stage.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.*;
import javafx.geometry.*;

public class RegisterBox {
	
    public void display(User user){
    	
    	/**Set Sign up Window*/
		GridPane paneSignup = new GridPane(); 		
		Stage stgSignup=new Stage();  
		stgSignup.initModality(Modality.APPLICATION_MODAL);
		paneSignup.setPadding(new Insets(5, 5, 5, 5));  
		paneSignup.setVgap(5);  
		paneSignup.setHgap(5);
		
		/**Set Labels*/
		Label username = new Label("Username"); 
		paneSignup.add(username, 0, 0);
		Label password = new Label("Password");
		paneSignup.add(password, 0, 1);
		
		/**Set TextFields*/
		TextField inputuser = new TextField();
		inputuser.setPromptText("Enter your username");
		paneSignup.add(inputuser, 1, 0, 3, 1);
		TextField inputpass = new TextField();
		inputpass.setPromptText("Enter your password");
		paneSignup.add(inputpass, 1, 1, 3, 1);
		
		/**Set Button for Register*/
		Button btnRegister = new Button("Register Now!");
		btnRegister.setOnAction(event->{
			//To do send message to the server
			user.setUsername(inputuser.getText());
			user.setPassword(inputpass.getText());
			stgSignup.close();						
	});		
		paneSignup.add(btnRegister, 0, 2);
			
		/**Set Button for Log in*/
		Button btnLogin = new Button("Log in");
		btnLogin.setOnAction(event->{
			//To do send message to the server
			//receive message from the server
			//if the user exists,  
			//user.setUsername(inputuser.getText());
			//user.setPassword(inputpass.getText());
			//user.setFavors(favors);
			stgSignup.close();
			//else
			//Label("Wrong username or password");
	});	
		paneSignup.add(btnLogin, 1, 2);
		
		/**Set parent window unanswered*/
		Scene sceneSignup = new Scene(paneSignup); 
		stgSignup.setScene(sceneSignup); 
		//stgSignup.show();
		stgSignup.showAndWait();
    }
}
