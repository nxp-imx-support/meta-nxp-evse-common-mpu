SRC_URI:remove = "https://raw.githubusercontent.com/ros2/orocos_kdl_vendor/humble/orocos_kdl_vendor/0001-include_project_name.patch;name=patch;patchdir=orocos-kdl"

FILESEXTRAPATHS:prepend := "${THISDIR}/${BPN}:"
SRC_URI += "file://0001-include_project_name.patch;name=patch;patchdir=orocos-kdl"
