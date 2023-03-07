package com.anna.systempicture;

/**
 * description:MIMETYPE
 * time: 2022/11/11 10:30 AM.
 *
 * @author TangAnna
 * email: tang_an@murongtech.com
 * copyright: 北京沐融信息科技股份有限公司
 */
public enum MimeType {
    /**
     * 任何类型
     */
    GENERIC_MIME("", "*/*"),
    /**
     * PDF 类型
     */
    PDF_MIMET(".pdf", "application/pdf"),
    /**
     * ZIP文件
     */
    ZIP_MIME(".zip", "application/zip"),
    /**
     * image
     */
    IMAGE_MIME(".image", "image/*"),
    /**
     * jpeg
     */
    JPEG_MIME(".jpeg", "image/jpeg"),
    /**
     * jpg
     */
    JPG_MIME(".jpg", "image/jpg"),
    /**
     * png
     */
    PNG_MIME(".png", "image/png"),
    /**
     * gif
     */
    GIF_MIME(".gif", "image/gif"),

    /**
     * tiff
     */
    TIFF_MIME(".tiff", "image/tiff"),
    /**
     * tif
     */
    TIF_MIME(".tif", "image/tif"),
    /**
     * xlsx
     */
    XLSX_MIME(".xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"),
    /**
     * csv
     */
    CSV_MIME(".csv", "text/comma-separated-values"),

    /**
     * xls
     */
    XLS_MIME(".xls", "application/vnd.ms-excel"),
    /**
     * doc
     */
    DOC_MIME(".doc", "application/msword"),
    /**
     * docx
     */
    DOCX_MIME(".docx", "application/vnd.openxmlformats-officedocument.wordprocessingml.document"),
    /**
     * other
     */
    OTHER_MIME("other", "other"),
    ;

    /**
     * 扩展名
     */
    private final String extension;
    /**
     * mimeType
     */
    private final String mime;

    MimeType(String extension, String mime) {
        this.extension = extension;
        this.mime = mime;
    }

    /**
     * @return 扩展名
     */
    public String getExtension() {
        return extension;
    }

    /**
     * @return mimeType
     */
    public String getMime() {
        return mime;
    }

    /**
     * 通过mimeType得到MimeType
     *
     * @param mime
     * @return
     */
    public static MimeType mime2MimeType(String mime) {
        if (null == mime) {
            return GENERIC_MIME;
        }
        for (MimeType mimeType : values()) {
            if (mimeType.getMime().equals(mime)) {
                return mimeType;
            }
        }
        return GENERIC_MIME;
    }

    /**
     * 通过extension得到MimeType
     *
     * @param extension
     * @return
     */
    public static MimeType extension2MimeType(String extension) {
        if (null == extension) {
            return GENERIC_MIME;
        }
        //判断是否是以点开头，不是点开头的补全
        if (!extension.startsWith(".")) {
            extension = "." + extension;
        }
        for (MimeType mimeType : values()) {
            if (mimeType.getExtension().equals(extension)) {
                return mimeType;
            }
        }
        return GENERIC_MIME;
    }
}
