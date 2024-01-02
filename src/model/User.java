package model;

import controller.DataController;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * This class represents a User.
 * 
 * @author Shivam Patel and Neel Patel
 */
public class User implements Serializable {

    private static final long serialVersionUID = 5L;
    private String username;
    private ArrayList<Album> albums = new ArrayList<>();
    private ArrayList<TagType> tagTypes = new ArrayList<TagType>();
    public static Album currentSessionAlbum;

    /**
     * Constructor for creating a new User.
     * Initializes userName to null and adds two default TagCategories.
     */
    public User() {
        username = null;
        tagTypes.add(new TagType("Location", true));
        tagTypes.add(new TagType("Person"));
    }

    /**
     * Constructor for User that takes in a username.
     * @param name the username for the user.
     */
    public User(String name) {
        this.username = name;
        tagTypes.add(new TagType("Location", true));
        tagTypes.add(new TagType("Person"));
    }

    /**
     * Method for setting new username of the user.
     * @param name the username for the user.
     */
    public void setUserName(String name) {
        this.username = name;
    }

    /**
     * Method for getting username.
     * @return the username for the user.
     */
    public String getUserName() {
        return username;
    }

    /**
     * Method for adding an album to the user's list of albums.
     * @param album the album to add.
     */
    public void addAlbum(Album album) {
        albums.add(album);
    }

    /**
     * Method for removing an album from the user's list of albums.
     * @param selectedAlbum the name of the album to remove.
     */
    public void removeAlbum(String selectedAlbum) {
        Iterator<Album> iterator = albums.iterator();
        while(iterator.hasNext()) {
            Album a = iterator.next();
            if(a.getAlbumName().equals(selectedAlbum)) {
                iterator.remove();
            }
        }
    }

    /**
     * Method for setting new name of the album.
     * @param currentName the current name of the album to rename.
     * @param newName the new name for the album.
     */
    public void renameAlbum(String currentName, String newName) {
        for(Album a: albums) {
            if(a.getAlbumName().equals(currentName)) {
                a.setAlbumName(newName);
            }
        }
    }

    /**
     * Method for getting the user's list of albums.
     * @return the user's list of albums.
     */
    public ArrayList<Album> getAlbumList() {
        return albums;
    }

    /**
     * Method for setting the current session album for the user.
     * @param albumName the name of the album to set as the current session album.
     */
    public void setCurrentSessionAlbum(String albumName) {
        User currentUser = DataController.getCurrentSessionUser();
        ArrayList<Album> allAlbum = currentUser.getAlbumList();
        for(Album a: allAlbum) {
            if(a.getAlbumName().equals(albumName)) {
                currentSessionAlbum = a;
            }
        }
    }

    /**
     * Method for getting the current session album for the user.
     * @return the current session album for the user.
     */
    public static Album getCurrentSessionAlbum() {
        return currentSessionAlbum;
    }

    /**
     * Method for adding a Tag Type to the user's list of TagTypes.
     * @param t the TagType to add.
     */
    public void addTagType(TagType t) {
        tagTypes.add(t);
    }
	
    /**
     * Method for removing the specified tag type from the list of types.
     *
     * @param tagType the name of the tag tag to be removed
     */
    public void removeTagType(String tagType) 
    {
        Iterator<TagType> iterator = tagTypes.iterator();
        while(iterator.hasNext())
        {
            TagType t = iterator.next();
            if(t.getName().equals(tagType))
            {
                iterator.remove();
            }
        }
    }

    /**
     * Method for returning list of all types names.
     *
     * @return an ArrayList of String objects representing types names
     */
    public ArrayList<String> getTypeList()
    {
        ArrayList<String> typeList = new ArrayList<String>();
        for(TagType c : tagTypes)
        {
            typeList.add(c.getName());
        }
        return typeList;
    }

    /**
     * Method for returning a list of all tag types.
     *
     * @return an ArrayList of TagType objects representing all types
     */
    public ArrayList<TagType> getTypes()
    {
        return tagTypes;
    }
}