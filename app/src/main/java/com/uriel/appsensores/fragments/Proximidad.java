package com.uriel.appsensores.fragments;


import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.uriel.appsensores.MainActivity;
import com.uriel.appsensores.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import static android.content.Context.SENSOR_SERVICE;
import static androidx.core.content.ContextCompat.getSystemService;

public class Proximidad extends Fragment implements SensorEventListener{
    Context context;
    SensorManager sm;
    Sensor sensor;
    TextView et;
    View layout;
    public Proximidad(Context context){
        this.context=context;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View vistaproximidad = inflater.inflate(R.layout.proximidad_main, container, false);

        et = vistaproximidad.findViewById(R.id.texto_proximidad);
        layout = vistaproximidad.findViewById(R.id.view_proximidad);
        //sm =(SensorManager)getSystemService(SENSOR_SERVICE);
        sm = (SensorManager) context.getApplicationContext().getSystemService(SENSOR_SERVICE);
        sensor = sm.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        sm.registerListener((SensorEventListener) this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
        sm.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
        return vistaproximidad;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        String text = String.valueOf(event.values[0]);
        et.setText(text);
        float valor = Float.parseFloat(text);
        if (valor == 0)
        {
            layout.setBackgroundColor(Color.RED);
        } else {
            layout.setBackgroundColor(Color.GREEN);
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
