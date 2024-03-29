/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.dto.order;

import jp.co.hankyuhanshin.itec.hitmall.dto.common.CheckMessageDto;
import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 注文メッセージDtoクラス
 *
 * @author DtoGenerator
 * @version $Revision: 1.5 $
 */
@Data
@Component
@Scope("prototype")
public class OrderMessageDto implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * 受注商品詳細メッセージマップ：Key=注文連番, 子マップKey=商品SEQ
     */
    private Map<Integer, Map<Integer, List<CheckMessageDto>>> orderGoodsMessageMapMap;

    /**
     * 受注商品詳細メッセージマップ：マップKey=商品SEQ
     */
    private Map<Integer, List<CheckMessageDto>> orderGoodsMessageMap;

    /**
     * 注文メッセージリスト
     */
    private List<CheckMessageDto> orderMessageList;

    /**
     * @return エラー系のメッセージの有無
     */
    public boolean hasErrorMessage() {

        if (orderMessageList != null) {
            for (CheckMessageDto dto : orderMessageList) {
                if (dto.isError()) {
                    return true;
                }
            }
        }

        return hasGoodsErrorMessage();
    }

    /**
     * @return 商品系のエラーメッセージの有無
     */
    public boolean hasGoodsErrorMessage() {
        if (orderGoodsMessageMapMap != null) {
            Collection<Map<Integer, List<CheckMessageDto>>> values = orderGoodsMessageMapMap.values();
            for (Map<Integer, List<CheckMessageDto>> consecutivelist : values) {
                for (List<CheckMessageDto> dtoList : consecutivelist.values()) {
                    for (CheckMessageDto dto : dtoList) {
                        if (dto.isError()) {
                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }

    // PDR Migrate Customization from here
    //    /**
    //     * PDR#013 09_データ連携（受注データ）<br/>
    //     *
    //     * <pre>
    //     * 注文メッセージDtoクラス
    //     *
    //     * Map追加メソッドを追加
    //     * </pre>
    //     */

    /**
     * 受注商品詳細メッセージマップ：Key=注文連番, 子マップKey=商品SEQを追加します。
     *
     * @param orderGoodsMessageMapMap 受注商品詳細メッセージマップ：Key=注文連番, 子マップKey=商品SEQ
     */
    public void addOrderGoodsMessageMapMap(Map<Integer, Map<Integer, List<CheckMessageDto>>> orderGoodsMessageMapMap) {

        if (this.getOrderGoodsMessageMapMap() == null) {
            this.setOrderGoodsMessageMapMap(orderGoodsMessageMapMap);
            return;
        }

        Map<Integer, Map<Integer, List<CheckMessageDto>>> map = getOrderGoodsMessageMapMap();
        map.putAll(orderGoodsMessageMapMap);
        setOrderGoodsMessageMapMap(map);
    }

    /**
     * 注文メッセージリストを追加します。
     *
     * @param checkMessageDtos 注文メッセージリスト
     */
    public void addOrderMessageList(List<CheckMessageDto> checkMessageDtos) {

        if (this.getOrderMessageList() == null) {
            this.setOrderMessageList(checkMessageDtos);
            return;
        }

        List<CheckMessageDto> list = getOrderMessageList();
        list.addAll(checkMessageDtos);
        setOrderMessageList(list);
    }
    // PDR Migrate Customization to here
}
