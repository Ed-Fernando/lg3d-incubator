#!/bin/bash
LG3DHOME=../lg3d

LG_SETTINGS="${LG_SETTINGS} -Dlg.etcdir=${LG3DHOME}/etc/"

if [ "$LGX11HOME" == "" ]
then
  LGX11HOME=${LG3DHOME}/ext/lg3d-x11
fi

if [ "$LGCORESRC" == "" ]
then
  LGCORESRC=${LG3DHOME}/src
fi

CLASSPATH="${LG3DHOME}/lib/ext/lg3d-core.jar:${LG3DHOME}/"
for arg in `ls ${LG3DHOME}/ext/*.jar`
do
  CLASSPATH="$CLASSPATH:$arg"
done
LD_LIBRARY_PATH="${LG3DHOME}/lib/i386:${LD_LIBRARY_PATH}"

DISPLAY=":0.0"

CONFIG="lgconfig_1p_nox.xml"
LGCONFIG="file:///${LG3DHOME}/etc/lg3d/${CONFIG}"
DISP_CONFIG="-Dlg.displayconfigurl=file:///${LG3DHOME}/etc/lg3d/displayconfig/j3d1x1"
 
CLASSPATH=".:./build/lib/zoetrope.jar:$CLASSPATH"

java -Xmx512m -cp ${CLASSPATH} -Dj3d.sortShape3DBounds="true" ${LG_SETTINGS} -Dlg.configurl=${LGCONFIG} ${DISP_CONFIG} org.jdesktop.lg3d.apps.zoetrope.Zoetrope $1
