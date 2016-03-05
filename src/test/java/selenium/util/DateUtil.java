package selenium.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 日付操作の共通クラス<br>
 * @author 7days
 */
public class DateUtil {

    /** Logger */
    @SuppressWarnings("unused")
    private static final Logger logger = LoggerFactory.getLogger(DateUtil.class);

    /** 日付フォーマット [yyyy/MM/dd] */
    public static final String YYYYMMDD = "yyyy/MM/dd";
    /** 日付フォーマット [yyyy/MM/dd HH:mm:ss] */
    public static final String YYYYMMDDHHMMSS = "yyyy/MM/dd HH:mm:ss";
    /** 日付フォーマット [yyyy/MM/dd HH:mm:ss] */
    public static final String YYYYMMDDHHMMSS_S = "yyyy/MM/dd HH:mm:ss.SSS";
    /** 日付フォーマット [yyyyMM] */
    public static final String YYYYMM_NOSLASH = "yyyyMM";
    /** 日付フォーマット [yyyyMMdd] */
    public static final String YYYYMMDD_NOSLASH = "yyyyMMdd";
    /** 日付フォーマット [yyyyMMddHHmmss] */
    public static final String YYYYMMDDHHMMSS_NODELIMITER = "yyyyMMddHHmmss";
    /** 日付フォーマット [yyyyMMddHHmmssS] */
    public static final String YYYYMMDDHHMMSS_S_NODELIMITER = "yyyyMMddHHmmssSSS";
    /** 日付フォーマット [yyyyMMddHHmmssS] */
    public static final String YYYYMMDD_HHMMSS_S_NODELIMITER = "yyyyMMdd_HHmmssSSS";

    /**
     * 現在日付を指定されたフォーマットで取得する。<br>
     * @param format 日付フォーマット
     * @return str 現在日付
     */
    public static String getStringSysDate(String format) {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern(format));

    }

}
