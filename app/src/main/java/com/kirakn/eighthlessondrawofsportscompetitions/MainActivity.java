package com.kirakn.eighthlessondrawofsportscompetitions;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private EditText input;
    private TextView output;
    private SensorManager sensorManager;
    private Sensor accelerometer;
    private int[] numbers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        input = findViewById(R.id.input);
        output = findViewById(R.id.output);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }
    private SensorEventListener sensorEventListener = new SensorEventListener() {

        @SuppressLint("SetTextI18n")
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            Sensor multiSensor = sensorEvent.sensor;
            if (multiSensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                float xAccelerometer = sensorEvent.values[0];
                float yAccelerometer = sensorEvent.values[1];
                float zAccelerometer = sensorEvent.values[2];
                float medianAccelerometer = (xAccelerometer + yAccelerometer + zAccelerometer) / 3;
                if (medianAccelerometer > 10) {
                      output.setText("Последовательность участия:\n");
                    try {
                        int number = Integer.parseInt(input.getText().toString());
                        numbers = valueArrayRandom(number);
                        for (int n: numbers) {
                            output.append(n + " ");
                        }
                        output.setText("Участнику достаётся номер " + valueRandom(number));
                    } catch (NumberFormatException n) {
                        Toast.makeText(MainActivity.this, "Вы не ввели количество участников", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(sensorEventListener, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(sensorEventListener);
    }

    private int[] valueArrayRandom(int number) {
        Random random = new Random();

        int[] arrayValue = new int[number];
        for (int i = 0; i < arrayValue.length; i++) {
            boolean flag = true;
            while (flag) {
                arrayValue[i] = random.nextInt(number) + 1;
                flag = false;
                for (int k = 0; k < i; k++) {
                    if (arrayValue[i] == arrayValue[k]) {
                        flag = true;
                        break;
                    }
                }
            }
        }
        return arrayValue;
    }
    private int valueRandom(int number) {
        Random random = new Random();
        return random.nextInt(number) + 1;
    }
}