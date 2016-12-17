package com.java.dictionary;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javafx.application.Application;
import javafx.stage.*;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.*;
import javafx.geometry.*;
import javafx.beans.binding.Bindings;

public class ApplicationUI extends Application {
	static User user;
	static DicTest dicTest;
	static NetStatus netStatus;
	static Socket server;
	Vector<TextArea> text;
	TextArea text1;
	TextArea text2;
	TextArea text3;
	String[] sortResult;
	Vector<ToggleButton> btnFavor = new Vector<ToggleButton>();
	Object object;
//	long start = 0;

	public void start(Stage primaryStage) throws Exception {
		
		/* Initialize variables */
		DialogueBox dialogueBox = new DialogueBox();
		ToggleButton btnFavorBD = new ToggleButton();
		ToggleButton btnFavorBY = new ToggleButton();
		ToggleButton btnFavorYD = new ToggleButton();
		btnFavor.add(btnFavorBD);
		btnFavor.add(btnFavorBY);
		btnFavor.add(btnFavorYD);
		text = new Vector<TextArea>();
		text1 = new TextArea();
		text2 = new TextArea();
		text3 = new TextArea();
		text.add(text1);
		text.add(text2);
		text.add(text3);

		/* Set the Stream */
		InputStream is = null;
		String path = System.getProperty("user.dir").replace("\\", "/");

		/* Set Grid Pane */
		GridPane pane = new GridPane();
		pane.setPadding(new Insets(10, 10, 10, 10));
		pane.setVgap(5);
		pane.setHgap(5);

		/* Set Input Box */
		TextField inputWord = new TextField();
		inputWord.setPromptText("Enter the word");
		GridPane.setConstraints(inputWord, 0, 0, 3, 1);
		pane.getChildren().add(inputWord);

		/* Set Check Field */
		CheckBox checkBD = new CheckBox("BaiDu");
		CheckBox checkBY = new CheckBox("Bing");
		CheckBox checkYD = new CheckBox("YouDao");
		checkBD.setSelected(true);
		checkBY.setSelected(true);
		checkYD.setSelected(true);
		pane.add(checkBD, 0, 1, 1, 1);
		pane.add(checkBY, 1, 1, 1, 1);
		pane.add(checkYD, 2, 1, 1, 1);

		/* Set Search Button */
		Button btnSearch = new Button("Search");
		btnSearch.setOnAction(event -> {
			// start = System.currentTimeMillis();
			if ((inputWord.getText() != null && !inputWord.getText().isEmpty())) {
				DicTest.setqName(inputWord.getText());
				btnFavorBD.setSelected(false);
				btnFavorBY.setSelected(false);
				btnFavorYD.setSelected(false);
				// if (!netStatus.isConnect()) {
				// new DialogueBox().displayNetUnconnected();
				// } else {
				// System.out.println(System.currentTimeMillis() - start);
				if (user.getUsername() != null) {
					sortResult = user.sort();
					for (int i = 0; i < 3; i++) {
						switch (sortResult[i]) {
						case "BD":
							dicTest.setBDtext(text.get(i));
							break;
						case "BY":
							dicTest.setBYtext(text.get(i));
							break;
						case "YD":
							dicTest.setYDtext(text.get(i));
							break;
						}
					}
				}
				user.setType("Update");
				System.out.println(user.getFavors().get("YD"));
				DicTest.getEs().execute(new ClientSocketSend<User>(user,server));
				if (checkBD.isSelected())
					dicTest.transBD();
				else
					dicTest.getBDtext().clear();

				if (checkBY.isSelected())
					dicTest.transBY();
				else
					dicTest.getBYtext().clear();

				if (checkYD.isSelected())
					dicTest.transYD();
				else
					dicTest.getYDtext().clear();
				// }
			}
		});
		GridPane.setConstraints(btnSearch, 3, 0, 1, 1);
		pane.getChildren().add(btnSearch);

		/* Set TextAreas */
		text.get(1).setWrapText(true);
		text.get(0).setWrapText(true);
		text.get(2).setWrapText(true);
		pane.add(text.get(0), 0, 2, 4, 3);
		pane.add(text.get(1), 0, 5, 4, 3);
		pane.add(text.get(2), 0, 8, 4, 3);
		dicTest.setBDtext(text.get(0));
		dicTest.setBYtext(text.get(1));
		dicTest.setYDtext(text.get(2));

		/* Set Login Label */
		Label labelForUser = new Label();
		pane.add(labelForUser, 4, 0, 1, 1);
		labelForUser.setAlignment(Pos.CENTER);
		labelForUser.setVisible(false);

		/* Set Logout & Login Button */
		Button btnSign = new Button("Register/Login");
		Button btnLogout = new Button("Log out");
		btnLogout.setStyle("-fx-font-size:8pt");

		// Logout
		pane.add(btnLogout, 4, 1, 1, 1);
		btnLogout.setVisible(false);
		btnLogout.setOnAction(event -> {
			// TODO send the user to server
			try {
				user.setType("Logout");
				DicTest.getEs().execute(new ClientSocketSend<User>(user, server));
//				server.close();
			} catch (Exception e) {
//				 TODO Auto-generated catch block
				e.printStackTrace();
			}
			user.setFavors(null);
			user.setPassword(null);
			user.setUsername(null);
			btnLogout.setVisible(false);
			labelForUser.setVisible(false);
			btnSign.setVisible(true);
		});
		// Login
		pane.add(btnSign, 4, 0, 1, 1);
		RegisterBox registerBox = new RegisterBox();
		btnSign.setOnAction(event -> {
			try {
				server = new Socket("172.28.173.38", 12345);
				DicTest.getEs().execute(new ClientSocketReceive(server));
				registerBox.display(user, server);
				if (user.getUsername() != null && user.getPassword() != null) {
					labelForUser.setText(user.getUsername());
					btnSign.setVisible(false);
					labelForUser.setVisible(true);
					btnLogout.setVisible(true);
				}
				else
					server.close();
			} catch (Exception e) {
				new DialogueBox().displayNetUnconnected();
				e.printStackTrace();
				}
		});

		/* Set Button for Sharing Text1 Word Card */
		ShareCardBox shareCardBox = new ShareCardBox();
		Button btnBDWordCard = new Button("Share!");
		pane.add(btnBDWordCard, 4, 4, 1, 1);
		btnBDWordCard.setOnAction(event -> {
			if (user.getUsername() == null)
				dialogueBox.displayAlertBox("Please Log in first!");
			else if (DicTest.getqName() != null && text.get(0).getText() != null)
				shareCardBox.display(DicTest.getqName(), text.get(0).getText(), user.getUsername(), server);
		});

		/* Set Button for Sharing Text2 Word Card */
		Button btnBYWordCard = new Button("Share!");
		pane.add(btnBYWordCard, 4, 7, 1, 1);
		btnBYWordCard.setOnAction(event -> {
			if (user.getUsername() == null)
				dialogueBox.displayAlertBox("Please Log in first!");
			else if (DicTest.getqName() != null && text.get(1).getText() != null)
				shareCardBox.display(DicTest.getqName(), text.get(1).getText(), user.getUsername(), server);
		});

		/* Set Button for Sharing Text3 Word Card */
		Button btnYDWordCard = new Button("Share!");
		pane.add(btnYDWordCard, 4, 10, 1, 1);
		btnYDWordCard.setOnAction(event -> {
			if (user.getUsername() == null)
				dialogueBox.displayAlertBox("Please Log in first!");
			else if (DicTest.getqName() != null && text.get(2).getText() != null)
				shareCardBox.display(DicTest.getqName(), text.get(2).getText(), user.getUsername(), server);
		});

		/* Set Button for favors */
		File fileEmpty = new File(path.concat("/images/Heart_empty.png"));
		is = new FileInputStream(fileEmpty);
		Image HeartEmpty = new Image(is);
		File filePadded = new File(path.concat("/images/Heart_padded.png"));
		is = new FileInputStream(filePadded);
		Image HeartPadded = new Image(is);

		ImageView heartViewBD = new ImageView();
		ImageView heartViewBY = new ImageView();
		ImageView heartViewYD = new ImageView();
		btnFavorBD.setGraphic(heartViewBD);
		btnFavorBY.setGraphic(heartViewBY);
		btnFavorYD.setGraphic(heartViewYD);
		heartViewBD.imageProperty()
				.bind(Bindings.when(btnFavorBD.selectedProperty()).then(HeartPadded).otherwise(HeartEmpty));
		heartViewBY.imageProperty()
				.bind(Bindings.when(btnFavorBY.selectedProperty()).then(HeartPadded).otherwise(HeartEmpty));
		heartViewYD.imageProperty()
				.bind(Bindings.when(btnFavorYD.selectedProperty()).then(HeartPadded).otherwise(HeartEmpty));
		for (int i = 0; i < 3; i++) {
			final int j = i;
			btnFavor.get(j).setStyle("-fx-background-color: transparent");
			pane.add(btnFavor.get(j), 4, i * 3 + 3, 1, 1);
			btnFavor.get(j).setOnAction(event -> {
				if (user.getUsername() == null) {
					dialogueBox.displayAlertBox("Please Log in first!");
					btnFavor.get(j).setSelected(false);
				} else {
					if (text.get(j) != null) {
						if (btnFavor.get(j).isSelected())
							user.addFavors(true, sortResult[j]);
						else
							user.addFavors(false, sortResult[j]);
					}
				}
			});
		}

		/* Set Scene */
		Scene scene = new Scene(pane, 500, 500);
		File fileCss = new File(path.concat("/dark.css"));
		is = new FileInputStream(fileCss);
		scene.getStylesheets().add(fileCss.toURL().toExternalForm());

		/* Close the stream */
		is.close();

		/* Set Stage */
		primaryStage.setScene(scene);
		primaryStage.setTitle("iDictionary");
		primaryStage.show();
		
		/*Open socket*/


	}

	public static Socket getServer() {
		return server;
	}

	public static void setServer(Socket server) {
		ApplicationUI.server = server;
	}

	public static void main(String[] args) {
		user = User.getInstance();
		dicTest = new DicTest();
		netStatus = new NetStatus();
		launch(args);
	}

}
