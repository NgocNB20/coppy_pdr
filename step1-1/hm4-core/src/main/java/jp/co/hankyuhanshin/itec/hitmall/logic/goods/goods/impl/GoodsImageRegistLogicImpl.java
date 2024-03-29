/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.goods.goods.impl;

import jp.co.hankyuhanshin.itec.hitmall.dao.goods.goods.GoodsImageDao;
import jp.co.hankyuhanshin.itec.hitmall.dto.common.FileRegistDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goods.GoodsImageEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.goods.GoodsImageRegistLogic;
import jp.co.hankyuhanshin.itec.hmbase.util.common.CollectionUtil;
import jp.co.hankyuhanshin.itec.hmbase.util.common.PropertiesUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.DateUtility;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 商品規格画像登録<br/>
 *
 * @author thang
 */
@Component
public class GoodsImageRegistLogicImpl extends AbstractShopLogic implements GoodsImageRegistLogic {

    /**
     * ロガー
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(GoodsImageRegistLogicImpl.class);

    /**
     * 何もしないモード
     */
    private static final String NONE_MODE = "0";

    /**
     * 商品規格画像登録モード
     */
    private static final String MODE_REGIST = "1";

    /**
     * 商品規格画像更新モード
     */
    private static final String MODE_UPDATE = "2";

    /**
     * 商品規格画像削除モード
     */
    private static final String MODE_DELETE = "3";

    /**
     * 商品規格画像登録DAO
     */
    private final GoodsImageDao goodsImageDao;

    // 日付関連Helper取得
    private final DateUtility dateUtility;

    private final List<GoodsImageEntity> modifyEntityList;

    private final List<String> deleteImageFilePathList;
    private final List<FileRegistDto> registImageFilePathList;
    private final String realPath;

    @Autowired
    public GoodsImageRegistLogicImpl(GoodsImageDao goodsImageDao, DateUtility dateUtility) {
        this.goodsImageDao = goodsImageDao;
        this.dateUtility = dateUtility;
        this.modifyEntityList = new ArrayList<>();
        this.deleteImageFilePathList = new ArrayList<>();
        this.registImageFilePathList = new ArrayList<>();
        this.realPath = PropertiesUtil.getSystemPropertiesValue("real.images.path.goods") + "/";
    }

    /**
     * 商品規格画像登録<br/>
     * @param goodsGroupSeq         商品グループSEQ
     * @param goodsImageEntityList  商品規格画像一覧
     * @return 処理件数マップ
     */
    @Override
    public Map<String, Object> execute(Integer goodsGroupSeq, List<GoodsImageEntity> goodsImageEntityList) {

        List<Integer> goodsGroupSeqList = new ArrayList<>();
        goodsGroupSeqList.add(goodsGroupSeq);
        List<GoodsImageEntity> entityList = goodsImageDao.getGoodsImageListByGoodsGroupSeqList(goodsGroupSeqList);

        int registCount = 0;
        int updateCount = 0;
        int deleteCount = 0;

        for (GoodsImageEntity goodsImageEntity : goodsImageEntityList) {
            String mode = getMode(goodsImageEntity.getGoodsGroupSeq(), goodsImageEntity.getGoodsSeq(), entityList,
                                  goodsImageEntity
                                 );
            switch (mode) {
                case MODE_REGIST: {
                    // 登録・更新日時の設定
                    Timestamp currentTime = dateUtility.getCurrentTime();
                    goodsImageEntity.setRegistTime(currentTime);
                    goodsImageEntity.setUpdateTime(currentTime);
                    registCount += goodsImageDao.insert(goodsImageEntity);
                    break;
                }
                case MODE_UPDATE: {
                    // 登録・更新日時の設定
                    Timestamp currentTime = dateUtility.getCurrentTime();
                    goodsImageEntity.setUpdateTime(currentTime);
                    updateCount += goodsImageDao.update(goodsImageEntity);
                    break;
                }
                case MODE_DELETE:
                    deleteCount += goodsImageDao.deleteEntity(goodsImageEntity.getGoodsGroupSeq(),
                                                              goodsImageEntity.getGoodsSeq()
                                                             );
                    break;
            }
        }

        List<GoodsImageEntity> deleteList =
                        (List<GoodsImageEntity>) CollectionUtils.removeAll(entityList, modifyEntityList);
        if (CollectionUtil.isNotEmpty(deleteList)) {
            for (GoodsImageEntity entity : deleteList) {
                goodsImageDao.deleteEntity(entity.getGoodsGroupSeq(), entity.getGoodsSeq());
            }
        }

        // 処理件数マップ
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put(DELETE_UNIT_IMAGE_FILE_PATH_LIST, deleteImageFilePathList);
        resultMap.put(REGIST_UNIT_IMAGE_FILE_PATH_LIST, registImageFilePathList);
        resultMap.put(GOODS_IMAGE_REGIST, registCount);
        resultMap.put(GOODS_IMAGE_UPDATE, updateCount);
        resultMap.put(GOODS_IMAGE_DELETE, deleteCount);

        // (6) 戻り値
        return resultMap;
    }

    /**
     * 商品規格画像処理モード取得<br/>
     * @param goodsGroupSeq         商品グループSEQ
     * @param goodsSeq              商品SEQ
     * @param entityList            DBの商品規格画像一覧
     * @param goodsImageEntity      商品規格画像
     * @return 処理モード
     */
    private String getMode(Integer goodsGroupSeq,
                           Integer goodsSeq,
                           List<GoodsImageEntity> entityList,
                           GoodsImageEntity goodsImageEntity) {
        for (GoodsImageEntity entity : entityList) {
            if (entity.getGoodsSeq().equals(goodsImageEntity.getGoodsSeq())) {
                modifyEntityList.add(entity);
                if (goodsImageEntity.getImageFileName() == null) {
                    deleteImageFilePathList.add(entity.getImageFileName());
                    return MODE_DELETE;
                } else {
                    if (goodsImageEntity.getTmpFilePath() != null) {
                        FileRegistDto fileRegistDto = new FileRegistDto();
                        fileRegistDto.setFromFilePath(goodsImageEntity.getTmpFilePath());
                        fileRegistDto.setToFilePath(realPath + entity.getImageFileName());
                        registImageFilePathList.add(fileRegistDto);
                        return MODE_UPDATE;
                    } else if (goodsImageEntity.getDisplayFlag() != entity.getDisplayFlag()) {
                        return MODE_UPDATE;
                    } else {
                        return NONE_MODE;
                    }
                }
            }
        }

        if (goodsImageEntity.getTmpFilePath() == null && goodsImageEntity.getImageFileName() == null) {
            return NONE_MODE;
        } else {
            FileRegistDto fileRegistDto = new FileRegistDto();
            fileRegistDto.setFromFilePath(goodsImageEntity.getTmpFilePath());
            fileRegistDto.setToFilePath(realPath + goodsImageEntity.getImageFileName());
            registImageFilePathList.add(fileRegistDto);
            return MODE_REGIST;
        }
    }
}
