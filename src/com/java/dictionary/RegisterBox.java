package com.java.dictionary;

import javafx.stage.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.*;
import javafx.scene.paint.Color;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.Socket;

import javafx.geometry.*;

/**
 * To generate the Window for Register/Log in
 * 
 * @author: cheny1231
 *
 */

public class RegisterBox {

	public void display(User user, Socket server) {

		/** Set Sign up Window */
		GridPane paneSignup = new GridPane();
		Stage stgSignup = new Stage();
		stgSignup.initModality(Modality.APPLICATION_MODAL);
		paneSignup.setPadding(new Insets(5, 5, 5, 5));
		paneSignup.setVgap(5);
		paneSignup.setHgap(5);
		paneSignup.setPrefSize(400, 150);

		/** Set Labels */
		Label username = new Label("Username");
		paneSignup.add(username, 0, 0);
		Label password = new Label("Password");
		paneSignup.add(password, 0, 1);
		Label error = new Label();
		paneSignup.add(error, 0, 3, 2, 1);
		error.setVisible(false);
		error.setStyle("-fx-text-fill:red");
		error.setTextFill(Color.RED);

		/** Set TextFields */
		TextField inputuser = new TextField();
		inputuser.setPromptText("Enter your username");
		paneSignup.add(inputuser, 1, 0, 3, 1);
		PasswordField inputpass = new PasswordField();
		inputpass.setPromptText("Enter your password");
		paneSignup.add(inputpass, 1, 1, 3, 1);

		/** Set Button for Register */
		Button btnRegister = new Button("Register Now!");
		btnRegister.setOnAction(event -> {
			user.setType("Register");
			user.setUsername(inputuser.getText());
			user.setPassword(inputpass.getText());
			System.out.println(user.getUsername());
			DicTest.getEs().execute(new ClientSocketSend<User>(user, server));
			while (true) {
				if (!ClientSocketReceive.getMessage().equals("")) {
					if (ClientSocketReceive.getMessage().equals("Success")) {
						ClientSocketReceive.setMessage("");
						DicTest.getEs().execute(new ClientSocketSend<String>("ACK", server));
						stgSignup.close();
						break;
					} else {
						ClientSocketReceive.setMessage("");
						error.setText("Username existed!");
						DicTest.getEs().execute(new ClientSocketSend<String>("ACK", server));
						user.setUsername(null);
						user.setPassword(null);
						error.setVisible(true);
						break;
					}
				} else
					System.out.println("RNothing");
			}
		});
		paneSignup.add(btnRegister, 0, 2);

		/** Set Button for Log in */
		Button btnLogin = new Button("Log in");
		btnLogin.setOnAction(event -> {
			user.setType("Login");
			user.setUsername(inputuser.getText());
			user.setPassword(inputpass.getText());
			DicTest.getEs().execute(new ClientSocketSend<User>(user, server));
			while (true) {
				if (!ClientSocketReceive.getMessage().equals("")) {
					if (ClientSocketReceive.getMessage().equals("User")) {
						ClientSocketReceive.setMessage("");
						 DicTest.getEs().execute(new ClientSocketSend<String>("ACK",server));
						stgSignup.close();
						break;
					} else if (ClientSocketReceive.getMessage().equals("Invalid")) {
						ClientSocketReceive.setMessage("");
						 DicTest.getEs().execute(new ClientSocketSend<String>("ACK",server));
						user.setUsername(null);
						user.setPassword(null);
						error.setText("You have logged in on another device!");
						error.setVisible(true);
						break;
					} else {
						ClientSocketReceive.setMessage("");
						 DicTest.getEs().execute(new ClientSocketSend<String>("ACK",server));
						user.setUsername(null);
						user.setPassword(null);
						error.setText("Wrong username or password!");
						error.setVisible(true);
						break;
					}
				} else
					System.out.println("LNothing");
			}

		});
		paneSignup.add(btnLogin, 1, 2);

		/** Set parent window unanswered */
		Scene sceneSignup = new Scene(paneSignup);
		String path = System.getProperty("user.dir").replace("\\", "/");
		File fileCss = new File(path.concat("/dark.css"));
		InputStream is = null;
		try {
			is = new FileInputStream(fileCss);
			sceneSignup.getStylesheets().add(fileCss.toURL().toExternalForm());
			is.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		stgSignup.setScene(sceneSignup);
		stgSignup.showAndWait();
	}
}
