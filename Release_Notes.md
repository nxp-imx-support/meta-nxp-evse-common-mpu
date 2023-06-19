NXP EasyEVSE EV Charging Station Development Platform Linux 3.0 Release Notes
=============================================================================


Release Contents
----------------

NXP EasyEVSE is a simulated electric vehicle charging station connected to the
Microsoft Azure IoT Central cloud over Wi-Fi.

The solution integrates the following NXP hardware:
- Host controller: i.MX 93 MPU
- Wi-Fi: M.2 Key-E IW612 1x1 2.4/5 GHz
- Powerline communications: EVSE-SIG-BRD2x
- Meter: TWR-KM35Z75M MCU
- Security: OM-SE050ARD-E secure element
- NFC: OM27160B1EVK NFC frontend
- GUI: DY1212W-4856 TFT LCD

The solution SW is developed on top of an environment combining:
- iMX Linux BSP (6.1.55-2.2.0) / Yocto Project 4.2 (Mickledore)
- Robotic OS 2 Humble
- Microsoft Azure IoT SDK C
- Lumissil HPGP SDK
- SEVENSTAX ISO15118 stack and Vehicle-to-Grid application
- NXP LibNFC-NCI
- NXP Plug & Trust Middleware
- SIG-BRD Firmware
- Meter KM35x Firmware

For more details please see the SW-Content-Register.txt from the same directory
as these notes.


What's new
----------

- Added ISO15118-20 AC Bidirectional Power Transfer support with Scheduled ControlMode
  and unified BPTChannel configuration
- Added PlugAndCharge functionality for ISO15118-20 using Secure Element SE050
- Create HSM driver for offloading signing to Secure Element SE050
- Add TLS 1.3 support for SE050
- EV selects authorization mode through command line parameters
- Redesign the GUI to a single screen to be more user friendly and suit ISO15118-20
  Bi-Directional Transfer
- Add support for ISO15118-20 AC Pause/Resume
- Use the same command for starting EVSE and EV demo, with different arguments
- Add options for EVSE side to control charging session through buttons on the EVK
  (Pause and Restart)
- Add options for EV side to control charging session through buttons on the EVK
  (Pause/Resume and Switch between Charging and Discharging)
- Add Pause Request option from GUI on EVSE side
- Update QT GUI application to automatically resize for different screen resolutions
- Fixed ISO15118 transfer not starting until the Grid power limit value is changed
  from the Cloud app
- Update the NFC libnfc-nci recipe to fetch the updated PN7160 dedicated branch
  from github
- Differentiate between ISO15118 versions in GUI
- Show Charging direction in cloud web application and GUI
- Created separate EV apps for EIM/PnC and ISO15118-2/20
- Add EIM authentication by using NFC (disabled by default)


Known Issues
------------

- Battery level increases when EV is not connected to EVSE
- Irms control displays 0 in GUI during Basic Charging
- Device is not being re-created in Cloud app without re-running the SE050
  reset and provisioning steps
- Cannot terminate a charging session from Cloud app
- EVK buttons not functioning as expected in some scenarios
- Direction change is not working in some scenarios
- GUI doesn't display the actual meter value when Irms>Grid Limit/EVSE Rate
- GUI displays Current=0 when Irms<0.8A
- Grid Limit stuck at 0 after network reconnection
- EVSE unable to connect to open (unsecured) Wi-Fi network
- Sometimes HLC fails to start when CP line is connected after both EVSE and EV
  apps are started
- ISO-20 EIM/PnC charging/discharging stop shortly after network disconnection/reconnection
- HLC does not pause on CP disconnected and does not resume when CP reconnected
- Elapsed Time and Vehicle ID do not reset when CP is disconnected during charging
- Energy Requested and Energy Delivered have different measurement units in GUI and Cloud
- Elapsed Time is always 0 during Basic Charging
- Charging doesn't resume after meter potentiometer value got to 0 then increased
- NFC ID not updated in GUI if NFC card is near the reader when the EVSE app is started 
- Cloud app shows "Discharge" after ISO-20 charging session completion
- ISO-2 GUI with Keysight as EV displays weird characters for Vehicle ID
- Timeout reached during ISO-2/ISO-20 charging with Keysight as EV
- GUI Grid Limit not updated with Cloud value after running without cloud
