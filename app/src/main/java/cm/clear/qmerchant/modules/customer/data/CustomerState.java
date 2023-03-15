package cm.clear.qmerchant.modules.customer.data;

import cm.clear.qmerchant.common.listviewmanagement.IItemState;

public class CustomerState implements IItemState {
    private int orderCount = 0;
    private int bookingCount = 0;

    public CustomerState() {
    }

    public CustomerState(int orderCount, int bookingCount) {
        this.orderCount = orderCount;
        this.bookingCount = bookingCount;
    }

    public int getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(int orderCount) {
        this.orderCount = orderCount;
    }

    public int getBookingCount() {
        return bookingCount;
    }

    public void setBookingCount(int bookingCount) {
        this.bookingCount = bookingCount;
    }

    @Override
    public String toString() {
        return "CustomerState{" +
                "orderCount=" + orderCount +
                ", bookingCount=" + bookingCount +
                '}';
    }
}
