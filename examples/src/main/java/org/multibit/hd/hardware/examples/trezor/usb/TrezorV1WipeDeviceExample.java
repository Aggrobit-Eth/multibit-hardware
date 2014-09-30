package org.multibit.hd.hardware.examples.trezor.usb;

import com.google.common.base.Optional;
import com.google.common.eventbus.Subscribe;
import org.multibit.hd.hardware.core.HardwareWalletClient;
import org.multibit.hd.hardware.core.HardwareWalletService;
import org.multibit.hd.hardware.core.events.HardwareWalletEvent;
import org.multibit.hd.hardware.core.messages.Features;
import org.multibit.hd.hardware.core.wallets.HardwareWallets;
import org.multibit.hd.hardware.trezor.clients.TrezorHardwareWalletClient;
import org.multibit.hd.hardware.trezor.wallets.v1.TrezorV1UsbHardwareWallet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>Step 2 - Wipe the device to factory defaults and load with known seed phrase</p>
 * <p>Requires Trezor V1 production device plugged into a USB HID interface.</p>
 * <p>This example demonstrates the message sequence to wipe a Trezor device back to its fresh out of the box
 * state and then set it up with a known seed phrase.</p>
 *
 * <h3>Only perform this example on a Trezor that you are using for test and development!</h3>
 *
 * @since 0.0.1
 *  
 */
public class TrezorV1WipeDeviceExample {

  private static final Logger log = LoggerFactory.getLogger(TrezorV1WipeDeviceExample.class);

  private HardwareWalletService hardwareWalletService;

  /**
   * <p>Main entry point to the example</p>
   *
   * @param args None required
   *
   * @throws Exception If something goes wrong
   */
  public static void main(String[] args) throws Exception {

    // All the work is done in the class
    TrezorV1WipeDeviceExample example = new TrezorV1WipeDeviceExample();

    example.executeExample();

  }

  /**
   * Execute the example
   */
  public void executeExample() {

    // Use factory to statically bind the specific hardware wallet
    TrezorV1UsbHardwareWallet wallet = HardwareWallets.newUsbInstance(
      TrezorV1UsbHardwareWallet.class,
      Optional.<Short>absent(),
      Optional.<Short>absent(),
      Optional.<String>absent()
    );

    // Wrap the hardware wallet in a suitable client to simplify message API
    HardwareWalletClient client = new TrezorHardwareWalletClient(wallet);

    // Wrap the client in a service for high level API suitable for downstream applications
    hardwareWalletService = new HardwareWalletService(client);

    // Register for the high level hardware wallet events
    HardwareWalletService.hardwareWalletEventBus.register(this);

    hardwareWalletService.start();

  }

  /**
   * <p>Downstream consumer applications should respond to hardware wallet events</p>
   *
   * @param event The hardware wallet event indicating a state change
   */
  @Subscribe
  public void onHardwareWalletEvent(HardwareWalletEvent event) {

    if (event.isFailed()) {
      // Treat as end of example
      System.exit(0);
      return;
    }

    if (event.isDisconnected()) {
      // Can simply wait for another device to be connected again
      return;
    }

    // Get some information about the device
    Features features = hardwareWalletService.getFeatures();
    log.info("Features: {}", features);

    // Use the service to make high level decisions
    if (hardwareWalletService.isWalletCreationRequired()) {

      // ... application performs a complex UI process to provide a seed phrase, PIN, label etc ...

      //
      hardwareWalletService.createWalletOnDevice(
        "english",
        "charlie",
        true,
        false,
        true,
        128
      );

    }

  }

}
