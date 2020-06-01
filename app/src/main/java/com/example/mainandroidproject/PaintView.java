package com.example.mainandroidproject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TableLayout;
import android.widget.TableRow;

import com.google.android.material.textfield.TextInputEditText;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class PaintView extends AppCompatActivity {

    private PaintViewBackand paintView;
    private ImageButton btnSettings;
    private ImageButton btnSave;
    private ImageButton btnClear;
    private RadioButton currentRadioBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paint_view);

        paintView = (PaintViewBackand) findViewById(R.id.paintView);
        btnSettings = (ImageButton) findViewById(R.id.btn_brush_settings);
        btnSave = (ImageButton) findViewById(R.id.btn_save_picture);
        btnClear = (ImageButton) findViewById(R.id.btn_clear_picture);


        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        paintView.init(metrics);

        //Colors
        final int MY_RED = Color.rgb(212,2,11);
        final int MY_BLUE = Color.rgb(10,148,207);
        final int MY_GRAY = Color.rgb(171,171,171);
        final int MY_ORANGE = Color.rgb(247,137,3);
        final int MY_GREEN = Color.rgb(103,153,6);
        final int MY_PUPRPLE = Color.rgb(164,108,203);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Окно подтверждения удаления
                AlertDialog.Builder builder = new AlertDialog.Builder(PaintView.this);
                builder.setMessage("Save picture?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent();
                        intent.putExtra("drawedPicturePath",  paintView.saveToInternalStorage(getApplicationContext()));
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                               //canser
                            }
                        });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Окно подтверждения удаления
                AlertDialog.Builder builder = new AlertDialog.Builder(PaintView.this);
                builder.setMessage("Clear picture?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        paintView.clear();
                    }
                })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //canser
                            }
                        });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater li = LayoutInflater.from(PaintView.this);
                View promptsView = li.inflate(R.layout.brush_settings_prompt, null);
                final AlertDialog.Builder mDialogBuilder = new AlertDialog.Builder(PaintView.this);
                mDialogBuilder.setView(promptsView);
                //Группы RadioGroup:
                final RadioGroup brushSizeMode = (RadioGroup) promptsView.findViewById(R.id.radioGroup_brush_size);
                final RadioGroup brushColorMode = (RadioGroup) promptsView.findViewById(R.id.radioGroup_brush_color);

                //В зависимости от выбранных настроек кисти активируем соответсвующие радо баттоны
//                View view = LayoutInflater.from(getApplication()).inflate(R.layout.brush_settings_prompt, null);
//                if (paintView.getStrokeWidth()==10) {
//                    currentRadioBtn = (RadioButton)view.findViewById(R.id.brush_size_mode1);
//                    currentRadioBtn.setChecked(true);
//                } else if (paintView.getStrokeWidth()==20) {
//                    currentRadioBtn = (RadioButton) view.findViewById(R.id.brush_size_mode2);
//                    currentRadioBtn.setChecked(true);
//                } else if (paintView.getStrokeWidth()==30) {
//                    currentRadioBtn = (RadioButton) view.findViewById(R.id.brush_size_mode3);
//                    currentRadioBtn.setChecked(true);
//                }
                //Настраиваем сообщение в диалоговом окне:
                mDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        //Событие кнопки "ОК"
                                        //Находим индексы активных радио кнопок:
                                        int brushSizeRadioButtonID = brushSizeMode.getCheckedRadioButtonId();
//                                        View radioButton = brushSizeMode.findViewById(radioButtonID);
//                                        int brushSizeIndexOfCheked = brushSizeMode.indexOfChild(radioButton);
                                        if(brushSizeRadioButtonID==R.id.brush_size_mode1)
                                            paintView.setBrushSize(10);
                                        else  if(brushSizeRadioButtonID==R.id.brush_size_mode2)
                                            paintView.setBrushSize(20);
                                        else  if(brushSizeRadioButtonID==R.id.brush_size_mode3)
                                            paintView.setBrushSize(30);


                                        int brushColorRadioButtonID = brushColorMode.getCheckedRadioButtonId();
                                        if(brushColorRadioButtonID==R.id.black_color)
                                            paintView.setCurrentColor(Color.BLACK);
                                        else if (brushColorRadioButtonID==R.id.dark_blue_color)
                                            paintView.setCurrentColor(MY_BLUE);
                                        else if (brushColorRadioButtonID==R.id.red_dark_color)
                                            paintView.setCurrentColor(MY_RED);
                                        else if (brushColorRadioButtonID==R.id.dark_gray_color)
                                            paintView.setCurrentColor(MY_GRAY);
                                        else if (brushColorRadioButtonID==R.id.dark_orange_color)
                                            paintView.setCurrentColor(MY_ORANGE);
                                        else if (brushColorRadioButtonID==R.id.dark_green_color)
                                            paintView.setCurrentColor(MY_GREEN);
                                        else if (brushColorRadioButtonID==R.id.dark_purple_color)
                                            paintView.setCurrentColor(MY_PUPRPLE);
                                    }
                                });

                //Создаем AlertDialog:
                final AlertDialog alertDialog = mDialogBuilder.create();
                alertDialog.getWindow().setBackgroundDrawableResource(R.color.custom_orange);
                alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface arg0) {
                        //font:
                        Typeface typeface = ResourcesCompat.getFont(getApplicationContext(), R.font.ljk_ccrickveitch_wo5_otf);
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );

                        //Визуал кнопки:
                        params.setMargins(40, 0, 0, 0);
                        final Button btnOk = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                        btnOk.setLayoutParams(params);
                        btnOk.setBackgroundResource(R.drawable.btn_table_prompt);
                        btnOk.setTextColor(getResources().getColor(R.color.custom_white));
                        btnOk.setTypeface(typeface);
                        btnOk.setTextSize(15);
                    }
                });
                //и отображаем его:
                alertDialog.show();

            }

        });
    }
}
