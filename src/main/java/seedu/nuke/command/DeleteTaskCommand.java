package seedu.nuke.command;

import seedu.nuke.task.Task;

import static seedu.nuke.util.Message.MESSAGE_TASK_ADDED;
import static seedu.nuke.util.Message.MESSAGE_TASK_REMOVED;

public class DeleteTaskCommand extends TaskCommand{

    public static final String COMMAND_WORD = "delt";
    public static final String MESSAGE_USAGE = COMMAND_WORD + " task description "+": Add a task to the module.";

    private final Task taskToDelete;

    public DeleteTaskCommand(Task task) {
        this.taskToDelete = task;
    }

    @Override
    public CommandResult execute() {
        //add the task to the module's task manager
        currentModule.getTaskManager().removeTask(taskToDelete);
        //add the task to the data manager
        dataManager.removeTask(taskToDelete);
        return new CommandResult(MESSAGE_TASK_REMOVED);
    }
}
