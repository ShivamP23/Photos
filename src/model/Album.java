package model;

import java.io.Serializable;
import java.util.ArrayList;
import controller.DataController;

/**
 * Class for representing an Album containing a collection of Photos.
 * 
 * @author Shivam Patel and Neel Patel
 */
public class Album implements Serializable {

    private static final long serialVersionUID = 4L;

    /**
     * An ArrayList containing the photos in this album.
     */
    private ArrayList<Photo> photos = new ArrayList<>();

    /**
     * The name of the album.
     */
    private String albumName;

    /**
     * Constructor for creating a new album with null value.
     */
    public Album() {
        albumName = null;
    }

    /**
     * Constructor for creating a new album with name
     *
     * @param name  name of the album
     */
    public Album(String name) {
        this.albumName = name;
    }

    /**
     * Constructor for creating an album with name and set of photos.
     *
     * @param name	The name of the album
     * @param photos	The name of the collection that contains Photos of nm album
     */
    public Album(String name, ArrayList<Photo> photos) {
        this.albumName = name;
        this.photos = photos;
    }

    /**
     * Method for setting new name of the album.
     *
     * @param newName	new name of album
     */
    public void setAlbumName(String newName) {
        this.albumName = newName;
    }

    /**
     * Method for getting the album name
     *
     * @return	 name of the album
     */
    public String getAlbumName() {
        return albumName;
    }

    /**
     * Method for adding a new photo in the album
     *
     * @param newPhoto	A photo that needs to be added in the album
     */
    public void addPhotos(Photo newPhoto) {
        photos.add(newPhoto);
    }

    /**
     * Method for getting the number of photos in album.
     *
     * @return	Number of photos
     */
    public int PhotosNum() {
        return this.getPhotoList().size();
    }

    /**
     * Method for getting all photos from the album.
     *
     * @return  list of photos in this album
     */
    public ArrayList<Photo> getPhotoList() {
        return photos;
    }

    /**
     * Method for Returning a string representation of the date range of photos in this
     * album.
     *
     * @return a string representation of the date range of photos in this album
     */
    public String dateRange() {
        String minDR = "99/99/9999";
        String maxDR = "00/00/0000";
        ArrayList<Photo> p = this.getPhotoList();
        if (p.size() == 0) {
            return "N/A";
        }
        for (Photo dP : p) {
            if (minDR.substring(6).compareTo(dP.getLastModDate().substring(6)) >= 0) {
                minDR = dP.getLastModDate();
                if (minDR.substring(3, 5).compareTo(dP.getLastModDate().substring(3, 5)) >= 0) {
                    minDR = dP.getLastModDate();
                    if (minDR.substring(0, 2).compareTo(dP.getLastModDate().substring(0, 2)) >= 0) {
                        minDR = dP.getLastModDate();
                    }
                }
            }
            if (maxDR.substring(6).compareTo(dP.getLastModDate().substring(6)) <= 0) {
                maxDR = dP.getLastModDate();
                if (maxDR.substring(3, 5).compareTo(dP.getLastModDate().substring(3, 5)) <= 0) {
                    maxDR = dP.getLastModDate();
                    if (maxDR.substring(0, 2).compareTo(dP.getLastModDate().substring(0, 2)) <= 0) {
                        maxDR = dP.getLastModDate();
                    }
                }
            }
        }
        return minDR.substring(0, 10) + " - " + maxDR.substring(0, 10);
    }

    /**
     * Method for getting the Album object when the name of the album is passed
     *
     * @param selectedItem	The name of the album
     * @return The Album object
     */
    public Album getAlbum(String selectedItem) {
        User u = DataController.getCurrentSessionUser();
        ArrayList<Album> a = u.getAlbumList();
        for (Album aR : a) {
            if (aR.getAlbumName().equals(selectedItem)) {
                return aR;
            }
        }
        return null;
    }
}
