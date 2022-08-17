package api.request.reactive.basic;


import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.auditing.DateTimeProvider;

import java.time.LocalDateTime;

@Slf4j
public class CreationExample {
    public static void main(String[] args) throws InterruptedException {
        single();
        Thread.sleep(3000L);
    }

    public static void single(){

        Single<String> single = Single.create(emitter->{
            emitter.onSuccess(String.valueOf(LocalDateTime.now()));
        });

        single.subscribe(new SingleObserver<String>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onSuccess(@NonNull String s) {
                log.info("date ={}", s);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                log.info("error ={}", e);
            }
        });
    }


}
