package com.imooc.o2o.util;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

//處理路徑相關
@Configuration
public class PathUtil {

    private static String separator = System.getProperty("file.separator");

    private static String winPath;

    private static String linuxPath;

    private static String shopReleventPath;

    @Value("${win.base.path}")
    public void setWinPath(String winPath) {
        PathUtil.winPath = winPath;
    }

    @Value("${linux.base.path}")
    public void setLinuxPath(String linuxPath) {
        PathUtil.linuxPath = linuxPath;
    }

    @Value("${shop.relevant.path}")
    public void setShopReleventPath(String shopReleventPath) {
        PathUtil.shopReleventPath = shopReleventPath;
    }

    //因應不同作業系統，設定存圖片的路徑
    public static String getImgBasePath() {
        String os = System.getProperty("os.name");
        String basePath = "";
        if (os.toLowerCase().startsWith("win")) {
            basePath = winPath;
        } else {
            basePath = linuxPath;
        }
        basePath = basePath.replace("/", separator);
        return basePath;
    }

    //為店舖新建一個資料夾用來存儲該店的圖片
    public static String getShopImagePath(Long shopId) {
        String path = shopReleventPath + shopId + separator;
        return path.replace("/", separator);
    }
}
