package api.request.reactive.operation;


import io.reactivex.rxjava3.core.Observable;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

@Slf4j
public class AggregationFunc {
    public static void count() {
        Observable.just(1,2,3,4,5,6,7,8,9)
                .count()
                .subscribe(data -> log.info("data ={}", data));
    }
    public static void countTwo(){
        Observable.concat(
                Arrays.asList(
                Observable.just(1,2,3,4,5,6,7,8,9),
                Observable.just(1,2,8,9),
                Observable.just(1,2,3,4,5,6,7)
                ))
                .count()
                .subscribe(data -> log.info("data ={}", data));

    }


    public static void reduce(){
        Observable.just(1,2,3,4,5,6,7,8,9,10)
                .doOnNext(data -> log.info("data ={}", data))
                .reduce(10,(x,y) -> x+y)
                .subscribe(data -> log.info("final ={}", data));
    }


    public static void scan(){
        Observable.just("a", "b", "c", "d", "e")
                .doOnNext(data -> log.info("data ={}", data))
                .scan((x,y)-> "(" +x +", "+y+")")
                .subscribe(data -> log.info("final ={}", data));
    }




    public static void main(String[] args) throws InterruptedException {
        scan();
        Thread.sleep(10000L);
    }
}
