package me.jingbin.bymvp.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * @author jingbin
 */
public class AESUtils {

    //public static final Logger LOGGER = LoggerFactory.getLogger(AESUtils.class);

    private static final String AES = "AES";

    private static final String CRYPT_KEY = "odaedadfsaefased";

    private static final String IV_STRING = "oploeddfsaefased";

    /**
     * 加密
     *
     * @param content 加密内容
     * @return 密文
     */
    public static String encrypt(String content) {
        byte[] encryptedBytes = new byte[0];
        try {
            byte[] byteContent = content.getBytes("UTF-8");
            // 注意，为了能与 iOS 统一
            // 这里的 key 不可以使用 KeyGenerator、SecureRandom、SecretKey 生成
            byte[] enCodeFormat = CRYPT_KEY.getBytes("UTF-8");
            SecretKeySpec secretKeySpec = new SecretKeySpec(enCodeFormat, AES);
            byte[] initParam = IV_STRING.getBytes("UTF-8");
            IvParameterSpec ivParameterSpec = new IvParameterSpec(initParam);
            // 指定加密的算法、工作模式和填充方式
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);
            encryptedBytes = cipher.doFinal(byteContent);
            // 同样对加密后数据进行 base64 编码
        } catch (IOException | NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {

            //LOGGER.error("AES encrypt Exception,content = {},Exception = {}", content, e.getStackTrace());
        }
        return new BASE64Encoder().encode(encryptedBytes);
        //return new Base64().encodeToString(encryptedBytes);
    }

    /**
     * 解密
     *
     * @param content 密文
     * @return 明文
     */
    public static String decrypt(String content) {
        // base64 解码
        try {
            byte[] encryptedBytes = new BASE64Decoder().decodeBuffer(content);
            byte[] enCodeFormat = CRYPT_KEY.getBytes();
            SecretKeySpec secretKey = new SecretKeySpec(enCodeFormat, AES);
            byte[] initParam = IV_STRING.getBytes();
            IvParameterSpec ivParameterSpec = new IvParameterSpec(initParam);
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, secretKey, ivParameterSpec);
            byte[] result = cipher.doFinal(encryptedBytes);

            return new String(result, "UTF-8");
        } catch (IOException | NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {

            //LOGGER.error("AES decrypt Exception,content = {},Exception = {}", content, e.getStackTrace());
        }
        return null;
    }


    /**
     * Json字符串转Class
     */
    public static <T> T jsonToClass(String strJsonData, Class<T> cls) {
        Gson gson = new Gson();
        T t = gson.fromJson(strJsonData, cls);
        // 空字符串转换
        return t;
    }

    /**
     * json转list
     */
    public static List<?> jsonToList(String strJsonData, Class<?> cls) {
        Gson gson = new Gson();
        List<?> list = gson.fromJson(strJsonData, new TypeToken<List<?>>() {
        }.getType());

        if (null == list) {
            return null;
        }

        return list;
    }

    /**
     * Class对象转Json字符串
     */
    public static <T> String jsonFromClass(T obj) {
        Gson gson = new Gson();
        return gson.toJson(obj);
    }
}
