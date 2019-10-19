package com.comp90018.drpet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.comp90018.drpet.helper.UserRetriever;

import java.util.Locale;

public class HomeActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager mSensorManager;
    private Sensor mHumiditySensor;
    private Sensor mTemperatureSensor;
    private boolean isHumiditySensorPresent;
    private boolean isTemperatureSensorPresent;
    private TextView commentTextView;
    private float mLastKnownRelativeHumidity = 0;

    TextView usernameTextView;

    public void makeAppointment(View view) {
        Intent intent = new Intent(getApplicationContext(), HospitalActivity.class);
        startActivity(intent);
    }

    public void manageAppointment(View view) {
        Intent intent = new Intent(getApplicationContext(), ManagementActivity.class);
        startActivity(intent);
    }

    public void managePets(View view) {
        Intent intent = new Intent(getApplicationContext(), ViewAllPetsActivity.class);
        startActivity(intent);
    }

    public void logout(View view) {
        Intent intent = new Intent(getApplicationContext(), SigninActivity.class);
        startActivity(intent);
    }

    public void setView() {
        UserRetriever retriever = new UserRetriever();
        retriever.retrieveData(new UserRetriever.FirebaseCallback() {
            @Override
            public void onCallback(UserModel user) {
                String username = user.name;
                usernameTextView.setText(username);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        usernameTextView = findViewById(R.id.usernameTextView);
        commentTextView = findViewById(R.id.commentTextView);

        setView();

        mSensorManager = (SensorManager) this.getSystemService(Context.SENSOR_SERVICE);

        String sensorStatus;

        if (mSensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY) != null) {
            sensorStatus = "Humidity sensor working.";
            mHumiditySensor = mSensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY);
            isHumiditySensorPresent = true;
        } else {
            sensorStatus = "Humidity sensor is not available!";
            isHumiditySensorPresent = false;
        }

        if (mSensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE) != null) {
            sensorStatus += "\nTemperature sensor working.";
            mTemperatureSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
            isTemperatureSensorPresent = true;
        } else {
            sensorStatus += "\nTemperature sensor is not available!";
            isTemperatureSensorPresent = false;
        }

        commentTextView.setText(sensorStatus);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isHumiditySensorPresent) {
            mSensorManager.registerListener(this, mHumiditySensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
        if (isTemperatureSensorPresent) {
            mSensorManager.registerListener(this, mTemperatureSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (isHumiditySensorPresent || isTemperatureSensorPresent) {
            mSensorManager.unregisterListener(this);
        }
    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_RELATIVE_HUMIDITY) {
            mLastKnownRelativeHumidity = event.values[0];
        } else if (event.sensor.getType() == Sensor.TYPE_AMBIENT_TEMPERATURE) {
            if (mLastKnownRelativeHumidity != 0) {
                float temperature = event.values[0];
                float humidity = mLastKnownRelativeHumidity;
                setComment(temperature, humidity);
            }
        }
    }

    public void setComment(float temperature, float humidity) {

        String comment = "Temperature: " + temperature + ", Humidity: " + humidity + "%.";

        if (temperature >= 40 || temperature <= 0 || humidity <= 30) {

            if (temperature >= 40) {
                comment += "\nThe temperature is too hot for your pet.";
            } else if (temperature <= 0) {
                comment += "\nThe temperature is too cold for your pet.";
            }

            if (humidity <= 30) {
                comment += "\nYour room is too dry for your pet.";
            }

        } else {
            comment += "\nThe temperature and humidity are suitable.";
        }

        commentTextView.setText(comment);

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSensorManager = null;
        mHumiditySensor = null;
        mTemperatureSensor = null;
    }
}
