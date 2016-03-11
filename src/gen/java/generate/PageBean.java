package generate;

import java.util.ArrayList;
import java.util.List;

/**
 * ページ情報
 * @author 7days
 */
public class PageBean {

    /** クラス名 */
    private String classNm;

    /** ページタイトル */
    private String title;

    /** input type="text"項目リスト */
    private List<ItemBean> typeTextList = new ArrayList<>();

    /**
     * クラス名を取得します。
     * @return クラス名
     */
    public String getClassNm() {
        return classNm;
    }

    /**
     * クラス名を設定します。
     * @param classNm クラス名
     */
    public void setClassNm(String classNm) {
        this.classNm = classNm;
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
     * input type="text"項目リストを取得します。
     * @return input type="text"項目リスト
     */
    public List<ItemBean> getTypeTextList() {
        return typeTextList;
    }

    /**
     * input type="text"項目リストを設定します。
     * @param typeTextList input type="text"項目リスト
     */
    public void setTypeTextList(List<ItemBean> typeTextList) {
        this.typeTextList = typeTextList;
    }

}
