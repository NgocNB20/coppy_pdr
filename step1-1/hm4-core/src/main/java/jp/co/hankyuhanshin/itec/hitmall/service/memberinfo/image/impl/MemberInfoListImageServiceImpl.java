// 2023-renew No22 from here
package jp.co.hankyuhanshin.itec.hitmall.service.memberinfo.image.impl;

import jp.co.hankyuhanshin.itec.hitmall.dto.memberinfo.image.MemberImageDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.memberinfo.image.MemberImageEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.image.MemberImageListLogic;
import jp.co.hankyuhanshin.itec.hitmall.service.AbstractShopService;
import jp.co.hankyuhanshin.itec.hitmall.service.memberinfo.image.MemberInfoListImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class MemberInfoListImageServiceImpl extends AbstractShopService implements MemberInfoListImageService {
    private final MemberImageListLogic memberImageListLogic;

    @Override
    public List<MemberImageDto> execute(Integer memberInfoSeq) {
        List<MemberImageEntity> memberImageEntityList = memberImageListLogic.execute(memberInfoSeq);
        List<MemberImageDto> memberImageDtoList = new ArrayList<>();
        for (MemberImageEntity memberImageEntity : memberImageEntityList) {
            MemberImageDto memberImageDto = new MemberImageDto();
            memberImageDto.setMemberInfoSeq(memberImageEntity.getMemberInfoSeq());
            memberImageDto.setMemberImageNo(memberImageEntity.getMemberImageNo());
            memberImageDto.setMemberImageType(memberImageEntity.getMemberImageType());
            memberImageDto.setMemberImageFileName(memberImageEntity.getMemberImageFileName());
            memberImageDtoList.add(memberImageDto);
        }
        return memberImageDtoList;
    }
}
// 2023-renew No22 to here
