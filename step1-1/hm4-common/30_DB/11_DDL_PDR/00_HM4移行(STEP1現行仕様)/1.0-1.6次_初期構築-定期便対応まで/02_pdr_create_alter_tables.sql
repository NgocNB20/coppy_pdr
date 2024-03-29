--********************************************************************************
--* テーブル名：会員 (MEMBERINFO)
--********************************************************************************
-- PDR Customization from here
ALTER TABLE MEMBERINFO
	ALTER COLUMN MEMBERINFOBIRTHDAY DROP NOT NULL,
	ALTER COLUMN MEMBERINFOPREFECTURE DROP NOT NULL,
	ADD COLUMN CUSTOMERNO NUMERIC(9,0),
	ADD COLUMN REPRESENTATIVENAME VARCHAR(40),
	ADD COLUMN MEMBERINFOADDRESS4 VARCHAR(40),
	ADD COLUMN MEMBERINFOADDRESS5 VARCHAR(40),
	ADD COLUMN BUSINESSTYPE VARCHAR(2),
	ADD COLUMN SENDFAXPERMITFLAG VARCHAR(1) NOT NULL DEFAULT 0,
	ADD COLUMN SENDDIRECTMAILFLAG VARCHAR(1) NOT NULL DEFAULT 0,
	ADD COLUMN NONCONSULTATIONDAY VARCHAR(9),
	ADD COLUMN APPROVESTATUS VARCHAR(1) NOT NULL DEFAULT 0,
	ADD COLUMN DRUGSALESTYPE VARCHAR(4),
	ADD COLUMN MEDICALEQUIPMENTSALESTYPE VARCHAR(4),
	ADD COLUMN DENTALMONOPOLYSALESTYPE VARCHAR(4),
	ADD COLUMN CREDITPAYMENTUSEFLAG VARCHAR(1) NOT NULL DEFAULT 0,
	ADD COLUMN TRANSFERPAYMENTUSEFLAG VARCHAR(1) NOT NULL DEFAULT 0,
	ADD COLUMN CASHDELIVERYUSEFLAG VARCHAR(1) NOT NULL DEFAULT 0,
	ADD COLUMN DIRECTDEBITUSEFLAG VARCHAR(1) NOT NULL DEFAULT 0,
	ADD COLUMN MONTHLYPAYUSEFLAG VARCHAR(1) NOT NULL DEFAULT 0,
	ADD COLUMN MEMBERLISTTYPE VARCHAR(4),
	ADD COLUMN ONLINEREGISTFLAG VARCHAR(1) NOT NULL DEFAULT 0;

COMMENT ON COLUMN MEMBERINFO.MEMBERINFOLASTNAME IS '事業所名'
;
COMMENT ON COLUMN MEMBERINFO.MEMBERINFOLASTKANA IS '事業所名（フリガナ）'
;
COMMENT ON COLUMN MEMBERINFO.CUSTOMERNO IS '顧客番号'
;
COMMENT ON COLUMN MEMBERINFO.REPRESENTATIVENAME IS '代表者名'
;
COMMENT ON COLUMN MEMBERINFO.MEMBERINFOADDRESS4 IS '会員住所-方書1'
;
COMMENT ON COLUMN MEMBERINFO.MEMBERINFOADDRESS5 IS '会員住所-方書2'
;
COMMENT ON COLUMN MEMBERINFO.BUSINESSTYPE IS '顧客区分'
;
COMMENT ON COLUMN MEMBERINFO.SENDFAXPERMITFLAG IS 'FAXによるおトク情報'
;
COMMENT ON COLUMN MEMBERINFO.SENDDIRECTMAILFLAG IS 'DMによるおトク情報'
;
COMMENT ON COLUMN MEMBERINFO.NONCONSULTATIONDAY IS '休診曜日'
;
COMMENT ON COLUMN MEMBERINFO.APPROVESTATUS IS '承認状態'
;
COMMENT ON COLUMN MEMBERINFO.DRUGSALESTYPE IS '医薬品・注射針販売区分'
;
COMMENT ON COLUMN MEMBERINFO.MEDICALEQUIPMENTSALESTYPE IS '医療機器販売区分'
;
COMMENT ON COLUMN MEMBERINFO.DENTALMONOPOLYSALESTYPE IS '歯科専売品販売区分'
;
COMMENT ON COLUMN MEMBERINFO.CREDITPAYMENTUSEFLAG IS 'クレジット決済使用可否'
;
COMMENT ON COLUMN MEMBERINFO.TRANSFERPAYMENTUSEFLAG IS 'コンビニ・郵便振込使用可否'
;
COMMENT ON COLUMN MEMBERINFO.CASHDELIVERYUSEFLAG IS '代金引換使用可否'
;
COMMENT ON COLUMN MEMBERINFO.DIRECTDEBITUSEFLAG IS '口座自動引落使用可否'
;
COMMENT ON COLUMN MEMBERINFO.MONTHLYPAYUSEFLAG IS '月締請求使用可否'
;
COMMENT ON COLUMN MEMBERINFO.MEMBERLISTTYPE IS '名簿区分'
;
COMMENT ON COLUMN MEMBERINFO.ONLINEREGISTFLAG IS 'オンライン登録フラグ'
;
-- PDR Customization to here


-- PDR Customization from here
--********************************************************************************
--* テーブル名：商品 (GOODS)
--********************************************************************************
ALTER TABLE GOODS
	ALTER COLUMN GOODSPRICE TYPE NUMERIC(10,0),
	ALTER COLUMN PREDISCOUNTPRICE TYPE NUMERIC(10,0),
	ADD COLUMN RESERVEFLAG VARCHAR(1) NOT NULL DEFAULT 0,
	ADD COLUMN LANDSENDFLAG VARCHAR(1) NOT NULL DEFAULT 0,
	ADD COLUMN COOLSENDFLAG VARCHAR(1) NOT NULL DEFAULT 0,
	ADD COLUMN COOLSENDFROM VARCHAR(5),
	ADD COLUMN COOLSENDTO VARCHAR(5),
	ADD COLUMN UNIT VARCHAR(4),
	ADD COLUMN PRICEMARKDISPFLAG VARCHAR(1) NOT NULL DEFAULT 0,
	ADD COLUMN SALEPRICEMARKDISPFLAG VARCHAR(1) NOT NULL DEFAULT 0,
	ADD COLUMN GOODSMANAGEMENTCODE VARCHAR(16),
	ADD COLUMN GOODSDIVISIONCODE NUMERIC(4),
	ADD COLUMN GOODSCATEGORY1 NUMERIC(4),
	ADD COLUMN GOODSCATEGORY2 NUMERIC(4),
	ADD COLUMN GOODSCATEGORY3 NUMERIC(4),
	ADD COLUMN SALEPRICEINTEGRITYFLAG VARCHAR(1) NOT NULL DEFAULT 0,
    ADD COLUMN subscriptionTargetType VARCHAR(1) DEFAULT 2 NOT NULL,
    ADD COLUMN subscriptionPermissionType VARCHAR(1) DEFAULT 2 NOT NULL,
    ADD COLUMN subscriptionRecommendedFlag VARCHAR(1) DEFAULT 0 NOT NULL;

COMMENT ON COLUMN GOODS.GOODSPRICE IS '価格'
;
COMMENT ON COLUMN GOODS.PREDISCOUNTPRICE IS 'セール価格'
;
COMMENT ON COLUMN GOODS.RESERVEFLAG IS '保留フラグ'
;
COMMENT ON COLUMN GOODS.LANDSENDFLAG IS '陸送商品フラグ'
;
COMMENT ON COLUMN GOODS.COOLSENDFLAG IS 'クール便フラグ'
;
COMMENT ON COLUMN GOODS.COOLSENDFROM IS 'クール便適用期間Ｆｒｏｍ'
;
COMMENT ON COLUMN GOODS.COOLSENDTO IS 'クール便適用期間Ｔｏ'
;
COMMENT ON COLUMN GOODS.UNIT IS '単位'
;
COMMENT ON COLUMN GOODS.PRICEMARKDISPFLAG IS '価格記号表示フラグ'
;
COMMENT ON COLUMN GOODS.SALEPRICEMARKDISPFLAG IS 'セール価格記号表示フラグ'
;
COMMENT ON COLUMN GOODS.GOODSMANAGEMENTCODE IS '管理商品コード'
;
COMMENT ON COLUMN GOODS.GOODSDIVISIONCODE IS '商品分類コード'
;
COMMENT ON COLUMN GOODS.GOODSCATEGORY1 IS 'カテゴリー1'
;
COMMENT ON COLUMN GOODS.GOODSCATEGORY2 IS 'カテゴリー2'
;
COMMENT ON COLUMN GOODS.GOODSCATEGORY3 IS 'カテゴリー3'
;
COMMENT ON COLUMN GOODS.SALEPRICEINTEGRITYFLAG IS 'セール価格整合性フラグ'
;
COMMENT ON COLUMN GOODS.subscriptionTargetType IS '定期便対象区分'
;
COMMENT ON COLUMN GOODS.subscriptionPermissionType IS '定期便受付許可区分'
;
COMMENT ON COLUMN GOODS.subscriptionRecommendedFlag IS '定期便おすすめフラグ'
;

--********************************************************************************
--* テーブル名：商品グループ表示 (GOODSGROUPDISPLAY)
--********************************************************************************
ALTER TABLE GOODSGROUPDISPLAY
	ADD COLUMN SALEICONFLAG VARCHAR(1) NOT NULL DEFAULT 0,
	ADD COLUMN RESERVEICONFLAG VARCHAR(1) NOT NULL DEFAULT 0,
	ADD COLUMN NEWICONFLAG VARCHAR(1) NOT NULL DEFAULT 0;

COMMENT ON COLUMN GOODSGROUPDISPLAY.SALEICONFLAG IS 'SALEアイコンフラグ'
;
COMMENT ON COLUMN GOODSGROUPDISPLAY.RESERVEICONFLAG IS 'お取りおきアイコンフラグ'
;
COMMENT ON COLUMN GOODSGROUPDISPLAY.NEWICONFLAG IS 'NEWアイコンフラグ'
;

--********************************************************************************
--* テーブル名：商品グループ (GOODSGROUP)
--********************************************************************************
ALTER TABLE GOODSGROUP
	ADD COLUMN GOODSCLASSTYPE VARCHAR(4),
	ADD COLUMN CATALOGDISPLAYORDER NUMERIC(4),
	ADD COLUMN GROUPPRICE NUMERIC(10) NOT NULL DEFAULT 0,
	ADD COLUMN GROUPSALEPRICE NUMERIC(10),
	ADD COLUMN GROUPPRICEMARKDISPFLAG VARCHAR(1) NOT NULL DEFAULT 0,
	ADD COLUMN GROUPSALEPRICEMARKDISPFLAG VARCHAR(1) NOT NULL DEFAULT 0,
	ADD COLUMN GROUPSALEPRICEINTEGRITYFLAG VARCHAR(1) NOT NULL DEFAULT 0;

COMMENT ON COLUMN GOODSGROUP.GOODSPREDISCOUNTPRICE IS 'シリーズセールコメント'
;
COMMENT ON COLUMN GOODSGROUP.GOODSCLASSTYPE IS '商品区分'
;
COMMENT ON COLUMN GOODSGROUP.CATALOGDISPLAYORDER IS 'カタログ表示順'
;
COMMENT ON COLUMN GOODSGROUP.GROUPPRICE IS 'シリーズ価格'
;
COMMENT ON COLUMN GOODSGROUP.GROUPSALEPRICE IS 'シリーズセール価格'
;
COMMENT ON COLUMN GOODSGROUP.GROUPPRICEMARKDISPFLAG IS 'シリーズ価格記号表示フラグ'
;
COMMENT ON COLUMN GOODSGROUP.GROUPSALEPRICEMARKDISPFLAG IS 'シリーズセール価格記号表示フラグ'
;
COMMENT ON COLUMN GOODSGROUP.GROUPSALEPRICEINTEGRITYFLAG IS 'シリーズセール価格整合性フラグ'
;

--********************************************************************************
--* テーブル名：住所録 (ADDRESSBOOK)
--********************************************************************************
ALTER TABLE ADDRESSBOOK
	ALTER COLUMN ADDRESSBOOKPREFECTURE DROP NOT NULL,
	ADD COLUMN ADDRESSBOOKADDRESS4 VARCHAR(15),
	ADD COLUMN ADDRESSBOOKADDRESS5 VARCHAR(15),
	ADD COLUMN CUSTOMERNO NUMERIC(9),
	ADD COLUMN ADDRESSBOOKAPPROVEFLAG VARCHAR(1) NOT NULL DEFAULT 0;

COMMENT ON COLUMN ADDRESSBOOK.ADDRESSBOOKLASTNAME IS '事業所名'
;
COMMENT ON COLUMN ADDRESSBOOK.ADDRESSBOOKFIRSTNAME IS '代表者名'
;
COMMENT ON COLUMN ADDRESSBOOK.ADDRESSBOOKLASTKANA IS '事業所名(フリガナ)'
;
COMMENT ON COLUMN ADDRESSBOOK.ADDRESSBOOKADDRESS4 IS '住所録住所-方書1'
;
COMMENT ON COLUMN ADDRESSBOOK.ADDRESSBOOKADDRESS5 IS '住所録住所-方書2'
;
COMMENT ON COLUMN ADDRESSBOOK.CUSTOMERNO IS '顧客番号'
;
COMMENT ON COLUMN ADDRESSBOOK.ADDRESSBOOKAPPROVEFLAG IS '住所録承認フラグ'
;

--********************************************************************************
--* テーブル名：問い合わせ (INQUIRY)
--********************************************************************************
ALTER TABLE INQUIRY
	ADD COLUMN INQUIRYCUSTOMERNO NUMERIC(9,0);

COMMENT ON COLUMN INQUIRY.INQUIRYLASTNAME IS '問い合わせ事業所名'
;
COMMENT ON COLUMN INQUIRY.INQUIRYFIRSTNAME IS '問い合わせご担当者名'
;
COMMENT ON COLUMN INQUIRY.INQUIRYCUSTOMERNO IS '問い合わせお客様番号'
;

--********************************************************************************
--* テーブル名：受注ご注文主 (ORDERPERSON)
--********************************************************************************
ALTER TABLE ORDERPERSON
	ALTER COLUMN ORDERPREFECTURE DROP NOT NULL;


COMMENT ON COLUMN ORDERPERSON.ORDERLASTNAME IS '事業所名'
;
COMMENT ON COLUMN ORDERPERSON.ORDERLASTKANA IS '事業所名(フリガナ)'
;

--********************************************************************************
--* テーブル名：受注配送 (ORDERDELIVERY)
--********************************************************************************
ALTER TABLE ORDERDELIVERY
	ALTER COLUMN RECEIVERPREFECTURE DROP NOT NULL;


COMMENT ON COLUMN ORDERDELIVERY.RECEIVERLASTNAME IS '事業所名'
;
COMMENT ON COLUMN ORDERDELIVERY.RECEIVERLASTKANA IS '代表者名'
;
COMMENT ON COLUMN ORDERDELIVERY.RECEIVERFIRSTNAME IS '事業所名(フリガナ)'
;

--********************************************************************************
--* テーブル名：基幹連携日時履歴 (COOPDATEHISTORY)
--********************************************************************************
CREATE TABLE COOPDATEHISTORY
(
    COOPID           VARCHAR(6) NOT NULL,
    LASTCOOPDATE     TIMESTAMP(0) NOT NULL,
    REGISTTIME       TIMESTAMP(0) NOT NULL,
    UPDATETIME       TIMESTAMP(0) NOT NULL
)
;
ALTER TABLE COOPDATEHISTORY
    ADD CONSTRAINT COOPDATEHISTORY_PKEY PRIMARY KEY (COOPID)
;
COMMENT ON TABLE COOPDATEHISTORY IS '基幹連携日時履歴'
;
COMMENT ON COLUMN COOPDATEHISTORY.COOPID IS '基幹連携ID'
;
COMMENT ON COLUMN COOPDATEHISTORY.LASTCOOPDATE IS '前回連携日時'
;
COMMENT ON COLUMN COOPDATEHISTORY.REGISTTIME IS '登録日時'
;
COMMENT ON COLUMN COOPDATEHISTORY.UPDATETIME IS '更新日時'
;
-- PDR Customization to here
