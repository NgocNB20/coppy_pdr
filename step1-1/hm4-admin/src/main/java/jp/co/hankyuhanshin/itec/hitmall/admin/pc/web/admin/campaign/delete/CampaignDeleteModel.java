package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.campaign.delete;

import jp.co.hankyuhanshin.itec.hitmall.admin.web.AbstractModel;
import jp.co.hankyuhanshin.itec.hitmall.annotation.converter.HCNumber;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.campaign.CampaignEntity;
import lombok.Data;

import java.math.BigDecimal;

/**
 * クーポン登録削除確認ページクラス
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Data
public class CampaignDeleteModel extends AbstractModel {

    /** キャンペーン */
    private CampaignEntity campaignEntity;

    /** キャンペーンコード */
    private String campaignCode;

    /** キャンペーン名 */
    private String campaignName;

    /** 備考 */
    private String note;

    /** リダイレクトURL */
    private String redirectUrl;

    /** 広告コスト */
    @HCNumber
    private BigDecimal campaignCost;

    /** 広告URL PC */
    private String campaignUrlPc;
}
