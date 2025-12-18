# Copyright 2024 NXP

SUMMARY = "Lumissil CG5317 firmware"
DESCRIPTION = "Home Plug Green PHY loadable firmware"
SECTION = "firmware"
DEPENDS = ""
PV = "4.05.000"

RDEPENDS:${PN} += "libgpiod"

LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://COPYING;md5=ef8ffca65ac64435c687e5b56e69b127"

SRC_URI = "file://${TOPDIR}/../CG5317_${PV}.tgz"
SRC_URI[md5sum] = "6c56e7e8eebe28b0f257eb99975a3910"
SRC_URI[sha256sum] = "4d209529eca0b7c4288b744da8678d884415b1c233038f7543573a1a0cdf5aad"

do_compile() {
}

S = "${WORKDIR}/${BP}"
UNPACKDIR = "${S}"
LUMI_DIR = "/home/root/res/cg5317"

do_install() {
    install -d -m 755 ${D}${LUMI_DIR}/binaries
    install ${UNPACKDIR}/res/cg5317/binaries/CG5317-04.05.000.0020-DEFAULT.bin ${D}${LUMI_DIR}/binaries
    install ${UNPACKDIR}/res/cg5317/binaries/eth_ev_config.bin ${D}${LUMI_DIR}/binaries
    install ${UNPACKDIR}/res/cg5317/binaries/eth_evse_config.bin ${D}${LUMI_DIR}/binaries
    install ${UNPACKDIR}/res/cg5317/binaries/golan_FlashUpgrade_image.dat ${D}${LUMI_DIR}/binaries
    install ${UNPACKDIR}/res/cg5317/binaries/spi_dbg_ev_config.bin ${D}${LUMI_DIR}/binaries
    install ${UNPACKDIR}/res/cg5317/binaries/spi_dbg_evse_config.bin ${D}${LUMI_DIR}/binaries
    install ${UNPACKDIR}/res/cg5317/binaries/spi_ev_config.bin ${D}${LUMI_DIR}/binaries
    install ${UNPACKDIR}/res/cg5317/binaries/spi_evse_config.bin ${D}${LUMI_DIR}/binaries
    install ${UNPACKDIR}/res/cg5317/binaries/golan_FlashUpgrade_image.dat ${D}${LUMI_DIR}/binaries

    install -d -m 755 ${D}${LUMI_DIR}/host
    install -m 0755 ${UNPACKDIR}/res/cg5317/host/host_loading_service ${D}${LUMI_DIR}/host
    install -m 0755 ${UNPACKDIR}/res/cg5317/host/ind_listener_app ${D}${LUMI_DIR}/host
    install -m 0755 ${UNPACKDIR}/res/cg5317/host/management_tool ${D}${LUMI_DIR}/host
    install -m 0755 ${UNPACKDIR}/res/cg5317/host/user_config_editor ${D}${LUMI_DIR}/host

    install -d -m 755 ${D}${LUMI_DIR}/prod
    install -m 0755 ${UNPACKDIR}/res/cg5317/prod/fw_parser ${D}${LUMI_DIR}/prod
    install -m 0755 ${UNPACKDIR}/res/cg5317/prod/mk_flash ${D}${LUMI_DIR}/prod
    install -m 0755 ${UNPACKDIR}/res/cg5317/prod/production_tool ${D}${LUMI_DIR}/prod
    install -m 0755 ${UNPACKDIR}/res/cg5317/prod/production_tool ${D}${LUMI_DIR}/prod

    install -d -m 755 ${D}${LUMI_DIR}/prog
    install -m 0755 ${UNPACKDIR}/res/cg5317/prog/prog ${D}${LUMI_DIR}/prog
}

FILES:${PN} += "${LUMI_DIR}/* ${LUMI_DIR}/prog/* ${LUMI_DIR}/host/* ${LUMI_DIR}/binaries/* ${LUMI_DIR}/prod/*"
