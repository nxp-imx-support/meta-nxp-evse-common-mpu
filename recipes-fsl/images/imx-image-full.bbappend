ROOTFS_POSTPROCESS_COMMAND:append:mx93-nxp-bsp = "install_demo; install_demo_easyevse; "
ROOTFS_POSTPROCESS_COMMAND:append:mx8-nxp-bsp = "install_demo; install_demo_easyevse; "

install_demo_easyevse() {
	printf "\n\n[output]\nname=DSI-1\nmode=1920x1080@60\ntransform=rotate-270" >> ${IMAGE_ROOTFS}${sysconfdir}/xdg/weston/weston.ini
	touch ${IMAGE_ROOTFS}/home/root/.bashrc
	chmod 755 ${IMAGE_ROOTFS}/home/root/.bashrc
	printf "source /etc/profile.d/ros/setup.sh\n" >> ${IMAGE_ROOTFS}${sysconfdir}/xdg/weston/weston.ini	
}

install_demo() {
    if ! grep -q "HOME=/home/root/" ${IMAGE_ROOTFS}${sysconfdir}/default/weston
    then
        printf "\nHOME=/home/root/\nQT_QPA_PLATFORM=wayland" >> ${IMAGE_ROOTFS}${sysconfdir}/default/weston
    fi
}
