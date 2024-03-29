/* システム */
/* SYS04 権限グループSEQ */
CREATE SEQUENCE AdminAuthGroupSeq INCREMENT 1 MINVALUE 1000 MAXVALUE 9999 START 1003;

/* SYS06 管理者SEQ */
CREATE SEQUENCE administratorSeq INCREMENT 1 MINVALUE 1 MAXVALUE 99999999 START 10000;

/* SYS07 配送方法SEQ */
CREATE SEQUENCE deliveryMethodSeq     INCREMENT 1 MINVALUE 1000 MAXVALUE 9999 START 1000;

/* SYS15 決済方法SEQ */
CREATE SEQUENCE settlementMethodSeq   INCREMENT 1 MINVALUE 1000 MAXVALUE 9999 START 1000;

/* SYS19 メールテンプレートSEQ */
CREATE SEQUENCE mailTemplateSeq       INCREMENT 1 MINVALUE 1000 MAXVALUE 9999 START 1000;

/* SYS21 バッチタスクSEQ */
CREATE SEQUENCE BATCHTASK_TASKID_SEQ INCREMENT 1 MINVALUE 1 MAXVALUE 99999999 START 10000000 CYCLE;

/* SYS25 カスタムアラートSEQ */
CREATE SEQUENCE customAlertSeq       INCREMENT 1 MINVALUE 1 MAXVALUE 99999999 START 10000000;

/* SYS26 サイトマップSEQ */
CREATE SEQUENCE siteMapSeq INCREMENT 1 MINVALUE 1 MAXVALUE 99999999 START 10000000 CACHE 1 CYCLE;

/* 店舗 */
/* SHO01 アイコンSEQ */
CREATE SEQUENCE iconSeq INCREMENT 1 MINVALUE 1 MAXVALUE 99999999 START 10000000;

/* SHO02 問い合わせ分類SEQ */
CREATE SEQUENCE inquiryGroupSeq       INCREMENT 1 MINVALUE 1000 MAXVALUE 9999 START 1000;

/* SHO03 問い合わせSEQ */
CREATE SEQUENCE inquirySeq INCREMENT 1 MINVALUE 1 MAXVALUE 99999999 START 10000000;

/* SHO04 フリーエリアFEQ */
CREATE SEQUENCE freeAreaSeq INCREMENT 1 MINVALUE 1 MAXVALUE 99999999 START 10000000;

/* SHO05 ニュースSEQ */
CREATE SEQUENCE newsSeq INCREMENT 1 MINVALUE 1 MAXVALUE 99999999 START 10000000;

/* SHO07 07クーポンSEQ */
CREATE SEQUENCE COUPONSEQ INCREMENT 1 MINVALUE 1 MAXVALUE 99999999 START 10000000;

/* SHO08 ポイントSEQ */
CREATE SEQUENCE POINTSEQ INCREMENT 1 MINVALUE 1 MAXVALUE 99999999 START 10000000;

/* SHO09 アンケートSEQ */
CREATE SEQUENCE QUESTIONNAIRESEQ INCREMENT 1 MINVALUE 1 MAXVALUE 99999999 START 10000000;

/* SHO10 アンケート設問SEQ */
CREATE SEQUENCE QUESTIONSEQ INCREMENT 1 MINVALUE 1 MAXVALUE 99999999 START 10000000;

/* SHO11 アンケート回答SEQ */
CREATE SEQUENCE QUESTIONNAIREREPLYSEQ INCREMENT 1 MINVALUE 1 MAXVALUE 99999999 START 10000000;

/* キャンペーン */
/* CAM01 キャンペーンSEQ */
CREATE SEQUENCE campaignSeq INCREMENT 1 MINVALUE 1 MAXVALUE 99999999 START 10000000;


/* 商品 */
/* GOO01 カテゴリSEQ */
CREATE SEQUENCE categorySeq INCREMENT 1 MINVALUE 1 MAXVALUE 99999999 START 10000000;

/* GOO03 商品グループSEQ */
CREATE SEQUENCE goodsGroupSeq INCREMENT 1 MINVALUE 1 MAXVALUE 99999999 START 10000000;

/* GOO16 商品SEQ */
CREATE SEQUENCE goodsSeq INCREMENT 1 MINVALUE 1 MAXVALUE 99999999 START 10000000;

/* GOO19 入庫実績SEQ */
CREATE SEQUENCE stockResultSeq INCREMENT 1 MINVALUE 1 MAXVALUE 99999999 START 10000000;

/* GOO21 セールSEQ */
CREATE SEQUENCE saleSeq INCREMENT 1 MINVALUE 1 MAXVALUE 99999999 START 10000000;

/* GOO24 ユーザーレビューSEQ */
CREATE SEQUENCE USERREVIEWSEQ INCREMENT 1 MINVALUE 1000 MAXVALUE 99999999 START 10000000;

/* GOO27 ユーザーレビュー通報SEQ */
CREATE SEQUENCE USERREVIEWREPORTSEQ INCREMENT 1 MINVALUE 1000 MAXVALUE 99999999 START 10000000;

/* 会員 */
/* MEM01 会員追加属性SEQ */
CREATE SEQUENCE MEMBERADDATTRSEQ INCREMENT 1 MINVALUE 1 MAXVALUE 99999999 START 10000000;

/* MEM02 会員SEQ */
CREATE SEQUENCE memberInfoSeq INCREMENT 1 MINVALUE 1 MAXVALUE 99999999 START 10000000;

/* MEM04 住所録SEQ */
CREATE SEQUENCE addressBookSeq INCREMENT 1 MINVALUE 1 MAXVALUE 99999999 START 10000000;

/* MEM07 確認メールSEQ */
CREATE SEQUENCE confirmMailSeq INCREMENT 1 MINVALUE 1 MAXVALUE 99999999 START 10000000 CACHE 1 CYCLE;


/* メール */
/* MAIL01 メールマガジンSEQ */
CREATE SEQUENCE magazineSeq INCREMENT 1 MINVALUE 1 MAXVALUE 99999999 START 10000000;


/* 受注 */
/* ORD01 カートSEQ */
CREATE SEQUENCE cartSeq INCREMENT 1 MINVALUE 1 MAXVALUE 99999999 START 10000000 CACHE 1 CYCLE;

/* ORD02 受注SEQ */
CREATE SEQUENCE orderSeq INCREMENT 1 MINVALUE 1 MAXVALUE 99999999 START 10000000;

/* ORD12 マルチペイメント請求SEQ */
CREATE SEQUENCE MULPAYBILLSEQ INCREMENT 1 MINVALUE 1 MAXVALUE 99999999 START 10000000;

/* ORD13 ﾏﾙﾁﾍﾟｲﾒﾝﾄ決済結果SEQ */
CREATE SEQUENCE MULPAYRESULTSEQ INCREMENT 1 MINVALUE 1 MAXVALUE 99999999 START 10000000;

/* ORD15 定期受注SEQ */
CREATE SEQUENCE periodicOrderSeq INCREMENT 1 MINVALUE 1 MAXVALUE 99999999 START 10000000;


/* レポート */
/* REP01 端末識別情報SEQ */
CREATE SEQUENCE accessUidSeq INCREMENT 1 MINVALUE 1 MAXVALUE 9999 START 1000 CYCLE;

/* RMS */
/* RMS14 SEQ(RMS再オーソリキュー) */
CREATE SEQUENCE RMSREAUTHORIQUEUESEQ INCREMENT 1 MINVALUE 1 MAXVALUE 99999999 START 10000000 CYCLE;

/* RMS15 SEQ(RMS在庫同期キュー) */
CREATE SEQUENCE RMSSTOCKSYNCQUEUESEQ INCREMENT 1 MINVALUE 1 MAXVALUE 99999999 START 10000000 CYCLE;

/*会員パスワード履歴*/
CREATE SEQUENCE memberinfopwhistorySeq INCREMENT 1 MINVALUE 1 MAXVALUE 99999999 START 10000000 CACHE 1;

/*管理者パスワード履歴*/
CREATE SEQUENCE administratorpwhistorySeq INCREMENT 1 MINVALUE 1 MAXVALUE 99999999 START 10000000 CACHE 1;

/*運営者確認メールSEQ*/
CREATE SEQUENCE adminconfirmmailseq INCREMENT 1 MINVALUE 1 MAXVALUE 99999999 START 10000000 CACHE 1;

/* プレビューアクセス制御SEQ */
CREATE SEQUENCE previewaccesscontrolseq INCREMENT 1 MINVALUE 1 MAXVALUE 99999999 START 10000000 CACHE 1;
