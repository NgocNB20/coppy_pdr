package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.shop.news;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOpenStatus;
import jp.co.hankyuhanshin.itec.hitmall.dto.shop.NewsSearchForBackDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.news.NewsEntity;
import jp.co.hankyuhanshin.itec.hitmall.util.common.EnumTypeUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.ConversionUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.DateUtility;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * ニュース検索画面Helper
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Component
public class NewsHelper {
    /**
     * 検索条件を画面に反映
     * 再検索用
     *
     * @param conditionDto 検索条件Dto
     * @param newsModel    検索ページ
     */
    public void toPageForLoad(NewsSearchForBackDaoConditionDto conditionDto, NewsModel newsModel) {

        // 変換Helper取得
        ConversionUtility conversionUtility = ApplicationContextUtility.getBean(ConversionUtility.class);

        /* 各検索条件を画面に反映する */
        // 期間(ニュース日付)開始
        newsModel.setSearchNewsTimeFrom(conversionUtility.toYmd(conditionDto.getNewsTimeFrom()));

        // 期間(ニュース日付)終了
        newsModel.setSearchNewsTimeTo(conversionUtility.toYmd(conditionDto.getNewsTimeTo()));

        // タイトル
        newsModel.setSearchTitle(conditionDto.getTitle());

        // URL
        newsModel.setSearchURL(conditionDto.getUrl());

        // 本文・詳細
        newsModel.setSearchBodyNote(conditionDto.getBodyNote());

        // 公開状態
        newsModel.setSearchNewsOpenStatus(EnumTypeUtil.getValue(conditionDto.getOpenStatus()));

        // 公開状態開始日 開始
        newsModel.setSearchNewsOpenStartTimeFrom(conversionUtility.toYmd(conditionDto.getOpenStartTimeFrom()));

        // 公開状態開始日 終了
        newsModel.setSearchNewsOpenStartTimeTo(conversionUtility.toYmd(conditionDto.getOpenStartTimeTo()));

        // 公開状態終了日 開始
        newsModel.setSearchNewsOpenEndTimeFrom(conversionUtility.toYmd(conditionDto.getOpenEndTimeFrom()));

        // 公開状態終了日 終了
        newsModel.setSearchNewsOpenEndTimeTo(conversionUtility.toYmd(conditionDto.getOpenEndTimeTo()));
    }

    /**
     * 画面入力値から検索条件Dtoを作成する
     *
     * @param newsModel 検索画面のページ情報
     * @return ニュース検索条件Dto(管理者用)
     */
    public NewsSearchForBackDaoConditionDto toNewsSearchForBackDaoConditionDtoForSearch(NewsModel newsModel) {

        NewsSearchForBackDaoConditionDto conditionDto =
                        ApplicationContextUtility.getBean(NewsSearchForBackDaoConditionDto.class);

        // 変換Helper取得
        ConversionUtility conversionUtility = ApplicationContextUtility.getBean(ConversionUtility.class);

        // 日付関連Helper取得
        DateUtility dateUtility = ApplicationContextUtility.getBean(DateUtility.class);

        // ニュース日時From
        conditionDto.setNewsTimeFrom(conversionUtility.toTimeStamp(newsModel.getSearchNewsTimeFrom()));

        // ニュース日時To
        if (newsModel.getSearchNewsTimeTo() != null) {
            conditionDto.setNewsTimeTo(
                            dateUtility.getEndOfDate(conversionUtility.toTimeStamp(newsModel.getSearchNewsTimeTo())));
        }

        // 公開状態
        conditionDto.setOpenStatus(
                        EnumTypeUtil.getEnumFromValue(HTypeOpenStatus.class, newsModel.getSearchNewsOpenStatus()));

        // 公開開始日時From
        conditionDto.setOpenStartTimeFrom(conversionUtility.toTimeStamp(newsModel.getSearchNewsOpenStartTimeFrom()));

        // 公開開始日時To
        if (newsModel.getSearchNewsOpenStartTimeTo() != null) {
            conditionDto.setOpenStartTimeTo(dateUtility.getEndOfDate(
                            conversionUtility.toTimeStamp(newsModel.getSearchNewsOpenStartTimeTo())));
        }
        // 公開終了日時From
        conditionDto.setOpenEndTimeFrom(conversionUtility.toTimeStamp(newsModel.getSearchNewsOpenEndTimeFrom()));

        // 公開終了日時To
        if (newsModel.getSearchNewsOpenEndTimeTo() != null) {
            conditionDto.setOpenEndTimeTo(dateUtility.getEndOfDate(
                            conversionUtility.toTimeStamp(newsModel.getSearchNewsOpenEndTimeTo())));
        }

        // タイトル
        conditionDto.setTitle(newsModel.getSearchTitle());

        // URL
        conditionDto.setUrl(newsModel.getSearchURL());

        // 本文・詳細
        conditionDto.setBodyNote(newsModel.getSearchBodyNote());

        return conditionDto;
    }

    /**
     * 検索結果をページに反映<br/>
     *
     * @param newsEntityList ニュースエンティティリスト
     * @param newsModel      ニュース検索ページ
     * @param conditionDto   検索Dto
     */
    public void toPageForSearch(List<NewsEntity> newsEntityList,
                                NewsModel newsModel,
                                NewsSearchForBackDaoConditionDto conditionDto) {

        // オフセット + 1をNoにセット
        int index = conditionDto.getOffset() + 1;
        List<NewsModelItem> resultItemList = new ArrayList<>();

        // 現在時刻取得
        DateUtility dateUtility = ApplicationContextUtility.getBean(DateUtility.class);
        Timestamp currentTime = dateUtility.getCurrentTime();

        for (NewsEntity newsEntity : newsEntityList) {
            NewsModelItem newsModelItem = ApplicationContextUtility.getBean(NewsModelItem.class);
            newsModelItem.setResultNo(index++);
            newsModelItem.setNewsSeq(newsEntity.getNewsSeq());
            newsModelItem.setNewsTime(newsEntity.getNewsTime());
            newsModelItem.setTitlePC(newsEntity.getTitlePC());
            newsModelItem.setTitleSP(newsEntity.getTitleSP());
            newsModelItem.setNewsOpenStatusPC(newsEntity.getNewsOpenStatusPC().getValue());
            newsModelItem.setTitleMB(newsEntity.getTitleMB());
            newsModelItem.setNewsOpenStatusMB(newsEntity.getNewsOpenStatusMB().getValue());
            newsModelItem.setNewsNotePc(newsEntity.getNewsNotePC());
            newsModelItem.setNewsNoteSp(newsEntity.getNewsNoteSP());
            // ニュース表示ステータス設定
            String newsDisplayStatusPC = getNewsDisplayStatusPC(newsEntity, currentTime);
            String newsDisplayStatusMB = getNewsDisplayStatusMB(newsEntity, currentTime);
            newsModelItem.setNewsDisplayStatusPC(newsDisplayStatusPC);
            newsModelItem.setNewsDisplayStatusMB(newsDisplayStatusMB);
            if (NewsModel.NEWS_DISPLAY_STATUS_DISPLAY.equals(newsDisplayStatusPC)
                || NewsModel.NEWS_DISPLAY_STATUS_DISPLAY.equals(newsDisplayStatusMB)) {
                newsModelItem.setNewsDisplayStatus(NewsModel.NEWS_DISPLAY_STATUS_DISPLAY);
            } else {
                newsModelItem.setNewsDisplayStatus(NewsModel.NEWS_DISPLAY_STATUS_HIDE);
            }

            resultItemList.add(newsModelItem);
        }

        newsModel.setResultItems(resultItemList);
    }

    /**
     * ニュース表示ステータス(PC)取得処理<br/>
     * ・ニュース公開ステータス(PC)が非公開の場合、「非表示：０」
     * ・ニュース公開ステータス(PC)が公開中で、公開開始日(PC)が未来の場合、「非表示：０」
     * ・ニュース公開ステータス(PC)が公開中で、公開終了日(PC)が過去の場合、「非表示：０」
     * ・上記以外の場合、「表示中：１」
     *
     * @param newsEntity   ニュースエンティティ
     * @param currentTime  現在時刻
     * @param customParams 案件用引数
     * @return ニュース表示ステータス 「非表示：０」、「表示中：１」
     */
    protected String getNewsDisplayStatusPC(NewsEntity newsEntity, Timestamp currentTime, Object... customParams) {

        // ニュース公開ステータス(PC)取得
        HTypeOpenStatus newsOpenStatusPC = newsEntity.getNewsOpenStatusPC();
        // ニュース公開開始時間(PC)取得
        Timestamp newsOpenStartTimePC = newsEntity.getNewsOpenStartTimePC();
        // ニュース公開終了時間(PC)取得
        Timestamp newsOpenEndTimePC = newsEntity.getNewsOpenEndTimePC();

        return getNewsDisplayStatus(currentTime, newsOpenStatusPC, newsOpenStartTimePC, newsOpenEndTimePC);
    }

    /**
     * ニュース表示ステータス(MB)取得処理<br/>
     * ・ニュース公開ステータス(MB)が非公開の場合、「非表示：０」
     * ・ニュース公開ステータス(MB)が公開中で、公開開始日(MB)が未来の場合、「非表示：０」
     * ・ニュース公開ステータス(MB)が公開中で、公開終了日(MB)が過去の場合、「非表示：０」
     * ・上記以外の場合、「表示中：１」
     *
     * @param newsEntity   ニュースエンティティ
     * @param currentTime  現在時刻
     * @param customParams 案件用引数
     * @return ニュース表示ステータス 「非表示：０」、「表示中：１」
     */
    protected String getNewsDisplayStatusMB(NewsEntity newsEntity, Timestamp currentTime, Object... customParams) {

        // ニュース公開ステータス(MB)取得
        HTypeOpenStatus newsOpenStatusMB = newsEntity.getNewsOpenStatusMB();
        // ニュース公開開始時間(MB)取得
        Timestamp newsOpenStartTimeMB = newsEntity.getNewsOpenStartTimeMB();
        // ニュース公開終了時間(MB)取得
        Timestamp newsOpenEndTimeMB = newsEntity.getNewsOpenEndTimeMB();

        return getNewsDisplayStatus(currentTime, newsOpenStatusMB, newsOpenStartTimeMB, newsOpenEndTimeMB);
    }

    /**
     * ニュース表示ステータス取得処理<br/>
     * ・ニュース公開ステータスが非公開の場合、「非表示：０」
     * ・ニュース公開ステータスが公開中で、公開開始日が未来の場合、「非表示：０」
     * ・ニュース公開ステータスが公開中で、公開終了日が過去の場合、「非表示：０」
     * ・上記以外の場合、「表示中：１」
     *
     * @param currentTime       現在時刻
     * @param newsOpenStatus    ニュース公開ステータス
     * @param newsOpenStartTime ニュース公開開始日
     * @param newsOpenEndTime   ニュース公開終了日
     * @param customParams      案件用引数
     * @return ニュース表示ステータス 「非表示：０」、「表示中：１」
     */
    protected String getNewsDisplayStatus(Timestamp currentTime,
                                          HTypeOpenStatus newsOpenStatus,
                                          Timestamp newsOpenStartTime,
                                          Timestamp newsOpenEndTime,
                                          Object... customParams) {
        if (HTypeOpenStatus.NO_OPEN.equals(newsOpenStatus)) {
            // 公開ステータスが非公開の場合、「非表示：０」
            return NewsModel.NEWS_DISPLAY_STATUS_HIDE;
        } else {
            // 公開ステータスが公開の場合、期間判定
            if (newsOpenStartTime != null && currentTime.before(newsOpenStartTime)) {
                // 公開開始日が未来の場合、「非表示：０」
                return NewsModel.NEWS_DISPLAY_STATUS_HIDE;
            } else if (newsOpenEndTime != null && currentTime.after(newsOpenEndTime)) {
                // 公開終了日が過去の場合、「非表示：０」
                return NewsModel.NEWS_DISPLAY_STATUS_HIDE;
            } else {
                // 公開期間の指定なし、または公開期間中の場合、「表示中：１」
                return NewsModel.NEWS_DISPLAY_STATUS_DISPLAY;
            }
        }
    }
}
