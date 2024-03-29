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
import jp.co.hankyuhanshin.itec.webapi.pdr.request.goods.GetOtherGoodsRequest;
import jp.co.hankyuhanshin.itec.webapi.pdr.request.goods.GetPriceRequest;
import jp.co.hankyuhanshin.itec.webapi.pdr.request.goods.GetQuantityDiscountRequest;
import jp.co.hankyuhanshin.itec.webapi.pdr.request.goods.GetReStockRequest;
import jp.co.hankyuhanshin.itec.webapi.pdr.request.goods.GetReserveRequest;
import jp.co.hankyuhanshin.itec.webapi.pdr.request.goods.GetSaleCheckRequest;
import jp.co.hankyuhanshin.itec.webapi.pdr.request.goods.GetSaleRequest;
import jp.co.hankyuhanshin.itec.webapi.pdr.request.goods.GetSeriesPriceRequest;
import jp.co.hankyuhanshin.itec.webapi.pdr.request.goods.GetStockRequest;
import jp.co.hankyuhanshin.itec.webapi.pdr.response.Result;
import jp.co.hankyuhanshin.itec.webapi.pdr.response.ResultInfo;
import jp.co.hankyuhanshin.itec.webapi.pdr.response.goods.GetOtherGoodsResponse;
import jp.co.hankyuhanshin.itec.webapi.pdr.response.goods.GetPriceResponse;
import jp.co.hankyuhanshin.itec.webapi.pdr.response.goods.GetQuantityDiscountCustomerSalePriceResponse;
import jp.co.hankyuhanshin.itec.webapi.pdr.response.goods.GetQuantityDiscountPriceResponse;
import jp.co.hankyuhanshin.itec.webapi.pdr.response.goods.GetQuantityDiscountResponse;
import jp.co.hankyuhanshin.itec.webapi.pdr.response.goods.GetQuantityDiscountSalePriceResponse;
import jp.co.hankyuhanshin.itec.webapi.pdr.response.goods.GetReStockResponse;
import jp.co.hankyuhanshin.itec.webapi.pdr.response.goods.GetReserveResponse;
import jp.co.hankyuhanshin.itec.webapi.pdr.response.goods.GetSaleCheckResponse;
import jp.co.hankyuhanshin.itec.webapi.pdr.response.goods.GetSaleResponse;
import jp.co.hankyuhanshin.itec.webapi.pdr.response.goods.GetSeriesPriceResponse;
import jp.co.hankyuhanshin.itec.webapi.pdr.response.goods.GetStockResponse;
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
import java.math.BigDecimal;
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
 * PDR#15 WEB-API連携 商品系WebAPI<br/>
 * <pre>
 * ・商品在庫数取得
 * ・取りおき情報取得
 * ・数量割引情報取得
 * ・価格情報取得
 * ・シリーズ商品価格情報取得
 * </pre>
 * @author k-katoh
 */
@Path("/goods")
public class Goods {

    /** サーブレットコンテキスト */
    @Context
    private ServletContext context;

    /**
     * 商品在庫数取得<br/>
     * @param jsonData 取得条件
     * <ul>
     *   <li>申込商品:goodsCode（パイプ区切り）</li>
     *   <li>数量:quantity</li>
     *   <li>顧客番号:customerNo</li>
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
     *     <li>商品コード:goodsCode</li>
     *     <li>在庫数:stockQuantity</li>
     *     <li>販売可否判定結果:saleYesNo</li>
     *     <li>販売不可メッセージ:saleNgMessage</li>
     *     <li>入荷予定日:stockDate</li>
     *     <li>入荷次第お届け可否:deliveryYesNo</li>
     *   </ul>
     * </ul>
     */
    @POST
    @Path("/GetStock")
    @Produces({"application/json"})
    @Consumes({"application/json"})
    public Response getStock(String jsonData) {

        Logger log = LogManager.getLogger("getStock");

        // 返却情報
        Result result = new Result();
        ResultInfo resultInfo = new ResultInfo();
        ApiInfo apiInfo = new ApiInfo("getStock", "商品在庫数取得[Goods/GetStock]", context, log);

        Connection conn = null;
        CallableStatement cs = null;

        try {

            // INパラメータをログに出力する
            log.info("[IN]" + jsonData);

            // POSTされたJSONをデコードし、専用のオブジェクトに変換する
            GetStockRequest req = JSON.decode(jsonData, GetStockRequest.class);

            // エラーキー情報を共通セット
            Utility.setErrorKeyInfo(apiInfo, jsonData);

            // JDBC接続
            String driverStr = PropManeger.getConf(apiInfo.getContext(), "db.jdbc.driver");
            Driver driver = (Driver) Class.forName(driverStr).newInstance();
            String connUrl = PropManeger.getConf(apiInfo.getContext(), "db.jdbc.url");
            conn = driver.connect(connUrl, new Properties());

            // パラメータをセットし、ストアドを呼出す
            cs = conn.prepareCall("{call ProcGetStock(?,?,?)}");
            // 申込商品
            cs.setString(1, req.getGoodsCode());
            // 数量
            cs.setString(2, req.getquantity());
            // 顧客番号
            cs.setString(3, req.getCustomerNo());
            // 取得結果（リターンコード）
            // cs.registerOutParameter(4, java.sql.Types.SMALLINT);
            log.info("ProcGetStock呼出（開始）：" + new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()));
            cs.executeQuery();
            log.info("ProcGetStock呼出（終了）：" + new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()));
            Integer status = 0;

            // 処理結果を取得してしまうとResultSetがクリアされてしまう為
            // 前もって取得しておく必要がある
            ResultSet rs = cs.getResultSet();
            List<Object> infoList = new ArrayList<>();
            while (rs != null && rs.next()) {
                GetStockResponse info = new GetStockResponse();
                // 申込商品
                info.setGoodsCode(rs.getString(1));
                // 在庫数
                info.setStockQuantity(rs.getString(2));
                // 販売可否判定結果
                info.setSaleYesNo(rs.getInt(3));
                // 販売不可メッセージ
                info.setSaleNgMessage(rs.getString(4));
                // 入荷予定日
                info.setStockDate(rs.getTimestamp(5));
                // 入荷次第お届け可否
                info.setDeliveryYesNo(rs.getString(6));

                // 2023-renew No11 from here
                // 販売制御区分
                info.setSaleControl(rs.getString(7));
                // ステータス
                status = rs.getInt(8);
                // 2023-renew No11 to here

                infoList.add(info);
            }

            // ストアドからの返却値
            apiInfo.setStoredName("ProcGetStock");
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
     *
     * 取りおき情報取得<br/>
     *
     * @param jsonData 取得条件
     * <ul>
     *   <li>顧客番号:customerNo</li>
     *   <li>配送先顧客番号:deliveryCustomerNo</li>
     *   <li>配送先郵便番号:deliveryZipcode</li>
     *   <li>申込商品:goodsCode（パイプ区切り）</li>
     * </ul>
     * @return JSON形式の処理結果
     * <ul>
     *   <li>処理結果:result<li>
     *   <ul>
     *     <li>ステータス:status</li>
     *     <li>メッセージ:message</li>
     *   </ul>
     *   <li>【リスト】情報リスト:info</li>
     *   <ul>
     *     <li>商品コード:goodsCode</li>
     *     <li>取りおき可否:reserveFlag</li>
     *     <li>適用割引:discountFlag</li>
     *     <li>予約可能開始日:possibleReserveFromDay</li>
     *     <li>予約可能終了日:possibleReserveToDay</li>
     *   </ul>
     * </ul>
     */
    @POST
    @Path("/GetReserve")
    @Produces({"application/json"})
    @Consumes({"application/json"})
    public Response getReserve(String jsonData) {

        Logger log = LogManager.getLogger("getReserve");

        // 返却情報
        Result result = new Result();
        ResultInfo resultInfo = new ResultInfo();
        ApiInfo apiInfo = new ApiInfo("getReserve", "取りおき情報取得[Goods/GetReserve]", context, log);

        Connection conn = null;
        CallableStatement cs = null;

        try {

            // INパラメータをログに出力する
            log.info("[IN]" + jsonData);

            // POSTされたJSONをデコードし、専用のオブジェクトに変換する
            GetReserveRequest req = JSON.decode(jsonData, GetReserveRequest.class);

            // エラーキー情報をセット(エラーメールは顧客番号のみ)
            apiInfo.setErrorKeyInfoLog(jsonData);
            apiInfo.setErrorKeyInfoMail("顧客番号=" + req.getCustomerNo());

            // JDBC接続
            String driverStr = PropManeger.getConf(apiInfo.getContext(), "db.jdbc.driver");
            Driver driver = (Driver) Class.forName(driverStr).newInstance();
            String connUrl = PropManeger.getConf(apiInfo.getContext(), "db.jdbc.url");
            conn = driver.connect(connUrl, new Properties());

            // パラメータをセットし、ストアドを呼出す
            cs = conn.prepareCall("{call ProcGetReserve(?,?,?,?)}");
            // 顧客番号
            if (req.getCustomerNo() == null) {
                cs.setNull(1, java.sql.Types.INTEGER);
            } else {
                cs.setInt(1, Integer.parseInt(req.getCustomerNo()));
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

            log.info("ProcGetReserve呼出（開始）：" + new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()));
            cs.executeQuery();
            log.info("ProcGetReserve呼出（終了）：" + new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()));
            Integer status = 0;

            // 処理結果を取得してしまうとResultSetがクリアされてしまう為
            // 前もって取得しておく必要がある
            ResultSet rs = cs.getResultSet();
            List<Object> infoList = new ArrayList<>();
            // 2023-renew No14 from here
            GetReserveResponse info;
            while (rs != null && rs.next()) {
                info = new GetReserveResponse();
                // 商品コード
                info.setGoodsCode(rs.getString(1));
                // 取りおき可否
                info.setReserveFlag(rs.getString(2));
                // 適用割引
                info.setDiscountFlag(rs.getString(3));
                // 予約可能開始日
                info.setPossibleReserveFromDay(rs.getTimestamp(4));
                // 予約可能終了日
                info.setPossibleReserveToDay(rs.getTimestamp(5));
                status = rs.getInt(6);
                infoList.add(info);
            }
            // 2023-renew No14 to here

            // ストアドからの返却値
            apiInfo.setStoredName("ProcGetReserve");
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
     *
     * 数量割引情報取得<br/>
     *
     * @param jsonData 取得条件
     * <ul>
     *   <li>顧客番号:customerNo</li>
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
     *     <li>申込商品:goodsCode</li>
     *     <li>【リスト】価格リスト:priceList</li>
     *     <ul>
     *       <li>価格:price</li>
     *       <li>数量閾値:level</li>
     *     </ul>
     *     <li>【リスト】割引価格リスト</li>
     *     <ul>
     *       <li>割引価格:salePrice</li>
     *       <li>割引数量閾値:saleLevel</li>
     *     </ul>
     *     <li>数量割引適用有無:saleFlag</li>
     *     <li>時価フラグ:marketPriceFlag</li>
     *     <li>【リスト】顧客セール価格リスト</li>
     *     <ul>
     *       <li>顧客セール価格:customerSalePrice</li>
     *       <li>顧客セール数量閾値:customerSaleLevel</li>
     *     </ul>
     *     <li>顧客セール割引適用有無:customerSlaeFlag</li>
     *   </ul>
     * </ul>
     */
    @POST
    @Path("/GetQuantityDiscount")
    @Produces({"application/json"})
    @Consumes({"application/json"})
    public Response getQuantityDiscount(String jsonData) {

        Logger log = LogManager.getLogger("getQuantityDiscount");

        // 返却情報
        Result result = new Result();
        ResultInfo resultInfo = new ResultInfo();
        ApiInfo apiInfo = new ApiInfo("getQuantityDiscount", "数量割引情報取得[Goods/GetQuantityDiscount]", context, log);

        Connection conn = null;
        CallableStatement cs = null;

        try {

            // INパラメータをログに出力する
            log.info("[IN]" + jsonData);

            // POSTされたJSONをデコードし、専用のオブジェクトに変換する
            GetQuantityDiscountRequest req = JSON.decode(jsonData, GetQuantityDiscountRequest.class);

            // エラーキー情報を共通セット
            Utility.setErrorKeyInfo(apiInfo, jsonData);

            // JDBC接続
            String driverStr = PropManeger.getConf(apiInfo.getContext(), "db.jdbc.driver");
            Driver driver = (Driver) Class.forName(driverStr).newInstance();
            String connUrl = PropManeger.getConf(apiInfo.getContext(), "db.jdbc.url");
            conn = driver.connect(connUrl, new Properties());

            // パラメータをセットし、ストアドを呼出す
            cs = conn.prepareCall("{call ProcGetQuantityDiscount(?,?)}");
            // 顧客番号
            if (req.getCustomerNo() == null) {
                cs.setNull(1, java.sql.Types.INTEGER);
            } else {
                cs.setInt(1, Integer.parseInt(req.getCustomerNo()));
            }
            // 申込商品
            cs.setString(2, req.getGoodsCode());
            // 取得結果（リターンコード）
            // cs.registerOutParameter(3, java.sql.Types.SMALLINT);
            log.info("ProcGetQuantityDiscount呼出（開始）：" + new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()));
            cs.executeQuery();
            log.info("ProcGetQuantityDiscount呼出（終了）：" + new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()));
            Integer status = 0;

            // 処理結果を取得してしまうとResultSetがクリアされてしまう為
            // 前もって取得しておく必要がある
            ResultSet rs = cs.getResultSet();

            String goodsCode = null;
            String wkGoodsCode = null;
            List<Object> infoList = new ArrayList<>();
            List<GetQuantityDiscountPriceResponse> priceList = new ArrayList<>();
            List<GetQuantityDiscountSalePriceResponse> salePriceList = new ArrayList<>();
            List<GetQuantityDiscountCustomerSalePriceResponse> customerSalePriceList = new ArrayList<>();
            GetQuantityDiscountResponse info = new GetQuantityDiscountResponse();
            GetQuantityDiscountPriceResponse priceInfo = new GetQuantityDiscountPriceResponse();
            GetQuantityDiscountSalePriceResponse salePriceInfo = new GetQuantityDiscountSalePriceResponse();
            GetQuantityDiscountCustomerSalePriceResponse customerSalePriceInfo =
                            new GetQuantityDiscountCustomerSalePriceResponse();
            while (rs != null && rs.next()) {

                goodsCode = rs.getString(1);

                // 商品が変わった場合
                if (wkGoodsCode != null && !wkGoodsCode.equals(goodsCode)) {
                    // 商品コードが変わった場合は、リストに追加する
                    if (priceList != null && priceList.size() != 0) {
                        info.setPriceList(priceList);
                    }
                    if (salePriceList != null && salePriceList.size() != 0) {
                        info.setSalePriceList(salePriceList);
                    }
                    if (customerSalePriceList != null && customerSalePriceList.size() != 0) {
                        info.setCustomerSalePriceList(customerSalePriceList);
                    }
                    infoList.add(info);
                }

                // １件目 または 商品が変わった場合
                if (wkGoodsCode == null || !wkGoodsCode.equals(goodsCode)) {
                    info = new GetQuantityDiscountResponse();
                    priceList = new ArrayList<>();
                    salePriceList = new ArrayList<>();
                    customerSalePriceList = new ArrayList<>();
                    // 申込商品
                    info.setGoodsCode(goodsCode);
                    // 数量割引適用有無
                    info.setSaleFlag(rs.getString(6));
                    // 時価フラグ
                    info.setMarketPriceFlag(rs.getString(7));
                    // 顧客セール割引適用有無
                    info.setCustomerSaleFlag(rs.getInt(10));
                }

                // 価格リスト
                if (rs.getBigDecimal(2) != null) {
                    priceInfo = new GetQuantityDiscountPriceResponse();
                    // 価格
                    priceInfo.setPrice(rs.getBigDecimal(2));
                    // 数量閾値
                    priceInfo.setLevel(rs.getString(3));
                    priceList.add(priceInfo);

                }
                // 割引価格リスト
                if (rs.getBigDecimal(4) != null) {
                    salePriceInfo = new GetQuantityDiscountSalePriceResponse();
                    // 割引価格
                    salePriceInfo.setSalePrice(rs.getBigDecimal(4));
                    try {
                        // 割引数量閾値
                        if (rs.getString(5) != null) {
                            // 小数点以下を切り捨てて整数にする
                            BigDecimal saleLevel = new BigDecimal(rs.getString(5)).setScale(0, BigDecimal.ROUND_DOWN);
                            salePriceInfo.setSaleLevel(saleLevel.toString());
                        } else {
                            salePriceInfo.setSaleLevel(rs.getString(5));
                        }
                        salePriceList.add(salePriceInfo);
                        // 文字列の場合
                    } catch (NumberFormatException e) {
                        salePriceInfo.setSaleLevel(rs.getString(5));
                        salePriceList.add(salePriceInfo);
                    }
                }

                // 顧客セール価格リスト
                if (rs.getBigDecimal(8) != null) {
                    customerSalePriceInfo = new GetQuantityDiscountCustomerSalePriceResponse();
                    // 割引価格
                    customerSalePriceInfo.setCustomerSalePrice(rs.getBigDecimal(8));
                    try {
                        // 割引数量閾値
                        if (rs.getString(9) != null) {
                            // 小数点以下を切り捨てて整数にする
                            BigDecimal customerSaleLevel =
                                            new BigDecimal(rs.getString(9)).setScale(0, BigDecimal.ROUND_DOWN);
                            customerSalePriceInfo.setCustomerSaleLevel(customerSaleLevel.toString());
                        } else {
                            customerSalePriceInfo.setCustomerSaleLevel(rs.getString(9));
                        }
                        customerSalePriceList.add(customerSalePriceInfo);
                        // 文字列の場合
                    } catch (NumberFormatException e) {
                        customerSalePriceInfo.setCustomerSaleLevel(rs.getString(9));
                        customerSalePriceList.add(customerSalePriceInfo);
                    }
                }
                wkGoodsCode = goodsCode;
                // 2023-renew No5 from here
                // セール終了日
                info.setSaleEndDay(rs.getTimestamp(11));
                // ステータス
                status = rs.getInt(12);
                // 2023-renew No5 to here
            }

            if (priceList != null && priceList.size() != 0) {
                info.setPriceList(priceList);
            }
            if (salePriceList != null && salePriceList.size() != 0) {
                info.setSalePriceList(salePriceList);
            }
            if (customerSalePriceList != null && customerSalePriceList.size() != 0) {
                info.setCustomerSalePriceList(customerSalePriceList);
            }
            infoList.add(info);

            // ストアドからの返却値
            apiInfo.setStoredName("ProcGetQuantityDiscount");
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
     *
     * 価格情報取得<br/>
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
     *     <li>申込商品:goodsCode</li>
     *     <li>価格（最低）:priceLow</li>
     *     <li>価格（最高）:priceHight</li>
     *     <li>セール価格（最低）:salePriceLow</li>
     *     <li>セール価格（最高）:salePriceHight</li>
     *     <li>セールコメント:saleComment</li>
     *     <li>販売制御区分:saleControl</li>
     *   </ul>
     * </ul>
     */
    @POST
    @Path("/GetPrice")
    @Produces({"application/json"})
    @Consumes({"application/json"})
    public Response getPrice(String jsonData) {

        Logger log = LogManager.getLogger("getPrice");

        // 返却情報
        Result result = new Result();
        ResultInfo resultInfo = new ResultInfo();
        ApiInfo apiInfo = new ApiInfo("getPrice", "価格情報取得[Goods/GetPrice]", context, log);

        Connection conn = null;
        CallableStatement cs = null;

        // 最後に処理対象となっていた申込商品
        String lastProcessGoodsCode = null;

        try {

            // INパラメータをログに出力する
            log.info("[IN]" + jsonData);

            // POSTされたJSONをデコードし、専用のオブジェクトに変換する
            GetPriceRequest req = JSON.decode(jsonData, GetPriceRequest.class);

            // エラーキー情報を共通セット
            Utility.setErrorKeyInfo(apiInfo, jsonData);

            // JDBC接続
            String driverStr = PropManeger.getConf(apiInfo.getContext(), "db.jdbc.driver");
            Driver driver = (Driver) Class.forName(driverStr).newInstance();
            String connUrl = PropManeger.getConf(apiInfo.getContext(), "db.jdbc.url");
            conn = driver.connect(connUrl, new Properties());

            // パラメータをセットし、ストアドを呼出す
            cs = conn.prepareCall("{call ProcGetPrice(?)}");
            // 指定日時
            cs.setTimestamp(1, req.getDesignationDate());
            // 取得結果（リターンコード）
            // cs.registerOutParameter(2, java.sql.Types.SMALLINT);
            log.info("ProcGetPrice呼出（開始）：" + new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()));
            cs.executeQuery();
            log.info("ProcGetPrice呼出（終了）：" + new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()));
            Integer status = 0;

            // 処理結果を取得してしまうとResultSetがクリアされてしまう為
            // 前もって取得しておく必要がある
            ResultSet rs = cs.getResultSet();
            List<Object> infoList = new ArrayList<>();
            GetPriceResponse info = new GetPriceResponse();
            while (rs != null && rs.next()) {
                info = new GetPriceResponse();
                // 申込商品
                info.setGoodsCode(rs.getString(1));
                lastProcessGoodsCode = rs.getString(1);
                // 2023-renew AddNo5 from here
                // 価格（最低）
                info.setPriceLow(rs.getBigDecimal(2));
                // 価格（最高）
                info.setPriceHight(rs.getBigDecimal(3));
                // セール価格（最低）
                info.setSalePriceLow(rs.getBigDecimal(4));
                // セール価格（最高）
                info.setSalePriceHight(rs.getBigDecimal(5));
                // 2023-renew AddNo5 to here
                // セールコメント
                info.setSaleComment(rs.getString(6));
                // 2023-renew No11 from here
                info.setSaleControl(rs.getInt(7));
                // ステータス
                status = rs.getInt(8);
                // 2023-renew No11 to here
                infoList.add(info);
            }

            // ストアドからの返却値
            apiInfo.setStoredName("ProcGetPrice");
            apiInfo.setStoredCode(status);
            Utility.makeResult(apiInfo, result, resultInfo, infoList);

        } catch (Exception ex) {

            if (lastProcessGoodsCode != null) {
                log.error("[最終処理対象申込商品]" + lastProcessGoodsCode);
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
     *
     * シリーズ価格情報取得<br/>
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
     *     <li>商品グループ管理番号:goodGroupCode</li>
     *     <li>シリーズセールコメント:saleComment</li>
     *     <li>ＮＥＷアイコンフラグ:newIconFlag</li>
     *     <li>お取りおきアイコンフラグ:reserveIconFlag</li>
     *     <li>ＳＡＬＥアイコンフラグ:saleIconFlag</li>
     *   </ul>
     * </ul>
     */
    @POST
    @Path("/GetSeriesPrice")
    @Produces({"application/json"})
    @Consumes({"application/json"})
    public Response getSeriesPrice(String jsonData) {

        Logger log = LogManager.getLogger("getSeriesPrice");

        // 返却情報
        Result result = new Result();
        ResultInfo resultInfo = new ResultInfo();
        ApiInfo apiInfo = new ApiInfo("getSeriesPrice", "シリーズ価格情報取得[Goods/GetSeriesPrice]", context, log);

        Connection conn = null;
        CallableStatement cs = null;

        // 最後に処理対象となっていた商品グループコード
        String lastProcessGoodsGroupCode = null;

        try {

            // INパラメータをログに出力する
            log.info("[IN]" + jsonData);

            // POSTされたJSONをデコードし、専用のオブジェクトに変換する
            GetSeriesPriceRequest req = JSON.decode(jsonData, GetSeriesPriceRequest.class);

            // エラーキー情報を共通セット
            Utility.setErrorKeyInfo(apiInfo, jsonData);

            // JDBC接続
            String driverStr = PropManeger.getConf(apiInfo.getContext(), "db.jdbc.driver");
            Driver driver = (Driver) Class.forName(driverStr).newInstance();
            String connUrl = PropManeger.getConf(apiInfo.getContext(), "db.jdbc.url");
            conn = driver.connect(connUrl, new Properties());

            // パラメータをセットし、ストアドを呼出す
            cs = conn.prepareCall("{call ProcGetSeriesPrice(?)}");
            // 指定日時
            cs.setTimestamp(1, req.getDesignationDate());
            // 取得結果（リターンコード）
            // cs.registerOutParameter(2, java.sql.Types.SMALLINT);
            log.info("ProcGetSeriesPrice呼出（開始）：" + new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()));
            cs.executeQuery();
            log.info("ProcGetSeriesPrice呼出（終了）：" + new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()));
            Integer status = 0;

            // 処理結果を取得してしまうとResultSetがクリアされてしまう為
            // 前もって取得しておく必要がある
            ResultSet rs = cs.getResultSet();
            List<Object> infoList = new ArrayList<>();
            GetSeriesPriceResponse info = new GetSeriesPriceResponse();
            while (rs != null && rs.next()) {
                info = new GetSeriesPriceResponse();
                // 商品グループ管理番号
                info.setGoodsGroupCode(rs.getString(1));
                lastProcessGoodsGroupCode = rs.getString(1);
                // 2023-renew AddNo5 from here
                // 2023-renew AddNo5 to here
                // シリーズセールコメント
                info.setSeriesSaleComment(rs.getString(2));
                // ＮＥＷアイコンフラグ
                info.setNewIconFlag(rs.getString(3));
                // お取りおきアイコンフラグ
                info.setReserveIconFlag(rs.getString(4));
                // ＳＡＬＥアイコンフラグ
                info.setSaleIconFlag(rs.getString(5));
                // 2023-renew No92 from here
                // アウトレットアイコンフラグ
                info.setOutletIconFlag(rs.getString(6));
                infoList.add(info);
                status = rs.getInt(7);
                // 2023-renew No92 to here
            }

            // ストアドからの返却値
            apiInfo.setStoredName("ProcGetSeriesPrice");
            apiInfo.setStoredCode(status);
            Utility.makeResult(apiInfo, result, resultInfo, infoList);

        } catch (Exception ex) {

            if (lastProcessGoodsGroupCode != null) {
                log.error("[最終処理対象申込商品]" + lastProcessGoodsGroupCode);
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
     * 入荷情報取得<br/>
     * @param jsonData 取得条件
     * <ul>
     *   <li>申込商品:goodsCode（パイプ区切り）</li>
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
     *     <li>商品コード:goodsCode</li>
     *     <li>在庫数:stockQuantity</li>
     *   </ul>
     * </ul>
     */
    @POST
    @Path("/GetReStock")
    @Produces({"application/json"})
    @Consumes({"application/json"})
    public Response getReStock(String jsonData) {

        Logger log = LogManager.getLogger("getReStock");

        // 返却情報
        Result result = new Result();
        ResultInfo resultInfo = new ResultInfo();
        ApiInfo apiInfo = new ApiInfo("getReStock", "入荷情報取得[Goods/GetReStock]", context, log);

        Connection conn = null;
        CallableStatement cs = null;

        try {

            // INパラメータをログに出力する
            log.info("[IN]" + jsonData);

            // POSTされたJSONをデコードし、専用のオブジェクトに変換する
            GetReStockRequest req = JSON.decode(jsonData, GetReStockRequest.class);

            // エラーキー情報を共通セット
            Utility.setErrorKeyInfo(apiInfo, jsonData);

            // JDBC接続
            String driverStr = PropManeger.getConf(apiInfo.getContext(), "db.jdbc.driver");
            Driver driver = (Driver) Class.forName(driverStr).newInstance();
            String connUrl = PropManeger.getConf(apiInfo.getContext(), "db.jdbc.url");
            conn = driver.connect(connUrl, new Properties());

            // パラメータをセットし、ストアドを呼出す
            cs = conn.prepareCall("{call ProcGetReStock(?)}");
            // 申込商品
            cs.setString(1, req.getGoodsCode());
            // 取得結果（リターンコード）
            // cs.registerOutParameter(2, java.sql.Types.SMALLINT);
            log.info("ProcGetReStock呼出（開始）：" + new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()));
            cs.executeQuery();
            log.info("ProcGetReStock呼出（終了）：" + new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()));
            Integer status = 0;

            // 処理結果を取得してしまうとResultSetがクリアされてしまう為
            // 前もって取得しておく必要がある
            ResultSet rs = cs.getResultSet();
            List<Object> infoList = new ArrayList<>();
            while (rs != null && rs.next()) {
                GetReStockResponse info = new GetReStockResponse();
                // 申込商品
                info.setGoodsCode(rs.getString(1));
                // 在庫数
                info.setStockQuantity(rs.getString(2));
                // ステータス
                status = rs.getInt(3);

                infoList.add(info);
            }

            // ストアドからの返却値
            apiInfo.setStoredName("ProcGetReStock");
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
     * セール情報取得<br/>
     * @param jsonData 取得条件
     * <ul>
     *   <li>指定日時:designationDate</li>
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
     *     <li>商品コード:goodsCode</li>
     *     <li>セール状態:saleStatus</li>
     *     <li>セールコード:saleCode</li>
     *     <li>セール期間From:saleFrom</li>
     *     <li>セール期間To:saleTo</li>
     *   </ul>
     * </ul>
     */
    @POST
    @Path("/GetSale")
    @Produces({"application/json"})
    @Consumes({"application/json"})
    public Response getSale(String jsonData) {

        Logger log = LogManager.getLogger("getSale");

        // 返却情報
        Result result = new Result();
        ResultInfo resultInfo = new ResultInfo();
        ApiInfo apiInfo = new ApiInfo("getSale", "セール在庫数取得[Goods/GetReStock]", context, log);

        Connection conn = null;
        CallableStatement cs = null;

        try {

            // INパラメータをログに出力する
            log.info("[IN]" + jsonData);

            // POSTされたJSONをデコードし、専用のオブジェクトに変換する
            GetSaleRequest req = JSON.decode(jsonData, GetSaleRequest.class);

            // エラーキー情報を共通セット
            Utility.setErrorKeyInfo(apiInfo, jsonData);

            // JDBC接続
            String driverStr = PropManeger.getConf(apiInfo.getContext(), "db.jdbc.driver");
            Driver driver = (Driver) Class.forName(driverStr).newInstance();
            String connUrl = PropManeger.getConf(apiInfo.getContext(), "db.jdbc.url");
            conn = driver.connect(connUrl, new Properties());

            // パラメータをセットし、ストアドを呼出す
            cs = conn.prepareCall("{call ProcGetSale(?)}");
            // 指定日時
            cs.setTimestamp(1, req.getDesignationDate());
            // 取得結果（リターンコード）
            // cs.registerOutParameter(2, java.sql.Types.SMALLINT);
            log.info("ProcGetSale呼出（開始）：" + new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()));
            cs.executeQuery();
            log.info("ProcGetSale呼出（終了）：" + new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()));
            Integer status = 0;

            // 処理結果を取得してしまうとResultSetがクリアされてしまう為
            // 前もって取得しておく必要がある
            ResultSet rs = cs.getResultSet();
            List<Object> infoList = new ArrayList<>();
            while (rs != null && rs.next()) {
                GetSaleResponse info = new GetSaleResponse();
                // 商品コード
                info.setGoodsCode(rs.getString(1));
                // セール状態
                info.setSaleStatus(rs.getString(2));
                // セールコード
                info.setSaleCode(rs.getString(3));
                // セール期間From
                info.setSaleFrom(rs.getTimestamp(4));
                // セール期間To
                info.setSaleTo(rs.getTimestamp(5));
                // ステータス
                status = rs.getInt(6);

                infoList.add(info);
            }

            // ストアドからの返却値
            apiInfo.setStoredName("ProcGetSale");
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

    // 2023-renew No11 from here

    /**
     *
     * 販売可否判定<br/>
     *
     * @param jsonData 取得条件
     * <ul>
     *   <li>申込商品:goodsCode（パイプ区切り）</li>
     *   <li>顧客番号:customerNo</li>
     * </ul>
     * @return JSON形式の処理結果
     * <ul>
     *   <li>処理結果:result<li>
     *   <ul>
     *     <li>ステータス:status</li>
     *     <li>メッセージ:message</li>
     *   </ul>
     *   <li>【リスト】情報リスト:info</li>
     *   <ul>
     *     <li>商品コード:goodsCode</li>
     *     <li>販売可否判定結果:goodsSaleYesNo</li>
     *   </ul>
     * </ul>
     */
    @POST
    @Path("/GetSaleCheck")
    @Produces({"application/json"})
    @Consumes({"application/json"})
    public Response GetSaleCheck(String jsonData) {

        Logger log = LogManager.getLogger("getSaleCheck");

        // 返却情報
        Result result = new Result();
        ResultInfo resultInfo = new ResultInfo();
        ApiInfo apiInfo = new ApiInfo("GetSaleCheck", "販売可否判定[Goods/GetSaleCheck]", context, log);

        Connection conn = null;
        CallableStatement cs = null;

        try {

            // INパラメータをログに出力する
            log.info("[IN]" + jsonData);

            // POSTされたJSONをデコードし、専用のオブジェクトに変換する
            GetSaleCheckRequest req = JSON.decode(jsonData, GetSaleCheckRequest.class);

            // エラーキー情報をセット(エラーメールは顧客番号のみ)
            apiInfo.setErrorKeyInfoLog(jsonData);
            apiInfo.setErrorKeyInfoMail("顧客番号=" + req.getCustomerNo());

            // JDBC接続
            String driverStr = PropManeger.getConf(apiInfo.getContext(), "db.jdbc.driver");
            Driver driver = (Driver) Class.forName(driverStr).newInstance();
            String connUrl = PropManeger.getConf(apiInfo.getContext(), "db.jdbc.url");
            conn = driver.connect(connUrl, new Properties());

            // パラメータをセットし、ストアドを呼出す
            cs = conn.prepareCall("{call ProcGetSaleCheck(?,?)}");

            // 申込商品
            cs.setString(1, req.getGoodsCode());
            // 顧客番号
            if (req.getCustomerNo() == null) {
                cs.setNull(2, java.sql.Types.INTEGER);
            } else {
                cs.setInt(2, Integer.parseInt(req.getCustomerNo()));
            }

            log.info("ProcGetSaleCheck呼出（開始）：" + new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()));
            cs.executeQuery();
            log.info("ProcGetSaleCheck呼出（終了）：" + new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()));
            Integer status = 0;

            // 処理結果を取得してしまうとResultSetがクリアされてしまう為
            // 前もって取得しておく必要がある
            ResultSet rs = cs.getResultSet();
            List<Object> infoList = new ArrayList<>();
            GetSaleCheckResponse info = null;
            while (rs != null && rs.next()) {
                info = new GetSaleCheckResponse();
                // 商品コード
                info.setGoodsCode(rs.getString(1));
                // 販売可否判定結果
                info.setGoodsSaleYesNo(rs.getInt(2));

                infoList.add(info);
                status = rs.getInt(3);
            }

            // ストアドからの返却値
            apiInfo.setStoredName("ProcGetSaleCheck");
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
    // 2023-renew No11 to here

    // 2023-renew No92 from here

    /**
     *
     * 後継品代替品情報取得<br/>
     *
     * @param jsonData 取得条件
     * <ul>
     *   <li>申込商品:goodsCode（パイプ区切り）</li>
     * </ul>
     * @return JSON形式の処理結果
     * <ul>
     *   <li>処理結果:result<li>
     *   <ul>
     *     <li>ステータス:status</li>
     *     <li>メッセージ:message</li>
     *   </ul>
     *   <li>【リスト】情報リスト:info</li>
     *   <ul>
     *     <li>商品コード:goodsCode</li>
     *     <li>後継品商品コード:otherGoodsCode</li>
     *   </ul>
     * </ul>
     */
    @POST
    @Path("/GetOtherGoods")
    @Produces({"application/json"})
    @Consumes({"application/json"})
    public Response GetOtherGoods(String jsonData) {

        Logger log = LogManager.getLogger("GetOtherGoods");

        // 返却情報
        Result result = new Result();
        ResultInfo resultInfo = new ResultInfo();
        ApiInfo apiInfo = new ApiInfo("GetOtherGoods", "販売可否判定[Goods/GetOtherGoods]", context, log);

        Connection conn = null;
        CallableStatement cs = null;

        try {

            // INパラメータをログに出力する
            log.info("[IN]" + jsonData);

            // POSTされたJSONをデコードし、専用のオブジェクトに変換する
            GetOtherGoodsRequest req = JSON.decode(jsonData, GetOtherGoodsRequest.class);

            // エラーキー情報を共通セット
            Utility.setErrorKeyInfo(apiInfo, jsonData);

            // JDBC接続
            String driverStr = PropManeger.getConf(apiInfo.getContext(), "db.jdbc.driver");
            Driver driver = (Driver) Class.forName(driverStr).newInstance();
            String connUrl = PropManeger.getConf(apiInfo.getContext(), "db.jdbc.url");
            conn = driver.connect(connUrl, new Properties());

            // パラメータをセットし、ストアドを呼出す
            cs = conn.prepareCall("{call ProcGetOtherGoods(?)}");

            // 申込商品
            cs.setString(1, req.getGoodsCode());

            log.info("ProcGetOtherGoods呼出（開始）：" + new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()));
            cs.executeQuery();
            log.info("ProcGetOtherGoods呼出（終了）：" + new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()));
            Integer status = 0;

            // 処理結果を取得してしまうとResultSetがクリアされてしまう為
            // 前もって取得しておく必要がある
            ResultSet rs = cs.getResultSet();
            List<Object> infoList = new ArrayList<>();
            GetOtherGoodsResponse info = null;
            while (rs != null && rs.next()) {
                info = new GetOtherGoodsResponse();
                // 商品コード
                info.setGoodsCode(rs.getString(1));
                // 後継品商品コード
                info.setOtherGoodsCode(rs.getString(2));

                infoList.add(info);
                status = rs.getInt(3);
            }

            // ストアドからの返却値
            apiInfo.setStoredName("ProcGetOtherGoods");
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
    // 2023-renew No92 to here
}
