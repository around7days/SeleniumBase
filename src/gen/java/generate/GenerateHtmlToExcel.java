package generate;

import generate.bean.ItemBean;
import generate.bean.PageBean;
import generate.com.GeneratePropertyManager;
import generate.com.GenerateUtils;
import generate.com.PageConst.FindBy;
import generate.com.PageConst.HtmlTag;
import generate.com.PageConst.ItemAttr;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellReference;
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

    /** プロパティ（generate） */
    private static final GeneratePropertyManager prop = GeneratePropertyManager.INSTANCE;

    /** Excel明細上限 */
    private static final int EXCEL_DETAIL_MAX = 100;

    /** 読み込むHTMLタグ情報 */
    private static StringJoiner htmlTags = new StringJoiner(",");
    static {
        for (HtmlTag htmltag : HtmlTag.values()) {
            htmlTags.add(htmltag.name());
        }
    }

    /**
     * 起動
     * @param args
     */
    public static void main(String[] args) {

        logger.error("処理開始---------------------------------------------------------------------------------------");
        try {
            new GenerateHtmlToExcel().execute();
        } catch (Exception e) {
            logger.error("system error", e);
        }
        logger.error("処理完了---------------------------------------------------------------------------------------");
    }

    /**
     * メイン処理
     * @throws IOException
     */
    private void execute() throws IOException {

        // HTMLファイルパス一覧の取得
        logger.debug("★getFileList");
        List<Path> fileList = getFileList();
        for (Path path : fileList) {
            logger.debug("★対象HTMLファイル : {}", path.toString());

            // HTMLページ解析
            logger.debug("★analyze");
            PageBean pageBean = analyze(path);

            // テンプレート生成
            logger.debug("★generate");
            generate(pageBean);
        }
    }

    /**
     * ファイル一覧の取得
     * @return ファイル一覧
     * @throws IOException
     */
    private List<Path> getFileList() throws IOException {

        Path dir = Paths.get(prop.getString("html.input.file.dir"));
        String fileNmRegex = prop.getString("html.input.file.name.regex");

        return GenerateUtils.getFileList(dir, fileNmRegex);
    }

    /**
     * HTMLページ解析
     * @param path
     * @return PageBean
     * @throws IOException
     */
    private PageBean analyze(Path path) throws IOException {

        // HTMLドキュメントの生成
        Document document = Jsoup.parse(path.toFile(), prop.getString("html.input.file.encoding"));

        // PageBeanの生成
        PageBean pageBean = new PageBean();

        // ページ名称(html)の取得
        pageBean.setPageNmHtml(path.getFileName().toString());

        // ページ名称(Excel)の生成
        pageBean.setPageNmExcel(getFileNm(path.getFileName().toString()) + ".xlsx");

        // タイトルの取得
        pageBean.setTitle(document.title());

        // HTML項目情報リストの取得
        List<ItemBean> itemList = getItemList(document, htmlTags.toString());
        pageBean.setItemList(itemList);

        return pageBean;
    }

    /**
     * 項目情報リストの取得
     * @param document
     * @param cssSelector
     * @return list
     */
    private List<ItemBean> getItemList(Document document,
                                       String cssSelector) {
        List<ItemBean> list = new ArrayList<>();

        // 属性オブジェクトの取得
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
            itemBean.setTag(element.tagName());

            // 値情報を設定
            itemBean.setText(element.text());

            // 項目・検索タイプ情報の設定
            String item = "";
            String findBy = "";
            String findByValue = "";
            if (itemBean.getAttrId() != null) {
                findBy = FindBy.id.name();
                findByValue = itemBean.getAttrId();
                item = itemBean.getAttrId();
            } else if (itemBean.getAttrName() != null) {
                findBy = FindBy.name.name();
                findByValue = itemBean.getAttrName();
                item = itemBean.getAttrName();
            } else if (itemBean.getAttrValue() != null) {
                findBy = FindBy.css.name();
                findByValue = itemBean.getAttrValue();
                item = itemBean.getAttrValue();
            } else if (itemBean.getText() != null) {
                findBy = FindBy.partialLinkText.name();
                findByValue = itemBean.getText();
                item = itemBean.getText();
            }
            itemBean.setItem(item);
            itemBean.setFindBy(findBy);
            itemBean.setFindByVal(findByValue);

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

        // テンプレートファイルパスの取得
        String templateFilePath = getClass().getClassLoader()
                                            .getResource(prop.getString("excel.template.file"))
                                            .getPath();

        // 出力先パスの取得
        Path outPutFilePath = Paths.get(prop.getString("excel.output.file.dir"), pageBean.getPageNmExcel());

        // コピーしたテンプレートファイルに値を反映
        try (FileInputStream fis = new FileInputStream(templateFilePath);
                Workbook workbook = new XSSFWorkbook(fis)) {
            // 先にcloseしておく。
            fis.close();

            // Mainシートの設定
            Sheet mainSheet = workbook.getSheet(prop.getString("excel.sheet.name.main"));
            mainSheetSetting(pageBean, mainSheet);

            // Headerシートの設定
            Sheet headerSheet = workbook.getSheet(prop.getString("excel.sheet.name.header"));
            headerSheetSetting(pageBean, headerSheet);

            // 結果出力
            // 出力
            workbook.write(new FileOutputStream(outPutFilePath.toString()));
            logger.debug("結果出力 : {}", outPutFilePath.toString());
        }
    }

    /**
     * メインシートの設定
     * @param pageBean
     * @param sheet
     */
    private void mainSheetSetting(PageBean pageBean,
                                  Sheet sheet) {
        // HTML名称
        setCellValue(sheet, prop.getString("excel.main.html.name.range"), pageBean.getPageNmHtml());

        // 値反映行を取得
        int targetRow = prop.getInt("excel.main.detail.start.row") - 1;
        // 項目情報単位で設定
        for (int i = 0; i < EXCEL_DETAIL_MAX; i++) {
            // 項目情報の取り出し
            ItemBean itemBean;
            if (pageBean.getItemList().size() > i) {
                itemBean = pageBean.getItemList().get(i);
            } else {
                itemBean = new ItemBean();
            }

            // 出力列情報の取得
            Row row = sheet.getRow(targetRow);

            // プロパティーキーが長いので省略用
            final String key1 = "excel.main.detail.item.";
            final String key2 = "excel.main.detail.implement.";

            // 画面項目
            setCellValue(row, (key1 + "name.col"), itemBean.getItem()); // 項目
            setCellValue(row, (key1 + "comment.col"), itemBean.getItemComment()); // 項目名
            String type = HtmlTag.input.name().equals(itemBean.getTag()) ? itemBean.getAttrType() : itemBean.getTag();
            setCellValue(row, (key1 + "type.col"), type); // 分類
            setCellValue(row, (key1 + "findby.col"), itemBean.getFindBy()); // 選択方法
            setCellValue(row, (key1 + "findby.val.col"), itemBean.getFindByVal()); // 選択値
            setCellValue(row, (key1 + "findby.cnt.col"), "単体"); // 該当数 XXX
            // 実装内容 XXX
            setCellValue(row, (key2 + "sendkeys.col"), ""); // 値設定
            setCellValue(row, (key2 + "get.value.col"), ""); // 値取得(value)
            setCellValue(row, (key2 + "get.text.col"), ""); // 値取得(text)
            setCellValue(row, (key2 + "click.col"), ""); // クリック
            setCellValue(row, (key2 + "select.index.col"), ""); // 選択(index)
            setCellValue(row, (key2 + "select.value.col"), ""); // 選択(value)
            setCellValue(row, (key2 + "select.text.col"), ""); // 選択(text)

            targetRow++;
        }

        if (pageBean.getItemList().size() > EXCEL_DETAIL_MAX) {
            logger.warn("Excel明細上限は{}件です。", EXCEL_DETAIL_MAX);
        }
    }

    /**
     * ヘッダーシートの設定
     * @param pageBean
     * @param sheet
     */
    private void headerSheetSetting(PageBean pageBean,
                                    Sheet sheet) {
    }

    /**
     * Cellへの値反映
     * @param sheet
     * @param range
     * @param value
     */
    private void setCellValue(Sheet sheet,
                              String range,
                              String value) {
        CellReference cr = new CellReference(range);
        Cell cell = sheet.getRow(cr.getRow()).getCell(cr.getCol());
        cell.setCellValue(value);
    }

    /**
     * Cellへの値反映
     * @param row
     * @param propKey
     * @param value
     */
    private void setCellValue(Row row,
                              String colPropKey,
                              String value) {
        int col = prop.getInt(colPropKey) - 1;
        Cell cell = row.getCell(col);
        cell.setCellValue(value);
    }

    /**
     * ファイル名から拡張子を除いたファイル名を取得<br>
     * @param fileNm
     * @return ファイル名
     */
    private String getFileNm(String fileNm) {
        return fileNm.substring(0, fileNm.lastIndexOf("."));
    }
}
