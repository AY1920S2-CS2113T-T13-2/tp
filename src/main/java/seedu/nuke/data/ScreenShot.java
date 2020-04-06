package seedu.nuke.data;

public class ScreenShot {
    private String encodedSavedList;

    public ScreenShot(String encodedSavedList) {
        this.encodedSavedList = encodedSavedList;
    }

    public String getEncodedSavedList() {
        return encodedSavedList;
    }
}

