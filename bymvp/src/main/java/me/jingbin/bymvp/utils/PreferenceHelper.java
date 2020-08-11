package me.jingbin.bymvp.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author yeqing
 * @class describe 本地存储工具
 * 使用前需调用init(Context ctx)初始化，初始化一般放在Application中
 * @time 2019/8/1 11:09
 */
public class PreferenceHelper {
    private SharedPreferences mPreferences;
    private static PreferenceHelper mIntance;

    private PreferenceHelper(Context ctx) {
        if (mPreferences == null) {
            mPreferences = ctx.getSharedPreferences("config", Context.MODE_PRIVATE);
        }
    }

    /**
     * 初始化本地存储工具
     *
     * @param ctx
     * @author mslan
     * @date 2015年3月1日 下午5:23:35
     */
    public static void init(Context ctx) {
        if (mIntance == null) {
            mIntance = new PreferenceHelper(ctx);
        }
    }

    /**
     * 是否初始化
     *
     * @return
     * @author mslan
     * @date 2015-3-9 下午2:43:07
     */
    public static boolean isInit() {
        return mIntance != null;
    }

    public static PreferenceHelper getIntance() {
        if (mIntance == null) {
            throw new IllegalArgumentException("please invoke init(Context) before used");
        }
        return mIntance;
    }

    /**
     * 本地保存String类型数据
     *
     * @param key   保存关键字
     * @param value 保存值
     * @return
     * @author mslan
     * @date 2015年3月1日 下午5:30:19
     */
    public boolean saveString(String key, String value) {
        return mPreferences.edit().putString(key, value).commit();
    }

    /**
     * 本地保存Boolean类型数据
     *
     * @param key   关键字
     * @param value 值
     * @return
     * @author mslan
     * @date 2015年3月1日 下午5:31:07
     */
    public boolean saveBoolean(String key, boolean value) {
        return mPreferences.edit().putBoolean(key, value).commit();
    }

    /**
     * 读取String类型数据，如果关键字不存在返回""
     *
     * @param key 关键字
     * @return
     * @author mslan
     * @date 2015年3月1日 下午5:31:51
     */
    public String readString(String key) {
        return readString(key, "");
    }

    /**
     * 读取String类型数据
     *
     * @param key      关键字
     * @param defValue 如果关键字不存在，返回的默认数据
     * @return
     * @author mslan
     * @date 2015年3月1日 下午5:33:29
     */
    public String readString(String key, String defValue) {
        return mPreferences.getString(key, defValue);
    }

    public int readInt(String key) {
        return readInt(key, 0);
    }

    public int readInt(String key, int defValue) {
        return mPreferences.getInt(key, defValue);
    }

    public boolean saveInt(String key, int value) {
        return mPreferences.edit().putInt(key, value).commit();
    }

    /**
     * 读取Boolean类型数据，如果关键字不存在返回false
     *
     * @param key 关键字
     * @return
     * @author mslan
     * @date 2015年3月1日 下午5:34:52
     */
    public boolean readBoolean(String key) {
        return readBoolean(key, false);
    }

    /**
     * 读取Boolean类型数据
     *
     * @param key 关键字
     * @param def 如果关键字不存在，返回的默认数据
     * @return
     * @author mslan
     * @date 2015年3月1日 下午5:35:21
     */
    public boolean readBoolean(String key, boolean def) {
        return mPreferences.getBoolean(key, def);
    }


    /**
     * 用于保存集合
     *
     * @param key key
     * @param map map数据
     * @return 保存结果
     */
    public <K, V> boolean putHashMapData(String key, Map<K, V> map) {
        boolean result;
        SharedPreferences.Editor editor = mPreferences.edit();
        try {
            Gson gson = new Gson();
            String json = gson.toJson(map);
            editor.putString(key, json);
            result = true;
        } catch (Exception e) {
            result = false;
            e.printStackTrace();
        }
        editor.apply();
        return result;
    }

    /**
     * 用于保存集合
     *
     * @param key key
     * @return HashMap
     */
    public <V> HashMap<String, V> getHashMapData(String key, Class<V> clsV) {
        String json = mPreferences.getString(key, "");
        HashMap<String, V> map = new HashMap<>();
        Gson gson = new Gson();
        try {
            JsonObject obj = new JsonParser().parse(json).getAsJsonObject();
            Set<Map.Entry<String, JsonElement>> entrySet = obj.entrySet();
            for (Map.Entry<String, JsonElement> entry : entrySet) {
                String entryKey = entry.getKey();
                JsonObject value = (JsonObject) entry.getValue();
                map.put(entryKey, gson.fromJson(value, clsV));
            }
        } catch (Exception e) {
            return null;
        }
        return map;
    }

    /**
     * 用于保存集合
     *
     * @param key  key
     * @param list 集合数据
     * @return 保存结果
     */
    public <T> boolean putListData(String key, List<T> list) {
        boolean result = false;
        SharedPreferences.Editor editor = mPreferences.edit();
        if (list != null && list.size() > 0) {
            String type = list.get(0).getClass().getSimpleName();
            JsonArray array = new JsonArray();
            try {
                switch (type) {
                    case "Boolean":
                        for (int i = 0; i < list.size(); i++) {
                            array.add((Boolean) list.get(i));
                        }
                        break;
                    case "Long":
                        for (int i = 0; i < list.size(); i++) {
                            array.add((Long) list.get(i));
                        }
                        break;
                    case "Float":
                        for (int i = 0; i < list.size(); i++) {
                            array.add((Float) list.get(i));
                        }
                        break;
                    case "String":
                        for (int i = 0; i < list.size(); i++) {
                            array.add((String) list.get(i));
                        }
                        break;
                    case "Integer":
                        for (int i = 0; i < list.size(); i++) {
                            array.add((Integer) list.get(i));
                        }
                        break;
                    default:
                        Gson gson = new Gson();
                        for (int i = 0; i < list.size(); i++) {
                            JsonElement obj = gson.toJsonTree(list.get(i));
                            array.add(obj);
                        }
                        break;
                }
                editor.putString(key, array.toString());
                result = true;
            } catch (Exception e) {
                result = false;
                e.printStackTrace();
            }
            editor.apply();
        } else {
            editor.putString(key, "");
            editor.apply();
        }
        return result;
    }

    /**
     * 获取保存的List
     *
     * @param key key
     * @return 对应的Lis集合
     */
    public <T> List<T> getListData(String key, Class<T> cls) {
        List<T> list = new ArrayList<>();
        String json = mPreferences.getString(key, "");
        try {
            if (!json.equals("") && json.length() > 0) {
                Gson gson = new Gson();
                JsonArray array = new JsonParser().parse(json).getAsJsonArray();
                for (JsonElement elem : array) {
                    list.add(gson.fromJson(elem, cls));
                }
            }
        } catch (Exception e) {
            return null;
        }
        return list;
    }

    public static void putString(String key, String value) {
        PreferenceHelper.getIntance().saveString(key, value);
    }

    public static String getString(String key) {
        return PreferenceHelper.getIntance().readString(key);
    }

    public boolean removeKey(String key) {
        return mPreferences.edit().remove(key).commit();
    }
}
