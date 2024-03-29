// 2023-renew No22 from here
package jp.co.hankyuhanshin.itec.hitmall.entity.memberinfo.image;

import lombok.Data;
import org.seasar.doma.Column;
import org.seasar.doma.Entity;
import org.seasar.doma.Id;
import org.seasar.doma.Table;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * メンバー画像エンティティ
 *
 * @author Doan Thang (VTI Japan Co., Ltd.)
 */
@Entity
@Table(name = "MemberImage")
@Data
@Component
@Scope("prototype")
public class MemberImageEntity implements Serializable {
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * 会員SEQ（必須）
     */
    @Column(name = "memberInfoSeq")
    @Id
    private Integer memberInfoSeq;

    /**
     * 会員画像用の連番（必須）
     */
    @Column(name = "memberImageNo")
    @Id
    private Integer memberImageNo;

    /**
     * ファイル名
     */
    @Column(name = "memberImageFileName")
    private String memberImageFileName;

    /**
     * type=会員画像種別 0：JPEG 1：PNG 2：PDF
     */
    @Column(name = "memberImageType")
    private String memberImageType;

    /**
     * 登録日時（必須）
     */
    @Column(name = "registTime")
    private Timestamp registTime;

    /**
     * 更新日時（必須）
     */
    @Column(name = "updateTime")
    private Timestamp updateTime;
}
// 2023-renew No22 to here
