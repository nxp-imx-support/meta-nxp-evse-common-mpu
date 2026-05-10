DESCRIPTION = "NXP EdgeLock SE05x Plug & Trust Middleware recipe"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/BSD-3-Clause;md5=550794465ba0ec5312d6919e203a55f9"
AUTHOR = "Marouene Boubakri <marouene.boubakri@nxp.com>"

SRC_URI = "file://${TOPDIR}/../SE-PLUG-TRUST-MW_04.07.01.zip \
           "

S = "${WORKDIR}/simw-top"

DEPENDS:append = "\
    openssl \
    python3 \
    "

RDEPENDS:${PN}:append = "\
    libcrypto \
    bash \
    python3 \
    python3-core \
    python3-setuptools \
    python3-cffi \
    python3-click \
    python3-cryptography \
    func-timeout \
    "

EXTRA_OECMAKE += " \
    -DPTMW_Applet=SE050_E \
    -DPTMW_SE05X_Ver=03_XX \
    -DPTMW_SE05X_Auth=None \
    -DPTMW_SCP=None \
    -DPTMW_HostCrypto=OPENSSL \
    -DPTMW_OpenSSL=3_0 \
    -DOPENSSL_INSTALL_PREFIX=${WORKDIR}/recipe-sysroot/usr/ \
    -DOPENSSL_ROOT_DIR=${WORKDIR}/recipe-sysroot/usr/ \
    -DPTMW_Host=iMXLinux \
    -DPTMW_SMCOM=T1oI2C \
    "

inherit pkgconfig cmake

do_install() {
    install -d ${D}/opt/ssscli
    install -d ${D}${bindir}
    install -d ${D}${libdir}

    install -m 0555 ${WORKDIR}/build/bin/se05x_* ${D}${bindir}/
    install -m 0644 ${WORKDIR}/build/sss/libsssapisw.so ${D}${libdir}/
    install -m 0644 ${WORKDIR}/build/sss/plugin/pkcs11/libsss_pkcs11.so ${D}${libdir}/
    cp -r ${S}/pycli/src/* ${D}/opt/ssscli
    cp -r ${S}/pycli/requirements.txt ${D}/opt/ssscli
}

SOLIBS = ".so"
FILES_SOLIBSDEV = ""

FILES:${PN}:append = " \
    ${bindir} \
    ${libdir}/*.so \
    /opt/* \
    "

pkg_postinst_ontarget:${PN} () {
    ldconfig /usr/local/lib
    cd /opt/ssscli
    python3 setup.py develop
}
