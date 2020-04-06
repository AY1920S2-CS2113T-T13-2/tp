package seedu.nuke.data.storage;

import com.alibaba.fastjson.JSON;
import seedu.nuke.data.ModuleManager;
import seedu.nuke.directory.Module;
import seedu.nuke.directory.TaskFile;
import seedu.nuke.exception.CorruptedFileException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static seedu.nuke.data.storage.StoragePath.TASK_FILE_DIRECTORY_PATH;

public class StorageManager {
    private static boolean isToSave = false;

    private String dataFileName;

    /**
     * Checks whether to save the list.
     *
     * @return
     *  The indication to save the list
     */
    public static boolean isToSave() {
        return isToSave;
    }

    /**
     * Indicates to the storage manager that a change is made to the list and needs to be saved.
     */
    public static void setIsSave() {
        isToSave = true;
    }

    /**
     * Constructor with name of the data file as argument.
     *
     * @param dataFileName the name of the file in which the data is stored.
     */
    public StorageManager(String dataFileName) {
        this.dataFileName = dataFileName;
    }

    /**
     * load from json data file and return an ArrayList of Module objects.
     *
     * @return an ArrayList of Module objects.
     */
    public ArrayList<Module> load() {
        String jsonStr;
        try {
            jsonStr = loadJsonStringFromFile(dataFileName);
            return (ArrayList<Module>) JSON.parseArray(jsonStr, Module.class);
        } catch (IOException e) {
            System.out.println("Error when reading from " + dataFileName);
            return new ArrayList<>();
        }
    }

    private static String loadJsonStringFromFile(String dataFileName) throws IOException {
        String encoding = "utf8";
        File file = new File(dataFileName);
        Long fileLength = file.length();
        byte[] fileContent = new byte[fileLength.intValue()];
        FileInputStream in = new FileInputStream(file);
        in.read(fileContent);
        in.close();
        try {
            return new String(fileContent, encoding);
        } catch (UnsupportedEncodingException e) {
            System.err.println("The OS does not support " + encoding);
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Save the current ArrayList of Module objects into json file.
     */
    public void save() {
        String jsonStr = JSON.toJSONString(ModuleManager.getModuleList());
        try {
            FileWriter fw = new FileWriter(dataFileName);
            fw.write(jsonStr);
            fw.flush();
            fw.close();
        } catch (IOException e) {
            System.out.println("something wrong when writing to " + dataFileName);
        }
    }

    /**
     * Saves the Module List into a file.
     */
    public void saveList() throws IOException {
        try {
            File saveFile = new File(dataFileName);
            saveFile.getParentFile().mkdirs();
            FileWriter fileWriter = new FileWriter(saveFile);
            fileWriter.write(new Encoder(ModuleManager.getModuleList()).encode());
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            throw new IOException("There was an error when saving the list...");
        }

        isToSave = false;
    }

    /**
     * Loads the Module List from the saved file.
     */
    public void loadList() {
        try {
            FileReader fileReader = new FileReader(dataFileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            ArrayList<Module> moduleList = new Decoder(bufferedReader).decode();
            ModuleManager.setModuleList(moduleList);
            bufferedReader.close();
            fileReader.close();
        } catch (IOException e) {
            ModuleManager.setModuleList(new ArrayList<>());
        } catch (CorruptedFileException | ArrayIndexOutOfBoundsException e) {
            System.out.println("File is corrupted!\n");
            ModuleManager.setModuleList(new ArrayList<>());
        }
    }

    public static String saveModuleToString(ArrayList<Module> moduleList) {
        return JSON.toJSONString(moduleList);
    }

    /**
     * Clean up all extra files from the files folder.
     */
    public void cleanUp() throws IOException {
        // Get all the names of the saved files
        File[] savedFiles = new File(StoragePath.TASK_FILE_DIRECTORY_PATH).listFiles();
        if (savedFiles == null) {
            return;
        }
        ArrayList<String> savedFileNames =
                Stream.of(savedFiles).map(File::getName).collect(Collectors.toCollection(ArrayList::new));

        // Get all supposed files
        final String noKeyword = "";
        ArrayList<TaskFile> files = ModuleManager.filter(noKeyword, noKeyword, noKeyword, noKeyword);

        // Get all files to be deleted
        files.stream().map(TaskFile::getFilePath).forEach(savedFileNames::remove);

        // Delete all extra files
        boolean hasDeleteError = false;
        for (String fileName : savedFileNames) {
            String filePath = String.format("%s/%s",  TASK_FILE_DIRECTORY_PATH, fileName);
            File fileToDelete = new File(filePath);
            if (!fileToDelete.exists()) {
                hasDeleteError = true;
                continue;
            }

            Path filePathToDelete = fileToDelete.toPath();
            Files.walk(filePathToDelete)
                    .sorted(Comparator.reverseOrder())
                    .map(Path::toFile)
                    .forEach(File::delete);

            assert !Files.exists(filePathToDelete) : "Directory still exists";
        }

        if (hasDeleteError) {
            throw new IOException("There was an error deleting some files.");
        }
    }
}
