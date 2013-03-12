package com.example.testlinkedblockingqueue;

import java.util.Vector;

import android.os.Handler;
import android.util.Log;

/**
 * 
 * @author zhucl-ca@c-platform.com on 2013-1-7
 */
public class OffLineDownLoadHelper
{
    private static final String TAG = OffLineDownLoadHelper.class.getSimpleName();
    private static OffLineDownLoadHelper instance = null;

    private boolean isAlive = true;
    private boolean isExcuting = false;
    private boolean isPause = false;

    private Vector<ReqBean> queue = new Vector<ReqBean>(30);
    private Object token = new Object();
    private Handler handler = new Handler();

    public static synchronized OffLineDownLoadHelper getInstance()
    {
        Log.i(TAG, "OffLineDownLoadHelper getInstance");
        if (instance == null)
        {
            instance = new OffLineDownLoadHelper();
        }
        return instance;
    }

    private OffLineDownLoadHelper()
    {
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                while (isAlive)
                {
                    try
                    {
                        if (queue == null && queue.isEmpty())
                        {
                            Log.i(TAG, "OffLineDownLoadHelper wait()");
                            token.wait();
                        }
                        else
                        {
                            if (!isExcuting)
                            {
                                if (queue != null && !queue.isEmpty())
                                {
                                    isExcuting = true;
                                    ReqBean bean = queue.remove(0);
                                    Log.e(TAG, "OffLineDownLoadHelper start " + bean.getUrl());
                                    if (isPause)
                                    {
                                        synchronized (token)
                                        {
                                            token.wait();
                                        }
                                    }
                                    excuteReq(bean);
                                }
                            }
                        }
                    } catch (Exception e)
                    {
                        Log.e(TAG, "OffLineDownLoadHelper Ecception ---> " + e.toString());
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    private void excuteReq(final ReqBean bean)
    {

    }

    /**
     * 
     */
    public synchronized void add(ReqBean bean)
    {
        isAlive = true;
        Log.i(TAG, "OffLineDownLoadHelper addRequest");
        if (bean == null)
        {
            return;
        }
        try
        {
            if (queue.contains(bean) || queue.contains(bean))
            {
                return;
            }
            queue.add(bean);

            synchronized (token)
            {
                token.notify();
            }
        } catch (Exception e)
        {
            Log.e(TAG, "OffLineDownLoadHelper addRequest Exception ---> " + e.toString());
            e.printStackTrace();
        }
    }

    public void Pause()
    {
        try
        {
            Log.e(TAG, "OffLineDownLoadHelper Pause");
            isPause = false;
        } catch (Exception e)
        {
            Log.e(TAG, "OffLineDownLoadHelper Pause Exception --> " + e.toString());
            e.printStackTrace();
        }
    }

    public void Resume()
    {
        try
        {
            Log.e(TAG, "OffLineDownLoadHelper Resume");
            isPause = true;
            synchronized (token)
            {
                token.notify();
            }
        } catch (Exception e)
        {
            Log.e(TAG, "OffLineDownLoadHelper Resume Exception --> " + e.toString());
            e.printStackTrace();
        }
    }

    public boolean getIsRuning()
    {
        return isAlive;
    }
}
