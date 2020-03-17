package seedu.nuke.data.task;

import seedu.nuke.data.file.TaskFileList;
import seedu.nuke.exception.DuplicateDataException;

import java.util.ArrayList;

public class TaskList {
    ArrayList<Task> taskList;

    public TaskList() {
        taskList = new ArrayList<>();
    }

    public ArrayList<Task> getTaskList() {
        return taskList;
    }

    public Task getTask(String description) throws TaskNotFoundException {
        for (Task task : taskList) {
            if (task.getDescription().equals(description)) {
                return task;
            }
        }
        throw new TaskNotFoundException();
    }

    /**
     * Checks for duplicates of the specified task in the Task List.
     * @param toCheck   The task to check
     * @return <code>TRUE</code> if there exists a duplicate, and <code>FALSE</code> otherwise
     */
    private boolean contains(Task toCheck) {
        for (Task task : taskList) {
            if (task.isSameTask(toCheck)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Add a task to the task List.
     *
     * @param toAdd the task to add
     */
    public void add(Task toAdd) throws DuplicateTaskException {
        if (contains(toAdd)) {
            throw new DuplicateTaskException();
        } else {
            taskList.add(toAdd);
        }
    }

    public void delete(int index) {
        taskList.remove(index);
    }

    public void delete(ArrayList<Task> tasks, ArrayList<Integer> toDeleteIndices) {
        for (int index : toDeleteIndices) {
            Task toDelete = tasks.get(index);
            for (Task task : taskList) {
                if (task == toDelete) {
                    delete(task);
                    break;
                }
            }
        }
    }

    public boolean delete(Task toDelete) {
        return taskList.remove(toDelete);
    }

    /**
     * Deletes a <b>Task</b> with the specified <code>description/code> from the <b>Task List</b>.
     *
     * @param description The description of the <b>Task</b> to be deleted
     * @throws TaskNotFoundException  If the task with the specified description is not found in the <b>Task List</b>
     * @return  The deleted task
     * @see Task
     */
    public Task delete(String description) throws TaskNotFoundException {
        Task toDelete = getTask(description);
        taskList.remove(toDelete);
        return toDelete;
    }

    public TaskFileList retrieve(String description) throws TaskNotFoundException {
        return getTask(description).getFiles();
    }

    public ArrayList<Task> filter(String taskKeyword) {
        ArrayList<Task> filteredTaskList = new ArrayList<>();
        for (Task category : taskList) {
            if (category.getDescription().toLowerCase().contains(taskKeyword.toLowerCase())) {
                filteredTaskList.add(category);
            }
        }
        return filteredTaskList;
    }

    public ArrayList<Task> filterExact(String taskKeyword) {
        // Returns all tasks in the Task List if no keyword is provided.
        if (taskKeyword.isEmpty()) {
            return this.getTaskList();
        }

        ArrayList<Task> filteredTaskList = new ArrayList<>();
        for (Task category : taskList) {
            if (category.getDescription().toLowerCase().equals(taskKeyword.toLowerCase())) {
                filteredTaskList.add(category);
            }
        }
        return filteredTaskList;
    }

    public static class TaskNotFoundException extends Exception {}
    public static class DuplicateTaskException extends DuplicateDataException {}
}
