package cm.clear.qmerchant.dashboard;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.appbar.MaterialToolbar;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Timer;

import cm.clear.qmerchant.R;
import cm.clear.qmerchant.database.QDatabase;
import cm.clear.qmerchant.databinding.ActivityDashboardBinding;
import cm.clear.qmerchant.manager.CheckConnectionTask;
import cm.clear.qmerchant.notificationService.ServiceRegister;
import cm.clear.qmerchant.notificationService.StartServiceReceiver;

public class DashboardActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityDashboardBinding binding;
    private MainViewModel mainViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityDashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        QDatabase.setContext(this);
        startOrNotifyService(this);

        // todo replace with changing timezones on display to Europe/Berlin
        //TimeZone.setDefault( TimeZone.getTimeZone("UTC"));

        Timer timer = new Timer();
        timer.schedule(new CheckConnectionTask(this), 0, 2000);
        if (!getResources().getBoolean(R.bool.isTablet))
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);



        MaterialToolbar mActionBarToolbar = (MaterialToolbar) findViewById(R.id.main_toolbar);
        //getMenuInflater().inflate(R.menu.top_app_bar, mActionBarToolbar.getMenu());
        setSupportActionBar(mActionBarToolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        //setSupportActionBar(binding.mainToolbar);
        if(mActionBarToolbar!=null) {
            Log.d("HOSIRUS", "HERE WE GO WITH THE RIGHT GUY.");
        }
        else {
            Log.d("HOSIRUS", "THE GUY IS NULL");
        }

        mActionBarToolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
            }
        });

        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_dashboard);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);


        String today = new SimpleDateFormat("dd-MM-yyyy").format(Calendar.getInstance().getTime());
        //TmsConverter.
        binding.textClockDate.setText(today);

        binding.enableOrdersSwitch.setUseMaterialThemeColors(true);
        binding.enableOrdersSwitch.setOnCheckedChangeListener(
                new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

//                        binding.enableOrdersSwitch.setChecked(isChecked);
//                        binding.enableOrdersSwitchGrid.setBackgroundColor(isChecked?getResources().getColor(R.color.blue_transparent):getResources().getColor(R.color.red_transparent));
//                        binding.enableOrdersSwitch.setText(isChecked?getResources().getString(R.string.orders_title_on):getResources().getString(R.string.orders_title_off));

                    }
                }
        );

        mainViewModel.getFabVisible().observe(this, fabVisibility -> {
            if (fabVisibility)
                binding.fab.setVisibility(View.VISIBLE);
            else
                binding.fab.setVisibility(View.GONE);
        });

        mainViewModel.getFabAction().observe(this, fabAction -> {
            binding.fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int i =  fabAction.getNavigation();
                    if (i!=0)
                        navController.navigate(i);
                }
            });
        });



        mActionBarToolbar.setNavigationOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {

                        Log.d("HOSIRUS", "I'm coming setNavigationOnClickListener "+v.getId());

                        switch ( v.getId() ){
                            case R.id.orders_enabled:
//                                Toast.makeText(getApplicationContext(),"Toogle orders status", Toast.LENGTH_LONG );
                                break;

                            case R.id.reservations_enabled:
//                                Toast.makeText(getApplicationContext(), "Toogle reservations status", Toast.LENGTH_LONG);
                                break;

                            case -1:{
                                NavController navController = Navigation.findNavController(DashboardActivity.this, R.id.nav_host_fragment_content_dashboard);
                                navController.popBackStack();

                            }break;

                            default:
//                                Toast.makeText(getApplicationContext(), "others options to fix", Toast.LENGTH_LONG);
                                break;
                        }
                    }
                }
        );

        mActionBarToolbar.setOnMenuItemClickListener(
                new Toolbar.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        Log.d("HOSIRUS", "I'm coming setOnMenuItemClickListener "+item.getItemId());

                        switch ( item.getItemId() ){
                            case R.id.orders_enabled:
//                                Toast.makeText(getApplicationContext(),"Toogle orders status", Toast.LENGTH_LONG );
                                break;

                            case R.id.reservations_enabled:
//                                Toast.makeText(getApplicationContext(), "Toogle reservations status", Toast.LENGTH_LONG);
                                break;

                            default:
//                                Toast.makeText(getApplicationContext(), "others options to fix", Toast.LENGTH_LONG);
                                break;
                        }

                        return true;
                    }
                }
        );
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        //getMenuInflater().inflate(R.menu.top_app_bar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_dashboard);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Log.d("HOSIRUS", "I'm coming onOptionsItemSelected "+item.getItemId());

        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;

            case R.id.orders_enabled:
                Log.d("HOSIRUS", "TOOGLE ORDERS");
//                Toast.makeText(getApplicationContext(),"Toogle orders status", Toast.LENGTH_LONG );
                break;

            case R.id.reservations_enabled:
                Log.d("HOSIRUS", "TOOGLE RESERVATIONS");
//                Toast.makeText(getApplicationContext(), "Toogle reservations status", Toast.LENGTH_LONG);
                break;

            default:
                Log.d("HOSIRUS", "TOOGLE OTHERS BUTTONS");
//                Toast.makeText(getApplicationContext(), "others options to fix", Toast.LENGTH_LONG);
                break;

        }
        return true;
    }

    public static void startOrNotifyService(@NonNull Context context) {
        //Log.d(TAG, "startOrNotifyService() called with: context = [" + context + "]");
        /*if (!isServiceRunning(context)) {
            Log.d(TAG, "startOrNotifyService() not running");
            Intent serviceIntent = new Intent(context, TransactionUploadService.class);
            context.startService(serviceIntent);
        } else {
            Log.d(TAG, "startOrNotifyService() running");
        }*/

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            StartServiceReceiver.scheduleJob(context);
        } else {
            ServiceRegister serviceRegister = new ServiceRegister();
            serviceRegister.launchService(context);
        }
    }
}