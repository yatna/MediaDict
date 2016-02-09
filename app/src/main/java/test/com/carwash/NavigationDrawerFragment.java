package test.com.carwash;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

//import android.widget.Toolbar;


/**
 * A simple {@link Fragment} subclass.
 */
public class NavigationDrawerFragment extends Fragment implements VivzAdapter.ClickListener {
    public static final String PREF_FILE_NAME="testpref";
    public static final String KEY_USER_LEARNED_DRAWER="user_learned_drawer";
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private View containerView;
    private boolean isDrawerOpened=false;
    private boolean mUserLearnedDrawer;
    String username,phone;
    private String MY_PREFS_NAME="DEFAULT";
    private boolean mFromSavedInstanceState;
    private RecyclerView recyclerView;
//String name,phone;
   private VivzAdapter adapter;
    UserLocalStore userLocalStore;

 /*   public void storeUserDetails(String username, String phone) {
        this.username=username;
        this.phone=phone;
    }*/


    public static List<Information> getData(){

        List<Information> data=new ArrayList<>();
        String[] titles={"Profile","Booking History","Payment","Promotions","Wallet","About Us","Help"};
        int[] icons={R.drawable.usr,R.drawable.history,R.drawable.payment,R.drawable.promotion,R.drawable.wallet,R.drawable.about,R.drawable.help};
        for(int i=0;i<100 && i<icons.length;i++)
        {
            Information current =new Information();
            current.iconId=icons[i%icons.length];
            current.title=titles[i% titles.length];

            data.add(current);

        }

        return data;

    }

public void onCreate(Bundle savedInstancestate){
    super.onCreate(savedInstancestate);


    System.out.println(username + "()()()(()()()()()()())" + phone);
    mUserLearnedDrawer= Boolean.valueOf(readFromPreferences(getActivity(), KEY_USER_LEARNED_DRAWER, "false"));
    if(savedInstancestate!=null) {
        mFromSavedInstanceState = true;


    }


}




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        SharedPreferences pref= getActivity().getSharedPreferences("s",Context.MODE_PRIVATE);
        userLocalStore = new UserLocalStore(this.getActivity());
        User user = userLocalStore.getLoggedInUser();
      username=pref.getString("Name","Sorry");
        phone= pref.getString("Phone","No_phone");
        View layout=inflater.inflate(R.layout.fragment_navigation_drawer, container, false);
        recyclerView=(RecyclerView)layout.findViewById(R.id.drawerList);
        //Intent sender=getIntent();
        if (userLocalStore.getLoggedInUser() == null) {
            adapter = new VivzAdapter(getActivity(), getData(), username, phone);
        }
        else
        {
            adapter = new VivzAdapter(getActivity(), getData(), user.username, user.phone);
        }
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return layout;


    }



    public  void setup(int fragmentId,DrawerLayout drawerLayout, final Toolbar toolbar) {

        containerView=getActivity().findViewById(fragmentId);
        mDrawerLayout=drawerLayout;
        mDrawerToggle=new ActionBarDrawerToggle(getActivity(),drawerLayout,toolbar,R.string.drawer_open,R.string.drawer_closed){
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                if(!mUserLearnedDrawer){
                    mUserLearnedDrawer=true;
                    saveToPreferences(getActivity(),KEY_USER_LEARNED_DRAWER,mUserLearnedDrawer+"");

                }
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getActivity().invalidateOptionsMenu();
            }

            public void onDrawerSlide(View drawerView,float slideOffset){
                super.onDrawerSlide(drawerView, slideOffset);
                if(getActivity().getClass()==MainActivity.class){
                    ((MainActivity)getActivity()).onDrawerSlide(slideOffset);
                    toolbar.setAlpha(1 - slideOffset / 2);
                }
                if(getActivity().getClass()==AfterAdv.class){
                    ((AfterAdv)getActivity()).onDrawerSlide(slideOffset);
                    toolbar.setAlpha(1 - slideOffset / 2);
                }



            }
        };
        if(!mUserLearnedDrawer && !mFromSavedInstanceState){
            mDrawerLayout.openDrawer(containerView);
        }

        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerLayout.post(new Runnable() {
            public void run() {
                mDrawerToggle.syncState();
            }
        });
    }
    public static void saveToPreferences(Context context, String preferenceName, String preferenceValue){
             SharedPreferences sharedPreferences=context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
             SharedPreferences.Editor editor=sharedPreferences.edit();
            editor.putString(preferenceName, preferenceValue);
              editor.apply();
          }
     public static String readFromPreferences(Context context, String preferenceName, String defaultValue){
                SharedPreferences sharedPreferences=context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
                return sharedPreferences.getString(preferenceName,defaultValue);
           }

    @Override
    public void itemClicked(View view, int position) {
        switch(position){
            case 0: startActivity(new Intent(getActivity(),Profile.class));
                break;
            case 1:startActivity(new Intent(getActivity(),History.class));
                break;
            case 2: startActivity(new Intent(getActivity(),CardNumber.class));
                break;
            case 3: startActivity(new Intent(getActivity(),Promotion.class));
                break;
            case 5: startActivity(new Intent(getActivity(),About_Us.class));
                break;
            case 6: startActivity(new Intent(getActivity(),Help.class));
                break;

        }


    }


}
