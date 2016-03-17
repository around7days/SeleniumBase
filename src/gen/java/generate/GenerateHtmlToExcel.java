package generate;

import generate.bean.ItemBean;
import generate.bean.PageBean;
import generate.com.GeneratePropertyManager;
import generate.com.PageConst.FindBy;
import generate.com.PageConst.HtmlTag;
import generate.com.PageConst.ItemAttr;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

import org.apache.poi.ss.usermodel.Cell;
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

        // HTMLファイルパスの取得
        logger.debug("★getFileList");
        List<Path> fileList = getFileList();
        for (Path path : fileList) {
            logger.debug("対象HTMLファイル : {}", path.toString());

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
     * @return
     * @throws IOException
     */
    private List<Path> getFileList() throws IOException {
        // 検索結果の格納List
        List<Path> list = new ArrayList<Path>();

        Path inputFileDir = Paths.get(prop.getString("html.input.file.dir"));
        String inputFileNmRegex = prop.getString("html.input.file.name.regex");

        // 検索処理
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(inputFileDir, inputFileNmRegex)) {
            stream.forEach(list::add);
        }

        return list;
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
        // XXX
        pageBean.setPageNmExcel(path.getFileName().toString() + ".xlsx");

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

        // テンプレートファイルパスの取得
        Path templateFilePath = new File(getClass().getClassLoader()
                                                   .getResource(prop.getString("excel.template.file"))
                                                   .getPath()).toPath();

        // 出力先パスの取得
        Path outPutFilePath = Paths.get(prop.getString("excel.output.file.dir"), pageBean.getPageNmExcel());

        // テンプレートファイルをコピー
        Files.copy(templateFilePath, outPutFilePath, StandardCopyOption.REPLACE_EXISTING);

        // コピーしたテンプレートファイルに値を反映
        try (Workbook workbook = new XSSFWorkbook(outPutFilePath.toString())) {

            /*
             * Mainシートの設定
             */
            {
                // Mainシートの取得
                Sheet sheet = workbook.getSheet(prop.getString("excel.sheet.name.main"));

                // 値反映行を取得
                int targetRow = prop.getInt("excel.main.detail.start.row") - 1;

                // 項目情報単位で設定
                for (int i = 0; i < pageBean.getItemList().size(); i++) {
                    if (i == EXCEL_DETAIL_MAX) {
                        logger.warn("Excel明細上限に達しました");
                        break;
                    }

                    ItemBean itemBean = pageBean.getItemList().get(i);
                    Row row = sheet.getRow(targetRow);

                    // プロパティーキーが長いので省略用
                    final String itemKey = "excel.main.detail.item.";
                    final String implementKey = "excel.main.detail.implement.";

                    setCellValue(row, (itemKey + "name.col"), itemBean.getItem()); // 項目
                    setCellValue(row, (itemKey + "comment.col"), itemBean.getItemComment()); // 項目名
                    setCellValue(row, (itemKey + "type.col"), ""); // 分類
                    setCellValue(row, (itemKey + "findby.col"), itemBean.getFindBy()); // 選択方法
                    setCellValue(row, (itemKey + "findby.val.col"), itemBean.getFindByValue()); // 選択値
                    setCellValue(row, (itemKey + "findby.cnt.col"), ""); // 該当数
                    setCellValue(row, (implementKey + "sendkeys.col"), ""); // 値設定
                    setCellValue(row, (implementKey + "get.value.col"), ""); // 値取得(value)
                    setCellValue(row, (implementKey + "get.text.col"), ""); // 値取得(text)
                    setCellValue(row, (implementKey + "click.col"), ""); // クリック
                    setCellValue(row, (implementKey + "select.index.col"), ""); // 選択(index)
                    setCellValue(row, (implementKey + "select.value.col"), ""); // 選択(value)
                    setCellValue(row, (implementKey + "select.text.col"), ""); // 選択(text)

                    targetRow++;

                }
            }

            /*
             * Headerシートの設定
             */
            // {
            // Sheet sheet = workbook.getSheet(prop.getString("excel.sheet.name.header"));
            //
            // }

            /*
             * 結果出力
             */
            workbook.write(new FileOutputStream(outPutFilePath.toString() + ".xlsx"));
            logger.debug("結果出力 : {}", outPutFilePath.toString());
        }
    }

    /**
     * Cellへの値反映
     * @param row
     * @param propKey
     * @param value
     * @return cell
     */
    private void setCellValue(Row row,
                              String colPropKey,
                              String value) {
        int col = prop.getInt(colPropKey) - 1;
        Cell cell = row.getCell(col);
        // if (value != null) {
        cell.setCellValue(value);
        // } else {
        // cell.setCellValue("");
        // }
    }
}
