package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.campaign;

import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 広告検索<br/>
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Data
@Component
@Scope("prototype")
public class CampaignModelItem implements Serializable {

    /** serialVersionUID */
    private static final long serialVersionUID = 1L;

    /** 連番 */
    private Integer resultNo;

    /** キャンペーンコード */
    private String campaignCode;

    /** キャンペーン名 */
    private String campaignName;

    /** 備考 */
    private String note;

    /** リダイレクトURL */
    private String redirectUrl;

    /** 広告コスト */
    private BigDecimal campaignCost;

    /**
     * @return the serialVersionUID
     */
    public static long getSerialVersionUID() {
        return serialVersionUID;
    }
}
