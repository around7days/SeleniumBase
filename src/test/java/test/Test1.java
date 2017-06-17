package test;

import static org.junit.Assert.*;

import org.junit.Test;
import org.openqa.selenium.By;

import selenium.base.AbstractSeleniumTest;

public class Test1 extends AbstractSeleniumTest {

    @Test
    public void テスト() throws InterruptedException {
        // 画面表示
        helper.url("https://www.yahoo.co.jp/");

        // 検索条件入力
        helper.find(By.name("p")).sendKeys("selenium");

        // 検索ボタンクリック
        helper.find(By.id("srchbtn")).click();

        Thread.sleep(2 * 1000);

        assertTrue(true);
    }

}
