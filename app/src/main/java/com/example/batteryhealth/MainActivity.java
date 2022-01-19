package com.example.batteryhealth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Button button;
    TextView textview,charging,batteryleft;
    IntentFilter intentfilter;
    int deviceHealth;
    String currentBatteryHealth="Battery Health ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button=findViewById(R.id.button);
        textview=findViewById(R.id.textView);
        charging=findViewById(R.id.textViewcharging);
        batteryleft=findViewById(R.id.textViewchargeleft);
        intentfilter=new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.this.registerReceiver(broadcastreceiver,intentfilter);
                // Intent to check if any action is performed on battery
                IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
                Intent batteryStatus = MainActivity.this.registerReceiver(null, ifilter);
                //To check if battery is charging
                int status = batteryStatus.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
                boolean isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING ||
                        status == BatteryManager.BATTERY_STATUS_FULL;
                int chargePlug = batteryStatus.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
                //If charging how is it charging
                boolean usbCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_USB;
                boolean acCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_AC;
                if (isCharging)
                {
                    if(usbCharge)
                    {
                        charging.setText("Charging with usb");
                    }
                    else if (acCharge)
                    {
                        charging.setText("Charging with AC");
                    }
                }
                else
                {
                    charging.setText("");
                }
                //To get how much battery is there in percentage
                int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
                int scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
                float batteryPct = level * 100 / (float)scale;
                batteryleft.setText("The charging left is "+batteryPct);
            }
        });
    }


    private BroadcastReceiver broadcastreceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            deviceHealth = intent.getIntExtra(BatteryManager.EXTRA_HEALTH,0);

            if(deviceHealth == BatteryManager.BATTERY_HEALTH_COLD){

                textview.setText(currentBatteryHealth+" = Cold");
            }

            if(deviceHealth == BatteryManager.BATTERY_HEALTH_DEAD){

                textview.setText(currentBatteryHealth+" = Dead");
            }

            if (deviceHealth == BatteryManager.BATTERY_HEALTH_GOOD){

                textview.setText(currentBatteryHealth+" = Good");
            }

            if(deviceHealth == BatteryManager.BATTERY_HEALTH_OVERHEAT){

                textview.setText(currentBatteryHealth+" = OverHeat");
            }

            if (deviceHealth == BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE){

                textview.setText(currentBatteryHealth+" = Over voltage");
            }

            if (deviceHealth == BatteryManager.BATTERY_HEALTH_UNKNOWN){

                textview.setText(currentBatteryHealth+" = Unknown");
            }
            if (deviceHealth == BatteryManager.BATTERY_HEALTH_UNSPECIFIED_FAILURE){

                textview.setText(currentBatteryHealth+" = Unspecified Failure");
            }
        }
    };
}