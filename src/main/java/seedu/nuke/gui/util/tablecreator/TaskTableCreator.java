package seedu.nuke.gui.util.tablecreator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.TextFlow;
import seedu.nuke.command.misc.InfoCommand;
import seedu.nuke.data.ModuleManager;
import seedu.nuke.directory.Task;
import seedu.nuke.exception.DataNotFoundException;
import seedu.nuke.gui.io.GuiExecutor;
import seedu.nuke.gui.util.tablecreator.basicdirectory.BasicTask;

import java.time.LocalDate;
import java.util.ArrayList;

import static seedu.nuke.common.Constants.CHECK_ICON;
import static seedu.nuke.common.Constants.CROSS_ICON;

public class TaskTableCreator {
    private static final int TABLE_WIDTH = 880;
    private static final int ID_WIDTH = 40;
    private static final int MODULE_CODE_WIDTH = 100;
    private static final int CATEGORY_NAME_WIDTH = 150;
    private static final int TASK_DESCRIPTION_WIDTH = 230;
    private static final int DEADLINE_WIDTH = 270;
    private static final int PRIORITY_WIDTH = 40;
    private static final int DONE_STATUS_WIDTH = 50;
    private static final int ROW_HEIGHT = 30;
    private static final int HEADER_HEIGHT = 35;

    private ArrayList<Task> taskList;
    private TextFlow consoleScreen;

    /**
     * Constructs the Task Table Creator class to create a task table.
     *
     * @param taskList
     *  The list of tasks to show
     * @param consoleScreen
     *  The console screen to show the list
     */
    public TaskTableCreator(ArrayList<Task> taskList, TextFlow consoleScreen) {
        this.taskList = taskList;
        this.consoleScreen = consoleScreen;
    }

    /**
     * Creates a Table View from the tasks in the task list.
     *
     * @return
     *  The Table View of the tasks in the task list
     */
    public TableView<BasicTask> createTaskListTable() {
        TableView<BasicTask> taskTable = new TableView<>();
        initialiseTaskTable(taskTable);
        return taskTable;
    }

    @SuppressWarnings("unchecked")
    private void initialiseTaskTable(TableView<BasicTask> taskTable) {
        // Row properties
        taskTable.setRowFactory(basicTaskTableView -> {
            TableRow<BasicTask> row = new TableRow<>();
            row.setPrefHeight(ROW_HEIGHT);
            row.setCursor(Cursor.HAND);
            row.setOnMouseClicked(event -> onRowClick(event, row));
            return row;
        });

        // Set height
        int height = taskList.size() * ROW_HEIGHT + HEADER_HEIGHT;
        taskTable.setPrefHeight(height);

        // Populate columns and data
        taskTable.getColumns()
                .addAll(getIdColumn(), getModuleCodeColumn(), getCategoryNameColumn(), getDescriptionColumn(),
                        getDeadlineColumn(), getPriorityColumn(), getDoneStatusColumn());
        taskTable.getItems().addAll(getBasicTaskList());

        // Width properties
        taskTable.setMinWidth(TABLE_WIDTH);
        taskTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        taskTable.setPlaceholder(new Label("No modules to show"));
    }

    private TableColumn<BasicTask, Integer> getIdColumn() {
        TableColumn<BasicTask, Integer> idColumn = new TableColumn<>("NO");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        idColumn.setPrefWidth(ID_WIDTH);
        idColumn.setStyle("-fx-alignment: CENTER");

        return idColumn;
    }

    private TableColumn<BasicTask, String> getModuleCodeColumn() {
        TableColumn<BasicTask, String> moduleCodeColumn = new TableColumn<>("Module");
        moduleCodeColumn.setCellValueFactory(new PropertyValueFactory<>("moduleCode"));
        moduleCodeColumn.setPrefWidth(MODULE_CODE_WIDTH);
        moduleCodeColumn.setStyle("-fx-alignment: CENTER");

        return moduleCodeColumn;
    }

    private TableColumn<BasicTask, String> getCategoryNameColumn() {
        TableColumn<BasicTask, String> categoryNameColumn = new TableColumn<>("Category");
        categoryNameColumn.setCellValueFactory(new PropertyValueFactory<>("categoryName"));
        categoryNameColumn.setPrefWidth(CATEGORY_NAME_WIDTH);
        categoryNameColumn.setStyle("-fx-alignment: CENTER");

        return categoryNameColumn;
    }

    private TableColumn<BasicTask, String> getDescriptionColumn() {
        TableColumn<BasicTask, String> descriptionColumn = new TableColumn<>("Task");
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        descriptionColumn.setPrefWidth(TASK_DESCRIPTION_WIDTH);
        descriptionColumn.setStyle("-fx-alignment: CENTER");

        return descriptionColumn;
    }

    private TableColumn<BasicTask, String> getDeadlineColumn() {
        TableColumn<BasicTask, String> deadlineColumn = new TableColumn<>("Deadline");
        deadlineColumn.setCellValueFactory(new PropertyValueFactory<>("deadline"));
        deadlineColumn.setPrefWidth(DEADLINE_WIDTH);
        // Color table rows depending on the proximity to deadline
        deadlineColumn.setCellFactory(column -> new TableCell<BasicTask, String>() {
            @Override
            protected void updateItem(String deadlineString, boolean empty) {
                super.updateItem(deadlineString, empty);
                TableRow<BasicTask> row = getTableRow();

                if (deadlineString == null || empty) {
                    row.setStyle("");
                    return;
                }
                setText(deadlineString);
                BasicTask basicTask = row.getItem();
                try {
                    Task task = ModuleManager.getTask(basicTask.getModuleCode(), basicTask.getCategoryName(),
                            basicTask.getDescription());
                    if (task.isDone()) {
                        // Highlight if task is done
                        row.setStyle("-fx-background-color: Gainsboro");
                    } else if (task.getDeadline().isPresent() && task.getDeadline().isDue()) {
                        // Highlight if task is due
                        row.setStyle("-fx-background-color: PeachPuff");
                    } else if (task.getDeadline().isPresent()
                            && task.getDeadline().isBefore(LocalDate.now().plusDays(3))) {
                        // Highlight if task is near deadline (within 2 days)
                        row.setStyle("-fx-background-color: CornSilk");
                    }
                } catch (DataNotFoundException e) {
                    // Do nothing
                }
            }
        });

        deadlineColumn.setStyle("-fx-alignment: CENTER");
        return deadlineColumn;
    }


    private TableColumn<BasicTask, Integer> getPriorityColumn() {
        TableColumn<BasicTask, Integer> priorityColumn = new TableColumn<>("Pty");
        priorityColumn.setCellValueFactory(new PropertyValueFactory<>("priority"));
        priorityColumn.setPrefWidth(PRIORITY_WIDTH);
        priorityColumn.setStyle("-fx-alignment: CENTER");

        return priorityColumn;
    }

    private TableColumn<BasicTask, String> getDoneStatusColumn() {
        TableColumn<BasicTask, String> doneStatusColumn = new TableColumn<>("Done");
        doneStatusColumn.setCellValueFactory(new PropertyValueFactory<>("doneStatus"));
        doneStatusColumn.setPrefWidth(DONE_STATUS_WIDTH);
        doneStatusColumn.setStyle("-fx-alignment: CENTER");

        return doneStatusColumn;
    }

    private ObservableList<BasicTask> getBasicTaskList() {
        ArrayList<BasicTask> basicTaskList = new ArrayList<>();

        int id = 1;
        for (Task task : taskList) {
            String deadline = task.getDeadline().isPresent() ? task.getDeadline().toShow() : "-NIL-";
            String doneStatus = task.isDone() ? CHECK_ICON : CROSS_ICON;
            basicTaskList.add(new BasicTask(id++, task.getParent().getParent().getModuleCode(),
                    task.getParent().getCategoryName(), task.getDescription(), deadline,
                    task.getPriority(), doneStatus));
        }

        return FXCollections.observableArrayList(basicTaskList);
    }

    private void onRowClick(MouseEvent mouseEvent, TableRow<BasicTask> row) {
        if (mouseEvent.getButton() == MouseButton.PRIMARY && !row.isEmpty()) {
            BasicTask rowData = row.getItem();
            try {
                Task task = ModuleManager.getTask(rowData.getModuleCode(), rowData.getCategoryName(),
                        rowData.getDescription());
                new GuiExecutor(consoleScreen).executeCommand(new InfoCommand(task));
            } catch (DataNotFoundException e) {
                // Do nothing
            }
        }
    }
}
