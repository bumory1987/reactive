package api.request.reactive.operation;

import ch.qos.logback.core.util.TimeUtil;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.internal.operators.observable.ObservableGroupBy;
import io.reactivex.rxjava3.observables.GroupedObservable;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static api.request.reactive.operation.DataFilter.*;


@Slf4j
public class DataTransform {

    public static void first(){
        Observable.just("korea", "america", "canada", "paris", "japan", "china")
                .filter(conutry-> conutry.length() ==5 )
                .map(country-> country.toUpperCase().charAt(0)+country.substring(1))
                .subscribe(data -> log.info("data ={}", data));


    }


    public static void second(){
        Observable.just("hello")
                .flatMap(data-> Observable.just("자바", "파이썬", "안드로이드").map(lang-> data +", "+lang))
                .subscribe(data -> log.info("data ={}", data));


    }

    public static void flatMapMan(){
        Observable.range(3 ,1 )
                .flatMap(map-> Observable.range(1, 9), (source,map )->{
                    return source *map;
                })
                .subscribe(data -> log.info("data ={}", data));


    }

    public static void concatMan(){
        LocalDateTime pre = LocalDateTime.now();
        log.info("start => {}", pre);
        Observable.interval(100L, TimeUnit.MILLISECONDS)
                .take(4)
                .skip(2)
                .concatMap(num -> Observable.interval(200L, TimeUnit.MILLISECONDS)
                                    .take(10)
                                    .skip(1)
                                    .map(row -> num+"  *  " + row + " = " + num*row)
                ).subscribe(
                        data-> log.info("data ={}", data),
                        error -> {},
                        ()->{
                            LocalDateTime finalTime = LocalDateTime.now();
                            log.info("finish => {}", Thread.currentThread().getName(),finalTime);}
                );

    }



    public static void switchMans(){
        LocalDateTime pre = LocalDateTime.now();
        log.info("start => {}", pre);
        Observable.interval(100L, TimeUnit.MILLISECONDS)
                .take(4)
                .skip(2)
                .switchMap(num -> Observable.interval(300L, TimeUnit.MILLISECONDS)
                        .take(10)
                        .skip(1)
                        .map(row -> num+"  *  " + row + " = " + num*row)
                ).subscribe(
                        data-> log.info("data ={}", data),
                        error -> {},
                        ()->{
                            LocalDateTime finalTime = LocalDateTime.now();
                            log.info("finish => {}", finalTime);}
                );

    }


    public static void groupBy(){
        Observable<GroupedObservable<CarMaker, Car>> observable =
                Observable.fromIterable(carList).groupBy(car->car.getCarMaker());
        observable.subscribe(
                groupedObservable->{
                    groupedObservable
                            .filter(car -> car.getCarMaker().equals(CarMaker.CHEVORLET))
                            .subscribe(car->{
                        log.info("key= {},car name ={}",groupedObservable.getKey()
                                , car.getCarName() );
                    });
                }
        );
    }


    public static void single(){
        Single<List<Integer>> single = Observable.just(1,3,5,7,9).toList();
        single.subscribe(data-> log.info("data ={}" , data));

    }

    public static void singleTwo(){
        Observable.fromIterable(carList)
                .toList()
                .subscribe(cars -> log.info("list ={}", cars));

    }

    public static void map(){
        Single<Map<String, String>> single = Observable.just(
                                "a-alpha", "b-bravoe", "c-charlie", "e-echo")
                .toMap(
                        data-> data.split("-")[0],
                        data-> data.split("-")[1]
                );
        single.subscribe(data-> log.info("data ={}" , data));

    }



    public static void main(String[] args) throws InterruptedException {
//        groupBy();
        map();
        Thread.sleep(10000L);
    }
}
