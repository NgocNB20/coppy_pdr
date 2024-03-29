/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.goods.category.impl;

import jp.co.hankyuhanshin.itec.hitmall.dto.goods.category.CategoryDetailsDto;
import jp.co.hankyuhanshin.itec.hitmall.service.AbstractShopService;
import jp.co.hankyuhanshin.itec.hitmall.service.goods.category.OpenCategoryListGetService;
import jp.co.hankyuhanshin.itec.hitmall.service.goods.category.OpenCategoryPassListGetService;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 公開カテゴリパスリスト取得<br/>
 *
 * @author ozaki
 * @version $Revision: 1.4 $
 */
@Service
public class OpenCategoryPassListGetServiceImpl extends AbstractShopService implements OpenCategoryPassListGetService {

    /**
     * 公開カテゴリリスト取得サービス
     */
    private final OpenCategoryListGetService openCategoryListGetService;

    @Autowired
    public OpenCategoryPassListGetServiceImpl(OpenCategoryListGetService openCategoryListGetService) {
        this.openCategoryListGetService = openCategoryListGetService;
    }

    /**
     * カテゴリSEQパスを元にカテゴリDTOのリストを取得する
     *
     * @param categorySeqPath カテゴリSEQパス
     * @return カテゴリ情報DTOリスト
     */
    @Override
    public List<CategoryDetailsDto> execute(String categorySeqPath) {

        // (1) パラメータチェック
        // ・カテゴリID ： null 又は 空文字の場合 エラーとして処理を終了する
        ArgumentCheckUtil.assertNotEmpty("categorySeqPath", categorySeqPath);

        // (2) カテゴリSEQパス分解
        // ・カテゴリSEQパスを８バイト区切りで、文字列の左から順にカテゴリSEQを取得する
        // カテゴリSEQパスに設定するカテゴリSEQは固定長（前０埋め）８桁で表している
        // ・取得したカテゴリSEQをリストに取得順にセットする
        int i = 0;
        List<Integer> categorySeqList = new ArrayList<>();
        while (true) {
            // 最後の８桁は自分なので、除外
            if (categorySeqPath.length() <= (i * 8)) {
                // if (categorySeqPath.length() <= ((i + 1) * 8)) {
                break;
            }

            categorySeqList.add(Integer.valueOf(categorySeqPath.substring(i * 8, ((i + 1) * 8))));
            i++;
        }

        // (3) カテゴリ情報リスト取得処理実行
        // ※『基本設計書（公開カテゴリリスト取得）.xls』参照
        // Logic OpenGoodsCategoryListGetService
        // パラメータ カテゴリSEQリスト
        // 戻り値 カテゴリィDTOリスト
        List<CategoryDetailsDto> categoryList = new ArrayList<>();
        if (categorySeqList.size() > 0) {
            categoryList = openCategoryListGetService.execute(categorySeqList);
        }
        return categoryList;
    }

}
