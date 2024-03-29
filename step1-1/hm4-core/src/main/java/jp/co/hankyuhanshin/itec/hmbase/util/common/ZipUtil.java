/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hmbase.util.common;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * ZIP 圧縮ユーティリティクラス<br/>
 *
 * @author hs32101
 */
public class ZipUtil {

    /**
     * 一時ファイルの接尾語
     */
    protected static final String TMP_SUFFIX = ".zip";

    /**
     * コンストラクタ
     */
    private ZipUtil() {
    }

    /**
     * baseFile を圧縮します<br/>
     *
     * @param outPutFile 出力ファイル名
     * @param baseFile   基準になるディレクトリまたはファイル
     * @throws IOException
     */
    public static void archive(String outPutFile, File baseFile) throws IOException {
        // 出力先ファイル
        File zipfile = new File(outPutFile + TMP_SUFFIX);
        ZipOutputStream zos = null;
        try {
            // 出力先 OutputStream を生成
            zos = new ZipOutputStream(new FileOutputStream(zipfile));
            archive(zos, baseFile);
        } finally {
            if (zos != null) {
                zos.close();
            }

        }
    }

    /**
     * file を zos に出力する<br/>
     *
     * @param zos  zipファイル出力ストリーム
     * @param file 入力元ファイル
     * @throws IOException
     */
    protected static void archive(ZipOutputStream zos, File file) throws IOException {

        if (file.isDirectory()) {
            // ディレクトリに含まれるファイルを再起呼び出し。
            File[] files = file.listFiles();
            if (files == null) {
                return;
            }
            for (File f : files) {
                archive(zos, f);
            }
        } else {
            BufferedInputStream fis = null;
            try {
                // 入力ストリーム生成
                fis = new BufferedInputStream(new FileInputStream(file));
                // Entry 名称を取得する。
                String entryName = file.getName();
                // 出力先 Entry を設定する。
                zos.putNextEntry(new ZipEntry(entryName));
                // 入力ファイルを読み込み出力ストリームに書き込んでいく
                int ava = 0;
                while ((ava = fis.available()) > 0) {
                    byte[] bs = new byte[ava];
                    fis.read(bs);
                    zos.write(bs);
                }
                // 書き込んだら Entry を close する。
                zos.closeEntry();
            } finally {
                if (fis != null) {
                    fis.close();
                }

            }
        }
    }

}
