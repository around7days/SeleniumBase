package generate.bean;

/**
 * 項目情報
 * @author 7days
 */
public class ItemBean {

    /** 項目名 */
    private String item;
    /** 項目名（先頭大文字） */
    private String itemUpper;
    /** 項目名（コメント用） */
    private String itemComment;
    /** タグ */
    private String tag;

    /** 検索タイプ */
    private String findBy;
    /** 検索タイプ値 */
    private String findByVal;

    /** 値 */
    private String text;

    /** attrType */
    private String attrType;
    /** attrId */
    private String attrId;
    /** attrName */
    private String attrName;
    /** attrValue */
    private String attrValue;

    /**
     * 項目名を取得します。
     * @return 項目名
     */
    public String getItem() {
        return item;
    }

    /**
     * 項目名を設定します。
     * @param item 項目名
     */
    public void setItem(String item) {
        this.item = item;
    }

    /**
     * 項目名（先頭大文字）を取得します。
     * @return 項目名（先頭大文字）
     */
    public String getItemUpper() {
        return itemUpper;
    }

    /**
     * 項目名（先頭大文字）を設定します。
     * @param itemUpper 項目名（先頭大文字）
     */
    public void setItemUpper(String itemUpper) {
        this.itemUpper = itemUpper;
    }

    /**
     * 項目名（コメント用）を取得します。
     * @return 項目名（コメント用）
     */
    public String getItemComment() {
        return itemComment;
    }

    /**
     * 項目名（コメント用）を設定します。
     * @param itemComment 項目名（コメント用）
     */
    public void setItemComment(String itemComment) {
        this.itemComment = itemComment;
    }

    /**
     * タグを取得します。
     * @return タグ
     */
    public String getTag() {
        return tag;
    }

    /**
     * タグを設定します。
     * @param tag タグ
     */
    public void setTag(String tag) {
        this.tag = tag;
    }

    /**
     * 検索タイプを取得します。
     * @return 検索タイプ
     */
    public String getFindBy() {
        return findBy;
    }

    /**
     * 検索タイプを設定します。
     * @param findBy 検索タイプ
     */
    public void setFindBy(String findBy) {
        this.findBy = findBy;
    }

    /**
     * 検索タイプ値を取得します。
     * @return 検索タイプ値
     */
    public String getFindByVal() {
        return findByVal;
    }

    /**
     * 検索タイプ値を設定します。
     * @param findByVal 検索タイプ値
     */
    public void setFindByVal(String findByVal) {
        this.findByVal = findByVal;
    }

    /**
     * 値を取得します。
     * @return 値
     */
    public String getText() {
        return text;
    }

    /**
     * 値を設定します。
     * @param text 値
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * attrTypeを取得します。
     * @return attrType
     */
    public String getAttrType() {
        return attrType;
    }

    /**
     * attrTypeを設定します。
     * @param attrType attrType
     */
    public void setAttrType(String attrType) {
        this.attrType = attrType;
    }

    /**
     * attrIdを取得します。
     * @return attrId
     */
    public String getAttrId() {
        return attrId;
    }

    /**
     * attrIdを設定します。
     * @param attrId attrId
     */
    public void setAttrId(String attrId) {
        this.attrId = attrId;
    }

    /**
     * attrNameを取得します。
     * @return attrName
     */
    public String getAttrName() {
        return attrName;
    }

    /**
     * attrNameを設定します。
     * @param attrName attrName
     */
    public void setAttrName(String attrName) {
        this.attrName = attrName;
    }

    /**
     * attrValueを取得します。
     * @return attrValue
     */
    public String getAttrValue() {
        return attrValue;
    }

    /**
     * attrValueを設定します。
     * @param attrValue attrValue
     */
    public void setAttrValue(String attrValue) {
        this.attrValue = attrValue;
    }
}
