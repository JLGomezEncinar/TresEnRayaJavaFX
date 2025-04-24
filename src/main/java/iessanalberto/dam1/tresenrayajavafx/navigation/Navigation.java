package iessanalberto.dam1.tresenrayajavafx.navigation;

import iessanalberto.dam1.tresenrayajavafx.screens.MainScreen;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Navigation {
    private static final Stage stage = new Stage();

    public static void navigate (String destination) {
        switch (destination) {
            case "MainScreen" -> {
                MainScreen mainScreen = new MainScreen();
                Scene mainScene = new Scene(mainScreen.getRoot(),480,360);
                stage.setTitle("Tres en Raya");
                stage.setScene(mainScene);
                stage.show();
            }
        }
    }
}
