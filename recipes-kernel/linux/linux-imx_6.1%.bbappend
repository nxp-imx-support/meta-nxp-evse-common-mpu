FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI:append = " \
    file://0001-EasyEVSE-LF6.1.36-DTS-support-for-i.MX8MNLPDDR4-and-.patch \
    file://0001-EasyEVSE-LF6.1.36-DTS-support-for-i.MX8MMLPDDR4-and-.patch \
    file://0001-PATCH-EasyEVSE-LF6.1.22-PN7160-NFC-driver-addition.patch \
    file://0001-EasyEVSE-Support-for-LVDS-display.patch \
    file://0001-EasyEVSE-UART-Reduce-characters-received-before-BUS-.patch \
    file://0002-EasyEVSE-Enable-gpio-keys-button-to-control-EVSE-and.patch \
    file://0003-EasyEVSE-Enable-autorepeat-to-fix-button-long-press-.patch \
"

do_copy_defconfig:append () {
    echo "CONFIG_NXP_NFC_I2C=y" >>  ${B}/.config
    echo "CONFIG_NXP_NFC_SPI=y" >>  ${B}/.config
    echo "CONFIG_NXP_NFC_RECOVERY=y" >>  ${B}/.config
}
