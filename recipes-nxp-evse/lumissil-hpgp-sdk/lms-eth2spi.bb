# Copyright 2025 NXP

SUMMARY = "Lumissil CG5317 SPI driver"
DESCRIPTION = "HomePlug Green PHY loadable driver"
SECTION = "driver"
PV = "4.05.000"

LICENSE = "CLOSED"

inherit module

SRC_URI = "file://SPI_driver.zip \  
     \
"

S = "${WORKDIR}/${BP}"
UNPACKDIR = "${S}"

RPROVIDES_${PN} += "kernel-module-lms-eth2spi"