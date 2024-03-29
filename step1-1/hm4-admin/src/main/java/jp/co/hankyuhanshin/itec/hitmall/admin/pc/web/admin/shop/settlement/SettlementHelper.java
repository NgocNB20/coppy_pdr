package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.shop.settlement;

import jp.co.hankyuhanshin.itec.hitmall.entity.shop.settlement.SettlementMethodEntity;
import jp.co.hankyuhanshin.itec.hitmall.service.division.DivisionMapGetService;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * SettlementHelper Class
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Component
public class SettlementHelper {

    /**
     * 検索結果画面反映<br/>
     *
     * @param settlementModel 決済方法設定ページ
     * @param resultList      決済方法リスト
     */
    public void toPage(SettlementModel settlementModel, List<SettlementMethodEntity> resultList) {
        List<SettlementModelItem> resultItems = new ArrayList<>();

        Integer orderDisplay = 1;
        for (SettlementMethodEntity settlementMethod : resultList) {
            SettlementModelItem resultItem = ApplicationContextUtility.getBean(SettlementModelItem.class);
            resultItem.setBillType(settlementMethod.getBillType());
            resultItem.setDeliveryMethodSeq(settlementMethod.getDeliveryMethodSeq());
            resultItem.setOpenStatusMb(settlementMethod.getOpenStatusMB());
            resultItem.setOpenStatusPc(settlementMethod.getOpenStatusPC());
            resultItem.setOrderDisplayValue(orderDisplay);
            resultItem.setSettlementMethodCommissionType(settlementMethod.getSettlementMethodCommissionType());
            resultItem.setSettlementMethodName(settlementMethod.getSettlementMethodName());
            resultItem.setSettlementMethodSeq(settlementMethod.getSettlementMethodSeq());
            resultItem.setSettlementMethodType(settlementMethod.getSettlementMethodType());
            resultItem.setDeliveryMethodName(getDeliveryMethodName(settlementMethod.getDeliveryMethodSeq()));
            resultItems.add(resultItem);
            orderDisplay++;
        }

        settlementModel.setResultItems(resultItems);
    }

    /**
     * 表示順変更<br/>
     *
     * @param settlementModel 決済方法設定ページ
     * @param actiontype      移動アクションタイプ 0=1つ上へ移動、1=1つ下へ移動、2=先頭へ移動、3=末尾へ移動
     */
    public void changeDisplay(SettlementModel settlementModel, int actiontype) {
        // 移動対象表示順
        Integer orderDisplay = settlementModel.getOrderDisplay();

        // 決済方法アイテムリスト
        List<SettlementModelItem> resultItems = settlementModel.getResultItems();

        // 移動先表示順を設定
        int nextOrderDisplay = 0;
        switch (actiontype) {
            case 0:
                nextOrderDisplay = orderDisplay - 1;
                break;
            case 1:
                nextOrderDisplay = orderDisplay + 1;
                break;
            case 2:
                nextOrderDisplay = 1;
                break;
            default:
                nextOrderDisplay = resultItems.size();
                break;
        }

        // リストサイズより大きな表示順は存在しないので処理終了
        if (orderDisplay > resultItems.size() || nextOrderDisplay > resultItems.size() || orderDisplay < 1
            || nextOrderDisplay < 1) {
            return;
        }

        // 決済方法アイテム移動
        SettlementModelItem moveItem = resultItems.remove(orderDisplay - 1);
        resultItems.add(nextOrderDisplay - 1, moveItem);

        // 表示順再設定
        int orderIndex = 1;
        for (SettlementModelItem resultItem : resultItems) {
            resultItem.setOrderDisplayValue(orderIndex);
            orderIndex++;
        }
        settlementModel.setOrderDisplay(nextOrderDisplay);
    }

    /**
     * 決済方法リスト取得<br />
     *
     * @param settlementModel 決済方法設定ページ
     * @return 決済方法リスト
     */
    public List<SettlementMethodEntity> getSettlementMethodEntityList(SettlementModel settlementModel) {
        List<SettlementMethodEntity> settlementMethodList = new ArrayList<>();

        // 決済方法アイテムリスト
        List<SettlementModelItem> resultItems = settlementModel.getResultItems();

        for (SettlementModelItem resultItem : resultItems) {
            SettlementMethodEntity settlementMethodEntity =
                            ApplicationContextUtility.getBean(SettlementMethodEntity.class);
            settlementMethodEntity.setOrderDisplay(resultItem.getOrderDisplayValue());
            settlementMethodEntity.setSettlementMethodSeq(resultItem.getSettlementMethodSeq());
            settlementMethodList.add(settlementMethodEntity);
        }

        return settlementMethodList;
    }

    /**
     * 配送方法名取得
     *
     * @param deliveryMethodSeq 配送方法SEQ
     * @return 配送方法名
     */
    private String getDeliveryMethodName(Integer deliveryMethodSeq) {
        DivisionMapGetService divisionMapGetService = ApplicationContextUtility.getBean(DivisionMapGetService.class);
        Map<String, String> deliveryItem = divisionMapGetService.getDeliveryMapList();

        if (deliveryMethodSeq == null || deliveryItem == null) {
            return null;
        }

        String deliveryMethodName = null;
        for (Map.Entry<String, String> entry : deliveryItem.entrySet()) {
            if (entry.getKey().equals(deliveryMethodSeq.toString())) {
                deliveryMethodName = entry.getValue();
                break;
            }
        }

        return deliveryMethodName;
    }
}
