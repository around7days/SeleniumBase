package selenium.com;

import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * PropertyManagerクラス<br>
 * （シングルトン）
 */
public enum SeleniumPropertyManager {
    INSTANCE;

    /** Logger */
    @SuppressWarnings("unused")
    private static final Logger logger = LoggerFactory.getLogger(SeleniumPropertyManager.class);

    /** プロパティ名(selenium.properties) */
    private static final String PROPERTY_NM = "selenium";

    /** プロパティ */
    private static final ResourceBundle rb = ResourceBundle.getBundle(PROPERTY_NM);

    /**
     * プロパティから値を取得
     * @param key
     * @return keyに対応する値
     */
    public String getString(String key) {
        return rb.getString(key);
    }
}
