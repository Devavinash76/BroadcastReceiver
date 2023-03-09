package dev.avinash.mysms;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.HIDE_OVERLAY_WINDOWS;
import static android.Manifest.permission.READ_SMS;
import static android.Manifest.permission.RECEIVE_SMS;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity{
    private static final int PERMISSION_REQUEST_CODE = 200;
    private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//         Get Current Date Time
        Calendar c = Calendar.getInstance();
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        String CurrentDate = sdf.format(c.getTime());

        try {
//            String CurrentDate= "09/24/2018";
            String FinalDate= "03/10/2022";
            Date date1;
            Date date2;
            SimpleDateFormat dates = new SimpleDateFormat("MM/dd/yyyy");
            date1 = dates.parse(CurrentDate);
            date2 = dates.parse(FinalDate);
            long difference = Math.abs(date1.getTime() - date2.getTime());
            long differenceDates = difference / (24 * 60 * 60 * 1000);
            String dayDifference = Long.toString(differenceDates);
            Toast.makeText(this, "The difference between two dates is" + dayDifference + " days", Toast.LENGTH_SHORT).show();

        } catch (Exception exception) {
            Toast.makeText(this, "Unable to find difference", Toast.LENGTH_SHORT).show();
        }

//        checkPermission();

//        requestPermission();
    }


    private void requestPermission() {

        ActivityCompat.requestPermissions(this, new String[]{RECEIVE_SMS, READ_SMS, HIDE_OVERLAY_WINDOWS}, PERMISSION_REQUEST_CODE);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0) {

                boolean locationAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                boolean cameraAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                if (locationAccepted && cameraAccepted)
                    Snackbar.make(view, "Permission Granted !", Snackbar.LENGTH_LONG).show();
                else {

                    Snackbar.make(view, "Permission Denied !", Snackbar.LENGTH_LONG).show();

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                        if (shouldShowRequestPermissionRationale(ACCESS_FINE_LOCATION)) {
                            showMessageOKCancel(
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            requestPermissions(new String[]{RECEIVE_SMS, READ_SMS},
                                                    PERMISSION_REQUEST_CODE);
                                        }
                                    });
                        }
                    }

                }
            }
        }
    }


    private void showMessageOKCancel(DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(MainActivity.this)
                .setMessage("You need to allow access to both the permissions")
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }
}