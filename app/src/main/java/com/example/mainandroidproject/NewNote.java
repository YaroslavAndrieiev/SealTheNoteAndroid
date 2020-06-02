package com.example.mainandroidproject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.io.File;
import java.util.ArrayList;
import java.util.Locale;

public class NewNote extends AppCompatActivity {

    private ImageButton btnConfirm;
    private ImageButton table;
    private ImageButton btnImage;
    private ImageButton btnBrush;
    private ImageButton btnToDo;
    private ImageButton btnMic;
    private TextView inputTextOfNote;
    private TextView inputTextOfNoteTitle;
    private LinearLayout content;
    private ScrollView scrollView;

    //Значания, которые будут приходить при нажатии на существующую заметку
    private String currentTitleNote;
    private String currentTextNote;
    private int currentPosition;

    private Typeface typefaceDunkin;
    private final static int REQUEST_CODE_IMAGE_LOAD = 3;
    private final static  int REQUEST_CODE_DRAWED_IMAGE = 4;
    private final static  int REQUEST_CODE_RECORDED_VOICE = 5;

//    private ArrayList<Object> noteContent;//Список всех елементов заметки
    private NoteContentItems items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_note);

        //Шрифт Dunkin
        typefaceDunkin= ResourcesCompat.getFont(NewNote.this, R.font.duntin_serif_bold_ttf);

        btnBrush = (ImageButton) findViewById(R.id.btn_brush);
        btnMic = (ImageButton) findViewById(R.id.btnMic);
        btnToDo = (ImageButton) findViewById(R.id.btn_to_do);
        scrollView=(ScrollView)findViewById(R.id.scroll_view);
        btnConfirm = (ImageButton) findViewById(R.id.btnConfirm);//Кнопка подтверждения
        btnImage = (ImageButton) findViewById(R.id.btnImg);//Кнопка добавления изображения из галареи
        inputTextOfNoteTitle = (TextView) findViewById(R.id.input_title);//Поле ввода заголовка новой заметки
        inputTextOfNote = (TextView) findViewById(R.id.textInput);//Поле ввода новой заметки
        table = (ImageButton) findViewById(R.id.ic_table);
        content = (LinearLayout) findViewById(R.id.ll_content_of_new_note);

        //Считываем данные из Intent в ветке после нажатия на существующую заметку (передаются данные этой заметки):
//        Intent intentFromAllNotes = getIntent();
//        ArrayList<Object> questions = new ArrayList<Object>();
//        questions = (ArrayList<Object>) intentFromAllNotes.getSerializableExtra("clickedNoteContent");
//
//        if  (questions != null) {
//            currentTitleNote = intentFromAllNotes.getStringExtra("clickedNoteTitle");
//            currentTextNote = intentFromAllNotes.getStringExtra("clickedNoteText");
//            currentPosition = intentFromAllNotes.getIntExtra("currentPosition", 0);
//
//            Toast.makeText(getApplicationContext(), "questions length="+questions.size(), Toast.LENGTH_SHORT).show();
//            if(questions.get(2)!=null)
//            {
//                Toast.makeText(getApplicationContext(), "2 element not null!!"+questions.get(2), Toast.LENGTH_SHORT).show();
//            }
//            Toast.makeText(getApplicationContext(), "2 element is null((("+questions.get(2), Toast.LENGTH_SHORT).show();
//
////            content.addView(questions.get(1).getItem());
////                for (int i=0; i < questions.size()-1; i++) {
////                    content.addView(questions.get(i).getItem());
////                }
//
//            inputTextOfNoteTitle.setText(currentTitleNote);
//            inputTextOfNote.setText(currentTextNote);
//        }
//        else
//            Toast.makeText(getApplicationContext(), "empty content", Toast.LENGTH_SHORT).show();

        Intent intentFromAllNotes = getIntent();
        if (intentFromAllNotes != null) {
            currentTitleNote = intentFromAllNotes.getStringExtra("clickedNoteTitle");
            currentTextNote = intentFromAllNotes.getStringExtra("clickedNoteText");
            currentPosition = intentFromAllNotes.getIntExtra("currentPosition", 0);
//            String someJsonItem = intentFromAllNotes.getStringExtra("notesByJsonString");
//            if(someJsonItem!=null && !someJsonItem.equals("")) {
//                Gson jsonLoad = new Gson();
//                NoteContentItem item = jsonLoad.fromJson(someJsonItem, NoteContentItem.class);
//                content.addView(item.getItem());
//            }
            inputTextOfNoteTitle.setText(currentTitleNote);
            inputTextOfNote.setText(currentTextNote);
        }



        //Проверяем какой сейчас requestCode (какая ветка кода)
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Если есть изменённая информация - возвращем её
                if (currentTitleNote != null)
                    returnAddedNote();
                else
                    returnNewNoteData();
            }
        });
        table.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Получаем вид с файла table_prompt.xml, который применим для диалогового окна:
                LayoutInflater li = LayoutInflater.from(NewNote.this);
                View promptsView = li.inflate(R.layout.table_prompt, null);
                //Создаем AlertDialog
                final AlertDialog.Builder mDialogBuilder = new AlertDialog.Builder(NewNote.this);
                //Настраиваем prompt.xml для нашего AlertDialog:
                mDialogBuilder.setView(promptsView);
                final EditText columnsCountInput = (EditText) promptsView.findViewById(R.id.columns_count_inp);
                final EditText rowsCountInput = (EditText) promptsView.findViewById(R.id.rows_count_inp);
                //Настраиваем сообщение в диалоговом окне:
                mDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        //Если нажато "ОК" проводяется проверки вводных данных, после из прохода созадётся таблица(TableLayout) и текс(EditText)
                                        final String columnsCountString = columnsCountInput.getText().toString();
                                        final String rowsCountString = rowsCountInput.getText().toString();
                                        int columnsCount = 0;
                                        int rowsCount = 0;
                                        if (!(columnsCountString.equals("") || rowsCountString.equals(""))) {
                                            try {
                                                columnsCount = Integer.parseInt(columnsCountString);
                                                rowsCount = Integer.parseInt(rowsCountString);
                                            } catch (Exception e) {
                                                dialog.cancel();
                                            }
                                            if (columnsCount > 0 && rowsCount > 0) {
                                                //Создаёт табличку с введёными параметрами:
                                                HorizontalScrollView horizontalScrollView = new HorizontalScrollView(getApplicationContext());
                                                TableLayout tableLayout = new TableLayout(getApplicationContext());
//                                                tableLayout.setStretchAllColumns(true);
//                                                tableLayout.setColumnCollapsed(columnsCount, true);

                                                for (int i = 0; i < rowsCount; i++) {
                                                    TableRow tableRow = new TableRow(getApplicationContext());
                                                    TextInputEditText tiet = new TextInputEditText(getApplicationContext());
                                                    tableRow.setPadding(5, 5, 5, 5);
                                                    tableRow.setBackgroundResource(R.drawable.table_item);

                                                    for (int j = 0; j < columnsCount; j++) {
                                                        EditText editText = new EditText(getApplicationContext());
                                                        editText.setText("");
                                                        editText.setTextColor(Color.BLACK);
                                                        editText.setEms(5);
                                                        Typeface typeface = ResourcesCompat.getFont(getApplicationContext(), R.font.duntin_serif_bold_ttf);
                                                        editText.setTypeface(typeface);
                                                        tableRow.addView(editText, j);
                                                    }
                                                    tableLayout.addView(tableRow, i);
                                                }
                                                horizontalScrollView.addView(tableLayout);

                                                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                                                        LinearLayout.LayoutParams.MATCH_PARENT,
                                                        LinearLayout.LayoutParams.WRAP_CONTENT);
                                                lp.setMargins(0,0,0,100);
                                                EditText edNew= new EditText(NewNote.this);
                                                edNew.setTypeface(typefaceDunkin);
                                                edNew.setTextSize(25);
                                                edNew.setBackgroundColor(Color.WHITE);
                                                edNew.setLayoutParams(lp);
                                                edNew.requestFocus();
                                                content.addView(horizontalScrollView);
                                                content.addView(edNew);
                                                scrollViewDown();
                                            }
                                        }
                                    }
                                })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });

                //Создаем AlertDialog:
                final AlertDialog alertDialog = mDialogBuilder.create();
                alertDialog.getWindow().setBackgroundDrawableResource(R.color.custom_orange);
                alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface arg0) {
                        //font
                        Typeface typeface = ResourcesCompat.getFont(getApplicationContext(), R.font.ljk_ccrickveitch_wo5_otf);
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        params.setMargins(40, 0, 0, 0);
                        final Button btnOk = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                        final Button btnCancel = alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE);
                        btnOk.setLayoutParams(params);
                        btnCancel.setBackgroundResource(R.drawable.btn_table_prompt);
                        btnOk.setBackgroundResource(R.drawable.btn_table_prompt);
                        btnCancel.setTextColor(getResources().getColor(R.color.custom_white));
                        btnOk.setTextColor(getResources().getColor(R.color.custom_white));
                        btnOk.setTypeface(typeface);
                        btnCancel.setTypeface(typeface);
                        btnCancel.setTextSize(15);
                        btnOk.setTextSize(15);
                    }
                });
                //и отображаем его:
                alertDialog.show();
            }
        });
        //Проверяем какой сейчас requestCode (какая ветка кода)
        btnImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, REQUEST_CODE_IMAGE_LOAD);
            }
        });
        btnMic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
                if  (intent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(intent,REQUEST_CODE_RECORDED_VOICE);
                } else {
                    Toast toast = Toast.makeText(getApplicationContext(), "Your device don't support speech input.(", Toast.LENGTH_LONG);
                }
//                scrollViewDown();
            }
        });
        btnToDo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout llNew= new LinearLayout(NewNote.this);
                llNew.setOrientation(LinearLayout.HORIZONTAL);
                RadioButton rbNew= new RadioButton(NewNote.this);
                rbNew.setPadding(10,0,10,0);

                //set radio button color:
                if(Build.VERSION.SDK_INT>=21)
                {
                    ColorStateList colorStateList = new ColorStateList(
                            new int[][]{
                                    new int[]{-android.R.attr.state_enabled}, //disabled
                                    new int[]{android.R.attr.state_enabled} //enabled
                            },
                            new int[] {
                                    Color.BLACK //disabled
                                    ,Color.BLACK //enabled
                            }
                    );
                    rbNew.setButtonTintList(colorStateList);//set the color tint list
                    rbNew.invalidate(); //could not be necessary
                }

                final EditText edNew = new EditText(NewNote.this);
                edNew.setTypeface(typefaceDunkin);
                edNew.setTextSize(25);
                edNew.setHint("Some text");
                edNew.setBackgroundColor(Color.WHITE);
                edNew.requestFocus();
                //cross text after click on radio button
                rbNew.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        edNew.setPaintFlags(edNew.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    }
                });

                llNew.addView(rbNew);
                llNew.addView(edNew);
                content.addView(llNew);
//                scrollViewDown();
            }
        });
        btnBrush.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intentUserProfile = new Intent(NewNote.this, PaintView.class);
                    startActivityForResult(intentUserProfile,REQUEST_CODE_DRAWED_IMAGE);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    //1)Создаём интент, запихиваем в него текст из inputTextOfNote называем этого"newNote"
    //2)Возвращаем результат, закрываем окно
    private void returnNewNoteData() {

        String newNoteTitle = inputTextOfNoteTitle.getText().toString();
        String newNoteText = inputTextOfNote.getText().toString();
        Intent intent = new Intent();
//        ArrayList<Object> object = new ArrayList<Object>();
//        Object item;
//        for (int i=0; i < content.getChildCount(); i++) {
//            object.add(content.getChildAt(i));
//        }
//        Toast.makeText(getApplicationContext(), "2 item="+object.get(2).getClass().toString(), Toast.LENGTH_SHORT).show();
//        Toast.makeText(getApplicationContext(), "content size="+object.size(), Toast.LENGTH_LONG).show();
//
//
//        Bundle bundle = new Bundle();
//        bundle.putSerializable("newNoteContent",object);
//        intent.putExtras(bundle);

//        intent.putExtra("newNoteContent", (ArrayList<NoteContent>)object);
        intent.putExtra("newNoteTitle",newNoteTitle);
        intent.putExtra("newNoteText",newNoteText);

//        Gson gsonObject = new Gson();
//        NoteContentItems someItemToJson = new NoteContentItems();
//        View view = new View(NewNote.this);
//        someItemToJson.addItem(view);
//        String someElementString = gsonObject.toJson(someItemToJson);
//        intent.putExtra("someElement",someElementString);
        setResult(RESULT_OK, intent);
        finish();
    }

    //Возвращаем изменённые данные
    private void returnAddedNote() {
        Intent intent3 = new Intent();
        intent3.putExtra("addedNoteText", inputTextOfNote.getText().toString());
        intent3.putExtra("addedNoteTitle", inputTextOfNoteTitle.getText().toString());
        intent3.putExtra("currentPositionBack", currentPosition);
        setResult(RESULT_OK, intent3);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);

        if (requestCode == REQUEST_CODE_IMAGE_LOAD && resultCode == RESULT_OK && null != data) {
            //1)Создание картинки
            //2)Сохранение пути к выделенной картинке из галереи
            //3)Установка изображения на ImageView
            ImageView ivNew= new ImageView(NewNote.this);
            Uri selectedImage = data.getData();
            ivNew.setImageURI(selectedImage);
            ivNew.setLayoutParams(lp);
            ivNew.getLayoutParams().height = 1000;
            ivNew.getLayoutParams().width = 1000;
            content.addView(ivNew);

            //Созлание следующего текстового поля
            EditText edNew= new EditText(NewNote.this);
            edNew.setTypeface(typefaceDunkin);
            edNew.setTextSize(25);
            edNew.setBackgroundColor(Color.WHITE);
            edNew.requestFocus();
            content.addView(edNew);
        } else  if (requestCode == REQUEST_CODE_DRAWED_IMAGE && resultCode == RESULT_OK && null != data) {

            String drawedPicturePath=data.getStringExtra("drawedPicturePath");
            //Toast toast = Toast.makeText(getApplicationContext(), drawedPicturePath, Toast.LENGTH_LONG);
            //toast.show();
            //После получение кода, что изображение нарисованно и сохраненно в локальную директорию, получаем path и добавляем картинку в записку
            ImageView ivNew = new ImageView(NewNote.this);
            ivNew.setLayoutParams(lp);
            ivNew.getLayoutParams().height = 1000;
            ivNew.getLayoutParams().width = 1000;
            ivNew.setImageURI(Uri.fromFile(new File(drawedPicturePath)));
            content.addView(ivNew);

            //Созлание следующего текстового поля
            EditText edNew= new EditText(NewNote.this);
            edNew.setTypeface(typefaceDunkin);
            edNew.setTextSize(25);
            edNew.setBackgroundColor(Color.WHITE);
            edNew.requestFocus();
            content.addView(edNew);
        } else if (requestCode == REQUEST_CODE_RECORDED_VOICE && resultCode == RESULT_OK && null != data) {
            ArrayList<String> recognizedText = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            EditText edNew = new EditText(NewNote.this);
            edNew.setTypeface(typefaceDunkin);
            edNew.setTextSize(25);
            edNew.setBackgroundColor(Color.WHITE);
            edNew.setText(recognizedText.get(0));
            content.addView(edNew);
        }
    }
    private void scrollViewDown()
    {
        scrollView.post(new Runnable() { @Override public void run() { scrollView.fullScroll(ScrollView.FOCUS_DOWN); } });
    }
}
