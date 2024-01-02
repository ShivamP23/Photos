package controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.Album;
import model.Photo;
import model.TagType;
import model.User;

/**
 * Controller class for View Album.
 *
 * @author Shivam Patel and Neel Patel
 */
public class ViewAlbumController implements Initializable {

    @FXML
    private ListView<String> lvPhotos;
    @FXML
    private AnchorPane managePhotoAP;
    @FXML
    private ImageView photoDisplay;
    @FXML
    private Button btnRemovePhoto;
    @FXML
    private Button btnCaption;
    @FXML
    private Button btnMovePhoto;
    @FXML
    private ListView<String> tagsList;
    @FXML
    private Button btnAddTagType;
    @FXML
    private Button btnRemoveTag;
    @FXML
    private ChoiceBox<String> ddTagTypes;
    @FXML
    private TextField tfTagValue;
    @FXML
    private Button btnAddTag;
    @FXML
    private ChoiceBox<String> ddMoveCopy;
    @FXML
    private Button btnCopyPhoto;
    @FXML
    private Text capT;
    @FXML
    private Text dateTimeT;
    @FXML
    private Button btnAddPhoto;
    @FXML
    private Button btnViewSlide;

    private ObservableList<String> photos;
    private ObservableList<String> tags;
    private ObservableList<String> tagTypes;

    /**
     * Method for Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        tags = FXCollections.observableArrayList();
        tagsList.setItems(tags);

        tagsList.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue)
                -> {
            if (newValue != null) {
                String selectedString = newValue;
                String categoryName = selectedString.substring(0, selectedString.indexOf(":")).trim();
                String tagValueStr = selectedString.substring(selectedString.indexOf(":") + 1).trim();
                ddTagTypes.getSelectionModel().select(categoryName);
                tfTagValue.setText(tagValueStr);
            }
        });

        lvPhotos.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
            @Override
            public ListCell<String> call(ListView<String> listView) {
                return new ListCell<String>() {
                    private ImageView imageView = new ImageView();

                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty || item == null) {
                            setText(null);
                            setGraphic(null);
                        } else {
                            Image image = new Image("file:" + item);
                            Label caption = new Label();
                            Label date = new Label();
                            imageView.setFitWidth(150);
                            imageView.setFitHeight(120);
                            imageView.setImage(image);
                            Album a = User.getCurrentSessionAlbum();
                            ArrayList<Photo> p = a.getPhotoList();
                            for (Photo po : p) {
                                if (item.equals(po.getPath())) {
                                    caption.setText(po.getPhotoCaption());
                                    caption.setFont(new Font(20));
                                    capT.setText("Caption: " + po.getPhotoCaption());
                                    dateTimeT.setText("Date/Time: " + po.getLastModDate());
                                }
                            }
                            HBox hb = new HBox();
                            VBox vb = new VBox();
                            vb.getChildren().addAll(caption, date);
                            vb.setPadding(new Insets(40, 0, 0, 40));
                            hb.getChildren().addAll(imageView, vb);
                            setGraphic(hb);

                            this.setOnMouseClicked(event
                                    -> {
                                if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 1) {
                                    for (Photo po : p) {
                                        if (item.equals(po.getPath())) {
                                            capT.setText("Caption: " + po.getPhotoCaption());
                                            dateTimeT.setText("Date/Time: " + po.getLastModDate());
                                        }
                                    }

                                    photoDisplay.setImage(image);
                                    photoDisplay.setPreserveRatio(true);
                                    tags.clear();
                                    int selectedIndex = lvPhotos.getSelectionModel().getSelectedIndex();
                                    Photo currPhoto = p.get(selectedIndex);
                                    tags.addAll(currPhoto.getTags());
                                    if (tags.isEmpty()) {
                                        btnRemoveTag.setDisable(true);
                                    } else {
                                        btnRemoveTag.setDisable(false);
                                    }

                                    tfTagValue.clear();
                                    ddTagTypes.getSelectionModel().select(0);

                                    tagsList.getSelectionModel().selectedItemProperty().addListener(
                                            (observable, oldValue, newValue) -> {
                                                if (newValue != null) {
                                                    String selectedString = newValue.toString();
                                                    String categoryName = selectedString.substring(0, selectedString.indexOf(":")).trim();
                                                    String tagValueStr = selectedString.substring(selectedString.indexOf(":") + 1).trim();
                                                    ddTagTypes.getSelectionModel().select(categoryName);
                                                    tfTagValue.setText(tagValueStr);
                                                }
                                            });
                                }
                            });
                        }
                    }
                };
            }
        });

        photos = FXCollections.observableArrayList();
        lvPhotos.setItems(photos);

        Album currentAlbum = User.getCurrentSessionAlbum();

        ArrayList<Photo> currentPhotos = currentAlbum.getPhotoList();
        for (Photo p : currentPhotos) {
            photos.add(p.getPath());
        }
        if (!photos.isEmpty()) {
            lvPhotos.getSelectionModel().select(0);
            String selectedItem = lvPhotos.getSelectionModel().getSelectedItem();
            Image i = new Image("file:" + selectedItem);
            photoDisplay.setImage(i);
            photoDisplay.setPreserveRatio(true);

            Album a = User.getCurrentSessionAlbum();
            int selectedIndex = lvPhotos.getSelectionModel().getSelectedIndex();
            ArrayList<Photo> p = a.getPhotoList();
            Photo currPhoto = p.get(selectedIndex);

            capT.setText("Caption: " + currPhoto.getPhotoCaption());
            dateTimeT.setText("Date/Time: " + currPhoto.getLastModDate());
            tags.addAll(currPhoto.getTags());
        }

        ddMoveCopy.getItems().add(0, "Please Select");
        AlbumMainPageController.albums.remove(currentAlbum.getAlbumName());
        ddMoveCopy.getItems().addAll(AlbumMainPageController.albums);
        ddMoveCopy.getSelectionModel().selectFirst();

        tagTypes = FXCollections.observableArrayList();
        ddTagTypes.setItems(tagTypes);
        tagTypes.add(0, "--Select--");
        User currentUser = DataController.getCurrentSessionUser();
        tagTypes.addAll(currentUser.getTypeList());

        ddTagTypes.getSelectionModel().selectFirst();

        if (photos.isEmpty()) {
            managePhotoAP.setVisible(false);
            btnViewSlide.setDisable(true);
        }
        if (tags.isEmpty()) {
            btnRemoveTag.setDisable(true);
        } else {
            btnRemoveTag.setDisable(false);
        }
    }

    /**
     * Method for action when user click on remove photo.
     *
     * @param event action event.
     */
    @FXML
    private void actionOnRemovePhoto(ActionEvent event) {
        int selectedID = lvPhotos.getSelectionModel().getSelectedIndex();
        if (selectedID != -1) {
            if (UtilsController.getConfirmation("Remove Photo", "Are you sure you want to delete selected Photo?")) {
                Album currentAlbum = User.getCurrentSessionAlbum();
                ArrayList<Photo> currentPhotos = currentAlbum.getPhotoList();
                currentPhotos.remove(selectedID);

                String selectedPhoto = lvPhotos.getSelectionModel().getSelectedItem();
                photos.remove(selectedPhoto);

                if (selectedID >= photos.size()) {
                    selectedID -= 1;
                }

                if (!photos.isEmpty()) {
                    lvPhotos.getSelectionModel().clearSelection();
                    lvPhotos.getSelectionModel().select(selectedID);
                    photoDisplay.setImage(new Image("file:" + lvPhotos.getSelectionModel().getSelectedItem()));
                    photoDisplay.setPreserveRatio(true);

                    tags.clear();
                    Album a = User.getCurrentSessionAlbum();
                    ArrayList<Photo> p = a.getPhotoList();
                    int selectedIndex = lvPhotos.getSelectionModel().getSelectedIndex();
                    Photo currPhoto = p.get(selectedIndex);
                    capT.setText("Caption: " + currPhoto.getPhotoCaption());
                    dateTimeT.setText("Date/Time: " + currPhoto.getLastModDate());
                    tags.addAll(currPhoto.getTags());
                } else {
                    tags.clear();
                    photoDisplay.setImage(null);
                    managePhotoAP.setVisible(false);
                    btnViewSlide.setDisable(true);
                }
                try {
                    DataController userData = DataController.getInstance();
                    userData.writeToAFile();
                } catch (Exception ex) {

                }
            }
        }
    }

    /**
     * Method for action when user click on recaption button.
     *
     * @param event action event.
     */
    @FXML
    private void actionOnReCaption(ActionEvent event) {
        String captionNm = null;
        int selectedID = lvPhotos.getSelectionModel().getSelectedIndex();
        TextInputDialog captionDialog = new TextInputDialog();
        captionDialog.setTitle("Caption/Re-Caption: ");
        captionDialog.setHeaderText(null);
        captionDialog.setContentText("Please enter caption name: ");
        captionDialog.getDialogPane().getButtonTypes().setAll(ButtonType.OK, ButtonType.CANCEL);
        Optional<String> captionResult = captionDialog.showAndWait();

        if (captionResult.isPresent()) {
            captionNm = captionResult.get();
        }

        Album a = User.getCurrentSessionAlbum();
        ArrayList<Photo> p = a.getPhotoList();
        Photo currentPhoto = p.get(selectedID);
        currentPhoto.setPhotoCaption(captionNm);
        lvPhotos.getSelectionModel().clearSelection();
        lvPhotos.getSelectionModel().select(selectedID);
        try {
                DataController userData = DataController.getInstance();
                userData.writeToAFile();
            } catch (Exception ex) {

            }
    }

    /**
     * Method for action when user click on Move Photo.
     *
     * @param event
     */
    @FXML
    private void actionOnMovePhoto(ActionEvent event) {
        if (photos.isEmpty()) {
            UtilsController.showError("Move Photo", "No photos in current album to move!");
        } else if (ddMoveCopy.getSelectionModel().getSelectedIndex() == 0) {
            UtilsController.showError("Move Photo", "No album selected!");
        } else {
            Album a = User.getCurrentSessionAlbum();
            int selectedID = lvPhotos.getSelectionModel().getSelectedIndex();
            ArrayList<Photo> p = a.getPhotoList();
            Photo toMovePhoto = p.get(selectedID);

            String selectedItem = ddMoveCopy.getSelectionModel().getSelectedItem();
            Album toMoveAlbum = a.getAlbum(selectedItem);
            ArrayList<Photo> ddmcP = toMoveAlbum.getPhotoList();

            boolean duplicate = false;
            for (Photo dP : ddmcP) {
                if (dP.getPath().equals(toMovePhoto.getPath())) {
                    duplicate = true;
                    break;
                }
            }

            if (!duplicate) {
                if (UtilsController.getConfirmation("Move Photo", "Are you sure you want to move selected photo to selected album?")) {
                    toMoveAlbum.addPhotos(toMovePhoto);
                    p.remove(selectedID);
                    photos.remove(selectedID);

                    if (selectedID >= photos.size()) {
                        selectedID -= 1;
                    }

                    if (!photos.isEmpty()) {
                        lvPhotos.getSelectionModel().clearSelection();
                        lvPhotos.getSelectionModel().select(selectedID);
                        String cdt = lvPhotos.getSelectionModel().getSelectedItem();
                        Photo curr = p.get(selectedID);
                        capT.setText("Caption: " + curr.getPhotoCaption());
                        dateTimeT.setText("Date/Time: " + curr.getLastModDate());

                        photoDisplay.setImage(new Image("file:" + cdt));
                        photoDisplay.setPreserveRatio(true);
                    } else {
                        tags.clear();
                        photoDisplay.setImage(null);
                        managePhotoAP.setVisible(false);
                        btnViewSlide.setDisable(true);
                    }
                }
            } else {
                UtilsController.showError("Move Photo", "The photo you are trying to move already exists and can't be duplicated!");
            }
            try {
                DataController userData = DataController.getInstance();
                userData.writeToAFile();
            } catch (Exception ex) {

            }
        }
    }

    /**
     * Method for action when user click on add tag type button.
     *
     * @param event action event.
     */
    @FXML
    private void actionOnAddTagType(ActionEvent event) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Create New Tag Category");
        dialog.setHeaderText(null);
        Label titleLabel = new Label("Enter the tag category title: ");
        TextField titleTextField = new TextField();
        HBox titleBox = new HBox(titleLabel, titleTextField);
        titleBox.setSpacing(10);
        Label label = new Label("Number of Values Allowed: ");
        ToggleGroup toggleGroup = new ToggleGroup();
        RadioButton multipleTagsRadioButton = new RadioButton("Multiple Values");
        multipleTagsRadioButton.setSelected(true);
        RadioButton singleTagRadioButton = new RadioButton("Single Values");
        singleTagRadioButton.setSelected(false);
        multipleTagsRadioButton.setToggleGroup(toggleGroup);
        singleTagRadioButton.setToggleGroup(toggleGroup);
        HBox hbox = new HBox(label, multipleTagsRadioButton, singleTagRadioButton);
        hbox.setSpacing(10);
        VBox vbox = new VBox(titleBox, hbox);
        vbox.setSpacing(20);
        dialog.getDialogPane().setContent(vbox);

        dialog.showAndWait().ifPresent(str
                -> {
            String newCategoryName = titleTextField.getText().trim();

            if (!tagTypes.contains(newCategoryName)) {
                boolean isSingular = singleTagRadioButton.isSelected();

                TagType newCategory = new TagType(newCategoryName);
                newCategory.setSingle(isSingular);
                User currentUser = DataController.getCurrentSessionUser();
                currentUser.addTagType(newCategory);
                tagTypes.add(newCategoryName);
            } else if (newCategoryName.isEmpty()) {
                UtilsController.showError("Add Tag Type", "Please enter a valid Tag Category Name");
                actionOnAddTagType(event);
            } else {
                UtilsController.showError("Add Tag Type", "A tag category with the name \"" + newCategoryName + "\" already exists.");
                actionOnAddTagType(event);
            }
        });
        try {
                DataController userData = DataController.getInstance();
                userData.writeToAFile();
            } catch (Exception ex) {

            }
    }

    /**
     * Method for action when user click on remove tag button.
     *
     * @param event action event.
     */
    @FXML
    private void removeTag(ActionEvent event) {
        String tag = tagsList.getSelectionModel().getSelectedItem();
        if (tags.contains(tag)) {
            if (UtilsController.getConfirmation("Remove Tag", "Are you sure you want to delete selected Tag?")) {
                Album a = User.getCurrentSessionAlbum();
                int selectedID = lvPhotos.getSelectionModel().getSelectedIndex();
                ArrayList<Photo> p = a.getPhotoList();
                Photo currPhoto = p.get(selectedID);
                currPhoto.removeTag(tag);
                tags.remove(tag);
                currPhoto.setLastModDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm a")));
                lvPhotos.getSelectionModel().clearSelection();
                lvPhotos.getSelectionModel().select(selectedID);
                if (tags.isEmpty()) {
                    ddTagTypes.getSelectionModel().select(0);
                    tfTagValue.clear();
                    btnRemoveTag.setDisable(true);
                }
            }
        } else {
            UtilsController.showError("Remove Tag", "No Tag with provided tag-category and tag-value exists");
        }
        try {
                DataController userData = DataController.getInstance();
                userData.writeToAFile();
            } catch (Exception ex) {

            }
    }

    /**
     * Method for action when user click on add tag button.
     *
     * @param event action event.
     */
    @FXML
    private void actionOnAddTag(ActionEvent event) {
        if (ddTagTypes.getSelectionModel().getSelectedIndex() == 0 || tfTagValue.getText().trim() == "") {
            UtilsController.showError("Add Tag", "Tag Category or Value not entered!");
        } else {

            String selectedCategoryName = ddTagTypes.getSelectionModel().getSelectedItem();
            String value = tfTagValue.getText().trim();

            if (!tags.contains(selectedCategoryName + " : " + value)) {
                User currentUser = DataController.getCurrentSessionUser();
                ArrayList<TagType> t = currentUser.getTypes();
                TagType selectedCategory = null;

                for (TagType c : t) {
                    if (c.getName().equals(selectedCategoryName)) {
                        selectedCategory = c;
                        break;
                    }
                }

                Boolean isSingularCategory = selectedCategory.isSingle();

                if (isSingularCategory) {
                    String check = selectedCategoryName;
                    Predicate<String> duplicate = (String s) -> (s.substring(0, s.indexOf(":")).trim().equals(check.trim()));
                    if (tags.stream().anyMatch(duplicate)) {
                        UtilsController.showError("Add Tag", "Multiples Tags for Tag Category \"" + selectedCategoryName + "\" is not allowed");
                        return;
                    }

                }

                TagType.Tag newTag = selectedCategory.new Tag(value);

                Album a = User.getCurrentSessionAlbum();
                int selectedID = lvPhotos.getSelectionModel().getSelectedIndex();
                ArrayList<Photo> p = a.getPhotoList();
                Photo currPhoto = p.get(selectedID);

                currPhoto.addTag(newTag);
                tags.add(newTag.toString());
                currPhoto.setLastModDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm a")));
                tagsList.getSelectionModel().select(newTag.toString());
                btnRemoveTag.setDisable(false);
                lvPhotos.getSelectionModel().clearSelection();
                lvPhotos.getSelectionModel().select(selectedID);
            } else {
                UtilsController.showError("Add Tag", "The Tag \"" + selectedCategoryName + " : " + value + "\" already exists for the selected photo.");
            }
        }
        try {
                DataController userData = DataController.getInstance();
                userData.writeToAFile();
            } catch (Exception ex) {

            }
    }

    /**
     * Method for action when user click on copy photo.
     *
     * @param event action event.
     */
    @FXML
    private void actionOnCopyPhoto(ActionEvent event) {
        if (photos.isEmpty()) {
            UtilsController.showError("Copy Photo", "No photos in current album to copy!");
        } else if (ddMoveCopy.getSelectionModel().getSelectedIndex() == 0) {
            UtilsController.showError("Copy Photo", "No album selected!");
        } else {
            Album a = User.getCurrentSessionAlbum();
            int selectedID = lvPhotos.getSelectionModel().getSelectedIndex();
            ArrayList<Photo> p = a.getPhotoList();
            Photo toCopyPhoto = p.get(selectedID);
            Photo ref = toCopyPhoto;

            String selectedItem = ddMoveCopy.getSelectionModel().getSelectedItem();
            Album toCopyAlbum = a.getAlbum(selectedItem);
            ArrayList<Photo> ddmcP = toCopyAlbum.getPhotoList();

            boolean duplicate = false;
            for (Photo dP : ddmcP) {
                if (dP.getPath().equals(toCopyPhoto.getPath())) {
                    duplicate = true;
                    break;
                }
            }

            if (!duplicate) {
                if (UtilsController.getConfirmation("Copy Photo", "Are you sure you want to copy selected photo to selected album?")) {
                    toCopyAlbum.addPhotos(ref);
                }
            } else {
                UtilsController.showError("Copy Photo", "The photo you are trying to copy already exists and can't be duplicated!");
            }
        }
        try {
                DataController userData = DataController.getInstance();
                userData.writeToAFile();
            } catch (Exception ex) {

            }
    }

    /**
     * Method for action when user click on back button.
     *
     * @param event action event.
     * @throws IOException handle exceptions
     */
    @FXML
    private void back(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/AlbumMainPage.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Method for action when user click on add photo button.
     *
     * @param event action event.
     */
    @FXML
    private void actionOnAddPhoto(ActionEvent event) {
        Album a = User.getCurrentSessionAlbum();
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.bmp", "*.jpeg", "*.png", "*.gif", "*.BMP", "*.JPEG", "*.PNG", "*.GIF"));
        File file = fileChooser.showOpenDialog(null);

        if (file != null) {
            String imagePath = file.getAbsolutePath();
            if (photos.contains(imagePath)) {
                UtilsController.showError("Add Photo", "Duplicate photo not allowed");
                actionOnAddPhoto(event);
            } else {
                Photo addP = null;
                User currUser = DataController.getCurrentSessionUser();
                ArrayList<Album> a1 = currUser.getAlbumList();
                boolean dup = false;
                for (Album tempA : a1) {
                    ArrayList<Photo> p1 = tempA.getPhotoList();
                    for (Photo tempP : p1) {
                        if (tempP.getPath().equals(imagePath)) {
                            addP = tempP;
                            dup = true;
                        }
                    }
                }

                if (!dup) {
                    String captionNm = null;
                    TextInputDialog captionDialog = new TextInputDialog();
                    captionDialog.setTitle("Add Photo Details: ");
                    captionDialog.setHeaderText(null);
                    captionDialog.setContentText("Please enter caption name. If you are not sure you can add a caption later.");
                    captionDialog.getDialogPane().getButtonTypes().setAll(ButtonType.OK);
                    Optional<String> captionResult = captionDialog.showAndWait();

                    if (captionResult.isPresent()) {
                        captionNm = captionResult.get();
                        capT.setText("Caption: " + captionNm);
                    } else {
                        capT.setText("Caption: ");
                    }
                    String dt = LocalDateTime.now().format(DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm a"));
                    dateTimeT.setText("Date/Time: " + dt);

                    photos.add(imagePath);
                    a.addPhotos(new Photo(captionNm, imagePath, dt));
                } else {
                    photos.add(addP.getPath());
                    a.addPhotos(addP);
                }

                int index = photos.indexOf(imagePath);
                lvPhotos.getSelectionModel().select(index);
                photoDisplay.setImage(new Image("file:" + imagePath));
                photoDisplay.setPreserveRatio(true);
                managePhotoAP.setVisible(true);
                btnViewSlide.setDisable(false);
            }
        }
        try {
                DataController userData = DataController.getInstance();
                userData.writeToAFile();
            } catch (Exception ex) {

            }
    }

    /**
     * Method for action when user click on slide show button.
     *
     * @param event action event.
     */
    @FXML
    private void actionOnSlideShow(ActionEvent event) {
        try {
            Album a = User.getCurrentSessionAlbum();
            ArrayList<Photo> photos = a.getPhotoList();
            if (photos.isEmpty()) {
                UtilsController.showError("Slide Show", "There are no photos in this album to start slideshow!");
            } else {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ViewSlideShow.fxml"));
                Parent sS = loader.load();
                ViewSlideShowController controller = loader.getController();
                Scene ssScene = new Scene(sS);
                int ssPointer = lvPhotos.getSelectionModel().getSelectedIndex();
                controller.start(ssPointer);
                Stage mainStage = (Stage) lvPhotos.getScene().getWindow();
                mainStage.setScene(ssScene);
            }
        } catch (Exception e) {  
            e.printStackTrace();
        }
    }

}

//fixed the S
