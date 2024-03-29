package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.shop.delivery.regisupdate;

import jp.co.hankyuhanshin.itec.hitmall.annotation.converter.HCNumber;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypePrefectureType;
import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Data
@Component
@Scope("prototype")
public class DeliveryPrefectureCarriageItem implements Serializable {

    /**
     * シリアルバージョンUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * 有効フラグ
     */
    private boolean activeFlag;

    /**
     * 都道府県
     */
    private String prefectureName;

    /**
     * 都道府県種別
     */
    private HTypePrefectureType prefectureType;

    /**
     * 送料
     */
    @HCNumber
    private String prefectureCarriage;
}
