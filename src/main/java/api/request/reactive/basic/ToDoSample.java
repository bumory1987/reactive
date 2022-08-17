package api.request.reactive.basic;


import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.*;
import io.reactivex.rxjava3.disposables.DisposableContainer;
import io.reactivex.rxjava3.schedulers.Schedulers;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.Arrays;



@Slf4j
public class ToDoSample {

    public static void firstClass(){
        Observable<String> observable = Observable.just("hello", "rx", "java");
        observable.subscribe(data -> System.out.println("data = " + data));
    }

    public static void SecondClass() throws InterruptedException {
        Flowable<Object> objectFlowable = Flowable.create(new FlowableOnSubscribe<Object>() {
            @Override
            public void subscribe(@NonNull FlowableEmitter<Object> emitter) throws Throwable {
                String[] datas = {"hello", "rxjava"};
                Arrays.stream(datas).forEach(item -> {
                    if (emitter.isCancelled()) return;
                    emitter.onNext(item);
                });
                emitter.onComplete();
            }
        }, BackpressureStrategy.BUFFER);

        objectFlowable.observeOn(Schedulers.computation())
                .subscribe(new Subscriber<Object>() {
                    private Subscription subscription;
                    @Override
                    public void onSubscribe(Subscription s) {
                        this.subscription = s;
                        this.subscription.request(Long.MAX_VALUE);
                    }

                    @Override
                    public void onNext(Object o) {
                        log.info("data ={}", (String)o );
                    }

                    @Override
                    public void onError(Throwable t) {
                        log.info("error ={}", t );
                    }

                    @Override
                    public void onComplete() {
                        log.info("complete" );

                    }
                });
    }


    public static void third() throws InterruptedException {
        Subscriber<Object> complete = new Subscriber<>() {
            private Subscription subscription;

            @Override
            public void onSubscribe(Subscription s) {
                this.subscription = s;
                this.subscription.request(Long.MAX_VALUE);
            }

            @Override
            public void onNext(Object o) {
                log.info("data ={}", (String) o);
            }

            @Override
            public void onError(Throwable t) {
                log.info("error ={}", t);
            }

            @Override
            public void onComplete() {
                log.info("complete");

            }
        };

        Flowable<Object> flowable = Flowable.create(emitter -> {
            String[] datas = {"hello", "rxjava"};
            Arrays.stream(datas).forEach(item -> {
                if (emitter.isCancelled()) return;
                emitter.onNext(item);
            });
            emitter.onComplete();
        }, BackpressureStrategy.BUFFER);


        flowable.observeOn(Schedulers.computation()).subscribe(complete);

    }


    public static void main(String[] args) throws InterruptedException {
//        firstClass();

        third();
        Thread.sleep(500L);

    }

}
