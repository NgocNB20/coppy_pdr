package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.goods;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeFreeDeliveryFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeGoodsSaleStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeIndividualDeliveryType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeStockManagementFlag;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goods.GoodsDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goodsgroup.GoodsGroupDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goodsgroup.GoodsGroupImageRegistUpdateDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goods.GoodsEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goodsgroup.GoodsGroupDisplayEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goodsgroup.GoodsGroupEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goodsgroup.GoodsGroupImageEntity;
import jp.co.hankyuhanshin.itec.hmbase.util.common.PropertiesUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 * 商品管理：商品登録更新抽象ページHelper
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Component
public class AbstractGoodsRegistUpdateHelper {

    /************************************
     ** 商品グループDTO初期化
     ************************************/
    /**
     * 更新または再利用登録時の商品グループ初期化<br/>
     *
     * @param abstractModel ページ
     * @param goodsGroupDto 商品グループDTO
     */
    public void initGoodsGroupDto(AbstractGoodsRegistUpdateModel abstractModel, GoodsGroupDto goodsGroupDto) {
        // 商品グループDTOをセットする
        abstractModel.setGoodsGroupDto(goodsGroupDto);

        // 商品グループ内から販売状態＝「削除」でない商品を１件取得
        GoodsEntity tmpGoodsEntity = null;
        for (GoodsDto goodsDto : goodsGroupDto.getGoodsDtoList()) {
            if (HTypeGoodsSaleStatus.DELETED != goodsDto.getGoodsEntity().getSaleStatusPC()) {
                tmpGoodsEntity = goodsDto.getGoodsEntity();
                break;
            }
        }

        // 共通商品情報を初期化する
        GoodsEntity commonGoodsEntity = ApplicationContextUtility.getBean(GoodsEntity.class);
        if (tmpGoodsEntity == null) {
            // 商品グループ内の全商品が販売状態＝「削除」だった場合
            // 共通商品情報を初期化する
            commonGoodsEntity.setIndividualDeliveryType(HTypeIndividualDeliveryType.OFF);
            commonGoodsEntity.setFreeDeliveryFlag(HTypeFreeDeliveryFlag.OFF);
            // 規格設定画面項目
            commonGoodsEntity.setUnitManagementFlag(abstractModel.getCommonGoodsEntity().getUnitManagementFlag());
            // 在庫管理フラグ
            commonGoodsEntity.setStockManagementFlag(HTypeStockManagementFlag.ON);
        } else {
            // 取得した商品の情報を共通商品情報にセット
            commonGoodsEntity.setGoodsSeq(tmpGoodsEntity.getGoodsSeq());
            commonGoodsEntity.setIndividualDeliveryType(tmpGoodsEntity.getIndividualDeliveryType());
            commonGoodsEntity.setFreeDeliveryFlag(tmpGoodsEntity.getFreeDeliveryFlag());
            // 規格設定画面項目
            commonGoodsEntity.setUnitManagementFlag(tmpGoodsEntity.getUnitManagementFlag());
            // 在庫管理フラグ
            commonGoodsEntity.setStockManagementFlag(tmpGoodsEntity.getStockManagementFlag());
        }
        abstractModel.setCommonGoodsEntity(commonGoodsEntity);
    }

    /**
     * 商品登録時の商品グループ初期化<br/>
     *
     * @param abstractModel ページ
     */
    public void initGoodsGroupDto(AbstractGoodsRegistUpdateModel abstractModel) {
        // 商品グループ情報を初期化する
        GoodsGroupEntity goodsGroupEntity = ApplicationContextUtility.getBean(GoodsGroupEntity.class);
        abstractModel.getGoodsGroupDto().setGoodsGroupEntity(goodsGroupEntity);
        // 商品グループ表示を初期化する
        GoodsGroupDisplayEntity goodsGroupDisplayEntity =
                        ApplicationContextUtility.getBean(GoodsGroupDisplayEntity.class);
        abstractModel.getGoodsGroupDto().setGoodsGroupDisplayEntity(goodsGroupDisplayEntity);

        // 共通商品情報を初期化する
        abstractModel.setCommonGoodsEntity(ApplicationContextUtility.getBean(GoodsEntity.class));
        abstractModel.getCommonGoodsEntity().setIndividualDeliveryType(HTypeIndividualDeliveryType.OFF);
        abstractModel.getCommonGoodsEntity().setFreeDeliveryFlag(HTypeFreeDeliveryFlag.OFF);
        // 規格設定画面項目
        abstractModel.getCommonGoodsEntity()
                     .setUnitManagementFlag(abstractModel.getCommonGoodsEntity().getUnitManagementFlag());
        // 在庫管理フラグ
        abstractModel.getCommonGoodsEntity().setStockManagementFlag(HTypeStockManagementFlag.ON);

    }

    /************************************
     ** 共通処理
     ************************************/
    /**
     * 共通商品情報から個別商品情報リストへのデータ反映<br/>
     *
     * @param abstractModel ページ
     */
    public void toGoodsListFromCommonGoods(AbstractGoodsRegistUpdateModel abstractModel) {
        if (abstractModel.getGoodsGroupDto() == null || abstractModel.getGoodsGroupDto().getGoodsDtoList() == null) {
            return;
        }
        for (GoodsDto goodsDto : abstractModel.getGoodsGroupDto().getGoodsDtoList()) {
            toGoodsDtoFromCommonGoods(abstractModel, goodsDto.getGoodsEntity());
        }
    }

    /**
     * 共通商品情報から個別商品情報へのデータ反映<br/>
     *
     * @param abstractModel ページ
     * @param goodsEntity   商品エンティティ
     */
    public void toGoodsDtoFromCommonGoods(AbstractGoodsRegistUpdateModel abstractModel, GoodsEntity goodsEntity) {
        // 商品基本情報設定
        goodsEntity.setIndividualDeliveryType(abstractModel.getCommonGoodsEntity().getIndividualDeliveryType());
        goodsEntity.setFreeDeliveryFlag(abstractModel.getCommonGoodsEntity().getFreeDeliveryFlag());
        // 規格設定画面項目
        goodsEntity.setUnitManagementFlag(abstractModel.getCommonGoodsEntity().getUnitManagementFlag());
        // 在庫管理フラグ
        goodsEntity.setStockManagementFlag(abstractModel.getCommonGoodsEntity().getStockManagementFlag());
    }

    /************************************
     ** 商品グループ画像関連処理
     ************************************/
    /**
     * 商品グループ画像登録更新用DTOリスト（ページ内編集用）の作成<br/>
     * 画像編集用画面のページ初期化時に呼ぶ。
     *
     * @param abstractModel ページ
     */
    public void initTmpGoodsGroupImageRegistUpdateDtoList(AbstractGoodsRegistUpdateModel abstractModel) {
        if (abstractModel.getTmpGoodsGroupImageRegistUpdateDtoList() == null) {
            if (abstractModel.getGoodsGroupImageRegistUpdateDtoList() != null) {
                // 商品グループ画像登録更新用DTOリストをセッションからページへディープコピーする
                abstractModel.setTmpGoodsGroupImageRegistUpdateDtoList(
                                abstractModel.getGoodsGroupImageRegistUpdateDtoList());
            } else {
                abstractModel.setTmpGoodsGroupImageRegistUpdateDtoList(new ArrayList<GoodsGroupImageRegistUpdateDto>());
            }
        }
    }

    /**
     * 商品グループ画像登録更新用DTOリスト（ページ内編集用）のセッション反映<br/>
     * 画像編集用画面での編集を終了して別画面へ遷移するときに呼ぶ。
     *
     * @param abstractModel ページ
     */
    public void setTmpGoodsGroupImageRegistUpdateDtoList(AbstractGoodsRegistUpdateModel abstractModel) {
        abstractModel.setGoodsGroupImageRegistUpdateDtoList(abstractModel.getTmpGoodsGroupImageRegistUpdateDtoList());
    }

    /**
     * 商品グループ画像パスの取得<br/>
     *
     * @param abstractModel      ページ
     * @param imageTypeVersionNo 画像種別内連番
     * @return 商品グループ画像ファイルパス
     */
    public String getGoodsImageFilepath(AbstractGoodsRegistUpdateModel abstractModel, Integer imageTypeVersionNo) {
        if (abstractModel.getGoodsGroupDto() == null
            || abstractModel.getGoodsGroupDto().getGoodsGroupImageEntityList() == null) {
            return null;
        }
        // 商品グループ画像登録更新用DTOリストを参照してファイルパスを取得
        if (abstractModel.getTmpGoodsGroupImageRegistUpdateDtoList() != null) {
            for (GoodsGroupImageRegistUpdateDto goodsGroupImageRegistUpdateDto : abstractModel.getTmpGoodsGroupImageRegistUpdateDtoList()) {
                if (goodsGroupImageRegistUpdateDto.getGoodsGroupImageEntity()
                                                  .getImageTypeVersionNo()
                                                  .equals(imageTypeVersionNo)) {
                    if (goodsGroupImageRegistUpdateDto.getDeleteFlg()) {
                        // 削除フラグがtrueの場合
                        return null;
                    }
                    // 画像操作Utility取得
                    String tmpImageDirpath = PropertiesUtil.getSystemPropertiesValue("tmp.path");
                    return tmpImageDirpath + "/" + goodsGroupImageRegistUpdateDto.getTmpImageFileName();
                }
            }
        }
        // 商品グループエンティティを参照してファイルパスを取得
        for (GoodsGroupImageEntity goodsGroupImageEntity : abstractModel.getGoodsGroupDto()
                                                                        .getGoodsGroupImageEntityList()) {
            if (goodsGroupImageEntity.getImageTypeVersionNo().equals(imageTypeVersionNo)) {
                String goodsGroupImagepath = PropertiesUtil.getSystemPropertiesValue("images.path.goods");
                return goodsGroupImagepath + "/" + goodsGroupImageEntity.getImageFileName();
            }
        }
        return null;
    }

    /**
     * 商品グループ画像登録更新情報作成<br/>
     *
     * @param abstractModel      ページ
     * @param imageTypeVersionNo 画像種別内連番
     * @param tmpImageFilePath   一時画像ファイルパス
     * @param tmpImageFileName   一時画像ファイル名
     * @param imageFileName      登録先画像ファイル名
     * @param deleteFlg          削除フラグ
     */
    public void makeGoodsGroupImageRegistUpdateInfo(AbstractGoodsRegistUpdateModel abstractModel,
                                                    Integer imageTypeVersionNo,
                                                    String tmpImageFilePath,
                                                    String tmpImageFileName,
                                                    String imageFileName,
                                                    boolean deleteFlg) {
        // 商品グループ画像登録更新情報がnullの場合は作成する
        if (abstractModel.getGoodsGroupImageRegistUpdateDtoList() == null) {
            abstractModel.setGoodsGroupImageRegistUpdateDtoList(new ArrayList<GoodsGroupImageRegistUpdateDto>());
        }
        if (abstractModel.getTmpGoodsGroupImageRegistUpdateDtoList() == null) {
            abstractModel.setTmpGoodsGroupImageRegistUpdateDtoList(new ArrayList<GoodsGroupImageRegistUpdateDto>());
        }

        GoodsGroupImageEntity goodsGroupImageEntity = null;
        // 商品グループDTOを参照して該当する商品グループ画像情報があれば取得する
        if (abstractModel.getGoodsGroupDto().getGoodsGroupImageEntityList() != null) {
            for (GoodsGroupImageEntity tmpGoodsGroupImageEntity : abstractModel.getGoodsGroupDto()
                                                                               .getGoodsGroupImageEntityList()) {
                if (tmpGoodsGroupImageEntity.getImageTypeVersionNo().equals(imageTypeVersionNo)) {
                    goodsGroupImageEntity = tmpGoodsGroupImageEntity;
                    break;
                }
            }
        }

        // 商品グループ画像登録更新用DTOリストに該当画像の情報があれば取得して更新する
        for (GoodsGroupImageRegistUpdateDto goodsGroupImageRegistUpdateDto : abstractModel.getTmpGoodsGroupImageRegistUpdateDtoList()) {
            if (goodsGroupImageRegistUpdateDto.getGoodsGroupImageEntity()
                                              .getImageTypeVersionNo()
                                              .equals(imageTypeVersionNo)) {
                // "削除"かつ商品グループ画像情報がない場合は、商品グループ画像登録更新用DTOリストから削除するのみ
                if (deleteFlg && goodsGroupImageEntity == null) {
                    abstractModel.getTmpGoodsGroupImageRegistUpdateDtoList().remove(goodsGroupImageRegistUpdateDto);
                }
                if (goodsGroupImageEntity != null) {
                    goodsGroupImageRegistUpdateDto.setGoodsGroupImageEntity(goodsGroupImageEntity);
                }
                goodsGroupImageRegistUpdateDto.setTmpImageFilePath(tmpImageFilePath);
                goodsGroupImageRegistUpdateDto.setTmpImageFileName(tmpImageFileName);
                goodsGroupImageRegistUpdateDto.setImageFileName(imageFileName);
                goodsGroupImageRegistUpdateDto.setDeleteFlg(deleteFlg);
                return;
            }
        }

        // 商品グループ画像エンティティが存在しない場合は
        // 商品グループ画像エンティティを作成して商品グループDTOに追加する
        if (goodsGroupImageEntity == null) {
            if (deleteFlg) {
                // 商品グループ画像エンティティが存在しない、かつ削除はありえないが、処理なしで戻す
                return;
            }
            goodsGroupImageEntity = ApplicationContextUtility.getBean(GoodsGroupImageEntity.class);
            if (abstractModel.getGoodsGroupDto().getGoodsGroupEntity() != null) {
                goodsGroupImageEntity.setGoodsGroupSeq(
                                abstractModel.getGoodsGroupDto().getGoodsGroupEntity().getGoodsGroupSeq());
            }
            goodsGroupImageEntity.setImageTypeVersionNo(imageTypeVersionNo);
            if (abstractModel.getGoodsGroupDto().getGoodsGroupImageEntityList() == null) {
                abstractModel.getGoodsGroupDto().setGoodsGroupImageEntityList(new ArrayList<>());
            }
        }

        // 商品グループ画像登録更新用DTOリストに存在しない場合は
        // 商品グループ画像エンティティと商品グループ画像登録更新用DTOを作成してリストに追加する
        GoodsGroupImageRegistUpdateDto goodsGroupImageRegistUpdateDto =
                        ApplicationContextUtility.getBean(GoodsGroupImageRegistUpdateDto.class);
        goodsGroupImageRegistUpdateDto.setGoodsGroupImageEntity(goodsGroupImageEntity);
        goodsGroupImageRegistUpdateDto.setTmpImageFilePath(tmpImageFilePath);
        goodsGroupImageRegistUpdateDto.setTmpImageFileName(tmpImageFileName);
        goodsGroupImageRegistUpdateDto.setImageFileName(imageFileName);
        goodsGroupImageRegistUpdateDto.setDeleteFlg(deleteFlg);
        abstractModel.getTmpGoodsGroupImageRegistUpdateDtoList().add(goodsGroupImageRegistUpdateDto);
        return;
    }

    /**
     * 文字列配列⇒スラッシュ区切り変換<br/>
     *
     * @param param 文字の配列
     * @return スラッシュ区切り後の文字列
     */
    public String joinSlashString(String[] param) {
        if (param == null || param.length == 0) {
            return null;
        }

        String retString = "";
        for (String str : param) {
            if (!"".equals(retString)) {
                retString += "/";
            }
            retString += str;
        }
        return retString;
    }

}
