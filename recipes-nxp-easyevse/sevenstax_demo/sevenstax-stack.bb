# Copyright 2024 NXP

SUMMARY:${PN}-staticdev = "Sevenstax ISO 15118 stack"
DESCRIPTION = "ISO 15118 network stack"
SECTION = "development"
DEPENDS = "libgpiod"
RDEPENDS:${PN} += "lumissil-hpgp-sdk"
PV = "11.03.00"

LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://${WORKDIR}/${BP}/COPYING;md5=2827219e81f28aba7c6a569f7c437fa7"

inherit fsl-eula-unpack module pkgconfig

STX_BIN_DIR ?= "${FSL_MIRROR}"
SRC_URI = "${STX_BIN_DIR}/${BP}.bin;fsl-eula=true"
SRC_URI[md5sum] = "8131f921796fa250152686ad29db277e"
SRC_URI[sha256sum] = "b52838076eac892af6fd9f8a0495b816b09ff9af969ed86e0290b6e909003138"

do_configure(){
}

do_compile(){
}

do_install(){
    install -d -m 0755 ${D}${libdir}/${BPN}
    install -d -m 0755 ${D}${includedir}/${BPN}
    install -o root -g root -m 444 lib/* ${D}${libdir}/${BPN}
    find encrypt helper include products scheduler \
	| cpio -pdmv --owner=root:root ${D}${includedir}/${BPN}
}

FILES:${PN}-staticdev += "${libdir}"
FILES:${PN}-staticdev += "${includedir}"
