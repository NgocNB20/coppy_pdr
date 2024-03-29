package jp.co.hankyuhanshin.itec.ukapi.pdr.resources;

import jp.co.hankyuhanshin.itec.ukapi.pdr.common.ApiInfo;
import jp.co.hankyuhanshin.itec.ukapi.pdr.common.Constant;
import jp.co.hankyuhanshin.itec.ukapi.pdr.common.Utility;
import jp.co.hankyuhanshin.itec.ukapi.pdr.request.feedcontents.GetUkUniSearchContentsRequest;
import jp.co.hankyuhanshin.itec.ukapi.pdr.request.feedcontents.GetUkUniSuggestContentsRequest;
import jp.co.hankyuhanshin.itec.ukapi.pdr.request.feedgoods.GetUkUniSearchGoodsRequest;
import jp.co.hankyuhanshin.itec.ukapi.pdr.request.feedgoods.GetUkUniSuggestGoodsRequest;
import jp.co.hankyuhanshin.itec.ukapi.pdr.request.rword.GetUkRWordRequest;
import jp.co.hankyuhanshin.itec.ukapi.pdr.response.UkResponse;
import jp.co.hankyuhanshin.itec.ukapi.pdr.response.UkResponseHeaderInfo;
import jp.co.hankyuhanshin.itec.ukapi.pdr.response.UkResponseInfo;
import jp.co.hankyuhanshin.itec.ukapi.pdr.response.feedcontents.GetUkUniSearchContentsResponse;
import jp.co.hankyuhanshin.itec.ukapi.pdr.response.feedgoods.GetUkUniSearchGoodsHeaderFallback;
import jp.co.hankyuhanshin.itec.ukapi.pdr.response.feedgoods.GetUkUniSearchGoodsResponse;
import jp.co.hankyuhanshin.itec.ukapi.pdr.response.rword.GetUkRWordInfoDocs;
import jp.co.hankyuhanshin.itec.ukapi.pdr.response.rword.GetUkRWordInfoWord;
import jp.co.hankyuhanshin.itec.ukapi.pdr.response.rword.GetUkRWordResponse;
import jp.co.hankyuhanshin.itec.ukapi.pdr.response.suggest.GetUkUniSuggestCampaign;
import jp.co.hankyuhanshin.itec.ukapi.pdr.response.suggest.GetUkUniSuggestCampaignDocs;
import jp.co.hankyuhanshin.itec.ukapi.pdr.response.suggest.GetUkUniSuggestCampaignResponse;
import jp.co.hankyuhanshin.itec.ukapi.pdr.response.suggest.GetUkUniSuggestCategory;
import jp.co.hankyuhanshin.itec.ukapi.pdr.response.suggest.GetUkUniSuggestCategoryDocs;
import jp.co.hankyuhanshin.itec.ukapi.pdr.response.suggest.GetUkUniSuggestCategoryResponse;
import jp.co.hankyuhanshin.itec.ukapi.pdr.response.suggest.GetUkUniSuggestHistory;
import jp.co.hankyuhanshin.itec.ukapi.pdr.response.suggest.GetUkUniSuggestHistoryDocs;
import jp.co.hankyuhanshin.itec.ukapi.pdr.response.suggest.GetUkUniSuggestHistoryResponse;
import jp.co.hankyuhanshin.itec.ukapi.pdr.response.suggest.GetUkUniSuggestKeyword;
import jp.co.hankyuhanshin.itec.ukapi.pdr.response.suggest.GetUkUniSuggestKeywordDocs;
import jp.co.hankyuhanshin.itec.ukapi.pdr.response.suggest.GetUkUniSuggestKeywordResponse;
import jp.co.hankyuhanshin.itec.ukapi.pdr.response.suggest.GetUkUniSuggestModel;
import jp.co.hankyuhanshin.itec.ukapi.pdr.response.suggest.GetUkUniSuggestModelDocs;
import jp.co.hankyuhanshin.itec.ukapi.pdr.response.suggest.GetUkUniSuggestModelResponse;
import jp.co.hankyuhanshin.itec.ukapi.pdr.response.suggest.GetUkUniSuggestProduct;
import jp.co.hankyuhanshin.itec.ukapi.pdr.response.suggest.GetUkUniSuggestProductDocs;
import jp.co.hankyuhanshin.itec.ukapi.pdr.response.suggest.GetUkUniSuggestProductResponse;
import jp.co.hankyuhanshin.itec.webapi.pdr.common.PropManeger;
import net.arnx.jsonic.JSON;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import java.sql.Array;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * UK-API連携 関連ワードAPI<br/>
 * <pre>
 * ・関連ワード
 * </pre>
 *
 * @author tt32117
 */
@Path("/ukapi")
public class UkApi {
    /** サーブレットコンテキスト */
    @Context
    private ServletContext context;

    /** 関連ワードで一度に取得する上限のデフォルト */
    private final static int rWordLimit = 10;

    /** ユニサーチで一度に取得する商品数上限のデフォルト */
    private final static int goodsLimit = 50;

    /** ユニサーチで一度に取得するコンテンツ数上限のデフォルト */
    private final static int contentsLimit = 20;

    /**
     * 関連ワードAPI
     * @param kw 検索キーワード
     * @return JSON形式の処理結果
     */
    @GET
    @Path("/rword")
    public Response getUkRWord(@QueryParam("kw") String kw, @QueryParam("rows") Integer rows) {
        Logger log = LogManager.getLogger("getUkRWord");

        // 返却情報
        UkResponse response = new UkResponse();
        UkResponseHeaderInfo header = new UkResponseHeaderInfo();
        ApiInfo apiInfo = new ApiInfo("getUkRWord", "関連ワード[uk/GetUkRWord]", context, log);

        Connection conn = null;
        CallableStatement cs = null;

        try {

            // INパラメータをログに出力する
            // リクエストデータ
            GetUkRWordRequest req = new GetUkRWordRequest();
            req.setKw(kw);
            req.setRows(rows);

            // エラーキー情報を共通セット
            Utility.setErrorKeyInfo(apiInfo, kw);

            // JDBC接続
            String driverStr = PropManeger.getConf(apiInfo.getContext(), "db.jdbc.driver");
            Driver driver = (Driver) Class.forName(driverStr).newInstance();
            String connUrl = PropManeger.getConf(apiInfo.getContext(), "db.jdbc.url");
            conn = driver.connect(connUrl, new Properties());

            // パラメータをセットし、ストアドを呼出す
            cs = conn.prepareCall("{call ProcGetUkRWord(?)}");
            cs.setString(1, req.getKw());

            // 処理開始時間
            final long accepted = System.currentTimeMillis();
            cs.executeQuery();
            final long elapsed = System.currentTimeMillis() - accepted;

            // レスポンスデータ
            Integer status = 0;
            GetUkRWordResponse res = null;
            // 数値フォーマット
            DecimalFormat decimalFormat = new DecimalFormat("000.000");

            // 処理結果を取得してしまうとResultSetがクリアされてしまう為
            // 前もって取得しておく必要がある
            ResultSet rs = cs.getResultSet();
            List<Object> resInfoList = new ArrayList<>();

            while (rs != null && rs.next()) {
                res = new GetUkRWordResponse();
                res.setWord(rs.getString(1));
                status = rs.getInt(2);
                resInfoList.add(res);
            }

            // ストアドからの返却値
            apiInfo.setStoredName("ProcGetUkRWord");
            apiInfo.setStoredCode(status);
            apiInfo.setQTime(decimalFormat.format((elapsed / 1000d)));

            Utility.makeResult(apiInfo, response, header);

            // 正常応答の場合、セット
            if (Constant.STORED_OK == header.getStatus()) {
                UkResponseInfo ukResponseInfo = new UkResponseInfo();
                // ヒット件数セット
                ukResponseInfo.setNumFound(resInfoList.size());
                // ヒット件数が上限を超えた分は切り捨てる
                int resSize = resInfoList.size();
                int limit = rWordLimit;
                if (rows != null) {
                    limit = rows;
                }
                if (resSize > limit) {
                    resInfoList.subList(limit, resSize).clear();
                }

                if (resInfoList != null && !resInfoList.isEmpty()) {
                    GetUkRWordInfoWord relatedWord = new GetUkRWordInfoWord();
                    GetUkRWordInfoDocs docs = new GetUkRWordInfoDocs();
                    docs.setItem(resInfoList);
                    relatedWord.setDocs(docs);
                    ukResponseInfo.setRelatedWord(relatedWord);
                }
                response.setResponse(ukResponseInfo);
            }

        } catch (Exception ex) {
            apiInfo.setException(ex);
            Utility.logicException(apiInfo, response, header);

        } finally {
            // クローズ処理
            Utility.close(cs, conn, apiInfo);
        }
        // 処理結果をJSON形式に変換して返却
        return Utility.createResponse(log, response, kw, Level.INFO);
    }

    /**
     * ユニサーチ（商品）API
     * @param category カテゴリ
     * @param kw 検索キーワード
     * @param page 検索結果ページ
     * @param sort ソート種類
     * @param user ユーザID
     * @return JSON or JSONP形式の処理結果
     */
    @GET
    @Path("/p1/v1/pdr")
    public Response getUkUniSearchGoods(@QueryParam("fq.category") String category,
                                        @QueryParam("kw") String kw,
                                        @QueryParam("page") Integer page,
                                        @QueryParam("rows") Integer rows,
                                        @QueryParam("sort") String sort,
                                        @QueryParam("user") String user) {
        Logger log = LogManager.getLogger("getUkUniSearchGoods");

        // 返却情報
        UkResponse response = new UkResponse();
        UkResponseHeaderInfo header = new UkResponseHeaderInfo();
        ApiInfo apiInfo = new ApiInfo("getUkUniSearchGoods", "ユニサーチ（商品）[uk/GetUkUniSearchGoods]", context, log);

        Connection conn = null;
        CallableStatement cs = null;

        try {

            // INパラメータをログに出力する
            // 255文字以上は切り捨て
            if (kw != null && kw.length() > 255) {
                kw = kw.substring(0, 254);
            }

            // エラーキー情報を共通セット
            Utility.setErrorKeyInfo(apiInfo, kw);

            // JDBC接続
            String driverStr = PropManeger.getConf(apiInfo.getContext(), "db.jdbc.driver");
            Driver driver = (Driver) Class.forName(driverStr).newInstance();
            String connUrl = PropManeger.getConf(apiInfo.getContext(), "db.jdbc.url");
            conn = driver.connect(connUrl, new Properties());

            // リクエストデータ
            GetUkUniSearchGoodsRequest req = new GetUkUniSearchGoodsRequest();

            req.setCategory(category);
            req.setPage(page);
            req.setRows(rows);
            req.setSort(sort);
            Array kwArray = null;
            // キーワードは配列で渡す
            if (kw != null && !kw.isEmpty()) {
                kwArray = conn.createArrayOf("VARCHAR", kw.split("[\\s|　]+"));
                req.setKw(kwArray);
            }

            Utility.createRequestLog(log, req, Level.INFO);

            // パラメータをセットし、ストアドを呼出す
            cs = conn.prepareCall("{call ProcGetUkUniSearchGoods(?,?,?)}");
            cs.setArray(1, kwArray);
            cs.setString(2, category);
            cs.setString(3, sort);

            // 処理開始時間
            final long accepted = System.currentTimeMillis();
            cs.executeQuery();
            final long elapsed = System.currentTimeMillis() - accepted;

            // レスポンスデータ
            int status = 0;
            GetUkUniSearchGoodsResponse res = null;
            // 数値フォーマット
            DecimalFormat decimalFormat = new DecimalFormat("000.000");

            // 処理結果を取得してしまうとResultSetがクリアされてしまう為
            // 前もって取得しておく必要がある
            ResultSet rs = cs.getResultSet();

            // レスポンス情報一時用リスト
            List<GetUkUniSearchGoodsResponse> tmpInfoList = new ArrayList<>();
            // カテゴリIDリスト作成用
            List<Map<String, String>> maplist = new ArrayList<>();
            // フォールバック後のキーワード格納用
            String[] fallbackkw = null;
            // docsリスト
            List<Object> resInfoList = new ArrayList<>();

            while (rs != null && rs.next()) {
                res = new GetUkUniSearchGoodsResponse();
                String groupId = rs.getString(1);
                res.setGroup_id(groupId);
                res.setNew_flg(rs.getInt(2));
                res.setSale_flg(rs.getInt(3));
                res.setReserve_flg(rs.getInt(4));
                res.setOutlet_flg(rs.getInt(5));
                res.setItem_name(rs.getString(6));
                res.setSale_comment(rs.getString(7));
                res.setMin_price(rs.getInt(8));
                res.setMax_price(rs.getInt(9));
                res.setSale_min_price(rs.getInt(10));
                res.setSale_max_price(rs.getInt(11));
                res.setItem_status(rs.getInt(13));
                res.setDrug_flg(rs.getInt(14));
                res.setItem_overview(rs.getString(15));
                res.setNew_date(rs.getTimestamp(16));
                res.setCatalog_id(rs.getInt(17));
                if (rs.getArray(18) != null) {
                    fallbackkw = (String[]) rs.getArray(18).getArray();
                }
                status = rs.getInt(19);
                // カテゴリーIDはgroup_idごとにリスト化する必要があるため別のMapに格納する
                Map<String, String> idCategoryMap = new HashMap<>();
                idCategoryMap.put("id", groupId);
                idCategoryMap.put("categoryid", rs.getString(12));
                maplist.add(idCategoryMap);
                tmpInfoList.add(res);
            }

            //group_idが重複している場合はcategory_idをリストに追加する
            Map<String, List<String>> result = new HashMap<>();
            for (Map<String, String> map : maplist) {
                String id = map.get("id");
                String categoryid = map.get("categoryid");

                if (result.containsKey(id)) {
                    result.get(id).add(categoryid);
                } else {
                    List<String> categoryids = new ArrayList<>();
                    categoryids.add(categoryid);
                    result.put(id, categoryids);
                }
            }

            // category_id_listをレスポンスListに格納する
            List<String> idlist = new ArrayList<>();
            for (GetUkUniSearchGoodsResponse tmpInfo : tmpInfoList) {
                if (!idlist.contains(tmpInfo.getGroup_id())) {
                    idlist.add(tmpInfo.getGroup_id());
                    tmpInfo.setCategory_id_list(result.get(tmpInfo.getGroup_id()));
                    resInfoList.add(tmpInfo);
                }
            }

            // ストアドからの返却値
            apiInfo.setStoredName("ProcGetUkUniSearchGoods");
            apiInfo.setStoredCode(status);
            apiInfo.setQTime(decimalFormat.format((elapsed / 1000d)));
            // フォールバックレスポンスを設定
            GetUkUniSearchGoodsHeaderFallback fallback = new GetUkUniSearchGoodsHeaderFallback();
            if (fallbackkw != null) {
                String keyword = String.join(" ", fallbackkw);
                // 小文字に変換して比較
                if (!Normalizer.normalize(keyword, Normalizer.Form.NFKC)
                               .equalsIgnoreCase(Normalizer.normalize(kw, Normalizer.Form.NFKC))) {
                    fallback.setType("word_delete");
                    fallback.setKeyword(keyword);
                }
            }
            // リクエストIDを設定
            // モックでは日時を設定する
            SimpleDateFormat date = new SimpleDateFormat("yyyy:MM:dd:HH:mm:ss");
            Date currentDate = new Date();
            String reqId = date.format(currentDate);

            Utility.makeResult(apiInfo, response, header, reqId, fallback);

            // 正常応答の場合、セット
            if (Constant.STORED_OK == header.getStatus()) {
                UkResponseInfo ukResponseInfo = new UkResponseInfo();
                Integer limit = goodsLimit;
                if (rows != null) {
                    limit = rows;
                }
                // 一度に取得可能な検索結果ドキュメントが設定されているためレスポンスを切り分ける
                // 最初の取得要素
                int firstElements = (req.getPage() - 1) * limit;
                // 最後の取得要素
                int lastElements = req.getPage() * limit;
                if (lastElements > resInfoList.size()) {
                    lastElements = resInfoList.size();
                }
                List<Object> returnInfoList = resInfoList.subList(firstElements, lastElements);

                if (returnInfoList != null && !returnInfoList.isEmpty()) {
                    ukResponseInfo.setPage(String.valueOf(req.getPage()));
                    ukResponseInfo.setDocs(returnInfoList);
                }
                ukResponseInfo.setNumFound(resInfoList.size());
                response.setResponse(ukResponseInfo);
            }

        } catch (Exception ex) {
            apiInfo.setException(ex);
            Utility.logicException(apiInfo, response, header);

        } finally {
            // クローズ処理
            Utility.close(cs, conn, apiInfo);
        }
        // 処理結果をJSON形式に変換して返却
        return Utility.createResponse(log, response, kw, Level.INFO);
    }

    /**
     * ユニサーチ（コンテンツ）API
     * @param kw 検索キーワード
     * @param page 検索結果ページ
     * @param sort ソート種類
     * @param user ユーザID
     * @return JSON形式の処理結果
     */
    @GET
    @Path("/p1/v1/pdrcontents")
    public Response getUkUniSearchContents(@QueryParam("kw") String kw,
                                           @QueryParam("page") Integer page,
                                           @QueryParam("rows") Integer rows,
                                           @QueryParam("sort") String sort,
                                           @QueryParam("user") String user) {
        Logger log = LogManager.getLogger("getUkUniSearchContents");

        // 返却情報
        UkResponse response = new UkResponse();
        UkResponseHeaderInfo header = new UkResponseHeaderInfo();
        ApiInfo apiInfo =
                        new ApiInfo("getUkUniSearchContents", "ユニサーチ（コンテンツ）[uk/GetUkUniSearchContents]", context, log);

        Connection conn = null;
        CallableStatement cs = null;

        try {

            // INパラメータをログに出力する
            // 255文字以上は切り捨て
            if (kw != null && kw.length() > 255) {
                kw = kw.substring(0, 254);
            }

            // エラーキー情報を共通セット
            Utility.setErrorKeyInfo(apiInfo, kw);

            // JDBC接続
            String driverStr = PropManeger.getConf(apiInfo.getContext(), "db.jdbc.driver");
            Driver driver = (Driver) Class.forName(driverStr).newInstance();
            String connUrl = PropManeger.getConf(apiInfo.getContext(), "db.jdbc.url");
            conn = driver.connect(connUrl, new Properties());

            // リクエストデータ
            GetUkUniSearchContentsRequest req = new GetUkUniSearchContentsRequest();

            req.setPage(page);
            req.setRows(rows);
            req.setSort(sort);
            Array kwArray = null;
            // キーワードは配列で渡す
            if (kw != null && !kw.isEmpty()) {
                kwArray = conn.createArrayOf("VARCHAR", kw.split("[\\s|　]+"));
                req.setKw(kwArray);
            }
            Utility.createRequestLog(log, req, Level.INFO);

            // パラメータをセットし、ストアドを呼出す
            cs = conn.prepareCall("{call ProcGetUkUniSearchContents(?)}");
            cs.setArray(1, kwArray);
            // 処理開始時間
            final long accepted = System.currentTimeMillis();
            cs.executeQuery();
            final long elapsed = System.currentTimeMillis() - accepted;

            // レスポンスデータ
            int status = 0;
            GetUkUniSearchContentsResponse res = null;
            // 数値フォーマット
            DecimalFormat decimalFormat = new DecimalFormat("000.000");

            // 処理結果を取得してしまうとResultSetがクリアされてしまう為
            // 前もって取得しておく必要がある
            ResultSet rs = cs.getResultSet();

            // フォールバック後のキーワード格納用
            String[] fallbackkw = null;
            // docsリスト
            List<Object> resInfoList = new ArrayList<>();

            while (rs != null && rs.next()) {
                res = new GetUkUniSearchContentsResponse();
                res.setContent_id(rs.getString(1));
                res.setContent_name(rs.getString(2));
                res.setTransition_url(rs.getString(3));
                res.setContent_image_url(rs.getString(4));
                if (rs.getArray(5) != null) {
                    fallbackkw = (String[]) rs.getArray(5).getArray();
                }
                status = rs.getInt(6);

                resInfoList.add(res);
            }

            // ストアドからの返却値
            apiInfo.setStoredName("ProcGetUkUniSearchContents");
            apiInfo.setStoredCode(status);
            apiInfo.setQTime(decimalFormat.format((elapsed / 1000d)));
            // フォールバックレスポンスを設定
            GetUkUniSearchGoodsHeaderFallback fallback = new GetUkUniSearchGoodsHeaderFallback();
            if (fallbackkw != null) {
                String keyword = String.join(" ", fallbackkw);
                // 小文字に変換して比較
                if (!Normalizer.normalize(keyword, Normalizer.Form.NFKC)
                               .equalsIgnoreCase(Normalizer.normalize(kw, Normalizer.Form.NFKC))) {
                    fallback.setType("word_delete");
                    fallback.setKeyword(keyword);
                }
            }
            // リクエストIDを設定
            // モックでは日時を設定する
            SimpleDateFormat date = new SimpleDateFormat("yyyy:MM:dd:HH:mm:ss");
            Date currentDate = new Date();
            String reqId = date.format(currentDate);

            Utility.makeResult(apiInfo, response, header, reqId, fallback);

            // 正常応答の場合、セット
            if (Constant.STORED_OK == header.getStatus()) {
                UkResponseInfo ukResponseInfo = new UkResponseInfo();
                Integer limit = contentsLimit;
                if (rows != null) {
                    limit = rows;
                }
                // 一度に取得可能な検索結果ドキュメントは最大20件のため、レスポンスを切り分ける
                // また、リクエストのページ数にかかわらず1件目から取得する
                // 最初の取得要素
                int firstElements = 0;
                // 最後の取得要素
                int lastElements = limit;
                if (lastElements > resInfoList.size()) {
                    lastElements = resInfoList.size();
                }
                List<Object> returnInfoList = resInfoList.subList(firstElements, lastElements);

                if (returnInfoList != null && !returnInfoList.isEmpty()) {
                    ukResponseInfo.setPage(String.valueOf(req.getPage()));
                    ukResponseInfo.setDocs(returnInfoList);
                }
                ukResponseInfo.setNumFound(resInfoList.size());
                response.setResponse(ukResponseInfo);
            }

        } catch (Exception ex) {
            apiInfo.setException(ex);
            Utility.logicException(apiInfo, response, header);

        } finally {
            // クローズ処理
            Utility.close(cs, conn, apiInfo);
        }
        // 処理結果をJSON形式に変換して返却
        return Utility.createResponse(log, response, kw, Level.INFO);
    }

    /**
     * ユニサジェスト（商品）API
     * @param cbk コールバック
     * @param kw 検索キーワード
     * @param rows ユニサジェストの検索結果件数
     * @param drCategory カテゴリサジェストの検索結果件数
     * @param drModelNumber 型番サジェストの検索結果件数
     * @param drProductName 商品名サジェストの検索結果件数
     * @param loginId ログイン後ハッシュId
     * @param uid 端末識別番号
     * @param history 履歴サジェストフラグ
     * @return JSONP形式の処理結果
     */
    @GET
    @Path("/qsuggest/v1/pdr")
    public Response getUkUniSuggestGoods(@QueryParam("cbk") String cbk,
                                         @QueryParam("kw") String kw,
                                         @QueryParam("rows") Integer rows,
                                         @QueryParam("dr_category") Integer drCategory,
                                         @QueryParam("dr_modelnumber") Integer drModelNumber,
                                         @QueryParam("dr_productname") Integer drProductName,
                                         @QueryParam("loginid") String loginId,
                                         @QueryParam("uid") String uid,
                                         @QueryParam("history") String history) {
        Logger log = LogManager.getLogger("getUkUniSuggestGoods");

        // 返却情報
        UkResponse response = new UkResponse();
        UkResponseHeaderInfo header = new UkResponseHeaderInfo();
        ApiInfo apiInfo = new ApiInfo("getUkUniSuggestGoods", "ユニサジェスト（商品）[uk/GetUkUniSuggestGoods]", context, log);

        Connection conn = null;
        CallableStatement cs = null;

        try {

            // INパラメータをログに出力する
            // 255文字以上は切り捨て
            if (kw != null && kw.length() > 255) {
                kw = kw.substring(0, 254);
            }
            // エラーキー情報を共通セット
            Utility.setErrorKeyInfo(apiInfo, kw);

            // JDBC接続
            String driverStr = PropManeger.getConf(apiInfo.getContext(), "db.jdbc.driver");
            Driver driver = (Driver) Class.forName(driverStr).newInstance();
            String connUrl = PropManeger.getConf(apiInfo.getContext(), "db.jdbc.url");
            conn = driver.connect(connUrl, new Properties());

            // リクエストデータ
            GetUkUniSuggestGoodsRequest req = new GetUkUniSuggestGoodsRequest();
            req.setKw(kw);
            req.setRows(rows);
            req.setDrCategory(drCategory);
            req.setDrModelNumber(drModelNumber);
            req.setDrProductName(drProductName);
            req.setUid(uid);
            req.setLoginId(loginId);
            req.setHistory(history);
            Utility.createRequestLog(log, req, Level.INFO);

            // パラメータをセットし、ストアドを呼出す
            cs = conn.prepareCall("{call ProcGetUkUniSuggestGoods(?,?,?)}");
            cs.setString(1, req.getKw());
            cs.setString(2, req.getUid());
            cs.setString(3, req.getLoginId());

            // 処理開始時間
            final long accepted = System.currentTimeMillis();
            cs.executeQuery();
            final long elapsed = System.currentTimeMillis() - accepted;

            // レスポンスデータ
            int status = 0;
            String type = "";
            GetUkUniSuggestKeywordResponse resKeyword = null;
            GetUkUniSuggestCategoryResponse resCategory = null;
            GetUkUniSuggestModelResponse resModel = null;
            GetUkUniSuggestProductResponse resProduct = null;
            GetUkUniSuggestHistoryResponse resHistory = null;

            // 数値フォーマット
            DecimalFormat decimalFormat = new DecimalFormat("000.000");

            // 処理結果を取得してしまうとResultSetがクリアされてしまう為
            // 前もって取得しておく必要がある
            ResultSet rs = cs.getResultSet();

            // レスポンス情報一時用リスト
            List<GetUkUniSuggestKeywordResponse> resKeywordInfoList = new ArrayList<>();
            List<GetUkUniSuggestCategoryResponse> resCategoryInfoList = new ArrayList<>();
            List<GetUkUniSuggestModelResponse> resModelInfoList = new ArrayList<>();
            List<GetUkUniSuggestProductResponse> resProductInfoList = new ArrayList<>();
            List<GetUkUniSuggestHistoryResponse> resHistoryInfoList = new ArrayList<>();

            while (rs != null && rs.next()) {
                status = rs.getInt(1);
                type = rs.getString(2);

                if ("keyword".equals(type)) {
                    resKeyword = new GetUkUniSuggestKeywordResponse();
                    resKeyword.setWord(rs.getString(3));
                    resKeywordInfoList.add(resKeyword);
                }
                if ("category".equals(type)) {
                    resCategory = new GetUkUniSuggestCategoryResponse();
                    resCategory.setDirect_category_id(rs.getString(3));
                    resCategory.setDirect_category_name(rs.getString(4));
                    resCategoryInfoList.add(resCategory);
                }
                if ("model".equals(type)) {
                    resModel = new GetUkUniSuggestModelResponse();
                    resModel.setDirect_group_id(rs.getString(3));
                    resModel.setDirect_item_name(rs.getString(4));
                    resModel.setDirect_min_price(rs.getString(5));
                    resModel.setDirect_max_price(rs.getString(6));
                    resModel.setDirect_sale_min_price(rs.getString(7));
                    resModel.setDirect_sale_max_price(rs.getString(8));
                    resModelInfoList.add(resModel);
                }
                if ("product".equals(type)) {
                    resProduct = new GetUkUniSuggestProductResponse();
                    resProduct.setDirect_group_id(rs.getString(3));
                    resProduct.setDirect_item_name(rs.getString(4));
                    resProduct.setDirect_min_price(rs.getString(5));
                    resProduct.setDirect_max_price(rs.getString(6));
                    resProduct.setDirect_sale_min_price(rs.getString(7));
                    resProduct.setDirect_sale_max_price(rs.getString(8));
                    resProductInfoList.add(resProduct);
                }
                if ("history".equals(type)) {
                    resHistory = new GetUkUniSuggestHistoryResponse();
                    resHistory.setWord(rs.getString(3));
                    resHistoryInfoList.add(resHistory);
                }
            }

            // ストアドからの返却値
            apiInfo.setStoredName("ProcGetUkUniSuggestGoods");
            apiInfo.setStoredCode(status);
            apiInfo.setQTime(decimalFormat.format((elapsed / 1000d)));

            Utility.makeResult(apiInfo, response, header);

            // 正常応答の場合、セット
            if (Constant.STORED_OK == header.getStatus()) {
                UkResponseInfo ukResponseInfo = new UkResponseInfo();

                int keywordNum = resKeywordInfoList.size();
                int categoryNum = resCategoryInfoList.size();
                int modelNum = resModelInfoList.size();
                int productNum = resProductInfoList.size();
                int historyNum = resHistoryInfoList.size();

                if (history != null) {
                    ukResponseInfo.setNumFound(historyNum);
                } else {
                    ukResponseInfo.setNumFound(kw != null ? keywordNum + categoryNum + modelNum + productNum : 0);
                }

                // 指定数削除
                if (keywordNum > 0 && kw != null) {
                    if (rows - drCategory - drModelNumber - drProductName <= keywordNum) {
                        resKeywordInfoList.subList(rows - drCategory - drModelNumber - drProductName, keywordNum)
                                          .clear();
                    }
                    GetUkUniSuggestKeywordDocs keywordDocs = new GetUkUniSuggestKeywordDocs();
                    keywordDocs.setItem(resKeywordInfoList);
                    GetUkUniSuggestKeyword keywordInfo = new GetUkUniSuggestKeyword();
                    keywordInfo.setDocs(keywordDocs);
                    ukResponseInfo.setKeyword(keywordInfo);
                }
                if (categoryNum > 0 && kw != null) {
                    if (drCategory <= categoryNum) {
                        resCategoryInfoList.subList(drCategory, categoryNum).clear();
                    }
                    GetUkUniSuggestCategoryDocs categoryDocs = new GetUkUniSuggestCategoryDocs();
                    categoryDocs.setItem(resCategoryInfoList);
                    GetUkUniSuggestCategory categoryInfo = new GetUkUniSuggestCategory();
                    categoryInfo.setDocs(categoryDocs);
                    ukResponseInfo.setCategory(categoryInfo);
                }
                if (modelNum > 0 && kw != null) {
                    if (drModelNumber <= modelNum) {
                        resModelInfoList.subList(drModelNumber, modelNum).clear();
                    }
                    GetUkUniSuggestModelDocs modelDocs = new GetUkUniSuggestModelDocs();
                    modelDocs.setItem(resModelInfoList);
                    GetUkUniSuggestModel modelInfo = new GetUkUniSuggestModel();
                    modelInfo.setDocs(modelDocs);
                    ukResponseInfo.setModelNumber(modelInfo);
                }
                if (productNum > 0 && kw != null) {
                    if (drProductName <= productNum) {
                        resProductInfoList.subList(drProductName, productNum).clear();
                    }
                    GetUkUniSuggestProductDocs productDocs = new GetUkUniSuggestProductDocs();
                    productDocs.setItem(resProductInfoList);
                    GetUkUniSuggestProduct product = new GetUkUniSuggestProduct();
                    product.setDocs(productDocs);
                    ukResponseInfo.setProductName(product);
                }
                // 履歴サジェストは固定値渡す
                if (historyNum > 0 && "on".equals(history)) {
                    if (rows <= historyNum) {
                        resHistoryInfoList.subList(rows, historyNum).clear();
                    }
                    GetUkUniSuggestHistoryDocs historyDocs = new GetUkUniSuggestHistoryDocs();
                    historyDocs.setItem(resHistoryInfoList);
                    GetUkUniSuggestHistory historyInfo = new GetUkUniSuggestHistory();
                    historyInfo.setDocs(historyDocs);
                    ukResponseInfo.setHistory(historyInfo);
                }

                response.setResponse(ukResponseInfo);
            }

        } catch (Exception ex) {
            apiInfo.setException(ex);
            Utility.logicException(apiInfo, response, header);

        } finally {
            // クローズ処理
            Utility.close(cs, conn, apiInfo);
        }
        // 処理結果をJSON形式に変換して返却
        return Utility.createResponseForJsonp(log, response, kw, Level.INFO, cbk);
    }

    /**
     * 履歴削除（商品）API
     * @param q 検索キーワード
     * @param loginId ログイン後ハッシュId
     * @param uid 端末識別番号
     * @return JSON形式の処理結果
     */
    @GET
    @Path("/history_delete/v1/pdr")
    public Response deleteUkHistoryGoods(@QueryParam("cbk") String cbk,
                                         @QueryParam("q") String q,
                                         @QueryParam("uid") String uid,
                                         @QueryParam("loginid") String loginId) {
        Logger log = LogManager.getLogger("deleteUkHistoryGoods");

        // 返却情報
        Response response = null;
        ApiInfo apiInfo = new ApiInfo("deleteUkHistoryGoods", "履歴削除（商品）[uk/DeleteUkHistoryGoods]", context, log);

        Connection conn = null;
        CallableStatement cs = null;

        try {
            // エラーキー情報を共通セット
            Utility.setErrorKeyInfo(apiInfo, q);

            if (uid.isEmpty() && loginId.isEmpty()) {
                // 異常応答
                Object responseBody = new Object() {
                    public final String message = "loginid or uid is missing";
                };

                JSON json = new JSON();
                String resultJSON = cbk + "(" + json.format(responseBody) + ")";

                log.info("[OUT]" + resultJSON);
                response = Response.ok().entity(resultJSON).build();

            } else {

                // JDBC接続
                String driverStr = PropManeger.getConf(apiInfo.getContext(), "db.jdbc.driver");
                Driver driver = (Driver) Class.forName(driverStr).newInstance();
                String connUrl = PropManeger.getConf(apiInfo.getContext(), "db.jdbc.url");
                conn = driver.connect(connUrl, new Properties());

                // リクエストデータ
                GetUkUniSuggestGoodsRequest req = new GetUkUniSuggestGoodsRequest();
                req.setKw(q);
                req.setUid(uid);
                req.setLoginId(loginId);
                Utility.createRequestLog(log, req, Level.INFO);

                // パラメータをセットし、ストアドを呼出す
                cs = conn.prepareCall("{call ProcDeleteUkHistoryGoods(?,?,?)}");
                cs.setString(1, req.getKw());
                cs.setString(2, req.getUid());
                cs.setString(3, req.getLoginId());

                // 処理開始時間
                cs.executeQuery();

                // レスポンスデータ
                int result = 0;

                // 処理結果を取得してしまうとResultSetがクリアされてしまう為
                // 前もって取得しておく必要がある
                ResultSet rs = cs.getResultSet();

                while (rs != null && rs.next()) {
                    result = rs.getInt(1);
                }
                log.info("[OUT]" + result);

                // ストアドからの返却値
                apiInfo.setStoredName("ProcDeleteUkHistoryGoods");

                // 正常応答の場合、セット
                Object responseBody = new Object() {
                    public final String message = "OK";
                };

                JSON json = new JSON();
                String resultJSON = cbk + "(" + json.format(responseBody) + ")";

                log.info("[OUT]" + resultJSON);
                response = Response.ok().entity(resultJSON).build();
            }

        } catch (Exception ex) {
            apiInfo.setException(ex);
        } finally {
            // クローズ処理
            Utility.close(cs, conn, apiInfo);
        }
        return response;
    }

    /**
     * ユニサジェスト（コンテンツ）API
     * @param cbk コールバック
     * @param kw 検索キーワード
     * @param rows ユニサジェストの検索結果件数
     * @param drCampaign 特集サジェストの検索結果件数
     * @param loginId ログイン後ハッシュId
     * @param uid 端末識別番号
     * @param history 履歴サジェストフラグ
     * @return JSONP形式の処理結果
     */
    @GET
    @Path("/qsuggest/v1/pdrcontents")
    public Response getUkUniSuggestContents(@QueryParam("cbk") String cbk,
                                            @QueryParam("kw") String kw,
                                            @QueryParam("rows") Integer rows,
                                            @QueryParam("dr_campaign") Integer drCampaign,
                                            @QueryParam("loginid") String loginId,
                                            @QueryParam("uid") String uid,
                                            @QueryParam("history") String history) {
        Logger log = LogManager.getLogger("getUkUniSuggestContents");

        // 返却情報
        UkResponse response = new UkResponse();
        UkResponseHeaderInfo header = new UkResponseHeaderInfo();
        ApiInfo apiInfo = new ApiInfo("getUkUniSuggestContents", "ユニサジェスト（コンテンツ）[uk/GetUkUniSuggestContents]", context,
                                      log
        );

        Connection conn = null;
        CallableStatement cs = null;

        try {

            // INパラメータをログに出力する
            // 255文字以上は切り捨て
            if (kw != null && kw.length() > 255) {
                kw = kw.substring(0, 254);
            }

            // エラーキー情報を共通セット
            Utility.setErrorKeyInfo(apiInfo, kw);

            // JDBC接続
            String driverStr = PropManeger.getConf(apiInfo.getContext(), "db.jdbc.driver");
            Driver driver = (Driver) Class.forName(driverStr).newInstance();
            String connUrl = PropManeger.getConf(apiInfo.getContext(), "db.jdbc.url");
            conn = driver.connect(connUrl, new Properties());

            // リクエストデータ
            GetUkUniSuggestContentsRequest req = new GetUkUniSuggestContentsRequest();
            req.setKw(kw);
            req.setRows(rows);
            req.setDrCampaign(drCampaign);
            req.setUid(uid);
            req.setLoginId(loginId);
            req.setHistory(history);
            Utility.createRequestLog(log, req, Level.INFO);

            // パラメータをセットし、ストアドを呼出す
            cs = conn.prepareCall("{call ProcGetUkUniSuggestContents(?,?,?)}");
            cs.setString(1, req.getKw());
            cs.setString(2, req.getUid());
            cs.setString(3, req.getLoginId());

            // 処理開始時間
            final long accepted = System.currentTimeMillis();
            cs.executeQuery();
            final long elapsed = System.currentTimeMillis() - accepted;

            // レスポンスデータ
            int status = 0;
            String type = "";
            GetUkUniSuggestKeywordResponse resKeyword = null;
            GetUkUniSuggestCampaignResponse resCampaign = null;
            GetUkUniSuggestHistoryResponse resHistory = null;

            // 数値フォーマット
            DecimalFormat decimalFormat = new DecimalFormat("000.000");

            // 処理結果を取得してしまうとResultSetがクリアされてしまう為
            // 前もって取得しておく必要がある
            ResultSet rs = cs.getResultSet();

            // レスポンス情報一時用リスト
            List<GetUkUniSuggestKeywordResponse> resKeywordInfoList = new ArrayList<>();
            List<GetUkUniSuggestCampaignResponse> resCampaignInfoList = new ArrayList<>();
            List<GetUkUniSuggestHistoryResponse> resHistoryInfoList = new ArrayList<>();

            while (rs != null && rs.next()) {
                status = rs.getInt(1);
                type = rs.getString(2);

                if ("keyword".equals(type)) {
                    resKeyword = new GetUkUniSuggestKeywordResponse();
                    resKeyword.setWord(rs.getString(3));
                    resKeywordInfoList.add(resKeyword);
                }
                if ("campaign".equals(type)) {
                    resCampaign = new GetUkUniSuggestCampaignResponse();
                    resCampaign.setDirect_campaign_url(rs.getString(3));
                    resCampaign.setDirect_campaign_title(rs.getString(4));
                    resCampaignInfoList.add(resCampaign);
                }
                if ("history".equals(type)) {
                    resHistory = new GetUkUniSuggestHistoryResponse();
                    resHistory.setWord(rs.getString(3));
                    resHistoryInfoList.add(resHistory);
                }
            }

            // ストアドからの返却値
            apiInfo.setStoredName("ProcGetUkUniSuggestContents");
            apiInfo.setStoredCode(status);
            apiInfo.setQTime(decimalFormat.format((elapsed / 1000d)));

            Utility.makeResult(apiInfo, response, header);

            // 正常応答の場合、セット
            if (Constant.STORED_OK == header.getStatus()) {
                UkResponseInfo ukResponseInfo = new UkResponseInfo();

                int keywordNum = resKeywordInfoList.size();
                int campaignNum = resCampaignInfoList.size();
                int historyNum = resHistoryInfoList.size();

                if (history != null) {
                    ukResponseInfo.setNumFound(historyNum);
                } else {
                    ukResponseInfo.setNumFound(kw != null ? keywordNum + campaignNum : 0);
                }

                // 指定数削除
                if (keywordNum > 0 && kw != null) {
                    if (rows - drCampaign <= keywordNum) {
                        resKeywordInfoList.subList(rows - drCampaign, keywordNum).clear();
                    }
                    GetUkUniSuggestKeywordDocs keywordDocs = new GetUkUniSuggestKeywordDocs();
                    keywordDocs.setItem(resKeywordInfoList);
                    GetUkUniSuggestKeyword keywordInfo = new GetUkUniSuggestKeyword();
                    keywordInfo.setDocs(keywordDocs);
                    ukResponseInfo.setKeyword(keywordInfo);
                }
                if (campaignNum > 0 && kw != null) {
                    if (drCampaign <= campaignNum) {
                        resCampaignInfoList.subList(drCampaign, campaignNum).clear();
                    }
                    GetUkUniSuggestCampaignDocs campaignDocs = new GetUkUniSuggestCampaignDocs();
                    campaignDocs.setItem(resCampaignInfoList);
                    GetUkUniSuggestCampaign campaignInfo = new GetUkUniSuggestCampaign();
                    campaignInfo.setDocs(campaignDocs);
                    ukResponseInfo.setCampaign(campaignInfo);
                }
                // 履歴サジェストは固定値渡す
                if (historyNum > 0 && "on".equals(history)) {
                    if (rows <= historyNum) {
                        resHistoryInfoList.subList(rows, historyNum).clear();
                    }
                    GetUkUniSuggestHistoryDocs historyDocs = new GetUkUniSuggestHistoryDocs();
                    historyDocs.setItem(resHistoryInfoList);
                    GetUkUniSuggestHistory historyInfo = new GetUkUniSuggestHistory();
                    historyInfo.setDocs(historyDocs);
                    ukResponseInfo.setHistory(historyInfo);
                }

                response.setResponse(ukResponseInfo);
            }

        } catch (Exception ex) {
            apiInfo.setException(ex);
            Utility.logicException(apiInfo, response, header);

        } finally {
            // クローズ処理
            Utility.close(cs, conn, apiInfo);
        }
        // 処理結果をJSON形式に変換して返却
        return Utility.createResponseForJsonp(log, response, kw, Level.INFO, cbk);
    }

    /**
     * 履歴削除（コンテンツ）API
     * @param q 検索キーワード
     * @param loginId ログイン後ハッシュId
     * @param uid 端末識別番号
     * @return JSON形式の処理結果
     */
    @GET
    @Path("/history_delete/v1/pdrcontents")
    public Response deleteUkHistoryContents(@QueryParam("cbk") String cbk,
                                            @QueryParam("q") String q,
                                            @QueryParam("uid") String uid,
                                            @QueryParam("loginid") String loginId) {
        Logger log = LogManager.getLogger("deleteUkHistoryContents");

        // 返却情報
        Response response = null;
        ApiInfo apiInfo =
                        new ApiInfo("deleteUkHistoryContents", "履歴削除（コンテンツ）[uk/DeleteUkHistoryContents]", context, log);

        Connection conn = null;
        CallableStatement cs = null;

        try {

            // INパラメータをログに出力する
            log.info("[IN]q:" + q);
            log.info("[IN]uid:" + uid);
            log.info("[IN]loginid:" + loginId);

            // エラーキー情報を共通セット
            Utility.setErrorKeyInfo(apiInfo, q);

            if (uid.isEmpty() && loginId.isEmpty()) {
                // 異常応答
                Object responseBody = new Object() {
                    public final String message = "loginid or uid is missing";
                };

                JSON json = new JSON();
                String resultJSON = cbk + "(" + json.format(responseBody) + ")";

                log.info("[OUT]" + resultJSON);
                response = Response.ok().entity(resultJSON).build();

            } else {

                // JDBC接続
                String driverStr = PropManeger.getConf(apiInfo.getContext(), "db.jdbc.driver");
                Driver driver = (Driver) Class.forName(driverStr).newInstance();
                String connUrl = PropManeger.getConf(apiInfo.getContext(), "db.jdbc.url");
                conn = driver.connect(connUrl, new Properties());

                // リクエストデータ
                GetUkUniSuggestGoodsRequest req = new GetUkUniSuggestGoodsRequest();
                req.setKw(q);
                req.setUid(uid);
                req.setLoginId(loginId);
                Utility.createRequestLog(log, req, Level.INFO);

                // パラメータをセットし、ストアドを呼出す
                cs = conn.prepareCall("{call ProcDeleteUkHistoryContents(?,?,?)}");
                cs.setString(1, req.getKw());
                cs.setString(2, req.getUid());
                cs.setString(3, req.getLoginId());

                // 処理開始時間
                cs.executeQuery();

                // レスポンスデータ
                int result = 0;

                // 処理結果を取得してしまうとResultSetがクリアされてしまう為
                // 前もって取得しておく必要がある
                ResultSet rs = cs.getResultSet();

                while (rs != null && rs.next()) {
                    result = rs.getInt(1);
                }
                log.info("[OUT]" + result);

                // ストアドからの返却値
                apiInfo.setStoredName("ProcDeleteUkHistoryContents");

                // 正常応答の場合、セット
                Object responseBody = new Object() {
                    public final String message = "OK";
                };

                JSON json = new JSON();
                String resultJSON = cbk + "(" + json.format(responseBody) + ")";

                log.info("[OUT]" + resultJSON);
                response = Response.ok().entity(resultJSON).build();
            }

        } catch (Exception ex) {
            apiInfo.setException(ex);
        } finally {
            // クローズ処理
            Utility.close(cs, conn, apiInfo);
        }
        return response;
    }
}
