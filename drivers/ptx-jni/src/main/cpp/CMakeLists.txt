cmake_minimum_required (VERSION 3.6)
project (Tornado)

find_package(JNI REQUIRED)

include_directories(
	source/
	${JNI_INCLUDE_DIRS}
	${C_INCLUDE_PATH}
	${CPLUS_INCLUDE_PATH}
)

file(GLOB_RECURSE SOURCE_FILES "*.c")
add_library(tornado-ptx SHARED
	source/PTXPlatform.c
	source/PTXContext.c
	source/PTXModule.c
	source/PTXStream.c
	source/PTXDevice.c
	source/PTXEvent.c
	source/PTX.c
	source/utils.c
)

#Support for CUDA Integration
FIND_PACKAGE(CUDA)
if(CUDA_FOUND)
    include_directories(${CUDA_INCLUDE_DIRS})
    link_directories(tornado-ptx ${CUDA_TOOLKIT_ROOT_DIR})
    set_property(TARGET tornado-ptx PROPERTY C_STANDARD 99)
    set_target_properties(tornado-ptx PROPERTIES LINKER_LANGUAGE C)
    target_link_libraries(tornado-ptx ${JNI_LIB_DIRS} -lcuda)
else(CUDA_FOUND)
    message("CUDA is not installed on this system.")
endif()


