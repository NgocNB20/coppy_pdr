/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.multipayment.impl;

import jp.co.hankyuhanshin.itec.hitmall.dao.multipayment.MulPayBillDao;
import jp.co.hankyuhanshin.itec.hitmall.entity.multipayment.MulPayBillEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.multipayment.MulPayBillLogic;
import jp.co.hankyuhanshin.itec.hmbase.application.AppLevelFacesMessage;
import jp.co.hankyuhanshin.itec.hmbase.util.AppLevelFacesMessageUtil;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.ConversionUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.NetworkUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.NumberUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * マルチペイメント請求ロジック実装クラス<br/>
 *
 * @author MN7017
 * @author matsumoto (itec) 2012/01/18 #2776 対応
 * @author Kaneko (itec) 2012/08/17 UtilからHelperへ変更　Util使用箇所をgetComponentで取得するように変更
 */
@Component
public class MulPayBillLogicImpl extends AbstractShopLogic implements MulPayBillLogic {

    /**
     * ロガー
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(MulPayBillLogicImpl.class);

    /**
     * マルチペイメント請求Dao<br/>
     */
    private final MulPayBillDao mulPayBillDao;

    @Autowired
    public MulPayBillLogicImpl(MulPayBillDao mulPayBillDao) {
        this.mulPayBillDao = mulPayBillDao;
    }

    /**
     * オーダーID受注コード部分取得
     *
     * @param orderId オーダーID
     * @return 受注コード部分。取得できなかった場合、null。
     */
    protected String extractOrderIdOrderCode(String orderId) {

        if (orderId == null) {
            return null;
        }

        return orderId.split("-")[0];
    }

    /**
     * オーダーID連番部分取得<br/>
     * 引数オーダーIDから連番部分を取得します。<br/>
     * 以下の場合、オーダーIDを取得できないので、nullを返却する。<br/>
     * ・orderIdがnull。<br/>
     * ・orderIdが受注番号と連番部に分割できない。<br/>
     * ・orderIdの連番部が数値ではない。<br/>
     *
     * @param orderId オーダーID
     * @return 連番部分が取得できた場合、連番部分の整数値。取得できなかった場合、null。
     */
    protected Integer extractOrderIdSequence(String orderId) {
        if (orderId == null) {
            return null;
        }

        if (!orderId.matches(".*-.*")) {
            return null;
        }

        // ３文字連番取得処理
        String[] splitOrderId = orderId.split("-");
        // 数値関連Helper取得
        NumberUtility numberUtility = ApplicationContextUtility.getBean(NumberUtility.class);
        if (numberUtility.isNumber(splitOrderId[1])) {
            // 変換Helper取得
            ConversionUtility conversionUtility = ApplicationContextUtility.getBean(ConversionUtility.class);

            // オーダーIDの連番部分が数値だった場合
            return conversionUtility.toInteger(splitOrderId[1]);
        } else {
            return null;
        }
    }

    /**
     * 対象受注SEQのオーダーID取得<br/>
     * <br/>
     *
     * @param orderSeq 受注SEQ
     * @return オーダーID
     */
    @Override
    public String getCurrentOrderId(Integer orderSeq) {

        // パラメータチェック
        ArgumentCheckUtil.assertNotNull("orderSeq", orderSeq);

        // オーダーID取得処理
        MulPayBillEntity mulPayBillEntity = mulPayBillDao.getLatestEntity(orderSeq);

        if (mulPayBillEntity == null) {
            return null;
        }

        return mulPayBillEntity.getOrderId();
    }

    /**
     * 対象の取引ID、取引パスワードのオーダーID取得<br/>
     *
     * @param accessId   取引ID
     * @param accessPass 取引パスワード
     * @return オーダーID
     */
    @Override
    public String getCurrentOrderId(String accessId, String accessPass) {

        // パラメータチェック
        ArgumentCheckUtil.assertNotNull("accessId", accessId);
        ArgumentCheckUtil.assertNotNull("accessPass", accessPass);

        // オーダーID取得処理
        MulPayBillEntity mulPayBillEntity = mulPayBillDao.getLatestEntityByAccessInfo(accessId, accessPass);

        if (mulPayBillEntity == null) {
            return null;
        }

        return mulPayBillEntity.getOrderId();
    }

    /**
     * 採番した次登録時のオーダーIDを取得する。<br/>
     *
     * @param shopSeq   ショップSEQ
     * @param orderSeq  オーダーSEQ
     * @param orderCode 受注コード
     * @return オーダーID
     */
    @Override
    public String getNextOrderId(Integer shopSeq, Integer orderSeq, String orderCode) {

        Integer seqno = 0;

        // パラメータチェック
        ArgumentCheckUtil.assertNotNull("shopSeq", shopSeq);
        ArgumentCheckUtil.assertNotNull("orderSeq", orderSeq);

        // ②MulPayBill
        // MulPayBillDao#getLatestEntity(orderSeq)を使用して最新のエンティティを取得する
        MulPayBillEntity mulPayBillEntity = mulPayBillDao.getLatestEntity(orderSeq);

        // ③エンティティを取得できた場合： extractOrderIdSequence(エンティティのorderId) で現在の連番を取得する
        if (mulPayBillEntity != null) {
            // 引数の orderCode が null の場合、エンティティの orderId より orderCode
            // 該当箇所を抜き取り、同名の引数を上書きする
            if (orderCode == null) {
                // エンティティよりorderCode部分を抜き取る。
                orderCode = extractOrderIdOrderCode(mulPayBillEntity.getOrderId());
            }

            seqno = extractOrderIdSequence(mulPayBillEntity.getOrderId());
            if (seqno == null) {
                // HIT-MALL上のマルペイ請求テーブル.オーダーIDが直接編集された場合にのみ、この条件文が実行される。
                // GMOへ送信するオーダーIDを組み立てることができないため、システムエラーを発生させ受注情報の修正を失敗させる。
                String[] msgArgs = {orderCode, mulPayBillEntity.getOrderId()};
                AppLevelFacesMessage facesMessage =
                                AppLevelFacesMessageUtil.getAllMessage(ERROR_CODE_ORDER_ID_IS_INVALID, msgArgs);
                String msg = facesMessage.getMessageCode() + ":" + facesMessage.getMessage();
                LOGGER.error(msg);

                // throwMessageを利用した場合、MultipaymentAlertInterceptor#exception2Messageでメールに出力するエラーメッセージが取得できない。
                // throwMessageによりスローされるAppLevelListException#getMessageがエラー情報を返却しなから。
                // 修正範囲を最小限に抑える目的でRuntimeExceptionを利用する。
                throw new RuntimeException(msg);
            }
        }

        // ⑤連番に+1する
        seqno++;

        // orderId作成

        String hostName = null;
        // ⑥処理しているサーバの名称を取得する。
        if (LOGGER.isDebugEnabled()) {
            // ネットワークHelper取得
            NetworkUtility networkUtility = ApplicationContextUtility.getBean(NetworkUtility.class);
            hostName = replaceHifunHostName(networkUtility.getLocalHostName());
        }
        // ⑦orderId を組み立てる
        // ①のエンティティのorderCode + "_" + 0埋め3桁された連番 + ≪"_" +
        // ホスト名≫
        // ※≪...≫箇所は、デバッグモードで実行している場合のみ追加する
        // ↑log4j のLOGGER.isDebugEnabled() を条件とする
        // ※全体が27文字を超えた場合、28文字目以降を切り捨てる
        // ※orderId の連番部分は 001 から始まる
        // ※デバッグ時のみにホスト名をつけるのは複数のローカルホストからGMOテストサーバにアクセスした際に、"orderId
        // はすでに使用済み"のエラーが出ないようにするための措置。本番では不要。

        String orderId = makeOrderId(orderCode, seqno, hostName);

        if (orderId.length() > 27) {
            orderId = orderId.substring(0, 26);
        }

        return orderId;
    }

    /**
     * マルチペイメント請求データを取得します。<br/>
     *
     * @param orderCode 受注コード
     * @return マルチペイメント請求
     */
    @Override
    public MulPayBillEntity getMulPayBillByOrderCode(String orderCode) {

        // マルチペイメント請求データ取得（受注コードに対し、オーダーID用接頭語 + オーダーIDをキーにデータ取得)
        String orderCodeWithPrefix = orderCode + "-";
        MulPayBillEntity mulPayBillEntity = mulPayBillDao.getLatestEntityByOrderCodeWithPrefix(orderCodeWithPrefix);

        return mulPayBillEntity;
    }

    /**
     * 英数字以外削除メソッド<br/>
     * ホスト名に英数字以外を含む場合削除する。<br/>
     *
     * @param hostname ホスト名称
     * @return ハイフン無しのホスト名称
     */
    public String replaceHifunHostName(String hostname) {

        if (hostname == null) {
            return null;
        }
        return hostname.replaceAll("[^a-zA-Z0-9]", "");
    }

    /**
     * オーダーID編集メソッド<br/>
     * 登録用オーダーIDを生成します。<br/>
     *
     * @param code     受注コード
     * @param seqno    連番
     * @param hostName ホスト名称
     * @return オーダーID
     */
    protected String makeOrderId(String code, Integer seqno, String hostName) {

        String retOrderId = null;

        String strSeqno = paddingZero(seqno, 3);
        retOrderId = code + "-" + strSeqno;

        // host名nullでない場合、これを連結する。
        if (hostName != null) {

            // host名称文字チェック（英数文字以外受け付けない）
            retOrderId = retOrderId + "-" + hostName;
        }

        return retOrderId;

    }

    /**
     * 前ZERO付き数値変換メソッド<br/>
     * 最大桁数に満たない場合、前ZEROを付与します。<br/>
     *
     * @param seqno 連番
     * @param digit 固定桁数
     * @return 前ZERO付き連番
     */
    public String paddingZero(Integer seqno, int digit) {

        String str = "" + seqno;
        int length = digit - str.length();
        if (length <= 0) {
            return str;
        }

        StringBuilder strbuf = new StringBuilder(length).append('0');
        for (int i = 1; i < length; i++) {
            strbuf.append('0');
        }

        return strbuf + str;

    }

    /**
     * マルチペイメント請求データを取得します。<br/>
     *
     * @param orderSeq       受注SEQ
     * @param orderVersionNo 受注履歴番号
     * @return マルチペイメント請求情報
     */
    @Override
    public MulPayBillEntity getMulPayBillByOrderSeqAndOrderVersionNo(Integer orderSeq, Integer orderVersionNo) {

        // 引数チェック
        ArgumentCheckUtil.assertNotNull("orderSeq", orderSeq);
        ArgumentCheckUtil.assertNotNull("orderVersionNo", orderVersionNo);

        // マルチペイメント請求情報の検索
        return mulPayBillDao.getMulPayBill(orderSeq, orderVersionNo);
    }

    /**
     * マルチペイメント請求データを取得します。<br/>
     * 指定した受注SEQで最新のマルチペイメント請求情報を取得する
     *
     * @param orderSeq 受注SEQ
     * @return マルチペイメント請求情報
     */
    @Override
    public MulPayBillEntity getMulPayBillByOrderSeq(Integer orderSeq) {
        // 引数チェック
        ArgumentCheckUtil.assertNotNull("orderSeq", orderSeq);

        // マルチペイメント請求情報の検索
        return mulPayBillDao.getLatestEntity(orderSeq);
    }

    /**
     * 受注SEQをもとにのクレジットのマルチペイメント通信用orderIdを取得します。<br/>
     *
     * @param orderSeq 受注SEQ
     * @return クレジットのマルチペイメント請求用orderId
     */
    @Override
    public String getLatestCreditOrderId(Integer orderSeq) {
        // 異常発生時のマルチペイメント請求データ取得
        return mulPayBillDao.getLatestCreditOrderId(orderSeq);
    }

    /**
     * マルチペイメント請求データを取得します。<br/>
     *
     * @param orderSeq 受注SEQ
     * @return マルチペイメント請求情報
     */
    @Override
    public MulPayBillEntity getLatestEntityExceptAmazonPayErrorOccured(Integer orderSeq) {

        // 引数チェック
        ArgumentCheckUtil.assertNotNull("orderSeq", orderSeq);

        // マルチペイメント請求情報の検索
        return mulPayBillDao.getLatestEntityExceptAmazonPayErrorOccured(orderSeq);
    }

    // Paygent Customization from here
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public void registAnotherTran(MulPayBillEntity entity) {
        regist(entity);
    }

    @Override
    public void regist(MulPayBillEntity entity) {
        mulPayBillDao.insert(entity);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public void updateAnotherTran(MulPayBillEntity entity) {
        update(entity);
    }

    @Override
    public void update(MulPayBillEntity entity) {
        mulPayBillDao.update(entity);
    }
    // Paygent Customization to here
}
