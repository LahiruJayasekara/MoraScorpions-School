package com.mlpj.www.morascorpions;

import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class TeacherActivity extends AppCompatActivity {

    //TextView tvText;
    UserLocalStore userLocalStore;
    private  String [] actions;
    private ListView lvTeacherActions;
    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher);

        //tvText = (TextView) findViewById(R.id.tvText);
        userLocalStore = new UserLocalStore(this);
        User user = userLocalStore.getUserDetails();
        actions = getResources().getStringArray(R.array.teacherActions);
        lvTeacherActions = (ListView)findViewById(R.id.lvTeacherActions);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_activated_1,
                actions);
        lvTeacherActions.setAdapter(adapter);
        lvTeacherActions.setOnItemClickListener(new DrawerItemClickListener());

        setTitle("Hi " + user.name);



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

        switch (position){
            case 1:
                setTitle("Mark Attendance");
                switchView(SelectDateActivity.class);
                break;
            default:
                setTitle("Hi " + user.name);
        }
        DrawerLayout drawerLayout =(DrawerLayout)findViewById(R.id.teacherDrawerLayout);
        drawerLayout.closeDrawer(lvTeacherActions);

    }

    public void switchView(Class context){
        Intent intent = new Intent(this,context);
        startActivity(intent);
    }
}
