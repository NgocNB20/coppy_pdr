 --
 --マルチペイメント請求内に残っている与信枠を確保したままの可能性がある受注データを検出する。
 --チケット#2725対応
SELECT
        mulpaybill_output.orderid
        ,mulpaybill_output.orderseq
        ,mulpaybill_output.orderversionno
        ,mulpaybill_output.jobcd
        ,mulpaybill_output.acs
        ,mulpaybill_output.tran_exec
    FROM
        -- 結合してできたデータからTrantypeが'EｎｔｒｙTran'かつ与信枠解放に登録済みの受注以外のレコードのみを抽出
        (
            SELECT
                    mulpaybill.orderid
                    ,mulpaybill.orderseq
                    ,mulpaybill.trantype
                    ,mulpaybill.orderversionno
                    ,mulpaybill.paytype
                    ,mulbill_exec.tran_exec
                    ,mulbill_exec.err_exec
                    ,mulbill_exec.acs
                    ,mulbill_secure.tran_secure
                    ,mulbill_secure.err_secure
                    ,mulpaybill.jobcd
                    ,mulpaybill.registtime
                FROM
                    mulpaybill mulpaybill
                    -- マルチペイメント請求のTrantypeが'ExecTran'のレコードをマルチペイメント請求のTrantypeが'EntryTran'のレコードに結合
                    LEFT OUTER JOIN (
                        SELECT
                                mulbill_exec.orderid
                                ,mulbill_exec.orderseq
                                ,mulbill_exec.trantype AS tran_exec
                                ,mulbill_exec.errcode AS err_exec
                                ,mulbill_exec.acs
                                ,mulbill_exec.jobcd
                            FROM
                                mulpaybill mulbill_exec
                            WHERE
                                mulbill_exec.trantype = 'ExecTran'
                    ) mulbill_exec
                        ON mulpaybill.orderid = mulbill_exec.orderid
                    -- マルチペイメント請求のTrantypeが'SecureTran'のレコードをマルチペイメント請求のTrantypeが'EntryTran'のレコードに結合
                    LEFT OUTER JOIN (
                        SELECT
                                mulbill_secure.orderid
                                ,mulbill_secure.orderseq
                                ,mulbill_secure.trantype AS tran_secure
                                ,mulbill_secure.errcode AS err_secure
                                ,mulbill_secure.jobcd
                            FROM
                                mulpaybill mulbill_secure
                            WHERE
                                mulbill_secure.trantype = 'SecureTran'
                    ) mulbill_secure
                        ON mulpaybill.orderid = mulbill_secure.orderid
                WHERE
                    mulpaybill.orderid NOT IN (
                        SELECT
                                creditlinereport.orderid
                            FROM
                                creditlinereport
                    )
                    AND mulpaybill.trantype = 'EntryTran'
                    -- 対象の受注をdiconファイルで設定した日数、時間で絞り込む
                    AND mulpaybill.registtime BETWEEN /*specifiedDay*/0 AND /*thresholdTime*/0
                    AND mulpaybill.errcode IS NULL
                ORDER BY mulpaybill.orderid
        ) AS mulpaybill_output
    WHERE
        -- エラーコードがＮＵＬＬのレコードのみを抽出
        mulpaybill_output.err_exec IS NULL
        AND mulpaybill_output.err_secure IS NULL
        -- 受注サマリに存在するオーダーSeqを持つマルチペイメント請求のレコードは対象外とする
        AND mulpaybill_output.orderseq NOT IN (
            SELECT
                    orderseq
                FROM
                    ordersummary ordersummary
                WHERE
                    mulpaybill_output.orderseq = ordersummary.orderseq
        )
