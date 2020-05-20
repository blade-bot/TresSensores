package com.uriel.appsensores.fragments;

import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.uriel.appsensores.MainActivity;
import com.uriel.appsensores.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import static android.content.Context.SENSOR_SERVICE;
import static android.hardware.Sensor.TYPE_LIGHT;
import static androidx.core.content.ContextCompat.getSystemService;

public class Luz extends Fragment {
    Context context;
    TextView tv1;
    View l;
    SensorManager sm;
    Sensor sensorluz;
    SensorEventListener listenerluz;
    public Luz(Context context)
    {
        this.context=context;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View vistaluz = inflater.inflate(R.layout.luz_main, container, false);

        tv1 = vistaluz.findViewById(R.id.texto_luz);
        l = vistaluz.findViewById(R.id.view_luz);

        //sensor de luz
        //sensormanagerluz = (SensorManager) getSystemService(SENSOR_SERVICE);
        sm = (SensorManager) context.getApplicationContext().getSystemService(SENSOR_SERVICE);
        sensorluz = sm.getDefaultSensor(TYPE_LIGHT);
        if (sensorluz == null)
        {
            Toast.makeText(getContext(),"Error al activar el sensor de luz", Toast.LENGTH_SHORT).show();
        }
        final float maximovalor = sensorluz.getMaximumRange();
        Log.i("valor maximo",String.valueOf(maximovalor));
        listenerluz = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                float valor_luz = event.values[0];
                tv1.setText(""+valor_luz);
                //valor para obtener un rango del 0 al 255
                int nuevovalorluz = (int) (255f * valor_luz / maximovalor);
                //valor para obtener el mismo rango pero sumandole para notar mas la diferencia
                //int nuevovalorluz = (int) (255f * valor_luz / maximovalor + 70);
                l.setBackgroundColor(Color.rgb(nuevovalorluz, nuevovalorluz, nuevovalorluz));
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };

        return vistaluz;
    }

    @Override
    public void onResume() {
        super.onResume();
        sm.registerListener(listenerluz, sensorluz, SensorManager.SENSOR_DELAY_FASTEST);
    }
}
