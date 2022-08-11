package api.request.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.ParallelFlux;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ApiController {

    private final WebClient webClient;

    @GetMapping("/test")
    Mono<String> hell() throws URISyntaxException {
        URI insertUri = new URI("https://apis.data.go.kr/B552584/ArpltnInforInqireSvc/getMsrstnAcctoRltmMesureDnsty?serviceKey=i1z1J%2BUagwRHR2a0tWJ%2FTr8wo872kZm38a2mPjsEcACA2LDprkmPFuB5ePAG2BQ7oaBz8YruDeG%2FMk74duqtHA%3D%3D&returnType=json&numOfRows=1&pageNo=1&stationName=%EC%A2%85%EB%A1%9C%EA%B5%AC&dataTerm=DAILY&ver=1.0");
        return webClient.get()
                .uri(insertUri)
                .retrieve()
                .bodyToMono(String.class);
    }



    Mono<String> trialMan(String input)  {
        URI insertUri = null;
        try{
            insertUri = new URI(input);
        }catch (Exception e){
            throw new IllegalArgumentException();
        }
        return webClient.get()
                .uri(insertUri)
                .retrieve()
                .bodyToMono(String.class);
    }



    @GetMapping("/sequence")
    Flux<String> doSequence() throws URISyntaxException {
        List<String> uriList = new ArrayList<>();
        uriList.add("https://apis.data.go.kr/B552584/ArpltnInforInqireSvc/getMsrstnAcctoRltmMesureDnsty?serviceKey=i1z1J%2BUagwRHR2a0tWJ%2FTr8wo872kZm38a2mPjsEcACA2LDprkmPFuB5ePAG2BQ7oaBz8YruDeG%2FMk74duqtHA%3D%3D&returnType=json&numOfRows=1&pageNo=1&stationName=%EB%B3%B4%EB%9E%8C%EB%8F%99&dataTerm=DAILY&ver=1.0");
        uriList.add("https://apis.data.go.kr/B552584/ArpltnInforInqireSvc/getMsrstnAcctoRltmMesureDnsty?serviceKey=i1z1J%2BUagwRHR2a0tWJ%2FTr8wo872kZm38a2mPjsEcACA2LDprkmPFuB5ePAG2BQ7oaBz8YruDeG%2FMk74duqtHA%3D%3D&returnType=json&numOfRows=1&pageNo=1&stationName=%EC%A2%85%EB%A1%9C%EA%B5%AC&dataTerm=DAILY&ver=1.0");
        uriList.add("ttps://apis.data.go.kr/B552584/ArpltnInforInqireSvc/getMsrstnAcctoRltmMesureDnsty?serviceKey=i1z1J%2BUagwRHR2a0tWJ%2FTr8wo872kZm38a2mPjsEcACA2LDprkmPFuB5ePAG2BQ7oaBz8YruDeG%2FMk74duqtHA%3D%3D&returnType=json&numOfRows=1&pageNo=1&stationName=%EB%B3%B4%EB%9E%8C%EB%8F%99&dataTerm=DAILY&ver=1.0");
        uriList.add("https://apis.data.go.kr/B552584/ArpltnInforInqireSvc/getMsrstnAcctoRltmMesureDnsty?serviceKey=i1z1J%2BUagwRHR2a0tWJ%2FTr8wo872kZm38a2mPjsEcACA2LDprkmPFuB5ePAG2BQ7oaBz8YruDeG%2FMk74duqtHA%3D%3D&returnType=json&numOfRows=1&pageNo=1&stationName=%EB%B3%B4%EB%9E%8C%EB%8F%99&dataTerm=DAILY&ver=1.0");
        uriList.add("https://apis.data.go.kr/B552584/ArpltnInforInqireSvc/getMsrstnAcctoRltmMesureDnsty?serviceKey=i1z1J%2BUagwRHR2a0tWJ%2FTr8wo872kZm38a2mPjsEcACA2LDprkmPFuB5ePAG2BQ7oaBz8YruDeG%2FMk74duqtHA%3D%3D&returnType=json&numOfRows=1&pageNo=1&stationName=%EC%A2%85%EB%A1%9C%EA%B5%AC&dataTerm=DAILY&ver=1.0");
        uriList.add("ttps://apis.data.go.kr/B552584/ArpltnInforInqireSvc/getMsrstnAcctoRltmMesureDnsty?serviceKey=i1z1J%2BUagwRHR2a0tWJ%2FTr8wo872kZm38a2mPjsEcACA2LDprkmPFuB5ePAG2BQ7oaBz8YruDeG%2FMk74duqtHA%3D%3D&returnType=json&numOfRows=1&pageNo=1&stationName=%EB%B3%B4%EB%9E%8C%EB%8F%99&dataTerm=DAILY&ver=1.0");
        Flux<String> stringParallelFlux = Flux.fromIterable(uriList).flatMap(item->{
            log.info("Thread ={}", Thread.currentThread().getName());
            return trialMan(item);

        });
        return stringParallelFlux;
    }


    @GetMapping("/sequence-two")
    ParallelFlux<String> doSequenceDifferently() throws URISyntaxException {
        List<String> uriList = new ArrayList<>();
        uriList.add("https://apis.data.go.kr/B552584/ArpltnInforInqireSvc/getMsrstnAcctoRltmMesureDnsty?serviceKey=i1z1J%2BUagwRHR2a0tWJ%2FTr8wo872kZm38a2mPjsEcACA2LDprkmPFuB5ePAG2BQ7oaBz8YruDeG%2FMk74duqtHA%3D%3D&returnType=json&numOfRows=1&pageNo=1&stationName=%EB%B3%B4%EB%9E%8C%EB%8F%99&dataTerm=DAILY&ver=1.0");
        uriList.add("https://apis.data.go.kr/B552584/ArpltnInforInqireSvc/getMsrstnAcctoRltmMesureDnsty?serviceKey=i1z1J%2BUagwRHR2a0tWJ%2FTr8wo872kZm38a2mPjsEcACA2LDprkmPFuB5ePAG2BQ7oaBz8YruDeG%2FMk74duqtHA%3D%3D&returnType=json&numOfRows=1&pageNo=1&stationName=%EC%A2%85%EB%A1%9C%EA%B5%AC&dataTerm=DAILY&ver=1.0");
        uriList.add("ttps://apis.data.go.kr/B552584/ArpltnInforInqireSvc/getMsrstnAcctoRltmMesureDnsty?serviceKey=i1z1J%2BUagwRHR2a0tWJ%2FTr8wo872kZm38a2mPjsEcACA2LDprkmPFuB5ePAG2BQ7oaBz8YruDeG%2FMk74duqtHA%3D%3D&returnType=json&numOfRows=1&pageNo=1&stationName=%EB%B3%B4%EB%9E%8C%EB%8F%99&dataTerm=DAILY&ver=1.0");
        uriList.add("https://apis.data.go.kr/B552584/ArpltnInforInqireSvc/getMsrstnAcctoRltmMesureDnsty?serviceKey=i1z1J%2BUagwRHR2a0tWJ%2FTr8wo872kZm38a2mPjsEcACA2LDprkmPFuB5ePAG2BQ7oaBz8YruDeG%2FMk74duqtHA%3D%3D&returnType=json&numOfRows=1&pageNo=1&stationName=%EB%B3%B4%EB%9E%8C%EB%8F%99&dataTerm=DAILY&ver=1.0");
        uriList.add("https://apis.data.go.kr/B552584/ArpltnInforInqireSvc/getMsrstnAcctoRltmMesureDnsty?serviceKey=i1z1J%2BUagwRHR2a0tWJ%2FTr8wo872kZm38a2mPjsEcACA2LDprkmPFuB5ePAG2BQ7oaBz8YruDeG%2FMk74duqtHA%3D%3D&returnType=json&numOfRows=1&pageNo=1&stationName=%EC%A2%85%EB%A1%9C%EA%B5%AC&dataTerm=DAILY&ver=1.0");
        uriList.add("ttps://apis.data.go.kr/B552584/ArpltnInforInqireSvc/getMsrstnAcctoRltmMesureDnsty?serviceKey=i1z1J%2BUagwRHR2a0tWJ%2FTr8wo872kZm38a2mPjsEcACA2LDprkmPFuB5ePAG2BQ7oaBz8YruDeG%2FMk74duqtHA%3D%3D&returnType=json&numOfRows=1&pageNo=1&stationName=%EB%B3%B4%EB%9E%8C%EB%8F%99&dataTerm=DAILY&ver=1.0");



        ParallelFlux<String> returnFlux= Flux.fromIterable(uriList).parallel().flatMap(item ->{
            try{
                log.info("Thread ={}", Thread.currentThread().getName());
                return trialMan(item);
            }catch(Exception e){
                throw new IllegalArgumentException();
            }
        });

        return returnFlux;
    }

    @GetMapping("show")
    Mono<String> show(){
        return Mono.just("hello");
    }



}
