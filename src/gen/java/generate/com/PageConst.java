package generate.com;

/**
 * Constクラス<br>
 * @author 7days
 */
public class PageConst {

    /** HTMLタグ */
    public enum HtmlTag {
        input, select, textarea, a;

        public static HtmlTag getEnum(String str) {
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
        text, password, radio, file, img, button, submit;

        public static ItemAttrType getEnum(String str) {
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
