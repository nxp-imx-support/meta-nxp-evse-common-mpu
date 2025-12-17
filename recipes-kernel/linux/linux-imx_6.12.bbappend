# NOTE: there is an issue with the NFC configuration, this is a known issue, has to be fixed - CONFIG_NXP_NFC_*** do not apply properly

FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI:append = " \
    git://github.com/NXPNFCLinux/nxpnfc.git;branch=master;protocol=https;name=pn7160;subdir=git/drivers/nfc/pn7160 \
    file://0001-Updated-repo-for-imx-LF-6.12.3.patch;apply=no \
    file://dts/ \
    file://Makefile_DTS_EasyEVSE \
    file://0001-Added-patch-for-UART-delay-reduction-6_12_20.patch \
    file://nfc.cfg \
"
SRCREV_pn7160="b2539909b6c073ee7949c7edd48d8e20cd1865a8"

DELTA_KERNEL_DEFCONFIG = "nfc.cfg"

NXP_FILES := "${THISDIR}/files"

addtask do_prepare_nfc after do_patch before do_copy_defconfig
addtask do_prepare_dts_makefile after do_patch before do_copy_defconfig

do_prepare_nfc() {
    cd ${S}/drivers/nfc/pn7160
    git apply ${NXP_FILES}/0001-Updated-repo-for-imx-LF-6.12.3.patch
    cat ${S}/drivers/nfc/pn7160/Makefile >> ${S}/drivers/nfc/Makefile
    rm ${S}/drivers/nfc/pn7160/Makefile
    mv ${S}/drivers/nfc/pn7160/pn7160/* ${S}/drivers/nfc/pn7160/
    rmdir ${S}/drivers/nfc/pn7160/pn7160
}

do_prepare_dts_makefile() {
    cp -r ${NXP_FILES}/dts/* ${S}/arch/arm64/boot/dts/freescale
    cat ${NXP_FILES}/Makefile_DTS_EasyEVSE >> ${S}/arch/arm64/boot/dts/freescale/Makefile
}
