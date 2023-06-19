#!/bin/sh
#
# NXP Build Enviroment Setup Script
#
# Copyright (C) 2015-2016 Freescale Semiconductor
#
# This program is free software; you can redistribute it and/or modify
# it under the terms of the GNU General Public License as published by
# the Free Software Foundation; either version 2 of the License, or
# (at your option) any later version.
#
# This program is distributed in the hope that it will be useful,
# but WITHOUT ANY WARRANTY; without even the implied warranty of
# MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
# GNU General Public License for more details.
#
# You should have received a copy of the GNU General Public License
# along with this program; if not, write to the Free Software
# Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA

echo -e "\n----------------\n"
imx-easyevse_exit_message()
{
   echo "i.MX EasyEVSE setup complete"
}

imx-easyevse_usage()
{
    echo -e "\nDescription: setup-imx-easyevse.sh will setup the bblayers and local.conf for an i.MX EasyEVSE MPU build."
    echo -e "\nUsage: source setup-imx-easysevse.sh
    Optional parameters: [-b build-dir] [-r humble] [-h]"
    echo "
    * [-b build-dir]: Build directory, if unspecified, script uses 'build-imx-easyevse' as the output directory
    * [-r ROS_DISTRO]: the ROS distro (humble)
    * [-h]: help
"
}

echo Reading command line parameters
# Read command line parameters
while getopts "k:r:t:b:e:gh" nxp_setup_flag
do
    case $nxp_setup_flag in
        b) BUILD_DIR="$OPTARG";
           echo -e "\n Build directory is $BUILD_DIR" ;
           ;;
        r) if [ -z "$OPTARG" ]; then
               echo -e "\n ROS distro is missing!" ;
		   else
	           ros_distro="$OPTARG"
	           echo -e "\n ROS distro is $ros_distro" ;
		   fi
           ;;   
        h) nxp_setup_help='true';
           ;;
        ?) nxp_setup_error='true';
           ;;
    esac
done

RELEASEPROGNAME="./imx-setup-release.sh"

EXTRA_LAYER_LIST=" \
    meta-nxp-easyevse-mpu-dev \
"

# get command line options
OLD_OPTIND=$OPTIND

if [ -z "$BUILD_DIR" ]; then
    BUILD_DIR=build-imx-easyevse
fi

if [ -n "$BASH_SOURCE" ]; then
	ROOTDIR="`readlink -f $BASH_SOURCE | xargs dirname`"
elif [ -n "$ZSH_NAME" ]; then
	ROOTDIR="`readlink -f $0 | xargs dirname`"
else
	ROOTDIR="`readlink -f $PWD | xargs dirname`"
fi
SOURCEDIR="$ROOTDIR/../.."

echo source $RELEASEPROGNAME -b $BUILD_DIR
source $RELEASEPROGNAME -b $BUILD_DIR

echo -e "\n## ROS platform layers" >> $BUILD_DIR/conf/bblayers.conf

case $ros_distro in
    humble)
      echo -e "\n the ROS distro is $ros_distro" ;
      echo "ROS_DISTRO = \"humble\"" >> $BUILD_DIR/conf/local.conf
      echo "INSANE_SKIP:rosbag2-py += \"already-stripped\"" >> $BUILD_DIR/conf/local.conf
      echo "INSANE_SKIP:rclpy += \"already-stripped\"" >> $BUILD_DIR/conf/local.conf
      echo "PREFERRED_VERSION_cmake = \"3.25.%\"" >> $BUILD_DIR/conf/local.conf
      echo "PREFERRED_VERSION_cmake-native = \"3.25.%\"" >> $BUILD_DIR/conf/local.conf
      echo "EDGELOCK2GO_HOSTNAME = \"w4dx9d3ansxis0hw.device-link.edgelock2go.com\"" >> $BUILD_DIR/conf/local.conf
      echo "EDGELOCK2GO_PORT = \"443\"" >> $BUILD_DIR/conf/local.conf

      echo "BBLAYERS += \" \${BSPDIR}/sources/meta-ros/meta-ros2-humble \"" >> $BUILD_DIR/conf/bblayers.conf
      echo "BBLAYERS += \" \${BSPDIR}/sources/meta-ros/meta-ros2 \"" >> $BUILD_DIR/conf/bblayers.conf
      echo "BBLAYERS += \" \${BSPDIR}/sources/meta-ros/meta-ros-common \"" >> $BUILD_DIR/conf/bblayers.conf
      ;;           
    *) 
      echo -e "\n the ROS distro selected is invalid" ;
      ;;
esac
echo "BBLAYERS += \" \${BSPDIR}/sources/meta-nxp-easyevse-mpu \"" >> $BUILD_DIR/conf/bblayers.conf
echo "BBLAYERS += \" \${BSPDIR}/sources/meta-iot-cloud \"" >> $BUILD_DIR/conf/bblayers.conf

for layer in $(eval echo ${EXTRA_LAYER_LIST}); do
	if [ -e "${SOURCEDIR}/${layer}" ]; then
		echo "BBLAYERS += \" \${BSPDIR}/sources/$layer \"" >> $BUILD_DIR/conf/bblayers.conf
	fi
done

echo

imx-easyevse_exit_message

echo "Cleaning up variables"
unset BUILD_DIR
unset nxp_setup_help nxp_setup_error nxp_setup_flag ros_distro
