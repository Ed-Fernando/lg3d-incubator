@echo off
set LG3DHOME=..\lg3d

if "%LGX11HOME%" == "" set LGX11HOME=%LG3DHOME%\ext\lg3d-x11

set TMP_CLASSPATH=.;.\build\lib\zoetrope.jar
set TMP_CLASSPATH=%TMP_CLASSPATH%;%LG3DHOME%\lib\ext\lg3d-core.jar
set TMP_CLASSPATH=%TMP_CLASSPATH%;%LG3DHOME%\ext\escher-0.2.2.lg.jar
set TMP_CLASSPATH=%TMP_CLASSPATH%;%LG3DHOME%\ext\jaimlib.jar
set TMP_CLASSPATH=%TMP_CLASSPATH%;%LG3DHOME%\ext\nwn-0.7.jar
set TMP_CLASSPATH=%TMP_CLASSPATH%;%LG3DHOME%\ext\odejava.jar
set TMP_CLASSPATH=%TMP_CLASSPATH%;%LG3DHOME%\ext\satin-v2.3.jar

set CONFIG=lgconfig_1p_nox.xml
set LGCONFIG=file:%LG3DHOME%\etc\lg3d\%CONFIG%

set DISP_CONFIG=-Dlg.displayconfigurl=file:%LG3DHOME%/etc/lg3d/displayconfig/j3d1x1

java -Xmx512m -cp %TMP_CLASSPATH% -Dj3d.sortShape3DBounds="true" -Dlg.configurl=%LGCONFIG% %DISP_CONFIG% -Dlg.etcdir=%LG3DHOME%\etc\ org.jdesktop.lg3d.apps.zoetrope.Zoetrope %1

set LG3DHOME=
set LGX11HOME=
set TMP_CLASSPATH=
set CONFIG=
set LGCONFIG=
set DISP_CONFIG=
