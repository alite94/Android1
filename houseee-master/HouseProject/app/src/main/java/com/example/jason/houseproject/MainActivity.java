package com.example.jason.houseproject;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView noticeListView;
    private NoticeListAdapter adapter;
    private List<Notice> noticeList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        noticeListView = (ListView) findViewById(R.id.noticeListView);
        noticeList = new ArrayList<Notice>();
        noticeList.add(new Notice("공지사항입니다", "장성현", "2018-05-06"));
        noticeList.add(new Notice("공지사항입니다", "장성현", "2018-05-06"));
        noticeList.add(new Notice("공지사항입니다", "장성현", "2018-05-06"));
        noticeList.add(new Notice("공지사항입니다", "장성현", "2018-05-06"));
        noticeList.add(new Notice("공지사항입니다", "장성현", "2018-05-06"));
        noticeList.add(new Notice("공지사항입니다", "장성현", "2018-05-06"));
        noticeList.add(new Notice("공지사항입니다", "장성현", "2018-05-06"));
        adapter = new NoticeListAdapter(getApplicationContext(), noticeList);
        noticeListView.setAdapter(adapter);

        final Button noticeButton = (Button) findViewById(R.id.noticeButton);
        final Button mainButton = (Button) findViewById(R.id.mainButton);
        final Button angelButton = (Button) findViewById(R.id.angleButton);
        final LinearLayout notice = (LinearLayout) findViewById(R.id.notice);


        noticeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                noticeButton.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                mainButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                angelButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment, new NoticeFragment());
                fragmentTransaction.commit();
            }
        });
        mainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                noticeButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                mainButton.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                angelButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment, new MainFragment());
                fragmentTransaction.commit();
            }
        });
        angelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                noticeButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                mainButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                angelButton.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment, new AngelFragment());
                fragmentTransaction.commit();
            }
        });
    }
}