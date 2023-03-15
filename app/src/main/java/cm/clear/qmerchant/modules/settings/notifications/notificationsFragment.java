package cm.clear.qmerchant.modules.settings.notifications;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.chinalwb.are.AREditText;
import com.chinalwb.are.styles.toolbar.IARE_Toolbar;

import cm.clear.qmerchant.R;
import cm.clear.qmerchant.databinding.FragmentNotificationsBinding;
import cm.clear.qmerchant.manager.CallAndCallback;
import cm.clear.qmerchant.manager.RequestManager;
import cm.clear.qmerchant.models.Mail;
import cm.clear.qmerchant.modules.settings.notifications.editMail.EditMailFragment;
import cm.clear.qmerchant.remote.ApiUtil;
import cm.clear.qmerchant.requestsHandler.QCallback;
import retrofit2.Call;
import retrofit2.Response;


public class notificationsFragment extends Fragment{
    private FragmentNotificationsBinding binding;
    private AREditText arEditText;
    private IARE_Toolbar iare_toolbar;

    static String order_Customer_on_Order = "ORDER_CUSTOMER_NOTIFICATION_ON_ORDER";
    static String order_Customer_on_Conf = "ORDER_CUSTOMER_NOTIFICATION_ON_CONFIRMATION";
    static String order_Customer_on_cancelling = "ORDER_CUSTOMER_NOTIFICATION_ON_CANCELLING";
    static String order_Customer_on_delivery = "ORDER_CUSTOMER_NOTIFICATION_ON_DELIVERY";
    static String booking_Customer_on_Booking = "BOOKING_CUSTOMER_NOTIFICATION_ON_BOOKING";
    static String booking_Customer_on_absence = "BOOKING_CUSTOMER_NOTIFICATION_ON_ABSENCE";
    static String booking_Customer_on_confirmation = "BOOKING_CUSTOMER_NOTIFICATION_ON_CONFIRMATION";
    static String booking_Customer_on_cancelling = "BOOKING_CUSTOMER_NOTIFICATION_ON_CANCELLING";
    static String booking_Customer_on_presence = "BOOKING_CUSTOMER_NOTIFICATION_ON_PRESENCE";

    private String labelNewOrder = "<html><p>No Data<p></html>";
    private String labelOrderConf = "<html><p>No Data<p></html>";
    private String labelOrderDelivey = "<html><p>No Data<p></html>";
    private String labelOrderCancel = "<html><p>No Data<p></html>";

    private String labelNewBooking = "<html><p>No Data<p></html>";
    private String labelBookingConf = "<html><p>No Data<p></html>";
    private String labelBookingCompleted = "<html><p>No Data<p></html>";
    private String labelBookingCancel = "<html><p>No Data<p></html>";
    private String labelBookingAbsence = "<html><p>No Data<p></html>";

    private String newOrderStatus;
    private String orderConfStatus;
    private String orderDelStatus;
    private String orderCancelStatus;

    private String newBookingStatus;
    private String bookingConfStatus;
    private String bookingCancelStatus;
    private String bookingCompletedStatus;
    private String bookingAbsenceStatus;


    private String topicNewOrder = "<html><p>No Data<p></html>";
    private String topicOrderConf = "<html><p>No Data<p></html>";
    private String topicOrderdelivery = "<html><p>No Data<p></html>";
    private String topicOrderCancel = "<html><p>No Data<p></html>";

    private String newOrderMail = "<html><p>No Data<p></html>";
    private String orderConf = "<html><p>No Data<p></html>";
    private String orderDelivered = "<html><p>No Data<p></html>";
    private String orderCancelling = "<html><p>No Data<p></html>";

    private String topicNewBooking = "<html><p>No Data<p></html>";
    private String topicBookingConf = "<html><p>No Data<p></html>";
    private String topicBookingCompleted = "<html><p>No Data<p></html>";
    private String topicBookingCancel = "<html><p>No Data<p></html>";
    private String topicBookingAbsence = "<html><p>No Data<p></html>";

    private String newBookingMail = "<html><p>No Data<p></html>";
    private String bookingConfMail = "<html><p>No Data<p></html>";
    private String bookingCompletedMail = "<html><p>No Data<p></html>";
    private String bookingCancelMail = "<html><p>No Data<p></html>";
    private String bookingAbsenceMail = "<html><p>No Data<p></html>";

    public notificationsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        getOnNewOrderStatus();
        getOnOrderConfStatus();
        getOnOrderCancelStatus();
        getOnOrderDelStatus();

        getOnBookingabscenceStatus();
        getOnNewBookingStatus();
        getOnBookingBookingCancelStatus();
        getOnBookingCompletedStatus();
        getOnBookingConfStatus();



        getNewOrderEMail();
        getCancelOrder();
        getConfOrderMail();
        getDeliveryOrder();

        getBookingAbscenceEmail();
        getBookingCompletedEmail();
        getBookingCancelEmail();
        getBookingConfEmail();
        getNewBookingemail();

        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SwitchAction();

//          Order
    }

    public void editEmail(String content, String label, String topic, LinearLayout layout) {
        View.OnClickListener editListenerNewElement = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("Content", content);
                bundle.putString("Label", label);
                bundle.putString("Topic", topic);

                EditMailFragment fragment = new EditMailFragment();
                fragment.setArguments(bundle);

                Navigation.findNavController(view)
                        .navigate(R.id.action_notificationsFragment_to_editMailFragment22, bundle);
            }
        };
        layout.setOnClickListener(editListenerNewElement);
    }

    public void setStatus(String constsname, String constvalue) {
        Call<Void> stringCall = ApiUtil.getSetupService().postStatus(constsname, constvalue);
        QCallback<Void> qCallback = new QCallback<Void>() {
            @Override
            public void responseSuccessful(Call<Void> call, Response<Void> response) {
                Log.d("KJ7", "responseSuccessful : ");
                Toast toast = Toast.makeText(getContext(), "Update Successfull", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 20, 30);
                toast.show();
            }

            @Override
            public void requestFailure(Call<Void> call, Throwable t) {
                Toast toast = Toast.makeText(getContext(), "Update Failled", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 20, 30);
                toast.show();
//                getOnNewOrderStatus();

            }

            @Override
            public void responseUnsuccessful(Call<Void> call, Response<Void> response) {
                Toast toast = Toast.makeText(getContext(), "Update Failled", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 20, 30);
                toast.show();
//                getOnNewOrderStatus();
            }
        };
        RequestManager.getInstance().addTempCall(new CallAndCallback(stringCall, qCallback));

        Log.d("TAG", "onClick: " + newOrderStatus);

    }


    public void SwitchAction() {
        //      Order switch actions
        binding.switchNewOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!binding.switchNewOrder.isChecked()) {
                    newOrderStatus = "0";
                    setStatus(order_Customer_on_Order, newOrderStatus);
                    Log.d("TAG", "onClick: " + newOrderStatus);

                } else {
                    newOrderStatus = "1";
                    setStatus(order_Customer_on_Order, newOrderStatus);
                    Log.d("TAG", "onClick: " + newOrderStatus);

                }
            }
        });
        binding.switchOrderConf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!binding.switchOrderConf.isChecked()) {
                    orderConfStatus = "0";
                    setStatus(order_Customer_on_Conf, orderConfStatus);
                    Log.d("TAG", "onClick: " + orderConfStatus);
                } else {
                    orderConfStatus = "1";
                    setStatus(order_Customer_on_Conf, orderConfStatus);
                    Log.d("TAG", "onClick: " + orderConfStatus);
                }
            }
        });

        binding.switchEndOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!binding.switchEndOrder.isChecked()) {
                    orderDelStatus = "0";
                    setStatus(order_Customer_on_delivery, orderDelStatus);
                    Log.d("TAG", "onClick: " + orderConfStatus);
                } else {
                    orderDelStatus = "1";
                    setStatus(order_Customer_on_delivery, orderDelStatus);
                    Log.d("TAG", "onClick: " + orderDelStatus);
                }
            }
        });

        binding.SwitchorderCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!binding.SwitchorderCancel.isChecked()) {

                    orderCancelStatus = "0";
                    setStatus(order_Customer_on_cancelling, orderCancelStatus);
                    Log.d("TAG", "onClick: " + orderCancelStatus);
                } else {
                    orderCancelStatus = "1";
                    setStatus(order_Customer_on_cancelling, orderCancelStatus);
                    Log.d("TAG", "onClick: " + orderCancelStatus);
                }
            }
        });

//        Booking Switch action

        binding.switchNewBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!binding.switchNewBooking.isChecked()) {
                    newBookingStatus = "0";
                    setStatus(booking_Customer_on_Booking, newBookingStatus);
                    Log.d("TAG", "onClick: " + newBookingStatus);
                } else {
                    newBookingStatus = "1";
                    setStatus(booking_Customer_on_Booking, newBookingStatus);
                    Log.d("TAG", "onClick: " + newBookingStatus);
                }
            }
        });

        binding.switchBookingConf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!binding.switchBookingConf.isChecked()) {
                    bookingConfStatus = "0";
                    setStatus(booking_Customer_on_confirmation, bookingConfStatus);
                    Log.d("TAG", "onClick: " + bookingConfStatus);
                } else {
                    bookingConfStatus = "1";
                    setStatus(booking_Customer_on_confirmation, bookingConfStatus);
                    Log.d("TAG", "onClick: " + bookingConfStatus);
                }
            }
        });

        binding.switchCancelBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!binding.switchCancelBooking.isChecked()) {
                    bookingCancelStatus = "0";
                    setStatus(booking_Customer_on_cancelling, bookingCancelStatus);
                    Log.d("TAG", "onClick: " + bookingCancelStatus);
                } else {
                    bookingCancelStatus = "1";
                    setStatus(booking_Customer_on_cancelling, bookingCancelStatus);
                    Log.d("TAG", "onClick: " + bookingCancelStatus);
                }
            }
        });

        binding.SwitchBookingCompleted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!binding.SwitchBookingCompleted.isChecked()) {
                    bookingCompletedStatus = "0";
                    setStatus(booking_Customer_on_presence, bookingCompletedStatus);
                    Log.d("TAG", "onClick: " + bookingCompletedStatus);
                } else {
                    bookingCompletedStatus = "1";
                    setStatus(booking_Customer_on_presence, bookingCompletedStatus);
                    Log.d("TAG", "onClick: " + bookingCompletedStatus);
                }
            }
        });

        binding.SwitchBookingAbsence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!binding.SwitchBookingAbsence.isChecked()) {
                    bookingAbsenceStatus = "0";
                    setStatus(booking_Customer_on_absence, bookingAbsenceStatus);
                    Log.d("TAG", "onClick: " + bookingAbsenceStatus);
                } else {
                    bookingAbsenceStatus = "1";
                    setStatus(booking_Customer_on_absence, bookingAbsenceStatus);
                    Log.d("TAG", "onClick: " + bookingAbsenceStatus);
                }
            }
        });
    }

    //  Get OrderStatus
    public void getStatusOne(String constname, String constvalue, Switch element) {
        Call<String> stringCall = ApiUtil.getSetupService().getStatus(constname);
        QCallback<String> qCallback = new QCallback<String>() {
            @Override
            public void responseSuccessful(Call<String> call, Response<String> response) {

                Log.d("TAG", "orderStatus is: " + constvalue);
                if (constvalue.equalsIgnoreCase("1")) {

                    Log.e("KJ7", "responseSuccessful: " + response.body());
                    element.setChecked(true);
                } else {
                    element.setChecked(false);
                }
            }

            @Override
            public void requestFailure(Call<String> call, Throwable t) {

            }

            @Override
            public void responseUnsuccessful(Call<String> call, Response<String> response) {

            }
        };
        RequestManager.getInstance().addTempCall(new CallAndCallback(stringCall, qCallback));
    }


    public void getOnNewOrderStatus() {
        Call<String> stringCall = ApiUtil.getSetupService().getStatus(order_Customer_on_Order);
        QCallback<String> qCallback = new QCallback<String>() {
            @Override
            public void responseSuccessful(Call<String> call, Response<String> response) {
                newOrderStatus = response.body();
                    binding.switchNewOrder.setVisibility(View.VISIBLE);

                Log.d("TAG", "orderStatus is: " + newOrderStatus);
                if (newOrderStatus.equalsIgnoreCase("1")) {
                    Log.e("KJ7", "responseSuccessful: " + response.body());
                    binding.switchNewOrder.setChecked(true);
                } else {
                    binding.switchNewOrder.setChecked(false);
                }
            }

            @Override
            public void requestFailure(Call<String> call, Throwable t) {

            }

            @Override
            public void responseUnsuccessful(Call<String> call, Response<String> response) {

            }
        };
        RequestManager.getInstance().addTempCall(new CallAndCallback(stringCall, qCallback));

    }

    public void getOnOrderConfStatus() {
        Call<String> stringCall = ApiUtil.getSetupService().getStatus(order_Customer_on_Conf);
        QCallback<String> qCallback = new QCallback<String>() {

            @Override
            public void responseSuccessful(Call<String> call, Response<String> response) {
                orderConfStatus = response.body();
                binding.switchOrderConf.setVisibility(View.VISIBLE);
                if (orderConfStatus.equalsIgnoreCase("1")) {
                    Log.e("KJ7", "responseSuccessful: " + response.body());

                    binding.switchOrderConf.setChecked(true);
                } else {
                    binding.switchOrderConf.setChecked(false);
                }
            }

            @Override
            public void requestFailure(Call<String> call, Throwable t) {

            }

            @Override
            public void responseUnsuccessful(Call<String> call, Response<String> response) {

            }
        };
        RequestManager.getInstance().addTempCall(new CallAndCallback(stringCall, qCallback));
    }

    public void getOnOrderCancelStatus() {
        Call<String> stringCall = ApiUtil.getSetupService().getStatus(order_Customer_on_cancelling);
        QCallback<String> qCallback = new QCallback<String>() {
            @Override
            public void responseSuccessful(Call<String> call, Response<String> response) {
                binding.SwitchorderCancel.setVisibility(View.VISIBLE);

                orderCancelStatus = response.body();
                if (orderCancelStatus.equalsIgnoreCase("1")) {
                    Log.e("KJ7", "responseSuccessful: " + response.body());
                    binding.SwitchorderCancel.setChecked(true);
                } else {
                    binding.SwitchorderCancel.setChecked(false);
                }
            }

            @Override
            public void requestFailure(Call<String> call, Throwable t) {

            }

            @Override
            public void responseUnsuccessful(Call<String> call, Response<String> response) {

            }
        };
        RequestManager.getInstance().addTempCall(new CallAndCallback(stringCall, qCallback));
    }

    public void getOnOrderDelStatus() {
        Call<String> stringCall = ApiUtil.getSetupService().getStatus(order_Customer_on_delivery);
        QCallback<String> qCallback = new QCallback<String>() {
            @Override
            public void responseSuccessful(Call<String> call, Response<String> response) {
                binding.switchEndOrder.setVisibility(View.VISIBLE);

                orderDelStatus = response.body();
                if (orderDelStatus.equalsIgnoreCase("1")) {
                    Log.e("KJ7", "responseSuccessful: " + response.body());
                    binding.switchEndOrder.setChecked(true);
                } else {
                    binding.switchEndOrder.setChecked(false);
                }
            }

            @Override
            public void requestFailure(Call<String> call, Throwable t) {

            }

            @Override
            public void responseUnsuccessful(Call<String> call, Response<String> response) {

            }
        };
        RequestManager.getInstance().addTempCall(new CallAndCallback(stringCall, qCallback));
    }
//    Get Booking Status

    public void getOnNewBookingStatus() {
        Call<String> stringCall = ApiUtil.getSetupService().getStatus(booking_Customer_on_Booking);
        QCallback<String> qCallback = new QCallback<String>() {
            @Override
            public void responseSuccessful(Call<String> call, Response<String> response) {
                binding.switchNewBooking.setVisibility(View.VISIBLE);
                newBookingStatus = response.body();
                if (newBookingStatus.equalsIgnoreCase("1")) {
                    Log.e("KJ7", "responseSuccessful: " + response.body());
                    binding.switchNewBooking.setChecked(true);
                } else {
                    binding.switchNewBooking.setChecked(false);
                }
            }

            @Override
            public void requestFailure(Call<String> call, Throwable t) {

            }

            @Override
            public void responseUnsuccessful(Call<String> call, Response<String> response) {

            }
        };
        RequestManager.getInstance().addTempCall(new CallAndCallback(stringCall, qCallback));

    }

    public void getOnBookingConfStatus() {
        Call<String> stringCall = ApiUtil.getSetupService().getStatus(booking_Customer_on_confirmation);
        QCallback<String> qCallback = new QCallback<String>() {
            @Override
            public void responseSuccessful(Call<String> call, Response<String> response) {
                binding.switchBookingConf.setVisibility(View.VISIBLE);
                bookingConfStatus = response.body();
                if (bookingConfStatus.equalsIgnoreCase("1")) {
                    Log.e("KJ7", "responseSuccessful: " + response.body());
                    binding.switchBookingConf.setChecked(true);
                } else {
                    binding.switchBookingConf.setChecked(false);
                }
            }

            @Override
            public void requestFailure(Call<String> call, Throwable t) {

            }

            @Override
            public void responseUnsuccessful(Call<String> call, Response<String> response) {

            }
        };
        RequestManager.getInstance().addTempCall(new CallAndCallback(stringCall, qCallback));
    }

    public void getOnBookingBookingCancelStatus() {
        Call<String> stringCall = ApiUtil.getSetupService().getStatus(booking_Customer_on_cancelling);
        QCallback<String> qCallback = new QCallback<String>() {
            @Override
            public void responseSuccessful(Call<String> call, Response<String> response) {
                binding.switchCancelBooking.setVisibility(View.VISIBLE);

                bookingCancelStatus = response.body();
                if (bookingCancelStatus.equalsIgnoreCase("1")) {
                    Log.e("KJ7", "responseSuccessful: " + response.body());
                    binding.switchCancelBooking.setChecked(true);
                } else {
                    binding.switchCancelBooking.setChecked(false);
                }
            }

            @Override
            public void requestFailure(Call<String> call, Throwable t) {

            }

            @Override
            public void responseUnsuccessful(Call<String> call, Response<String> response) {

            }
        };
        RequestManager.getInstance().addTempCall(new CallAndCallback(stringCall, qCallback));
    }

    public void getOnBookingCompletedStatus() {
        Call<String> stringCall = ApiUtil.getSetupService().getStatus(booking_Customer_on_presence);
        QCallback<String> qCallback = new QCallback<String>() {
            @Override
            public void responseSuccessful(Call<String> call, Response<String> response) {
                binding.SwitchBookingCompleted.setVisibility(View.VISIBLE);
                bookingCompletedStatus = response.body();
                if (bookingCompletedStatus.equalsIgnoreCase("1")) {
                    Log.e("KJ7", "responseSuccessful: " + response.body());
                    binding.SwitchBookingCompleted.setChecked(true);
                } else {
                    binding.SwitchBookingCompleted.setChecked(false);
                }
            }

            @Override
            public void requestFailure(Call<String> call, Throwable t) {

            }

            @Override
            public void responseUnsuccessful(Call<String> call, Response<String> response) {

            }
        };
        RequestManager.getInstance().addTempCall(new CallAndCallback(stringCall, qCallback));
    }

    public void getOnBookingabscenceStatus() {
        Call<String> stringCall = ApiUtil.getSetupService().getStatus(booking_Customer_on_absence);
        QCallback<String> qCallback = new QCallback<String>() {
            @Override
            public void responseSuccessful(Call<String> call, Response<String> response) {
                binding.SwitchBookingAbsence.setVisibility(View.VISIBLE);
                bookingAbsenceStatus = response.body();
                if (bookingAbsenceStatus.equalsIgnoreCase("1")) {
                    Log.e("KJ7", "responseSuccessful: " + response.body());
                    binding.SwitchBookingAbsence.setChecked(true);
                } else {
                    binding.SwitchBookingAbsence.setChecked(false);
                }
            }

            @Override
            public void requestFailure(Call<String> call, Throwable t) {

            }

            @Override
            public void responseUnsuccessful(Call<String> call, Response<String> response) {

            }
        };
        RequestManager.getInstance().addTempCall(new CallAndCallback(stringCall, qCallback));
    }

    //Get Email Template for Order
    public void getNewOrderEMail() {
        Call<Mail> stringCall = ApiUtil.getSetupService().getMail(order_Customer_on_Order);
        QCallback<Mail> qCallback = new QCallback<Mail>() {
            @Override
            public void responseSuccessful(Call<Mail> call, Response<Mail> response) {
                newOrderMail = response.body().getContent();
                labelNewOrder = response.body().getLabel();
                topicNewOrder = response.body().getTopic();
                editEmail(newOrderMail, labelNewOrder, topicNewOrder, binding.NewOrderLayout);

            }

            @Override
            public void requestFailure(Call<Mail> call, Throwable t) {
                Log.d("KJ7", "requestFailure for getmail: ");
            }

            @Override
            public void responseUnsuccessful(Call<Mail> call, Response<Mail> response) {
                Log.d("KJ7", "responseUnsuccessful:GetMail ");

            }
        };
        RequestManager.getInstance().addTempCall(new CallAndCallback(stringCall, qCallback));
//

    }

    public void getConfOrderMail() {
        Call<Mail> stringCall = ApiUtil.getSetupService().getMail(order_Customer_on_Conf);
        QCallback<Mail> qCallback = new QCallback<Mail>() {
            @Override
            public void responseSuccessful(Call<Mail> call, Response<Mail> response) {
                orderConf = response.body().getContent();
                labelOrderConf = response.body().getLabel();
                topicOrderConf = response.body().getTopic();
                editEmail(orderConf, labelOrderConf, topicOrderConf, binding.OrderConfirmation);

            }

            @Override
            public void requestFailure(Call<Mail> call, Throwable t) {
                Log.d("KJ7", "requestFailure for getmail: ");

            }

            @Override
            public void responseUnsuccessful(Call<Mail> call, Response<Mail> response) {
                Log.d("KJ7", "responseUnsuccessful:GetMail ");
            }
        };
        RequestManager.getInstance().addTempCall(new CallAndCallback(stringCall, qCallback));
    }

    public void getCancelOrder() {

        Call<Mail> stringCall = ApiUtil.getSetupService().getMail(order_Customer_on_cancelling);
        QCallback<Mail> qCallback = new QCallback<Mail>() {
            @Override
            public void responseSuccessful(Call<Mail> call, Response<Mail> response) {
                orderCancelling = response.body().getContent();
                labelOrderCancel = response.body().getLabel();
                topicOrderCancel = response.body().getTopic();
                editEmail(orderCancelling, labelOrderCancel, topicOrderCancel, binding.OrderCancel);

            }

            @Override
            public void requestFailure(Call<Mail> call, Throwable t) {
                Log.d("KJ7", "requestFailure for getmail: ", t);
            }

            @Override
            public void responseUnsuccessful(Call<Mail> call, Response<Mail> response) {
                Log.d("KJ7", "responseUnsuccessful:GetMail ");
            }
        };
        RequestManager.getInstance().addTempCall(new CallAndCallback(stringCall, qCallback));
    }

    public void getDeliveryOrder() {
        Call<Mail> stringCall = ApiUtil.getSetupService().getMail(order_Customer_on_delivery);
        QCallback<Mail> qCallback = new QCallback<Mail>() {
            @Override
            public void responseSuccessful(Call<Mail> call, Response<Mail> response) {
                orderDelivered = response.body().getContent();
                labelOrderDelivey = response.body().getLabel();
                topicOrderdelivery = response.body().getTopic();
                editEmail(orderDelivered, labelOrderDelivey, topicOrderdelivery, binding.OrderDelivery);

            }

            @Override
            public void requestFailure(Call<Mail> call, Throwable t) {
                Log.d("KJ7", "requestFailure for getmail Delivery: ");


            }

            @Override
            public void responseUnsuccessful(Call<Mail> call, Response<Mail> response) {
                Log.d("KJ7", "responseUnsuccessful:GetMail ");
            }
        };
        RequestManager.getInstance().addTempCall(new CallAndCallback(stringCall, qCallback));
    }

    //     Get Email Template for Booking
    public void getNewBookingemail() {
        Call<Mail> stringCall = ApiUtil.getSetupService().getMail(booking_Customer_on_Booking);
        QCallback<Mail> qCallback = new QCallback<Mail>() {
            @Override
            public void responseSuccessful(Call<Mail> call, Response<Mail> response) {
                newBookingMail = response.body().getContent();
                labelNewBooking = response.body().getLabel();
                topicNewBooking = response.body().getTopic();
                editEmail(newBookingMail, labelNewBooking, topicNewBooking, binding.NewBookingLayout);

            }

            @Override
            public void requestFailure(Call<Mail> call, Throwable t) {

            }

            @Override
            public void responseUnsuccessful(Call<Mail> call, Response<Mail> response) {

            }
        };
        RequestManager.getInstance().addTempCall(new CallAndCallback(stringCall, qCallback));
    }

    public void getBookingConfEmail() {
        Call<Mail> stringCall = ApiUtil.getSetupService().getMail(booking_Customer_on_confirmation);
        QCallback<Mail> qCallback = new QCallback<Mail>() {
            @Override
            public void responseSuccessful(Call<Mail> call, Response<Mail> response) {
                bookingConfMail = response.body().getContent();
                labelBookingConf = response.body().getLabel();
                topicBookingConf = response.body().getTopic();
                editEmail(bookingConfMail, labelBookingConf, topicBookingConf, binding.BookingConfirmation);

            }

            @Override
            public void requestFailure(Call<Mail> call, Throwable t) {

            }

            @Override
            public void responseUnsuccessful(Call<Mail> call, Response<Mail> response) {

            }
        };
        RequestManager.getInstance().addTempCall(new CallAndCallback(stringCall, qCallback));
    }

    public void getBookingCancelEmail() {
        Call<Mail> stringCall = ApiUtil.getSetupService().getMail(booking_Customer_on_cancelling);
        QCallback<Mail> qCallback = new QCallback<Mail>() {
            @Override
            public void responseSuccessful(Call<Mail> call, Response<Mail> response) {
                bookingCancelMail = response.body().getContent();
                labelBookingCancel = response.body().getLabel();
                topicBookingCancel = response.body().getTopic();
                editEmail(bookingCancelMail, labelBookingCancel, topicBookingCancel, binding.BookingCancelLayout);

            }

            @Override
            public void requestFailure(Call<Mail> call, Throwable t) {

            }

            @Override
            public void responseUnsuccessful(Call<Mail> call, Response<Mail> response) {

            }
        };
        RequestManager.getInstance().addTempCall(new CallAndCallback(stringCall, qCallback));
    }

    public void getBookingCompletedEmail() {
        Call<Mail> stringCall = ApiUtil.getSetupService().getMail(booking_Customer_on_presence);
        QCallback<Mail> qCallback = new QCallback<Mail>() {
            @Override
            public void responseSuccessful(Call<Mail> call, Response<Mail> response) {
                bookingCompletedMail = response.body().getContent();
                labelBookingCompleted = response.body().getLabel();
                topicBookingCompleted = response.body().getTopic();
                editEmail(bookingCompletedMail, labelBookingCompleted, topicBookingCompleted, binding.BookingCompletedLayout);

            }

            @Override
            public void requestFailure(Call<Mail> call, Throwable t) {

            }

            @Override
            public void responseUnsuccessful(Call<Mail> call, Response<Mail> response) {

            }
        };
        RequestManager.getInstance().addTempCall(new CallAndCallback(stringCall, qCallback));
    }

    public void getBookingAbscenceEmail() {
        Call<Mail> stringCall = ApiUtil.getSetupService().getMail(booking_Customer_on_absence);
        QCallback<Mail> qCallback = new QCallback<Mail>() {
            @Override
            public void responseSuccessful(Call<Mail> call, Response<Mail> response) {
                bookingAbsenceMail = response.body().getContent();
                labelBookingAbsence = response.body().getLabel();
                topicBookingAbsence = response.body().getTopic();
                editEmail(bookingAbsenceMail, labelBookingAbsence, topicBookingAbsence, binding.BookingAbsenceLayout);

            }

            @Override
            public void requestFailure(Call<Mail> call, Throwable t) {

            }

            @Override
            public void responseUnsuccessful(Call<Mail> call, Response<Mail> response) {

            }
        };
        RequestManager.getInstance().addTempCall(new CallAndCallback(stringCall, qCallback));
    }


}