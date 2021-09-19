package backend;

import org.reactivestreams.Publisher;

public interface Metronome {
  Publisher<Integer> getSecondsEmitter();
  Publisher<Integer> getMinutesEmitter();
  Publisher<Integer> getHoursEmitter();
}
