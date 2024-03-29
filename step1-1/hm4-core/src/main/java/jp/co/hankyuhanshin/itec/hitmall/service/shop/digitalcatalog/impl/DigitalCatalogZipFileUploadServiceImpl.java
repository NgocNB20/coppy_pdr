// 2023-renew No42 from here
package jp.co.hankyuhanshin.itec.hitmall.service.shop.digitalcatalog.impl;

import jp.co.hankyuhanshin.itec.hitmall.service.AbstractShopService;
import jp.co.hankyuhanshin.itec.hitmall.service.shop.digitalcatalog.DigitalCatalogZipFileUploadService;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import jp.co.hankyuhanshin.itec.hmbase.util.common.PropertiesUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.FileOperationUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.UnzipUtility;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * デジタルカタロzip一括アップロードService実装クラス
 *
 * @author Doan Thang (VTI Japan Co., Ltd.)
 */
@Service
public class DigitalCatalogZipFileUploadServiceImpl extends AbstractShopService
                implements DigitalCatalogZipFileUploadService {

    /**
     * ロガー
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(DigitalCatalogZipFileUploadServiceImpl.class);

    /**
     * テンプファイルディレクトリパス
     */
    private static final String TMP_FILE_PATH = "real.tmp.path";

    /**
     * ファイルパス
     */
    private static final String DIGITAL_CATALOG_FILE_PATH = "digital.catalog.directory";

    /**
     * zipファイルをディレクトリに保存します
     *
     * @param uploadedFile           UploadedFile
     * @param tmpCatalogZipFilePath  アップロード先判定
     * @return
     */
    @Override
    public int execute(MultipartFile uploadedFile, String tmpCatalogZipFilePath) {

        // パラメータのチェック
        ArgumentCheckUtil.assertNotNull("uploadedFile", "uploadedFile");

        try {
            if (StringUtils.isEmpty(PropertiesUtil.getSystemPropertiesValue(TMP_FILE_PATH))) {
                LOGGER.warn(TMP_FILE_PATH + "が取得できません。system.propertiesを確認してください。");
                return 0;
            }

            putFile(uploadedFile, tmpCatalogZipFilePath);
        } catch (RuntimeException e) {
            if (e.getCause().getCause() instanceof IOException) {
                throwMessage("PDR-2023RENEW-42-001-");
            }

            LOGGER.warn("RuntimeExceptionが発生しました。 -- " + e);

            return 0;
        } catch (Exception e) {
            LOGGER.warn("予期せぬ例外が発生しました。 -- " + e);

            return 0;
        }

        return 1;
    }

    /**
     * デジタルカタログzip一括アップロード処理を実行します。
     *
     * @param zipImageTarget            アップロード先判定
     * @param digitalCatalogUploadedDir デジタルカタログアップロードディレクトリ
     * @return int
     */
    @Override
    public int execute(String zipImageTarget, String digitalCatalogUploadedDir) {

        // 展開後のファイルリスト
        List<File> fileList = null;

        try {

            String digitalCatalogDir = PropertiesUtil.getSystemPropertiesValue(DIGITAL_CATALOG_FILE_PATH);

            if (StringUtils.isEmpty(digitalCatalogDir)) {
                LOGGER.warn(DIGITAL_CATALOG_FILE_PATH + "が取得できません。system.propertiesを確認してください。");
                return 0;
            }

            File digitalCatalogFile = new File(digitalCatalogUploadedDir);

            if (digitalCatalogFile.exists()) {
                FileUtils.cleanDirectory(digitalCatalogFile);
            }

            // ZIP 解凍Helper取得
            UnzipUtility unzipUtility = ApplicationContextUtility.getBean(UnzipUtility.class);

            fileList = unzipUtility.unzip(new File(zipImageTarget), digitalCatalogDir, true);
        } catch (RuntimeException e) {

            if (e.getCause().getCause() instanceof IOException) {
                throwMessage("PDR-2023RENEW-42-001-");
            }

            LOGGER.warn("RuntimeExceptionが発生しました。 -- " + e);

            return 0;
        } catch (Exception e) {
            LOGGER.warn("予期せぬ例外が発生しました。 -- " + e);

            return 0;
        }

        if (fileList == null) {
            LOGGER.warn("アップロード結果が取得できません。");

            return 0;
        }

        return fileList.size();
    }

    /**
     * ファイル移動処理<br/>
     *
     * @param src 移動元ファイル
     * @param dst 移動先ファイル名
     */
    private void putFile(final MultipartFile src, final String dst) throws IOException {

        FileOperationUtility fileOperationUtility = ApplicationContextUtility.getBean(FileOperationUtility.class);
        fileOperationUtility.put(src, dst);

    }
}
// 2023-renew No42 to here
