package seedu.nuke.gui.util.tablecreator.basicdirectory;

public class BasicModule {
    private Integer id;
    private String moduleCode;
    private String title;

    /**
     * Constructs the basic Module class with only its attributes for creating a Module Table.
     *
     * @param id
     *  The id of the module in the list
     * @param moduleCode
     *  The module code of the module
     * @param title
     *  The title of the module
     */
    public BasicModule(Integer id, String moduleCode, String title) {
        this.id = id;
        this.moduleCode = moduleCode;
        this.title = title;
    }

    /**
     * Returns the id of the module.
     *
     * @return
     *  The id of the module
     */
    public Integer getId() {
        return id;
    }

    /**
     * Returns the module code of the module.
     *
     * @return
     *  The module code of the module
     */
    public String getModuleCode() {
        return moduleCode;
    }

    /**
     * Returns the title of the module.
     *
     * @return
     *  The title of the module
     */
    public String getTitle() {
        return title;
    }
}
