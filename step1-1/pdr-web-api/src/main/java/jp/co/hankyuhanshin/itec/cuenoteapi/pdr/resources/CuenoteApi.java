/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */
package jp.co.hankyuhanshin.itec.cuenoteapi.pdr.resources;

import jp.co.hankyuhanshin.itec.cuenoteapi.pdr.common.ApiInfo;
import jp.co.hankyuhanshin.itec.cuenoteapi.pdr.common.PropManeger;
import jp.co.hankyuhanshin.itec.cuenoteapi.pdr.common.Utility;
import jp.co.hankyuhanshin.itec.cuenoteapi.pdr.request.GetCuenoteApiAddressImportRequest;
import jp.co.hankyuhanshin.itec.cuenoteapi.pdr.request.GetCuenoteApiDeliveryReserveRequestDto;
import jp.co.hankyuhanshin.itec.cuenoteapi.pdr.request.GetCuenoteApiMailSetRequest;
import jp.co.hankyuhanshin.itec.cuenoteapi.pdr.response.GetCuenoteApiAddressImportResponse;
import jp.co.hankyuhanshin.itec.cuenoteapi.pdr.response.GetCuenoteApiDeliveryReserveResponse;
import jp.co.hankyuhanshin.itec.cuenoteapi.pdr.response.GetCuenoteApiGetDeliveryResponse;
import jp.co.hankyuhanshin.itec.cuenoteapi.pdr.response.GetCuenoteApiMailSetResponse;
import jp.co.hankyuhanshin.itec.cuenoteapi.pdr.response.Result;
import jp.co.hankyuhanshin.itec.cuenoteapi.pdr.response.ResultInfo;
import net.arnx.jsonic.JSON;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
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

// st75001 新規作成

/**
 * CUENOTE-API連携 cuenoteapi<br/>
 * @author st75001
 */
@Path("/CuenoteApi")
public class CuenoteApi {

    /** サーブレットコンテキスト */
    @Context
    private ServletContext context;

    /**
     * アドレス帳レコードインポート(入荷)<br/>
     * @param jsonData 取得条件
     */
    @POST
    @Path("/GetReStockCuenoteApiAddressImport")
    @Produces({"application/json"})
    @Consumes({"application/json"})
    public Response getReStockCuenoteApiAddressImport(String jsonData) {

        Logger log = LogManager.getLogger("getReStockCuenoteApiAddressImport");

        // 返却情報
        Result result = new Result();
        ResultInfo resultInfo = new ResultInfo();
        ApiInfo apiInfo = new ApiInfo("getReStockCuenoteApiAddressImport", "アドレス帳インポート[CuenoteApi/getReStockCuenoteApiAddressImport]", context, log);

        Connection conn = null;
        CallableStatement cs = null;
        List<Object> infoList = new ArrayList<>();

        try {

            // INパラメータをログに出力する
            log.info("[IN]" + jsonData);

            // POSTされたJSONをデコードし、専用のオブジェクトに変換する
            GetCuenoteApiAddressImportRequest req = JSON.decode(jsonData, GetCuenoteApiAddressImportRequest.class);

            // エラーキー情報を共通セット
            Utility.setErrorKeyInfo(apiInfo, jsonData);

            // JDBC接続
            String driverStr = PropManeger.getConf(apiInfo.getContext(), "db.jdbc.driver");
            Driver driver = (Driver) Class.forName(driverStr).newInstance();
            String connUrl = PropManeger.getConf(apiInfo.getContext(), "db.jdbc.url");
            conn = driver.connect(connUrl, new Properties());

            // パラメータをセットし、ストアドを呼出す
            cs = conn.prepareCall("{call ProcGetReStockCuenoteApiAddressImport(?,?,?)}");
            // メールアドレス
            cs.setString(1, req.getEmail());
            // 事業所名
            cs.setString(2, req.getOffice_name());
            // 商品情報
            cs.setString(3, req.getGoods_info());
            // 取得結果（リターンコード）
            // cs.registerOutParameter(4, java.sql.Types.SMALLINT);
            log.info("ProcGetReStockCuenoteApiAddressImport呼出（開始）：" + new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()));
            cs.executeQuery();
            log.info("ProcGetReStockCuenoteApiAddressImport呼出（終了）：" + new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()));
            Integer status = 200;

            // 処理結果を取得してしまうとResultSetがクリアされてしまう為
            // 前もって取得しておく必要がある
            ResultSet rs = cs.getResultSet();
            while (rs != null && rs.next()) {
                GetCuenoteApiAddressImportResponse info = new GetCuenoteApiAddressImportResponse();
                // アドレス帳 ID
                info.setAdbook(rs.getString(1));
                // 追加されたレコード数
                info.setAdd_record(rs.getInt(2));
                // インポート処理情報の作成時刻
                info.setCreatetime(rs.getString(3));
                // 削除されたレコード数
                info.setDelete_record(rs.getInt(4));
                // 形式エラーで追加されなかったレコード数
                info.setError_record(rs.getInt(5));
                // インポート反映処理の終了時刻
                info.setExecute_end_time(rs.getString(6));
                // インポート反映処理にかかった時間（秒）
                info.setExecute_sec(rs.getInt(7));
                // インポート反映処理の開始時刻
                info.setExecute_start_time(rs.getString(8));
                // インポート処理ID
                info.setImport_key(rs.getString(9));
                // インポートの方式
                info.setImport_type(rs.getString(10));
                // 存在せず削除できなかったレコード数
                info.setNotexists_record(rs.getInt(11));
                // インポート準備処理の終了時刻
                info.setPrepare_end_time(rs.getString(12));
                // インポート準備処理にかかった時間（秒）
                info.setPrepare_sec(rs.getInt(13));
                // インポート準備処理の開始時刻
                info.setPrepare_start_time(rs.getString(14));
                // インポートデータの全レコード数
                info.setTotal_record(rs.getInt(15));
                // 更新されたレコード数
                info.setUpdate_record(rs.getInt(16));
                // インポート処理情報の更新時刻
                info.setUpdatetime(rs.getString(17));
                // ファイル処理にかかった時間（秒）
                info.setUpload_sec(rs.getInt(18));
                // ファイル処理の開始時刻
                info.setUpload_start_time(rs.getString(19));
                // ステータス
                status = rs.getInt(20);

                infoList.add(info);
            }

            // ストアドからの返却値
            apiInfo.setStoredName("ProcGetReStockCuenoteApiAddressImport");
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
        return Utility.createResponse(log, infoList, jsonData, Level.INFO, apiInfo);
    }

    /**
     * アドレス帳レコードインポート(セール)<br/>
     * @param jsonData 取得条件
     */
    @POST
    @Path("/GetSaleCuenoteApiAddressImport")
    @Produces({"application/json"})
    @Consumes({"application/json"})
    public Response getSaleCuenoteApiAddressImport(String jsonData) {

        Logger log = LogManager.getLogger("getSaleCuenoteApiAddressImport");

        // 返却情報
        Result result = new Result();
        ResultInfo resultInfo = new ResultInfo();
        ApiInfo apiInfo = new ApiInfo("getSaleCuenoteApiAddressImport", "アドレス帳インポート[CuenoteApi/getSaleCuenoteApiAddressImport]", context, log);

        Connection conn = null;
        CallableStatement cs = null;
        List<Object> infoList = new ArrayList<>();

        try {

            // INパラメータをログに出力する
            log.info("[IN]" + jsonData);

            // POSTされたJSONをデコードし、専用のオブジェクトに変換する
            GetCuenoteApiAddressImportRequest req = JSON.decode(jsonData, GetCuenoteApiAddressImportRequest.class);

            // エラーキー情報を共通セット
            Utility.setErrorKeyInfo(apiInfo, jsonData);

            // JDBC接続
            String driverStr = PropManeger.getConf(apiInfo.getContext(), "db.jdbc.driver");
            Driver driver = (Driver) Class.forName(driverStr).newInstance();
            String connUrl = PropManeger.getConf(apiInfo.getContext(), "db.jdbc.url");
            conn = driver.connect(connUrl, new Properties());

            // パラメータをセットし、ストアドを呼出す
            cs = conn.prepareCall("{call ProcGetSaleCuenoteApiAddressImport(?,?,?)}");
            // メールアドレス
            cs.setString(1, req.getEmail());
            // 事業所名
            cs.setString(2, req.getOffice_name());
            // 商品情報
            cs.setString(3, req.getGoods_info());
            // 取得結果（リターンコード）
            // cs.registerOutParameter(4, java.sql.Types.SMALLINT);
            log.info("ProcGetSaleCuenoteApiAddressImport呼出（開始）：" + new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()));
            cs.executeQuery();
            log.info("ProcGetSaleCuenoteApiAddressImport呼出（終了）：" + new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()));
            Integer status = 200;

            // 処理結果を取得してしまうとResultSetがクリアされてしまう為
            // 前もって取得しておく必要がある
            ResultSet rs = cs.getResultSet();
            while (rs != null && rs.next()) {
                GetCuenoteApiAddressImportResponse info = new GetCuenoteApiAddressImportResponse();
                // アドレス帳 ID
                info.setAdbook(rs.getString(1));
                // 追加されたレコード数
                info.setAdd_record(rs.getInt(2));
                // インポート処理情報の作成時刻
                info.setCreatetime(rs.getString(3));
                // 削除されたレコード数
                info.setDelete_record(rs.getInt(4));
                // 形式エラーで追加されなかったレコード数
                info.setError_record(rs.getInt(5));
                // インポート反映処理の終了時刻
                info.setExecute_end_time(rs.getString(6));
                // インポート反映処理にかかった時間（秒）
                info.setExecute_sec(rs.getInt(7));
                // インポート反映処理の開始時刻
                info.setExecute_start_time(rs.getString(8));
                // インポート処理ID
                info.setImport_key(rs.getString(9));
                // インポートの方式
                info.setImport_type(rs.getString(10));
                // 存在せず削除できなかったレコード数
                info.setNotexists_record(rs.getInt(11));
                // インポート準備処理の終了時刻
                info.setPrepare_end_time(rs.getString(12));
                // インポート準備処理にかかった時間（秒）
                info.setPrepare_sec(rs.getInt(13));
                // インポート準備処理の開始時刻
                info.setPrepare_start_time(rs.getString(14));
                // インポートデータの全レコード数
                info.setTotal_record(rs.getInt(15));
                // 更新されたレコード数
                info.setUpdate_record(rs.getInt(16));
                // インポート処理情報の更新時刻
                info.setUpdatetime(rs.getString(17));
                // ファイル処理にかかった時間（秒）
                info.setUpload_sec(rs.getInt(18));
                // ファイル処理の開始時刻
                info.setUpload_start_time(rs.getString(19));
                // ステータス
                status = rs.getInt(20);

                infoList.add(info);
            }

            // ストアドからの返却値
            apiInfo.setStoredName("ProcGetSaleCuenoteApiAddressImport");
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
        return Utility.createResponse(log, infoList, jsonData, Level.INFO, apiInfo);
    }

    /**
     * メール文書セット複製<br/>
     * @param jsonData 取得条件
     */
    @POST
    @Path("/GetCuenoteApiMailSet")
    @Produces({"application/json"})
    @Consumes({"application/json"})
    public Response getCuenoteApiMailSet(String jsonData) {

        Logger log = LogManager.getLogger("getCuenoteApiMailSet");

        // 返却情報
        Result result = new Result();
        ResultInfo resultInfo = new ResultInfo();
        ApiInfo apiInfo = new ApiInfo("getCuenoteApiMailSet", "アドレス帳インポート[CuenoteApi/getCuenoteApiMailSet]", context, log);
        List<Object> infoList = new ArrayList<>();

        Connection conn = null;
        CallableStatement cs = null;

        try {

            // INパラメータをログに出力する
            log.info("[IN]" + jsonData);

            // POSTされたJSONをデコードし、専用のオブジェクトに変換する
            GetCuenoteApiMailSetRequest req = JSON.decode(jsonData, GetCuenoteApiMailSetRequest.class);

            // エラーキー情報を共通セット
            Utility.setErrorKeyInfo(apiInfo, jsonData);

            // JDBC接続
            String driverStr = PropManeger.getConf(apiInfo.getContext(), "db.jdbc.driver");
            Driver driver = (Driver) Class.forName(driverStr).newInstance();
            String connUrl = PropManeger.getConf(apiInfo.getContext(), "db.jdbc.url");
            conn = driver.connect(connUrl, new Properties());

            // パラメータをセットし、ストアドを呼出す
            cs = conn.prepareCall("{call ProcGetCuenoteApiMailSet(?,?,?,?)}");
            // メールテンプレートのメール文書セットＩＤ（必須）
            cs.setString(1, req.getOriginal_mail_id());
            // メール文書セット名（必須）
            cs.setString(2, req.getTitle());
            // 備考
            cs.setString(3, req.getComment());
            // 使用目的
            cs.setString(4, req.getPurpose());
            // 取得結果（リターンコード）
            // cs.registerOutParameter(4, java.sql.Types.SMALLINT);
            log.info("ProcGetCuenoteApiMailSet呼出（開始）：" + new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()));
            cs.executeQuery();
            log.info("ProcGetCuenoteApiMailSet呼出（終了）：" + new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()));
            Integer status = 201;

            // 処理結果を取得してしまうとResultSetがクリアされてしまう為
            // 前もって取得しておく必要がある
            ResultSet rs = cs.getResultSet();
            while (rs != null && rs.next()) {
                GetCuenoteApiMailSetResponse info = new GetCuenoteApiMailSetResponse();
                // ロケーション
                info.setLocation(rs.getString(1));
                // ステータス
                status = rs.getInt(2);

                infoList.add(info);
            }

            // ストアドからの返却値
            apiInfo.setStoredName("ProcGetCuenoteApiMailSet");
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
        return Utility.createResponse(log, infoList, jsonData, Level.INFO, apiInfo);
    }

    /**
     * 配信情報予約<br/>
     * @param jsonData 取得条件
     */
    @POST
    @Path("/GetCuenoteApiDeliveryReserve")
    @Produces({"application/json"})
    @Consumes({"application/json"})
    public Response getCuenoteApiDeliveryReserve(String jsonData) {

        Logger log = LogManager.getLogger("getCuenoteApiDeliveryReserve");

        // 返却情報
        Result result = new Result();
        ResultInfo resultInfo = new ResultInfo();
        ApiInfo apiInfo = new ApiInfo("getCuenoteApiDeliveryReserve", "アドレス帳インポート[CuenoteApi/getCuenoteApiDeliveryReserve]", context, log);

        Connection conn = null;
        CallableStatement cs = null;
        List<Object> infoList = new ArrayList<>();

        try {

            // INパラメータをログに出力する
            log.info("[IN]" + jsonData);

            // POSTされたJSONをデコードし、専用のオブジェクトに変換する
            GetCuenoteApiDeliveryReserveRequestDto req = JSON.decode(jsonData, GetCuenoteApiDeliveryReserveRequestDto.class);

            // エラーキー情報を共通セット
            Utility.setErrorKeyInfo(apiInfo, jsonData);

            // JDBC接続
            String driverStr = PropManeger.getConf(apiInfo.getContext(), "db.jdbc.driver");
            Driver driver = (Driver) Class.forName(driverStr).newInstance();
            String connUrl = PropManeger.getConf(apiInfo.getContext(), "db.jdbc.url");
            conn = driver.connect(connUrl, new Properties());

            // パラメータをセットし、ストアドを呼出す
            cs = conn.prepareCall("{call ProcGetCuenoteApiDeliveryReserve(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
            // アドレス帳ID
            cs.setString(1, req.getAdbook_id());
            // メール文書セットID
            cs.setInt(2, req.getMail_id());
            // アドレスの絞り込み
            cs.setString(3, req.getRefinement());
            // 過去配信やWebトラッキングの絞り込みを指定
            cs.setString(4, req.getConditions());
            // リークエンシーで除外する配信回数を指定
            cs.setInt(5, req.getRatelimit_value());
            // フリークエンシーで除外する配信回数の集計期間を日数で指定
            cs.setInt(6, req.getRatelimit_window_day());
            // 配信を開始する日時を指定。 時刻はW3C-DTFで指定。（必須）
            cs.setString(7, req.getDelivery_time());
            // 除外するエラーカウント数を指定
            cs.setInt(8, req.getError_count_threshold());
            // 時間帯制御の配信開始時間を指定。
            cs.setInt(9, req.getTime_period_starthour());
            // 時間帯制御の配信終了時間を指定。
            cs.setInt(10, req.getTime_period_endhour());
            // 配信期限を時間単位で指定
            cs.setInt(11, req.getDelivery_deadline_hour());
            // 配信速度を秒間辺りの通数で指定
            cs.setInt(12, req.getDelivery_velocity());
            // HTML画像の処理方法を指定。
            cs.setString(13, req.getDelivery_image_inlines());
            // 承認機能の利用有無を指定
            cs.setBoolean(14, req.getIs_approval_use());
            // 配信除外アドレス帳IDを指定
            cs.setString(15, req.getExclusion_adbook_id());
            // 重複アドレス帳利用時重複除外利用有無を指定。
            cs.setBoolean(16, req.getIs_nonuniq_aggregation_use());
            // 重複除外機能利用時の判定カラムを指定
            cs.setString(17, req.getNonuniq_aggregation_column());
            // 重複除外機能利用時の条件を指
            cs.setString(18, req.getNonuniq_aggregation_op());
            // テスト対象件数を%で指定。
            cs.setInt(19, req.getPause_success_percent());
            // 予約時刻からAB判定までの待ち時間を指定。
            cs.setInt(20, req.getAbtesting_measure_time_hour());
            // 予約時刻からAB判定までの待ち時間を指定。
            cs.setInt(21, req.getAbtesting_measure_time_min());
            // ABテストの効果測定方法を開封率またはクリック率から選びます
            cs.setString(22, req.getAbtesting_measure());

            // 取得結果（リターンコード）
            // cs.registerOutParameter(4, java.sql.Types.SMALLINT);
            log.info("ProcGetCuenoteApiDeliveryReserve呼出（開始）：" + new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()));
            cs.executeQuery();
            log.info("ProcGetCuenoteApiDeliveryReserve呼出（終了）：" + new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()));
            Integer status = 201;

            // 処理結果を取得してしまうとResultSetがクリアされてしまう為
            // 前もって取得しておく必要がある
            ResultSet rs = cs.getResultSet();
            while (rs != null && rs.next()) {
                GetCuenoteApiDeliveryReserveResponse info = new GetCuenoteApiDeliveryReserveResponse();
                // ロケーション
                info.setLocation(rs.getString(1));
                // ステータス
                status = rs.getInt(2);

                infoList.add(info);
            }

            // ストアドからの返却値
            apiInfo.setStoredName("ProcGetCuenoteApiDeliveryReserve");
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
        return Utility.createResponse(log, infoList, jsonData, Level.INFO, apiInfo);
    }

    /**
     * 配信情報取得<br/>
     * @param deliveryId 配信ＩＤ
     */
    @GET
    @Path("/GetCuenoteApiGetDelivery")
    @Produces({"application/json"})
    @Consumes({"application/json"})
    public Response getCuenoteApiGetDelivery(String deliveryId) {

        Logger log = LogManager.getLogger("getCuenoteApiGetDelivery");

        // 返却情報
        Result result = new Result();
        ResultInfo resultInfo = new ResultInfo();
        ApiInfo apiInfo = new ApiInfo("getCuenoteApiGetDelivery", "アドレス帳インポート[CuenoteApi/getCuenoteGetDelivery]", context, log);

        Connection conn = null;
        CallableStatement cs = null;
        List<Object> infoList = new ArrayList<>();

        try {

            // INパラメータをログに出力する
            log.info("[IN]" + deliveryId);

            // エラーキー情報を共通セット
            Utility.setErrorKeyInfo(apiInfo, deliveryId);

            // JDBC接続
            String driverStr = PropManeger.getConf(apiInfo.getContext(), "db.jdbc.driver");
            Driver driver = (Driver) Class.forName(driverStr).newInstance();
            String connUrl = PropManeger.getConf(apiInfo.getContext(), "db.jdbc.url");
            conn = driver.connect(connUrl, new Properties());

            // パラメータをセットし、ストアドを呼出す
            cs = conn.prepareCall("{call ProcGetCuenoteApiGetDelivery(?)}");
            // 配信ＩＤ
            cs.setString(1, deliveryId);
            // 取得結果（リターンコード）
            // cs.registerOutParameter(4, java.sql.Types.SMALLINT);
            log.info("ProcGetCuenoteApiGetDelivery呼出（開始）：" + new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()));
            cs.executeQuery();
            log.info("ProcGetCuenoteApiGetDelivery呼出（終了）：" + new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()));
            Integer status = 200;

            // 処理結果を取得してしまうとResultSetがクリアされてしまう為
            // 前もって取得しておく必要がある
            ResultSet rs = cs.getResultSet();
            while (rs != null && rs.next()) {
                GetCuenoteApiGetDeliveryResponse info = new GetCuenoteApiGetDeliveryResponse();
                // 配信ID
                info.setDelivery_id(rs.getInt(1));
                // アドレス帳 ID
                info.setAdbook_id(rs.getString(2));
                // アドレス帳名
                info.setAdbook_name(rs.getString(3));
                // メール文書セットID
                info.setMail_id(rs.getInt(4));
                // メール文書セット名
                info.setMail_name(rs.getString(5));
                // アドレスの絞り込み条件
                info.setRefinement(rs.getString(6));
                // 過去配信やWebトラッキングの絞り込み条件
                info.setConditions(rs.getString(7));
                // フリークエンシーで除外する配信回数
                info.setRatelimit_value(rs.getInt(8));
                // フリークエンシーで除外する配信回数の集計期間（日数）
                info.setRatelimit_window_day(rs.getInt(9));
                // 配信準備ステータス [‘wait’, ‘preparing’, ‘end’, ‘failed’]
                info.setFc_status(rs.getString(10));
                // MTAの配信ステータス [‘wait’, ‘prepare’, ‘preparing’, ‘delivering’, ‘done’, ‘suspended’, ‘canceled’]
                info.setMta_status(rs.getString(11));
                // 配信を開始する日時 (W3C DateTime)
                info.setDelivery_time(rs.getString(12));
                // MTAに配信を登録した日時 (W3C DateTime)
                info.setAssemble_time(rs.getString(13));
                // 初回配信完了日時 (W3C DateTime)
                info.setFirst_delivery_end_time(rs.getString(14));
                // 配信完了日時 (W3C DateTime)
                info.setDelivery_end_time(rs.getString(15));
                // 除外するエラーカウント数
                info.setError_count_threshold(rs.getInt(16));
                // 時間帯制御の配信開始時間
                info.setTime_period_starthour(rs.getInt(17));
                // 時間帯制御の配信終了時間
                info.setTime_period_endhour(rs.getInt(18));
                // 配信期限
                info.setDelivery_deadline_hour(rs.getInt(19));
                // 配信速度
                info.setDelivery_velocity(rs.getInt(20));
                // HTML画像処理
                info.setDelivery_image_inlines(rs.getString(21));
                // 承認機能の利用有無
                info.setIs_approval_use(rs.getBoolean(22));
                // 配信除外アドレス帳ID
                info.setExclusion_adbook_id(rs.getString(23));
                // 重複アドレス帳利用時重複除外利用有無
                info.setIs_nonuniq_aggregation_use(rs.getBoolean(24));
                // 重複除外機能利用時の判定カラム
                info.setNonuniq_aggregation_column(rs.getString(25));
                // 重複除外機能利用時の条件 [‘max’, ‘min’]
                info.setNonuniq_aggregation_op(rs.getString(26));
                // テスト対象件数。単位は%
                info.setPause_success_percent(rs.getInt(27));
                // 予約時刻からAB判定までの待ち時間の○時間○分の時間の指定
                info.setAbtesting_measure_time_hour(rs.getInt(28));
                // 予約時刻からAB判定までの待ち時間の○時間○分の分の指定
                info.setAbtesting_measure_time_min(rs.getInt(29));
                // ABテストの効果測定方法 [‘open’, ‘click’] (optional)
                info.setAbtesting_measure(rs.getString(30));
                // 配信予定件数
                info.setStat_count(rs.getInt(31));
                // 配信成功件数
                info.setStat_success(rs.getInt(32));
                // 配信失敗件数
                info.setStat_failure(rs.getInt(33));
                // 配信一時失敗件数
                info.setStat_deferral(rs.getInt(34));
                // ステータス
                status = rs.getInt(35);

                infoList.add(info);
            }

            // ストアドからの返却値
            apiInfo.setStoredName("ProcGetCuenoteApiGetDelivery");
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
        return Utility.createResponse(log, infoList, deliveryId, Level.INFO, apiInfo);
    }
}
