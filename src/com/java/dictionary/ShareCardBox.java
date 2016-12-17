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
import java.net.Socket;
import java.util.Vector;

import javafx.beans.value.*;
import javafx.collections.*;
import javafx.event.*;

public class ShareCardBox {

	// static DialogueBox dialogueBox;
	ObservableList<String> usersShared;

	@SuppressWarnings("unchecked")
	public void display(String word, String translation, String username, Socket server) {
		/** Set Sign up Window */
		BorderPane paneShareCard = new BorderPane();
		Stage stgShareCard = new Stage();
		stgShareCard.initModality(Modality.APPLICATION_MODAL);
		// paneShareCard.setPadding(new Insets(5, 5, 5, 5));
		// paneShareCard.setVgap(5);
		// paneShareCard.setHgap(5);

		/** Set the Labels */
		Label tips = new Label("Choose the user(s) to share the word card");
		paneShareCard.setTop(tips);

		/** Set the Text Area */
		TextArea userList = new TextArea();
		paneShareCard.setRight(userList);
		userList.setPrefSize(140, 170);
		BorderPane.setAlignment(userList, Pos.TOP_CENTER);

		/** Set the List */
		ListView<String> list = new ListView<>();
		ObservableList<String> items = FXCollections.observableArrayList();
		DicTest.getEs().execute(new ClientSocketSend<String>("ListOfOnlineUser", server));
		while (true) {
			if (!ClientSocketReceive.getMessage().equals("")) {
				if (ClientSocketReceive.getMessage().equals("online user")) {
					Vector<String> usernames = (Vector<String>) ClientSocketReceive.getObject();
					for(String i: usernames){
						items.add(i);
					}
					ClientSocketReceive.setMessage("");
					ClientSocketReceive.setObject(null);
					DicTest.getEs().execute(new ClientSocketSend<String>("ACK", server));
					break;
				} else {
					ClientSocketReceive.setMessage("");
					ClientSocketReceive.setObject(null);
					DicTest.getEs().execute(new ClientSocketSend<String>("ACK", server));
					new DialogueBox().displayAlertBox("Unaccessible to user list!");
					stgShareCard.close();
					break;
				}
			} else
				System.out.println("ListNothing");
		}
		// TODO receive the on-line user list from the server
		// ObservableList<String> items =
		// FXCollections.observableArrayList("Single", "Double", "Suite",
		// "Family App");
		list.setItems(items);

		list.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		list.setPrefSize(140, 170);
		paneShareCard.setLeft(list);
		BorderPane.setAlignment(list, Pos.TOP_CENTER);
		list.setOnMouseClicked(event -> {
			ObservableList<String> selectedItems = list.getSelectionModel().getSelectedItems();
			userList.setText("");
			for (String s : selectedItems) {
				userList.appendText(s);
			}
		});

		// userList.textProperty().bind(list.getSelectionModel().selectedItemProperty());
		// list.getSelectionModel().selectedItemProperty().addListener(
		// (ObservableValue<? extends String> observable, String oldValue,
		// String newValue) ->{
		// userList.setText(newValue);
		// });

		/** Set the Button */
		Button btnShare = new Button("Share!");
		paneShareCard.setBottom(btnShare);
		BorderPane.setAlignment(btnShare, Pos.CENTER);
		btnShare.setPrefSize(100, 40);
		btnShare.setFont(Font.font("TimesRoman", 18));
		String[] translations = translation.split("！！！！");
		btnShare.setOnAction(event -> {
			usersShared = list.getSelectionModel().getSelectedItems();
			try {
				Vector<String> usernames= new Vector();
				for(String i: usersShared){
					usernames.add(i);
				}
				ShareWordCard shareWordCard = new ShareWordCard(usernames, username);
				shareWordCard.alphaWords2Image(word, translations[0], translations[1]);
				// TODO send message to the server
				// if succeed
				DicTest.getEs().execute(new ClientSocketSend<ShareWordCard>(shareWordCard, server));
				while (!ClientSocketReceive.getMessage().equals("")) {
					if (ClientSocketReceive.getMessage().equals("WordCardShared")) {
						ClientSocketReceive.setMessage("");
						DicTest.getEs().execute(new ClientSocketSend<String>("ACK", server));
						new DialogueBox().displayAlertBox("Your friends have received the Word Card!");
						stgShareCard.close();
					} else {
						ClientSocketReceive.setMessage("");
						DicTest.getEs().execute(new ClientSocketSend<String>("ACK", server));
						new DialogueBox().displayAlertBox("Error!");
						stgShareCard.close();
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		});

		/** Set parent window unanswered */
		Scene sceneShareCard = new Scene(paneShareCard, 300, 360);
		InputStream is = null;
		String path = System.getProperty("user.dir").replace("\\", "/");
		File fileCss = new File(path.concat("/dark.css"));
		try {
			is = new FileInputStream(fileCss);
			sceneShareCard.getStylesheets().add(fileCss.toURL().toExternalForm());
			is.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		paneShareCard.prefHeightProperty().bind(sceneShareCard.heightProperty());
		paneShareCard.prefWidthProperty().bind(sceneShareCard.widthProperty());
		stgShareCard.setScene(sceneShareCard);
		stgShareCard.showAndWait();

	}

}
