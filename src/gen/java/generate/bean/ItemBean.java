package generate.bean;

/**
 * 項目情報
 * @author 7days
 */
public class ItemBean {

    /** HTML */
    private String html;

    /* ------------------------------- */
    /** 項目名 */
    private String item;
    /** tag */
    private String tag;
    /** type */
    private String type;
    /** id */
    private String id;
    /** name */
    private String name;
    /** value */
    private String value;
    /** text */
    private String text;

    /* ------------------------------- */
    /** 選択方法 */
    private String findBy;
    /** 選択方法の値 */
    private String findByVal;
    /** 選択方法の取得数 */
    private String findByCnt;

    /* ------------------------------- */
    /** 値選択 */
    private String operateSendKeys;
    /** 値取得(value) */
    private String operateGetValue;
    /** 値取得(text) */
    private String operateGetText;
    /** クリック */
    private String operateClick;
    /** 選択(index) */
    private String operateSelectIndex;
    /** 選択(value) */
    private String operateSelectValue;
    /** 選択(text) */
    private String operateSelectText;

    /**
     * HTMLを取得します。
     * @return HTML
     */
    public String getHtml() {
        return html;
    }

    /**
     * HTMLを設定します。
     * @param html HTML
     */
    public void setHtml(String html) {
        this.html = html;
    }

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
     * tagを取得します。
     * @return tag
     */
    public String getTag() {
        return tag;
    }

    /**
     * tagを設定します。
     * @param tag tag
     */
    public void setTag(String tag) {
        this.tag = tag;
    }

    /**
     * typeを取得します。
     * @return type
     */
    public String getType() {
        return type;
    }

    /**
     * typeを設定します。
     * @param type type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * idを取得します。
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * idを設定します。
     * @param id id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * nameを取得します。
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * nameを設定します。
     * @param name name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * valueを取得します。
     * @return value
     */
    public String getValue() {
        return value;
    }

    /**
     * valueを設定します。
     * @param value value
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * textを取得します。
     * @return text
     */
    public String getText() {
        return text;
    }

    /**
     * textを設定します。
     * @param text text
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * 選択方法を取得します。
     * @return 選択方法
     */
    public String getFindBy() {
        return findBy;
    }

    /**
     * 選択方法を設定します。
     * @param findBy 選択方法
     */
    public void setFindBy(String findBy) {
        this.findBy = findBy;
    }

    /**
     * 選択方法の値を取得します。
     * @return 選択方法の値
     */
    public String getFindByVal() {
        return findByVal;
    }

    /**
     * 選択方法の値を設定します。
     * @param findByVal 選択方法の値
     */
    public void setFindByVal(String findByVal) {
        this.findByVal = findByVal;
    }

    /**
     * 選択方法の取得数を取得します。
     * @return 選択方法の取得数
     */
    public String getFindByCnt() {
        return findByCnt;
    }

    /**
     * 選択方法の取得数を設定します。
     * @param findByCnt 選択方法の取得数
     */
    public void setFindByCnt(String findByCnt) {
        this.findByCnt = findByCnt;
    }

    /**
     * 値選択を取得します。
     * @return 値選択
     */
    public String getOperateSendKeys() {
        return operateSendKeys;
    }

    /**
     * 値選択を設定します。
     * @param operateSendKeys 値選択
     */
    public void setOperateSendKeys(String operateSendKeys) {
        this.operateSendKeys = operateSendKeys;
    }

    /**
     * 値取得(value)を取得します。
     * @return 値取得(value)
     */
    public String getOperateGetValue() {
        return operateGetValue;
    }

    /**
     * 値取得(value)を設定します。
     * @param operateGetValue 値取得(value)
     */
    public void setOperateGetValue(String operateGetValue) {
        this.operateGetValue = operateGetValue;
    }

    /**
     * 値取得(text)を取得します。
     * @return 値取得(text)
     */
    public String getOperateGetText() {
        return operateGetText;
    }

    /**
     * 値取得(text)を設定します。
     * @param operateGetText 値取得(text)
     */
    public void setOperateGetText(String operateGetText) {
        this.operateGetText = operateGetText;
    }

    /**
     * クリックを取得します。
     * @return クリック
     */
    public String getOperateClick() {
        return operateClick;
    }

    /**
     * クリックを設定します。
     * @param operateClick クリック
     */
    public void setOperateClick(String operateClick) {
        this.operateClick = operateClick;
    }

    /**
     * 選択(index)を取得します。
     * @return 選択(index)
     */
    public String getOperateSelectIndex() {
        return operateSelectIndex;
    }

    /**
     * 選択(index)を設定します。
     * @param operateSelectIndex 選択(index)
     */
    public void setOperateSelectIndex(String operateSelectIndex) {
        this.operateSelectIndex = operateSelectIndex;
    }

    /**
     * 選択(value)を取得します。
     * @return 選択(value)
     */
    public String getOperateSelectValue() {
        return operateSelectValue;
    }

    /**
     * 選択(value)を設定します。
     * @param operateSelectValue 選択(value)
     */
    public void setOperateSelectValue(String operateSelectValue) {
        this.operateSelectValue = operateSelectValue;
    }

    /**
     * 選択(text)を取得します。
     * @return 選択(text)
     */
    public String getOperateSelectText() {
        return operateSelectText;
    }

    /**
     * 選択(text)を設定します。
     * @param operateSelectText 選択(text)
     */
    public void setOperateSelectText(String operateSelectText) {
        this.operateSelectText = operateSelectText;
    }

    public String toString() {
        return html;
    }
}
