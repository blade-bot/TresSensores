package com.uriel.appsensores.fragments;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
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

import static android.content.Context.SEARCH_SERVICE;
import static android.content.Context.SENSOR_SERVICE;
import static androidx.core.content.ContextCompat.getSystemService;

public class Acelerometro extends Fragment {
    Context context;
    TextView tv_x,tv_y,tv_z;
    SensorManager sensormanageracelerometro;
    Sensor sensoracelerometro;
    SensorEventListener listeneracelerometro;
    int estado=0;
    public Acelerometro(Context context){
        this.context=context;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View vistaacelerometro = inflater.inflate(R.layout.acelerometro_main, container, false);

        tv_x = vistaacelerometro.findViewById(R.id.texto_x);
        tv_y = vistaacelerometro.findViewById(R.id.texto_y);
        tv_z = vistaacelerometro.findViewById(R.id.texto_z);

        //Acelerometro y latigo
        //sensormanageracelerometro = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensormanageracelerometro = (SensorManager) context.getApplicationContext().getSystemService(SENSOR_SERVICE);
        sensoracelerometro = sensormanageracelerometro.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if (sensoracelerometro==null)
        {
            Toast.makeText(getContext(),"Error al iniciar el acelerometro", Toast.LENGTH_SHORT).show();
        }
        listeneracelerometro = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                float x = event.values[0];
                float y = event.values[1];
                float z = event.values[2];

                tv_x.setText("" + x);
                tv_y.setText("" + y);
                tv_z.setText("" + z);
                //casos del latigo
                if (x<-4 && estado==0)
                {
                    estado++;
                } else if (x>5 && estado==1)
                {
                    estado++;
                }
                if (estado==2)
                {
                    estado=0;
                    Sonido();
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };
        start();
        return vistaacelerometro;
    }

    private void start(){
        sensormanageracelerometro.registerListener(listeneracelerometro, sensoracelerometro, sensormanageracelerometro.SENSOR_DELAY_NORMAL);
    }
    private void stop(){
        sensormanageracelerometro.unregisterListener(listeneracelerometro);
    }
    public void Sonido(){
        MediaPlayer mediaPlayer = MediaPlayer.create(getContext(), R.raw.latigo);
        mediaPlayer.start();
    }

    @Override
    public void onResume() {
        start();
        super.onResume();
    }

    @Override
    public void onPause() {
        stop();
        super.onPause();
    }
}
