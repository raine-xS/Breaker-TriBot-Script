package scripts.frontend;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.ScriptListening;
import org.tribot.script.sdk.Waiting;


import javax.swing.SwingUtilities;
import java.io.*;

public abstract class ScriptUI extends Application {
    private boolean isOpen = false;
    private Scene scene;
    private Stage stage;

    /************************************************************************************************************************
    * A UI constructor that initializes the JavaFX runtime on the Event Dispatch Thread (EDT) using JFXPanel.               *
    *                                                                                                                       *
    * JFXPanel is a Swing component that allows embedding JavaFX content into a Swing application. Normally,                *
    * JavaFX applications are initialized via Application.launch(). However, since TriBot scripts are executed              *
    * multiple times/restarted frequently in a single JVM instance and Application.launch() can only be called once,        *
    * using JFXPanel() ensures that the JavaFX runtime is initialized without the limitations of Application.launch().      *
    *                                                                                                                       *
    ************************************************************************************************************************/
    public ScriptUI() {
        SwingUtilities.invokeLater(() -> {
            // Initializes the JavaFX runtime on EDT with JFXPanel() for restartability.
            new JFXPanel();
            // Schedules the `start` method to be executed on the JavaFX Application Thread since
            // JavaFX-related operations must be manipulated only from the JavaFX Application Thread.
            Platform.runLater(() -> {
                start(new Stage());
            });
        });
        // Wait for stage to initialize on the other thread before continuing.
        waitForStageInit(5000);
    }

    /************************************************************************************************************************
    * Constructs a UI with a specified title, stylesheet, and icon. See GUI() for details.                                  *
    * Null values skips over the option.                                                                                    *
    *                                                                                                                       *
    * @param title                  the title of the JavaFX stage.                                                          *
    * @param stylesheetResourcePath the path to the stylesheet resource for styling the JavaFX scene.                       *
    *************************************************************************************************************************/
    public ScriptUI(String title, String stylesheetResourcePath, String iconResourcePath) {
        SwingUtilities.invokeLater(() -> {
            // Initializes the JavaFX runtime on EDT with JFXPanel() for restartability.
            new JFXPanel();
            Platform.runLater(() -> {
                // Schedules the `start` method to be executed on the JavaFX Application Thread since
                // JavaFX-related operations must be manipulated only from the JavaFX Application Thread.
                // Passes 'title' and 'stylesheetResourcePath' as arguments into the 'start' method.
                start(new Stage(), title, stylesheetResourcePath, iconResourcePath);
            });
        });
        // Wait for stage to initialize on the other thread before continuing.
        waitForStageInit(5000);
    }

    protected abstract Parent createFXContent(); // Abstract method that must be implemented by subclasses to provide the content for the application scene. Runs on the JavaFX Application Thread.

    /************************************************************************************************************************
    * Initializes and sets up the JavaFX stage with a default scene.                                                        *
    *                                                                                                                       *
    * @param stage the primary stage for this application.                                                                  *
    ************************************************************************************************************************/
    @Override
    public void start(Stage stage) {
        Platform.setImplicitExit(false); // Prevents JavaFX from exiting when the last window is closed

        this.stage = stage;
        this.scene = new Scene(createFXContent(), 500, 300);

        this.stage.setScene(this.scene);

        this.setOnCloseListeners();
    }

    /************************************************************************************************************************
    * Initializes and sets up the JavaFX stage with an option for title, stylesheet, and icon.                              *
    *                                                                                                                       *
    * @param stage                  the primary stage for this application.                                                 *
    * @param title                  the title of the JavaFX stage.                                                          *
    * @param stylesheetResourcePath the path to the stylesheet resource for styling the JavaFX scene.                       *
    * @param iconResourcePath       the path to the icon resource used for the icon of the application.                     *
    *************************************************************************************************************************/
    public void start(Stage stage, String title, String stylesheetResourcePath, String iconResourcePath) {
        this.start(stage);
        
        if (title != null) {
            this.setTitle(title);
        }
        if (stylesheetResourcePath != null) {
            this.setSceneStyles(stylesheetResourcePath);
        }
        if (iconResourcePath != null) {
            this.setIcon(iconResourcePath);
        }
    }

    //  Closes the GUI window.
    public void close() {
        if (this.stage == null){
            throw new IllegalStateException("Stage is not initialized.");
        }

        isOpen = false;
        Platform.runLater(() -> stage.close()); // Close the GUI window. The method is executed on the JavaFX Application Thread to ensure thread safety.
    }

    // Checks if the GUI window is open.
    public boolean isOpen() {
        return isOpen;
    }

    public Scene getScene() {
        return this.scene;
    }

    public Stage getStage() {
        return stage;
    }

    // Sets whether the window is always on top.
    public void setAlwaysOnTop(Boolean bool) {
        this.stage.setAlwaysOnTop(bool);
    }

    // Sets the icon of the application window from the provided resource path.
    public void setIcon(String iconResourcePath) {
        // Do nothing if resource path is null
        if (iconResourcePath == null) {
            return;
        }
        try {
            Log.log("[setIcon] Setting icon.");
            // Get the icon as an input stream
            InputStream iconInputStream = getClass().getClassLoader().getResourceAsStream(iconResourcePath);

            // Create a temporary file in the default temporary-file directory of the operating system
            File tempIconFile = File.createTempFile("temp_icon", ".png");
            tempIconFile.deleteOnExit(); // Ensure the file is deleted when the JVM exits

            // Write the icon data to the temporary file
            try (FileOutputStream tempFileOutputStream = new FileOutputStream(tempIconFile)) { // Create a file output stream to write to the temp file
                iconInputStream.transferTo(tempFileOutputStream); // Writes the input stream to the output stream file
            }

            // Load the icon from the temporary file
            Image icon = new Image(tempIconFile.toURI().toString());
            Platform.runLater(() -> this.stage.getIcons().add(icon));

        } catch (Exception e) {
            Log.debug(e.toString());
        }
    }

    // Sets the stylesheets for the scene from the provided resource path.
    public void setSceneStyles(String stylesheetResourcePath) {
        // Do nothing if resource path is null
        if (stylesheetResourcePath == null) {
            return;
        }
        try {
            // Get the stylesheet and store it as a String variable
            Log.log("[setSceneStyles] Setting stylesheet.");
            byte[] resourceContents = getClass().getClassLoader().getResourceAsStream(stylesheetResourcePath).readAllBytes();
            String cssResource = new String(resourceContents);

            //Create a temporary file in the default temporary-file directory of the operating system
            File tempCssFile = File.createTempFile("temp", ".css");
            tempCssFile.deleteOnExit(); // Ensure the file is deleted when the JVM exits

            // Write the CSS content String to the file
            try (FileWriter fileWriter = new FileWriter(tempCssFile)) {
                fileWriter.write(cssResource);
            }

            // Add the file's URL to the scene's stylesheets
            this.scene.getStylesheets().add(tempCssFile.toURI().toURL().toExternalForm());
        } catch (Exception e) {
            Log.debug(e.toString());
        }
    }

    // Sets the title of the application window.
    public void setTitle(String title){
        // Do nothing if title is null
        if (title == null) {
            return;
        }
        this.stage.setTitle(title);
    }

    // Displays the GUI window.
    public void show() {
        if (this.stage == null){
            throw new IllegalStateException("Stage is not initialized.");
        }

        isOpen = true;
        Platform.runLater(() -> stage.show()); // Show the GUI window. The method is executed on the JavaFX Application Thread to ensure thread safety.
    }

    // Sets listeners for handling window close requests and script ending events.
    private void setOnCloseListeners() {
        if (this.stage == null){
            throw new IllegalStateException("Stage is not initialized.");
        }

        // Listener for window close request
        this.stage.setOnCloseRequest( (event) -> {
            Log.log("[onCloseRequest] Exiting.");
            Platform.runLater(this::close); // Closes the window upon window close request
        });

        // Listener for script ending event
        ScriptListening.addPreEndingListener(() -> {
            Log.log("[preEndingListener] Script is ending.");
            Platform.runLater(this::close); // Closes the window upon script ending event
        });
    }

    // Waits for the stage to initialize within a specified timeout period.
    private void waitForStageInit(int timeout) {
        long startTime = System.currentTimeMillis(); // current system time in milliseconds
        while (this.stage == null) {
            // Checks if the waiting period has exceeded the timeout
            if ((System.currentTimeMillis() - startTime) > timeout) {
                throw new RuntimeException("Timeout waiting for stage to initialize.");
            }

            // Recheck on 100 millisecond increments
            Waiting.wait(100);
        }
    }

}
