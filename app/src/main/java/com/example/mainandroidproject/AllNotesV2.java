package com.example.mainandroidproject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;

import java.util.ArrayList;


public class AllNotesV2 extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,MainAdapter.onNoteListener{

    SharedPreferencesData myData;
    SharedPreferences sp;


    private DrawerLayout drawerLayout;
    private TextView tvInfo;
    private Toolbar toolbar;
    private ImageButton userProfile;
    private ImageButton btnNewNote;
    private ImageButton btnUndo;
    private RecyclerView staggeredRecyclerView;
    private MainAdapter recyclerViewAdapter;
    private StaggeredGridLayoutManager manager;
    private NavigationView navMenu;
    private TextInputEditText searchQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_notes_v2);


//            РЕШИТЬ ПРОБЛЕМУ ПУСТОГО sp ПРИ ПЕРВОМ ЗАПУСКЕ
//        UPDATE:Проблема решена в  loadDataFromSharedPreferences()!
//        Старое решение:
//          myData= new SharedPreferencesData();
//          saveDataToSharedPreferences();


        loadDataFromSharedPreferences();

        toolbar = findViewById(R.id.test_toolbar);
//        toolbar.setNavigationIcon(R.drawable.ic_btn_arrow_back);
        setSupportActionBar(toolbar);
//        toolbar.setLogo(R.drawable.seal_the_note);

        btnNewNote = findViewById(R.id.bntNewNote);//Кнопка создания новой заметки
        tvInfo = (TextView)findViewById(R.id.test_tv);
        userProfile = findViewById(R.id.user_profile_img_btn);
        drawerLayout = findViewById(R.id.test_drawer_layout);
        btnUndo = findViewById(R.id.bntUndo);

        searchQuery =  (TextInputEditText) findViewById(R.id.search_query);
        searchQuery.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                filterRecyclerView(s.toString());
            }
        });

        //Menu stuff:
        NavigationView navigationView=(NavigationView)findViewById(R.id.test_navigation_view);
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.drawer_open,R.string.drawer_close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        //RecyclerView stuff:
        staggeredRecyclerView = (RecyclerView)findViewById(R.id.recycler_view);//RecyclerView
        manager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);//StaggeredGridLayoutManager


        pushInfoToDataRecyclerView();//Занести все заметки в таблицу


        userProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intentUserProfile = new Intent(AllNotesV2.this, UserProfile.class);
                    startActivity(intentUserProfile);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        //По клику на кнопку btnNewNote вызвать метод newNote() или удаляться выделенные заметки
        btnNewNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Если кнопка красная - будет удаление, иначе она синяя и будет добавление
                if(btnNewNote.getDrawable().getConstantState().equals(getResources().getDrawable(R.drawable.ic_btn_new_red).getConstantState())) {

                    //Окно подтверждения удаления
                    AlertDialog.Builder builder = new AlertDialog.Builder(AllNotesV2.this);
                    builder.setMessage("Delete selected notes?");
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            myData.deleteAllEnabled();
                            saveDataToSharedPreferences();
                            RefreshDataBySharedPreferences();
                            //Toast.makeText(AllNotes.this, "Удаление!", Toast.LENGTH_SHORT).show();
                            removeSelectionsFromNotes();
                        }
                    })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                    removeSelectionsFromNotes();
                                }
                            });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
                else
                    newNote();
            }
        });
        btnUndo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btnUndo.getVisibility()==View.VISIBLE){
                    removeSelectionsFromNotes();
                }
            }
        });
    }

    private void filterRecyclerView(String text) {
        ArrayList<Note> filteredList = new ArrayList<>();

        for(Note item : myData.getNotesList()) {
            if (item.getTitle().toLowerCase().contains(searchQuery.getText())) {
                filteredList.add(item);
            }
            recyclerViewAdapter.filterList(filteredList);
        }
    }

    //Actions on selection items in menu
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
//        String itemName=(String) item.getTitle();
//        tvInfo.setText(itemName);
        closeDrawer();

        switch (item.getItemId()){
            case R.id.item_1:
                break;

            case R.id.item_2:
                break;
        }
        return false;
    }

    private void closeDrawer() {
        drawerLayout.closeDrawer(GravityCompat.START);
    }

    private void openDrawer(){
        drawerLayout.openDrawer(GravityCompat.START);
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            closeDrawer();
        }
        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.right_buttons,menu);

        //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        toolbar.setNavigationIcon(R.drawable.ic_navigation);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
//        if(id==R.id.test_user_btn)
//            tvInfo.setText("USER!");

        return super.onOptionsItemSelected(item);
    }

    //Снимает выделение с заметок, возвраащет состояние визуальных элементов к "стандартному" виду
    private void removeSelectionsFromNotes()
    {
        btnUndo.animate().rotation(180F).setInterpolator(new AccelerateDecelerateInterpolator());
        btnUndo.setVisibility(View.INVISIBLE);
        pushInfoToDataRecyclerView();
        btnNewNote.animate().rotation(90F).setInterpolator(new AccelerateDecelerateInterpolator());
        btnNewNote.setImageResource(R.drawable.ic_bnt_new);
        myData.setAllNotesUnEnabled();
    }

    //Очищает myData, заполняет заново из SharedPreferences и выводим на экран
    private void RefreshDataBySharedPreferences() {
        myData=null;
        loadDataFromSharedPreferences();
        pushInfoToDataRecyclerView();
    }

    //Метод пушит информацию в RecyclerView из myData.getNotesList() (из списка всех записок)
    private void pushInfoToDataRecyclerView() {
        manager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        staggeredRecyclerView.setLayoutManager(manager);
        recyclerViewAdapter = new MainAdapter(this,myData.getNotesList(),this,btnNewNote,btnUndo);
        staggeredRecyclerView.setAdapter(recyclerViewAdapter);
    }

    private void newNote() {
        try {
            //Создали intent и вызывли Activity, чтобы получить обратно Result
            Intent intent = new Intent(this, NewNote.class);
            //Вызываем класс NewNote, для получения результата
            startActivityForResult(intent,1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Получение данных из класса NewNote, если данные пришли - добавить их в TitlesList и NotesList
    //Обработчик requestCode:
    //requestCode=1 - ветка создания новой заметки
    //requestCode=2 - ветка изменения заметки по нажатию
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data==null) {
            return;
        }

        if(requestCode==1) {
            String title = data.getStringExtra("newNoteTitle");
            String text = data.getStringExtra("newNoteText");

//            ArrayList<NoteContent> questions = new ArrayList<NoteContent>();
//            questions = (ArrayList<NoteContent>) data.getSerializableExtra("newNoteContent");\
//            Bundle bundleObj = data.getExtras();
//            ArrayList<Object> questions = ( ArrayList<Object>) bundleObj.getSerializable("newNoteContent");
//
//            if (questions==null)
//                Toast.makeText(getApplicationContext(), "object = null", Toast.LENGTH_LONG).show();
//            else {
//                Toast.makeText(getApplicationContext(), "object length:"+questions.size(), Toast.LENGTH_LONG).show();
//                Toast.makeText(getApplicationContext(), "2 element all notes:"+questions.get(2), Toast.LENGTH_LONG).show();
//            }


//            String someJsonItem = data.getStringExtra("someElement");
            Note newNote = new Note(title,text);
            myData.addNote(newNote);
            saveDataToSharedPreferences();
            RefreshDataBySharedPreferences();
        }
        else if(requestCode==2) {
            int currentPosition=data.getIntExtra("currentPositionBack",0);
            String newTitle=data.getStringExtra("addedNoteTitle");
            String newText=data.getStringExtra("addedNoteText");
            if(!(myData.getNotesList().get(currentPosition).getTitle().equals(newTitle) && myData.getNotesList().get(currentPosition).getText().equals(newText))) {
                myData.getNotesList().get(currentPosition).setTitle(newTitle);
                myData.getNotesList().get(currentPosition).setText(newText);
                saveDataToSharedPreferences();
                RefreshDataBySharedPreferences();
            }
        }
    }
    //Используется класс Gson, метод которого toJson(Object) позволяет трансформировать экземпляр класс в json строку
    //Затем возможно обратное приобразование через метод fromJson(String, class)
    //Сохраняет myData в SharedPreferences
    private void saveDataToSharedPreferences() {
        sp = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor mEdit1 = sp.edit();
        Gson jsonSave = new Gson();
        String notesByJsonString = jsonSave.toJson(myData);
        mEdit1.putString("notesByJsonString",notesByJsonString);
        mEdit1.apply();
    }
    //Загружает myData из SharedPreferences
    private void loadDataFromSharedPreferences() {
        sp = getPreferences(MODE_PRIVATE);
        Gson jsonLoad = new Gson();
        String notesByJsonString = sp.getString("notesByJsonString", "");
        //Если notesByJsonString строка с сохранёнными заметками пустая, значит это первый запуск..
        //Создаём пустой список заметок
        //Сохраняем его в SharedPreferences
        //И заново загружакем через loadDataFromSharedPreferences();
        if(notesByJsonString.equals("")) {
            //Toast.makeText(AllNotesV2.this, "sp was empty... creating sp...", Toast.LENGTH_SHORT).show();
            myData = new SharedPreferencesData();
            saveDataToSharedPreferences();
            loadDataFromSharedPreferences();
        }
        else {
            //Иначе - заметки в строке notesByJsonString есть, значи просто загружаем их...
            myData = jsonLoad.fromJson(notesByJsonString, SharedPreferencesData.class);
        }
    }

    //Событие нажатия на существующую заметку
    @Override
    public void onNoteClick(int position) {
        Note clickedNote = myData.getNotesList().get(position);
        Intent intent = new Intent(this,NewNote.class);
//        intent.putExtra("clickedNoteContent",clickedNote.getNoteContent());
        intent.putExtra("clickedNoteTitle",clickedNote.getTitle());
        intent.putExtra("clickedNoteText",clickedNote.getText());
        intent.putExtra("notesByJsonString",clickedNote.getSomeItemInJson());
        intent.putExtra("currentPosition",position);
        startActivityForResult(intent,2);
    }

    //onAttachedToWindow и onKeyDown сохраняют sp после событий закрытия/шаг назад
    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        saveDataToSharedPreferences();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK ) {
            saveDataToSharedPreferences();
            return true;
        }
        if (keyCode == KeyEvent.KEYCODE_HOME) {
            saveDataToSharedPreferences();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


}
