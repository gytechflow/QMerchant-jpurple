package cm.clear.qmerchant.modules.settings.user;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import cm.clear.qmerchant.R;
import cm.clear.qmerchant.databinding.FragmentUserFragmentBinding;
import cm.clear.qmerchant.interfaces.ResponseListener;
import cm.clear.qmerchant.manager.CallAndCallback;
import cm.clear.qmerchant.manager.RequestManager;
import cm.clear.qmerchant.models.Mail;
import cm.clear.qmerchant.modules.settings.notifications.editMail.EditMailFragment;
import cm.clear.qmerchant.remote.ApiUtil;
import cm.clear.qmerchant.requestsHandler.QCallback;
import retrofit2.Call;
import retrofit2.Response;


public class User_fragment extends Fragment implements ResponseListener {
    private FragmentUserFragmentBinding binding;

    static String qdilla_opening_hours= "QDILLA_OPENING_HOURS";
    static String qdilla_diplayed_news="QDILLA_DISPLAYED_NEWS";

    private String labelOpeningHours = "<html><p>No Data<p></html>";
    private String labeldisplayedNews = "<html><p>No Data<p></html>";

    private String topicOpeningHours = "<html><p>No Data<p></html>";
    private String topicDisplayedNews = "<html><p>No Data<p></html>";

    private String OpeningTemplate = "<html><p>No Data<p></html>";
    private String NewsTemplate = "<html><p>No Data<p></html>";






    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentUserFragmentBinding.inflate(inflater,container,false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getDisplayedNewsTemplate();
        getOpeningHoursTemplate();
//        editEmail(OpeningTemplate,labelOpeningHours,topicOpeningHours,binding.BtnEditOpeningHours);
//        editEmail(NewsTemplate,labeldisplayedNews,topicDisplayedNews,binding.BtnEditDisplayedNews);
    }

    public void editEmail(String content, String label, String topic, Button btn) {
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
                        .navigate(R.id.action_userfragment_to_editMailFragment2, bundle);
            }
        };
        btn.setOnClickListener(editListenerNewElement);
    }

    public void getOpeningHoursTemplate(){
        Call<Mail> stringCall = ApiUtil.getSetupService().getMail(qdilla_opening_hours);
        QCallback<Mail> qCallback = new QCallback<Mail>() {
            @Override
            public void responseSuccessful(Call<Mail> call, Response<Mail> response) {
                labelOpeningHours = response.body().getLabel();
                topicOpeningHours = response.body().getTopic();
                OpeningTemplate = response.body().getContent();
                Log.d("KJ7", "responseSuccessful: Opening Hours");
                editEmail(OpeningTemplate,labelOpeningHours,topicOpeningHours,binding.BtnEditOpeningHours);

            }

            @Override
            public void requestFailure(Call<Mail> call, Throwable t) {
                Log.d("KJ7", "requestFailure: ");

            }

            @Override
            public void responseUnsuccessful(Call<Mail> call, Response<Mail> response) {
                Log.d("KJ7", "responseUnsuccessful: ");
            }
        };
        RequestManager.getInstance().addNewCall(this ,new CallAndCallback(stringCall, qCallback));
    }

    public void getDisplayedNewsTemplate(){
        Call<Mail> stringCall = ApiUtil.getSetupService().getMail(qdilla_diplayed_news);
        QCallback<Mail> qCallback = new QCallback<Mail>() {
            @Override
            public void responseSuccessful(Call<Mail> call, Response<Mail> response) {
                labeldisplayedNews = response.body().getLabel();
                topicDisplayedNews = response.body().getTopic();
                NewsTemplate = response.body().getContent();
                Log.d("KJ7", "responseSuccessful: News Template ");
                editEmail(NewsTemplate,labeldisplayedNews,topicDisplayedNews,binding.BtnEditDisplayedNews);


            }

            @Override
            public void requestFailure(Call<Mail> call, Throwable t) {
                Log.d("KJ7", "requestFailure: ");
            }

            @Override
            public void responseUnsuccessful(Call<Mail> call, Response<Mail> response) {
                Log.d("KJ7", "responseUnsuccessful: ");
            }
        };
        RequestManager.getInstance().addNewCall(this ,new CallAndCallback(stringCall, qCallback));
    }

    @Override
    public void reloadRequest() {

    }
}