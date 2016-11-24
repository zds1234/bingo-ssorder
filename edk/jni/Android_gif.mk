LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE    := libgif
LOCAL_CFLAGS = -DFIXED_POINT -DUSE_KISS_FFT -DEXPORT="" -UHAVE_CONFIG_H

LOCAL_SRC_FILES := gif.c \
./giflib/dgif_lib.c \
./giflib/gif_hash.c \
./giflib/gifalloc.c 

include $(BUILD_SHARED_LIBRARY)

