package generate;

import static generate.com.PageConst.*;
import generate.bean.ItemBean;
import generate.bean.PageBean;
import generate.com.GeneratePropertyManager;
import generate.com.GenerateUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GenerateExcelToJava {

    /** Logger */
    private static final Logger logger = LoggerFactory.getLogger(GenerateExcelToJava.class);

    /** プロパティ */
    private static final GeneratePropertyManager prop = GeneratePropertyManager.INSTANCE;

    /**
     * 起動
     * @param args
     */
    public static void main(String[] args) {

        logger.error("処理開始---------------------------------------------------------------------------------------");
        try {
            new GenerateExcelToJava().execute();
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

        // Excelファイルパス一覧の取得
        logger.debug("★getFileList");
        List<Path> fileList = getFileList();
        for (Path path : fileList) {
            logger.debug("対象Excelファイル : {}", path.toString());

            // Excelページ解析
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

        Path dir = Paths.get(prop.getString("excel.input.file.dir"));
        String fileNmRegex = prop.getString("excel.input.file.extension");

        return GenerateUtils.getFileList(dir, fileNmRegex);
    }

    /**
     * Excelページ解析
     * @param path
     * @return PageBean
     * @throws IOException
     */
    private PageBean analyze(Path path) throws IOException {

        // PageBeanの生成
        PageBean pageBean = new PageBean();

        // ページ名称の取得
        pageBean.setName(GenerateUtils.getFileNm(path));

        // Excelファイルの読込
        try (FileInputStream fis = new FileInputStream(path.toFile()); Workbook workbook = new XSSFWorkbook(fis)) {
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
                ItemBean itemBean = new ItemBean();

                // 出力列情報の取得
                Row row = sheet.getRow(targetRow);

                // HTML画面項目
                itemBean.setItem(getCellValue(row, "excel.item.col")); // 項目名
                itemBean.setTag(getCellValue(row, "excel.item.tag.col")); // tag
                itemBean.setType(getCellValue(row, "excel.item.input.type.col")); // type
                itemBean.setId(getCellValue(row, "excel.item.id.col")); // id
                itemBean.setName(getCellValue(row, "excel.item.name.col")); // name
                itemBean.setValue(getCellValue(row, "excel.item.value.col")); // value
                itemBean.setText(getCellValue(row, "excel.item.text.col")); // text
                // FindBy
                itemBean.setFindBy(getCellValue(row, "excel.item.findby.col")); // 選択方法
                itemBean.setFindByVal(getCellValue(row, "excel.item.findby.val.col")); // 選択値
                itemBean.setFindByCnt(getCellValue(row, "excel.item.findby.cnt.col")); // 取得数
                // Java実装
                itemBean.setOperateSendKeys(getCellValue(row, "excel.java.sendkeys.col")); // 値設定
                itemBean.setOperateGetValue(getCellValue(row, "excel.java.get.value.col")); // 値取得(value)
                itemBean.setOperateGetText(getCellValue(row, "excel.java.get.text.col")); // 値取得(text)
                itemBean.setOperateClick(getCellValue(row, "excel.java.click.col")); // クリック
                itemBean.setOperateSelectIndex(getCellValue(row, "excel.java.select.index.col")); // 選択(index)
                itemBean.setOperateSelectValue(getCellValue(row, "excel.java.select.value.col")); // 選択(value)
                itemBean.setOperateSelectText(getCellValue(row, "excel.java.select.text.col")); // 選択(text)

                targetRow++;

                // リストに追加
                if (!itemBean.getItem().isEmpty()) {
                    pageBean.getItemList().add(itemBean);
                }
            }
        }

        return pageBean;
    }

    /**
     * テンプレート生成
     * @param pageBean
     * @throws IOException
     */
    private void generate(PageBean pageBean) throws IOException {

        // 出力ファイルパスの生成
        String fileNm = pageBean.getName() + ".java";
        Path outputPath = Paths.get(prop.getString("java.output.file.dir"), fileNm);

        // Velocityの初期化
        Path velocityPropPath = GenerateUtils.getPath(prop.getString("velocity.property.file"));
        Velocity.init(velocityPropPath.toString());

        // テンプレートの読込
        String templateEncoding = prop.getString("velocity.template.file.encoding");
        Template template = Velocity.getTemplate(prop.getString("velocity.template.file"), templateEncoding);

        // テーブル単位でマージ・ファイル出力
        // Velocityコンテキストに値を設定
        VelocityContext context = new VelocityContext();
        context.put("pageBean", pageBean);
        context.put("q", "\""); // ダブルクォーテーションのエスケープ

        // テンプレートのマージ
        String javaEncoding = prop.getString("java.output.file.encoding");
        try (PrintWriter pw = new PrintWriter(outputPath.toFile(), javaEncoding)) {
            // マージ
            template.merge(context, pw);
            // フラッシュ
            pw.flush();
        }

        logger.debug("結果出力 : {}", outputPath.toString());
    }

    /**
     * Cellへの値反映
     * @param row
     * @param propKey
     * @return 値
     */
    private String getCellValue(Row row,
                                String colPropKey) {
        int col = prop.getInt(colPropKey) - 1;
        Cell cell = row.getCell(col);
        if (cell == null) {
            return "";
        }
        return cell.getStringCellValue();
    }
}
