package cm.clear.qmerchant.modules.settings.notifications.editMail;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;


import com.chinalwb.are.AREditor;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import cm.clear.qmerchant.R;
import cm.clear.qmerchant.databinding.FragmentEditMailBinding;
import cm.clear.qmerchant.manager.CallAndCallback;
import cm.clear.qmerchant.manager.RequestManager;
import cm.clear.qmerchant.interfaces.ResponseListener;
import cm.clear.qmerchant.models.Mail;
import cm.clear.qmerchant.remote.ApiUtil;
import cm.clear.qmerchant.requestsHandler.QCallback;
import retrofit2.Call;
import retrofit2.Response;


public class EditMailFragment extends Fragment {
    private FragmentEditMailBinding binding;
    private String email;
    private String label;
    private String topic;
    private String preview;

    public EditMailFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        email = getArguments().getString("Content");
        label = getArguments().getString("Label");
        topic = getArguments().getString("Topic");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentEditMailBinding.inflate(inflater, container, false);
        binding.visual.setBackgroundColor(Color.parseColor("#8c9eff"));
        binding.visual.setTextColor(Color.parseColor("#ffffff"));
        if (label.equalsIgnoreCase("<html><p>No Data<p></html>")) {
            binding.apply.setVisibility(View.GONE);
        }

        // Inflate the layout for this fragment
        if (getArguments() != null) {

            binding.textField.setText(email);
            binding.topic.setText(topic);
            binding.label.setText(label);
            WebView webView = binding.homeweb;
            webView.loadDataWithBaseURL(null, binding.textField.getText().toString(), "text/html", "utf-8", null);
        } else {
            Log.d("KJ7", "onCreate: No Data send ");
        }
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button btn = binding.apply;
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("KJ7", "Apply: ");
                new MaterialAlertDialogBuilder(getActivity())


                        .setMessage(R.string.apply_message )
                        .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // Respond to positive button press
                                String mail = binding.textField.getText().toString();
                                String topicMail = binding.topic.getText().toString();
                                email=mail;
                                topic=topicMail;

                                Mail templateMail = new Mail();
                                templateMail.setContent(email);
                                templateMail.setTopic(topic);
                                templateMail.setLabel(label);

                                Call<Void> stringCall = ApiUtil.getSetupService().postMail(templateMail);
                                QCallback<Void> qCallback = new QCallback<Void>() {
                                    @Override
                                    public void responseSuccessful(Call<Void> call, Response<Void> response) {
                                        Log.d("KJ7", "responseSuccessful: ");
                                        Toast toast = Toast.makeText(getContext(),R.string.updateSucessfull, Toast.LENGTH_LONG);
                                        toast.setGravity( Gravity.CENTER,20,30);
                                        toast.show();

                                    }

                                    @Override
                                    public void requestFailure(Call<Void> call, Throwable t) {
                                        Log.d("KJ7", "requestFailure: ", t);
                                        Log.d("KJ7", "responseSuccessful: ");
                                        Toast toast = Toast.makeText(getContext(),R.string.updateUnSucessfull, Toast.LENGTH_LONG);
                                        toast.setGravity( Gravity.CENTER,20,30);
                                        toast.show();
                                    }

                                    @Override
                                    public void responseUnsuccessful(Call<Void> call, Response<Void> response) {
                                        Log.d("KJ7", "responseUnsuccessful: ");
                                        Log.d("KJ7", "responseSuccessful: ");
                                        Toast toast = Toast.makeText(getContext(),R.string.updateFaillure, Toast.LENGTH_LONG);
                                        toast.setGravity( Gravity.CENTER,20,30);
                                        toast.show();
                                    }
                                };
                                RequestManager.getInstance().addTempCall(new CallAndCallback(stringCall, qCallback));


                            }
                        }).show();

            }
        });

        binding.source.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.source.setBackgroundColor(Color.parseColor("#8c9eff"));
                binding.source.setTextColor(Color.parseColor("#ffffff"));
                binding.visual.setBackgroundColor(Color.parseColor("#ffffff"));
                binding.visual.setTextColor(Color.parseColor("#8c9eff"));
                binding.PreviewLayout.setVisibility(View.GONE);
                binding.VisualLayout.setVisibility(View.GONE);
                binding.sourceLayout.setVisibility(View.VISIBLE);
                binding.apply.setVisibility(View.GONE);
            }
        });

//        binding.preview.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                binding.PreviewLayout.setVisibility(View.VISIBLE);
//                binding.VisualLayout.setVisibility(View.GONE);
//                binding.sourceLayout.setVisibility(View.GONE);
//                binding.apply.setVisibility(View.GONE);
//            }
//        });

        binding.visual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = binding.textField.getText().toString();
                Log.d("KJ7", "onClick: " + email);
                WebView webView = binding.homeweb;
                webView.loadDataWithBaseURL(null, email, "text/html", "utf-8", null);
                binding.source.setBackgroundColor(Color.parseColor("#ffffff"));
                binding.source.setTextColor(Color.parseColor("#8c9eff"));

                binding.visual.setBackgroundColor(Color.parseColor("#8c9eff"));
                binding.visual.setTextColor(Color.parseColor("#ffffff"));
                binding.PreviewLayout.setVisibility(View.GONE);
                binding.sourceLayout.setVisibility(View.GONE);
                binding.VisualLayout.setVisibility(View.VISIBLE);
                binding.apply.setVisibility(View.VISIBLE);


            }
        });


    }
}