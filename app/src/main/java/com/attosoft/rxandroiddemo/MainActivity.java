package com.attosoft.rxandroiddemo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.util.AsyncListUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.functions.Func1;

public class MainActivity extends AppCompatActivity {

    private final static String TAG = "MainActivity";

    @Bind(R.id.recyclerView)
    RecyclerView mRecyclerView;

    @Bind(R.id.swipe_container)
    SwipeRefreshLayout mSwipeRefreshLayout;

    AppAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mSwipeRefreshLayout.setOnRefreshListener(this::refreshList);
        mAdapter = new AppAdapter();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private Observable<AppInfo> getApps(Activity context){
        return Observable.create(subscriber -> {
            List<AppInfo> apps = new ArrayList<AppInfo>();

            final Intent mainItent = new Intent(Intent.ACTION_MAIN, null);
            mainItent.addCategory(Intent.CATEGORY_LAUNCHER);

            List<ResolveInfo> infos = context.getPackageManager().queryIntentActivities(mainItent, 0);

            for (ResolveInfo info : infos) {
                apps.add(new AppInfo(info.activityInfo.name,
                        info.activityInfo.flags, info.activityInfo.parentActivityName));
            }

            for (AppInfo info : apps) {
                if (subscriber.isUnsubscribed()) {
                    return;
                }

                subscriber.onNext(info);
            }

            if (!subscriber.isUnsubscribed()) {
                subscriber.onCompleted();
            }
        });
    }

    private void refreshList(){
//        getApps(this).toSortedList()
//                .subscribe(new Observer<List<AppInfo>>() {
//                    @Override
//                    public void onCompleted() {
//                        Toast.makeText(getApplication(),"Here is the List!",
//                                Toast.LENGTH_SHORT).show();
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        Toast.makeText(getApplication(),"Something went wrong!",
//                                Toast.LENGTH_SHORT).show();
//                        mSwipeRefreshLayout.setRefreshing(false);
//                    }
//
//                    @Override
//                    public void onNext(List<AppInfo> appInfos) {
//                        mRecyclerView.setVisibility(View.VISIBLE);
//                        mAdapter.addApps(appInfos);
//                        mAdapter.notifyDataSetChanged();
//                        mSwipeRefreshLayout.setRefreshing(false);
//                    }
//                });
//        Observable
//                .from(getAppList())
////                .filter(info -> info.getName().startsWith("Q"))
//                .distinct()
//                .map(info -> {
//                        info.setName(info.getName().toLowerCase());
//                        return info;
//                })
//                .just(getAppList().get(0),getAppList().get(1),getAppList().get(2))
//                .repeat(3)
//                .subscribe(getObserver());
        Observable<AppInfo> observable = Observable.from(getAppList());
        List<AppInfo>  appList= getAppList();
        Collections.reverse(appList);
        Observable<AppInfo> observable1 = Observable.from(appList);
        Observable.merge(observable1,observable).subscribe(appinfo -> {
           mAdapter.addApplication(appinfo);
            mAdapter.notifyDataSetChanged();
        });

//        Observable.just(1,2,3,4,5,6)
//                .scan((sum,item)->sum+item)
//                .subscribe(item -> Log.d(TAG,"item is " + item));

//        Observable.range(10, 3)
//                .subscribe(number ->{
//                    Log.d(TAG,"I say :" + number);
//                    Toast.makeText(getApplicationContext(),"I say :" + number,Toast.LENGTH_SHORT).show();
//                });

//        Observable.interval(3, TimeUnit.SECONDS)
//                .subscribe(number ->{
//                    Log.d(TAG,"I say " + number);
////                    Toast.makeText(getApplicationContext(),"I say :" + number,Toast.LENGTH_SHORT).show();
//                });
//        Observable.timer(3,3,TimeUnit.SECONDS)
//                .subscribe(number ->{
//                    Log.d(TAG,"I say " + number);
//                });
    }

    private Observable<Integer> getInt(){
        return Observable.create(subscriber -> {
            if(subscriber.isUnsubscribed()){
                return;
            }
            Log.d(TAG,"GetInt");
            subscriber.onNext(42);
            subscriber.onCompleted();
        });
    }

    private Observer<AppInfo> getObserver(){
        return new Observer<AppInfo>() {
            @Override
            public void onCompleted() {
                Toast.makeText(getApplication(),"Here is the List!",
                        Toast.LENGTH_SHORT).show();
                mSwipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(getApplication(),"Something went wrong!",
                        Toast.LENGTH_SHORT).show();
                mSwipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onNext(AppInfo info) {
//                        Toast.makeText(getApplication(),"onNext",
//                                Toast.LENGTH_SHORT).show();
                mAdapter.addApplication(info);
                mAdapter.notifyItemInserted(mAdapter.getItemCount());
            }

        };
    }

    private List<AppInfo> getAppList(){
        List<AppInfo> appInfoList = new ArrayList<>();

        appInfoList.add(new AppInfo("微信",1988,""));
        appInfoList.add(new AppInfo("QQ",1988,""));
        appInfoList.add(new AppInfo("云之家",1988,""));

        return appInfoList;
    }
}
