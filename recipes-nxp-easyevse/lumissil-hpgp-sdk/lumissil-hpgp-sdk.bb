# Copyright 2024 NXP

SUMMARY = "Lumissil CG5317 firmware"
DESCRIPTION = "Home Plug Green PHY loadable firmware"
SECTION = "firmware"
DEPENDS = ""
PV = "4.03.000"

LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://COPYING;md5=2827219e81f28aba7c6a569f7c437fa7"

inherit fsl-eula-unpack
SRC_URI = "${FSL_MIRROR}/${BP}.bin;fsl-eula=true"
SRC_URI[md5sum] = "6c56e7e8eebe28b0f257eb99975a3910"
SRC_URI[sha256sum] = "4d209529eca0b7c4288b744da8678d884415b1c233038f7543573a1a0cdf5aad"

do_compile() {
}

S = "${WORKDIR}/${BP}"
FWDIR = "/home/root/res/cg5317"

do_install() {
	install -d -m 755 ${D}/home/root/res/cg5317
	install ${S}/CG5317-04.03.000.0030-DEFAULT.bin ${D}${FWDIR}
	(cd ${D}${FWDIR} && ln -sf CG5317-04.03.000.0030-DEFAULT.bin fw.bin)
	install ${S}/eth_cco_config.bin     ${D}${FWDIR}
	install ${S}/eth_sta_config.bin     ${D}${FWDIR}
	install ${S}/spi_cco_config.bin     ${D}${FWDIR}
	install ${S}/spi_dbg_cco_config.bin ${D}${FWDIR}
	install ${S}/spi_dbg_sta_config.bin ${D}${FWDIR}
	install ${S}/spi_sta_config.bin     ${D}${FWDIR}
}

FILES:${PN} += "${FWDIR}/* "
