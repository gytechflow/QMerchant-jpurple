package cm.clear.qmerchant.modules.coupons.adapter;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import cm.clear.qmerchant.databinding.ItemCouponListBinding;
import cm.clear.qmerchant.models.coupon.Coupon;
import cm.clear.qmerchant.models.coupon.CouponState;
import cm.clear.qmerchant.modules.coupons.Coupons;

public class CouponsAdapter extends RecyclerView.Adapter<CouponsAdapter.ViewHolder> {
    private List<Coupon> coupons = new ArrayList<>();
    private final Coupons owner;

    public CouponsAdapter(@NonNull List<Coupon> coupons, @NonNull Coupons owner) {
        this.coupons = new ArrayList<>(coupons);
        this.owner = owner;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCouponListBinding binding = ItemCouponListBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new CouponsAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Coupon coupon = coupons.get(position);
        holder.binding.setCoupon(coupon);
        holder.binding.setClickHandler(new ClickHandler(CouponsAdapter.this));
//        holder.binding.setCouponColor();
//        holder.couponId.setText(coupon.getCode());
//        holder.couponValue.setText(coupon.getValue());
        holder.state.setImageTintList(ColorStateList.valueOf(holder.itemView.getContext().getResources()
                .getColor(CouponState.getStateColor(coupon.getState_code()))));

        holder.useCoupon.setBackgroundTintList(ColorStateList.valueOf(holder.itemView.getContext().getResources()
                .getColor(CouponState.getUseStateColor(coupon.getState_code()))));

        holder.discardCoupon.setBackgroundTintList(ColorStateList.valueOf(holder.itemView.getContext().getResources()
                .getColor(CouponState.getCancelStateColor(coupon.getState_code()))));

    }

    void reload() {
        owner.reload();
    }

    @Override
    public int getItemCount() {
        return coupons.size();
    }

    public void navigate(String s, Bundle couponDetails, int destination) {
        owner.onNavigate(s, couponDetails, destination);
    }

    public void add(@NonNull List<Coupon> list) {
        if (!list.isEmpty()){
            coupons.addAll(list);
            notifyItemRangeInserted(coupons.indexOf(list.get(0)), list.size());
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView couponCode, couponValue;
        LinearLayout useCoupon, discardCoupon;
        ImageView state;
        ItemCouponListBinding binding;

        public ViewHolder(@NonNull ItemCouponListBinding binding) {
            super(binding.getRoot());
            this.couponCode = binding.couponCode;
            this.couponValue = binding.couponValue;
            this.state = binding.state;
            this.useCoupon = binding.useCoupon;
            this.discardCoupon = binding.discardCoupon;
            this.binding = binding;
        }
    }
}
