// CHECKSTYLE IGNORE THIS LINE ファイルサイズオーバーを許容：単純に項目が多いため。
/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */
package jp.co.hankyuhanshin.itec.webapi.pdr.resources;

import jp.co.hankyuhanshin.itec.webapi.pdr.common.ApiInfo;
import jp.co.hankyuhanshin.itec.webapi.pdr.common.PropManeger;
import jp.co.hankyuhanshin.itec.webapi.pdr.common.Utility;
import jp.co.hankyuhanshin.itec.webapi.pdr.request.order.CancelOrderRequest;
import jp.co.hankyuhanshin.itec.webapi.pdr.request.order.GetNotYetShippingOrderHistoryRequest;
import jp.co.hankyuhanshin.itec.webapi.pdr.request.order.GetPreShipmentOrderHistoryRequest;
import jp.co.hankyuhanshin.itec.webapi.pdr.request.order.GetPurchasedCommodityInformationRequest;
import jp.co.hankyuhanshin.itec.webapi.pdr.request.order.GetShipmentInformationRequest;
import jp.co.hankyuhanshin.itec.webapi.pdr.response.Result;
import jp.co.hankyuhanshin.itec.webapi.pdr.response.ResultInfo;
import jp.co.hankyuhanshin.itec.webapi.pdr.response.order.CancelOrderResponse;
import jp.co.hankyuhanshin.itec.webapi.pdr.response.order.GetNotYetShippingOrderHistoryGoodsResponse;
import jp.co.hankyuhanshin.itec.webapi.pdr.response.order.GetNotYetShippingOrderHistoryResponse;
import jp.co.hankyuhanshin.itec.webapi.pdr.response.order.GetPreShipmentOrderHistoryGoodsResponse;
import jp.co.hankyuhanshin.itec.webapi.pdr.response.order.GetPreShipmentOrderHistoryResponse;
import jp.co.hankyuhanshin.itec.webapi.pdr.response.order.GetPurchasedCommodityInformationResponse;
import jp.co.hankyuhanshin.itec.webapi.pdr.response.order.GetShipmentInformationGoodsResponse;
import jp.co.hankyuhanshin.itec.webapi.pdr.response.order.GetShipmentInformationResponse;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

// PDR#15 20161115 k-katoh 新規作成

/**
 * PDR#15 WEB-API連携 注文系WebAPI<br/>
 * <pre>
 * ・出荷情報取得
 * ・注文履歴（未配送）取得
 * ・注文履歴（配送済み）取得
 * ・購入済み商品情報取得
 * </pre>
 * @author k-katoh
 */
@Path("/order")
public class OrderHistory {

    /** サーブレットコンテキスト */
    @Context
    private ServletContext context;

    /**
     * 出荷情報取得<br/>
     *
     * @param jsonData 取得条件
     * <ul>
     *   <li>指定日時:designationDate</li>
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
     *     <li>受注番号:orderNo</li>
     *     <li>事業所名:officeName</li>
     *     <li>お届け先事業所名:receiveOfficeName</li>
     *     <li>お届け先郵便番号:receiveZipcode</li>
     *     <li>お届け先住所(都道府県・市区町村):receiveAddress1</li>
     *     <li>お届け先住所(丁目・番地):receiveAddress2</li>
     *     <li>お届け先住所(建物名・部屋番号):receiveAddress3</li>
     *     <li>お届け先住所(方書1):receiveAddress4</li>
     *     <li>お届け先住所(方書2):receiveAddress5</li>
     *     <li>小計:subTotalPrice</li>
     *     <li>値引:discountPrice</li>
     *     <li>送料:shippingPrice</li>
     *     <li>消費税:taxPrice</li>
     *     <li>合計金額:totalPrice</li>
     *     <li>配送方法:deliveryName</li>
     *     <li>支払方法:paymentName</li>
     *     <li>配達指定日:deliveryDesignatedDay</li>
     *     <li>配達指定時間:deliveryDesignatedTimeName</li>
     *     <li>配送確認URL:deliveryComfimURL</li>
     *     <li>送り状番号:invoiceNo</li>
     *     <li>メールアドレス:mailAddress</li>
     *     <li>【リスト】商品リスト:goods</li>
     *     <ul>
     *       <li>申込商品:goodsNo</li>
     *       <li>商品名:goodsName</li>
     *       <li>数量:count</li>
     *       <li>単位:unitName</li>
     *       <li>単価:unitPrice</li>
     *       <li>金額:price</li>
     *       <li>適用割引:discountFlag</li>
     *     </ul>
     *   </ul>
     * </ul>
     */
    @POST
    @Path("/GetShipmentInformation")
    @Produces({"application/json"})
    @Consumes({"application/json"})
    public Response getShipmentInformation(String jsonData) {

        Logger log = LogManager.getLogger("getShipmentInformation");

        // 返却情報
        Result result = new Result();
        ResultInfo resultInfo = new ResultInfo();
        ApiInfo apiInfo = new ApiInfo("getShipmentInformation", "出荷情報取得[Order/GetShipmentInformation]", context, log);

        Connection conn = null;
        CallableStatement cs = null;

        try {

            // INパラメータをログに出力する
            log.info("[IN]" + jsonData);

            // POSTされたJSONをデコードし、専用のオブジェクトに変換する
            GetShipmentInformationRequest req = JSON.decode(jsonData, GetShipmentInformationRequest.class);

            // JDBC接続
            String driverStr = PropManeger.getConf(apiInfo.getContext(), "db.jdbc.driver");
            Driver driver = (Driver) Class.forName(driverStr).newInstance();
            String connUrl = PropManeger.getConf(apiInfo.getContext(), "db.jdbc.url");
            conn = driver.connect(connUrl, new Properties());

            // パラメータをセットし、ストアドを呼出す
            cs = conn.prepareCall("{call ProcGetShipmentInformation(?)}");
            // 指定日時
            cs.setTimestamp(1, req.getDesignationDate());
            // 取得結果（リターンコード）
            // cs.registerOutParameter(2, java.sql.Types.SMALLINT);
            cs.executeQuery();
            Integer status = 0;

            // 処理結果を取得してしまうとResultSetがクリアされてしまう為
            // 前もって取得しておく必要がある
            ResultSet rs = cs.getResultSet();
            List<Object> infoList = new ArrayList<>();
            GetShipmentInformationResponse info = new GetShipmentInformationResponse();
            List<GetShipmentInformationGoodsResponse> goodsList = new ArrayList<>();
            GetShipmentInformationGoodsResponse goodsInfo;

            String wkOrderNo = "0";

            while (rs != null && rs.next()) {

                // 受注番号が変わった場合
                if (wkOrderNo != "0" && wkOrderNo != String.valueOf(rs.getInt(1))) {
                    info.setGoodsList(goodsList);
                    infoList.add(info);
                    goodsList = new ArrayList<>();

                }

                // １件目 または 受注番号が変わった場合
                if (wkOrderNo == "0" || wkOrderNo != String.valueOf(rs.getInt(1))) {
                    info = new GetShipmentInformationResponse();
                    // 受注番号
                    info.setOrderNo(rs.getString(1));
                    // エラーキー情報を共通セット
                    Utility.setErrorKeyInfo(apiInfo, "最終処理受注番号=" + rs.getString(1));
                    // 事業所名
                    info.setOfficeName(rs.getString(2));
                    // お届け先事業所名
                    info.setReceiveOfficeName(rs.getString(3));
                    // お届け先郵便番号
                    info.setReceiveZipcode(rs.getString(4));
                    // お届け先住所(都道府県・市区町村)
                    info.setReceiveAddress1(rs.getString(5));
                    // お届け先住所(丁目・番地)
                    info.setReceiveAddress2(rs.getString(6));
                    // お届け先住所(建物名・部屋番号)
                    info.setReceiveAddress3(rs.getString(7));
                    // お届け先住所(方書1)
                    info.setReceiveAddress4(rs.getString(8));
                    // お届け先住所(方書2)
                    info.setReceiveAddress5(rs.getString(9));
                    // 小計
                    info.setSubTotalPrice(rs.getString(10));
                    // 値引
                    info.setDiscountPrice(rs.getString(11));
                    // 送料
                    info.setShippingPrice(rs.getString(12));
                    // 消費税
                    info.setTaxPrice(rs.getString(13));
                    // 合計金額
                    info.setTotalPrice(rs.getString(14));
                    // 配送方法
                    info.setDeliveryName(rs.getString(15));
                    // 支払方法
                    info.setPaymentName(rs.getString(16));
                    // 配達指定日
                    info.setDeliveryDesignatedDay(rs.getTimestamp(17));
                    // 配達指定時間
                    info.setDeliveryDesignatedTimeName(rs.getString(18));
                    // 配送確認URL
                    info.setDeliveryComfirmURL(rs.getString(19));
                    // 送り状番号
                    info.setInvoiceNo(rs.getString(26));
                    // メールアドレス
                    info.setMailAddress(rs.getString(27));
                }

                goodsInfo = new GetShipmentInformationGoodsResponse();
                // 申込商品
                goodsInfo.setGoodsNo(rs.getString(20));
                // 商品名
                goodsInfo.setGoodsName(rs.getString(21));
                // 数量
                goodsInfo.setCount(rs.getString(22));
                // 単位
                goodsInfo.setUnitName(rs.getString(23));
                // 単価
                goodsInfo.setUnitPrice(rs.getString(24));
                // 金額
                goodsInfo.setPrice(rs.getString(25));
                // 適用割引
                goodsInfo.setDiscountFlag(rs.getString(28));
                goodsList.add(goodsInfo);

                wkOrderNo = String.valueOf(rs.getInt(1));
                // ステータス
                status = rs.getInt(29);
            }
            if (goodsList != null && !goodsList.isEmpty()) {
                info.setGoodsList(goodsList);
                if (info != null) {
                    infoList.add(info);
                }
            }

            // ストアドからの返却値
            apiInfo.setStoredName("ProcGetShipmentInformation");
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
     * 注文履歴（未配送）取得<br/>
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
     *     <li>受注番号:</li>
     *     <li>受付方法:receptionTypeName</li>
     *     <li>注文日時:orderDate</li>
     *     <li>お届け先事業所名:receiveOfficeName</li>
     *     <li>お届け先郵便番号:receiveZipcode</li>
     *     <li>お届け先住所(都道府県・市区町村):receiveAddress1</li>
     *     <li>お届け先住所(丁目・番地):receiveAddress2</li>
     *     <li>お届け先住所(建物名・部屋番号):receiveAddress3</li>
     *     <li>お届け先住所(方書1):receiveAddress4</li>
     *     <li>お届け先住所(方書2):receiveAddress5</li>
     *     <li>お届け日:receiveDate</li>
     *     <li>支払方法:paymentType</li>
     *     <li>お支払い合計(税込):paymentTotalPrice</li>
     *     <li>適用クーポン番号:couponNo</li>
     *     <li>適用クーポン名:couponName</li>
     *     <li>【リスト】商品リスト:goods</li>
     *     <ul>
     *       <li>申込商品:goodsCode</li>
     *       <li>商品名:goodsName</li>
     *       <li>数量:goodsCount</li>
     *       <li>単位:unitName</li>
     *       <li>適用割引:discountFlag</li>
     *     </ul>
     *   </ul>
     * </ul>
     */
    @POST
    @Path("/GetNotYetShippingOrderHistory")
    @Produces({"application/json"})
    @Consumes({"application/json"})
    public Response getNotYetShippingOrderHistory(String jsonData) {

        Logger log = LogManager.getLogger("getNotYetShippingOrderHistory");

        // 返却情報
        Result result = new Result();
        ResultInfo resultInfo = new ResultInfo();
        ApiInfo apiInfo =
                        new ApiInfo("getNotYetShippingOrderHistory", "注文履歴（未配送）取得[Order/GetNotYetShippingOrderHistory]",
                                    context, log
                        );

        Connection conn = null;
        CallableStatement cs = null;

        try {

            // INパラメータをログに出力する
            log.info("[IN]" + jsonData);

            // POSTされたJSONをデコードし、専用のオブジェクトに変換する
            GetNotYetShippingOrderHistoryRequest req =
                            JSON.decode(jsonData, GetNotYetShippingOrderHistoryRequest.class);

            // エラーキー情報を共通セット
            Utility.setErrorKeyInfo(apiInfo, jsonData);

            // JDBC接続
            String driverStr = PropManeger.getConf(apiInfo.getContext(), "db.jdbc.driver");
            Driver driver = (Driver) Class.forName(driverStr).newInstance();
            String connUrl = PropManeger.getConf(apiInfo.getContext(), "db.jdbc.url");
            conn = driver.connect(connUrl, new Properties());

            // パラメータをセットし、ストアドを呼出す
            cs = conn.prepareCall("{call ProcGetNotYetShippingOrderHistory(?)}");
            // 顧客番号
            if (req.getCustomerNo() == null) {
                cs.setNull(1, java.sql.Types.INTEGER);
            } else {
                cs.setInt(1, Integer.parseInt(req.getCustomerNo()));
            }
            // 取得結果（リターンコード）
            // cs.registerOutParameter(2, java.sql.Types.SMALLINT);
            cs.executeQuery();
            Integer status = 0;

            // 処理結果を取得してしまうとResultSetがクリアされてしまう為
            // 前もって取得しておく必要がある
            ResultSet rs = cs.getResultSet();
            List<Object> infoList = new ArrayList<>();
            GetNotYetShippingOrderHistoryResponse info = new GetNotYetShippingOrderHistoryResponse();
            List<GetNotYetShippingOrderHistoryGoodsResponse> goodsList = new ArrayList<>();
            GetNotYetShippingOrderHistoryGoodsResponse goodsInfo;

            int wkOrderNo = 0;

            while (rs != null && rs.next()) {

                // 受注番号が変わった場合
                if (wkOrderNo != 0 && wkOrderNo != rs.getInt(1)) {
                    info.setGoodsList(goodsList);
                    infoList.add(info);
                    goodsList = new ArrayList<>();
                }
                // １件目 または 受注番号が変わった場合
                if (wkOrderNo == 0 || wkOrderNo != rs.getInt(1)) {
                    // 固定部分を生成する
                    info = new GetNotYetShippingOrderHistoryResponse();
                    // 受注番号
                    info.setOrderNo(rs.getString(1));
                    // 受付方法
                    info.setReceptionTypeName(rs.getString(2));
                    // 注文日時
                    info.setOrderDate(rs.getTimestamp(3));
                    // お届け先事業所名
                    info.setReceiveOfficeName(rs.getString(4));
                    // お届け先郵便番号
                    info.setReceiveZipcode(rs.getString(5));
                    // お届け先住所(都道府県・市区町村)
                    info.setReceiveAddress1(rs.getString(6));
                    // お届け先住所(丁目・番地)
                    info.setReceiveAddress2(rs.getString(7));
                    // お届け先住所(建物名・部屋番号)
                    info.setReceiveAddress3(rs.getString(8));
                    // お届け先住所(方書1)
                    info.setReceiveAddress4(rs.getString(9));
                    // お届け先住所(方書2)
                    info.setReceiveAddress5(rs.getString(10));
                    // お届け日
                    info.setReceiveDate(rs.getTimestamp(11));
                    // 支払方法
                    info.setPaymentType(rs.getString(12));
                    // お支払い合計(税込)
                    info.setPaymentTotalPrice(rs.getString(13));
                    // 2023-renew No24 from here
                    // 適用クーポン番号
                    info.setCouponNo(rs.getString(14));
                    // 適用クーポン名
                    info.setCouponName(rs.getString(15));
                    // 2023-renew No24 to here
                    // 2023-renew No68 from here
                    // キャンセル可否フラグ
                    info.setCancelYesNo(rs.getInt(16));
                    // 2023-renew No68 to here
                }

                goodsInfo = new GetNotYetShippingOrderHistoryGoodsResponse();
                // 申込商品
                goodsInfo.setGoodsCode(rs.getString(17));
                // 商品名
                goodsInfo.setGoodsName(rs.getString(18));
                // 数量
                goodsInfo.setGoodsCount(rs.getString(19));
                // 単位
                goodsInfo.setUnitName(rs.getString(20));
                // 適用割引
                goodsInfo.setDiscountFlag(rs.getString(21));
                goodsList.add(goodsInfo);
                // ステータス
                status = rs.getInt(22);
                wkOrderNo = rs.getInt(1);
            }

            if (goodsList != null && goodsList.size() > 0) {
                info.setGoodsList(goodsList);
            }
            if (info != null) {
                infoList.add(info);
            }

            // ストアドからの返却値
            apiInfo.setStoredName("ProcGetNotYetShippingOrderHistory");
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
     * 注文履歴（配送済み）取得<br/>
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
     *     <li>受注番号:</li>
     *     <li>受付方法:receptionTypeName</li>
     *     <li>注文日時:orderDate</li>
     *     <li>お届け先事業所名:receiveOfficeName</li>
     *     <li>お届け先郵便番号:receiveZipcode</li>
     *     <li>お届け先住所(都道府県・市区町村):receiveAddress1</li>
     *     <li>お届け先住所(丁目・番地):receiveAddress2</li>
     *     <li>お届け先住所(建物名・部屋番号):receiveAddress3</li>
     *     <li>お届け先住所(方書1):receiveAddress4</li>
     *     <li>お届け先住所(方書2):receiveAddress5</li>
     *     <li>お届け日:receiveDate</li>
     *     <li>支払方法:paymentType</li>
     *     <li>お支払い合計(税込):paymentTotalPrice</li>
     *     <li>適用クーポン番号:couponNo</li>
     *     <li>適用クーポン名:couponName</li>
     *     <li>【リスト】商品リスト:goods</li>
     *     <ul>
     *       <li>申込商品:goodsCode</li>
     *       <li>商品名:goodsName</li>
     *       <li>数量:goodsCount</li>
     *       <li>単位:unitName</li>
     *       <li>適用割引:discountFlag</li>
     *     </ul>
     *   </ul>
     * </ul>
     */
    @POST
    @Path("/GetPreShipmentOrderHistory")
    @Produces({"application/json"})
    @Consumes({"application/json"})
    public Response getPreShipmentOrderHistory(String jsonData) {

        Logger log = LogManager.getLogger("getPreShipmentOrderHistory");

        // 返却情報
        Result result = new Result();
        ResultInfo resultInfo = new ResultInfo();
        ApiInfo apiInfo = new ApiInfo("getPreShipmentOrderHistory", " 注文履歴（配送済み）取得[Order/GetPreShipmentOrderHistory]",
                                      context, log
        );

        Connection conn = null;
        CallableStatement cs = null;

        try {

            // INパラメータをログに出力する
            log.info("[IN]" + jsonData);

            // POSTされたJSONをデコードし、専用のオブジェクトに変換する
            // PDR #343
            GetPreShipmentOrderHistoryRequest req = JSON.decode(jsonData, GetPreShipmentOrderHistoryRequest.class);

            // エラーキー情報を共通セット
            Utility.setErrorKeyInfo(apiInfo, jsonData);

            // JDBC接続
            String driverStr = PropManeger.getConf(apiInfo.getContext(), "db.jdbc.driver");
            Driver driver = (Driver) Class.forName(driverStr).newInstance();
            String connUrl = PropManeger.getConf(apiInfo.getContext(), "db.jdbc.url");
            conn = driver.connect(connUrl, new Properties());

            // パラメータをセットし、ストアドを呼出す
            // 2023-renew No52 from here
            cs = conn.prepareCall("{call ProcGetPreShipmentOrderHistory(?,?,?)}");
            // 2023-renew No52 to here
            // 顧客番号
            if (req.getCustomerNo() == null) {
                cs.setNull(1, java.sql.Types.INTEGER);
            } else {
                cs.setInt(1, Integer.parseInt(req.getCustomerNo()));
            }
            // 2023-renew No52 from here
            //絞り込み開始日
            cs.setTimestamp(2, req.getSearchStartDay());

            //絞り込み終了日
            cs.setTimestamp(3, req.getSearchEndDay());
            // 2023-renew No52 to here
            // 取得結果（リターンコード）
            // cs.registerOutParameter(2, java.sql.Types.SMALLINT);
            cs.executeQuery();
            Integer status = 0;

            // 処理結果を取得してしまうとResultSetがクリアされてしまう為
            // 前もって取得しておく必要がある
            ResultSet rs = cs.getResultSet();
            List<Object> infoList = new ArrayList<>();
            GetPreShipmentOrderHistoryResponse info = new GetPreShipmentOrderHistoryResponse();
            List<GetPreShipmentOrderHistoryGoodsResponse> goodsList = new ArrayList<>();
            GetPreShipmentOrderHistoryGoodsResponse goodsInfo;

            int wkOrderNo = 0;

            while (rs != null && rs.next()) {

                // 受注番号が変わった場合
                if (wkOrderNo != 0 && wkOrderNo != rs.getInt(1)) {
                    info.setGoodsList(goodsList);
                    infoList.add(info);
                    goodsList = new ArrayList<>();

                }
                // １件目 または 受注番号が変わった場合
                if (wkOrderNo == 0 || wkOrderNo != rs.getInt(1)) {
                    // 固定部分を生成する
                    info = new GetPreShipmentOrderHistoryResponse();
                    // 受注番号
                    info.setOrderNo(rs.getString(1));
                    // 受付方法
                    info.setReceptionTypeName(rs.getString(2));
                    // 注文日時
                    info.setOrderDate(rs.getTimestamp(3));
                    // お届け先事業所名
                    info.setReceiveOfficeName(rs.getString(4));
                    // お届け先郵便番号
                    info.setReceiveZipcode(rs.getString(5));
                    // お届け先住所(都道府県・市区町村)
                    info.setReceiveAddress1(rs.getString(6));
                    // お届け先住所(丁目・番地)
                    info.setReceiveAddress2(rs.getString(7));
                    // お届け先住所(建物名・部屋番号)
                    info.setReceiveAddress3(rs.getString(8));
                    // お届け先住所(方書1)
                    info.setReceiveAddress4(rs.getString(9));
                    // お届け先住所(方書2)
                    info.setReceiveAddress5(rs.getString(10));
                    // お届け日
                    info.setReceiveDate(rs.getTimestamp(11));
                    // 支払方法
                    info.setPaymentType(rs.getString(12));
                    // お支払い合計(税込)
                    info.setPaymentTotalPrice(rs.getString(13));
                    // 2023-renew No24 from here
                    // 適用クーポン番号
                    info.setCouponNo(rs.getString(14));
                    // 適用クーポン名
                    info.setCouponName(rs.getString(15));
                    // 2023-renew No24 to here
                }

                goodsInfo = new GetPreShipmentOrderHistoryGoodsResponse();
                // 申込商品
                goodsInfo.setGoodsCode(rs.getString(16));
                // 商品名
                goodsInfo.setGoodsName(rs.getString(17));
                // 数量
                goodsInfo.setGoodsCount(rs.getString(18));
                // 単位
                goodsInfo.setUnitName(rs.getString(19));
                // 適用割引
                goodsInfo.setDiscountFlag(rs.getString(20));
                goodsList.add(goodsInfo);

                wkOrderNo = rs.getInt(1);
                // ステータス
                status = rs.getInt(21);
            }

            if (goodsList != null && goodsList.size() > 0) {
                info.setGoodsList(goodsList);
            }
            if (info != null) {
                infoList.add(info);
            }

            // ストアドからの返却値
            apiInfo.setStoredName("ProcGetPreShipmentOrderHistory");
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
     * 購入済み商品情報取得<br/>
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
     *     <li>申込商品:goodsCode</li>
     *   </ul>
     * </ul>
     */
    @POST
    @Path("/GetPurchasedCommodityInformation")
    @Produces({"application/json"})
    @Consumes({"application/json"})
    public Response getPurchasedCommodityInformation(String jsonData) {

        Logger log = LogManager.getLogger("getPurchasedCommodityInformation");
        // 返却情報
        Result result = new Result();
        ResultInfo resultInfo = new ResultInfo();
        ApiInfo apiInfo = new ApiInfo("getPurchasedCommodityInformation",
                                      "購入済み商品情報取得[Order/GetPurchasedCommodityInformation]", context, log
        );

        Connection conn = null;
        CallableStatement cs = null;

        try {

            // INパラメータをログに出力する
            log.info("[IN]" + jsonData);

            // POSTされたJSONをデコードし、専用のオブジェクトに変換する
            GetPurchasedCommodityInformationRequest req =
                            JSON.decode(jsonData, GetPurchasedCommodityInformationRequest.class);

            // エラーキー情報を共通セット
            Utility.setErrorKeyInfo(apiInfo, jsonData);

            // JDBC接続
            String driverStr = PropManeger.getConf(apiInfo.getContext(), "db.jdbc.driver");
            Driver driver = (Driver) Class.forName(driverStr).newInstance();
            String connUrl = PropManeger.getConf(apiInfo.getContext(), "db.jdbc.url");
            conn = driver.connect(connUrl, new Properties());

            // パラメータをセットし、ストアドを呼出す
            cs = conn.prepareCall("{call ProcGetPurchasedCommodityInformation(?)}");
            // 顧客番号
            if (req.getCustomerNo() == null) {
                cs.setNull(1, java.sql.Types.INTEGER);
            } else {
                cs.setInt(1, Integer.parseInt(req.getCustomerNo()));
            }
            // 取得結果（リターンコード）
            // cs.registerOutParameter(2, java.sql.Types.SMALLINT);
            log.info("ProcGetPurchasedCommodityInformation呼出（開始）：" + new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(
                            new Date()));
            cs.executeQuery();
            log.info("ProcGetPurchasedCommodityInformation呼出（終了）：" + new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(
                            new Date()));
            Integer status = 0;

            // 処理結果を取得してしまうとResultSetがクリアされてしまう為
            // 前もって取得しておく必要がある
            ResultSet rs = cs.getResultSet();
            List<Object> infoList = new ArrayList<Object>();
            GetPurchasedCommodityInformationResponse info = new GetPurchasedCommodityInformationResponse();
            while (rs != null && rs.next()) {
                info = new GetPurchasedCommodityInformationResponse();
                // 申込商品
                info.setGoodsCode(rs.getString(1));

                infoList.add(info);
                // ステータス
                status = rs.getInt(2);
            }

            // ストアドからの返却値
            apiInfo.setStoredName("ProcGetPurchasedCommodityInformation");
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
     * 注文履歴（過去1年）取得<br/>
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
     *     <li>受注番号:</li>
     *     <li>受付方法:receptionTypeName</li>
     *     <li>注文日時:orderDate</li>
     *     <li>お届け先事業所名:receiveOfficeName</li>
     *     <li>お届け先郵便番号:receiveZipcode</li>
     *     <li>お届け先住所(都道府県・市区町村):receiveAddress1</li>
     *     <li>お届け先住所(丁目・番地):receiveAddress2</li>
     *     <li>お届け先住所(建物名・部屋番号):receiveAddress3</li>
     *     <li>お届け先住所(方書1):receiveAddress4</li>
     *     <li>お届け先住所(方書2):receiveAddress5</li>
     *     <li>お届け日:receiveDate</li>
     *     <li>支払方法:paymentType</li>
     *     <li>お支払い合計(税込):paymentTotalPrice</li>
     *     <li>【リスト】商品リスト:goods</li>
     *     <ul>
     *       <li>申込商品:goodsCode</li>
     *       <li>商品名:goodsName</li>
     *       <li>数量:goodsCount</li>
     *       <li>単位:unitName</li>
     *     </ul>
     *   </ul>
     * </ul>
     */
    @POST
    @Path("/GetPreShipmentOrderHistoryAggregate")
    @Produces({"application/json"})
    @Consumes({"application/json"})
    public Response getPreShipmentOrderHistoryAggregate(String jsonData) {

        Logger log = LogManager.getLogger("getPreShipmentOrderHistoryAggregate");

        // 返却情報
        Result result = new Result();
        ResultInfo resultInfo = new ResultInfo();
        ApiInfo apiInfo = new ApiInfo("getPreShipmentOrderHistoryAggregate",
                                      " 注文履歴（過去1年）取得[Order/GetPreShipmentOrderHistoryAggregate(PastYear)]", context, log
        );

        Connection conn = null;
        CallableStatement cs = null;

        try {

            // INパラメータをログに出力する
            log.info("[IN]" + jsonData);

            // POSTされたJSONをデコードし、専用のオブジェクトに変換する
            // PDR #343
            GetPreShipmentOrderHistoryRequest req = JSON.decode(jsonData, GetPreShipmentOrderHistoryRequest.class);

            // エラーキー情報を共通セット
            Utility.setErrorKeyInfo(apiInfo, jsonData);

            // JDBC接続
            String driverStr = PropManeger.getConf(apiInfo.getContext(), "db.jdbc.driver");
            Driver driver = (Driver) Class.forName(driverStr).newInstance();
            String connUrl = PropManeger.getConf(apiInfo.getContext(), "db.jdbc.url");
            conn = driver.connect(connUrl, new Properties());

            // パラメータをセットし、ストアドを呼出す
            cs = conn.prepareCall("{call ProcGetPreShipmentOrderHistoryPastYear(?)}");
            // 顧客番号
            if (req.getCustomerNo() == null) {
                cs.setNull(1, java.sql.Types.INTEGER);
            } else {
                cs.setInt(1, Integer.parseInt(req.getCustomerNo()));
            }
            // 取得結果（リターンコード）
            // cs.registerOutParameter(2, java.sql.Types.SMALLINT);
            log.info("ProcGetPreShipmentOrderHistoryPastYear呼出（開始）：" + new SimpleDateFormat(
                            "yyyy/MM/dd HH:mm:ss").format(new Date()));
            cs.executeQuery();
            log.info("ProcGetPreShipmentOrderHistoryPastYear呼出（終了）：" + new SimpleDateFormat(
                            "yyyy/MM/dd HH:mm:ss").format(new Date()));
            Integer status = 0;
            // 処理結果を取得してしまうとResultSetがクリアされてしまう為
            // 前もって取得しておく必要がある
            ResultSet rs = cs.getResultSet();
            List<Object> infoList = new ArrayList<>();
            GetPreShipmentOrderHistoryResponse info = new GetPreShipmentOrderHistoryResponse();
            List<GetPreShipmentOrderHistoryGoodsResponse> goodsList = new ArrayList<>();
            GetPreShipmentOrderHistoryGoodsResponse goodsInfo;

            int wkOrderNo = 0;

            while (rs != null && rs.next()) {

                // 受注番号が変わった場合
                if (wkOrderNo != 0 && wkOrderNo != rs.getInt(1)) {
                    info.setGoodsList(goodsList);
                    infoList.add(info);
                    goodsList = new ArrayList<>();

                }
                // １件目 または 受注番号が変わった場合
                if (wkOrderNo == 0 || wkOrderNo != rs.getInt(1)) {
                    // 固定部分を生成する
                    info = new GetPreShipmentOrderHistoryResponse();
                    // 受注番号
                    info.setOrderNo(rs.getString(1));
                    // 受付方法
                    info.setReceptionTypeName(rs.getString(2));
                    // 注文日時
                    info.setOrderDate(rs.getTimestamp(3));
                    // お届け先事業所名
                    info.setReceiveOfficeName(rs.getString(4));
                    // お届け先郵便番号
                    info.setReceiveZipcode(rs.getString(5));
                    // お届け先住所(都道府県・市区町村)
                    info.setReceiveAddress1(rs.getString(6));
                    // お届け先住所(丁目・番地)
                    info.setReceiveAddress2(rs.getString(7));
                    // お届け先住所(建物名・部屋番号)
                    info.setReceiveAddress3(rs.getString(8));
                    // お届け先住所(方書1)
                    info.setReceiveAddress4(rs.getString(9));
                    // お届け先住所(方書2)
                    info.setReceiveAddress5(rs.getString(10));
                    // お届け日
                    info.setReceiveDate(rs.getTimestamp(11));
                    // 支払方法
                    info.setPaymentType(rs.getString(12));
                    // お支払い合計(税込)
                    info.setPaymentTotalPrice(rs.getString(13));
                }

                goodsInfo = new GetPreShipmentOrderHistoryGoodsResponse();
                // 申込商品
                goodsInfo.setGoodsCode(rs.getString(14));
                // 商品名
                goodsInfo.setGoodsName(rs.getString(15));
                // 数量
                goodsInfo.setGoodsCount(rs.getString(16));
                // 単位
                goodsInfo.setUnitName(rs.getString(17));
                goodsList.add(goodsInfo);

                wkOrderNo = rs.getInt(1);
                // ステータス
                status = rs.getInt(18);
            }

            if (goodsList != null && goodsList.size() > 0) {
                info.setGoodsList(goodsList);
            }
            if (info != null) {
                infoList.add(info);
            }

            // ストアドからの返却値
            apiInfo.setStoredName("ProcGetPreShipmentOrderHistoryPastYear");
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

    // 2023-renew No68 from here

    /**
     * 注文キャンセル
     *
     * @param jsonData 取得条件
     * <ul>
     *   <li>受注番号:orderNo</li>
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
     *     <li>注文キャンセル結果:cancelResult</li>
     *     <li>受注番号:orderNo</li>
     *     <li>お届け希望日:receiveDate</li>
     *   </ul>
     * </ul>
     */
    @POST
    @Path("/CancelOrder")
    @Produces({"application/json"})
    @Consumes({"application/json"})
    public Response cancelOrder(String jsonData) {

        Logger log = LogManager.getLogger("cancelOrder");

        // 返却情報
        Result result = new Result();
        ResultInfo resultInfo = new ResultInfo();
        ApiInfo apiInfo = new ApiInfo("cancelOrder", "注文キャンセル[Order/CancelOrder]", context, log);

        Connection conn = null;
        CallableStatement cs = null;

        try {

            // INパラメータをログに出力する
            log.info("[IN]" + jsonData);

            // POSTされたJSONをデコードし、専用のオブジェクトに変換する
            CancelOrderRequest req = JSON.decode(jsonData, CancelOrderRequest.class);

            // エラーキー情報を共通セット
            Utility.setErrorKeyInfo(apiInfo, jsonData);

            // JDBC接続
            String driverStr = PropManeger.getConf(apiInfo.getContext(), "db.jdbc.driver");
            Driver driver = (Driver) Class.forName(driverStr).newInstance();
            String connUrl = PropManeger.getConf(apiInfo.getContext(), "db.jdbc.url");
            conn = driver.connect(connUrl, new Properties());

            // パラメータをセットし、ストアドを呼出す
            cs = conn.prepareCall("{call ProcCancelOrder(?)}");

            // 受注番号
            if (req.getOrderNo() == null) {
                cs.setNull(1, java.sql.Types.INTEGER);
            } else {
                cs.setInt(1, req.getOrderNo());
            }

            // 取得結果（リターンコード）
            log.info("ProcCancelOrder呼出（開始）：" + new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()));
            cs.executeQuery();
            log.info("ProcCancelOrder呼出（終了）：" + new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()));
            Integer status = 0;

            // 処理結果を取得してしまうとResultSetがクリアされてしまう為
            // 前もって取得しておく必要がある
            ResultSet rs = cs.getResultSet();
            List<Object> infoList = new ArrayList<>();
            CancelOrderResponse cancelOrderResponse;
            while (rs != null && rs.next()) {
                cancelOrderResponse = new CancelOrderResponse();
                // 注文キャンセル結果
                cancelOrderResponse.setCancelResult(rs.getInt(1));
                // 受注番号
                cancelOrderResponse.setOrderNo(rs.getInt(2));
                // お届け希望日
                cancelOrderResponse.setReceiveDate(rs.getTimestamp(3));
                // ステータス
                status = rs.getInt(4);
                infoList.add(cancelOrderResponse);
            }

            // ストアドからの返却値
            apiInfo.setStoredName("ProcCancelOrder");
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

    // 2023-renew No68 to here

}
