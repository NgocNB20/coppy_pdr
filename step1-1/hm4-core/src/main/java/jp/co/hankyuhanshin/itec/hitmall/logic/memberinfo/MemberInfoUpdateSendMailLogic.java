// 2023-renew AddNo2 from here
package jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo;

import jp.co.hankyuhanshin.itec.hitmall.entity.memberinfo.MemberInfoEntity;

import java.util.List;

/**
 * 会員情報更新
 * メールアドレスでリマインダー
 *
 * @author Thang Doan (VJP)
 */
public interface MemberInfoUpdateSendMailLogic {

    /**
     * ...
     *
     * @param memberInfoSeq
     * @param memberInfoAfterChange
     * @param memberInfoBeforeChange
     * @param imageAddCount
     * @param modifiedList
     * @param medicalTreatmentTitleList
     * @return メール送信結果
     */
    void execute(Integer memberInfoSeq,
                    MemberInfoEntity memberInfoAfterChange,
                    MemberInfoEntity memberInfoBeforeChange,
                    Integer imageAddCount,
                    List<String> modifiedList,
                    List<String> medicalTreatmentTitleList);
}
// 2023-renew AddNo2 to here
