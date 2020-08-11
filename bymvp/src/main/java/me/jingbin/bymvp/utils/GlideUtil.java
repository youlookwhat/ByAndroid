package me.jingbin.bymvp.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.BindingAdapter;
import androidx.fragment.app.FragmentActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;

import java.io.File;

import jp.wasabeef.glide.transformations.BlurTransformation;
import me.jingbin.mvpbinding.R;

/**
 * Created by jingbin on 2016/11/26.
 */

public class GlideUtil {

    private static GlideUtil instance;

    private GlideUtil() {
    }

    public static GlideUtil getInstance() {
        if (instance == null) {
            instance = new GlideUtil();
        }
        return instance;
    }


    /**
     * 显示随机的图片(每日推荐)
     *
     * @param imgNumber 有几张图片要显示,对应默认图
     * @param imageUrl  显示图片的url
     * @param imageView 对应图片控件
     */
    public static void displayRandom(int imgNumber, String imageUrl, ImageView imageView) {
        Glide.with(imageView.getContext())
                .load(imageUrl)
                .placeholder(getMusicDefaultPic(imgNumber))
                .error(getMusicDefaultPic(imgNumber))
                .transition(DrawableTransitionOptions.withCrossFade(1500))
                .into(imageView);
    }

    private static int getMusicDefaultPic(int imgNumber) {
//        switch (imgNumber) {
//            case 1:
//                return R.drawable.img_two_bi_one;
//            case 2:
//                return R.drawable.img_four_bi_three;
//            case 3:
//                return R.drawable.img_one_bi_one;
//            case 4:
//                return R.drawable.shape_bg_loading;
//            default:
//                break;
//        }
//        return R.drawable.img_four_bi_three;
        return R.color.color_loading;
    }

//--------------------------------------

    /**
     * 用于干货item，将gif图转换为静态图
     */
    public static void displayGif(String url, ImageView imageView) {

        Glide.with(imageView.getContext())
                .asBitmap()
                .load(url)
                .placeholder(R.color.color_loading)
                .error(R.color.color_loading)
//                .skipMemoryCache(true) //跳过内存缓存
//                .crossFade(1000)
//                .diskCacheStrategy(DiskCacheStrategy.SOURCE)// 缓存图片源文件（解决加载gif内存溢出问题）
//                .into(new GlideDrawableImageViewTarget(imageView, 1));
                .into(imageView);
    }

    /**
     * 书籍、妹子图、电影列表图
     * 默认图区别
     */
    public static void displayEspImage(String url, ImageView imageView, int type) {
        Glide.with(imageView.getContext())
                .load(url)
                .transition(DrawableTransitionOptions.withCrossFade(500))
                .placeholder(getDefaultPic(type))
                .error(getDefaultPic(type))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
    }

    private static int getDefaultPic(int type) {
//        switch (type) {
//            case 0:// 电影
//                return R.drawable.img_default_movie;
//            case 1:// 妹子
//                return R.drawable.img_default_meizi;
//            case 2:// 书籍
//                return R.drawable.img_default_book;
//            case 3:
//                return R.drawable.shape_bg_loading;
//            default:
//                break;
//        }
        return R.color.color_loading;
    }

    /**
     * 显示高斯模糊效果（电影详情页）
     */
    private static void displayGaussian(Context context, String url, ImageView imageView) {
        // "23":模糊度；"4":图片缩放4倍后再进行模糊
        Glide.with(context)
                .load(url)
                .error(R.color.color_loading)
                .placeholder(R.color.color_loading)
                .transition(DrawableTransitionOptions.withCrossFade(500))
                .transform(new BlurTransformation(50, 8))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
    }

    /**
     * 加载圆角图,暂时用到显示头像
     */
    @BindingAdapter("android:displayCircle")
    public static void displayCircle(ImageView imageView, String imageUrl) {
        Glide.with(imageView.getContext())
                .load(imageUrl)
                .transition(DrawableTransitionOptions.withCrossFade(500))
                .error(R.color.color_loading)
                .transform(new CircleCrop())
                .into(imageView);
    }

    /**
     * 妹子，电影列表图
     *
     * @param defaultPicType 电影：0；妹子：1； 书籍：2
     */
    @BindingAdapter({"android:displayFadeImage", "android:defaultPicType"})
    public static void displayFadeImage(ImageView imageView, String url, int defaultPicType) {
        displayEspImage(url, imageView, defaultPicType);
    }

    /**
     * 没有加载中的图
     */
    @BindingAdapter("android:showImg")
    public static void showImg(ImageView imageView, String url) {
        Glide.with(imageView.getContext())
                .load(url)
                .transition(DrawableTransitionOptions.withCrossFade(500))
                .placeholder(getDefaultPic(0))
                .error(getDefaultPic(0))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
    }

    public static void showImg(ImageView imageView, File url) {
        Glide.with(imageView.getContext())
                .load(url)
                .transition(DrawableTransitionOptions.withCrossFade(500))
                .placeholder(getDefaultPic(0))
                .error(getDefaultPic(0))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
    }

    /**
     * 电影列表图片
     */
    public static void showsizeImage(ImageView imageView, String url, int width, int height) {
        Glide.with(imageView.getContext())
                .load(url)
                .transition(DrawableTransitionOptions.withCrossFade(500))
                .override(width, height)
                .placeholder(getDefaultPic(0))
                .error(getDefaultPic(0))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
    }

    /**
     * 电影详情页显示高斯背景图
     */
    @BindingAdapter("android:showImgBg")
    public static void showImgBg(ImageView imageView, String url) {
        displayGaussian(imageView.getContext(), url, imageView);
    }


    /**
     * 热门电影头部图片
     */
    @BindingAdapter({"android:displayRandom", "android:imgType"})
    public static void displayRandom(ImageView imageView, int imageUrl, int imgType) {
        Glide.with(imageView.getContext())
                .load(imageUrl)
                .placeholder(getMusicDefaultPic(imgType))
                .error(getMusicDefaultPic(imgType))
                .transition(DrawableTransitionOptions.withCrossFade(1500))
                .into(imageView);
    }

    /**
     * 加载缩略图
     */
    public static void loadThumbnailImage(@NonNull FragmentActivity activity, ImageView imageView,
                                          String thumbnailUrl, String imageUrl, @Nullable RequestListener<Drawable> requestListener) {
        if (!TextUtils.isEmpty(imageUrl) && !imageUrl.endsWith(thumbnailUrl)) {
            int[] thumbnailImageSize = getImageSize(thumbnailUrl, false);
            // 缩略图需要加上宽高，不然加载完成后图的宽高不对
            RequestBuilder<Drawable> thumbnail = Glide
                    .with(activity)
                    .load(thumbnailUrl)
                    .override(thumbnailImageSize[0], thumbnailImageSize[1]);
            Glide.with(activity)
                    .load(imageUrl)
                    .thumbnail(thumbnail)
                    .listener(requestListener).into(imageView);
        } else {
            Glide.with(activity)
                    .load(imageUrl)
                    .transition(DrawableTransitionOptions.withCrossFade(300))
                    .listener(requestListener).into(imageView);
        }
    }

    public static int[] getImageSize(String imageUrl, boolean isNull) {
        int[] xes = new int[]{350, 540};
        if (imageUrl.contains("/")) {
            String[] split = imageUrl.split("/");
            String s = split[split.length - 1];
            boolean contains = s.contains("x");
            if (contains) {
                String[] xes1 = s.split("x");
                try {
                    xes[0] = Integer.parseInt(xes1[0]);
                    xes[1] = Integer.parseInt(xes1[1]);
                    return xes;
                } catch (Exception ignored) {
                }
            }
        }
        if (isNull) {
            return null;
        } else {
            return xes;
        }
    }
}
