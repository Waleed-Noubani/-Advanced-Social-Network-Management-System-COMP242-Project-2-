package application;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class User implements Comparable<User> {
	// private static int empno = 0;
	private int idUser;
	private String name;
	private int age;
	private LinkdList<User> friendsArr = new LinkdList<>();
	private LinkdList<Post> PostsArr = new LinkdList<>();
	private LinkdList<Post> postsSHaredArr = new LinkdList<>();

	public User(int idUser, String name, int age) {
		setIdUser(idUser);
		setName(name);
		setAge(age);
	}

	void addFrind(int frindId) {
		Node<User> curr = Main.userL.head.getNext();
		while (curr != Main.userL.head) {
			if (curr.getData().getIdUser() == frindId) {
				User u = curr.getData();
				if (!friendsArr.searchSort(u))
					friendsArr.addSort(u);
				break;
			} else
				curr = curr.getNext();
		}
	}

//	void addPost(Post shareId) {
//		Node<User> curr = Main.userL.head.getNext();
//		while (curr != Main.userL.head) {
//			if (curr.getData().getIdUser() == shareId.getCreatorId()) {
//				PostsArr.addSort(shareId);
//				break;
//			} else
//				curr = curr.getNext();
//		}
//	}

//	void addSharePost(int PostSHareId) {
//		Node<Post> currP = Post_Interface.usPo.getPostsArr().head.getNext();
//		while (currP != Post_Interface.usPo.getPostsArr().head) {
//			if (currP.getData().getPostId() == PostSHareId) {
//				postsSHaredArr.addSort(PostSHareId);
//				break;
//			} else
//				currP = currP.getNext();
//		}
//	}

//	static String counterEmpno() {
//		return "" + empno++;
//	}

	public int getIdUser() {
		return idUser;
	}

	public void setIdUser(int idUser) {
		if (idUser > 0)
			this.idUser = idUser;
		else {

			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("There is an exception");
			alert.setHeaderText("Look, there is an input error in ID ");
			alert.setContentText("Please, enter only ID");
			alert.showAndWait();
		}
	}

	public String getName() {
		return name;
	}

//	public void setName(String name) {
//		this.name = name;
//	}
	public void setName(String name) {
		if (isValidName(name)) {
			this.name = name;
		} else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("There is an exception");
			alert.setHeaderText("Look, there is an input error ");
			alert.setContentText("Please, enter only the characters in first name");
			alert.showAndWait();

		}

	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		if (age >= 10 && age <= 100) {
			this.age = age;
		} else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("There is an exception");
			alert.setHeaderText("Look, there is an input error ");
			alert.setContentText("Please, enter age agen");
			alert.showAndWait();
		}
	}

	@Override
	public String toString() {
		return "User [" + idUser + ", " + name + "," + age + "]";
	}

	@Override
	public int compareTo(User o) {
		return this.name.compareTo(o.name);
	}

	public LinkdList<User> getFriendsArr() {
		return friendsArr;
	}

	public void setFriendsArr(LinkdList<User> friendsArr) {
		this.friendsArr = friendsArr;
	}

	public static boolean isValidName(String name) {
		for (int i = 0; i < name.length(); i++) {
			char ch = name.charAt(i);
			if (!Character.isLetter(ch)) {
				return false;
			}
		}
		return true;
	}

	public LinkdList<Post> getPostsArr() {
		return PostsArr;
	}

	public void setPostsArr(LinkdList<Post> postsArr) {
		PostsArr = postsArr;
	}

	public LinkdList<Post> getPostsSHaredArr() {
		return postsSHaredArr;
	}

	public void setPostsSHaredArr(LinkdList<Post> postsSHaredArr) {
		this.postsSHaredArr = postsSHaredArr;
	}

//	public static boolean validPhone(int age) {
//		
//		for (int i = 0; i < age.length(); i++) {
//			if (!Character.isDigit(phoneNumber.charAt(i))) {
//				return false;
//			}
//		}
//
//		return true;
//	}

}
