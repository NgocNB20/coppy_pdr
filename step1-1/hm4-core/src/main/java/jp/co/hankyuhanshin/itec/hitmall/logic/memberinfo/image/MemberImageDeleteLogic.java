// 2023-renew No22 from here
package jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.image;

import jp.co.hankyuhanshin.itec.hitmall.entity.memberinfo.image.MemberImageEntity;

/**
 * メンバーエンティティのロジックを削除する
 *
 * @author Doan Thang (VTI Japan Co., Ltd.)
 */
public interface MemberImageDeleteLogic {
    /**
     * ロジック実行<br/>
     *
     * @param memberInfoSeq 会員SEQ;
     * @return 削除件数
     */
    int execute(Integer memberInfoSeq);
}
// 2023-renew No22 to here
