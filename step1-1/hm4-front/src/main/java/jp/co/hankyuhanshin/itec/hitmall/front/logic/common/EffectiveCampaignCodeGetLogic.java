package jp.co.hankyuhanshin.itec.hitmall.front.logic.common;

/**
 * 有効なキャンペーンコード取得ロジック<br/>
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 *
 */
public interface EffectiveCampaignCodeGetLogic {

    /**
     * 有効なキャンペーンコード取得処理<br/>
     *
     * @param shopSeq ショップSEQ
     * @param accessUid 端末識別番号
     * @return キャンペーンコード
     */
    String execute(Integer shopSeq, String accessUid);
}
