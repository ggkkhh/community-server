package com.roydon.business.oss.enums;

import com.roydon.common.exception.base.BaseException;

import java.util.HashMap;
import java.util.Map;

/**
 * FileTypeEnum
 *
 * @AUTHOR: roydon
 * @DATE: 2023/5/15
 **/
public enum FileTypeEnum {

    JPG(1, "jpg"),
    JPEG(2, "jpeg"),
    PNG(3, "png"),
    GIF(4, "gif"),
    MP4(5, "mp4"),
    TXT(6, "txt"),
    DOC(7, "doc"),
    DOCX(8, "docx"),
    XLS(9, "xls"),
    XLSX(10, "xlsx"),
    MP3(11, "mp3"),
    PDF(12, "pdf"),
    OTHERS(-1, "others");

    private final Integer value;
    private final String name;
    private static final Map<Integer, FileTypeEnum> ENUM_MAP = new HashMap();

    FileTypeEnum(Integer value, String name) {
        this.value = value;
        this.name = name;
    }

    public Integer getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public static FileTypeEnum fromValue(int valueType) {
        FileTypeEnum enm = (FileTypeEnum) ENUM_MAP.get(valueType);
        return enm;
    }

    protected static void registerEnum(FileTypeEnum[] enums) {
        if (enums != null) {
            FileTypeEnum[] var1 = enums;
            int var2 = enums.length;

            for (int var3 = 0; var3 < var2; ++var3) {
                FileTypeEnum enm = var1[var3];
                int key = enm.getValue();
                FileTypeEnum old = (FileTypeEnum) ENUM_MAP.put(key, enm);
                if (old != null) {
                    throw new BaseException("Repeated value:" + old.name());
                }
            }
        }

    }

    static {
        registerEnum(values());
    }

}
