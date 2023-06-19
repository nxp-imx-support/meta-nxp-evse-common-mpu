SRC_URI:remove = "file://0001-Use-platform-yaml-cpp.patch"

FILESEXTRAPATHS:prepend := "${THISDIR}/${BPN}:"
SRC_URI += "file://0002-Use-platform-yaml-cpp.patch"
