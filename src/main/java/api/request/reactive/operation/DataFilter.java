package api.request.reactive.operation;


import io.reactivex.rxjava3.core.Observable;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
public class DataFilter {

    public enum CarType{
        SEDAN, SUV
    }

    public enum CarMaker{
        CHEVORLET, HYUNDAE, SSANGYOUNG, SAMSUNG;

    }

    @Getter
    public static class Car{
        public CarMaker carMaker;
        public CarType carType;
        public String carName;
        public int price;


        public Car(CarMaker carMaker, CarType carType, String carName, int price) {
            this.carMaker = carMaker;
            this.carType = carType;
            this.carName = carName;
            this.price = price;
        }
    }



    public static List<Car> carList =
            Arrays.asList(
                    new Car(CarMaker.CHEVORLET, CarType.SEDAN , "말리부", 300000 ),
                    new Car(CarMaker.SAMSUNG, CarType.SEDAN , "so", 200000 ),
                    new Car(CarMaker.SAMSUNG, CarType.SEDAN , "vone", 100000 ),
                    new Car(CarMaker.HYUNDAE, CarType.SEDAN , "A", 300000 ),
                    new Car(CarMaker.SAMSUNG, CarType.SEDAN , "B", 400000 ),
                    new Car(CarMaker.HYUNDAE, CarType.SEDAN , "c", 500000 ),
                    new Car(CarMaker.CHEVORLET, CarType.SEDAN , "d", 600000 ),
                    new Car(CarMaker.CHEVORLET, CarType.SEDAN , "e", 400000 ),
                    new Car(CarMaker.HYUNDAE, CarType.SEDAN , "f", 300000 ),
                    new Car(CarMaker.CHEVORLET, CarType.SEDAN , "볼트", 300000 ),
                    new Car(CarMaker.HYUNDAE, CarType.SEDAN , "싼타페", 100000 ),
                    new Car(CarMaker.CHEVORLET, CarType.SEDAN , "h", 200000 ),
                    new Car(CarMaker.SAMSUNG, CarType.SEDAN , "s33", 100000 )
                    );


    public static CarMaker[] carMakersList=
            {
                    CarMaker.CHEVORLET,
                    CarMaker.HYUNDAE,
                    CarMaker.HYUNDAE,
                    CarMaker.HYUNDAE,
                    CarMaker.HYUNDAE,
                    CarMaker.HYUNDAE,
                    CarMaker.HYUNDAE,
                    CarMaker.CHEVORLET,
                    CarMaker.HYUNDAE,
                    CarMaker.CHEVORLET,
                    CarMaker.SAMSUNG

            };
    public static void filter(){
        Observable.fromIterable(carList)
                .filter(car->car.getCarMaker() == CarMaker.CHEVORLET)
                .filter(car->car.price > 300000)
                .subscribe(car-> log.info("car name ={} , car maker ={}", car.getCarName(), car.carMaker));
    }


    public static void distinct(){
        Observable.fromIterable(Arrays.asList(carMakersList))
                .distinct()
                .filter(carMaker -> carMaker.equals(CarMaker.HYUNDAE))
                .subscribe(data -> log.info("data = {}", data));
    }


    public static void takeMan(){
        Observable.interval(1000L, TimeUnit.MILLISECONDS)
                .take(3500L, TimeUnit.MILLISECONDS)
                .subscribe(data-> log.info("data ={}", data));
    }



    public static void takeUntil(){
        Observable.interval(1000L, TimeUnit.MILLISECONDS)
                .takeUntil(Observable.timer(6000L, TimeUnit.MILLISECONDS))
                .subscribe(data-> log.info("data ={}", data));
    }


    public static void skip(){
        Observable.range(1, 6)
                .skip(3)
                .subscribe(data-> log.info("data ={}", data));
    }


    public static void main(String[] args) throws InterruptedException {
        skip();
        Thread.sleep(10000L);
    }

}
