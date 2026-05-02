package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ListIterator;
import java.util.Scanner;

import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class UserInterFace extends VBox {
	RadioButton r1;
	RadioButton r2;
	Button add;
	Button delete;
	Button update;
	Button search;
	TextField textSearch;
	TableView<User> tableUser = new TableView<>();
	static ObservableList<User> searchRes = FXCollections.observableArrayList();
	User user;
	Button readF;
	Button backU;
	File fileR;

	MainInterFace mainInter;
	Scene sceneMain;

	UserInterFace(Stage PrimaryStage) {

		Label s = new Label(" USER MANAGMENT ");
		s.setStyle("-fx-font-size: 55px; -fx-font-weight: bold; -fx-text-fill: #2a2a2a; -fx-font-family: 'Arial';");

		Label sear = new Label("Search : ");
		textSearch = new TextField();
		r1 = new RadioButton("User ID");
		r2 = new RadioButton("Name");
		ToggleGroup toggle = new ToggleGroup();
		r1.setToggleGroup(toggle);
		r2.setToggleGroup(toggle);
		toggle.selectToggle(r1);

		HBox hohoSearch = new HBox(20, sear, textSearch, r1, r2);
		hohoSearch.setAlignment(Pos.CENTER);

		ImageView a = new ImageView(new Image("subscription-user.png"));
		a.setFitHeight(60);
		a.setFitWidth(100);
		add = new Button("", a);

		ImageView b = new ImageView(new Image("remove-user.png"));
		b.setFitHeight(60);
		b.setFitWidth(100);
		delete = new Button("", b);

		ImageView c = new ImageView(new Image("update.png"));
		c.setFitHeight(60);
		c.setFitWidth(100);
		update = new Button("", c);

		ImageView d = new ImageView(new Image("user-avatar.png"));
		d.setFitHeight(60);
		d.setFitWidth(100);
		search = new Button("", d);

		ImageView f = new ImageView(new Image("project.png"));
		f.setFitHeight(60);
		f.setFitWidth(100);
		readF = new Button("", f);

		ImageView g = new ImageView(new Image("back.png"));
		g.setFitHeight(90);
		g.setFitWidth(160);
		backU = new Button("", g);

		tableDisblay();
		tableUser.setItems(Main.userL.linkedToOps());

		HBox buttonsUser = new HBox(30, add, search, delete, update, readF);
		buttonsUser.setAlignment(Pos.CENTER);
		this.getChildren().addAll(s, hohoSearch, buttonsUser, tableUser, backU);
		this.setAlignment(Pos.CENTER);
		this.setSpacing(35);
		this.setStyle("-fx-background-color: lightgray;");

		backU.setOnAction(e -> {
			mainInter = new MainInterFace(PrimaryStage);
			sceneMain = new Scene(mainInter);
			PrimaryStage.setScene(sceneMain);
			PrimaryStage.setFullScreen(true);

		});
		add.setOnAction(e -> actionAdd());
		update.setOnAction(e -> actionUpdate());

		readF.setOnAction(e -> {
			try {
				FileChooser fileChooser = new FileChooser();
				fileR = fileChooser.showOpenDialog(PrimaryStage);
				if (fileR != null)
					readUserFile(fileR);
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
		});

		search.setOnAction(e -> {
			if (!textSearch.getText().isEmpty()) {
				searchRes.clear();
				if (r1.isSelected()) {

					for (User userLoop : Main.userL) {
						if (userLoop.getIdUser() == Integer.parseInt(textSearch.getText().trim())) {
							user = userLoop;
						}

					}
//					Node<User> curr = Main.userL.head.getNext();
//					while (curr != Main.userL.head) {
//						if (curr.getData().getIdUser() == Integer.parseInt(textSearch.getText().trim())) {
//							user = curr.getData();
//							break;
//						}
//						curr = curr.getNext();
//					}
					// System.out.println("the user id is :" + user.getIdUser());
				} else if (r2.isSelected()) {
					// ListIterator<User> curr = Main.userL.listItretor();
					Node<User> curr = Main.userL.head.getNext();

					while (curr != Main.userL.head) {
						// User u = curr.next();
						if (curr.getData().getName().equalsIgnoreCase(textSearch.getText().trim())) {
							user = curr.getData();
							break;
						}
						curr = curr.getNext();
					}
				}

				if (user != null) {
					searchRes.add(user);
					tableUser.setItems(searchRes);
				} else {
					Alert alert = new Alert(AlertType.WARNING);
					alert.setTitle("search failed");
					alert.setHeaderText("search failed");
					alert.setContentText(" not found user !");
					alert.showAndWait();
				}

			} else {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("search failed");
				alert.setHeaderText("search failed");
				alert.setContentText("text field is empty !");
				alert.showAndWait();
			}
		});

		delete.setOnAction(e -> deleteUser());

	}

	public void tableDisblay() {
		TableColumn<User, Integer> idColumn = new TableColumn<>(" ID User ");
		idColumn.setCellValueFactory(new PropertyValueFactory<>("idUser"));

		TableColumn<User, String> nameColumn = new TableColumn<>(" Name User ");
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

		TableColumn<User, Integer> ageColumn = new TableColumn<>(" Age User ");
		ageColumn.setCellValueFactory(new PropertyValueFactory<>("age"));

		tableUser.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		tableUser.getColumns().addAll(idColumn, nameColumn, ageColumn);
	}

	void readUserFile(File nameF) throws FileNotFoundException {
		try {
			Scanner input = new Scanner(nameF);
			while (input.hasNext()) {
				String[] arrLine = input.nextLine().split(" ");
				int id = Integer.parseInt(arrLine[0].trim());
				String name = arrLine[1];
				int age = Integer.parseInt(arrLine[2]);
				if (idHerhaling(id) && id > 0 && age > 10)
					Main.userL.addSort(new User(id, name, age));
				else {
					Alert alert = new Alert(AlertType.WARNING);
					alert.setTitle("read File failed");
					alert.setHeaderText("read File failed");
					alert.setContentText("(ID) مكرر");
					alert.showAndWait();
				}
			}
			Main.userL.print();

		} catch (Exception e) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("read File failed");
			alert.setHeaderText("read File failed");
			alert.setContentText("read File failed!");
			alert.showAndWait();
		}

	}

	public void actionAdd() {
		Stage stageAdd = new Stage();
		GridPane grid = new GridPane();
		grid.setVgap(15);
		grid.setHgap(15);

		TextField idd = new TextField();
//		idd.setEditable(false);
//		idd.setStyle("-fx-text-fill: lightgray;");
		idd.setPrefColumnCount(15);
		TextField name = new TextField();
		TextField age = new TextField();

		Button addUser = new Button("add User");

		grid.add(new Label("ID   :"), 0, 0);
		grid.add(idd, 1, 0);
		grid.add(new Label("Name:"), 0, 1);
		grid.add(name, 1, 1);
		grid.add(new Label("Age User:"), 0, 2);
		grid.add(age, 1, 2);
		grid.add(addUser, 0, 3);

		Scene scene = new Scene(grid);
		stageAdd.setScene(scene);

		addUser.setOnAction(e -> {
			if (!(idd.getText().isEmpty() || name.getText().isEmpty() || age.getText().isEmpty())
					&& idHerhaling(Integer.parseInt(idd.getText().trim())) && Integer.parseInt(age.getText()) > 10) {
				try {
					User u = new User(Integer.parseInt(idd.getText().trim()), name.getText(),
							Integer.parseInt(age.getText().trim()));

					Main.userL.addSort(u);
					System.out.println(Main.userL.print());

					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Success");
					alert.setHeaderText("User Added");
					alert.setContentText("User added successfully!");
					alert.showAndWait();
					stageAdd.close();
				} catch (Exception e2) {
					Alert alert = new Alert(AlertType.WARNING);
					alert.setTitle("Action failed");
					alert.setHeaderText("User dont Added");
					alert.setContentText("User added failed!");
					alert.showAndWait();
					stageAdd.close();
				}

			} else {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Action failed");
				alert.setHeaderText("User dont Added");
				alert.setContentText("Text fIeld is empty or ID Dupliseer or age -");
				alert.showAndWait();
				stageAdd.close();
			}

		});

		stageAdd.show();

	}

	public void actionUpdate() {
		if (user != null) {
			Stage stageUpdate = new Stage();
			GridPane grid = new GridPane();
			grid.setVgap(15);
			grid.setHgap(15);

			TextField idd = new TextField(user.getIdUser() + "");
			idd.setEditable(false);
			idd.setStyle("-fx-text-fill: lightgray;");
			idd.setPrefColumnCount(15);
			TextField name = new TextField(user.getName());
			TextField age = new TextField(user.getAge() + "");

			Button updateUser = new Button("Update user");

			grid.add(new Label("ID   :"), 0, 0);
			grid.add(idd, 1, 0);
			grid.add(new Label("Name: "), 0, 1);
			grid.add(name, 1, 1);
			grid.add(new Label("Age User:"), 0, 2);
			grid.add(age, 1, 2);
			grid.add(updateUser, 0, 3);

			Scene scene = new Scene(grid);
			stageUpdate.setScene(scene);

			updateUser.setOnAction(e -> {

				if (!(idd.getText().isEmpty() || name.getText().isEmpty() || age.getText().isEmpty())) {
					try {
						User u = new User(Integer.parseInt(idd.getText().trim()), name.getText(),
								Integer.parseInt(age.getText().trim()));
						Main.userL.delete(user);

						Main.userL.addLast(u);
						System.out.println(Main.userL.print());
						tableUser.setItems(Main.userL.linkedToOps());

						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setTitle("Success");
						alert.setHeaderText("Friends Update");
						alert.setContentText("Friends Update successfully!");
						alert.showAndWait();
						stageUpdate.close();
					} catch (Exception e2) {
						Alert alert = new Alert(AlertType.WARNING);
						alert.setTitle("Action failed");
						alert.setHeaderText("User dont Update");
						alert.setContentText("User update failed!");
						alert.showAndWait();
						stageUpdate.close();
					}

				} else {
					Alert alert = new Alert(AlertType.WARNING);
					alert.setTitle("Action failed");
					alert.setHeaderText("User dont updated");
					alert.setContentText("User update failed!");
					alert.showAndWait();
					stageUpdate.close();
				}
			});

			stageUpdate.show();
		} else {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Action failed");
			alert.setHeaderText("User dont updated");
			alert.setContentText("User update failed!");
			alert.showAndWait();
		}
	}

	void deleteUser() {
		if (user != null) {
			Main.userL.delete(user);
			System.out.println(Main.userL.print());

			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Success");
			alert.setHeaderText("user delete");
			alert.setContentText("user delete successfully!");
			alert.showAndWait();
		} else {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Action failed");
			alert.setHeaderText("User dont removed");
			alert.setContentText("User not found!");
			alert.showAndWait();
		}
	}

	boolean idHerhaling(int id) { // لمنع تكرار (UserId)
		Node<User> curr = Main.userL.head.getNext();
		while (curr != Main.userL.head) {
			if (curr.getData().getIdUser() == id) {
				return false;
			}
			curr = curr.getNext();
		}
		return true;
	}
}
//if (!textSearch.getText().isEmpty()) {
//	searchRes.clear();
//	if (r1.isSelected()) {
//		for (User curr : Main.userL) {
//			if (curr.getIdUser() == Integer.parseInt(textSearch.getText().trim())) {
//				user = curr;
//			}
//		}