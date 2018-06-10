#!/bin/bash

APP_NAME="toc"                                                  # Name of the app
VERSION=$(parse_git_hash)                                       # App version
USERNAME=$1                                                     # Docker Hub username

cd ./target/universal                                           # Go to directory where application zip is located
rm -rf ./tmp ./dist                                             # Deleted existing directories if those exist

unzip ./${APP_NAME}*.zip -d ./tmp                               # Extract application zip
mv ./tmp/${APP_NAME}* ./dist                                    # Move application files into dist folder
rm -rf ./tmp                                                    # Remove temp directory
cd ../..                                                        # Go back to application root

docker build -t ${USERNAME}/${APP_NAME}:${VERSION} .            # Build docker image


function parse_git_hash() {
  git rev-parse --short HEAD 2> /dev/null | sed "s/\(.*\)/@\1/"
}