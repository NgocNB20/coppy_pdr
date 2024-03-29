// 2023-renew No22 from here
package jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.regist;

import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeMemberImage;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * ユーザーアップロードファイルモデル<br/>
 *
 */
@Data
public class RegistUploadFile {
    // 2023-renew AddNo2 from here
    /**
     * 一時ファイルを保存するディレクトリのパス
     */
    private static final String GET_TMP_PATH = "/tmp-docs/";
    // 2023-renew AddNo2 to here

    /**
     * 序数
     */
    private Integer fileNo;

    /**
     * 確認書類パス<br/>
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

    // 2023-renew AddNo2 from here
    /**
     * ...<br/>
     */
    private boolean isNewUploadImage;
    // 2023-renew AddNo2 to here

    /**
     * メンバー画像の種類
     * 0: JPEG
     * 1: PNG
     * 2: PDF
     */
    private HTypeMemberImage extensionType = HTypeMemberImage.JPEG;

    // 2023-renew AddNo2 from here

    /**
     *  ...
     * @return
     */
    public boolean isSavedImage() {
        return StringUtils.isNotBlank(filePath) && filePath.contains(GET_TMP_PATH);
    }

    // 2023-renew AddNo2 to here
}
// 2023-renew No22 to here
