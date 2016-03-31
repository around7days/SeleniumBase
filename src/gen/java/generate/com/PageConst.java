package generate.com;

/**
 * Constクラス<br>
 * @author 7days
 */
public class PageConst {

    /** Excel明細上限 */
    public static final int EXCEL_DETAIL_MAX = 200;

    /** 選択結果（重複） */
    public static final String CNT_MULTI = "複数";

    /** 選択 */
    public static final String ON = "○";

    /** HTMLタグ */
    public enum HtmlTag {
        input, textarea, a, select, frame;

        public static HtmlTag getEnum(String str) {
            if (str == null) return null;
            for (HtmlTag htmlTag : HtmlTag.values()) {
                if (htmlTag.name().toLowerCase().equals(str.toLowerCase())) {
                    return htmlTag;
                }
            }
            return null;
        }
    }

    /** 項目属性 */
    public enum ItemAttr {
        type, id, name, value;

        public static ItemAttr getEnum(String str) {
            if (str == null) return null;
            for (ItemAttr itemAttr : ItemAttr.values()) {
                if (itemAttr.name().toLowerCase().equals(str.toLowerCase())) {
                    return itemAttr;
                }
            }
            return null;
        }
    }

    /** 項目Type属性 */
    public enum ItemAttrType {
        text, password, radio, file, img, checkbox, button, submit;

        public static ItemAttrType getEnum(String str) {
            if (str == null) return null;
            for (ItemAttrType itemAttrType : ItemAttrType.values()) {
                if (itemAttrType.name().toLowerCase().equals(str.toLowerCase())) {
                    return itemAttrType;
                }
            }
            return null;
        }
    }

    /** FindByアノテーション */
    public enum FindBy {
        id, name, css, linkText, partialLinkText, xpath;
    }

}
