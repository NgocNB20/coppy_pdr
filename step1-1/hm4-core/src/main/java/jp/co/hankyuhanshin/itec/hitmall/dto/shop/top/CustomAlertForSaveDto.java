/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.dto.shop.top;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeAlertType;
import jp.co.hankyuhanshin.itec.hmbase.dto.AbstractConditionDto;
import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * カスタムアラート保存用Dtoクラス<br/>
 *
 * @author s_tsuru
 */
@Data
@Component
@Scope("prototype")
public class CustomAlertForSaveDto implements Serializable {

    /**
     * SerialVersionUID<br />
     */
    private static final long serialVersionUID = 1L;

    /**
     * 検索条件抽象Dto
     */
    public AbstractConditionDto abstractConditionDto;

    /**
     * カスタムアラートタイプ
     */
    public HTypeAlertType alertType;

    /**
     * カスタムアラート名
     */
    public String alertName;
}
