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
import seedu.nuke.directory.TaskFile;
import seedu.nuke.exception.DataNotFoundException;
import seedu.nuke.gui.io.GuiExecutor;
import seedu.nuke.gui.util.tablecreator.basicdirectory.BasicFile;

import java.util.ArrayList;

public class FileTableCreator {
    private static final int TABLE_WIDTH = 880;
    private static final int ID_WIDTH = 50;
    private static final int MODULE_CODE_WIDTH = 130;
    private static final int CATEGORY_NAME_WIDTH = 180;
    private static final int TASK_DESCRIPTION_WIDTH = 240;
    private static final int FILE_NAME_WIDTH = 280;
    private static final int ROW_HEIGHT = 30;
    private static final int HEADER_HEIGHT = 35;

    private ArrayList<TaskFile> fileList;
    private TextFlow consoleScreen;

    /**
     * Constructs the TaskFile Table Creator class to create a file table.
     *
     * @param fileList
     *  The list of files to show
     * @param consoleScreen
     *  The console screen to show the list
     */
    public FileTableCreator(ArrayList<TaskFile> fileList, TextFlow consoleScreen) {
        this.fileList = fileList;
        this.consoleScreen = consoleScreen;
    }

    /**
     * Creates a Table View from the files in the file list.
     *
     * @return
     *  The Table View of the files in the file list
     */
    public TableView<BasicFile> createFileListTable() {
        TableView<BasicFile> fileTable = new TableView<>();
        initialiseTaskFileTable(fileTable);
        return fileTable;
    }

    @SuppressWarnings("unchecked")
    private void initialiseTaskFileTable(TableView<BasicFile> fileTable) {
        // Row properties
        fileTable.setRowFactory(basicTaskFileTableView -> {
            TableRow<BasicFile> row = new TableRow<>();
            row.setPrefHeight(ROW_HEIGHT);
            row.setCursor(Cursor.HAND);
            row.setOnMouseClicked(event -> onRowClick(event, row));
            return row;
        });

        // Set height
        int height = fileList.size() * ROW_HEIGHT + HEADER_HEIGHT;
        fileTable.setPrefHeight(height);

        // Populate columns and data
        fileTable.getColumns()
                .addAll(getIdColumn(), getModuleCodeColumn(), getCategoryNameColumn(), getTaskDescriptionColumn(),
                        getFileNameColumn());
        fileTable.getItems().addAll(getBasicFileList());

        // Width properties
        fileTable.setMinWidth(TABLE_WIDTH);
        fileTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        fileTable.setPlaceholder(new Label("No modules to show"));
    }

    private TableColumn<BasicFile, Integer> getIdColumn() {
        TableColumn<BasicFile, Integer> idColumn = new TableColumn<>("NO");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        idColumn.setPrefWidth(ID_WIDTH);
        idColumn.setStyle("-fx-alignment: CENTER");

        return idColumn;
    }

    private TableColumn<BasicFile, String> getModuleCodeColumn() {
        TableColumn<BasicFile, String> moduleCodeColumn = new TableColumn<>("Module");
        moduleCodeColumn.setCellValueFactory(new PropertyValueFactory<>("moduleCode"));
        moduleCodeColumn.setPrefWidth(MODULE_CODE_WIDTH);
        moduleCodeColumn.setStyle("-fx-alignment: CENTER");

        return moduleCodeColumn;
    }

    private TableColumn<BasicFile, String> getCategoryNameColumn() {
        TableColumn<BasicFile, String> categoryNameColumn = new TableColumn<>("Category");
        categoryNameColumn.setCellValueFactory(new PropertyValueFactory<>("categoryName"));
        categoryNameColumn.setPrefWidth(CATEGORY_NAME_WIDTH);
        categoryNameColumn.setStyle("-fx-alignment: CENTER");

        return categoryNameColumn;
    }

    private TableColumn<BasicFile, String> getTaskDescriptionColumn() {
        TableColumn<BasicFile, String> descriptionColumn = new TableColumn<>("Task");
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("taskDescription"));
        descriptionColumn.setPrefWidth(TASK_DESCRIPTION_WIDTH);
        descriptionColumn.setStyle("-fx-alignment: CENTER");

        return descriptionColumn;
    }

    private TableColumn<BasicFile, String> getFileNameColumn() {
        TableColumn<BasicFile, String> deadlineColumn = new TableColumn<>("File");
        deadlineColumn.setCellValueFactory(new PropertyValueFactory<>("fileName"));
        deadlineColumn.setPrefWidth(FILE_NAME_WIDTH);
        deadlineColumn.setStyle("-fx-alignment: CENTER");

        return deadlineColumn;
    }

    private ObservableList<BasicFile> getBasicFileList() {
        ArrayList<BasicFile> basicTaskFileList = new ArrayList<>();

        int id = 1;
        for (TaskFile file : fileList) {
            basicTaskFileList.add(new BasicFile(id++, file.getParent().getParent().getParent().getModuleCode(),
                    file.getParent().getParent().getCategoryName(), file.getParent().getDescription(),
                    file.getFileName()));
        }

        return FXCollections.observableArrayList(basicTaskFileList);
    }

    private void onRowClick(MouseEvent mouseEvent, TableRow<BasicFile> row) {
        if (mouseEvent.getButton() == MouseButton.PRIMARY && !row.isEmpty()) {
            BasicFile rowData = row.getItem();
            try {
                TaskFile file = ModuleManager.getFile(rowData.getModuleCode(), rowData.getCategoryName(),
                        rowData.getTaskDescription(), rowData.getFileName());
                new GuiExecutor(consoleScreen).executeCommand(new InfoCommand(file));
            } catch (DataNotFoundException e) {
                // Do nothing
            }
        }
    }
}
