import java.util.Properties;
import java.awt.event.KeyEvent;
import java.io.*;

public class KeySetting {
    private static final String SETTINGS_FILE = "keySetting.properties";
    private static Properties prop = new Properties();

    static {
        loadSettings();
    }

    public static void loadSettings() {
        try (InputStream input = new FileInputStream(SETTINGS_FILE)) {
            prop.load(input);
        } catch (IOException e) {
            // 设置默认键位
            setDefaultKey();
            saveSettings();
        }
    }

    public static void saveSettings() {
        try (OutputStream output = new FileOutputStream(SETTINGS_FILE)) {
            prop.store(output, "Tetris Key Configs");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int getKey(String action) {
        return Integer.parseInt(prop.getProperty(action, "-1"));
    }

    public static void setKey(String action, int keyCode) {
        prop.setProperty(action, String.valueOf(keyCode));
    }

    public static void setDefaultKey() {
        setKey("left", KeyEvent.VK_LEFT);
        setKey("right", KeyEvent.VK_RIGHT);
        setKey("down", KeyEvent.VK_DOWN);
        setKey("rotate", KeyEvent.VK_UP);
        setKey("pause", KeyEvent.VK_SPACE);
        setKey("settings", KeyEvent.VK_S);
    }
}

