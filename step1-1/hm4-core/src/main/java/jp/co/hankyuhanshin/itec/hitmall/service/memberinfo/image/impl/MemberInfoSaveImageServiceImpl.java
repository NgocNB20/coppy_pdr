// 2023-renew No22 from here
package jp.co.hankyuhanshin.itec.hitmall.service.memberinfo.image.impl;

import jp.co.hankyuhanshin.itec.hitmall.dto.memberinfo.image.MemberImageDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.memberinfo.image.MemberImageEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.image.MemberImageRegistLogic;
import jp.co.hankyuhanshin.itec.hitmall.service.AbstractShopService;
import jp.co.hankyuhanshin.itec.hitmall.service.memberinfo.image.MemberInfoSaveImageService;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 会員イメージサービスの実装
 *
 * @author Doan Thang (VTI Japan Co., Ltd.)
 */

@Service
@RequiredArgsConstructor
public class MemberInfoSaveImageServiceImpl extends AbstractShopService implements MemberInfoSaveImageService {

    private final MemberImageRegistLogic memberImageRegistLogic;

    /**
     * 会員画像登録を実行する
     *
     * @param memberInfoSeq 会員SEQ
     * @param memberImageNo 会員画像用の連番
     * @param memberImageFileName ファイル名
     * @param memberImageType type=会員画像種別 0：JPEG 1：PNG 2：PDF
     * @return 登録件数
     * */
    @Override
    public int execute(Integer memberInfoSeq,
                       Integer memberImageNo,
                       String memberImageFileName,
                       String memberImageType) {
        // 引数チェック
        ArgumentCheckUtil.assertNotNull("memberInfoSeq", memberInfoSeq);
        ArgumentCheckUtil.assertNotNull("memberImageNo", memberImageNo);
        ArgumentCheckUtil.assertNotNull("memberImageFileName", memberImageFileName);
        ArgumentCheckUtil.assertNotNull("memberImageType", memberImageType);

        // エンティティの作成
        MemberImageEntity memberImageEntity = getComponent(MemberImageEntity.class);
        memberImageEntity.setMemberInfoSeq(memberInfoSeq);
        memberImageEntity.setMemberImageNo(memberImageNo);
        memberImageEntity.setMemberImageFileName(memberImageFileName);
        memberImageEntity.setMemberImageType(memberImageType);

        // メンバーイメージ作成ロジック
        return memberImageRegistLogic.execute(memberImageEntity);
    }
}
// 2023-renew No22 to here
