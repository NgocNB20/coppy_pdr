/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.shop.inquiry.impl;

import jp.co.hankyuhanshin.itec.hitmall.dao.inquiry.InquiryDao;
import jp.co.hankyuhanshin.itec.hitmall.dao.inquiry.InquiryDetailDao;
import jp.co.hankyuhanshin.itec.hitmall.dao.inquiry.InquiryGroupDao;
import jp.co.hankyuhanshin.itec.hitmall.dto.inquiry.InquiryDetailsDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.inquiry.InquiryDetailEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.inquiry.InquiryEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.inquiry.InquiryGroupEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.inquiry.InquiryGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.inquiry.InquiryGroupGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.service.AbstractShopService;
import jp.co.hankyuhanshin.itec.hitmall.service.shop.inquiry.InquiryGetService;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 問い合わせ取得
 *
 * @author shibuya
 * @version $Revision: 1.1 $
 */
@Service
public class InquiryGetServiceImpl extends AbstractShopService implements InquiryGetService {

    /**
     * 問い合わせ取得
     */
    private final InquiryGetLogic inquiryGetLogic;
    /**
     * 問い合わせ分類取得
     */
    private final InquiryGroupGetLogic inquiryGroupGetLogic;

    /**
     * 問い合わせDao
     */
    private final InquiryDao inquiryDao;
    /**
     * 問い合わせ詳細DAO
     */
    private final InquiryDetailDao inquiryDetailDao;
    /**
     * 問い合わせ分類Dao
     */
    private final InquiryGroupDao inquiryGroupDao;

    @Autowired
    public InquiryGetServiceImpl(InquiryGetLogic inquiryGetLogic,
                                 InquiryGroupGetLogic inquiryGroupGetLogic,
                                 InquiryDao inquiryDao,
                                 InquiryDetailDao inquiryDetailDao,
                                 InquiryGroupDao inquiryGroupDao) {

        this.inquiryGetLogic = inquiryGetLogic;
        this.inquiryGroupGetLogic = inquiryGroupGetLogic;
        this.inquiryDao = inquiryDao;
        this.inquiryDetailDao = inquiryDetailDao;
        this.inquiryGroupDao = inquiryGroupDao;
    }

    /**
     * 問い合わせ取得
     *
     * @param inquirySeq 問い合わせSEQ
     * @return 問い合わせ詳細Dto
     */
    @Override
    public InquiryDetailsDto execute(Integer inquirySeq) {

        // パラメータチェック
        ArgumentCheckUtil.assertGreaterThanZero("inquirySeq", inquirySeq);
        // 共通情報の取得
        Integer shopSeq = 1001;

        // 問題なければ取得・作成
        return this.createInquiryDetailsDto(inquirySeq, shopSeq);
    }

    /**
     * 問い合わせ詳細Dto作成
     *
     * @param inquirySeq 問い合わせSEQ
     * @param shopSeq    ショップSEQ
     * @return 問い合わせ詳細Dto
     */
    protected InquiryDetailsDto createInquiryDetailsDto(Integer inquirySeq, Integer shopSeq) {
        // 問い合わせ取得
        InquiryEntity inquiryEntity = inquiryGetLogic.execute(inquirySeq, shopSeq);
        if (inquiryEntity == null) {
            throwMessage(MSGCD_INQUIRY_GET_FAIL);
        }

        // 問い合わせ分類取得
        InquiryGroupEntity inquiryGroupEntity =
                        inquiryGroupGetLogic.execute(inquiryEntity.getInquiryGroupSeq(), shopSeq);
        if (inquiryGroupEntity == null) {
            throwMessage(MSGCD_INQUIRYGROUP_GET_FAIL);
        }

        // 問い合わせ詳細Dto作成
        InquiryDetailsDto inquiryDetailsDto = getComponent(InquiryDetailsDto.class);

        inquiryDetailsDto.setInquiryEntity(inquiryEntity);
        inquiryDetailsDto.setInquiryGroupName(inquiryGroupEntity.getInquiryGroupName());

        // 問い合わせ内容取得
        List<InquiryDetailEntity> list = inquiryDetailDao.getInquiryDetailsListByInquirySeq(
                        inquiryDetailsDto.getInquiryEntity().getInquirySeq());
        if (list == null) {
            return null;
        }
        inquiryDetailsDto.setInquiryDetailEntityList(list);

        return inquiryDetailsDto;
    }

    /**
     * 実行メソッド
     *
     * @param orderCode 受注コード
     * @return 問い合わせ詳細DTOリスト
     */
    @Override
    public List<InquiryDetailsDto> execute(String orderCode) {

        // 昇順で問い合わせ情報取得
        return this.execute(orderCode, false);
    }

    /**
     * 実行メソッド(ソート順指定)
     *
     * @param orderCode 受注コード
     * @param asc       昇順/降順フラグ
     * @return 問い合わせ詳細DTOリスト
     */
    @Override
    public List<InquiryDetailsDto> execute(String orderCode, boolean asc) {
        // 問い合わせ取得
        List<InquiryEntity> inquiryEntityList = getEntityList(orderCode, asc);

        // 戻り値用のリスト作成
        List<InquiryDetailsDto> returnList = new ArrayList<>();
        Integer shopSeq = 1001;
        for (InquiryEntity inquiryEntity : inquiryEntityList) {
            // 問い合わせ内容取得
            List<InquiryDetailEntity> list =
                            inquiryDetailDao.getInquiryDetailsListByInquirySeq(inquiryEntity.getInquirySeq());

            // 問い合わせ分類取得
            InquiryGroupEntity inquiryGroupEntity =
                            inquiryGroupDao.getEntityByShopSeq(inquiryEntity.getInquiryGroupSeq(), shopSeq);

            // 戻り値のDto作成
            InquiryDetailsDto inquiryDetailsDto = ApplicationContextUtility.getBean(InquiryDetailsDto.class);

            // 問い合わせ情報を設定
            inquiryDetailsDto.setInquiryEntity(inquiryEntity);
            // 問い合わせ詳細リストを設定
            inquiryDetailsDto.setInquiryDetailEntityList(list);
            // 問い合わせ分類名を設定
            inquiryDetailsDto.setInquiryGroupName(inquiryGroupEntity.getInquiryGroupName());

            returnList.add(inquiryDetailsDto);
        }

        return returnList;
    }

    /**
     * 注文番号が一致する問い合わせ情報を取得
     *
     * @param orderCode 注文番号
     * @param asc       昇順/降順フラグ
     * @return 問い合わせEntityのリスト
     */
    public List<InquiryEntity> getEntityList(String orderCode, boolean asc) {
        // 共通情報の取得
        Integer shopSeq = 1001;
        // パラメータチェック
        ArgumentCheckUtil.assertNotEmpty("orderCode", orderCode);

        // 問い合わせ取得
        return inquiryDao.getEntityListByOrderCode(orderCode, shopSeq, asc);
    }

}
