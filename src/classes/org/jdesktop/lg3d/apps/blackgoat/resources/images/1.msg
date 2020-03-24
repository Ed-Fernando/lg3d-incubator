lg3d-interest@javadesktop.org
08/12/05 6:20 PM
LgClassLoader
I've created a reloadable classloader for lg3d, it allows you to make
changes to your code and restart your application without having to
restart all of lg3d. I won't commit the code to the cvs yet (as it
will break normal operation). I need to round it up, and do a little
bit of optimisation in the classpath system.

You can see an example screenshot at:
http://gameldar.fdns.net/lg3d/lgclassloader.jpg

This should two different versions of the Help dialog where I've
changed the colour and recompiled, and then run it again.

How does this work?

Basically there are two types of classpaths now - reloadable, and
non-reloadable (or system) classpaths. The system classpaths are the
core lg3d components - e.g. displayserver, scenemanager, wg, sg
packages (or anything in lg3d-core/src/classes).
The reloadable classpaths are anything that is in the
'application/user' space - e.g ext/app jars, lg3d-incubator and
lg3d-demo-apps.

Whenever an application is launched a new LgClassLoader is used to
load the classes for the application, which reloads all the classes in
the reloadable classpath. Therefore if these are changed then the
updated classes are used.

As an aside (and actually the reason I started developing this) the
CLASSPATH is no longer needed within the startup scripts (except that
the LgClassLoader must be upon the classpath), and this fixes the
'Line too long' problems.
The classpaths can be edited by two separate config files in the etc/
directory. classpath.cfg and reloadclasspath.cfg. The files have the
following format:

lt;type>:<path relative to lg.dir>
Where <type> is either 'dir' or 'lib'two 
lib meaning that any jar files within that directory will be added to
the classpath.



You can grab the code for this at:
http://gameldar.fdns.net/lg3d/lgclassloader.tar.gz

Note that it puts the LgClassLoader classes in a directory
classes/org/jdesktop/lg3d/utils (not build/classes) as the
LgClassLoader classes have to be on the CLASSPATH. I manually copy of
these classes after a build (I haven't set up a special ant target
yet).

If you uncompress this in your lg3d-core directory you will need to
build the core (there are changes to AppLaunchAction.java), then you
can run it via the new scripts lg3d-cl in the src/devscripts
directory.

There are a couple of problems with it at the moment (I haven't tested
my latest changes on Windows, but I did have it working there, I'll
try that this weekend). The main one is that the jar files in the
ext/app directory are being left open when being read, and so you
can't override them in Windows to put your changes in (hence the
'detecting open files' thread a couple of days ago).

But please feel free to play around and break it. I'm sure it won't be hard

Kirk

---------------------------------------------------------------------
To unsubscribe, e-mail: interest-unsubscribe@lg3d.dev.java.net
For additional commands, e-mail: interest-help@lg3d.dev.java.net
