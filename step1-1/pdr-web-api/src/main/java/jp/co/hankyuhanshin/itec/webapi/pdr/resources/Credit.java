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
import jp.co.hankyuhanshin.itec.webapi.pdr.request.credit.CreditResultRequest;
import jp.co.hankyuhanshin.itec.webapi.pdr.request.credit.GetOrderDetailInformationRequest;
import jp.co.hankyuhanshin.itec.webapi.pdr.response.Result;
import jp.co.hankyuhanshin.itec.webapi.pdr.response.ResultInfo;
import jp.co.hankyuhanshin.itec.webapi.pdr.response.credit.GetOrderDetailInformationResponse;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

/**
 * PDR#351,352 WEB-API連携 与信系WebAPI<br/>
 * <pre>
 * ・受注詳細情報取得
 * ・オーソリ結果連携
 * </pre>
 * @author junichi-iwata
 */
@Path("/credit")
public class Credit {

    /** サーブレットコンテキスト */
    @Context
    private ServletContext context;

    /**
     * 受注詳細情報取得<br/>
     * @param jsonData 取得条件
     * <ul>
     *   <li>顧客番号:customerNo</li>
     *   <li>出荷状態:shipmentStatus</li>
     *   <li>出荷日-From:shipmentDateFrom</li>
     *   <li>出荷日-To:shipmentDateTo</li>
     *   <li>受注番号:orderNo</li>
     * </ul>
     *
     * @return JSON形式の処理結果
     * <ul>
     *   <li>処理結果:result</li>
     *   <ul>
     *     <li>ステータス:status</li>
     *     <li>メッセージ:message</li>
     *   </ul>
     *   <li>情報リスト:info</li>
     *   <ul>
     *     <li>顧客番号:customerNo</li>
     *     <li>事業所名:officeName</li>
     *     <li>電話番号:tel</li>
     *     <li>受注番号:orderNo</li>
     *     <li>受付方法:receptionTypeName</li>
     *     <li>受注日:orderDate</li>
     *     <li>出荷予定日:shipmentDate</li>
     *     <li>配達指定日:deliveryDesignatedDay</li>
     *     <li>請求金額:billingPrice</li>
     *     <li>預り金充当金額:depositPrice</li>
     *     <li>請求残高金額:billingBalancePrice</li>
     *     <li>クレジット決済ID:creditPaymentID</li>
     *     <li>マーチャント取引ID:tradingID</li>
     *     <li>決済処理結果:creditResult</li>
     *     <li>保留区分:holdType</li>
     *     <li>保留区分名称:holdTypeName</li>
     *   </ul>
     * </ul>
     */
    @POST
    @Path("/GetOrderDetailInformation")
    @Produces({"application/json"})
    @Consumes({"application/json"})
    public Response getOrderDetailInformation(String jsonData) {

        Logger log = LogManager.getLogger("getOrderDetailInformation");

        // 返却情報
        Result result = new Result();
        ResultInfo resultInfo = new ResultInfo();
        ApiInfo apiInfo =
                        new ApiInfo("getOrderDetailInformation", "受注詳細情報取得[credit/GetOrderDetailInformation]", context,
                                    log
                        );

        Connection conn = null;
        CallableStatement cs = null;

        try {

            // INパラメータをログに出力する
            log.info("[IN]" + jsonData);

            // POSTされたJSONをデコードし、専用のオブジェクトに変換する
            GetOrderDetailInformationRequest req = JSON.decode(jsonData, GetOrderDetailInformationRequest.class);

            // エラーキー情報を共通セット
            Utility.setErrorKeyInfo(apiInfo, jsonData);

            // JDBC接続
            String driverStr = PropManeger.getConf(apiInfo.getContext(), "db.jdbc.driver");
            Driver driver = (Driver) Class.forName(driverStr).newInstance();
            String connUrl = PropManeger.getConf(apiInfo.getContext(), "db.jdbc.url");
            conn = driver.connect(connUrl, new Properties());

            // パラメータをセットし、ストアドを呼出す
            cs = conn.prepareCall("{call ProcGetOrderDetailInformation(?,?,?,?,?)}");
            // 顧客番号
            if (req.getCustomerNo() == null) {
                cs.setNull(1, java.sql.Types.INTEGER);
            } else {
                cs.setInt(1, Integer.parseInt(req.getCustomerNo()));
            }
            // 出荷状態
            cs.setString(2, req.getShipmentStatus());
            // 出荷日-From
            cs.setTimestamp(3, req.getShipmentDateFrom());
            // 出荷日-To
            cs.setTimestamp(4, req.getShipmentDateTo());
            // 受注番号
            if (req.getOrderNo() == null) {
                cs.setNull(5, java.sql.Types.INTEGER);
            } else {
                cs.setInt(5, Integer.parseInt(req.getOrderNo()));
            }
            // 取得結果（リターンコード）
            // cs.registerOutParameter(6, java.sql.Types.SMALLINT);
            log.info("ProcGetOrderDetailInformation呼出（開始）：" + new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(
                            new Date()));
            cs.executeQuery();
            log.info("ProcGetOrderDetailInformation呼出（終了）：" + new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(
                            new Date()));
            Integer status = 0;

            // 処理結果を取得してしまうとResultSetがクリアされてしまう為
            // 前もって取得しておく必要がある
            ResultSet rs = cs.getResultSet();
            List<Object> infoList = new ArrayList<>();
            while (rs != null && rs.next()) {
                GetOrderDetailInformationResponse info = new GetOrderDetailInformationResponse();
                /** 顧客番号 */
                info.setCustomerNo(rs.getString(1));
                /** 事業所名 */
                info.setOfficeName(rs.getString(2));
                /** 電話番号 */
                info.setTel(rs.getString(3));
                /** 受注番号 */
                info.setOrderNo(rs.getString(4));
                /** 受付方法 */
                info.setReceptionTypeName(rs.getString(5));
                /** 受注日 */
                info.setOrderDate(rs.getTimestamp(6));
                /** 出荷予定日 */
                info.setEstimatedShipmentDate(rs.getTimestamp(7));
                /** 出荷日 */
                info.setShipmentDate(rs.getTimestamp(8));
                /** 配達指定日 */
                info.setDeliveryDesignatedDay(rs.getTimestamp(9));
                /** 請求金額 */
                info.setBillingPrice(rs.getBigDecimal(10));
                /** 預り金充当金額 */
                info.setDepositPrice(rs.getBigDecimal(11));
                /** 請求残高金額 */
                info.setBillingBalancePrice(rs.getBigDecimal(12));
                /** クレジット決済ID */
                info.setCreditPaymentID(rs.getString(13));
                /** マーチャント取引ID */
                info.setTradingID(rs.getString(14));
                /** 決済処理結果 */
                info.setCreditResult(rs.getString(15));
                /** 保留区分 */
                info.setHoldType(rs.getString(16));
                /** 保留区分名称 */
                info.setHoldTypeName(rs.getString(17));

                infoList.add(info);
                // ステータス
                status = rs.getInt(18);
            }

            // ストアドからの返却値
            apiInfo.setStoredName("ProcGetOrderDetailInformation");
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
     * オーソリ結果連携<br/>
     *
     * @param jsonData JSON形式のオーソリ結果情報
     * <ul>
     * <li>受注番号:orderNo</li>
     * <li>クレジット決済ID:creditPaymentID</li>
     * <li>マーチャント取引ID:tradingID</li>
     * <li>請求金額:billingPrice</li>
     * <li>与信取得日時:creditDate</li>
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
    @Path("/CreditResult")
    @Produces({"application/json"})
    @Consumes({"application/json"})
    public Response creditResult(String jsonData) {

        Logger log = LogManager.getLogger("creditResult");

        // 返却情報
        Result result = new Result();
        ResultInfo resultInfo = new ResultInfo();
        ApiInfo apiInfo = new ApiInfo("creditResult", "オーソリ結果連携[Credit/CreditResult]", context, log);

        Connection conn = null;
        CallableStatement cs = null;

        try {

            // INパラメータをログに出力する
            log.info("[IN]" + jsonData);

            // POSTされたJSONをデコードし、専用のオブジェクトに変換する
            CreditResultRequest req = JSON.decode(jsonData, CreditResultRequest.class);

            // エラーキー情報を共通セット
            Utility.setErrorKeyInfo(apiInfo, jsonData);

            // JDBC接続
            String driverStr = PropManeger.getConf(apiInfo.getContext(), "db.jdbc.driver");
            Driver driver = (Driver) Class.forName(driverStr).newInstance();
            String connUrl = PropManeger.getConf(apiInfo.getContext(), "db.jdbc.url");
            conn = driver.connect(connUrl, new Properties());

            conn.setAutoCommit(false);

            // パラメータをセットし、ストアドを呼出す
            cs = conn.prepareCall("{call ProcCreditResult(?,?,?,?,?,?,?)}");
            // 受注番号
            if (req.getOrderNo() == null) {
                cs.setNull(1, java.sql.Types.INTEGER);
            } else {
                cs.setInt(1, Integer.parseInt(req.getOrderNo()));
            }
            // クレジット決済ID
            cs.setString(2, req.getCreditPaymentID());
            // マーチャント取引ID
            cs.setString(3, req.getTradingID());
            // 請求金額
            cs.setBigDecimal(4, req.getBillingPrice());
            // 与信取得日時
            cs.setTimestamp(5, req.getCreditDate());
            // 担当者コード
            if (req.getUserID() == null) {
                cs.setNull(6, java.sql.Types.SMALLINT);
            } else {
                cs.setShort(6, Short.parseShort(req.getUserID()));
            }
            // 登録結果(リターンコード)
            cs.registerOutParameter(7, java.sql.Types.SMALLINT);
            cs.executeUpdate();

            // ストアドからの返却値
            apiInfo.setStoredName("ProcCreditResult");
            apiInfo.setStoredCode(cs.getShort(7));
            Utility.makeResult(apiInfo, result, resultInfo, null);

            if (Constant.STORED_OK != apiInfo.getStoredCode()) {
                conn.rollback();
            } else {
                conn.commit();
            }
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
            Utility.close(cs, conn, apiInfo);
        }

        // 処理結果をJSON形式に変換して返却
        return Utility.createResponse(log, result, jsonData, Level.INFO);
    }

}
