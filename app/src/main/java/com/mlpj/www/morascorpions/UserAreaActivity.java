package com.mlpj.www.morascorpions;

//import android.app.Fragment;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserAreaActivity extends AppCompatActivity {

    private NavigationView mNavigationView;
    private User mCurrentUser;
    private UserLocalStore mUserLocalStore;
    private CircleImageView mProfilePic;
    private TextView mProfileName;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private MenuItem mPreviousMenuItem;
    private int flag = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_area);

        mUserLocalStore = new UserLocalStore(this);
        mCurrentUser = mUserLocalStore.getUserDetails();

        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer);
        mToggle = new ActionBarDrawerToggle(this,mDrawerLayout,R.string.open,R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mNavigationView = (NavigationView)findViewById(R.id.navigationView);
        setNavigationViewMenu(mCurrentUser.userType);
        mProfileName = (TextView) mNavigationView.getHeaderView(0).findViewById(R.id.profileName);
        mProfileName.setText(mCurrentUser.getName());
        mProfilePic = (CircleImageView) mNavigationView.getHeaderView(0).findViewById(R.id.circleImageProfile);
        Picasso.with(this).load("https://www.sonypark360.net/wp-content/uploads/2017/08/profile-pictures.png").into(mProfilePic);

        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;
                switch (item.getItemId()){
                    case R.id.parentHome:
                        fragment = new ParentHomeFragment();
                        break;
                    case R.id.teacherHome:
                        fragment = new TeacherHomeFragment();
                        break;
                    case R.id.teacherMarkAttendance:
                        fragment = new TeacherMarkAttendanceFragment();
                        break;
                    case R.id.teacherChat:
                        fragment = new TeacherChatFragment();
                        break;
                    case R.id.parentChat:
                        fragment = new TeacherChatFragment();
                        break;
                }

                FragmentManager fragmentManager = getSupportFragmentManager();

                fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                fragmentManager.beginTransaction().replace(R.id.fragmentContainer,fragment).commit();

                if(flag == 0){
                    item.setChecked(true);
                    mPreviousMenuItem =item;
                    flag++;
                }else{
                    mPreviousMenuItem.setChecked(false);
                    item.setChecked(true);
                    mPreviousMenuItem = item;
                }

                mDrawerLayout.closeDrawers();
                return true;
            }
        });
    }

    private void setNavigationViewMenu(String userType) {
        Fragment fragment = null;
        switch (userType){
            case "s":
                mNavigationView.inflateMenu(R.menu.menu_student_navigation_drawer);
                break;
            case "pa":
                mNavigationView.inflateMenu(R.menu.menu_parent_navigation_drawer);
                fragment = new ParentHomeFragment();
                break;
            case "pr":
                mNavigationView.inflateMenu(R.menu.menu_principal_navigation_drawer);
                break;
            case "t":
                mNavigationView.inflateMenu(R.menu.menu_teacher_navigation_drawer);
                fragment = new TeacherHomeFragment();
                break;
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragmentContainer,fragment).commit();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(mToggle.onOptionsItemSelected(item)){
            return true;
        }
        switch (item.getItemId()){
            case R.id.action_LogOut:
                mUserLocalStore.clearUser();
                mUserLocalStore.setUserLoggedIn(false);
                Intent intent = new Intent(this,LoginActivity.class);
                startActivity(intent);
                finish();

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Rect viewRect = new Rect();
        mNavigationView.getGlobalVisibleRect(viewRect);


        if (!viewRect.contains((int) ev.getRawX(), (int) ev.getRawY())) {
            mDrawerLayout.closeDrawers();
        }
        return super.dispatchTouchEvent(ev);
    }


}
