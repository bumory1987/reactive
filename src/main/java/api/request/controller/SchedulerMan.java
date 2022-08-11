package api.request.controller;

import api.request.entity.Prediction;
import api.request.entity.PredictionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.ParallelFlux;
import reactor.core.scheduler.Schedulers;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.net.URI;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class SchedulerMan {

    private final PredictionRepository predictionRepository;

    @PostConstruct
    public void doAction(){
        repeatRequest();
    }

    private final WebClient webClient;



    public void trialManTwo(String input)  {
        URI insertUri = null;
        try{
            insertUri = new URI(input);
        }catch (Exception e){
            throw new IllegalArgumentException();
        }
        webClient.get()
                .uri(insertUri)
                .retrieve()
                .bodyToMono(String.class).subscribe(result->{
                    predictionRepository.save(result);
                    log.info("result ={}", result);
                });

    }

    @Scheduled(fixedRate = 20000)
    public void repeatRequest(){
        log.info("start!!");
        List<String> uriList = new ArrayList<>();
        uriList.add("https://apis.data.go.kr/B552584/ArpltnInforInqireSvc/getMsrstnAcctoRltmMesureDnsty?serviceKey=i1z1J%2BUagwRHR2a0tWJ%2FTr8wo872kZm38a2mPjsEcACA2LDprkmPFuB5ePAG2BQ7oaBz8YruDeG%2FMk74duqtHA%3D%3D&returnType=json&numOfRows=1&pageNo=1&stationName=%EB%8F%99%EC%9E%91%EB%8C%80%EB%A1%9C%20%EC%A4%91%EC%95%99%EC%B0%A8%EB%A1%9C&dataTerm=3MONTH&ver=1.0");
        uriList.add("ttps://apis.data.go.kr/B552584/ArpltnInforInqireSvc/getMsrstnAcctoRltmMesureDnsty?serviceKey=i1z1J%2BUagwRHR2a0tWJ%2FTr8wo872kZm38a2mPjsEcACA2LDprkmPFuB5ePAG2BQ7oaBz8YruDeG%2FMk74duqtHA%3D%3D&returnType=json&numOfRows=1&pageNo=1&stationName=%EB%B3%B4%EB%9E%8C%EB%8F%99&dataTerm=DAILY&ver=1.0");
        uriList.add("https://apis.data.go.kr/B552584/ArpltnInforInqireSvc/getMsrstnAcctoRltmMesureDnsty?serviceKey=i1z1J%2BUagwRHR2a0tWJ%2FTr8wo872kZm38a2mPjsEcACA2LDprkmPFuB5ePAG2BQ7oaBz8YruDeG%2FMk74duqtHA%3D%3D&returnType=json&numOfRows=1&pageNo=1&stationName=%EC%A2%85%EB%A1%9C%EA%B5%AC&dataTerm=DAILY&ver=1.0");
        Arrays.asList(uriList.toArray()).stream().parallel().forEach(input ->
                { trialManTwo((String) input);
                } );

    }


//
//    public void repeatRequest(){
//        log.info("start!!");
//        List<String> uriList = new ArrayList<>();
//        uriList.add("https://apis.data.go.kr/B552584/ArpltnInforInqireSvc/getMsrstnAcctoRltmMesureDnsty?serviceKey=i1z1J%2BUagwRHR2a0tWJ%2FTr8wo872kZm38a2mPjsEcACA2LDprkmPFuB5ePAG2BQ7oaBz8YruDeG%2FMk74duqtHA%3D%3D&returnType=json&numOfRows=1&pageNo=1&stationName=%EB%B3%B4%EB%9E%8C%EB%8F%99&dataTerm=DAILY&ver=1.0");
//        uriList.add("https://apis.data.go.kr/B552584/ArpltnInforInqireSvc/getMsrstnAcctoRltmMesureDnsty?serviceKey=i1z1J%2BUagwRHR2a0tWJ%2FTr8wo872kZm38a2mPjsEcACA2LDprkmPFuB5ePAG2BQ7oaBz8YruDeG%2FMk74duqtHA%3D%3D&returnType=json&numOfRows=1&pageNo=1&stationName=%EC%A2%85%EB%A1%9C%EA%B5%AC&dataTerm=DAILY&ver=1.0");
//        uriList.add("ttps://apis.data.go.kr/B552584/ArpltnInforInqireSvc/getMsrstnAcctoRltmMesureDnsty?serviceKey=i1z1J%2BUagwRHR2a0tWJ%2FTr8wo872kZm38a2mPjsEcACA2LDprkmPFuB5ePAG2BQ7oaBz8YruDeG%2FMk74duqtHA%3D%3D&returnType=json&numOfRows=1&pageNo=1&stationName=%EB%B3%B4%EB%9E%8C%EB%8F%99&dataTerm=DAILY&ver=1.0");
//        uriList.add("https://apis.data.go.kr/B552584/ArpltnInforInqireSvc/getMsrstnAcctoRltmMesureDnsty?serviceKey=i1z1J%2BUagwRHR2a0tWJ%2FTr8wo872kZm38a2mPjsEcACA2LDprkmPFuB5ePAG2BQ7oaBz8YruDeG%2FMk74duqtHA%3D%3D&returnType=json&numOfRows=1&pageNo=1&stationName=%EB%B3%B4%EB%9E%8C%EB%8F%99&dataTerm=DAILY&ver=1.0");
//        uriList.add("https://apis.data.go.kr/B552584/ArpltnInforInqireSvc/getMsrstnAcctoRltmMesureDnsty?serviceKey=i1z1J%2BUagwRHR2a0tWJ%2FTr8wo872kZm38a2mPjsEcACA2LDprkmPFuB5ePAG2BQ7oaBz8YruDeG%2FMk74duqtHA%3D%3D&returnType=json&numOfRows=1&pageNo=1&stationName=%EC%A2%85%EB%A1%9C%EA%B5%AC&dataTerm=DAILY&ver=1.0");
//        uriList.add("ttps://apis.data.go.kr/B552584/ArpltnInforInqireSvc/getMsrstnAcctoRltmMesureDnsty?serviceKey=i1z1J%2BUagwRHR2a0tWJ%2FTr8wo872kZm38a2mPjsEcACA2LDprkmPFuB5ePAG2BQ7oaBz8YruDeG%2FMk74duqtHA%3D%3D&returnType=json&numOfRows=1&pageNo=1&stationName=%EB%B3%B4%EB%9E%8C%EB%8F%99&dataTerm=DAILY&ver=1.0");
//
//        Flux<Long> interval = Flux.interval(Duration.ofSeconds(1));
//
//        ParallelFlux<String> stringParallelFlux = Flux.fromArray(uriList.toArray())
//                .parallel()
//                .runOn(Schedulers.boundedElastic()).flatMap(item -> {
//                    return trialManTwo((String) item);
//                });
//
//        Flux.zip(interval, stringParallelFlux).map(tu-> tu.getT2()).doOnEach(item->{
//            log.info("item ={}", item.get());
//        }).subscribe(result ->{
//            log.info("result ={}", result);
//        });
//
//
//    }





}
