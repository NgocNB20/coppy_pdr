/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.shop.news.registupdate;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOpenStatus;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.news.NewsEntity;
import jp.co.hankyuhanshin.itec.hitmall.util.common.EnumTypeUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.ConversionUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.ZenHanConversionUtility;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

/**
 * クーポン登録更新用Helperクラス。
 *
 * @author
 */
@Component
public class NewsRegistUpdateHelper {

    /**
     * 初期処理時の画面反映
     *
     * @param newsRegistUpdateModel ニュース登録更新画面
     * @param newsEntity            ニュースエンティティ
     */
    public void toPageForLoad(NewsRegistUpdateModel newsRegistUpdateModel, NewsEntity newsEntity) {

        // 画面へ反映する情報が指定されている場合
        if (newsEntity != null) {

            // 変換Helper取得
            ConversionUtility conversionUtility = ApplicationContextUtility.getBean(ConversionUtility.class);

            // 反映
            newsRegistUpdateModel.setNewsSeq(newsEntity.getNewsSeq());

            newsRegistUpdateModel.setNewsDate(conversionUtility.toYmd(newsEntity.getNewsTime()));
            newsRegistUpdateModel.setNewsTime(conversionUtility.toHms(newsEntity.getNewsTime()));

            // PC
            newsRegistUpdateModel.setTitlePC(newsEntity.getTitlePC());
            newsRegistUpdateModel.setNewsBodyPC(newsEntity.getNewsBodyPC());
            newsRegistUpdateModel.setNewsNotePC(newsEntity.getNewsNotePC());
            newsRegistUpdateModel.setNewsUrlPC(newsEntity.getNewsUrlPC());
            // スマートフォン
            newsRegistUpdateModel.setTitleSP(newsEntity.getTitleSP());
            newsRegistUpdateModel.setNewsBodySP(newsEntity.getNewsBodySP());
            newsRegistUpdateModel.setNewsNoteSP(newsEntity.getNewsNoteSP());
            newsRegistUpdateModel.setNewsUrlSP(newsEntity.getNewsUrlSP());
            // PC／スマートフォン共通
            newsRegistUpdateModel.setNewsOpenStatusPC(newsEntity.getNewsOpenStatusPC().getValue());
            newsRegistUpdateModel.setNewsOpenStartDatePC(conversionUtility.toYmd(newsEntity.getNewsOpenStartTimePC()));
            newsRegistUpdateModel.setNewsOpenStartTimePC(conversionUtility.toHms(newsEntity.getNewsOpenStartTimePC()));
            newsRegistUpdateModel.setNewsOpenEndDatePC(conversionUtility.toYmd(newsEntity.getNewsOpenEndTimePC()));
            newsRegistUpdateModel.setNewsOpenEndTimePC(conversionUtility.toHms(newsEntity.getNewsOpenEndTimePC()));

            // モバイル
            newsRegistUpdateModel.setTitleMB(newsEntity.getTitleMB());
            newsRegistUpdateModel.setNewsBodyMB(newsEntity.getNewsBodyMB());
            newsRegistUpdateModel.setNewsNoteMB(newsEntity.getNewsNoteMB());
            newsRegistUpdateModel.setNewsUrlMB(newsEntity.getNewsUrlMB());
            newsRegistUpdateModel.setNewsOpenStatusMB(newsEntity.getNewsOpenStatusMB().getValue());
            newsRegistUpdateModel.setNewsOpenStartDateMB(conversionUtility.toYmd(newsEntity.getNewsOpenStartTimeMB()));
            newsRegistUpdateModel.setNewsOpenStartTimeMB(conversionUtility.toHms(newsEntity.getNewsOpenStartTimeMB()));
            newsRegistUpdateModel.setNewsOpenEndDateMB(conversionUtility.toYmd(newsEntity.getNewsOpenEndTimeMB()));
            newsRegistUpdateModel.setNewsOpenEndTimeMB(conversionUtility.toHms(newsEntity.getNewsOpenEndTimeMB()));

            newsRegistUpdateModel.setNewsEntity(newsEntity);
        }
        newsRegistUpdateModel.setNormality(true);
    }

    /**
     * スマートフォンへコピー処理時の画面反映
     *
     * @param newsRegistUpdateModel ニュース登録更新画面
     */
    public void toPageForCopyToSmartPhone(NewsRegistUpdateModel newsRegistUpdateModel) {

        newsRegistUpdateModel.setTitleSP(newsRegistUpdateModel.getTitlePC());
        newsRegistUpdateModel.setNewsBodySP(newsRegistUpdateModel.getNewsBodyPC());
        newsRegistUpdateModel.setNewsNoteSP(newsRegistUpdateModel.getNewsNotePC());
    }

    /**
     * モバイルへコピー処理時の画面反映
     *
     * @param newsRegistUpdateModel ニュース登録更新画面
     */
    public void toPageForCopyToMobile(NewsRegistUpdateModel newsRegistUpdateModel) {

        newsRegistUpdateModel.setTitleMB(this.convertToMB(newsRegistUpdateModel.getTitlePC()));
        newsRegistUpdateModel.setNewsBodyMB(this.convertToMB(newsRegistUpdateModel.getNewsBodyPC()));
        newsRegistUpdateModel.setNewsNoteMB(this.convertToMB(newsRegistUpdateModel.getNewsNotePC()));
    }

    /**
     * 確認画面へ遷移前の画面反映
     *
     * @param newsRegistUpdateModel ニュース登録更新画面
     */
    public void toPageForConfirm(NewsRegistUpdateModel newsRegistUpdateModel) {

        // 変換Helper取得
        ConversionUtility conversionUtility = ApplicationContextUtility.getBean(ConversionUtility.class);

        // 時刻反映
        newsRegistUpdateModel.setNewsTime(conversionUtility.toDefaultHms(newsRegistUpdateModel.getNewsDate(),
                                                                         newsRegistUpdateModel.getNewsTime(),
                                                                         ConversionUtility.DEFAULT_START_TIME
                                                                        ));
        newsRegistUpdateModel.setNewsOpenStartTimePC(
                        conversionUtility.toDefaultHms(newsRegistUpdateModel.getNewsOpenStartDatePC(),
                                                       newsRegistUpdateModel.getNewsOpenStartTimePC(),
                                                       ConversionUtility.DEFAULT_START_TIME
                                                      ));
        newsRegistUpdateModel.setNewsOpenStartTimeMB(
                        conversionUtility.toDefaultHms(newsRegistUpdateModel.getNewsOpenStartDateMB(),
                                                       newsRegistUpdateModel.getNewsOpenStartTimeMB(),
                                                       ConversionUtility.DEFAULT_START_TIME
                                                      ));
        newsRegistUpdateModel.setNewsOpenEndTimePC(
                        conversionUtility.toDefaultHms(newsRegistUpdateModel.getNewsOpenEndDatePC(),
                                                       newsRegistUpdateModel.getNewsOpenEndTimePC(),
                                                       ConversionUtility.DEFAULT_END_TIME
                                                      ));
        newsRegistUpdateModel.setNewsOpenEndTimeMB(
                        conversionUtility.toDefaultHms(newsRegistUpdateModel.getNewsOpenEndDateMB(),
                                                       newsRegistUpdateModel.getNewsOpenEndTimeMB(),
                                                       ConversionUtility.DEFAULT_END_TIME
                                                      ));

    }

    /**
     * コピー用に半角変換した値を返す
     *
     * @param valuePC PC値
     * @return 携帯値
     */
    protected String convertToMB(String valuePC) {

        String ret = null;
        if (valuePC != null) {
            // 全角、半角の変換Helper取得
            ZenHanConversionUtility zenHanConversionUtility =
                            ApplicationContextUtility.getBean(ZenHanConversionUtility.class);
            ret = zenHanConversionUtility.toHankaku(valuePC, new Character[] {'＆', '＜', '＞', '”', '’', '￥'});
        }

        return ret;
    }

    /**
     * ニュース登録更新時の処理
     *
     * @param newsRegistUpdateModel ページ
     * @return ニュース情報
     */
    public NewsEntity toNewsEntityForNewsRegist(NewsRegistUpdateModel newsRegistUpdateModel) {

        NewsEntity newsEntity = newsRegistUpdateModel.getNewsEntity();
        newsEntity.setNewsTime(this.ymdhmsToTimestamp(newsRegistUpdateModel.getNewsDate(),
                                                      newsRegistUpdateModel.getNewsTime()
                                                     ));

        // PC
        newsEntity.setTitlePC(newsRegistUpdateModel.getTitlePC());
        newsEntity.setNewsBodyPC(newsRegistUpdateModel.getNewsBodyPC());
        newsEntity.setNewsNotePC(newsRegistUpdateModel.getNewsNotePC());
        newsEntity.setNewsUrlPC(newsRegistUpdateModel.getNewsUrlPC());
        // スマートフォン
        newsEntity.setTitleSP(newsRegistUpdateModel.getTitleSP());
        newsEntity.setNewsBodySP(newsRegistUpdateModel.getNewsBodySP());
        newsEntity.setNewsNoteSP(newsRegistUpdateModel.getNewsNoteSP());
        newsEntity.setNewsUrlSP(newsRegistUpdateModel.getNewsUrlSP());
        // PC／スマートフォン共通
        newsEntity.setNewsOpenStatusPC(EnumTypeUtil.getEnumFromValue(HTypeOpenStatus.class,
                                                                     newsRegistUpdateModel.getNewsOpenStatusPC()
                                                                    ));
        newsEntity.setNewsOpenStartTimePC(this.ymdhmsToTimestamp(newsRegistUpdateModel.getNewsOpenStartDatePC(),
                                                                 newsRegistUpdateModel.getNewsOpenStartTimePC()
                                                                ));
        newsEntity.setNewsOpenEndTimePC(this.ymdhmsToTimestamp(newsRegistUpdateModel.getNewsOpenEndDatePC(),
                                                               newsRegistUpdateModel.getNewsOpenEndTimePC()
                                                              ));

        // モバイル
        newsEntity.setTitleMB(newsRegistUpdateModel.getTitleMB());
        newsEntity.setNewsBodyMB(newsRegistUpdateModel.getNewsBodyMB());
        newsEntity.setNewsNoteMB(newsRegistUpdateModel.getNewsNoteMB());
        newsEntity.setNewsUrlMB(newsRegistUpdateModel.getNewsUrlMB());
        newsEntity.setNewsOpenStatusMB(EnumTypeUtil.getEnumFromValue(HTypeOpenStatus.class,
                                                                     newsRegistUpdateModel.getNewsOpenStatusMB()
                                                                    ));
        newsEntity.setNewsOpenStartTimeMB(this.ymdhmsToTimestamp(newsRegistUpdateModel.getNewsOpenStartDateMB(),
                                                                 newsRegistUpdateModel.getNewsOpenStartTimeMB()
                                                                ));
        newsEntity.setNewsOpenEndTimeMB(this.ymdhmsToTimestamp(newsRegistUpdateModel.getNewsOpenEndDateMB(),
                                                               newsRegistUpdateModel.getNewsOpenEndTimeMB()
                                                              ));

        return newsEntity;
    }

    /**
     * 年月日・時分秒→タイムスタンプ
     *
     * @param ymd 日付項目値
     * @param hms 時刻項目値
     * @return 引数から取得されるタイムスタンプ
     */
    protected Timestamp ymdhmsToTimestamp(String ymd, String hms) {

        Timestamp ret = null;
        if (ymd != null && hms != null) {
            // 変換Helper取得
            ConversionUtility conversionUtility = ApplicationContextUtility.getBean(ConversionUtility.class);

            ret = conversionUtility.toTimeStamp(ymd, hms);
        }
        return ret;

    }
}
