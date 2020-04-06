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
import seedu.nuke.directory.Module;
import seedu.nuke.gui.io.GuiExecutor;
import seedu.nuke.gui.util.tablecreator.basicdirectory.BasicModule;

import java.util.ArrayList;

public class ModuleTableCreator {
    private static final int TABLE_WIDTH = 880;
    private static final int ID_WIDTH = 50;
    private static final int MODULE_CODE_WIDTH = 150;
    private static final int TITLE_WIDTH = 680;
    private static final int ROW_HEIGHT = 30;
    private static final int HEADER_HEIGHT = 35;

    private ArrayList<Module> moduleList;
    private TextFlow consoleScreen;

    /**
     * Constructs the Module Table Creator class to create a module table.
     *
     * @param moduleList
     *  The list of modules to show
     * @param consoleScreen
     *  The console screen to show the list
     */
    public ModuleTableCreator(ArrayList<Module> moduleList, TextFlow consoleScreen) {
        this.moduleList = moduleList;
        this.consoleScreen = consoleScreen;
    }

    /**
     * Creates a Table View from the modules in the module list.
     *
     * @return
     *  The Table View of the modules in the module list
     */
    public TableView<BasicModule> createModuleListTable() {
        TableView<BasicModule> moduleTable = new TableView<>();
        initialiseModuleTable(moduleTable);
        return moduleTable;
    }

    @SuppressWarnings("unchecked")
    private void initialiseModuleTable(TableView<BasicModule> moduleTable) {
        // Row properties
        moduleTable.setRowFactory(basicModuleTableView -> {
            TableRow<BasicModule> row = new TableRow<>();
            row.setPrefHeight(ROW_HEIGHT);
            row.setCursor(Cursor.HAND);
            row.setOnMouseClicked(event -> onRowClick(event, row));
            return row;
        });

        // Set height
        int height = moduleList.size() * ROW_HEIGHT + HEADER_HEIGHT;
        moduleTable.setPrefHeight(height);

        // Populate columns and data
        moduleTable.getColumns().addAll(getIdColumn(), getModuleCodeColumn(), getTitleColumn());
        moduleTable.getItems().addAll(getBasicModuleList());

        // Width properties
        moduleTable.setMinWidth(TABLE_WIDTH);
        moduleTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        moduleTable.setPlaceholder(new Label("No modules to show"));
    }

    private TableColumn<BasicModule, Integer> getIdColumn() {
        TableColumn<BasicModule, Integer> idColumn = new TableColumn<>("NO");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        idColumn.setPrefWidth(ID_WIDTH);
        idColumn.setStyle("-fx-alignment: CENTER");

        return idColumn;
    }

    private TableColumn<BasicModule, String> getModuleCodeColumn() {
        TableColumn<BasicModule, String> moduleCodeColumn = new TableColumn<>("Module Code");
        moduleCodeColumn.setCellValueFactory(new PropertyValueFactory<>("moduleCode"));
        moduleCodeColumn.setPrefWidth(MODULE_CODE_WIDTH);
        moduleCodeColumn.setStyle("-fx-alignment: CENTER");

        return moduleCodeColumn;
    }

    private TableColumn<BasicModule, String> getTitleColumn() {
        TableColumn<BasicModule, String> titleColumn = new TableColumn<>("Module Title");
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        titleColumn.setMinWidth(TITLE_WIDTH);
        titleColumn.setStyle("-fx-alignment: CENTER");

        return titleColumn;
    }

    private ObservableList<BasicModule> getBasicModuleList() {
        ArrayList<BasicModule> basicModuleList = new ArrayList<>();

        int id = 1;
        for (Module module : moduleList) {
            basicModuleList.add(new BasicModule(id++, module.getModuleCode(), module.getTitle()));
        }

        return FXCollections.observableArrayList(basicModuleList);
    }

    private void onRowClick(MouseEvent mouseEvent, TableRow<BasicModule> row) {
        if (mouseEvent.getButton() == MouseButton.PRIMARY && !row.isEmpty()) {
            BasicModule rowData = row.getItem();
            try {
                Module module = ModuleManager.getModule(rowData.getModuleCode());
                new GuiExecutor(consoleScreen).executeCommand(new InfoCommand(module));
            } catch (ModuleManager.ModuleNotFoundException e) {
                // Do nothing
            }
        }
    }
}
