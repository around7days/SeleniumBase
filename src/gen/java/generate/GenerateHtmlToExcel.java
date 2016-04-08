package generate;

import static generate.com.PageConst.*;
import generate.bean.ItemBean;
import generate.bean.PageBean;
import generate.com.GeneratePropertyManager;
import generate.com.GenerateUtils;
import generate.com.PageConst.FindBy;
import generate.com.PageConst.HtmlTag;
import generate.com.PageConst.ItemAttr;
import generate.com.PageConst.ItemAttrType;

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

        // ページ名称の取得
        pageBean.setName(GenerateUtils.getFileNm(path));

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
        List<ItemBean> itemBeanList = new ArrayList<>();

        // 属性オブジェクトの取得
        for (Element element : document.select(cssSelector)) {
            logger.debug(element.toString());

            // 項目情報の生成
            ItemBean itemBean = new ItemBean();

            // HTMLを設定
            itemBean.setHtml(element.toString());

            // タグを設定
            itemBean.setTag(element.tagName());

            // type, id, name, valueの設定
            for (Attribute attr : element.attributes()) {
                ItemAttr itemAttr = ItemAttr.getEnum(attr.getKey());
                if (itemAttr == null) {
                    continue;
                }

                switch (itemAttr) {
                case type:
                    itemBean.setType(attr.getValue());
                    break;
                case id:
                    itemBean.setId(attr.getValue());
                    break;
                case name:
                    itemBean.setName(attr.getValue());
                    break;
                case value:
                    itemBean.setValue(attr.getValue());
                    break;
                }
            }

            // textの設定
            if (itemBean.getValue() == null) {
                String text = !element.text().isEmpty() ? element.text() : element.outerHtml();
                itemBean.setText(text);
            }

            // 項目情報リストに含まれているかチェック
            ItemBean existsItemBean = existsItemBean(itemBeanList, itemBean);
            if (existsItemBean != null) {
                // 項目情報リストに含まれている場合
                // 取り出した項目情報に対して、取得数を「Multi」に変更して終了
                existsItemBean.setFindByCnt(CNT_MULTI);
                continue;
            }

            // 項目情報リストに含まれていない場合
            // 新規でリストに追加
            itemBeanList.add(itemBean);
        }

        return itemBeanList;
    }

    /**
     * 項目情報の存在チェック
     * @param itemBeanList
     * @param itemBean
     * @return [存在する:項目情報 存在しない:null]
     */
    private ItemBean existsItemBean(List<ItemBean> itemBeanList,
                                    ItemBean itemBean) {
        for (ItemBean tempBean : itemBeanList) {
            if (itemBean.toString().equals(tempBean.toString())) {
                return tempBean;
            }
        }
        return null;
    }

    /**
     * テンプレート生成
     * @param pageBean
     * @throws IOException
     */
    private void generate(PageBean pageBean) throws IOException {

        // テンプレートファイルパスの取得
        Path templateFilePath = GenerateUtils.getPath(prop.getString("excel.template.file"));

        // 出力先パスの取得
        String fileNm = pageBean.getName() + ".xlsx";
        Path outPutFilePath = Paths.get(prop.getString("excel.output.file.dir"), fileNm);

        // コピーしたテンプレートファイルに値を反映
        try (FileInputStream fis = new FileInputStream(templateFilePath.toFile());
             Workbook workbook = new XSSFWorkbook(fis)) {
            // 先にcloseしておく(重要)
            fis.close();

            /*
             * Mainシートの設定
             */
            Sheet sheet = workbook.getSheet(prop.getString("excel.sheet.name"));

            // 値反映行を取得
            int targetRow = prop.getInt("excel.start.row") - 1;
            // 項目情報単位で設定
            for (int i = 0; i < EXCEL_DETAIL_MAX; i++) {
                // 項目情報の取り出し
                ItemBean itemBean;
                if (pageBean.getItemList().size() > i) {
                    itemBean = pageBean.getItemList().get(i);
                } else {
                    itemBean = new ItemBean(); // 空情報でExcelの項目を上書きする為に使用
                }

                // FindBy情報を生成
                makeFindByInfo(itemBean);
                // Operate情報を生成
                makeOperateInfo(itemBean);

                // 出力列情報の取得
                Row row = sheet.getRow(targetRow);

                // HTML画面項目
                setCellValue(row, "excel.item.col", itemBean.getItem()); // 項目名
                setCellValue(row, "excel.item.tag.col", itemBean.getTag()); // tag
                setCellValue(row, "excel.item.input.type.col", itemBean.getType()); // type
                setCellValue(row, "excel.item.id.col", itemBean.getId()); // id
                setCellValue(row, "excel.item.name.col", itemBean.getName()); // name
                setCellValue(row, "excel.item.value.col", itemBean.getValue()); // value
                setCellValue(row, "excel.item.text.col", itemBean.getText()); // text
                // FindBy
                setCellValue(row, "excel.item.findby.col", itemBean.getFindBy()); // 選択方法
                setCellValue(row, "excel.item.findby.val.col", itemBean.getFindByVal()); // 選択値
                setCellValue(row, "excel.item.findby.cnt.col", itemBean.getFindByCnt()); // 取得数
                // Java実装
                setCellValue(row, "excel.java.sendkeys.col", itemBean.getOperateSendKeys()); // 値設定
                setCellValue(row, "excel.java.get.value.col", itemBean.getOperateGetValue()); // 値取得(value)
                setCellValue(row, "excel.java.get.text.col", itemBean.getOperateGetText()); // 値取得(text)
                setCellValue(row, "excel.java.click.col", itemBean.getOperateClick()); // クリック
                setCellValue(row, "excel.java.select.index.col", itemBean.getOperateSelectIndex()); // 選択(index)
                setCellValue(row, "excel.java.select.value.col", itemBean.getOperateSelectValue()); // 選択(value)
                setCellValue(row, "excel.java.select.text.col", itemBean.getOperateSelectText()); // 選択(text)
                setCellValue(row, "excel.java.frame.change.col", itemBean.getOperateFrameChange()); // Frame変更

                targetRow++;
            }

            if (pageBean.getItemList().size() > EXCEL_DETAIL_MAX) {
                logger.warn("Excel明細上限は{}件です。", EXCEL_DETAIL_MAX);
            }

            /*
             * 結果出力
             */
            workbook.write(new FileOutputStream(outPutFilePath.toString()));
            logger.debug("結果出力 : {}", outPutFilePath.toString());
        }
    }

    /**
     * FindBy情報を生成
     * @param itemBean
     */
    private void makeFindByInfo(ItemBean itemBean) {
        String findBy = "";
        String findByVal = "";
        if (!GenerateUtils.isEmpty(itemBean.getId())) {
            // id
            findBy = FindBy.id.name();
            findByVal = itemBean.getId();
        } else if (!GenerateUtils.isEmpty(itemBean.getName())) {
            // name
            findBy = FindBy.name.name();
            findByVal = itemBean.getName();
        } else if (!GenerateUtils.isEmpty(itemBean.getValue())) {
            // value
            findBy = FindBy.css.name();
            if (HtmlTag.input == HtmlTag.getEnum(itemBean.getTag())) {
                // inputタグ
                findByVal = "input[type='" + itemBean.getType() + "'][value='" + itemBean.getValue() + "']";
            } else {
                // その他タグ
                findByVal = "input[value='" + itemBean.getValue() + "']";
            }
        } else if (!GenerateUtils.isEmpty(itemBean.getText())) {
            // text
            findBy = FindBy.partialLinkText.name();
            findByVal = itemBean.getText();
        }
        itemBean.setFindBy(findBy);
        itemBean.setFindByVal(findByVal);
    }

    /**
     * Operate情報を生成
     * @param itemBean
     */
    private void makeOperateInfo(ItemBean itemBean) {

        // タグ情報で判断
        HtmlTag tag = HtmlTag.getEnum(itemBean.getTag());
        if (tag != null) {
            switch (tag) {
            case a:
                itemBean.setOperateClick(ON);
                break;
            case select:
                itemBean.setOperateSelectIndex(ON);
                itemBean.setOperateSelectText(ON);
                itemBean.setOperateSelectValue(ON);
                break;
            case textarea:
                itemBean.setOperateSendKeys(ON);
                break;
            case input:
                break;
            case frame:
                itemBean.setOperateFrameChange(ON);
                break;
            }

        }

        // input type情報で判断
        ItemAttrType type = ItemAttrType.getEnum(itemBean.getType());
        if (type != null) {
            switch (type) {
            case checkbox:
            case button:
            case submit:
            case radio:
            case img:
                itemBean.setOperateClick(ON);
                break;
            case text:
            case password:
                itemBean.setOperateSendKeys(ON);
                break;
            case file:
                break;
            }
        }
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

}
