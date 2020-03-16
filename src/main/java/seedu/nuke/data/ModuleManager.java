package seedu.nuke.data;

import seedu.nuke.exception.DuplicateDataException;
import seedu.nuke.exception.ModuleNotProvidedException;
import seedu.nuke.exception.ModuleNotFoundException;
import seedu.nuke.module.Module;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * a manager that manages all modules.
 * have an arraylist that contains all modules
 * deals with operations that are related to modules
 */
public class ModuleManager implements Iterable<Module> {
    private ArrayList<Module> modules = new ArrayList<>();
    private HashMap<String, String> modulesMap;

    public ModuleManager(HashMap<String, String> modulesMap) {
        this.modulesMap = modulesMap;
    }

    public void setModules(ArrayList<Module> modules) {
        this.modules = modules;
    }

    /**
     * method to return all the modules.
     * @return all modules
     */
    public ArrayList<Module> getModuleList() {
        return modules;
    }
    //public Module getAModule(String moduleCode) {

    //}

    /**
     * Checks if the list contains an equivalent task as the given description.
     * @param moduleCode the module code to check if provided by NUS currently
     * @return true if NUS is providing the module currently
     */
    public boolean contains(String moduleCode) {
        for (Module p : modules) {
            if (p.getModuleCode().equals(moduleCode)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Add a module to the module List.
     *
     * @param moduleCode the module code of the module to add
     */
    public void add(String moduleCode) throws DuplicateModuleException, ModuleNotProvidedException {
        //check duplicate
        if (modules.contains(moduleCode)) {
            throw new DuplicateModuleException();
        } else if (!modulesMap.containsKey(moduleCode)) {
            throw new ModuleNotProvidedException();
        } else {
            Module toAdd = new Module(moduleCode, modulesMap.get(moduleCode), null);
            modules.add(toAdd);
        }
    }

    /**
     * Clears all tasks in list.
     */
    public void clear() {
        modules.clear();
    }

    public Module getLastAddedModule() {
        return modules.get(modules.size() - 1);
    }

    /**
     * Remove a module to the list.
     * @param toDelete the task to remove
     * @throws ModuleNotFoundException if the to-remove module does not exist
     */
    public void delete(Module toDelete) throws ModuleNotFoundException {
        boolean isModuleFoundAndDeleted = modules.remove(toDelete);
        if (!isModuleFoundAndDeleted) {
            throw new ModuleNotFoundException("");
        }
    }

    /**
     * Deletes a <b>Module</b> with <code>module code</code> in the <b>Module List</b>.
     *
     * @param moduleCode    The module code of the <b>Module</b> to be deleted
     * @throws ModuleNotFoundException
     * If the module with the specified module code is not found in the <b>Module List</b>
     * @see Module
     */
    public Module delete(String moduleCode) throws ModuleNotFoundException {
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
     * get the index of new-to-add task.
     * @return the next-to-add task index
     */
    public int getNextTaskIndex() {
        return modules.size();
    }

    public class DuplicateModuleException extends DuplicateDataException {
    }

    /**
     * get a module object according to moduleCode.
     * @param moduleCode the moduleCode of the module
     * @return a module object that has the moduleCode
     */
    public Module getModuleWithCode(String moduleCode) {
        for (Module module: modules
             ) {
            if (module.getModuleCode().equals(moduleCode)) {
                assert module.getModuleCode().equals(moduleCode);
                return module;
            }
        }
        return null;
    }
}
