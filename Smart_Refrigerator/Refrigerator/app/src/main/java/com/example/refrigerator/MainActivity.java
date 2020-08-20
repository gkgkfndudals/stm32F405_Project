package com.example.refrigerator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.fragment.app.ListFragment;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class MainActivity extends AppCompatActivity implements FragmentCallback, HomeFragment.OnApplySelectedListener, InsertFoodFragment.OnApplySelectedListenerTwo{
    public static final String sIP = "10.1.4.124";
    //사용할 통신 포트
    public static final int sPORT = 9999;

    public TransmissionUDP transmissionUDP = null;

    public TextView txtView = null;
    public EditText et_msg;


    BottomNavigationView bottomNavigationView;
    FragmentManager manager;
    Fragment fragment;
    FrameLayout frameLayout;
    Toolbar toolbar;

    HomeFragment homeFragment;
    InsertFoodFragment insertFoodFragment;
    ListViewFragment listViewFragment;

    static String flag = "0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
////////////////////////////////////////////////////////////////
        Log.d("main flag: ", flag);


        ///////////////////////////////////////////////////////

        homeFragment = new HomeFragment();
        listViewFragment = new ListViewFragment();
        insertFoodFragment = new InsertFoodFragment();
        //fragment = getSupportFragmentManager().findFragmentById(R.id.map);

        frameLayout = findViewById(R.id.main_layout);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        bottomNavigationView = findViewById(R.id.bottomNavigationView); //프래그먼트 생성

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                int itemId = menuItem.getItemId();
                switch (itemId) {
                    case R.id.tab1: {
                        onFragmentSelected(0, null);
                        break;
                    }
                    case R.id.tab2: {
                        onFragmentSelected(1, null);
                        break;
                    }
                    case R.id.tab3: {
                        onFragmentSelected(2, null);
                        break;
                    }
                }
                return true;
            }
        });


        onFragmentSelected(0, null);


    }


    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    class TransmissionUDP extends Thread {
        public void run() {
            try {
                //UDP 통신용 소켓 생성
                Log.d("thread flag", "hi1");
                DatagramSocket socket = new DatagramSocket();
                Log.d("thread flag", "hi2");
                //서버 주소 변수
                InetAddress serverAddr = InetAddress.getByName(sIP);
                Log.d("thread flag", "hi3");

                //보낼 데이터 생성
                //byte[] buf = ("Hello World").getBytes();

                byte[] buf = flag.getBytes();
                Log.d("thread flag", String.valueOf(buf));
                //패킷으로 변경
                DatagramPacket packet = new DatagramPacket(buf, buf.length, serverAddr, sPORT);
                Log.d("thread flag", "hi4");

                //패킷 전송!
                socket.send(packet);
                Log.d("thread flag", "hi5");
                //데이터 수신 대기
                socket.receive(packet);
                Log.d("thread flag", "hi6");
                //데이터 수신되었다면 문자열로 변환
                String msg = new String(packet.getData());

                Log.d("msg flag", msg);
            }catch (Exception e){

            }
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public void onFragmentSelected(int position, Bundle bundle) {
        manager = getSupportFragmentManager();
        if (position == 0) {
            frameLayout.setVisibility(View.VISIBLE);
//            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//            ft.commit();
            manager.beginTransaction().replace(R.id.main_layout, homeFragment).commit();
            //viewPager.setCurrentItem(2);
            toolbar.setTitle("냉장고를 부탁해");
        } else if (position == 1) {
            frameLayout.setVisibility(View.VISIBLE);
            manager.beginTransaction().replace(R.id.main_layout, insertFoodFragment).commit();
            toolbar.setTitle("제품 추가");
        } else if (position == 2) {
            frameLayout.setVisibility(View.VISIBLE);
//            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//            ft.commit();
            manager.beginTransaction().replace(R.id.main_layout, listViewFragment).commit();

            toolbar.setTitle("제품 현황");
        }
    }

    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_layout, fragment).commit();      // Fragment로 사용할 MainActivity내의 layout공간을 선택합니다.
    }

    @Override
    public void onCatagoryApplySelected(String str) {
        flag = str;

        Log.d("onSelected flag: ", flag);
        transmissionUDP = new TransmissionUDP();
        transmissionUDP.start();
    }

    @Override
    public void onCatagoryApplySelectedTwo(String str) {
        flag = str;

        Log.d("onSelected flag: ", flag);
        transmissionUDP = new TransmissionUDP();
        transmissionUDP.start();
    }


}