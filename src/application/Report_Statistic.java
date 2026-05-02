package application;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.ListIterator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Report_Statistic extends VBox {
	TableView<Post> tablePostMy = new TableView<Post>();
	TableView<Post> tablePostShareMy = new TableView<Post>();
	TableView<User> tableActiveUser = new TableView<User>();
	ObservableList<User> mostActive = FXCollections.observableArrayList();

	TextField searchText1;
	Button searchPostCreated;

	TextField textMostActive;
	Button searchMostUserActive;

	TextField searchTextDetailsPosts;
	Button searchDetailsUser;

	TextArea details;

	Button backRS;
	MainInterFace mainInter;
	Scene sceneMain;
	Button saveCreatedPost;
	Button saveSharedPost;
	Button saveFriends;

	public Report_Statistic(Stage PrimaryStage) {
		tablePost();
		tablePostShare();
		userMorePosts();
		Label s = new Label(" REPORT AND STATISTIC ");
		s.setStyle("-fx-font-size: 55px; -fx-font-weight: bold; -fx-text-fill: #2a2a2a; -fx-font-family: 'Arial';");

		ImageView f = new ImageView(new Image("back.png"));
		f.setFitHeight(60);
		f.setFitWidth(100);
		backRS = new Button("", f);

		searchText1 = new TextField();
		searchText1.setPrefColumnCount(10);

		HBox hoho1 = new HBox(10, new Label("User ID : "), searchText1);

		ImageView a = new ImageView(new Image("search.png"));
		a.setFitHeight(60);
		a.setFitWidth(100);
		searchPostCreated = new Button("", a);
		VBox vPosetMy = new VBox(15, hoho1, searchPostCreated, tablePostMy);
		vPosetMy.setAlignment(Pos.CENTER);

		VBox vPostShare = new VBox(15, new Label("posts shared with user this :"), tablePostShareMy);
		vPostShare.setAlignment(Pos.CENTER);

		searchTextDetailsPosts = new TextField();
		searchTextDetailsPosts.setPrefColumnCount(10);
		HBox hoho3 = new HBox(10, new Label("User ID : "), searchTextDetailsPosts);

		ImageView c = new ImageView(new Image("search.png"));
		c.setFitHeight(60);
		c.setFitWidth(100);
		searchDetailsUser = new Button("", c);

		details = new TextArea();

		saveCreatedPost = new Button("save created post");
		saveSharedPost = new Button("Save shared post");
		saveFriends = new Button("Save friends      ");
		HBox Hsave = new HBox(70, saveCreatedPost, saveSharedPost, saveFriends);

		VBox vDetailsUser = new VBox(15, hoho3, searchDetailsUser, details, Hsave);
		vPostShare.setAlignment(Pos.CENTER);

		ImageView k = new ImageView(new Image("search.png"));
		k.setFitHeight(60);
		k.setFitWidth(100);
		textMostActive = new TextField();
		textMostActive.setPrefColumnCount(5);
		searchMostUserActive = new Button("", k);
		HBox hb = new HBox(new Label("numper user most active :", textMostActive));

		VBox vMostAct = new VBox(15, new Label("Most Active Users :"), hb, searchMostUserActive, tableActiveUser);

		HBox hTables = new HBox(30, vPosetMy, vPostShare, vMostAct, vDetailsUser);
		hTables.setAlignment(Pos.CENTER);

		this.getChildren().addAll(s, hTables, backRS);
		this.setAlignment(Pos.CENTER);
		this.setSpacing(35);
		this.setStyle("-fx-background-color: lightgray;");

		backRS.setOnAction(e -> {
			mainInter = new MainInterFace(PrimaryStage);
			sceneMain = new Scene(mainInter);
			PrimaryStage.setScene(sceneMain);
			PrimaryStage.setFullScreen(true);

		});
		/// 1 2 عمل اكشن للجدولين
		searchPostCreated.setOnAction(e -> {
			if (!searchText1.getText().isEmpty()) {
				User user = null;
				Node<User> curr = Main.userL.head.getNext();
				while (curr != Main.userL.head) {
					if (curr.getData().getIdUser() == Integer.parseInt(searchText1.getText().trim())) {
						user = curr.getData();
						break;
					}
					curr = curr.getNext();
				}
				if (user != null) {
					tablePostMy.setItems(user.getPostsArr().linkedToOps());
					tablePostShareMy.setItems(user.getPostsSHaredArr().linkedToOps());
					// System.out.println(user.getPostsArr().print());
				} else {
					Alert alert = new Alert(AlertType.WARNING);
					alert.setTitle("search failed");
					alert.setHeaderText("search failed");
					alert.setContentText(" user not found!");
					alert.showAndWait();
				}
			} else {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("search failed");
				alert.setHeaderText("search failed");
				alert.setContentText(" user not found!");
				alert.showAndWait();
			}

		});

		searchMostUserActive.setOnAction(e -> {
			findMostActive(Integer.parseInt(textMostActive.getText()));
			tableActiveUser.setItems(mostActive);
		});

		searchDetailsUser.setOnAction(e -> {
			if (!searchTextDetailsPosts.getText().isEmpty()) {
				User user = null;
				Node<User> curr = Main.userL.head.getNext();
				while (curr != Main.userL.head) {
					if (curr.getData().getIdUser() == Integer.parseInt(searchTextDetailsPosts.getText().trim())) {
						user = curr.getData();
						break;
					}
					curr = curr.getNext();
				}
				if (user != null) {
					int lPost = user.getPostsArr().length();
					int lShare = user.getPostsSHaredArr().length();
					details.setText("(" + user.getName() + ") had created (" + lPost + ") posts \n and shared with ("
							+ lShare + ") posts from other users");
				} else {
					Alert alert = new Alert(AlertType.WARNING);
					alert.setTitle("search failed");
					alert.setHeaderText("search failed");
					alert.setContentText(" user not found!");
					alert.showAndWait();
				}
			} else {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("search failed");
				alert.setHeaderText("search failed");
				alert.setContentText(" user not found!");
				alert.showAndWait();
			}
		});

		// saveCreatedPost.setOnAction(e -> saveCreatedPostInFile());
		saveSharedPost.setOnAction(e -> saveShareMyPostInFile());
		saveFriends.setOnAction(e -> saveFrinds());

	}

	void tablePost() {
		TableColumn<Post, Integer> idPost = new TableColumn<>("Post ID");
		idPost.setCellValueFactory(new PropertyValueFactory<>("postId"));

		TableColumn<Post, String> content = new TableColumn<>("Post Content");
		content.setCellValueFactory(new PropertyValueFactory<>("content"));

		tablePostMy.getColumns().addAll(idPost, content);
		tablePostMy.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

	}

	void tablePostShare() {
		TableColumn<Post, Integer> idPost = new TableColumn<>("Post ID");
		idPost.setCellValueFactory(new PropertyValueFactory<>("postId"));

		TableColumn<Post, String> content = new TableColumn<>("Post Content");
		content.setCellValueFactory(new PropertyValueFactory<>("content"));

		tablePostShareMy.getColumns().addAll(idPost, content);
		tablePostShareMy.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

	}

	void userMorePosts() {
		TableColumn<User, Integer> idUseer = new TableColumn<>("User ID");
		idUseer.setCellValueFactory(new PropertyValueFactory<>("idUser"));

		TableColumn<User, String> nameU = new TableColumn<>("User Name ");
		nameU.setCellValueFactory(new PropertyValueFactory<>("name"));

		tableActiveUser.getColumns().addAll(idUseer, nameU);
		tableActiveUser.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

	}

//	void saveCreatedPostInFile() {
//		Alert alert = new Alert(AlertType.CONFIRMATION);
//		alert.setTitle("Save In File");
//		alert.setHeaderText("Descending or Ascending");
//		ComboBox<String> combo = new ComboBox<String>();
//		combo.getItems().addAll("Ascending ", "Descending");
//		combo.getSelectionModel().select(0);
//		alert.getDialogPane().setContent(combo);
//		if (alert.showAndWait().get() == ButtonType.OK) {
//			try {
//				PrintWriter output = new PrintWriter("C://Users//HP//OneDrive//Desktop//saveCreatedPost.txt");
//				output.println("Posts Created Report\r\n" + "-----------------------");
//				if (combo.getValue().equals("Ascending")) {
//					ListIterator<User> curr = Main.userL.listItretor();
//					while (curr.hasNext()) {
//						User user = curr.next();
//						output.println("User : " + user.getName());
//						ListIterator<Post> currP = user.getPostsArr().listItretor();
//						while (currP.hasNext()) {
//							output.println(currP.next().toString());
//						}
//					}
//				} else {
////- Post ID: 1, Content: Hello World, 21.3.2025, Shared With: Fatima, Khaled, Layla
//
//					ListIterator<User> curr = Main.userL.listItretor();
//					while (curr.hasPrevious()) {
//						User user = curr.previous();
//						output.println("User : " + user.getName());
//						ListIterator<Post> currP = user.getPostsArr().listItretor();
//						while (currP.hasPrevious()) {
//							output.println(currP.previous().toString());
//
//						}
//					}
//
//				}
//				output.close();
//			} catch (Exception ee) {
//
//			}
//		}
//	}

	void saveShareMyPostInFile() {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Save In File");
		alert.setHeaderText("Descending or Ascending");
		ComboBox<String> combo = new ComboBox<String>();
		combo.getItems().addAll("Ascending", "Descending");
		combo.getSelectionModel().select(0);
		alert.getDialogPane().setContent(combo);
		if (alert.showAndWait().get() == ButtonType.OK) {
			try {
				PrintWriter output = new PrintWriter("C://Users//HP//OneDrive//Desktop//savePostWithMe.txt");
				output.println("Posts Shared With User Report\r\n" + "-----------------------------");
				if (combo.getValue().equals("Ascending")) {
					// ListIterator<User> curr = Main.userL.listItretor();
					Node<User> curr = Main.userL.head.getNext();
					while (curr != Main.userL.head) {
						User user = curr.getData();
						output.println("User : " + curr.getData().getName());
						curr = curr.getNext();
						// System.out.println("User : " + user.getName());
						// ListIterator<Post> currP = user.getPostsSHaredArr().listItretor();
						Node<Post> currP = user.getPostsSHaredArr().head.getNext();

						while (currP != user.getPostsSHaredArr().head) {
							// System.out.println("99999999999" + currP.next().toString());
							output.println(currP.getData().toString());
							currP = currP.getNext();
						}

					}
				}
				output.close();

			} catch (Exception e) {
				System.out.println("not save " + e.getMessage());
			}
		}
	}

	public void saveFrinds() {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Save In File");
		alert.setHeaderText("Descending or Ascending");
		ComboBox<String> combo = new ComboBox<String>();
		combo.getItems().addAll("Ascending", "Descending");
		combo.getSelectionModel().select(0);
		alert.getDialogPane().setContent(combo);
		if (alert.showAndWait().get() == ButtonType.OK) {
			try {
				PrintWriter output = new PrintWriter("C://Users//HP//OneDrive//Desktop//saveFriends.txt");
				output.println("Friends With User Report\r\n" + "-----------------------------");
				if (combo.getValue().equals("Ascending")) {
					Node<User> curr = Main.userL.head.getNext();
					while (curr != Main.userL.head) {
						User user = curr.getData();
						output.println("User : " + curr.getData().getName());
						curr = curr.getNext();

						Node<User> currF = user.getFriendsArr().head.getNext();
						while (currF != user.getFriendsArr().head) {
							output.println(currF.getData().getName() + "-->");
							currF = currF.getNext();
						}
					}
				}
				output.close();
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
	}

	public void findMostActive(int n) {
		// LinkdList<User> copy = new LinkdList<User>();
		ArrayList<User> copy = new ArrayList<User>();
		Node<User> curr = Main.userL.head.getNext();
		while (curr != Main.userL.head) {
			copy.add(0, curr.getData()); // add first
			curr = curr.getNext();
		}
		ArrayList<User> mostActiveList = new ArrayList<User>();

		while (n-- > 0) {
			int maxCount = -1;
			User maxUser = null;

			for (int i = 0; i < copy.size(); i++) {
				int postCount = copy.get(i).getPostsArr().length();
				if (postCount > maxCount) {
					maxCount = postCount;
					maxUser = copy.get(i);
				}

			}

			if (maxUser != null) {
				mostActiveList.add(maxUser);
				copy.remove(maxUser);
			}
		}

		mostActive.setAll(mostActiveList);
	}

}
