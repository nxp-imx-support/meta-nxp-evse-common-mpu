SRC_URI:remove = "file://0001-CMakeLists.txt-drop-dependency-on-zstd_vendor.patch"

FILESEXTRAPATHS:prepend := "${THISDIR}/${BPN}:"
SRC_URI += "file://0002-CMakeLists.txt-drop-dependency-on-zstd_vendor.patch"

inherit pkgconfig
