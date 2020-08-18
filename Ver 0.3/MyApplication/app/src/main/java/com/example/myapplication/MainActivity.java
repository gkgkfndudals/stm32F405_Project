package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class MainActivity extends AppCompatActivity {
    public static final String sIP = "10.1.4.124";
    //사용할 통신 포트
    public static final int sPORT = 9999;

    //데이터 보낼 클랙스
    public SendData mSendData = null;

    //화면 표시용 TextView
    public TextView txtView = null;
    public EditText et_msg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button btnHello = (Button) findViewById(R.id.Hello);
        //txtView를 layout의 TextView와 연결
        txtView = (TextView) findViewById(R.id.textView);
        et_msg = findViewById(R.id.et_msg);

        //버튼이 눌렸다면
        btnHello.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //SendData 클래스 생성
                mSendData = new SendData();
                //보내기 시작
                mSendData.start();
            }
        });
    }

    class SendData extends Thread{
        public void run(){
            try{
                //UDP 통신용 소켓 생성
                DatagramSocket socket = new DatagramSocket();
                //서버 주소 변수
                InetAddress serverAddr = InetAddress.getByName(sIP);

                //보낼 데이터 생성
                //byte[] buf = ("Hello World").getBytes();
                Log.d("et_msg", et_msg.getText().toString());
                byte[] buf = et_msg.getText().toString().getBytes();
                //패킷으로 변경
                DatagramPacket packet = new DatagramPacket(buf, buf.length, serverAddr, sPORT);

                //패킷 전송!
                socket.send(packet);

                //데이터 수신 대기
                socket.receive(packet);
                //데이터 수신되었다면 문자열로 변환
                String msg = new String(packet.getData());

                //txtView에 표시
                txtView.setText(msg);
            }catch (Exception e){

            }
        }
    }
}