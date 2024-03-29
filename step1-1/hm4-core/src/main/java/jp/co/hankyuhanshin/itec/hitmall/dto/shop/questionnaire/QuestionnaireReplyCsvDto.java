package jp.co.hankyuhanshin.itec.hitmall.dto.shop.questionnaire;

import jp.co.hankyuhanshin.itec.hitmall.annotation.csv.CsvColumn;
import jp.co.hankyuhanshin.itec.hmbase.utility.DateUtility;
import lombok.Data;
import org.seasar.doma.Entity;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

/**
 * アンケート回答CSV用検索結果Dtoクラス<br />
 * <pre>
 * 検索結果を保持するためのDTOクラス。
 * </pre>
 * @author matsuda
 */
@Data
@Entity
@Component
@Scope("prototype")
public class QuestionnaireReplyCsvDto {

    /**受付日時*/
    @CsvColumn(order = 10, columnLabel = "受付日時", dateFormat = DateUtility.YMD_SLASH_HMS)
    private Timestamp registTime;

    /**会員SEQ*/
    @CsvColumn(order = 30, columnLabel = "会員SEQ")
    private Integer memberInfoSeq;

    /**会員ID*/
    @CsvColumn(order = 40, columnLabel = "会員ID")
    private String memberInfoId;

    /**受注番号*/
    @CsvColumn(order = 50, columnLabel = "受注番号")
    private String orderCode;

    /**アンケートSEQ*/
    @CsvColumn(order = 60, columnLabel = "アンケートSEQ")
    private Integer questionnaireSeq;

    /**アンケートキー*/
    @CsvColumn(order = 70, columnLabel = "アンケートキー")
    private String questionnaireKey;

    /**アンケート名*/
    @CsvColumn(order = 80, columnLabel = "アンケート名")
    private String name;

    /**設問内容1*/
    @CsvColumn(order = 90, columnLabel = "設問内容1")
    private String question1;

    /**回答1*/
    @CsvColumn(order = 100, columnLabel = "回答1")
    private String reply1;

    /**設問内容2*/
    @CsvColumn(order = 110, columnLabel = "設問内容2")
    private String question2;

    /**回答2*/
    @CsvColumn(order = 120, columnLabel = "回答2")
    private String reply2;

    /**設問内容3*/
    @CsvColumn(order = 130, columnLabel = "設問内容3")
    private String question3;

    /**回答3*/
    @CsvColumn(order = 140, columnLabel = "回答3")
    private String reply3;

    /**設問内容4*/
    @CsvColumn(order = 150, columnLabel = "設問内容4")
    private String question4;

    /**回答4*/
    @CsvColumn(order = 160, columnLabel = "回答4")
    private String reply4;

    /**設問内容5*/
    @CsvColumn(order = 170, columnLabel = "設問内容5")
    private String question5;

    /**回答5*/
    @CsvColumn(order = 180, columnLabel = "回答5")
    private String reply5;

    /**設問内容6*/
    @CsvColumn(order = 190, columnLabel = "設問内容6")
    private String question6;

    /**回答6*/
    @CsvColumn(order = 200, columnLabel = "回答6")
    private String reply6;

    /**設問内容7*/
    @CsvColumn(order = 210, columnLabel = "設問内容7")
    private String question7;

    /**回答7*/
    @CsvColumn(order = 220, columnLabel = "回答7")
    private String reply7;

    /**設問内容8*/
    @CsvColumn(order = 230, columnLabel = "設問内容8")
    private String question8;

    /**回答8*/
    @CsvColumn(order = 240, columnLabel = "回答8")
    private String reply8;

    /**設問内容9*/
    @CsvColumn(order = 250, columnLabel = "設問内容9")
    private String question9;

    /**回答9*/
    @CsvColumn(order = 260, columnLabel = "回答9")
    private String reply9;

    /**設問内容10*/
    @CsvColumn(order = 270, columnLabel = "設問内容10")
    private String question10;

    /**回答10*/
    @CsvColumn(order = 280, columnLabel = "回答10")
    private String reply10;

    /**設問内容11*/
    @CsvColumn(order = 290, columnLabel = "設問内容11")
    private String question11;

    /**回答11*/
    @CsvColumn(order = 300, columnLabel = "回答11")
    private String reply11;

    /**設問内容12*/
    @CsvColumn(order = 310, columnLabel = "設問内容12")
    private String question12;

    /**回答12*/
    @CsvColumn(order = 320, columnLabel = "回答12")
    private String reply12;

    /**設問内容13*/
    @CsvColumn(order = 330, columnLabel = "設問内容13")
    private String question13;

    /**回答13*/
    @CsvColumn(order = 340, columnLabel = "回答13")
    private String reply13;

    /**設問内容14*/
    @CsvColumn(order = 350, columnLabel = "設問内容14")
    private String question14;

    /**回答14*/
    @CsvColumn(order = 360, columnLabel = "回答14")
    private String reply14;

    /**設問内容15*/
    @CsvColumn(order = 370, columnLabel = "設問内容15")
    private String question15;

    /**回答15*/
    @CsvColumn(order = 380, columnLabel = "回答15")
    private String reply15;

    /**設問内容16*/
    @CsvColumn(order = 390, columnLabel = "設問内容16")
    private String question16;

    /**回答16*/
    @CsvColumn(order = 400, columnLabel = "回答16")
    private String reply16;

    /**設問内容17*/
    @CsvColumn(order = 410, columnLabel = "設問内容17")
    private String question17;

    /**回答17*/
    @CsvColumn(order = 420, columnLabel = "回答17")
    private String reply17;

    /**設問内容18*/
    @CsvColumn(order = 430, columnLabel = "設問内容18")
    private String question18;

    /**回答18*/
    @CsvColumn(order = 440, columnLabel = "回答18")
    private String reply18;

    /**設問内容19*/
    @CsvColumn(order = 450, columnLabel = "設問内容19")
    private String question19;

    /**回答19*/
    @CsvColumn(order = 460, columnLabel = "回答19")
    private String reply19;

    /**設問内容20*/
    @CsvColumn(order = 470, columnLabel = "設問内容20")
    private String question20;

    /**回答20*/
    @CsvColumn(order = 480, columnLabel = "回答20")
    private String reply20;

    /**設問内容21*/
    @CsvColumn(order = 490, columnLabel = "設問内容21")
    private String question21;

    /**回答21*/
    @CsvColumn(order = 500, columnLabel = "回答21")
    private String reply21;

    /**設問内容22*/
    @CsvColumn(order = 510, columnLabel = "設問内容22")
    private String question22;

    /**回答22*/
    @CsvColumn(order = 520, columnLabel = "回答22")
    private String reply22;

    /**設問内容23*/
    @CsvColumn(order = 530, columnLabel = "設問内容23")
    private String question23;

    /**回答23*/
    @CsvColumn(order = 540, columnLabel = "回答23")
    private String reply23;

    /**設問内容24*/
    @CsvColumn(order = 550, columnLabel = "設問内容24")
    private String question24;

    /**回答24*/
    @CsvColumn(order = 560, columnLabel = "回答24")
    private String reply24;

    /**設問内容25*/
    @CsvColumn(order = 570, columnLabel = "設問内容25")
    private String question25;

    /**回答25*/
    @CsvColumn(order = 580, columnLabel = "回答25")
    private String reply25;

    /**設問内容26*/
    @CsvColumn(order = 590, columnLabel = "設問内容26")
    private String question26;

    /**回答26*/
    @CsvColumn(order = 600, columnLabel = "回答26")
    private String reply26;

    /**設問内容27*/
    @CsvColumn(order = 610, columnLabel = "設問内容27")
    private String question27;

    /**回答27*/
    @CsvColumn(order = 620, columnLabel = "回答27")
    private String reply27;

    /**設問内容28*/
    @CsvColumn(order = 630, columnLabel = "設問内容28")
    private String question28;

    /**回答28*/
    @CsvColumn(order = 640, columnLabel = "回答28")
    private String reply28;

    /**設問内容29*/
    @CsvColumn(order = 650, columnLabel = "設問内容29")
    private String question29;

    /**回答29*/
    @CsvColumn(order = 660, columnLabel = "回答29")
    private String reply29;

    /**設問内容30*/
    @CsvColumn(order = 670, columnLabel = "設問内容30")
    private String question30;

    /**回答30*/
    @CsvColumn(order = 680, columnLabel = "回答30")
    private String reply30;

}
