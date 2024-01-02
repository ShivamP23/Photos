package model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import model.TagType.Tag;

/**
 * The Photo class represents a photo object, with values of caption, image
 * path, last modified date, and a list of tags. This class provides methods to
 * get and modify these value, as well as methods to add and remove tags. The
 * class also implements the Serializable interface to allow for saving and
 * loading Photo objects.
 *
 * @author Shivam Patel and Neel Patel
 */
public class Photo implements Serializable {

    private static final long serialVersionUID = 3L;
    private String photoCaption;
    private String path;
    private String lastModifyDate;
    private ArrayList<Tag> listTags = new ArrayList<Tag>();

    /**
     * Constructor for creating new photo object with null values.
     */
    public Photo() {
        photoCaption = null;
        path = null;
        setLastModDate(null);
    }

    /**
     * Constructor for creating a new Photo object with the given caption, image
     * path, and last modified date.
     *
     * @param caption caption of the photo
     * @param photoPath file path to the image file of the photo
     * @param date the last modified date of the photo
     */
    public Photo(String caption, String photoPath, String date) {
        this.photoCaption = caption;
        this.path = photoPath;
        this.lastModifyDate = date;
    }

    /**
     * Method for getting the list of tags associated with the photo.
     *
     * @return ArrayList of strings representing each tag associated with the
     * photo
     */
    public ArrayList<String> getTags() {
        ArrayList<String> list = new ArrayList<String>();
        for (Tag t : listTags) {
            list.add(t.toString());
        }
        return list;
    }

    /**
     * Method for checking either the photo contain the specified tag or not.
     *
     * @param tagVal the string value of the tag to search for
     * @return true if the photo contains the tag, false if not
     */
    public boolean containsTag(String tagVal) {
        for (Tag t : listTags) {
            if (t.toString().equals(tagVal)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Method for adding a new tag in the photo.
     *
     * @param tag tag to add to the photo
     */
    public void addTag(Tag tag) {
        listTags.add(tag);
    }

    /**
     * Method for setting new caption to the photo.
     *
     * @param caption new caption for the photo
     */
    public void setPhotoCaption(String caption) {
        photoCaption = caption;
        setLastModDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm a")));
    }

    /**
     * Method for getting the caption of the photo.
     *
     * @return the caption of the photo
     */
    public String getPhotoCaption() {
        return photoCaption;
    }

    /**
     * Method for setting new path of the photo.
     *
     * @param path the new file path for the photo
     */
    public void setPath(String path) {
        this.path = path;
        setLastModDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm a")));
    }

    /**
     * Method for getting the path of the photo.
     *
     * @return the file path to the image file of the photo
     */
    public String getPath() {
        return path;
    }

    /**
     * Method for getting the last modified date of the photo.
     *
     * @return last modified date of the photo
     */
    public String getLastModDate() {
        return lastModifyDate;
    }

    /**
     * Method for updating the last modify date of the photo.
     *
     * @param lastModDate	String form of date
     */
    public void setLastModDate(String lastModDate) {
        this.lastModifyDate = lastModDate;
    }

    /**
     * Method for removing a tag from the photo
     *
     * @param tagVal	Tag in String format
     */
    public void removeTag(String tagVal) {
        Iterator<Tag> iterator = listTags.iterator();
        while (iterator.hasNext()) {
            Tag t = iterator.next();
            if (t.toString().equals(tagVal)) {
                iterator.remove();
            }
        }
    }
}
