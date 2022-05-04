package com.example.mypaint;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

import com.skydoves.colorpickerview.ColorEnvelope;
import com.skydoves.colorpickerview.ColorPickerDialog;
import com.skydoves.colorpickerview.listeners.ColorEnvelopeListener;

public class MainActivity extends AppCompatActivity {
    SimplePaint simplePaint;
    ImageView imageViewColorPicker;
    ImageView imageViewVoltar;
    ImageView imageViewLimpar;
    ImageView imageViewQuadrado;
    ImageView imageViewCirculo;
    ImageView imageViewLinha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        simplePaint = findViewById(R.id.simplePaint);
        imageViewLinha = findViewById(R.id.linha);
        imageViewQuadrado = findViewById(R.id.quadrado);
        imageViewCirculo = findViewById(R.id.circulo);
        imageViewLimpar = findViewById(R.id.limpar);
        imageViewVoltar = findViewById(R.id.voltar);
        imageViewColorPicker = findViewById(R.id.colorPicker);

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId())
                {
                    case R.id.linha: simplePaint.setStyleType(StyleType.linha); break;
                    case R.id.quadrado: simplePaint.setStyleType(StyleType.quadrado); break;
                    case R.id.circulo: simplePaint.setStyleType(StyleType.circulo); break;
                    case R.id.limpar: simplePaint.removeDraw(); break;
                    case R.id.voltar: simplePaint.backDraw(); break;
                    case R.id.colorPicker: colorPickerSelectColor(); break;
                }
            }
        };

        imageViewColorPicker.setOnClickListener(onClickListener);
        imageViewVoltar.setOnClickListener(onClickListener);
        imageViewLimpar.setOnClickListener(onClickListener);
        imageViewQuadrado.setOnClickListener(onClickListener);
        imageViewCirculo.setOnClickListener(onClickListener);
        imageViewLinha.setOnClickListener(onClickListener);
    }

    public void colorPickerSelectColor() {
        new ColorPickerDialog.Builder(this)
                .setTitle("ColorPicker Dialog")
                .setPreferenceName("MyColorPickerDialog")
                .setPositiveButton(getString(R.string.confirm),
                        new ColorEnvelopeListener() {
                            @Override
                            public void onColorSelected(ColorEnvelope envelope, boolean fromUser) {
                                setColor(envelope);
                            }
                        })
                .setNegativeButton(getString(R.string.cancel),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        })
                .attachAlphaSlideBar(true)
                .attachBrightnessSlideBar(true)
                .setBottomSpace(12)
                .show();
    }

    private void setColor(ColorEnvelope envelope) {
        //simplePaint.setColor(Color.valueOf(envelope.getColor()));
        //imageViewColorPicker.setColorFilter(Color.valueOf(envelope.getColor()).toArgb());
    }
}
