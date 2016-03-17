package generate.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * ページ情報
 * @author 7days
 */
public class PageBean {

    /** ページ名称（HTML） */
    private String pageNmHtml;

    /** ページ名称（Excel） */
    private String pageNmExcel;

    /** ページタイトル */
    private String title;

    /** 項目リスト */
    private List<ItemBean> itemList = new ArrayList<>();

    /**
     * ページ名称（HTML）を取得します。
     * @return ページ名称（HTML）
     */
    public String getPageNmHtml() {
        return pageNmHtml;
    }

    /**
     * ページ名称（HTML）を設定します。
     * @param pageNmHtml ページ名称（HTML）
     */
    public void setPageNmHtml(String pageNmHtml) {
        this.pageNmHtml = pageNmHtml;
    }

    /**
     * ページ名称（Excel）を取得します。
     * @return ページ名称（Excel）
     */
    public String getPageNmExcel() {
        return pageNmExcel;
    }

    /**
     * ページ名称（Excel）を設定します。
     * @param pageNmExcel ページ名称（Excel）
     */
    public void setPageNmExcel(String pageNmExcel) {
        this.pageNmExcel = pageNmExcel;
    }

    /**
     * ページタイトルを取得します。
     * @return ページタイトル
     */
    public String getTitle() {
        return title;
    }

    /**
     * ページタイトルを設定します。
     * @param title ページタイトル
     */
    public void setTitle(String title) {
        this.title = title;
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
