package com.example.testlinkedblockingqueue;

import java.util.concurrent.LinkedBlockingQueue;

import android.util.Log;

public class TestHelper implements Runnable
{
    private final static String TAG = TestHelper.class.getSimpleName();

    private LinkedBlockingQueue<ReqBean> tasksQueue = new LinkedBlockingQueue<ReqBean>();

    private boolean isRun = false;

    private ReqBean currentBean = null;

    public TestHelper()
    {
        Log.i(TAG, "new TestHelper()");
        new Thread(this).start();
    }

    public void add(ReqBean task)
    {
        Log.i(TAG, "add()");
        tasksQueue.add(task);
    }

    public void pause()
    {
        Log.i(TAG, "pause()");
        isRun = false;
    }

    public void resume()
    {
        Log.i(TAG, "resume()");
        isRun = true;
        synchronized (currentBean)
        {
            currentBean.notifyAll();
        }
    }

    @Override
    public void run()
    {
        isRun = true;
        try
        {
            while ((currentBean = tasksQueue.take()) != null)
            {
                if (!isRun)
                {
                    synchronized (currentBean)
                    {
                        currentBean.wait();
                    }
                }
                Thread.sleep(1000);
                Log.i(TAG, "run sleep(1000)" + currentBean.getUrl());

            }
        } catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }

}
