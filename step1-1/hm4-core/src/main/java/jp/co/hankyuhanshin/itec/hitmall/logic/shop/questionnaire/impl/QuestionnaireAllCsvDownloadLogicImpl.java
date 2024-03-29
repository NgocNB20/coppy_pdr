package jp.co.hankyuhanshin.itec.hitmall.logic.shop.questionnaire.impl;

import jp.co.hankyuhanshin.itec.hitmall.dao.shop.questionnaire.QuestionnaireReplyDao;
import jp.co.hankyuhanshin.itec.hitmall.dto.shop.questionnaire.QuestionnaireReplyCsvDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.shop.questionnaire.QuestionnaireReplyCsvSearchDto;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.questionnaire.QuestionnaireAllCsvDownloadLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

/**
 * アンケート回答取得ロジックの実装クラス。<br />
 *
 * @author matsuda
 */
@Component
public class QuestionnaireAllCsvDownloadLogicImpl extends AbstractShopLogic
                implements QuestionnaireAllCsvDownloadLogic {

    /**
     * アンケート回答DAOクラス
     */
    private final QuestionnaireReplyDao questionnaireReplyDao;

    /**
     * コンストラクター
     *
     * @param questionnaireReplyDao アンケート回答DAOクラス
     */
    @Autowired
    public QuestionnaireAllCsvDownloadLogicImpl(QuestionnaireReplyDao questionnaireReplyDao) {
        this.questionnaireReplyDao = questionnaireReplyDao;
    }

    /**
     * アンケート回答CSV用検索条件Dtoからアンケート情報を取得する。
     *
     * @param conditionDto アンケート回答CSV用検索条件Dto
     * @return アンケート回答CSV用検索条件Dtoリスト
     */
    @Override
    public Stream<QuestionnaireReplyCsvDto> execute(QuestionnaireReplyCsvSearchDto conditionDto) {
        return this.questionnaireReplyDao.getSearchResultList(conditionDto);
    }
}
