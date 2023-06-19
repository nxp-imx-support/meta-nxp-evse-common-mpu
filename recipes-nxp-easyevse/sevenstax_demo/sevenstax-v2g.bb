# Copyright 2024 NXP

SUMMARY = "Sevenstax V2G Demo"
DESCRIPTION = "Electric Vehicle Charger Demo"
SECTION = "application"
DEPENDS = "sevenstax-stack libgpiod"
RDEPENDS:${PN} += "lumissil-hpgp-sdk"
PV = "03.00.00"

LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://${WORKDIR}/${BP}/COPYING;md5=2827219e81f28aba7c6a569f7c437fa7"

inherit fsl-eula-unpack module pkgconfig cmake

STX_BIN_DIR ?= "${FSL_MIRROR}"
SRC_URI = "${STX_BIN_DIR}/${BP}.bin;fsl-eula=true \
	   file://sevenstax.sh \
"
SRC_URI[md5sum] = "22b28e9af1fa730e37333909983f0cb6"
SRC_URI[sha256sum] = "08c763853eba5df5eb8193aa7a33f8f39bc145e9183d2520e62b813d40185b3a"

# By default build ISO15118-2 EV binaries
USE_LEGACY_ISO2 ?= "1"
# By default do not enable EIM with NFC
USE_EIM_BY_NFC ?= "0"

# Enable ISO 15118-20
FEATPROD_V2G_ISO20_SUPPORTED ?= "ON"

# TLS support
FEATPROD_TLS13_CLNT_SUPPORTED ?= "ON"
FEATPROD_TLS13_SERV_SUPPORTED ?= "ON"
FEATPROD_TLSCLNT_SUPPORTED ?= "ON"
FEATPROD_TLSSERV_SUPPORTED ?= "ON"
# NXP security for EVSE
FEATPROD_SERV_PKCS11_SUPPORTED ?= "ON"
# SevenSTAX security for EV
FEATPROD_CLNT_PKCS11_SUPPORTED ?= "OFF"
# EIM by NFC
FEATPROD_PAYMENTOPTION_EIM_NFC ?= "${@oe.utils.conditional('USE_EIM_BY_NFC', '1', 'ON', 'OFF', d)}"

FEATURES_V2G = "-DFEATPROD_V2G_ISO20_SUPPORTED:STRING=${FEATPROD_V2G_ISO20_SUPPORTED} "

FEATURES_TLS_SERV = "-DFEATPROD_TLS13_SERV_SUPPORTED:STRING=${FEATPROD_TLS13_SERV_SUPPORTED} \
	-DFEATPROD_TLSSERV_SUPPORTED:STRING=${FEATPROD_TLSSERV_SUPPORTED} "

FEATURES_TLS_CLNT = "-DFEATPROD_TLS13_CLNT_SUPPORTED:STRING=${FEATPROD_TLS13_CLNT_SUPPORTED} \
	-DFEATPROD_TLSCLNT_SUPPORTED:STRING=${FEATPROD_TLSCLNT_SUPPORTED} "

FEATURES_EVSE = "${FEATURES_V2G} ${FEATURES_TLS_SERV} -DFEATPROD_PKCS11_SUPPORTED:STRING=${FEATPROD_SERV_PKCS11_SUPPORTED} \
			-DFEATPROD_V2G_PAYMENTOPTION_EIM_NFC:STRING=${FEATPROD_PAYMENTOPTION_EIM_NFC}"

FEATURES_EV = "${FEATURES_V2G} ${FEATURES_TLS_CLNT} -DFEATPROD_PKCS11_SUPPORTED:STRING=${FEATPROD_CLNT_PKCS11_SUPPORTED}"

SEVENSTAX_STACK ?= "sevenstax-stack"
SEVENSTAX_DIR ?= "/home/root"
RES_DIR = "${SEVENSTAX_DIR}/res"

python do_display_banner() {
    bb.plain("***********************************************");
    bb.plain("*                                             *");
    bb.plain("*  CMAKE PRESET SEVENSTAX DEMOS Compiling     *");
    bb.plain("*                                             *");
    bb.plain("***********************************************");
}
addtask display_banner before do_build

do_configure(){
    cd ${S}
    mkdir -p lib
    cp ${PKG_CONFIG_SYSROOT_DIR}/${libdir}/${SEVENSTAX_STACK}/libstx_*.a lib
    (cd ${PKG_CONFIG_SYSROOT_DIR}/${includedir}/${SEVENSTAX_STACK} && find . -print | cpio -o) \
	| cpio -idmv

	cmake ${FEATURES_EVSE} --preset demo_v2g_evse_pnc_cg5317_eth_linux_imx93_release
	cmake -DFEATPROD_V2G_PNC_SUPPORTED:STRING="ON" \
		-DFEATPROD_EV_PAYMENTOPTION_EXTERNAL:STRING="OFF" \
		${FEATURES_EV} --preset demo_v2g_pev_pnc_cg5317_eth_linux_imx93_release
	cmake -DFEATPROD_V2G_PNC_SUPPORTED:STRING="OFF" \
		-DFEATPROD_EV_PAYMENTOPTION_EXTERNAL:STRING="ON" \
		${FEATURES_EV} --preset demo_v2g_pev_eim_cg5317_eth_linux_imx93_release

	if [ "${USE_LEGACY_ISO2}" = "1" ]; then
		# Legacy ISO15118-2 EV PnC configuration - build with defaults
		cmake  --preset demo_v2g_pev_pnc_cg5317_eth_linux_legacy_imx93_release
		cmake  --preset demo_v2g_pev_eim_cg5317_eth_linux_legacy_imx93_release
	fi
}

do_compile(){
    cd ${S}
    cmake --build ./build_demo_v2g_evse_pnc_cg5317_eth_linux_imx93_release
    cmake --build ./build_demo_v2g_pev_pnc_cg5317_eth_linux_imx93_release
    cmake --build ./build_demo_v2g_pev_eim_cg5317_eth_linux_imx93_release

	if [ "${USE_LEGACY_ISO2}" = "1" ]; then
		cmake --build ./build_demo_v2g_pev_pnc_cg5317_eth_linux_legacy_imx93_release
		cmake --build ./build_demo_v2g_pev_eim_cg5317_eth_linux_legacy_imx93_release
	fi
}

do_install(){

    install -d -m 0755 ${D}${libdir}
    install -d -m 0755 ${D}${includedir}
    install -d -m 0755 ${D}${SEVENSTAX_DIR}
    install -d -m 0755 ${D}${sysconfdir}/profile.d
    cd ${S}/build_demo_v2g_evse_pnc_cg5317_eth_linux_imx93_release

    V2G_EVSE="v2g_evse_pnc_cg5317_eth"
    V2G_PEV_PNC="v2g_pev_pnc_cg5317_eth"
    V2G_PEV_EIM="v2g_pev_eim_cg5317_eth"

    # install the STX libraries
    install -m 0644 ${S}/build_demo_${V2G_EVSE}_linux_imx93_release/target/imx93/libstx_${V2G_EVSE}.a ${D}${libdir}
    install -m 0644 ${S}/build_demo_${V2G_PEV_PNC}_linux_imx93_release/target/imx93/libstx_${V2G_PEV_PNC}.a ${D}${libdir}
    install -m 0644 ${S}/build_demo_${V2G_PEV_EIM}_linux_imx93_release/target/imx93/libstx_${V2G_PEV_EIM}.a ${D}${libdir}

	if [ "${USE_LEGACY_ISO2}" = "1" ]; then
		install -m 0644 ${S}/build_demo_${V2G_PEV_PNC}_linux_legacy_imx93_release/target/imx93/libstx_${V2G_PEV_PNC}_iso2.a ${D}${libdir}
		install -m 0644 ${S}/build_demo_${V2G_PEV_EIM}_linux_legacy_imx93_release/target/imx93/libstx_${V2G_PEV_EIM}_iso2.a ${D}${libdir}
	fi

    # install all the necessary headers for the ROS client
    install -m 0755 ${S}/appl/*.h                                   	${D}${includedir}
    install -m 0755 ${S}/configs/demo_v2g_evse_pnc_cg5317_eth_linux/*.h	${D}${includedir}
    install -m 0755 ${S}/driver/ethernet/*.h                        	${D}${includedir}
    install -m 0755 ${S}/driver/trgthw/*.h                          	${D}${includedir}
    install -m 0755 ${S}/helper/*/*.h                               	${D}${includedir}
    install -m 0755 ${S}/include/*                                  	${D}${includedir}
    install -m 0755 ${S}/products/tcpip/ip.h                        	${D}${includedir}
    install -m 0755 ${S}/products/tcpip/ipv6/ipv6.h                 	${D}${includedir}
    install -m 0755 ${S}/target/imx93/include/*                     	${D}${includedir}

    install -m 0755 ${S}/build_demo_${V2G_EVSE}_linux_imx93_release/target/imx93/stx_evse_eth 	${D}${SEVENSTAX_DIR}
    install -m 0755 ${S}/build_demo_${V2G_PEV_PNC}_linux_imx93_release/target/imx93/stx_pev_eth_pnc 	${D}${SEVENSTAX_DIR}
    install -m 0755 ${S}/build_demo_${V2G_PEV_EIM}_linux_imx93_release/target/imx93/stx_pev_eth_eim 	${D}${SEVENSTAX_DIR}

	if [ "${USE_LEGACY_ISO2}" = "1" ]; then
		install -m 0755 ${S}/build_demo_${V2G_PEV_PNC}_linux_legacy_imx93_release/target/imx93/stx_pev_eth_pnc_iso2 	${D}${SEVENSTAX_DIR}
		install -m 0755 ${S}/build_demo_${V2G_PEV_EIM}_linux_legacy_imx93_release/target/imx93/stx_pev_eth_eim_iso2 	${D}${SEVENSTAX_DIR}
	fi

    # install testing V2G keys and certificates
    install -d -m 0755 ${D}${RES_DIR}
    cp -r ${S}/res/*   ${D}${RES_DIR}
    # install keys provisioning script
    install -m 0755 ${S}/res/se05x/provision.sh ${D}${RES_DIR}/se05x/

    # install shell configuration
    install -m 0644 ${WORKDIR}/sevenstax.sh ${D}${sysconfdir}/profile.d
}

FILES:${PN} += "${SEVENSTAX_DIR}"
FILES:${PN} += "${sysconfdir}"
FILES:${PN}-staticdev += " /usr/* "
