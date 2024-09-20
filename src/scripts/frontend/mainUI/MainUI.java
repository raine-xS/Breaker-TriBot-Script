package scripts.frontend.mainUI;

import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import scripts.frontend.ScriptUI;

public class MainUI extends ScriptUI {
    private TabPane tabPane;

    private Tab mainTab;

    private VBox mainTabVbox;

    private HBox runtimeHbox;
    private Label scriptRuntimeLabel;
    private TextField scriptRuntimeTextfield;

    private HBox logoutOptionsHbox;
    private Label logoutInPlaceLabel;
    private Label logoutInIdleLocationLabel;
    private ToggleGroup logoutOptionsToggleGroup;
    private RadioButton logoutInPlaceRadioButton;
    private RadioButton LogoutInIdleLocationRadioButton;

    private VBox logoutInIdleLocationAdditionalOptionsVbox;

    private HBox logoutMethodHbox;
    private Label logoutMethodLabel;
    private ChoiceBox<String> logoutMethodChoiceBox;

    private HBox idleLocationHbox;
    private Label idleLocationLabel;
    private ChoiceBox<String> idleLocationChoiceBox;

    private HBox idleTimeBeforeLogoutHbox;
    private Label idleTimeBeforeLogoutLabel;
    private TextField idleTimeBeforeLogoutTextField;

    private HBox startHbox;
    private Button startButton;

    private Tab profileTab;

    private VBox profileTabVbox;
    private HBox profileTabHbox;

    private VBox saveLoadVbox;
    private Button loadProfileButton;
    private Button saveProfileButton;

    // Default parameterless constructor.
    public MainUI() {
        super();

        this.setAlwaysOnTop(true);
    }

    // Constructor that allows setting a title, stylesheet path, and icon path for the GUI.
    public MainUI(String title, String stylesheetResourcePath, String iconResourcePath) {
        super(title, stylesheetResourcePath, iconResourcePath);

        this.setAlwaysOnTop(true);
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
    protected Parent createFXContent() {
        this.scriptRuntimeLabel = new Label("Time to run script for: ");
        this.scriptRuntimeTextfield = new TextField("Minutes");
        this.runtimeHbox = new HBox();
        this.runtimeHbox.getChildren().addAll(scriptRuntimeLabel, scriptRuntimeTextfield);

        this.logoutOptionsToggleGroup = new ToggleGroup();
        this.logoutInPlaceLabel = new Label("Logout in place:");
        this.logoutInPlaceRadioButton = new RadioButton();
        this.logoutInPlaceRadioButton.setToggleGroup(this.logoutOptionsToggleGroup);
        this.logoutInIdleLocationLabel = new Label("Logout in idle location:");
        this.LogoutInIdleLocationRadioButton = new RadioButton();
        this.LogoutInIdleLocationRadioButton.setToggleGroup(this.logoutOptionsToggleGroup);
        this.logoutOptionsHbox = new HBox();
        this.logoutOptionsHbox.getChildren().addAll(logoutInPlaceLabel, logoutInPlaceRadioButton, logoutInIdleLocationLabel, LogoutInIdleLocationRadioButton);

        this.logoutMethodLabel = new Label("Logout Method:");
        this.logoutMethodChoiceBox = new ChoiceBox<>();
        this.logoutMethodHbox = new HBox();
        this.logoutMethodHbox.getChildren().addAll(logoutMethodLabel, logoutMethodChoiceBox);

        this.idleLocationLabel = new Label("Idle Location:");
        this.idleLocationChoiceBox = new ChoiceBox<>();
        this.idleLocationHbox = new HBox();
        this.idleLocationHbox.getChildren().addAll(idleLocationLabel, idleLocationChoiceBox);

        this.idleTimeBeforeLogoutLabel = new Label("Idle X minutes before logout");
        this.idleTimeBeforeLogoutTextField = new TextField("Minutes");
        this.idleTimeBeforeLogoutHbox = new HBox();
        this.idleTimeBeforeLogoutHbox.getChildren().addAll(idleTimeBeforeLogoutLabel, idleTimeBeforeLogoutTextField);

        this.logoutInIdleLocationAdditionalOptionsVbox = new VBox();
        this.logoutInIdleLocationAdditionalOptionsVbox.getChildren().addAll(logoutMethodHbox, idleLocationHbox, idleTimeBeforeLogoutHbox);

        this.startButton = new Button("Start");
        this.startButton.idProperty().set("start-button");
        HBox.setHgrow(startButton, Priority.ALWAYS); // Ensure the button takes all available space
        this.startHbox = new HBox();
        this.startHbox.idProperty().set("start-hbox");
        this.startHbox.getChildren().addAll(startButton);

        this.mainTabVbox = new VBox();
        this.mainTabVbox.getChildren().addAll(runtimeHbox, logoutOptionsHbox, logoutInIdleLocationAdditionalOptionsVbox, startHbox);

        this.mainTab = new Tab("Main");
        this.mainTab.setContent(mainTabVbox);

        this.loadProfileButton = new Button("Load profile");
        this.loadProfileButton.idProperty().set("load-profile-button");
        this.saveProfileButton = new Button("Save profile");
        this.saveProfileButton.idProperty().set("save-profile-button");
        this.saveLoadVbox = new VBox();
        this.saveLoadVbox.idProperty().set("save-load-vbox");
        this.saveLoadVbox.getChildren().addAll(saveProfileButton, loadProfileButton);

        this.profileTabHbox = new HBox();
        this.profileTabHbox.getChildren().addAll(saveLoadVbox);
        this.profileTabVbox = new VBox(profileTabHbox);
        this.profileTabVbox.idProperty().set("profile-tab-vbox");

        this.profileTab = new Tab("Profile");
        this.profileTab.setContent(profileTabVbox);

        this.tabPane = new TabPane();
        this.tabPane.getTabs().addAll(mainTab, profileTab);

        return this.tabPane;
    }

    public ChoiceBox<String> getIdleLocationChoiceBox() {
        return this.idleLocationChoiceBox;
    }

    public HBox getIdleLocationHbox() {
        return this.idleLocationHbox;
    }

    public Label getIdleLocationLabel() {
        return this.idleLocationLabel;
    }

    public HBox getIdleTimeBeforeLogoutHbox() {
        return this.idleTimeBeforeLogoutHbox;
    }

    public Label getIdleTimeBeforeLogoutLabel() {
        return this.idleTimeBeforeLogoutLabel;
    }

    public TextField getIdleTimeBeforeLogoutTextField() {
        return this.idleTimeBeforeLogoutTextField;
    }

    public VBox getLogoutInIdleLocationAdditionalOptionsVbox() {
        return this.logoutInIdleLocationAdditionalOptionsVbox;
    }

    public Label getLogoutInIdleLocationLabel() {
        return this.logoutInIdleLocationLabel;
    }

    public RadioButton getLogoutInIdleLocationRadioButton() {
        return this.LogoutInIdleLocationRadioButton;
    }

    public Label getLogoutInPlaceLabel() {
        return this.logoutInPlaceLabel;
    }

    public RadioButton getLogoutInPlaceRadioButton() {
        return this.logoutInPlaceRadioButton;
    }

    public HBox getLogoutMethodHbox() {
        return this.logoutMethodHbox;
    }

    public Label getLogoutMethodLabel() {
        return this.logoutMethodLabel;
    }

    public ChoiceBox<String> getLogoutMethodChoiceBox() {
        return this.logoutMethodChoiceBox;
    }

    public HBox getLogoutOptionsHbox() {
        return this.logoutOptionsHbox;
    }

    public ToggleGroup getLogoutOptionsToggleGroup() {
        return this.logoutOptionsToggleGroup;
    }

    public Tab getMainTab() {
        return this.mainTab;
    }

    public VBox getMainTabVbox() {
        return this.mainTabVbox;
    }

    public Tab getProfileTab() {
        return this.profileTab;
    }

    public HBox getRuntimeHbox() {
        return this.runtimeHbox;
    }

    public Label getScriptRuntimeLabel() {
        return this.scriptRuntimeLabel;
    }

    public TextField getScriptRuntimeTextfield() {
        return this.scriptRuntimeTextfield;
    }

    public TabPane getTabPane() {
        return this.tabPane;
    }

}
