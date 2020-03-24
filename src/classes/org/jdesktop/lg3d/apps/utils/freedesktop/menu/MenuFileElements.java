/***************************************************************************
 *                                                                         *
 *   This program is free software; you can redistribute it and/or modify  *
 *   it under the terms of the GNU General Public License as published by  *
 *   the Free Software Foundation; either version 2 of the License, or     *
 *   (at your option) any later version.                                   *
 *                                                                         *
 *   This program is distributed in the hope that it will be useful,       *
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of        *
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the         *
 *   GNU General Public License for more details.                          *
 *                                                                         *
 *   You should have received a copy of the GNU General Public License     *
 *   along with this program; if not, write to the                         *
 *   Free Software Foundation, Inc.,                                       *
 *   59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.             *
 *                                                                         *
 *   Copyright (C) 2006                                                    * 
 *                  Juan Gonzalez (juan@aga-system.com)                    *
 *                & Sun Microsystems                                       *
 *                                                                         *
 ***************************************************************************
 * MenuFileElements.java
 *
 * Created on 4 de agosto de 2006, 15:47
 *
 * $Revision: 1.3 $
 * $Date: 2006-08-17 23:22:20 $
 * $State: Exp $
 */
package org.jdesktop.lg3d.apps.utils.freedesktop.menu;


/**
 * This class is used to identify the type of each node from the menu file,
 * and transform it into a typesage enum for processing easyness.
 * <B>NOTE</B>: The javadocs on this class are duplicated for each element type so they are accesible from all contexts
 */
public class MenuFileElements {
    /**
     * The typesafe enum used to ease the processing of the menu file
     */
    public static enum Possible {
        /**
         * The root element is &lt;Menu>. Each &lt;Menu> element may contain any number of nested &lt;Menu> 
         * elements, indicating submenus.
         */
        Menu,
        /**
         * This element may only appear below &lt;Menu>. The content of this element is a directory name.
         * Desktop entries in this directory are scanned and added to the pool of entries which can 
         * be included in this &lt;Menu> and its submenus. Only files ending in ".desktop" should be used, 
         * other files are ignored.
         * 
         * Desktop entries in the pool of available entries are identified by their desktop-file
         * id (see Desktop-File Id). The desktop-file id of a desktop entry is equal to its filename, 
         * with any path components removed. So given a &lt;AppDir> /foo/bar and desktop entry 
         * /foo/bar/Hello.desktop the desktop entry would get a desktop-file id of Hello.desktop
         * 
         * If the directory contains sub-directories then these sub-directories should be (recursively)
         * scanned as well. The name of the subdirectory should be added as prefix to the desktop-file 
         * id together with a dash character ("-") So given a &lt;AppDir> /foo/bar and desktop entry 
         * /foo/bar/booz/Hello.desktop the desktop entry would get a desktop-file id of booz-Hello.desktop 
         * A desktop entry /foo/bar/bo/oz/Hello.desktop would result in a desktop-file id of bo-oz-Hello.desktop
         * 
         * &lt;AppDir> elements appearing later in the menu file have priority in case of collisions
         * between desktop-file ids.
         * 
         * If the filename given as an &lt;AppDir> is not an absolute path, it should be located relative 
         * to the location of the menu file being parsed.
         * 
         * Duplicate &lt;AppDir> elements (that specify the same directory) should be ignored, but 
         * the last duplicate in the file should be used when establishing the order in which to
         * scan the directories. This is important when merging (see the section called “Merging”). 
         * The order of &lt;AppDir> elements with respect to &lt;Include> and &lt;Exclude> elements is not
         * relevant, also to facilitate merging.
         */
        AppDir,
        /**
         * This element may only appear below &lt;Menu>. The element has no content. The element should 
         * be treated as if it were a list of &lt;AppDir> elements containing the default app dir locations 
         * (datadir/applications/ etc.). When expanding &lt;DefaultAppDirs> to a list of &lt;AppDir>, the 
         * default locations that are earlier in the search path go later in the &lt;Menu> so that they 
         * have priority.
         */
        DefaultAppDirs,
        /**
         * This element may only appear below &lt;Menu>. The content of this element is a directory name.
         * Each directory listed in a &lt;DirectoryDir> element will be searched for directory entries 
         * to be used when resolving the &lt;Directory> element for this menu and its submenus. 
         * If the filename given as a &lt;DirectoryDir> is not an absolute path, it should be located 
         * relative to the location of the menu file being parsed.
         * 
         * Directory entries in the pool of available entries are identified by their relative path 
         * (see Relative path).
         * 
         * If two directory entries have duplicate relative paths, the one from the last (furthest down)
         * element in the menu file must be used. Only files ending in the extension ".directory" should 
         * be loaded, other files should be ignored.
         * 
         * Duplicate &lt;DirectoryDir> elements (that specify the same directory) are handled as with 
         * duplicate &lt;AppDir> elements (the last duplicate is used).
         */
        DirectoryDir,
        /**
         * This element may only appear below &lt;Menu>. The element has no content. The element should 
         * be treated as if it were a list of &lt;DirectoryDir> elements containing the default desktop 
         * dir locations (datadir/desktop-directories/ etc.). The default locations that are earlier 
         * in the search path go later in the &lt;Menu> so that they have priority.
         */
        DefaultDirectoryDirs,
        /**
         * Each &lt;Menu> element must have a single &lt;Name>  element. The content of the &lt;Name> element 
         * is a name to be used when referring to the given menu. Each submenu of a given &lt;Menu> must 
         * have a unique name. &lt;Menu>  elements can thus be referenced by a menu path, for example 
         * "Applications/Graphics." The &lt;Name> field must not contain the slash character ("/"); 
         * implementations should discard any name containing a slash. See also Menu path.
         */
        Name,
        /**
         * Each &lt;Menu> element has any number of &lt;Directory>  elements. The content of the &lt;Directory> 
         * element is the relative path of a directory entry containing meta information about the &lt;Menu>,
         * such as its icon and localized name. If no &lt;Directory> is specified for a &lt;Menu>, its &lt;Name> 
         * field should be used as the user-visible name of the menu.
         * 
         * Duplicate &lt;Directory> elements are allowed in order to simplify menu merging, and allow user 
         * menus to override system menus. The last &lt;Directory> element to appear in the menu file "wins" 
         * and other elements are ignored, unless the last element points to a nonexistent directory entry, 
         * in which case the previous element should be tried instead, and so on.
         */
        Directory,
        /**
         * Each &lt;Menu> may contain any number of &lt;OnlyUnallocated> and &lt;NotOnlyUnallocated>  elements. 
         * Only the last such element to appear is relevant, as it determines whether the &lt;Menu> can 
         * contain any desktop entries, or only those desktop entries that do not match other menus. 
         * If neither &lt;OnlyUnallocated> nor &lt;NotOnlyUnallocated> elements are present, the default is 
         * &lt;NotOnlyUnallocated>.
         * 
         * To handle &lt;OnlyUnallocated>, the menu file must be analyzed in two conceptual passes. 
         * The first pass processes &lt;Menu> elements that can match any desktop entry. During this pass, 
         * each desktop entry is marked as allocated according to whether it was matched by an &lt;Include> 
         * rule in some &lt;Menu>. The second pass processes only &lt;Menu> elements that are restricted to 
         * unallocated desktop entries. During the second pass, queries may only match desktop entries 
         * that were not marked as allocated during the first pass. See the section called “Generating the menus”.
         */
        OnlyUnallocated,
        /**
         * Each &lt;Menu> may contain any number of &lt;OnlyUnallocated> and &lt;NotOnlyUnallocated>  elements. 
         * Only the last such element to appear is relevant, as it determines whether the &lt;Menu> can 
         * contain any desktop entries, or only those desktop entries that do not match other menus. 
         * If neither &lt;OnlyUnallocated> nor &lt;NotOnlyUnallocated> elements are present, the default is 
         * &lt;NotOnlyUnallocated>.
         * 
         * To handle &lt;OnlyUnallocated>, the menu file must be analyzed in two conceptual passes. 
         * The first pass processes &lt;Menu> elements that can match any desktop entry. During this pass, 
         * each desktop entry is marked as allocated according to whether it was matched by an &lt;Include> 
         * rule in some &lt;Menu>. The second pass processes only &lt;Menu> elements that are restricted to 
         * unallocated desktop entries. During the second pass, queries may only match desktop entries 
         * that were not marked as allocated during the first pass. See the section called “Generating the menus”.
         */
        NotOnlyUnallocated,
        /**
         * Each &lt;Menu> may contain any number of &lt;Deleted> and &lt;NotDeleted> elements. Only the last 
         * such element to appear is relevant, as it determines whether the &lt;Menu>  has been deleted. 
         * If neither &lt;Deleted> nor &lt;NotDeleted> elements are present, the default is &lt;NotDeleted>. 
         * The purpose of this element is to support menu editing. If a menu contains a &lt;Deleted> 
         * element not followed by a &lt;NotDeleted> element, that menu should be ignored.
         */
        Deleted,
        /**
         * Each &lt;Menu> may contain any number of &lt;Deleted> and &lt;NotDeleted> elements. Only the last 
         * such element to appear is relevant, as it determines whether the &lt;Menu>  has been deleted. 
         * If neither &lt;Deleted> nor &lt;NotDeleted> elements are present, the default is &lt;NotDeleted>. 
         * The purpose of this element is to support menu editing. If a menu contains a &lt;Deleted> 
         * element not followed by a &lt;NotDeleted> element, that menu should be ignored.
         */
        NotDeleted,
        /**
         * An &lt;Include> element is a set of rules attempting to match some of the known desktop entries. 
         * The &lt;Include> element contains a list of any number of matching rules. Matching rules are 
         * specified using the elements &lt;And>, &lt;Or>, &lt;Not>, &lt;All>, &lt;Filename>, and &lt;Category>. Each rule 
         * in a list of rules has a logical OR relationship, that is, desktop entries which match any rule 
         * are included in the menu.
         * 
         * &lt;Include> elements must appear immediately under &lt;Menu> elements. The desktop entries they 
         * match are included in the menu. &lt;Include> and &lt;Exclude> elements for a given &lt;Menu> are 
         * processed in order, with queries earlier in the file handled first. This has implications 
         * for merging, see the section called “Merging”. See the section called “Generating the menus” 
         * for full details on how to process &lt;Include> and &lt;Exclude> elements.
         */
        Include,
        /**
         * Any number of &lt;Exclude> elements may appear below a &lt;Menu> element. The content of an &lt;Exclude> 
         * element is a list of matching rules, just as with an &lt;Include>. However, the desktop entries 
         * matched are removed from the list of desktop entries included so far. (Thus an &lt;Exclude> element 
         * that appears before any &lt;Include> elements will have no effect, for example, as no desktop 
         * entries have been included yet.)
         */
        Exclude,
        /**
         * The &lt;Filename> element is the most basic matching rule. It matches a desktop entry if the 
         * desktop entry has the given desktop-file id. See Desktop-File Id.
         */
        Filename,
        /**
         * The &lt;Category> element is another basic matching predicate. It matches a desktop entry 
         * if the desktop entry has the given category in its Categories field.
         */
        Category,
        /**
         * The &lt;All> element is a matching rule that matches all desktop entries.
         */
        All,
        /**
         * The &lt;And> element contains a list of matching rules. If each of the matching rules inside 
         * the &lt;And>  element match a desktop entry, then the entire &lt;And> rule matches the desktop 
         * entry.
         */
        And,
        /**
         * The &lt;Or> element contains a list of matching rules. If any of the matching rules inside 
         * the &lt;Or>  element match a desktop entry, then the entire &lt;Or> rule matches the desktop entry.
         */
        Or,
        /**
         * The &lt;Not> element contains a list of matching rules. If any of the matching rules inside 
         * the &lt;Not> element matches a desktop entry, then the entire &lt;Not> rule does not match the 
         * desktop entry. That is, matching rules below &lt;Not> have a logical OR relationship.
         */
        Not,
        /**
         * Any number of &lt;MergeFile> elements may be listed below a &lt;Menu> element, giving the name 
         * of another menu file to be merged into this one. the section called “Merging”  specifies 
         * how merging is done. The root &lt;Menu> of the merged file will be merged into the immediate 
         * parent of the &lt;MergeFile> element. The &lt;Name> element of the root &lt;Menu> of the merged 
         * file are ignored.
         * 
         * If the type attribute is missing or set to "path" then the contents of the &lt;MergeFile> 
         * element indicates the file to be merged. If this is not an absolute path then the file 
         * to be merged should be located relative to the location of the menu file that contains 
         * this &lt;MergeFile> element.
         * 
         * Duplicate &lt;MergeFile> elements (that specify the same file) are handled as with duplicate 
         * &lt;AppDir> elements (the last duplicate is used).
         * 
         * If the type attribute is set to "parent" and the file that contains this &lt;MergeFile> 
         * element is located under one of the paths specified by $XDG_CONFIG_DIRS, the contents 
         * of the element should be ignored and the remaining paths specified by $XDG_CONFIG_DIRS 
         * are searched for a file with the same relative filename. The first file encountered 
         * should be merged. There should be no merging at all if no matching file is found.
         * 
         * Compatibility note: The filename specified inside the &lt;MergeFile> element should be 
         * ignored if the type attribute is set to "parent", it should however be expected that 
         * implementations based on previous versions of this specification will ignore the type 
         * attribute and that such implementations will use the filename inside the &lt;MergeFile> 
         * element instead.
         * 
         * Example 1: If $XDG_CONFIG_HOME is "~/.config/" and $XDG_CONFIG_DIRS is 
         * "/opt/gnome/:/etc/xdg/" and the file ~/.config/menus/applications.menu contains 
         * &lt;MergeFile type="parent">/opt/kde3/etc/xdg/menus/applications.menu&lt;/MergeFile> then the
         * file /opt/gnome/menus/applications.menu should be merged if it exists. If that file 
         * does not exists then the file /etc/xdg/menus/applications.menu should be merged instead.
         * 
         * Example 2: If $XDG_CONFIG_HOME is "~/.config/" and $XDG_CONFIG_DIRS is 
         * "/opt/gnome/:/etc/xdg/" and the file /opt/gnome/menus/applications.menu contains 
         * &lt;MergeFile type="parent">/opt/kde3/etc/xdg/menus/applications.menu&lt;/MergeFile> then the 
         * file /etc/xdg/menus/applications.menu should be merged if it exists.
         */
        MergeFile,
        /**
         *     Any number of &lt;MergeDir> elements may be listed below a &lt;Menu> element. A &lt;MergeDir> contains
         * the name of a directory. Each file in the given directory which ends in the ".menu" extension should
         * be merged in the same way that a &lt;MergeFile> would be. If the filename given as a &lt;MergeDir> is not 
         * an absolute path, it should be located relative to the location of the menu file being parsed. 
         * The files inside the merged directory are not merged in any specified order.
         * 
         * Duplicate &lt;MergeDir> elements (that specify the same directory) are handled as with duplicate &lt;AppDir> 
         * elements (the last duplicate is used).
         */
        MergeDir,
        /**
         * This element may only appear below &lt;Menu>. The element has no content. The element should 
         * be treated as if it were a list of &lt;MergeDir> elements containing the default merge directory
         * locations. When expanding &lt;DefaultMergeDirs> to a list of &lt;MergeDir>, the default locations 
         * that are earlier in the search path go later in the &lt;Menu> so that they have priority.
         */
        DefaultMergeDirs,
        /**
         * This element may only appear below &lt;Menu>. The text content of this element is a directory 
         * name. Each directory listed in a &lt;LegacyDir> element will be an old-style legacy hierarchy 
         * of desktop entries, see the section called “Legacy Menu Hierarchies” for how to load such 
         * a hierarchy. Implementations must not load legacy hierarchies that are not explicitly 
         * specified in the menu file (because for example the menu file may not be the main menu). 
         * If the filename given as a &lt;LegacyDir> is not an absolute path, it should be located relative 
         * to the location of the menu file being parsed.
         * 
         * Duplicate &lt;LegacyDir> elements (that specify the same directory) are handled as with duplicate 
         * &lt;AppDir> elements (the last duplicate is used).
         * 
         * The &lt;LegacyDir> element may have one attribute, prefix. Normally, given a &lt;LegacyDir> /foo/bar 
         * and desktop entry /foo/bar/baz/Hello.desktop the desktop entry would get a desktop-file id of 
         * Hello.desktop. Given a prefix of boo-, it would instead be assigned the desktop-file id boo-Hello.desktop. 
         * The prefix should not contain path separator ('/') characters.
         */
        LegacyDir,
        /**
         * This element may only appear below &lt;Menu>. The element has no content. The element should 
         * be treated as if it were a list of &lt;LegacyDir> elements containing the traditional desktop 
         * file locations supported by KDE with a hard coded prefix of "kde-". When expanding &lt;KDELegacyDirs> 
         * to a list of &lt;LegacyDir>, the locations that are earlier in the search path go later in the &lt;Menu> 
         * so that they have priority. The search path can be obtained by running kde-config --path apps
         */
        KDELegacyDirs,
        /**
         * This element may only appear below &lt;Menu>. The &lt;Move> element contains pairs of &lt;Old>/&lt;New>  
         * elements indicating how to rename a descendant of the current &lt;Menu>. If the destination path 
         * already exists, the moved menu is merged with the destination menu (see the section called “Merging” for details).
         * 
         * &lt;Move> is used primarily to fix up legacy directories. For example, say you are merging a 
         * &lt;LegacyDir> with folder names that don't match the current hierarchy; the legacy folder 
         * names can be moved to the new names, where they will be merged with the new folders.
         * 
         * &lt;Move> is also useful for implementing menu editing, see the section called “Menu editing”.
         */
        Move,
        /**
         * This element may only appear below &lt;Move>, and must be followed by a &lt;New> element. 
         * The content of both &lt;Old> and &lt;New> should be a menu path (slash-separated concatenation 
         * of &lt;Name> fields, see Menu path). Paths are interpreted relative to the menu containing 
         * the &lt;Move> element.
         */
        Old,
        /**
         * This element may only appear below &lt;Move>, and must be preceded by an &lt;Old> element. 
         * The &lt;New> element specifies the new path for the preceding &lt;Old> element.
         */
        New,
        /**
         * The &lt;Layout> element is an optional part of this specification. Implementations that do
         * not support the &lt;Layout> element should preserve any &lt;Layout> elements and their contents 
         * as far as possible. Each &lt;Menu> may optionally contain a &lt;Layout> element. If multiple elements 
         * appear then only the last such element is relevant. The purpose of this element is to offer 
         * suggestions for the presentation of the menu. If a menu does not contain a &lt;Layout> element 
         * or if it contains an empty &lt;Layout> element then the default layout should be used. The &lt;Layout> 
         * element may contain &lt;Filename>, &lt;Menuname>, &lt;Separator> and &lt;Merge> elements. The &lt;Layout> 
         * element defines a suggested layout for the menu starting from top to bottom. References to 
         * desktop entries that are not contained in this menu as defined by the &lt;Include> and &lt;Exclude> 
         * elements should be ignored. References to sub-menus that are not directly contained in this 
         * menu as defined by the &lt;Menu> elements should be ignored.
         */
        Layout,
        /**
         * The &lt;DefaultLayout> element is an optional part of this specification. 
         * Implementations that do not support the &lt;DefaultLayout> element should preserve any 
         * &lt;DefaultLayout> elements and their contents as far as possible. Each &lt;Menu> may 
         * optionally contain a &lt;DefaultLayout> element which defines the default-layout for 
         * the current menu and all its sub-menus. If a menu has a &lt;DefaultLayout> element then 
         * this will override any default-layout specified by a parent menu. The default-layout 
         * defines the suggested layout if a &lt;Menu> element does either not have &lt;Layout> element 
         * or if it has an empty &lt;Layout> element. For explanations of the various attributes see 
         * the &lt;Menuname> element. If no default-layout has been specified then the layout as 
         * specified by the following elements should be assumed: 
         *    &lt;DefaultLayout show_empty="false" inline="false" inline_limit="4" inline_header="true" inline_alias="false">&lt;Merge type="menus"/>&lt;Merge type="files"/>&lt;/DefaultLayout>
         */
        DefaultLayout,
        /**
         * This element may only appear as a child of a &lt;Layout> or &lt;DefaultLayout>  menu. Its contents 
         * references an immediate sub-menu of the current menu as defined with the &lt;Menu> element, as 
         * such it should never contain a slash. If no such sub-menu exists the element should be ignored.
         * This element may have various attributes, the default values are taken from the DefaultLayout key. 
         * The show_empty attribute defines whether a menu that contains no desktop entries and no sub-menus 
         * should be shown at all. The show_empty attribute can be "true" or "false". It may have an inline 
         * attribute that can be either "true" or "false". If the inline attribute is "true" the menu that 
         * is referenced may be copied into the current menu at the current point instead of being inserted 
         * as sub-menu of the current menu. The optional inline_limit attribute defines the maximum number 
         * of entries that can be inlined. If the sub-menu has more entries than inline_limit, the sub-menu 
         * will not be inlined. If the inline_limit is 0 (zero) there is no limit. The optional inline_header 
         * attribute defines whether an inlined menu should be preceded with a header entry listing the caption 
         * of the sub-menu. The inline_header attribute can be either "true" or "false". The optional inline_alias 
         * attribute defines whether a single inlined entry should adopt the caption of the inlined menu. 
         * In such case no additional header entry will be added regardless of the value of the inline_header 
         * attribute. The inline_alias attribute can be either "true" or "false". Example: if a menu has a sub-menu 
         * titled "WordProcessor" with a single entry "OpenOffice 4.2", and both inline="true" and inline_alias="true" 
         * are specified then this would result in the "OpenOffice 4.2" entry being inlined in the current menu but 
         * the "OpenOffice 4.2" caption of the entry would be replaced with "WordProcessor".
         */
        Menuname,
        /**
         * This element may only appear as a child of a &lt;Layout> or &lt;DefaultLayout>  menu. It indicates a 
         * suggestion to draw a visual separator at this point in the menu. &lt;Separator> elements at the 
         * start of a menu, at the end of a menu or that directly follow other &lt;Separator> elements may 
         * be ignored.
         */
        Separator,
        /**
         * This element may only appear as a child of a &lt;Layout> or &lt;DefaultLayout>  menu. It indicates 
         * the point where desktop entries and sub-menus that are not explicitly mentioned within the &lt;Layout>
         * or &lt;DefaultLayout> element are to be inserted. It has a type attribute that indicates which 
         * elements should be inserted: type="menus" means that all sub-menus that are not explicitly mentioned 
         * should be inserted in alphabetical order of their visual caption at this point. type="files" means 
         * that all desktop entries contained in this menu that are not explicitly mentioned should be inserted 
         * in alphabetical order of their visual caption at this point. type="all" means that a mix of all 
         * sub-menus and all desktop entries that are not explicitly mentioned should be inserted in alphabetical 
         * order of their visual caption at this point. Each &lt;Layout> or &lt;DefaultLayout> element shall have exactly 
         * one &lt;Merge type="all">  element or it shall have exactly one &lt;Merge type="files"> and exactly one 
         * &lt;Merge type="menus"> element. An exception is made for a completely empty &lt;Layout> element which may 
         * be used to indicate that the default-layout should be used instead.
         */
        Merge
    };
    /**
     * The root element is &lt;Menu>. Each &lt;Menu> element may contain any number of nested &lt;Menu> 
     * elements, indicating submenus.
     */
    public static final String Menu = "Menu";
    /**
     * This element may only appear below &lt;Menu>. The content of this element is a directory name.
     * Desktop entries in this directory are scanned and added to the pool of entries which can 
     * be included in this &lt;Menu> and its submenus. Only files ending in ".desktop" should be used, 
     * other files are ignored.
     * 
     * Desktop entries in the pool of available entries are identified by their desktop-file
     * id (see Desktop-File Id). The desktop-file id of a desktop entry is equal to its filename, 
     * with any path components removed. So given a &lt;AppDir> /foo/bar and desktop entry 
     * /foo/bar/Hello.desktop the desktop entry would get a desktop-file id of Hello.desktop
     * 
     * If the directory contains sub-directories then these sub-directories should be (recursively)
     * scanned as well. The name of the subdirectory should be added as prefix to the desktop-file 
     * id together with a dash character ("-") So given a &lt;AppDir> /foo/bar and desktop entry 
     * /foo/bar/booz/Hello.desktop the desktop entry would get a desktop-file id of booz-Hello.desktop 
     * A desktop entry /foo/bar/bo/oz/Hello.desktop would result in a desktop-file id of bo-oz-Hello.desktop
     * 
     * &lt;AppDir> elements appearing later in the menu file have priority in case of collisions
     * between desktop-file ids.
     * 
     * If the filename given as an &lt;AppDir> is not an absolute path, it should be located relative 
     * to the location of the menu file being parsed.
     * 
     * Duplicate &lt;AppDir> elements (that specify the same directory) should be ignored, but 
     * the last duplicate in the file should be used when establishing the order in which to
     * scan the directories. This is important when merging (see the section called “Merging”). 
     * The order of &lt;AppDir> elements with respect to &lt;Include> and &lt;Exclude> elements is not
     * relevant, also to facilitate merging.
     */
    public static final String AppDir = "AppDir";
    /**
     * This element may only appear below &lt;Menu>. The element has no content. The element should 
     * be treated as if it were a list of &lt;AppDir> elements containing the default app dir locations 
     * (datadir/applications/ etc.). When expanding &lt;DefaultAppDirs> to a list of &lt;AppDir>, the 
     * default locations that are earlier in the search path go later in the &lt;Menu> so that they 
     * have priority.
     */
    public static final String DefaultAppDirs = "DefaultAppDirs";
    /**
     * This element may only appear below &lt;Menu>. The content of this element is a directory name.
     * Each directory listed in a &lt;DirectoryDir> element will be searched for directory entries 
     * to be used when resolving the &lt;Directory> element for this menu and its submenus. 
     * If the filename given as a &lt;DirectoryDir> is not an absolute path, it should be located 
     * relative to the location of the menu file being parsed.
     * 
     * Directory entries in the pool of available entries are identified by their relative path 
     * (see Relative path).
     * 
     * If two directory entries have duplicate relative paths, the one from the last (furthest down)
     * element in the menu file must be used. Only files ending in the extension ".directory" should 
     * be loaded, other files should be ignored.
     * 
     * Duplicate &lt;DirectoryDir> elements (that specify the same directory) are handled as with 
     * duplicate &lt;AppDir> elements (the last duplicate is used).
     */
    public static final String DirectoryDir = "DirectoryDir";
    /**
     * This element may only appear below &lt;Menu>. The element has no content. The element should 
     * be treated as if it were a list of &lt;DirectoryDir> elements containing the default desktop 
     * dir locations (datadir/desktop-directories/ etc.). The default locations that are earlier 
     * in the search path go later in the &lt;Menu> so that they have priority.
     */
    public static final String DefaultDirectoryDirs = "DefaultDirectoryDirs";
    /**
     * Each &lt;Menu> element must have a single &lt;Name>  element. The content of the &lt;Name> element 
     * is a name to be used when referring to the given menu. Each submenu of a given &lt;Menu> must 
     * have a unique name. &lt;Menu>  elements can thus be referenced by a menu path, for example 
     * "Applications/Graphics." The &lt;Name> field must not contain the slash character ("/"); 
     * implementations should discard any name containing a slash. See also Menu path.
     */
    public static final String Name = "Name";
    /**
     * Each &lt;Menu> element has any number of &lt;Directory>  elements. The content of the &lt;Directory> 
     * element is the relative path of a directory entry containing meta information about the &lt;Menu>,
     * such as its icon and localized name. If no &lt;Directory> is specified for a &lt;Menu>, its &lt;Name> 
     * field should be used as the user-visible name of the menu.
     * 
     * Duplicate &lt;Directory> elements are allowed in order to simplify menu merging, and allow user 
     * menus to override system menus. The last &lt;Directory> element to appear in the menu file "wins" 
     * and other elements are ignored, unless the last element points to a nonexistent directory entry, 
     * in which case the previous element should be tried instead, and so on.
     */
    public static final String Directory = "Directory";
    /**
     * Each &lt;Menu> may contain any number of &lt;OnlyUnallocated> and &lt;NotOnlyUnallocated>  elements. 
     * Only the last such element to appear is relevant, as it determines whether the &lt;Menu> can 
     * contain any desktop entries, or only those desktop entries that do not match other menus. 
     * If neither &lt;OnlyUnallocated> nor &lt;NotOnlyUnallocated> elements are present, the default is 
     * &lt;NotOnlyUnallocated>.
     * 
     * To handle &lt;OnlyUnallocated>, the menu file must be analyzed in two conceptual passes. 
     * The first pass processes &lt;Menu> elements that can match any desktop entry. During this pass, 
     * each desktop entry is marked as allocated according to whether it was matched by an &lt;Include> 
     * rule in some &lt;Menu>. The second pass processes only &lt;Menu> elements that are restricted to 
     * unallocated desktop entries. During the second pass, queries may only match desktop entries 
     * that were not marked as allocated during the first pass. See the section called “Generating the menus”.
     */
    public static final String OnlyUnallocated = "OnlyUnallocated";
    /**
     * Each &lt;Menu> may contain any number of &lt;OnlyUnallocated> and &lt;NotOnlyUnallocated>  elements. 
     * Only the last such element to appear is relevant, as it determines whether the &lt;Menu> can 
     * contain any desktop entries, or only those desktop entries that do not match other menus. 
     * If neither &lt;OnlyUnallocated> nor &lt;NotOnlyUnallocated> elements are present, the default is 
     * &lt;NotOnlyUnallocated>.
     * 
     * To handle &lt;OnlyUnallocated>, the menu file must be analyzed in two conceptual passes. 
     * The first pass processes &lt;Menu> elements that can match any desktop entry. During this pass, 
     * each desktop entry is marked as allocated according to whether it was matched by an &lt;Include> 
     * rule in some &lt;Menu>. The second pass processes only &lt;Menu> elements that are restricted to 
     * unallocated desktop entries. During the second pass, queries may only match desktop entries 
     * that were not marked as allocated during the first pass. See the section called “Generating the menus”.
     */
    public static final String NotOnlyUnallocated = "NotOnlyUnallocated";
    /**
     * Each &lt;Menu> may contain any number of &lt;Deleted> and &lt;NotDeleted> elements. Only the last 
     * such element to appear is relevant, as it determines whether the &lt;Menu>  has been deleted. 
     * If neither &lt;Deleted> nor &lt;NotDeleted> elements are present, the default is &lt;NotDeleted>. 
     * The purpose of this element is to support menu editing. If a menu contains a &lt;Deleted> 
     * element not followed by a &lt;NotDeleted> element, that menu should be ignored.
     */
    public static final String Deleted = "Deleted";
    /**
     * Each &lt;Menu> may contain any number of &lt;Deleted> and &lt;NotDeleted> elements. Only the last 
     * such element to appear is relevant, as it determines whether the &lt;Menu>  has been deleted. 
     * If neither &lt;Deleted> nor &lt;NotDeleted> elements are present, the default is &lt;NotDeleted>. 
     * The purpose of this element is to support menu editing. If a menu contains a &lt;Deleted> 
     * element not followed by a &lt;NotDeleted> element, that menu should be ignored.
     */
    public static final String NotDeleted = "NotDeleted";
    /**
     * An &lt;Include> element is a set of rules attempting to match some of the known desktop entries. 
     * The &lt;Include> element contains a list of any number of matching rules. Matching rules are 
     * specified using the elements &lt;And>, &lt;Or>, &lt;Not>, &lt;All>, &lt;Filename>, and &lt;Category>. Each rule 
     * in a list of rules has a logical OR relationship, that is, desktop entries which match any rule 
     * are included in the menu.
     * 
     * &lt;Include> elements must appear immediately under &lt;Menu> elements. The desktop entries they 
     * match are included in the menu. &lt;Include> and &lt;Exclude> elements for a given &lt;Menu> are 
     * processed in order, with queries earlier in the file handled first. This has implications 
     * for merging, see the section called “Merging”. See the section called “Generating the menus” 
     * for full details on how to process &lt;Include> and &lt;Exclude> elements.
     */
    public static final String Include = "Include";
    /**
     * Any number of &lt;Exclude> elements may appear below a &lt;Menu> element. The content of an &lt;Exclude> 
     * element is a list of matching rules, just as with an &lt;Include>. However, the desktop entries 
     * matched are removed from the list of desktop entries included so far. (Thus an &lt;Exclude> element 
     * that appears before any &lt;Include> elements will have no effect, for example, as no desktop 
     * entries have been included yet.)
     */
    public static final String Exclude = "Exclude";   
    /**
     * The &lt;Filename> element is the most basic matching rule. It matches a desktop entry if the 
     * desktop entry has the given desktop-file id. See Desktop-File Id.
     */
    public static final String Filename = "Filename";    
    /**
     * The &lt;Category> element is another basic matching predicate. It matches a desktop entry 
     * if the desktop entry has the given category in its Categories field.
     */
    public static final String Category = "Category";
    
    /**
     * The &lt;All> element is a matching rule that matches all desktop entries.
     */
    public static final String All = "All";
    /**
     * The &lt;And> element contains a list of matching rules. If each of the matching rules inside 
     * the &lt;And>  element match a desktop entry, then the entire &lt;And> rule matches the desktop 
     * entry.
     */
    public static final String And = "And";
    /**
     * The &lt;Or> element contains a list of matching rules. If any of the matching rules inside 
     * the &lt;Or>  element match a desktop entry, then the entire &lt;Or> rule matches the desktop entry.
     */
    public static final String Or = "Or";
    /**
     * The &lt;Not> element contains a list of matching rules. If any of the matching rules inside 
     * the &lt;Not> element matches a desktop entry, then the entire &lt;Not> rule does not match the 
     * desktop entry. That is, matching rules below &lt;Not> have a logical OR relationship.
     */
    public static final String Not = "Not";
    /**
     * Any number of &lt;MergeFile> elements may be listed below a &lt;Menu> element, giving the name 
     * of another menu file to be merged into this one. the section called “Merging”  specifies 
     * how merging is done. The root &lt;Menu> of the merged file will be merged into the immediate 
     * parent of the &lt;MergeFile> element. The &lt;Name> element of the root &lt;Menu> of the merged 
     * file are ignored.
     * 
     * If the type attribute is missing or set to "path" then the contents of the &lt;MergeFile> 
     * element indicates the file to be merged. If this is not an absolute path then the file 
     * to be merged should be located relative to the location of the menu file that contains 
     * this &lt;MergeFile> element.
     * 
     * Duplicate &lt;MergeFile> elements (that specify the same file) are handled as with duplicate 
     * &lt;AppDir> elements (the last duplicate is used).
     * 
     * If the type attribute is set to "parent" and the file that contains this &lt;MergeFile> 
     * element is located under one of the paths specified by $XDG_CONFIG_DIRS, the contents 
     * of the element should be ignored and the remaining paths specified by $XDG_CONFIG_DIRS 
     * are searched for a file with the same relative filename. The first file encountered 
     * should be merged. There should be no merging at all if no matching file is found.
     * 
     * Compatibility note: The filename specified inside the &lt;MergeFile> element should be 
     * ignored if the type attribute is set to "parent", it should however be expected that 
     * implementations based on previous versions of this specification will ignore the type 
     * attribute and that such implementations will use the filename inside the &lt;MergeFile> 
     * element instead.
     * 
     * Example 1: If $XDG_CONFIG_HOME is "~/.config/" and $XDG_CONFIG_DIRS is 
     * "/opt/gnome/:/etc/xdg/" and the file ~/.config/menus/applications.menu contains 
     * &lt;MergeFile type="parent">/opt/kde3/etc/xdg/menus/applications.menu&lt;/MergeFile> then the
     * file /opt/gnome/menus/applications.menu should be merged if it exists. If that file 
     * does not exists then the file /etc/xdg/menus/applications.menu should be merged instead.
     * 
     * Example 2: If $XDG_CONFIG_HOME is "~/.config/" and $XDG_CONFIG_DIRS is 
     * "/opt/gnome/:/etc/xdg/" and the file /opt/gnome/menus/applications.menu contains 
     * &lt;MergeFile type="parent">/opt/kde3/etc/xdg/menus/applications.menu&lt;/MergeFile> then the 
     * file /etc/xdg/menus/applications.menu should be merged if it exists.
     */
    public static final String MergeFile = "MergeFile";
    /**
     *     Any number of &lt;MergeDir> elements may be listed below a &lt;Menu> element. A &lt;MergeDir> contains
     * the name of a directory. Each file in the given directory which ends in the ".menu" extension should
     * be merged in the same way that a &lt;MergeFile> would be. If the filename given as a &lt;MergeDir> is not 
     * an absolute path, it should be located relative to the location of the menu file being parsed. 
     * The files inside the merged directory are not merged in any specified order.
     * 
     * Duplicate &lt;MergeDir> elements (that specify the same directory) are handled as with duplicate &lt;AppDir> 
     * elements (the last duplicate is used).
     */
    public static final String MergeDir = "MergeDir";
    /**
     * This element may only appear below &lt;Menu>. The element has no content. The element should 
     * be treated as if it were a list of &lt;MergeDir> elements containing the default merge directory
     * locations. When expanding &lt;DefaultMergeDirs> to a list of &lt;MergeDir>, the default locations 
     * that are earlier in the search path go later in the &lt;Menu> so that they have priority.
     */
    public static final String DefaultMergeDirs = "DefaultMergeDirs";
    /**
     * This element may only appear below &lt;Menu>. The text content of this element is a directory 
     * name. Each directory listed in a &lt;LegacyDir> element will be an old-style legacy hierarchy 
     * of desktop entries, see the section called “Legacy Menu Hierarchies” for how to load such 
     * a hierarchy. Implementations must not load legacy hierarchies that are not explicitly 
     * specified in the menu file (because for example the menu file may not be the main menu). 
     * If the filename given as a &lt;LegacyDir> is not an absolute path, it should be located relative 
     * to the location of the menu file being parsed.
     * 
     * Duplicate &lt;LegacyDir> elements (that specify the same directory) are handled as with duplicate 
     * &lt;AppDir> elements (the last duplicate is used).
     * 
     * The &lt;LegacyDir> element may have one attribute, prefix. Normally, given a &lt;LegacyDir> /foo/bar 
     * and desktop entry /foo/bar/baz/Hello.desktop the desktop entry would get a desktop-file id of 
     * Hello.desktop. Given a prefix of boo-, it would instead be assigned the desktop-file id boo-Hello.desktop. 
     * The prefix should not contain path separator ('/') characters.
     */
    public static final String LegacyDir = "LegacyDir";
    /**
     * This element may only appear below &lt;Menu>. The element has no content. The element should 
     * be treated as if it were a list of &lt;LegacyDir> elements containing the traditional desktop 
     * file locations supported by KDE with a hard coded prefix of "kde-". When expanding &lt;KDELegacyDirs> 
     * to a list of &lt;LegacyDir>, the locations that are earlier in the search path go later in the &lt;Menu> 
     * so that they have priority. The search path can be obtained by running kde-config --path apps
     */
    public static final String KDELegacyDirs = "KDELegacyDirs";
    /**
     * This element may only appear below &lt;Menu>. The &lt;Move> element contains pairs of &lt;Old>/&lt;New>  
     * elements indicating how to rename a descendant of the current &lt;Menu>. If the destination path 
     * already exists, the moved menu is merged with the destination menu (see the section called “Merging” for details).
     * 
     * &lt;Move> is used primarily to fix up legacy directories. For example, say you are merging a 
     * &lt;LegacyDir> with folder names that don't match the current hierarchy; the legacy folder 
     * names can be moved to the new names, where they will be merged with the new folders.
     * 
     * &lt;Move> is also useful for implementing menu editing, see the section called “Menu editing”.
     */
    public static final String Move = "Move";
    /**
     * This element may only appear below &lt;Move>, and must be followed by a &lt;New> element. 
     * The content of both &lt;Old> and &lt;New> should be a menu path (slash-separated concatenation 
     * of &lt;Name> fields, see Menu path). Paths are interpreted relative to the menu containing 
     * the &lt;Move> element.
     */
    public static final String Old = "Old";
    /**
     * This element may only appear below &lt;Move>, and must be preceded by an &lt;Old> element. 
     * The &lt;New> element specifies the new path for the preceding &lt;Old> element.
     */
    public static final String New = "New";
    /**
     * The &lt;Layout> element is an optional part of this specification. Implementations that do
     * not support the &lt;Layout> element should preserve any &lt;Layout> elements and their contents 
     * as far as possible. Each &lt;Menu> may optionally contain a &lt;Layout> element. If multiple elements 
     * appear then only the last such element is relevant. The purpose of this element is to offer 
     * suggestions for the presentation of the menu. If a menu does not contain a &lt;Layout> element 
     * or if it contains an empty &lt;Layout> element then the default layout should be used. The &lt;Layout> 
     * element may contain &lt;Filename>, &lt;Menuname>, &lt;Separator> and &lt;Merge> elements. The &lt;Layout> 
     * element defines a suggested layout for the menu starting from top to bottom. References to 
     * desktop entries that are not contained in this menu as defined by the &lt;Include> and &lt;Exclude> 
     * elements should be ignored. References to sub-menus that are not directly contained in this 
     * menu as defined by the &lt;Menu> elements should be ignored.
     */
    public static final String Layout = "Layout";
    /**
     * The &lt;DefaultLayout> element is an optional part of this specification. 
     * Implementations that do not support the &lt;DefaultLayout> element should preserve any 
     * &lt;DefaultLayout> elements and their contents as far as possible. Each &lt;Menu> may 
     * optionally contain a &lt;DefaultLayout> element which defines the default-layout for 
     * the current menu and all its sub-menus. If a menu has a &lt;DefaultLayout> element then 
     * this will override any default-layout specified by a parent menu. The default-layout 
     * defines the suggested layout if a &lt;Menu> element does either not have &lt;Layout> element 
     * or if it has an empty &lt;Layout> element. For explanations of the various attributes see 
     * the &lt;Menuname> element. If no default-layout has been specified then the layout as 
     * specified by the following elements should be assumed: 
     *    &lt;DefaultLayout show_empty="false" inline="false" inline_limit="4" inline_header="true" inline_alias="false">&lt;Merge type="menus"/>&lt;Merge type="files"/>&lt;/DefaultLayout>
     */
    public static final String DefaultLayout = "DefaultLayout";
    /**
     * This element may only appear as a child of a &lt;Layout> or &lt;DefaultLayout>  menu. Its contents 
     * references an immediate sub-menu of the current menu as defined with the &lt;Menu> element, as 
     * such it should never contain a slash. If no such sub-menu exists the element should be ignored.
     * This element may have various attributes, the default values are taken from the DefaultLayout key. 
     * The show_empty attribute defines whether a menu that contains no desktop entries and no sub-menus 
     * should be shown at all. The show_empty attribute can be "true" or "false". It may have an inline 
     * attribute that can be either "true" or "false". If the inline attribute is "true" the menu that 
     * is referenced may be copied into the current menu at the current point instead of being inserted 
     * as sub-menu of the current menu. The optional inline_limit attribute defines the maximum number 
     * of entries that can be inlined. If the sub-menu has more entries than inline_limit, the sub-menu 
     * will not be inlined. If the inline_limit is 0 (zero) there is no limit. The optional inline_header 
     * attribute defines whether an inlined menu should be preceded with a header entry listing the caption 
     * of the sub-menu. The inline_header attribute can be either "true" or "false". The optional inline_alias 
     * attribute defines whether a single inlined entry should adopt the caption of the inlined menu. 
     * In such case no additional header entry will be added regardless of the value of the inline_header 
     * attribute. The inline_alias attribute can be either "true" or "false". Example: if a menu has a sub-menu 
     * titled "WordProcessor" with a single entry "OpenOffice 4.2", and both inline="true" and inline_alias="true" 
     * are specified then this would result in the "OpenOffice 4.2" entry being inlined in the current menu but 
     * the "OpenOffice 4.2" caption of the entry would be replaced with "WordProcessor".
     */
    public static final String Menuname = "Menuname";
    /**
     * This element may only appear as a child of a &lt;Layout> or &lt;DefaultLayout>  menu. It indicates a 
     * suggestion to draw a visual separator at this point in the menu. &lt;Separator> elements at the 
     * start of a menu, at the end of a menu or that directly follow other &lt;Separator> elements may 
     * be ignored.
     */
    public static final String Separator = "Separator";
    /**
     * This element may only appear as a child of a &lt;Layout> or &lt;DefaultLayout>  menu. It indicates 
     * the point where desktop entries and sub-menus that are not explicitly mentioned within the &lt;Layout>
     * or &lt;DefaultLayout> element are to be inserted. It has a type attribute that indicates which 
     * elements should be inserted: type="menus" means that all sub-menus that are not explicitly mentioned 
     * should be inserted in alphabetical order of their visual caption at this point. type="files" means 
     * that all desktop entries contained in this menu that are not explicitly mentioned should be inserted 
     * in alphabetical order of their visual caption at this point. type="all" means that a mix of all 
     * sub-menus and all desktop entries that are not explicitly mentioned should be inserted in alphabetical 
     * order of their visual caption at this point. Each &lt;Layout> or &lt;DefaultLayout> element shall have exactly 
     * one &lt;Merge type="all">  element or it shall have exactly one &lt;Merge type="files"> and exactly one 
     * &lt;Merge type="menus"> element. An exception is made for a completely empty &lt;Layout> element which may 
     * be used to indicate that the default-layout should be used instead.
     */
    public static final String Merge = "Merge";
    
    /**
     * Converts the given element name into a typesafe enum.
     * @param nodeName Name of the element node to identify
     * @return A typesafe enum that indicates the type of the given element
     */
    public static Possible toEnum(String nodeName) {
        Possible output;
        if(Menu.equals(nodeName))
            output = Possible.Menu;
        else if(AppDir.equals(nodeName))
            output = Possible.AppDir;
        else if(DefaultAppDirs.equals(nodeName))
            output = Possible.DefaultAppDirs;
        else if(DirectoryDir.equals(nodeName))
            output = Possible.DirectoryDir;
        else if(DefaultDirectoryDirs.equals(nodeName))
            output = Possible.DefaultDirectoryDirs;
        else if(Name.equals(nodeName))
            output = Possible.Name;
        else if(Directory.equals(nodeName))
            output = Possible.Directory;
        else if(OnlyUnallocated.equals(nodeName))
            output = Possible.OnlyUnallocated;
        else if(NotOnlyUnallocated.equals(nodeName))
            output = Possible.NotOnlyUnallocated;
        else if(Deleted.equals(nodeName))
            output = Possible.Deleted;
        else if(NotDeleted.equals(nodeName))
            output = Possible.NotDeleted;
        else if(Include.equals(nodeName))
            output = Possible.Include;
        else if(Exclude.equals(nodeName))
            output = Possible.Exclude;
        else if(Filename.equals(nodeName))
            output = Possible.Filename;
        else if(Category.equals(nodeName))
            output = Possible.Category;
        else if(All.equals(nodeName))
            output = Possible.All;
        else if(And.equals(nodeName))
            output = Possible.And;
        else if(Or.equals(nodeName))
            output = Possible.Or;
        else if(Not.equals(nodeName))
            output = Possible.Not;
        else if(MergeFile.equals(nodeName))
            output = Possible.MergeFile;
        else if(MergeDir.equals(nodeName))
            output = Possible.MergeDir;
        else if(DefaultMergeDirs.equals(nodeName))
            output = Possible.DefaultMergeDirs;
        else if(LegacyDir.equals(nodeName))
            output = Possible.LegacyDir;
        else if(KDELegacyDirs.equals(nodeName))
            output = Possible.KDELegacyDirs;
        else if(Move.equals(nodeName))
            output = Possible.Move;
        else if(Old.equals(nodeName))
            output = Possible.Old;
        else if(New.equals(nodeName))
            output = Possible.New;
        else if(Layout.equals(nodeName))
            output = Possible.Layout;
        else if(DefaultLayout.equals(nodeName))
            output = Possible.DefaultLayout;
        else if(Menuname.equals(nodeName))
            output = Possible.Menuname;
        else if(Separator.equals(nodeName))
            output = Possible.Separator;
        else if(Merge.equals(nodeName))
            output = Possible.Merge;
        else
            throw new IllegalArgumentException(nodeName + " is not valid here");
        return output;
    }
}