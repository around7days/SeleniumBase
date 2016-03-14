package generate.bean;

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
    private List<ItemBean> textList = new ArrayList<>();

    /** input type="radio"項目リスト */
    private List<ItemBean> radioList = new ArrayList<>();

    /** input type="checkbox"項目リスト */
    private List<ItemBean> checkboxList = new ArrayList<>();

    /** input type="button"項目リスト */
    private List<ItemBean> buttonList = new ArrayList<>();

    /** select項目リスト */
    private List<ItemBean> selectList = new ArrayList<>();

    /** textarea項目リスト */
    private List<ItemBean> textareaList = new ArrayList<>();

    /** anchor項目リスト */
    private List<ItemBean> anchorList = new ArrayList<>();

    /**
     * 全項目リストを取得します。
     * @return 全項目リスト
     */
    public List<ItemBean> getAllItemList() {
        List<ItemBean> list = new ArrayList<>();
        list.addAll(textList);
        list.addAll(radioList);
        list.addAll(checkboxList);
        list.addAll(buttonList);
        list.addAll(selectList);
        list.addAll(textareaList);
        list.addAll(anchorList);
        return list;
    }

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
    public List<ItemBean> getTextList() {
        return textList;
    }

    /**
     * input type="text"項目リストを設定します。
     * @param textList input type="text"項目リスト
     */
    public void setTextList(List<ItemBean> textList) {
        this.textList = textList;
    }

    /**
     * input type="radio"項目リストを取得します。
     * @return input type="radio"項目リスト
     */
    public List<ItemBean> getRadioList() {
        return radioList;
    }

    /**
     * input type="radio"項目リストを設定します。
     * @param radioList input type="radio"項目リスト
     */
    public void setRadioList(List<ItemBean> radioList) {
        this.radioList = radioList;
    }

    /**
     * input type="checkbox"項目リストを取得します。
     * @return input type="checkbox"項目リスト
     */
    public List<ItemBean> getCheckboxList() {
        return checkboxList;
    }

    /**
     * input type="checkbox"項目リストを設定します。
     * @param checkboxList input type="checkbox"項目リスト
     */
    public void setCheckboxList(List<ItemBean> checkboxList) {
        this.checkboxList = checkboxList;
    }

    /**
     * input type="button"項目リストを取得します。
     * @return input type="button"項目リスト
     */
    public List<ItemBean> getButtonList() {
        return buttonList;
    }

    /**
     * input type="button"項目リストを設定します。
     * @param buttonList input type="button"項目リスト
     */
    public void setButtonList(List<ItemBean> buttonList) {
        this.buttonList = buttonList;
    }

    /**
     * select項目リストを取得します。
     * @return select項目リスト
     */
    public List<ItemBean> getSelectList() {
        return selectList;
    }

    /**
     * select項目リストを設定します。
     * @param selectList select項目リスト
     */
    public void setSelectList(List<ItemBean> selectList) {
        this.selectList = selectList;
    }

    /**
     * textarea項目リストを取得します。
     * @return textarea項目リスト
     */
    public List<ItemBean> getTextareaList() {
        return textareaList;
    }

    /**
     * textarea項目リストを設定します。
     * @param textareaList textarea項目リスト
     */
    public void setTextareaList(List<ItemBean> textareaList) {
        this.textareaList = textareaList;
    }

    /**
     * anchor項目リストを取得します。
     * @return anchor項目リスト
     */
    public List<ItemBean> getAnchorList() {
        return anchorList;
    }

    /**
     * anchor項目リストを設定します。
     * @param anchorList anchor項目リスト
     */
    public void setAnchorList(List<ItemBean> anchorList) {
        this.anchorList = anchorList;
    }
}
