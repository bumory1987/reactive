package api.request.reactive.operation;


import ch.qos.logback.core.util.TimeUtil;
import io.reactivex.rxjava3.core.Observable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.NumberUtils;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;
import java.util.function.DoubleToIntFunction;
import java.util.function.DoubleToLongFunction;

@Slf4j
public class ExtraOperator {
    public static void delay(){
        log.info(LocalDateTime.now().toString());
        Observable.just(1,3,4,6)
                .doOnNext(data -> log.info("data ={} ", data))
                .delay(5000L, TimeUnit.MILLISECONDS)
                .subscribe(data->
                {
                    log.info(LocalDateTime.now().toString());
                    log.info("receiver ={}", data);
                });

    }

    public static void delayTwo(){
        log.info(LocalDateTime.now().toString());
        Observable.just(1,3,5,7)
//                .doOnNext(data -> log.info("data ={} ", data))
                .delay(item->{
                    TimeUnit.MILLISECONDS.sleep(2000L);
                    log.info("thread ={}", item);
                    return Observable.just(item);
                })
                .subscribe(data->
                {
                    log.info(LocalDateTime.now().toString());
                    log.info("receiver ={}", data);
                });

    }


    public static void timeOut(){
        Observable.range(1,5)
                .map(num ->{
                    long time = 1000L;
                    if( num == 4){
                        time = 1500L;
                    }
                    TimeUnit.MILLISECONDS.sleep(time);
                    return num;})
                .timeout(1200L, TimeUnit.MILLISECONDS)
                .subscribe(
                        data-> log.info("data= {}" ,data),
                        error-> log.info("error ={}", error.getMessage()),
                        () -> log.info("finally"));
    }


    public static void timeInterval(){
        Observable.just(1,3,5,7,9)
                .delay(item ->{
                    int  random = (int) Math.random()* 10000;
                    log.info("value= {}", random);
                    TimeUnit.MILLISECONDS.sleep(random);
                    return Observable.just(item);
                })
                .timeInterval()
                .subscribe(
                        data-> log.info("[{}] data= {}", data.time(),data),
                        error-> log.info("error ={}", error.getMessage()),
                        () -> log.info("finally"));
    }


    public static void delaySubscription(){
        log.info(LocalDateTime.now().toString());
        Observable.just(1,3,5,7)
                .doOnNext(data -> {
                    log.info(LocalDateTime.now().toString());
                    log.info("data ={} ", data);
                })
                .delaySubscription(5000L,TimeUnit.MILLISECONDS)
                .subscribe(data->
                {
                    log.info(LocalDateTime.now().toString());
                    log.info("receiver ={}", data);
                });

    }


    public static void materialize(){
        Observable.just(1,2,3,4,5,6)
                .materialize()
                .subscribe(
                data-> {
                    String notificationType= data.isOnNext()? "onNext()" : (data.isOnError()) ?
                            "onError!" : "onComplete";

                    log.info("notification = {}", notificationType);
                    log.info("data ={}", data.getValue());
                },
                error-> log.info("error ={}", error.getMessage()),
                () -> log.info("finally"));
    }

    //all, amb, contain , defaultIfEmpty, sequenceEqual


    public static void contain(){

    }

    public static void main(String[] args) throws InterruptedException {
        delaySubscription();
        Thread.sleep(20000L);
    }
}
