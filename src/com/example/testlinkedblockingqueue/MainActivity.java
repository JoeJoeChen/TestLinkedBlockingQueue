package com.example.testlinkedblockingqueue;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity
{

    Button startBtn, addBtn, addAllBtn, pauseBtn, resumeBtn, stopBtn;
    TestHelper helper = null;
    OffLineDownLoadHelper loadHelper = null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        helper = new TestHelper();
        loadHelper = OffLineDownLoadHelper.getInstance();

        startBtn = (Button) findViewById(R.id.button1);
        addBtn = (Button) findViewById(R.id.button2);
        addBtn.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                helper.add(new ReqBean("add"));
            }
        });
        addAllBtn = (Button) findViewById(R.id.button6);
        addAllBtn.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                for (int i = 0; i < 20; i++)
                {
                    loadHelper.add(new ReqBean("url--" + i));
                }
            }
        });
        pauseBtn = (Button) findViewById(R.id.button3);
        pauseBtn.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
//                helper.pause();
                loadHelper.Pause();
            }
        });
        resumeBtn = (Button) findViewById(R.id.button4);
        resumeBtn.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                helper.resume();
            }
        });
        stopBtn = (Button) findViewById(R.id.button5);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    // @Override
    // public void onClick(View v)
    // {
    // if (v == startBtn)
    // {
    // if (helper == null)
    // helper = new TestHelper();
    // }
    // else if (v == addBtn)
    // {
    // helper.add(new ReqBean("add"));
    // }
    // else if (v == addAllBtn)
    // {
    // for (int i = 0; i < 20; i++)
    // {
    // helper.add(new ReqBean("url--" + i));
    // }
    // }
    // else if (v == pauseBtn)
    // {
    // helper.pause();
    // }
    // else if (v == resumeBtn)
    // {
    // helper.resume();
    // }
    // else if (v == stopBtn)
    // {
    //
    // }
    // }
}
