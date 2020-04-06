package seedu.nuke.gui.util.tablecreator.basicdirectory;

public class BasicFile {
    private Integer id;
    private String moduleCode;
    private String categoryName;
    private String taskDescription;
    private String fileName;

    /**
     * Constructs the basic TaskFile class with only its attributes for creating a File Table.
     *
     * @param id
     *  The id of the file in the list
     * @param moduleCode
     *  The module code of the parent module
     * @param categoryName
     *  The name of the parent category
     * @param taskDescription
     *  The description of the parent task
     * @param fileName
     *  The name of the file
     */
    public BasicFile(Integer id, String moduleCode, String categoryName, String taskDescription, String fileName) {
        this.id = id;
        this.moduleCode = moduleCode;
        this.categoryName = categoryName;
        this.taskDescription = taskDescription;
        this.fileName = fileName;
    }

    /**
     * Returns the id of the file.
     *
     * @return
     *  The id of the file
     */
    public Integer getId() {
        return id;
    }

    /**
     * Returns the module code of the parent module.
     *
     * @return
     *  The module code of the parent module
     */
    public String getModuleCode() {
        return moduleCode;
    }

    /**
     * Returns the name of the parent category.
     *
     * @return
     *  The name of the parent category
     */
    public String getCategoryName() {
        return categoryName;
    }

    /**
     * Returns the description of the parent task.
     *
     * @return
     *  The description of the parent task
     */
    public String getTaskDescription() {
        return taskDescription;
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
}
