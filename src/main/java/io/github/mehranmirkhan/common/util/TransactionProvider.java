package io.github.mehranmirkhan.common.util;

import java.util.function.Function;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
public class TransactionProvider {
  @Transactional
  public <REQ, RESP> RESP doWithTransaction(Function<REQ, RESP> func, REQ t) {
    return func.apply(t);
  }

  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public <REQ, RESP> RESP doWithNewTransaction(Function<REQ, RESP> func, REQ t) {
    return func.apply(t);
  }

  @Transactional(readOnly = true)
  public <REQ, RESP> RESP doWithReadOnlyTransaction(Function<REQ, RESP> func, REQ t) {
    return func.apply(t);
  }

  @Transactional
  public void runInTransaction(Runnable func) {
    func.run();
  }

  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public void runInNewTransaction(Runnable func) {
    func.run();
  }

  @Transactional(readOnly = true)
  public void runInReadOnlyTransaction(Runnable func) {
    func.run();
  }
}
