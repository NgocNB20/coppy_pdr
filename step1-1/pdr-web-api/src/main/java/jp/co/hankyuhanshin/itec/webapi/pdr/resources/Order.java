/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */
package jp.co.hankyuhanshin.itec.webapi.pdr.resources;

import jp.co.hankyuhanshin.itec.webapi.pdr.common.ApiInfo;
import jp.co.hankyuhanshin.itec.webapi.pdr.common.Constant;
import jp.co.hankyuhanshin.itec.webapi.pdr.common.PropManeger;
import jp.co.hankyuhanshin.itec.webapi.pdr.common.Utility;
import jp.co.hankyuhanshin.itec.webapi.pdr.request.order.AddCouponRequest;
import jp.co.hankyuhanshin.itec.webapi.pdr.request.order.AddOrderInformationOrderRequest;
import jp.co.hankyuhanshin.itec.webapi.pdr.request.order.AddOrderInformationRequest;
import jp.co.hankyuhanshin.itec.webapi.pdr.request.order.AddOrderMerchandiseInformationRequest;
import jp.co.hankyuhanshin.itec.webapi.pdr.request.order.CouponCheckRequest;
import jp.co.hankyuhanshin.itec.webapi.pdr.request.order.GetBeforePaymentRequest;
import jp.co.hankyuhanshin.itec.webapi.pdr.request.order.GetConsumptionTaxRateRequest;
import jp.co.hankyuhanshin.itec.webapi.pdr.request.order.GetCouponListRequest;
import jp.co.hankyuhanshin.itec.webapi.pdr.request.order.GetDeliveryCompanyRequest;
import jp.co.hankyuhanshin.itec.webapi.pdr.request.order.GetDeliveryInformationRequest;
import jp.co.hankyuhanshin.itec.webapi.pdr.request.order.GetDiscountsResultRequest;
import jp.co.hankyuhanshin.itec.webapi.pdr.request.order.GetPromotionInformationRequest;
import jp.co.hankyuhanshin.itec.webapi.pdr.request.order.GetQuantityDiscountsResultRequest;
import jp.co.hankyuhanshin.itec.webapi.pdr.request.order.GetShipmentDateInfoRequest;
import jp.co.hankyuhanshin.itec.webapi.pdr.request.order.GetShipmentDateRequest;
import jp.co.hankyuhanshin.itec.webapi.pdr.request.order.GetShippingProprietyRequest;
import jp.co.hankyuhanshin.itec.webapi.pdr.request.order.OrderPendingCheckRequest;
import jp.co.hankyuhanshin.itec.webapi.pdr.response.Result;
import jp.co.hankyuhanshin.itec.webapi.pdr.response.ResultInfo;
import jp.co.hankyuhanshin.itec.webapi.pdr.response.order.AddCouponResponse;
import jp.co.hankyuhanshin.itec.webapi.pdr.response.order.CouponCheckResponse;
import jp.co.hankyuhanshin.itec.webapi.pdr.response.order.GetBeforePaymentResponse;
import jp.co.hankyuhanshin.itec.webapi.pdr.response.order.GetConsumptionTaxRateResponse;
import jp.co.hankyuhanshin.itec.webapi.pdr.response.order.GetCouponListResponse;
import jp.co.hankyuhanshin.itec.webapi.pdr.response.order.GetDeliveryCompanyResponse;
import jp.co.hankyuhanshin.itec.webapi.pdr.response.order.GetDeliveryInformationDateResponse;
import jp.co.hankyuhanshin.itec.webapi.pdr.response.order.GetDeliveryInformationResponse;
import jp.co.hankyuhanshin.itec.webapi.pdr.response.order.GetDiscountsResultResponse;
import jp.co.hankyuhanshin.itec.webapi.pdr.response.order.GetPromotionAmountInformationResponse;
import jp.co.hankyuhanshin.itec.webapi.pdr.response.order.GetPromotionBundledInformationResponse;
import jp.co.hankyuhanshin.itec.webapi.pdr.response.order.GetPromotionDiscountInformationResponse;
import jp.co.hankyuhanshin.itec.webapi.pdr.response.order.GetPromotionInformationResponse;
import jp.co.hankyuhanshin.itec.webapi.pdr.response.order.GetQuantityDiscountsResultResponse;
import jp.co.hankyuhanshin.itec.webapi.pdr.response.order.GetShipmentDateResponse;
import jp.co.hankyuhanshin.itec.webapi.pdr.response.order.GetShippingProprietyResponse;
import jp.co.hankyuhanshin.itec.webapi.pdr.response.order.OrderPendingCheckResponse;
import net.arnx.jsonic.JSON;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

// PDR#15 20161115 k-katoh 新規作成

/**
 * PDR#15 WEB-API連携 注文系WebAPI<br/>
 * <pre>
 * ・割引適用結果取得
 * ・受注連携
 * ・プロモーション
 * ・配送情報取得
 * ・配送会社取得
 * ・配送可否取得
 * ・注文保留チェック
 * ・消費税率取得
 * ・数量割引適用結果取得
 * ・クーポン有効性チェック
 * </pre>
 * @author k-katoh
 */
@Path("/order")
public class Order {

    /** サーブレットコンテキスト */
    @Context
    private ServletContext context;

    /**
     * 割引適用結果取得<br/>
     *
     * @param jsonData 取得条件
     * <ul>
     *   <li>顧客番号:customerNo</li>
     *   <li>申込商品:goodsCode</li>
     *   <li>数量:quantity</li>
     *   <li>表示順:orderDisplay</li>
     * </ul>
     * @return JSON形式の処理結果
     * <ul>
     *   <li>処理結果:result</li>
     *   <ul>
     *     <li>ステータス:status</li>
     *     <li>メッセージ:message</li>
     *   </ul>
     *   <li>【リスト】情報リスト:info</li>
     *   <ul>
     *     <li>申込商品:goodsCode</li>
     *     <li>適用割引:saleType</li>
     *     <li>数量割引グループコード:saleGroupCode</li>
     *     <li>割引価格:salePrice</li>
     *     <li>数量:quantity</li>
     *     <li>セールコード:saleCode</li>
     *     <li>備考:note</li>
     *     <li>注意事項:hints</li>
     *     <li>表示順:orderDisplay</li>
     *   </ul>
     * </ul>
     */
    @POST
    @Path("/GetDiscountsResult")
    @Produces({"application/json"})
    @Consumes({"application/json"})
    public Response getDiscountsResult(String jsonData) {

        Logger log = LogManager.getLogger("getDiscountsResult");

        // 返却情報
        Result result = new Result();
        ResultInfo resultInfo = new ResultInfo();
        ApiInfo apiInfo = new ApiInfo("getDiscountsResult", "割引適用結果取得[Order/GetDiscountsResult]", context, log);

        Connection conn = null;
        CallableStatement cs = null;

        try {

            log.info("[IN]" + jsonData);

            // POSTされたJSONをデコードし、専用のオブジェクトに変換する
            GetDiscountsResultRequest req = JSON.decode(jsonData, GetDiscountsResultRequest.class);

            // エラーキー情報を共通セット
            Utility.setErrorKeyInfo(apiInfo, jsonData);

            // JDBC接続
            String driverStr = PropManeger.getConf(apiInfo.getContext(), "db.jdbc.driver");
            Driver driver = (Driver) Class.forName(driverStr).newInstance();
            String connUrl = PropManeger.getConf(apiInfo.getContext(), "db.jdbc.url");
            conn = driver.connect(connUrl, new Properties());

            // パラメータをセットし、ストアドを呼出す
            cs = conn.prepareCall("{call ProcGetDiscountsResult(?,?,?,?)}");
            // 顧客番号
            if (req.getCustomerNo() == null) {
                cs.setNull(1, java.sql.Types.INTEGER);
            } else {
                cs.setInt(1, Integer.parseInt(req.getCustomerNo()));
            }
            // 申込商品
            cs.setString(2, req.getGoodsCode());
            // 数量
            cs.setString(3, req.getQuantity());
            // 表示順
            cs.setString(4, req.getOrderDisplay());
            // 取得結果（リターンコード）
            // cs.registerOutParameter(5, java.sql.Types.SMALLINT);
            cs.executeQuery();
            Integer status = 0;

            // 処理結果を取得してしまうとResultSetがクリアされてしまう為
            // 前もって取得しておく必要がある
            ResultSet rs = cs.getResultSet();
            List<Object> infoList = new ArrayList<>();
            GetDiscountsResultResponse info;
            while (rs != null && rs.next()) {
                info = new GetDiscountsResultResponse();
                // 申込商品
                info.setGoodsCode(rs.getString(1));
                // 適用割引
                info.setSaleType(rs.getString(2));
                // 数量割引グループコード
                info.setSaleGroupCode(rs.getString(3));
                // 割引価格
                info.setSalePrice(rs.getString(4));
                // 数量
                info.setQuantity(rs.getString(5));
                // セールコード
                info.setSaleCode(rs.getString(6));
                // 備考
                info.setNote(rs.getString(7));
                // 注意事項
                info.setHints(rs.getString(8));
                // 表示順
                info.setOrderDisplay(rs.getString(9));
                // ステータス
                status = rs.getInt(10);
                infoList.add(info);
            }

            // ストアドからの返却値
            apiInfo.setStoredName("ProcGetDiscountsResult");
            apiInfo.setStoredCode(status);
            Utility.makeResult(apiInfo, result, resultInfo, infoList);

        } catch (Exception ex) {

            apiInfo.setException(ex);
            Utility.logicException(apiInfo, result, resultInfo);

        } finally {

            // クローズ処理
            Utility.close(cs, conn, apiInfo);
        }

        // 処理結果をJSON形式に変換して返却
        return Utility.createResponse(log, result, jsonData, Level.INFO);
    }

    /**
     * 受注連携<br/>
     *
     * @param jsonData 登録情報
     * <ul>
     *   <li>【リスト】受注リスト</li>
     *   <ul>
     *     <li>受注番号:orderNo</li>
     *     <li>関連受注番号:relOrderNo</li>
     *     <li>入力担当者:inputUserID</li>
     *     <li>確定担当者:comfirmUserID</li>
     *     <li>受付方法:orderType</li>
     *     <li>受注日:orderDate</li>
     *     <li>出荷予定日:shipmentDate</li>
     *     <li>顧客番号:customerNo</li>
     *     <li>電話番号:telNo</li>
     *     <li>広告媒体:mediaCode</li>
     *     <li>倉庫:stockroomCode</li>
     *     <li>お届け先顧客番号:deliveryCustomerNo</li>
     *     <li>お届け先連絡番号:deliveryTelNo</li>
     *     <li>使用ポイント:usePoint</li>
     *     <li>支払方法:paymentType</li>
     *     <li>クレジット会社:creditCompanyCode</li>
     *     <li>クレジット番号:creditCardNo</li>
     *     <li>クレジット有効期限:creditExpirationDate</li>
     *     <li>クレジット支払回数:creditSplitNumber</li>
     *     <li>クレジット決済ID:creditPaymentID</li>
     *     <li>配送方法:deliveryType</li>
     *     <li>請求書:requisitionType</li>
     *     <li>保留区分:holdType</li>
     *     <li>配達指定日:deliveryDesignatedDay</li>
     *     <li>配達指定時間:deliveryDesignatedTimeCode</li>
     *     <li>出荷場メモ１:shippingMemo1</li>
     *     <li>出荷場メモ２:shippingMemo2</li>
     *     <li>合計金額:totalPrice</li>
     *     <li>ポイント値引:pointDiscountPrice</li>
     *     <li>プロモ値引:promotionDiscountPrice</li>
     *     <li>値引:discountPrice</li>
     *     <li>合計値引:totalDiscountPrice</li>
     *     <li>送料:shippingPrice</li>
     *     <li>請求金額:billingPrice</li>
     *     <li>内消費税:taxPrice</li>
     *     <li>【リスト】商品リスト:goodsInfo</li>
     *     <ul>
     *       <li>受注連番:orderSeq</li>
     *       <li>申込商品:goodsNo</li>
     *       <li>数量:quantity</li>
     *       <li>単価:unitPrice</li>
     *       <li>金額:price</li>
     *       <li>状態フラグ:stateFlag</li>
     *       <li>セールコード:saleCode</li>
     *       <li>備考:remarks</li>
     *       <li>注意事項:hints</li>
     *       <li>同梱商品フラグ:bundleFlag</li>
     *       <li>グループ:groupCode</li>
     *       <li>明細番号:detailNo</li>
     *     </ul>
     *   </ul>
     * </ul>
     * @return JSON形式の処理結果
     * <ul>
     *   <li>処理結果:result</li>
     *   <ul>
     *     <li>ステータス:status</li>
     *     <li>メッセージ:message</li>
     *   </ul>
     * </ul>
     */
    @POST
    @Path("/AddOrderInformation")
    @Produces({"application/json"})
    @Consumes({"application/json"})
    public Response addOrderInformation(String jsonData) {

        // 注文情報登録エラー
        final int errorAddOrderInformation = 1;
        // 注文商品情報登録エラー
        final int errorAddOrderMerchandiseInformation = 2;

        Logger log = LogManager.getLogger("addOrderInformation");

        // 返却情報
        Result result = new Result();
        ResultInfo resultInfo = new ResultInfo();
        ApiInfo apiInfo = new ApiInfo("addOrderInformation", "受注連携[Order/AddOrderInformation]", context, log);
        int status = 0;

        Connection conn = null;
        CallableStatement cs = null;
        CallableStatement cs2 = null;

        try {

            // INパラメータをログに出力する
            log.info("[IN]" + jsonData);

            // POSTされたJSONをデコードし、専用のオブジェクトに変換する
            AddOrderInformationRequest req = JSON.decode(jsonData, AddOrderInformationRequest.class);

            // エラーキー情報をセット(エラーメールは受注番号と顧客番号のみ)
            apiInfo.setErrorKeyInfoLog(jsonData);
            apiInfo.setErrorKeyInfoMail("受注番号=" + req.getOrderInfo().get(0).getOrderNo() + ":顧客番号=" + req.getOrderInfo()
                                                                                                         .get(0)
                                                                                                         .getCustomerNo());

            // JDBC接続
            String driverStr = PropManeger.getConf(apiInfo.getContext(), "db.jdbc.driver");
            Driver driver = (Driver) Class.forName(driverStr).newInstance();
            String connUrl = PropManeger.getConf(apiInfo.getContext(), "db.jdbc.url");
            conn = driver.connect(connUrl, new Properties());

            conn.setAutoCommit(false);

            cs = conn.prepareCall(Utility.createCallStored("ProcAddOrderInformation", 40));
            cs2 = conn.prepareCall(Utility.createCallStored("ProcAddOrderMerchandiseInformation", 18));

            for (AddOrderInformationOrderRequest order : req.getOrderInfo()) {

                // パラメータをセットし、ストアドを呼出す
                cs.clearParameters();

                // 受注番号
                if (order.getOrderNo() == null) {
                    cs.setNull(1, java.sql.Types.INTEGER);
                } else {
                    cs.setInt(1, Integer.parseInt(order.getOrderNo()));
                }
                // 関連受注番号
                if (order.getRelOrderNo() == null) {
                    cs.setNull(2, java.sql.Types.INTEGER);
                } else {
                    cs.setInt(2, Integer.parseInt(order.getRelOrderNo()));
                }
                // 入力担当者
                if (order.getInputUserID() == null) {
                    cs.setNull(3, java.sql.Types.INTEGER);
                } else {
                    cs.setInt(3, Short.parseShort(order.getInputUserID()));
                }
                // 確定担当者
                if (order.getComfirmUserID() == null) {
                    cs.setNull(4, java.sql.Types.INTEGER);
                } else {
                    cs.setInt(4, Short.parseShort(order.getComfirmUserID()));
                }
                // 受付方法
                if (order.getOrderType() == null) {
                    cs.setNull(5, java.sql.Types.INTEGER);
                } else {
                    cs.setInt(5, Short.parseShort(order.getOrderType()));
                }
                // 受注日
                cs.setTimestamp(6, order.getOrderDate());
                // 出荷予定日
                cs.setTimestamp(7, order.getShipmentDate());
                // 入荷予定日
                cs.setTimestamp(8, order.getStockDate());
                // 顧客番号
                if (order.getCustomerNo() == null) {
                    cs.setNull(9, java.sql.Types.INTEGER);
                } else {
                    cs.setInt(9, Integer.parseInt(order.getCustomerNo()));
                }
                // 電話番号
                cs.setString(10, order.getTelNo());
                // 広告媒体
                cs.setString(11, order.getMediaCode());
                // 倉庫
                if (order.getStockroomCode() == null) {
                    cs.setNull(12, java.sql.Types.INTEGER);
                } else {
                    cs.setInt(12, Short.parseShort(order.getStockroomCode()));
                }
                // お届け先顧客番号
                if (order.getDeliveryCustomerNo() == null) {
                    cs.setNull(13, java.sql.Types.INTEGER);
                } else {
                    cs.setInt(13, Integer.parseInt(order.getDeliveryCustomerNo()));
                }
                // お届け先連絡番号
                cs.setString(14, order.getDeliveryTelNo());
                // 使用ポイント
                if (order.getUsePoint() == null) {
                    cs.setNull(15, java.sql.Types.INTEGER);
                } else {
                    cs.setInt(15, Integer.parseInt(order.getUsePoint()));
                }
                // 支払方法
                if (order.getPaymentType() == null) {
                    cs.setNull(16, java.sql.Types.INTEGER);
                } else {
                    cs.setInt(16, Short.parseShort(order.getPaymentType()));
                }
                // クレジット会社
                if (order.getCreditCompanyCode() == null) {
                    cs.setNull(17, java.sql.Types.INTEGER);
                } else {
                    cs.setInt(17, Short.parseShort(order.getCreditCompanyCode()));
                }
                // クレジット番号
                cs.setString(18, order.getCreditCardNo());
                // クレジット有効期限
                cs.setString(19, order.getCreditExpirationDate());
                // クレジット支払回数
                if (order.getCreditSplitNumber() == null) {
                    cs.setNull(20, java.sql.Types.INTEGER);
                } else {
                    cs.setInt(20, Short.parseShort(order.getCreditSplitNumber()));
                }
                // クレジット決済ＩＤ
                cs.setString(21, order.getCreditPaymentID());
                // 配送方法
                if (order.getDeliveryType() == null) {
                    cs.setNull(22, java.sql.Types.INTEGER);
                } else {
                    cs.setInt(22, Short.parseShort(order.getDeliveryType()));
                }
                // 請求書
                if (order.getRequisitionType() == null) {
                    cs.setNull(23, java.sql.Types.INTEGER);
                } else {
                    cs.setInt(23, Short.parseShort(order.getRequisitionType()));
                }
                // 保留区分
                if (order.getHoldType() == null) {
                    cs.setNull(24, java.sql.Types.INTEGER);
                } else {
                    cs.setInt(24, Short.parseShort(order.getHoldType()));
                }
                // 配達指定日
                cs.setTimestamp(25, order.getDeliveryDesignatedDay());
                // 配達指定時間
                if (order.getDeliveryDesignatedTimeCode() == null) {
                    cs.setNull(26, java.sql.Types.INTEGER);
                } else {
                    cs.setInt(26, Short.parseShort(order.getDeliveryDesignatedTimeCode()));
                }
                // 出荷場メモ１
                cs.setString(27, order.getShippingMemo1());
                // 出荷場メモ２
                cs.setString(28, order.getShippingMemo2());
                // 合計金額
                cs.setBigDecimal(29, order.getTotalPrice());
                // ポイント値引
                cs.setBigDecimal(30, order.getPointDiscountPrice());
                // プロモ値引
                cs.setBigDecimal(31, order.getPromotionDiscountPrice());
                // 値引
                cs.setBigDecimal(32, order.getDiscountPrice());
                // 合計値引
                cs.setBigDecimal(33, order.getTotalDiscountPrice());
                // 送料
                cs.setBigDecimal(34, order.getShippingPrice());
                // 請求金額
                cs.setBigDecimal(35, order.getBillingPrice());
                // 内消費税
                cs.setBigDecimal(36, order.getTaxPrice());
                // マーチャント取引ID
                cs.setString(37, order.getTradingID());
                // クーポンコード
                cs.setString(38, order.getCouponCode());
                // プロモーションコード
                cs.setString(39, order.getPromotionCode());

                // 取得結果（リターンコード）
                cs.registerOutParameter(40, java.sql.Types.SMALLINT);
                cs.executeUpdate();

                // ストアドからの返却値
                status = cs.getShort(40);

                // ストアドからの返却値が正常以外の場合
                if (Constant.STORED_OK != status) {

                    try {
                        if (conn != null) {
                            conn.rollback();
                        }
                    } catch (SQLException e) {
                        apiInfo.setException(e);
                        Utility.errorLogAndMail(apiInfo);
                    }

                    // 注文情報登録ストアドからエラーが返却された場合
                    resultInfo.setStatus(errorAddOrderInformation);
                    resultInfo.setMessage(apiInfo.getErrorMsg());
                    result.setResult(resultInfo);

                    // 返却
                    apiInfo.setStoredCode(status);
                    apiInfo.setStoredName("ProcAddOrderInformation");
                    Utility.errorLogAndMail(apiInfo);
                    // 処理結果をJSON形式に変換して返却
                    return Utility.createResponse(log, result, jsonData, Level.INFO);

                }

                for (AddOrderMerchandiseInformationRequest goods : order.getGoodsInfo()) {
                    // パラメータをセットし、ストアドを呼出す
                    cs2.clearParameters();

                    // 受注番号
                    if (order.getOrderNo() == null) {
                        cs2.setNull(1, java.sql.Types.INTEGER);
                    } else {
                        cs2.setInt(1, Integer.parseInt(order.getOrderNo()));
                    }
                    // 受注連番
                    if (goods.getOrderSeq() == null) {
                        cs2.setNull(2, java.sql.Types.INTEGER);
                    } else {
                        cs2.setInt(2, Short.parseShort(goods.getOrderSeq()));
                    }
                    // 申込商品
                    cs2.setString(3, goods.getGoodsNo());
                    // 数量
                    if (goods.getQuantity() == null) {
                        cs2.setNull(4, java.sql.Types.INTEGER);
                    } else {
                        cs2.setInt(4, Short.parseShort(goods.getQuantity()));
                    }
                    // 単価
                    cs2.setBigDecimal(5, goods.getUnitPrice());
                    // 金額
                    cs2.setBigDecimal(6, goods.getPrice());
                    // 状態フラグ
                    if (goods.getStateFlag() == null) {
                        cs2.setNull(7, java.sql.Types.INTEGER);
                    } else {
                        cs2.setInt(7, Short.parseShort(goods.getStateFlag()));
                    }
                    // セールコード
                    cs2.setString(8, goods.getSaleCode());
                    // 備考
                    cs2.setString(9, goods.getRemarks());
                    // 注意事項
                    cs2.setString(10, goods.getHints());
                    // 同梱商品フラグ
                    if (goods.getBundleFlag() == null) {
                        cs2.setNull(11, java.sql.Types.INTEGER);
                    } else {
                        cs2.setInt(11, Short.parseShort(goods.getBundleFlag()));
                    }
                    // グループ
                    if (goods.getGroupCode() == null) {
                        cs2.setNull(12, java.sql.Types.INTEGER);
                    } else {
                        cs2.setInt(12, Short.parseShort(goods.getGroupCode()));
                    }
                    // 明細番号
                    if (goods.getDetailNo() == null) {
                        cs2.setNull(13, java.sql.Types.INTEGER);
                    } else {
                        cs2.setInt(13, Short.parseShort(goods.getDetailNo()));
                    }
                    // 適用割引
                    cs2.setString(14, goods.getDiscountFlag());
                    // 標準単価
                    if (goods.getBasePrice() == null) {
                        cs2.setNull(15, java.sql.Types.INTEGER);
                    } else {
                        cs2.setBigDecimal(15, goods.getBasePrice());
                    }
                    // セール値引額
                    if (goods.getSaleDiscount() == null) {
                        cs2.setNull(16, java.sql.Types.INTEGER);
                    } else {
                        cs2.setBigDecimal(16, goods.getSaleDiscount());
                    }
                    // 単価値引額
                    if (goods.getDiscountFlag() == null) {
                        cs2.setNull(17, java.sql.Types.INTEGER);
                    } else {
                        cs2.setBigDecimal(17, goods.getUnitDiscountPrice());
                    }

                    // 取得結果（リターンコード）
                    cs2.registerOutParameter(18, java.sql.Types.SMALLINT);
                    cs2.executeUpdate();

                    // ストアドからの返却値
                    status = cs2.getShort(18);

                    // ストアドからの返却値が正常以外の場合
                    if (Constant.STORED_OK != status) {

                        try {
                            if (conn != null) {
                                conn.rollback();
                            }
                        } catch (SQLException e) {
                            apiInfo.setException(e);
                            Utility.errorLogAndMail(apiInfo);
                        }

                        log.error("[受注番号：" + order.getOrderNo() + "][受注連番：" + goods.getOrderSeq() + "]");

                        resultInfo.setMessage(apiInfo.getErrorMsg());

                        // 注文情報登録ストアドからエラーが返却された場合
                        resultInfo.setStatus(errorAddOrderMerchandiseInformation);
                        resultInfo.setMessage(PropManeger.getMessage(context, "addOrderMerchandiseInformation.error"));
                        result.setResult(resultInfo);

                        // 返却
                        apiInfo.setStoredCode(status);
                        apiInfo.setStoredName("ProcAddOrderMerchandiseInformation");
                        Utility.errorLogAndMail(apiInfo);

                        // 処理結果をJSON形式に変換して返却
                        return Utility.createResponse(log, result, jsonData, Level.INFO);
                    }
                }
            }

            resultInfo.setStatus(status);
            result.setResult(resultInfo);

            conn.commit();

        } catch (Exception ex) {

            try {
                if (conn != null) {
                    conn.rollback();
                }
            } catch (SQLException e) {
                apiInfo.setException(e);
                Utility.errorLogAndMail(apiInfo);
            }

            apiInfo.setException(ex);
            Utility.logicException(apiInfo, result, resultInfo);

        } finally {
            // クローズ処理
            Utility.close(cs, cs2, conn, apiInfo);
        }

        // 処理結果をJSON形式に変換して返却
        return Utility.createResponse(log, result, jsonData, Level.INFO);
    }

    /**
     * プロモーション<br/>
     *
     * @param jsonData 受注情報
     * <ul>
     *   <li>明細内容のリスト:detailInfo</li>
     * </ul>
     * @return JSON形式の処理結果
     * <ul>
     *   <li>処理結果:result</li>
     *   <ul>
     *     <li>ステータス:status</li>
     *     <li>メッセージ:message</li>
     *   </ul>
     *   <li>情報リスト:info</li>
     *   <ul>
     *     <li>金額情報リスト:priceInfo</li>
     *     <ul>
     *       <li>出荷予定日</li>
     *       <li>配達指定日</li>
     *       <li>入荷予定日</li>
     *       <li>値引</li>
     *       <li>送料</li>
     *       <li>消費税</li>
     *       <li>プロモーションコード</li>
     *     </ul>
     *     <li>同梱情報リスト:bundleInfo</li>
     *     <ul>
     *       <li>出荷予定日</li>
     *       <li>配達指定日</li>
     *       <li>入荷予定日</li>
     *       <li>明細番号</li>
     *       <li>同梱商品</li>
     *       <li>同梱数量</li>
     *       <li>納品書印字フラグ</li>
     *     </ul>
     *     <li>割引情報リスト:discountInfo</li>
     *     <ul>
     *       <li>受注連番</li>
     *       <li>設定パターン</li>
     *       <li>割引率</li>
     *       <li>単価値引額</li>
     *     </ul>
     *     <li>クーポン以外プロモーション適用結果</li>
     *     <li>クーポンプロモーション適用結果</li>
     *     <li>クーポン名称</li>
     *   </ul>
     * </ul>
     */
    @POST
    @Path("/GetPromotionInformation")
    @Produces({"application/json"})
    @Consumes({"application/json"})
    public Response getPromotionInformation(String jsonData) {

        // 正常終了（プロモーション対象外）
        final int normalNot = 1;
        // プロモーション付与対象チェックエラー
        final int errorPromotionGranteesCheck = 2;
        // プロモーション金額情報取得エラー
        final int errorGetPromotionAmountInformation = 3;
        // プロモーション同梱情報取得エラー
        final int errorPromotionBundledInformation = 4;
        // プロモーション同梱納品書印字取得エラー
        final int errorGetPromotionBundledPrint = 5;
        // プロモーション値引率情報取得エラー
        final int errorGetPromotionDiscountInformation = 6;
        Integer status = 0;

        Logger log = LogManager.getLogger("getPromotionInformation");

        // 返却情報
        Result result = new Result();
        ResultInfo resultInfo = new ResultInfo();
        ApiInfo apiInfo =
                        new ApiInfo("getPromotionInformation", "プロモーション[Order/GetPromotionInformation]", context, log);

        Connection conn = null;
        CallableStatement cs = null;

        try {

            // INパラメータをログに出力する
            log.info("[IN]" + jsonData);

            // POSTされたJSONをデコードし、専用のオブジェクトに変換する
            GetPromotionInformationRequest req = JSON.decode(jsonData, GetPromotionInformationRequest.class);

            // エラーキー情報を共通セット
            Utility.setErrorKeyInfo(apiInfo, jsonData);

            // JDBC接続
            String driverStr = PropManeger.getConf(apiInfo.getContext(), "db.jdbc.driver");
            Driver driver = (Driver) Class.forName(driverStr).newInstance();
            String connUrl = PropManeger.getConf(apiInfo.getContext(), "db.jdbc.url");
            conn = driver.connect(connUrl, new Properties());

            // パラメータをセットし、ストアドを呼出す
            cs = conn.prepareCall(
                            Utility.createCallStored("ProcPromotionGranteesCheck", Constant.PROMOTION_DETAIL_NUM));
            String promotionOrderno = null;

            // 2023-renew No14 from here
            // リクエスト配達指定日保持Set
            // ※配達指定日までMockDBに持たせるのは試験効率が悪すぎるので、リクエストの値をセットして返すため。
            Set<String> deliveryDesignatedDays = new HashSet<>();
            // 2023-renew No14 to here

            // 明細内容
            for (int i = 1; i <= Constant.PROMOTION_DETAIL_NUM; i++) {
                if (req.getDetailInfo() != null && req.getDetailInfo().size() >= i) {
                    cs.setString(i, req.getDetailInfo().get(i - 1));
                    if (i == 1) {
                        promotionOrderno = req.getDetailInfo().get(i - 1).substring(0, 7);
                    }
                    // 2023-renew No14 from here
                    // リクエストの配達指定日をリストに保持
                    deliveryDesignatedDays.add(req.getDetailInfo().get(i - 1).substring(123, 131));
                    // 2023-renew No14 to here
                } else {
                    cs.setNull(i, java.sql.Types.VARCHAR);
                }
            }

            // 取得結果（リターンコード）
            // cs.registerOutParameter(100, java.sql.Types.SMALLINT);
            cs.executeQuery();
            status = 0;

            // 処理結果を取得してしまうとResultSetがクリアされてしまう為
            // 前もって取得しておく必要がある
            ResultSet rs = cs.getResultSet();
            List<GetPromotionInformationResponse> couponInfoList = new ArrayList<>();
            GetPromotionInformationResponse couponInfo;

            while (rs != null && rs.next()) {
                couponInfo = new GetPromotionInformationResponse();

                // クーポン以外プロモーション適用結果
                couponInfo.setPromApplyFlag(rs.getString(12));
                // クーポンプロモーション適用結果
                couponInfo.setCouponApplyFlag(rs.getString(13));
                // クーポン名称
                couponInfo.setCouponName(rs.getString(14));
                // ステータス
                status = rs.getInt(15);

                couponInfoList.add(couponInfo);
            }

            // ストアドからの返却値が正常以外の場合
            if (Constant.STORED_OK != status) {

                // ストアドからエラーが返却された場合
                resultInfo.setStatus(errorPromotionGranteesCheck);
                resultInfo.setMessage(PropManeger.getMessage(context, "promotionGranteesCheck.error"));
                result.setResult(resultInfo);

                // エラーログ、エラーメール送信
                apiInfo.setStoredCode(status);
                apiInfo.setStoredName("ProcPromotionGranteesCheck");
                Utility.errorLogAndMail(apiInfo);
                // 処理結果をJSON形式に変換して返却
                return Utility.createResponse(log, result, jsonData, Level.INFO);
            }

            // 受注番号が返却されなかった場合は、プロモーション情報なし
            if (promotionOrderno == null || promotionOrderno.isEmpty()) {
                // ストアドからエラーが返却された場合
                resultInfo.setStatus(normalNot);
                result.setResult(resultInfo);

                // 処理結果をJSON形式に変換して返却
                return Utility.createResponse(log, result, jsonData, Level.INFO);
            }

            // パラメータをセットし、ストアドを呼出す
            cs.close();
            cs = conn.prepareCall("{call ProcGetPromotionAmountInformation(?)}");
            // 受注番号
            cs.setInt(1, Integer.parseInt(promotionOrderno));

            // 取得結果リターンコード）
            // cs.registerOutParameter(2, java.sql.Types.SMALLINT);
            cs.executeQuery();
            status = 0;

            // プロモーション情報が存在する場合
            // 処理結果を取得してしまうとResultSetがクリアされてしまう為
            // 前もって取得しておく必要がある
            rs.close();
            rs = cs.getResultSet();
            List<GetPromotionAmountInformationResponse> priceInfoList = new ArrayList<>();
            GetPromotionAmountInformationResponse priceInfo;
            while (rs != null && rs.next()) {

                // 2023-renew No14 from here
                // リスエストに含まれる配達指定日の分だけ、リストを膨らませる
                // ※配達指定日までMockDBに持たせるのは、試験効率が悪すぎるため・・・
                for (String deliveryDesignatedDay : deliveryDesignatedDays) {
                    priceInfo = new GetPromotionAmountInformationResponse();

                    // 出荷予定日
                    priceInfo.setShipmentDate(rs.getString(2));
                    // 配達指定日
                    priceInfo.setDeliveryDesignatedDay(deliveryDesignatedDay);
                    // 入荷予定日
                    priceInfo.setStockDate(rs.getString(3));
                    // 値引
                    priceInfo.setDiscountPrice(rs.getBigDecimal(4));
                    // 送料
                    priceInfo.setShippingPrice(rs.getBigDecimal(5));
                    // 消費税
                    priceInfo.setTaxPrice(rs.getBigDecimal(6));
                    // プロモーションコード
                    priceInfo.setPromotionCode(rs.getString(7));

                    // リスト追加
                    priceInfoList.add(priceInfo);
                }
                // 2023-renew No14 to here

                // ステータス
                status = rs.getInt(8);
            }

            // ストアドからの返却値
            // status = cs.getInt(2);

            // ストアドからの返却値が正常以外の場合
            if (Constant.STORED_OK != status) {

                // ストアドからエラーが返却された場合
                resultInfo.setStatus(errorGetPromotionAmountInformation);
                resultInfo.setMessage(PropManeger.getMessage(context, "getPromotionAmountInformation.error"));
                result.setResult(resultInfo);

                // エラーログ、エラーメール送信
                apiInfo.setStoredCode(status);
                apiInfo.setStoredName("ProcGetPromotionAmountInformation");
                Utility.errorLogAndMail(apiInfo);
                // 処理結果をJSON形式に変換して返却
                return Utility.createResponse(log, result, jsonData, Level.INFO);
            }

            // パラメータをセットし、ストアドを呼出す
            cs.close();
            cs = conn.prepareCall("{call ProcGetPromotionBundledInformation(?)}");

            // 受注番号
            cs.setInt(1, Integer.parseInt(promotionOrderno));

            cs.executeQuery();
            status = 0;

            // プロモーション情報が存在する場合
            // 処理結果を取得してしまうとResultSetがクリアされてしまう為
            // 前もって取得しておく必要がある
            rs.close();
            rs = cs.getResultSet();
            List<GetPromotionBundledInformationResponse> bundleInfoList = new ArrayList<>();
            GetPromotionBundledInformationResponse bundleInfo;

            // 商品コードの重複を省くためHashSetを使用する
            Set<String> bundleGoodsCodeSet = new HashSet<>();

            while (rs != null && rs.next()) {

                // 2023-renew No14 from here
                // リスエストに含まれる配達指定日の分だけ、リストを膨らませる
                // ※配達指定日までMockDBに持たせるのは、試験効率が悪すぎるため・・・
                for (String deliveryDesignatedDay : deliveryDesignatedDays) {
                    bundleInfo = new GetPromotionBundledInformationResponse();

                    // 出荷予定日
                    bundleInfo.setShipmentDate(rs.getString(2));
                    // 配達指定日
                    bundleInfo.setDeliveryDesignatedDay(deliveryDesignatedDay);
                    // 入荷予定日
                    bundleInfo.setStockDate(rs.getString(3));
                    // 明細番号
                    bundleInfo.setOrderSeq(rs.getString(4));
                    // 同梱商品
                    bundleInfo.setBundleGoodsCode(rs.getString(5));
                    // 同梱数量
                    bundleInfo.setBundleGoodsCount(rs.getString(6));

                    // リスト追加
                    bundleInfoList.add(bundleInfo);
                }
                // 2023-renew No14 to here
                bundleGoodsCodeSet.add(rs.getString(5));

                // ステータス
                status = rs.getInt(7);

            }

            // ストアドからの返却値
            // status = cs.getInt(2);

            // ストアドからの返却値が正常以外の場合
            if (Constant.STORED_OK != status) {

                // ストアドからエラーが返却された場合
                resultInfo.setStatus(errorPromotionBundledInformation);
                resultInfo.setMessage(PropManeger.getMessage(context, "getPromotionBundledInformation.error"));
                result.setResult(resultInfo);

                // エラーログ、エラーメール送信
                apiInfo.setStoredCode(status);
                apiInfo.setStoredName("ProcGetPromotionBundledInformation");
                Utility.errorLogAndMail(apiInfo);
                // 処理結果をJSON形式に変換して返却
                return Utility.createResponse(log, result, jsonData, Level.INFO);
            }

            // パラメータをセットし、ストアドを呼出す
            cs.close();
            cs = conn.prepareCall("{call ProcGetPromotionBundledPrint(?)}");

            StringBuffer goodsList = new StringBuffer();
            for (String goodsCode : bundleGoodsCodeSet) {
                if (goodsList.length() != 0) {
                    goodsList.append("|");
                }
                goodsList.append(goodsCode);
            }
            // 申込商品（パイプ区切り）
            cs.setString(1, goodsList.toString());
            // 取得結果リターンコード）
            // cs.registerOutParameter(2, java.sql.Types.SMALLINT);
            cs.executeQuery();
            status = 0;

            // プロモーション情報が存在する場合
            // 処理結果を取得してしまうとResultSetがクリアされてしまう為
            // 前もって取得しておく必要がある
            rs.close();
            rs = cs.getResultSet();
            while (rs != null && rs.next()) {

                for (GetPromotionBundledInformationResponse bundleInformation : bundleInfoList) {
                    if (bundleInformation.getBundleGoodsCode().equals(rs.getString(1))) {
                        bundleInformation.setDeliverySlipFlag(rs.getString(2));
                    }
                }
                // ステータス
                status = rs.getInt(3);
            }

            if (bundleInfoList != null && !bundleInfoList.isEmpty()) {

                // ストアドからの返却値
                // status = cs.getInt(2);

                // ストアドからの返却値が正常以外の場合
                if (Constant.STORED_OK != status) {

                    // ストアドからエラーが返却された場合
                    resultInfo.setStatus(errorGetPromotionBundledPrint);
                    resultInfo.setMessage(PropManeger.getMessage(context, "getPromotionBundledPrint.error"));
                    result.setResult(resultInfo);

                    // エラーログ、エラーメール送信
                    apiInfo.setStoredCode(status);
                    apiInfo.setStoredName("ProcGetPromotionBundledPrint");
                    Utility.errorLogAndMail(apiInfo);
                    // 処理結果をJSON形式に変換して返却
                    return Utility.createResponse(log, result, jsonData, Level.INFO);
                }
            }

            // パラメータをセットし、ストアドを呼出す
            cs.close();
            cs = conn.prepareCall("{call ProcGetPromotionDiscountInformation(?)}");
            // 受注番号
            cs.setInt(1, Integer.parseInt(promotionOrderno));
            // 取得結果リターンコード）
            // cs.registerOutParameter(2, java.sql.Types.SMALLINT);
            cs.executeQuery();
            status = 0;

            // プロモーション値引率情報が存在する場合
            // 処理結果を取得してしまうとResultSetがクリアされてしまう為
            // 前もって取得しておく必要がある
            rs.close();
            rs = cs.getResultSet();
            List<GetPromotionDiscountInformationResponse> discountInfoList = new ArrayList<>();
            GetPromotionDiscountInformationResponse discountInfo;

            while (rs != null && rs.next()) {
                discountInfo = new GetPromotionDiscountInformationResponse();
                // 受注連番
                discountInfo.setOrderSerial(rs.getString(2));
                // 設定パターン
                discountInfo.setSettingPattern(rs.getString(3));
                // 割引率
                discountInfo.setDiscountRate(rs.getBigDecimal(4));
                // 単価値引額
                discountInfo.setUnitDiscountPrice(rs.getBigDecimal(5));

                discountInfoList.add(discountInfo);

                // ステータス
                status = rs.getInt(6);
            }

            // ストアドからの返却値
            // status = cs.getInt(2);

            // ストアドからの返却値が正常以外の場合
            if (Constant.STORED_OK != status) {

                // ストアドからエラーが返却された場合
                resultInfo.setStatus(errorGetPromotionDiscountInformation);
                resultInfo.setMessage(PropManeger.getMessage(context, "getPromotionDiscountInformation.error"));
                result.setResult(resultInfo);

                // エラーログ、エラーメール送信
                apiInfo.setStoredCode(status);
                apiInfo.setStoredName("ProcGetPromotionDiscountInformation");
                Utility.errorLogAndMail(apiInfo);
                // 処理結果をJSON形式に変換して返却
                return Utility.createResponse(log, result, jsonData, Level.INFO);
            }

            // 受注番号がある、かつ、「priceInfo」「bundleInfo」「discountInfo」が空の場合は、プロモーション情報なしと判定
            if ((promotionOrderno != null || !promotionOrderno.isEmpty()) && priceInfoList.isEmpty()
                && bundleInfoList.isEmpty() && discountInfoList.isEmpty()) {
                resultInfo.setStatus(normalNot);
                result.setResult(resultInfo);

                // 処理結果をJSON形式に変換して返却
                return Utility.createResponse(log, result, jsonData, Level.INFO);
            }

            List<Object> infoList = new ArrayList<>();
            GetPromotionInformationResponse info = new GetPromotionInformationResponse();
            if (priceInfoList != null && priceInfoList.size() > 0) {
                info.setPriceInfo(priceInfoList);
            }
            if (bundleInfoList != null && bundleInfoList.size() > 0) {
                info.setBundleInfo(bundleInfoList);
            }
            if (discountInfoList != null && discountInfoList.size() > 0) {
                info.setDiscountInfo(discountInfoList);
            }
            if (couponInfoList != null && couponInfoList.size() > 0) {
                info.setPromApplyFlag(couponInfoList.get(0).getPromApplyFlag());
                info.setCouponApplyFlag(couponInfoList.get(0).getCouponApplyFlag());
                info.setCouponName(couponInfoList.get(0).getCouponName());
            }
            if ((priceInfoList != null && priceInfoList.size() > 0) || (bundleInfoList != null
                                                                        && bundleInfoList.size() > 0) || (
                                discountInfoList != null && discountInfoList.size() > 0) || (couponInfoList != null
                                                                                             && couponInfoList.size()
                                                                                                > 0)) {
                infoList.add(info);
            }
            if (infoList.size() > 0) {
                result.setInfo(infoList);
            }

            resultInfo.setStatus(status);
            result.setResult(resultInfo);

        } catch (Exception ex) {

            apiInfo.setException(ex);
            Utility.logicException(apiInfo, result, resultInfo);

        } finally {
            // クローズ処理
            Utility.close(cs, conn, apiInfo);
        }

        // 処理結果をJSON形式に変換して返却
        return Utility.createResponse(log, result, jsonData, Level.INFO);
    }

    /**
     * 配送情報取得<br/>
     *
     * @param jsonData 取得条件
     * <ul>
     *   <li>注文者顧客番号:orderCustomerNo</li>
     *   <li>配送先顧客番号:deliveryCustomerNo</li>
     *   <li>配送先郵便番号:deliveryZipcode</li>
     *   <li>申込商品:goodsCode（パイプ区切り）</li>
     * </ul>
     * @return JSON形式の処理結果
     * <ul>
     *   <li>処理結果:result</li>
     *   <ul>
     *     <li>ステータス:status</li>
     *     <li>メッセージ:message</li>
     *   </ul>
     *   <li>【リスト】情報リスト:info</li>
     *   <ul>
     *     <li>お届け希望日、時間帯指定可否:deliveryAssignFlag</li>
     *     <li>配送会社コード:deliveryCompanyCode</li>
     *     <li>お届け可否:deliveryFlag</li>
     *     <li>お届け不可申込商品:nodeliveryGoodsCode</li>
     *     <li>共通最短お届け日:comEarlyReceiveDate</li>
     *     <li>【リスト】日付情報リスト:dateInfo</li>
     *     <ul>
     *       <li>お届け可能日:receiveDate</li>
     *       <li>時間帯コード:dispTimeZoneCode</li>
     *     </ul>
     *   </ul>
     * </ul>
     */
    @POST
    @Path("/GetDeliveryInformation")
    @Produces({"application/json"})
    @Consumes({"application/json"})
    public Response getDeliveryInformation(String jsonData) {

        Logger log = LogManager.getLogger("getDeliveryInformation");

        // 返却情報
        Result result = new Result();
        ResultInfo resultInfo = new ResultInfo();
        ApiInfo apiInfo = new ApiInfo("getDeliveryInformation", "配送情報取得[Order/GetDeliveryInformation]", context, log);

        Connection conn = null;
        CallableStatement cs = null;

        try {

            // INパラメータをログに出力する
            log.info("[IN]" + jsonData);

            // POSTされたJSONをデコードし、専用のオブジェクトに変換する
            GetDeliveryInformationRequest req = JSON.decode(jsonData, GetDeliveryInformationRequest.class);

            // エラーキー情報をセット(エラーメールは注文者顧客番号のみ)
            apiInfo.setErrorKeyInfoLog(jsonData);
            apiInfo.setErrorKeyInfoMail("注文者顧客番号=" + req.getOrderCustomerNo());

            // JDBC接続
            String driverStr = PropManeger.getConf(apiInfo.getContext(), "db.jdbc.driver");
            Driver driver = (Driver) Class.forName(driverStr).newInstance();
            String connUrl = PropManeger.getConf(apiInfo.getContext(), "db.jdbc.url");
            conn = driver.connect(connUrl, new Properties());

            // パラメータをセットし、ストアドを呼出す
            cs = conn.prepareCall("{call ProcGetDeliveryInformation(?,?,?,?)}");
            // 注文者顧客番号
            if (req.getOrderCustomerNo() == null) {
                cs.setNull(1, java.sql.Types.INTEGER);
            } else {
                cs.setInt(1, Integer.parseInt(req.getOrderCustomerNo()));
            }
            // 配送先顧客番号
            if (req.getDeliveryCustomerNo() == null) {
                cs.setNull(2, java.sql.Types.INTEGER);
            } else {
                cs.setInt(2, Integer.parseInt(req.getDeliveryCustomerNo()));
            }
            // 配送先郵便番号
            cs.setString(3, req.getDeliveryZipcode());
            // 申込商品
            cs.setString(4, req.getGoodsCode());
            // 取得結果（リターンコード）
            // cs.registerOutParameter(5, java.sql.Types.SMALLINT);
            cs.executeQuery();
            Integer status = 0;

            // 処理結果を取得してしまうとResultSetがクリアされてしまう為
            // 前もって取得しておく必要がある
            ResultSet rs = cs.getResultSet();
            List<Object> infoList = new ArrayList<>();
            GetDeliveryInformationResponse info = new GetDeliveryInformationResponse();
            List<GetDeliveryInformationDateResponse> dateList = new ArrayList<>();
            GetDeliveryInformationDateResponse dateInfo;

            boolean isSetHeader = false;

            while (rs != null && rs.next()) {

                // １件目の場合は固定部分を生成
                if (!isSetHeader) {
                    // お届け希望日、時間帯指定可否
                    info.setDeliveryAssignFlag(rs.getString(1));
                    // 配送会社コード
                    info.setDeliveryCompanyCode(rs.getString(2));
                    // お届け可否
                    info.setDeliveryFlag(rs.getString(3));
                    // お届け不可申込商品
                    info.setNodeliveryGoodsCode(rs.getString(4));
                    // 2023-renew No14 from here
                    // 共通最短お届け日（1件目のお届け日を設定）
                    info.setComEarlyReceiveDate(rs.getTimestamp(5));
                    // 2023-renew No14 to here
                    isSetHeader = true;
                }

                // ここから商品リスト部分
                if (rs.getTimestamp(5) != null) {
                    dateInfo = new GetDeliveryInformationDateResponse();
                    // お届け可能日
                    dateInfo.setReceiveDate(rs.getTimestamp(5));
                    // 時間帯コード
                    dateInfo.setDispTimeZoneCode(rs.getString(6));
                    dateList.add(dateInfo);
                }
                // ステータス
                status = rs.getInt(7);
            }

            // 日付情報リストが存在する場合は、設定
            if (dateList != null && !dateList.isEmpty()) {
                info.setDateInfo(dateList);
            }
            if (info != null) {
                infoList.add(info);
            }

            // ストアドからの返却値
            apiInfo.setStoredName("ProcGetDeliveryInformation");
            apiInfo.setStoredCode(status);
            Utility.makeResult(apiInfo, result, resultInfo, infoList);

        } catch (Exception ex) {

            apiInfo.setException(ex);
            Utility.logicException(apiInfo, result, resultInfo);

        } finally {

            // クローズ処理
            Utility.close(cs, conn, apiInfo);
        }

        // 処理結果をJSON形式に変換して返却
        return Utility.createResponse(log, result, jsonData, Level.INFO);
    }

    /**
     * 配送会社取得<br/>
     *
     * @param jsonData 取得条件
     * <ul>
     *   <li>申込商品:goodsCode</li>
     * </ul>
     * @return JSON形式の処理結果
     * <ul>
     *   <li>処理結果:result</li>
     *   <ul>
     *     <li>ステータス:status</li>
     *     <li>メッセージ:message</li>
     *   </ul>
     *   <li>情報:info</li>
     *   <ul>
     *     <li>配送会社コード:deliveryCompanyCode</li>
     *   </ul>
     * </ul>
     */
    @POST
    @Path("/GetDeliveryCompany")
    @Produces({"application/json"})
    @Consumes({"application/json"})
    public Response getDeliveryCompany(String jsonData) {

        Logger log = LogManager.getLogger("getDeliveryCompany");

        // 返却情報
        Result result = new Result();
        ResultInfo resultInfo = new ResultInfo();
        ApiInfo apiInfo = new ApiInfo("getDeliveryCompany", "配送会社取得[Order/GetDeliveryCompany]", context, log);

        Connection conn = null;
        CallableStatement cs = null;

        try {

            // INパラメータをログに出力する
            log.info("[IN]" + jsonData);

            // POSTされたJSONをデコードし、専用のオブジェクトに変換する
            GetDeliveryCompanyRequest req = JSON.decode(jsonData, GetDeliveryCompanyRequest.class);

            // エラーキー情報を共通セット
            Utility.setErrorKeyInfo(apiInfo, jsonData);

            // JDBC接続
            String driverStr = PropManeger.getConf(apiInfo.getContext(), "db.jdbc.driver");
            Driver driver = (Driver) Class.forName(driverStr).newInstance();
            String connUrl = PropManeger.getConf(apiInfo.getContext(), "db.jdbc.url");
            conn = driver.connect(connUrl, new Properties());

            // パラメータをセットし、ストアドを呼出す
            cs = conn.prepareCall("{call ProcGetDeliveryCompany(?)}");
            // 申込商品
            cs.setString(1, req.getGoodsCode());
            // 取得結果（リターンコード）
            // cs.registerOutParameter(2, java.sql.Types.SMALLINT);
            // ストアド実行
            cs.executeQuery();
            Integer status = 0;

            // 処理結果を取得してしまうとResultSetがクリアされてしまう為
            // 前もって取得しておく必要がある
            ResultSet rs = cs.getResultSet();
            List<Object> infoList = new ArrayList<>();
            GetDeliveryCompanyResponse info;
            while (rs != null && rs.next()) {
                info = new GetDeliveryCompanyResponse();
                // 配送会社コード
                info.setDeliveryCompanyCode(rs.getString(1));
                // ステータス
                status = rs.getInt(2);

                infoList.add(info);
            }

            // ストアドからの返却値
            apiInfo.setStoredName("ProcGetDeliveryCompany");
            apiInfo.setStoredCode(status);
            Utility.makeResult(apiInfo, result, resultInfo, infoList);

        } catch (Exception ex) {

            apiInfo.setException(ex);
            Utility.logicException(apiInfo, result, resultInfo);

        } finally {

            // クローズ処理
            Utility.close(cs, conn, apiInfo);
        }

        // 処理結果をJSON形式に変換して返却
        return Utility.createResponse(log, result, jsonData, Level.INFO);
    }

    /**
     * 配送可否取得<br/>
     *
     * @param jsonData 取得条件
     * <ul>
     *   <li>注文者顧客番号:orderCustomerNo</li>
     *   <li>配送先顧客番号:deliveryCustomerNo</li>
     *   <li>申込商品:goodsCode（パイプ区切り）</li>
     * </ul>
     * @return JSON形式の処理結果
     * <ul>
     *   <li>処理結果:result</li>
     *   <ul>
     *     <li>ステータス:status</li>
     *     <li>メッセージ:message</li>
     *   </ul>
     *   <li>情報:info</li>
     *   <ul>
     *     <li>お届け可否:deliveryVoteFlag</li>
     *   </ul>
     * </ul>
     */
    @POST
    @Path("/GetShippingPropriety")
    @Produces({"application/json"})
    @Consumes({"application/json"})
    public Response getShippingPropriety(String jsonData) {

        Logger log = LogManager.getLogger("getShippingPropriety");

        // 返却情報
        Result result = new Result();
        ResultInfo resultInfo = new ResultInfo();
        ApiInfo apiInfo = new ApiInfo("getShippingPropriety", "配送可否取得[Order/GetShippingPropriety]", context, log);

        Connection conn = null;
        CallableStatement cs = null;

        try {

            // INパラメータをログに出力する
            log.info("[IN]" + jsonData);

            // POSTされたJSONをデコードし、専用のオブジェクトに変換する
            GetShippingProprietyRequest req = JSON.decode(jsonData, GetShippingProprietyRequest.class);

            // エラーキー情報を共通セット
            Utility.setErrorKeyInfo(apiInfo, jsonData);

            // JDBC接続
            String driverStr = PropManeger.getConf(apiInfo.getContext(), "db.jdbc.driver");
            Driver driver = (Driver) Class.forName(driverStr).newInstance();
            String connUrl = PropManeger.getConf(apiInfo.getContext(), "db.jdbc.url");
            conn = driver.connect(connUrl, new Properties());

            // パラメータをセットし、ストアドを呼出す
            cs = conn.prepareCall("{call ProcGetShippingPropriety(?,?,?)}");
            // 注文者顧客番号
            cs.setString(1, req.getOrderCustomerNo());
            // 配送先顧客番号
            cs.setString(2, req.getDeliveryCustomerNo());
            // 申込商品
            cs.setString(3, req.getGoodsCode());
            // 取得結果（リターンコード）
            // cs.registerOutParameter(4, java.sql.Types.SMALLINT);
            // ストアド実行
            cs.executeQuery();
            Integer status = 0;

            // 処理結果を取得してしまうとResultSetがクリアされてしまう為
            // 前もって取得しておく必要がある
            ResultSet rs = cs.getResultSet();
            List<Object> infoList = new ArrayList<>();
            GetShippingProprietyResponse info;
            while (rs != null && rs.next()) {
                info = new GetShippingProprietyResponse();
                // お届け可否
                info.setDeliveryVoteFlag(rs.getString(1));

                infoList.add(info);
                // ステータス
                status = rs.getInt(2);
            }

            // ストアドからの返却値
            apiInfo.setStoredName("ProcGetShippingPropriety");
            apiInfo.setStoredCode(status);
            Utility.makeResult(apiInfo, result, resultInfo, infoList);

        } catch (Exception ex) {

            apiInfo.setException(ex);
            Utility.logicException(apiInfo, result, resultInfo);

        } finally {

            // クローズ処理
            Utility.close(cs, conn, apiInfo);
        }

        // 処理結果をJSON形式に変換して返却
        return Utility.createResponse(log, result, jsonData, Level.INFO);
    }

    /**
     * 注文保留チェック<br/>
     *
     * @param jsonData 取得条件
     * <ul>
     *   <li>顧客番号:customerNo</li>
     *   <li>支払方法:paymentType</li>
     *   <li>注文金額合計:orderTotalPrice</li>
     * </ul>
     * @return JSON形式の処理結果
     * <ul>
     *   <li>処理結果:result</li>
     *   <ul>
     *     <li>ステータス:status</li>
     *     <li>メッセージ:message</li>
     *   </ul>
     *   <li>情報:info</li>
     *   <ul>
     *     <li>判定結果:checkResult</li>
     *     <li>保留区分:holdType</li>
     *   </ul>
     * </ul>
     */
    @POST
    @Path("/OrderPendingCheck")
    @Produces({"application/json"})
    @Consumes({"application/json"})
    public Response orderPendingCheck(String jsonData) {

        Logger log = LogManager.getLogger("orderPendingCheck");

        // 返却情報
        Result result = new Result();
        ResultInfo resultInfo = new ResultInfo();
        ApiInfo apiInfo = new ApiInfo("orderPendingCheck", "注文保留チェック[Order/OrderPendingCheck]", context, log);

        Connection conn = null;
        CallableStatement cs = null;

        try {

            // INパラメータをログに出力する
            log.info("[IN]" + jsonData);

            // POSTされたJSONをデコードし、専用のオブジェクトに変換する
            OrderPendingCheckRequest req = JSON.decode(jsonData, OrderPendingCheckRequest.class);

            // エラーキー情報を共通セット
            Utility.setErrorKeyInfo(apiInfo, jsonData);

            // JDBC接続
            String driverStr = PropManeger.getConf(apiInfo.getContext(), "db.jdbc.driver");
            Driver driver = (Driver) Class.forName(driverStr).newInstance();
            String connUrl = PropManeger.getConf(apiInfo.getContext(), "db.jdbc.url");
            conn = driver.connect(connUrl, new Properties());

            // パラメータをセットし、ストアドを呼出す
            cs = conn.prepareCall("{call ProcOrderPendingCheck(?,?,?)}");
            // 顧客番号
            if (req.getCustomerNo() == null) {
                cs.setNull(1, java.sql.Types.INTEGER);
            } else {
                cs.setInt(1, Integer.parseInt(req.getCustomerNo()));
            }
            // 支払方法
            if (req.getPaymentType() == null) {
                cs.setNull(2, java.sql.Types.INTEGER);
            } else {
                cs.setInt(2, Integer.parseInt(req.getPaymentType()));
            }
            // 注文金額合計
            cs.setBigDecimal(3, req.getOrderTotalPrice());
            // 取得結果（リターンコード）
            // cs.registerOutParameter(4, java.sql.Types.SMALLINT);
            cs.executeQuery();
            Integer status = 0;

            // 処理結果を取得してしまうとResultSetがクリアされてしまう為
            // 前もって取得しておく必要がある
            ResultSet rs = cs.getResultSet();
            List<Object> infoList = new ArrayList<Object>();
            OrderPendingCheckResponse info;
            while (rs != null && rs.next()) {
                info = new OrderPendingCheckResponse();
                // 判定結果
                info.setCheckResult(rs.getString(1));
                // 保留区分
                info.setHoldType(rs.getString(2));

                infoList.add(info);
                // ステータス
                status = rs.getInt(3);
            }

            // ストアドからの返却値
            apiInfo.setStoredName("ProcOrderPendingCheck");
            apiInfo.setStoredCode(status);
            Utility.makeResult(apiInfo, result, resultInfo, infoList);

        } catch (Exception ex) {

            apiInfo.setException(ex);
            Utility.logicException(apiInfo, result, resultInfo);

        } finally {

            // クローズ処理
            Utility.close(cs, conn, apiInfo);
        }

        // 処理結果をJSON形式に変換して返却
        return Utility.createResponse(log, result, jsonData, Level.INFO);
    }

    /**
     * 消費税率取得<br/>
     *
     * @param jsonData 取得条件
     * <ul>
     *   <li>申込商品:goodsCode</li>
     * </ul>
     * @return JSON形式の処理結果
     * <ul>
     *   <li>処理結果:result</li>
     *   <ul>
     *     <li>ステータス:status</li>
     *     <li>メッセージ:message</li>
     *   </ul>
     *   <li>【リスト】情報リスト:info</li>
     *   <ul>
     *     <li>商品コード:goodsCode</li>
     *     <li>消費税率:taxRate</li>
     *   </ul>
     * </ul>
     */
    @POST
    @Path("/GetConsumptionTaxRate")
    @Produces({"application/json"})
    @Consumes({"application/json"})
    public Response getConsumptionTaxRate(String jsonData) {

        Logger log = LogManager.getLogger("getConsumptionTaxRate");

        // 返却情報
        Result result = new Result();
        ResultInfo resultInfo = new ResultInfo();
        ApiInfo apiInfo = new ApiInfo("getConsumptionTaxRate", "消費税率取得[Order/GetConsumptionTaxRate]", context, log);

        Connection conn = null;
        CallableStatement cs = null;

        try {
            // INパラメータをログに出力する
            log.info("[IN]" + jsonData);

            // POSTされたJSONをデコードし、専用のオブジェクトに変換する
            GetConsumptionTaxRateRequest req = JSON.decode(jsonData, GetConsumptionTaxRateRequest.class);

            // エラーキー情報を共通セット
            Utility.setErrorKeyInfo(apiInfo, jsonData);

            // JDBC接続
            String driverStr = PropManeger.getConf(apiInfo.getContext(), "db.jdbc.driver");
            Driver driver = (Driver) Class.forName(driverStr).newInstance();
            String connUrl = PropManeger.getConf(apiInfo.getContext(), "db.jdbc.url");
            conn = driver.connect(connUrl, new Properties());

            // パラメータをセットし、ストアドを呼出す
            cs = conn.prepareCall("{call ProcGetConsumptionTaxRate(?)}");
            // 商品コード
            cs.setString(1, req.getGoodsCode());
            // 取得結果（リターンコード）
            // cs.registerOutParameter(2, java.sql.Types.SMALLINT);
            cs.executeQuery();
            Integer status = 0;

            // 処理結果を取得してしまうとResultSetがクリアされてしまう為
            // 前もって取得しておく必要がある
            ResultSet rs = cs.getResultSet();
            List<Object> infoList = new ArrayList<Object>();
            GetConsumptionTaxRateResponse info;
            while (rs != null && rs.next()) {
                info = new GetConsumptionTaxRateResponse();
                // 商品コード
                info.setGoodsCode(rs.getString(1));
                // 消費税率
                info.setTaxRate(rs.getBigDecimal(2));
                // ステータス
                status = rs.getInt(3);

                infoList.add(info);
            }

            // ストアドからの返却値
            apiInfo.setStoredName("ProcGetConsumptionTaxRate");
            apiInfo.setStoredCode(status);
            Utility.makeResult(apiInfo, result, resultInfo, infoList);

        } catch (Exception ex) {

            apiInfo.setException(ex);
            Utility.logicException(apiInfo, result, resultInfo);

        } finally {

            // クローズ処理
            Utility.close(cs, conn, apiInfo);
        }

        // 処理結果をJSON形式に変換して返却
        return Utility.createResponse(log, result, jsonData, Level.INFO);
    }

    /**
     * 数量割引適用結果取得<br/>
     *
     * @param jsonData 取得条件
     * <ul>
     *   <li>顧客番号:customerNo</li>
     *   <li>申込商品:goodsCode</li>
     *   <li>数量:quantity</li>
     * </ul>
     * @return JSON形式の処理結果
     * <ul>
     *   <li>処理結果:result</li>
     *   <ul>
     *     <li>ステータス:status</li>
     *     <li>メッセージ:message</li>
     *   </ul>
     *   <li>【リスト】情報リスト:info</li>
     *   <ul>
     *     <li>申込商品:goodsCode</li>
     *     <li>適用割引:saleType</li>
     *     <li>数量割引グループコード:saleGroupCode</li>
     *     <li>割引価格:salePrice</li>
     *     <li>数量:quantity</li>
     *     <li>セールコード:saleCode</li>
     *     <li>備考:note</li>
     *     <li>注意事項:hints</li>
     *   </ul>
     * </ul>
     */
    @POST
    @Path("/GetQuantityDiscountsResult")
    @Produces({"application/json"})
    @Consumes({"application/json"})
    public Response getQuantityDiscountsResult(String jsonData) {

        Logger log = LogManager.getLogger("getQuantityDiscountsResult");

        // 返却情報
        Result result = new Result();
        ResultInfo resultInfo = new ResultInfo();
        ApiInfo apiInfo = new ApiInfo("getQuantityDiscountsResult", "数量割引適用結果取得[Order/GetQuantityDiscountsResult]",
                                      context, log
        );

        Connection conn = null;
        CallableStatement cs = null;

        try {

            log.info("[IN]" + jsonData);

            // POSTされたJSONをデコードし、専用のオブジェクトに変換する
            GetQuantityDiscountsResultRequest req = JSON.decode(jsonData, GetQuantityDiscountsResultRequest.class);

            // エラーキー情報を共通セット
            Utility.setErrorKeyInfo(apiInfo, jsonData);

            // JDBC接続
            String driverStr = PropManeger.getConf(apiInfo.getContext(), "db.jdbc.driver");
            Driver driver = (Driver) Class.forName(driverStr).newInstance();
            String connUrl = PropManeger.getConf(apiInfo.getContext(), "db.jdbc.url");
            conn = driver.connect(connUrl, new Properties());

            // パラメータをセットし、ストアドを呼出す
            cs = conn.prepareCall("{call ProcGetQuantityDiscountsResult(?,?,?)}");
            // 顧客番号
            if (req.getCustomerNo() == null) {
                cs.setNull(1, java.sql.Types.INTEGER);
            } else {
                cs.setInt(1, Integer.parseInt(req.getCustomerNo()));
            }
            // 申込商品
            cs.setString(2, req.getGoodsCode());
            // 数量
            cs.setString(3, req.getQuantity());
            // 取得結果（リターンコード）
            // cs.registerOutParameter(4, java.sql.Types.SMALLINT);
            cs.executeQuery();
            Integer status = 0;

            // 処理結果を取得してしまうとResultSetがクリアされてしまう為
            // 前もって取得しておく必要がある
            ResultSet rs = cs.getResultSet();
            List<Object> infoList = new ArrayList<>();
            GetQuantityDiscountsResultResponse info;
            while (rs != null && rs.next()) {
                info = new GetQuantityDiscountsResultResponse();
                // 申込商品
                info.setGoodsCode(rs.getString(1));
                // 適用割引
                info.setSaleType(rs.getString(2));
                // 数量割引グループコード
                info.setSaleGroupCode(rs.getString(3));
                // 割引価格
                info.setSalePrice(rs.getString(4));
                // 数量
                info.setQuantity(rs.getString(5));
                // セールコード
                info.setSaleCode(rs.getString(6));
                // 備考
                info.setNote(rs.getString(7));
                // 注意事項
                info.setHints(rs.getString(8));
                // 価格
                info.setPrice(rs.getString(9));
                infoList.add(info);
                // ステータス
                status = rs.getInt(10);
            }

            // ストアドからの返却値
            apiInfo.setStoredName("ProcGetQuantityDiscountsResult");
            apiInfo.setStoredCode(status);
            Utility.makeResult(apiInfo, result, resultInfo, infoList);

        } catch (Exception ex) {

            apiInfo.setException(ex);
            Utility.logicException(apiInfo, result, resultInfo);

        } finally {

            // クローズ処理
            Utility.close(cs, conn, apiInfo);
        }

        // 処理結果をJSON形式に変換して返却
        return Utility.createResponse(log, result, jsonData, Level.INFO);
    }

    /**
     * クーポン有効性チェック<br/>
     *
     * @param jsonData 取得条件
     * <ul>
     *   <li>クーポンコード:couponCode</li>
     * </ul>
     * @return JSON形式の処理結果
     * <ul>
     *   <li>処理結果:result</li>
     *   <ul>
     *     <li>ステータス:status</li>
     *     <li>メッセージ:message</li>
     *   </ul>
     *   <li>【リスト】情報:info</li>
     *   <ul>
     *     <li>クーポン名称:couponName</li>
     *   </ul>
     * </ul>
     */
    @POST
    @Path("/CouponCheck")
    @Produces({"application/json"})
    @Consumes({"application/json"})
    public Response couponCheck(String jsonData) {

        Logger log = LogManager.getLogger("couponCheck");

        // 返却情報
        Result result = new Result();
        ResultInfo resultInfo = new ResultInfo();
        ApiInfo apiInfo = new ApiInfo("couponCheck", "クーポン有効性チェック[Order/CouponCheck]", context, log);

        Connection conn = null;
        CallableStatement cs = null;

        try {

            log.info("[IN]" + jsonData);

            // POSTされたJSONをデコードし、専用のオブジェクトに変換する
            CouponCheckRequest req = JSON.decode(jsonData, CouponCheckRequest.class);

            // エラーキー情報を共通セット
            Utility.setErrorKeyInfo(apiInfo, jsonData);

            // JDBC接続
            String driverStr = PropManeger.getConf(apiInfo.getContext(), "db.jdbc.driver");
            Driver driver = (Driver) Class.forName(driverStr).newInstance();
            String connUrl = PropManeger.getConf(apiInfo.getContext(), "db.jdbc.url");
            conn = driver.connect(connUrl, new Properties());

            // パラメータをセットし、ストアドを呼出す
            cs = conn.prepareCall("{call ProcCouponCheck(?)}");

            // クーポンコード
            cs.setString(1, req.getCouponCode());

            // 取得結果（リターンコード）
            // cs.registerOutParameter(2, java.sql.Types.SMALLINT);
            log.info("ProcCouponCheck呼出（開始）：" + new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()));
            cs.executeQuery();
            log.info("ProcCouponCheck呼出（終了）：" + new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()));
            Integer status = 0;
            // 処理結果を取得してしまうとResultSetがクリアされてしまう為
            // 前もって取得しておく必要がある
            ResultSet rs = cs.getResultSet();
            List<Object> infoList = new ArrayList<>();
            CouponCheckResponse info;
            while (rs != null && rs.next()) {
                info = new CouponCheckResponse();
                // クーポン名称
                info.setCouponName(rs.getString(1));
                // ステータス
                status = rs.getInt(2);
                infoList.add(info);
            }

            if (infoList.size() == 0) {
                status = 1;
            }

            // ストアドからの返却値
            apiInfo.setStoredName("ProcCouponCheck");
            apiInfo.setStoredCode(status);
            Utility.makeResult(apiInfo, result, resultInfo, infoList);

        } catch (Exception ex) {

            apiInfo.setException(ex);
            Utility.logicException(apiInfo, result, resultInfo);

        } finally {

            // クローズ処理
            Utility.close(cs, conn, apiInfo);
        }

        // 処理結果をJSON形式に変換して返却
        return Utility.createResponse(log, result, jsonData, Level.INFO);
    }

    // 2023-renew No24 from here

    /**
     * クーポン取得
     *
     * @param jsonData 取得条件
     * <ul>
     *   <li>顧客番号:customerNo</li>
     *   <li>クーポン番号:couponNo</li>
     * </ul>
     * @return JSON形式の処理結果
     * <ul>
     *   <li>処理結果:result</li>
     *   <ul>
     *     <li>ステータス:status</li>
     *     <li>メッセージ:message</li>
     *   </ul>
     *   <li>【リスト】情報:info</li>
     *   <ul>
     *     <li>クーポン名:couponName</li>
     *   </ul>
     * </ul>
     */
    @POST
    @Path("/AddCoupon")
    @Produces({"application/json"})
    @Consumes({"application/json"})
    public Response addCoupon(String jsonData) {

        Logger log = LogManager.getLogger("addCoupon");

        // 返却情報
        Result result = new Result();
        ResultInfo resultInfo = new ResultInfo();
        ApiInfo apiInfo = new ApiInfo("addCoupon", "クーポン取得[Order/AddCoupon]", context, log);

        Connection conn = null;
        CallableStatement cs = null;

        try {

            // INパラメータをログに出力する
            log.info("[IN]" + jsonData);

            // POSTされたJSONをデコードし、専用のオブジェクトに変換する
            AddCouponRequest req = JSON.decode(jsonData, AddCouponRequest.class);

            // エラーキー情報を共通セット
            Utility.setErrorKeyInfo(apiInfo, jsonData);

            // JDBC接続
            String driverStr = PropManeger.getConf(apiInfo.getContext(), "db.jdbc.driver");
            Driver driver = (Driver) Class.forName(driverStr).newInstance();
            String connUrl = PropManeger.getConf(apiInfo.getContext(), "db.jdbc.url");
            conn = driver.connect(connUrl, new Properties());

            // パラメータをセットし、ストアドを呼出す
            cs = conn.prepareCall("{call ProcAddCoupon(?, ?)}");

            // 顧客番号
            cs.setInt(1, req.getCustomerNo());
            // クーポン番号
            cs.setString(2, req.getCouponNo());

            // 登録結果（リターンコード）
            log.info("ProcAddCoupon呼出（開始）：" + new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()));
            cs.executeQuery();
            log.info("ProcAddCoupon呼出（終了）：" + new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()));
            Integer status = 0;
            // 処理結果を取得してしまうとResultSetがクリアされてしまう為
            // 前もって取得しておく必要がある
            ResultSet rs = cs.getResultSet();
            List<Object> infoList = new ArrayList<>();
            AddCouponResponse couponResponse;
            while (rs != null && rs.next()) {
                couponResponse = new AddCouponResponse();
                // クーポン名
                couponResponse.setCouponName(rs.getString(1));
                // ステータス
                status = rs.getInt(2);
                infoList.add(couponResponse);
            }

            if (infoList.size() == 0) {
                status = 1;
            }

            // ストアドからの返却値
            apiInfo.setStoredName("ProcAddCoupon");
            apiInfo.setStoredCode(status);
            Utility.makeResult(apiInfo, result, resultInfo, infoList);

        } catch (Exception ex) {

            apiInfo.setException(ex);
            Utility.logicException(apiInfo, result, resultInfo);

        } finally {

            // クローズ処理
            Utility.close(cs, conn, apiInfo);
        }

        // 処理結果をJSON形式に変換して返却
        return Utility.createResponse(log, result, jsonData, Level.INFO);
    }

    /**
     * 利用可能クーポン一覧取得
     *
     * @param jsonData 取得条件
     * <ul>
     *   <li>顧客番号:customerNo</li>
     * </ul>
     * @return JSON形式の処理結果
     * <ul>
     *   <li>処理結果:result</li>
     *   <ul>
     *     <li>ステータス:status</li>
     *     <li>メッセージ:message</li>
     *   </ul>
     *   <li>【リスト】情報:info</li>
     *   <ul>
     *     <li>クーポン番号:couponNo</li>
     *     <li>クーポン名:couponName</li>
     *     <li>適用条件:couponConditions</li>
     *     <li>詳細説明:couponExplain</li>
     *   </ul>
     * </ul>
     */
    @POST
    @Path("/GetCouponList")
    @Produces({"application/json"})
    @Consumes({"application/json"})
    public Response getCouponList(String jsonData) {

        Logger log = LogManager.getLogger("getCouponList");

        // 返却情報
        Result result = new Result();
        ResultInfo resultInfo = new ResultInfo();
        ApiInfo apiInfo = new ApiInfo("getCouponList", "利用可能クーポン一覧取得[Order/GetCouponList]", context, log);

        Connection conn = null;
        CallableStatement cs = null;

        try {

            // INパラメータをログに出力する
            log.info("[IN]" + jsonData);

            // POSTされたJSONをデコードし、専用のオブジェクトに変換する
            GetCouponListRequest req = JSON.decode(jsonData, GetCouponListRequest.class);

            // エラーキー情報を共通セット
            Utility.setErrorKeyInfo(apiInfo, jsonData);

            // JDBC接続
            String driverStr = PropManeger.getConf(apiInfo.getContext(), "db.jdbc.driver");
            Driver driver = (Driver) Class.forName(driverStr).newInstance();
            String connUrl = PropManeger.getConf(apiInfo.getContext(), "db.jdbc.url");
            conn = driver.connect(connUrl, new Properties());

            // パラメータをセットし、ストアドを呼出す
            cs = conn.prepareCall("{call ProcGetCouponList(?)}");

            // 顧客番号
            cs.setInt(1, req.getCustomerNo());

            // 取得結果（リターンコード）
            log.info("ProcGetCouponList呼出（開始）：" + new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()));
            cs.executeQuery();
            log.info("ProcGetCouponList呼出（終了）：" + new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()));
            Integer status = 0;
            // 処理結果を取得してしまうとResultSetがクリアされてしまう為
            // 前もって取得しておく必要がある
            ResultSet rs = cs.getResultSet();
            List<Object> infoList = new ArrayList<>();
            GetCouponListResponse getCouponListResponse;
            while (rs != null && rs.next()) {
                getCouponListResponse = new GetCouponListResponse();
                // クーポン番号
                getCouponListResponse.setCouponNo(rs.getString(1));
                // クーポン名
                getCouponListResponse.setCouponName(rs.getString(2));
                // 適用条件
                getCouponListResponse.setCouponConditions(rs.getString(3));
                // 詳細説明
                getCouponListResponse.setCouponExplain(rs.getString(4));
                // ステータス
                status = rs.getInt(5);
                infoList.add(getCouponListResponse);
            }

            // ストアドからの返却値
            apiInfo.setStoredName("ProcGetCouponList");
            apiInfo.setStoredCode(status);
            Utility.makeResult(apiInfo, result, resultInfo, infoList);

        } catch (Exception ex) {

            apiInfo.setException(ex);
            Utility.logicException(apiInfo, result, resultInfo);

        } finally {

            // クローズ処理
            Utility.close(cs, conn, apiInfo);
        }

        // 処理結果をJSON形式に変換して返却
        return Utility.createResponse(log, result, jsonData, Level.INFO);
    }

    // 2023-renew No24 to here

    // 2023-renew No14 from here

    /**
     * 前回支払方法取得<br/>
     *
     * @param jsonData 取得条件
     * <ul>
     *   <li>顧客番号:customerNo</li>
     * </ul>
     * @return JSON形式の処理結果
     * <ul>
     *   <li>処理結果:result</li>
     *   <ul>
     *     <li>ステータス:status</li>
     *     <li>メッセージ:message</li>
     *   </ul>
     *   <li>【リスト】情報リスト:info</li>
     *   <ul>
     *     <li>前回支払方法:beforePaymentType</li>
     *     <li>決済ID:paymentId</li>
     *   </ul>
     * </ul>
     */
    @POST
    @Path("/GetBeforePayment")
    @Produces({"application/json"})
    @Consumes({"application/json"})
    public Response getBeforePayment(String jsonData) {

        Logger log = LogManager.getLogger("getBeforePayment");

        // 返却情報
        Result result = new Result();
        ResultInfo resultInfo = new ResultInfo();
        ApiInfo apiInfo = new ApiInfo("getBeforePayment", "前回支払方法取得[Order/GetBeforePayment]", context, log);

        Connection conn = null;
        CallableStatement cs = null;

        try {

            // INパラメータをログに出力する
            log.info("[IN]" + jsonData);

            // POSTされたJSONをデコードし、専用のオブジェクトに変換する
            GetBeforePaymentRequest req = JSON.decode(jsonData, GetBeforePaymentRequest.class);

            // エラーキー情報を共通セット
            Utility.setErrorKeyInfo(apiInfo, jsonData);

            // JDBC接続
            String driverStr = PropManeger.getConf(apiInfo.getContext(), "db.jdbc.driver");
            Driver driver = (Driver) Class.forName(driverStr).newInstance();
            String connUrl = PropManeger.getConf(apiInfo.getContext(), "db.jdbc.url");
            conn = driver.connect(connUrl, new Properties());

            // パラメータをセットし、ストアドを呼出す
            cs = conn.prepareCall("{call ProcGetBeforePayment(?)}");

            // 顧客番号
            cs.setInt(1, req.getCustomerNo());

            // 取得結果（リターンコード）
            log.info("ProcGetBeforePayment呼出（開始）：" + new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()));
            cs.executeQuery();
            log.info("ProcGetBeforePayment呼出（終了）：" + new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()));
            Integer status = 0;
            // 処理結果を取得してしまうとResultSetがクリアされてしまう為
            // 前もって取得しておく必要がある
            ResultSet rs = cs.getResultSet();
            List<Object> infoList = new ArrayList<>();
            GetBeforePaymentResponse getBeforePaymentResponse;
            while (rs != null && rs.next()) {
                getBeforePaymentResponse = new GetBeforePaymentResponse();
                // 前回支払方法
                getBeforePaymentResponse.setBeforePaymentType(rs.getString(1));
                // 決済ID
                getBeforePaymentResponse.setPaymentId(rs.getString(2));
                // ステータス
                status = rs.getInt(3);
                infoList.add(getBeforePaymentResponse);
            }

            // ストアドからの返却値
            apiInfo.setStoredName("ProcGetBeforePayment");
            apiInfo.setStoredCode(status);
            Utility.makeResult(apiInfo, result, resultInfo, infoList);

        } catch (Exception ex) {

            apiInfo.setException(ex);
            Utility.logicException(apiInfo, result, resultInfo);

        } finally {

            // クローズ処理
            Utility.close(cs, conn, apiInfo);
        }

        // 処理結果をJSON形式に変換して返却
        return Utility.createResponse(log, result, jsonData, Level.INFO);
    }

    /**
     * 出荷予定日取得<br/>
     *
     * @param jsonData 取得条件
     * <ul>
     *   <li>注文者顧客番号:orderCustomerNo</li>
     *   <li>配送先顧客番号:deliveryCustomerNo</li>
     *   <li>配送先郵便番号:deliveryZipcode</li>
     *   <li>配送会社コード:deliveryCompanyCode</li>
     *   <li>【リスト】情報リスト:info</li>
     *   <ul>
     *     <li>申込商品:goodsCode</li>
     *     <li>配達指定日:deliveryDesignatedDay</li>
     *     <li>配達指定時間:deliveryDesignatedTimeCode</li>
     *   </ul>
     * </ul>
     * @return JSON形式の処理結果
     * <ul>
     *   <li>処理結果:result</li>
     *   <ul>
     *     <li>ステータス:status</li>
     *     <li>メッセージ:message</li>
     *   </ul>
     *   <li>【リスト】情報リスト:info</li>
     *   <ul>
     *     <li>申込商品:goodsCode</li>
     *     <li>配達指定日:deliveryDesignatedDay</li>
     *     <li>出荷予定日:shipmentDate</li>
     *   </ul>
     * </ul>
     */
    @POST
    @Path("/GetShipmentDate")
    @Produces({"application/json"})
    @Consumes({"application/json"})
    public Response getShipmentDate(String jsonData) {

        Logger log = LogManager.getLogger("getShipmentDate");

        // 返却情報
        Result result = new Result();
        ResultInfo resultInfo = new ResultInfo();
        ApiInfo apiInfo = new ApiInfo("getShipmentDate", "出荷予定日取得[Order/GetShipmentDate]", context, log);

        Connection conn = null;
        CallableStatement cs = null;
        CallableStatement cs2 = null;

        try {

            // INパラメータをログに出力する
            log.info("[IN]" + jsonData);

            // POSTされたJSONをデコードし、専用のオブジェクトに変換する
            GetShipmentDateRequest req = JSON.decode(jsonData, GetShipmentDateRequest.class);

            // エラーキー情報を共通セット
            Utility.setErrorKeyInfo(apiInfo, jsonData);

            // JDBC接続
            String driverStr = PropManeger.getConf(apiInfo.getContext(), "db.jdbc.driver");
            Driver driver = (Driver) Class.forName(driverStr).newInstance();
            String connUrl = PropManeger.getConf(apiInfo.getContext(), "db.jdbc.url");
            conn = driver.connect(connUrl, new Properties());

            // パラメータをセットし、ストアドを呼出す
            cs = conn.prepareCall("{call ProcGetShipmentDateByDeliveryInformation(?)}");
            cs2 = conn.prepareCall("{call ProcGetShipmentDateByReserve(?)}");

            // 注文者顧客番号
            cs.setInt(1, req.getOrderCustomerNo());

            // 取得結果（リターンコード）
            log.info("ProcGetShipmentDateByDeliveryInformation呼出（開始）：" + new SimpleDateFormat(
                            "yyyy/MM/dd HH:mm:ss").format(new Date()));
            cs.executeQuery();
            log.info("ProcGetShipmentDateByDeliveryInformation呼出（終了）：" + new SimpleDateFormat(
                            "yyyy/MM/dd HH:mm:ss").format(new Date()));

            // 処理結果を取得してしまうとResultSetがクリアされてしまう為
            // 前もって取得しておく必要がある
            ResultSet rs = cs.getResultSet();
            Map<Timestamp, Timestamp> deliveryInformationMap = new HashMap<>();
            while (rs != null && rs.next()) {
                // 配送情報MAP作成（キー：お届け可能日、値：出荷予定日）
                deliveryInformationMap.put(rs.getTimestamp(1), rs.getTimestamp(2));
            }

            List<Object> infoList = new ArrayList<>();
            GetShipmentDateResponse getShipmentDateResponse;

            // 申込商品
            String goodsCode;
            // 配達指定日
            Timestamp deliveryDesignatedDay;
            // 配達指定時間
            Integer deliveryDesignatedTimeCode;

            for (GetShipmentDateInfoRequest goods : req.getInfo()) {
                goodsCode = goods.getGoodsCode();
                deliveryDesignatedDay = goods.getDeliveryDesignatedDay();
                deliveryDesignatedTimeCode = goods.getDeliveryDesignatedTimeCode();

                // 出荷予定日取得APIのレスポンスモデルを初期化
                getShipmentDateResponse = new GetShipmentDateResponse();
                // 申込商品（商品番号）
                getShipmentDateResponse.setGoodsCode(goodsCode);

                // 今すぐお届けの場合
                // ※配達指定日、配達指定時間が常にセットされている（配達指定なしの場合も値「0」が来る）
                if (deliveryDesignatedDay != null && deliveryDesignatedTimeCode != null) {
                    // 配達指定日
                    getShipmentDateResponse.setDeliveryDesignatedDay(deliveryDesignatedDay);
                    // 出荷予定日
                    getShipmentDateResponse.setShipmentDate(deliveryInformationMap.get(deliveryDesignatedDay));
                    // リストにセットして次のループへ
                    infoList.add(getShipmentDateResponse);
                    continue;
                }

                // 入荷次第お届けの場合
                // ※配達指定日、配達指定時間が常にNULL
                if (deliveryDesignatedDay == null && deliveryDesignatedTimeCode == null) {
                    // 他項目はNULLのままセットして次のループへ
                    infoList.add(getShipmentDateResponse);
                    continue;
                }

                // 以降、セールde予約の場合

                // パラメータをセットし、ストアドを呼出す
                cs2.clearParameters();

                // 申込商品（商品番号）
                cs2.setString(1, goods.getGoodsCode());

                // 取得結果（リターンコード）
                log.info("ProcGetShipmentDateByReserve呼出（開始）：" + new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(
                                new Date()));
                cs2.executeQuery();
                log.info("ProcGetShipmentDateByReserve呼出（終了）：" + new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(
                                new Date()));

                // 処理結果を取得してしまうとResultSetがクリアされてしまう為
                // 前もって取得しておく必要がある
                ResultSet rs2 = cs2.getResultSet();

                Map<Timestamp, Timestamp> reserveInformationMap = new HashMap<>();
                while (rs2 != null && rs2.next()) {
                    // 取りおき可否
                    Integer reserveFlag = rs2.getInt(4);
                    if (reserveFlag != null && reserveFlag == 1) {
                        // 取りおき情報MAP作成（キー：お届け可能日、値：出荷予定日）
                        reserveInformationMap.put(rs2.getTimestamp(2), rs2.getTimestamp(3));
                    }
                }

                // 配達指定日
                getShipmentDateResponse.setDeliveryDesignatedDay(deliveryDesignatedDay);
                // 出荷予定日
                getShipmentDateResponse.setShipmentDate(reserveInformationMap.containsKey(deliveryDesignatedDay) ?
                                                                        reserveInformationMap.get(
                                                                                        deliveryDesignatedDay) :
                                                                        // セールde予約の配達指定日は自由入力なので、スタブ上にデータがない場合は固定値を返す
                                                                        new Timestamp((new SimpleDateFormat(
                                                                                        "yyyy/MM/dd").parse(
                                                                                        "2099/12/31")).getTime()));

                infoList.add(getShipmentDateResponse);
            }

            // ストアドからの返却値
            apiInfo.setStoredName("ProcGetShipmentDate");
            apiInfo.setStoredCode(0);
            Utility.makeResult(apiInfo, result, resultInfo, infoList);

        } catch (Exception ex) {

            apiInfo.setException(ex);
            Utility.logicException(apiInfo, result, resultInfo);

        } finally {

            // クローズ処理
            Utility.close(cs, cs2, conn, apiInfo);
        }

        // 処理結果をJSON形式に変換して返却
        return Utility.createResponse(log, result, jsonData, Level.INFO);
    }

    // 2023-renew No14 to here

}
