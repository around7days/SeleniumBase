package selenium.base;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.imageio.ImageIO;

import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;
import ru.yandex.qatools.ashot.shooting.ShootingStrategy;

/**
 * Captureクラス
 * @author 7days
 */
public class SeleniumCapture {

    /** ロガー */
    private static final Logger logger = LoggerFactory.getLogger(SeleniumCapture.class);

    /** プロパティ */
    private static final SeleniumPropertyManager prop = SeleniumPropertyManager.INSTANCE;

    /** ファイル名：拡張子（画像） */
    private static final String IMAGE_EXTENSION = ".jpg";
    /** ファイル名：拡張子（テキスト） */
    private static final String TEXT_EXTENSION = ".txt";
    /** ファイル名：日付フォーマット */
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmssSSS");

    /** 出力先ディレクトリ */
    private static final Path outputDir = Paths.get(prop.getString("capture.dir"), getSysDateTime());

    /** WebDriver */
    private WebDriver driver = null;
    /** ファイル名：接頭語 */
    private String prefix = "";

    /**
     * 出力先ディレクトリの生成
     */
    static {
        if (!outputDir.toFile().exists()) {
            logger.info("create capture dir -> {}", outputDir.toAbsolutePath().normalize());
            outputDir.toFile().mkdirs();
        }
    }

    /**
     * コンストラクタ
     * @param driver
     */
    public SeleniumCapture(WebDriver driver) {
        this.driver = driver;
    }

    /**
     * キャプチャ取得（名前自動）
     * @throws IOException
     */
    public void screenShot() throws IOException {
        // 自動ファイル名
        String fileNm = prefix + getSysDateTime() + IMAGE_EXTENSION;
        Path path = outputDir.resolve(fileNm);

        screenShot(path);
    }

    /**
     * キャプチャ取得
     * @param path
     * @throws IOException
     */
    public void screenShot(Path path) throws IOException {
        logger.debug("capture -> {}", path.toString());

        // 出力
        // TakesScreenshot screen = (TakesScreenshot) driver;
        // Files.write(path, screen.getScreenshotAs(OutputType.BYTES));

        // キャプチャ取得
        ShootingStrategy shootStrategy = ShootingStrategies.viewportPasting(100); // Webページ全体
        Screenshot screen = new AShot().shootingStrategy(shootStrategy).takeScreenshot(driver);

        // 出力
        ImageIO.write(screen.getImage(), "jpeg", path.toFile());
    }

    /**
     * キャプチャ取得（名前自動）<br>
     * ※アラートメッセージ専用
     * @param alert
     * @throws IOException
     */
    public void screenShotAlertMsg(Alert alert) throws IOException {
        // 自動ファイル名
        String fileNm = prefix + getSysDateTime() + TEXT_EXTENSION;
        Path path = outputDir.resolve(fileNm);

        logger.debug("capture -> {}", path.toString());

        // 出力
        try (BufferedWriter bw = Files.newBufferedWriter(path, StandardOpenOption.CREATE_NEW)) {
            bw.write(alert.getText());
        }
    }

    /**
     * ファイル名：接頭語を取得します。
     * @return ファイル名：接頭語
     */
    public String getPrefix() {
        return prefix;
    }

    /**
     * ファイル名：接頭語を設定します。
     * @param prefix ファイル名：接頭語
     */
    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    /**
     * 現在時刻の取得
     * @return dateTime
     */
    private static String getSysDateTime() {
        return LocalDateTime.now().format(dateTimeFormatter);
    }

}
