package backend;

import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.sse.Event;
import jakarta.inject.Inject;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;

@Controller("/api")
public class TimeController
{
  private final TimeService timeService;

  @Inject
  public TimeController(TimeService timeService) {
    this.timeService = timeService;
  }

  @Get(value="/hello", produces = MediaType.TEXT_PLAIN)
  public String hello()
  {
    return "Hello World";
  }

  @Get("/seconds")
  public Publisher<Event<Integer>> seconds()
  {
    return Flux.from(timeService.getSeconds())
      .map(Event::of);
  }

  @Get("/minutes")
  public Publisher<Event<Integer>> minutes()
  {
    return Flux.from(timeService.getMinutes())
      .map(Event::of);

  }


  @Get("/hours")
  public Publisher<Event<Integer>> hours()
  {
    return Flux.from(timeService.getHours())
      .map(Event::of);
  }
}
