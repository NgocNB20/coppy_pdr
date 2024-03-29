package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.goods.registupdate.validation;

import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.goods.registupdate.GoodsRegistUpdateModel;
import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.goods.registupdate.validation.group.UploadUnitImageGroup;
import jp.co.hankyuhanshin.itec.hitmall.validator.HImageJpegFileValidator;
import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.SmartValidator;

import javax.validation.Validation;
import java.util.Objects;

@Data
@Component
public class UnitImageValidator implements SmartValidator {

    /**
     * jpegファイルじゃなかった場合
     */
    public static final String NOT_JPEG_MESSAGE_ID = "{HImageJpegFileValidator.INVALID_detail}";

    /**
     * ファイルが存在しなかった場合のエラーメッセージ
     */
    public static final String NOT_FOUND_MESSAGE_ID = "{HImageJpegFileValidator.NOTFOUND_detail}";

    /**
     * 規格画像項目名
     */
    public static final String FILED_NAME_ITEMS = "unitImagesItems[";
    public static final String FILED_NAME_UNIT_IMAGE = "].unitImage";

    @Override
    public boolean supports(Class<?> clazz) {
        return GoodsRegistUpdateModel.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors, Object... validationHints) {
        if (Objects.equals(validationHints, null)) {
            // 対象groupがない場合、チェックしない
            return;
        }

        if (!UploadUnitImageGroup.class.equals(validationHints[0])) {
            // バリデータ対象のgroupが、UploadUnitImageGroup以外の場合、チェックしない
            return;
        }

        GoodsRegistUpdateModel model = (GoodsRegistUpdateModel) target;

        // 更新／登録日バリデータ
        if (model.getUnitImagesItemNo() != null) {
            // パターンはカンマで分割
            HImageJpegFileValidator validator = Validation.buildDefaultValidatorFactory()
                                                          .getConstraintValidatorFactory()
                                                          .getInstance(HImageJpegFileValidator.class);

            if (model.getUnitImagesItems().get(model.getUnitImagesItemNo()).getUnitImage().getSize() == 0) {
                errors.rejectValue(FILED_NAME_ITEMS + model.getUnitImagesItemNo() + FILED_NAME_UNIT_IMAGE,
                                   NOT_FOUND_MESSAGE_ID, "指定されたファイルが見つかりません。再度ファイルを指定し、アップロードしてください。"
                                  );
            } else {
                boolean result = validator.isValid(
                                model.getUnitImagesItems().get(model.getUnitImagesItemNo()).getUnitImage(), null);
                if (!result) {
                    errors.rejectValue(FILED_NAME_ITEMS + model.getUnitImagesItemNo() + FILED_NAME_UNIT_IMAGE,
                                       NOT_JPEG_MESSAGE_ID, "jpeg ファイルを指定してください。"
                                      );
                }
            }
        }
    }

    /**
     * 未使用
     */
    @Override
    public void validate(Object target, Errors errors) {
        // 未使用
    }
}
