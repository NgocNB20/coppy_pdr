// 2023-renew No42 from here
package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.shop.digitalcatalog;

import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.common.validation.group.ZipUploadGroup;
import jp.co.hankyuhanshin.itec.hitmall.admin.web.AbstractModel;
import jp.co.hankyuhanshin.itec.hitmall.annotation.validator.HVMultipartFile;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.web.multipart.MultipartFile;

/**
 * デジタルカタログアップロードモデル<br/>
 *
 * @author Doan Thang (VTI Japan Co., Ltd.)
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class DigitalCatalogUploadModel extends AbstractModel {

    /**
     * Zipアップロードファイル
     */
    @HVMultipartFile(fileExtension = {"zip"}, groups = {ZipUploadGroup.class})
    private MultipartFile zipDigitalCatalogFile;

    /**
     * アップロードしたファイル数
     */
    private int uploadFileCount;

}
// 2023-renew No42 to here