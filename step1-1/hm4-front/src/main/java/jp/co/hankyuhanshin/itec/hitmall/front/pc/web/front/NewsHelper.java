/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front;

import jp.co.hankyuhanshin.itec.hitmall.api.shop.param.NewsEntityResponse;
import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.ConversionUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.DateUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeOpenStatus;
import jp.co.hankyuhanshin.itec.hitmall.front.entity.shop.news.NewsEntity;
import jp.co.hankyuhanshin.itec.hitmall.front.util.common.EnumTypeUtil;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

/**
 * ニュース詳細 Helper
 *
 * @author kn23834
 * @version $Revision: 1.0 $
 */
@Component
public class NewsHelper {

    /**
     * 画面表示・再表示<br/>
     *
     * @param newsEntity ニュースエンティティ情報
     * @param newsModel  ニュース詳細Model
     */
    public void toPageForLoad(NewsEntity newsEntity, NewsModel newsModel) {

        // 日付関連Helper取得
        DateUtility dateUtility = ApplicationContextUtility.getBean(DateUtility.class);

        Timestamp now = dateUtility.getCurrentTime();

        // 表示対象外のニュースの場合は、何もしない
        if (newsEntity == null || newsEntity.getNewsOpenStatusPC().equals(HTypeOpenStatus.NO_OPEN) || (
                        newsEntity.getNewsOpenStartTimePC() != null
                        && newsEntity.getNewsOpenStartTimePC().compareTo(now) > 0) || (
                            newsEntity.getNewsOpenEndTimePC() != null
                            && newsEntity.getNewsOpenEndTimePC().compareTo(now) < 0)) {

            newsModel.setNewsBodyPC(null);
            newsModel.setNewsNotePC(null);
            newsModel.setTitlePC(null);
            newsModel.setNewsTime(null);

            return;
        }

        newsModel.setNewsBodyPC(newsEntity.getNewsBodyPC());
        newsModel.setNewsNotePC(newsEntity.getNewsNotePC());
        newsModel.setTitlePC(newsEntity.getTitlePC());
        newsModel.setNewsTime(newsEntity.getNewsTime());

    }

    /**
     * ニュースに変換
     *
     * @param newsEntityResponse ニュースニュースEntityレスポンス
     * @return ニュース
     */
    public NewsEntity toNewsEntity(NewsEntityResponse newsEntityResponse) {
        if (newsEntityResponse == null) {
            return null;
        }
        ConversionUtility conversionUtility = ApplicationContextUtility.getBean(ConversionUtility.class);

        NewsEntity newsEntity = new NewsEntity();
        newsEntity.setNewsSeq(newsEntityResponse.getNewsSeq());
        newsEntity.setRegistTime(conversionUtility.toTimeStamp(newsEntityResponse.getRegistTime()));
        newsEntity.setUpdateTime(conversionUtility.toTimeStamp(newsEntityResponse.getUpdateTime()));
        newsEntity.setNewsTime(conversionUtility.toTimeStamp(newsEntityResponse.getNewsTime()));
        newsEntity.setNewsNotePC(newsEntityResponse.getNewsNote());
        newsEntity.setTitlePC(newsEntityResponse.getTitle());
        newsEntity.setNewsBodyPC(newsEntityResponse.getNewsBody());
        newsEntity.setNewsOpenEndTimePC(conversionUtility.toTimeStamp(newsEntityResponse.getNewsOpenEndTime()));
        newsEntity.setNewsOpenStartTimePC(conversionUtility.toTimeStamp(newsEntityResponse.getNewsOpenStartTime()));
        newsEntity.setNewsOpenStatusPC(
                        EnumTypeUtil.getEnumFromValue(HTypeOpenStatus.class, newsEntityResponse.getNewsOpenStatus()));
        newsEntity.setNewsNotePC(newsEntityResponse.getNewsNote());
        return newsEntity;
    }

}
