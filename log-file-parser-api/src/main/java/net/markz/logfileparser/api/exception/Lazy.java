package net.markz.logfileparser.api.exception;

/**
 * A functional interfaces that lazily runs callback.
 * @param <T>
 */
@FunctionalInterface
public interface Lazy<T> {
  T lazyDo();
}
