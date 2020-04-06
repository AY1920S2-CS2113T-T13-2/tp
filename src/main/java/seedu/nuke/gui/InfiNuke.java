package seedu.nuke.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import seedu.nuke.Executor;
import seedu.nuke.data.ModuleLoader;
import seedu.nuke.data.ModuleManager;
import seedu.nuke.data.ScreenShotManager;
import seedu.nuke.data.storage.StorageManager;
import seedu.nuke.data.storage.StoragePath;

import java.io.IOException;
import java.util.HashMap;

public class InfiNuke extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void init() throws Exception {
        // Load modules and tasks
        HashMap<String, String> modulesMap = ModuleLoader.load(StoragePath.NUS_MODULE_LIST_PATH);
        StorageManager storageManager = new StorageManager(StoragePath.SAVE_PATH);
        ModuleManager.initialise(modulesMap);
        storageManager.loadList();
        ScreenShotManager.initialise();
        Executor.setIsGui();
    }

    @Override
    public void start(Stage stage) throws IOException {
        stage.setTitle("INFI-NUKE");
        stage.getIcons().add(new Image("images/venus_icon.png"));
        stage.setMinWidth(1250);
        stage.setMinHeight(700);

        FXMLLoader sceneLoader = new FXMLLoader(getClass().getResource("/main.fxml"));
        Parent mainRoot = sceneLoader.load();
        Scene main = new Scene(mainRoot);

        stage.setScene(main);
        stage.show();
    }
}
