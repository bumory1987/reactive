package api.request.reactive.operation;

import io.reactivex.rxjava3.core.Observable;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

@Slf4j
public class MergeMan {

    public static void merge(){
        Observable<Long> take = Observable.interval(200L, TimeUnit.MILLISECONDS)
                .take(5);
        Observable<Long> map = Observable.interval(400L, TimeUnit.MILLISECONDS)
                .take(5)
                .map(num -> num + 1000);
        Observable.merge(take, map).subscribe(data -> log.info("data ={}", data));



    }


    public static void concat(){
        Observable<Long> take = Observable.interval(500L, TimeUnit.MILLISECONDS)
                .take(4);
        Observable<Long> map = Observable.interval(300L, TimeUnit.MILLISECONDS)
                .take(5)
                .map(num -> num + 1000);
        Observable.concat(take, map).subscribe(data -> log.info("data ={}", data));

    }



    public static void zip(){
        Observable<Long> take = Observable.interval(200L, TimeUnit.MILLISECONDS)
                .take(4);
        Observable<Long> map = Observable.interval(400L, TimeUnit.MILLISECONDS)
                .take(6);
        Observable.zip(take, map, (d1 ,d2)->{
            return d1+d2;
        }).subscribe(data -> log.info("data ={}", data));

    }


    public static void combineLatest(){
        Observable<Long> take = Observable.interval(700L, TimeUnit.MILLISECONDS)
                .take(4);
        Observable<Long> map = Observable.interval(1000L, TimeUnit.MILLISECONDS)
                .take(4);
        Observable.combineLatest(take, map, (d1 ,d2)->{

            return "data1 => " + d1 + ", " +"data2 =>" +d2;
        }).subscribe(data -> log.info("data ={}", data));

    }

    public static void main(String[] args) throws InterruptedException {
        combineLatest();
        Thread.sleep(100000L);
    }
}
