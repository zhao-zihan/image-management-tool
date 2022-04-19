package tool;


import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Use of interfaces
 * Here I let the Controller class implement the Initializable interface,
 * so I could add choices to the choice box and disable some buttons
 */
public class Controller implements Initializable {

    /**
     * Use of encapsulation
     * set all variables and methods to private to avoid manipulations from outside the class
     */

    @FXML
    private Button uploadButton;

    @FXML
    private Button convertButton;

    @FXML
    private Button downloadButton;

    @FXML
    private Button restartButton;

    @FXML
    private Button selectDirectoryButton;

    @FXML
    private Label imageInfoLabel;


    @FXML
    private Label heightLabel;

    @FXML
    private Label widthLabel;

    @FXML
    private Label cameraLabel;

    @FXML
    private Label dateLabel;

    @FXML
    private Label selectOutputLabel;

    @FXML
    private Label infoReminderLabel;

    @FXML
    private ChoiceBox<String> selectFormatBox;

    //all the acceptable formats
    private String[] formats = {".jpg", ".png", ".gif", ".bmp", ".jff", ".jpe", ".jpeg", ".wbmp"};

    @FXML
    private ImageView photoImageView;

    private tool.Image uploadedImage;
    private FileChooser fileChooser;
    private File filePath;

    private String[] desiredType = new String[1];
    private javaxt.io.Image convertedImage;
    private DirectoryChooser directoryChooser;
    private File storeLocation;


    /**
     * This method will allow users to upload images from their computer, and a thumbnail of the image and some of its basic info will be displayed;
     */
    public void uploadButtonPushed(ActionEvent event) {
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();

        //allow users to upload files from their computers
        fileChooser = new FileChooser();
        fileChooser.setTitle("Upload image");

//        this.filePath = fileChooser.showOpenDialog(stage);
        uploadedImage = tool.Image.getInstance();
        uploadedImage.setFilePath(fileChooser.showOpenDialog(stage));
        this.filePath = uploadedImage.getFilePath();

        //update the image with exception handling
        try {
            BufferedImage bufferedImage = (BufferedImage)ImageIO.read(filePath);
            javafx.scene.image.Image image = SwingFXUtils.toFXImage(bufferedImage, null);
            photoImageView.setImage(image);

            convertedImage = new javaxt.io.Image(filePath);
            java.util.HashMap<Integer, Object> exif = convertedImage.getExifTags();

            //Display image info: height, width, camera, date
            heightLabel.setText(Double.toString(image.getHeight()));
            widthLabel.setText(Double.toString(image.getWidth()));
            if (exif.get(0x0110) != null) {
                cameraLabel.setText(exif.get(0x0110).toString());
            } else {
                cameraLabel.setText("N/A");
            }
            if (exif.get(0x0132) != null) {
                dateLabel.setText(exif.get(0x0132).toString().substring(0, 10));
            } else {
                dateLabel.setText("N/A");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Since many images hide their metadata, the following codes print out all the metadata of the image to the console
        try {
            Metadata metadata = null;
            try {
                metadata = ImageMetadataReader.readMetadata(filePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
            for (Directory directory : metadata.getDirectories()) {
                for (Tag tag : directory.getTags()) {
                    System.out.println(tag);
                }
            }
            infoReminderLabel.setText("More image info on console");
        } catch (ImageProcessingException e) {
            e.printStackTrace();
        }

    }

    public void selectFormatBoxSelected() {

        //Enable convert button after the image is uploaded
        if (filePath != null) {
            this.convertButton.setDisable(false);
        }
    }

    public void convertButtonPushed() {
        desiredType[0] = selectFormatBox.getValue().toString();
        convertButton.setText("converted!");

        System.out.println(desiredType[0]);

        System.out.println(filePath.getName());
        System.out.println(filePath.getName().substring(0, filePath.getName().lastIndexOf('.')) + desiredType[0]);

        //print out all the acceptable input and output formats
        String[] inputFormats = javaxt.io.Image.InputFormats;
        String[] outputFormats = javaxt.io.Image.OutputFormats;
        System.out.println("Accepted input formats:");
        for (String s : inputFormats) {
            System.out.println(s + " ");
        }
        System.out.println("Accepted output formats:");
        for (String s : outputFormats) {
            System.out.println(s + " ");
        }

    }

    /**
     * This method will allow users to select the directory they want to save the image to,
     * that directory will also be memorized as directoryChooser
     */
    public void selectDirectoryButtonPushed(ActionEvent event) {
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Select a directory");
        storeLocation = directoryChooser.showDialog(stage);
        downloadButton.setDisable(false);
        selectDirectoryButton.setText(storeLocation.getPath().toString());
    }

    /**
     * This method will allow users to download the converted image to the directory they selected
     * using the above method
     */
    public void downloadButtonPushed() {
        if (desiredType[0] != null) {
            String path = storeLocation.getPath().toString();
            path = path +"\\" + filePath.getName().substring(0, filePath.getName().lastIndexOf(".")) + desiredType[0];
            System.out.println(path);
            convertedImage.saveAs(path);
            downloadButton.setText("downloaded!");
            System.out.println("successfully download!");
        }
    }

    /**
     * This method will allow users to continue to upload images by resetting all the variables and contexts to defaults
     */
    public void restartButtonPushed() {
            selectOutputLabel.setText("2. Select output format");
            convertButton.setText("convert");
            selectDirectoryButton.setText("Select a directory to save");
            downloadButton.setText("download");
            fileChooser = null;
            filePath = null;
            desiredType[0] = null;
            convertedImage = null;
            directoryChooser = null;
            storeLocation = null;
            uploadedImage = null;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Add format choices to the choice box
        selectFormatBox.getItems().addAll(formats);
        selectFormatBox.setOnAction(this::getDesiredType);


        //Disable convert button until an image is uploaded
        convertButton.setDisable(true);

        //Disable download button until users have selected the saving directory
        downloadButton.setDisable(true);

    }


    public void getDesiredType(ActionEvent event) {
        String output = selectFormatBox.getValue();
        selectOutputLabel.setText(output);
    }
}

