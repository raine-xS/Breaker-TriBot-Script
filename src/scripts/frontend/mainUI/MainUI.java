package scripts.frontend.mainUI;

import javafx.scene.Parent;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import org.tribot.script.sdk.Log;
import scripts.frontend.GUI;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;


public class MainUI extends GUI {
    @Override
    protected Parent createContent() {
        Tab main = new Tab("main");
        Tab profile = new Tab("profile");
        TabPane tabPane = new TabPane();
        tabPane.getTabs().addAll(main, profile);


        return tabPane;
    }


}
