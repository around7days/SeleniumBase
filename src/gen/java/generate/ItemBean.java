package generate;

/**
 * 項目情報
 * @author 7days
 */
public class ItemBean {

    /** 変数名 */
    private String item;
    /** 変数名（先頭大文字） */
    private String itemUpper;

    /** 検索属性 */
    private String findBy;
    /** 検索属性値 */
    private String findByValue;

    /** attrType */
    private String attrType;
    /** attrId */
    private String attrId;
    /** attrName */
    private String attrName;
    /** attrValue */
    private String attrValue;

    /**
     * 変数名を取得します。
     * @return 変数名
     */
    public String getItem() {
        return item;
    }

    /**
     * 変数名を設定します。
     * @param item 変数名
     */
    public void setItem(String item) {
        this.item = item;
    }

    /**
     * 変数名（先頭大文字）を取得します。
     * @return 変数名（先頭大文字）
     */
    public String getItemUpper() {
        return itemUpper;
    }

    /**
     * 変数名（先頭大文字）を設定します。
     * @param itemUpper 変数名（先頭大文字）
     */
    public void setItemUpper(String itemUpper) {
        this.itemUpper = itemUpper;
    }

    /**
     * 検索属性を取得します。
     * @return 検索属性
     */
    public String getFindBy() {
        return findBy;
    }

    /**
     * 検索属性を設定します。
     * @param findBy 検索属性
     */
    public void setFindBy(String findBy) {
        this.findBy = findBy;
    }

    /**
     * 検索属性値を取得します。
     * @return 検索属性値
     */
    public String getFindByValue() {
        return findByValue;
    }

    /**
     * 検索属性値を設定します。
     * @param findByValue 検索属性値
     */
    public void setFindByValue(String findByValue) {
        this.findByValue = findByValue;
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
