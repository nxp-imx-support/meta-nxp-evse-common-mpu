DESCRIPTION = "Example of minimal publisher/subscriber using rclcpp."
LICENSE = "Apache License 2.0"
LIC_FILES_CHKSUM = "file://package.xml;beginline=8;endline=8;md5=12c26a18c7f493fdc7e8a93b16b7c04f"

SRC_URI = "git://github.com/bojankoce/ros2pkg;protocol=https"

# Modify these as desired
PV = "0.1.0"
SRCREV = "1312445de2e6861d9561c0f89f4827b94c2ff6b1"

DEPENDS = "ament-cmake-native rclcpp std-msgs"

S = "${WORKDIR}/git"

# NOTE: unable to map the following CMake package dependencies: rclcpp ament_lint_auto std_msgs ros_ament_cmake