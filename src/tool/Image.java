package tool;

import java.io.File;

/**
 * @author zihan
 * @create 2022-04-18 18:19
 */

/**
 * Design pattern: Singleton
 * Use of encapsulation
 */
public class Image {
    private File filePath;

    private Image() {}

    private static Image instance = null;

    public static synchronized Image getInstance() {
        if (instance == null) {
            instance = new Image();
        }
        return instance;
    }

    public File getFilePath() {
        return filePath;
    }

    public void setFilePath(File filePath) {
        this.filePath = filePath;
    }

}
