// 2023-renew No22 from here
package jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.image.impl;

import jp.co.hankyuhanshin.itec.hitmall.dao.memberinfo.image.MemberImageDao;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.image.MemberImageDeleteLogic;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MemberImageDeleteLogicImpl extends AbstractShopLogic implements MemberImageDeleteLogic {
    private final MemberImageDao memberImageDao;

    @Override
    public int execute(Integer memberInfoSeq) {
        return memberImageDao.deleteMemberImageEntity(memberInfoSeq);
    }
}
// 2023-renew No22 to here
