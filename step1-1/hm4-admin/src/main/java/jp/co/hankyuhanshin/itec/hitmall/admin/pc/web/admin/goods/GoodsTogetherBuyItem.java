// 2023-renew No21 from here
package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.goods;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeRegisterMethodType;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.ZenHanConversionUtility;
import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * 商品管理：商品詳細ページ よく一緒に購入されている商品アイテム<br/>
 *
 * @author Doan Thang (VTI Japan Co., Ltd.)
 */
@Data
@Component
@Scope("prototype")
public class GoodsTogetherBuyItem {

    /**
     * シリアルバージョンID<br/>
     */
    private static final long serialVersionUID = 1L;

    /**
     * よく一緒に購入されているNo<br/>
     */
    private Integer togetherBuyDspNo;

    /**
     * よく一緒に購入されている商品グループコード<br/>
     */
    private String togetherBuyGoodsGroupCode;

    /**
     * よく一緒に購入されている商品グループ名<br/>
     */
    private String togetherBuyGoodsGroupName;

    /**
     * 登録方法<br/>
     */
    private HTypeRegisterMethodType registmethod;

    /**
     * 表示タイプ
     *
     * @return true=集計バッチ、false=管理画面
     */
    public boolean isBatchRegist() {
        return HTypeRegisterMethodType.BATCH.equals(registmethod);
    }

    /**
     * よく一緒に購入されているNo（全角変換）
     *
     * @return 全角No
     */
    public String togetherBuyZenkakuNo() {
        // 全角、半角の変換Helper取得
        ZenHanConversionUtility zenHanConversionUtility =
                        ApplicationContextUtility.getBean(ZenHanConversionUtility.class);
        if (isBatchRegist() && togetherBuyDspNo > 20) {
            int no = togetherBuyDspNo - 20;
            return zenHanConversionUtility.toZenkaku(Integer.toString(no));
        } else {
            return zenHanConversionUtility.toZenkaku(Integer.toString(togetherBuyDspNo));
        }
    }
}
// 2023-renew No21 to here
