package page;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GeneratePageObject {

    /** Logger */
    private static Logger logger = LoggerFactory.getLogger(GeneratePageObject.class);

    public static void main(String[] args) {

        try {
            new GeneratePageObject().execute();
        } catch (Exception e) {
            logger.error("system error", e);

        }
    }

    /**
     * メイン処理
     * @throws IOException
     */
    private void execute() throws IOException {

        analyze();

        generate();
    }

    private void generate() throws FileNotFoundException, UnsupportedEncodingException {

        // Velocityの初期化
        Velocity.init(this.getClass().getResource("/velocity.properties").getPath());

        // テンプレートの読込
        Template template = Velocity.getTemplate("pageObject.vm");

        // テーブル単位でマージ・ファイル出力
        // Velocityコンテキストに値を設定
        VelocityContext context = new VelocityContext();
        // context.put("table", tableBean);
        context.put("q", "\""); // ダブルクォーテーションのエスケープ

        // 出力ファイルパスの生成
        Path outputPath = Paths.get("./generate/export/page.java");

        // テンプレートのマージ
        PrintWriter pw = new PrintWriter(outputPath.toFile());
        template.merge(context, pw);

        // フラッシュ
        pw.flush();

        // クローズ
        pw.close();

    }

    private Object analyze() throws IOException {
        String path = "./generate/import/マニフェスト予約・登録.html";

        // HTMLドキュメントの生成
        Document document = Jsoup.parse(new File(path), "UTF-8");

        // text属性オブジェクトの取得
        Elements elements = document.select("input[type=text]");
        for (Element element : elements) {
            logger.debug(element.toString());
        }
        return null;
    }

}
