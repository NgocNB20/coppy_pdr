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
import jp.co.hankyuhanshin.itec.webapi.pdr.request.subscription.RegularEntryCheckRequest;
import jp.co.hankyuhanshin.itec.webapi.pdr.request.subscription.RegularEntryRequest;
import jp.co.hankyuhanshin.itec.webapi.pdr.request.subscription.RegularGetEntryRequest;
import jp.co.hankyuhanshin.itec.webapi.pdr.request.subscription.RegularGetInformationRequest;
import jp.co.hankyuhanshin.itec.webapi.pdr.request.subscription.RegularGetItemRequest;
import jp.co.hankyuhanshin.itec.webapi.pdr.request.subscription.RegularMailSendRequest;
import jp.co.hankyuhanshin.itec.webapi.pdr.request.subscription.RegularSetInformationRequest;
import jp.co.hankyuhanshin.itec.webapi.pdr.response.Result;
import jp.co.hankyuhanshin.itec.webapi.pdr.response.ResultInfo;
import jp.co.hankyuhanshin.itec.webapi.pdr.response.subscription.RegularEntryCheckResponse;
import jp.co.hankyuhanshin.itec.webapi.pdr.response.subscription.RegularGetEntryResponse;
import jp.co.hankyuhanshin.itec.webapi.pdr.response.subscription.RegularGetInformationResponse;
import jp.co.hankyuhanshin.itec.webapi.pdr.response.subscription.RegularGetItemResponse;
import jp.co.hankyuhanshin.itec.webapi.pdr.response.subscription.RegularMailSendResponse;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * WEB-API連携 定期便系WebAPI<br/>
 * <pre>
 * ・定期便登録内容取得
 * ・お届け曜日・支払方法取得
 * ・お届け曜日・支払方法更新
 * ・定期便登録
 * ・定期便対象商品リスト取得
 * </pre>
 *
 * @author tt32117
 */
@Path("/subscription")
public class Subscription {

    /** サーブレットコンテキスト */
    @Context
    private ServletContext context;

    /**
     * 定期便登録内容取得<br/>
     *
     * @param jsonData JSON形式の取得条件
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
     *   <li>[リスト]情報:info</li>
     *   <ul>
     *     <li>詳細番号:detailNo</li>
     *     <li>申込商品:goodsCode</li>
     *     <li>数量:quantity</li>
     *     <li>サイクル:cycle</li>
     *     <li>前回出荷日:preShipmentDate</li>
     *     <li>次回お届け予定日:nextDeliveryDate</li>
     *     <li>お届け先顧客番号:receiveCustomerNo</li>
     *     <li>お届け先事業所名:receiveOfficeName</li>
     *     <li>お届け先事業所名フリガナ:receiveOfficeKana</li>
     *     <li>お届け先郵便番号:receiveZipcode</li>
     *     <li>お届け先住所(都道府県・市区町村):receiveAddress1</li>
     *     <li>お届け先住所(丁目・番地):receiveAddress2</li>
     *     <li>お届け先住所(建物名・部屋番号):receiveAddress3</li>
     *     <li>お届け先住所(方書1):receiveAddress4</li>
     *     <li>お届け先住所(方書2):receiveAddress5</li>
     *     <li>送料コメント:carriageMemo</li>
     *     <li>出荷準備中フラグ:prepareFlag</li>
     *     <li>出荷可否区分:shippableType</li>
     *     <li>後継品フラグ:replaceFlag</li>
     *     <li>後継品申込商品:replaceGoodsCode</li>
     *     <li>商品代金:goodsPriceTotal</li>
     *     <li>消費税:tax</li>
     *     <li>送料:carriage</li>
     *     <li>ご請求金額（目安）:billPriceTotal</li>
     *   </ul>
     * </ul>
     */
    @POST
    @Path("/RegularGetEntry")
    @Produces({"application/json"})
    @Consumes({"application/json"})
    public Response regularGetEntry(String jsonData) {

        Logger log = LogManager.getLogger("regularGetEntry");

        // 返却情報
        Result result = new Result();
        ResultInfo resultInfo = new ResultInfo();
        ApiInfo apiInfo = new ApiInfo("regularGetEntry", "定期便登録内容取得[Subscription/RegularGetEntry]", context, log);

        Connection conn = null;
        CallableStatement cs = null;

        try {

            // INパラメータをログに出力する
            log.info("[IN]" + jsonData);

            // POSTされたJSONをデコードし、専用のオブジェクトに変換する
            RegularGetEntryRequest req = JSON.decode(jsonData, RegularGetEntryRequest.class);

            // エラーキー情報を共通セット
            Utility.setErrorKeyInfo(apiInfo, jsonData);

            // JDBC接続
            String driverStr = PropManeger.getConf(apiInfo.getContext(), "db.jdbc.driver");
            Driver driver = (Driver) Class.forName(driverStr).newInstance();
            String connUrl = PropManeger.getConf(apiInfo.getContext(), "db.jdbc.url");
            conn = driver.connect(connUrl, new Properties());

            // パラメータをセットし、ストアドを呼出す
            cs = conn.prepareCall("{call ProcRegularGetEntry(?)}");

            // IN：顧客番号
            if (req.getCustomerNo() == null) {
                cs.setNull(1, java.sql.Types.INTEGER);
            } else {
                cs.setInt(1, Integer.parseInt(req.getCustomerNo()));
            }

            // 登録結果（リターンコード）
            // cs.registerOutParameter(2, java.sql.Types.SMALLINT);
            cs.executeQuery();
            Integer status = 0;

            // 処理結果を取得してしまうとResultSetがクリアされてしまう為
            // 前もって取得しておく必要がある
            ResultSet rs = cs.getResultSet();
            List<Object> resInfoList = new ArrayList<>();

            while (rs != null && rs.next()) {
                RegularGetEntryResponse res = new RegularGetEntryResponse();
                // OUT:詳細番号
                res.setDetailNo(rs.getString(1));
                // OUT:申込商品
                res.setGoodsCode(rs.getString(2));
                // OUT:数量
                res.setQuantity(rs.getString(3));
                // OUT:サイクル
                res.setCycle(rs.getString(4));
                // OUT:前回出荷日
                res.setPreShipmentDate(rs.getTimestamp(5));
                // OUT:次回お届け予定日
                res.setNextDeliveryDate(rs.getTimestamp(6));
                // OUT:お届け先顧客番号
                res.setReceiveCustomerNo(rs.getString(7));
                // OUT:お届け先事業所名
                res.setReceiveOfficeName(rs.getString(8));
                // OUT:お届け先事業所名フリガナ
                res.setReceiveOfficeKana(rs.getString(9));
                // OUT:お届け先郵便番号
                res.setReceiveZipcode(rs.getString(10));
                // OUT:お届け先住所(都道府県・市区郡)
                res.setReceiveAddress1(rs.getString(11));
                // OUT:お届け先住所(町村・番地)
                res.setReceiveAddress2(rs.getString(12));
                // OUT:お届け先住所(マンション・建物名)
                res.setReceiveAddress3(rs.getString(13));
                // OUT:お届け先住所(部屋番号)
                res.setReceiveAddress4(rs.getString(14));
                // OUT:お届け先住所(部屋番号)
                res.setReceiveAddress5(rs.getString(15));
                // OUT:送料コメント
                res.setCarriageMemo(rs.getString(16));
                // OUT:出荷準備中フラグ
                res.setPrepareFlag(rs.getString(17));
                // OUT:出荷可否区分
                res.setShippableType(rs.getString(18));
                // OUT:後継品フラグ
                res.setReplaceFlag(rs.getString(19));
                // OUT:後継品申込商品
                res.setReplaceGoodsCode(rs.getString(20));
                // OUT:商品代金
                res.setGoodsPriceTotal(rs.getBigDecimal(21));
                // OUT:消費税
                res.setTax(rs.getBigDecimal(22));
                // OUT:送料
                res.setCarriage(rs.getBigDecimal(23));
                // OUT:ご請求金額合計（目安）
                res.setBillPriceTotal(rs.getBigDecimal(24));

                resInfoList.add(res);
                // ステータス
                status = rs.getInt(25);
            }

            // ストアドからの返却値
            apiInfo.setStoredName("ProcRegularGetEntry");
            apiInfo.setStoredCode(status);
            Utility.makeResult(apiInfo, result, resultInfo, resInfoList);

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
     * お届け曜日・支払方法取得<br/>
     *
     * @param jsonData JSON形式の取得条件
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
     *   <li>[リスト]情報:info</li>
     *   <ul>
     *     <li>お届け曜日:weekday</li>
     *     <li>支払方法:paymentType</li>
     *     <li>顧客カードID:customerCardId</li>
     *   </ul>
     * </ul>
     */
    @POST
    @Path("/RegularGetInformation")
    @Produces({"application/json"})
    @Consumes({"application/json"})
    public Response regularGetInformation(String jsonData) {

        Logger log = LogManager.getLogger("regularGetInformation");

        // 返却情報
        Result result = new Result();
        ResultInfo resultInfo = new ResultInfo();
        ApiInfo apiInfo = new ApiInfo("regularGetInformation", "お届け曜日・支払方法取得[Subscription/RegularGetInformation]",
                                      context, log
        );

        Connection conn = null;
        CallableStatement cs = null;

        try {

            // INパラメータをログに出力する
            log.info("[IN]" + jsonData);

            // POSTされたJSONをデコードし、専用のオブジェクトに変換する
            RegularGetInformationRequest req = JSON.decode(jsonData, RegularGetInformationRequest.class);

            // エラーキー情報を共通セット
            Utility.setErrorKeyInfo(apiInfo, jsonData);

            // JDBC接続
            String driverStr = PropManeger.getConf(apiInfo.getContext(), "db.jdbc.driver");
            Driver driver = (Driver) Class.forName(driverStr).newInstance();
            String connUrl = PropManeger.getConf(apiInfo.getContext(), "db.jdbc.url");
            conn = driver.connect(connUrl, new Properties());

            // パラメータをセットし、ストアドを呼出す
            cs = conn.prepareCall("{call ProcRegularGetInformation(?)}");

            // IN：顧客番号
            if (req.getCustomerNo() == null) {
                cs.setNull(1, java.sql.Types.INTEGER);
            } else {
                cs.setInt(1, Integer.parseInt(req.getCustomerNo()));
            }

            // 登録結果（リターンコード）
            // cs.registerOutParameter(2, java.sql.Types.SMALLINT);
            cs.executeQuery();
            Integer status = 0;

            // 処理結果を取得してしまうとResultSetがクリアされてしまう為
            // 前もって取得しておく必要がある
            ResultSet rs = cs.getResultSet();
            List<Object> resInfoList = new ArrayList<>();

            while (rs != null && rs.next()) {
                RegularGetInformationResponse res = new RegularGetInformationResponse();
                // OUT:お届け曜日
                res.setWeekday(rs.getString(1));
                // OUT:支払方法
                res.setPaymentType(rs.getString(2));
                // OUT:顧客カードID
                res.setCustomerCardId(rs.getString(3));
                resInfoList.add(res);
                // ステータス
                status = rs.getInt(4);
            }

            // ストアドからの返却値
            apiInfo.setStoredName("ProcRegularGetInformation");
            apiInfo.setStoredCode(status);
            Utility.makeResult(apiInfo, result, resultInfo, resInfoList);

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
     * お届け曜日・支払方法更新<br/>
     *
     * @param jsonData JSON形式の取得条件
     * <ul>
     *   <li>顧客番号:customerNo</li>
     *   <li>お届け曜日:weekday</li>
     *   <li>支払方法:paymentType</li>
     *   <li>顧客カードID:customerCardId</li>
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
    @Path("/RegularSetInformation")
    @Produces({"application/json"})
    @Consumes({"application/json"})
    public Response regularSetInformation(String jsonData) {

        Logger log = LogManager.getLogger("regularSetInformation");

        // 返却情報
        Result result = new Result();
        ResultInfo resultInfo = new ResultInfo();
        ApiInfo apiInfo = new ApiInfo("regularSetInformation", "お届け曜日・支払方法更新[Subscription/RegularSetInformation]",
                                      context, log
        );

        Connection conn = null;
        CallableStatement cs = null;

        try {

            // INパラメータをログに出力する
            log.info("[IN]" + jsonData);

            // POSTされたJSONをデコードし、専用のオブジェクトに変換する
            RegularSetInformationRequest req = JSON.decode(jsonData, RegularSetInformationRequest.class);

            // エラーキー情報を共通セット
            Utility.setErrorKeyInfo(apiInfo, jsonData);

            // JDBC接続
            String driverStr = PropManeger.getConf(apiInfo.getContext(), "db.jdbc.driver");
            Driver driver = (Driver) Class.forName(driverStr).newInstance();
            String connUrl = PropManeger.getConf(apiInfo.getContext(), "db.jdbc.url");
            conn = driver.connect(connUrl, new Properties());

            conn.setAutoCommit(false);

            // パラメータをセットし、ストアドを呼出す
            cs = conn.prepareCall("{call ProcRegularSetInformation(?,?,?,?,?)}");

            // IN：顧客番号
            if (req.getCustomerNo() == null) {
                cs.setNull(1, java.sql.Types.INTEGER);
            } else {
                cs.setInt(1, Integer.parseInt(req.getCustomerNo()));
            }
            // IN：お届け曜日
            if (req.getWeekday() == null) {
                cs.setNull(2, java.sql.Types.SMALLINT);
            } else {
                cs.setShort(2, Short.parseShort(req.getWeekday()));
            }
            // IN：支払方法
            if (req.getPaymentType() == null) {
                cs.setNull(3, java.sql.Types.SMALLINT);
            } else {
                cs.setShort(3, Short.parseShort(req.getPaymentType()));
            }
            // IN：顧客カードID
            cs.setString(4, req.getCustomerCardId());

            // 更新結果（リターンコード）
            cs.registerOutParameter(5, java.sql.Types.SMALLINT);
            cs.executeUpdate();

            // ストアドからの返却値
            apiInfo.setStoredName("ProcRegularSetInformation");
            apiInfo.setStoredCode(cs.getShort(5));
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

    /**
     * 定期便登録内容確認<br/>
     *
     * @param jsonData JSON形式の取得条件
     * <ul>
     *   <li>顧客番号:customerNo</li>
     *   <li>区分:type</li>
     *   <li>申込商品:goodsCode</li>
     *   <li>数量:quantity</li>
     *   <li>サイクル:cycle</li>
     *   <li>お届け先顧客番号:receiveCustomerNo</li>
     * </ul>
     * @return JSON形式の処理結果
     * <ul>
     *   <li>処理結果:result</li>
     *   <ul>
     *     <li>ステータス:status</li>
     *     <li>メッセージ:message</li>
     *   </ul>
     *   <li>[リスト]情報:info</li>
     *   <ul>
     *     <li>初回お届け日:firstDeliveryDate</li>
     *     <li>商品代金:goodsPriceTotal</li>
     *     <li>消費税:tax</li>
     *     <li>送料:carriage</li>
     *     <li>送料コメント:carriageMemo</li>
     *     <li>請求金額:billPriceTotal</li>
     *   </ul>
     * </ul>
     */
    @POST
    @Path("/RegularEntryCheck")
    @Produces({"application/json"})
    @Consumes({"application/json"})
    public Response regularEntryCheck(String jsonData) {

        Logger log = LogManager.getLogger("regularEntryCheck");

        // 返却情報
        Result result = new Result();
        ResultInfo resultInfo = new ResultInfo();
        ApiInfo apiInfo = new ApiInfo("regularEntryCheck", "定期便登録内容確認[Subscription/RegularEntryCheck]", context, log);

        Connection conn = null;
        CallableStatement cs = null;

        try {

            // INパラメータをログに出力する
            log.info("[IN]" + jsonData);

            // POSTされたJSONをデコードし、専用のオブジェクトに変換する
            RegularEntryCheckRequest req = JSON.decode(jsonData, RegularEntryCheckRequest.class);

            // エラーキー情報を共通セット
            Utility.setErrorKeyInfo(apiInfo, jsonData);

            // JDBC接続
            String driverStr = PropManeger.getConf(apiInfo.getContext(), "db.jdbc.driver");
            Driver driver = (Driver) Class.forName(driverStr).newInstance();
            String connUrl = PropManeger.getConf(apiInfo.getContext(), "db.jdbc.url");
            conn = driver.connect(connUrl, new Properties());

            // パラメータをセットし、ストアドを呼出す
            cs = conn.prepareCall("{call ProcRegularEntryCheck(?,?,?,?,?,?)}");

            // IN：顧客番号
            if (req.getCustomerNo() == null) {
                cs.setNull(1, java.sql.Types.INTEGER);
            } else {
                cs.setInt(1, Integer.parseInt(req.getCustomerNo()));
            }
            // IN：区分
            if (req.getType() == null) {
                cs.setNull(2, java.sql.Types.SMALLINT);
            } else {
                cs.setShort(2, Short.parseShort(req.getType()));
            }
            // IN:申込商品
            cs.setString(3, req.getGoodsCode());
            // IN:数量
            if (req.getQuantity() == null) {
                cs.setNull(4, java.sql.Types.INTEGER);
            } else {
                cs.setInt(4, Integer.parseInt(req.getQuantity()));
            }
            // IN:サイクル
            if (req.getCycle() == null) {
                cs.setNull(5, java.sql.Types.SMALLINT);
            } else {
                cs.setShort(5, Short.parseShort(req.getCycle()));
            }
            // IN:お届け先顧客番号
            if (req.getReceiveCustomerNo() == null) {
                cs.setNull(6, java.sql.Types.INTEGER);
            } else {
                cs.setInt(6, Integer.parseInt(req.getReceiveCustomerNo()));
            }
            // 登録結果（リターンコード）
            // cs.registerOutParameter(7, java.sql.Types.SMALLINT);
            cs.executeQuery();
            Integer status = 0;
            // 処理結果を取得してしまうとResultSetがクリアされてしまう為
            // 前もって取得しておく必要がある
            ResultSet rs = cs.getResultSet();
            List<Object> resInfoList = new ArrayList<>();

            while (rs != null && rs.next()) {
                RegularEntryCheckResponse res = new RegularEntryCheckResponse();
                // OUT:初回お届け日
                res.setFirstDeliveryDate(rs.getTimestamp(1));
                // OUT:商品代金
                res.setGoodsPriceTotal(rs.getBigDecimal(2));
                // OUT:消費税
                res.setTax(rs.getBigDecimal(3));
                // OUT:送料
                res.setCarriage(rs.getBigDecimal(4));
                // OUT:送料コメント
                res.setCarriageMemo(rs.getString(5));
                // OUT:請求金額
                res.setBillPriceTotal(rs.getBigDecimal(6));

                resInfoList.add(res);
                // ステータス
                status = rs.getInt(7);
            }

            // ストアドからの返却値
            apiInfo.setStoredName("ProcRegularEntryCheck");
            apiInfo.setStoredCode(status);
            Utility.makeResult(apiInfo, result, resultInfo, resInfoList);

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
     * 定期便登録<br/>
     *
     * @param jsonData JSON形式の取得条件
     * <ul>
     *   <li>顧客番号:customerNo</li>
     *   <li>区分:type</li>
     *   <li>詳細番号:detailNo</li>
     *   <li>申込商品:goodsCode</li>
     *   <li>数量:quantity</li>
     *   <li>サイクル:cycle</li>
     *   <li>スキップフラグ:skipFlag</li>
     *   <li>お届け先顧客番号:receiveCustomerNo</li>
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
    @Path("/RegularEntry")
    @Produces({"application/json"})
    @Consumes({"application/json"})
    public Response regularEntry(String jsonData) {

        // 正常終了（スキップ指定済）
        final int skipsetting = 2;

        Logger log = LogManager.getLogger("regularEntry");

        // 返却情報
        Result result = new Result();
        ResultInfo resultInfo = new ResultInfo();
        ApiInfo apiInfo = new ApiInfo("regularEntry", "定期便登録[Subscription/RegularEntry]", context, log);

        Connection conn = null;
        CallableStatement cs = null;

        try {

            // INパラメータをログに出力する
            log.info("[IN]" + jsonData);

            // POSTされたJSONをデコードし、専用のオブジェクトに変換する
            RegularEntryRequest req = JSON.decode(jsonData, RegularEntryRequest.class);

            // エラーキー情報を共通セット
            Utility.setErrorKeyInfo(apiInfo, jsonData);

            // JDBC接続
            String driverStr = PropManeger.getConf(apiInfo.getContext(), "db.jdbc.driver");
            Driver driver = (Driver) Class.forName(driverStr).newInstance();
            String connUrl = PropManeger.getConf(apiInfo.getContext(), "db.jdbc.url");
            conn = driver.connect(connUrl, new Properties());

            conn.setAutoCommit(false);

            // パラメータをセットし、ストアドを呼出す
            cs = conn.prepareCall("{call ProcRegularEntry(?,?,?,?,?,?,?,?,?)}");

            // IN：顧客番号
            if (req.getCustomerNo() == null) {
                cs.setNull(1, java.sql.Types.INTEGER);
            } else {
                cs.setInt(1, Integer.parseInt(req.getCustomerNo()));
            }
            // IN：区分
            if (req.getType() == null) {
                cs.setNull(2, java.sql.Types.SMALLINT);
            } else {
                cs.setShort(2, Short.parseShort(req.getType()));
            }
            // IN:詳細番号
            if (req.getDetailNo() == null) {
                cs.setNull(3, java.sql.Types.INTEGER);
            } else {
                cs.setInt(3, Integer.parseInt(req.getDetailNo()));
            }
            // IN:申込商品
            cs.setString(4, req.getGoodsCode());
            // IN:数量
            if (req.getQuantity() == null) {
                cs.setNull(5, java.sql.Types.INTEGER);
            } else {
                cs.setInt(5, Integer.parseInt(req.getQuantity()));
            }
            // IN:サイクル
            if (req.getCycle() == null) {
                cs.setNull(6, java.sql.Types.SMALLINT);
            } else {
                cs.setShort(6, Short.parseShort(req.getCycle()));
            }
            // IN:スキップフラグ
            if (req.getSkipFlag() == null) {
                cs.setNull(7, java.sql.Types.SMALLINT);
            } else {
                cs.setShort(7, Short.parseShort(req.getSkipFlag()));
            }
            // IN:お届け先顧客番号
            if (req.getReceiveCustomerNo() == null) {
                cs.setNull(8, java.sql.Types.INTEGER);
            } else {
                cs.setInt(8, Integer.parseInt(req.getReceiveCustomerNo()));
            }

            // 更新結果（リターンコード）
            cs.registerOutParameter(9, java.sql.Types.SMALLINT);
            cs.executeUpdate();

            // ストアドからの返却値
            apiInfo.setStoredName("ProcRegularEntry");
            apiInfo.setStoredCode(cs.getShort(9));

            // ストアドからの返却値が正常以外の場合
            if (Constant.STORED_OK != apiInfo.getStoredCode() && skipsetting != apiInfo.getStoredCode()) {

                // ストアドからのエラーコードをそのまま返却
                resultInfo.setMessage(apiInfo.getErrorMsg());

                Utility.errorLogAndMail(apiInfo);

            }
            resultInfo.setStatus(apiInfo.getStoredCode());
            result.setResult(resultInfo);

            if (Constant.STORED_OK != apiInfo.getStoredCode() && skipsetting != apiInfo.getStoredCode()) {
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

    /**
     * 定期便対象商品リスト取得<br/>
     * @param jsonData 取得条件
     * <ul>
     *   <li>指定日:designationDate</li>
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
     *     <li>申込商品:goodsCode</li>
     *     <li>定期便対象区分:targetType</li>
     *     <li>受付可否区分:permissionType</li>
     *     <li>おススメフラグ:recommendFlag</li>
     *   </ul>
     * </ul>
     */
    @POST
    @Path("/RegularGetItem")
    @Produces({"application/json"})
    @Consumes({"application/json"})
    public Response regularGetItem(String jsonData) {

        Logger log = LogManager.getLogger("regularGetItem");

        // 返却情報
        Result result = new Result();
        ResultInfo resultInfo = new ResultInfo();
        ApiInfo apiInfo = new ApiInfo("regularGetItem", "定期便対象商品リスト取得[Subscription/RegularGetItem]", context, log);

        Connection conn = null;
        CallableStatement cs = null;

        try {

            // INパラメータをログに出力する
            log.info("[IN]" + jsonData);

            // POSTされたJSONをデコードし、専用のオブジェクトに変換する
            RegularGetItemRequest req = JSON.decode(jsonData, RegularGetItemRequest.class);

            // エラーキー情報を共通セット
            Utility.setErrorKeyInfo(apiInfo, jsonData);

            // JDBC接続
            String driverStr = PropManeger.getConf(apiInfo.getContext(), "db.jdbc.driver");
            Driver driver = (Driver) Class.forName(driverStr).newInstance();
            String connUrl = PropManeger.getConf(apiInfo.getContext(), "db.jdbc.url");
            conn = driver.connect(connUrl, new Properties());

            // パラメータをセットし、ストアドを呼出す
            cs = conn.prepareCall("{call ProcRegularGetItem(?)}");
            // 定期便商品
            cs.setTimestamp(1, req.getDesignationDate());
            // 取得結果（リターンコード）
            // cs.registerOutParameter(2, java.sql.Types.SMALLINT);
            cs.executeQuery();
            Integer status = 0;
            // 処理結果を取得してしまうとResultSetがクリアされてしまう為
            // 前もって取得しておく必要がある
            ResultSet rs = cs.getResultSet();
            List<Object> infoList = new ArrayList<>();
            while (rs != null && rs.next()) {
                RegularGetItemResponse info = new RegularGetItemResponse();
                // 申込商品
                info.setGoodsCode(rs.getString(1));
                // 定期便対象区分
                info.setTargetType(rs.getString(2));
                // 受付可否区分
                info.setPermissionType(rs.getString(3));
                // おすすめフラグ
                info.setRecommendFlag(rs.getString(4));

                infoList.add(info);
                // ステータス
                status = rs.getInt(5);
            }

            // ストアドからの返却値
            apiInfo.setStoredName("ProcRegularGetItem");
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
     * 定期便出荷情報メール送信<br/>
     * @param jsonData 取得条件
     * <ul>
     *   <li>指定日:designationDate</li>
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
     *     <li>メール区分:mailType</li>
     *     <li>顧客番号:customerNo</li>
     *     <li>事業所名:officeName</li>
     *     <li>支払方法:paymentName</li>
     *     <li>配送方法:deliveryName</li>
     *     <li>配達指定日:deliveryDesignatedDay</li>
     *     <li>配達指定時間:deliveryDesignatedTime</li>
     *     <li>配送確認URL:deliveryComfirmURL</li>
     *     <li>送り状番号:invoiceNo</li>
     *     <li>次回お届け予定日:nextDeliveryDate</li>
     *     <li>お届け顧客番号:receiveCustomerNo</li>
     *     <li>お届け先事業所名:receiveOfficeName</li>
     *     <li>お届け先郵便番号:receiveZipcode</li>
     *     <li>お届け先住所(都道府県・市区町村):receiveAddress1</li>
     *     <li>お届け先住所(丁目・番地):receiveAddress2</li>
     *     <li>お届け先住所(建物名・部屋番号):receiveAddress3</li>
     *     <li>お届け先住所(方書1):receiveAddress4</li>
     *     <li>お届け先住所(方書2):receiveAddress5</li>
     *     <li>小計:subTotalPrice</li>
     *     <li>送料:shippingPrice</li>
     *     <li>消費税:taxPrice</li>
     *     <li>合計金額:totalPrice</li>
     *     <li>詳細番号:detailNo</li>
     *     <li>申込商品:goodsCode</li>
     *     <li>商品名:goodsName</li>
     *     <li>数量:quantity</li>
     *     <li>単位:unit</li>
     *     <li>受注可否区分:permissionType</li>
     *     <li>出荷可否区分:shippableType</li>
     *     <li>後継品フラグ:replaceFlag</li>
     *     <li>後継品申込商品:replaceGoodsCode</li>
     *     <li>後継品商品名:replaceGoodsName</li>
     *     <li>コメント:comment</li>
     *     <li>メールアドレス:mailAddress</li>
     *   </ul>
     * </ul>
     */
    @POST
    @Path("/RegularMailSend")
    @Produces({"application/json"})
    @Consumes({"application/json"})
    public Response regularMailSend(String jsonData) {

        Logger log = LogManager.getLogger("regularMailSend");

        // 返却情報
        Result result = new Result();
        ResultInfo resultInfo = new ResultInfo();
        ApiInfo apiInfo = new ApiInfo("regularMailSend", "出荷情報メール送信[Subscription/RegularMailSend]", context, log);

        Connection conn = null;
        CallableStatement cs = null;

        try {

            // INパラメータをログに出力する
            log.info("[IN]" + jsonData);

            // POSTされたJSONをデコードし、専用のオブジェクトに変換する
            RegularMailSendRequest req = JSON.decode(jsonData, RegularMailSendRequest.class);

            // エラーキー情報を共通セット
            Utility.setErrorKeyInfo(apiInfo, jsonData);

            // JDBC接続
            String driverStr = PropManeger.getConf(apiInfo.getContext(), "db.jdbc.driver");
            Driver driver = (Driver) Class.forName(driverStr).newInstance();
            String connUrl = PropManeger.getConf(apiInfo.getContext(), "db.jdbc.url");
            conn = driver.connect(connUrl, new Properties());

            // パラメータをセットし、ストアドを呼出す
            cs = conn.prepareCall("{call ProcRegularMailSend(?)}");
            // 指定日
            cs.setTimestamp(1, req.getDesignationDate());
            // 取得結果（リターンコード）
            // cs.registerOutParameter(2, java.sql.Types.SMALLINT);
            cs.executeQuery();
            Integer status = 0;

            // 処理結果を取得してしまうとResultSetがクリアされてしまう為
            // 前もって取得しておく必要がある
            ResultSet rs = cs.getResultSet();
            List<Object> infoList = new ArrayList<>();
            RegularMailSendResponse info;

            while (rs != null && rs.next()) {
                info = new RegularMailSendResponse();
                // メール区分
                info.setMailType(rs.getString(1));
                // 顧客番号
                info.setCustomerNo(rs.getInt(2));
                // 受注番号
                info.setOrderNo(rs.getInt(3));
                // 事業所名
                info.setOfficeName(rs.getString(4));
                // 支払方法
                info.setPaymentName(rs.getString(5));
                // 配送方法
                info.setDeliveryName(rs.getString(6));
                // 配達指定日
                info.setDeliveryDesignatedDay(rs.getTimestamp(7));
                // 配達指定時間
                info.setDeliveryDesignatedTime(rs.getString(8));
                // 配送確認URL
                info.setDeliveryComfirmURL(rs.getString(9));
                // 送り状番号
                info.setInvoiceNo(rs.getString(10));
                // 次回お届け予定日
                info.setNextDeliveryDate(rs.getTimestamp(11));
                // お届け顧客番号
                info.setReceiveCustomerNo(rs.getInt(12));
                // お届け先事業所名
                info.setReceiveOfficeName(rs.getString(13));
                // お届け先郵便番号
                info.setReceiveZipcode(rs.getString(14));
                // お届け先住所(都道府県・市区町村)
                info.setReceiveAddress1(rs.getString(15));
                // お届け先住所(丁目・番地)
                info.setReceiveAddress2(rs.getString(16));
                // お届け先住所(建物名・部屋番号)
                info.setReceiveAddress3(rs.getString(17));
                // お届け先住所(方書1)
                info.setReceiveAddress4(rs.getString(18));
                // お届け先住所(方書2)
                info.setReceiveAddress5(rs.getString(19));
                // 小計
                info.setSubTotalPrice(rs.getString(20));
                // 送料
                info.setShippingPrice(rs.getString(21));
                // 消費税
                info.setTaxPrice(rs.getString(22));
                // 合計金額
                info.setTotalPrice(rs.getString(23));
                // 詳細番号
                info.setDetailNo(rs.getInt(24));
                // 申込商品
                info.setGoodsCode(rs.getString(25));
                // 商品名
                info.setGoodsName(rs.getString(26));
                // 数量
                info.setQuantity(rs.getInt(27));
                // 単位
                info.setUnit(rs.getString(28));
                // 金額
                info.setPrice(rs.getString(29));
                // 受付可否区分
                info.setPermissionType(rs.getString(30));
                // 出荷可否区分
                info.setShippableType(rs.getString(31));
                // 後継品フラグ
                info.setReplaceFlag(rs.getString(32));
                // 後継品申込商品
                info.setReplaceGoodsCode(rs.getString(33));
                // 後継品商品名
                info.setReplaceGoodsName(rs.getString(34));
                // コメント
                info.setComment(rs.getString(35));
                // メールアドレス
                info.setMailAddress(rs.getString(36));

                infoList.add(info);
                // ステータス
                status = rs.getInt(37);
            }

            // ストアドからの返却値
            apiInfo.setStoredName("ProcRegularMailSend");
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
}
