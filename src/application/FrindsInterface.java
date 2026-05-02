package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.Scanner;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class FrindsInterface extends VBox {
	Button addFrind;
	Button remove;
	Button searchF;
	TextField searchFrind;
	Button backF;
	Button readF;
	File fileR;
	TableView<User> tableF = new TableView<>();
	MainInterFace mainInter;
	Scene sceneMain;
	static User searchUser;

	public FrindsInterface(Stage PrimaryStage) {

		Label s = new Label(" FRIENDSHIP MANAGMENT ");
		s.setStyle("-fx-font-size: 55px; -fx-font-weight: bold; -fx-text-fill: #2a2a2a; -fx-font-family: 'Arial';");

		Label sear = new Label("User ID : ");
		searchFrind = new TextField();

		HBox hohoSearch = new HBox(20, sear, searchFrind);
		hohoSearch.setAlignment(Pos.CENTER);

		ImageView a = new ImageView(new Image("add-friend.png"));
		a.setFitHeight(60);
		a.setFitWidth(100);
		addFrind = new Button("", a);

		ImageView b = new ImageView(new Image("remove-user.png"));
		b.setFitHeight(60);
		b.setFitWidth(100);
		remove = new Button("", b);

		ImageView d = new ImageView(new Image("user-avatar.png"));
		d.setFitHeight(60);
		d.setFitWidth(100);
		searchF = new Button("", d);

		ImageView f = new ImageView(new Image("project.png"));
		f.setFitHeight(60);
		f.setFitWidth(100);
		readF = new Button("", f);

		tableFriend();
		ImageView g = new ImageView(new Image("back.png"));
		g.setFitHeight(60);
		g.setFitWidth(100);
		backF = new Button("", g);

		// tableF.setItems(User.getF.linkedToOps());

		HBox buttonsUser = new HBox(30, addFrind, searchF, remove, readF);
		buttonsUser.setAlignment(Pos.CENTER);

		this.getChildren().addAll(s, hohoSearch, buttonsUser, tableF, backF);
		this.setAlignment(Pos.CENTER);
		this.setSpacing(35);
		this.setStyle("-fx-background-color: lightgray;");

		backF.setOnAction(e -> {
			mainInter = new MainInterFace(PrimaryStage);
			sceneMain = new Scene(mainInter);
			PrimaryStage.setScene(sceneMain);
			PrimaryStage.setFullScreen(true);

		});

		addFrind.setOnAction(e -> addFrindd());

		readF.setOnAction(e -> {
			try {
				FileChooser fileChooser = new FileChooser();
				fileR = fileChooser.showOpenDialog(PrimaryStage);
				if (fileR != null)
					readFriendFile(fileR);
			} catch (Exception ex) {
			}
		});
		searchF.setOnAction(e -> {
			if (!searchFrind.getText().isEmpty()) {

				Node<User> curr = Main.userL.head.getNext();
				while (curr != Main.userL.head) {
					if (curr.getData().getIdUser() == Integer.parseInt(searchFrind.getText())) {
						searchUser = curr.getData();
					}
					curr = curr.getNext();
				}

				if (searchUser != null) {
					tableF.setItems(searchUser.getFriendsArr().linkedToOps());

				} else {
					Alert alert = new Alert(AlertType.WARNING);
					alert.setTitle("search failed");
					alert.setHeaderText("search failed");
					alert.setContentText(" user not found!");
					alert.showAndWait();

				}

				// System.out.println(curr.getData().getFriendsArr().print());
			} else {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("search failed");
				alert.setHeaderText("search failed");
				alert.setContentText(" text field is empty!");
				alert.showAndWait();

			}

		});

		remove.setOnAction(e -> deleteFriend());
	}

	void tableFriend() {

		TableColumn<User, Integer> frindID = new TableColumn<>("Friend ID");
		frindID.setCellValueFactory(new PropertyValueFactory<>("idUser"));
		TableColumn<User, String> namee = new TableColumn<>("Friend Name");
		namee.setCellValueFactory(new PropertyValueFactory<>("name"));
		tableF.getColumns().addAll(frindID, namee);
		tableF.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
	}

	public void readFriendFile(File file) throws FileNotFoundException {
		try {
			Scanner input = new Scanner(file);
			while (input.hasNext()) {
				String[] arrLine = input.nextLine().split(",");
				int id = Integer.parseInt(arrLine[0]);
				Node<User> curr = Main.userL.head.getNext();
				while (curr != Main.userL.head) {
					if (curr.getData().getIdUser() == id) {
						for (int i = 1; i < arrLine.length; i++) {
							int friendID = Integer.parseInt(arrLine[i].trim());
							if (friendID != id)
								curr.getData().addFrind(friendID);
						}
						System.out.println(curr.getData().getFriendsArr().print());
					}
					curr = curr.getNext();

				}
			}
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("read File failed");
			alert.setHeaderText("read File failed");
			alert.setContentText("read File failed!");
			alert.showAndWait();
		}
	}

	void addFrindd() {
		Stage stageAdd = new Stage();
		GridPane grid = new GridPane();
		grid.setVgap(15);
		grid.setHgap(15);

		TextField idUserr = new TextField();
		idUserr.setPrefColumnCount(15);
		TextField idFrind = new TextField();

		Button addFriend = new Button("add friend");

		grid.add(new Label("ID User  :"), 0, 0);
		grid.add(idUserr, 1, 0);
		grid.add(new Label("ID frind :"), 0, 1);
		grid.add(idFrind, 1, 1);
		grid.add(addFriend, 0, 2);

		Scene scene = new Scene(grid);
		stageAdd.setScene(scene);

		addFriend.setOnAction(e -> {

			if (!(idFrind.getText().isEmpty() || idUserr.getText().isEmpty())) {
				Node<User> curr = Main.userL.head.getNext();
				Node<User> userNode = null;
				Node<User> friendNode = null;

				while (curr != Main.userL.head) {
					if (curr.getData().getIdUser() == Integer.parseInt(idUserr.getText())) {
						userNode = curr;
					}
					if (curr.getData().getIdUser() == Integer.parseInt(idFrind.getText())) {
						friendNode = curr;
					}
					curr = curr.getNext();
				}

				if (userNode == null) {
					Alert alert = new Alert(AlertType.WARNING);
					alert.setTitle("Action failed");
					alert.setHeaderText("Friend dont Added");
					alert.setContentText("Friend added failed!");
					alert.showAndWait();
					stageAdd.close();

				} else if (friendNode == null) {
					Alert alert = new Alert(AlertType.WARNING);
					alert.setTitle("Action failed");
					alert.setHeaderText("Friend dont Added");
					alert.setContentText("Friend added failed!");
					alert.showAndWait();
					stageAdd.close();

					// }else if ( idHerhaling(userNode,friendNode) == false) {

				} else if (friendNode.getData().getIdUser() != userNode.getData().getIdUser()) {

					userNode.getData().addFrind(Integer.parseInt(idFrind.getText()));
					// userNode.getData().getFriendsArr().addSort(friendNode.getData());
					System.out.println(userNode.getData().getFriendsArr().print());

					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Success");
					alert.setHeaderText("Friends Added");
					alert.setContentText("Friends added successfully!");
					alert.showAndWait();
					stageAdd.close();
				}
				System.out.println(curr.getData().getFriendsArr().print());

			} else {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Action failed");
				alert.setHeaderText("Friend dont Added");
				alert.setContentText("Friend added failed!");
				alert.showAndWait();
				stageAdd.close();
			}

		});
		stageAdd.show();
	}

	void deleteFriend() {
		Stage stageDelete = new Stage();
		GridPane grid = new GridPane();
		grid.setVgap(15);
		grid.setHgap(15);

		TextField idFrind = new TextField();

		Button removeFriend = new Button("remove friend");

		grid.add(new Label("ID frind :"), 0, 0);
		grid.add(idFrind, 1, 0);
		grid.add(removeFriend, 0, 1);

		Scene scene = new Scene(grid);
		stageDelete.setScene(scene);

		removeFriend.setOnAction(e -> {
			// try {
			if (!idFrind.getText().isEmpty()) {
				Node<User> curr = searchUser.getFriendsArr().head.getNext();
				User remU = null;
				while (curr != searchUser.getFriendsArr().head) {
					if (curr.getData().getIdUser() == Integer.parseInt(idFrind.getText().trim())) {
						remU = curr.getData();
					}
					curr = curr.getNext();
				}

				if (remU != null) {
					searchUser.getFriendsArr().delete(remU);
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Success");
					alert.setHeaderText("Friends delete");
					alert.setContentText("Friends delete successfully!");
					alert.showAndWait();
					stageDelete.close();
				} else {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("failed");
					alert.setHeaderText("Friends not delete");
					alert.setContentText("Friends or user not found");
					alert.showAndWait();
					stageDelete.close();
				}
			}
			System.out.println(searchUser.getFriendsArr().print());

		});
		stageDelete.show();
	}

//	boolean idHerhaling(Node <User>  user, int id) { // لمنع تكرار (UserId)
//		Node<Integer> curr = user.getFriendsArr().head.getNext();
//		while (curr != user.getFriendsArr().head) {
//			if (curr.getData() == id) {
//				return false;
//			}
//			curr = curr.getNext();
//		}
//		return true;
//	}
}
