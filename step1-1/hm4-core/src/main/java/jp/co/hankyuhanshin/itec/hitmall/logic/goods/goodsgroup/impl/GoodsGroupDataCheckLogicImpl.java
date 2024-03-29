/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.goods.goodsgroup.impl;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeGoodsSaleStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOpenDeleteStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeRegisterMethodType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSiteType;
import jp.co.hankyuhanshin.itec.hitmall.dao.goods.category.CategoryDao;
import jp.co.hankyuhanshin.itec.hitmall.dao.goods.goods.GoodsDao;
import jp.co.hankyuhanshin.itec.hitmall.dao.goods.goodsgroup.GoodsGroupDao;
import jp.co.hankyuhanshin.itec.hitmall.dao.goods.goodsgroup.GoodsInformationIconDao;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goods.GoodsDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goodsgroup.GoodsGroupDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.icon.GoodsInformationIconDetailsDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.category.CategoryEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.category.CategoryGoodsEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goods.GoodsEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goodsgroup.GoodsGroupDisplayEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goodsgroup.GoodsGroupEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goodsgroup.GoodsRelationEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goodsgroup.GoodsTogetherBuyGroupEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.goods.GoodsDataCheckLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.goodsgroup.GoodsGroupDataCheckLogic;
import jp.co.hankyuhanshin.itec.hitmall.utility.GoodsUtility;
import jp.co.hankyuhanshin.itec.hmbase.exception.AppLevelListException;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * 商品グループデータチェック<br/>
 *
 * @author hirata
 * @author Kaneko(Itec) 2011/04/26 チケット #2632 対応
 */
@Component
public class GoodsGroupDataCheckLogicImpl extends AbstractShopLogic implements GoodsGroupDataCheckLogic {

    /**
     * ロガー
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(GoodsGroupDataCheckLogicImpl.class);

    /**
     * 商品DAO
     */
    private final GoodsDao goodsDao;

    /**
     * 商品グループDAO
     */
    private final GoodsGroupDao goodsGroupDao;

    /**
     * カテゴリDAO
     */
    private final CategoryDao categoryDao;

    /**
     * 商品インフォメーションアイコンDao
     */
    private final GoodsInformationIconDao goodsInformationIconDao;

    /**
     * 商品データチェックLogic
     */
    private final GoodsDataCheckLogic goodsDataCheckLogic;

    @Autowired
    public GoodsGroupDataCheckLogicImpl(GoodsDao goodsDao,
                                        GoodsGroupDao goodsGroupDao,
                                        CategoryDao categoryDao,
                                        GoodsInformationIconDao goodsInformationIconDao,
                                        GoodsDataCheckLogic goodsDataCheckLogic) {
        this.goodsDao = goodsDao;
        this.goodsGroupDao = goodsGroupDao;
        this.categoryDao = categoryDao;
        this.goodsInformationIconDao = goodsInformationIconDao;
        this.goodsDataCheckLogic = goodsDataCheckLogic;
    }

    /**
     * 仕様はインタフェースのJavadocを参照
     *
     * @param goodsGroupSeq Integer
     * @param siteType      HTypeSiteType
     * @return boolean
     */
    @Override
    public boolean isAvailable(Integer goodsGroupSeq, HTypeSiteType siteType) {
        if (siteType == null) {
            throw new IllegalArgumentException("HTypeSiteType is null");
        }

        GoodsGroupEntity entity = goodsGroupDao.getEntity(goodsGroupSeq);
        if (entity == null) {
            throw new IllegalArgumentException(String.format("商品グループSEQ(%s)はレコードは存在しない", goodsGroupSeq));
        }

        GoodsUtility goodsUtility = ApplicationContextUtility.getBean(GoodsUtility.class);

        // 注意!! isFrontPCのみテスト済み（#2940対応）
        if (siteType.isFrontPC() || siteType.isFrontSP()) {
            HTypeOpenDeleteStatus goodsOpenStatus = entity.getGoodsOpenStatusPC();
            Timestamp openStartTime = entity.getOpenStartTimePC();
            Timestamp openEndTime = entity.getOpenEndTimePC();
            return goodsUtility.isGoodsOpen(goodsOpenStatus, openStartTime, openEndTime);
        } else if (siteType.isBack()) {
            // 参照するのはPCとMBどちらのステータスでも良い
            // （理由） PC/MBのどちらかのみ削除は出来ないため
            return HTypeOpenDeleteStatus.DELETED != entity.getGoodsOpenStatusPC();
        } else {
            throw new IllegalArgumentException(String.format("指定したsiteType(%s)は無効。PC/MB/Adminの何れかを指定すること", siteType));
        }
    }

    /**
     * 商品グループデータチェック<br/>
     * 商品グループDTOの登録・更新前チェックを行う。<br/>
     * CSV用にエラーパラメータの末尾に商品グループコード・商品コードを付加する。<br/>
     *
     * @param goodsGroupDto           商品グループDto
     * @param goodsRelationEntityList 関連商品エンティティリスト
     * @param shopSeq                 ショップSEQ
     */
    @Override
    public void execute(GoodsGroupDto goodsGroupDto,
                        List<GoodsRelationEntity> goodsRelationEntityList,
                        // 2023-renew No21 from here
                        List<GoodsTogetherBuyGroupEntity> goodsTogetherBuyGroupEntityList,
                        // 2023-renew No21 to here
                        Integer shopSeq) {

        // (1) パラメータチェック
        // 商品グループDTOが null でないかをチェック
        ArgumentCheckUtil.assertNotNull("goodsGroupDto", goodsGroupDto);
        // 商品グループDTO．商品DTOリスト が null または 0件 でないかをチェック
        ArgumentCheckUtil.assertNotEmpty("goodsDtoList", goodsGroupDto.getGoodsDtoList());

        // (2) 商品チェック
        // 2023-renew No21 from here
        checkGoods(goodsGroupDto, goodsRelationEntityList, goodsTogetherBuyGroupEntityList, shopSeq);
        // 2023-renew No21 to here

        // (3) エラーメッセージがあれば投げる
        if (hasErrorList()) {
            throwMessage();
        }
    }

    /**
     * 商品をチェック<br/>
     * <pre>
     * ・商品グループチェック
     * ・商品グループ表示チェック
     * ・カテゴリ登録商品チェック
     * ・関連商品チェック
     * ・商品データチェック
     * </pre>
     *
     * @param goodsGroupDto           商品グループDTO
     * @param goodsRelationEntityList 関連商品エンティティリスト
     * @param shopSeq                 ショップSEQ
     * @param customParams            案件用引数
     */
    protected void checkGoods(GoodsGroupDto goodsGroupDto,
                              List<GoodsRelationEntity> goodsRelationEntityList,
                              // 2023-renew No21 from here
                              List<GoodsTogetherBuyGroupEntity> goodsTogetherBuyGroupEntityList,
                              // 2023-renew No21 to here
                              Integer shopSeq,
                              Object... customParams) {

        // (2)-1 商品グループチェック
        checkGoodsGroup(goodsGroupDto, shopSeq);

        // (2)-2 商品グループ表示チェック
        checkGoodsGroupDisp(goodsGroupDto, shopSeq);

        // (2)-3 カテゴリ登録商品チェック
        checkCategoryGoods(goodsGroupDto);

        // (2)-4 関連商品チェック
        checkRelationGoodsGroup(goodsGroupDto, goodsRelationEntityList);

        // (2)-5 商品データチェック
        checkGoodsData(goodsGroupDto, shopSeq);

        // 2023-renew No21 from here
        // (2)-6 よく一緒に購入される商品チェック
        checkGoodsTogetherBuyGroup(goodsGroupDto, goodsTogetherBuyGroupEntityList);
        // 2023-renew No21 to here
    }

    /**
     * 商品グループチェック<br/>
     * <pre>
     * ・商品グループコード重複チェック
     * ・全商品削除チェック
     * ・商品グループ新着日付チェック
     * </pre>
     *
     * @param goodsGroupDto 商品グループDTO
     * @param shopSeq       ショップSEQ
     * @param customParams  案件用引数
     */
    protected void checkGoodsGroup(GoodsGroupDto goodsGroupDto, Integer shopSeq, Object... customParams) {

        // (2)-1-1 商品グループコード重複チェック
        checkGoodsGroupCodeRepetition(goodsGroupDto, shopSeq);

        // (2)-1-2 全商品削除チェック
        checkAllGoodsDeleted(goodsGroupDto);

        // (2)-1-3 商品グループ新着日付チェック
        checkGoodsGroupNewdate(goodsGroupDto);
    }

    /**
     * 商品グループコード重複チェック<br/>
     * <pre>
     * </pre>
     *
     * @param goodsGroupDto 商品グループDTO
     * @param shopSeq       ショップSEQ
     * @param customParams  案件用引数
     */
    protected void checkGoodsGroupCodeRepetition(GoodsGroupDto goodsGroupDto, Integer shopSeq, Object... customParams) {

        // 商品グループエンティティを、商品グループコード(LowerCase)で取得
        // ※パラメータのNULLは考慮してません。(NULLだとここに到達できないので)
        GoodsGroupEntity paramEntity = goodsGroupDto.getGoodsGroupEntity();
        GoodsGroupEntity dbEntity = goodsGroupDao.checkDuplicateByCode(shopSeq, paramEntity.getGoodsGroupCode());
        if (dbEntity == null) {
            return;
        }

        // 取得した商品グループエンティティ．商品グループSEQが（パラメータ）商品グループDTO．商品グループエンティティ．商品グループSEQと異なる場合
        if (!dbEntity.getGoodsGroupSeq().equals(paramEntity.getGoodsGroupSeq())) {
            // 商品グループコード重複エラーをエラーメッセージに追加。（エラーは投げない。）
            addErrorMessage(MSGCD_GOODSGROUPCODE_REPETITION_FAIL,
                            new Object[] {paramEntity.getGoodsGroupCode(), paramEntity.getGoodsGroupCode(), null}
                           );
        }
    }

    /**
     * 全商品削除チェック<br/>
     * <pre>
     * </pre>
     *
     * @param goodsGroupDto 商品グループDTO
     * @param customParams  案件用引数
     */
    protected void checkAllGoodsDeleted(GoodsGroupDto goodsGroupDto, Object... customParams) {

        // （パラメータ）商品グループDTO．商品DTOリストの、 商品DTO．商品エンティティ．販売状態 がすべて"削除"の場合
        boolean allGoodsDeleted = true;
        // CSVアップロード(更新モード)での一部商品のみアップロード時、全商品削除チェック用Set
        Set<Integer> goodsSeqSet = new HashSet<>();
        for (GoodsDto goodsDto : goodsGroupDto.getGoodsDtoList()) {
            GoodsEntity entity = goodsDto.getGoodsEntity();
            goodsSeqSet.add(entity.getGoodsSeq());
            if (HTypeGoodsSaleStatus.DELETED != entity.getSaleStatusPC()) {
                allGoodsDeleted = false;
                break;
            }
        }

        if (!allGoodsDeleted) {
            return;
        }

        // CSVアップロード(更新モード)での一部商品のみアップロード時、その他商品の削除チェック
        if (goodsGroupDto.getGoodsGroupEntity().getGoodsGroupSeq() != null) {
            List<GoodsEntity> goodsEntityList = goodsDao.getGoodsListByGoodsGroupSeq(
                            goodsGroupDto.getGoodsGroupEntity().getGoodsGroupSeq());
            for (int i = 0; goodsEntityList != null && i < goodsEntityList.size(); i++) {
                if (!goodsSeqSet.contains(goodsEntityList.get(i).getGoodsSeq()) && (HTypeGoodsSaleStatus.DELETED
                                                                                    != goodsEntityList.get(i)
                                                                                                      .getSaleStatusPC())) {
                    allGoodsDeleted = false;
                    break;
                }
            }
        }

        if (!allGoodsDeleted) {
            return;
        }

        // 全商品削除エラーをメッセージに追加。（エラーは投げない。）
        addErrorMessage(MSGCD_ALL_GOODS_DELETED_FAIL,
                        new Object[] {goodsGroupDto.getGoodsGroupEntity().getGoodsGroupCode(), null}
                       );
    }

    /**
     * 商品グループ新着日付チェック<br/>
     * <pre>
     * </pre>
     *
     * @param goodsGroupDto 商品グループDTO
     * @param customParams  案件用引数
     */
    protected void checkGoodsGroupNewdate(GoodsGroupDto goodsGroupDto, Object... customParams) {

        // （更新時）商品グループSEQ=null かつ 新着日付がnullの場合エラー(CSVアップロード時のみありうる)
        if (goodsGroupDto.getGoodsGroupEntity().getGoodsGroupSeq() == null
            || goodsGroupDto.getGoodsGroupEntity().getWhatsnewDate() != null) {
            return;
        }

        addErrorMessage(MSGCD_WHATSNEWDATE_NONE_FAIL,
                        new Object[] {goodsGroupDto.getGoodsGroupEntity().getGoodsGroupCode(), null}
                       );
    }

    /**
     * 商品グループ表示チェック<br/>
     * <pre>
     * ・インフォメーションアイコンSEQリストを作成
     * </pre>
     *
     * @param goodsGroupDto 商品グループDTO
     * @param shopSeq       ショップSEQ
     * @param customParams  案件用引数
     */
    protected void checkGoodsGroupDisp(GoodsGroupDto goodsGroupDto, Integer shopSeq, Object... customParams) {

        // (2)-2-1 インフォメーションアイコンSEQリストを作成
        makeGoodsInformationIcon(goodsGroupDto, shopSeq);
    }

    /**
     * インフォメーションアイコンSEQリストを作成<br/>
     * <pre>
     * </pre>
     *
     * @param goodsGroupDto 商品グループDTO
     * @param shopSeq       ショップSEQ
     * @param customParams  案件用引数
     */
    protected void makeGoodsInformationIcon(GoodsGroupDto goodsGroupDto, Integer shopSeq, Object... customParams) {

        // アイコンリストを取得して、マップに格納
        Map<Integer, GoodsInformationIconDetailsDto> iconDetailsMap = makeIconDetailsMap(shopSeq);

        // インフォメーションアイコンからアイコンSEQリストを作成
        makeInformationIconPC(iconDetailsMap, goodsGroupDto);

    }

    /**
     * アイコン詳細マップを作成<br/>
     * <pre>
     * </pre>
     *
     * @param shopSeq      ショップSEQ
     * @param customParams 案件用引数
     * @return アイコン詳細マップ
     */
    protected Map<Integer, GoodsInformationIconDetailsDto> makeIconDetailsMap(Integer shopSeq, Object... customParams) {

        // アイコンリストを取得して、マップに格納する
        Map<Integer, GoodsInformationIconDetailsDto> iconDetailsMap = new HashMap<>();
        List<GoodsInformationIconDetailsDto> detailsList =
                        goodsInformationIconDao.getGoodsInformationIconDetailsDtoList(shopSeq);
        for (GoodsInformationIconDetailsDto detailsDto : detailsList) {
            iconDetailsMap.put(detailsDto.getIconSeq(), detailsDto);
        }

        return iconDetailsMap;
    }

    /**
     * インフォメーションアイコンからアイコンSEQリストを作成<br/>
     * <pre>
     * </pre>
     *
     * @param iconDetailsMap アイコン詳細マップ
     * @param goodsGroupDto  商品グループDTO
     * @param customParams   案件用引数
     */
    protected void makeInformationIconPC(Map<Integer, GoodsInformationIconDetailsDto> iconDetailsMap,
                                         GoodsGroupDto goodsGroupDto,
                                         Object... customParams) {

        GoodsGroupDisplayEntity goodsGroupDisplayEntity = goodsGroupDto.getGoodsGroupDisplayEntity();
        GoodsGroupEntity goodsGroupEntity = goodsGroupDto.getGoodsGroupEntity();

        if (goodsGroupDisplayEntity.getInformationIconPC() == null || "".equals(
                        goodsGroupDisplayEntity.getInformationIconPC())) {
            return;
        }

        // インフォメーションアイコンPCからアイコンSEQリストを作成する
        String[] iconSeqList = goodsGroupDisplayEntity.getInformationIconPC().split("/");
        StringBuilder iconListPc = new StringBuilder();
        for (String iconSeq : iconSeqList) {
            if (iconDetailsMap.get(Integer.parseInt(iconSeq)) != null && !StringUtils.isEmpty(
                            iconDetailsMap.get(Integer.parseInt(iconSeq)).getColorCode())) {
                iconListPc.append(iconSeq).append("/");
            } else {
                addErrorMessage(MSGCD_INFORMATIONICONPC_NONE_FAIL,
                                new Object[] {iconSeq, goodsGroupEntity.getGoodsGroupCode(), null}
                               );
            }
        }
        if (iconListPc.length() == 0) {
            goodsGroupDisplayEntity.setInformationIconPC(null);
            return;
        }
        // 最後の / を取り除く
        String iconList = iconListPc.toString().substring(0, iconListPc.length() - 1).toString();
        goodsGroupDisplayEntity.setInformationIconPC(iconList);
    }

    /**
     * カテゴリ登録商品チェック<br/>
     * <pre>
     * ・カテゴリ存在チェック
     * </pre>
     *
     * @param goodsGroupDto 商品グループDTO
     * @param customParams  案件用引数
     */
    protected void checkCategoryGoods(GoodsGroupDto goodsGroupDto, Object... customParams) {

        // (2)-3-1 カテゴリ存在チェック
        checkCategoryGoodsExist(goodsGroupDto);
    }

    /**
     * カテゴリ存在チェック<br/>
     * <pre>
     * </pre>
     *
     * @param goodsGroupDto 商品グループDTO
     * @param customParams  案件用引数
     */
    protected void checkCategoryGoodsExist(GoodsGroupDto goodsGroupDto, Object... customParams) {

        if (goodsGroupDto.getCategoryGoodsEntityList() == null) {
            return;
        }

        List<Integer> categorySeqList = new ArrayList<>();
        // （パラメータ）商品グループDTO．カテゴリ登録商品エンティティリスト のカテゴリSEQから、 カテゴリSEQリスト を作成する。
        for (CategoryGoodsEntity categoryGoodsEntity : goodsGroupDto.getCategoryGoodsEntityList()) {
            if (categorySeqList.contains(categoryGoodsEntity.getCategorySeq())) {
                // 登録カテゴリ重複エラーをエラーメッセージに追加。（エラーは投げない。）
                addErrorMessage(MSGCD_CATEGORY_MULTI_REGIST_FAIL,
                                new Object[] {goodsGroupDto.getGoodsGroupEntity().getGoodsGroupCode().toString(),
                                                categoryGoodsEntity.getCategorySeq().toString(),
                                                goodsGroupDto.getGoodsGroupEntity().getGoodsGroupCode(), null}
                               );
                continue;
            }
            categorySeqList.add(categoryGoodsEntity.getCategorySeq());
        }

        if (categorySeqList == null || categorySeqList.size() == 0) {
            return;
        }

        List<CategoryEntity> categoryEntityList = categoryDao.getCategoryListBySeqList(categorySeqList);
        if (categorySeqList.size() == categoryEntityList.size()) {
            return;
        }

        // カテゴリエンティティマップを作成する
        Map<Integer, CategoryEntity> categoryEntityMap = new HashMap<>();
        for (CategoryEntity categoryEntity : categoryEntityList) {
            categoryEntityMap.put(categoryEntity.getCategorySeq(), categoryEntity);
        }
        for (Integer categorySeq : categorySeqList) {
            if (categoryEntityMap.get(categorySeq) == null) {
                // カテゴリなしエラーをエラーメッセージに追加。（エラーは投げない。）
                addErrorMessage(
                                MSGCD_CATEGORY_NONE_FAIL, new Object[] {categorySeq.toString(),
                                                goodsGroupDto.getGoodsGroupEntity().getGoodsGroupCode(), null});
            }
        }
    }

    /**
     * 関連商品チェック<br/>
     * <pre>
     * ・商品グループ存在チェック
     * </pre>
     *
     * @param goodsGroupDto           商品グループDTO
     * @param goodsRelationEntityList 商品関連エンティティリスト
     * @param customParams            案件用引数
     */
    protected void checkRelationGoodsGroup(GoodsGroupDto goodsGroupDto,
                                           List<GoodsRelationEntity> goodsRelationEntityList,
                                           Object... customParams) {

        // (2)-4-1 商品グループ存在チェック
        checkRelationGoodsGroupExist(goodsGroupDto, goodsRelationEntityList);
    }

    // 2023-renew No21 from here
    /**
     * よく一緒に購入される商品チェック<br/>
     * <pre>
     * ・商品グループ存在チェック
     * </pre>
     *
     * @param goodsGroupDto           商品グループDTO
     * @param goodsTogetherBuyGroupEntityList よく一緒に購入される商品エンティティリスト
     * @param customParams            案件用引数
     */
    protected void checkGoodsTogetherBuyGroup(GoodsGroupDto goodsGroupDto,
                                           List<GoodsTogetherBuyGroupEntity> goodsTogetherBuyGroupEntityList,
                                           Object... customParams) {

        // (2)-4-1 商品グループ存在チェック
        checkGoodsTogetherBuyGroupExist(goodsGroupDto, goodsTogetherBuyGroupEntityList);
    }
    // 2023-renew No21 to here

    /**
     * 商品グループ存在チェック<br/>
     * <pre>
     * </pre>
     *
     * @param goodsGroupDto           商品グループDTO
     * @param goodsRelationEntityList 商品関連エンティティリスト
     * @param customParams            案件用引数
     */
    protected void checkRelationGoodsGroupExist(GoodsGroupDto goodsGroupDto,
                                                List<GoodsRelationEntity> goodsRelationEntityList,
                                                Object... customParams) {

        // （パラメータ）関連商品エンティティリスト の関連商品グループSEQから、 関連商品グループSEQリスト を作成する。
        List<Integer> relationGoodsGroupSeqList = new ArrayList<>();
        for (GoodsRelationEntity goodsRelationEntity : goodsRelationEntityList) {
            if (relationGoodsGroupSeqList.contains(goodsRelationEntity.getGoodsRelationGroupSeq())) {
                // 関連商品重複エラーをエラーメッセージに追加。（エラーは投げない。）
                addErrorMessage(MSGCD_GOODSRELATION_MULTI_REGIST_FAIL,
                                new Object[] {goodsGroupDto.getGoodsGroupEntity().getGoodsGroupCode().toString(),
                                                goodsRelationEntity.getGoodsRelationGroupSeq().toString(),
                                                goodsGroupDto.getGoodsGroupEntity().getGoodsGroupCode(), null}
                               );
                continue;
            }
            relationGoodsGroupSeqList.add(goodsRelationEntity.getGoodsRelationGroupSeq());
        }

        if (relationGoodsGroupSeqList == null || relationGoodsGroupSeqList.size() == 0) {
            return;
        }

        List<GoodsGroupEntity> goodsGroupEntityList = goodsGroupDao.getGoodsGroupList(relationGoodsGroupSeqList);
        if (relationGoodsGroupSeqList.size() == goodsGroupEntityList.size()) {
            return;
        }

        // 関連商品エンティティマップを作成する
        Map<Integer, GoodsGroupEntity> goodsGroupEntityMap = new HashMap<>();
        for (GoodsGroupEntity tmpGoodsGroupEntity : goodsGroupEntityList) {
            goodsGroupEntityMap.put(tmpGoodsGroupEntity.getGoodsGroupSeq(), tmpGoodsGroupEntity);
        }
        for (Integer relationGoodsGroupSeq : relationGoodsGroupSeqList) {
            if (goodsGroupEntityMap.get(relationGoodsGroupSeq) == null) {
                // 関連商品グループなしエラーをエラーメッセージに追加。（エラーは投げない。）
                addErrorMessage(
                                MSGCD_RELATION_GOODSGROUP_NONE_FAIL, new Object[] {relationGoodsGroupSeq.toString(),
                                                goodsGroupDto.getGoodsGroupEntity().getGoodsGroupCode(), null});
            }
        }
    }

    // 2023-renew No21 from here
    /**
     * 商品グループ存在チェック<br/>
     * <pre>
     * </pre>
     *
     * @param goodsGroupDto           商品グループDTO
     * @param goodsTogetherBuyGroupEntityList よく一緒に購入される商品エンティティリスト
     * @param customParams            案件用引数
     */
    protected void checkGoodsTogetherBuyGroupExist(GoodsGroupDto goodsGroupDto,
                                                List<GoodsTogetherBuyGroupEntity> goodsTogetherBuyGroupEntityList,
                                                Object... customParams) {

        // （パラメータ）よく一緒に購入される商品エンティティリスト のよく一緒に購入される商品グループSEQから、よく一緒に購入される商品グループSEQリスト を作成する。
        List<Integer> goodsTogetherBuyGroupSeqList = new ArrayList<>();
        for (GoodsTogetherBuyGroupEntity goodsTogetherBuyGroupEntity : goodsTogetherBuyGroupEntityList) {
            if (HTypeRegisterMethodType.BACK.equals(goodsTogetherBuyGroupEntity.getRegistMethod()) && goodsTogetherBuyGroupSeqList.contains(goodsTogetherBuyGroupEntity.getGoodsTogetherBuyGroupSeq())) {
                // よく一緒に購入される商品重複エラーをエラーメッセージに追加。（エラーは投げない。）
                addErrorMessage(
                                MSGCD_GOODS_TOGETHER_BUY_GROUP_MULTI_REGIST_FAIL,
                                new Object[] {goodsGroupDto.getGoodsGroupEntity().getGoodsGroupCode(),
                                                goodsTogetherBuyGroupEntity.getGoodsTogetherBuyGroupSeq().toString(),
                                                goodsGroupDto.getGoodsGroupEntity().getGoodsGroupCode(), null}
                               );
                continue;
            }
            goodsTogetherBuyGroupSeqList.add(goodsTogetherBuyGroupEntity.getGoodsTogetherBuyGroupSeq());
        }

        if (ObjectUtils.isEmpty(goodsTogetherBuyGroupSeqList)) {
            return;
        }

        List<GoodsGroupEntity> goodsGroupEntityList = goodsGroupDao.getGoodsGroupList(goodsTogetherBuyGroupSeqList);
        if (goodsTogetherBuyGroupSeqList.size() == goodsGroupEntityList.size()) {
            return;
        }

        // よく一緒に購入される商品エンティティマップを作成する
        Map<Integer, GoodsGroupEntity> goodsGroupEntityMap = new HashMap<>();
        for (GoodsGroupEntity tmpGoodsGroupEntity : goodsGroupEntityList) {
            goodsGroupEntityMap.put(tmpGoodsGroupEntity.getGoodsGroupSeq(), tmpGoodsGroupEntity);
        }
        for (Integer goodsTogetherBuyGroupSeq : goodsTogetherBuyGroupSeqList) {
            if (goodsGroupEntityMap.get(goodsTogetherBuyGroupSeq) == null) {
                // よく一緒に購入される商品グループなしエラーをエラーメッセージに追加。（エラーは投げない。）
                addErrorMessage(
                                MSGCD_GOODS_TOGETHER_BUY_GROUP_NONE_FAIL, new Object[] {goodsTogetherBuyGroupSeq.toString(),
                                                goodsGroupDto.getGoodsGroupEntity().getGoodsGroupCode(), null});
            }
        }
    }
    // 2023-renew No21 to here

    /**
     * 商品データチェック<br/>
     * <pre>
     * </pre>
     *
     * @param goodsGroupDto 商品グループDTO
     * @param shopSeq       ショップSEQ
     * @param customParams  案件用引数
     */
    protected void checkGoodsData(GoodsGroupDto goodsGroupDto, Integer shopSeq, Object... customParams) {

        List<GoodsEntity> goodsEntityList = new ArrayList<>();
        for (GoodsDto goodsDto : goodsGroupDto.getGoodsDtoList()) {
            goodsEntityList.add(goodsDto.getGoodsEntity());
        }
        try {
            goodsDataCheckLogic.execute(
                            goodsEntityList, shopSeq, goodsGroupDto.getGoodsGroupEntity().getGoodsGroupCode());
        } catch (AppLevelListException appe) {
            // 商品データチェックLogicで発生したエラーをcatchして追加
            LOGGER.error("例外処理が発生しました", appe);
            addErrorMessage(appe);
        }
    }
}
