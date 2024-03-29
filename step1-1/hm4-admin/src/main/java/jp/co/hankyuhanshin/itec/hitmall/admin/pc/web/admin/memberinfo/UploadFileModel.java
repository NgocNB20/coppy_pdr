// 2023-renew No22 from here
package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.memberinfo;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeUploadExtension;
import lombok.Data;

/**
 * アップロードするファイルモデル
 *
 * @author Doan Thang (VTI Japan Co., Ltd.)
 */
@Data
public class UploadFileModel {
    /**
     * 序数
     */
    private Integer fileNo;

    /**
     * ファイルパス<br/>
     */
    private String filePath;

    /**
     * ファイル名<br/>
     */
    private String fileName;

    /**
     * 元のファイル名<br/>
     */
    private String originName;

    /**
     * 更新日<br/>
     */
    private Long updateDate;

    /**
     * 削除されました
     */
    private Boolean isDeleted = Boolean.FALSE;

    /**
     * ファイルの種類
     * 0: JPG
     * 1: PNG
     * 2: PDF
     */
    private HTypeUploadExtension extensionType = HTypeUploadExtension.JPEG;

}
// 2023-renew No22 to here
