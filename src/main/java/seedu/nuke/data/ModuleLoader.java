package seedu.nuke.data;

import com.alibaba.fastjson.JSON;
import org.apache.commons.io.FileUtils;
import seedu.nuke.data.storage.StoragePath;
import seedu.nuke.util.DummyModule;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

/**
 * This class is to load all the modules that NUS offer, scraping from the nusmods,
 * when users are trying to enrol into modules, the module code must
 * appear in the list of the list which load method return, or else fail.
 */
public class ModuleLoader {

    /**
     * load data from a jason file that contains all information about all NUS existing modules.
     *
     * @param dataFileName name of the jason file
     * @return a HashMapof String to String, which is a map that map all modules with its information
     * @throws FileNotFoundException when cannot find the jason file
     */
    public static HashMap<String, String> load(String dataFileName) {
        String jsonStr;
        jsonStr = loadJsonStringFromFile(dataFileName);
        List<DummyModule> moduleList = JSON.parseArray(jsonStr, DummyModule.class);// extractModules(jsonStr);
        if (moduleList == null) {
            return new HashMap<String, String>();
        }
        HashMap<String, String> modulesMap = convertToHashMap(moduleList);
        return modulesMap;
    }

    private static HashMap<String, String> convertToHashMap(List<DummyModule> moduleList) {
        HashMap<String, String> modulesMap = new HashMap<>();
        for (DummyModule module : moduleList) {
            modulesMap.put(module.getModuleCode(), module.getTitle());
        }
        return modulesMap;
    }

    private static String loadJsonStringFromFile(String dataFileName) {
        String encoding = "utf8";
        File file = new File(dataFileName);
        Long fileLength = file.length();
        byte[] fileContent = new byte[fileLength.intValue()];
        try {
            FileInputStream in = new FileInputStream(file);
            in.read(fileContent);
            in.close();
        } catch (FileNotFoundException e) {
            File folder = new File(StoragePath.FILE_BASE_PATH);
            if (!folder.exists() && !folder.isDirectory()) {
                folder.mkdirs();
            }
            return openFile(getModuleListUrlFromNusmods());
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            return new String(fileContent, encoding);
        } catch (UnsupportedEncodingException e) {
            System.err.println("The OS does not support " + encoding);
            e.printStackTrace();
            return null;
        }
    }

    private static String openFile(String filePath) {
        int httpResult; // the status from the server response
        String content = "";
        try {
            URL url = new URL(filePath); // create URL
            URLConnection urlConn = url.openConnection(); // try to connect and get the status code
            try {
                urlConn.connect();
            } catch (UnknownHostException e) {
                return "";
            }
            HttpURLConnection httpConn = (HttpURLConnection) urlConn;
            httpResult = httpConn.getResponseCode();
            if (httpResult != HttpURLConnection.HTTP_OK) {
                System.out.print("cannot connect!");
            } else {
                int fileSize = urlConn.getContentLength(); // get the length of the data
                InputStreamReader isReader = new InputStreamReader(urlConn.getInputStream(), "UTF-8");
                BufferedReader reader = new BufferedReader(isReader);
                StringBuffer buffer = new StringBuffer();
                String line; // to save the content of every line
                line = reader.readLine(); // read the first line
                while (line != null) { // if line is empty, means finish reading
                    buffer.append(line); // append to the buffer
                    buffer.append(" "); // add new line
                    line = reader.readLine(); // read the next line
                }
                content = buffer.toString();

                saveModuleListToDisk();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }

    private static void saveModuleListToDisk() throws IOException {
        URL httpUrl = new URL(getModuleListUrlFromNusmods());
        FileUtils.copyURLToFile(httpUrl, new File(StoragePath.NUS_MODULE_LIST_PATH));
    }

    private static String getModuleListUrlFromNusmods() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        String urlFmt = "https://api.nusmods.com/v2/%d-%d/moduleList.json";
        String url;
        if (month < 6) {
            url = String.format(urlFmt, year - 1, year);
        } else {
            url = String.format(urlFmt, year, year + 1);
        }
        return url;
    }
}
