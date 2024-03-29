/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.goods.goodsgroup.impl;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeGoodsSaleStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOpenDeleteStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSiteType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeStockStatusType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeUploadType;
import jp.co.hankyuhanshin.itec.hitmall.dao.goods.goodsgroup.GoodsGroupDao;
import jp.co.hankyuhanshin.itec.hitmall.dao.goods.goodsgroup.stock.StockStatusDisplayDao;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goods.GoodsDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goods.GoodsSearchForDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goodsgroup.GoodsGroupDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.icon.GoodsInformationIconDetailsDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goodsgroup.GoodsGroupDisplayEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goodsgroup.GoodsGroupEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goodsgroup.GoodsGroupImageEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goodsgroup.stock.StockStatusDisplayEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.goods.GoodsListGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.goods.GoodsStockStatusGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.goodsgroup.GoodsGroupDetailsGetByCodeLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.goodsgroup.GoodsGroupDisplayGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.goodsgroup.GoodsGroupImageGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.goodsgroup.GoodsInformationIconGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.utility.GoodsUtility;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.DateUtility;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * 商品グループ詳細取得<br/>
 *
 * @author ozaki
 */
@Component
public class GoodsGroupDetailsGetByCodeLogicImpl extends AbstractShopLogic implements GoodsGroupDetailsGetByCodeLogic {

    // PDR Migrate Customization from here
    /** アップロード区分 */
    public static HTypeUploadType uploadType = null;
    // PDR Migrate Customization to here

    /**
     * 商品グループ在庫表示DAO
     */
    private final StockStatusDisplayDao stockStatusDisplayDao;

    /**
     * 商品グループDAO
     */
    private final GoodsGroupDao goodsGroupDao;

    /**
     * 商品グループ画像取得ロジッククラス
     */
    private final GoodsGroupImageGetLogic goodsGroupImageGetLogic;

    /**
     * 商品グループ表示取得ロジッククラス
     */
    private final GoodsGroupDisplayGetLogic goodsGroupDisplayGetLogic;

    /**
     * 商品一覧取得ロジッククラス
     */
    private final GoodsListGetLogic goodsListGetLogic;

    /**
     * 商品インフォメーションアイコンリスト取得ロジック
     */
    private final GoodsInformationIconGetLogic goodsInformationIconGetLogic;

    /**
     * 商品の在庫状態取得ロジック
     */
    private final GoodsStockStatusGetLogic goodsStockStatusGetLogic;

    /**
     * DateUtility
     */
    private final DateUtility dateUtility;

    /**
     * 商品Utility
     */
    private final GoodsUtility goodsUtility;

    @Autowired
    public GoodsGroupDetailsGetByCodeLogicImpl(StockStatusDisplayDao stockStatusDisplayDao,
                                               GoodsGroupDao goodsGroupDao,
                                               GoodsGroupImageGetLogic goodsGroupImageGetLogic,
                                               GoodsGroupDisplayGetLogic goodsGroupDisplayGetLogic,
                                               GoodsListGetLogic goodsListGetLogic,
                                               GoodsInformationIconGetLogic goodsInformationIconGetLogic,
                                               GoodsStockStatusGetLogic goodsStockStatusGetLogic,
                                               DateUtility dateUtility,
                                               GoodsUtility goodsUtility) {
        this.stockStatusDisplayDao = stockStatusDisplayDao;
        this.goodsGroupDao = goodsGroupDao;
        this.goodsGroupImageGetLogic = goodsGroupImageGetLogic;
        this.goodsGroupDisplayGetLogic = goodsGroupDisplayGetLogic;
        this.goodsListGetLogic = goodsListGetLogic;
        this.goodsInformationIconGetLogic = goodsInformationIconGetLogic;
        this.goodsStockStatusGetLogic = goodsStockStatusGetLogic;
        this.dateUtility = dateUtility;
        this.goodsUtility = goodsUtility;
    }

    /**
     * 商品グループ情報取得<br/>
     *
     * @param shopSeq        ショップSEQ
     * @param goodsGroupCode 商品グループコード
     * @param goodsCode      商品コード
     * @param siteType       サイト区分
     * @param openStatus     公開状態
     * @param customParams   案件用引数
     * @return 商品グループエンティティ
     */
    @Override
    public GoodsGroupEntity getGoodsGroup(Integer shopSeq,
                                          String goodsGroupCode,
                                          String goodsCode,
                                          HTypeSiteType siteType,
                                          HTypeOpenDeleteStatus openStatus,
                                          Object... customParams) {
        return getGoodsGroup(shopSeq, goodsGroupCode, goodsCode, siteType, openStatus);
    }

    /**
     * @param shopSeq        ショップSEQ
     * @param goodsGroupCode 商品グループコード
     * @param goodsCode      商品コード
     * @param siteType       サイト区分
     * @param openStatus     公開状態
     * @return 商品グループ情報DTO
     * @see GoodsGroupDetailsGetByCodeLogicImpl#execute(Integer, String, String, HTypeSiteType, HTypeSiteType, HTypeGoodsOpenStatus, Boolean)
     * @see jp.co.hankyuhanshin.itec.hitmall.logic.goods.goodsgroup.impl.GoodsGroupDetailsGetByCodeLogicImpl#execute(Integer, String, String, HTypeSiteType, HTypeSiteType, HTypeGoodsOpenStatus, Boolean)
     * @see jp.co.hankyuhanshin.itec.hitmall.service.goods.group.GoodsGroupGetService#executeForPreview(String, HTypeSiteType)
     * 商品グループ詳細取得<br/>
     * 商品グループコードから、商品グループ詳細DTOを取得する。<br/>
     */
    @Override
    public GoodsGroupDto execute(Integer shopSeq,
                                 String goodsGroupCode,
                                 String goodsCode,
                                 HTypeSiteType siteType,
                                 HTypeOpenDeleteStatus openStatus) {
        return execute(shopSeq, goodsGroupCode, goodsCode, siteType, openStatus, null);
    }

    /**
     * 商品グループ詳細取得<br/>
     * 商品グループコードから、商品グループ詳細DTOを取得する。<br/>
     *
     * @param shopSeq        ショップSEQ
     * @param goodsGroupCode 商品グループコード
     * @param goodsCode      商品コード
     * @param siteType       サイト区分
     * @param openStatus     公開状態
     * @param imageGetFlag   画像取得フラグ
     * @return 商品グループ情報DTO
     */
    @Override
    public GoodsGroupDto execute(Integer shopSeq,
                                 String goodsGroupCode,
                                 String goodsCode,
                                 HTypeSiteType siteType,
                                 HTypeOpenDeleteStatus openStatus,
                                 Boolean imageGetFlag) {
        String goodsInfo = null;
        if (StringUtils.isNotEmpty(goodsGroupCode)) {
            goodsInfo = goodsGroupCode;
        }
        if (StringUtils.isNotEmpty(goodsCode)) {
            goodsInfo = goodsCode;
        }

        // パラメータチェック
        checkParameter(shopSeq, goodsInfo);

        // 商品グループ情報取得
        GoodsGroupEntity goodsGroupEntity = getGoodsGroup(shopSeq, goodsGroupCode, goodsCode, siteType, openStatus);
        if (goodsGroupEntity == null) {
            return null;
        }

        // 商品グループDTO作成
        GoodsGroupDto goodsGroupDto = makeGoodsGroupDto(goodsGroupEntity, shopSeq, siteType, imageGetFlag);

        // 商品グループDTOに在庫状況情報を追加
        addStockStatusInfo(goodsGroupDto, siteType, shopSeq);

        // 戻り値
        return goodsGroupDto;
    }

    /**
     * 商品グループDTOに在庫状況を追加<br/>
     *
     * @param goodsGroupDto 商品グループDTO
     * @param siteType      サイト種別
     * @param shopSeq       ショップSEQ
     */
    protected void addStockStatusInfo(GoodsGroupDto goodsGroupDto, HTypeSiteType siteType, Integer shopSeq) {

        // 在庫状態更新バッチ実行時点での商品グループ在庫状態を取得して設定
        StockStatusDisplayEntity batchUpdateStockStatus =
                        stockStatusDisplayDao.getEntity(goodsGroupDto.getGoodsGroupEntity().getGoodsGroupSeq());
        if (batchUpdateStockStatus == null) {
            // 前回の在庫状況更新バッチ実行後に登録された商品の在庫状況はまだ存在しない
            goodsGroupDto.setBatchUpdateStockStatus(getComponent(StockStatusDisplayEntity.class));
        } else {
            goodsGroupDto.setBatchUpdateStockStatus(batchUpdateStockStatus);
        }

        List<GoodsDto> goodsDtoList = goodsGroupDto.getGoodsDtoList();

        // 在庫状態テーブルから取得した在庫状態表示をリアルタイムの在庫状態表示で上書きします。
        // 在庫管理しない商品でも在庫及び在庫設定は存在する為、リアルタイム在庫状況が null ということはあり得ない。
        StockStatusDisplayEntity realTimeStockStatus = getComponent(StockStatusDisplayEntity.class);

        // 規格単位のリアルタイム在庫状況を取得
        Map<Integer, HTypeStockStatusType> stockStatusPcMap = goodsStockStatusGetLogic.execute(goodsDtoList, shopSeq);

        // 商品DTOにリアルタイム在庫状況を設定
        setGoodsRealTimeStockStatus(goodsDtoList, stockStatusPcMap);

        // 商品グループ単位に変換しセット
        realTimeStockStatus.setStockStatusPc(goodsUtility.convertGoodsGroupStockStatus(goodsDtoList, stockStatusPcMap));

        // 商品グループのリアルタイム在庫状況を設定
        goodsGroupDto.setRealTimeStockStatus(realTimeStockStatus);

    }

    /**
     * 商品DTOに在庫状況を設定する<br/>
     *
     * @param goodsDtoList     商品DTOリスト
     * @param stockStatusPcMap PC在庫状況MAP＜商品SEQ、在庫状況＞
     */
    protected void setGoodsRealTimeStockStatus(List<GoodsDto> goodsDtoList,
                                               Map<Integer, HTypeStockStatusType> stockStatusPcMap) {
        for (GoodsDto dto : goodsDtoList) {
            if (stockStatusPcMap.containsKey(dto.getGoodsEntity().getGoodsSeq())) {
                dto.setStockStatusPc(stockStatusPcMap.get(dto.getGoodsEntity().getGoodsSeq()));
            }
        }
    }

    /**
     * 商品グループDTO作成<br/>
     *
     * @param goodsGroupEntity 商品グループエンティティ
     * @param shopSeq          ショップSEQ
     * @param siteType         サイトタイプ
     * @param imageGetFlag     画像取得フラグ
     * @return 商品グループDTO
     */
    protected GoodsGroupDto makeGoodsGroupDto(GoodsGroupEntity goodsGroupEntity,
                                              Integer shopSeq,
                                              HTypeSiteType siteType,
                                              Boolean imageGetFlag) {

        Integer goodsGroupSeq = goodsGroupEntity.getGoodsGroupSeq();

        // 商品グループ画像情報取得
        Map<Integer, List<GoodsGroupImageEntity>> goodsGroupImageListMap = getGoodsGroupImageListMap(goodsGroupSeq);

        // 商品グループ表示情報取得
        GoodsGroupDisplayEntity goodsGroupDisplayEntity = getGoodsGroupDisplay(goodsGroupSeq);

        // 商品インフォメーションアイコンリスト取得
        List<GoodsInformationIconDetailsDto> goodsInformationIconDetailsDtoList =
                        getGoodsInformationIconDetailsDtoList(siteType, goodsGroupDisplayEntity);

        // 商品情報リスト取得
        List<GoodsDto> goodsDtoList = getGoodsDtoList(siteType, goodsGroupSeq);

        // 商品グループDTOの編集
        GoodsGroupDto goodsGroupDto = createGoodsGroupDto(goodsGroupEntity, goodsGroupSeq, goodsGroupImageListMap,
                                                          goodsGroupDisplayEntity, goodsInformationIconDetailsDtoList,
                                                          goodsDtoList
                                                         );

        return goodsGroupDto;
    }

    /**
     * 販売中かを判定<br/>
     * 販売以外または販売期間外は対象外
     *
     * @param goodsDto 商品DTO
     * @param siteType サイト種別
     * @return true：対象外
     */
    protected boolean judgNoSale(GoodsDto goodsDto, HTypeSiteType siteType) {

        // サイト種別で判定用項目を決定
        HTypeGoodsSaleStatus saleStatus = null;
        Timestamp startTime = null;
        Timestamp endTime = null;
        saleStatus = goodsDto.getGoodsEntity().getSaleStatusPC();
        startTime = goodsDto.getGoodsEntity().getSaleStartTimePC();
        endTime = goodsDto.getGoodsEntity().getSaleEndTimePC();

        // 販売以外は対象外
        if (!HTypeGoodsSaleStatus.SALE.equals(saleStatus)) {
            return true;
        }

        // 販売期間外は対象外
        if (!dateUtility.isOpen(startTime, endTime)) {
            return true;
        }
        return false;

    }

    /**
     * 入力パラメータチェック<br/>
     *
     * @param shopSeq      ショップSEQ
     * @param goodsInfo    商品グループコード or 商品コード
     * @param customParams 案件用引数
     */
    protected void checkParameter(Integer shopSeq, String goodsInfo, Object... customParams) {
        // ショップSEQが null でないかをチェック
        // 商品グループコード or 商品コードが null でないかをチェック
        ArgumentCheckUtil.assertNotNull("shopSeq", shopSeq);
        ArgumentCheckUtil.assertNotEmpty("goodsGroupCode", goodsInfo);
    }

    /**
     * 商品グループ情報取得<br/>
     *
     * @param shopSeq        ショップSEQ
     * @param goodsGroupCode 商品グループコード
     * @param goodsCode      商品コード
     * @param siteType       サイト区分
     * @param openStatus     公開状態
     * @return 商品グループエンティティ
     */
    protected GoodsGroupEntity getGoodsGroup(Integer shopSeq,
                                             String goodsGroupCode,
                                             String goodsCode,
                                             HTypeSiteType siteType,
                                             HTypeOpenDeleteStatus openStatus) {
        // （パラメータ）ショップSEQ、（パラメータ）商品グループコードをもとに、商品グループエンティティを取得する
        // 商品グループDaoの商品グループ取得処理を実行する。
        return goodsGroupDao.getGoodsGroupByCode(shopSeq, goodsGroupCode, goodsCode, siteType, openStatus);
    }

    /**
     * 商品グループ画像情報取得<br/>
     *
     * @param goodsGroupSeq 商品グループSEQ
     * @param customParams  案件用引数
     * @return 商品グループ画像マップ
     */
    protected Map<Integer, List<GoodsGroupImageEntity>> getGoodsGroupImageListMap(Integer goodsGroupSeq,
                                                                                  Object... customParams) {
        // (2)で取得した商品グループエンティティの商品グループエンティティ．商品グループSEQをもとに商品グループSEQリスト（1件）を作成する。
        // 作成した商品グループSEQリストをもとに、商品グループ画像取得Logicを利用して、商品グループ画像マップを取得する
        // Logic GoodsGroupImageGetLogic
        // パラメータ 商品グループSEQリスト
        // 戻り値 商品グループ画像マップ
        List<Integer> goodsGroupSeqList = new ArrayList<>();
        goodsGroupSeqList.add(goodsGroupSeq);
        return goodsGroupImageGetLogic.execute(goodsGroupSeqList);
    }

    /**
     * 商品グループ表示情報取得<br/>
     *
     * @param goodsGroupSeq 商品グループSEQ
     * @param customParams  案件用引数
     * @return 商品グループ表示エンティティ
     */
    protected GoodsGroupDisplayEntity getGoodsGroupDisplay(Integer goodsGroupSeq, Object... customParams) {
        // 商品グループ表示取得Logicを利用して、商品グループ表示情報を取得する
        // Logic GoodsGroupDisplayGetLogic
        // パラメータ 商品グループSEQ
        // 戻り値 商品グループ表示エンティティ
        return goodsGroupDisplayGetLogic.execute(goodsGroupSeq);
    }

    /**
     * 商品インフォメーションアイコンリスト取得<br/>
     *
     * @param siteType                サイト区分
     * @param goodsGroupDisplayEntity 商品グループ表示エンティティ
     * @param customParams            案件用引数
     * @return 商品インフォメーションアイコンリスト
     */
    protected List<GoodsInformationIconDetailsDto> getGoodsInformationIconDetailsDtoList(HTypeSiteType siteType,
                                                                                         GoodsGroupDisplayEntity goodsGroupDisplayEntity,
                                                                                         Object... customParams) {
        // 商品グループ表示情報の商品インフォメーションアイコンを元に、商品インフォメーションアイコン取得Logicを利用して、
        // 商品インフォメーションアイコンエンティティリストを取得する。
        // Logic GoodsInformationIconGetLogic
        // パラメータ 商品グループ表示．インフォメーションアイコン
        // 戻り値 商品インフォメーションアイコンマップ
        List<GoodsInformationIconDetailsDto> goodsInformationIconDetailsDtoList = new ArrayList<>();
        if (!"".equals(goodsGroupDisplayEntity.getInformationIconPC())) {
            String[] informationIconSeqArray = null;
            if ((siteType == HTypeSiteType.FRONT_PC || siteType == HTypeSiteType.FRONT_SP)
                && goodsGroupDisplayEntity.getInformationIconPC() != null) {
                informationIconSeqArray = goodsGroupDisplayEntity.getInformationIconPC().split("/");
            } else if (siteType == HTypeSiteType.BACK) {
                // TreeSet
                Set<String> treeSet = new TreeSet<>();
                if (goodsGroupDisplayEntity.getInformationIconPC() != null && !"".equals(
                                goodsGroupDisplayEntity.getInformationIconPC())) {
                    treeSet.addAll(Arrays.asList(goodsGroupDisplayEntity.getInformationIconPC().split("/")));
                }
                informationIconSeqArray = treeSet.toArray(new String[] {});
            }
            if (informationIconSeqArray != null) {
                List<Integer> informationIconSeqList = new ArrayList<>();
                for (int i = 0; i < informationIconSeqArray.length; i++) {
                    if (!informationIconSeqArray[i].equals("")) {
                        informationIconSeqList.add(Integer.parseInt(informationIconSeqArray[i]));
                    }
                }
                if (informationIconSeqList.size() > 0) {
                    goodsInformationIconDetailsDtoList = goodsInformationIconGetLogic.execute(informationIconSeqList);

                }
            }
        }
        return goodsInformationIconDetailsDtoList;
    }

    /**
     * 商品情報リスト取得<br/>
     *
     * @param siteType      サイト区分
     * @param goodsGroupSeq 商品グループSEQ
     * @param customParams  案件用引数
     * @return 商品情報リスト
     */
    protected List<GoodsDto> getGoodsDtoList(HTypeSiteType siteType, Integer goodsGroupSeq, Object... customParams) {
        // 商品Dao用検索条件DTOを生成して、以下の項目を設定する
        // 項目名 設定値
        // 商品グループSEQ （(2)で取得した商品グループエンティティ）商品グループエンティティ．商品グループSEQ
        // 並び順 " order by goods.orderdisplay asc " （商品テーブル．並び順 の昇順）
        GoodsSearchForDaoConditionDto goodsSearchForDaoConditionDto = getComponent(GoodsSearchForDaoConditionDto.class);
        goodsSearchForDaoConditionDto.setGoodsGroupSeq(goodsGroupSeq);
        goodsSearchForDaoConditionDto.setOrderField("orderdisplay");
        goodsSearchForDaoConditionDto.setOrderAsc(true);
        // PDR Migrate Customization from here
        if (!siteType.isBack()) {
            goodsSearchForDaoConditionDto.setSaleStatus(HTypeGoodsSaleStatus.SALE);
        }
        if (uploadType != null) {
            goodsSearchForDaoConditionDto.setCsvUploadType(uploadType);
        }
        // 入れたら初期化
        uploadType = null;
        // PDR Migrate Customization to here

        // 商品Dao用検索条件DTOをもとに、商品リスト取得Logicを利用して、商品DTOリストを取得する
        // Logic GoodsListGetLogic
        // パラメータ 商品Dao用検索条件DTO
        // 戻り値 商品DTOリスト
        List<GoodsDto> goodsDtoList = goodsListGetLogic.execute(goodsSearchForDaoConditionDto);
        return goodsDtoList;
    }

    /**
     * 商品グループDTOの編集<br/>
     *
     * @param goodsGroupEntity                   商品グループエンティティ
     * @param goodsGroupSeq                      商品グループSEQ
     * @param goodsGroupImageListMap             商品グループ画像マップ
     * @param goodsGroupDisplayEntity            商品グループ表示エンティティ
     * @param goodsInformationIconDetailsDtoList 商品インフォメーションアイコンリスト
     * @param goodsDtoList                       商品DTOリスト
     * @return 商品グループDTO
     */
    protected GoodsGroupDto createGoodsGroupDto(GoodsGroupEntity goodsGroupEntity,
                                                Integer goodsGroupSeq,
                                                Map<Integer, List<GoodsGroupImageEntity>> goodsGroupImageListMap,
                                                GoodsGroupDisplayEntity goodsGroupDisplayEntity,
                                                List<GoodsInformationIconDetailsDto> goodsInformationIconDetailsDtoList,
                                                List<GoodsDto> goodsDtoList) {
        // ①（戻り値用）商品グループDTOを初期化する。
        // ②（(2)で取得した）商品グループエンティティを、（戻り値用）商品グループDTO．商品グループエンティティ にセットする。
        // ③（(2)で取得した）商品グループエンティティ．商品グループSEQをキーに、（(3)で取得した）商品グループ画像マップから商品グループ画像エンティティリストを取得し、
        // （戻り値用）商品グループDTO．商品グループ画像エンティティリスト にセットする。
        // ④（(4)で取得した）商品グループ表示エンティティリストを、（戻り値用）商品グループDTO．商品グループ表示エンティティ
        // にセットする。
        // ⑤（(5)で取得した）商品DTOリストを、（戻り値用）商品グループDTO．商品DTOリスト にセットする。
        GoodsGroupDto goodsGroupDto = getComponent(GoodsGroupDto.class);
        goodsGroupDto.setGoodsGroupEntity(goodsGroupEntity);
        goodsGroupDto.setGoodsGroupImageEntityList(goodsGroupImageListMap.get(goodsGroupSeq));
        goodsGroupDto.setGoodsGroupDisplayEntity(goodsGroupDisplayEntity);
        goodsGroupDto.setGoodsDtoList(goodsDtoList);
        goodsGroupDto.setGoodsInformationIconDetailsDtoList(goodsInformationIconDetailsDtoList);
        return goodsGroupDto;
    }

    /**
     * 非販売商品を除外<br/>
     *
     * @param siteType      サイト種別
     * @param goodsGroupDto 商品グループDTO
     */
    protected void excludeNoSaleGoods(HTypeSiteType siteType, GoodsGroupDto goodsGroupDto) {
        Iterator<GoodsDto> it = goodsGroupDto.getGoodsDtoList().iterator();
        while (it.hasNext()) {
            GoodsDto goodsDto = it.next();
            if (HTypeSiteType.FRONT_PC.equals(siteType) || HTypeSiteType.FRONT_SP.equals(siteType)) {
                if (!goodsUtility.isGoodsSalesPc(goodsDto.getGoodsEntity())) {
                    it.remove();
                }
            }
        }
    }
    // PDR Migrate Customization from here

    /**
     * アップロード区分取得<br/>
     *
     * @param csvUploadType アップロード区分
     */
    public void setUploadType(HTypeUploadType csvUploadType) {
        uploadType = csvUploadType;
    }

    // 2023-renew No92 from here
    /**
     * 商品グループ情報リスト取得
     *
     * @param goodsCodes    商品コードリスト
     * @return  商品グループ情報DTOリスト
     */
    @Override
    public List<GoodsGroupDto> execute(List<String> goodsCodes) {
        // 商品情報リスト取得
        List<GoodsDto> goodsDtoList = getGoodsDtoListByGoodsCodeList(goodsCodes);

        // 商品グループリスト情報取得
        List<Integer> goodsGroupSeqs = goodsDtoList.stream()
                        .map(goodsDto -> goodsDto.getGoodsEntity().getGoodsGroupSeq())
                        .collect(Collectors.toList());
        List<GoodsGroupEntity> goodsGroupEntities = getGoodsGroupListBySeqList(goodsGroupSeqs);

        // 商品グループDTOリスト作成
        return goodsGroupEntities.stream()
                        .map(goodsGroupEntity -> createGoodsGroupDto(
                                        goodsGroupEntity,
                                        goodsGroupEntity.getGoodsGroupSeq(),
                                        new HashMap<>(),
                                        null,
                                        null,
                                        goodsDtoList.stream()
                                                        .filter(goodsDto -> goodsGroupEntity.getGoodsGroupSeq().equals(goodsDto.getGoodsEntity().getGoodsGroupSeq()))
                                                        .collect(Collectors.toList())))
                        .collect(Collectors.toList());
    }

    /**
     * 商品グループ情報リスト取得<br/>
     *
     * @param goodsGroupSeqs    商品グループSEQリスト
     * @return  商品グループエンティティリスト
     */
    protected List<GoodsGroupEntity> getGoodsGroupListBySeqList(List<Integer> goodsGroupSeqs) {
        // （パラメータ）商品グループSEQリストをもとに、商品グループエンティティリストを取得する
        // 商品グループDaoの商品グループ取得処理を実行する。
        return goodsGroupDao.getGoodsGroupListBySeqList(goodsGroupSeqs);
    }

    /**
     * 商品情報リスト取得<br/>
     *
     * @param goodsCodes    商品コードリスト
     * @param customParams  案件用引数
     * @return 商品情報リスト
     */
    protected List<GoodsDto> getGoodsDtoListByGoodsCodeList(List<String> goodsCodes, Object... customParams) {
        // 商品コードリストをもとに、商品リスト取得Logicを利用して、商品DTOリストを取得する
        // Logic GoodsListGetLogic
        // パラメータ 商品コードリスト
        // 戻り値 商品DTOリスト
        return goodsListGetLogic.execute(goodsCodes);
    }
    // 2023-renew No92 to here
    // PDR Migrate Customization to here
}
