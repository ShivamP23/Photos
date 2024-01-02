/**
 * This controller is designed to manage user objects by providing methods for writing to and reading from a file.
 * The writeFromAFile and readFromAFile functions will be invoked before the application opens and closes, respectively, to save and retrieve data.
 * It adheres to the Singleton design pattern, and all classes intended for serialization implement the Serializable interface.
 */
package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import model.Album;
import model.Photo;
import model.User;

/**
 * The DataController class represents a controller for handling user data in
 * the system. This class is responsible for managing the list of users, adding
 * and deleting users, and reading and writing user data to a file. It also
 * provides methods for setting and retrieving the current session user. This
 * class implements the Serializable interface to allow for serialization of
 * user data to a file.
 *
 * @author Shivam Patel and Neel Patel
 *
 */
public class DataController implements Serializable {

    /**
     * A unique identifier for the serialized version of this class.
     */
    private static final long serialVersionUID = 6L;
    private static DataController instance = null;
    private ArrayList<User> users;

    /**
     * The singleton instance of the UserDataController class.
     */
    public static User currentSessionUser;

    /**
     * Constructor for creating a new object of DataController with default user
     * stock and initialize it with default photos.
     */
    private DataController() {
        users = new ArrayList<>();
        currentSessionUser = null;

        User stock = new User("stock");
        users.add(stock);
        Album stockAlbum = new Album("stock");
        stock.addAlbum(stockAlbum);

        Photo p1 = new Photo("Acura", "database/acura.jpg.jpg", LocalDateTime.now().format(DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm a")));
        Photo p2 = new Photo("Bugatti Mistral", "database/BugattiMistral.jpg", LocalDateTime.now().format(DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm a")));
        Photo p3 = new Photo("Jaguar F-Type", "database/Jaguar.jpg", LocalDateTime.now().format(DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm a")));
        Photo p4 = new Photo("Lamborghini Urus", "database/Lamborghini.jpg", LocalDateTime.now().format(DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm a")));
        Photo p5 = new Photo("Lamborghini huracan", "database/lamborghiniHuracan.jpg", LocalDateTime.now().format(DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm a")));
        Photo p6 = new Photo("Porsche 911", "database/Porsche.jpg", LocalDateTime.now().format(DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm a")));

        stockAlbum.addPhotos(p1);
        stockAlbum.addPhotos(p2);
        stockAlbum.addPhotos(p3);
        stockAlbum.addPhotos(p4);
        stockAlbum.addPhotos(p5);
        stockAlbum.addPhotos(p6);
    }

    /**
     * Method for returning the singleton instance of the DataController class.
     * If the instance does not exist, it creates a new one.
     *
     * @return the singleton instance of the DataController class.
     */
    public static DataController getInstance() {
        if (instance == null) {
            instance = new DataController();
        }
        return instance;
    }

    /**
     * Method for adding a new user to the list of users.
     *
     * @param user the user to be added.
     */
    public void addUser(User user) {
        users.add(user);
    }

    /**
     * Method for deleting a user from the list of users with username.
     *
     * @param username the username of the user to be deleted.
     */
    public void deleteUser(String username) {
        for (User user : users) {
            if (user.getUserName().equals(username)) {
                users.remove(user);
                break;
            }
        }
    }

    /**
     * Method for returning a list of usernames of all the users in the system.
     *
     * @return a list of usernames of all the users in the system.
     */
    public ArrayList<String> getUsernames() {
        ArrayList<String> usernames = new ArrayList<String>();
        for (User user : users) {
            usernames.add(user.getUserName());
        }
        return usernames;
    }

    /**
     * Method for checking if the list of users contains a user with the
     * provided username.
     *
     * @param username the username to be checked.
     * @return true if the user exists, false otherwise.
     */
    public boolean containsUser(String username) {
        for (User user : users) {
            if (user.getUserName().equals(username)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Method for reading users data from the file.
     *
     * @return the DataController instance containing the user data read from a
     * file.
     */
    public static DataController readFromAFile() {
        DataController loadUsers = DataController.getInstance();
        File file = new File("database/users.ser");
        try {
            if (file.length() == 0) {
                //
            } else {
                try ( ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                    Object o = ois.readObject();
                    if (o instanceof ArrayList<?>) {
                        @SuppressWarnings("unchecked")
                        ArrayList<User> userList = (ArrayList<User>) o;
                        loadUsers.users = userList;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return loadUsers;
    }

    /**
     * Method for writing the user's data to the file.
     */
    public void writeToAFile() throws IOException {
        File f = new File("database/users.ser");
        if(!f.exists()){
            f.createNewFile();
        }
        try ( ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("database/users.ser"))) {
            oos.writeObject(users);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method for setting the current session user with username.
     *
     * @param username the username of the user to be set as the current session
     * user.
     */
    public void setCurrentSessionUser(String username) {
        for (User allUser : users) {
            if (allUser.getUserName().equals(username)) {
                currentSessionUser = allUser;
            }
        }
    }

    /**
     * Method for returning the current session user.
     *
     * @return the current session user.
     */
    public static User getCurrentSessionUser() {
        return currentSessionUser;
    }
}
