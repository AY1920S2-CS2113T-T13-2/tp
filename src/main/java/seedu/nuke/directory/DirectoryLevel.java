package seedu.nuke.directory;

public enum DirectoryLevel {
    NONE,
    ROOT,
    MODULE,
    CATEGORY,
    TASK,
    FILE {
        @Override
        public DirectoryLevel next() {
            return null;
        }
    };

    public DirectoryLevel next() {
        return values()[ordinal() + 1];
    }
}
