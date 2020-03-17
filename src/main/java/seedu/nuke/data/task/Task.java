package seedu.nuke.data.task;

import seedu.nuke.data.category.Category;
import seedu.nuke.data.file.TaskFile;
import seedu.nuke.data.file.TaskFileList;
import seedu.nuke.format.DateTime;

import static seedu.nuke.common.Constants.NO_ICON;
import static seedu.nuke.common.Constants.YES_ICON;

public class Task {
    private Category parentCategory;
    private String description;
    private boolean isDone;
    private DateTime deadline;
    private int priority;
    private TaskFileList files;

    public Task(Category parentCategory, String description, DateTime deadline, int priority) {
        this.parentCategory = parentCategory;
        this.description = description;
        this.isDone = false;
        this.deadline = deadline;
        this.priority = priority;
        this.files = new TaskFileList();
    }

    public Category getParentCategory() {
        return parentCategory;
    }

    public String getDescription() {
        return description;
    }

    public boolean isDone() {
        return isDone;
    }

    public DateTime getDeadline() {
        return deadline;
    }

    public int getPriority() {
        return priority;
    }

    public String getStatusIcon() {
        return (isDone ? YES_ICON : NO_ICON);
    }

    public TaskFileList getFiles() {
        return files;
    }

    public void setDeadline(DateTime deadline) {
        this.deadline = deadline;
    }

    public void addFile(String fileName, String filePath) {
        files.add(new TaskFile(fileName, filePath));
    }

    /**
     * Checks if one task has the same description as another.
     *
     * @param task  The task to check
     * @return  <code>TRUE</code> if they are the same, and <code>FALSE</code> otherwise
     */
    public boolean isSameTask(Task task) {
        return this.description.equals(task.description);
    }

    @Override
    public String toString() {
        StringBuilder printOut = new StringBuilder(getStatusIcon() + " " + getDescription() + " by " + getDeadline());
        printOut.append(files);
        return printOut.toString();
    }
}
