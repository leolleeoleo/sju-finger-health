#
# Generated Makefile - do not edit!
#
# Edit the Makefile in the project folder instead (../Makefile). Each target
# has a -pre and a -post target defined where you can add customized code.
#
# This makefile implements configuration specific macros and targets.


# Environment
MKDIR=mkdir
CP=cp
GREP=grep
NM=nm
CCADMIN=CCadmin
RANLIB=ranlib
CC=gcc
CCC=g++
CXX=g++
FC=gfortran
AS=as

# Macros
CND_PLATFORM=Cygwin-Windows
CND_CONF=Debug
CND_DISTDIR=dist
CND_BUILDDIR=build

# Include project Makefile
include Makefile

# Object Directory
OBJECTDIR=${CND_BUILDDIR}/${CND_CONF}/${CND_PLATFORM}

# Object Files
OBJECTFILES= \
	${OBJECTDIR}/src/state.o \
	${OBJECTDIR}/src/ra8806.o \
	${OBJECTDIR}/src/main.o \
	${OBJECTDIR}/src/config.o \
	${OBJECTDIR}/src/client.o \
	${OBJECTDIR}/src/iic.o \
	${OBJECTDIR}/src/lcd1602.o \
	${OBJECTDIR}/src/lcd.o \
	${OBJECTDIR}/src/interrupt.o \
	${OBJECTDIR}/src/uart.o \
	${OBJECTDIR}/src/iap.o \
	${OBJECTDIR}/src/base.o \
	${OBJECTDIR}/src/spi.o \
	${OBJECTDIR}/src/keyboard.o \
	${OBJECTDIR}/src/tfs_m51.o \
	${OBJECTDIR}/src/touch.o \
	${OBJECTDIR}/src/sim300.o \
	${OBJECTDIR}/src/sc16is752.o \
	${OBJECTDIR}/src/flash.o


# C Compiler Flags
CFLAGS=

# CC Compiler Flags
CCFLAGS=
CXXFLAGS=

# Fortran Compiler Flags
FFLAGS=

# Assembler Flags
ASFLAGS=

# Link Libraries and Options
LDLIBSOPTIONS=

# Build Targets
.build-conf: ${BUILD_SUBPROJECTS}
	"${MAKE}"  -f nbproject/Makefile-${CND_CONF}.mk ${CND_DISTDIR}/${CND_CONF}/${CND_PLATFORM}/healthmicroprocessor.exe

${CND_DISTDIR}/${CND_CONF}/${CND_PLATFORM}/healthmicroprocessor.exe: ${OBJECTFILES}
	${MKDIR} -p ${CND_DISTDIR}/${CND_CONF}/${CND_PLATFORM}
	${LINK.c} -o ${CND_DISTDIR}/${CND_CONF}/${CND_PLATFORM}/healthmicroprocessor ${OBJECTFILES} ${LDLIBSOPTIONS} 

${OBJECTDIR}/src/state.o: src/state.c 
	${MKDIR} -p ${OBJECTDIR}/src
	${RM} $@.d
	$(COMPILE.c) -g -MMD -MP -MF $@.d -o ${OBJECTDIR}/src/state.o src/state.c

${OBJECTDIR}/src/ra8806.o: src/ra8806.c 
	${MKDIR} -p ${OBJECTDIR}/src
	${RM} $@.d
	$(COMPILE.c) -g -MMD -MP -MF $@.d -o ${OBJECTDIR}/src/ra8806.o src/ra8806.c

${OBJECTDIR}/src/main.o: src/main.c 
	${MKDIR} -p ${OBJECTDIR}/src
	${RM} $@.d
	$(COMPILE.c) -g -MMD -MP -MF $@.d -o ${OBJECTDIR}/src/main.o src/main.c

${OBJECTDIR}/src/config.o: src/config.c 
	${MKDIR} -p ${OBJECTDIR}/src
	${RM} $@.d
	$(COMPILE.c) -g -MMD -MP -MF $@.d -o ${OBJECTDIR}/src/config.o src/config.c

${OBJECTDIR}/src/client.o: src/client.c 
	${MKDIR} -p ${OBJECTDIR}/src
	${RM} $@.d
	$(COMPILE.c) -g -MMD -MP -MF $@.d -o ${OBJECTDIR}/src/client.o src/client.c

${OBJECTDIR}/src/iic.o: src/iic.c 
	${MKDIR} -p ${OBJECTDIR}/src
	${RM} $@.d
	$(COMPILE.c) -g -MMD -MP -MF $@.d -o ${OBJECTDIR}/src/iic.o src/iic.c

${OBJECTDIR}/src/lcd1602.o: src/lcd1602.c 
	${MKDIR} -p ${OBJECTDIR}/src
	${RM} $@.d
	$(COMPILE.c) -g -MMD -MP -MF $@.d -o ${OBJECTDIR}/src/lcd1602.o src/lcd1602.c

${OBJECTDIR}/src/lcd.o: src/lcd.c 
	${MKDIR} -p ${OBJECTDIR}/src
	${RM} $@.d
	$(COMPILE.c) -g -MMD -MP -MF $@.d -o ${OBJECTDIR}/src/lcd.o src/lcd.c

${OBJECTDIR}/src/interrupt.o: src/interrupt.c 
	${MKDIR} -p ${OBJECTDIR}/src
	${RM} $@.d
	$(COMPILE.c) -g -MMD -MP -MF $@.d -o ${OBJECTDIR}/src/interrupt.o src/interrupt.c

${OBJECTDIR}/src/uart.o: src/uart.c 
	${MKDIR} -p ${OBJECTDIR}/src
	${RM} $@.d
	$(COMPILE.c) -g -MMD -MP -MF $@.d -o ${OBJECTDIR}/src/uart.o src/uart.c

${OBJECTDIR}/src/iap.o: src/iap.asm 
	${MKDIR} -p ${OBJECTDIR}/src
	$(AS) $(ASFLAGS) -g -o ${OBJECTDIR}/src/iap.o src/iap.asm

${OBJECTDIR}/src/base.o: src/base.c 
	${MKDIR} -p ${OBJECTDIR}/src
	${RM} $@.d
	$(COMPILE.c) -g -MMD -MP -MF $@.d -o ${OBJECTDIR}/src/base.o src/base.c

${OBJECTDIR}/src/spi.o: src/spi.c 
	${MKDIR} -p ${OBJECTDIR}/src
	${RM} $@.d
	$(COMPILE.c) -g -MMD -MP -MF $@.d -o ${OBJECTDIR}/src/spi.o src/spi.c

${OBJECTDIR}/src/keyboard.o: src/keyboard.c 
	${MKDIR} -p ${OBJECTDIR}/src
	${RM} $@.d
	$(COMPILE.c) -g -MMD -MP -MF $@.d -o ${OBJECTDIR}/src/keyboard.o src/keyboard.c

${OBJECTDIR}/src/tfs_m51.o: src/tfs_m51.c 
	${MKDIR} -p ${OBJECTDIR}/src
	${RM} $@.d
	$(COMPILE.c) -g -MMD -MP -MF $@.d -o ${OBJECTDIR}/src/tfs_m51.o src/tfs_m51.c

${OBJECTDIR}/src/touch.o: src/touch.c 
	${MKDIR} -p ${OBJECTDIR}/src
	${RM} $@.d
	$(COMPILE.c) -g -MMD -MP -MF $@.d -o ${OBJECTDIR}/src/touch.o src/touch.c

${OBJECTDIR}/src/sim300.o: src/sim300.c 
	${MKDIR} -p ${OBJECTDIR}/src
	${RM} $@.d
	$(COMPILE.c) -g -MMD -MP -MF $@.d -o ${OBJECTDIR}/src/sim300.o src/sim300.c

${OBJECTDIR}/src/sc16is752.o: src/sc16is752.c 
	${MKDIR} -p ${OBJECTDIR}/src
	${RM} $@.d
	$(COMPILE.c) -g -MMD -MP -MF $@.d -o ${OBJECTDIR}/src/sc16is752.o src/sc16is752.c

${OBJECTDIR}/src/flash.o: src/flash.c 
	${MKDIR} -p ${OBJECTDIR}/src
	${RM} $@.d
	$(COMPILE.c) -g -MMD -MP -MF $@.d -o ${OBJECTDIR}/src/flash.o src/flash.c

# Subprojects
.build-subprojects:

# Clean Targets
.clean-conf: ${CLEAN_SUBPROJECTS}
	${RM} -r ${CND_BUILDDIR}/${CND_CONF}
	${RM} ${CND_DISTDIR}/${CND_CONF}/${CND_PLATFORM}/healthmicroprocessor.exe

# Subprojects
.clean-subprojects:

# Enable dependency checking
.dep.inc: .depcheck-impl

include .dep.inc
