package selenium.com;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.openqa.selenium.Alert;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Captureクラス
 * @author 7days
 */
public class Capture {

    /** ロガー */
    private static final Logger logger = LoggerFactory.getLogger(Capture.class);

    /** 出力先ディレクトリパス（デフォルト） */
    private static final String OUTPUT_DIR_DEFAULT = "./capture";
    /** ファイル名：拡張子（画像） */
    private static final String IMAGE_EXTENSION = ".jpg";
    /** ファイル名：拡張子（テキスト） */
    private static final String TEXT_EXTENSION = ".txt";

    /** WebDriver */
    private WebDriver driver = null;
    /** ファイル名：接頭語 */
    private String prefix = "";
    /** 出力先ディレクトリ */
    private static Path outputDir = Paths.get(OUTPUT_DIR_DEFAULT);

    /**
     * コンストラクタ
     * @param driver
     */
    public Capture(WebDriver driver) {
        this.driver = driver;
    }

    /**
     * キャプチャ取得（名前自動）
     * @throws IOException
     */
    public void screenShot() throws IOException {

        // 自動ファイル名
        String fileNm = prefix + getTime() + IMAGE_EXTENSION;
        Path path = outputDir.resolve(fileNm);

        screenShot(path);
    }

    /**
     * キャプチャ取得
     * @param path
     * @throws IOException
     */
    public void screenShot(Path path) throws IOException {

        // if (driver instanceof HtmlUnitDriver) {
        // return;
        // } else if (driver instanceof RemoteWebDriver) {
        // driver = new Augmenter().augment(driver);
        // } else {
        // // 何もしない
        // }

        logger.debug("capture : {}", path.toAbsolutePath().normalize().toString());

        // 出力
        TakesScreenshot screen = (TakesScreenshot) driver;
        Files.write(path, screen.getScreenshotAs(OutputType.BYTES));
    }

    /**
     * キャプチャ取得（名前自動）<br>
     * 　　※アラートメッセージ専用
     * @param alert
     * @throws IOException
     */
    public void screenShotAlertMsg(Alert alert) throws IOException {

        // 自動ファイル名
        String fileNm = prefix + getTime() + TEXT_EXTENSION;
        Path path = outputDir.resolve(fileNm);

        logger.debug("capture : {}", path.toAbsolutePath().normalize().toString());

        // 出力
        try (BufferedWriter bw = Files.newBufferedWriter(path, StandardOpenOption.CREATE_NEW)) {
            bw.write(alert.getText());
        }
    }

    /**
     * 出力先ディレクトリ生成
     */
    public void createOutputDir() {
        outputDir.toFile().mkdirs();
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
     * @return
     */
    private static String getTime() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmssSSS"));
    }

}
