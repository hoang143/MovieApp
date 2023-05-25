package com.example.mockprojectv3.viewmodel;

import androidx.annotation.MainThread;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Custom implementation of LiveData to handle events that should be observed only once.
 *
 * @param <T> The type of data to be held by this instance.
 */
public class SingleLiveEvent<T> extends MutableLiveData<T> {
    private final AtomicBoolean isPending = new AtomicBoolean(false);

    @MainThread
    @Override
    public void observe(LifecycleOwner owner, Observer<? super T> observer) {
        super.observe(owner, new Observer<T>() {
            @Override
            public void onChanged(T value) {
                if (isPending.compareAndSet(true, false)) {
                    observer.onChanged(value);
                }
            }
        });
    }

    @MainThread
    @Override
    public void setValue(T value) {
        isPending.set(true);
        super.setValue(value);
    }

    /**
     * Use this method to reset the state of the event.
     * This allows the event to be observed again after being consumed.
     */

    @MainThread
    public void reset() {
        isPending.set(false);
    }
}

