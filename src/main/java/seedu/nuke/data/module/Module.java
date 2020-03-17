package seedu.nuke.data.module;

import seedu.nuke.data.category.CategoryList;

public class Module {
    private String moduleCode;
    private String title;
    private String description;
    private CategoryList categories;

    public Module(String moduleCode, String title, String description) {
        this.moduleCode = moduleCode.toUpperCase();
        this.title = title;
        this.description = description;
        this.categories = new CategoryList(this);
    }

    public String getModuleCode() {
        return moduleCode;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public CategoryList getCategories() {
        return categories;
    }

    public boolean isSameModule(Module module) {
        return this.moduleCode.equals(module.moduleCode.toUpperCase());
    }
}
