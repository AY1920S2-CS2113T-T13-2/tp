package seedu.nuke.command;

import seedu.nuke.data.ModuleManager;
import seedu.nuke.task.Task;
import seedu.nuke.module.Module;

import static seedu.nuke.util.Message.MESSAGE_TASK_ADDED;

public class AddTaskCommand extends TaskCommand{

    public static final String COMMAND_WORD = "addt";
    public static final String MESSAGE_USAGE = COMMAND_WORD + " task description "+": Add a task to the module.";

    private final Task taskToAdd;

    public AddTaskCommand(Task task) {
        this.taskToAdd = task;
    }

    @Override
    public CommandResult execute() {
        //add the task to the module's task manager
        currentModule.getTaskManager().addTask(taskToAdd);
        //add the task to the data manager
        dataManager.addTask(taskToAdd);
        return new CommandResult(MESSAGE_TASK_ADDED);
    }
}
