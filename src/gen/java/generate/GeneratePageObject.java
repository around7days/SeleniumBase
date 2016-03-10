package generate;

import generate.com.GeneratePropertyManager;

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
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GeneratePageObject {

    /** Logger */
    private static Logger logger = LoggerFactory.getLogger(GeneratePageObject.class);

    /** プロパティ */
    private static GeneratePropertyManager prop = GeneratePropertyManager.INSTANCE;

    /** 暫定定数 */
    private String inputFileNm = "入力フォーム サンプル.html";
    private String outputFileNm = "page.java";

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

    /**
     * @return
     * @throws IOException
     */
    private Object analyze() throws IOException {
        Path path = Paths.get(prop.getString("file.input.dir"), inputFileNm);

        // HTMLドキュメントの生成
        Document document = Jsoup.parse(path.toFile(), prop.getString("file.input.encoding"));

        // input type=text属性オブジェクトの取得
        logger.debug("■input type=text");
        for (Element element : document.select("input[type=text]")) {
            Attributes attrs = element.attributes();
            attrs.forEach(attr -> logger.debug(attr.getKey()));
            logger.debug(element.toString());
        }

        // input type=password属性オブジェクトの取得
        logger.debug("■input type=password");
        for (Element element : document.select("input[type=password]")) {
            logger.debug(element.toString());
        }

        // input type=radio属性オブジェクトの取得
        logger.debug("■input type=radio");
        for (Element element : document.select("input[type=radio]")) {
            logger.debug(element.toString());
        }

        // input type=checkbox属性オブジェクトの取得
        logger.debug("■input type=checkbox");
        for (Element element : document.select("input[type=checkbox]")) {
            logger.debug(element.toString());
        }

        // select属性オブジェクトの取得
        logger.debug("■select");
        for (Element element : document.select("select")) {
            logger.debug(element.toString());
        }

        // textarea属性オブジェクトの取得
        logger.debug("■textarea");
        for (Element element : document.select("textarea")) {
            logger.debug(element.toString());
        }

        // title属性オブジェクトの取得
        logger.debug("■title");
        for (Element element : document.select("title")) {
            logger.debug(element.toString());
        }

        return null;
    }

    /**
     * @throws FileNotFoundException
     * @throws UnsupportedEncodingException
     */
    private void generate() throws FileNotFoundException, UnsupportedEncodingException {

        // Velocityの初期化
        Velocity.init(this.getClass().getResource("/" + prop.getString("velocity.property.file")).getPath());

        // テンプレートの読込
        Template template = Velocity.getTemplate(prop.getString("velocity.template.file"));

        // テーブル単位でマージ・ファイル出力
        // Velocityコンテキストに値を設定
        VelocityContext context = new VelocityContext();
        // context.put("table", tableBean);
        // context.put("q", "\""); // ダブルクォーテーションのエスケープ

        // 出力ファイルパスの生成
        Path outputPath = Paths.get(prop.getString("file.output.dir"), outputFileNm);

        // テンプレートのマージ
        PrintWriter pw = new PrintWriter(outputPath.toFile());
        template.merge(context, pw);

        // フラッシュ
        pw.flush();

        // クローズ
        pw.close();
    }

}
