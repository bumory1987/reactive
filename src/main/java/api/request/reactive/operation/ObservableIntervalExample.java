package api.request.reactive.operation;


import ch.qos.logback.core.util.TimeUtil;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.functions.Consumer;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.logging.Logger;

@Slf4j
public class ObservableIntervalExample {


    public static void intervalMan(){
        Observable.interval(0L, 1000L, TimeUnit.MILLISECONDS)
                .map(num-> num +"_count")
                .subscribe(data->{
                    log.info("next ={}", data);
                });
    }

    public static void range(){
        Observable.range(0, 5).subscribe(num-> log.info("data ={}", num));

    }

    public static void timer(){
        log.info("date ={}", LocalDateTime.now());
        Observable<String > observable = Observable.timer(20000, TimeUnit.MILLISECONDS)
                .map(count-> "do work");
        observable.subscribe(data-> log.info("data ={}", data));

    }


    public static void deferMan() throws InterruptedException {
        Observable<LocalDateTime> observable = Observable.defer(()->{
            LocalDateTime current = LocalDateTime.now();
            return Observable.just(current);
        });

        Observable<LocalDateTime> other = Observable.just(LocalDateTime.now());
        observable.subscribe(time-> log.info("time defer one ={}" ,time));
        other.subscribe(time-> log.info("time original one ={}" ,time));

        Thread.sleep(10000);
        observable.subscribe(time-> log.info("time defer two ={}" ,time));
        other.subscribe(time-> log.info("time original two ={}" ,time));


    }

    public static void testSequence() throws InterruptedException {
        Observable<LocalDateTime> observable = Observable.defer(()->{
            LocalDateTime current = LocalDateTime.now();
            return Observable.just(current);
        });

        List<io.reactivex.rxjava3.functions.Consumer> functionsMan = new ArrayList<>();
        functionsMan.add((io.reactivex.rxjava3.functions.Consumer<LocalDateTime>) (time)->log.info("test1 ={}", time));
        functionsMan.add((io.reactivex.rxjava3.functions.Consumer<LocalDateTime>) (time)->log.info("test2 ={}", time));
        functionsMan.add((io.reactivex.rxjava3.functions.Consumer<LocalDateTime>) (time)->log.info("test3 ={}", time));


//        Consumer consumer = functionsMan.get(0);
//        observable.subscribe(consumer);



        functionsMan.stream().forEach(itemFunc-> {
            observable.subscribe( itemFunc);
            try {
                TimeUnit.MILLISECONDS.sleep(1000L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });



    }


    public static void main(String[] args) throws InterruptedException {
        testSequence();
        Thread.sleep(30000L);
    }
}
