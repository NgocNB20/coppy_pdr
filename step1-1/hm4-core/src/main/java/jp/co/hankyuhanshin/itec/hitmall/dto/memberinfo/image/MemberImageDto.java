// 2023-renew No22 from here
package jp.co.hankyuhanshin.itec.hitmall.dto.memberinfo.image;

import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * メンバーイメージDTO
 *
 * @author Doan Thang (VTI Japan Co., Ltd.)
 */
@Data
@Component
@Scope("prototype")
public class MemberImageDto implements Serializable {
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * 会員SEQ（必須）
     */
    private Integer memberInfoSeq;

    /**
     * 会員画像用の連番（必須）
     */
    private Integer memberImageNo;

    /**
     * ファイル名
     */
    private String memberImageFileName;

    /**
     * type=会員画像種別 0：JPEG 1：PNG 2：PDF
     */
    private String memberImageType;
}
// 2023-renew No22 to here
