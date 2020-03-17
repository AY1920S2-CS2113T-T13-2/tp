package seedu.nuke.command.prompt;

import seedu.nuke.command.Command;
import seedu.nuke.command.CommandResult;
import seedu.nuke.common.DataType;
import seedu.nuke.data.category.Category;
import seedu.nuke.data.module.Module;
import seedu.nuke.data.module.ModuleList;
import seedu.nuke.data.task.Task;
import seedu.nuke.gui.io.Executor;

import java.util.ArrayList;

import static seedu.nuke.util.Message.*;

public class DeleteConfirmationPrompt extends Command {
    private ConfirmationStatus confirmationStatus;

    public DeleteConfirmationPrompt(ConfirmationStatus confirmationStatus) {
        this.confirmationStatus = confirmationStatus;
    }

    public void deleteSingleModule(Module toDelete) {
        ModuleList.delete(toDelete);
    }

    public void deleteSingleCategory(Category toDelete) {
        Module parentModule = toDelete.getParentModule();
        parentModule.getCategories().delete(toDelete);
    }

    public void deleteSingleTask(Task toDelete) {
        Category parentCategory = toDelete.getParentCategory();
        parentCategory.getTasks().delete(toDelete);
    }

    public void deleteMultipleModules(ArrayList<Module> modules, ArrayList<Integer> toDeleteIndices) {
        for (int index : toDeleteIndices) {
            Module toDelete = modules.get(index);
            deleteSingleModule(toDelete);
        }
    }

    public void deleteMultipleCategories(ArrayList<Category> categories, ArrayList<Integer> toDeleteIndices) {
        for (int index : toDeleteIndices) {
            Category toDelete = categories.get(index);
            deleteSingleCategory(toDelete);
        }
    }

    public void deleteMultipleTasks(ArrayList<Task> tasks, ArrayList<Integer> toDeleteIndices) {
        for (int index : toDeleteIndices) {
            Task toDelete = tasks.get(index);
            deleteSingleTask(toDelete);
        }
    }

    private CommandResult executeSingleDelete(ArrayList<?> filteredList) {
        DataType dataType = Executor.getDataType();
        switch (dataType) {
            case MODULE: {
                Module toDelete = (Module) filteredList.get(0);
                deleteSingleModule(toDelete);
                return new CommandResult(MESSAGE_DELETE_MODULE_SUCCESS);
            }
            case CATEGORY: {
                Category toDelete = (Category) filteredList.get(0);
                deleteSingleCategory(toDelete);
                return new CommandResult(MESSAGE_DELETE_CATEGORY_SUCCESS);
            }
            case TASK: {
                Task toDelete = (Task) filteredList.get(0);
                deleteSingleTask(toDelete);
                return new CommandResult(MESSAGE_DELETE_TASK_SUCCESS);
            }
            default:
                return new CommandResult("Error in single deletion.");
        }
    }

    @SuppressWarnings("unchecked")
    private CommandResult executeMultipleDelete(ArrayList<?> filteredList) {
        ArrayList<Integer> toDeleteIndices = Executor.getIndices();
        DataType dataType = Executor.getDataType();
        switch (dataType) {
            case MODULE: {
                deleteMultipleModules((ArrayList<Module>) filteredList, toDeleteIndices);
                return new CommandResult(MESSAGE_DELETE_MODULE_SUCCESS);
            }
            case CATEGORY: {
                deleteMultipleCategories((ArrayList<Category>) filteredList, toDeleteIndices);
                return new CommandResult(MESSAGE_DELETE_CATEGORY_SUCCESS);
            }
            case TASK: {
                deleteMultipleTasks((ArrayList<Task>) filteredList, toDeleteIndices);
                return new CommandResult(MESSAGE_DELETE_TASK_SUCCESS);
            }
            default:
                return new CommandResult("Error in multiple deletion.");
        }
    }

    @Override
    public CommandResult execute() {
        switch (confirmationStatus) {
        case CONFIRM:
            Executor.terminatePrompt();
            ArrayList<?> filteredList = Executor.getFilteredList();
            if (filteredList.size() == 1) {
                return executeSingleDelete(filteredList);
            } else {
                return executeMultipleDelete(filteredList);
            }
        case ABORT:
            Executor.terminatePrompt();
            return new CommandResult(MESSAGE_DELETE_ABORTED);
        default:
            return new CommandResult(MESSAGE_PROMPT_FORMAT);
        }
    }
}
