package com.java.dictionary;

import javafx.application.*;
import javafx.stage.*;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.*;
import javafx.geometry.*;

public class ApplicationUI extends Application {
	static User user;
	static DicTest dicTest;
	static TextArea BDtext;
	static TextArea BYtext;
	static TextArea YDtext;

	public void start(Stage primaryStage) throws Exception {

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
			if ((inputWord.getText() != null && !inputWord.getText().isEmpty())) {
				DicTest.setqName(inputWord.getText());
				//To do save the favors for last word and send it to the server
				
				if (checkBD.isSelected())
					dicTest.transBD();
				else
					BDtext.clear();

				if (checkBY.isSelected())
					dicTest.transBY();
				else
					BYtext.clear();

				if (checkYD.isSelected())
					dicTest.transYD();
				else
					YDtext.clear();
			}
		});
		GridPane.setConstraints(btnSearch, 3, 0, 1, 1);
		pane.getChildren().add(btnSearch);

		/* Set TextAreas */
		BDtext = new TextArea();
		BYtext = new TextArea();
		YDtext = new TextArea();
		// BackgroundImage imageBD= new BackgroundImage(new
		// Image("https://ss0.bdstatic.com/5aV1bjqh_Q23odCf/static/superman/img/logo/bd_logo1_31bdc765.png",32,32,false,true),
		// BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT,
		// BackgroundPosition.DEFAULT,
		// BackgroundSize.DEFAULT);
		// BDtext.setBackground(new Background(imageBD));
		BYtext.setWrapText(true);
		BDtext.setWrapText(true);
		YDtext.setWrapText(true);
		pane.add(BDtext, 0, 2, 3, 3);
		pane.add(BYtext, 0, 5, 3, 3);
		pane.add(YDtext, 0, 8, 3, 3);
		DicTest.setBDtext(BDtext);
		DicTest.setBYtext(BYtext);
		DicTest.setYDtext(YDtext);
		
		/* Set Login Label*/
		Label labelForUser = new Label();
		pane.add(labelForUser, 4, 0, 1, 1);
		labelForUser.setVisible(false);
		
		/* Set Logout & Login Button*/
		Button btnSign = new Button("Register/Login");
		Button btnLogout = new Button("Log out");
		//Logout
		pane.add(btnLogout, 4, 1, 1, 1);
		btnLogout.setVisible(false);
		btnLogout.setOnAction(event -> {
			user.setFavors(null);
			user.setPassword(null);
			user.setUsername(null);
			btnLogout.setVisible(false);
			labelForUser.setVisible(false);
			btnSign.setVisible(true);
		});
		//Login
		pane.add(btnSign, 4, 0, 1, 1);
		RegisterBox registerBox = new RegisterBox();
		btnSign.setOnAction(event -> {
			registerBox.display(user);
			if(user.getUsername()!=null && user.getPassword()!=null){
				labelForUser.setText(user.getUsername());
				btnSign.setVisible(false);
				labelForUser.setVisible(true);
				btnLogout.setVisible(true);
			}
			});
		


		/* Set Button for Sharing Baidu Word Card */
		Button btnBDWordCard = new Button("Share!");
		pane.add(btnBDWordCard, 3, 4, 1, 1);
		btnBDWordCard.setOnAction(event -> {
			if (DicTest.getqName() != null && BDtext.getText() != null)
				new ShareCardBox().display(DicTest.getqName(), BDtext.getText(), "百度翻译");
		});

		/* Set Button for Sharing Bing Word Card */
		Button btnBYWordCard = new Button("Share!");
		pane.add(btnBYWordCard, 3, 7, 1, 1);
		btnBYWordCard.setOnAction(event -> {
			if (DicTest.getqName() != null && BYtext.getText() != null)
				new ShareCardBox().display(DicTest.getqName(), BYtext.getText(), "必应翻译");
		});

		/* Set Button for Sharing Youdao Word Card */
		Button btnYDWordCard = new Button("Share!");
		pane.add(btnYDWordCard, 3, 10, 1, 1);
		btnYDWordCard.setOnAction(event -> {
			if (DicTest.getqName() != null && YDtext.getText() != null)
				new ShareCardBox().display(DicTest.getqName(), YDtext.getText(), "有道翻译");
		});

		/* Set Button for favor of Baidu */

		/* Set Button for favor of Bing */

		/* Set Button for favor of Youdao */

		/* Set Scene */
		Scene scene = new Scene(pane, 500, 500);

		/* Set Stage */
		primaryStage.setScene(scene);
		primaryStage.setTitle("iDictionary");
		primaryStage.show();
	}

	public static void main(String[] args) {
		user = User.getInstance();
		dicTest = new DicTest();
		launch(args);
	}

}
