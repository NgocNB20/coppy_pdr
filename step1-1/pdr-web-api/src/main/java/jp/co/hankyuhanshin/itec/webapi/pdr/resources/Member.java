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
import jp.co.hankyuhanshin.itec.webapi.pdr.request.member.AddDestinationRequest;
import jp.co.hankyuhanshin.itec.webapi.pdr.request.member.AddUserInformationRequest;
import jp.co.hankyuhanshin.itec.webapi.pdr.request.member.DelDestinationRequest;
import jp.co.hankyuhanshin.itec.webapi.pdr.request.member.GetDestinationRequest;
import jp.co.hankyuhanshin.itec.webapi.pdr.request.member.GetUserInformationRequest;
import jp.co.hankyuhanshin.itec.webapi.pdr.request.member.RepUserFaxRequest;
import jp.co.hankyuhanshin.itec.webapi.pdr.request.member.RepUserMailaddressRequest;
import jp.co.hankyuhanshin.itec.webapi.pdr.request.member.RepUserNoticeRequest;
import jp.co.hankyuhanshin.itec.webapi.pdr.response.Result;
import jp.co.hankyuhanshin.itec.webapi.pdr.response.ResultInfo;
import jp.co.hankyuhanshin.itec.webapi.pdr.response.member.GetDestinationResponse;
import jp.co.hankyuhanshin.itec.webapi.pdr.response.member.GetUserInformationResponse;
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
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

// PDR#15 20161115 k-katoh 新規作成

/**
 * PDR#15 WEB-API連携 会員系WebAPI<br/>
 * <pre>
 * ・会員情報登録
 * ・会員変更情報取得
 * ・会員情報更新
 * </pre>
 * @author k-katoh
 */
@Path("/member")
public class Member {

    /** サーブレットコンテキスト */
    @Context
    private ServletContext context;

    /**
     * 会員情報登録WebAPI<br/>
     * <pre>
     * 会員情報を基幹システムに登録します。
     * </pre>
     *
     * @param jsonData JSON形式の会員情報
     * <ul>
     * <li>顧客番号:memberNo</li>
     * <li>メールアドレス:mailAddress</li>
     * <li>事業所名:officeName</li>
     * <li>事業所名フリガナ:officeKana</li>
     * <li>代表者名:representative</li>
     * <li>顧客区分:businessType</li>
     * <li>電話番号:tel</li>
     * <li>FAX番号:fax</li>
     * <li>郵便番号:zipCode</li>
     * <li>住所(都道府県・市区町村):city</li>
     * <li>住所(丁目・番地):address</li>
     * <li>住所(建物名・部屋番号):building</li>
     * <li>住所(方書1):other1</li>
     * <li>住所(方書2):other2</li>
     * <li>休診曜日:nonConsultationDay</li>
     * <li>Eメールによる情報提供:mailPermitFlag</li>
     * <li>FAXによる情報提供:faxPermitFlag</li>
     * <li>DMによる情報提供:sendDirectMailFlag</li>
     * <li>歯科専売品販売区分:dentalMonopolySalesType</li>
     * <li>医療機器販売区分:medicalEquipmentSalesType</li>
     * <li>医薬品・注射針販売区分:drugSalesType</li>
     * <li>名簿区分:memberListType</li>
     * <li>オンライン登録フラグ:onlineRegistFlag</li>
     * <li>診療項目:treatmentType</li>
     * <li>その他診療内容メモ:shinryoMemo</li>
     * <li>金属商品価格お知らせメール:metalPermitFlag</li>
     * <li>確認書類:kakuninShoKbn</li>
     * <li>経理区分:keiriKbn</li>
     * <li>オンラインログイン可否:onlineYesNo</li>
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
    @Path("/AddUserInformation")
    @Produces({"application/json"})
    @Consumes({"application/json"})
    public Response addUserInformation(String jsonData) {

        Logger log = LogManager.getLogger("addUserInformation");

        // 返却情報
        Result result = new Result();
        ResultInfo resultInfo = new ResultInfo();
        ApiInfo apiInfo = new ApiInfo("addUserInformation", "会員情報登録[Member/AddUserInformation]", context, log);

        Connection conn = null;
        CallableStatement cs = null;

        try {

            // INパラメータをログに出力する
            log.info("[IN]" + jsonData);

            // POSTされたJSONをデコードし、専用のオブジェクトに変換する
            AddUserInformationRequest userInfo = JSON.decode(jsonData, AddUserInformationRequest.class);

            // エラーキー情報をセット(エラーメールは顧客番号のみ)
            apiInfo.setErrorKeyInfoLog(jsonData);
            apiInfo.setErrorKeyInfoMail("顧客番号=" + userInfo.getCustomerNo());

            // JDBC接続
            String driverStr = PropManeger.getConf(apiInfo.getContext(), "db.jdbc.driver");
            Driver driver = (Driver) Class.forName(driverStr).newInstance();
            String connUrl = PropManeger.getConf(apiInfo.getContext(), "db.jdbc.url");
            conn = driver.connect(connUrl, new Properties());

            conn.setAutoCommit(false);

            // パラメータをセットし、ストアドを呼出す
            cs = conn.prepareCall(
                            "{call ProcAddUserInformation(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
            // 顧客番号
            if (userInfo.getCustomerNo() == null) {
                cs.setNull(1, java.sql.Types.INTEGER);
            } else {
                cs.setInt(1, Integer.parseInt(userInfo.getCustomerNo()));
            }
            // メールアドレス
            cs.setString(2, userInfo.getMailAddress());
            // 事業所名
            cs.setString(3, userInfo.getOfficeName());
            // 事業所名フリガナ
            cs.setString(4, userInfo.getOfficeKana());
            // 代表者名
            cs.setString(5, userInfo.getRepresentative());
            // 顧客区分
            if (userInfo.getBusinessType() == null) {
                cs.setNull(6, java.sql.Types.SMALLINT);
            } else {
                cs.setShort(6, Short.parseShort(userInfo.getBusinessType()));
            }
            // 電話番号
            cs.setString(7, userInfo.getTel());
            // FAX番号
            cs.setString(8, userInfo.getFax());
            // 郵便番号
            cs.setString(9, userInfo.getZipCode());
            // 住所１
            cs.setString(10, userInfo.getCity());
            // 住所２
            cs.setString(11, userInfo.getAddress());
            // 住所３
            cs.setString(12, userInfo.getBuilding());
            // 住所４
            cs.setString(13, userInfo.getOther1());
            // 住所５
            cs.setString(14, userInfo.getOther2());
            // 休診曜日
            cs.setString(15, userInfo.getNonConsultationDay());
            // Ｅメールによる情報提供
            if (userInfo.getMailPermitFlag() == null) {
                cs.setNull(16, java.sql.Types.SMALLINT);
            } else {
                cs.setShort(16, Short.parseShort(userInfo.getMailPermitFlag()));
            }
            // ＦＡＸによる情報提供
            if (userInfo.getFaxPermitFlag() == null) {
                cs.setNull(17, java.sql.Types.SMALLINT);
            } else {
                cs.setShort(17, Short.parseShort(userInfo.getFaxPermitFlag()));
            }
            // ＤＭによる情報提供
            if (userInfo.getSendDirectMailFlag() == null) {
                cs.setNull(18, java.sql.Types.SMALLINT);
            } else {
                cs.setShort(18, Short.parseShort(userInfo.getSendDirectMailFlag()));
            }
            // 歯科専売品販売区分
            if (userInfo.getDentalMonopolySalesType() == null) {
                cs.setNull(19, java.sql.Types.SMALLINT);
            } else {
                cs.setShort(19, Short.parseShort(userInfo.getDentalMonopolySalesType()));
            }
            // 医療機器販売区分
            if (userInfo.getMedicalEquipmentSalesType() == null) {
                cs.setNull(20, java.sql.Types.SMALLINT);
            } else {
                cs.setShort(20, Short.parseShort(userInfo.getMedicalEquipmentSalesType()));
            }
            // 医薬品注射針販売区分
            if (userInfo.getDrugSalesType() == null) {
                cs.setNull(21, java.sql.Types.SMALLINT);
            } else {
                cs.setShort(21, Short.parseShort(userInfo.getDrugSalesType()));
            }
            // 名簿区分
            if (userInfo.getMemberListType() == null) {
                cs.setNull(22, java.sql.Types.SMALLINT);
            } else {
                cs.setShort(22, Short.parseShort(userInfo.getMemberListType()));
            }
            // オンライン登録フラグ
            if (userInfo.getOnlineRegistFlag() == null) {
                cs.setNull(23, java.sql.Types.SMALLINT);
            } else {
                cs.setShort(23, Short.parseShort(userInfo.getOnlineRegistFlag()));
            }
            // 診療項目
            cs.setString(24, userInfo.getTreatmentType());
            // その他診療内容メモ
            cs.setString(25, userInfo.getShinryoMemo());
            // 金属商品価格お知らせメール
            if (userInfo.getMetalPermitFlag() == null) {
                cs.setNull(26, java.sql.Types.SMALLINT);
            } else {
                cs.setShort(26, Short.parseShort(userInfo.getMetalPermitFlag()));
            }
            // 確認書類
            if (userInfo.getKakuninShoKbn() == null) {
                cs.setNull(27, java.sql.Types.SMALLINT);
            } else {
                cs.setShort(27, Short.parseShort(userInfo.getKakuninShoKbn()));
            }
            // 経理区分
            if (userInfo.getKeiriKbn() == null) {
                cs.setNull(28, java.sql.Types.SMALLINT);
            } else {
                cs.setShort(28, Short.parseShort(userInfo.getKeiriKbn()));
            }
            // オンラインログイン可否
            if (userInfo.getOnlineYesNo() == null) {
                cs.setNull(29, java.sql.Types.SMALLINT);
            } else {
                cs.setShort(29, Short.parseShort(userInfo.getOnlineYesNo()));
            }
            // 登録結果（リターンコード）
            cs.registerOutParameter(30, java.sql.Types.SMALLINT);
            cs.executeUpdate();

            // ストアドからの返却値
            apiInfo.setStoredName("ProcAddUserInformation");
            apiInfo.setStoredCode(cs.getShort(30));
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
     * 会員変更情報取得<br/>
     *
     * @param jsonData JSON形式の取得条件
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
     *   <li>[リスト]情報:info</li>
     *   <ul>
     *     <li>顧客番号:memberNo</li>
     *     <li>メールアドレス:mailAddress</li>
     *     <li>事業所名:officeName</li>
     *     <li>事業所名フリガナ:officeKana</li>
     *     <li>代表者名:representative</li>
     *     <li>顧客区分:businessType</li>
     *     <li>電話番号:tel</li>
     *     <li>FAX番号:fax</li>
     *     <li>郵便番号:zipCode</li>
     *     <li>住所(都道府県・市区町村):city</li>
     *     <li>住所(丁目・番地):address</li>
     *     <li>住所(建物名・部屋番号):building</li>
     *     <li>住所(方書1):other1</li>
     *     <li>住所(方書2):other2</li>
     *     <li>休診曜日:nonConsultationDay</li>
     *     <li>Eメールによる情報提供:mailPermitFlag</li>
     *     <li>FAXによる情報提供:faxPermitFlag</li>
     *     <li>DMによる情報提供:sendDirectMailFlag</li>
     *     <li>歯科専売品販売区分:dentalMonopolySalesType</li>
     *     <li>医療機器販売区分:medicalEquipmentSalesType</li>
     *     <li>医薬品・注射針販売区分:drugSalesType</li>
     *     <li>クレジット決済使用可否:creditPaymentUseFlag</li>
     *     <li>コンビニ郵便振込使用可否:transferPaymentUseFlag</li>
     *     <li>代金引換使用可否:cashDeliveryUseFlag</li>
     *     <li>口座自動引落使用可否:directDebitUseFlag</li>
     *     <li>月締請求使用可否:monthlyPayUseFlag</li>
     *     <li>名簿区分:memberListType</li>
     *     <li>オンライン登録フラグ:onlineRegistFlag</li>
     *     <li>診療内容:treatmentType</li>
     *     <li>その他診療内容メモ:treatmentTypeMemo</li>
     *     <li>金属商品価格お知らせメール:metalPermitFlag</li>
     *     <li>確認書類:kakuninShoKbn</li>
     *     <li>経理区分:keiriKbn</li>
     *     <li>オンラインログイン可否:onlineYesNo</li>
     *   </ul>
     * </ul>
     */
    @POST
    @Path("/GetUserInformation")
    @Produces({"application/json"})
    @Consumes({"application/json"})
    public Response getUserInformation(String jsonData) {

        Logger log = LogManager.getLogger("getUserInformation");

        // 返却情報
        Result result = new Result();
        ResultInfo resultInfo = new ResultInfo();
        ApiInfo apiInfo = new ApiInfo("getUserInformation", "会員変更情報取得[Member/GetUserInformation]", context, log);

        Connection conn = null;
        CallableStatement cs = null;

        try {

            // INパラメータをログに出力する
            log.info("[IN]" + jsonData);

            // POSTされたJSONをデコードし、専用のオブジェクトに変換する
            GetUserInformationRequest req = JSON.decode(jsonData, GetUserInformationRequest.class);

            // エラーキー情報を共通セット
            Utility.setErrorKeyInfo(apiInfo, jsonData);

            // JDBC接続
            String driverStr = PropManeger.getConf(apiInfo.getContext(), "db.jdbc.driver");
            Driver driver = (Driver) Class.forName(driverStr).newInstance();
            String connUrl = PropManeger.getConf(apiInfo.getContext(), "db.jdbc.url");
            conn = driver.connect(connUrl, new Properties());

            // パラメータをセットし、ストアドを呼出す
            cs = conn.prepareCall("{call ProcGetUserInformation(?)}");
            // 指定日時
            cs.setTimestamp(1, req.getDesignationDate());
            // 登録結果（リターンコード）
            // cs.registerOutParameter(2, java.sql.Types.SMALLINT);
            log.info("ProcGetUserInformation呼出（開始）：" + new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()));
            cs.executeQuery();
            log.info("ProcGetUserInformation呼出（終了）：" + new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()));
            Integer status = 0;
            // 処理結果を取得してしまうとResultSetがクリアされてしまう為
            // 前もって取得しておく必要がある
            ResultSet rs = cs.getResultSet();
            List<Object> userInfoList = new ArrayList<>();
            while (rs != null && rs.next()) {
                GetUserInformationResponse userInfo = new GetUserInformationResponse();

                // 顧客番号
                userInfo.setMemberNo(rs.getString(1));
                // メールアドレス
                userInfo.setMailAddress(rs.getString(2));
                // 事業所名
                userInfo.setOfficeName(rs.getString(3));
                // 事業所名フリガナ
                userInfo.setOfficeKana(rs.getString(4));
                // 代表者名
                userInfo.setRepresentative(rs.getString(5));
                // 顧客区分
                userInfo.setBusinessType(rs.getString(6));
                // 電話番号
                userInfo.setTel(rs.getString(7));
                // ＦＡＸ番号
                userInfo.setFax(rs.getString(8));
                // 郵便番号
                userInfo.setZipCode(rs.getString(9));
                // 住所１
                userInfo.setCity(rs.getString(10));
                // 住所２
                userInfo.setAddress(rs.getString(11));
                // 住所３
                userInfo.setBuilding(rs.getString(12));
                // 住所４
                userInfo.setOther1(rs.getString(13));
                // 住所５
                userInfo.setOther2(rs.getString(14));
                // 休診曜日
                userInfo.setNonConsultationDay(rs.getString(15));
                // Ｅメールによる情報提供
                userInfo.setMailPermitFlag(rs.getString(16));
                // ＦＡＸによる情報提供
                userInfo.setFaxPermitFlag(rs.getString(17));
                // ＤＭによる情報提供
                userInfo.setSendDirectMailFlag(rs.getString(18));
                // 歯科専売品販売区分
                userInfo.setDentalMonopolySalesType(rs.getString(19));
                // 医療機器販売区分
                userInfo.setMedicalEquipmentSalesType(rs.getString(20));
                // 医薬品注射針販売区分
                userInfo.setDrugSalesType(rs.getString(21));
                // クレジット決済使用可否
                userInfo.setCreditPaymentUseFlag(rs.getString(22));
                // コンビニ郵便振込使用可否
                userInfo.setTransferPaymentUseFlag(rs.getString(23));
                // 代金引換使用可否
                userInfo.setCashDeliveryUseFlag(rs.getString(24));
                // 口座自動引落使用可否
                userInfo.setDirectDebitUseFlag(rs.getString(25));
                // 月締請求使用可否
                userInfo.setMonthlyPayUseFlag(rs.getString(26));
                // 名簿区分
                userInfo.setMemberListType(rs.getString(27));
                // オンライン登録フラグ
                userInfo.setOnlineRegistFlag(rs.getString(28));
                // 診療内容
                userInfo.setTreatmentType(rs.getString(29));
                // その他診療内容メモ
                userInfo.setTreatmentTypeMemo(rs.getString(30));
                // 金属商品価格お知らせメール
                userInfo.setMetalPermitFlag(rs.getString(31));
                // 確認書類
                userInfo.setKakuninShoKbn(rs.getString(32));
                // 経理区分
                userInfo.setKeiriKbn(rs.getString(33));
                // オンラインログイン可否
                userInfo.setOnlineYesNo(rs.getString(34));

                userInfoList.add(userInfo);
                // ステータス
                status = rs.getInt(35);
            }

            // ストアドからの返却値
            apiInfo.setStoredName("ProcGetUserInformation");
            apiInfo.setStoredCode(status);
            Utility.makeResult(apiInfo, result, resultInfo, userInfoList);

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
     * 会員情報更新<br/>
     *
     * @param jsonData JSON形式の会員変更情報
     * <ul>
     * <li>顧客番号:customerNo</li>
     * <li>メールアドレス:mailAddress</li>
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
    @Path("/RepUserMailaddress")
    @Produces({"application/json"})
    @Consumes({"application/json"})
    public Response repUserMailaddress(String jsonData) {

        Logger log = LogManager.getLogger("repUserMailaddress");

        // 返却情報
        Result result = new Result();
        ResultInfo resultInfo = new ResultInfo();
        ApiInfo apiInfo = new ApiInfo("repUserMailaddress", "会員情報更新[Member/RepUserMailaddress]", context, log);

        Connection conn = null;
        CallableStatement cs = null;

        try {

            // INパラメータをログに出力する
            log.info("[IN]" + jsonData);

            // POSTされたJSONをデコードし、専用のオブジェクトに変換する
            RepUserMailaddressRequest req = JSON.decode(jsonData, RepUserMailaddressRequest.class);

            // エラーキー情報をセット(エラーメールは顧客番号のみ)
            apiInfo.setErrorKeyInfoLog(jsonData);
            apiInfo.setErrorKeyInfoMail("顧客番号=" + req.getCustomerNo());

            // JDBC接続
            String driverStr = PropManeger.getConf(apiInfo.getContext(), "db.jdbc.driver");
            Driver driver = (Driver) Class.forName(driverStr).newInstance();
            String connUrl = PropManeger.getConf(apiInfo.getContext(), "db.jdbc.url");
            conn = driver.connect(connUrl, new Properties());

            conn.setAutoCommit(false);

            // パラメータをセットし、ストアドを呼出す
            cs = conn.prepareCall("{call ProcRepUserMailaddress(?,?,?)}");
            // 顧客番号
            if (req.getCustomerNo() == null) {
                cs.setNull(1, java.sql.Types.INTEGER);
            } else {
                cs.setInt(1, Integer.parseInt(req.getCustomerNo()));
            }
            // メールアドレス
            cs.setString(2, req.getMailAddress());
            // 更新結果（リターンコード）
            cs.registerOutParameter(3, java.sql.Types.SMALLINT);
            cs.executeUpdate();

            // ストアドからの返却値
            apiInfo.setStoredName("ProcRepUserMailaddress");
            apiInfo.setStoredCode(cs.getShort(3));
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
     * 会員お知らせ情報更新<br/>
     *
     * @param jsonData JSON形式の会員お知らせ情報更新
     * <ul>
     * <li>顧客番号:customerNo</li>
     * <li>Eメールによる情報提供:mailPermitFlag</li>
     * <li>金属商品価格お知らせメール:metalPermitFlag</li>
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
    @Path("/RepUserNotice")
    @Produces({"application/json"})
    @Consumes({"application/json"})
    public Response repUserNotice(String jsonData) {

        Logger log = LogManager.getLogger("repUserNotice");

        // 返却情報
        Result result = new Result();
        ResultInfo resultInfo = new ResultInfo();
        ApiInfo apiInfo = new ApiInfo("repUserNotice", "会員お知らせ情報更新[Member/RepUserNotice]", context, log);

        Connection conn = null;
        CallableStatement cs = null;

        try {

            // INパラメータをログに出力する
            log.info("[IN]" + jsonData);

            // POSTされたJSONをデコードし、専用のオブジェクトに変換する
            RepUserNoticeRequest req = JSON.decode(jsonData, RepUserNoticeRequest.class);

            // エラーキー情報をセット(エラーメールは顧客番号のみ)
            apiInfo.setErrorKeyInfoLog(jsonData);
            apiInfo.setErrorKeyInfoMail("顧客番号=" + req.getCustomerNo());

            // JDBC接続
            String driverStr = PropManeger.getConf(apiInfo.getContext(), "db.jdbc.driver");
            Driver driver = (Driver) Class.forName(driverStr).newInstance();
            String connUrl = PropManeger.getConf(apiInfo.getContext(), "db.jdbc.url");
            conn = driver.connect(connUrl, new Properties());

            conn.setAutoCommit(false);

            // パラメータをセットし、ストアドを呼出す
            // 2023-renew No85-1 from here
            cs = conn.prepareCall("{call ProcRepUserNotice(?,?,?,?,?,?,?)}");
            // 2023-renew No85-1 to here
            // 顧客番号
            if (req.getCustomerNo() == null) {
                cs.setNull(1, java.sql.Types.INTEGER);
            } else {
                cs.setInt(1, Integer.parseInt(req.getCustomerNo()));
            }
            // Eメールによる情報提供
            if (req.getMailPermitFlag() == null) {
                cs.setNull(2, java.sql.Types.SMALLINT);
            } else {
                cs.setShort(2, Short.parseShort(req.getMailPermitFlag()));
            }
            // 金属商品価格お知らせメール
            if (req.getMetalPermitFlag() == null) {
                cs.setNull(3, java.sql.Types.SMALLINT);
            } else {
                cs.setShort(3, Short.parseShort(req.getMetalPermitFlag()));
            }
            // 2023-renew No85-1 from here
            if (req.getNonConsultationDay() == null) {
                cs.setNull(4, Types.VARCHAR);
            } else {
                cs.setString(4, req.getNonConsultationDay());
            }
            if (req.getTreatmentType() == null) {
                cs.setNull(5, Types.VARCHAR);
            } else {
                cs.setString(5, req.getTreatmentType());
            }
            if (req.getTreatmentTypeMemo() == null) {
                cs.setNull(6, Types.VARCHAR);
            } else {
                cs.setString(6, req.getTreatmentTypeMemo());
            }

            // 更新結果（リターンコード）
            cs.registerOutParameter(7, Types.SMALLINT);
            // 2023-renew No85-1 to here

            log.info("ProcRepUserNotice呼出（開始）：" + new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()));
            cs.executeUpdate();
            log.info("ProcRepUserNotice呼出（終了）：" + new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()));

            // ストアドからの返却値
            apiInfo.setStoredName("ProcRepUserNotice");
            // 2023-renew No85-1 from here
            apiInfo.setStoredCode(cs.getShort(7));
            // 2023-renew No85-1 to here
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

    // 2023-renew No85-1 from here
    /**
     * 会員FAX情報更新<br/>
     *
     * @param jsonData JSON形式の会員FAX情報更新
     * <ul>
     * <li>顧客番号:customerNo</li>
     * <li>FAX番号:fax</li>
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
    @Path("/RepUserFax")
    @Produces({"application/json"})
    @Consumes({"application/json"})
    public Response repUserFax(String jsonData) {

        Logger log = LogManager.getLogger("repUserFax");

        // 返却情報
        Result result = new Result();
        ResultInfo resultInfo = new ResultInfo();
        ApiInfo apiInfo = new ApiInfo("repUserFax", "会員FAX情報更新[Member/RepUserFax]", context, log);

        Connection conn = null;
        CallableStatement cs = null;

        try {

            // INパラメータをログに出力する
            log.info("[IN]" + jsonData);

            // POSTされたJSONをデコードし、専用のオブジェクトに変換する
            RepUserFaxRequest req = JSON.decode(jsonData, RepUserFaxRequest.class);

            // エラーキー情報をセット(エラーメールは顧客番号のみ)
            apiInfo.setErrorKeyInfoLog(jsonData);
            apiInfo.setErrorKeyInfoMail("顧客番号=" + req.getCustomerNo());

            // JDBC接続
            String driverStr = PropManeger.getConf(apiInfo.getContext(), "db.jdbc.driver");
            Driver driver = (Driver) Class.forName(driverStr).newInstance();
            String connUrl = PropManeger.getConf(apiInfo.getContext(), "db.jdbc.url");
            conn = driver.connect(connUrl, new Properties());

            conn.setAutoCommit(false);

            // パラメータをセットし、ストアドを呼出す
            cs = conn.prepareCall("{call ProcRepUserFax(?,?,?)}");
            // 顧客番号
            if (req.getCustomerNo() == null) {
                cs.setNull(1, java.sql.Types.INTEGER);
            } else {
                cs.setInt(1, Integer.parseInt(req.getCustomerNo()));
            }
            if(req.getFax() == null) {
                cs.setNull(2, java.sql.Types.VARCHAR);
            } else {
                // メールアドレス
                cs.setString(2, req.getFax());
            }
            // 更新結果（リターンコード）
            cs.registerOutParameter(3, java.sql.Types.SMALLINT);
            log.info("ProcRepUserFax呼出（開始）：" + new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()));
            cs.executeUpdate();
            log.info("ProcRepUserFax呼出（終了）：" + new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()));

            // ストアドからの返却値
            apiInfo.setStoredName("ProcRepUserFax");
            apiInfo.setStoredCode(cs.getShort(3));
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
    // 2023-renew No85-1 to here

    /**
     * お届け先参照<br/>
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
     *     <li>お届け先顧客番号:receiveCustomerNo</li>
     *     <li>お届け先事業所名:officeName</li>
     *     <li>お届け先事業所名フリガナ:officeKana</li>
     *     <li>お届け先代表者名:representative</li>
     *     <li>お届け先顧客区分:businessType</li>
     *     <li>お届け先電話番号:tel</li>
     *     <li>お届け先郵便番号:zipCode</li>
     *     <li>お届け先住所１(都道府県・市区町村):city</li>
     *     <li>お届け先住所２(丁目・番地):address</li>
     *     <li>お届け先住所３(建物名・部屋番号):building</li>
     *     <li>お届け先住所４(方書１、２):other</li>
     *   </ul>
     * </ul>
     */
    @POST
    @Path("/GetDestination")
    @Produces({"application/json"})
    @Consumes({"application/json"})
    public Response getDestination(String jsonData) {

        Logger log = LogManager.getLogger("getDestination");

        // 返却情報
        Result result = new Result();
        ResultInfo resultInfo = new ResultInfo();
        ApiInfo apiInfo = new ApiInfo("getDestination", "お届け先参照[Member/GetDestination]", context, log);

        Connection conn = null;
        CallableStatement cs = null;

        try {

            // INパラメータをログに出力する
            log.info("[IN]" + jsonData);

            // POSTされたJSONをデコードし、専用のオブジェクトに変換する
            GetDestinationRequest req = JSON.decode(jsonData, GetDestinationRequest.class);

            // エラーキー情報を共通セット
            Utility.setErrorKeyInfo(apiInfo, jsonData);

            // JDBC接続
            String driverStr = PropManeger.getConf(apiInfo.getContext(), "db.jdbc.driver");
            Driver driver = (Driver) Class.forName(driverStr).newInstance();
            String connUrl = PropManeger.getConf(apiInfo.getContext(), "db.jdbc.url");
            conn = driver.connect(connUrl, new Properties());

            // パラメータをセットし、ストアドを呼出す
            cs = conn.prepareCall("{call ProcGetDestination(?)}");
            // 顧客番号
            cs.setInt(1, req.getCustomerNo());
            // 登録結果（リターンコード）
            // cs.registerOutParameter(2, java.sql.Types.SMALLINT);
            log.info("ProcGetDestination呼出（開始）：" + new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()));
            cs.executeQuery();
            log.info("ProcGetDestination呼出（終了）：" + new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()));
            Integer status = 0;
            // 処理結果を取得してしまうとResultSetがクリアされてしまう為
            // 前もって取得しておく必要がある
            ResultSet rs = cs.getResultSet();
            List<Object> userInfoList = new ArrayList<>();
            while (rs != null && rs.next()) {
                GetDestinationResponse userInfo = new GetDestinationResponse();

                // 顧客番号
                userInfo.setReceiveCustomerNo(rs.getInt(1));
                // お届け先事業所名
                userInfo.setOfficeName(rs.getString(2));
                // お届け先事業所名フリガナ
                userInfo.setOfficeKana(rs.getString(3));
                // お届け先代表者名
                userInfo.setRepresentative(rs.getString(4));
                // お届け先顧客区分
                userInfo.setBusinessType(rs.getInt(5));
                // お届け先電話番号
                userInfo.setTel(rs.getString(6));
                // お届け先郵便番号
                userInfo.setZipCode(rs.getString(7));
                // お届け先住所１(都道府県・市区町村)
                userInfo.setCity(rs.getString(8));
                // お届け先住所２(丁目・番地)
                userInfo.setAddress(rs.getString(9));
                // お届け先住所３(建物名・部屋番号)
                userInfo.setBuilding(rs.getString(10));
                // お届け先住所４(方書１、２)
                userInfo.setOther(rs.getString(11));
                // 承認フラグ
                userInfo.setApprovalFlag(rs.getInt(12));
                // ステータス
                status = rs.getInt(13);

                userInfoList.add(userInfo);
            }

            // ストアドからの返却値
            apiInfo.setStoredName("ProcGetDestination");
            apiInfo.setStoredCode(status);
            Utility.makeResult(apiInfo, result, resultInfo, userInfoList);

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
     * お届け先削除<br/>
     *
     * @param jsonData JSON形式のお届け先情報
     * <ul>
     * <li>顧客番号:customerNo</li>
     * <li>お届け先顧客番号:receiveCustomerNo</li>
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
    @Path("/DelDestination")
    @Produces({"application/json"})
    @Consumes({"application/json"})
    public Response delDestination(String jsonData) {

        Logger log = LogManager.getLogger("delDestination");

        // 返却情報
        Result result = new Result();
        ResultInfo resultInfo = new ResultInfo();
        ApiInfo apiInfo = new ApiInfo("delDestination", "お届け先削除[Member/DelDestination]", context, log);

        Connection conn = null;
        CallableStatement cs = null;

        try {

            // INパラメータをログに出力する
            log.info("[IN]" + jsonData);

            // POSTされたJSONをデコードし、専用のオブジェクトに変換する
            DelDestinationRequest req = JSON.decode(jsonData, DelDestinationRequest.class);

            // エラーキー情報をセット(エラーメールは顧客番号とお届け先顧客番号)
            apiInfo.setErrorKeyInfoLog(jsonData);
            apiInfo.setErrorKeyInfoMail("顧客番号=" + req.getCustomerNo() + ",お届け先顧客番号=" + req.getReceiveCustomerNo());

            // JDBC接続
            String driverStr = PropManeger.getConf(apiInfo.getContext(), "db.jdbc.driver");
            Driver driver = (Driver) Class.forName(driverStr).newInstance();
            String connUrl = PropManeger.getConf(apiInfo.getContext(), "db.jdbc.url");
            conn = driver.connect(connUrl, new Properties());

            conn.setAutoCommit(false);

            // パラメータをセットし、ストアドを呼出す
            cs = conn.prepareCall("{call ProcDelDestination(?,?,?)}");
            // 顧客番号
            if (req.getCustomerNo() == null) {
                cs.setNull(1, java.sql.Types.INTEGER);
            } else {
                cs.setInt(1, req.getCustomerNo());
            }
            // お届け先顧客番号
            cs.setInt(2, req.getReceiveCustomerNo());
            // 更新結果（リターンコード）
            cs.registerOutParameter(3, java.sql.Types.SMALLINT);

            log.info("ProcDelDestination呼出（開始）：" + new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()));
            cs.executeUpdate();
            log.info("ProcDelDestination呼出（終了）：" + new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()));

            // ストアドからの返却値
            apiInfo.setStoredName("ProcDelDestination");
            apiInfo.setStoredCode(cs.getShort(3));
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
     * お届け先登録WebAPI<br/>
     * <pre>
     * お届け先情報を基幹システムに登録します。
     * </pre>
     *
     * @param jsonData JSON形式のお届け先情報
     * <ul>
     * <li>顧客番号:customerNo</li>
     * <li>事業所名:officeName</li>
     * <li>事業所名フリガナ:officeKana</li>
     * <li>代表者名:representative</li>
     * <li>電話番号:tel</li>
     * <li>郵便番号:zipCode</li>
     * <li>お届け先住所１(都道府県・市区町村):city</li>
     * <li>お届け先住所２(丁目・番地):address</li>
     * <li>お届け先住所３(建物名・部屋番号):building</li>
     * <li>お届け先住所４(方書1、２):other</li>
     * <li>お届け先顧客区分:businessType</li>
     * <li>お届け先確認書類:kakuninShoKbn</li>
     * <li>お届け先経理区分:keiriKbn</li>
     * <li>お届け先オンラインログイン可否:onlineYesNo</li>
     * <li>お届け先名簿区分:memberListType</li>
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
    @Path("/AddDestination")
    @Produces({"application/json"})
    @Consumes({"application/json"})
    public Response addDestination(String jsonData) {

        Logger log = LogManager.getLogger("addDestination");

        // 返却情報
        Result result = new Result();
        ResultInfo resultInfo = new ResultInfo();
        ApiInfo apiInfo = new ApiInfo("addDestination", "お届け先登録[Member/AddDestination]", context, log);

        Connection conn = null;
        CallableStatement cs = null;

        try {

            // INパラメータをログに出力する
            log.info("[IN]" + jsonData);

            // POSTされたJSONをデコードし、専用のオブジェクトに変換する
            AddDestinationRequest req = JSON.decode(jsonData, AddDestinationRequest.class);

            // エラーキー情報をセット(エラーメールは顧客番号+お届け先顧客番号)
            apiInfo.setErrorKeyInfoLog(jsonData);
            apiInfo.setErrorKeyInfoMail("顧客番号=" + req.getCustomerNo() + "お届け先顧客番号=" + req.getReceiveCustomerNo());

            // JDBC接続
            String driverStr = PropManeger.getConf(apiInfo.getContext(), "db.jdbc.driver");
            Driver driver = (Driver) Class.forName(driverStr).newInstance();
            String connUrl = PropManeger.getConf(apiInfo.getContext(), "db.jdbc.url");
            conn = driver.connect(connUrl, new Properties());

            conn.setAutoCommit(false);

            // パラメータをセットし、ストアドを呼出す
            cs = conn.prepareCall("{call ProcAddDestination(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
            // 顧客番号
            if (req.getCustomerNo() == null) {
                cs.setNull(1, java.sql.Types.INTEGER);
            } else {
                cs.setInt(1, Integer.parseInt(req.getCustomerNo()));
            }
            // お届け先顧客番号
            if (req.getReceiveCustomerNo() == null) {
                cs.setNull(2, java.sql.Types.INTEGER);
            } else {
                cs.setInt(2, Integer.parseInt(req.getReceiveCustomerNo()));
            }
            // お届け先事業所名
            cs.setString(3, req.getOfficeName());
            // お届け先事業所名フリガナ
            cs.setString(4, req.getOfficeKana());
            // お届け先代表者名
            cs.setString(5, req.getRepresentative());
            // お届け先電話番号
            cs.setString(6, req.getTel());
            // お届け先郵便番号
            cs.setString(7, req.getZipCode());
            // お届け先都道府県コード
            cs.setString(8, req.getCityCd());
            // お届け先住所１(都道府県・市区町村)
            cs.setString(9, req.getCity());
            // お届け先住所２(丁目・番地)
            cs.setString(10, req.getAddress());
            // お届け先住所３(建物名・部屋番号)
            cs.setString(11, req.getBuilding());
            // お届け先住所４(方書１、２)
            cs.setString(12, req.getOther());
            // お届け先先顧客区分
            if (req.getBusinessType() == null) {
                cs.setNull(13, java.sql.Types.SMALLINT);
            } else {
                cs.setShort(13, Short.parseShort(req.getBusinessType()));
            }
            // お届け先確認書類
            if (req.getKakuninShoKbn() == null) {
                cs.setNull(14, java.sql.Types.SMALLINT);
            } else {
                cs.setShort(14, Short.parseShort(req.getKakuninShoKbn()));
            }
            // お届け先経理区分
            if (req.getKeiriKbn() == null) {
                cs.setNull(15, java.sql.Types.SMALLINT);
            } else {
                cs.setShort(15, Short.parseShort(req.getKeiriKbn()));
            }
            // お届け先オンラインログイン可否区分
            if (req.getOnlineYesNo() == null) {
                cs.setNull(16, java.sql.Types.SMALLINT);
            } else {
                cs.setShort(16, Short.parseShort(req.getOnlineYesNo()));
            }
            // お届け先名簿区分
            if (req.getMemberListType() == null) {
                cs.setNull(17, java.sql.Types.SMALLINT);
            } else {
                cs.setShort(17, Short.parseShort(req.getMemberListType()));
            }

            // 登録結果（リターンコード）
            cs.registerOutParameter(18, java.sql.Types.SMALLINT);

            log.info("ProcAddDestination呼出（開始）：" + new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()));
            cs.executeUpdate();
            log.info("ProcAddDestination呼出（終了）：" + new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()));

            // ストアドからの返却値
            apiInfo.setStoredName("ProcAddDestination");
            apiInfo.setStoredCode(cs.getShort(18));
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
