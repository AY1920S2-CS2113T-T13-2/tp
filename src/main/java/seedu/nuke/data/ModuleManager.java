package seedu.nuke.data;

import seedu.nuke.exception.DuplicateDataException;
import seedu.nuke.exception.ModuleNotFoundException;
import seedu.nuke.data.module.Module;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static seedu.nuke.util.ExceptionMessage.*;

public class ModuleManager implements Iterable<Module> {
    private static ArrayList<Module> modules = new ArrayList<>();

    /**
     * @return all modules
     */
    public static List<Module> getModuleList() {
        return modules;
    }

    //public Module getAModule(String moduleCode) {

    //}

    /**
     * Checks if the list contains an equivalent task as the given description.
     * @param toCheck the task to-check
     * @return true if the task exists
     */
    public static boolean contains(Module toCheck) {
        for (Module p : modules) {
            if (p.isSameModule(toCheck)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Add a module to the module List.
     *
     * @param toAdd the module to add
     */
    public static void add(Module toAdd) throws DuplicateModuleException {
        //check duplicate
        if (modules.contains(toAdd)){
            //display duplicate message
            System.out.println(MESSAGE_DUPLICATE_MODULE);
        } else {
            modules.add(toAdd);
        }
    }

    /**
     * Clears all tasks in list.
     */
    public void clear() {
        modules.clear();
    }

    /**
     * Remove a module to the list.
     * @param toDelete the task to remove
     * @throws ModuleNotFoundException if the to-remove module does not exist
     */
    public static void delete(Module toDelete) throws ModuleNotFoundException {
        boolean isModuleFoundAndDeleted = modules.remove(toDelete);
        if (!isModuleFoundAndDeleted) {
            throw new ModuleNotFoundException("");
        }
    }

    /**
     * Deletes a <b>Module</b> with the specified <code>module code</code> in the <b>Module List</b>.
     *
     * @param moduleCode    The module code of the <b>Module</b> to be deleted
     * @throws ModuleNotFoundException  If the module with the specified module code is not found in the <b>Module List</b>
     * @see Module
     */
    public static Module delete(String moduleCode) throws ModuleNotFoundException {
        for (Module module : modules) {
            if (module.getModuleCode().toUpperCase().equals(moduleCode)) {
                modules.remove(module);
                return module;
            }
        }
        throw new ModuleNotFoundException("");
    }

    @Override
    public Iterator<Module> iterator() {
        return modules.iterator();
    }

    /**
     * @return the next-to-add task index
     */
    public int getNextTaskIndex(){
        return modules.size();
    }

    public static class DuplicateModuleException extends DuplicateDataException {}

    public static Module getModuleWithCode(String moduleCode){
        for (Module module: getModuleList()
             ) {
            if (module.getModuleCode().equals(moduleCode)){
                return module;
            }
        }
        return null;
    }
}
