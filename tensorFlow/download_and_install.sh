bplist00�_WebMainResource�	
_WebResourceData_WebResourceMIMEType_WebResourceTextEncodingName^WebResourceURL_WebResourceFrameNameO?<html><head></head><body><pre style="word-wrap: break-word; white-space: pre-wrap;">#!/bin/bash

set -e

VERSION=0.1alpha1
INSTALLER_PACKAGE=tensorflow_macos-$VERSION.tar.gz
INSTALLER_PATH=https://github.com/apple/tensorflow_macos/releases/download/v$VERSION/$INSTALLER_PACKAGE
INSTALLER_SCRIPT=install_venv.sh

echo

# Check to make sure we're good to go.
if [[ $(uname) != Darwin ]] || [[ $(sw_vers -productName) != macOS ]] || [[ $(sw_vers -productVersion) != "11."* ]] ; then 
  echo "ERROR: TensorFlow with ML Compute acceleration is only available on macOS 11.0 and later." 
  exit 1
fi

# This 
echo "Installation script for pre-release tensorflow_macos $VERSION.  Please visit https://github.com/apple/tensorflow_macos "
echo "for instructions and license information."   
echo
echo "This script will download tensorflow_macos $VERSION and needed binary dependencies, then install them into a new "
echo "or existing Python 3.8 virtual enviornoment."

# Make sure the user knows what's going on.  
read -p 'Continue [y/N]? '    

if [[ ! $REPLY =~ ^[Yy]$ ]]
then
exit 1
fi
echo

echo "Downloading installer."
tmp_dir=$(mktemp -d)

pushd $tmp_dir

curl -LO $INSTALLER_PATH 

echo "Extracting installer."
tar xf $INSTALLER_PACKAGE

cd tensorflow_macos 

function graceful_error () { 
  echo 
  echo "Error running installation script with default options.  Please fix the above errors and proceed by running "
  echo 
  echo "  $PWD/$INSTALLER_SCRIPT --prompt"
  echo 
  echo
  exit 1
}

bash ./$INSTALLER_SCRIPT --prompt || graceful_error 

popd
rm -rf $tmp_dir










</pre></body></html>Ztext/plainUUTF-8__https://raw.githubusercontent.com/apple/tensorflow_macos/master/scripts/download_and_install.shP    ( : P n } ����J                           K