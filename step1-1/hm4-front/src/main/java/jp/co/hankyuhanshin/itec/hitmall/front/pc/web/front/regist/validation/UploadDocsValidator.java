package jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.regist.validation;

import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.member.confirm.update.MemberConfirmUpdateModel;
import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.regist.RegistModel;
import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.regist.RegistUploadFile;
import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.regist.validation.group.UploadDocsGroup;
import jp.co.hankyuhanshin.itec.hitmall.front.validator.HSpecialCharacterValidator;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.SmartValidator;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Validation;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * バリデータアップロードファイル
 *
 * @author Doan Thang (VTI Japan Co., Ltd.)
 */
@Data
@Component
public class UploadDocsValidator implements SmartValidator {

    /**
     * アップロードファイルの登録
     */
    private final static String FILED_NAME_UPLOAD_FILES = "uploadFiles";

    /**
     * 無効な番号のファイルのアップロードに関するメッセージ エラー
     **/
    public static final String INVALID_LIMIT_MESSAGE_ID = "PDR-2023RENEW-22-002-E";

    /**
     * アップロードファイルの最大サイズ
     **/
    private final static long MAX_UPLOAD_FILE_SIZE = 5 * 1024 * 1024;

    /**
     * アップロードした画像が5MBより大きい場合エラー
     **/
    public static final String INVALID_SIZE_MESSAGE_ID = "PDR-2023RENEW-22-001-E";

    /**
     * 無効な拡張子ファイルのアップロードに関するメッセージ エラー
     **/
    public static final String INVALID_EXTENSION_MESSAGE_ID = "PDR-2023RENEW-22-003-E";

    /**
     *  エラー
     */
    private final static String INVALID_FILE_NAME_LENGTH_MESSAGE_ID = "PDR-2023RENEW-22-004-E";

    /**
     *  ファイル名に使用不可文字が含まれている場合エラー
     */
    private final static String INVALID_SPECIAL_CHARACTER_MESSAGE_ID = "PDR-2023RENEW-22-005-E";

    /**
     * 拡張子リスト
     **/
    private final List<String> EXTENSIONS = List.of("JPEG", "JPG", "PNG", "PDF");

    /**
     * ファイル名に文字を許可する
     **/
    private final char[] allowChars = {'_', '(', ')', '-'};

    // 2023-renew AddNo2 from here
    /**
     * 一時ファイルを保存するディレクトリのパス
     */
    private static final String GET_TMP_PATH = "/tmp-docs/";
    // 2023-renew AddNo2 from here

    @Override
    public void validate(Object target, Errors errors, Object... validationHints) {

        if (Objects.equals(validationHints, null)) {
            // 対象groupがない場合、チェックしない
            return;
        }

        if (!UploadDocsGroup.class.equals(validationHints[0])) {
            // バリデータ対象のgroupが、UploadDocsGroup以外の場合、チェックしない
            return;
        }

        try {

            HSpecialCharacterValidator validator = Validation.buildDefaultValidatorFactory()
                                                             .getConstraintValidatorFactory()
                                                             .getInstance(HSpecialCharacterValidator.class);
            validator.setAllowCharacters(allowChars);

            // 2023-renew AddNo2 from here
            MultipartFile[] uploadFiles = null;
            List<RegistUploadFile> registUploadFiles = null;
            if (target instanceof RegistModel) {
                RegistModel registModel = (RegistModel) target;
                uploadFiles = registModel.getUploadFiles();
                registUploadFiles = registModel.getRegistUploadFiles();
            } else if (target instanceof MemberConfirmUpdateModel) {
                MemberConfirmUpdateModel memberConfirmUpdateModel = (MemberConfirmUpdateModel) target;
                uploadFiles = memberConfirmUpdateModel.getUploadFiles();
                registUploadFiles = memberConfirmUpdateModel.getUploadFileModelList();
            }
            // 2023-renew AddNo2 to here

            if (Objects.equals(uploadFiles, null)) {
                return;
            }

            // 2023-renew AddNo2 from here
            if (Objects.equals(registUploadFiles, null))
                registUploadFiles = new ArrayList<>();
            // アップロードした画像が4枚以上の場合エラー
            if (target instanceof MemberConfirmUpdateModel) {
                if (uploadFiles.length > 3) {
                    errors.rejectValue(FILED_NAME_UPLOAD_FILES, INVALID_LIMIT_MESSAGE_ID);
                    return;
                }
                if (registUploadFiles.size() + uploadFiles.length > 6) {
                    errors.rejectValue(FILED_NAME_UPLOAD_FILES, INVALID_LIMIT_MESSAGE_ID);
                    return;
                }
                if (registUploadFiles.stream().filter(RegistUploadFile::isSavedImage).count() + uploadFiles.length
                    > 3) {
                    errors.rejectValue(FILED_NAME_UPLOAD_FILES, INVALID_LIMIT_MESSAGE_ID);
                    return;
                }
            } else {
                if (registUploadFiles.size() + uploadFiles.length > 3) {
                    errors.rejectValue(FILED_NAME_UPLOAD_FILES, INVALID_LIMIT_MESSAGE_ID);
                    return;
                }
            }
            // 2023-renew AddNo2 to here

            for (MultipartFile file : uploadFiles) {
                // アップロードした画像が5MBより大きい場合エラー
                if (file.getSize() > MAX_UPLOAD_FILE_SIZE) {
                    errors.rejectValue(FILED_NAME_UPLOAD_FILES, INVALID_SIZE_MESSAGE_ID);
                }

                String fileName = file.getOriginalFilename();
                if (StringUtils.isEmpty(fileName)) {
                    return;
                }
                String ext = getFileExtension(file.getOriginalFilename());
                // アップロードした画像がPNG・JPG・PDF以外の場合エラー
                if (ext == null || !EXTENSIONS.contains(ext)) {
                    errors.rejectValue(FILED_NAME_UPLOAD_FILES, INVALID_EXTENSION_MESSAGE_ID);
                    return;
                }

                // ファイル名（拡張子含む）が101文字以上の場合エラー
                // ファイル名（拡張子含む）が101文字以上の場合エラー
                int length = 0;
                if (ext != null && ext.toUpperCase().equals(EXTENSIONS.get(0))) {
                    length = 82;
                } else {
                    length = 83;
                }
                if (fileName.length() > length) {
                    errors.rejectValue(FILED_NAME_UPLOAD_FILES, INVALID_FILE_NAME_LENGTH_MESSAGE_ID);
                }

                // ファイル名に使用不可文字が含まれている場合エラー
                if (!validator.isValid(fileName.substring(0, fileName.lastIndexOf('.')), null)) {
                    errors.rejectValue(FILED_NAME_UPLOAD_FILES, INVALID_SPECIAL_CHARACTER_MESSAGE_ID);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean supports(Class<?> clazz) {
        // 2023-renew AddNo2 from here
        return RegistModel.class.isAssignableFrom(clazz) || MemberConfirmUpdateModel.class.isAssignableFrom(clazz);
        // 2023-renew AddNo2 to here
    }

    @Override
    public void validate(Object target, Errors errors) {
        // 未使用
    }

    /**
     * ファイル拡張子を取得するメソッド
     * @param fileName: ファイル名
     * @return ファイル名拡張子
     * */
    private String getFileExtension(String fileName) {
        // 最後に出現したドットを検索します (.)
        int lastDotIndex = fileName.lastIndexOf('.');

        // ドットが見つかり、それが文字列の最後の文字ではないかどうかを確認します。
        if (lastDotIndex > 0 && lastDotIndex < fileName.length() - 1) {
            return fileName.substring(lastDotIndex + 1).toUpperCase();
        } else {
            // ファイル拡張子が見つかりません
            return null;
        }
    }
}

// 2023-renew No22 to here
