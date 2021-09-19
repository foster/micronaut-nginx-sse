package backend;

import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.sse.Event;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Controller("/hello")
public class HelloController
{
  @Get(produces = MediaType.TEXT_PLAIN)
  public String index()
  {
    return "Hello World";
  }

  @Get("/seconds")
  public Publisher<Event<String>> seconds()
  {

    Flux<String> flux = Flux.interval(Duration.ofSeconds(1))
      .map(input -> {
        return "Hello " + (input + 1);
      });

    return Mono.just("Hello 0").concatWith(flux).map(Event::of);
  }
}
