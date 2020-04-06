package seedu.nuke.gui.util.tablecreator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.TextFlow;
import seedu.nuke.command.misc.InfoCommand;
import seedu.nuke.data.ModuleManager;
import seedu.nuke.directory.Category;
import seedu.nuke.exception.DataNotFoundException;
import seedu.nuke.gui.io.GuiExecutor;
import seedu.nuke.gui.util.tablecreator.basicdirectory.BasicCategory;

import java.util.ArrayList;

public class CategoryTableCreator {
    private static final int TABLE_WIDTH = 880;
    private static final int ID_WIDTH = 50;
    private static final int MODULE_CODE_WIDTH = 150;
    private static final int CATEGORY_NAME_WIDTH = 630;
    private static final int PRIORITY_WIDTH = 50;
    private static final int ROW_HEIGHT = 30;
    private static final int HEADER_HEIGHT = 35;

    private ArrayList<Category> categoryList;
    private TextFlow consoleScreen;

    /**
     * Constructs the Category Table Creator class to create a category table.
     *
     * @param categoryList
     *  The list of categories to show
     * @param consoleScreen
     *  The console screen to show the list
     */
    public CategoryTableCreator(ArrayList<Category> categoryList, TextFlow consoleScreen) {
        this.categoryList = categoryList;
        this.consoleScreen = consoleScreen;
    }

    /**
     * Creates a Table View from the categories in the category list.
     *
     * @return
     *  The Table View of the categories in the category list
     */
    public TableView<BasicCategory> createCategoryListTable() {
        TableView<BasicCategory> categoryTable = new TableView<>();
        initialiseCategoryTable(categoryTable);
        return categoryTable;
    }

    @SuppressWarnings("unchecked")
    private void initialiseCategoryTable(TableView<BasicCategory> categoryTable) {
        // Row properties
        categoryTable.setRowFactory(basicCategoryTableView -> {
            TableRow<BasicCategory> row = new TableRow<>();
            row.setPrefHeight(ROW_HEIGHT);
            row.setCursor(Cursor.HAND);
            row.setOnMouseClicked(event -> onRowClick(event, row));
            return row;
        });

        // Set height
        int height = categoryList.size() * ROW_HEIGHT + HEADER_HEIGHT;
        categoryTable.setPrefHeight(height);

        // Populate columns and data
        categoryTable.getColumns()
                .addAll(getIdColumn(), getModuleCodeColumn(), getCategoryNameColumn(), getPriorityColumn());
        categoryTable.getItems().addAll(getBasicCategoryList());

        // Width properties
        categoryTable.setMinWidth(TABLE_WIDTH);
        categoryTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        categoryTable.setPlaceholder(new Label("No modules to show"));
    }

    private TableColumn<BasicCategory, Integer> getIdColumn() {
        TableColumn<BasicCategory, Integer> idColumn = new TableColumn<>("NO");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        idColumn.setPrefWidth(ID_WIDTH);
        idColumn.setStyle("-fx-alignment: CENTER");

        return idColumn;
    }

    private TableColumn<BasicCategory, String> getModuleCodeColumn() {
        TableColumn<BasicCategory, String> moduleCodeColumn = new TableColumn<>("Module");
        moduleCodeColumn.setCellValueFactory(new PropertyValueFactory<>("moduleCode"));
        moduleCodeColumn.setPrefWidth(MODULE_CODE_WIDTH);
        moduleCodeColumn.setStyle("-fx-alignment: CENTER");

        return moduleCodeColumn;
    }

    private TableColumn<BasicCategory, String> getCategoryNameColumn() {
        TableColumn<BasicCategory, String> categoryNameColumn = new TableColumn<>("Category");
        categoryNameColumn.setCellValueFactory(new PropertyValueFactory<>("categoryName"));
        categoryNameColumn.setPrefWidth(CATEGORY_NAME_WIDTH);
        categoryNameColumn.setStyle("-fx-alignment: CENTER");

        return categoryNameColumn;
    }

    private TableColumn<BasicCategory, Integer> getPriorityColumn() {
        TableColumn<BasicCategory, Integer> priorityColumn = new TableColumn<>("Pty");
        priorityColumn.setCellValueFactory(new PropertyValueFactory<>("priority"));
        priorityColumn.setPrefWidth(PRIORITY_WIDTH);
        priorityColumn.setStyle("-fx-alignment: CENTER");

        return priorityColumn;
    }

    private ObservableList<BasicCategory> getBasicCategoryList() {
        ArrayList<BasicCategory> basicCategoryList = new ArrayList<>();

        int id = 1;
        for (Category category : categoryList) {
            basicCategoryList.add(new BasicCategory(id++, category.getParent().getModuleCode(),
                    category.getCategoryName(), category.getCategoryPriority()));
        }

        return FXCollections.observableArrayList(basicCategoryList);
    }

    private void onRowClick(MouseEvent mouseEvent, TableRow<BasicCategory> row) {
        if (mouseEvent.getButton() == MouseButton.PRIMARY && !row.isEmpty()) {
            BasicCategory rowData = row.getItem();
            try {
                Category category = ModuleManager.getCategory(rowData.getModuleCode(), rowData.getCategoryName());
                new GuiExecutor(consoleScreen).executeCommand(new InfoCommand(category));
            } catch (DataNotFoundException e) {
                // Do nothing
            }
        }
    }
}
