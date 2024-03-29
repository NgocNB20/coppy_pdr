-- *****************************
-- ** テーブルデータ移行用SQL **
-- *****************************

-- 『ショップ』
--   shop
INSERT INTO public.shop
(
  shopseq
  , shopid
  , shopnamepc
  , shopnamemb
  , urlpc
  , urlmb
  , shopopenstatuspc
  , shopopenstatusmb
  , shopopenstarttimepc
  , shopopenendtimepc
  , shopopenstarttimemb
  , shopopenendtimemb
  , metadescription
  , metakeyword
  , pointaddbasepricedefault
  , pointaddratedefault
  , pointstarttime1
  , pointendtime1
  , pointaddbaseprice1
  , pointaddrate1
  , pointstarttime2
  , pointendtime2
  , pointaddbaseprice2
  , pointaddrate2
  , memberranksetdate
  , memberrankpurchasestarttime
  , versionno
  , registtime
  , updatetime
  , autoapprovalflag
  , reviewdefaultpoint
)
SELECT
  shopseq
  , shopid
  , shopnamepc
  , shopnamemb
  , urlpc
  , urlmb
  , shopopenstatuspc
  , shopopenstatusmb
  , shopopenstarttimepc
  , shopopenendtimepc
  , shopopenstarttimemb
  , shopopenendtimemb
  , metadescription
  , metakeyword
  , pointaddbasepricedefault
  , pointaddratedefault
  , pointstarttime1
  , pointendtime1
  , pointaddbaseprice1
  , pointaddrate1
  , pointstarttime2
  , pointendtime2
  , pointaddbaseprice2
  , pointaddrate2
  , memberranksetdate
  , memberrankpurchasestarttime
  , versionno
  , registtime
  , updatetime
  , '0'
  , null
FROM
    hm3.shop
;


-- 『郵便番号住所』
--   zipcode
INSERT INTO public.zipcode
(
  localcode
  , oldzipcode
  , zipcode
  , prefecturekana
  , citykana
  , townkana
  , prefecturename
  , cityname
  , townname
  , anyzipflag
  , numberflag1
  , numberflag2
  , numberflag3
  , updatevisibletype
  , updatenotetype
  , registtime
  , updatetime
)
SELECT
  localcode
  , oldzipcode
  , zipcode
  , prefecturekana
  , citykana
  , townkana
  , prefecturename
  , cityname
  , townname
  , anyzipflag
  , numberflag1
  , numberflag2
  , numberflag3
  , updatevisibletype
  , updatenotetype
  , registtime
  , updatetime
FROM
    hm3.zipcode
;


-- 『事業所郵便番号』
--   officezipcode
INSERT INTO public.officezipcode
(
  officecode
  , officekana
  , officename
  , prefecturename
  , cityname
  , townname
  , numbers
  , zipcode
  , oldzipcode
  , handlingbranchname
  , individualtype
  , anyzipflag
  , updatecode
  , registtime
  , updatetime
)
SELECT
  officecode
  , officekana
  , officename
  , prefecturename
  , cityname
  , townname
  , numbers
  , zipcode
  , oldzipcode
  , handlingbranchname
  , individualtype
  , anyzipflag
  , updatecode
  , registtime
  , updatetime
FROM
    hm3.officezipcode
;


-- 『運営者権限グループ』
--   adminauthgroup
INSERT INTO public.adminauthgroup
(
  adminauthgroupseq
  , shopseq
  , authgroupdisplayname
  , registtime
  , updatetime
)
SELECT
  adminauthgroupseq
  , shopseq
  , authgroupdisplayname
  , registtime
  , updatetime
FROM
    hm3.adminauthgroup
;


-- 『運営者権限グループ詳細』
--   adminauthgroupdetail
INSERT INTO public.adminauthgroupdetail
(
  adminauthgroupseq
  , authtypecode
  , authlevel
  , registtime
  , updatetime
)
SELECT
  adminauthgroupseq
  , authtypecode
  , authlevel
  , registtime
  , updatetime
FROM
    hm3.adminauthgroupdetail
;


-- 『管理者』
--   administrator
INSERT INTO public.administrator
(
  administratorseq
  , shopseq
  , administratorstatus
  , administratorid
  , administratorpassword
  , mail
  , administratorlastname
  , administratorfirstname
  , administratorlastkana
  , administratorfirstkana
  , usestartdate
  , useenddate
  , adminauthgroupseq
  , registtime
  , updatetime
  , passwordchangetime
  , passwordexpirydate
  , loginfailurecount
  , accountlocktime
  , passwordneedchangeflag
  , passwordsha256encryptedflag
)
SELECT
  administratorseq
  , shopseq
  , administratorstatus
  , administratorid
  , administratorpassword
  , mail
  , administratorlastname
  , administratorfirstname
  , administratorlastkana
  , administratorfirstkana
  , usestartdate
  , useenddate
  , adminauthgroupseq
  , registtime
  , updatetime
  , CURRENT_TIMESTAMP
  , CURRENT_TIMESTAMP + '90 day'
  , 0
  , null
  , '0'
  , '1'
FROM
    hm3.administrator
;


-- 『配送方法』
--   deliverymethod
INSERT INTO public.deliverymethod
(
  deliverymethodseq
  , shopseq
  , deliverymethodname
  , deliverymethoddisplaynamepc
  , deliverymethoddisplaynamemb
  , deliverychaseurl
  , deliverychaseurldisplayperiod
  , openstatuspc
  , openstatusmb
  , deliverynotepc
  , deliverynotemb
  , deliverymethodtype
  , equalscarriage
  , largeamountdiscountprice
  , largeamountdiscountcarriage
  , shortfalldisplayflag
  , deliveryleadtime
  , possibleselectdays
  , receivertimezone1
  , receivertimezone2
  , receivertimezone3
  , receivertimezone4
  , receivertimezone5
  , receivertimezone6
  , receivertimezone7
  , receivertimezone8
  , receivertimezone9
  , receivertimezone10
  , orderdisplay
  , registtime
  , updatetime
)
SELECT
  deliverymethodseq
  , shopseq
  , deliverymethodname
  , deliverymethoddisplaynamepc
  , deliverymethoddisplaynamemb
  , null
  , null
  , openstatuspc
  , openstatusmb
  , deliverynotepc
  , deliverynotemb
  , deliverymethodtype
  , equalscarriage
  , largeamountdiscountprice
  , largeamountdiscountcarriage
  , shortfalldisplayflag
  , deliveryleadtime
  , possibleselectdays
  , receivertimezone1
  , receivertimezone2
  , receivertimezone3
  , receivertimezone4
  , receivertimezone5
  , receivertimezone6
  , receivertimezone7
  , receivertimezone8
  , receivertimezone9
  , receivertimezone10
  , orderdisplay
  , registtime
  , updatetime
FROM
    hm3.deliverymethod
;


-- 『配送区分別送料』
--   deliverymethodtypecarriage
INSERT INTO public.deliverymethodtypecarriage
(
  deliverymethodseq
  , prefecturetype
  , maxprice
  , carriage
  , registtime
  , updatetime
)
SELECT
  deliverymethodseq
  , prefecturetype
  , maxprice
  , carriage
  , registtime
  , updatetime
FROM
    hm3.deliverymethodtypecarriage
;


-- 『配送特別料金エリア』
--   deliveryspecialchargearea
INSERT INTO public.deliveryspecialchargearea
(
  deliverymethodseq
  , zipcode
  , carriage
  , registtime
  , updatetime
)
SELECT
  deliverymethodseq
  , zipcode
  , carriage
  , registtime
  , updatetime
FROM
    hm3.deliveryspecialchargearea
;


-- 『配送不可能エリア』
--   deliveryimpossiblearea
INSERT INTO public.deliveryimpossiblearea
(
  deliverymethodseq
  , zipcode
  , registtime
  , updatetime
)
SELECT
  deliverymethodseq
  , zipcode
  , registtime
  , updatetime
FROM
    hm3.deliveryimpossiblearea
;


-- 『マルチペイメントサイト設定』
--   mulpaysite
INSERT INTO public.mulpaysite
(
  siteid
  , sitepassword
  , siteaccessurl
)
SELECT
  siteid
  , sitepassword
  , siteaccessurl
FROM
    hm3.mulpaysite
;


-- 『マルチペイメントショップ設定』
--   mulpayshop
INSERT INTO public.mulpayshop
(
  shopseq
  , shopid
  , shoppass
  , shopaccessurl
  , tdtenantname
  , httpaccept
  , httpuseragent
  , clientfield1
  , clientfield2
  , clientfield3
  , clientfieldflag
  , shopmailaddress
  , registerdisp1
  , registerdisp2
  , registerdisp3
  , registerdisp4
  , registerdisp5
  , registerdisp6
  , registerdisp7
  , registerdisp8
  , receiptsdisp1
  , receiptsdisp2
  , receiptsdisp3
  , receiptsdisp4
  , receiptsdisp5
  , receiptsdisp6
  , receiptsdisp7
  , receiptsdisp8
  , receiptsdisp9
  , receiptsdisp10
  , receiptsdisp11
  , receiptsdisp12
  , receiptsdisp13
  , suicaaddinfo1
  , suicaaddinfo2
  , suicaaddinfo3
  , suicaaddinfo4
  , edyaddinfo1
  , edyaddinfo2
  , itemname
)
SELECT
  shopseq
  , shopid
  , shoppass
  , shopaccessurl
  , tdtenantname
  , httpaccept
  , httpuseragent
  , clientfield1
  , clientfield2
  , clientfield3
  , clientfieldflag
  , shopmailaddress
  , registerdisp1
  , registerdisp2
  , registerdisp3
  , registerdisp4
  , registerdisp5
  , registerdisp6
  , registerdisp7
  , registerdisp8
  , receiptsdisp1
  , receiptsdisp2
  , receiptsdisp3
  , receiptsdisp4
  , receiptsdisp5
  , receiptsdisp6
  , receiptsdisp7
  , receiptsdisp8
  , receiptsdisp9
  , receiptsdisp10
  , receiptsdisp11
  , receiptsdisp12
  , receiptsdisp13
  , suicaaddinfo1
  , suicaaddinfo2
  , suicaaddinfo3
  , suicaaddinfo4
  , edyaddinfo1
  , edyaddinfo2
  , itemname
FROM
    hm3.mulpayshop
;


-- 『カードブランド』
--   cardbrand
INSERT INTO public.cardbrand
(
  cardbrandseq
  , cardbrandcode
  , cardbrandname
  , cardbranddisplaypc
  , cardbranddisplaymb
  , orderdisplay
  , installment
  , bounussingle
  , bounusinstallment
  , revolving
  , installmentcounts
  , frontdisplayflag
)
SELECT
  cardbrandseq
  , cardbrandcode
  , cardbrandname
  , cardbranddisplaypc
  , cardbranddisplaymb
  , orderdisplay
  , installment
  , bounussingle
  , bounusinstallment
  , revolving
  , installmentcounts
  , frontdisplayflag
FROM
    hm3.cardbrand
;


-- 『コンビニ名称』
--   convenience
INSERT INTO public.convenience
(
  conveniseq
  , paycode
  , convenicode
  , payname
  , conveniname
  , shopseq
  , useflag
  , displayorder
  , registtime
  , updatetime
)
SELECT
  conveniseq
  , paycode
  , convenicode
  , payname
  , conveniname
  , shopseq
  , '1'
  , ROW_NUMBER() OVER (ORDER BY conveniseq ASC) AS displayorder
  , registtime
  , updatetime
FROM
    hm3.convenience
;


-- 『決済方法』
--   settlementmethod
INSERT INTO public.settlementmethod
(
  settlementmethodseq
  , shopseq
  , settlementmethodname
  , settlementmethoddisplaynamepc
  , settlementmethoddisplaynamemb
  , openstatuspc
  , openstatusmb
  , settlementnotepc
  , settlementnotemb
  , settlementmethodtype
  , settlementmethodcommissiontype
  , billtype
  , deliverymethodseq
  , equalscommission
  , settlementmethodpricecommissionflag
  , largeamountdiscountprice
  , largeamountdiscountcommission
  , orderdisplay
  , maxpurchasedprice
  , paymenttimelimitdaycount
  , minpurchasedprice
  , canceltimelimitdaycount
  , settlementmailrequired
  , enablecardnoholding
  , enablesecuritycode
  , enable3dsecure
  , enableinstallment
  , enablebonussinglepayment
  , enablebonusinstallment
  , enablerevolving
  , registtime
  , updatetime
)
SELECT
  settlementmethodseq
  , shopseq
  , settlementmethoddisplaynamepc -- PDRでは決済方法表示名PCだけあればOKなので上書き
  , settlementmethoddisplaynamepc
  , settlementmethoddisplaynamemb
  , openstatuspc
  , openstatusmb
  , REPLACE(settlementnotepc, '/ec/shop/', '/')
  , REPLACE(settlementnotemb, '/ec/shop/', '/')
  , settlementmethodtype
  , settlementmethodcommissiontype
  , billtype
  , deliverymethodseq
  , equalscommission
  , settlementmethodpricecommissionflag
  , largeamountdiscountprice
  , largeamountdiscountcommission
  , orderdisplay
  , maxpurchasedprice
  , paymenttimelimitdaycount
  , minpurchasedprice
  , canceltimelimitdaycount
  , settlementmailrequired
  , enablecardnoholding
  , enablesecuritycode
  , enable3dsecure
  , enableinstallment
  , enablebonussinglepayment
  , enablebonusinstallment
  , enablerevolving
  , registtime
  , updatetime
FROM
    hm3.settlementmethod
;


-- 『決済方法金額別手数料』
--   settlementmethodpricecommission
INSERT INTO public.settlementmethodpricecommission
(
  settlementmethodseq
  , maxprice
  , commission
  , registtime
  , updatetime
)
SELECT
  settlementmethodseq
  , maxprice
  , commission
  , registtime
  , updatetime
FROM
    hm3.settlementmethodpricecommission
;


-- 『熨斗グループ』
--   giftwrappinggroup
INSERT INTO public.giftwrappinggroup
(
  giftwrappinggroupseq
  , giftwrappinggroupname
  , orderdisplay
  , shopseq
  , versionno
  , registtime
  , updatetime
)
SELECT
  giftwrappinggroupseq
  , giftwrappinggroupname
  , orderdisplay
  , shopseq
  , versionno
  , registtime
  , updatetime
FROM
    hm3.giftwrappinggroup
;


-- 『熨斗』
--   giftwrapping
INSERT INTO public.giftwrapping
(
  giftwrappingseq
  , giftwrappingname
  , giftwrappingnote
  , giftwrappingimage
  , giftwrappinggroupseq
  , nameprintflag
  , orderdisplay
  , giftwrappingopenstatus
  , giftwrappingopenstarttime
  , giftwrappingopenendtime
  , versionno
  , registtime
  , updatetime
)
SELECT
  giftwrappingseq
  , giftwrappingname
  , giftwrappingnote
  , giftwrappingimage
  , giftwrappinggroupseq
  , nameprintflag
  , orderdisplay
  , giftwrappingopenstatus
  , giftwrappingopenstarttime
  , giftwrappingopenendtime
  , versionno
  , registtime
  , updatetime
FROM
    hm3.giftwrapping
;


-- 『メールテンプレート』
--   mailtemplate
INSERT INTO public.mailtemplate
(
  mailtemplateseq
  , shopseq
  , mailtemplatename
  , mailtemplatetype
  , mailtemplatedefaultflag
  , mailtemplatesubject
  , mailtemplatefromaddress
  , mailtemplatetoaddress
  , mailtemplateccaddress
  , mailtemplatebccaddress
  , registtime
  , updatetime
)
SELECT
  mailtemplateseq
  , shopseq
  , mailtemplatename
  , mailtemplatetype
  , mailtemplatedefaultflag
  , mailtemplatesubjectpc
  , mailtemplatefromaddress
  , mailtemplatetoaddress
  , mailtemplateccaddress
  , mailtemplatebccaddress
  , registtime
  , updatetime
FROM
    hm3.mailtemplate
WHERE
    mailtemplatetype IN ('04','05','06','07','31','32','33','98')
;


/* 最終決定：不使用テーブルのため移行対象外とします
-- 『休日』
--   holiday
INSERT INTO public.holiday
(
  deliverymethodseq
  , date
  , year
  , name
  , registtime
)
SELECT
  1001
  , date
  , year
  , name
  , registtime
FROM
    hm3.holiday
;
*/

-- 『バッチ管理』
--   batchtask
INSERT INTO public.batchtask
(
  taskid
  , batchderive
  , batchtype
  , batchname
  , targethost
  , targetfile
  , extraargs
  , shopseq
  , taskowner
  , visiblegroup
  , interruptiblegroup
  , accepttime
  , batchhost
  , taskstatus
  , taskprogress
  , totalrecord
  , processedrecord
  , quitflag
  , quituser
  , terminatetime
  , reportstring
  , expirytime
)
SELECT
  taskid
  , batchderive
  , batchtype
  , batchname
  , targethost
  , targetfile
  , extraargs
  , shopseq
  , taskowner
  , visiblegroup
  , interruptiblegroup
  , accepttime
  , batchhost
  , taskstatus
  , taskprogress
  , totalrecord
  , processedrecord
  , quitflag
  , quituser
  , terminatetime
  , reportstring
  , expirytime
FROM
    hm3.batchtask
;


-- 『目標管理』
--   targetmanagement
INSERT INTO public.targetmanagement
(
  shopseq
  , targetmanagementdate
  , targettoppv
  , targetvisitcount
  , targetordercount
  , targetorderprice
  , targetmembercount
  , resulttoppv
  , resultvisitcount
  , resultordercount
  , resultorderprice
  , resultmembercount
  , registtime
  , updatetime
)
SELECT
  shopseq
  , targetmanagementdate
  , targettoppv
  , targetvisitcount
  , targetordercount
  , targetorderprice
  , targetmembercount
  , resulttoppv
  , resultvisitcount
  , resultordercount
  , resultorderprice
  , resultmembercount
  , registtime
  , updatetime
FROM
    hm3.targetmanagement
;


-- 『簡易集計受注』
--   easytotalingorder
INSERT INTO public.easytotalingorder
(
  shopseq
  , targetdate
  , guestordercount
  , guestorderpricetotal
  , beginnerordercount
  , beginnerorderpricetotal
  , repeaterordercount
  , repeaterorderpricetotal
  , totalordercount
  , totalorderprice
  , sessioncount
  , registtime
  , updatetime
)
SELECT
  shopseq
  , targetdate
  , guestordercount
  , guestorderpricetotal
  , beginnerordercount
  , beginnerorderpricetotal
  , repeaterordercount
  , repeaterorderpricetotal
  , totalordercount
  , totalorderprice
  , sessioncount
  , registtime
  , updatetime
FROM
    hm3.easytotalingorder
;


-- 『管理情報アラート』
--   managementinformationalert
INSERT INTO public.managementinformationalert
(
  shopseq
  , custommadeorder
  , periodicordererror
  , paymenttimelimitorder
  , paymentexcessanddeficiencyorder
  , prepaymentshipmentorder
  , laterpaymentshipmentorder
  , billemergencyorder
  , orderpointstockbelow
  , noresponseinformationmail
  , inquirygeneralnoresponse
  , registtime
  , updatetime
  , inquiryordernoresponse
  , waitingorder
  , reviewunapproved
  , reviewviolatereport
)
SELECT
  shopseq
  , custommadeorder
  , periodicordererror
  , paymenttimelimitorder
  , paymentexcessanddeficiencyorder
  , prepaymentshipmentorder
  , laterpaymentshipmentorder
  , billemergencyorder
  , orderpointstockbelow
  , noresponseinformationmail
  , inquirynoresponse
  , registtime
  , updatetime
  , 0
  , 0
  , 0
  , 0
FROM
    hm3.managementinformationalert
;


-- 『カスタムアラート』
--   customalert
INSERT INTO public.customalert
(
  customalertseq
  , alerttype
  , alertname
  , searchcondition
  , searchresultcount
  , shopseq
  , registtime
  , updatetime
)
SELECT
  customalertseq
  , alerttype
  , alertname
  , searchcondition
  , searchresultcount
  , shopseq
  , registtime
  , updatetime
FROM
    hm3.customalert
;


-- 『サイトマップ』
--   sitemap
INSERT INTO public.sitemap
(
  sitemapseq
  , sitemapname
  , sitetype
  , outputfilename
  , outputflag
  , urltype
  , loc
  , changefreq
  , priority
  , note
  , registtime
  , updatetime
)
SELECT
  sitemapseq
  , sitemapname
  , sitetype
  , outputfilename
  , outputflag
  , urltype
  , loc
  , changefreq
  , priority
  , note
  , registtime
  , updatetime
FROM
    hm3.sitemap
;


-- 『フリーエリア』
--   freearea
INSERT INTO public.freearea
(
  freeareaseq
  , shopseq
  , freeareakey
  , openstarttime
  , freeareatitle
  , freeareabodypc
  , freeareabodysp
  , freeareabodymb
  , targetgoods
  , registtime
  , updatetime
  , sitemapflag
)
SELECT
  freeareaseq
  , shopseq
  , freeareakey
  , openstarttime
  , freeareatitle
  , REPLACE(REPLACE(REPLACE(REPLACE(freeareabodypc, '/ec/shop/', '/'),'/lp.html?fkey=','/special.html?fkey='),'fkey=companyinfo_jp','fkey=companyinfo_jp&menu=no'),'fkey=companyinfo_eng','fkey=companyinfo_eng&menu=no')
  , REPLACE(REPLACE(REPLACE(REPLACE(freeareabodysp, '/ec/shop/', '/'),'/lp.html?fkey=','/special.html?fkey='),'fkey=companyinfo_jp','fkey=companyinfo_jp&menu=no'),'fkey=companyinfo_eng','fkey=companyinfo_eng&menu=no')
  , REPLACE(REPLACE(REPLACE(REPLACE(freeareabodymb, '/ec/shop/', '/'),'/lp.html?fkey=','/special.html?fkey='),'fkey=companyinfo_jp','fkey=companyinfo_jp&menu=no'),'fkey=companyinfo_eng','fkey=companyinfo_eng&menu=no')
  , targetgoods
  , registtime
  , updatetime
  , sitemapflag
FROM
    hm3.freearea
;


-- 『ニュース』
--   news
INSERT INTO public.news
(
  newsseq
  , shopseq
  , titlepc
  , titlesp
  , titlemb
  , newsbodypc
  , newsbodysp
  , newsbodymb
  , newsurlpc
  , newsurlsp
  , newsurlmb
  , newsopenstatuspc
  , newsopenstatusmb
  , newsopenstarttimepc
  , newsopenendtimepc
  , newsopenstarttimemb
  , newsopenendtimemb
  , newstime
  , registtime
  , updatetime
  , newsnotepc
  , newsnotesp
  , newsnotemb
)
SELECT
  newsseq
  , shopseq
  , titlepc
  , titlesp
  , titlemb
  , newsbodypc
  , newsbodysp
  , newsbodymb
  , newsurlpc
  , newsurlsp
  , newsurlmb
  , newsopenstatuspc
  , newsopenstatusmb
  , newsopenstarttimepc
  , newsopenendtimepc
  , newsopenstarttimemb
  , newsopenendtimemb
  , newstime
  , registtime
  , updatetime
  , newsnotepc
  , newsnotesp
  , newsnotemb
FROM
    hm3.news
;


-- 『アンケート』
--   questionnaire
INSERT INTO public.questionnaire
(
  questionnaireseq
  , shopseq
  , questionnairekey
  , name
  , namepc
  , openstatuspc
  , openstarttime
  , openendtime
  , freetextpc
  , completetextpc
  , memo
  , versionno
  , registtime
  , updatetime
  , sitemapflag
)
SELECT
  questionnaireseq
  , shopseq
  , questionnairekey
  , name
  , namepc
  , openstatuspc
  , openstarttime
  , openendtime
  , freetextpc
  , completetextpc
  , memo
  , versionno
  , registtime
  , updatetime
  , sitemapflag
FROM
    hm3.questionnaire
;


-- 『アンケート設問』
--   question
INSERT INTO public.question
(
  questionseq
  , questionnaireseq
  , orderdisplay
  , question
  , openstatus
  , replyrequiredflag
  , replytype
  , replyvalidatepattern
  , replymaxlength
  , choices
  , versionno
  , registtime
  , updatetime
)
SELECT
  questionseq
  , questionnaireseq
  , orderdisplay
  , question
  , openstatus
  , replyrequiredflag
  , replytype
  , replyvalidatepattern
  , replymaxlength
  , choices
  , versionno
  , registtime
  , updatetime
FROM
    hm3.question
;


-- 『アンケート回答』
--   questionnairereply
INSERT INTO public.questionnairereply
(
  questionnairereplyseq
  , questionnaireseq
  , sitetype
  , devicetype
  , memberinfoseq
  , ordercode
  , registtime
  , questionnairekey
)
SELECT
  questionnairereplyseq
  , h3questionnairereply.questionnaireseq
  , sitetype
  , devicetype
  , memberinfoseq
  , ordercode
  , h3questionnairereply.registtime
  , hm3questionnaire.questionnairekey
FROM
    hm3.questionnairereply h3questionnairereply
LEFT OUTER JOIN 
    hm3.questionnaire hm3questionnaire
     ON  h3questionnairereply.questionnaireseq = hm3questionnaire.questionnaireseq
;


-- 『アンケート設問回答』
--   questionreply
INSERT INTO public.questionreply
(
  questionnairereplyseq
  , questionseq
  , reply
  , registtime
)
SELECT
  questionnairereplyseq
  , questionseq
  , reply
  , registtime
FROM
    hm3.questionreply
;


-- 『アンケート回答集計』
--   questionnairereplytotal
INSERT INTO public.questionnairereplytotal
(
  questionnaireseq
  , replycount
  , registtime
)
SELECT
  questionnaireseq
  , replycount
  , registtime
FROM
    hm3.questionnairereplytotal
;


-- 『アンケート設問回答集計』
--   questionreplytotal
INSERT INTO public.questionreplytotal
(
  questionseq
  , replychoice
  , replychoicecount
  , registtime
)
SELECT
  questionseq
  , replychoice
  , replychoicecount
  , registtime
FROM
    hm3.questionreplytotal
;


-- 『フリーエリア表示対象会員』
--   freeareaviewablemember
INSERT INTO public.freeareaviewablemember
(
  freeareaseq
  , memberinfoseq
  , shopseq
  , registtime
  , updatetime
)
SELECT
  freeareaseq
  , memberinfoseq
  , shopseq
  , registtime
  , updatetime
FROM
    hm3.freeareaviewablemember
;


-- 『ニュース表示対象会員』
--   newsviewablemember
INSERT INTO public.newsviewablemember
(
  newsseq
  , memberinfoseq
  , shopseq
  , registtime
  , updatetime
)
SELECT
  newsseq
  , memberinfoseq
  , shopseq
  , registtime
  , updatetime
FROM
    hm3.newsviewablemember
;


-- 『キャンペーン』
--   campaign
INSERT INTO public.campaign
(
  campaignseq
  , shopseq
  , campaigncode
  , campaignname
  , campaignstartdate
  , campaignenddate
  , redirecturl
  , note
  , campaigncost
  , deleteflag
  , registtime
  , updatetime
)
SELECT
  campaignseq
  , shopseq
  , campaigncode
  , campaignname
  , campaignstartdate
  , campaignenddate
  , redirecturl
  , note
  , campaigncost
  , deleteflag
  , registtime
  , updatetime
FROM
    hm3.campaign
;


-- 『カテゴリ』
--   category
INSERT INTO public.category
(
  categoryseq
  , shopseq
  , categoryid
  , categoryname
  , categoryopenstatuspc
  , categoryopenstatusmb
  , categoryopenstarttimepc
  , categoryopenendtimepc
  , categoryopenstarttimemb
  , categoryopenendtimemb
  , categorytype
  , categoryseqpath
  , orderdisplay
  , rootcategoryseq
  , categorylevel
  , categorypath
  , manualdisplayflag
  , versionno
  , registtime
  , updatetime
  , sitemapflag
)
SELECT
  categoryseq
  , shopseq
  , categoryid
  , categoryname
  , categoryopenstatuspc
  , categoryopenstatusmb
  , categoryopenstarttimepc
  , categoryopenendtimepc
  , categoryopenstarttimemb
  , categoryopenendtimemb
  , categorytype
  , categoryseqpath
  , orderdisplay
  , rootcategoryseq
  , categorylevel
  , categorypath
  , manualdisplayflag
  , versionno
  , registtime
  , updatetime
  , sitemapflag
FROM
    hm3.category
;


-- 『カテゴリ表示』
--   categorydisplay
INSERT INTO public.categorydisplay
(
  categoryseq
  , categorynamepc
  , categorynamemb
  , categorynotepc
  , categorynotemb
  , freetextpc
  , freetextsp
  , freetextmb
  , metadescription
  , metakeyword
  , categoryimagepc
  , categoryimagesp
  , categoryimagemb
  , registtime
  , updatetime
)
SELECT
  categoryseq
  , categorynamepc
  , categorynamemb
  , categorynotepc
  , categorynotemb
  , freetextpc
  , freetextsp
  , freetextmb
  , metadescription
  , metakeyword
  , categoryimagepc
  , categoryimagesp
  , categoryimagemb
  , registtime
  , updatetime
FROM
    hm3.categorydisplay
;


-- 『会員』
--   memberinfo
INSERT INTO public.memberinfo
(
  memberinfoseq
  , memberinfostatus
  , memberinfouniqueid
  , memberinfoid
  , memberinfopassword
  , memberinfolastname
  , memberinfofirstname
  , memberinfolastkana
  , memberinfofirstkana
  , memberinfosex
  , memberinfobirthday
  , memberinfotel
  , memberinfocontacttel
  , memberinfozipcode
  , memberinfoprefecture
  , prefecturetype
  , memberinfoaddress1
  , memberinfoaddress2
  , memberinfoaddress3
  , memberinfomail
  , sendmailpermitflag
  , sendmailstarttime
  , sendmailstoptime
  , shopseq
  , accessuid
  , versionno
  , admissionymd
  , secessionymd
  , memo
  , memberinfofax
  , lastlogintime
  , lastlogindevicetype
  , lastloginuseragent
  , paymentmemberid
  , paymentcardregisttype
  , loginfailurecount
  , accountlocktime
  , passwordneedchangeflag
  , registtime
  , updatetime
  , noantisocialflag
  , customerno
  , representativename
  , memberinfoaddress4
  , memberinfoaddress5
  , businesstype
  , sendfaxpermitflag
  , senddirectmailflag
  , nonconsultationday
  , approvestatus
  , drugsalestype
  , medicalequipmentsalestype
  , dentalmonopolysalestype
  , creditpaymentuseflag
  , transferpaymentuseflag
  , cashdeliveryuseflag
  , directdebituseflag
  , monthlypayuseflag
  , memberlisttype
  , onlineregistflag
  , medicaltreatmentflag
  , medicaltreatmentmemo
  , metalpermitflag
  , confdocumenttype
  , accountingtype
  , onlineLoginAdvisability
)
SELECT
  hm3member.memberinfoseq
  , memberinfostatus
  , memberinfouniqueid
  , memberinfoid
  , memberinfopassword
  , memberinfolastname
  , memberinfofirstname
  , memberinfolastkana
  , memberinfofirstkana
  , memberinfosex
  , memberinfobirthday
  , memberinfotel
  , memberinfocontacttel
  , memberinfozipcode
  , memberinfoprefecture
  , prefecturetype
  , memberinfoaddress1
  , memberinfoaddress2
  , memberinfoaddress3
  , memberinfomail
  , sendmailpermitflag
  , sendmailstarttime
  , sendmailstoptime
  , hm3member.shopseq
  , accessuid
  , versionno
  , admissionymd
  , secessionymd
  , memo
  , memberinfofax
  , lastlogintime
  , lastlogindevicetype
  , lastloginuseragent
  , paymentmemberid
  , paymentcardregisttype
  , loginfailurecount
  , accountlocktime
  , CASE WHEN passwordneedchangeflag IS NULL THEN '0' ELSE passwordneedchangeflag END AS passwordneedchangeflag
  , hm3member.registtime
  , hm3member.updatetime
  , CASE WHEN hm3memberadd.memberaddattrinput IS NULL THEN '0' WHEN hm3memberadd.memberaddattrinput = '保証する' THEN '1' ELSE '0' END AS noantisocialflag
  , customerno
  , representativename
  , memberinfoaddress4
  , memberinfoaddress5
  , businesstype
  , sendfaxpermitflag
  , senddirectmailflag
  , nonconsultationday
  , approvestatus
  , drugsalestype
  , medicalequipmentsalestype
  , dentalmonopolysalestype
  , creditpaymentuseflag
  , transferpaymentuseflag
  , cashdeliveryuseflag
  , directdebituseflag
  , monthlypayuseflag
  , memberlisttype
  , onlineregistflag
  , medicaltreatmentflag
  , medicaltreatmentmemo
  , metalpermitflag
  , confdocumenttype
  , accountingtype
  , onlineLoginAdvisability
FROM
    hm3.memberinfo hm3member
LEFT OUTER JOIN 
    hm3.memberaddattributeresult hm3memberadd
     ON  hm3memberadd.memberinfoseq = hm3member.memberinfoseq
     AND hm3memberadd.memberaddattrseq = '10000001'
;


/* 最終決定：不使用テーブルのため移行対象外とします
-- 『住所録』
--   addressbook
INSERT INTO public.addressbook
(
  addressbookseq
  , memberinfoseq
  , addressbookname
  , addressbooklastname
  , addressbookfirstname
  , addressbooklastkana
  , addressbookfirstkana
  , addressbooktel
  , addressbookzipcode
  , addressbookprefecture
  , addressbookaddress1
  , addressbookaddress2
  , addressbookaddress3
  , registtime
  , updatetime
  , addressbookaddress4
  , addressbookaddress5
  , customerno
  , addressbookapproveflag
)
SELECT
  addressbookseq
  , memberinfoseq
  , addressbookname
  , addressbooklastname
  , addressbookfirstname
  , addressbooklastkana
  , addressbookfirstkana
  , addressbooktel
  , addressbookzipcode
  , addressbookprefecture
  , addressbookaddress1
  , addressbookaddress2
  , addressbookaddress3
  , registtime
  , updatetime
  , addressbookaddress4
  , addressbookaddress5
  , customerno
  , addressbookapproveflag
FROM
    hm3.addressbook
;
*/


-- 『確認メール』
--   confirmmail
INSERT INTO public.confirmmail
(
  confirmmailseq
  , shopseq
  , memberinfoseq
  , orderseq
  , confirmmail
  , confirmmailtype
  , confirmmailpassword
  , effectivetime
  , registtime
  , updatetime
)
SELECT
  confirmmailseq
  , shopseq
  , memberinfoseq
  , orderseq
  , confirmmail
  , confirmmailtype
  , confirmmailpassword
  , effectivetime
  , registtime
  , updatetime
FROM
    hm3.confirmmail
;


-- 『マルチペイメント請求』
--   mulpaybill
INSERT INTO public.mulpaybill
(
  mulpaybillseq
  , paytype
  , trantype
  , orderseq
  , orderversionno
  , orderid
  , accessid
  , accesspass
  , jobcd
  , method
  , paytimes
  , seqmode
  , cardseq
  , amount
  , tax
  , tdflag
  , acs
  , forward
  , approve
  , tranid
  , trandate
  , convenience
  , confno
  , receiptno
  , paymentterm
  , custid
  , bkcode
  , encryptreceiptno
  , mailaddress
  , edyorderno
  , suicaorderno
  , errcode
  , errinfo
  , paymenturl
  , cancelamount
  , canceltax
  , amazonorderreferenceid
  , amazonpaymentconfirmstatus
  , registtime
  , updatetime
)
SELECT
  mulpaybillseq
  , paytype
  , trantype
  , orderseq
  , orderversionno
  , orderid
  , accessid
  , accesspass
  , jobcd
  , method
  , paytimes
  , seqmode
  , cardseq
  , amount
  , tax
  , tdflag
  , acs
  , forward
  , approve
  , tranid
  , trandate
  , convenience
  , confno
  , receiptno
  , paymentterm
  , custid
  , bkcode
  , encryptreceiptno
  , mailaddress
  , edyorderno
  , suicaorderno
  , errcode
  , errinfo
  , null
  , null
  , null
  , null
  , null
  , registtime
  , updatetime
FROM
    hm3.mulpaybill
;


-- 『マルチペイメント決済結果』
--   mulpayresult
INSERT INTO public.mulpayresult
(
  mulpayresultseq
  , receivemode
  , processedflag
  , shopseq
  , orderseq
  , orderid
  , status
  , jobcd
  , processdate
  , itemcode
  , amount
  , tax
  , siteid
  , memberid
  , cardno
  , expire
  , currency
  , forward
  , method
  , paytimes
  , tranid
  , approve
  , trandate
  , errcode
  , errinfo
  , clientfield1
  , clientfield2
  , clientfield3
  , paytype
  , cvscode
  , cvsconfno
  , cvsreceiptno
  , edyreceiptno
  , edyorderno
  , suicareceiptno
  , suicaorderno
  , custid
  , bkcode
  , confno
  , paymentterm
  , encryptreceiptno
  , finishdate
  , receiptdate
  , cancelamount
  , canceltax
  , amazonorderreferenceid
  , registtime
  , updatetime
)
SELECT
  mulpayresultseq
  , receivemode
  , processedflag
  , shopseq
  , orderseq
  , orderid
  , status
  , jobcd
  , processdate
  , itemcode
  , amount
  , tax
  , siteid
  , memberid
  , cardno
  , expire
  , currency
  , forward
  , method
  , paytimes
  , tranid
  , approve
  , trandate
  , errcode
  , errinfo
  , clientfield1
  , clientfield2
  , clientfield3
  , paytype
  , cvscode
  , cvsconfno
  , cvsreceiptno
  , edyreceiptno
  , edyorderno
  , suicareceiptno
  , suicaorderno
  , custid
  , bkcode
  , confno
  , paymentterm
  , encryptreceiptno
  , finishdate
  , receiptdate
  , null
  , null
  , null
  , registtime
  , updatetime
FROM
    hm3.mulpayresult
;


-- 『与信枠解放』
--   creditlinereport
INSERT INTO public.creditlinereport
(
  orderid
  , registtime
  , updatetime
)
SELECT
  orderid
  , registtime
  , updatetime
FROM
    hm3.creditlinereport
;


-- 『同時注文排他』
--   simultaneousorderexclusion
INSERT INTO public.simultaneousorderexclusion
(
  memberinfoseq
  , versionno
  , registtime
  , updatetime
)
SELECT
  memberinfoseq
  , versionno
  , registtime
  , updatetime
FROM
    hm3.simultaneousorderexclusion
;


-- 『アクセス情報』
--   accessinfo
INSERT INTO public.accessinfo
(
  shopseq
  , accesstype
  , accessdate
  , sitetype
  , devicetype
  , goodsgroupseq
  , campaigncode
  , accessuid
  , accesscount
  , registtime
  , updatetime
)
SELECT
  shopseq
  , accesstype
  , accessdate
  , sitetype
  , devicetype
  , goodsgroupseq
  , campaigncode
  , accessuid
  , accesscount
  , registtime
  , updatetime
FROM
    hm3.accessinfo
;


-- 『アクセス検索キーワード』
--   accesssearchkeyword
INSERT INTO public.accesssearchkeyword
(
  shopseq
  , accesstime
  , accessuid
  , sitetype
  , searchkeyword
  , searchresultcount
  , searchcategoryseq
  , searchpricefrom
  , searchpriceto
  , pageid
  , registtime
  , updatetime
)
SELECT
  shopseq
  , accesstime
  , accessuid
  , sitetype
  , searchkeyword
  , searchresultcount
  , searchcategoryseq
  , searchpricefrom
  , searchpriceto
  , REPLACE(REPLACE(pageid, '/view/front/', '/'),'_sp','')
  , registtime
  , updatetime
FROM
    hm3.accesssearchkeyword
;


-- 『基幹連携日時履歴』
--   coopdatehistory
INSERT INTO public.coopdatehistory
(
  coopid
  , lastcoopdate
  , registtime
  , updatetime
)
SELECT
  coopid
  , lastcoopdate
  , registtime
  , updatetime
FROM
    hm3.coopdatehistory
;


-- 『ログイン可否判定』
--   loginadvisability
INSERT INTO public.loginadvisability
(
  loginadvisabilityseq
  , memberinfostatus
  , approvestatus
  , onlineloginadvisability
  , memberlisttype
  , accountingtype
  , loginadvisabilitytype
)
SELECT
  loginadvisabilityseq
  , memberinfostatus
  , approvestatus
  , onlineloginadvisability
  , memberlisttype
  , accountingtype
  , loginadvisabilitytype
FROM
    hm3.loginadvisability
;


-- 『販売可否判定』
--   salesadvisability
INSERT INTO public.salesadvisability
(
  salesadvisabilityseq
  , logintype
  , dentalmonopolysalesflg
  , businesstype
  , confdocumenttype
  , goodsclasstype
  , salablegoodstype
)
SELECT
  salesadvisabilityseq
  , logintype
  , dentalmonopolysalesflg
  , businesstype
  , confdocumenttype
  , goodsclasstype
  , salablegoodstype
FROM
    hm3.salesadvisability
;
