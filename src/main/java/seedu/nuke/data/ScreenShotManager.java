package seedu.nuke.data;

import java.util.LinkedList;
import java.util.List;

public class ScreenShotManager {
    private List<ScreenShot> screenShotList;

    public ScreenShotManager() {
        screenShotList = new LinkedList<ScreenShot>();
    }

    public ScreenShotManager(List<ScreenShot> screenShotList) {
        this.screenShotList = screenShotList;
    }

    public List<ScreenShot> getScreenShotList() {
        return screenShotList;
    }

    public void setScreenShotList(List<ScreenShot> screenShotList) {
        this.screenShotList = screenShotList;
    }
}
