package com.sys1yagi.android.rxrecyclerview_load_more;

import com.sys1yagi.android.rxrecyclerview_load_more.databinding.ActivityMainBinding;
import com.sys1yagi.android.rxrecyclerview_load_more.model.Item;
import com.sys1yagi.android.rxrecyclerview_load_more.rx.RxRecyclerViewScrollSubject;
import com.sys1yagi.android.rxrecyclerview_load_more.view.ItemAdapter;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

import ix.Ix;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.Subscriptions;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    RxRecyclerViewScrollSubject rxRecyclerViewScrollSubject = new RxRecyclerViewScrollSubject();

    Subscription subscription = Subscriptions.empty();

    ItemAdapter adapter = new ItemAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

        binding.recyclerView.setAdapter(adapter);
        binding.recyclerView.setLayoutManager(linearLayoutManager);
        load();
    }

    void sleep(long time) {
        try {
            Thread.sleep(time);
        } catch (Exception e) {
            //no op
        }
    }

    void load() {
        Observable
                .<List<Item>>create(subscriber -> {
                    sleep(1000);
                    List<Item> items = new ArrayList<>();
                    Ix.range(adapter.getItemCount(), 10).forEach(i -> {
                        Item item = new Item("title=" + i);
                        items.add(item);
                    });
                    subscriber.onNext(items);
                    subscriber.onCompleted();
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(items -> {
                    adapter.addAll(items);
                    loadWhenLastPosition();
                });
    }

    void loadWhenLastPosition() {
        subscription = rxRecyclerViewScrollSubject.observable().subscribe(event -> {
            subscription.unsubscribe();
            load();
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        rxRecyclerViewScrollSubject
                .start(binding.recyclerView, (LinearLayoutManager) binding.recyclerView.getLayoutManager());
    }

    @Override
    protected void onPause() {
        super.onPause();
        rxRecyclerViewScrollSubject.stop();
    }
}
