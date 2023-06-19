SUMMARY = "NXP EasyEVSE Helpers"
DESCRIPTION = "Adds auxiliary files for well functioning of EasyEVSE MPU demo"
LICENSE = "CLOSED"
LIC_FILES_CHKSUM = ""

RDEPENDS:${PN} += " bash"

inherit pkgconfig

PV = "1.0+${SRCPV}"

S = "${WORKDIR}/git/src/easyevse/src"

NXP_EASYEVSE_SRC ?= "git://github.com/nxp-imx-support/nxp-easyevse-mpu.git;protocol=https"
SRCBRANCH_easyevse ?= "${SRC_BRANCH}"

SRC_URI= "\
	${NXP_EASYEVSE_SRC};branch=${SRCBRANCH_easyevse};name=easyevse \
	 "

SRCREV_easyevse = "${AUTOREV}"

do_install() {
    install -d -m 755 ${D}/home/root/.nxp-easyevse
	cp ${S}/cloud/cloud.conf ${D}/home/root/.nxp-easyevse
	cp ${S}/../../evse-startup.sh ${D}/home/root/.nxp-easyevse
	ln -s root/res ${D}/home/res
	cp -R ${S}/../../cloud-manual-provisioning ${D}/home/root/.nxp-easyevse
}

FILES:${PN} += "/home/root/.nxp-easyevse/* "
FILES:${PN} += "/home/res "
