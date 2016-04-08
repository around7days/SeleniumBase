package generate.com;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class GenerateUtils {

    /**
     * ファイル一覧の取得
     * @param dir
     * @param fileNmRegex
     * @return ファイル一覧
     * @throws IOException
     */
    public static List<Path> getFileList(Path dir,
                                         String fileNmRegex) throws IOException {
        // 検索結果の格納List
        List<Path> list = new ArrayList<Path>();

        // 検索処理
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir, fileNmRegex)) {
            stream.forEach(list::add);
        }

        return list;
    }

    /**
     * ファイルパスから拡張子を除いたファイル名を取得<br>
     * @param filePath
     * @return ファイル名
     */
    public static String getFileNm(Path filePath) {
        String fileNm = filePath.toFile().getName();
        return fileNm.substring(0, fileNm.lastIndexOf("."));
    }

    /**
     * ファイル名からファイルパスを取得
     * @param fileNm
     * @return path
     */
    public static Path getPath(String fileNm) {
        String path = GeneratePropertyManager.INSTANCE.getClass().getClassLoader().getResource(fileNm).getPath();
        return new File(path).toPath();
    }

    /**
     * 先頭文字大文字化
     * @param value
     * @return value
     */
    public static String firstCharUpper(String value) {
        if (value.length() > 1) {
            return String.valueOf(value.charAt(0)).toUpperCase() + value.substring(1, value.length());
        } else {
            return value.toUpperCase();
        }
    }

    /**
     * 空白チェック
     * @param value
     * @return 結果
     */
    public static boolean isEmpty(String value) {
        return (value == null || value.isEmpty());
    }

}
