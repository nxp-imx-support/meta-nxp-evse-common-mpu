DESCRIPTION = "NXP EdgeLock SE05x Plug & Trust Middleware recipe"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/BSD-3-Clause;md5=550794465ba0ec5312d6919e203a55f9"
AUTHOR = "Marouene Boubakri <marouene.boubakri@nxp.com>"

SRC_URI = "file://${TOPDIR}/../se05x_mw_v04.05.00.zip \
           file://0001-paho.mqtt.c-fix-buildpaths-QA-warning.patch \
           file://0002-nxp_iot_agent-define-custom-EdgeLock-2GO-hostname-po.patch \
           file://device-link.edgelock2go.com.crt \
           "

S = "${WORKDIR}/simw-top"

DEPENDS:append = "\
    openssl \
    python3 \
    "

RDEPENDS_${PN}:append = "\
    libcrypto \
    bash \
    python3 \
    python3-core \
    python3-setuptools \
    python3-cffi \
    python3-click \
    python3-cryptography \
    python3-func-timeout \
    "

EDGELOCK2GO_HOSTNAME ?= "company-specific hostname"
EDGELOCK2GO_PORT ?= "443"

EXTRA_OECMAKE += " \
    -DPTMW_Applet=SE050_E \
    -DPTMW_SE05X_Auth=None \
    -DPTMW_SCP=None \
    -DPTMW_HostCrypto=OPENSSL \
    -DPTMW_Host=iMXLinux \
    -DPTMW_SMCOM=T1oI2C \
    -DEDGELOCK2GO_HOSTNAME:STRING=${EDGELOCK2GO_HOSTNAME} \
    -DEDGELOCK2GO_PORT:STRING=${EDGELOCK2GO_PORT} \
    "

inherit pkgconfig cmake

do_install:append() {

    # Trust EdgeLock 2GO CA certificate
    install -d ${D}${prefix}/local/share/ca-certificates
    install -m 0644 ${WORKDIR}/sources-unpack/device-link.edgelock2go.com.crt ${D}${prefix}/local/share/ca-certificates

    sed -i "s#/usr/local/lib/libsss_engine.so#/usr/lib/libsss_engine.so#g" ${S}/demos/linux/common/openssl11_sss_se050.cnf
    install -d ${D}${sysconfdir}/ssl/
    install -m 0644  ${S}/demos/linux/common/openssl11_sss_se050.cnf ${D}${sysconfdir}/ssl
    
    # Install ssscli (for development purpose)
    install -d ${D}/opt/ssscli
    cp -r ${S}/pycli/src/* ${D}/opt/ssscli
    cp -r ${S}/pycli/requirements.txt ${D}/opt/ssscli
}

SOLIBS = ".so"
FILES_SOLIBSDEV = ""

FILES:${PN}:append = " \
    ${prefix}/* \
    /opt/* \
    "
    
pkg_postinst_ontarget:${PN} () {
    update-ca-certificates
}
