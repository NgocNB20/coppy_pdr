// 2023-renew No22 from here
package jp.co.hankyuhanshin.itec.hitmall.service.memberinfo.image;

import jp.co.hankyuhanshin.itec.hitmall.dto.memberinfo.image.MemberImageDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.memberinfo.image.MemberImageEntity;

/**
 * 会員イメージサービスインターフェース
 *
 * @author Doan Thang (VTI Japan Co., Ltd.)
 */
public interface MemberInfoSaveImageService {
    int execute(Integer memberInfoSeq, Integer memberImageNo, String memberImageFileName, String memberImageType);
}
// 2023-renew No22 to here
