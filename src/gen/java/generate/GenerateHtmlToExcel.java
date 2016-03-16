package generate;

import generate.bean.ItemBean;
import generate.bean.PageBean;
import generate.com.GeneratePropertyManager;
import generate.com.PageConst.FindBy;
import generate.com.PageConst.Item;
import generate.com.PageConst.ItemAttr;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GenerateHtmlToExcel {

    /** Logger */
    private static final Logger logger = LoggerFactory.getLogger(GenerateHtmlToExcel.class);

    /** プロパティ */
    private static final GeneratePropertyManager prop = GeneratePropertyManager.INSTANCE;

    /** 暫定定数 */
    private static final String inputFileNm = "入力フォーム サンプル.html";
    private static final String outputFileNm = "入力フォーム サンプル.xlsx";

    /**
     * 起動
     * @param args
     */
    public static void main(String[] args) {

        try {
            new GenerateHtmlToExcel().execute();
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

        // anchor属性オブジェクトの取得
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
            } else if (itemBean.getAttrName() != null) {
                itemBean.setFindBy(FindBy.name.name());
                itemBean.setFindByValue(itemBean.getAttrName());
            } else if (itemBean.getAttrValue() != null) {
                itemBean.setFindBy(FindBy.css.name());
                itemBean.setFindByValue(itemBean.getAttrValue());
            } else if (itemBean.getText() != null) {
                itemBean.setFindBy(FindBy.partialLinkText.name());
                itemBean.setFindByValue(itemBean.getText());
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

        int rows = 100;
        String fileName = "C:/Users/panasonic/Desktop/output.xlsx";

        try (Workbook workbook = new XSSFWorkbook()) {

            Sheet sheet = workbook.createSheet();
            for (int i = 0; i < rows; i++) {
                Row row = sheet.createRow(i);

                // int x = 0;
                // row.createCell(x).setCellValue("000");
                // row.getCell(x).setCellStyle(cellStyle1);
                // x++;
                // row.createCell(x).setCellValue("2016/03/11 09:53:55");
                // row.getCell(x).setCellStyle(cellStyle1);
                // x++;
                // row.createCell(x).setCellValue("foo\nbar-");
                // row.getCell(x).setCellStyle(cellStyle2);
            }

            workbook.write(new FileOutputStream(fileName));
        }

        // // Velocityの初期化
        // Velocity.init(this.getClass().getResource("/" + prop.getString("velocity.property.file")).getPath());
        //
        // // テンプレートの読込
        // Template template = Velocity.getTemplate(prop.getString("velocity.template.file"));
        //
        // // テーブル単位でマージ・ファイル出力
        // // Velocityコンテキストに値を設定
        // VelocityContext context = new VelocityContext();
        // context.put("pageBean", pageBean);
        // context.put("q", "\""); // ダブルクォーテーションのエスケープ
        //
        // // 出力ファイルパスの生成
        // Path outputPath = Paths.get(prop.getString("file.output.dir"), outputFileNm);
        //
        // // テンプレートのマージ
        // PrintWriter pw = new PrintWriter(outputPath.toFile());
        // template.merge(context, pw);
        //
        // // フラッシュ
        // pw.flush();
        //
        // // クローズ
        // pw.close();
        //
        // // // 結果出力
        // // System.out.println("");
        // // Files.readAllLines(outputPath).forEach(System.out::println);
    }
}
