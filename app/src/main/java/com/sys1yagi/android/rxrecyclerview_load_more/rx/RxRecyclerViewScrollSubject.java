package com.sys1yagi.android.rxrecyclerview_load_more.rx;

import com.jakewharton.rxbinding.support.v7.widget.RecyclerViewScrollEvent;
import com.jakewharton.rxbinding.support.v7.widget.RxRecyclerView;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import rx.Observable;
import rx.Subscription;
import rx.subjects.PublishSubject;
import rx.subjects.Subject;
import rx.subscriptions.Subscriptions;

public class RxRecyclerViewScrollSubject {

    Subject<RecyclerViewScrollEvent, RecyclerViewScrollEvent> subject = PublishSubject.create();

    Subscription subscription = Subscriptions.empty();

    public Observable<RecyclerViewScrollEvent> observable() {
        return subject;
    }

    public void start(RecyclerView recyclerView, LinearLayoutManager linearLayoutManager) {
        subscription.unsubscribe();

        subscription = RxRecyclerView.scrollEvents(recyclerView).subscribe(event -> {
            int totalItemCount = linearLayoutManager.getItemCount();
            int lastVisibleItemPosition = linearLayoutManager.findLastVisibleItemPosition();
            if (totalItemCount - 1 <= lastVisibleItemPosition) {
                subject.onNext(event);
            }
        });
    }

    public void stop() {
        subscription.unsubscribe();
    }
}
