package generate.com;

/**
 * Constクラス<br>
 * @author 7days
 */
public class PageConst {

    /** HTMLタグ */
    public enum HtmlTag {
        input, select, textarea, a;
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

    /** FindByアノテーション */
    public enum FindBy {
        id, name, css, linkText, partialLinkText, xpath;
    }

}
