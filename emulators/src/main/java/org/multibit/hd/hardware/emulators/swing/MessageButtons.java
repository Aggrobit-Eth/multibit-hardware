package org.multibit.hd.hardware.emulators.swing;

import org.multibit.hd.hardware.core.events.HardwareWalletEvents;
import org.multibit.hd.hardware.core.messages.ProtocolMessageType;
import org.multibit.hd.hardware.core.messages.SystemMessageType;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * <p>Utility to provide the following to emulators:</p>
 * <ul>
 * <li>Various button instances</li>
 * </ul>
 *
 * @since 0.0.1
 *  
 */
public class MessageButtons {

  /**
   * Utilities have private constructors
   */
  private MessageButtons() {
  }

  /**
   * @param type The message type
   *
   * @return A new button instance with a suitable action
   */
  public static JButton newProtocolButton(final ProtocolMessageType type) {

    Action action = new AbstractAction() {
      @Override
      public void actionPerformed(ActionEvent e) {

        HardwareWalletEvents.fireProtocolEvent(type, null);

      }
    };

    JButton button = new JButton(action);
    button.setText(type.name());

    return button;

  }

  /**
   * @param type The message type
   *
   * @return A new button instance with a suitable action
   */
  public static JButton newSystemButton(final SystemMessageType type) {

    Action action = new AbstractAction() {
      @Override
      public void actionPerformed(ActionEvent e) {

        HardwareWalletEvents.fireSystemEvent(type);

      }
    };

    JButton button = new JButton(action);
    button.setText(type.name());

    return button;

  }

}
