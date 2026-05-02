package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.Scanner;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Post_Interface extends VBox {
	Button readF;
	File fileR;
	TableView<Post> tablePostMy = new TableView<>();
	TableView<User> tablePostOther = new TableView<>();
	TableView<Post> tableDeleteMySharePost = new TableView<>();

	Button addPost;
	Button deletePostMy;
	Button searchPost1;
	TextField searchPostMy;

	Button deletePostOther;
	Button searchPost2;
	TextField searchPostOther;

	Button deletePostShareMy;

	Button backP;
	MainInterFace mainInter;
	Scene sceneMain;
	static User userP; // هذا اليوزر الاساس الي تم البحث عنه والشغل بده يصير عليه
	static Post searchPostResult;

	public Post_Interface(Stage PrimaryStage) {
		tableDeletePostShareMyFromOther();
		Label s = new Label(" Posts MANAGMENT ");
		s.setStyle("-fx-font-size: 55px; -fx-font-weight: bold; -fx-text-fill: #2a2a2a; -fx-font-family: 'Arial';");

		Label sear = new Label("Search by UserId: ");
		searchPostMy = new TextField();

		HBox hohoSearch = new HBox(20, sear, searchPostMy);
		hohoSearch.setAlignment(Pos.CENTER);

		ImageView a = new ImageView(new Image("add.png"));
		a.setFitHeight(60);
		a.setFitWidth(100);
		addPost = new Button("", a);

		ImageView b = new ImageView(new Image("delete.png"));
		b.setFitHeight(60);
		b.setFitWidth(100);
		deletePostMy = new Button("", b);

		ImageView c = new ImageView(new Image("search.png"));
		c.setFitHeight(60);
		c.setFitWidth(100);
		searchPost1 = new Button("", c);

		ImageView g = new ImageView(new Image("project.png"));
		g.setFitHeight(60);
		g.setFitWidth(100);
		readF = new Button("", g);
		User u;
		tablePostMy();
		HBox hoho1 = new HBox(30, searchPost1, addPost, deletePostMy, readF);
		VBox vovo1 = new VBox(20, new Label("View posts for a specific user"), hohoSearch, hoho1, tablePostMy);

		Label searchh = new Label("Search by PostId: ");
		searchPostOther = new TextField();

		HBox hohoSearch2 = new HBox(20, searchh, searchPostOther);
		hohoSearch2.setAlignment(Pos.CENTER);

		ImageView d = new ImageView(new Image("delete.png"));
		d.setFitHeight(60);
		d.setFitWidth(100);
		deletePostOther = new Button("", d);

		ImageView f = new ImageView(new Image("search.png"));
		f.setFitHeight(60);
		f.setFitWidth(100);
		searchPost2 = new Button("", f);

		ImageView h = new ImageView(new Image("back.png"));
		h.setFitHeight(60);
		h.setFitWidth(100);
		backP = new Button("", h);

		tablePostOther();
		HBox hoho2 = new HBox(30, searchPost2, deletePostOther);
		hoho2.setAlignment(Pos.CENTER);

		VBox vovo2 = new VBox(20, new Label("If the user wants to delete one of those who shared a post with him"),
				hohoSearch2, hoho2, tablePostOther);

		ImageView z = new ImageView(new Image("delete.png"));
		z.setFitHeight(60);
		z.setFitWidth(100);
		deletePostShareMy = new Button("", z);

		HBox hoho3 = new HBox(30, deletePostShareMy);
		hoho2.setAlignment(Pos.CENTER);
		VBox vovo3 = new VBox(20, new Label("To delete a post shared with a specific user"), hoho3,
				tableDeleteMySharePost);

		HBox hoho12 = new HBox(60, vovo1, vovo3, vovo2);
		hoho12.setAlignment(Pos.CENTER);

		this.getChildren().addAll(s, hoho12, backP);
		this.setAlignment(Pos.CENTER);
		this.setSpacing(35);
		this.setStyle("-fx-background-color: lightgray;");

		backP.setOnAction(e -> {
			mainInter = new MainInterFace(PrimaryStage);
			sceneMain = new Scene(mainInter);
			PrimaryStage.setScene(sceneMain);
			PrimaryStage.setFullScreen(true);

		});

		readF.setOnAction(e -> {
			try {
				FileChooser fileChooser = new FileChooser();
				fileR = fileChooser.showOpenDialog(PrimaryStage);
				if (fileR != null)
					readPostFile(fileR);
			} catch (Exception ex) {
			}
		});

		searchPost1.setOnAction(e -> {
			if (!searchPostMy.getText().isEmpty()) {
//				ListIterator<User> curr = Main.userL.listItretor();
//				while(curr.hasNext()) {
//					User user = curr.next();
//					if (user.getIdUser() == Integer.parseInt(searchPostMy.getText().trim())) {
//						userP = user;
//					}
//				}
				Node<User> curr = Main.userL.head.getNext();
				while (curr != Main.userL.head) {
					if (curr.getData().getIdUser() == Integer.parseInt(searchPostMy.getText().trim())) {
						userP = curr.getData();
					}
					curr = curr.getNext();
				}

				if (userP != null) {
					tablePostMy.setItems(userP.getPostsArr().linkedToOps());
					System.out.println(userP.getPostsSHaredArr().print());
					tableDeleteMySharePost.setItems(userP.getPostsSHaredArr().linkedToOps());
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
				alert.setContentText(" text field is empty!");
				alert.showAndWait();
			}

		});

		searchPost2.setOnAction(e -> {  // لاظهار المستخدمين اللذين تم مشاركتهم في منشور معين
			if (!searchPostOther.getText().isEmpty()) {
				Node<Post> currP = userP.getPostsArr().head.getNext();
				while (currP != userP.getPostsArr().head) {
					if (currP.getData().getPostId() == Integer.parseInt(searchPostOther.getText().trim())) {
						searchPostResult = currP.getData();
					}
					currP = currP.getNext();
				}
				if (searchPostResult != null) {
					ObservableList<User> list = FXCollections.observableArrayList(searchPostResult.getShareWith());
					tablePostOther.setItems(list);
				} else {
					Alert alert = new Alert(AlertType.WARNING);
					alert.setTitle("search failed");
					alert.setHeaderText("search failed");
					alert.setContentText(" text field is empty!");
					alert.showAndWait();
				}
			} else {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("search failed");
				alert.setHeaderText("search failed");
				alert.setContentText(" text field is empty!");
				alert.showAndWait();
			}
			System.out.println(userP.getPostsSHaredArr().print());
		});

		addPost.setOnAction(e -> actionAddPosts());
		deletePostMy.setOnAction(e -> deletePost());
		deletePostOther.setOnAction(e -> deleteUserShare());
		deletePostShareMy.setOnAction(e -> deletePostShareMy());
	}

	void tablePostMy() {
		TableColumn<Post, Integer> idPosts = new TableColumn<>(" Post ID");
		idPosts.setCellValueFactory(new PropertyValueFactory<>("postId"));

		TableColumn<Post, Integer> creatorIdd = new TableColumn<>(" Creator ID ");
		creatorIdd.setCellValueFactory(new PropertyValueFactory<>("creatorId"));

		TableColumn<Post, String> contentt = new TableColumn<>(" Content ");
		contentt.setCellValueFactory(new PropertyValueFactory<>("content"));

		TableColumn<Post, String> creationDatee = new TableColumn<>("Creation Date");
		creationDatee.setCellValueFactory(new PropertyValueFactory<>("creationDate"));

		tablePostMy.getColumns().addAll(idPosts, creatorIdd, contentt, creationDatee);
		tablePostMy.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

	}

	private void readPostFile(File file) {
		try {
			Scanner input = new Scanner(file);
			while (input.hasNext()) {
				String[] arr = input.nextLine().split(",");
				int idPost = Integer.parseInt(arr[0].trim());
				int idCreator = Integer.parseInt(arr[1].trim());
				String content = arr[2];
				String date = arr[3];

				Node<User> curr = Main.userL.head.getNext(); // idCreator ببحث عن هذا اليوزر في قائمة اليوزر وبصير اضيف عليه المنشورات
				User user = null;
				while (curr != Main.userL.head) {
					if (curr.getData().getIdUser() == idCreator) {
						user = curr.getData();
						break;
					}
					curr = curr.getNext();
				}

				if (user != null && idHerhaling(user, idPost) && idPost > 0 && content != null && !date.startsWith("-")) {
					Post post = new Post(idPost, idCreator, content, date);
					user.getPostsArr().addSort(post);

					for (int i = 4; i < arr.length; i++) {
						int share = Integer.parseInt(arr[i].trim());

						Node<User> currSH = Main.userL.head.getNext();
						while (currSH != Main.userL.head) {
							if (currSH.getData().getIdUser() == share) { // 
								
								if (!currSH.getData().getPostsSHaredArr().searchSort(post))
								currSH.getData().getPostsSHaredArr().addSort(post); // ضيف هذا المنشور على قائمة اليوزر الي قراته كمنشور مشارك معه

								post.getShareWith().add(currSH.getData()); // ضيف ان هذا المنشور تمت مشاركته مع هذا اليوزر وهذا وهذا
								break;
							}
							currSH = currSH.getNext();
						}
					}
					System.out
							.println("the user" + user.getIdUser() + " post shard with " + user.getPostsSHaredArr().print());
				} else {
					Alert alert = new Alert(AlertType.WARNING);
					alert.setTitle("read File failed");
					alert.setHeaderText("read File failed");
					alert.setContentText("(ID Post) مكرر");
					alert.showAndWait();
				}
			}

		} catch (FileNotFoundException e) {
			System.err.println("errorrr");
		}
	}

	void tablePostOther() {
		TableColumn<User, Integer> userShareID = new TableColumn<>("User share ID");
		userShareID.setCellValueFactory(new PropertyValueFactory<User, Integer>("idUser"));

		tablePostOther.getColumns().add(userShareID);
		tablePostOther.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

	}

	void tableDeletePostShareMyFromOther() {
		TableColumn<Post, Integer> PostId = new TableColumn<>("Post ID");
		PostId.setCellValueFactory(new PropertyValueFactory<Post, Integer>("postId"));

		TableColumn<Post, String> contentt = new TableColumn<>(" Content ");
		contentt.setCellValueFactory(new PropertyValueFactory<>("content"));

		tableDeleteMySharePost.getColumns().addAll(PostId, contentt);
		tableDeleteMySharePost.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

	}

	void actionAddPosts() {
		Stage stageAdd = new Stage();
		GridPane grid = new GridPane();
		grid.setVgap(15);
		grid.setHgap(15);

		TextField iddPost = new TextField();

		TextField iddUser = new TextField();
		if (userP != null)
			iddUser.setText(userP.getIdUser() + "");
		iddUser.setEditable(false);
		iddUser.setStyle("-fx-text-fill: lightgray;");

		TextField content = new TextField();
		// TextField date = new TextField();
		DatePicker date = new DatePicker();

		date.setValue(LocalDate.now());
		date.setOnAction(event -> {
			LocalDate selectedDate = date.getValue();
		});

		Button addPost = new Button("Add Post");

		grid.add(new Label("Post ID   :"), 0, 0);
		grid.add(iddPost, 1, 0);
		grid.add(new Label("User ID   :"), 0, 1);
		grid.add(iddUser, 1, 1);
		grid.add(new Label("content:"), 0, 2);
		grid.add(content, 1, 2);
		grid.add(new Label("Date post:"), 0, 3);
		grid.add(date, 1, 3);
		grid.add(addPost, 0, 4);

		addPost.setOnAction(e -> {
			if (!(iddPost.getText().isEmpty() || content.getText().isEmpty() || date.getValue().toString().isEmpty())
					&& idHerhaling(userP, Integer.parseInt(iddPost.getText().trim()))
					&& Integer.parseInt(iddPost.getText()) > 0 && !date.getValue().toString().startsWith("-")
					&& userP != null) {
				try {
					Post p = new Post(Integer.parseInt(iddPost.getText()), Integer.parseInt(iddUser.getText()),
							content.getText(), date.getValue().toString());
					userP.getPostsArr().addSort(p);
					
					
					System.out.println(userP.getPostsArr().print());
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Success");
					alert.setHeaderText("post Added");
					alert.setContentText("post added successfully!");
					alert.showAndWait();
					stageAdd.close();
				} catch (Exception ee) {
					Alert alert = new Alert(AlertType.WARNING);
					alert.setTitle("Action failed");
					alert.setHeaderText("Post dont Added");
					alert.setContentText("Post added failed!");
					alert.showAndWait();
					stageAdd.close();
				}
			} else {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Action failed");
				alert.setHeaderText("Post dont Added");
				alert.setContentText("Text fIeld is empty or ID Post Dupliseer (مكرر)!");
				alert.showAndWait();
				stageAdd.close();
			}

		});

		Scene scene = new Scene(grid);
		stageAdd.setScene(scene);
		stageAdd.show();

	}

	void deletePost() {
		Stage stageRem = new Stage();
		GridPane grid = new GridPane();
		grid.setVgap(15);
		grid.setHgap(15);

		TextField iddPost = new TextField();
		TextField iddUser = new TextField();
		if (userP != null)
			iddUser.setText(userP.getIdUser() + "");
		iddUser.setEditable(false);
		iddUser.setStyle("-fx-text-fill: lightgray;");
		Button deletePost = new Button("delete Post");

		grid.add(new Label("User ID   :"), 0, 0);
		grid.add(iddUser, 1, 0);
		grid.add(new Label("Post ID to be deleted :"), 0, 1);
		grid.add(iddPost, 1, 1);
		grid.add(deletePost, 0, 2);

		Scene scene = new Scene(grid);
		stageRem.setScene(scene);

		deletePost.setOnAction(e -> {
			if (!iddPost.getText().isEmpty() && userP != null) {
				Node<Post> curr = userP.getPostsArr().head.getNext();
				Post p = null;
				while (curr != userP.getPostsArr().head) {
					if (curr.getData().getPostId() == Integer.parseInt(iddPost.getText().trim())) {
						p = curr.getData();
					}
					curr = curr.getNext();

				}

				if (p != null) {
					userP.getPostsArr().delete(p);
					
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Success");
					alert.setHeaderText("post deleted");
					alert.setContentText("post deleted successfully!");
					alert.showAndWait();
					stageRem.close();

					for (User user : p.getShareWith()) {
						user.getPostsSHaredArr().delete(p); // عشان احذف هذا المنشور ايضا من عند الاصدقاء الي شاركته معهم
					}
					
					Alert aalert = new Alert(AlertType.INFORMATION);
					aalert.setTitle("Success");
					aalert.setHeaderText("post deleted");
					aalert.setContentText("post deleted successfully!");
					aalert.showAndWait();
					stageRem.close();
				} else {
					Alert alert = new Alert(AlertType.WARNING);
					alert.setTitle("Action failed");
					alert.setHeaderText("post dont deleted");
					alert.setContentText("post deleted failed!");
					alert.showAndWait();
					stageRem.close();
				}

			} else {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Action failed");
				alert.setHeaderText("post dont deleted");
				alert.setContentText("post deleted failed!");
				alert.showAndWait();
				stageRem.close();
			}
		});

		stageRem.show();
	}


	void deletePostShareMy() {
		Stage stageRem = new Stage();
		GridPane grid = new GridPane();
		grid.setVgap(15);
		grid.setHgap(15);
		TextField iddPost = new TextField();
		Button deletePostFromShareMy = new Button("delete Share Post");

		grid.add(new Label("Post ID   :"), 0, 0);
		grid.add(iddPost, 1, 0);
		grid.add(deletePostFromShareMy, 0, 1);

		Scene scene = new Scene(grid);
		stageRem.setScene(scene);

		deletePostFromShareMy.setOnAction(e -> {
			Post p = null;
			if (!iddPost.getText().isEmpty()) {
				Node<Post> curr = userP.getPostsSHaredArr().head.getNext();
				while (curr != userP.getPostsSHaredArr().head) {
					if (curr.getData().getPostId() == Integer.parseInt(iddPost.getText().trim())) {
						p = curr.getData();
					}
					curr = curr.getNext();
				}
				System.out.println("the post is :" + p);
				if (p != null) {
					userP.getPostsSHaredArr().delete(p);
					
					
					Alert aalert = new Alert(AlertType.INFORMATION);
					aalert.setTitle("Success");
					aalert.setHeaderText("post deleted");
					aalert.setContentText("post deleted successfully!");
					aalert.showAndWait();
					stageRem.close();

				} else {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Action failed");
					alert.setHeaderText("post dont deleted");
					alert.setContentText("post deleted failed!");
					alert.showAndWait();
					stageRem.close();
				}

			} else {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Action failed");
				alert.setHeaderText("post dont deleted");
				alert.setContentText("post deleted failed!");
				alert.showAndWait();
				stageRem.close();
			}

			System.out.println(userP.getPostsSHaredArr().print());
			stageRem.close();

		});
		stageRem.show();

	}
	
	void deleteUserShare() {
		Stage stageRem = new Stage();
		GridPane grid = new GridPane();
		grid.setVgap(15);
		grid.setHgap(15);
		TextField iddPost = new TextField(searchPostResult.getPostId() + "");
		iddPost.setEditable(false);
		iddPost.setStyle("-fx-text-fill: lightgray;");
		TextField iddUser = new TextField();
		
		Button deleteShareUser = new Button("delete Share User");
		
		grid.add(new Label("User ID   :"), 0, 0);
		grid.add(iddUser, 1, 0);
		grid.add(new Label("Post ID to be deleted :"), 0, 1);
		grid.add(iddPost, 1, 1);
		grid.add(deleteShareUser, 0, 2);
		
		Scene scene = new Scene(grid);
		stageRem.setScene(scene);
		
		deleteShareUser.setOnAction(e -> {
			if (searchPostResult != null) {
				User u = null;
// بلف عالاراي ليست للبوست الي بحثت عنه التي تخزن الي شاركت 				// معهم منشوري في وقت سابق
				
				for (User userDelete : searchPostResult.getShareWith()) { // Post search other Result
					if (userDelete.getIdUser() == Integer.parseInt(iddUser.getText().trim())) {
						u = userDelete;
						
					}
				}
				
				if (u != null) {
					searchPostResult.getShareWith().remove(u); // حذف صديق من المشاركة من الاصدقاء الذي قد كنت شاركت
					// معهم منشوري في وقت سابق
					u.getPostsSHaredArr().delete(searchPostResult);
					
					
					Alert aalert = new Alert(AlertType.INFORMATION);
					aalert.setTitle("Success");
					aalert.setHeaderText("post deleted");
					aalert.setContentText("post deleted successfully!");
					aalert.showAndWait();
					stageRem.close();
				} else {
					Alert alert = new Alert(AlertType.WARNING);
					alert.setTitle("Action failed");
					alert.setHeaderText("post dont deleted");
					alert.setContentText("post deleted failed!");
					alert.showAndWait();
					stageRem.close();
				}
			}
			stageRem.close();
			
		});
		stageRem.show();
		
	}

	boolean idHerhaling(User user, int id) {
		Node<Post> curr = user.getPostsArr().head.getNext();
		while (curr != user.getPostsArr().head) {
			if (curr.getData().getPostId() == id) {
				return false;
			}
			curr = curr.getNext();
		}
		return true;
	}
}