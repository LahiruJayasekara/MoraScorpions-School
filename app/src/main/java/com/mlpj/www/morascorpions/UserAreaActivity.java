package com.mlpj.www.morascorpions;

//import android.app.Fragment;
import android.app.ProgressDialog;
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
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserAreaActivity extends AppCompatActivity {

    private NavigationView mNavigationView;
    private User mCurrentUser;
    private UserLocalStore mUserLocalStore;
    private CircleImageView mProfilePic;
    private TextView mProfileName;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private MenuItem mPreviousMenuItem;
    private ProgressDialog mProgressDialog;
    private int flag = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_area);


        mUserLocalStore = new UserLocalStore(this);
        mCurrentUser = mUserLocalStore.getUserDetails();
        Log.d("RoleName", mCurrentUser.getRoleName());

        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer);
        mToggle = new ActionBarDrawerToggle(this,mDrawerLayout,R.string.open,R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mNavigationView = (NavigationView)findViewById(R.id.navigationView);
        setNavigationViewMenu(mCurrentUser.getRoleName());
        mProfileName = (TextView) mNavigationView.getHeaderView(0).findViewById(R.id.profileName);
        mProfileName.setText(mCurrentUser.getName());
        mProfilePic = (CircleImageView) mNavigationView.getHeaderView(0).findViewById(R.id.circleImageProfile);
        if(!mCurrentUser.getPicUrl().equals("")){
            Picasso.with(this).load(mCurrentUser.getPicUrl()).into(mProfilePic);
        }

        if(mCurrentUser.getRoleName().equals("Teacher")){
            Menu menu = mNavigationView.getMenu();
            MenuItem menuItem = menu.findItem(R.id.teacherClasses);
            final Menu menu1 = menuItem.getSubMenu();


            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setTitle("Fetching Data...");
            mProgressDialog.setMessage("Please wait...");
            mProgressDialog.show();


            Retrofit.Builder builder = new Retrofit.Builder()
                    //.baseUrl(getString(R.string.base_url_localhost))       //localhost
                    .baseUrl("http://sclmanagement.azurewebsites.net/")    //remote localhost
                    .addConverterFactory(GsonConverterFactory.create());
            Retrofit retrofit = builder.build();

            ApiClient client = retrofit.create(ApiClient.class);
            Call<ArrayList<ClassSubjectOfTeacher>> call =  client.getSubjectClassesOfTeacher(mCurrentUser.getP_Id());

            call.enqueue(new Callback<ArrayList<ClassSubjectOfTeacher>>() {
                @Override
                public void onResponse(Call<ArrayList<ClassSubjectOfTeacher>> call, Response<ArrayList<ClassSubjectOfTeacher>> response) {
                    mProgressDialog.dismiss();
                    List<ClassSubjectOfTeacher> classSubjectList = new ArrayList<>();
                    classSubjectList = response.body();
                    for(int i = 0; i < classSubjectList.size(); i++){
                        menu1.add(Menu.NONE,classSubjectList.get(i).getId(),Menu.NONE, classSubjectList.get(i).getClassRoomName() + " " + classSubjectList.get(i).getSubjectName()).setIcon(R.drawable.ic_navigate_next);
                    }

                }

                @Override
                public void onFailure(Call<ArrayList<ClassSubjectOfTeacher>> call, Throwable t) {
                    mProgressDialog.dismiss();
                    Toast.makeText(UserAreaActivity.this, "Error " + t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });

        }


        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;
                switch (item.getItemId()){
                    case R.id.teacherHome:
                        fragment = new TeacherHomeFragment();
                        break;
                    case R.id.teacherMarkAttendance:
                        fragment = new TeacherMarkAttendanceFragment();
                        break;
                    case R.id.teacherChat:
                        fragment = new TeacherChatFragment();
                        break;
                    case R.id.teacherProfile:
                        fragment = new TeacherProfileFragment();
                        break;
                    case R.id.parentHome:
                        fragment = new ParentHomeFragment();
                        break;
                    case R.id.parentProfile:
                        fragment = new ParentProfileFragment();
                        break;
                    case R.id.parentAttendanceView:
                        fragment = new ParentAttendanceViewFragment();
                        break;
                    case R.id.parentChat:
                        fragment = new ParentChatFragment();
                        break;
                    case R.id.parentHw:
                        fragment = new HwViewFragment();
                        break;
                    case R.id.studentHome:
                        fragment = new StudentHomeFragment();
                        break;
                    case R.id.studentProfile:
                        fragment = new StudentProfileFragment();
                        break;
                    case R.id.studentHw:
                        fragment = new HwViewFragment();
                        break;
                    default:
                        fragment = new NotesAndHwFragment();
                        Bundle bundle = new Bundle();
                        bundle.putInt("ternaryId", item.getItemId());
                        fragment.setArguments(bundle);
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
            case "Student":
                mNavigationView.inflateMenu(R.menu.menu_student_navigation_drawer);
                fragment = new StudentHomeFragment();
                break;
            case "Parent":
                mNavigationView.inflateMenu(R.menu.menu_parent_navigation_drawer);
                fragment = new ParentHomeFragment();
                break;
            case "Principal":
                mNavigationView.inflateMenu(R.menu.menu_principal_navigation_drawer);
                break;
            case "Teacher":
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
