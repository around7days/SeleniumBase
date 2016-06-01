package generate.com;

import java.util.ResourceBundle;
import java.util.TreeSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * PropertyManagerクラス<br>
 * （シングルトン）
 * @author 7days
 */
public enum GeneratePropertyManager {
    INSTANCE;

    /** Logger */
    private static final Logger logger = LoggerFactory.getLogger(GeneratePropertyManager.class);

    /** プロパティ名(generate.properties) */
    private static final String PROPERTY_NM = "generate";

    /** プロパティ */
    private static final ResourceBundle rb = ResourceBundle.getBundle(PROPERTY_NM);

    /** プロパティ一覧出力（debug用） */
    static {
        TreeSet<String> keys = new TreeSet<String>(rb.keySet());
        for (String key : keys) {
            String val = String.format("%-35s", key) + " : " + rb.getString(key);
            logger.debug(val);
        }
    }

    /**
     * プロパティから値を取得
     * @param key
     * @return keyに対応する値
     */
    public int getInt(String key) {
        String val = getString(key);
        return Integer.valueOf(val);
    }

    /**
     * プロパティから値を取得
     * @param key
     * @return keyに対応する値
     */
    public String getString(String key) {
        if (!rb.containsKey(key)) {
            logger.warn("not contains key : {}", key);
            return null;
        }
        return rb.getString(key);
    }

}
