package seedu.nuke.directory;

import seedu.nuke.data.ModuleManager;

/**
 * The Root of the Directory, i.e. the starting point of the Directory Tree.
 */
public class Root extends Directory {
    public Root() {
        super();
    }

    @Override
    public Directory getParent() {
        return null;
    }

    @Override
    public DirectoryLevel getLevel() {
        return DirectoryLevel.ROOT;
    }

    /**
     * Returns a string containing information about the Root.
     *
     * @return
     *  A string containing the Root information
     */
    @Override
    public String toString() {
        return String.format("Root\nNumber of Modules: %d\n",
            ModuleManager.getModuleList().size());
    }
}
