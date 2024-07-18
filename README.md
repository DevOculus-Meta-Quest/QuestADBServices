# QuestADBServices

QuestADBServices is an Android application designed to enable ADB over TCP on Meta Quest headsets. This allows wireless ADB connections to the headset, making development and debugging more convenient without needing to connect via USB.

## Features

- **Wireless ADB**: Enables ADB over TCP on port 5555.
- **Auto-start on Boot**: Ensures the ADB service is restarted after the device reboots.
- **Self-signed RSA Key Generation**: Handles the generation and management of RSA keys for ADB authentication.

## Getting Started

### Prerequisites

- Android Studio installed
- Meta Quest headset
- ADB installed and configured on your development machine

### Installation

1. Clone the repository:
    ```sh
    git clone https://github.com/DevOculus-Meta-Quest/QuestADBServices.git
    ```

2. Open the project in Android Studio.

3. Build and run the project on your Meta Quest headset.

### Usage

#### Initial Setup

1. **Deploy the App**:
    - Open the project in Android Studio.
    - Connect your Meta Quest headset via USB.
    - Run the app from Android Studio.

2. **Connect via ADB**:
    - Once the app is running on the headset, use the following command to connect wirelessly:
        ```sh
        adb connect <device-ip>:5555
        ```

3. **Verify Connection**:
    - Check the connection by running:
        ```sh
        adb devices
        ```
    - You should see your device listed.

#### Post-Reboot

- The app is configured to start the ADB service automatically after the device reboots.
- You can reconnect wirelessly using the same `adb connect <device-ip>:5555` command.

## Implementation Details

### Main Components

- **MainActivity**: Launches the app and starts the `ShellService` to enable ADB over TCP.
- **ShellService**: Configures ADB to listen on port 5555 and handles RSA key generation.
- **BootReceiver**: Ensures `ShellService` starts automatically after a device reboot.

### Key Files

- **MainActivity.kt**: Handles the UI and starts the `ShellService`.
- **ShellService.kt**: Contains the logic to configure ADB and manage the RSA keys.
- **BootReceiver.kt**: Listens for the device boot event and starts the `ShellService`.
- **AdbUtils.kt**: Utility class for handling RSA key generation and storage.

### AndroidManifest.xml

- Ensures the necessary permissions are declared.
- Registers the `BootReceiver` to listen for the `BOOT_COMPLETED` event.

## Contributing

1. Fork the repository.
2. Create your feature branch (`git checkout -b feature/your-feature`).
3. Commit your changes (`git commit -m 'Add some feature'`).
4. Push to the branch (`git push origin feature/your-feature`).
5. Open a pull request.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Acknowledgements

- [cgutman/adblib](https://github.com/cgutman/adblib) for the ADB library.

