// 2023-renew No22 from here
package jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.image.impl;

import jp.co.hankyuhanshin.itec.hitmall.dao.memberinfo.image.MemberImageDao;
import jp.co.hankyuhanshin.itec.hitmall.entity.memberinfo.image.MemberImageEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.image.MemberImageListLogic;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class MemberImageListLogicImpl extends AbstractShopLogic implements MemberImageListLogic {

    private final MemberImageDao memberImageDao;

    @Override
    public List<MemberImageEntity> execute(Integer memberInfoSeq) {
        return memberImageDao.getListMemberImage(memberInfoSeq);
    }
}
// 2023-renew No22 to here
