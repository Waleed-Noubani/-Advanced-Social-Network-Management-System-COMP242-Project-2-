package application;

import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.scene.Scene;

public class MainInterFace extends VBox {

	Button userInterFace;
	Button frindInterFace;
	Button postInterFace;
	Button reportAndStatistics;

	UserInterFace userClass;
	Scene userScene;

	FrindsInterface frindClass;
	Scene frindScene;

	Post_Interface postClass;
	Scene postScene;

	Report_Statistic reportClass;
	Scene reportScene;

	public MainInterFace(Stage primaryStage) {

		Menu uMenu = new Menu("User");
		Menu fMenu = new Menu("Friends");
		Menu pMenu = new Menu("Posts");
		Menu sMenu = new Menu("Statistics and reports");
		Menu closMenu = new Menu(" Closing ");

		MenuItem openUser = new MenuItem("Open User Interface");
		MenuItem openFriends = new MenuItem("Open Friends Interface");
		MenuItem openPost = new MenuItem("Open Post Interface");
		MenuItem openStatistics = new MenuItem("Open Reports and Statistics");
		MenuItem clos = new MenuItem("Closing");

		uMenu.getItems().add(openUser);
		fMenu.getItems().add(openFriends);
		pMenu.getItems().add(openPost);
		sMenu.getItems().add(openStatistics);
		closMenu.getItems().add(clos);

		MenuBar menuBar = new MenuBar(uMenu, fMenu, pMenu, sMenu, closMenu);
		menuBar.setStyle("-fx-font-size: 16px;");

		ImageView mainImage = new ImageView(new Image("mainImage.jpg"));
		mainImage.setFitHeight(515);
		mainImage.setFitWidth(1200);

		ImageView a = new ImageView(new Image("user-gear.png"));
		a.setFitHeight(85);
		a.setFitWidth(140);
		userInterFace = new Button("", a);

		ImageView b = new ImageView(new Image("followers.png"));
		b.setFitHeight(85);
		b.setFitWidth(140);
		frindInterFace = new Button("", b);

		ImageView c = new ImageView(new Image("post.png"));
		c.setFitHeight(85);
		c.setFitWidth(140);
		postInterFace = new Button("", c);

		ImageView d = new ImageView(new Image("immigration.png"));
		d.setFitHeight(85);
		d.setFitWidth(140);
		reportAndStatistics = new Button("", d);

		HBox buttonsMejarHbox = new HBox(30, userInterFace, frindInterFace, postInterFace, reportAndStatistics);
		buttonsMejarHbox.setAlignment(Pos.CENTER);
		this.getChildren().addAll(menuBar, buttonsMejarHbox, mainImage);
		this.setAlignment(Pos.CENTER);
		this.setSpacing(35);
		this.setStyle("-fx-background-color: lightgray;");

		userInterFace.setOnAction(e -> {
			userClass = new UserInterFace(primaryStage);
			userScene = new Scene(userClass);
			primaryStage.setScene(userScene);
			primaryStage.setFullScreen(true);
		});

		frindInterFace.setOnAction(e -> {
			frindClass = new FrindsInterface(primaryStage);
			frindScene = new Scene(frindClass);
			primaryStage.setScene(frindScene);
			primaryStage.setFullScreen(true);
		});

		postInterFace.setOnAction(e -> {
			postClass = new Post_Interface(primaryStage);
			postScene = new Scene(postClass);
			primaryStage.setScene(postScene);
			primaryStage.setFullScreen(true);
		});

		reportAndStatistics.setOnAction(e -> {
			reportClass = new Report_Statistic(primaryStage);
			reportScene = new Scene(reportClass);
			primaryStage.setScene(reportScene);
			primaryStage.setFullScreen(true);
		});
		//////////////////////////////////////////////////
		openUser.setOnAction(e -> {
			userClass = new UserInterFace(primaryStage);
			userScene = new Scene(userClass);
			primaryStage.setScene(userScene);
			primaryStage.setFullScreen(true);
		});

		openFriends.setOnAction(e -> {
			frindClass = new FrindsInterface(primaryStage);
			frindScene = new Scene(frindClass);
			primaryStage.setScene(frindScene);
			primaryStage.setFullScreen(true);
		});   

		openPost.setOnAction(e -> {
			postClass = new Post_Interface(primaryStage);
			postScene = new Scene(postClass);
			primaryStage.setScene(postScene);
			primaryStage.setFullScreen(true);
		});

		openStatistics.setOnAction(e -> {
			reportClass = new Report_Statistic(primaryStage);
			reportScene = new Scene(reportClass);
			primaryStage.setScene(reportScene);
			primaryStage.setFullScreen(true);
		});
		
		clos.setOnAction(e ->primaryStage.close() );

	}
}
