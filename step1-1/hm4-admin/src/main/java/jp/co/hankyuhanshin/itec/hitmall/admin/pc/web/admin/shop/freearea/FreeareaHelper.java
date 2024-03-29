package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.shop.freearea;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSiteMapFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeUkFeedInfoSendFlag;
import jp.co.hankyuhanshin.itec.hitmall.dto.shop.freearea.FreeAreaSearchDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.FreeAreaEntity;
import jp.co.hankyuhanshin.itec.hitmall.util.common.EnumTypeUtil;
import jp.co.hankyuhanshin.itec.hmbase.util.seasar.StringUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.ConversionUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.DateUtility;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * フリーエリア検索HELPER
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Component
public class FreeareaHelper {

    /**
     * 検索条件を画面に反映
     * 再検索用
     *
     * @param conditionDto 検索条件Dto
     * @param indexPage    検索ページ
     */
    public void toPageForLoad(FreeAreaSearchDaoConditionDto conditionDto, FreeareaModel freeareaModel) {

        // 変換Helper取得
        ConversionUtility conversionUtility = ApplicationContextUtility.getBean(ConversionUtility.class);

        /* 各検索条件を画面に反映する */

        // キー
        freeareaModel.setSearchFreeAreaKey(conditionDto.getFreeAreaKey());

        // タイトル
        freeareaModel.setSearchFreeAreaTitle(conditionDto.getFreeAreaTitle());

        // 公開開始日 From
        freeareaModel.setSearchOpenStartTimeFrom(conversionUtility.toYmd(conditionDto.getOpenStartTimeFrom()));

        // 公開開始日 To
        freeareaModel.setSearchOpenStartTimeTo(conversionUtility.toYmd(conditionDto.getOpenStartTimeTo()));

        // 表示状態-日時タイプ
        freeareaModel.setSearchDateType(conditionDto.getDateType());

        // 表示状態-日付（日）
        freeareaModel.setSearchTargetDate(conditionDto.getTargetDate());

        // 表示状態-日付（時間）
        freeareaModel.setSearchTargetTime(conditionDto.getTargetTime());

        // 表示状態
        freeareaModel.setSearchOpenStateArray(conditionDto.getOpenStatusList().toArray(new String[] {}));

        // サイトマップ出力
        freeareaModel.setSearchSiteMapFlag(EnumTypeUtil.getValue(conditionDto.getSiteMapFlag()));

        // ユニサーチ連携フラグ
        freeareaModel.setSearchUkFeedInfoSendFlag(EnumTypeUtil.getValue(conditionDto.getUkFeedInfoSendFlag()));

    }

    /**
     * 検索条件作成
     *
     * @param indexPage ページ情報
     * @return フリーエリア検索条件Dto
     */
    public FreeAreaSearchDaoConditionDto toFreeAreaSearchDaoConditionDtoForSearch(FreeareaModel freeareaModel) {
        FreeAreaSearchDaoConditionDto conditionDto = (FreeAreaSearchDaoConditionDto) ApplicationContextUtility.getBean(
                        FreeAreaSearchDaoConditionDto.class);

        // 変換Helper取得 / 日付関連Helper取得
        ConversionUtility conversionUtility = ApplicationContextUtility.getBean(ConversionUtility.class);
        DateUtility dateUtility = ApplicationContextUtility.getBean(DateUtility.class);

        // キー
        conditionDto.setFreeAreaKey(freeareaModel.getSearchFreeAreaKey());
        // タイトル
        conditionDto.setFreeAreaTitle(freeareaModel.getSearchFreeAreaTitle());
        // 公開開始日時From
        conditionDto.setOpenStartTimeFrom(conversionUtility.toTimeStamp(freeareaModel.getSearchOpenStartTimeFrom()));
        // 公開開始日時To
        if (freeareaModel.getSearchOpenStartTimeTo() != null) {
            conditionDto.setOpenStartTimeTo(dateUtility.getEndOfDate(
                            conversionUtility.toTimeStamp(freeareaModel.getSearchOpenStartTimeTo())));
        }

        // 基準日 /日付タイプ / 日付
        Timestamp baseDate = dateUtility.getCurrentTime();
        if (FreeareaModel.SEARCH_DATE_TYPE_TARGETDATE.equals(freeareaModel.getSearchDateType())) {
            // 初期時間 00:00:00
            if (StringUtil.isEmpty(freeareaModel.getSearchTargetTime())) {
                freeareaModel.setSearchTargetTime(ConversionUtility.DEFAULT_START_TIME);
            }
            baseDate = conversionUtility.toTimeStamp(freeareaModel.getSearchTargetDate(),
                                                     freeareaModel.getSearchTargetTime()
                                                    );
        }
        conditionDto.setDateType(freeareaModel.getSearchDateType());
        conditionDto.setTargetDate(freeareaModel.getSearchTargetDate());
        conditionDto.setTargetTime(freeareaModel.getSearchTargetTime());
        conditionDto.setBaseDate(baseDate);
        conditionDto.setSiteMapFlag(
                        EnumTypeUtil.getEnumFromValue(HTypeSiteMapFlag.class, freeareaModel.getSearchSiteMapFlag()));
        // 2023-renew No36-1, No61,67,95 from here
        conditionDto.setUkFeedInfoSendFlag(EnumTypeUtil.getEnumFromValue(HTypeUkFeedInfoSendFlag.class,
                                                                         freeareaModel.getSearchUkFeedInfoSendFlag()
                                                                        ));
        // 2023-renew No36-1, No61,67,95 to here

        // 公開状態
        conditionDto.setOpenStatusList(Arrays.asList(freeareaModel.getSearchOpenStateArray()));

        return conditionDto;
    }

    /**
     * 検索結果をページに反映
     *
     * @param freeAreaEntityList フリーエリアエンティティ
     * @param indexPage          フリーエリア検索ページ
     * @param conditionDto       検索条件Dto
     */
    public void toPageForSearch(List<FreeAreaEntity> freeAreaEntityList,
                                FreeareaModel freeareaModel,
                                FreeAreaSearchDaoConditionDto conditionDto) {

        // オフセット + 1をNoにセット
        int index = conditionDto.getOffset() + 1;
        List<FreeareaResultItem> resultItemList = new ArrayList<>();
        for (FreeAreaEntity freeAreaEntity : freeAreaEntityList) {
            FreeareaResultItem indexPageItem = ApplicationContextUtility.getBean(FreeareaResultItem.class);
            indexPageItem.setResultNo(index++);
            indexPageItem.setFreeAreaSeq(freeAreaEntity.getFreeAreaSeq());
            indexPageItem.setFreeAreaKey(freeAreaEntity.getFreeAreaKey());
            indexPageItem.setFreeAreaTitle(freeAreaEntity.getFreeAreaTitle());
            indexPageItem.setOpenStartTime(freeAreaEntity.getOpenStartTime());
            indexPageItem.setFreeAreaOpenStatus(freeAreaEntity.getFreeAreaOpenStatus().getValue());
            indexPageItem.setSiteMapFlag(freeAreaEntity.getSiteMapFlag().getValue());
            // 2023-renew No36-1, No61,67,95 from here
            indexPageItem.setUkFeedInfoSendFlag(freeAreaEntity.getUkFeedInfoSendFlag().getValue());
            // 2023-renew No36-1, No61,67,95 to here
            resultItemList.add(indexPageItem);
        }
        freeareaModel.setResultItems(resultItemList);
    }
}
