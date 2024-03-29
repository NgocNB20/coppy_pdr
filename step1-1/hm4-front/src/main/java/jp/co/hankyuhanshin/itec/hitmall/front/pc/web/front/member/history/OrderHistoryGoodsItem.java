// PDR Migrate Customization from here
package jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.member.history;

import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeDiscountsType;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeGoodsSaleStatus;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeOpenDeleteStatus;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeSalableGoodsType;
import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * PDR#032 配送状況・注文履歴の基幹連携
 * 注文履歴（未出荷）画面 詳細情報アイテムクラス（商品別一覧）
 * 注文連携Web-APIより取得した情報を画面に表示
 *
 * @author s.kume
 */
@Data
@Component
@Scope("prototype")
public class OrderHistoryGoodsItem implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * 選択商品コード
     */
    private String gcd;

    /**
     * 商品がDBに存在するかのフラグ
     */
    private boolean goodsPresenceFlag;

    /**
     * 申込商品
     */
    private String goodsCode;

    /**
     * 商品名
     */
    private String goodsName;

    /**
     * 数量
     */
    private Integer goodsCount;

    /**
     * 単位
     */
    private String unitName;

    /**
     * 適用割引
     */
    private String discountFlag;

    /**
     * 販売可能商品区分
     */
    private HTypeSalableGoodsType salableGoodsType;

    /**
     * 販売状態
     */
    private HTypeGoodsSaleStatus saleStatus;

    /**
     * 公開状態
     */
    private HTypeOpenDeleteStatus openDeleteStatus;

    // 2024-renew No06 from here
    /**
     * 商品画像アイテム
     */
    private String goodsImage;
    // 2024-renew No06 to here

    /**
     * 商品名リンク表示判定
     * <p>
     * 以下の場合は商品名のリンクを非表示にする。
     * ・非公開商品、公開状態削除商品の場合
     * ・販売可能商品区分が閲覧不可（ログイン後）商品の場合
     * ・WEB-APIで返却された商品がDBに存在しなかった場合
     *
     * @return true=表示、false=非表示
     */
    public boolean isViewLinkGoodsName() {
        // WEB-APIで返却された商品がDBに存在しない
        if (!goodsPresenceFlag) {
            return false;
        }
        // 商品公開状態が非公開商品、公開状態削除
        if (HTypeOpenDeleteStatus.NO_OPEN.equals(openDeleteStatus) || HTypeOpenDeleteStatus.DELETED.equals(
                        openDeleteStatus)) {
            return false;
        }
        // 販売可能商品区分が閲覧不可（ログイン後）
        if (HTypeSalableGoodsType.NOT_VIEW_LOGIN.equals(salableGoodsType)) {
            return false;
        }
        // 心意気価格商品の場合
        if (HTypeDiscountsType.SALEON_EMOTION_PRICE.getValue().equals(discountFlag)) {
            return false;
        }
        return true;
    }

    /**
     * カートに入れるボタン表示判定
     * <p>
     * 以下の場合はカートに入れるボタンを非表示にする。
     * ・非公開商品、公開状態削除商品の場合
     * ・非販売商品、販売状態削除商品の場合
     * ・販売可能商品区分が購入不可（ログイン後）、閲覧不可（ログイン後）商品の場合
     * ・WEB-APIで返却された商品がDBに存在しなかった場合
     *
     * @return true=表示、false=非表示
     */
    public boolean isViewGoCartButtun() {
        // WEB-APIで返却された商品がDBに存在しない
        if (!goodsPresenceFlag) {
            return false;
        }
        // 商品公開状態が非公開商品、公開状態削除
        if (HTypeOpenDeleteStatus.NO_OPEN.equals(openDeleteStatus) || HTypeOpenDeleteStatus.DELETED.equals(
                        openDeleteStatus)) {
            return false;
        }
        // 商品販売状態が非販売商品、販売状態削除
        if (HTypeGoodsSaleStatus.NO_SALE.equals(saleStatus) || HTypeGoodsSaleStatus.DELETED.equals(saleStatus)) {
            return false;
        }
        // 販売可能商品区分が購入不可（ログイン後）、閲覧不可（ログイン後）
        if (HTypeSalableGoodsType.SALEOFF.equals(salableGoodsType) || HTypeSalableGoodsType.NOT_VIEW_LOGIN.equals(
                        salableGoodsType)) {
            return false;
        }
        // 心意気価格商品の場合
        if (HTypeDiscountsType.SALEON_EMOTION_PRICE.getValue().equals(discountFlag)) {
            return false;
        }
        return true;
    }
}
// PDR Migrate Customization to here
