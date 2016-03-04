package selenium;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import selenium.com.AbstractSeleniumTest;
import selenium.com.Capture;
import selenium.com.DateUtil;
import selenium.com.WebDriverFactory;
import selenium.com.WebDriverFactory.Browser;

public class SeleniumTest extends AbstractSeleniumTest {

    /** ロガー */
    private static final Logger logger = LoggerFactory.getLogger(SeleniumTest.class);

    @BeforeClass
    public static void init() {
        logger.debug("---------- 処理開始 ----------");
    }

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    @AfterClass
    public static void finish() {
        logger.debug("---------- 処理終了 ----------");
    }

    /**
     * マニフェスト登録～２次マニフェスト登録～最終処分報告
     * @throws IOException
     * @throws InterruptedException
     */
    @Test
    public void マニフェスト登録_２次マニフェスト_登録最終処分報告() throws IOException, InterruptedException {

        for (Browser browser : browserList) {
            /* Webブラウザの起動 */
            driver = WebDriverFactory.create(browser);

            No1_1次マニフェスト登録();

            /* Webブラウザの終了 */
            driver.quit();
        }
    }

    /**
     * No1_1次マニフェスト登録
     * @throws IOException
     * @throws InterruptedException
     */
    private void No1_1次マニフェスト登録() throws IOException, InterruptedException {

        /*
         * キャプチャ設定
         */
        Capture capture = new Capture(driver);
        capture.setPrefix("No1_1次マニフェスト登録");

        /*
         * 初期表示（ログイン画面）
         */
        driver.get("http://pisetmdbdv01.mew.co.jp/pb_demo/ActionServlet");
        capture.screenShot();

        assertEquals(driver.getTitle(), "排出物遵法管理システム");

        /*
         * ログイン画面 ------------------------------------------------
         */
        // ID・パスワードの設定
        $(By.name("userid")).sendKeys("adminhj");
        $(By.name("password")).sendKeys("adminhj");

        capture.screenShot();

        // ログインボタンクリック
        $(By.name("btnname")).click();

        /*
         * メニュー画面 ------------------------------------------------
         */
        capture.screenShot();
        assertEquals(driver.getTitle(), "排出物遵法管理システム");

        // メインフレームに移動
        // TODO 名前が分からんから定数で・・・
        driver.switchTo().frame(4);

        // マニフェスト予約・登録アンカークリック
        $(By.partialLinkText("マニフェスト予約・登録")).click();

        /*
         * マニフェスト予約・登録画面 ------------------------------------------------
         */
        capture.screenShot();
        assertEquals(driver.getTitle(), "マニフェスト予約・登録");

        // ルート名称コンボボックスの選択
        new Select($(By.id("ROUTE_CD"))).selectByVisibleText("動作確認用、廃プラスチック類（ノーマル）");

        capture.screenShot();

        // 新規ボタンクリック
        $buttonVal("新　規").click();

        /*
         * マニフェスト予約・本登録（詳細） ------------------------------------------------
         */
        capture.screenShot();
        assertEquals(driver.getTitle(), "マニフェスト予約・本登録（詳細）");

        // マニフェスト番号の設定
        $(By.name("MFEST_KOFU_NO")).sendKeys("J" + DateUtil.getStringSysDate("MMddHHmmss"));

        capture.screenShot();

        // 添付省略ボタンクリック
        $buttonVal("添付省略").click();

        /*
         * マニフェスト予約・本登録（確認）画面 ------------------------------------------------
         */
        capture.screenShot();
        assertEquals(driver.getTitle(), "マニフェスト予約・本登録（確認）");

        // 登録ボタンクリック
        $buttonVal("登　録").click();

        // アラートダイアログ表示まで待機し、OKを選択
        driver.switchTo().alert().accept();

        Thread.sleep(2 * 1000);

        /*
         * マニフェスト予約・登録画面 ------------------------------------------------
         */
        // アラートダイアログ表示まで待機し、OKを選択
        {
            Alert alert = driver.switchTo().alert();
            capture.screenShotAlertMsg(alert);
            alert.accept();
        }

        capture.screenShot();
    }

}
