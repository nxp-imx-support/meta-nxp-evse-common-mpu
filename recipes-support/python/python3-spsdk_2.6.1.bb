SUMMARY = "spsdk: Open Source Secure Provisioning SDK for NXP MCU/MPU."

HOMEPAGE = "https://github.com/NXPmicro/spsdk"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://spsdk-2.6.1/LICENSE;md5=863e3c0c79e2589ac9d16c3918e115d1"

SRC_URI[sha256sum] = "83b77446250add7f9d0b747bafd62bfb9ad8c92ef22b5927ad50c1d5cbb3633b"
SRC_URI[wheel.sha256sum] = "89a1b6033a5c7025f44c0d4effe393467ab6edc7fff55f8f7fd15ad6a449fba7"
PYPI_PACKAGE = "spsdk"
PYPI_WHEEL_NAME = "spsdk-2.6.1-py3-none-any.whl" 

inherit pypi_wheel

S = "${WORKDIR}/sources"
UNPACKDIR = "${S}"

FILES:${PN} += "${datadir}/*"
