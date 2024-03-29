/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */
package jp.co.hankyuhanshin.itec.hitmall.dto.goods.category;

import jp.co.hankyuhanshin.itec.hmbase.util.csvupload.CsvUploadError;
import jp.co.hankyuhanshin.itec.hmbase.util.csvupload.CsvUploadResult;

import java.util.ArrayList;
import java.util.List;

/**
 * カテゴリーCSVアップロード時用アップロード結果クラス。<br />
 * CsvUploadResult に非エラー系通知情報を格納できるようにサブクラスを作成。
 *
 * @author Tomo (Itec) 2011/12/26 チケット #2714 対応
 */
// 2023-renew categoryCSV from here
public class CategoryCsvUploadResult extends CsvUploadResult {

    /** シリアル */
    private static final long serialVersionUID = 3517349931276356489L;

    /** 非エラー系メッセージ */
    protected List<CsvUploadError> infoMessageList;

    /** トップカテゴリーの更新不可を指摘済みかどうか */
    protected boolean topCategoryUpdateAlerted;

    /**
     * コンストラクタ
     */
    public CategoryCsvUploadResult() {
        this.infoMessageList = new ArrayList<>();
    }

    /**
     * INFOとして通知するメッセージを追加する
     * @param row   通知対象行番号
     * @param msgId 通知メッセージID
     * @param args  通知メッセージ引数
     */
    public void addMessage(int row, String msgId, Object... args) {
        CsvUploadError message = new CsvUploadError();
        message.setMessageCode(msgId);
        message.setArgs(args);
        message.setRow(row);
        this.infoMessageList.add(message);
    }

    /**
     * 通知メッセージリストを返す
     * @return 通知メッセージリスト
     */
    public List<CsvUploadError> getInfoMessageList() {
        return infoMessageList;
    }

    /**
     * 通知メッセージリストを設定する
     * @param infoMessageList 通知メッセージリスト
     */
    public void setInfoMessageList(List<CsvUploadError> infoMessageList) {
        this.infoMessageList = infoMessageList;
    }

    /**
     * 「トップカテゴリー更新」の通知済みかどうか
     * @return  「トップカテゴリー更新」の通知済みフラグ
     */
    public boolean isTopCategoryUpdateAlerted() {
        return topCategoryUpdateAlerted;
    }

    /**
     * 「トップカテゴリー更新」の通知済みを設定する
     * @param topCategoryUpdateAlerted  「トップカテゴリー更新」の通知済みフラグ
     */
    public void setTopCategoryUpdateAlerted(boolean topCategoryUpdateAlerted) {
        this.topCategoryUpdateAlerted = topCategoryUpdateAlerted;
    }
}
// 2023-renew categoryCSV to here
