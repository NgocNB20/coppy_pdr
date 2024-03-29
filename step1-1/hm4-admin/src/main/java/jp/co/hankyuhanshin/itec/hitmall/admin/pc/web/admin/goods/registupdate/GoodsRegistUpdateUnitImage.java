package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.goods.registupdate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeUnitImageFlag;
import lombok.Data;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Data
@Component
@Scope("prototype")
public class GoodsRegistUpdateUnitImage {
    /**
     * シリアルバージョンID
     */
    private static final long serialVersionUID = 1L;

    /**
     * No
     */
    private Integer imageDspNo;

    /**
     * 商品コード
     */
    private String goodsCode;

    /**
     * 規格画像パス
     */
    private String urlImagePath;

    /**
     * 規格画像パス
     */
    private String realImagePath;

    /**
     * 規格画像名
     */
    private String unitImageName;

    /**
     * 規格画像
     */
    @JsonIgnore
    private MultipartFile unitImage;

    // 2023-renew No76 from here
    /**
     * 規格画像有無
     */
    private HTypeUnitImageFlag unitImageFlag;
    // 2023-renew No76 to here

    /**
     * 商品画像有無判定<br/>
     *
     * @return true=画像パスがある場合
     */
    public boolean isExistImage() {
        return !StringUtils.isEmpty(urlImagePath);
    }

    // 2023-renew No76 from here

    /**
     * 規格画像有無判定
     *
     * @return true:規格画像あり false:規格画像なし
     */
    public boolean isValidUnitImage() {
        return HTypeUnitImageFlag.ON.equals(this.unitImageFlag);
    }

    // 2023-renew No76 to here
}
