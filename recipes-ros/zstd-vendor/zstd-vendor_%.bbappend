SRC_URI:remove = "file://0001-CMakeLists.txt-prevent-building-zstd-with-ExternalPr.patch"

FILESEXTRAPATHS:prepend := "${THISDIR}/${BPN}:"
SRC_URI += "file://0001-PATCH-CMakeLists.txt-prevent-building-zstd-with-Exte.patch"
