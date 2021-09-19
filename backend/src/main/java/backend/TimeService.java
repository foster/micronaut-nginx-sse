package backend;

import org.reactivestreams.Publisher;

public interface TimeService
{
  Publisher<Integer> getSeconds();
  Publisher<Integer> getMinutes();
  Publisher<Integer> getHours();
}
