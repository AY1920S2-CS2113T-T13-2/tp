package seedu.nuke.ui;

import seedu.nuke.command.ChangeDirectoryCommand;
import seedu.nuke.command.CommandResult;
import seedu.nuke.command.ExitCommand;
import seedu.nuke.command.HelpCommand;
import seedu.nuke.command.addcommand.AddCategoryCommand;
import seedu.nuke.command.addcommand.AddFileCommand;
import seedu.nuke.command.addcommand.AddModuleCommand;
import seedu.nuke.command.addcommand.AddTagCommand;
import seedu.nuke.command.addcommand.AddTaskCommand;
import seedu.nuke.command.editcommand.EditCategoryCommand;
import seedu.nuke.command.editcommand.EditModuleCommand;
import seedu.nuke.command.editcommand.EditTaskCommand;
import seedu.nuke.command.editcommand.MarkAsDoneCommand;
import seedu.nuke.command.filtercommand.deletecommand.DeleteCategoryCommand;
import seedu.nuke.command.filtercommand.deletecommand.DeleteModuleCommand;
import seedu.nuke.command.filtercommand.deletecommand.DeleteTagCommand;
import seedu.nuke.command.filtercommand.deletecommand.DeleteTaskCommand;
import seedu.nuke.command.filtercommand.listcommand.ListAllTasksDeadlineCommand;
import seedu.nuke.command.filtercommand.listcommand.ListCategoryCommand;
import seedu.nuke.command.filtercommand.listcommand.ListModuleTasksDeadlineCommand;
import seedu.nuke.command.filtercommand.listcommand.ListTaskCommand;
import seedu.nuke.directory.Category;
import seedu.nuke.directory.DirectoryLevel;
import seedu.nuke.directory.DirectoryTraverser;
import seedu.nuke.directory.Module;
import seedu.nuke.directory.Task;
import seedu.nuke.directory.TaskFile;
import seedu.nuke.util.ListCreator;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Ui {
    private static final String LS = System.lineSeparator();
    public static final ArrayList<String> commands = new ArrayList<>();

    private final Scanner in;
    private final PrintStream out;


    /**
     * Constructs the <code>UI</code>.
     */
    public Ui() {
        in = new Scanner(System.in);
        out = new PrintStream(System.out);
        commands.add(ChangeDirectoryCommand.MESSAGE_USAGE);
        commands.add(AddModuleCommand.MESSAGE_USAGE);
        commands.add(AddCategoryCommand.MESSAGE_USAGE);
        commands.add(AddTaskCommand.MESSAGE_USAGE);
        commands.add(AddTagCommand.MESSAGE_USAGE);
        commands.add(AddFileCommand.MESSAGE_USAGE);
        commands.add(EditModuleCommand.MESSAGE_USAGE);
        commands.add(EditCategoryCommand.MESSAGE_USAGE);
        commands.add(EditTaskCommand.MESSAGE_USAGE);
        commands.add(MarkAsDoneCommand.MESSAGE_USAGE);
        commands.add(DeleteModuleCommand.MESSAGE_USAGE);
        commands.add(DeleteCategoryCommand.MESSAGE_USAGE);
        commands.add(DeleteTaskCommand.MESSAGE_USAGE);
        commands.add(DeleteTagCommand.MESSAGE_USAGE);
        commands.add(ListCategoryCommand.MESSAGE_USAGE);
        commands.add(ListTaskCommand.MESSAGE_USAGE);
        commands.add(ListModuleTasksDeadlineCommand.MESSAGE_USAGE);
        commands.add(ListAllTasksDeadlineCommand.MESSAGE_USAGE);
        commands.add(HelpCommand.MESSAGE_USAGE);
        commands.add(ExitCommand.MESSAGE_USAGE);
    }

    /**
     * Shows the directory path to the user.
     */
    public void showDirectoryPath() {
        out.println(String.format("%s : ", DirectoryTraverser.getFullPath()));
    }

    /**
     * Reads the user input from the command line.
     *
     * @return The input given by the user
     */
    public String getInput() {
        showDirectoryPath();
        return in.nextLine().trim();
    }

    /**
     * Shows the result message after the command given by the user input is executed.
     * <br>
     * Also, shows a list to the user if requested.
     *
     * @param result
     *  The result after executing a command given by the user input
     */
    public void showResult(CommandResult result) {
        out.println(result.getFeedbackToUser().replace("\n", LS));

        if ((result.getDirectoryLevel() == DirectoryLevel.NONE) && result.getHelpGuide() == null) {
            return;
        }

        if (result.getHelpGuide() != null) {
            printShownList(result.getHelpGuide());
            return;
        }

        String listTableToShow;
        switch (result.getDirectoryLevel()) {

        case MODULE:
            ArrayList<Module> moduleList = result.getShownList().stream()
                    .map(Module.class::cast)
                    .collect(Collectors.toCollection(ArrayList::new));
            listTableToShow = ListCreator.createModuleListTable(moduleList);
            break;

        case CATEGORY:
            ArrayList<Category> categoryList = result.getShownList().stream()
                    .map(Category.class::cast)
                    .collect(Collectors.toCollection(ArrayList::new));
            listTableToShow = ListCreator.createCategoryListTable(categoryList);
            break;

        case TASK:
            ArrayList<Task> taskList = result.getShownList().stream()
                    .map(Task.class::cast)
                    .collect(Collectors.toCollection(ArrayList::new));
            listTableToShow = ListCreator.createTaskListTable(taskList);
            break;

        case FILE:
            ArrayList<TaskFile> fileList = result.getShownList().stream()
                    .map(TaskFile.class::cast)
                    .collect(Collectors.toCollection(ArrayList::new));
            listTableToShow = ListCreator.createFileListTable(fileList);
            break;

        default:
            return;
        }

        out.println(listTableToShow);
    }

    /**
     * Shows a message to the user.
     *
     * @param message Message to be shown
     */
    public void showSystemMessage(String message) {
        out.println(message.replace("\n", LS));
    }

    /**
     * print the Strings in an ArrayList to the user.
     *
     * @param shownList an ArrayList of Strings to be shown to the user
     */
    public void printShownList(ArrayList<String> shownList) {
        final String divider = String.format("%s%s%s", "+", "-".repeat(100), "+");
        System.out.println(divider);

        for (String str : shownList) {
            System.out.println(str);
        }

        System.out.println(divider + "\n");
    }

}
