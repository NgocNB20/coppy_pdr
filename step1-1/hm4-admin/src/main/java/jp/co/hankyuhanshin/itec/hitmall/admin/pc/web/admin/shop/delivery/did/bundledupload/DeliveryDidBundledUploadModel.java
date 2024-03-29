/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */
package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.shop.delivery.did.bundledupload;

import jp.co.hankyuhanshin.itec.hitmall.admin.web.AbstractModel;
import jp.co.hankyuhanshin.itec.hitmall.annotation.validator.HVCsvHeader;
import jp.co.hankyuhanshin.itec.hitmall.annotation.validator.HVMultipartFile;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeDeliveryMethodType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOpenDeleteStatus;
import jp.co.hankyuhanshin.itec.hitmall.dto.shop.delivery.DeliveryImpossibleDayCsvDto;
import jp.co.hankyuhanshin.itec.hmbase.util.common.CollectionUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 一括アップロード<br/>
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class DeliveryDidBundledUploadModel extends AbstractModel {

    /**
     * ファイル削除失敗メッセージ
     */
    public static final String MSGCD_FAIL_DELETE = "AYH000107";

    /**
     * アップロードファイル
     */
    @HVMultipartFile(fileExtension = {"csv"})
    @HVCsvHeader(csvDtoClass = DeliveryImpossibleDayCsvDto.class)
    private MultipartFile uploadFile;

    /**
     * 年(引き継ぎ用）
     */
    private Integer redirectYear;

    /**
     * 年
     */
    @NotNull(message = "{HRequiredValidator.REQUIRED_detail}")
    private Integer year;

    /**
     * 配送方法SEQ(引き継ぎ用）
     */
    private Integer redirectDmcd;

    /**
     * 配送方法SEQ
     */
    @NotNull(message = "{HRequiredValidator.REQUIRED_detail}")
    private Integer dmcd;

    /**
     * 配送方法名
     */
    private String deliveryMethodName;

    /**
     * 公開状態PC
     */
    private HTypeOpenDeleteStatus openStatusPC = HTypeOpenDeleteStatus.NO_OPEN;

    /**
     * 配送方法種別
     */
    private HTypeDeliveryMethodType deliveryMethodType;

    /**
     * エラーリスト
     */
    private List<DeliveryDidBundledUploadFinishModelItem> resultItems;

    /**
     * アップロード件数
     */
    private int uploadCount;

    /**
     * バリデーション限度数
     */
    private Integer validLimit;

    /**
     * バリデーション限度数で終了したか否かのフラグ
     */
    private boolean validLimitFlg;

    /**
     * エラーメッセージ判定
     *
     * @return true=エラー、false=エラーなし
     */
    public boolean isErrMsgTable() {
        return CollectionUtil.isNotEmpty(resultItems);
    }
}
