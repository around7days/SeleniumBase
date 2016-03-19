package generate;

import generate.bean.PageBean;
import generate.com.GeneratePropertyManager;
import generate.com.GenerateUtils;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

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
            logger.debug("★対象Excelファイル : {}", path.toString());

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
        String fileNmRegex = prop.getString("excel.input.file.name.regex");

        return GenerateUtils.getFileList(dir, fileNmRegex);
    }

    /**
     * HTMLページ解析
     * @param path
     * @return PageBean
     * @throws IOException
     */
    private PageBean analyze(Path path) throws IOException {

        // PageBeanの生成
        PageBean pageBean = new PageBean();

        return pageBean;
    }

    /**
     * テンプレート生成
     * @param pageBean
     * @throws IOException
     */
    private void generate(PageBean pageBean) throws IOException {

        // Velocityの初期化
        Path velocityPropPath = GenerateUtils.getFilePath(prop.getString("velocity.property.file"));
        Velocity.init(velocityPropPath.toString());

        // テンプレートの読込
        Template template = Velocity.getTemplate(prop.getString("velocity.template.file"));

        // テーブル単位でマージ・ファイル出力
        // Velocityコンテキストに値を設定
        VelocityContext context = new VelocityContext();
        context.put("pageBean", pageBean);
        context.put("q", "\""); // ダブルクォーテーションのエスケープ

        // 出力ファイルパスの生成
        Path outputPath = Paths.get(prop.getString("file.output.dir"), pageBean.getPageNmJava());

        // テンプレートのマージ
        PrintWriter pw = new PrintWriter(outputPath.toFile());
        template.merge(context, pw);

        // フラッシュ
        pw.flush();

        // クローズ
        pw.close();

    }
}
