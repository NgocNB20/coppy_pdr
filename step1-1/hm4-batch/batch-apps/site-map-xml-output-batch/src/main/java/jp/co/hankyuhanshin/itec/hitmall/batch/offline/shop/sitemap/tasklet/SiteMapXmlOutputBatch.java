/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.batch.offline.shop.sitemap.tasklet;

import jp.co.hankyuhanshin.itec.hitmall.batch.common.BatchExitMessageDto;
import jp.co.hankyuhanshin.itec.hitmall.batch.common.BatchExitMessageUtil;
import jp.co.hankyuhanshin.itec.hitmall.batch.core.AbstractBatch;
import jp.co.hankyuhanshin.itec.hitmall.batch.offline.shop.sitemap.dto.SiteMapXmlOutputMessageDto;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeBatchName;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeMailTemplateType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeUrlType;
import jp.co.hankyuhanshin.itec.hitmall.dao.goods.category.CategoryDao;
import jp.co.hankyuhanshin.itec.hitmall.dao.goods.goodsgroup.GoodsGroupDao;
import jp.co.hankyuhanshin.itec.hitmall.dao.shop.freearea.FreeAreaDao;
import jp.co.hankyuhanshin.itec.hitmall.dao.shop.news.NewsDao;
import jp.co.hankyuhanshin.itec.hitmall.dao.shop.questionnaire.QuestionnaireDao;
import jp.co.hankyuhanshin.itec.hitmall.dao.shop.sitemap.SiteMapDao;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goodsgroup.GoodsGroupForSiteMapDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.mail.MailDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.shop.sitemap.SiteMapIndexXmlDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.shop.sitemap.SiteMapUrlSetXmlDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.shop.sitemap.UrlXmlDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.category.CategoryEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.FreeAreaEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.news.NewsEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.questionnaire.QuestionnaireEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.sitemap.SiteMapEntity;
import jp.co.hankyuhanshin.itec.hitmall.service.mail.MailSendService;
import jp.co.hankyuhanshin.itec.hmbase.util.common.PropertiesUtil;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ZipUtil;
import jp.co.hankyuhanshin.itec.hmbase.util.mail.InstantMailSetting;
import jp.co.hankyuhanshin.itec.hmbase.util.seasar.StringUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.DateUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.FileOperationUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.core.env.Environment;

import javax.xml.bind.JAXB;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * サイトマップXML出力バッチ
 * 作成日：2021/03/31
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
public class SiteMapXmlOutputBatch extends AbstractBatch {

    /**
     * ロガー
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(SiteMapXmlOutputBatch.class);

    /**
     * 作業ディレクトリ
     */
    private String workDir;

    /**
     * 出力ディレクトリ
     */
    private String outDir;

    /**
     * バックアップディレクトリ
     */
    private String backupDir;

    /**
     * TOPディレクトリURL
     */
    private String topDirUrl;

    /**
     * サイトマップDao
     */
    private final SiteMapDao siteMapDao;

    /**
     * カテゴリDao
     */
    private final CategoryDao categoryDao;

    /**
     * 商品グループDao
     */
    private final GoodsGroupDao goodsGroupDao;

    /**
     * ニュースDao
     */
    private final NewsDao newsDao;

    /**
     * フリーエリアDao
     */
    private final FreeAreaDao freeAreaDao;

    /**
     * アンケートDao
     */
    private final QuestionnaireDao questionnaireDao;

    /**
     * 使用する文字セット
     */
    public static final String MAIL_CARSET = "ISO-2022-JP";

    /**
     * XMLファイルサフィックス
     */
    protected static final String XML_SURFFIX = ".xml";

    /**
     * ZIPファイルサフィックス
     */
    public static final String ZIP_SURFFIX = ".zip";

    /**
     * XMLファイル日付フォーマット
     */
    public static final String XML_DATE_FORMAT = "yyyyMMdd_HHmmss";

    /**
     * XML最終更新日用日付フォーマット
     */
    public static final String W3C_DATETIME = "yyyy-MM-dd'T'HH:mm:ssXXX";

    /**
     * 改行コード
     */
    public static final String CRLF = "\r\n";

    /**
     * 日付関連Utility
     */
    private final DateUtility dateUtility;

    /**
     * バッチ処理日付
     */
    private Timestamp currentTime;

    /**
     * サイトマップXMLリスト（sitemapindex）
     */
    private final SiteMapIndexXmlDto siteMapIndexXmlDto;

    /**
     * サイトマップ情報（URL単位）
     */
    private List<UrlXmlDto> urlXmlDtoList;

    /**
     * サイトマップ情報（サイトマップ単位）
     */
    private List<UrlXmlDto> siteMapSiteMapXmlDtoList = new ArrayList<>();

    /**
     * レポートメッセージリスト
     */
    private List<SiteMapXmlOutputMessageDto> messageDtoList;

    /**
     * レポートメッセージリスト
     */
    private String errorMessage;

    /**
     * 作業ファイル名
     */
    private String fileName = "";

    /**
     * 作業ファイル
     */
    private File tmpFile = null;

    /**
     * XML書き出し文字列（各サイトマップ用）
     */
    private StringWriter urlsetString = new StringWriter();

    /**
     * XML書き出し文字列（サイトマップインデックス用）
     */
    private StringWriter siteMapIndexString = new StringWriter();

    /**
     * XML置換文字列
     */
    private static final String XML_REPLACE_STRING = "standalone=\"yes\"";

    /**
     * 管理者用メール送信設定
     */
    private final InstantMailSetting mailSetting;

    /**
     * メール送信サービス
     */
    private final MailSendService mailSendService;

    /**
     * バッチ終了メッセージ共通処理
     */
    private final BatchExitMessageUtil exitMessageUtil;

    /**
     * コンストラクタ<br/>
     */
    public SiteMapXmlOutputBatch(Environment environment) {
        this.siteMapDao = ApplicationContextUtility.getBean(SiteMapDao.class);
        this.categoryDao = ApplicationContextUtility.getBean(CategoryDao.class);
        this.goodsGroupDao = ApplicationContextUtility.getBean(GoodsGroupDao.class);
        this.newsDao = ApplicationContextUtility.getBean(NewsDao.class);
        this.freeAreaDao = ApplicationContextUtility.getBean(FreeAreaDao.class);
        this.dateUtility = ApplicationContextUtility.getBean(DateUtility.class);
        this.siteMapIndexXmlDto = ApplicationContextUtility.getBean(SiteMapIndexXmlDto.class);
        this.mailSendService = ApplicationContextUtility.getBean(MailSendService.class);
        this.exitMessageUtil = new BatchExitMessageUtil();
        this.mailSetting = new InstantMailSetting(environment.getProperty("mail.setting.sitemapxmloutput.smtp.server"),
                                                  environment.getProperty("mail.setting.sitemapxmloutput.mail.from"),
                                                  null, null, Collections.singletonList(
                        environment.getProperty("mail.setting.sitemapxmloutput.mail.receivers"))
        );
        this.questionnaireDao = ApplicationContextUtility.getBean(QuestionnaireDao.class);
    }

    /**
     * 主処理<br/>
     *
     * @return 処理結果
     * @throws Exception Exception
     */
    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        // コンテキスト取得
        ExecutionContext executionContext =
                        chunkContext.getStepContext().getStepExecution().getJobExecution().getExecutionContext();

        // バッチのジョッブ情報取得
        JobParameters jobParameters = chunkContext.getStepContext().getStepExecution().getJobParameters();
        String administratorId = jobParameters.getString("administratorId");
        String shopSeq = jobParameters.getString("shopSeq");
        if (!org.apache.commons.lang3.StringUtils.isEmpty(shopSeq)) {
            super.setShopSeq(Integer.valueOf(shopSeq));
        }

        try {
            // 前処理
            init();

            /* サイトマップ情報を取得 */
            LOGGER.info("▼サイトマップの取得開始");
            List<SiteMapEntity> entityList = siteMapDao.getEntityByOutputFlag();
            LOGGER.info(" サイトマップ取得件数：" + entityList.size() + " 件");
            LOGGER.info("▲サイトマップの取得完了");

            // 出力対象がない場合はそのまま終了
            if (entityList.size() != 0) {
                LOGGER.info("▼各サイトマップXMLの出力開始");

                /** ファイル単位で処理する情報を初期化 */
                resetSiteMapXml();
                for (SiteMapEntity entity : entityList) {

                    // 処理対象のファイル名が切り替わった際にファイル出力
                    // ※ただしループ1回目はキーが空文字なのでファイル出力は行わない
                    if ((!StringUtil.isEmpty(fileName)) && !fileName.equals(entity.getOutputFileName())) {
                        // XMLファイルの作成
                        tmpFile = createXml(workDir, fileName);

                        // XMLファイルの出力
                        outputSiteMapXml(tmpFile, urlXmlDtoList);

                        // レポートメッセージの作成
                        makeReportMessage(tmpFile, urlXmlDtoList);

                        // サイトマップインデックスXML情報の作成
                        makeSiteMapIndexXml(tmpFile);
                        LOGGER.info(" サイトマップXML出力:" + fileName);

                        // ファイル単位で処理する情報を初期化
                        resetSiteMapXml();
                    }
                    // サイトマップXML情報の作成
                    makeSiteMapXml(entity);
                    // ブレイクキーの交換
                    fileName = entity.getOutputFileName();
                }

                // サイトマップXMLファイルの作成
                tmpFile = createXml(workDir, fileName);

                // サイトマップXMLファイルの出力
                outputSiteMapXml(tmpFile, urlXmlDtoList);

                // サイトマップインデックスXML情報の作成
                makeSiteMapIndexXml(tmpFile);

                // レポートメッセージの作成
                makeReportMessage(tmpFile, urlXmlDtoList);
                LOGGER.info(" サイトマップXML出力:" + fileName);
                resetSiteMapXml();
                LOGGER.info("▲各サイトマップXMLの出力完了");

                LOGGER.info("▼サイトマップインデックスXML出力開始");
                fileName = "sitemap_index.xml";

                // サイトマップインデックスXMLファイルの作成
                tmpFile = createXml(workDir, fileName);

                // サイトマップインデックスXMLファイルの出力
                outputSiteMapIndexXml(tmpFile);
                LOGGER.info(" サイトマップインデックスXML出力:" + fileName);

                // レポートメッセージの作成
                makeReportMessage(tmpFile, siteMapSiteMapXmlDtoList);
                LOGGER.info("▲サイトマップインデックスXML出力完了");

                // 作業ディレクトリから出力ディレクトリへ移動
                moveFile(workDir, outDir, false);

                /* サイトマップXMLバックアップ */
                backupFile(workDir, backupDir, false);

                /* 完了メール送信(管理者) */
                transmitMall();

            } else {
                LOGGER.info("出力対象がありませんでした。");
                executionContext.put(
                                BatchExitMessageUtil.exitMsg, exitMessageUtil.convertObjectToJson(
                                                new BatchExitMessageDto(null, "出力対象がありませんでした。")));
            }
        } catch (Exception e) {
            LOGGER.error("サイトマップ作成処理で例外が発生しました。バッチ処理を中断します。", e);

            if (StringUtil.isEmpty(errorMessage)) {
                errorMessage = "処理中に予期せぬエラーが発生しました。";
            }
            // 管理者に異常メール送信
            sendAdministratorErrorMail(errorMessage);
            executionContext.put(
                            BatchExitMessageUtil.exitMsg, exitMessageUtil.convertObjectToJson(
                                            new BatchExitMessageDto(null, "サイトマップ作成処理で例外が発生しました。バッチ処理を中断します。")));
            throw e;
        } finally {
            // 作業フォルダ内ファイルの削除
            clearWorkDirectory();
        }
        executionContext.put(
                        BatchExitMessageUtil.exitMsg, exitMessageUtil.convertObjectToJson(
                                        new BatchExitMessageDto(null, this.getReportString().toString())));

        return RepeatStatus.FINISHED;
    }

    /**
     * 初期処理<br/>
     *
     * @throws Exception             Exception エクセプション
     * @throws FileNotFoundException ディレクトリ・ファイルなし
     */
    protected void init() throws Exception {
        // FileNotFoundException
        LOGGER.info("▼初期処理開始");

        // バッチ開始時間の設定
        currentTime = dateUtility.getCurrentTime();

        // ディレクトリ設定の読み込み
        outDir = PropertiesUtil.getSystemPropertiesValue("sitemapxmloutput.output.outdir");
        workDir = PropertiesUtil.getSystemPropertiesValue("sitemapxmloutput.output.workdir");
        backupDir = PropertiesUtil.getSystemPropertiesValue("sitemapxmloutput.output.backupdir");

        // TOPディレクトリURL
        topDirUrl = PropertiesUtil.getSystemPropertiesValue("sitemapxmloutput.topdir.url");

        // 作業フォルダ内ファイルの削除
        clearWorkDirectory();

        // サイトマップXML初期化
        resetSiteMapXml();

        // レポートメッセージ初期化
        messageDtoList = new ArrayList<>();

        LOGGER.info("▲初期処理終了");

    }

    /**
     * サイトマップXML出力情報を初期化
     */
    protected void resetSiteMapXml() {
        // 作業ファイル初期化
        fileName = "";
        tmpFile = null;
        // サイトマップ情報（URL単位） を初期化
        urlsetString = new StringWriter();
        urlXmlDtoList = new ArrayList<>();
    }

    /**
     * XMLファイルの作成<br/>
     *
     * @param workDir  作業ディレクトリ
     * @param fileName XMLファイル名
     * @return 作成ファイル
     * @throws Exception XMLファイル作成失敗
     */
    public File createXml(String workDir, String fileName) throws Exception {
        try {
            File tmpFile = new File(workDir + fileName);
            return tmpFile;
        } catch (Exception e) {
            // ファイルの作成に失敗
            LOGGER.error("XMLファイルの作成に失敗しました。", e);
            errorMessage = "XMLファイルの作成に失敗しました。";
            throw e;
        }
    }

    /**
     * サイトマップXML情報の作成<br/>
     *
     * @param entity サイトマップエンティティ
     */
    public void makeSiteMapXml(SiteMapEntity entity) {

        // XMLを作成
        if (HTypeUrlType.UNCHANGED.equals(entity.getUrlType())) {
            // 0:静的
            setSeitekiSiteMapXml(entity);
        } else if (HTypeUrlType.ITEM_LIST == entity.getUrlType()) {
            // 1:商品一覧
            setCategorySiteMapXml(entity);
        } else if (HTypeUrlType.GOODS_DETAIL == entity.getUrlType()
                   || HTypeUrlType.SET_GOODA_DETAIL == entity.getUrlType()
                   || HTypeUrlType.GOODS_EXPLANATION == entity.getUrlType()
                   || HTypeUrlType.STOCK_LIST == entity.getUrlType()
                   || HTypeUrlType.GOODS_PICTURE_LIST == entity.getUrlType()
                   || HTypeUrlType.GOODS_UNIT_PICTURE_LIST == entity.getUrlType()) {
            // 2:商品詳細、3:セット商品詳細、
            // 9:商品説明（MBのみ）、10:在庫一覧（MBのみ）、11:商品画像一覧（MBのみ）、12:商品画像一覧(規格)（MBのみ）
            setGoodsDetailSiteMapXml(entity);
        } else if (HTypeUrlType.NEWS_DETAIL == entity.getUrlType()) {
            // 4:ニュース詳細
            setNewsSiteMapXml(entity);
        } else if (HTypeUrlType.SPECIAL_PAGE == entity.getUrlType() || HTypeUrlType.LP_PAGE == entity.getUrlType()) {
            // 5:特集ページ、6:ランディングページ
            setSpecialPageSiteMapXml(entity);
        } else if (HTypeUrlType.QUESTIONNAIRE == entity.getUrlType()) {
            // 8:アンケート
            setQuestionSiteMapXml(entity);
        }

    }

    /**
     * サイトマップXML（静的）設定<br/>
     *
     * @param entity サイトマップエンティティ
     */
    public void setSeitekiSiteMapXml(SiteMapEntity entity) {

        // サイトマップ用XMLリスト設定
        setUrlXmlDtoList(entity, null, null);

    }

    /**
     * サイトマップXML（各カテゴリ）設定<br/>
     *
     * @param entity サイトマップエンティティ
     */
    public void setCategorySiteMapXml(SiteMapEntity entity) {

        // 各カテゴリ情報の取得
        List<CategoryEntity> entityList =
                        categoryDao.getCategoryForSiteMap(entity.getSiteType().getValue(), currentTime);
        for (CategoryEntity categoryEntity : entityList) {
            // サイトマップ用XMLリスト設定
            setUrlXmlDtoList(
                            entity, "cid=" + encodingUrlParameter(categoryEntity.getCategoryId()),
                            categoryEntity.getUpdateTime()
                            );

        }
    }

    /**
     * サイトマップXML（商品詳細・セット商品詳細・商品説明・在庫一覧・商品画像一覧）設定<br/>
     *
     * @param entity サイトマップエンティティ
     */
    public void setGoodsDetailSiteMapXml(SiteMapEntity entity) {

        // 商品詳細情報の取得
        List<GoodsGroupForSiteMapDto> dtoList = goodsGroupDao.getGoodsGroupForSiteMap(entity.getSiteType().getValue(),
                                                                                      entity.getUrlType().getValue(),
                                                                                      currentTime
                                                                                     );
        for (GoodsGroupForSiteMapDto dto : dtoList) {
            String loc = null;
            if (HTypeUrlType.GOODS_PICTURE_LIST.equals(entity.getUrlType())) {
                loc = "ggcd=" + encodingUrlParameter(dto.getGoodsGroupCode()) + "&versionNo=" + encodingUrlParameter(
                                String.valueOf(dto.getImageTypeVersionNo()));
            } else {
                loc = "ggcd=" + encodingUrlParameter(dto.getGoodsGroupCode());
            }
            // サイトマップ用XMLリスト設定
            setUrlXmlDtoList(entity, loc, dto.getLatestUpdateTime());
        }
    }

    /**
     * サイトマップXML（ニュース詳細）設定<br/>
     *
     * @param entity サイトマップエンティティ
     */
    public void setNewsSiteMapXml(SiteMapEntity entity) {

        List<NewsEntity> entityList = newsDao.getEntityForSiteMap(entity.getSiteType().getValue(), currentTime);

        for (NewsEntity newsEntity : entityList) {
            // サイトマップ用XMLリスト設定
            setUrlXmlDtoList(
                            entity, "nseq=" + encodingUrlParameter(newsEntity.getNewsSeq().toString()),
                            newsEntity.getUpdateTime()
                            );
        }
    }

    /**
     * サイトマップXML（特集ページ・ランディングページ）設定<br/>
     *
     * @param entity サイトマップエンティティ
     */
    public void setSpecialPageSiteMapXml(SiteMapEntity entity) {

        List<FreeAreaEntity> entityList = freeAreaDao.getEntityForSiteMap(entity.getUrlType().getValue(), currentTime);

        for (FreeAreaEntity freeAreaEntity : entityList) {
            // サイトマップ用XMLリスト設定
            setUrlXmlDtoList(
                            entity, "fkey=" + encodingUrlParameter(freeAreaEntity.getFreeAreaKey()),
                            freeAreaEntity.getUpdateTime()
                            );
        }
    }

    /**
     * サイトマップXML（アンケート）設定<br/>
     *
     * @param entity サイトマップエンティティ
     */
    public void setQuestionSiteMapXml(SiteMapEntity entity) {

        List<QuestionnaireEntity> entityList =
                        questionnaireDao.getEntityForSiteMap(entity.getSiteType().getValue(), currentTime);

        for (QuestionnaireEntity questionnaireEntity : entityList) {
            // サイトマップ用XMLリスト設定
            setUrlXmlDtoList(
                            entity, "qkey=" + encodingUrlParameter(questionnaireEntity.getQuestionnaireKey()),
                            questionnaireEntity.getUpdateTime()
                            );
        }
    }

    /**
     * サイトマップ用XML設定<br/>
     *
     * @param entity     サイトマップエンティティ
     * @param param      動的パラメータ
     * @param updateTime 更新日時
     */
    public void setUrlXmlDtoList(SiteMapEntity entity, String param, Timestamp updateTime) {

        if (updateTime == null) {
            updateTime = currentTime;
        }

        // XML用DTOコンポーネントの宣言
        UrlXmlDto urlXmlDto = ApplicationContextUtility.getBean(UrlXmlDto.class);
        if (StringUtil.isNotEmpty(param)) {
            urlXmlDto.setLoc(entity.getLoc() + param);
        } else {
            urlXmlDto.setLoc(entity.getLoc());
        }

        urlXmlDto.setLastmod(dateUtility.format(updateTime, W3C_DATETIME));
        urlXmlDto.setChangefreq(entity.getChangefreq());
        urlXmlDto.setPriority(entity.getPriority());

        urlXmlDtoList.add(urlXmlDto);
    }

    /**
     * パラメータURLエンコーディング<br/>
     *
     * @param str 対象文字列
     * @return URLエンコードされたパラメータ
     */
    public String encodingUrlParameter(String str) {
        try {
            return URLEncoder.encode(str, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            LOGGER.warn("文字エンコーディングサポート例外：", e);
        }
        return str;
    }

    /**
     * サイトマップXML出力<br/>
     *
     * @param workFile      作成XML
     * @param urlXmlDtoList XMLリスト
     * @throws IOException XMLファイル生成エラー
     */
    public void outputSiteMapXml(File workFile, List<UrlXmlDto> urlXmlDtoList) throws IOException {
        try {
            // サイトマップXMLリスト（urlset）
            SiteMapUrlSetXmlDto urlSetXmlDto = ApplicationContextUtility.getBean(SiteMapUrlSetXmlDto.class);

            // XML出力準備
            urlSetXmlDto.setUrl(urlXmlDtoList);

            // DTOをXML出力文字列に出力
            JAXB.marshal(urlSetXmlDto, urlsetString);

            // XML出力文字列を作業ディレクトリに出力
            outputXml(workFile, urlsetString.toString().replace(XML_REPLACE_STRING, ""));

        } catch (Exception e) {
            LOGGER.error("サイトマップXML出力で失敗しました。設定ファイルを確認してください。" + CRLF + "ファイル :" + workFile, e);
            errorMessage = "サイトマップXML出力で失敗しました。設定ファイルを確認してください。" + CRLF + "ファイル :" + workFile;
            throw e;
        }
    }

    /**
     * サイトマップインデックスXML情報の作成<br/>
     *
     * @param workFile 作成XML
     */
    public void makeSiteMapIndexXml(File workFile) {

        // XML用DTOコンポーネントの宣言
        UrlXmlDto xmlSiteMapDto = ApplicationContextUtility.getBean(UrlXmlDto.class);

        // loc
        xmlSiteMapDto.setLoc(topDirUrl + workFile.getName());
        SimpleDateFormat sdf = new SimpleDateFormat(W3C_DATETIME);

        // lastmod
        xmlSiteMapDto.setLastmod(sdf.format(workFile.lastModified()));

        // 追加
        siteMapSiteMapXmlDtoList.add(xmlSiteMapDto);
    }

    /**
     * サイトマップインデックスXML出力<br/>
     *
     * @param workFile 作成XML
     * @throws IOException XMLファイル生成エラー
     */
    public void outputSiteMapIndexXml(File workFile) throws IOException {
        try {
            // XML出力準備
            siteMapIndexXmlDto.setSitemap(siteMapSiteMapXmlDtoList);
            // XMLを作業ディレクトリに出力
            JAXB.marshal(siteMapIndexXmlDto, siteMapIndexString);
            outputXml(workFile, siteMapIndexString.toString().toString().replace(XML_REPLACE_STRING, ""));

        } catch (Exception e) {
            LOGGER.error("サイトマップインデックスXML出力で失敗しました。設定ファイルを確認してください。" + CRLF + "ファイル :" + workFile, e);
            errorMessage = "サイトマップインデックスXML出力で失敗しました。設定ファイルを確認してください。" + CRLF + "ファイル :" + workFile;
            throw e;
        }
    }

    /**
     * XML出力<br/>
     *
     * @param workFile  作成XML
     * @param xmlString XML
     * @throws IOException XMLファイル生成エラー
     */
    public void outputXml(File workFile, String xmlString) throws IOException {

        // XMLファイル出力
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(workFile), "UTF-8")));
            pw.write(xmlString);
        } finally {
            if (pw != null) {
                pw.close();
            }
        }
    }

    /**
     * ファイルバックアップ<br />
     *
     * @param fromPath  コピー元
     * @param toPath    コピー先
     * @param removeSrc true：コピー元削除 false:削除しない
     * @throws Exception コピーエラー
     */
    protected void backupFile(String fromPath, String toPath, boolean removeSrc) throws Exception {
        LOGGER.info("▼XMLファイルをバックアップディレクトリへ圧縮・移動開始");
        try {
            // ファイルを圧縮し、バックアップディレクトリに格納
            for (SiteMapXmlOutputMessageDto messageDto : messageDtoList) {
                String zipName = getZipFileName(messageDto.getFileName());
                ZipUtil.archive(backupDir + dateUtility.format(currentTime, XML_DATE_FORMAT) + zipName.replaceAll(
                                ZIP_SURFFIX, ""), new File(workDir + messageDto.getFileName()));
            }
        } catch (Exception e) {
            // XMLバックアップ失敗
            LOGGER.error(" サイトマップXMLのバックアップに失敗しました。" + CRLF + "バックアップ元ディレクトリ :" + fromPath + CRLF + "バックアップ先ディレクトリ:"
                         + toPath, e);
            errorMessage = "サイトマップXMLのバックアップに失敗しました。" + CRLF + "バックアップ元ディレクトリ :" + fromPath + CRLF + "バックアップ先ディレクトリ:"
                           + toPath;
        }
        LOGGER.info("▲XMLファイルをバックアップディレクトリへ圧縮・移動完了");

    }

    /**
     * zipファイル名の生成<br/>
     *
     * @param fileName zip化前ファイル名
     * @return zipファイル名
     */
    protected String getZipFileName(String fileName) {
        return fileName.replaceAll(XML_SURFFIX, ZIP_SURFFIX);
    }

    /**
     * ファイル移動<br />
     *
     * @param fromPath  コピー元
     * @param toPath    コピー先
     * @param removeSrc true：コピー元削除 false:削除しない
     * @throws Exception コピーエラー
     */
    protected void moveFile(String fromPath, String toPath, boolean removeSrc) throws Exception {
        LOGGER.info("▼XMLファイルを出力ディレクトリへ移動開始");
        try {
            File fromFile = new File(fromPath);
            File toFile = new File(toPath);
            FileOperationUtility fileOperationUtility = ApplicationContextUtility.getBean(FileOperationUtility.class);
            fileOperationUtility.put(fromFile, toFile, removeSrc);
        } catch (Exception e) {
            // XML移動失敗
            LOGGER.error(
                            " 出力ディレクトリへの移動に失敗しました。" + CRLF + "コピー元ディレクトリ :" + fromPath + CRLF + "コピー先ディレクトリ :" + toPath,
                            e
                        );
            errorMessage = "出力ディレクトリへの移動に失敗しました。" + CRLF + "コピー元ディレクトリ :" + fromPath + CRLF + "コピー先ディレクトリ :" + toPath;
            throw e;
        }
        LOGGER.info("▲XMLファイルを出力ディレクトリへ移動完了");
    }

    /**
     * レポートメッセージ作成<br/>
     *
     * @param workFile 処理対象ファイル
     * @param dtoList  出力対象DTOリスト
     */
    protected void makeReportMessage(File workFile, List<UrlXmlDto> dtoList) {
        SiteMapXmlOutputMessageDto messageDto = ApplicationContextUtility.getBean(SiteMapXmlOutputMessageDto.class);
        messageDto.setFileName(workFile.getName());
        messageDto.setFileSize(workFile.length());
        messageDto.setCountUrl(dtoList.size());
        messageDtoList.add(messageDto);
    }

    /**
     * レポートメッセージ設定<br/>
     *
     * @param reportMessage レポートメッセージ文字列バッファ
     */
    protected void setReportMessage(StringBuilder reportMessage) {
        if (messageDtoList.size() == 0) {
            reportMessage.append("出力対象データなし");
        } else {
            // Dtoに詰めた情報からレポート情報を作成
            for (SiteMapXmlOutputMessageDto messageDto : messageDtoList) {
                reportMessage.append(
                                String.format("%-6s", "ファイル名：") + String.format("%-25s", messageDto.getFileName()));
                reportMessage.append(String.format(" %-4s", "サイズ：") + String.format(" %10s bytes",
                                                                                    messageDto.getFileSize()
                                                                                   ));
                reportMessage.append(String.format("%4s", "件数 ") + String.format("%8s", messageDto.getCountUrl())
                                     + String.format("%3s", "件 ") + CRLF);
            }
        }

        if (StringUtil.isNotEmpty(errorMessage)) {
            reportMessage.append(CRLF + "-----" + CRLF);
            reportMessage.append(errorMessage + CRLF);
        }
    }

    /**
     * メール送信処理<br/>
     *
     * @throws Exception メール送信失敗
     */
    protected void transmitMall() throws Exception {
        try {
            MailDto mailDto = ApplicationContextUtility.getBean(MailDto.class);

            Map<String, Object> mailContentsMap = new HashMap<>();
            Map<String, String> valueMap = new LinkedHashMap<>();

            // システム名を取得
            String systemName = PropertiesUtil.getSystemPropertiesValue("system.name");

            // メール内変数の設定
            valueMap.put("SYSTEM", systemName);
            StringBuilder resultMessage = new StringBuilder();
            setReportMessage(resultMessage);
            valueMap.put("RESULT", resultMessage.toString());
            valueMap.put("BATCH_NAME", HTypeBatchName.BATCH_SITEMAPXML_OUTPUT.getLabel());

            mailContentsMap.put("admin", valueMap);

            mailDto.setMailTemplateType(HTypeMailTemplateType.SITEMAP_XML_OUTPUT_ADMINISTRATOR_MAIL);
            mailDto.setFrom(this.mailSetting.getMailFrom());
            mailDto.setToList(this.mailSetting.getNotificationReceivers());
            mailDto.setSubject("【" + systemName + "】" + HTypeBatchName.BATCH_SITEMAPXML_OUTPUT.getLabel() + "報告");
            mailDto.setMailContentsMap(mailContentsMap);

            this.mailSendService.execute(mailDto);
            LOGGER.info("送信しました。");
            report("サイトマップ出力処理は正常に終了しました。");
            report(resultMessage.toString());
        } catch (Exception e) {
            LOGGER.warn("管理者への通知メール送信に失敗しました。", e);
        }

    }

    /**
     * レポート登録時にレポート内容が1000文字を超えている場合は 999文字でカットして登録します
     *
     * @param line レポート内容
     * @see jp.co.hankyuhanshin.itec.hitmall.batch.core.AbstractBatch#report(java.lang.String)
     */
    @Override
    protected void report(String line) {

        // 設定済みのレポート文字数
        int reportLength = getReportString().length();

        if (reportLength == 999) {
            // レポートがすでに999文字の場合は何も設定しない
            return;
        }

        // レポートの総文字数を取得
        int allLength = reportLength + line.length();
        if (allLength >= 1000) {
            // 1000文字を超えている場合は999にする
            line = line.substring(0, 999 - reportLength - 1);
        }

        // レポート登録
        super.report(line);
    }

    /**
     * 管理者向けエラーメールを送信する
     *
     * @param result 結果レポート文字列
     * @return 送信成功:true、送信失敗:false
     */
    protected boolean sendAdministratorErrorMail(final String result) {

        try {
            MailDto mailDto = ApplicationContextUtility.getBean(MailDto.class);

            Map<String, Object> mailContentsMap = new HashMap<>();
            final Map<String, String> valueMap = new HashMap<>();

            // システム名を取得
            String systemName = PropertiesUtil.getSystemPropertiesValue("system.name");

            valueMap.put("SYSTEM", systemName);
            valueMap.put("BATCH_NAME", HTypeBatchName.BATCH_SITEMAPXML_OUTPUT.getLabel());
            valueMap.put("RESULT", result);

            mailContentsMap.put("error", valueMap);

            mailDto.setMailTemplateType(HTypeMailTemplateType.SITEMAP_XML_OUTPUT_ADMINISTRATOR_ERROR_MAIL);
            mailDto.setFrom(this.mailSetting.getMailFrom());
            mailDto.setToList(this.mailSetting.getNotificationReceivers());
            mailDto.setSubject("【" + systemName + "】" + HTypeBatchName.BATCH_SITEMAPXML_OUTPUT.getLabel() + "報告");
            mailDto.setMailContentsMap(mailContentsMap);

            this.mailSendService.execute(mailDto);

            LOGGER.info("管理者へ通知メールを送信しました。");
            this.report(new Timestamp(System.currentTimeMillis()) + " 例外が発生しました。ロールバックし、処理を終了します。");

            return true;
        } catch (Exception e) {
            LOGGER.warn("管理者への通知メール送信に失敗しました。", e);

            return false;
        }
    }

    /**
     * 作業フォルダのクリア
     *
     * @throws Exception 作業フォルダクリア時エラー
     */
    public void clearWorkDirectory() throws Exception {
        LOGGER.info("▼作業フォルダ内ファイル削除処理開始");

        File workDirectory = new File(workDir);
        File[] files = workDirectory.listFiles();
        if (files == null) {
            LOGGER.warn("　対象ファイルが存在しませんでした。");
            return;
        }

        for (File tempFile : files) {
            tempFile.delete();
        }
        LOGGER.info("▲作業フォルダ内ファイル削除成功");

    }

}
