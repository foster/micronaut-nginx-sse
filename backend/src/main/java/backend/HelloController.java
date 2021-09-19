package backend;

import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.sse.Event;
import jakarta.inject.Inject;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;

@Controller("/hello")
public class HelloController
{
  private final Metronome metronome;

  @Inject
  public HelloController(Metronome metronome) {
    this.metronome = metronome;
  }

  @Get(produces = MediaType.TEXT_PLAIN)
  public String index()
  {
    return "Hello World";
  }

  @Get("/seconds")
  public Publisher<Event<String>> seconds()
  {
    Flux<String> flux = Flux.from(metronome.getSecondsEmitter())
      .map(sec -> "Hello " + sec);

    return flux.map(Event::of);
  }

  @Get("/minutes")
  public Publisher<Event<String>> minutes()
  {
    Flux<String> flux = Flux.from(metronome.getMinutesEmitter())
      .map(sec -> "Minute " + sec);

    return flux.map(Event::of);
  }


  @Get("/hours")
  public Publisher<Event<String>> hours()
  {
    Flux<String> flux = Flux.from(metronome.getHoursEmitter())
      .map(sec -> "Hour " + sec);

    return flux.map(Event::of);
  }
}
