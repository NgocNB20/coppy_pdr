package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.shop.questionnaire;

import jp.co.hankyuhanshin.itec.hitmall.admin.web.AbstractModel;
import jp.co.hankyuhanshin.itec.hitmall.annotation.converter.HCDate;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOpenDeleteStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeReplyRequiredFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeReplyType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeReplyValidatePattern;
import jp.co.hankyuhanshin.itec.hitmall.dto.graph.PieGraphDto;
import jp.co.hankyuhanshin.itec.hitmall.utility.QuestionnaireUtility;
import jp.co.hankyuhanshin.itec.hmbase.util.common.CollectionUtil;
import jp.co.hankyuhanshin.itec.hmbase.util.common.PropertiesUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.DateUtility;
import lombok.Data;
import org.springframework.util.StringUtils;

import java.sql.Timestamp;
import java.text.MessageFormat;
import java.util.List;

/**
 * アンケート詳細モデル<br />
 *
 * @author matsuda
 */
@Data
public class QuestionnaireDetailModel extends AbstractModel {

    /** 処理対象のアンケートSEQ */
    private Integer seq;

    /*
     * 画面項目（基本共通設定）
     */

    /** アンケートキー */
    private String questionnaireKey;

    /** アンケートSEQ */
    private Integer questionnaireSeq;

    /** 登録日時 */
    @HCDate(pattern = DateUtility.YMD_SLASH_HMS)
    private Timestamp registTime;

    /** 更新日時 */
    @HCDate(pattern = DateUtility.YMD_SLASH_HMS)
    private Timestamp updateTime;

    /** アンケート名称 */
    private String name;

    /** アンケート公開開始日時 */
    @HCDate(pattern = DateUtility.YMD_SLASH_HMS)
    private Timestamp openStartTime;

    /** アンケート公開終了日時 */
    @HCDate(pattern = DateUtility.YMD_SLASH_HMS)
    private Timestamp openEndTime;

    // PDR Migrate Customization from here
    /** サイトマップ出力 */
    private String siteMapFlag;
    // PDR Migrate Customization to here

    /** 管理用メモ */

    private String memo;

    /*
     * 画面項目（基本サイト別設定）
     */

    /** アンケート表示名PC */
    private String namePc;

    /** アンケート公開状態PC */
    private HTypeOpenDeleteStatus openStatusPc;

    /** アンケート説明文PC */
    private String freeTextPc;

    /** アンケート回答完了文PC */
    private String completeTextPc;

    /*
     * 画面項目（設問設定一覧）データリスト
     */
    /** 設問設定 */
    private QuestionnaireDetailModelItem questionnaireDetailModelItem;

    /** 設問設定 リスト */
    private List<QuestionnaireDetailModelItem> questionnaireDetailModelItems;

    /*
     * 画面項目（設問設定一覧）データ項目
     */
    /** jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.shop.questionnaire.DetailsPageItem#dspNo */
    private Integer dspNo;

    /** jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.shop.questionnaire.DetailsPageItem#orderDisplay */
    private Integer orderDisplay;

    /** jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.shop.questionnaire.DetailsPageItem#question */
    private String question;

    /** jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.shop.questionnaire.DetailsPageItem#openStatus */
    private HTypeOpenDeleteStatus openStatus;

    /** jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.shop.questionnaire.DetailsPageItem#replyRequiredFlag */
    private HTypeReplyRequiredFlag replyRequiredFlag;

    /** jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.shop.questionnaire.DetailsPageItem#replyType */
    private HTypeReplyType replyType;

    /** jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.shop.questionnaire.DetailsPageItem#replyValidatePattern */
    private HTypeReplyValidatePattern replyValidatePattern;

    /** jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.shop.questionnaire.DetailsPageItem#replyMaxLength */
    private Integer replyMaxLength;

    /** jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.shop.questionnaire.DetailsPageItem#choices */
    private String choices;

    /** jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.shop.questionnaire.DetailsPageItem#choicesDispItems */
    private String[] choicesDispItems;

    /** 設問設定一覧データ：選択肢（画面表示用） */
    private String choicesDisp;

    /*
     * 集計情報
     */
    /** 回答数 */
    private Integer replyCount;

    /**
     * @return true = 未集計
     */
    public boolean isEmptyReplyCount() {
        return replyCount == null;
    }

    /** 集計日時 */
    @HCDate(pattern = DateUtility.YMD_SLASH_HMS)
    private Timestamp totalRegistTime;

    /*
     * 表示項目以外
     */
    private Integer questionSeq;

    /**
     * アンケート設問回答の集計グラフ<br />
     * 2列表示にする為、Itemsをネストさせている
     */
    private List<List<PieGraphDto>> pieGraphItemsItems;

    /** １列分（２つ）のアンケート設問回答の集計グラフ */
    private List<PieGraphDto> pieGraphItems;

    /** @see jp.co.hankyuhanshin.itec.hitmall.dto.graph.PieGraphDto#graphTitle */

    private String graphTitle;

    /** @see jp.co.hankyuhanshin.itec.hitmall.dto.graph.PieGraphDto#graphDataJson */
    private String graphDataJson;

    /** @return true = アンケート設問回答の集計データなし */
    public boolean isNotEmptyPieGraphItems() {
        return !CollectionUtil.isEmpty(pieGraphItemsItems);
    }

    /**
     * @return the questionnairePageUrlPc
     */
    public String getQuestionnairePageUrlPc() {
        if (StringUtils.isEmpty(questionnaireKey)) {
            return null;
        }
        String url = PropertiesUtil.getSystemPropertiesValue("questionnaire.url.pc");
        url = MessageFormat.format(url, questionnaireKey);
        return url;
    }

    /**
     * 注文確認画面用のアンケートか否か<br/>
     * ※アンケートキーが「order」か否かで判定
     *
     * @return true：注文確認画面用
     */
    public boolean isOrderQestion() {
        QuestionnaireUtility questionnaireUtility = ApplicationContextUtility.getBean(QuestionnaireUtility.class);
        return questionnaireUtility.getQuestionnaireKeyOrder().equals(questionnaireKey);
    }

}
