// 2023-renew AddNo2 from here
package jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.member.confirm.update;

import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.MemberInfoApi;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.MemberInfoConfirmScreenUpdateRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.MemberInfoEntityResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.MemberInfoImageEntityResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.MemberInfoImageListResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.MemberInfoImageRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.shop.ZipcodeApi;
import jp.co.hankyuhanshin.itec.hitmall.api.shop.param.ResultFlagResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.shop.param.ZipCodeMatchingCheckRequest;
import jp.co.hankyuhanshin.itec.hitmall.front.annotation.exception.HEHandler;
import jp.co.hankyuhanshin.itec.hitmall.front.base.exception.AppLevelListException;
import jp.co.hankyuhanshin.itec.hitmall.front.base.util.common.CollectionUtil;
import jp.co.hankyuhanshin.itec.hitmall.front.base.util.common.PropertiesUtil;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeMemberImage;
import jp.co.hankyuhanshin.itec.hitmall.front.entity.memberinfo.MemberInfoEntity;
import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.common.validation.ConfirmGroup;
import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.member.MemberHelper;
import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.member.confirm.MemberConfirmHelper;
import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.regist.RegistUploadFile;
import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.regist.validation.UploadDocsValidator;
import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.regist.validation.group.UploadDocsGroup;
import jp.co.hankyuhanshin.itec.hitmall.front.web.AbstractController;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 会員情報変更 Controller
 */
@RequestMapping("/member/confirm")
@Controller
@SessionAttributes(value = {"memberConfirmUpdateModel"})
public class MemberConfirmUpdateController extends AbstractController {

    /**
     * ログ
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(MemberConfirmUpdateController.class);

    /**
     * 会員Api
     */
    private final MemberInfoApi memberInfoApi;

    /**
     * 郵便番号API
     */
    private final ZipcodeApi zipcodeApi;

    /**
     * 会員情報確認画面Dxo
     */
    private final MemberConfirmHelper memberConfirmHelper;

    /**
     * 一時ファイルを保存するディレクトリのパス
     */
    private static final String GET_TMP_PATH = "/tmp-docs/";

    /**
     * ファイルを保存するディレクトリのパス
     */
    private static final String GET_REAL_PATH = "/confirm-docs/";

    /**
     * 一時ファイルをアップロードするフォルダーのパス
     */
    // 2023-renew No22 from here
    private static final String TMP_PATH = "real.tmp.path.conf.document";

    /**
     * 実パスストレージのアップロードファイル
     */
    private static final String REAL_PATH = "real.path.conf.document";
    /**
     * メンバーヘルパー
     */
    private final MemberHelper memberHelper;
    // 2023-renew No22 to here

    /**
     * バリデーター アップロードファイルを検証する
     */
    private final UploadDocsValidator uploadDocsValidator;

    private static final String MEMBER_INFO_UPDATE_INDEX_PAGE = "member/confirm/update";

    /**
     * 都道府県と郵便番号の整合性エラー:AMR000406
     * 定数にWを付与
     */
    protected static final String MSGCD_PREFECTURE_CONSISTENCY = "AMR000406W";

    /**
     * 会員情報失敗エラー<br/>
     * <code>MSGCD_MEMBERINFOENTITYDTO_NULL</code>
     */
    private static final String MSGCD_MEMBERINFOENTITYDTO_NULL = "SMM000201W";

    /**
     * 入力した会員情報が変更前の会員情報と同じです。入力内容をご確認ください。
     * <code>PDR-2023RENEW-2-007-W</code>
     */
    private static final String MSGCD_MEMBER_INFO_NOT_UPDATE = "PDR-2023RENEW-2-007-W";

    /**
     * ファイルが指定されていません
     * <code>PDR-2023RENEW-22-006-E</code>
     */
    private static final String MSGCD_FILE_IS_NOT_SPECIFIED = "PDR-2023RENEW-22-006-E";

    /**
     * 一時ファイルを保存するディレクトリのパス
     */
    private static final String TMP_URI_CONFIRM_DOCUMENT = "tmp.uri.conf.document";

    /**
     * コンストラクタ
     *
     * @param memberInfoApi          郵便番号API
     * @param zipcodeApi            郵便番号API
     * @param memberConfirmHelper   アドレス帳 Helperクラス
     * @param uploadDocsValidator   バリデーター アップロードファイルクラス
     */
    @Autowired
    public MemberConfirmUpdateController(MemberInfoApi memberInfoApi,
                                         ZipcodeApi zipcodeApi,
                                         MemberConfirmHelper memberConfirmHelper,
                                         // 2023-renew No22 from here
                                         MemberHelper memberHelper,
                                         // 2023-renew No22 to here
                                         UploadDocsValidator uploadDocsValidator) {
        this.memberInfoApi = memberInfoApi;
        this.zipcodeApi = zipcodeApi;
        this.memberConfirmHelper = memberConfirmHelper;
        this.uploadDocsValidator = uploadDocsValidator;
        // 2023-renew No22 from here
        this.memberHelper = memberHelper;
        // 2023-renew No22 to here
    }

    /**
     * 標準ファイルの動的バリデーター
     *
     * @param error
     */
    @InitBinder(value = "memberConfirmUpdateModel")
    public void initBinder(WebDataBinder error) {
        // 規格画像の動的バリデータをセット
        error.addValidators(uploadDocsValidator);
    }

    /**
     * 入力画面：初期処理
     *
     * @param memberConfirmUpdateModel  会員情報変更モデル
     * @param model                     the model
     * @return string
     */
    @GetMapping(value = "/update.html")
    @HEHandler(exception = AppLevelListException.class, returnView = MEMBER_INFO_UPDATE_INDEX_PAGE)
    public String doLoadIndex(MemberConfirmUpdateModel memberConfirmUpdateModel,
                              RedirectAttributes redirectAttributes,
                              Model model,
                              HttpSession httpSession) {
        // 会員情報の取得 会員メニュー中は会員状態を見ないで取得
        Integer memberInfoSeq = getCommonInfo().getCommonInfoUser().getMemberInfoSeq();

        if (memberConfirmUpdateModel.isReloadFlag()) {
            memberConfirmUpdateModel.setReloadFlag(false);
            return MEMBER_INFO_UPDATE_INDEX_PAGE;
        }

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
        MemberInfoEntity memberInfoEntity = memberConfirmHelper.toMemberInfoEntity(response);

        // 画面に反映
        memberConfirmHelper.toPageForLoad(memberInfoEntity, memberConfirmUpdateModel, true);

        getUploadFiles(memberConfirmUpdateModel, GET_REAL_PATH, memberInfoSeq.toString());

        return MEMBER_INFO_UPDATE_INDEX_PAGE;
    }

    /**
     * 処理完了後の変更確認画面に遷移
     *
     * @param memberConfirmUpdateModel  会員情報変更モデル
     * @param error                     BindingResult
     * @param model                     Model
     */
    @PostMapping(value = "/update.html")
    @HEHandler(exception = AppLevelListException.class, returnView = MEMBER_INFO_UPDATE_INDEX_PAGE)
    public String doUpdate(@Validated({ConfirmGroup.class}) MemberConfirmUpdateModel memberConfirmUpdateModel,
                           BindingResult error,
                           RedirectAttributes redirectAttributes,
                           HttpSession session,
                           Model model) {
        if (error.hasErrors()) {
            return MEMBER_INFO_UPDATE_INDEX_PAGE;
        }

        // 都道府県と郵便番号が不一致の場合
        if (!checkPrefectureAndZipCode(memberConfirmUpdateModel)) {
            throwMessage(MSGCD_PREFECTURE_CONSISTENCY);
        }

        // 画面に反映
        memberConfirmHelper.toPageForLoad(
                        memberConfirmUpdateModel.getMemberInfoDetail(), memberConfirmUpdateModel, false);
        if (checkNotChangeInfo(memberConfirmUpdateModel)) {
            throwMessage(MSGCD_MEMBER_INFO_NOT_UPDATE);
        }

        try {
            MemberInfoConfirmScreenUpdateRequest request =
                            memberConfirmHelper.toMemberInfoUpdateRequest(memberConfirmUpdateModel);
            memberInfoApi.updateConfirmScreen(request);
            if (!checkNotChangeImages(memberConfirmUpdateModel)) {
                Integer memberInfoSeq = memberConfirmUpdateModel.getMemberInfoDetail().getMemberInfoSeq();
                this.copyTmpToRealPath(memberInfoSeq, memberConfirmUpdateModel.getUploadFileModelList()
                                                                              .stream()
                                                                              .filter(RegistUploadFile::isSavedImage)
                                                                              .collect(Collectors.toList()),
                                       memberConfirmUpdateModel.getLastImageImageNo(), session
                                      );
            }
        } catch (HttpClientErrorException e) {
            LOGGER.error("例外処理が発生しました", e);
            // AppLevelListExceptionへ変換
            addAppLevelListException(e);
            throwMessage();
        }

        memberConfirmUpdateModel.setLastImageImageNo(0);
        return "redirect:/member/confirm/complete.html";
    }

    @GetMapping(value = "/complete.html")
    public String doLoadConfirm(@Validated({ConfirmGroup.class}) MemberConfirmUpdateModel memberConfirmUpdateModel,
                                BindingResult error,
                                RedirectAttributes redirectAttributes,
                                Model model,
                                SessionStatus sessionStatus) {
        if (error.hasErrors()) {
            return "redirect:/error.html";
        }
        // Modelをセッションより破棄
        sessionStatus.setComplete();

        return "member/confirm/complete";
    }

    /**
     * 確認書類をアップロードする
     *
     * @param memberConfirmUpdateModel  会員情報変更モデル
     * @param error                     BindingResult
     * @param model                     Model
     * @return
     */
    @PostMapping(value = "/update.html", params = "doUploadFile")
    public String doUploadFile(@Validated(UploadDocsGroup.class) MemberConfirmUpdateModel memberConfirmUpdateModel,
                               BindingResult error,
                               Model model,
                               HttpSession session) {
        if (error.hasErrors()) {
            memberConfirmUpdateModel.setReloadFlag(true);
            return MEMBER_INFO_UPDATE_INDEX_PAGE;
        }

        MultipartFile[] uploadFiles = memberConfirmUpdateModel.getUploadFiles();
        if (CollectionUtil.isEmpty(memberConfirmUpdateModel.getUploadFileModelList())) {
            memberConfirmUpdateModel.setUploadFileModelList(new ArrayList<>());
        }

        String key = session.getId();
        String filePath = this.createFolder(key, TMP_PATH, false);
        this.doUploadFile(memberConfirmUpdateModel, filePath, uploadFiles);

        memberConfirmUpdateModel.setReloadFlag(true);
        return "redirect:/member/confirm/update.html";
    }

    /**
     * ファイルを削除する
     *
     * @param memberConfirmUpdateModel  会員情報変更モデル
     * @param error                     BindingResult
     * @param model                     Model
     * @return
     */
    @PostMapping(value = "/update.html", params = "doDeleteFile")
    public String doDeleteFile(MemberConfirmUpdateModel memberConfirmUpdateModel, BindingResult error, Model model) {
        if (CollectionUtil.isNotEmpty(memberConfirmUpdateModel.getUploadFileModelList()) && Objects.nonNull(
                        memberConfirmUpdateModel.getFileNo())) {
            memberConfirmUpdateModel.getUploadFileModelList()
                                    .removeIf(file -> file.getFileNo().equals(memberConfirmUpdateModel.getFileNo()));
        }
        memberConfirmUpdateModel.setErrorUpload(false);
        memberConfirmUpdateModel.setReloadFlag(true);
        return "redirect:/member/confirm/update.html";
    }

    /**
     * アップロードファイルを取得する
     *
     * @param memberConfirmModel: メンバー 機種確認
     * @param pathTypeGetImage: ファイルへのアクセスパス
     * @param memberInfoSeq: メンバーID
     *
     */
    private void getUploadFiles(MemberConfirmUpdateModel memberConfirmModel,
                                String pathTypeGetImage,
                                String memberInfoSeq) {

        // アップロードされたファイルをデータベースから取得する
        MemberInfoImageListResponse memberInfoImageListResponse =
                        memberInfoApi.getListMemberImage(Integer.valueOf(memberInfoSeq));
        List<MemberInfoImageEntityResponse> memberInfoImageEntityResponseList =
                        memberInfoImageListResponse.getMemberInfoImageEntityResponse();

        // dtoをモデルに変換する
        if (CollectionUtil.isNotEmpty(memberInfoImageEntityResponseList)) {
            Collections.sort(memberInfoImageEntityResponseList,
                             Comparator.comparingInt(MemberInfoImageEntityResponse::getMemberImageNo)
                            );
            memberConfirmModel.setLastImageImageNo(
                            memberInfoImageEntityResponseList.get(memberInfoImageEntityResponseList.size() - 1)
                                                             .getMemberImageNo());
        }
        List<RegistUploadFile> uploadFiles =
                        memberConfirmHelper.toRegistUploadFile(memberInfoImageEntityResponseList, pathTypeGetImage);
        memberConfirmModel.setUploadFileModelList(uploadFiles);
    }

    /**
     * 一時フォルダーを作成する
     *
     * @param key      セッション時間
     * @param typePath 一時ファイルをアップロードするフォルダーのパス
     * @return 一時フォルダ名
     */
    private String createFolder(String key, String typePath, boolean withoutCleanDirectory) {
        String filePath = PropertiesUtil.getSystemPropertiesValue(typePath) + File.separator + key;
        Path path = Paths.get(filePath);
        try {
            if (!(Files.exists(path) && Files.isDirectory(path))) {
                Files.createDirectories(path);
            }
            if (typePath.equals(REAL_PATH) && !withoutCleanDirectory) {
                FileUtils.cleanDirectory(new File(filePath));
            }
        } catch (IOException e) {
            LOGGER.error("ファイルのコピーに失敗する", e);
            throw new RuntimeException("一時リポジトリを作成できません");
        }
        return filePath;
    }

    /**
     * 確認書類をアップロードする
     *
     * @param memberConfirmUpdateModel
     * @param filePath
     * @param uploadFiles
     */
    private void doUploadFile(MemberConfirmUpdateModel memberConfirmUpdateModel,
                              String filePath,
                              MultipartFile[] uploadFiles) {
        try {
            if (memberConfirmUpdateModel.getUploadFileModelList() == null)
                memberConfirmUpdateModel.setUploadFileModelList(new ArrayList<>());
            for (MultipartFile uploadFile : uploadFiles) {
                RegistUploadFile registUploadFile = new RegistUploadFile();
                // 2023-renew No22 from here
                String newFileName = memberHelper.renameFile(uploadFile.getOriginalFilename());
                // 2023-renew No22 to here
                registUploadFile.setOriginName(uploadFile.getOriginalFilename());
                registUploadFile.setFilePath(
                                PropertiesUtil.getSystemPropertiesValue(TMP_URI_CONFIRM_DOCUMENT) + "/" + newFileName);
                String target = filePath + File.separator + newFileName;
                Files.copy(uploadFile.getInputStream(), Paths.get(target), StandardCopyOption.REPLACE_EXISTING);
                registUploadFile.setFileName(newFileName);
                if (memberConfirmUpdateModel.getUploadFileModelList().isEmpty()) {
                    registUploadFile.setFileNo(1);
                } else {
                    Optional<RegistUploadFile> maxNoFile = memberConfirmUpdateModel.getUploadFileModelList()
                                                                                   .stream()
                                                                                   .max(Comparator.comparingInt(
                                                                                                   RegistUploadFile::getFileNo));
                    Integer lastIndex = -1;
                    if (maxNoFile.isPresent()) {
                        lastIndex = maxNoFile.get().getFileNo();
                    }
                    registUploadFile.setFileNo(lastIndex + 1);
                }
                // 2023-renew No22 from here
                if (uploadFile.getContentType() != null && uploadFile.getContentType().contains("pdf")) {
                    registUploadFile.setExtensionType(HTypeMemberImage.PDF);
                } else if (uploadFile.getContentType() != null && uploadFile.getContentType().contains("png")) {
                    registUploadFile.setExtensionType(HTypeMemberImage.PNG);
                }
                // 2023-renew No22 to here
                registUploadFile.setNewUploadImage(true);
                memberConfirmUpdateModel.getUploadFileModelList().add(registUploadFile);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 都道府県と郵便番号の不整合チェック
     *
     * @param memberConfirmUpdateModel 会員情報変更モデル
     */
    protected boolean checkPrefectureAndZipCode(MemberConfirmUpdateModel memberConfirmUpdateModel) {

        // nullの場合service未実行(必須チェックは別タスク)
        if (StringUtils.isEmpty(memberConfirmUpdateModel.getMemberInfoZipCode1()) || StringUtils.isEmpty(
                        memberConfirmUpdateModel.getMemberInfoZipCode2()) || StringUtils.isEmpty(
                        memberConfirmUpdateModel.getMemberInfoPrefecture())) {
            return true;
        }
        ZipCodeMatchingCheckRequest zipCodeMatchingCheckRequest = new ZipCodeMatchingCheckRequest();

        zipCodeMatchingCheckRequest.setZipCode(memberConfirmUpdateModel.getMemberInfoZipCode1()
                                               + memberConfirmUpdateModel.getMemberInfoZipCode2());
        zipCodeMatchingCheckRequest.setPrefecture(memberConfirmUpdateModel.getMemberInfoPrefecture());

        ResultFlagResponse response = null;
        try {
            response = zipcodeApi.registCheckZipcodeMatching(zipCodeMatchingCheckRequest);
        } catch (HttpClientErrorException e) {
            LOGGER.error("例外処理が発生しました", e);
            // AppLevelListExceptionへ変換
            addAppLevelListException(e);
            throwMessage();
        }

        // 郵便番号住所情報取得サービス実行
        return response.getResultFlag();
    }

    /**
     * 会員情報を変更チェック
     *
     * @param memberConfirmUpdateModel  会員情報変更モデル
     * @return
     */
    protected boolean checkNotChangeInfo(MemberConfirmUpdateModel memberConfirmUpdateModel) {
        return this.checkNotChangeInfoDetail(memberConfirmUpdateModel) && this.checkNotChangeImages(
                        memberConfirmUpdateModel);
    }

    private boolean checkNotChangeInfoDetail(MemberConfirmUpdateModel memberConfirmUpdateModel) {
        return CollectionUtil.isEmpty(memberConfirmUpdateModel.getModifiedList());
    }

    private boolean checkNotChangeImages(MemberConfirmUpdateModel memberConfirmUpdateModel) {
        return CollectionUtils.isEmpty(memberConfirmUpdateModel.getUploadFileModelList()
                                                               .stream()
                                                               .filter(RegistUploadFile::isSavedImage)
                                                               .collect(Collectors.toList()));
    }

    /**
     * ファイルを移動する
     *
     * @param seq seq
     * @param files ファイル
     * @param lastImageImageNo 最後の画像番号
     * @param session セッション
     */
    private void copyTmpToRealPath(Integer seq,
                                   List<RegistUploadFile> files,
                                   Integer lastImageImageNo,
                                   HttpSession session) {
        if (files == null)
            files = new ArrayList<>();
        String key = session.getId();
        String tmpPathFolder = createFolder(key, TMP_PATH, false);
        String realPathFolder = createFolder(String.valueOf(seq), REAL_PATH, true);
        try {
            int count = lastImageImageNo > 0 ? lastImageImageNo + 1 : 1;
            for (RegistUploadFile file : files) {
                String sourcePath = tmpPathFolder + File.separator + file.getFileName();
                File sourceFile = new File(sourcePath);
                if (!sourceFile.exists()) {
                    LOGGER.error("画像が取得できない場合エラー");
                    throwMessage(MSGCD_FILE_IS_NOT_SPECIFIED);
                }
                String fileNameWithDate = memberHelper.renameFile(file.getOriginName());
                String destinationPath = realPathFolder + File.separator + fileNameWithDate;
                Path source = Paths.get(sourcePath);
                Path destination = Paths.get(destinationPath);
                Files.move(source, destination, StandardCopyOption.REPLACE_EXISTING);
                // データベースに保存
                file.setFileName(fileNameWithDate);
                saveImage(seq, file, count);
                file.setFilePath(GET_REAL_PATH + file.getFileName());
                count++;
            }
            FileUtils.deleteDirectory(new File(tmpPathFolder));
        } catch (IOException e) {
            LOGGER.error("ファイルのコピーに失敗する", e);
        }
    }

    /**
     * ファイルを保存
     *
     * @param memberInfoSeq 会員情報SEQ
     * @param registUploadFile ユーザーアップロードファイル
     * @param no 番号
     */
    private void saveImage(Integer memberInfoSeq, RegistUploadFile registUploadFile, int no) {
        MemberInfoImageRequest request = new MemberInfoImageRequest();
        request.setMemberInfoSeq(memberInfoSeq);
        request.setMemberImageNo(no);
        request.setMemberImageFileName(registUploadFile.getFileName());
        request.setMemberImageType(registUploadFile.getExtensionType().getValue());
        memberInfoApi.createMemberImage(request);
    }
}
// 2023-renew AddNo2 to here
