FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI:append = " \
    file://0001-EasyEVSE-LF6.1.36-Defconfig-support-for-i.MX8MNLPDDR.patch \
    file://0001-EasyEVSE-LF6.1.36-Defconfig-support-for-i.MX8MMLPDDR.patch \
    \
    ${@bb.utils.contains('DISTRO_FEATURES', 'LVDS_DISPLAY_SUPPORT', 'file://0001-EasyEVSE-Select-by-default-the-device-tree-for-the-L.patch', '', d)} \
"
