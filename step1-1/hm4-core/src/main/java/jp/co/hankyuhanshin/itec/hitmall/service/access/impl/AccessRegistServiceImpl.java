/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.access.impl;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeAccessType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeDeviceType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSiteType;
import jp.co.hankyuhanshin.itec.hitmall.entity.access.AccessInfoEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.access.AccessRegistLogic;
import jp.co.hankyuhanshin.itec.hitmall.service.AbstractShopService;
import jp.co.hankyuhanshin.itec.hitmall.service.access.AccessRegistService;
import jp.co.hankyuhanshin.itec.hmbase.utility.DateUtility;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * アクセス情報登録サービス<br/>
 *
 * 内容別にアクセス情報を登録する<br/>
 *
 * @author kimura
 * @author kaneko　(itec) チケット対応　#2644　訪問者数にクローラが含まれている
 * @author Kaneko (itec) 2012/08/21 UtilからHelperへ変更　Util使用箇所をgetComponentで取得するように変更
 *
 */
@Component
public class AccessRegistServiceImpl extends AbstractShopService implements AccessRegistService {

    /** ログ */
    private static final Logger LOGGER = LoggerFactory.getLogger(AccessRegistServiceImpl.class);

    /** アクセス状況登録ロジッククラス  */
    private final AccessRegistLogic accessRegistLogic;

    /** DateUtility  */
    private final DateUtility dateUtility;

    @Autowired
    public AccessRegistServiceImpl(AccessRegistLogic accessRegistLogic, DateUtility dateUtility) {
        this.accessRegistLogic = accessRegistLogic;
        this.dateUtility = dateUtility;
    }

    /**
     *
     * アクセス情報登録<br/>
     * 内容別にアクセス情報を登録する<br/>
     *
     * @param accessType アクセス種別
     * @param goodsGroupSeq 商品SEQ
     * @param accessCount アクセス数(受注の個数、金額の場合のみ指定)
     * @return 更新・登録件数
     */
    @Override
    public int execute(HTypeAccessType accessType,
                       Integer goodsGroupSeq,
                       Integer accessCount,
                       String accessUid,
                       HTypeSiteType siteType,
                       String campaignCode) {

        // パラメータチェック
        if (!parameterCheck(accessType, goodsGroupSeq, accessCount)) {
            return 0;
        }

        // 共通情報チェック
        if (!commonInfoCheck(accessType, accessUid, siteType, campaignCode)) {
            return 0;
        }

        // アクセス情報エンティティの作成
        AccessInfoEntity accessInfoEntity =
                        makeAccessInfoEntity(accessType, goodsGroupSeq, accessCount, accessUid, campaignCode);

        // (4) アクセス情報登録処理実行
        return accessRegistLogic.execute(accessInfoEntity);
    }

    /**
     * パラメータのチェック<br/>
     *
     * @param accessType アクセス種別
     * @param goodsGroupSeq 商品SEQ
     * @param accessCount アクセス数
     * @return boolean
     */
    protected boolean parameterCheck(HTypeAccessType accessType, Integer goodsGroupSeq, Integer accessCount) {

        // アクセス種別のバリデート
        if (accessType == null) {
            LOGGER.error("accessType" + "がnullです。");
            return false;
        }

        // 商品アクセス回数or商品カート投入or受注個数の場合
        if (HTypeAccessType.GOODS_ACCESS_COUNT.equals(accessType) || HTypeAccessType.GOODS_CART_ADD_COUNT.equals(
                        accessType) || HTypeAccessType.GOODS_ORDER_GOODS_COUNT.equals(accessType)) {
            // 商品SEQのバリデート
            if (goodsGroupSeq == null) {
                LOGGER.error("goodsGroupSeq" + "がnullです。");
                return false;
            }
        }

        // 商品アクセス回数orアクセス種別が受注個数or受注金額の場合
        if (HTypeAccessType.GOODS_ORDER_GOODS_COUNT.equals(accessType) || HTypeAccessType.GOODS_ACCESS_COUNT.equals(
                        accessType) || HTypeAccessType.GOODS_ORDER_PRICE.equals(accessType)) {
            // カウントのバリデート
            if (accessCount == null) {
                LOGGER.error("accessCount" + "がnullです。");
                return false;
            }
        }
        return true;
    }

    /**
     *
     * 共通情報のチェック<br/>
     *
     * @param accessType アクセス種別
     * @return boolean
     *
     */
    protected boolean commonInfoCheck(HTypeAccessType accessType,
                                      String accessUid,
                                      HTypeSiteType siteType,
                                      String campaignCode) {

        // 端末識別情報のバリデート
        if (StringUtils.isEmpty(accessUid)) {
            LOGGER.error("accessUid" + "がnullまたは、空文字です。");
            return false;
        }

        // サイト種別のバリデート
        if (siteType == null) {
            LOGGER.error("siteType" + "がnullです。");
            return false;
        }

        // キャンペーンコードのバリデート
        if (accessType.isCampaignType()) {
            if (StringUtils.isEmpty(campaignCode)) {
                LOGGER.error("campaignCode" + "がnullまたは、空文字です。");
                return false;
            }
        }
        return true;
    }

    /**
     *
     * アクセス情報エンティティ作成<br/>
     * アクセス種別別に、アクセス情報エンティティを作成する<br/>
     *
     * @param accessType アクセス種別
     * @param goodsGroupSeq 商品SEQ
     * @param accessCount アクセス数
     * @return アクセス情報エンティティ
     */
    protected AccessInfoEntity makeAccessInfoEntity(HTypeAccessType accessType,
                                                    Integer goodsGroupSeq,
                                                    Integer accessCount,
                                                    String accessUid,
                                                    String campaignCode) {
        // 共通項目設定
        AccessInfoEntity accessInfoEntity = getComponent(AccessInfoEntity.class);
        accessInfoEntity.setAccessType(accessType);
        accessInfoEntity.setAccessDate(dateUtility.getCurrentDate());
        accessInfoEntity.setShopSeq(1001);
        accessInfoEntity.setSiteType(HTypeSiteType.FRONT_PC);
        accessInfoEntity.setDeviceType(HTypeDeviceType.PC);
        accessInfoEntity.setAccessUid(accessUid);
        accessInfoEntity.setCampaignCode(campaignCode);

        // アクセス種別が商品アクセス回数の場合
        if (HTypeAccessType.GOODS_ACCESS_COUNT.equals(accessType)) {

            accessInfoEntity.setAccessCount(accessCount);
            accessInfoEntity.setGoodsGroupSeq(goodsGroupSeq);
            accessInfoEntity.setAccessUid("");
            accessInfoEntity.setCampaignCode("");
            return accessInfoEntity;

            // 商品カート投入回数の場合
        } else if (HTypeAccessType.GOODS_CART_ADD_COUNT.equals(accessType)) {

            accessInfoEntity.setAccessCount(1);
            accessInfoEntity.setGoodsGroupSeq(goodsGroupSeq);
            accessInfoEntity.setAccessUid("");
            accessInfoEntity.setCampaignCode("");
            return accessInfoEntity;

            // アクセス種別が受注回数の場合
        } else if (HTypeAccessType.GOODS_ORDER_COUNT.equals(accessType)) {

            accessInfoEntity.setAccessCount(1);
            accessInfoEntity.setGoodsGroupSeq(0);
            accessInfoEntity.setAccessUid("");
            return accessInfoEntity;

            // アクセス種別が受注個数の場合
        } else if (HTypeAccessType.GOODS_ORDER_GOODS_COUNT.equals(accessType)) {

            accessInfoEntity.setAccessCount(accessCount);
            accessInfoEntity.setGoodsGroupSeq(goodsGroupSeq);
            accessInfoEntity.setAccessUid("");
            accessInfoEntity.setCampaignCode("");
            return accessInfoEntity;

            // アクセス種別が受注金額の場合
        } else if (HTypeAccessType.GOODS_ORDER_PRICE.equals(accessType)) {

            accessInfoEntity.setAccessCount(accessCount);
            accessInfoEntity.setGoodsGroupSeq(0);
            accessInfoEntity.setAccessUid("");
            return accessInfoEntity;

            // アクセス種別がキャンペーンアクセスの場合
        } else if (HTypeAccessType.CAMPAIGN_ACCESS_COUNT.equals(accessType)) {

            accessInfoEntity.setAccessCount(1);
            accessInfoEntity.setGoodsGroupSeq(0);
            return accessInfoEntity;

            // アクセス種別が会員入会または、退会の場合
        } else if (HTypeAccessType.MEMBER_REGIST_COUNT.equals(accessType) || HTypeAccessType.MEMBER_REMOVE_COUNT.equals(
                        accessType)) {

            accessInfoEntity.setAccessCount(1);
            accessInfoEntity.setGoodsGroupSeq(0);
            accessInfoEntity.setAccessUid("");
            return accessInfoEntity;

            // アクセス種別がメルマガ登録または、解除の場合
        } else if (HTypeAccessType.SESSION_COUNT.equals(accessType) || HTypeAccessType.TOP_PAGE_PV_COUNT.equals(
                        accessType)) {

            accessInfoEntity.setAccessCount(1);
            accessInfoEntity.setGoodsGroupSeq(0);
            accessInfoEntity.setAccessUid("");
            accessInfoEntity.setCampaignCode("");
            return accessInfoEntity;

        }
        return accessInfoEntity;
    }

}
