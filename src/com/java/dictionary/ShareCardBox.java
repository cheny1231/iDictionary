package com.java.dictionary;

import javafx.stage.*;
import javafx.util.Callback;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxListCell;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.*;
import javafx.geometry.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Vector;

import javafx.beans.value.*;
import javafx.collections.*;

public class ShareCardBox {

	// static DialogueBox dialogueBox;
	ObservableList<Item> usersShared;

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
		
//		ListView<String> list = new ListView<>();
//		ObservableList<String> items = FXCollections.observableArrayList();
		ListView<Item> list = new ListView<Item>();
		DicTest.getEs().execute(new ClientSocketSend<String>("ListOfOnlineUser", server));
		while (true) {
			if (!ClientSocketReceive.getMessage().equals("")) {
				if (ClientSocketReceive.getMessage().equals("online user")) {
					Vector<String> usernames = (Vector<String>) ClientSocketReceive.getObject();
					for (int i = 1; i < usernames.size(); i++) {
						Item item = new Item(usernames.get(i),false);
						item.onProperty().addListener((obs, wasOn, isNowOn)->{
							System.out.println(item.getName() + "changed on state from " + wasOn + "to" + isNowOn);
							ObservableList<Item> items = list.getItems();
							userList.setText("");
							for (Item s : items) {
								if(s.isOn())
									userList.appendText(s.getName() + "\n");
							}
						});
						list.getItems().add(item);
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
		
//		list.setItems(items);
		list.setCellFactory(CheckBoxListCell.forListView(new Callback<Item, ObservableValue<Boolean>>(){
			public ObservableValue<Boolean> call(Item item){
				return item.onProperty();
			}
		}));
		
		list.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		list.setPrefSize(140, 170);
		paneShareCard.setLeft(list);
		BorderPane.setAlignment(list, Pos.TOP_CENTER);
//		list.setOnMouseClicked(event -> {
//			ObservableList<Item> selectedItems = list.getSelectionModel().getSelectedItems();
//			userList.setText("");
//			for (Item s : selectedItems) {
//				userList.appendText(s.getName());
//			}
//		});

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
			Vector<String> shareWordCard = new Vector<String>();
			usersShared = list.getItems();
			try {
//				Vector<String> usernames = new Vector<String>();
				shareWordCard.add(username);
				for (Item i : usersShared) {
					if(i.isOn()){
						if(shareWordCard.size()>1)
							shareWordCard.set(1, shareWordCard.get(1) + " " + i.getName());
						else
							shareWordCard.add(i.getName());
					}
				}
//				ShareWordCard shareWordCard = new ShareWordCard(usernames, username);
				shareWordCard.add(word);
				shareWordCard.add(translations[0]);
				shareWordCard.add(translations[1]);
//				shareWordCard.alphaWords2Image(word, translations[0], translations[1]);
				// TODO send message to the server
				// if succeed
//				ClientSocketSend.cnt = 0;
				DicTest.getEs().execute(new ClientSocketSend<Vector>(shareWordCard, server));
				while (true) {
					if (!ClientSocketReceive.getMessage().equals("")) {
						if (ClientSocketReceive.getMessage().equals("WordCardShared")) {
							ClientSocketReceive.setMessage("");
							DicTest.getEs().execute(new ClientSocketSend<String>("ACK", server));
							new DialogueBox().displayAlertBox("Your friends have received the Word Card!");
							stgShareCard.close();
							break;
						} else {
							ClientSocketReceive.setMessage("");
							DicTest.getEs().execute(new ClientSocketSend<String>("ACK", server));
							new DialogueBox().displayAlertBox("Error!");
							stgShareCard.close();
							break;
						}
					}
					else
						System.out.println("CNothing");
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
