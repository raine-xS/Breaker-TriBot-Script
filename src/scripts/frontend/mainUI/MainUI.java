package scripts.frontend.mainUI;

import javafx.scene.Parent;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.tribot.script.sdk.Log;
import scripts.frontend.GUI;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class MainUI extends GUI {

    // Default parameterless constructor.
    public MainUI() {
        super();
    }

    // Constructor that allows setting a title, stylesheet path, and icon path for the GUI.
    public MainUI(String title, String stylesheetResourcePath, String iconResourcePath) {
        super(title, stylesheetResourcePath, iconResourcePath);
    }

    // Static factory method for creating a new MainUI instance with a specific title.
    public static MainUI newMainUIWithTitle(String title) {
        return new MainUI(title, null, null);
    }

    // Static factory method for creating a new MainUI instance with a stylesheet.
    public static MainUI newMainUIWithStylesheet(String stylesheetResourcePath) {
        return new MainUI(null, stylesheetResourcePath, null);
    }

    // Static factory method for creating a new MainUI instance with an icon.
    public static MainUI newMainUIWithIcon(String iconResourcePath) {
        return new MainUI(null, null, iconResourcePath);
    }

    // Static factory method for creating a new MainUI instance with a title and stylesheet.
    public static MainUI newMainUIWithTitleStylesheet(String title, String stylesheetResourcePath) {
        return new MainUI(title, stylesheetResourcePath, null);
    }

    // Static factory method for creating a new MainUI instance with a title and icon.
    public static MainUI newMainUIWithTitleIcon(String title, String iconResourcePath) {
        return new MainUI(title, iconResourcePath, null);
    }

    // Static factory method for creating a new MainUI instance with a stylesheet and icon.
    public static MainUI newMainUIWithStylesheetIcon(String stylesheetResourcePath, String iconResourcePath) {
        return new MainUI(null, stylesheetResourcePath, iconResourcePath);
    }

    // Static factory method for creating a new MainUI instance with a title, stylesheet, and icon.
    public static MainUI newMainUIWithTitleStylesheetIcon(String title, String stylesheetResourcePath, String iconResourcePath) {
        return new MainUI(title, stylesheetResourcePath, iconResourcePath);
    }

    @Override
    protected Parent createContent() {
        // Create the "Main" tab and make it non-closable
        Tab main = new Tab("Main");
        main.setClosable(false);

        // Create the "Profile" tab and make it non-closable
        Tab profile = new Tab("Profile");
        profile.setClosable(false);

        // Create a TabPane to hold the tabs and add the "Main" and "Profile" tabs to it
        TabPane tabPane = new TabPane();
        tabPane.getTabs().addAll(main, profile);

        // Return the TabPane as the root node for the UI
        return tabPane;
    }
}
