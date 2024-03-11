package eyesee;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author DuncanRuns
 */
public class EyeSeeOptions {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final EyeSeeOptions INSTANCE = load();
    public int x = 0;
    public int y = 0;
    public int refreshRate = 60;
    public int hotkey = 86; // https://pastebin.com/raw/Wqi3frfi
    public boolean useToggle = false;
    public int disappearAfter = 3000;
    public int viewportWidth = 60;
    public int viewportHeight = 80;
    public double scaleFactor = 5.0;

    private EyeSeeOptions() {
    }

    public static EyeSeeOptions getInstance() {
        return INSTANCE;
    }

    private static EyeSeeOptions load() {
        try {
            return fromString(eyesee.FileUtil.readString(getOptionsPath()));
        } catch (Exception ignored) {
            return new EyeSeeOptions();
        }
    }

    private static EyeSeeOptions fromString(String jsonString) {
        return GSON.fromJson(jsonString, EyeSeeOptions.class);
    }

    public static Path getOptionsPath() {
        return Paths.get(System.getProperty("user.home")).resolve(".EyeSee").resolve("options.json");
    }

    private static void ensureEyeSeeDir() {
        // Special care is needed to make a .EyeSee folder for some reason...
        // Using Files.createDirectories on a path.getParent() would create .EyeSee as a file for some reason.
        new File((System.getProperty("user.home") + "/.EyeSee/").replace("\\", "/").replace("//", "/")).mkdirs();
    }

    public void save() {
        ensureEyeSeeDir();
        try {
            FileUtil.writeString(getOptionsPath(), getJsonString());
        } catch (Exception ignored) {
            System.out.println("Error saving D:");
        }
    }

    public String getJsonString() {
        return GSON.toJson(this);
    }

    public int displayWidth() {
        return (int) (viewportWidth * scaleFactor);
    }

    public int displayHeight() {
        return (int) (viewportHeight * scaleFactor);
    }
}
