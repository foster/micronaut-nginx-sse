package backend;

import jakarta.inject.Singleton;
import org.reactivestreams.Publisher;
import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.time.LocalTime;

@Singleton
public class MetronomeImpl implements Metronome
{
  private int lastSecond = -1;
  private int lastMinute = -1;
  private int lastHour = -1;
  private Publisher<LocalTime> timeEmitter;
  Publisher<Integer> secondsEmitter;
  Publisher<Integer> minutesEmitter;
  Publisher<Integer> hoursEmitter;

  public MetronomeImpl() {
    timeEmitter = Flux.interval(Duration.ofMillis(100))
      .map(__ -> LocalTime.now());
  }

  @PostConstruct
  public void initialize() {
    System.out.println("Initializing metronome");

    secondsEmitter = Flux.from(timeEmitter)
      .map(time -> time.getSecond())
      .filter(s -> s != lastSecond)
      .doOnNext(s -> lastSecond = s)
      .doOnNext(s -> System.out.println("second tick: " + s))
      .cache();

    minutesEmitter = Flux.from(timeEmitter)
      .map(time -> time.getMinute())
      .filter(m -> m != lastMinute)
      .doOnNext(m -> lastMinute = m)
      .doOnNext(m -> System.out.println("minute tick: " + m))
      .cache();

    hoursEmitter = Flux.from(timeEmitter)
      .map(time -> time.getHour())
      .filter(h -> h != lastHour)
      .doOnNext(h -> lastHour = h)
      .doOnNext(h -> System.out.println("hour tick: " + h))
      .cache();
  }

  @Override
  public Publisher<Integer> getSecondsEmitter() {
    return this.secondsEmitter;
  }

  @Override
  public Publisher<Integer> getMinutesEmitter() {
    return this.minutesEmitter;
  }

  @Override
  public Publisher<Integer> getHoursEmitter() {
    return this.hoursEmitter;
  }
}
