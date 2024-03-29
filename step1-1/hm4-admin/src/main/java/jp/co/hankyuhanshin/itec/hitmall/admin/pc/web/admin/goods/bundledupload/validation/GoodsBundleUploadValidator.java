package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.goods.bundledupload.validation;

import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.common.validation.group.UploadGroup;
import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.goods.bundledupload.GoodsBundledUploadModel;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeUploadType;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goods.GoodsCsvDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goods.GoodsEnterpriseCsvDto;
import jp.co.hankyuhanshin.itec.hitmall.validator.HCsvHeaderValidator;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.SmartValidator;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Validation;
import java.util.Objects;

/**
 * 商品一括アップロード画面の動的バリデータ
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */

@Component
public class GoodsBundleUploadValidator implements SmartValidator {

    /**
     * 金額別送料アイテムリスト
     */
    public static final String FILED_NAME_UPLOAD_CSV_FILE = "uploadCsvFile";

    /**
     * CSVヘッダバリデーションNG時メッセージ
     */
    public static final String CSV_HEADER_VALIDATOR_MESSAGE_ID = "HCsvHeaderValidator.INVALID_detail";

    /**
     * @param target
     * @param errors
     * @param validationHints
     */
    @Override
    public void validate(Object target, Errors errors, Object... validationHints) {
        if (Objects.equals(validationHints, null)) {
            // 対象groupがない場合、チェックしない
            return;
        }

        if (!UploadGroup.class.equals(validationHints[0])) {
            // バリデータ対象のgroupが、UploadGroup、チェックしない
            return;
        }

        GoodsBundledUploadModel model = (GoodsBundledUploadModel) target;

        if (HTypeUploadType.REGIST.getValue().equals(model.getUploadType())) {
            // PDR Migrate Customization from here
            // 新規登録時はファイルクラスを基幹連携用CSVDtoクラスへ
            validate(model.getUploadCsvFile(), errors, GoodsEnterpriseCsvDto.class);
            // PDR Migrate Customization to here
        } else {
            validate(model.getUploadCsvFile(), errors, GoodsCsvDto.class);
        }

    }

    /**
     * @param value
     * @param errors
     * @param csvDtoClass
     * @return
     */
    private void validate(MultipartFile value, Errors errors, Class<?> csvDtoClass) {
        // 数値チェックバリデータ
        HCsvHeaderValidator validator = Validation.buildDefaultValidatorFactory()
                                                  .getConstraintValidatorFactory()
                                                  .getInstance(HCsvHeaderValidator.class);

        validator.setRestrict(false);
        validator.setCsvDtoClass(csvDtoClass);
        validator.setCsvCharset("MS932");

        boolean result = validator.isValid(value, null);
        if (!result) {
            errors.rejectValue(FILED_NAME_UPLOAD_CSV_FILE, CSV_HEADER_VALIDATOR_MESSAGE_ID, new String[] {}, null);
        }

    }

    /**
     * @param clazz
     * @return
     */
    @Override
    public boolean supports(Class<?> clazz) {
        return GoodsBundledUploadModel.class.isAssignableFrom(clazz);
    }

    /**
     * 未使用
     */
    @Override
    public void validate(Object target, Errors errors) {
        // 未使用
    }
}
