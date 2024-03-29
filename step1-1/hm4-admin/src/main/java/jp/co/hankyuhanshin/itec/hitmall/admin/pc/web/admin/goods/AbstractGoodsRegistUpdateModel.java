package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.goods;

import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.common.validation.group.ConfirmGroup;
import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.goods.registupdate.validation.group.UploadImageGroup;
import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.goods.registupdate.validation.group.UploadUnitImageGroup;
import jp.co.hankyuhanshin.itec.hitmall.admin.web.AbstractModel;
import jp.co.hankyuhanshin.itec.hitmall.annotation.validator.HVBothSideSpace;
import jp.co.hankyuhanshin.itec.hitmall.annotation.validator.HVSpecialCharacter;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeGoodsSaleStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeUnitManagementFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.SpaceValidateMode;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goodsgroup.GoodsGroupDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goodsgroup.GoodsGroupImageRegistUpdateDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goods.GoodsEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goods.GoodsImageEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goodsgroup.GoodsRelationEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goodsgroup.GoodsTogetherBuyGroupEntity;
import jp.co.hankyuhanshin.itec.hmbase.constant.ValidatorConstants;
import jp.co.hankyuhanshin.itec.hmbase.util.common.PropertiesUtil;
import jp.co.hankyuhanshin.itec.hmbase.util.seasar.StringUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.ObjectUtils;
import org.hibernate.validator.constraints.Length;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.List;

/**
 * 商品管理：商品登録更新抽象ページ
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class AbstractGoodsRegistUpdateModel extends AbstractModel {

    /**
     * ロガー
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractGoodsRegistUpdateModel.class);

    /**
     * コンストラクタ<br/>
     * 初期値の設定<br/>
     */
    public AbstractGoodsRegistUpdateModel() {
        super();
        this.inputingFlg = false;
        try {
            this.goodsRelationAmount =
                            Integer.parseInt(PropertiesUtil.getSystemPropertiesValue("goods.relation.amount"));
            // 2023-renew No21 from here
            this.goodsTogetherBuyAmount =
                            Integer.parseInt(PropertiesUtil.getSystemPropertiesValue("goods.togetherbuy.amount"));
            // 2023-renew No21 to here
        } catch (Exception e) {
            LOGGER.error("例外処理が発生しました", e);
            // 設定ファイルの関連商品最大設定数が数値に変換できない場合、デフォルト値(関連商品最大設定数:5個)とする
        }
    }

    /**
     * 商品グループSEQ(画面用)
     */
    private Integer scGoodsGroupSeq;

    /**
     * 戻り先画面<br/>
     */
    private Class<?> backPage;

    /**
     * 戻り先画面(セッション保存用)<br/>
     */
    private Class<?> storedBackPage;

    /**
     * 新規登録フラグ<br/>
     * true : 新規登録 || false : 更新
     */
    private boolean registFlg;

    /**
     * 登録更新中フラグ<br/>
     */
    private boolean inputingFlg;

    /**
     * 戻り先URL
     */
    private String goBackLinkHref;

    /**
     * 関連商品保持設定数<br/>
     * システムプロパティから取得<br />
     */
    private int goodsRelationAmount = 5;

    // 2023-renew No21 from here
    /**
     * よく一緒に購入される商品保持設定数<br/>
     * システムプロパティから取得<br />
     */
    private int goodsTogetherBuyAmount = 20;
    // 2023-renew No21 to here

    /**
     * 商品グループ情報<br/>
     */
    private GoodsGroupDto goodsGroupDto;

    /**
     * 関連商品グループ情報<br/>
     */
    private List<GoodsRelationEntity> goodsRelationEntityList;

    // 2023-renew No21 from here
    /**
     * よく一緒に購入される商品情報
     */
    private List<GoodsTogetherBuyGroupEntity> goodsTogetherBuyGroupEntityList;
    // 2023-renew No21 to here

    /**
     * 商品グループ画像登録更新用DTOリスト<br/>
     */
    private List<GoodsGroupImageRegistUpdateDto> goodsGroupImageRegistUpdateDtoList;

    /**
     * 商品グループ画像登録更新用DTOリスト（ページ内編集用）<br/>
     */
    private List<GoodsGroupImageRegistUpdateDto> tmpGoodsGroupImageRegistUpdateDtoList;

    /**
     * 商品規格画像リスト<br/>
     */
    private List<GoodsImageEntity> unitImageList;

    /**
     * （共通）商品エンティティ情報<br/>
     * 全商品共通の情報を保持する。（商品タイプ、個別配送など）<br/>
     */
    private GoodsEntity commonGoodsEntity;

    /**
     * 商品管理番号<br/>
     */
    @NotEmpty(groups = {ConfirmGroup.class, UploadImageGroup.class, UploadUnitImageGroup.class})
    @Pattern(regexp = ValidatorConstants.REGEX_GOODS_GROUP_CODE,
             message = ValidatorConstants.MSGCD_REGEX_GOODS_GROUP_CODE,
             groups = {ConfirmGroup.class, UploadImageGroup.class})
    @Length(min = 1, max = ValidatorConstants.LENGTH_GOODS_GROUP_CODE_MAXIMUM,
            groups = {ConfirmGroup.class, UploadImageGroup.class})
    private String goodsGroupCode;

    /**
     * 商品名<br/>
     */
    @NotEmpty(groups = {ConfirmGroup.class})
    @HVBothSideSpace(startWith = SpaceValidateMode.ALLOW_SPACE, endWith = SpaceValidateMode.ALLOW_SPACE,
                     groups = {ConfirmGroup.class})
    @HVSpecialCharacter(allowPunctuation = true, groups = {ConfirmGroup.class})
    @Length(min = 1, max = 120, groups = {ConfirmGroup.class})
    private String goodsGroupName;

    /**
     * 再利用フラグ<br/>
     */
    private String recycleFlg;

    /**
     * 再利用元商品グループコード<br/>
     */
    private String recycledGoodsGroupCode;

    /************************************
     ** Redirect受け渡し項目
     ************************************/
    /**
     * 商品グループコード<br/>
     */
    private String redirectGoodsGroupCode;

    /**
     * 再利用フラグ<br/>
     */
    private String redirectRecycle;

    /**
     * 処理モード<br/>
     * 再検索を行う場合は、md=listのパラメータを付与する。<br/>
     */
    private String md;

    /**
     * 呼び出し元<br/>
     * サイドメニュー、メニューバーの商品新規登録からきた場合、from=menuのパラメータを付与する<br/>
     */
    private String from;

    /************************************
     ** 登録・更新判定
     ************************************/
    /**
     * 登録処理判定<br/>
     *
     * @return true=商品グループ情報がnull
     */
    public boolean isRegist() {
        return registFlg;
    }

    /************************************
     ** 商品販売状態=削除でない最初の商品のインデックス
     ************************************/
    /**
     * 商品グループ内の削除でない最初の商品のインデックスを返す
     *
     * @param goodsGroupDto 商品グループDto
     * @return 商品販売状態=削除でない最初の商品のインデックス
     */
    public int getNotDeletedGoodsFirstIndex(GoodsGroupDto goodsGroupDto) {
        for (int i = 0; goodsGroupDto.getGoodsDtoList() != null && i < goodsGroupDto.getGoodsDtoList().size(); i++) {
            if (HTypeGoodsSaleStatus.DELETED != goodsGroupDto.getGoodsDtoList()
                                                             .get(i)
                                                             .getGoodsEntity()
                                                             .getSaleStatusPC()) {
                return i;
            }
        }
        return -1;
    }

    /**
     * コンディション:規格管理有無<br/>
     *
     * @return true：規格管理あり
     */
    public boolean isUnitManagementExist() {
        try {
            // 2023-renew No21 from here
            if(ObjectUtils.isNotEmpty(getCommonGoodsEntity())) {
                return HTypeUnitManagementFlag.ON.equals(getCommonGoodsEntity().getUnitManagementFlag());
            } else {
                return false;
            }
            // 2023-renew No21 to here
        } catch (Exception e) {
            LOGGER.error("例外処理が発生しました", e);
            return false;
        }
    }

    /**
     * @return the goodsGroupName
     */
    public String getGoodsGroupName() {
        if (StringUtil.isNotEmpty(goodsGroupName)) {
            return goodsGroupName;
        }
        if (goodsGroupDto != null && goodsGroupDto.getGoodsGroupEntity() != null
            && goodsGroupDto.getGoodsGroupEntity().getGoodsGroupName() != null) {
            return goodsGroupDto.getGoodsGroupEntity().getGoodsGroupName();
        }
        return goodsGroupName;
    }

}
