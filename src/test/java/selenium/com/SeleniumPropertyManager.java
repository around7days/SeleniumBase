package selenium.com;

import java.util.ResourceBundle;
import java.util.TreeSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * PropertyManagerクラス<br>
 * （シングルトン）
 * @author 7days
 */
public enum SeleniumPropertyManager {
    INSTANCE;

    /** Logger */
    private static final Logger logger = LoggerFactory.getLogger(SeleniumPropertyManager.class);

    /** プロパティ名(selenium.properties) */
    private static final String PROPERTY_NM = "selenium";

    /** プロパティ */
    private static final ResourceBundle rb = ResourceBundle.getBundle(PROPERTY_NM);

    /** プロパティ一覧出力（debug用） */
    static {
        TreeSet<String> keys = new TreeSet<String>(rb.keySet());
        for (String key : keys) {
            String val = String.format("%-30s", key) + " : " + rb.getString(key);
            logger.debug(val);
        }
    }

    /**
     * プロパティから値を取得
     * @param key
     * @return keyに対応する値
     */
    public String getString(String key) {
        if (!rb.containsKey(key)) {
            logger.warn("not contains key : {}", key);
            return "";
        }
        return rb.getString(key);
    }
}
