package cm.clear.qmerchant.common;

import android.content.res.ColorStateList;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.progressindicator.CircularProgressIndicator;

public class CustomBindingAdapters {
    @BindingAdapter("app:src")
    public static void setImageViewSrc(@NonNull ImageView imageView, @DrawableRes int drawableRes) {
        imageView.setImageResource(drawableRes);
    }

    @BindingAdapter("app:addOnScrollListener")
    public static void addOnScrollListener(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.OnScrollListener scrollListener) {
        recyclerView.addOnScrollListener(scrollListener);
    }

    @BindingAdapter("app:setOnRefreshListener")
    public static void setOnRefreshListener(@NonNull SwipeRefreshLayout refreshLayout, @NonNull SwipeRefreshLayout.OnRefreshListener refreshListener) {
        refreshLayout.setOnRefreshListener(refreshListener);
    }

    @BindingAdapter("app:setAdapter")
    public static void setAdapter(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.Adapter adapter) {
        recyclerView.setAdapter(adapter);
    }

    @BindingAdapter("app:setRefreshing")
    public static void setRefreshing(@NonNull SwipeRefreshLayout refreshLayout, boolean visibility) {
        refreshLayout.setRefreshing(visibility);
    }

    @BindingAdapter("app:backgroundTint")
    public static void setViewBackgroundTint(@NonNull View view, @ColorInt int color) {
        view.setBackgroundTintList(ColorStateList.valueOf(color));
    }

    @BindingAdapter("app:layoutMarginTop")
    public static void setLayoutMarginBottom(@NonNull View view, @NonNull Float dimen) {
        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        layoutParams.topMargin = dimen.intValue();
        view.setLayoutParams(layoutParams);
    }

    @BindingAdapter("app:layoutHeight")
    public static void setLayoutHeight(@NonNull View view, @NonNull Float dimen) {
        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        layoutParams.height = dimen.intValue();
        view.setLayoutParams(layoutParams);
    }

    @BindingAdapter("app:positioning")
    public static void setPositioning(@NonNull View view, @NonNull Float margin, @NonNull Float height) {
        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        layoutParams.height = height.intValue();
        layoutParams.topMargin = margin.intValue();
        view.setLayoutParams(layoutParams);
    }

    @BindingAdapter("app:indicator_Color")
    public static void indicator_Color(@NonNull CircularProgressIndicator view, @ColorInt int color) {
        view.setIndicatorColor(color);
    }

    @BindingAdapter("app:background")
    public static void background(@NonNull View view, @DrawableRes int bg) {
        view.setBackgroundResource(bg);
    }
}
