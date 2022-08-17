package api.request.reactive.operation;

import api.request.reactive.basic.ToDoSample;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import lombok.extern.slf4j.Slf4j;

import java.sql.Time;
import java.util.concurrent.TimeUnit;
@Slf4j
public class ErrorMan {


    public static void error(){
        Observable.just(5)
                .flatMap(num->
                        Observable.interval(200L, TimeUnit.MILLISECONDS)
                                .doOnNext(data -> log.info("data ={}", data))
                                .take(5)
                                .map(i-> num/ i))
                .subscribe(
                        data-> log.info("data next = {}", data),
                        error-> log.info("error = {}", error),
                        ()-> log.info("finally")
                );
    }


    public static void onErrorReturn(){
        Observable.just(5)
                .flatMap(num->
                        Observable.interval(200L, TimeUnit.MILLISECONDS)
                                .doOnNext(data -> log.info("data ={}", data))
                                .take(5)
                                .map(i-> num/ i)
                                .onErrorReturn(
                                        exception -> {
                                            if(exception instanceof ArithmeticException){
                                                log.info("error occurs ={}", exception.getMessage());
                                            }
                                            return -1L;
                                        }))
                .subscribe(
                        data-> log.info("data next = {}", data),
                        error-> log.info("error = {}", error),
                        ()-> log.info("finally")
                );
    }



    public static void onErrorResumeNext(){
        Observable.just(5)
                .flatMap(num->
                        Observable.interval(200L, TimeUnit.MILLISECONDS)
                                .doOnNext(data -> log.info("data ={}", data))
                                .take(5)
                                .map(i-> num/ i)
                                .onErrorResumeNext(
                                        throwable -> {
                                         log.info("error ={}", throwable.getMessage());
                                         return Observable.interval(200L, TimeUnit.MILLISECONDS)
                                                 .take(5)
                                                 .skip(1)
                                                 .map(i -> num/i);

                                        }))
                .subscribe(
                        data-> log.info("data next = {}", data),
                        error-> log.info("error = {}", error),
                        ()-> log.info("finally")
                );
    }


    public static void retry(){
        Observable.just(5)
                .flatMap(num ->
                        Observable.interval(200L, TimeUnit.MILLISECONDS)
                                .map(i ->{
                                    log.info("trails");
                                     return num/i;

                                })
                                .retry(5)
                                .onErrorReturn(throwable -> -1L)
                        ).subscribe(
                                data-> log.info("data= {}" ,data),
                                error-> log.info("error ={}", error),
                                () -> log.info("finally")

                );
    }


    public static void retryFunc(){
        Observable.just(5)
                .flatMap(num ->
                        Observable.interval(200L, TimeUnit.MILLISECONDS)
                                .map(i ->{
                                    return num/i;

                                })
                                .retry((retryCount, ex)->{
                                    log.info("trial ={}", retryCount);
                                    TimeUnit.MILLISECONDS.sleep(1000L);
                                    return retryCount < 5 ? true : false;
                                })
                                .onErrorReturn(throwable -> -1L)
                ).subscribe(
                        data-> log.info("data= {}" ,data),
                        error-> log.info("error ={}", error),
                        () -> log.info("finally")

                );
    }





    public static void main(String[] args) throws InterruptedException {
        retryFunc();
        Thread.sleep(30000L);
    }
}
