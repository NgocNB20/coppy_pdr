/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.administrator.regist;

import jp.co.hankyuhanshin.itec.hitmall.admin.web.AbstractModel;
import jp.co.hankyuhanshin.itec.hitmall.annotation.converter.HCHankaku;
import jp.co.hankyuhanshin.itec.hitmall.annotation.converter.HCZenkaku;
import jp.co.hankyuhanshin.itec.hitmall.annotation.converter.HCZenkakuKana;
import jp.co.hankyuhanshin.itec.hitmall.annotation.validator.HVMailAddress;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypePasswordNeedChangeFlag;
import jp.co.hankyuhanshin.itec.hmbase.constant.RegularExpressionsConstants;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.sql.Timestamp;
import java.util.Map;

/**
 * 新規運営者登録ページ<br/>
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class AdministratorRegistModel extends AbstractModel {

    /**
     * 選択した運営者状態が不正
     */
    public static final String MSGCD_ADMINISTRATOR_STATUS_ERROR = "AYO000601";

    /**
     * 運営者IDが不正
     */
    public static final String MSGCD_ADMINISTRATOR_ID_ERROR = "{AYO000602W}";

    /**
     * 一意制約エラー
     */
    public static final String MSGCD_ADMINISTRATOR_ID_DUPLICATION = "AYO000701";

    /**
     * コンストラクタ<br/>
     * 初期値の設定<br/>
     */
    public AdministratorRegistModel() {
        super();
    }

    /**
     * アクセス処理モード
     */
    private String inputMd;

    /**
     * 検索条件復元判断用フラグ(検索画面に遷移する際に指定する。)
     */
    private String md;

    /**
     * 会員ID<br/>
     */
    @NotEmpty
    @Pattern(regexp = "[a-zA-Z0-9]+", message = MSGCD_ADMINISTRATOR_ID_ERROR)
    @Length(min = 1, max = 20)
    @HCHankaku
    private String administratorId;

    /**
     * パスワード<br/>
     */
    @NotEmpty
    @Pattern(regexp = RegularExpressionsConstants.PASSWORD_NUMBER_REGEX,
             message = "{HPasswordValidator.INVALID_detail}")
    @Length(min = 6, max = 20)
    private String administratorPassword;

    /**
     * 氏名（姓）<br/>
     */
    @NotEmpty
    @Pattern(regexp = RegularExpressionsConstants.ZENKAKU_REGEX, message = "{HZenkakuValidator.INVALID_detail}")
    @Length(min = 1, max = 15)
    @HCZenkaku
    private String administratorLastName;

    /**
     * 氏名（名）<br/>
     */
    @NotEmpty
    @Pattern(regexp = RegularExpressionsConstants.ZENKAKU_REGEX, message = "{HZenkakuValidator.INVALID_detail}")
    @Length(min = 1, max = 15)
    @HCZenkaku
    private String administratorFirstName;

    /**
     * フリガナ(姓)<br/>
     */
    @NotEmpty
    @Pattern(regexp = RegularExpressionsConstants.ZENKAKU_KANA_REGEX,
             message = "{HZenkakuKanaValidator.INVALID_detail}")
    @Length(min = 1, max = 15)
    @HCZenkakuKana
    private String administratorLastKana;

    /**
     * フリガナ(名)<br/>
     */
    @NotEmpty
    @Pattern(regexp = RegularExpressionsConstants.ZENKAKU_KANA_REGEX,
             message = "{HZenkakuKanaValidator.INVALID_detail}")
    @Length(min = 1, max = 15)
    @HCZenkakuKana
    private String administratorFirstKana;

    /**
     * メールアドレス<br/>
     */
    @NotEmpty
    @HVMailAddress
    @Length(min = 1, max = 160)
    @HCHankaku
    private String administratorMail;

    /**
     * 管理者状態リスト<br/>
     */
    private Map<String, String> administratorStatusItems;

    /**
     * 管理者グループリスト<br/>
     */
    private Map<String, String> administratorGroupSeqItems;

    /**
     * 管理者状態<br/>
     */
    @NotEmpty(message = "{HRequiredValidator.REQUIRED_detail}")
    private String administratorStatus;

    /**
     * 管理者グループ名<br/>
     */
    private String administratorGroupName;

    /**
     * 管理者グループSEQ<br/>
     */
    @NotEmpty(message = "{HRequiredValidator.REQUIRED_detail}")
    private String administratorGroupSeq;

    /**
     * 利用開始日<br/>
     */
    private Timestamp useStartDate;

    /**
     * 利用終了日<br/>
     */
    private Timestamp useEndDate;

    /**
     * 不正操作を判断するためのフラグ
     */
    private boolean normality;

    /**
     * パスワード変更要求フラグ<br/>
     */
    private HTypePasswordNeedChangeFlag passwordNeedChangeFlag;

}
