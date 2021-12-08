package edu.neu.course.project;

import androidx.appcompat.app.AppCompatActivity;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class DashBoard extends AppCompatActivity {

    private TextView language;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash);
        language = findViewById(R.id.language_id);
        language.setText("Korean");
        //NavHostFragment navHostFragment = (NavHostFragment)getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView);
        //NavController navController = navHostFragment.getNavController();
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.homeScreen, R.id.social, R.id.settings)
                .build();
        NavController navController = Navigation.findNavController(this,R.id.fragmentContainerView);
        NavigationUI.setupActionBarWithNavController(this,navController,appBarConfiguration);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);
    }
//
//    public void ClickMenu(View view) {
//
//        drawerOpen(dlayout);
//    }
//
//    private void drawerOpen(DrawerLayout dlayout) {
//
//        dlayout.openDrawer(GravityCompat.START);
//    }
//
//    public void clickLogo(View v) {
//        drawerClose(dlayout);
//    }
////
////    private void drawerClose(DrawerLayout dlayout) {
////
////        if (dlayout.isDrawerOpen(GravityCompat.START)) {
////            dlayout.closeDrawer(GravityCompat.START);
////        }
////    }

//    public void clickHome(View v) {
//
//        recreate();
//
//    }
//
//    public void clickDashboard(View v) {
//
//
//    }
//
//
//    private void redirectActivity(Activity act, Class aClass) {
//
//        Intent intent = new Intent(act, aClass);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        act.startActivity(intent);
//    }

    public void clickProgress(View v) {

        MyProgressDialogFragment fragment_progress = new MyProgressDialogFragment();
        fragment_progress.show(getSupportFragmentManager(), "MyProgressFragment");

    }

    public void clickLogout(View v) {

        logout(this);
    }

    private void logout(Activity activity) {

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("logout");
        builder.setMessage("Are you sure you want to logout?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                activity.finishAffinity();
                System.exit(0);
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                dialogInterface.dismiss();

            }
        });
        builder.show();
    }

}














































