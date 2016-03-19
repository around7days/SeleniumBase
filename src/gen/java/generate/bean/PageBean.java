package generate.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * ページ情報
 * @author 7days
 */
public class PageBean {

    /** ページ名称 */
    private String name;

    /** 項目リスト */
    private List<ItemBean> itemList = new ArrayList<>();

    /**
     * ページ名称を取得します。
     * @return ページ名称
     */
    public String getName() {
        return name;
    }

    /**
     * ページ名称を設定します。
     * @param name ページ名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 項目リストを取得します。
     * @return 項目リスト
     */
    public List<ItemBean> getItemList() {
        return itemList;
    }

    /**
     * 項目リストを設定します。
     * @param itemList 項目リスト
     */
    public void setItemList(List<ItemBean> itemList) {
        this.itemList = itemList;
    }

}
