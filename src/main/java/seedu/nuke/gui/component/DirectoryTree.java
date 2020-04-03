package seedu.nuke.gui.component;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.TextFlow;
import seedu.nuke.data.ModuleManager;
import seedu.nuke.directory.Category;
import seedu.nuke.directory.Module;
import seedu.nuke.directory.Task;
import seedu.nuke.gui.io.GuiExecutor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;

public class DirectoryTree extends TreeView<Label> {

    private TextFlow consoleScreen;

    /**
     * Constructs the Directory Tree class.
     */
    public DirectoryTree(TextFlow consoleScreen) {
        super();
        this.consoleScreen = consoleScreen;

        setStyle("-fx-font-family: Consolas; -fx-font-size: 11pt; -fx-font-weight: bold; "
                + "-fx-focus-color: transparent; -fx-faint-focus-color: transparent; "
                + "-fx-border-color: lightgrey; -fx-border-radius: 3;");
        setShowRoot(false);

        populateTree();
    }

    private void populateTree() {
        TreeItem<Label> root = new TreeItem<>(new Label("Root"));

        // Get and sort Module List
        ArrayList<Module> moduleList = ModuleManager.getModuleList();
        moduleList.sort(Comparator.comparing(Module::getModuleCode));

        for (Module module : moduleList) {
            // Add module to tree
            TreeItem<Label> moduleItem = new TreeItem<>();
            styleTreeItem(moduleItem, root, module.getModuleCode(), Color.SANDYBROWN);
            moduleItem.getValue().setOnMouseClicked(mouseEvent ->
                    onClickModule(mouseEvent, module)
            );

            // Get and sort Category List
            ArrayList<Category> categoryList = module.getCategories().getCategoryList();
            categoryList.sort(Comparator.comparing(Category::getCategoryName));

            for (Category category : categoryList) {
                // Add category to tree
                TreeItem<Label> categoryItem = new TreeItem<>();
                styleTreeItem(categoryItem, moduleItem, category.getCategoryName(), Color.DARKSEAGREEN);
                categoryItem.getValue().setOnMouseClicked(mouseEvent ->
                        onClickCategory(mouseEvent, module.getModuleCode(), category)
                );

                // Get and sort Task List
                ArrayList<Task> taskList = category.getTasks().getTaskList();
                taskList.sort(Comparator.comparing(Task::getDescription));

                for (Task task : taskList) {
                    // Add task to tree
                    TreeItem<Label> taskItem = new TreeItem<>();
                    if (!task.getDeadline().isPresent()) {
                        styleTreeItem(taskItem, categoryItem, task.getDescription(), Color.CORNFLOWERBLUE);
                    } else if (task.isDone()) {
                        // Task is done but not deleted yet
                        styleTreeItem(taskItem, categoryItem, task.getDescription(), Color.LIGHTGRAY);
                    } else if (task.getDeadline().isDue()) {
                        // Highlight if task is due
                        styleTreeItem(taskItem, categoryItem, task.getDescription(), Color.CRIMSON);
                        taskItem.getValue().setStyle("-fx-background-color: PeachPuff");
                    } else if (task.getDeadline().isBefore(LocalDate.now().plusDays(3))) {
                        // Highlight if task is near deadline (within 2 days)
                        styleTreeItem(taskItem, categoryItem, task.getDescription(), Color.CORAL);
                        taskItem.getValue().setStyle("-fx-background-color: Moccasin");
                    } else {
                        styleTreeItem(taskItem, categoryItem, task.getDescription(), Color.CORNFLOWERBLUE);
                    }
                    taskItem.getValue().setOnMouseClicked(mouseEvent ->
                            onClickTask(mouseEvent, module.getModuleCode(), category.getCategoryName(), task)
                    );
                }
            }
        }
        this.setRoot(root);
    }

    private void styleTreeItem(TreeItem<Label> treeItem, TreeItem<Label> parentItem, String name, Color color) {
        Label treeLabel = new Label(name);
        treeLabel.setTextFill(color);
        treeLabel.setPadding(new Insets(3));
        treeLabel.setCursor(Cursor.HAND);
        treeItem.setValue(treeLabel);
        treeItem.setExpanded(true);
        parentItem.getChildren().add(treeItem);
    }

    public void refresh() {
        populateTree();
    }

    private void onClickModule(MouseEvent mouseEvent, Module module) {
        final String listCategoryString = String.format("lsc -m %s -e -a", module.getModuleCode());
        if (mouseEvent.getButton() == MouseButton.PRIMARY) {
            new GuiExecutor(consoleScreen).showMessage(module.toString());
            new GuiExecutor(consoleScreen).executeAction(listCategoryString);
        }
    }

    private void onClickCategory(MouseEvent mouseEvent, String moduleCode, Category category) {
        final String listTaskString = String.format("lst -m %s -c %s -e -a", moduleCode, category.getCategoryName());
        if (mouseEvent.getButton() == MouseButton.PRIMARY) {
            new GuiExecutor(consoleScreen).showMessage(category.toString());
            new GuiExecutor(consoleScreen).executeAction(listTaskString);
        }
    }

    private void onClickTask(MouseEvent mouseEvent, String moduleCode, String categoryName, Task task) {
        final String listFileString =
                String.format("lsf -m %s -c %s -t %s -e -a", moduleCode, categoryName, task.getDescription());
        if (mouseEvent.getButton() == MouseButton.PRIMARY) {
            new GuiExecutor(consoleScreen).showMessage(task.toString());
            new GuiExecutor(consoleScreen).executeAction(listFileString);
        }
    }
}
