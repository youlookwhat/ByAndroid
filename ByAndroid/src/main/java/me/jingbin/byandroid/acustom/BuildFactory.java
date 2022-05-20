package me.jingbin.byandroid.acustom;


/**
 * @author jingbin
 */
public class BuildFactory {

    private static BuildFactory instance;
    private Object gankHttps;
    private Object doubanHttps;

    public static BuildFactory getInstance() {
        if (instance == null) {
            synchronized (BuildFactory.class) {
                if (instance == null) {
                    instance = new BuildFactory();
                }
            }
        }
        return instance;
    }

    public <T> T create(Class<T> a, String type) {

        switch (type) {
            case HttpUtils.API_GANKIO:
                if (gankHttps == null) {
                    synchronized (BuildFactory.class) {
                        if (gankHttps == null) {
                            gankHttps = HttpUtils.getInstance().getBuilder(type).build().create(a);
                        }
                    }
                }
                return (T) gankHttps;
            case HttpUtils.API_DOUBAN:
                if (doubanHttps == null) {
                    synchronized (BuildFactory.class) {
                        if (doubanHttps == null) {
                            doubanHttps = HttpUtils.getInstance().getBuilder(type).build().create(a);
                        }
                    }
                }
                return (T) doubanHttps;
            default:
                if (gankHttps == null) {
                    synchronized (BuildFactory.class) {
                        if (gankHttps == null) {
                            gankHttps = HttpUtils.getInstance().getBuilder(type).build().create(a);
                        }
                    }
                }
                return (T) gankHttps;
        }
    }

}
