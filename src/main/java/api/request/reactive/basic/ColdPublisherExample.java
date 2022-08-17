package api.request.reactive.basic;


import ch.qos.logback.core.util.TimeUtil;
import io.reactivex.rxjava3.core.BackpressureOverflowStrategy;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import lombok.extern.slf4j.Slf4j;
import reactor.core.scheduler.Scheduler;

import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;



@Slf4j
public class ColdPublisherExample {

    public static void main(String[] args) throws InterruptedException {
        drop();
//        buffer();
//        backPressureLatest();
    }



    public static void buffer() throws RuntimeException, InterruptedException {
         Flowable.interval(1L, TimeUnit.MILLISECONDS)
                 .doOnNext(data-> log.info("generation= {}", data))
                 .onBackpressureBuffer(10,
                         ()->{ log.info("overflow");}
                         , BackpressureOverflowStrategy.DROP_OLDEST)
                 .doOnNext(data-> log.info("#onBackPressureBuffer doOnNext = {}", data))
                 .observeOn(Schedulers.computation(), false, 5)
                 .subscribe(data ->{
                     log.info("소비자 처리 대기 중 ...");
                     TimeUnit.MILLISECONDS.sleep(10);
                     log.info("data ={}", data);
                 }, error-> log.info("error ={}", error.getMessage())
                         ,()-> log.info("finally"));

        Thread.sleep(2000L);

    }


    public static void drop() throws RuntimeException, InterruptedException {
        Flowable.interval(300L, TimeUnit.MILLISECONDS)
                .doOnNext(data-> log.info("generation => {}", data))
                .onBackpressureDrop((data)->
                        log.info("dropData ==> {}", data))
                .observeOn(Schedulers.computation(), false, 1)
                .subscribe(data ->{
                            log.info("소비자 처리 대기 중 ...");
                            TimeUnit.MILLISECONDS.sleep(1000L);
                            log.info("data ={}", data);
                        }, error-> log.info("error ={}", error.getMessage())
                        ,()-> log.info("finally"));

        Thread.sleep(5500L);



    }


        public static void backPressureLatest() throws RuntimeException, InterruptedException {
        Flowable.interval(300L, TimeUnit.MILLISECONDS)
                .doOnNext(data-> log.info("generation => {}", data))
                .onBackpressureLatest()
                .observeOn(Schedulers.computation(), false, 1)
                .subscribe(data ->{
                            log.info("소비자 처리 대기 중 ...");
                            TimeUnit.MILLISECONDS.sleep(1000L);
                            log.info("data ={}", data);
                        }, error-> log.info("error ={}", error.getMessage())
                        ,()-> log.info("finally"));

        Thread.sleep(5500L);



    }
}
