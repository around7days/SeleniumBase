package generate.com;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
     * ファイルパスの取得
     * @param fileNm
     * @return path
     */
    public static Path getFilePath(String fileNm) {
        return Paths.get(GenerateUtils.class.getClass().getClassLoader().getResource(fileNm).getPath());
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

}
