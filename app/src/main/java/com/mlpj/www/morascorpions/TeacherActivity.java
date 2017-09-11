package com.mlpj.www.morascorpions;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class TeacherActivity extends AppCompatActivity {

    //TextView tvText;
    UserLocalStore userLocalStore;
    private  String [] actions;
    private ListView lvTeacherActions;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher);

        //tvText = (TextView) findViewById(R.id.tvText);
        userLocalStore = new UserLocalStore(this);
        actions = getResources().getStringArray(R.array.teacherActions);
        lvTeacherActions = (ListView)findViewById(R.id.lvTeacherActions);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_activated_1,
                actions);
        lvTeacherActions.setAdapter(adapter);
        lvTeacherActions.setOnItemClickListener(new DrawerItemClickListener());

        Intent intent = getIntent();
        User user = (User)intent.getSerializableExtra("user");



        setTitle("Hi " + user.name);

        //tvText.setText("You are logged in as a Teacher");



    }


    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_LogOut:
                userLocalStore.clearUser();
                userLocalStore.setUserLoggedIn(false);
                Intent intent = new Intent(this,LoginActivity.class);
                startActivity(intent);
                finish();

        }
        return super.onOptionsItemSelected(item);
    }

    public class DrawerItemClickListener implements ListView.OnItemClickListener{
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
            selectItem(position);
        }
    }

    public void selectItem(int position){
        Fragment fragment;
        switch (position){
            case 1:
                fragment = new MarkAttendanceFragment();
                break;
            default:
                fragment = new HomeFragment();
        }

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.teacherContentFrame,fragment);
        ft.addToBackStack(null);
        ft.commit();

    }
}
