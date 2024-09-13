package scripts.frontend;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.ScriptListening;
import org.tribot.script.sdk.Waiting;

import javax.swing.SwingUtilities;
import java.io.*;

public abstract class GUI extends Application {
    private String title;
    private boolean isOpen = false;
    private Scene scene;
    private Stage stage;

    public GUI() {
        SwingUtilities.invokeLater(() -> {
            // Initializes the JavaFX runtime on EDT with JFXPanel()
            // JFXPanel is a Swing component that allows embedding JavaFX content into a Swing application.
            // TriBot scripts can be executed multiple times or restarted frequently. Since Application.launch() can only be called once,
            // using JFXPanel() instead ensures that the JavaFX runtime is initialized without the limitations of Application.launch().
            new JFXPanel();
            Platform.runLater(() -> {
                start(new Stage());
            });
        });
        // Let's wait for stage to initialize on the other thread before continuing.
        waitForStageInit(5000);
    }

    protected abstract Parent createContent();

    @Override
    public void start(Stage stage) {
        Platform.setImplicitExit(false); // Prevents JavaFX from exiting when the last window is closed

        this.stage = stage;
        this.stage.setTitle(this.title);
        this.scene = new Scene(createContent(), 300, 200);
        try {
            Log.debug("Setting Styles");
            // scripts/frontend/mainUI/MainUIStyles.css
            byte[] resourceContents = getClass().getClassLoader().getResourceAsStream("scripts/frontend/mainUI/MainUIStyles.css").readAllBytes();
            String cssResource = new String(resourceContents);
            Log.debug("Stylesheet:\n " + cssResource);

            //Create a temporary file in the default temporary-file directory of the operating system
            File tempCssFile = File.createTempFile("temp", ".css");

            // Write the CSS content to the file
            try (FileWriter fileWriter = new FileWriter(tempCssFile)) {
                fileWriter.write(cssResource);
            }

            // Add the file's URL to the scene's stylesheets
            this.scene.getStylesheets().add(tempCssFile.toURI().toURL().toExternalForm());
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.stage.setScene(this.scene);

        this.setOnCloseListeners();
    }

    public void close() {
        if (stage == null){
            throw new IllegalStateException("Stage is not initialized.");
        }

        isOpen = false;
        Platform.runLater(() -> stage.close());
    }

    public boolean isOpen() {
        return isOpen;
    }

//    public void setSceneStyles(String resourcePath){
//        try {
//            Log.debug("Setting Styles");
//            // scripts/frontend/mainUI/MainUIStyles.css
//            byte[] resourceContents = getClass().getClassLoader().getResourceAsStream(resourcePath).readAllBytes();
//            String cssResource = new String(resourceContents);
//            Log.debug("Stylesheet:\n " + cssResource);
//
//            //Create a temporary file in the default temporary-file directory of the operating system
//            File tempCssFile = File.createTempFile("temp", ".css");
//
//            // Write the CSS content to the file
//            try (FileWriter fileWriter = new FileWriter(tempCssFile)) {
//                fileWriter.write(cssResource);
//            }
//
//            // Add the file's URL to the scene's stylesheets
//            this.scene.getStylesheets().add(tempCssFile.toURI().toURL().toExternalForm());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    public void setTitle(String title){
        this.title = title;
    }

    public void show() {
        if (stage == null){
            throw new IllegalStateException("Stage is not initialized.");
        }

        isOpen = true;
        Platform.runLater(() -> stage.show());
    }

//    private void refresh() {
//        Platform.runLater(() -> {
//            if (this.stage != null) {
//                stage.setTitle(this.title);
//            }
//            if (this.stage != null) {
//                this.stage.setScene(this.scene);
//            }
//        });
//    }

    private void setOnCloseListeners() {
        if (stage == null){
            throw new IllegalStateException("Stage is not initialized.");
        }

        this.stage.setOnCloseRequest( (event) -> {
            Log.debug("Exiting.");
            Platform.runLater(this::close);
        });

        ScriptListening.addPreEndingListener(() -> {
            Log.debug("Script is ending.");
            Platform.runLater(this::close);
        });
    }

    private void waitForStageInit(int timeout) {
        long startTime = System.currentTimeMillis(); // current system time in milliseconds
        while (this.stage == null) {
            // Let's throw an exception when the time passed exceeds the timeout time.
            if ((System.currentTimeMillis() - startTime) > timeout) {
                throw new RuntimeException("Timeout waiting for stage to initialize.");
            }

            // Recheck on 100ms increments
            Waiting.wait(100);
        }
    }

}
