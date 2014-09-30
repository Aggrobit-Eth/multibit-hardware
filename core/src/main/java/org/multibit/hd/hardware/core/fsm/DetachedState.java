package org.multibit.hd.hardware.core.fsm;

import org.multibit.hd.hardware.core.HardwareWalletClient;
import org.multibit.hd.hardware.core.events.MessageEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>State to provide the following to hardware wallet clients:</p>
 * <ul>
 * <li>State transitions based on low level message events</li>
 * </ul>
 * <p>The "detached" state represents when the underlying hardware is waiting for a wallet to be attached.</p>
 *
 * @since 0.0.1
 *  
 */
public class DetachedState extends AbstractHardwareWalletState {

  private static final Logger log = LoggerFactory.getLogger(DetachedState.class);

  @Override
  protected void internalTransition(HardwareWalletClient client, HardwareWalletContext context, MessageEvent event) {

    switch (event.getMessageType()) {
      // TODO Implement
      default:
        log.info("Unexpected message event '{}'", event.getMessageType().name());
        context.resetToConnected();
    }

  }
}
