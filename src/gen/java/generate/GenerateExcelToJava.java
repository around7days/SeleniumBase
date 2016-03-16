package generate;

import generate.bean.ItemBean;
import generate.bean.PageBean;
import generate.com.GeneratePropertyManager;
import generate.com.PageConst.FindBy;
import generate.com.PageConst.Item;
import generate.com.PageConst.ItemAttr;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GenerateExcelToJava {

    /** Logger */
    private static final Logger logger = LoggerFactory.getLogger(GenerateExcelToJava.class);

    /** プロパティ */
    private static final GeneratePropertyManager prop = GeneratePropertyManager.INSTANCE;

    /** 暫定定数 */
    private static final String inputFileNm = "入力フォーム サンプル.html";
    private static final String outputFileNm = "SamplePage.java";
    private static final String outputClassNm = "SamplePage";
    private static final String dummyCd = "dummy";

    /**
     * 起動
     * @param args
     */
    public static void main(String[] args) {

        try {
            new GenerateExcelToJava().execute();
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

        // クラス名の設定
        pageBean.setClassNm(outputClassNm);

        // input type="text" or input type="password"項目情報リストの取得
        List<ItemBean> textList = getItemList(document, "input[type=text],input[type=password]", Item.text);
        pageBean.setTextList(textList);

        // input type="radio"属性オブジェクトの取得
        List<ItemBean> radioList = getItemList(document, "input[type=radio]", Item.radio);
        pageBean.setRadioList(radioList);

        // input type="checkbox"属性オブジェクトの取得
        List<ItemBean> checkboxList = getItemList(document, "input[type=checkbox]", Item.checkbox);
        pageBean.setCheckboxList(checkboxList);

        // input type="button"項目情報リストの取得
        List<ItemBean> buttonList = getItemList(document, "input[type=button],input[type=submit]", Item.button);
        pageBean.setButtonList(buttonList);

        // select属性オブジェクトの取得
        List<ItemBean> selectList = getItemList(document, "select", Item.select);
        pageBean.setSelectList(selectList);

        // textarea属性オブジェクトの取得
        List<ItemBean> textareaList = getItemList(document, "textarea", Item.textarea);
        pageBean.setTextareaList(textareaList);

        // a属性オブジェクトの取得
        List<ItemBean> anchorList = getItemList(document, "a", Item.anchor);
        pageBean.setAnchorList(anchorList);

        return pageBean;
    }

    /**
     * 項目情報リストの取得
     * @param document
     * @param cssSelector
     * @param item
     * @return list
     */
    private List<ItemBean> getItemList(Document document,
                                       String cssSelector,
                                       Item item) {
        List<ItemBean> list = new ArrayList<>();

        // 属性オブジェクトの取得
        logger.debug("■{}", cssSelector);
        for (Element element : document.select(cssSelector)) {
            logger.debug(element.toString());

            // 項目情報の生成
            ItemBean itemBean = new ItemBean();

            // 属性情報の設定
            for (Attribute attr : element.attributes()) {
                ItemAttr itemAttr = ItemAttr.getEnum(attr.getKey());
                if (itemAttr == null) {
                    continue;
                }

                switch (itemAttr) {
                case type:
                    itemBean.setAttrType(attr.getValue());
                    break;
                case id:
                    itemBean.setAttrId(attr.getValue());
                    break;
                case name:
                    itemBean.setAttrName(attr.getValue());
                    break;
                case value:
                    itemBean.setAttrValue(attr.getValue());
                    break;
                }
            }

            // タグ情報を設定
            itemBean.setTagName(element.tagName());

            // 値情報を設定
            itemBean.setText(element.text());

            // 検索値情報の設定
            if (itemBean.getAttrId() != null) {
                itemBean.setFindBy(FindBy.id.name());
                itemBean.setFindByValue(itemBean.getAttrId());
                itemBean.setItem(itemBean.getAttrId());
            } else if (itemBean.getAttrName() != null) {
                itemBean.setFindBy(FindBy.name.name());
                itemBean.setFindByValue(itemBean.getAttrName());
                itemBean.setItem(itemBean.getAttrName());
            } else if (itemBean.getAttrValue() != null) {
                itemBean.setFindBy(FindBy.css.name());
                itemBean.setFindByValue(itemBean.getAttrValue());
                itemBean.setItem(dummyCd + new Random().nextInt(100000));
            } else if (itemBean.getText() != null) {
                itemBean.setFindBy(FindBy.partialLinkText.name());
                itemBean.setFindByValue(itemBean.getText());
                itemBean.setItem(dummyCd + new Random().nextInt(100000));
            }

            // 変数名（大文字）の設定
            itemBean.setItemUpper(firstCharUpper(itemBean.getItem()));
            // 変数名（コメント用）の設定
            itemBean.setItemComment(itemBean.getItem() + " " + itemBean.getTagName());
            if (itemBean.getAttrType() != null) {
                itemBean.setItemComment(itemBean.getItemComment() + " type=" + itemBean.getAttrType());
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

        // // 結果出力
        // System.out.println("");
        // Files.readAllLines(outputPath).forEach(System.out::println);
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
