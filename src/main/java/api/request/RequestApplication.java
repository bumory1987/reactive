package api.request;

import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import javax.net.ssl.SSLException;

@SpringBootApplication
@EnableScheduling
public class RequestApplication {

	@Bean
	public WebClient createWebClient() throws SSLException {
		SslContext ssl = SslContextBuilder
				.forClient()
				.trustManager(InsecureTrustManagerFactory.INSTANCE).build();
		HttpClient httpClient = HttpClient.create().secure(builder -> builder.sslContext(ssl));
		return WebClient.builder().clientConnector(new ReactorClientHttpConnector(httpClient)).build();
	}


//		@Bean
//	public WebClient createWebClient() throws SSLException {
//	SSLFactory sslFactory = SSLFactory.builder()
//			.withIdentityMaterial("identity.jks", "password".toCharArray())
//			.withTrustMaterial("truststore.jks", "password".toCharArray())
//			.build();
//
//	SslContext sslContext = NettySslUtils.forClient(sslFactory).build();
//	HttpClient httpClient = HttpClient.create().secure(sslSpec -> sslSpec.sslContext(sslContext));

	public static void main(String[] args) {
		SpringApplication.run(RequestApplication.class, args);
	}

}
