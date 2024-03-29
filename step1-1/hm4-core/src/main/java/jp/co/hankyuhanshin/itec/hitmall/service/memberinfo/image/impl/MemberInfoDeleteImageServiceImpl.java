// 2023-renew No22 from here
package jp.co.hankyuhanshin.itec.hitmall.service.memberinfo.image.impl;

import jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.image.MemberImageDeleteLogic;
import jp.co.hankyuhanshin.itec.hitmall.service.AbstractShopService;
import jp.co.hankyuhanshin.itec.hitmall.service.memberinfo.image.MemberInfoDeleteImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MemberInfoDeleteImageServiceImpl extends AbstractShopService implements MemberInfoDeleteImageService {
    private final MemberImageDeleteLogic memberImageDeleteLogic;

    @Override
    public int execute(Integer memberInfoSeq) {
        return memberImageDeleteLogic.execute(memberInfoSeq);
    }
}
// 2023-renew No22 to here
