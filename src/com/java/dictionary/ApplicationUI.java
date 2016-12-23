package com.java.dictionary;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.java.dictionary.bean.GroupBean;
import com.java.dictionary.bean.FavorBean;
import com.java.dictionary.common.Constant;
import com.java.dictionary.common.IMessage;
import com.java.dictionary.common.ResultHook;
import com.java.dictionary.common.UserHelper;
import com.java.dictionary.dic.DicHelper;
import com.java.dictionary.net.ClientSocketReceive;
import com.java.dictionary.net.ClientSocketSend;
import com.java.dictionary.net.NetStatus;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBase;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * The applicationUI class is the entrance of the program and the main UI design
 * 
 * @author: cheny1231
 *
 */

public class ApplicationUI extends Application implements EventHandler<ActionEvent> {

	private static final String ID_BUTTON_SIGN = "sign";
	private static final String ID_BUTTON_SEARCH = "search";
	private static final String ID_BUTTON_EXIT = "logout";

	static ExecutorService es;

	// static User user;
	static UserHelper userHelper;
	static DicHelper dicHelper;
	static NetStatus netStatus;
	static Socket server;

	DialogueBox dialogueBox;
	ShareCardBox shareCardBox;
	Vector<TextArea> text;
	RegisterBox registerBox;
	TextField inputWord;
	CheckBox checkBD;
	CheckBox checkBY;
	CheckBox checkYD;
	Label labelForUser;
	Button btnSign;
	Button btnLogout;
	Button btnSearch;

	// String[] sortResult;
	Vector<ToggleButton> btnFavor = new Vector<ToggleButton>();

	static List<GroupBean> group = new LinkedList<>();
	GroupBean group1 = new GroupBean();
	GroupBean group2 = new GroupBean();
	GroupBean group3 = new GroupBean();

	Object object;
	// long start = 0;

	public static void main(String[] args) {
		/**
		 * Thread pool of 6
		 */
		es = Executors.newFixedThreadPool(6);
		// user = ;
		userHelper = new UserHelper(UserDB.getInstance());
		dicHelper = new DicHelper();
		dicHelper.setBDCallback(BDCallback);
		dicHelper.setBYCallback(BYCallback);
		dicHelper.setYDCallback(YDCallback);

		netStatus = new NetStatus();
		launch(args);
	}

	public void start(Stage primaryStage) throws Exception {

		/* Set Grid Pane */
		GridPane pane = new GridPane();
		pane.setPadding(new Insets(10, 10, 10, 10));
		pane.setVgap(5);
		pane.setHgap(5);

		/* Initialize variables */
		dialogueBox = new DialogueBox();
		ToggleButton btnFavor1 = new ToggleButton();
		ToggleButton btnFavor2 = new ToggleButton();
		ToggleButton btnFavor3 = new ToggleButton();
		btnFavor.add(btnFavor1);
		btnFavor.add(btnFavor2);
		btnFavor.add(btnFavor3);
		TextArea textArea1 = new TextArea();
		TextArea textArea2 = new TextArea();
		TextArea textArea3 = new TextArea();

		/* Set Input Box */
		inputWord = new TextField();
		inputWord.setPromptText(Constant.INPUT_INFORM);
		GridPane.setConstraints(inputWord, 0, 0, 3, 1);
		pane.getChildren().add(inputWord);

		/* Set Check Field */
		initCheckBox();
		pane.add(checkBD, 0, 1, 1, 1);
		pane.add(checkBY, 1, 1, 1, 1);
		pane.add(checkYD, 2, 1, 1, 1);

		/* Set Search Button */
		btnSearch = new Button(Constant.TEXT_SEARCH);
		btnSearch.setId(ID_BUTTON_SEARCH);
		btnSearch.setOnAction(this);
		GridPane.setConstraints(btnSearch, 3, 0, 1, 1);
		pane.getChildren().add(btnSearch);

		/* Set TextAreas */
		textArea1.setWrapText(true);
		textArea2.setWrapText(true);
		textArea3.setWrapText(true);
		pane.add(textArea1, 0, 2, 4, 3);
		pane.add(textArea2, 0, 5, 4, 3);
		pane.add(textArea3, 0, 8, 4, 3);
		/*
		 * dicHelper.setBDtext(text.get(0)); dicHelper.setBYtext(text.get(1));
		 * dicHelper.setYDtext(text.get(2));
		 */

		/* Set Login Label */
		labelForUser = new Label();
		pane.add(labelForUser, 4, 0, 1, 1);
		labelForUser.setAlignment(Pos.CENTER);
		labelForUser.setVisible(false);

		/* Set Logout & Login Button */
		btnSign = new Button("Register/Login");
		btnSign.setId(ID_BUTTON_SIGN);

		btnLogout = new Button("Log out");
		btnLogout.setId(ID_BUTTON_EXIT);
		btnLogout.setStyle("-fx-font-size:8pt");

		// Logout
		pane.add(btnLogout, 4, 1, 1, 1);
		btnLogout.setVisible(false);
		btnLogout.setOnAction(this);
		// Login
		pane.add(btnSign, 4, 0, 1, 1);
		registerBox = new RegisterBox();
		btnSign.setOnAction(this);

		/* Set Button for Sharing Text1 Word Card */
		shareCardBox = new ShareCardBox();
		Button btn1WordCard = new Button(Constant.TEXT_SHARE);
		pane.add(btn1WordCard, 4, 4, 1, 1);
		btn1WordCard.setOnAction(this);

		/* Set Button for Sharing Text2 Word Card */
		Button btn2WordCard = new Button(Constant.TEXT_SHARE);
		pane.add(btn2WordCard, 4, 7, 1, 1);
		btn2WordCard.setOnAction(this);

		/* Set Button for Sharing Text3 Word Card */
		Button btn3WordCard = new Button(Constant.TEXT_SHARE);
		pane.add(btn3WordCard, 4, 10, 1, 1);
		btn3WordCard.setOnAction(this);

		/* Set the Stream */
		InputStream is = null;
		String path = System.getProperty("user.dir").replace("\\", "/");
		/* Set Button for favors */
		File fileEmpty = new File(path.concat("/images/Heart_empty.png"));
		is = new FileInputStream(fileEmpty);
		Image HeartEmpty = new Image(is);
		File filePadded = new File(path.concat("/images/Heart_padded.png"));
		is = new FileInputStream(filePadded);
		Image HeartPadded = new Image(is);

		ImageView heartView1 = new ImageView();
		ImageView heartView2 = new ImageView();
		ImageView heartView3 = new ImageView();
		btnFavor1.setGraphic(heartView1);
		btnFavor2.setGraphic(heartView2);
		btnFavor3.setGraphic(heartView3);
		heartView1.imageProperty()
				.bind(Bindings.when(btnFavor1.selectedProperty()).then(HeartPadded).otherwise(HeartEmpty));
		heartView2.imageProperty()
				.bind(Bindings.when(btnFavor2.selectedProperty()).then(HeartPadded).otherwise(HeartEmpty));
		heartView3.imageProperty()
				.bind(Bindings.when(btnFavor3.selectedProperty()).then(HeartPadded).otherwise(HeartEmpty));
		for (int i = 0; i < 3; i++) {
			final int j = i;
			btnFavor.get(j).setStyle("-fx-background-color: transparent");
			pane.add(btnFavor.get(j), 4, i * 3 + 3, 1, 1);
			btnFavor.get(j).setOnAction(this);
		}

		group1.setTextView(textArea1);
		group1.setBtnWordCard(btn1WordCard);
		group1.setBtnFavor(btnFavor1);
		group1.setId(Constant.ID_BD);

		group2.setTextView(textArea2);
		group2.setBtnWordCard(btn2WordCard);
		group2.setBtnFavor(btnFavor2);
		group2.setId(Constant.ID_BY);

		group3.setTextView(textArea3);
		group3.setBtnWordCard(btn3WordCard);
		group3.setBtnFavor(btnFavor3);
		group3.setId(Constant.ID_YD);

		group.add(group1);
		group.add(group2);
		group.add(group3);

		/* Set Scene */
		Scene scene = new Scene(pane, 500, 500);
		File fileCss = new File(path.concat("/dark.css"));
		is = new FileInputStream(fileCss);
		scene.getStylesheets().add(fileCss.toURI().toURL().toExternalForm());

		/* Close the stream */
		is.close();

		/* Set Stage */
		primaryStage.setScene(scene);
		primaryStage.setTitle(Constant.TITLE);
		primaryStage.show();

	}

	private void initCheckBox() {
		checkBD = new CheckBox("BaiDu");
		checkBY = new CheckBox("Bing");
		checkYD = new CheckBox("YouDao");
		checkBD.setId(Constant.ID_BD);
		checkBY.setId(Constant.ID_BY);
		checkYD.setId(Constant.ID_YD);
		checkBD.setOnAction(this);
		checkBY.setOnAction(this);
		checkYD.setOnAction(this);

		checkBD.setSelected(true);
		checkBY.setSelected(true);
		checkYD.setSelected(true);

		dicHelper.setAll(true);

	}

	public static Socket getServer() {
		return server;
	}

	public static void setServer(Socket server) {
		ApplicationUI.server = server;
	}

	public static ExecutorService getEs() {
		return es;
	}

	/**
	 * ÖØÖÃ¿Ø¼þ
	 */
	private void reset() {
		group.stream().forEach(action -> {
			action.getTextView().clear();
			action.getBtnFavor().setSelected(false);
		});
	}

	private boolean textNotEmpty(String text) {
		return text != null && !"".equals(text.trim());
	}

	@Override
	public void handle(ActionEvent event) {
		ButtonBase btn = (ButtonBase) event.getSource();
		String id = btn.getId();
		if (id == null)
			return;
		switch (id) {
		case ID_BUTTON_SEARCH:
			if ((inputWord.getText() != null && !inputWord.getText().isEmpty())) {
				// DicHelper.setqName(inputWord.getText());
				reset();

				// if (!netStatus.isConnect()) {
				// new DialogueBox().displayNetUnconnected();
				// } else {
				// System.out.println(System.currentTimeMillis() - start);

				if (userHelper.isLogIn()) {
					List<FavorBean> sortResult = userHelper.getSortedList();
					for (int i = 0; i < sortResult.size(); i++) {
						group.get(i).setId(sortResult.get(i).getType());
					}
				}
				dicHelper.getTrans(inputWord.getText());
				/*
				 * if (userHelper.isLogIn()) { userHelper.setDataType("Update");
				 * // System.out.println(user.getYD()); if (es == null) return;
				 * //TODO es.execute(new ClientSocketSend<User>(user, server));
				 * }
				 */
			}
			break;
		case ID_BUTTON_SIGN:
			try {
				// Open Socket
				server = new Socket("172.28.173.38", 12345);
				if (es == null)
					return;
				es.execute(new ClientSocketReceive(es, server));
				registerBox.show(server);
				if (userHelper.isLogIn()) {
					labelForUser.setText(userHelper.getUserName());
					btnSign.setVisible(false);
					labelForUser.setVisible(true);
					btnLogout.setVisible(true);
				} else
					server.close();
			} catch (Exception e) {
				new DialogueBox().displayNetUnconnected();
				// e.printStackTrace();
			}
			break;
		case ID_BUTTON_EXIT:
			try {
				userHelper.setDataType("Logout");
				// TODO DicTest.getEs().execute(new ClientSocketSend<User>(user,
				// server));
				ClientSocketSend.cnt = 0;
				server.close();
			} catch (Exception e) {
				// e.printStackTrace();
			}
			userHelper.logOut();

			break;
		case Constant.ID_BD:
			if (!((CheckBox) btn).isSelected())
				dicHelper.setBDEnable(false);
			else
				dicHelper.setBDEnable(true);
			break;
		case Constant.ID_BY:
			if (!((CheckBox) btn).isSelected())
				dicHelper.setBYEnable(false);
			else
				dicHelper.setBYEnable(true);
			break;
		case Constant.ID_YD:
			if (!((CheckBox) btn).isSelected())
				dicHelper.setYDEnable(false);
			else
				dicHelper.setYDEnable(true);
			break;
		case Constant.ID_BUTTON_LIKE_BD:
		case Constant.ID_BUTTON_LIKE_BY:
		case Constant.ID_BUTTON_LIKE_YD:
			// userHelper.logIn("rhg", "123");
			if (!userHelper.isLogIn()) {
				dialogueBox.displayAlertBox("Please Log in first!");
				((ToggleButton) btn).setSelected(false);
				return;
			}
			// System.out.println(group.stream().count());

			group.stream().filter(obj -> obj.getBtnFavor().getId().equals(id)).forEach(action -> {
				System.out.println("" + action.getTextView());
				if (action.getTextView() != null) {
					if (action.getBtnFavor().isSelected())
						userHelper.addFavor(true, id);
					else
						userHelper.addFavor(false, id);

				}
			});
			break;
		case Constant.ID_BUTTON_SHARE_BD:
		case Constant.ID_BUTTON_SHARE_BY:
		case Constant.ID_BUTTON_SHARE_YD:
//			userHelper.logIn("rhg", "123");
			if (!userHelper.isLogIn()) {
				dialogueBox.displayAlertBox("Please Log in first!");
				return;
			}
			if (textNotEmpty(inputWord.getText()))
				group.stream().filter(predicate -> predicate.getBtnWordCard().getId().equals(id)).forEach(action -> {
					if (!textNotEmpty(action.getTextView().getText())){
						System.out.println(action.getTextView().getText());
						dialogueBox.displayAlertBox("Content is empty");
						return;
					}
					shareCardBox.display(inputWord.getText(), action.getTextView().getText(), userHelper.getUserName(),
							server);
				});
			else dialogueBox.displayAlertBox("Content is empty");
			break;
		}
	}

	static ResultHook<IMessage> BDCallback = new ResultHook<IMessage>() {

		@Override
		public void result(IMessage result) {
			group.stream().filter(predicate -> predicate.getId().equals(Constant.ID_BD)).forEach(action -> {
				action.getTextView().setText("BDTrans:  " + result.getMsg());
			});

		}
	};
	static ResultHook<IMessage> BYCallback = new ResultHook<IMessage>() {

		@Override
		public void result(IMessage result) {

			group.stream().filter(predicate -> predicate.getId().equals(Constant.ID_BY)).forEach(action -> {
				action.getTextView().setText("BYTrans:  " + result.getMsg());
			});

		}
	};
	static ResultHook<IMessage> YDCallback = new ResultHook<IMessage>() {

		@Override
		public void result(IMessage result) {

			group.stream().filter(predicate -> predicate.getId().equals(Constant.ID_YD)).forEach(action -> {
				action.getTextView().setText("YDTrans:  " + result.getMsg());
			});

		}
	};

}
