package backend;

import jakarta.inject.Singleton;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.time.LocalTime;

@Singleton
public class TimeServiceImpl
        implements TimeService
{
  private Publisher<LocalTime> timeEmitter;
  Publisher<Integer> secondsEmitter;
  Publisher<Integer> minutesEmitter;
  Publisher<Integer> hoursEmitter;

  public TimeServiceImpl() {
    timeEmitter = Flux.interval(Duration.ofMillis(100))
      .map(__ -> LocalTime.now())
      .share();
  }

  @PostConstruct
  public void initialize() {
    System.out.println("Initializing metronome");

    secondsEmitter = Flux.from(timeEmitter)
      .map(time -> time.getSecond())
      .distinctUntilChanged()
      .doOnNext(s -> System.out.println("second tick: " + s))
      .cache(1);

    minutesEmitter = Flux.from(timeEmitter)
      .map(time -> time.getMinute())
      .distinctUntilChanged()
      .doOnNext(m -> System.out.println("minute tick: " + m))
      .cache(1);

    hoursEmitter = Flux.from(timeEmitter)
      .map(time -> time.getHour())
      .distinctUntilChanged()
      .doOnNext(h -> System.out.println("hour tick: " + h))
      .cache(1);
  }

  @Override
  public Publisher<Integer> getSeconds() {
    return this.secondsEmitter;
  }

  @Override
  public Publisher<Integer> getMinutes() {
    return this.minutesEmitter;
  }

  @Override
  public Publisher<Integer> getHours() {
    return this.hoursEmitter;
  }
}
