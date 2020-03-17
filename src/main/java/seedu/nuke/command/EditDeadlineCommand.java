package seedu.nuke.command;

import seedu.nuke.data.task.Task;

/*
    edit the deadline of the task
 */
public class EditDeadlineCommand extends EditTaskCommand {
    public static final String COMMAND_WORD = "editDeadline";
    public static final String MESSAGE_USAGE_1 = COMMAND_WORD + "task description "+": Edit the deadline of a task.";
    public static final String MESSAGE_USAGE_2 = "      Example: " + COMMAND_WORD+ " read a book" + "YYYY-MM-DD hh:mm:ss";
    public static final String MESSAGE_DONE = "  Nice! I've marked this task as done:  %s";

    /**
     * set the deadline
     * @param toEdit full command args string
     */
    public EditDeadlineCommand(String toEdit) {
        super(toEdit);
    }

    @Override
    public CommandResult execute() {
        // todo find the target task
        final Task toEdit = getTargetTask();
        // todo set the description
        toEdit.setDeadline(null);
        return new CommandResult(String.format(MESSAGE_DONE, toEdit.getDescription()));
    }
}
