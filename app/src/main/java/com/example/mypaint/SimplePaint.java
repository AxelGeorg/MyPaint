package com.example.mypaint;

import android.content.Context;
import android.graphics.*;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.List;

public class SimplePaint extends View {
    List<Paint> paintList;
    List<Path> pathList;
    Paint currentPaint;
    Path currentPath;
    ColorDrawable currentColor;
    StyleType style = StyleType.linha;

    float auxiliarLxInicial = 0,
            auxiliarLxFinal = 0,
            auxiliarLyInicial = 0,
            auxiliarLyFinal = 0;

    public SimplePaint(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        paintList = new ArrayList<Paint>();
        pathList = new ArrayList<Path>();

        currentColor = new ColorDrawable();
        currentColor.setColor(Color.BLACK);

        inicializar();
    }

    public void inicializar() {
        currentPaint = new Paint();
        currentPath = new Path();

        currentPaint.setStyle(Paint.Style.STROKE);
        currentPaint.setStrokeWidth(20);
        currentPaint.setColor(currentColor.getColor());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int idx = 0; idx < paintList.size(); idx++)
            canvas.drawPath(pathList.get(idx), paintList.get(idx));

        canvas.drawPath(currentPath, currentPaint);

        switch (style) {
            case linha:
                canvas.drawPath(currentPath, currentPaint);
                break;
            case circulo:
                break;
            case quadrado:
                break;
        }

        canvas.drawPath(currentPath, currentPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float ly, lx;

        lx = event.getX();
        ly = event.getY();

        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:
                currentPath.moveTo(lx, ly);
                currentPath.lineTo(lx, ly);

                auxiliarLxInicial = lx;
                auxiliarLyInicial = ly;

                break;

            case MotionEvent.ACTION_MOVE:
                auxiliarLxFinal = lx;
                auxiliarLyFinal= ly;
                double distanciaMovendo = Math.sqrt(Math.pow(auxiliarLxFinal - auxiliarLxInicial, 2) + Math.pow(auxiliarLyFinal - auxiliarLyInicial, 2));

                if (style == StyleType.linha) {
                    currentPath.lineTo(lx, ly);
                }else if (style == StyleType.circulo){
                    float raio = (float) (distanciaMovendo / 2);
                    float x = (auxiliarLxInicial + auxiliarLxFinal) / 2;
                    float y = (auxiliarLyInicial + auxiliarLyFinal) / 2;
                    currentPath.reset();
                    currentPath.addCircle(x, y, raio, Path.Direction.CW);
                }else if (style == StyleType.quadrado){
                    currentPath.reset();
                    currentPath.addRect(auxiliarLxInicial,auxiliarLyInicial,auxiliarLxFinal,auxiliarLyFinal, Path.Direction.CCW);
                }

                break;

            case MotionEvent.ACTION_UP:
                auxiliarLxFinal = lx;
                auxiliarLyFinal= ly;

                if (style == StyleType.linha) {
                    currentPath.lineTo(lx, ly);
                }else if (style == StyleType.circulo){
                    double distanciaFinal = Math.sqrt(Math.pow(auxiliarLxFinal - auxiliarLxInicial, 2) + Math.pow(auxiliarLyFinal - auxiliarLyInicial, 2));
                    float raio = (float) (distanciaFinal / 2);
                    float x = (auxiliarLxInicial + auxiliarLxFinal) / 2;
                    float y = (auxiliarLyInicial + auxiliarLyFinal) / 2;
                    currentPath.addCircle(x, y, raio, Path.Direction.CW);
                }else if (style == StyleType.quadrado){
                    currentPath.addRect(auxiliarLxInicial,auxiliarLyInicial,auxiliarLxFinal,auxiliarLyFinal, Path.Direction.CCW);
                }

                paintList.add(currentPaint);
                pathList.add(currentPath);

                inicializar();
                break;
            default:
                break;
        }

        invalidate();
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void setColor(Color color) {
        currentColor.setColor(color.toArgb());
        currentPaint.setColor(color.toArgb());
    }

    public void backDraw() {
        if (paintList.isEmpty()) {
            return;
        }

        paintList.remove(paintList.size() - 1);
        pathList.remove(pathList.size() - 1);

        invalidate();
    }

    public void removeDraw() {
        if (paintList.isEmpty()) {
            return;
        }

        paintList.clear();
        pathList.clear();

        invalidate();
    }

    public void setStyleType(StyleType style) {
        this.style = style;
    }
}

enum StyleType {
    linha,
    circulo,
    quadrado,
}
