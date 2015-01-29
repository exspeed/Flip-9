"""
copyicon.py
--
Searches Google's material-design-icons for a certain icon.
Upon finding a match, asks for confirmation for file name.
Then, copies the proper resources to the corresponding drawable folder
of the desired program into the correct folders:
drawable-mdpi, drawable-hdpi, drawable-xhdpi, drawable-xxhpdi, drawable-xxxhdpi

IMPORTANT: Change resFolder to the appropriate /res/ folder path.

USAGE: python3 copyicon.py <icon> <new name>
"""

# CHANGE THIS PATH TO YOUR RES FOLDER
resFolder = 'Flip9/res'

# Material design icon folder path
iconFolder = 'material-design-icons'

import fnmatch, os, shutil, subprocess, sys

# Gets the file name from a path name
def getFileName(pathname):
    split = pathname.split('/')
    return split[(len(split) - 1)]

# Check for correct number of arguments
if len(sys.argv) != 3:
    sys.exit('Usage: python3 copyicon.py <icon> <new name>')

# Folder to search
findFile = sys.argv[1]
newFileName = sys.argv[2]

# Add .png extension if missing input
if '.png' not in newFileName:
    newFileName += '.png'

# File matching pattern
find = '*' + findFile + '*.png'

# Check if the material design icons are in the same folder
if not os.path.isdir(iconFolder):
    print('Cannot find Google\'s material-design-icons folder.')
    answer = input('Would you like to clone it now? (y/n): ')

    if 'y' in answer or 'Y' in answer:
        url = 'https://github.com/google/material-design-icons'
        subprocess.call('git clone ' + url, shell=True)
        print()
    else:
        sys.exit('The icons must be cloned in order to continue.')


# Check if folder exists
if not os.path.isdir(resFolder): 
    sys.exit('The specified resource folder \'' 
            + resFolder + '\' does not exist.\n'
            + 'Change the \'resFolder\' variable in this script.')

# Find files that match the icon and dp size
matches = []
for root, directories, filenames in os.walk(iconFolder):
    if 'drawable' in root:
        for filename in fnmatch.filter(filenames, find):
            matches.append(os.path.join(root, filename))

# Alert user if there are no matches
if len(matches) == 0:
    sys.exit('No matches found for \'' + findFile + '\'')

# Options found within mdpi folder
options = []
i = 0

# Show options
print('Options: ')
for pathname in matches:
    if 'mdpi' in pathname:
        options.append(pathname)
        print('[' + str(i) + ']: ' + getFileName(pathname))
        i += 1

# Get user selection
index = int(input('Selection: '))

# Error with selection index
if index < 0 or index >= len(options):
    sys.exit('Selection must be between 0 and ' + str(len(options) - 1) + '.')

# Get icon file name from full path name
icon = getFileName(options[index])

# Drawable folder path names
mdpi = resFolder + '/drawable-mdpi'
hdpi = resFolder + '/drawable-hdpi'
xhdpi = resFolder + '/drawable-xhdpi'
xxhdpi = resFolder + '/drawable-xxhdpi'
xxxhdpi = resFolder + '/drawable-xxxhdpi'

# Ensure that the folders exist
if not os.path.isdir(mdpi):
    os.mkdir(mdpi)
if not os.path.isdir(hdpi):
    os.mkdir(hdpi)
if not os.path.isdir(xhdpi):
    os.mkdir(xhdpi)
if not os.path.isdir(xxhdpi):
    os.mkdir(xxhdpi)
if not os.path.isdir(xxxhdpi):
    os.mkdir(xxxhdpi)

# Copy the files into the corresponding folders
for filename in matches:
    if icon in filename:
        if 'xxxhdpi' in filename:
            shutil.copy2(filename, xxxhdpi + '/' + newFileName)
        elif 'xxhdpi' in filename:
            shutil.copy2(filename, xxhdpi + '/' + newFileName)
        elif 'xhdpi' in filename:
            shutil.copy2(filename, xhdpi + '/' + newFileName)
        elif 'hdpi' in filename:
            shutil.copy2(filename, hdpi + '/' + newFileName)
        elif 'mdpi' in filename:
            shutil.copy2(filename, mdpi + '/' + newFileName)

print(icon + ' has been copied successfully as ' + newFileName + '.')
