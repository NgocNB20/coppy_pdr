// PDR Migrate Customization from here

package jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.member.confirm;

import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.MemberInfoApi;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.CardInfoDeleteRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.CardInfoRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.CardInfoResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.MemberInfoEntityResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.MemberInfoImageEntityResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.MemberInfoImageListResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.WebapiApi;
import jp.co.hankyuhanshin.itec.hitmall.front.annotation.exception.HEHandler;
import jp.co.hankyuhanshin.itec.hitmall.front.base.exception.AppLevelListException;
import jp.co.hankyuhanshin.itec.hitmall.front.base.util.common.CollectionUtil;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeDeliveryCompletePermitFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeMedicalTreatmentFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeMetalPermitFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeOrderCompletePermitFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeSendMailPermitFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.common.CheckMessageDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.memberinfo.MedicalTreatmentDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.multipayment.ComResultDto;
import jp.co.hankyuhanshin.itec.hitmall.front.entity.memberinfo.MemberInfoEntity;
import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.regist.RegistUploadFile;
import jp.co.hankyuhanshin.itec.hitmall.front.utility.ComTransactionUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.web.AbstractController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;

/**
 * 会員情報画面 Controller
 *
 * @author kn23834
 * @version $Revision: 1.0 $
 */
@RequestMapping("/member/confirm")
@Controller
@SessionAttributes(value = "memberConfirmModel")
public class MemberConfirmController extends AbstractController {

    /**
     * ログ
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(MemberConfirmController.class);

    /**
     * 会員情報確認画面Dxo
     */
    private final MemberConfirmHelper memberConfirmHelper;

    /**
     * 通信ユーティリティ
     */
    private final ComTransactionUtility comTransactionUtility;

    /**
     * 診療項目Dto
     */
    private final MedicalTreatmentDto medicalTreatmentDto;

    /**
     * 登録完了メッセージ
     */
    protected static final String MSG_UPD_CONF = "PDR-0429-001-A-I";

    /**
     * 会員Api
     */
    private final MemberInfoApi memberInfoApi;

    /**
     * WEB-APIApi
     */
    private final WebapiApi webapiApi;

    // 2023-renew No22 from here
    /**
     * 実パスストレージのアップロードファイル
     */
    private static final String REAL_PATH = "real.path.conf.document";
    /**
     * ファイルを保存するディレクトリのパス
     */
    private static final String GET_REAL_PATH = "/confirm-docs/";
    // 2023-renew No22 to here

    // 2023-renew AdNo2 from here
    /**
     * 会員情報失敗エラー<br/>
     * <code>MSGCD_MEMBERINFOENTITYDTO_NULL</code>
     */
    private static final String MSGCD_MEMBERINFOENTITYDTO_NULL = "SMM000201W";
    // 2023-renew AdNo2 to here

    /**
     * コンストラクタ
     *
     * @param memberConfirmHelper アドレス帳 Helperクラス
     * @param medicalTreatmentDto 診療項目Dtoクラス
     * @param memberInfoApi
     * @param webapiApi
     */
    @Autowired
    public MemberConfirmController(MemberConfirmHelper memberConfirmHelper,
                                   MedicalTreatmentDto medicalTreatmentDto,
                                   ComTransactionUtility comTransactionUtility,
                                   MemberInfoApi memberInfoApi,
                                   WebapiApi webapiApi) {
        this.memberConfirmHelper = memberConfirmHelper;
        this.medicalTreatmentDto = medicalTreatmentDto;
        this.comTransactionUtility = comTransactionUtility;
        this.memberInfoApi = memberInfoApi;
        this.webapiApi = webapiApi;
    }

    /**
     * 初期処理
     *
     * @return 自画面
     */
    @GetMapping(value = {"/", "/index.html"})
    @HEHandler(exception = AppLevelListException.class, returnView = "member/confirm/index")
    protected String doLoadIndex(MemberConfirmModel memberConfirmModel,
                                 RedirectAttributes redirectAttributes,
                                 Model model,
                                 // 2023-renew No22 from here
                                 HttpSession httpSession) {
        // 2023-renew No22 to here

        // モデル初期化
        clearModel(MemberConfirmModel.class, memberConfirmModel, model);

        // 会員情報の取得 会員メニュー中は会員状態を見ないで取得
        Integer memberInfoSeq = getCommonInfo().getCommonInfoUser().getMemberInfoSeq();

        // 2023-renew No22 from here
        getUploadFiles(memberConfirmModel, REAL_PATH, GET_REAL_PATH, memberInfoSeq.toString());
        // 2023-renew No22 to here

        // 2023-renew AdNo2 from here
        MemberInfoEntityResponse response = null;
        try {
            try {
                response = memberInfoApi.getByMemberInfoSeq(memberInfoSeq);
            } catch (HttpClientErrorException e) {
                LOGGER.error("例外処理が発生しました", e);
                // AppLevelListExceptionへ変換
                addAppLevelListException(e);
                throwMessage();
            }
        } catch (Throwable ex) {
            addMessage(MSGCD_MEMBERINFOENTITYDTO_NULL, redirectAttributes, model);
            return "redirect:/error.html";
        }
        // 2023-renew AdNo2 to here
        MemberInfoEntity memberInfoEntity = memberConfirmHelper.toMemberInfoEntity(response);

        // 画面に反映
        memberConfirmHelper.toPageForLoad(memberInfoEntity, memberConfirmModel);

        // 診療項目を設定
        setMedicalTreatment(memberInfoEntity, memberConfirmModel);

        // メールお得情報設定
        if (HTypeSendMailPermitFlag.ON.equals(memberInfoEntity.getSendMailPermitFlag())) {
            memberConfirmModel.setSendMail(true);
        } else {
            memberConfirmModel.setSendMail(false);
        }

        if (HTypeMetalPermitFlag.ON.equals(memberInfoEntity.getMetalPermitFlag())) {
            memberConfirmModel.setMetalPermitFlag(true);
        } else {
            memberConfirmModel.setMetalPermitFlag(false);
        }

        // 2023-renew No79 from here
        if (HTypeOrderCompletePermitFlag.ON.equals(memberInfoEntity.getOrderCompletePermitFlag())) {
            memberConfirmModel.setOrderCompletePermitFlag(true);
        } else {
            memberConfirmModel.setOrderCompletePermitFlag(false);
        }

        if (HTypeDeliveryCompletePermitFlag.ON.equals(memberInfoEntity.getDeliveryCompletePermitFlag())) {
            memberConfirmModel.setDeliveryCompletePermitFlag(true);
        } else {
            memberConfirmModel.setDeliveryCompletePermitFlag(false);
        }
        // 2023-renew No79 to here

        // PDR Migrate Customization from here

        // サブシステム側との連携のため
        // クレジットカード情報保持種別に関わらず
        // ペイジェントを参照し、該当カード情報が存在するか確認
        ComResultDto comResultDto = null;
        try {
            // カード情報照会
            CardInfoRequest cardInfoRequest = new CardInfoRequest();
            cardInfoRequest.setSessionId(getCommonInfo().getCommonInfoBase().getSessionId());
            cardInfoRequest.setAccessUid(getCommonInfo().getCommonInfoBase().getAccessUid());
            CardInfoResponse cardInfoResponse = null;
            try {
                cardInfoResponse = memberInfoApi.getCardInfoByMemberInfoSeq(memberInfoSeq, cardInfoRequest);
            } catch (HttpClientErrorException e) {
                LOGGER.error("例外処理が発生しました", e);
                // AppLevelListExceptionへ変換
                addAppLevelListException(e);
                throwMessage();
            }

            comResultDto = memberConfirmHelper.toComResultDto(cardInfoResponse);
        } catch (Throwable e) {
            LOGGER.error("例外処理が発生しました", e);
            addMessage(ComTransactionUtility.MSGCD_CREDIT_CARD_INFO_GET_ERROR, null, redirectAttributes, model);
            return "member/confirm/index";
        }

        // 存在しない
        if (comTransactionUtility.isErrorOccurred(comResultDto)) {
            CheckMessageDto messageDto = comResultDto.getMessageList().get(0);
            addMessage(messageDto.getMessageId(), messageDto.getArgs(), redirectAttributes, model);
            return "member/confirm/index";
        }

        // ページ情報セット
        memberConfirmHelper.setRegistedPaygentCardInfo(comResultDto, memberConfirmModel);

        // PDR Migrate Customization to here

        return "member/confirm/index";
    }

    // // 2023-renew AddNo2 from here
    //    @PostMapping(value = {"/", "index.html"}, params = "doOnceUpdate")
    //    @HEHandler(exception = AppLevelListException.class, returnView = "member/confirm/index")
    //    public String doOnceUpdate(MemberConfirmModel memberConfirmModel,
    //                               RedirectAttributes redirectAttributes,
    //                               Model model,
    //                               SessionStatus sessionStatus) {
    //
    //        // 会員情報の取得 会員メニュー中は会員状態を見ないで取得
    //        Integer memberInfoSeq = getCommonInfo().getCommonInfoUser().getMemberInfoSeq();
    //
    //        MemberInfoConfirmScreenUpdateRequest request = new MemberInfoConfirmScreenUpdateRequest();
    //        request.setMemberInfoSeq(memberInfoSeq);
    //
    //        if (memberConfirmModel.isSendMail()) {
    //            request.setSendMail(true);
    //        } else {
    //            request.setSendMail(false);
    //        }
    //
    //        if (memberConfirmModel.isMetalPermitFlag()) {
    //            request.setMetalPermitFlag(true);
    //        } else {
    //            request.setMetalPermitFlag(false);
    //        }
    //        // EC会員情報更新処理
    //        try {
    //            memberInfoApi.updateConfirmScreen(request);
    //        } catch (HttpClientErrorException e) {
    //            LOGGER.error("例外処理が発生しました", e);
    //            // AppLevelListExceptionへ変換
    //            addAppLevelListException(e);
    //            throwMessage();
    //        }
    //
    //        // 変更完了メッセージ表示
    //        addInfoMessage(MSG_UPD_CONF, null, redirectAttributes, model);
    //
    //        // Modelをセッションより破棄
    //        sessionStatus.setComplete();
    //
    //        return "redirect:/member/confirm/index.html";
    //    }
    // // 2023-renew AddNo2 to here

    @PostMapping(value = {"/", "/index.html"}, params = "doOnceDelete")
    @HEHandler(exception = AppLevelListException.class, returnView = "member/confirm/index")
    public String doOnceDelete(MemberConfirmModel memberConfirmModel,
                               RedirectAttributes redirectAttributes,
                               Model model) {

        // 指定したカードの情報取得
        MemberRegistedCardItem item =
                        memberConfirmModel.getRegistedCardItems().get(memberConfirmModel.getRegistedCardIndex());

        CardInfoDeleteRequest request = new CardInfoDeleteRequest();
        request.setCardId(item.getCardId());
        request.setMemberInfoSeq(String.valueOf(getCommonInfo().getCommonInfoUser().getMemberInfoSeq()));
        // カード削除処理
        try {
            memberInfoApi.updateCardInfo(request);
        } catch (HttpClientErrorException e) {
            LOGGER.error("例外処理が発生しました", e);
            // AppLevelListExceptionへ変換
            addAppLevelListException(e);
            throwMessage();
        }

        return "redirect:/member/confirm/index.html";
    }

    /**
     * 診療項目設定
     *
     * @param memberInfoEntity 会員Entity
     */
    protected void setMedicalTreatment(MemberInfoEntity memberInfoEntity, MemberConfirmModel memberConfirmModel) {

        // 診療項目1_タイトル
        memberConfirmModel.setMedicalTreatment1Title(medicalTreatmentDto.getMedicalTreatment1Title());
        // 診療項目2_タイトル
        memberConfirmModel.setMedicalTreatment2Title(medicalTreatmentDto.getMedicalTreatment2Title());
        // 診療項目3_タイトル
        memberConfirmModel.setMedicalTreatment3Title(medicalTreatmentDto.getMedicalTreatment3Title());
        // 診療項目4_タイトル
        memberConfirmModel.setMedicalTreatment4Title(medicalTreatmentDto.getMedicalTreatment4Title());
        // 診療項目5_タイトル
        memberConfirmModel.setMedicalTreatment5Title(medicalTreatmentDto.getMedicalTreatment5Title());
        // 診療項目6_タイトル
        memberConfirmModel.setMedicalTreatment6Title(medicalTreatmentDto.getMedicalTreatment6Title());
        // 診療項目7_タイトル
        memberConfirmModel.setMedicalTreatment7Title(medicalTreatmentDto.getMedicalTreatment7Title());
        // 診療項目8_タイトル
        memberConfirmModel.setMedicalTreatment8Title(medicalTreatmentDto.getMedicalTreatment8Title());
        // 診療項目9_タイトル
        memberConfirmModel.setMedicalTreatment9Title(medicalTreatmentDto.getMedicalTreatment9Title());
        // 診療項目10_タイトル
        memberConfirmModel.setMedicalTreatment10Title(medicalTreatmentDto.getMedicalTreatment10Title());

        // 診療項目1_表示判定
        memberConfirmModel.setMedicalTreatment1Disp(medicalTreatmentDto.getMedicalTreatment1Disp());
        // 診療項目2_表示判定
        memberConfirmModel.setMedicalTreatment2Disp(medicalTreatmentDto.getMedicalTreatment2Disp());
        // 診療項目3_表示判定
        memberConfirmModel.setMedicalTreatment3Disp(medicalTreatmentDto.getMedicalTreatment3Disp());
        // 診療項目4_表示判定
        memberConfirmModel.setMedicalTreatment4Disp(medicalTreatmentDto.getMedicalTreatment4Disp());
        // 診療項目5_表示判定
        memberConfirmModel.setMedicalTreatment5Disp(medicalTreatmentDto.getMedicalTreatment5Disp());
        // 診療項目6_表示判定
        memberConfirmModel.setMedicalTreatment6Disp(medicalTreatmentDto.getMedicalTreatment6Disp());
        // 診療項目7_表示判定
        memberConfirmModel.setMedicalTreatment7Disp(medicalTreatmentDto.getMedicalTreatment7Disp());
        // 診療項目8_表示判定
        memberConfirmModel.setMedicalTreatment8Disp(medicalTreatmentDto.getMedicalTreatment8Disp());
        // 診療項目9_表示判定
        memberConfirmModel.setMedicalTreatment9Disp(medicalTreatmentDto.getMedicalTreatment9Disp());
        // 診療項目10_表示判定
        memberConfirmModel.setMedicalTreatment10Disp(medicalTreatmentDto.getMedicalTreatment10Disp());

        // 診療項目
        memberConfirmModel.setMedicalTreatment(memberInfoEntity.getMedicalTreatmentFlag());

        String[] medicalTreatment =
                        String.format("%-10s", memberInfoEntity.getMedicalTreatmentFlag()).replace(" ", "0").split("");

        for (int i = 1; i < medicalTreatment.length; i++) {
            // 2023-renew AddNo2 from here
            if (HTypeMedicalTreatmentFlag.ON.getValue().equals(medicalTreatment[i - 1])) {
                // 2023-renew AddNo2 to here
                switch (i) {
                    case 1:
                        memberConfirmModel.setMedicalTreatment1(HTypeMedicalTreatmentFlag.ON.getValue());
                        continue;
                    case 2:
                        memberConfirmModel.setMedicalTreatment2(HTypeMedicalTreatmentFlag.ON.getValue());
                        continue;
                    case 3:
                        memberConfirmModel.setMedicalTreatment3(HTypeMedicalTreatmentFlag.ON.getValue());
                        continue;
                    case 4:
                        memberConfirmModel.setMedicalTreatment4(HTypeMedicalTreatmentFlag.ON.getValue());
                        continue;
                    case 5:
                        memberConfirmModel.setMedicalTreatment5(HTypeMedicalTreatmentFlag.ON.getValue());
                        continue;
                    case 6:
                        memberConfirmModel.setMedicalTreatment6(HTypeMedicalTreatmentFlag.ON.getValue());
                        continue;
                    case 7:
                        memberConfirmModel.setMedicalTreatment7(HTypeMedicalTreatmentFlag.ON.getValue());
                        continue;
                    case 8:
                        memberConfirmModel.setMedicalTreatment8(HTypeMedicalTreatmentFlag.ON.getValue());
                        continue;
                    case 9:
                        memberConfirmModel.setMedicalTreatment9(HTypeMedicalTreatmentFlag.ON.getValue());
                        continue;
                    case 10:
                        memberConfirmModel.setMedicalTreatment10(HTypeMedicalTreatmentFlag.ON.getValue());
                        continue;
                    default:
                        break;
                }
            } else {
                switch (i) {
                    case 1:
                        memberConfirmModel.setMedicalTreatment1(HTypeMedicalTreatmentFlag.OFF.getValue());
                        continue;
                    case 2:
                        memberConfirmModel.setMedicalTreatment2(HTypeMedicalTreatmentFlag.OFF.getValue());
                        continue;
                    case 3:
                        memberConfirmModel.setMedicalTreatment3(HTypeMedicalTreatmentFlag.OFF.getValue());
                        continue;
                    case 4:
                        memberConfirmModel.setMedicalTreatment4(HTypeMedicalTreatmentFlag.OFF.getValue());
                        continue;
                    case 5:
                        memberConfirmModel.setMedicalTreatment5(HTypeMedicalTreatmentFlag.OFF.getValue());
                        continue;
                    case 6:
                        memberConfirmModel.setMedicalTreatment6(HTypeMedicalTreatmentFlag.OFF.getValue());
                        continue;
                    case 7:
                        memberConfirmModel.setMedicalTreatment7(HTypeMedicalTreatmentFlag.OFF.getValue());
                        continue;
                    case 8:
                        memberConfirmModel.setMedicalTreatment8(HTypeMedicalTreatmentFlag.OFF.getValue());
                        continue;
                    case 9:
                        memberConfirmModel.setMedicalTreatment9(HTypeMedicalTreatmentFlag.OFF.getValue());
                        continue;
                    case 10:
                        memberConfirmModel.setMedicalTreatment10(HTypeMedicalTreatmentFlag.OFF.getValue());
                        continue;
                    default:
                        break;
                }
            }
        }
    }

    // 2023-renew No22 from here

    /**
     * アップロードファイルを取得する
     *
     * @param memberConfirmModel: メンバー 機種確認
     * @param pathTypeGetImage: ファイルへのアクセスパス
     * @param memberInfoSeq: メンバーID
     *
     */
    private void getUploadFiles(MemberConfirmModel memberConfirmModel,
                                String pathType,
                                String pathTypeGetImage,
                                String memberInfoSeq) {
        // アップロードされたファイルをデータベースから取得する
        MemberInfoImageListResponse memberInfoImageListResponse =
                        memberInfoApi.getListMemberImage(Integer.valueOf(memberInfoSeq));
        List<MemberInfoImageEntityResponse> memberInfoImageEntityResponseList =
                        memberInfoImageListResponse.getMemberInfoImageEntityResponse();

        if (CollectionUtil.isNotEmpty(memberInfoImageEntityResponseList)) {
            // dtoをモデルに変換する
            List<RegistUploadFile> uploadFiles =
                            memberConfirmHelper.toRegistUploadFile(memberInfoImageEntityResponseList, pathTypeGetImage);

            memberConfirmModel.setUploadFiles(uploadFiles);
        }
    }

    /**
     * ファイルの更新日を取得する
     *
     * @param filePath: ファイルパス
     * @return: ファイルの日付を長い形式で更新します
     */
    private Long getUpdateDateOfFile(String filePath) {
        try {
            Path path = FileSystems.getDefault().getPath(filePath);
            BasicFileAttributes attributes = Files.readAttributes(path, BasicFileAttributes.class);

            // Get the last modified date
            long lastModifiedTime = attributes.lastModifiedTime().toMillis();

            return lastModifiedTime;
        } catch (IOException e) {
            LOGGER.error("例外処理が発生しました", e);
            return null;
        }
    }
    // 2023-renew No22 to here
}
// PDR Migrate Customization to here
