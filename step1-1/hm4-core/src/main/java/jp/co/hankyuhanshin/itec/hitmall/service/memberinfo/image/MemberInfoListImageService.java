// 2023-renew No22 from here
package jp.co.hankyuhanshin.itec.hitmall.service.memberinfo.image;

import jp.co.hankyuhanshin.itec.hitmall.dto.memberinfo.image.MemberImageDto;

import java.util.List;

/**
 * 会員画像一覧サービスインターフェース
 *
 * @author Doan Thang (VTI Japan Co., Ltd.)
 */
public interface MemberInfoListImageService {
    List<MemberImageDto> execute(Integer memberInfoSeq);
}
// 2023-renew No22 to here
