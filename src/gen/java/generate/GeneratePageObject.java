package generate;

import generate.com.GeneratePropertyManager;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GeneratePageObject {

    /** Logger */
    private static final Logger logger = LoggerFactory.getLogger(GeneratePageObject.class);

    /** プロパティ */
    private static final GeneratePropertyManager prop = GeneratePropertyManager.INSTANCE;

    /** 暫定定数 */
    private String inputFileNm = "入力フォーム サンプル.html";
    private String outputFileNm = "pageObject.java";

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

        // HTMLファイルパスの取得
        Path path = Paths.get(prop.getString("file.input.dir"), inputFileNm);

        // HTMLページ解析
        PageBean pageBean = analyze(path);

        // テンプレート生成
        generate(pageBean);
    }

    /**
     * HTMLページ解析
     * @param path
     * @return PageBean
     * @throws IOException
     */
    private PageBean analyze(Path path) throws IOException {

        // HTMLドキュメントの生成
        Document document = Jsoup.parse(path.toFile(), prop.getString("file.input.encoding"));

        // PageBeanの生成
        PageBean pageBean = new PageBean();

        // input type="text"項目情報リストの取得
        List<ItemBean> typeTextList = getTypeTextList(document, "input[type=text]");
        pageBean.setTypeTextList(typeTextList);

        // // input type=password属性オブジェクトの取得
        // logger.debug("■input type=password");
        // for (Element element : document.select("input[type=password]")) {
        // logger.debug(element.toString());
        // }
        //
        // // input type=radio属性オブジェクトの取得
        // logger.debug("■input type=radio");
        // for (Element element : document.select("input[type=radio]")) {
        // logger.debug(element.toString());
        // }
        //
        // // input type=checkbox属性オブジェクトの取得
        // logger.debug("■input type=checkbox");
        // for (Element element : document.select("input[type=checkbox]")) {
        // logger.debug(element.toString());
        // }
        //
        // // select属性オブジェクトの取得
        // logger.debug("■select");
        // for (Element element : document.select("select")) {
        // logger.debug(element.toString());
        // }
        //
        // // textarea属性オブジェクトの取得
        // logger.debug("■textarea");
        // for (Element element : document.select("textarea")) {
        // logger.debug(element.toString());
        // }
        //

        // title属性オブジェクトの取得
        logger.debug("■title");
        for (Element element : document.select("title")) {
            logger.debug(element.toString());

            pageBean.setClassNm(element.text());
            pageBean.setTitle(element.text());
        }

        return pageBean;
    }

    /**
     * input type="text"項目情報リストの取得
     * @param document
     * @param cssSelector
     * @return list
     */
    private List<ItemBean> getTypeTextList(Document document,
                                           String cssSelector) {
        List<ItemBean> list = new ArrayList<>();

        // input type=text属性オブジェクトの取得
        logger.debug("■input type=text");
        for (Element element : document.select(cssSelector)) {
            logger.debug(element.toString());

            // 項目情報の生成
            ItemBean itemBean = new ItemBean();

            // 属性情報の設定
            for (Attribute attr : element.attributes()) {
                switch (attr.getKey().toLowerCase()) {
                case "type":
                    itemBean.setAttrType(attr.getValue());
                    break;
                case "id":
                    itemBean.setAttrId(attr.getValue());
                    break;
                case "name":
                    itemBean.setAttrName(attr.getValue());
                    break;
                }
            }

            // 検索値情報の設定
            if (itemBean.getAttrId() != null) {
                itemBean.setFindBy("id");
                itemBean.setItem(itemBean.getAttrId());
                itemBean.setItemUpper(firstCharUpper(itemBean.getAttrId()));
                itemBean.setFindByValue(itemBean.getAttrId());
            } else if (itemBean.getAttrName() != null) {
                itemBean.setFindBy("name");
                itemBean.setItem(firstCharUpper(itemBean.getAttrName()));
                itemBean.setFindByValue(itemBean.getAttrName());
            }

            // リストに追加
            list.add(itemBean);
        }

        return list;
    }

    /**
     * テンプレート生成
     * @param pageBean
     * @throws IOException
     */
    private void generate(PageBean pageBean) throws IOException {

        // Velocityの初期化
        Velocity.init(this.getClass().getResource("/" + prop.getString("velocity.property.file")).getPath());

        // テンプレートの読込
        Template template = Velocity.getTemplate(prop.getString("velocity.template.file"));

        // テーブル単位でマージ・ファイル出力
        // Velocityコンテキストに値を設定
        VelocityContext context = new VelocityContext();
        context.put("pageBean", pageBean);
        context.put("q", "\""); // ダブルクォーテーションのエスケープ

        // 出力ファイルパスの生成
        Path outputPath = Paths.get(prop.getString("file.output.dir"), outputFileNm);

        // テンプレートのマージ
        PrintWriter pw = new PrintWriter(outputPath.toFile());
        template.merge(context, pw);

        // フラッシュ
        pw.flush();

        // クローズ
        pw.close();

        // 結果出力
        System.out.println("");
        Files.readAllLines(outputPath).forEach(System.out::println);
    }

    /**
     * 先頭文字大文字か
     * @param value
     * @return value
     */
    private String firstCharUpper(String value) {
        if (value.length() > 1) {
            return String.valueOf(value.charAt(0)).toUpperCase() + value.substring(1, value.length());
        } else {
            return value.toUpperCase();
        }
    }
}
