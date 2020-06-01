//package com.example.mainandroidproject;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.appcompat.app.AlertDialog;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.drawerlayout.widget.DrawerLayout;
//import androidx.recyclerview.widget.RecyclerView;
//import androidx.recyclerview.widget.StaggeredGridLayoutManager;
//
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.media.MediaRouter;
//import android.os.Bundle;
//import android.view.Gravity;
//import android.view.KeyEvent;
//import android.view.View;
//import android.view.animation.AccelerateDecelerateInterpolator;
//import android.widget.ImageButton;
//import android.widget.Toast;
//
//import com.google.android.material.navigation.NavigationView;
//import com.google.gson.Gson;
//
//
//public class AllNotes extends AppCompatActivity implements MainAdapter.onNoteListener{
//
//    SharedPreferencesData myData;
//    SharedPreferences sp;
//
//    private ImageButton btnNewNote;
//    private ImageButton menuBtn;
//    private ImageButton userProfile;
//    private RecyclerView staggeredRecyclerView;
//    private MainAdapter recyclerViewAdapter;
//    private StaggeredGridLayoutManager manager;
//    private NavigationView navMenu;
//    private DrawerLayout drawerLayout;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_all_notes);
////        if (savedInstanceState == null) {
////            // приложение запущено впервые
////            Toast.makeText(AllNotes.this, "ЭТО 1 ЗАПУСК!!", Toast.LENGTH_SHORT).show();
////        }
////        else {
////            // приложение восстановлено из памяти
////            Toast.makeText(AllNotes.this, "ЭТО НЕ 1 ЗАПУСК!!", Toast.LENGTH_SHORT).show();
////        }
//
//
//        //РЕШИТЬ ПРОБЛЕМУ ПУСТОГО sp ПРИ ПЕРВОМ ЗАПУСКЕ
////        myData= new SharedPreferencesData();
////        saveDataToSharedPreferences();
//
//        //Загрузить данные из SharedPreferences в myData
////        myData= new SharedPreferencesData();
////        saveDataToSharedPreferences();
//        loadDataFromSharedPreferences();
//
//
//        btnNewNote = findViewById(R.id.bntNewNote);//Кнопка создания новой заметки
//        menuBtn=findViewById(R.id.btnMenu);//Кнопка меню
//        userProfile=findViewById(R.id.user_profile_btn);
//        staggeredRecyclerView = (RecyclerView)findViewById(R.id.recycler_view);//RecyclerView
//        manager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);//StaggeredGridLayoutManager
//
//
//        pushInfoToDataRecyclerView();//Занести все заметки в таблицу
//
//        //По клику на кнопку btnNewNote вызвать метод newNote() или удаляться выделенные заметки
//        btnNewNote.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //Если кнопка красная - будет удаление, иначе она синяя и будет добавление
//                if(btnNewNote.getDrawable().getConstantState().equals(getResources().getDrawable(R.drawable.ic_btn_new_red).getConstantState())) {
//
//                    //Окно подтверждения удаления
//                    AlertDialog.Builder builder = new AlertDialog.Builder(AllNotes.this);
//                    builder.setMessage("Are you sure than you want to delete the selected notes?");
//                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            myData.deleteAllEnabled();
//                            saveDataToSharedPreferences();
//                            RefreshDataBySharedPreferences();
//                            //Toast.makeText(AllNotes.this, "Удаление!", Toast.LENGTH_SHORT).show();
//                            removeSelectionsFromNotes();
//                        }
//                        })
//                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialog, int which) {
//                                    dialog.cancel();
//                                    removeSelectionsFromNotes();
//                                }
//                            });
//                    AlertDialog alertDialog = builder.create();
//                    alertDialog.show();
//                }
//                else
//                    newNote();
//            }
//        });
//
//        menuBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //Если кнопка - стрелка назад
//                if(menuBtn.getDrawable().getConstantState().equals(getResources().getDrawable(R.drawable.undo_icon).getConstantState())) {
//                    removeSelectionsFromNotes();
//                }
//                else {
//                    try {
//                        Intent intentUserProfile = new Intent(AllNotes.this, test.class);
//                        startActivity(intentUserProfile);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//
////                    drawerLayout.openDrawer(Gravity.LEFT);
////                    myData.clearNotesList();
////                    saveDataToSharedPreferences();
////                    loadDataFromSharedPreferences();
////                    pushInfoToDataRecyclerView();
////                    Toast.makeText(AllNotes.this, "All notes were deleted!", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//
//        userProfile.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                try {
//                    Intent intentUserProfile = new Intent(AllNotes.this, UserProfile.class);
//                    startActivity(intentUserProfile);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//    }
//
//    //Снимает выделение с заметок, возвраащет состояние визуальных элементов к "стандартному" виду
//    private void removeSelectionsFromNotes()
//    {
//        menuBtn.setImageResource(R.drawable.ic_navigation);
//        pushInfoToDataRecyclerView();
//        btnNewNote.animate().rotation(90F).setInterpolator(new AccelerateDecelerateInterpolator());
//        btnNewNote.setImageResource(R.drawable.ic_bnt_new);
//        myData.setAllNotesUnEnabled();
//    }
//
//    //Очищает myData, заполняет заново из SharedPreferences и выводим на экран
//    private void RefreshDataBySharedPreferences() {
//        myData=null;
//        loadDataFromSharedPreferences();
//        pushInfoToDataRecyclerView();
//    }
//
//    //Метод пушит информацию в RecyclerView из myData.getNotesList() (из списка всех записок)
//    private void pushInfoToDataRecyclerView() {
//        manager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
//        staggeredRecyclerView.setLayoutManager(manager);
//        recyclerViewAdapter= new MainAdapter(this,myData.getNotesList(),this,menuBtn,btnNewNote);
//        staggeredRecyclerView.setAdapter(recyclerViewAdapter);
//    }
//
//    private void newNote() {
//        try {
//            //Создали intent и вызывли Activity, чтобы получить обратно Result
//            Intent intent = new Intent(this, NewNote.class);
//            //Вызываем класс NewNote, для получения результата
//            startActivityForResult(intent,1);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    //Получение данных из класса NewNote, если данные пришли - добавить их в TitlesList и NotesList
//    //Обработчик requestCode:
//    //requestCode=1 - ветка создания новой заметки
//    //requestCode=2 - ветка изменения заметки по нажатию
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if(data==null) {
//            return;
//        }
//
//        if(requestCode==1) {
//            String title=data.getStringExtra("newNoteTitle");
//            String text=data.getStringExtra("newNote");
//            //Если введёны текст есть, добавляем и сохраняем заметку
//            if(!text.equals("") || !title.equals("")) {
//                myData.addNote(title,text);
//                saveDataToSharedPreferences();
//            }
//        }
//        else
//            if(requestCode==2) {
//                int currentPosition=data.getIntExtra("currentPositionBack",0);
//                String newTitle=data.getStringExtra("addedNoteTitle");
//                String newText=data.getStringExtra("addedNoteText");
//                if(!(myData.getNotesList().get(currentPosition).getTitle().equals(newTitle) && myData.getNotesList().get(currentPosition).getText().equals(newText))) {
//                    myData.getNotesList().get(currentPosition).setTitle(newTitle);
//                    myData.getNotesList().get(currentPosition).setText(newText);
//                    saveDataToSharedPreferences();
//                    RefreshDataBySharedPreferences();
//                }
//            }
//    }
//    //Используется класс Gson, метод которого toJson(Object) позволяет трансформировать экземпляр класс в json строку
//    //Затем возможно обратное приобразование через метод fromJson(String, class)
//    //Сохраняет myData в SharedPreferences
//    private void saveDataToSharedPreferences() {
//        sp=getPreferences(MODE_PRIVATE);
//        SharedPreferences.Editor mEdit1 = sp.edit();
//        Gson jsonSave = new Gson();
//        String notesByJsonString = jsonSave.toJson(myData);
//        mEdit1.putString("notesByJsonString",notesByJsonString);
//        mEdit1.apply();
//    }
//    //Загружает myData из SharedPreferences
//    private void loadDataFromSharedPreferences() {
//        sp=getPreferences(MODE_PRIVATE);
//        Gson jsonLoad = new Gson();
//        String notesByJsonString = sp.getString("notesByJsonString", "");
//        myData = jsonLoad.fromJson(notesByJsonString, SharedPreferencesData.class);
//    }
//
//    //Событие нажатия на существующую заметку
//    @Override
//    public void onNoteClick(int position) {
//        myData.getNotesList().get(position);
//        Intent intent = new Intent(this,NewNote.class);
//        intent.putExtra("currentTitleNote",myData.getNotesList().get(position).getTitle());
//        intent.putExtra("currentTextNote",myData.getNotesList().get(position).getText());
//        intent.putExtra("currentPosition",position);
//        startActivityForResult(intent,2);
//    }
//
//
//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_BACK ) {
//            // do something on back.
//            return true;
//        }
//        return super.onKeyDown(keyCode, event);
//    }
//}
