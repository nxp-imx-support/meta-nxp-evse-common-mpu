# Copyright 2024 NXP

SUMMARY = "Lumissil CG5317 firmware"
DESCRIPTION = "Home Plug Green PHY loadable firmware"
SECTION = "firmware"
DEPENDS = ""
PV = "4.05.000"

LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://COPYING;md5=2827219e81f28aba7c6a569f7c437fa7"

inherit fsl-eula-unpack
SRC_URI = "file://${TOPDIR}/../CG5317_${PV}.tgz;fsl-eula=true"
SRC_URI[md5sum] = "6c56e7e8eebe28b0f257eb99975a3910"
SRC_URI[sha256sum] = "4d209529eca0b7c4288b744da8678d884415b1c233038f7543573a1a0cdf5aad"

do_compile() {
}

S = "${WORKDIR}/${BP}"
UNPACKDIR = "${S}"
LUMI_DIR = "/home/root/res/cg5317"

do_install() {
    install -d -m 755 ${D}${LUMI_DIR}/binaries
    install ${UNPACKDIR}/SDK/binaries/CG5317-04.05.000.0020-DEFAULT.bin ${D}${LUMI_DIR}/binaries
    install ${UNPACKDIR}/SDK/binaries/eth_ev_config.bin ${D}${LUMI_DIR}/binaries
    install ${UNPACKDIR}/SDK/binaries/eth_evse_config.bin ${D}${LUMI_DIR}/binaries
    install ${UNPACKDIR}/SDK/binaries/golan_FlashUpgrade_image.dat ${D}${LUMI_DIR}/binaries
    install ${UNPACKDIR}/SDK/binaries/spi_dbg_ev_config.bin ${D}${LUMI_DIR}/binaries
    install ${UNPACKDIR}/SDK/binaries/spi_dbg_evse_config.bin ${D}${LUMI_DIR}/binaries
    install ${UNPACKDIR}/SDK/binaries/spi_ev_config.bin ${D}${LUMI_DIR}/binaries
    install ${UNPACKDIR}/SDK/binaries/spi_evse_config.bin ${D}${LUMI_DIR}/binaries
    install ${UNPACKDIR}/SDK/binaries/golan_FlashUpgrade_image.dat ${D}${LUMI_DIR}/binaries

    install -d -m 755 ${D}${LUMI_DIR}/host
    install -m 0755 ${UNPACKDIR}/SDK/host/examples/host_loading_service/host_loading_service ${D}${LUMI_DIR}/host
    install -m 0755 ${UNPACKDIR}/SDK/host/examples/ind_listener_app/ind_listener_app ${D}${LUMI_DIR}/host
    install -m 0755 ${UNPACKDIR}/SDK/host/examples/management_tool/management_tool ${D}${LUMI_DIR}/host
    install -m 0755 ${UNPACKDIR}/SDK/host/examples/user_config_editor/user_config_editor ${D}${LUMI_DIR}/host

    install -d -m 755 ${D}${LUMI_DIR}/prod
    install -m 0755 ${UNPACKDIR}/SDK/production/examples/fw_parser/fw_parser ${D}${LUMI_DIR}/prod
    install -m 0755 ${UNPACKDIR}/SDK/production/examples/mk_flash/mk_flash ${D}${LUMI_DIR}/prod
    install -m 0755 ${UNPACKDIR}/SDK/production/examples/production_tool/production_tool ${D}${LUMI_DIR}/prod
    install -m 0755 ${UNPACKDIR}/SDK/production/examples/production_tool/production_tool ${D}${LUMI_DIR}/prod

    install -d -m 755 ${D}${LUMI_DIR}/prog
    install -m 0755 ${UNPACKDIR}/SDK/Prog_tool/prog/bin/prog ${D}${LUMI_DIR}/prog
}

FILES:${PN} += "${LUMI_DIR}/* ${LUMI_DIR}/prog/* ${LUMI_DIR}/host/* ${LUMI_DIR}/binaries/* ${LUMI_DIR}/prod/*"
