/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.mail;

import jp.co.hankyuhanshin.itec.hitmall.front.entity.memberinfo.MemberInfoEntity;
import jp.co.hankyuhanshin.itec.hitmall.front.web.AbstractModel;
import lombok.Data;

/**
 * メールアドレス変更 Model
 *
 * @author kn23834
 * @version $Revision: 1.0 $
 */
@Data
public class MailModel extends AbstractModel {

    /**
     * 会員エンティティ<br/>
     */
    private MemberInfoEntity memberInfoEntity;

    /**
     * メールURLパラメータ<br/>
     */
    private String mid;

    /**
     * 変更前メールアドレス<br/>
     */
    private String preMemberInfoMail;

    /**
     * 変更後メールアドレス<br/>
     */
    private String memberInfoMail;

    /**
     * 無効なURLからの遷移かどうか<br/>
     * ※デフォルトtrue。Controllerで正常処理実行時のみfalseが設定
     */
    private boolean errorUrl = true;

}
