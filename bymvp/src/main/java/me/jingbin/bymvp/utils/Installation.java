package me.jingbin.bymvp.utils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import androidx.core.app.ActivityCompat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.UUID;

/**
 * @author jingbin
 * @date 2017/9/12
 * 生成UUID
 * 平台支持：Android - 2.2+ (支持): 与设备的imei号一致。
 * 注意：如果无法获取设备imei则使用设备wifi的mac地址，如果无法获取设备mac地址则随机生成设备标识号，确保不同App在同一台设备上获取的值一致。
 * --
 * --
 * --
 * --- imei              设备唯一，不会随着安装包卸载变化
 * --- 设备mac地址        设备唯一，不会随着安装包卸载变化
 * --- 随机生成设备标识号  每次卸载后再安装都会生成新的
 * 生成uuid规则：
 * - 第一步获取imei:  由于Android6.0敏感权限：Android6.0及以上需要权限才能获取到imei,如果不能获取到则获取设备的mac地址
 * - 第二步获取设备mac地址： 有网：所有设备均可获取到mac地址且唯一，    无网时：Android6.0及以下不能获取到，以上可以，但是有特例，比如魅族可以获取到
 * - 第三步获取随机生成设备标识号，系统随机生成，每次卸载后都会变化
 * --
 * --
 * --- 友盟推送的设备id：可能每隔一段时间会变化(变化规则由友盟确定)
 * --- imei:如果有，就不会变化。用户权限同意后会及时记录上传。
 */

public class Installation {

    private static String sID = null;
    private static final String INSTALLATION = "INSTALLATION";

    /**
     * 首先：获取imei
     * 然后：获取设备wifi的mac地址
     * 最后：随机生成设备标识号
     */
    public static String getUUid(Context context) {
        if (!TextUtils.isEmpty(getIMEI(context))) {
            return getIMEI(context);
        } else if (!TextUtils.isEmpty(getMacAddress())) {
            return getMacAddress();
        } else {
            return randomUUID(context);
        }
    }

    /**
     * 1、获取设备imei
     * 注意：官方不推荐，没开权限无法获取到
     */
    @SuppressLint("HardwareIds")
    public static String getIMEI(Context context) {
        try {
            String imei = "";
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                return imei;
            }
            //实例化TelephonyManager对象
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            //获取IMEI号
            if (telephonyManager != null) {
                imei = telephonyManager.getDeviceId();
            }
            //再次做个验证，也不是什么时候都能获取到的
            if (imei == null) {
                imei = "";
            }
            return imei;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 2、获取MAC地址  6.0 后这段代码可以获取到
     * 获取mac地址有一点需要注意的就是android 6.0版本后，以下注释方法不再适用，不管任何手机都会返回"02:00:00:00:00:00"这个默认的mac地址，
     * 这是google官方为了加强权限管理而禁用了getSYstemService(Context.WIFI_SERVICE)方法来获得mac地址。
     * String macAddress= "";
     * WifiManager wifiManager = (WifiManager) MyApp.getContext().getSystemService(Context.WIFI_SERVICE);
     * WifiInfo wifiInfo = wifiManager.getConnectionInfo();
     * macAddress = wifiInfo.getMacAddress();
     * return macAddress;
     */
    public static String getMacAddress() {
        String macAddress = "";
        StringBuilder buf = new StringBuilder();
        NetworkInterface networkInterface = null;
        try {
            networkInterface = NetworkInterface.getByName("eth1");
            if (networkInterface == null) {
                networkInterface = NetworkInterface.getByName("wlan0");
            }
            if (networkInterface == null) {
                return macAddress;
            }
            byte[] addr = networkInterface.getHardwareAddress();
            for (byte b : addr) {
                buf.append(String.format("%02X:", b));
            }
            if (buf.length() > 0) {
                buf.deleteCharAt(buf.length() - 1);
            }
            macAddress = buf.toString();
        } catch (SocketException e) {
            e.printStackTrace();
            return macAddress;
        }
        return macAddress;
    }

    /**
     * 3、随机生成设备标识号
     * 注意：每次卸载后再安装都会生成新的
     */
    private synchronized static String randomUUID(Context context) {
        if (sID == null) {
            File installation = new File(context.getFilesDir(), INSTALLATION);
            try {
                if (!installation.exists()) {
                    writeInstallationFile(installation);
                }
                sID = readInstallationFile(installation);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sID;
    }

    private static String readInstallationFile(File installation) throws IOException {
        RandomAccessFile f = new RandomAccessFile(installation, "r");
        byte[] bytes = new byte[(int) f.length()];
        f.readFully(bytes);
        f.close();
        return new String(bytes);
    }

    private static void writeInstallationFile(File installation) throws IOException {
        FileOutputStream out = new FileOutputStream(installation);
        String id = UUID.randomUUID().toString();
        out.write(id.getBytes());
        out.close();
    }

}
