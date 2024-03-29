package jp.co.hankyuhanshin.itec.hitmall.dto.shop.questionnaire;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOpenDeleteStatus;
import jp.co.hankyuhanshin.itec.hmbase.dto.AbstractConditionDto;
import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.List;

/**
 * アンケート回答CSV用検索条件Dtoクラス<br />
 * <pre>
 * 検索条件を保持するためのDTOクラス。
 * </pre>
 * @author matsuda
 */
@Data
@Component
@Scope("prototype")
public class QuestionnaireReplyCsvSearchDto extends AbstractConditionDto {

    /** serialVersionUID */
    private static final long serialVersionUID = 1L;

    /** サイト区分 */
    private String site;

    /** ショップSEQ*/
    private Integer shopSeq;

    /** アンケートSEQ*/
    private Integer questionnaireSeq;

    /** アンケートキー*/
    private String questionnaireKey;

    /**アンケート名称 */
    private String name;

    /**アンケート公開状態*/
    private HTypeOpenDeleteStatus openStatus;

    /**アンケート公開開始日時(From)*/
    private Timestamp openStartTimeFrom;

    /**アンケート公開開始日時(To)*/
    private Timestamp openStartTimeTo;

    /**アンケート公開終了日時(From)*/
    private Timestamp openEndTimeFrom;

    /**アンケート公開終了日時(To)*/
    private Timestamp openEndTimeTo;

    /** 受付サイト */
    private List<String> siteTypeList;

    /** 受付デバイス*/
    private List<String> deviceTypeList;

    /**受付日時(From)*/
    private Timestamp registTimeFrom;

    /**受付日時(To)*/
    private Timestamp registTimeTo;

    /**受注番号*/
    private String orderCode;

    /**会員SEQ*/
    private Integer memberInfoSeq;

    /**会員ID*/
    private String memberInfoId;

}
