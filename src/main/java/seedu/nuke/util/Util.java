package seedu.nuke.util;

import java.util.ArrayList;

public class Util {

    @SuppressWarnings("unchecked")
    public static <newType, oldType> ArrayList<newType> castArrayList(ArrayList<oldType> list){
        ArrayList<newType> newlyCastedArrayList = new ArrayList<newType>();
        for(oldType listObject : list) {
            newlyCastedArrayList.add((newType)listObject);
        }
        return newlyCastedArrayList;
    }
}
