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
  private Publisher<LocalTime> heartbeat;
  Publisher<Integer> secondsEmitter;
  Publisher<Integer> minutesEmitter;
  Publisher<Integer> hoursEmitter;

  public TimeServiceImpl() {
    heartbeat = Flux.interval(Duration.ofMillis(100))
      .map(__ -> LocalTime.now())
      .share();
  }

  @PostConstruct
  public void initialize() {
    System.out.println("Initializing TimerServiceImpl");

    secondsEmitter = Flux.from(heartbeat)
      .map(time -> time.getSecond())
      .distinctUntilChanged()
      .doOnNext(s -> System.out.println("second tick: " + s))
      .cache(1)
      .share();

    minutesEmitter = Flux.from(heartbeat)
      .map(time -> time.getMinute())
      .distinctUntilChanged()
      .doOnNext(m -> System.out.println("minute tick: " + m))
      .cache(1)
      .share();

    hoursEmitter = Flux.from(heartbeat)
      .map(time -> time.getHour())
      .distinctUntilChanged()
      .doOnNext(h -> System.out.println("hour tick: " + h))
      .cache(1)
      .share();
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
