package jp.co.hankyuhanshin.itec.hmbase.seasar;

/**
 * 機能概要：＜修正要＞
 * 作成日：2021/02/26
 *
 * @author Phan Tien VU (VTI Japan Co., Ltd.)
 */
public interface PagerCondition {

    public static final int NONE_LIMIT = -1;

    /**
     * 検索結果の総件数を取得します。
     *
     * @return 総件数
     */
    public int getCount();

    /**
     * 検索結果の総件数をセットします。
     *
     * @param count 総件数
     */
    public void setCount(int count);

    /**
     * 検索結果から一度に取得する最大件数を取得します。
     *
     * @return 最大件数
     */
    public int getLimit();

    /**
     * 検索結果から一度に取得する最大件数をセットします。
     *
     * @param limit 最大件数
     */
    public void setLimit(int limit);

    /**
     * 検索結果の取得開始位置ををセットします。
     *
     * @param offset 取得開始位置
     */
    public void setOffset(int offset);

    /**
     * 検索結果の取得開始位置をを取得します。
     *
     * @return 取得開始位置
     */
    public int getOffset();

}
