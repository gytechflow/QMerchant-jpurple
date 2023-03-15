package cm.clear.qmerchant.models.order;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.Comparator;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;

public class OrderDateComparator implements Comparator<Order> {
    @Override
    public int compare(Order order, Order t1) {
        return 0;
    }

    @Override
    public Comparator<Order> reversed() {
        return Comparator.super.reversed();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public Comparator<Order> thenComparing(Comparator<? super Order> other) {
        return Comparator.super.thenComparing(other);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public <U> Comparator<Order> thenComparing(Function<? super Order, ? extends U> keyExtractor, Comparator<? super U> keyComparator) {
        return Comparator.super.thenComparing(keyExtractor, keyComparator);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public <U extends Comparable<? super U>> Comparator<Order> thenComparing(Function<? super Order, ? extends U> keyExtractor) {
        return Comparator.super.thenComparing(keyExtractor);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public Comparator<Order> thenComparingInt(ToIntFunction<? super Order> keyExtractor) {
        return Comparator.super.thenComparingInt(keyExtractor);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public Comparator<Order> thenComparingLong(ToLongFunction<? super Order> keyExtractor) {
        return Comparator.super.thenComparingLong(keyExtractor);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public Comparator<Order> thenComparingDouble(ToDoubleFunction<? super Order> keyExtractor) {
        return Comparator.super.thenComparingDouble(keyExtractor);
    }
}
