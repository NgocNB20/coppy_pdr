package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.mailtemplate.send;

import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.common.validation.group.ConfirmGroup;
import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.common.validation.group.DisplayMailBodyGroup;
import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.mailtemplate.send.validation.group.SelectGroup;
import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.mailtemplate.send.validation.group.SendTestGroup;
import jp.co.hankyuhanshin.itec.hitmall.admin.web.AbstractModel;
import jp.co.hankyuhanshin.itec.hitmall.annotation.validator.HVMailAddressExtended;
import jp.co.hankyuhanshin.itec.hitmall.annotation.validator.HVMailAddressExtendedArray;
import jp.co.hankyuhanshin.itec.hitmall.annotation.validator.HVSpecialCharacter;
import jp.co.hankyuhanshin.itec.hitmall.application.mailtemplate.MailTemplateTypeEntry;
import jp.co.hankyuhanshin.itec.hitmall.dto.mail.MailDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.mail.MailSendDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

@Data
@EqualsAndHashCode(callSuper = false)
public class MailtemplateSendModel extends AbstractModel {

    /**
     * エラーコード：必須入力、タブ・半角スペースのみ入力時エラー
     */
    public static final String MSGCD_REQUIRED_EMPTY_FAIL = "AYM001702W";

    /**
     * 遷移前アプリケーションより受け取った値
     */
    private MailSendDto mailSendDto;

    /**
     * テキスト版メールを選択可能か
     */
    private boolean selectableTextMailAddresses;

    /**
     * HTML版メールを選択可能か
     */
    private boolean selectableHtmlMailAddresses;

    /**
     * 携帯版メールを選択可能か
     */
    private boolean selectableMobileMailAddresses;

    /**
     * テンプレートSEQ
     */
    @NotNull(message = "テンプレート を選択してください。", groups = {SelectGroup.class})
    private Integer mailTemplateSeq;

    /**
     * テンプレート名称
     */
    private String mailTemplateName;

    /**
     * テンプレートタイプ
     */
    private String mailTemplateType;

    /**
     * テンプレートタイプ名称
     */
    private String mailTemplateTypeName;

    /**
     * getMailBodyValidator() で使用する mailTemplateType.dicon の内容
     */
    public List<MailTemplateTypeEntry> mailTemplateTypeList;

    /**
     * 初期化済み
     */
    private boolean initialized;

    /**
     * 見出しアイテムリスト
     */
    private List<MailtemplateSelectItem> indexItems;

    /**
     * FROM アドレス
     */
    @NotEmpty(groups = {ConfirmGroup.class, SendTestGroup.class})
    @Length(min = 1, max = 200, groups = {ConfirmGroup.class, SendTestGroup.class})
    @HVMailAddressExtended(groups = {ConfirmGroup.class, SendTestGroup.class})
    private String fromAddress;

    /**
     * BCCアドレス
     */
    @HVSpecialCharacter(allowPunctuation = true, groups = {ConfirmGroup.class})
    @Length(min = 1, max = 200, groups = {ConfirmGroup.class, SendTestGroup.class})
    @HVMailAddressExtendedArray(groups = {ConfirmGroup.class})
    private String bccAddress;

    /**
     * テスト送信アドレス
     */
    @NotEmpty(groups = {SendTestGroup.class})
    @Length(min = 1, max = 200, groups = {ConfirmGroup.class, SendTestGroup.class})
    @HVMailAddressExtended(groups = {ConfirmGroup.class, SendTestGroup.class})
    private String testAddress;

    /**
     * 編集中のメール件名  - 動的バリデーション適用項目
     */
    private String mailTitle;

    /**
     * 編集中のメール本文  - 動的バリデーション適用項目
     */
    // 2023-renew general-mail from here
    @HVSpecialCharacter(allowPunctuation = true)
    private String mailBody;

    /**
     * 確認画面で表示用のメール本文
     */
    @HVSpecialCharacter(allowPunctuation = true)
    private String confirmDisplayMailBody;
    // 2023-renew general-mail to here

    /**
     * 現在編集中の版
     */
    private int editingVersion;

    /**
     * 直前まで編集していた版
     */
    private int lastEditingVersion;

    /**
     * プレビュー表示中かどうか
     */
    private boolean showingPreview;

    /**
     * プレースホルダガイド
     */
    private String placeholderGuide;

    /**
     * 確認画面から戻ってきたかどうか
     */
    private boolean backwardTransition;

    /**
     * テストメールを送信した
     */
    private boolean mailSent;

    /**
     * テストメール送信失敗
     */
    private boolean mailSendFailure;

    /**
     * 機能名称
     */
    private String appName;

    /**
     * テンプレート選択ページをスキップしたかどうか
     */
    private boolean skippedSelectPage;

    /**
     * 戻り先ページ
     */
    private String previousPage;

    /**
     * 再検索キー
     */
    private String md;

    /**
     * 自分にもメールを送信するか
     */
    private boolean sendMeToo;

    /**
     * 送信失敗情報
     */
    private List<MailDto> failureList;

    /**
     * メール送信したかどうか
     */
    private boolean sendOk = false;

    /**
     * 送信処理に失敗失敗したかどうか
     */
    private boolean anyFailure;

    /**
     * 送信処理に成功した件数が0件以上あるか
     */
    private boolean anySuccesser;

    // 2023-renew general-mail from here
    /**
     * 編集画面にてメール本文テキストエリアを表示するかどうか
     */
    private boolean mailBodyDisplayFlag = false;

    /**
     * プレースホルダ情報に使用される変数マップ
     */
    private Map<String, Object> mailContentsMap;
    // 2023-renew general-mail to here

    //
    // 画面項目用アクセッサ
    //

    /**
     * メールの件数を返す
     *
     * @return 件数
     */
    public String getTotalToAddressCount() {
        return Integer.toString(this.mailSendDto.getMailDtoList().size());
    }

    /**
     * キャンペーン系画面からの遷移か
     *
     * @return そうである場合 true
     */
    public boolean isLocalNavCampaign() {
        if (this.mailSendDto.getApplication() == null) {
            return false;
        }

        return MailSendDto.CAMPAGIN == this.mailSendDto.getApplication();
    }

    /**
     * カテゴリ系画面からの遷移か
     *
     * @return そうである場合 true
     */
    public boolean isLocalNavCategory() {
        if (this.mailSendDto.getApplication() == null) {
            return false;
        }

        return MailSendDto.CATEGORY == this.mailSendDto.getApplication();
    }

    /**
     * 商品系画面からの遷移か
     *
     * @return そうである場合 true
     */
    public boolean isLocalNavGoods() {
        if (this.mailSendDto.getApplication() == null) {
            return false;
        }

        return MailSendDto.GOODS == this.mailSendDto.getApplication();
    }

    /**
     * 会員系画面からの遷移か
     *
     * @return そうである場合 true
     */
    public boolean isLocalNavMember() {
        if (this.mailSendDto.getApplication() == null) {
            return false;
        }

        return MailSendDto.MEMBER == this.mailSendDto.getApplication();
    }

    /**
     * 会員系画面からの遷移か
     *
     * @return そうである場合 true
     */
    public boolean isLocalNavOrder() {
        if (this.mailSendDto.getApplication() == null) {
            return false;
        }

        return MailSendDto.ORDER == this.mailSendDto.getApplication();
    }

    /**
     * ショップ計画面からの遷移か
     *
     * @return そうである場合 true
     */
    public boolean isLocalNavShop() {
        if (this.mailSendDto.getApplication() == null) {
            return false;
        }

        return MailSendDto.SHOP == this.mailSendDto.getApplication();
    }

    /**
     * システム系画面からの遷移か
     *
     * @return そうである場合 true
     */
    public boolean isLocalNavSystem() {
        if (this.mailSendDto.getApplication() == null) {
            return false;
        }

        return MailSendDto.SYSTEM == this.mailSendDto.getApplication();
    }

    /**
     * 集計系画面からの遷移か
     *
     * @return そうである場合 true
     */
    public boolean isLocalNavTotaling() {
        if (this.mailSendDto.getApplication() == null) {
            return false;
        }

        return MailSendDto.TOTALING == this.mailSendDto.getApplication();
    }

    /**
     * 定義されていない画面からの遷移か
     *
     * @return そうである場合 true
     */
    public boolean isKnownApplication() {

        return this.mailSendDto.getApplication() == null;
    }

}
