package com.example.andrewrxexample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.disposables.Disposable;
import io.reactivex.subscribers.DisposableSubscriber;

public class MainActivity extends AppCompatActivity {

    EditText editView;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editView = findViewById(R.id.edit_view);
        textView = findViewById(R.id.text_view);
        Disposable d = createFlowable().subscribeWith(new DisposableSubscriber<Editable>() {
            @Override
            public void onNext(Editable s) {
                textView.setText(s);
            }

            @Override
            public void onComplete() {
                Log.d("Dto", "onComplete");
            }

            @Override
            public void onError(Throwable t) {
                Log.d("Dto", "onError");
            }
        });
    }

    private Flowable<Editable> createFlowable() {
        return Flowable.create(new FlowableOnSubscribe<Editable>() {
            @Override
            public void subscribe(final FlowableEmitter<Editable> emitter) {
                editView.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        emitter.onNext(s);
                    }
                });
            }
        }, BackpressureStrategy.BUFFER);
    }
}
