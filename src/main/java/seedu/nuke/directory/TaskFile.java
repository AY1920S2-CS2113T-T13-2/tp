package seedu.nuke.directory;

public class TaskFile extends Directory {
    private String fileName;
    private String filePath;
    private String originalFilePath;

    /**
     * Constructs the task file.
     *
     * @param task
     *  The parent task of the task file
     * @param fileName
     *  The name of the file
     * @param filePath
     *  The path to the file
     * @param originalFilePath
     *  The original path to the file
     */
    public TaskFile(Task task, String fileName, String filePath, String originalFilePath) {
        super(task);
        this.fileName = fileName;
        this.filePath = filePath;
        this.originalFilePath = originalFilePath;
    }

    /**
     * Returns the name of the file.
     *
     * @return
     *  The name of the file
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * Returns the path of the file.
     *
     * @return
     *  The path of the file
     */
    public String getFilePath() {
        return filePath;
    }

    /**
     * Returns the original path of the file.
     *
     * @return
     *  The original path of the file
     */
    public String getOriginalFilePath() {
        return originalFilePath;
    }

    @Override
    public Task getParent() {
        return (Task) this.parent;
    }

    @Override
    public DirectoryLevel getLevel() {
        return DirectoryLevel.FILE;
    }

    /**
     * Edit the file name of the task.
     *
     * @param fileName
     *  The file name of the task
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * Edit the file path of the task.
     *
     * @param filePath
     *  The file path of the task
     */
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Checks if one file has the same name as another.
     *
     * @param fileName
     *  The file name to check
     * @return
     *  <code>TRUE</code> if they are the same, and <code>FALSE</code> otherwise
     */
    public boolean isSameFile(String fileName) {
        return this.fileName.equals(fileName);
    }

    /**
     * Returns a string containing the standard File attributes.
     *
     * @return
     *  A string containing the standard File attributes
     */
    @Override
    public String toString() {
        return String.format("File Name: %s\nModule Code: %s\nCategory Name: %s\nTask Description: %s\n"
                + "Original File Path: %s\n",
                fileName, getParent().getParent().getParent().getModuleCode(),
                getParent().getParent().getCategoryName(), getParent().getDescription(), originalFilePath);
    }
}
