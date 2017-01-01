package maxiaobu.mqlviewpagerlibrary.header;

import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorListenerAdapter;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import maxiaobu.mqlviewpagerlibrary.MaterialViewPager;

/**
    *Created by 马小布 on 2017/1/1.
    *introduction: 真他娘的不知道说点啥
    *email:maxiaobu1999@163.com
    *功能：
    *伪码：
    *待完成：
*/
public class MaterialViewPagerImageHelper {

    private static MaterialViewPager.OnImageLoadListener imageLoadListener;

    /**
     * change the image with a fade
     *
     * @param urlImage
     * @param fadeDuration TODO : remove Picasso
     */
    public static void setImageUrl(final ImageView imageView, final String urlImage, final int fadeDuration) {
        final float alpha = ViewCompat.getAlpha(imageView);
        final ImageView viewToAnimate = imageView;

        //fade to alpha=0
        fadeOut(viewToAnimate, fadeDuration, new ViewPropertyAnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(View view) {
                super.onAnimationEnd(view);

                //change the image when alpha=0
                Glide.with(imageView.getContext()).load(urlImage)
                    .centerCrop()
                    .listener(new RequestListener<String, GlideDrawable>() {
                        @Override
                        public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                            Log.e("skajdh", "onException: "+e.toString() );
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                            //then fade to alpha=1
                            fadeIn(viewToAnimate, alpha, fadeDuration, new ViewPropertyAnimatorListenerAdapter());
                            if (imageLoadListener != null) {
                                imageLoadListener.OnImageLoad(imageView, ((BitmapDrawable) imageView.getDrawable()).getBitmap());
                            }
                            return false;
                        }
                    })
                    .into(viewToAnimate);
            }
        });
    }

    public static void fadeOut(View view, int fadeDuration, ViewPropertyAnimatorListenerAdapter listener) {
        //fade to alpha=0
        ViewCompat.animate(view)
            .alpha(0)
            .setDuration(fadeDuration)
            .withLayer()
            .setInterpolator(new DecelerateInterpolator())
            .setListener(listener);
    }

    public static void fadeIn(View view, float alpha, int fadeDuration, ViewPropertyAnimatorListenerAdapter listener) {
        //fade to alpha=0
        ViewCompat.animate(view)
            .alpha(alpha)
            .setDuration(fadeDuration)
            .withLayer()
            .setInterpolator(new AccelerateInterpolator())
            .setListener(listener);
    }

    /**
     * change the image with a fade
     *
     * @param drawable
     * @param fadeDuration
     */
    public static void setImageDrawable(final ImageView imageView, final Drawable drawable, final int fadeDuration) {
        final float alpha = ViewCompat.getAlpha(imageView);
        final ImageView viewToAnimate = imageView;

        fadeOut(viewToAnimate, fadeDuration, new ViewPropertyAnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(View view) {
                super.onAnimationEnd(view);
                //change the image when alpha=0

                imageView.setImageDrawable(drawable);

                //then fade to alpha=1
                fadeIn(viewToAnimate, alpha, fadeDuration, new ViewPropertyAnimatorListenerAdapter());
            }
        });
    }

    public static void setImageLoadListener(MaterialViewPager.OnImageLoadListener imageLoadListener) {
        MaterialViewPagerImageHelper.imageLoadListener = imageLoadListener;
    }
}

