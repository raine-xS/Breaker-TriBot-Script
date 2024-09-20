package scripts.frontend.mainUI;

import org.tribot.script.sdk.Log;

public class MainUIController {
    private MainUI mainUI;

    public MainUIController(MainUI mainUI){
        this.mainUI = mainUI;
        initialize();
    }

    private void initialize() {
        mainUI.getMainTab().setClosable(false);

        mainUI.getProfileTab().setClosable(false);

        mainUI.getScriptRuntimeTextfield().focusedProperty().addListener((observable, prevFocused, nowFocused) -> {
            // When the TextField is focused and contains no input
            if (nowFocused == true &&
                prevFocused == false &&
                mainUI.getScriptRuntimeTextfield().getText().toLowerCase().equals("minutes")) {
                // Clear the text
                mainUI.getScriptRuntimeTextfield().clear();
                // Set text color
                mainUI.getScriptRuntimeTextfield().setStyle("-fx-text-fill: #000000;");
            }
            // When the TextField is unfocused and contains no input
            if (nowFocused == false &&
                prevFocused == true &&
                mainUI.getScriptRuntimeTextfield().getText().isEmpty()) {
                // Set the text to "minutes"
                mainUI.getScriptRuntimeTextfield().setText("Minutes");
                // Set text color
                mainUI.getScriptRuntimeTextfield().setStyle("-fx-text-fill: #afafaf;");
            }
        });
        mainUI.getScriptRuntimeTextfield().textProperty().addListener((observableText, oldText, newText) -> {
            // When the text field is focused and the text does not contain digits only
            if (mainUI.getScriptRuntimeTextfield().isFocused() &&
                !newText.matches("\\d*")) {
                // Remove non-digit characters
                mainUI.getScriptRuntimeTextfield().setText(newText.replaceAll("[^\\d]", ""));
            }

            // Define the max length of the text
            int maxLength = 5;
            // When the text field is focused and the text becomes too long
            if (mainUI.getScriptRuntimeTextfield().isFocused()  &&
                newText.length() > maxLength) {
                // Truncate the text to the maximum length
                mainUI.getScriptRuntimeTextfield().setText(newText.substring(0, maxLength));
            }
        });

        // Set default radio button to be selected
        mainUI.getLogoutInPlaceRadioButton().selectedProperty().set(true);

        // Only additional options for logging out in idle location if the logout in idle location radio button is selected
        mainUI.getLogoutInIdleLocationAdditionalOptionsVbox().visibleProperty().bind(mainUI.getLogoutInIdleLocationRadioButton().selectedProperty());

        mainUI.getIdleTimeBeforeLogoutTextField().focusedProperty().addListener((observable, prevFocused, nowFocused) -> {
            // When the TextField is focused and contains no input
            if (nowFocused == true &&
                    prevFocused == false &&
                    mainUI.getIdleTimeBeforeLogoutTextField().getText().toLowerCase().equals("minutes")) {
                // Clear the text
                mainUI.getIdleTimeBeforeLogoutTextField().clear();
                // Set text color
                mainUI.getIdleTimeBeforeLogoutTextField().setStyle("-fx-text-fill: #000000;");
            }
            // When the TextField is unfocused and contains no input
            if (nowFocused == false &&
                    prevFocused == true &&
                    mainUI.getIdleTimeBeforeLogoutTextField().getText().isEmpty()) {
                // Set the text to "minutes"
                mainUI.getIdleTimeBeforeLogoutTextField().setText("Minutes");
                // Set text color
                mainUI.getIdleTimeBeforeLogoutTextField().setStyle("-fx-text-fill: #afafaf;");
            }
        });
        mainUI.getIdleTimeBeforeLogoutTextField().textProperty().addListener((observableText, oldText, newText) -> {
            // When the text field is focused and the text does not contain digits only
            if (mainUI.getIdleTimeBeforeLogoutTextField().isFocused() &&
                    !newText.matches("\\d*")) {
                // Remove non-digit characters
                mainUI.getIdleTimeBeforeLogoutTextField().setText(newText.replaceAll("[^\\d]", ""));
            }

            // Define the max length of the text
            int maxLength = 5;
            // When the text field is focused and the text becomes too long
            if (mainUI.getIdleTimeBeforeLogoutTextField().isFocused()  &&
                    newText.length() > maxLength) {
                // Truncate the text to the maximum length
                mainUI.getIdleTimeBeforeLogoutTextField().setText(newText.substring(0, maxLength));
            }
        });

        mainUI.getIdleLocationChoiceBox().getItems().addAll("Grand Exchange"); // Add choice box options for idle location
        mainUI.getIdleLocationChoiceBox().setValue("Grand Exchange"); // Set default option

        mainUI.getLogoutMethodChoiceBox().getItems().addAll("Afk Timeout", "Logout Button", "Random"); // Add choice box options for logout method
        mainUI.getLogoutMethodChoiceBox().setValue("Afk Timeout"); // Set default option


    }


}
